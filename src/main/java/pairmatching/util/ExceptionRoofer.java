package pairmatching.util;


import java.util.function.Supplier;
import pairmatching.view.OutputView;


public final class ExceptionRoofer {

    private static final OutputView outputView = new OutputView();

    private ExceptionRoofer() {
    }

    public static <T> T supply(final Supplier<T> method) {
        while (true) {
            try {
                return method.get();
            } catch (IllegalArgumentException illegalArgumentException) {
                outputView.printError(illegalArgumentException.getMessage());
            }
        }
    }
}
