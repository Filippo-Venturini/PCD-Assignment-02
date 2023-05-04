package virtual_threads.controller;

import utils.Folder;
import utils.Result;
import utils.SetupInfo;
import virtual_threads.model.Model;
import virtual_threads.model.ScanFolderTask;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Controller implements SourceAnalyzer{

    private final Model model;

    public Controller(Model model){
        this.model = model;
    }

    @Override
    public CompletableFuture<Result> getReport(SetupInfo setupInfo) {
        CompletableFuture<Result> result = new CompletableFuture<>();

        this.model.init(setupInfo);

        Thread.ofVirtual().start(() -> {
            Thread rootThread = null;
            try {
                rootThread = Thread.ofVirtual().unstarted(new ScanFolderTask(Folder.fromDirectory(new File(setupInfo.dir())), this.model.getResult()));
                rootThread.start();
                rootThread.join();
                result.complete(model.getResult());
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        return result;
    }
}
