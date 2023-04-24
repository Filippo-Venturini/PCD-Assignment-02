package tmp.model;

import executors.model.Document;
import utils.AnalyzedFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class CountLinesTask implements Callable<AnalyzedFile> {
    private final Document document;

    public CountLinesTask(Document document){
        this.document = document;
    }

    @Override
    public AnalyzedFile call() throws Exception {
        return new AnalyzedFile(this.document.getPath(), this.document.countLines());
    }

}
