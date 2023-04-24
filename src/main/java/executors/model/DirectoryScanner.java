package executors.model;

import utils.Report;
import utils.Result;

import java.util.concurrent.ForkJoinPool;

public class DirectoryScanner {

    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public Result scan(Folder folder) {
        return forkJoinPool.invoke(new ScanFolderTask(folder));
    }

}
