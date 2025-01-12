package pairmatching.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import pairmatching.domain.constant.Course;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.PairMatchingException;

public class CrewRepository {

    private static List<Crew> crews = new ArrayList<>();

    private CrewRepository() {
    }

    public static void addCrew(final Crew crew) {
        if (crews.contains(crew)) {
            throw new PairMatchingException(ErrorMessage.DUPLICATED_CREW);
        }

        crews.add(crew);
    }

    public static List<String> findNameByCourse(final Course course) {
        return crews.stream()
                .filter(crew -> crew.isCourse(course))
                .map(Crew::getName)
                .collect(Collectors.toList());
    }

    public static Crew findByName(final String name) {
        return crews.stream()
                .filter(crew -> crew.isName(name))
                .findAny()
                .orElseThrow(() -> new PairMatchingException(ErrorMessage.INVALID_CREW));
    }

    public static List<Crew> findByName(final List<String> names) {
        return names.stream()
                .map(CrewRepository::findByName)
                .collect(Collectors.toList());
    }

    public static void init() {
        crews = new ArrayList<>();
    }
}
