package vertx.model;

import utils.Document;
import utils.Folder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.impl.future.PromiseImpl;
import vertx.controller.Controller;

public class ScanFolderAgent extends AbstractVerticle {

    private final Folder folder;
    private final Controller controller;
    private int deployedAgents = 0;
    private Promise<Void> isCompleted = new PromiseImpl<>();
    private boolean isRoot = false;

    public ScanFolderAgent(Controller controller, Folder folder, boolean isRoot) {
        this(controller, folder);
        this.isRoot = isRoot;
    }

    public ScanFolderAgent(Controller controller, Folder folder) {
        this.folder = folder;
        this.controller = controller;
    }

    @Override
    public void start(Promise<Void> startPromise) throws InterruptedException {

        deployedAgents += folder.getSubFolders().size();
        deployedAgents += folder.getDocuments().size();

        vertx.eventBus().consumer("stop", message -> {
            if(isRoot){
                log(context.deploymentID());
                vertx.undeploy(context.deploymentID()).onFailure(res -> {
                    res.printStackTrace();
                });
            }else {
                vertx.undeploy(context.deploymentID());
            }
        });

        if(deployedAgents == 0){
            startPromise.complete();
            isCompleted.complete();
            return;
        }

        if(isRoot){
            this.log(folder + "scanning folders");
        }

        for (Folder subFolder: folder.getSubFolders()) {
            final ScanFolderAgent scanFolderAgent = new ScanFolderAgent(this.controller, subFolder);
            vertx.deployVerticle(scanFolderAgent);
            scanFolderAgent.onComputationEnded().onComplete(res -> {
                deployedAgents--;
                if(deployedAgents == 0){
                    if(isRoot){
                        this.log(folder + " stop");
                    }
                    isCompleted.complete();
                }
            });
        }

        if(isRoot){
            this.log(folder + "scanning documents");
        }

        for (Document document: folder.getDocuments()) {
            CountLinesAgent countLinesAgent = new CountLinesAgent(controller, document);
            this.vertx.deployVerticle(countLinesAgent).onComplete(res -> {
                deployedAgents--;
                if(deployedAgents == 0){
                    if(isRoot){
                        this.log(folder + " stop");
                    }
                    isCompleted.complete();
                }
            });
        }
        if(isRoot){
            this.log(folder + " ended start");
        }

        startPromise.complete();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public Future<Void> onComputationEnded(){
        return isCompleted.future();
    }

    private void log(String msg) {
        System.out.println("[SCAN FOLDER AGENT]["+Thread.currentThread()+"] " + msg);
    }

}