package vertx.controller;

import executors.model.Folder;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.impl.future.PromiseImpl;
import utils.AnalyzedFile;
import utils.Result;
import utils.SetupInfo;
import vertx.model.Model;
import vertx.model.ScanFolderAgent;

import java.io.File;
import java.io.IOException;

public class Controller implements SourceAnalyzer{
    private final Model model;

    public Controller (Model model){
        this.model = model;
    }

    @Override
    public Future<Result> getReport(SetupInfo setupInfo, Vertx vertx) {
        try {
            Promise<Result> result = new PromiseImpl<>();
            this.model.setResult(new Result(setupInfo.nIntervals(), setupInfo.lastIntervalLowerBound()));
            vertx.deployVerticle(new ScanFolderAgent(this, Folder.fromDirectory(new File(setupInfo.dir()))), res -> {
                System.out.println("CALLBACK: "+this.model.getResult());
                result.complete(this.model.getResult());
            });
            return result.future();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAnalyzedFile(AnalyzedFile analyzedFile){
        this.model.getResult().add(analyzedFile);
    }
}
