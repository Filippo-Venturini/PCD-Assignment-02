package utils;

import tmp.model.Interval;
import utils.AnalyzedFile;

import java.util.List;
import java.util.Map;

public record Report(List<AnalyzedFile> ranking, Map<Interval, Integer> distribution) {
}
