package pairmatching.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pairmatching.domain.Course;
import pairmatching.domain.Level;
import pairmatching.domain.MatchInfo;
import pairmatching.domain.Mission;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.ParMatchingException;

public final class Parser {

    private static final Pattern MATCH_INFO_PATTERN = Pattern.compile("^[가-힣0-9]+, [가-힣0-9]+, [가-힣0-9]+$");

    private Parser() {
    }

    public static MatchInfo convertToMatchInfo(final String input) {
        validateMatchInfo(input);

        final String[] matchInfo = input.split(", ");
        final Course course = Course.from(matchInfo[0]);
        final Level level = Level.from(matchInfo[1]);
        final Mission mission = Mission.from(matchInfo[2]);

        return new MatchInfo(course, level, mission);
    }

    private static void validateMatchInfo(final String input) {
        Matcher matcher = MATCH_INFO_PATTERN.matcher(input);

        if (matcher.matches()) {
            return;
        }

        throw new ParMatchingException(ErrorMessage.INVALID_FORMAT);
    }
}
