package pairmatching.exception;

public enum ErrorMessage {

    INVALID_MAIN_FUNCTION("존재하지 않는 메인 기능입니다."),
    INVALID_COURSE("존재하지 않는 코스입니다."),
    INVALID_LEVEL("존재하지 않는 레벨입니다."),
    INVALID_MISSION("존재하지 않는 미션입니다."),
    INVALID_FORMAT("입력 형식이 잘못되었습니다."),
    DUPLICATED_CREW("중복된 크루입니다."),
    FILE_NOT_FOUND("파일을 찾을 수 없습니다."),
    IO("IO 예외입니다.");

    private static final String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }
}
