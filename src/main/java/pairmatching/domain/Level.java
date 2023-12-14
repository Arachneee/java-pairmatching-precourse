package pairmatching.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.PairMatchingException;

public enum Level {

    LEVEL1("레벨1"),
    LEVEL2("레벨2"),
    LEVEL3("레벨3"),
    LEVEL4("레벨4"),
    LEVEL5("레벨5");

    private static final Map<String, Level> levels = Arrays.stream(values())
            .collect(Collectors.toMap(level -> level.name,
                    level -> level));
    private final String name;

    Level(final String name) {
        this.name = name;
    }

    public static Level from(final String name) {
        return levels.computeIfAbsent(name, key -> {
            throw new PairMatchingException(ErrorMessage.INVALID_LEVEL);
        });
    }

}
