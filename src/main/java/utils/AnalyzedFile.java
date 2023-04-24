package utils;

public class AnalyzedFile implements Comparable<AnalyzedFile>{
    private String path;
    private int lines;

    public AnalyzedFile(String path, int lines) {
        this.path = path;
        this.lines = lines;
    }

    public String path() {
        return path;
    }

    public int lines() {
        return lines;
    }

    @Override
    public int compareTo(AnalyzedFile o) {
        return Integer.compare(o.lines, this.lines);
    }
}
