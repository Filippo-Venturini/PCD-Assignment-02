package executors.controller;

import utils.Report;
import tmp.model.SetupInfo;

import java.util.concurrent.CompletableFuture;

public interface SourceAnalyzer {
    CompletableFuture<Report> getReport(SetupInfo setupInfo);

    //analizeSources(String dir);
}
