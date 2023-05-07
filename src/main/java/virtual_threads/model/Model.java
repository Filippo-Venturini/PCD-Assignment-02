package virtual_threads.model;

import utils.Result;
import utils.SetupInfo;

import java.util.concurrent.CompletableFuture;

public class Model {
    private Result result;
    private CompletableFuture<Void> stopExecution;

    public void init(SetupInfo setupInfo){
        this.result = new Result(setupInfo.nIntervals(), setupInfo.lastIntervalLowerBound(), setupInfo.nFiles());
        this.stopExecution = new CompletableFuture<>();
    }

    public Result getResult(){
        return this.result;
    }

    public CompletableFuture<Void> getStopExecution(){
        return this.stopExecution;
    }

}
