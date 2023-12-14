package pairmatching.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MatchRepository {

    private static Map<MatchInfo, Pairs> matchTable = new HashMap<>();


    public static boolean containKey(final MatchInfo matchInfo) {
        return matchTable.containsKey(matchInfo);
    }

    public static List<Pairs> findPairsByLevel(final Level level) {
        return Collections.unmodifiableList(matchTable.entrySet().stream()
                .filter(entry -> entry.getKey().isLevel(level))
                .map(Entry::getValue)
                .collect(Collectors.toList()));
    }
}
