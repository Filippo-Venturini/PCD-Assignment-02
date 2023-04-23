package executors.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

public class CountLinesTask implements Callable<AnalyzedFile> {
    private final String path;

    public CountLinesTask(String path){
        this.path = path;
    }

    @Override
    public AnalyzedFile call() throws Exception {
        return new AnalyzedFile(this.path, this.countLines(this.path));
    }

    private int countLines(String path){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path));
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            reader.close();
            return lines;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
