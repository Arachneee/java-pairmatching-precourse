package pairmatching.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.PairMatchingException;

public enum Mission {

    CAR_RACING("자동차경주", Level.LEVEL1),
    LOTTO("로또", Level.LEVEL1),
    BASEBALL("숫자야구게임", Level.LEVEL1),
    SHOPPING_BASKET("장바구니", Level.LEVEL2),
    PAYMENT("결제", Level.LEVEL2),
    SUBWAY_ROUTE_MAP("지하철노선도", Level.LEVEL2),
    PERFORMANCE("성능개선", Level.LEVEL4),
    DISTRIBUTION("배포", Level.LEVEL4);

    private static final Map<String, Mission> missions = Arrays.stream(values())
            .collect(Collectors.toMap(mission -> mission.name,
                    mission -> mission));
    private final String name;
    private final Level level;

    Mission(String name, Level level) {
        this.name = name;
        this.level = level;
    }

    public static Mission from(final String name) {
        return missions.computeIfAbsent(name, key -> {
            throw new PairMatchingException(ErrorMessage.INVALID_MISSION);
        });
    }


}
