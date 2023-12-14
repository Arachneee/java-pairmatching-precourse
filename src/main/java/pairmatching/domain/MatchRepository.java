package pairmatching.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import pairmatching.domain.constant.Level;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.PairMatchingException;

public class MatchRepository {

    private static Map<MatchInfo, Pairs> matchTable = new HashMap<>();

    private MatchRepository() {
    }

    public static void init() {
        matchTable = new HashMap<>();
    }

    public static void save(final MatchInfo matchInfo, final Pairs pairs) {
        matchTable.put(matchInfo, pairs);
    }

    public static List<Pairs> findPairsByLevel(final Level level) {
        return Collections.unmodifiableList(matchTable.entrySet().stream()
                .filter(entry -> entry.getKey().isLevel(level))
                .map(Entry::getValue)
                .collect(Collectors.toList()));
    }

    public static Pairs findPairsByMatchInfo(final MatchInfo matchInfo) {
        return matchTable.computeIfAbsent(matchInfo, key -> {
            throw new PairMatchingException(ErrorMessage.INVALID_INFO);
        });
    }

    public static boolean containKey(final MatchInfo matchInfo) {
        return matchTable.containsKey(matchInfo);
    }
}
