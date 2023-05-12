package rx.controller;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import rx.model.Model;
import utils.AnalyzedFile;
import utils.Folder;
import utils.Result;
import utils.SetupInfo;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller implements SourceAnalyzer{
    private final Model model;

    public Controller(Model model){
        this.model = model;
    }

    @Override
    public Single<Result> getReport(SetupInfo setupInfo){
        this.model.resetStopExecution();
        Result emptyResult = new Result(setupInfo.nIntervals(), setupInfo.lastIntervalLowerBound(), setupInfo.nFiles());

        return this.analyzeFolder(setupInfo.dir())
                .sequential() //Merge all the parallel flowable in a single one
                .reduce(emptyResult, (result, af) -> result.accumulate(af));
    }

    @Override
    public Flowable<Result> analyzeSources(SetupInfo setupInfo){
        this.model.resetStopExecution();
        Result result = new Result(setupInfo.nIntervals(), setupInfo.lastIntervalLowerBound(), setupInfo.nFiles());

        return this.analyzeFolder(setupInfo.dir())
                .sequential()//Merge all the parallel flowable in a single one
                .map(af -> result.accumulate(af));
    }

    private ParallelFlowable<AnalyzedFile> analyzeFolder(String folder){
        return Flowable.just(folder)
                .map(p -> Folder.fromDirectory(new File(p)))
                .flatMap(f -> this.getSubFolders(f))
                .subscribeOn(Schedulers.io())  //Execute the upstream operators on another Thread
                .parallel()
                .runOn(Schedulers.computation()) //Execute each parallel computation on a different Thread
                .flatMap(f -> Flowable.fromIterable(f.getDocuments()))
                .map(d -> new AnalyzedFile(d.getPath(), d.countLines()));
    }

    private Flowable<Folder> getSubFolders(Folder folder){
        return Flowable
                .fromIterable(folder.getSubFolders())
                .skipWhile(af -> this.model.getStopExecution().get())
                .flatMap(f -> this.getSubFolders(f))
                .concatWith(Flowable.just(folder));
    }

    public void stopExecution(){
        this.model.getStopExecution().set(true);
    }
}
