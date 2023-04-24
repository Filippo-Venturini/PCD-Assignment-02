package executors.model;

import utils.Report;
import utils.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

public class DirectoryScanner {

    private final ForkJoinPool forkJoinPool = new ForkJoinPool();
    private final CompletableFuture<Result> finalReport = new CompletableFuture<>();

    public void scan(Folder folder) {
        this.finalReport.complete(forkJoinPool.invoke(new ScanFolderTask(folder)));
    }

    public CompletableFuture<Result> getFinalReport(){
        return this.finalReport;
    }

}
