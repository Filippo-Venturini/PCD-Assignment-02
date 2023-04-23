package executors.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class ScanDirectoryService extends Thread{
    private String startDir;
    private ExecutorService executor;
    private Result result = new Result(10, 200);

    public ScanDirectoryService(String startDir, int nThread){
        this.startDir = startDir;
        this.executor = Executors.newFixedThreadPool(nThread);
    }

    @Override
    public void run() {
        List<String> paths = new ArrayList<>();

        try {
            paths = this.executor.submit(new SearchFilesTask(this.startDir)).get();
        }  catch (Exception ex){
            ex.printStackTrace();
        }

        for(String path : paths){
            Future<AnalyzedFile> analyzedFileFuture = this.executor.submit(new CountLinesTask(path));
            this.executor.execute(new ComputeResultTask(result, analyzedFileFuture));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(result.getRanking(10));
        System.out.println(result.getDistribution());
        //TODO UPDATE VIEW RISULTATI FINALI
    }
}
