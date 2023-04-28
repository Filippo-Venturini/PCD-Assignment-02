package executors.model;
import utils.AnalyzedFile;
import utils.Result;
import utils.SetupInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ScanFolderTask extends RecursiveTask<Result> {
    private final Folder folder;
    private final SetupInfo setupInfo;
    private final Result midReport;

    public ScanFolderTask(Folder folder, SetupInfo setupInfo, Result midReport){
        super();
        this.folder = folder;
        this.setupInfo = setupInfo;
        this.midReport = midReport;
    }

    @Override
    protected Result compute() {
        List<RecursiveTask<AnalyzedFile>> countLinesTasks = new LinkedList<RecursiveTask<AnalyzedFile>>();
        List<RecursiveTask<Result>> scanFoldersTasks = new LinkedList<RecursiveTask<Result>>();

        for (Document document : folder.getDocuments()) {
            CountLinesTask task = new CountLinesTask(document, midReport);
            countLinesTasks.add(task);
            task.fork();
        }

        for (Folder subFolder : folder.getSubFolders()) {
            ScanFolderTask task = new ScanFolderTask(subFolder, setupInfo, midReport);
            scanFoldersTasks.add(task);
            task.fork();
        }

        Result result = new Result(setupInfo.nIntervals(), setupInfo.lastIntervalLowerBound());

        for(RecursiveTask<AnalyzedFile> countLinesTask : countLinesTasks){
            result.add(countLinesTask.join());
        }

        for(RecursiveTask<Result> scanFolderTask : scanFoldersTasks){
            result.merge(scanFolderTask.join());
        }

        return result;
    }
}
