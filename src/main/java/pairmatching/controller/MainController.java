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
import pairmatching.domain.MatchRepository;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.PairMatchingException;
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
        initCrew(Course.BACKEND, "backend-crew.md");
        initCrew(Course.FRONTEND, "frontend-crew.md");
    }

    private static void initCrew(final Course course, final String fileName) {
        final FileInputStream file = readFile(fileName);
        saveCrews(course, file);
    }

    private static FileInputStream readFile(final String fileName) {
        final ClassLoader classLoader = MainController.class.getClassLoader();
        FileInputStream file = null;
        try {
            file = new FileInputStream(classLoader.getResource(fileName).getFile());
        } catch (FileNotFoundException e) {
            throw new PairMatchingException(ErrorMessage.FILE_NOT_FOUND);
        }
        return file;
    }

    private static void saveCrews(Course course, FileInputStream file) {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file));

        while (true) {
            final String crewName;
            try {
                crewName = bufferedReader.readLine();
            } catch (IOException e) {
                throw new PairMatchingException(ErrorMessage.IO);
            }
            if (crewName == null) {
                break;
            }
            CrewRepository.addCrew(new Crew(course, crewName));
        }
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
