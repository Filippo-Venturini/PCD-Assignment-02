package rx.model;

import java.util.concurrent.atomic.AtomicBoolean;

public class Model {
    private final AtomicBoolean stopExecution = new AtomicBoolean();

    public AtomicBoolean getStopExecution(){
        return this.stopExecution;
    }

    public void resetStopExecution(){
        this.stopExecution.set(false);
    }
}
