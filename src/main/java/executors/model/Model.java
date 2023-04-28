package executors.model;

import utils.Result;

public class Model {
    private final DirectoryScanner directoryScanner = new DirectoryScanner();

    public DirectoryScanner getDirectoryScanner() {
        return this.directoryScanner;
    }
}
