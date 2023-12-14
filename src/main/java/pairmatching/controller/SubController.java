package pairmatching.controller;

import pairmatching.domain.MatchInfo;
import pairmatching.domain.MatchRepository;
import pairmatching.domain.constant.MainFunction;
import pairmatching.domain.constant.Rematch;
import pairmatching.dto.PairsDto;
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

    public SubController(InputView inputView, OutputView outputView, MatchService matchService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.matchService = matchService;
    }

    public void run(final MainFunction mainFunction) {
        if (mainFunction.isMatch()) {
            matchPair();
            return;
        }
        if (mainFunction.isRead()) {
            readPair();
            return;
        }
        if (mainFunction.isInit()) {
            initPair();
        }
    }

    private void matchPair() {
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

    private void readPair() {
        outputView.printInfo();
        final MatchInfo matchInfo = getMatchInfo();

        try {
            final PairsDto pairsDto = matchService.find(matchInfo);
            outputView.printMatchResult(pairsDto);
        } catch (PairMatchingException exception) {
            outputView.printError(exception.getMessage());
        }
    }

    private void initPair() {
        matchService.init();
        outputView.printInit();
    }
}
