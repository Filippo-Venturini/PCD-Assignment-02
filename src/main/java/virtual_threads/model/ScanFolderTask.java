package virtual_threads.model;

import utils.Document;
import utils.Folder;
import utils.Result;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ScanFolderTask implements Runnable{
    private final Folder folder;
    private final Result result;
    private final CompletableFuture<Void> stopExecution;

    public ScanFolderTask(Folder folder, Result result, CompletableFuture<Void> stopExecution){
        this.folder = folder;
        this.result = result;
        this.stopExecution = stopExecution;
    }

    @Override
    public void run() {
        this.stopExecution.thenRun(() ->{
           Thread.currentThread().interrupt();
        });

        List<Thread> threads = new LinkedList<>();

        for(Folder subFolder : this.folder.getSubFolders()){
            Thread subFolderThread = Thread.ofVirtual().unstarted(new ScanFolderTask(subFolder, result, stopExecution));
            threads.add(subFolderThread);
            subFolderThread.start();
        }

        for(Document document : this.folder.getDocuments()){
            Thread documentThread = Thread.ofVirtual().unstarted(new CountLinesTask(document, result, stopExecution));
            threads.add(documentThread);
            documentThread.start();
        }

        for(Thread threadToWait : threads){
            try {
                threadToWait.join();
            } catch (InterruptedException e) {
            }
        }
    }
}
