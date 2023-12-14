package pairmatching.controller;


import pairmatching.domain.MainFunction;
import pairmatching.util.ExceptionRoofer;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

public class PairMatchingController {

    private final InputView inputView;
    private final OutputView outputView;

    public PairMatchingController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        init();
        run();
    }

    private void init() {

    }

    private void run() {
        MainFunction mainFunction = getMainFunction();

    }

    private MainFunction getMainFunction() {
        return ExceptionRoofer.supply(() -> {
            String mainFunctionValue = inputView.readMainFunction();
            return MainFunction.from(mainFunctionValue);
        });
    }
}
