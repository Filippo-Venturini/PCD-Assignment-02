package vertx.model;

import executors.model.Folder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import utils.AnalyzedFile;
import utils.Result;
import utils.SetupInfo;

import java.io.File;
import java.io.IOException;

public class MasterAgent extends AbstractVerticle {

    private Result result;

    public void start() {
        EventBus eb = this.getVertx().eventBus();

        eb.consumer("scan", message -> {
            SetupInfo setupInfo = (SetupInfo) message;

            this.result = new Result(setupInfo.nIntervals(), setupInfo.lastIntervalLowerBound());

            try {
                Vertx.vertx().deployVerticle(new ScanFolderAgent(Folder.fromDirectory(new File(setupInfo.dir()))), res -> {
                    eb.publish("final-report", this.result);
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        eb.consumer("analized-file", message -> {
            AnalyzedFile analyzedFile = (AnalyzedFile) message;
            this.result.add(analyzedFile);
            eb.publish("mid-report", this.result);
        });
    }
}
