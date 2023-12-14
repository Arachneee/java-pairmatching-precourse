package pairmatching.controller;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import pairmatching.domain.Course;
import pairmatching.domain.Crew;
import pairmatching.domain.CrewRepository;
import pairmatching.domain.MainFunction;
import pairmatching.domain.MatchInfo;
import pairmatching.domain.MatchRepository;
import pairmatching.domain.Rematch;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.PairMatchingException;
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
        initCrew(Course.BACKEND, "backend-crew.md");
        initCrew(Course.FRONTEND, "frontend-crew.md");
    }

    private static void initCrew(final Course course, final String fileName) {
        ClassLoader classLoader = PairMatchingController.class.getClassLoader();
        FileInputStream file = null;
        try {
            file = new FileInputStream(classLoader.getResource(fileName).getFile());
        } catch (FileNotFoundException e) {
            throw new PairMatchingException(ErrorMessage.FILE_NOT_FOUND);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file));

        while (true) {
            try {
                String crewName = bufferedReader.readLine();
                if (crewName == null) {
                    break;
                }
                CrewRepository.addCrew(new Crew(course, crewName));
            } catch (IOException e) {
                throw new PairMatchingException(ErrorMessage.IO);
            }
        }
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

    private MainFunction getMainFunction() {
        return ExceptionRoofer.supply(() -> {
            String mainFunctionValue = inputView.readMainFunction();
            return MainFunction.from(mainFunctionValue);
        });
    }

    private void matchPair() {
        outputView.printInfo();
        MatchInfo matchInfo = getMatchInfo();
        if (MatchRepository.containKey(matchInfo)) {
            Rematch rematch = getRematch();

        }

    }

    private Rematch getRematch() {
        return ExceptionRoofer.supply(() -> {
            String reMatch = inputView.readRematch();
            return Rematch.from(reMatch);
        });
    }

    private MatchInfo getMatchInfo() {
        return ExceptionRoofer.supply(() -> {
            String courseMissionLevel = inputView.readInfo();
            return Parser.convertToMatchInfo(courseMissionLevel);
        });
    }

    private void readPair() {

    }

    private void initPair() {

    }
}
