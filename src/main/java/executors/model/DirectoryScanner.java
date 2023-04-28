package executors.model;

import utils.Report;
import utils.Result;
import utils.SetupInfo;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

public class DirectoryScanner {

    private final ForkJoinPool forkJoinPool = new ForkJoinPool();
    private final CompletableFuture<Result> finalReport = new CompletableFuture<>();
    private Result midReport;

    public void scan(Folder folder, SetupInfo setupInfo) {
        this.finalReport.complete(forkJoinPool.invoke(new ScanFolderTask(folder, setupInfo, midReport)));
    }

    public CompletableFuture<Result> getFinalReport(){
        return this.finalReport;
    }

    public Result getMidReport(){
        return this.midReport;
    }

    public void resetMidReport(SetupInfo setupInfo){
        this.midReport = new Result(setupInfo.nIntervals(), setupInfo.lastIntervalLowerBound());
    }

}
