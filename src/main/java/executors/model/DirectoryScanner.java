package executors.model;

import utils.Folder;
import utils.Result;
import utils.SetupInfo;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class DirectoryScanner {

    private ForkJoinPool forkJoinPool = new ForkJoinPool();
    private Result midReport;

    public ForkJoinTask<Result> getFinalReport(SetupInfo setupInfo){
        this.resetMidReport(setupInfo);
        try {
            return forkJoinPool.submit(new ScanFolderTask(Folder.fromDirectory(new File(setupInfo.dir())), setupInfo, midReport));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Result getMidReport(SetupInfo setupInfo){
        this.resetMidReport(setupInfo);
        try {
            this.forkJoinPool.execute(new ScanFolderTask(Folder.fromDirectory(new File(setupInfo.dir())), setupInfo, midReport));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.midReport;
    }

    private void resetMidReport(SetupInfo setupInfo){
        this.midReport = new Result(setupInfo.nIntervals(), setupInfo.lastIntervalLowerBound(), setupInfo.nFiles());
    }

    public void stopExecution(){
        this.forkJoinPool.shutdownNow();
        this.forkJoinPool = new ForkJoinPool();
    }

}
