package executors.model;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ComputeResultTask implements Runnable{
    private final Result result;
    private final Future<AnalyzedFile> toCompute;

    public ComputeResultTask(Result result, Future<AnalyzedFile> toCompute){
        this.result = result;
        this.toCompute = toCompute;
    }

    @Override
    public void run() {
        try {
            this.result.add(toCompute.get());
            //TODO UPDATE VIEW RISULTATI PARZIALI
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
