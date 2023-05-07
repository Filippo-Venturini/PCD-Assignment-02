package virtual_threads.controller;

import utils.Result;
import utils.SetupInfo;

import java.util.Vector;
import java.util.concurrent.CompletableFuture;

public interface SourceAnalyzer {
    CompletableFuture<Result> getReport(SetupInfo setupInfo);
    Result analyzeSources(SetupInfo setupInfo, CompletableFuture<Void> executionEnded);
}
