package tmp.model;

public class Model {
    private final ScanDirectoryService scanDirectoryService;

    public Model(String startDir, int nThread){
        this.scanDirectoryService = new ScanDirectoryService(startDir, nThread);
    }

    public ScanDirectoryService getScanDirectoryService(){
        return this.scanDirectoryService;
    }
}
