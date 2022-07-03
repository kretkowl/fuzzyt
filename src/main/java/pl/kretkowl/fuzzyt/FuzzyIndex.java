package pl.kretkowl.fuzzyt;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FuzzyIndex {
    
    private final boolean caseSensitive;
    private float defaultTreshold;

    private final Map<String, String> index = new HashMap<>();

    public FuzzyIndex(float defaultTreshold) {
        this(true, defaultTreshold);
    }

    public FuzzyIndex(boolean caseSensitive, float defaultTreshold) {
        this.caseSensitive = caseSensitive;
        this.defaultTreshold = defaultTreshold;
    }

    public void addAll(List<String> values) {
        values.stream()
            .forEach(this::add);
    }

    public void add(String value) {
        index.put(Levenshtein.tokenSortString(caseSensitive ? value : value.toLowerCase()), value);
    }

    public List<String> filter(String exp) {
        return filter(exp, defaultTreshold);
    }

    public List<String> filter(String exp, float treshold) {
        String expression = Levenshtein.tokenSortString(caseSensitive ? exp : exp.toLowerCase());

        class Match {
            float match;
            String value;
            public Match(float match, String value) {
                this.match = match;
                this.value = value;
            }
        }
        return index.entrySet().stream()
            .map(e -> new Match(Levenshtein.ldRatio(expression, e.getKey()), e.getValue()))
            .filter(res -> res.match >= treshold)
            .sorted(Comparator.comparing(m -> m.match, Comparator.reverseOrder()))
            .map(m -> m.value)
            .collect(Collectors.toList());
    }
}
