package pairmatching.exception;

public enum ErrorMessage {

    INVALID_MAIN_FUNCTION("없는 메인 기능입니다.");

    private static final String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }
}
