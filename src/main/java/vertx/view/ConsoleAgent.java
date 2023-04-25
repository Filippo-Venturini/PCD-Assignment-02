package vertx.view;

import io.vertx.core.AbstractVerticle;
import utils.*;
import vertx.controller.Controller;

import java.util.Map;
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
        controller.getReport(setupInfo, vertx).onComplete(res -> {
            System.out.println("Files ranking:");
            for(AnalyzedFile result : res.result().getRanking()){
                System.out.println(result.path() + " has: " + result.lines() + " lines.");
            }
            System.out.println("\nFiles distribution:");
            for(Map.Entry<Interval, Integer> entry : res.result().getDistribution().entrySet()){
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
            vertx.close();
        });
    }

    private void log(String msg) {
        System.out.println("[CONSOLE AGENT][" + Thread.currentThread() + "] " + msg);
    }
}
