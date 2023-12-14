package pairmatching.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.PairMatchingException;

public enum Rematch {

    YES("네"),
    NO("아니오");

    private static final Map<String, Rematch> rematches = Arrays.stream(values())
            .collect(Collectors.toMap(rematch -> rematch.value,
                    rematch -> rematch));
    private final String value;

    Rematch(String value) {
        this.value = value;
    }

    public static Rematch from(final String value) {
        return rematches.computeIfAbsent(value, key -> {
            throw new PairMatchingException(ErrorMessage.INVALID_REMATCH);
        });
    }

    public boolean isNo() {
        return this.equals(NO);
    }
}
