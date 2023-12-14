package pairmatching.domain;

import java.util.ArrayList;
import java.util.List;
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
}
