package pairmatching.domain;

import java.util.ArrayList;
import java.util.List;

public class Pair {

    private final List<Crew> crews;

    public Pair(Crew crew1, Crew crew2) {
        List<Crew> crews = new ArrayList<>();
        crews.add(crew1);
        crews.add(crew2);
        this.crews = crews;
    }

    public Pair(Crew crew1, Crew crew2, Crew crew3) {
        List<Crew> crews = new ArrayList<>();
        crews.add(crew1);
        crews.add(crew2);
        crews.add(crew3);
        this.crews = crews;
    }

    public boolean hasPair(final Pair other) {
        return crews.stream()
                .filter(other::contain)
                .count() >= 2L;
    }

    private boolean contain(final Crew crew) {
        return crews.contains(crew);
    }
}
