package pairmatching.domain;

import java.util.Objects;
import pairmatching.domain.constant.Course;
import pairmatching.domain.constant.Level;
import pairmatching.domain.constant.Mission;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.PairMatchingException;

public class MatchInfo {

    private final Course course;
    private final Mission mission;

    private MatchInfo(final Course course, final Mission mission) {
        this.course = course;
        this.mission = mission;
    }

    public static MatchInfo of(final Course course, final Level level, final Mission mission) {
        if (mission.isNotLevel(level)) {
            throw new PairMatchingException(ErrorMessage.INVALID_MISSION);
        }

        return new MatchInfo(course, mission);
    }

    public boolean isLevel(final Level level) {
        return !this.mission.isNotLevel(level);
    }

    public Course getCourse() {
        return course;
    }

    public Level getLevel() {
        return mission.getLevel();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MatchInfo matchInfo = (MatchInfo) o;
        return course == matchInfo.course && mission == matchInfo.mission;
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, mission);
    }
}
