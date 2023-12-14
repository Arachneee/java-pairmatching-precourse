package pairmatching.domain;

import java.util.Objects;

public class MatchInfo {
    private final Course course;
    private final Level level;
    private final Mission mission;

    public MatchInfo(Course course, Level level, Mission mission) {
        this.course = course;
        this.level = level;
        this.mission = mission;
    }

    public boolean isLevel(final Level level) {
        return this.level.equals(level);
    }

    public Course getCourse() {
        return course;
    }

    public Level getLevel() {
        return level;
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
        return course == matchInfo.course && level == matchInfo.level && mission == matchInfo.mission;
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, level, mission);
    }
}
