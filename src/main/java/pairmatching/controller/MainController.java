package pairmatching.controller;


import pairmatching.domain.constant.Course;
import pairmatching.domain.CrewRepository;
import pairmatching.domain.Initializer;
import pairmatching.domain.constant.MainFunction;
import pairmatching.domain.MatchRepository;
import pairmatching.util.ExceptionRoofer;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

public class MainController {

    private final InputView inputView;
    private final OutputView outputView;

    public MainController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        init();
        run();
    }

    private void init() {
        CrewRepository.init();
        MatchRepository.init();
        Initializer.initCrew(Course.BACKEND, "backend-crew.md");
        Initializer.initCrew(Course.FRONTEND, "frontend-crew.md");
    }

    private void run() {
        while (true) {
            final MainFunction mainFunction = getMainFunction();
            if (mainFunction.isTerminate()) {
                break;
            }

            final SubController subController = new SubController(inputView, outputView);
            subController.run(mainFunction);
        }
    }

    private MainFunction getMainFunction() {
        return ExceptionRoofer.supply(() -> {
            final String mainFunctionValue = inputView.readMainFunction();
            return MainFunction.from(mainFunctionValue);
        });
    }
}
