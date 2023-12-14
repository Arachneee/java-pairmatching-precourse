package pairmatching.domain;

public class MatchInfo {
    private final Course course;
    private final Level level;
    private final Mission mission;

    public MatchInfo(Course course, Level level, Mission mission) {
        this.course = course;
        this.level = level;
        this.mission = mission;
    }
}
