package pairmatching.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Pairs {

    private final List<Pair> pairs;

    public Pairs(List<Pair> pairs) {
        this.pairs = pairs;
    }

    public boolean hasDuplicatePair(final Pairs other) {
        return pairs.stream()
                .anyMatch(other::hasDuplicatePair);
    }

    public boolean hasDuplicatePair(final Pair other) {
        return pairs.stream()
                .anyMatch(other::hasPair);
    }

    public List<List<String>> getPairsName() {
        return pairs.stream()
                .map(Pair::getNames)
                .collect(Collectors.toList());
    }
}
