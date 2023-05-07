package virtual_threads.controller;

import utils.Folder;
import utils.Result;
import utils.SetupInfo;
import virtual_threads.model.Model;
import virtual_threads.model.ScanFolderTask;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Controller implements SourceAnalyzer{

    private final Model model;

    public Controller(Model model){
        this.model = model;
    }

    @Override
    public CompletableFuture<Result> getReport(SetupInfo setupInfo) {
        CompletableFuture<Result> result = new CompletableFuture<>();

        this.model.init(setupInfo);

        Thread.ofVirtual().start(() -> {
            Thread rootThread = null;
            try {
                rootThread = Thread.ofVirtual().unstarted(new ScanFolderTask(Folder.fromDirectory(new File(setupInfo.dir())), this.model.getResult(), this.model.getStopExecution()));
                rootThread.start();
                rootThread.join();
                result.complete(model.getResult());
            } catch (InterruptedException | IOException e) {
            }
        });

        return result;
    }

    @Override
    public Result analyzeSources(SetupInfo setupInfo, CompletableFuture<Void> executionEnded) {

        this.model.init(setupInfo);

        Thread.ofVirtual().start(() -> {
            Thread rootThread = null;
            try {
                rootThread = Thread.ofVirtual().unstarted(new ScanFolderTask(Folder.fromDirectory(new File(setupInfo.dir())), this.model.getResult(), this.model.getStopExecution()));
                rootThread.start();
                rootThread.join();
                executionEnded.complete(null);
            } catch (InterruptedException | IOException e) {
            }
        });

        return this.model.getResult();
    }

    public void stopExecution(){
        this.model.getStopExecution().complete(null);
    }

    public void processEvent(Runnable runnable){
        Thread.ofVirtual().start(runnable);
    }

}
