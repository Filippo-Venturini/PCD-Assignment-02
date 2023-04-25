package vertx.view;

import io.vertx.core.AbstractVerticle;
import utils.SetupInfo;
import utils.Strings;
import vertx.controller.Controller;

import java.util.Scanner;
import java.util.Set;

public class ConsoleAgent extends AbstractVerticle {
    private final Controller controller;
    private final SetupInfo setupInfo;

    public ConsoleAgent(Controller controller, SetupInfo setupInfo){
        this.controller = controller;
        this.setupInfo = setupInfo;
    }

    public void start() {
        log("started");
        controller.getReport(setupInfo, vertx).onComplete(res -> {
            System.out.println(res.result().getRanking(10));
            System.out.println(res.result().getDistribution());
        });
    }

    private void log(String msg) {
        System.out.println("[CONSOLE AGENT][" + Thread.currentThread() + "] " + msg);
    }
}
