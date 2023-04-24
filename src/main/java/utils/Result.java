package utils;

import java.util.*;
import java.util.stream.Collectors;

public class Result {
    private Set<AnalyzedFile> ranking = new TreeSet<>();
    private final Map<Interval, Integer> distribution = new TreeMap<>();

    public Result(int nIntervals, int lastIntervalLowerBound){
        if(nIntervals == 1){
            distribution.put(new Interval(0, Integer.MAX_VALUE), 0);
        }else {
            final int intervalSize = lastIntervalLowerBound / (nIntervals - 1);
            for (int i = 0; i < nIntervals - 2; i++) {
                distribution.put(new Interval(intervalSize * i, intervalSize * (i + 1)), 0);
            }
            distribution.put(new Interval(intervalSize * (nIntervals - 2), lastIntervalLowerBound), 0);
            distribution.put(new Interval(lastIntervalLowerBound, Integer.MAX_VALUE), 0);
        }
    }

    public synchronized List<AnalyzedFile> getRanking(int nResults) {
        return this.ranking.stream().limit(nResults).collect(Collectors.toList());
    }

    public synchronized List<AnalyzedFile> getAllAnalizedFiles() {
        return this.ranking.stream().collect(Collectors.toList());
    }

    public synchronized Map<Interval, Integer> getDistribution() {
        return this.distribution;
    }

    public synchronized void add(AnalyzedFile elem) {
        this.ranking.add(elem);
        for(Map.Entry<Interval, Integer> entry : this.distribution.entrySet()){
            if(entry.getKey().contains(elem.lines())){
                entry.setValue(entry.getValue() + 1);
            }
        }
    }

    public synchronized void merge(Result otherResult){
        for(AnalyzedFile analyzedFile : otherResult.getAllAnalizedFiles()){
            this.add(analyzedFile);
        }
    }
}
