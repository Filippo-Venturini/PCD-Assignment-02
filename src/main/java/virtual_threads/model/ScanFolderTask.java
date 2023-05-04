package virtual_threads.model;

import utils.Document;
import utils.Folder;
import utils.Result;

import java.util.LinkedList;
import java.util.List;

public class ScanFolderTask implements Runnable{
    private final Folder folder;
    private final Result result;

    public ScanFolderTask(Folder folder, Result result){
        this.folder = folder;
        this.result = result;
    }

    @Override
    public void run() {
        List<Thread> threads = new LinkedList<>();

        for(Folder subFolder : this.folder.getSubFolders()){
            Thread subFolderThread = Thread.ofVirtual().unstarted(new ScanFolderTask(subFolder, result));
            threads.add(subFolderThread);
            subFolderThread.start();
        }

        for(Document document : this.folder.getDocuments()){
            Thread documentThread = Thread.ofVirtual().unstarted(new CountLinesTask(document, result));
            threads.add(documentThread);
            documentThread.start();
        }

        for(Thread threadToWait : threads){
            try {
                threadToWait.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
