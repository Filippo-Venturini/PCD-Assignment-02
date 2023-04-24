package tmp.controller;

import executors.controller.SourceAnalyzer;
import tmp.model.Model;
import utils.Report;
import tmp.model.SetupInfo;

import java.util.concurrent.CompletableFuture;

public class Controller implements SourceAnalyzer {
    private final Model model;

    public Controller(Model model){
        this.model = model;
    }

    @Override
    public CompletableFuture<Report> getReport(SetupInfo setupInfo) {
        return this.model.getScanDirectoryService().getFinalReport();
    }

    public void start(){
        this.model.getScanDirectoryService().start();
    }
}
