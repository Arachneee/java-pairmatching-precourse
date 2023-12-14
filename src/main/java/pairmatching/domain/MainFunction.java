package pairmatching.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.ParMatchingException;

public enum MainFunction {

    MATCH("1"),
    READ("2"),
    INIT("3"),
    TERMINATE("Q");

    private static final Map<String, MainFunction> mainFunctions = Arrays.stream(values())
            .collect(Collectors.toMap(function -> function.value,
                    function -> function));
    private final String value;

    MainFunction(String value) {
        this.value = value;
    }

    public static MainFunction from(final String value) {
        return mainFunctions.computeIfAbsent(value, key -> {
            throw new ParMatchingException(ErrorMessage.INVALID_MAIN_FUNCTION);
        });
    }


    public boolean isMatch() {
        return this.equals(MATCH);
    }

    public boolean isRead() {
        return this.equals(READ);
    }

    public boolean isInit() {
        return this.equals(INIT);
    }
}
