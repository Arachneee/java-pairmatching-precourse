package pairmatching.domain;


import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.PairMatchingException;

public enum Course {

    BACKEND("백엔드"),
    FRONTEND("프론트엔드");

    private static final Map<String, Course> coures = Arrays.stream(values())
            .collect(Collectors.toMap(course -> course.name,
                    course -> course));
    private String name;


    Course(String name) {
        this.name = name;
    }

    public static Course from(final String name) {
        return coures.computeIfAbsent(name, key -> {
            throw new PairMatchingException(ErrorMessage.INVALID_COURSE);
        });
    }
}
