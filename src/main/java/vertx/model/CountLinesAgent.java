package vertx.model;

import executors.model.Document;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import utils.AnalyzedFile;

public class CountLinesAgent extends AbstractVerticle {
    private final Document document;

    public CountLinesAgent(Document document){
        this.document = document;
    }

    public void start(Promise<Void> startPromise) {
        EventBus eb = this.getVertx().eventBus();

        eb.publish("analized-file", new AnalyzedFile(document.getPath(), document.countLines()));
        startPromise.complete();
    }
}
