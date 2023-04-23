package executors.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchFilesTask implements Callable<List<String>> {

    private final String startDir;

    public SearchFilesTask(String startDir){
        this.startDir = startDir;
    }

    @Override
    public List<String> call() throws Exception {
        return this.searchFiles();
    }

    private List<String> searchFiles(){
        try (Stream<Path> walkStream = Files.walk(Paths.get(this.startDir))) {
            return walkStream.filter(p -> p.toFile().isFile() && p.toString().endsWith(".java"))
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
