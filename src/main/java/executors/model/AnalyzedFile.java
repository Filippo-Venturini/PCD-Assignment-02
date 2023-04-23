package executors.model;

public record AnalyzedFile(String path, int lines) implements Comparable<AnalyzedFile>{
    @Override
    public int compareTo(AnalyzedFile o) {
        return Integer.compare(o.lines, this.lines);
    }
}
