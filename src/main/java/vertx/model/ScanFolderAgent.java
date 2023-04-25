package vertx.model;

import executors.model.Document;
import executors.model.Folder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import vertx.controller.Controller;

public class ScanFolderAgent extends AbstractVerticle {

    private final Folder folder;
    private int deployedAgent;
    private final Controller controller;

    public ScanFolderAgent(Controller controller, Folder folder){
        this.controller = controller;
        this.folder = folder;
    }

    public void start(Promise<Void> startPromise) {
        //log("started");
        deployedAgent += this.folder.getSubFolders().size();
        deployedAgent += this.folder.getDocuments().size();

        if(deployedAgent == 0){
            startPromise.complete();
            return;
        }

        for(Folder subFolder : this.folder.getSubFolders()){
            vertx.deployVerticle(new ScanFolderAgent(controller, subFolder), res -> {
                deployedAgent--;

                if(deployedAgent == 0){
                    startPromise.complete();
                    log("COMPLETE: ");
                    //this.undeploy();
                }
            });
        }

        for(Document document : this.folder.getDocuments()){
            vertx.deployVerticle(new CountLinesAgent(controller, document), res -> {
                deployedAgent--;

                if(deployedAgent == 0){
                    startPromise.complete();

                    //this.undeploy();
                }
            });
        }
    }

    private void undeploy(){
        vertx.undeploy(context.deploymentID()).onComplete(res -> {
            if (res.succeeded()) {
                log("Undeployed ok");
            } else {
                log("Undeploy failed!");
            }
        });
    }

    private void log(String msg) {
        System.out.println("[SCAN FOLDER AGENT][" + Thread.currentThread() + "] " + msg);
    }
}
