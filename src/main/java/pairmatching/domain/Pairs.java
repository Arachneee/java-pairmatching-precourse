package pairmatching.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.PairMatchingException;

public class Pairs {

    private static final int MIN_CREW_COUNT = 2;
    private final List<Pair> pairs;

    private Pairs(final List<Pair> pairs) {
        this.pairs = pairs;
    }

    public static Pairs create(final List<Crew> crews) {
        if (crews.size() < MIN_CREW_COUNT) {
            throw new PairMatchingException(ErrorMessage.INVALID_PAIR_COUNT);
        }

        int count = crews.size();

        if (count % 2 == 0) {
            return createEvenPair(crews);
        }

        return createOddPair(crews);
    }

    private static Pairs createEvenPair(final List<Crew> crews) {
        List<Pair> pairs = new ArrayList<>();
        final int count = crews.size();

        for (int i = 0; i < count; i += 2) {
            final Pair pair = new Pair(crews.get(i), crews.get(i + 1));
            pairs.add(pair);
        }

        return new Pairs(pairs);
    }

    private static Pairs createOddPair(final List<Crew> crews) {
        List<Pair> pairs = new ArrayList<>();
        final int count = crews.size();

        for (int i = 0; i < count - 3; i += 2) {
            final Pair pair = new Pair(crews.get(i), crews.get(i + 1));
            pairs.add(pair);
        }

        Pair pair = new Pair(crews.get(count - 3), crews.get(count - 2), crews.get(count - 1));
        pairs.add(pair);

        return new Pairs(pairs);
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
