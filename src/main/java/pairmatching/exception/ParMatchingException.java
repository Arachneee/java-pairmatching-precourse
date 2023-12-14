package pairmatching.exception;

public class ParMatchingException extends IllegalArgumentException {

    public ParMatchingException(final ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
