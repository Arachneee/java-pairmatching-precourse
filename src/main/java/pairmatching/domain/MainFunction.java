package pairmatching.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.PairMatchingException;

public enum MainFunction {

    MATCH("1"),
    READ("2"),
    INIT("3"),
    TERMINATE("Q");

    private static final Map<String, MainFunction> mainFunctions = Arrays.stream(values())
            .collect(Collectors.toMap(function -> function.value,
                    function -> function));
    private final String value;

    MainFunction(final String value) {
        this.value = value;
    }

    public static MainFunction from(final String value) {
        return mainFunctions.computeIfAbsent(value, key -> {
            throw new PairMatchingException(ErrorMessage.INVALID_MAIN_FUNCTION);
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

    public boolean isTerminate() {
        return this.equals(TERMINATE);
    }
}
