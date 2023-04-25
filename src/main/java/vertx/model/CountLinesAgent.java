package vertx.model;

import executors.model.Document;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import utils.AnalyzedFile;
import vertx.controller.Controller;

public class CountLinesAgent extends AbstractVerticle {
    private final Document document;
    private final Controller controller;

    public CountLinesAgent(Controller controller, Document document){
        this.controller = controller;
        this.document = document;
    }

    public void start(Promise<Void> startPromise) {
        this.controller.addAnalyzedFile(new AnalyzedFile(document.getPath(), document.countLines()));

        EventBus eb = vertx.eventBus();

        eb.consumer("stop-execution", message -> {
            startPromise.complete();
        });

        eb.publish("mid-report","");

        startPromise.complete();
    }

    private void log(String msg) {
        System.out.println("[COUNT LINES AGENT][" + Thread.currentThread() + "] " + msg);
    }
}
