package pairmatching.controller;


import pairmatching.domain.MainFunction;
import pairmatching.domain.MatchInfo;
import pairmatching.util.ExceptionRoofer;
import pairmatching.util.Parser;
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
        while (true) {
            MainFunction mainFunction = getMainFunction();
            if (mainFunction.isMatch()) {
                matchPair();
                continue;
            }

            if (mainFunction.isRead()) {
                readPair();
                continue;
            }

            if (mainFunction.isInit()) {
                initPair();
                continue;
            }

            // mainFunction is TERMINATE
            break;
        }

    }

    private void matchPair() {
        outputView.printInfo();
        MatchInfo matchInfo = getMatchInfo();

    }

    private MatchInfo getMatchInfo() {
        return ExceptionRoofer.supply(() -> {
            String courseMissionLevel = inputView.readInfo();
            return Parser.convertToMatchInfo(courseMissionLevel);
        });
    }

    private MainFunction getMainFunction() {
        return ExceptionRoofer.supply(() -> {
            String mainFunctionValue = inputView.readMainFunction();
            return MainFunction.from(mainFunctionValue);
        });
    }
}
