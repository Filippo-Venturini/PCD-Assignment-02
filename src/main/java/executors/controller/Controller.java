package executors.controller;

import executors.model.Folder;
import executors.model.Model;
import utils.SetupInfo;
import utils.Report;
import utils.Result;

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
        return this.model.getDirectoryScanner().getFinalReport();
    }

    @Override
    public Result analyzeSources(SetupInfo setupInfo) {
        return this.model.getDirectoryScanner().getMidReport();
    }

    public void startScan(SetupInfo setupInfo) throws IOException {
        this.model.getDirectoryScanner().resetMidReport(setupInfo);
        this.model.getDirectoryScanner().scan(Folder.fromDirectory(new File(setupInfo.dir())), setupInfo);
    }

    public void processEvent(Runnable runnable){
        new Thread(runnable).start();
    }
}
