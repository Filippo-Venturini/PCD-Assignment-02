package executors.model;
import utils.AnalyzedFile;
import utils.Result;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ScanFolderTask extends RecursiveTask<Result> {
    private final Folder folder;

    public ScanFolderTask(Folder folder){
        super();
        this.folder = folder;
    }

    @Override
    protected Result compute() {
        List<RecursiveTask<AnalyzedFile>> countLinesTasks = new LinkedList<RecursiveTask<AnalyzedFile>>();
        List<RecursiveTask<Result>> scanFoldersTasks = new LinkedList<RecursiveTask<Result>>();

        for (Document document : folder.getDocuments()) {
            CountLinesTask task = new CountLinesTask(document);
            countLinesTasks.add(task);
            task.fork();
        }

        for (Folder subFolder : folder.getSubFolders()) {
            ScanFolderTask task = new ScanFolderTask(subFolder);
            scanFoldersTasks.add(task);
            task.fork();
        }

        Result result = new Result(10, 100);

        for(RecursiveTask<AnalyzedFile> countLinesTask : countLinesTasks){
            result.add(countLinesTask.join());
        }

        for(RecursiveTask<Result> scanFolderTask : scanFoldersTasks){
            result.merge(scanFolderTask.join());
        }

        return result;
    }
}
