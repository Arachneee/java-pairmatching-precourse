package pairmatching.controller;


import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import pairmatching.domain.RandomShuffler;
import pairmatching.domain.constant.Course;
import pairmatching.domain.CrewRepository;
import pairmatching.domain.Initializer;
import pairmatching.domain.MatchRepository;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.PairMatchingException;
import pairmatching.service.MatchService;
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
            final SubController subController = new SubController(inputView, outputView, new MatchService(new RandomShuffler()));
            if (mainFunction.isMatch()) {
                subController.matchPair();
                continue;
            }
            if (mainFunction.isRead()) {
                subController.readPair();
                continue;
            }
            if (mainFunction.isInit()) {
                subController.initPair();
                continue;
            }
            // mainFunction is Terminate
            break;
        }
    }

    private MainFunction getMainFunction() {
        return ExceptionRoofer.supply(() -> {
            final String mainFunctionValue = inputView.readMainFunction();
            return MainFunction.from(mainFunctionValue);
        });
    }

    private enum MainFunction {

        MATCH("1"),
        READ("2"),
        INIT("3"),
        TERMINATE("Q");

        private static final Map<String, MainFunction> mainFunctions = Arrays.stream(values())
                .collect(Collectors.toMap(function -> function.value,
                        function -> function));
        private final String value;

        MainFunction(final String value) {
            this.value = value;
        }

        public static MainFunction from(final String value) {
            return mainFunctions.computeIfAbsent(value, key -> {
                throw new PairMatchingException(ErrorMessage.INVALID_MAIN_FUNCTION);
            });
        }


        public boolean isMatch() {
            return this.equals(MATCH);
        }

        public boolean isRead() {
            return this.equals(READ);
        }

        public boolean isInit() {
            return this.equals(INIT);
        }
    }
}
