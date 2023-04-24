package utils;

public class SetupInfo{
    private String dir;
    private int nFiles;
    private int nIntervals;
    private int lastIntervalLowerBound;

    public SetupInfo(String dir, int nFiles, int nIntervals, int lastIntervalLowerBound) {
        this.dir = dir;
        this.nFiles = nFiles;
        this.nIntervals = nIntervals;
        this.lastIntervalLowerBound = lastIntervalLowerBound;
    }

    public String dir() {
        return dir;
    }

    public int nFiles() {
        return nFiles;
    }

    public int nIntervals() {
        return nIntervals;
    }

    public int lastIntervalLowerBound() {
        return lastIntervalLowerBound;
    }
}
