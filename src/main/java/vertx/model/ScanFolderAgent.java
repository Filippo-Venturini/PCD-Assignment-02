package vertx.model;

import executors.model.Document;
import executors.model.Folder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class ScanFolderAgent extends AbstractVerticle {

    private final Folder folder;
    private int deployedAgent;

    public ScanFolderAgent(Folder folder){
        this.folder = folder;
    }

    public void start(Promise<Void> startPromise) {

        deployedAgent += this.folder.getSubFolders().size();
        deployedAgent += this.folder.getDocuments().size();

        for(Folder subFolder : this.folder.getSubFolders()){
            Vertx.vertx().deployVerticle(new ScanFolderAgent(subFolder), res -> {
                deployedAgent--;

                if(deployedAgent == 0){
                    startPromise.complete();
                }
            });
        }

        for(Document document : this.folder.getDocuments()){
            Vertx.vertx().deployVerticle(new CountLinesAgent(document), res -> {
                deployedAgent--;

                if(deployedAgent == 0){
                    startPromise.complete();
                }
            });
        }

    }
}
