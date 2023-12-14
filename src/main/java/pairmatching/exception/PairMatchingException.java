package pairmatching.exception;

public class PairMatchingException extends IllegalArgumentException {

    public PairMatchingException(final ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
