package vertx.view;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import utils.Result;
import utils.SetupInfo;
import utils.Strings;
import vertx.controller.Controller;

import java.util.Scanner;

public class ConsoleAgent extends AbstractVerticle {
    private final Controller controller;
    private SetupInfo setupInfo;

    public ConsoleAgent(Controller controller, SetupInfo setupInfo){
        this.controller = controller;
        this.setupInfo = setupInfo;
    }

    public void start() {
        EventBus eb = this.getVertx().eventBus();
        this.controller.getReport(this.setupInfo, eb);

        eb.consumer("final-report", message -> {
            Result result = (Result) message;
            System.out.println(result.getRanking(10));
            System.out.println(result.getDistribution());
        });
    }


}
