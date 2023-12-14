package pairmatching.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchRepository {

    private static Map<MatchInfo, List<List<Crew>>> matchTable = new HashMap<>();


    public static boolean containKey(final MatchInfo matchInfo) {
        return matchTable.containsKey(matchInfo);
    }
}
