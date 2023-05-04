package virtual_threads.controller;

import utils.Result;
import utils.SetupInfo;

import java.util.concurrent.CompletableFuture;

public interface SourceAnalyzer {
    CompletableFuture<Result> getReport(SetupInfo setupInfo);
}
