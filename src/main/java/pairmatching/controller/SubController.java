package pairmatching.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import pairmatching.domain.MatchInfo;
import pairmatching.domain.MatchRepository;
import pairmatching.dto.PairsDto;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.PairMatchingException;
import pairmatching.service.MatchService;
import pairmatching.util.ExceptionRoofer;
import pairmatching.util.Parser;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

public class SubController {

    private final InputView inputView;
    private final OutputView outputView;
    private final MatchService matchService;

    public SubController(final InputView inputView, final OutputView outputView, final MatchService matchService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.matchService = matchService;
    }

    public void matchPair() {
        outputView.printInfo();
        final MatchInfo matchInfo = getMatchInfo();
        runMatching(matchInfo);
    }

    private MatchInfo getMatchInfo() {
        return ExceptionRoofer.supply(() -> {
            final String courseMissionLevel = inputView.readInfo();
            return Parser.convertToMatchInfo(courseMissionLevel);
        });
    }

    private void runMatching(final MatchInfo matchInfo) {
        if (MatchRepository.containKey(matchInfo)) {
            final Rematch rematch = getRematch();
            if (rematch.isNo()) {
                matchPair();
                return;
            }
        }
        try {
            final PairsDto pairsDto = matchService.createPairs(matchInfo);
            outputView.printMatchResult(pairsDto);
        } catch (PairMatchingException exception) {
            outputView.printError(exception.getMessage());
        }
    }

    private Rematch getRematch() {
        return ExceptionRoofer.supply(() -> {
            final String reMatch = inputView.readRematch();
            return Rematch.from(reMatch);
        });
    }

    public void readPair() {
        outputView.printInfo();
        final MatchInfo matchInfo = getMatchInfo();

        try {
            final PairsDto pairsDto = matchService.find(matchInfo);
            outputView.printMatchResult(pairsDto);
        } catch (PairMatchingException exception) {
            outputView.printError(exception.getMessage());
        }
    }

    public void initPair() {
        matchService.init();
        outputView.printInit();
    }

    private enum Rematch {

        YES("네"),
        NO("아니오");

        private static final Map<String, Rematch> rematches = Arrays.stream(values())
                .collect(Collectors.toMap(rematch -> rematch.value,
                        rematch -> rematch));
        private final String value;

        Rematch(final String value) {
            this.value = value;
        }

        public static Rematch from(final String value) {
            return rematches.computeIfAbsent(value, key -> {
                throw new PairMatchingException(ErrorMessage.INVALID_REMATCH);
            });
        }

        public boolean isNo() {
            return this.equals(NO);
        }
    }
}
