package pairmatching.controller;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import pairmatching.domain.Crew;
import pairmatching.domain.CrewRepository;
import pairmatching.domain.MainFunction;
import pairmatching.domain.MatchInfo;
import pairmatching.domain.MatchRepository;
import pairmatching.domain.Pairs;
import pairmatching.domain.Rematch;
import pairmatching.dto.PairsDto;
import pairmatching.util.ExceptionRoofer;
import pairmatching.util.Parser;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

public class SubController {

    private final InputView inputView;
    private final OutputView outputView;

    public SubController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
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
        checkRematch(matchInfo);
    }

    private MatchInfo getMatchInfo() {
        return ExceptionRoofer.supply(() -> {
            final String courseMissionLevel = inputView.readInfo();
            return Parser.convertToMatchInfo(courseMissionLevel);
        });
    }

    private void checkRematch(final MatchInfo matchInfo) {
        if (MatchRepository.containKey(matchInfo)) {
            final Rematch rematch = getRematch();

            if (rematch.isNo()) {
                matchPair();
            }
        }
        matchStart(matchInfo);
    }

    private Rematch getRematch() {
        return ExceptionRoofer.supply(() -> {
            final String reMatch = inputView.readRematch();
            return Rematch.from(reMatch);
        });
    }

    private void matchStart(final MatchInfo matchInfo) {
        final Pairs pairs = getPairs(matchInfo);
        MatchRepository.save(matchInfo, pairs);

        final PairsDto pairsDto = PairsDto.from(pairs);
        outputView.printMatchResult(pairsDto);
    }

    private Pairs getPairs(final MatchInfo matchInfo) {
        final List<String> crewNames = CrewRepository.findNameByCourse(matchInfo.getCourse());
        while (true) {
            final Pairs pairs = createPairs(crewNames);
            final List<Pairs> levelPairs = MatchRepository.findPairsByLevel(matchInfo.getLevel());

            if (hasDuplicatePair(levelPairs, pairs)) {
                continue;
            }
            return pairs;
        }
    }

    private Pairs createPairs(final List<String> crewNames) {
        final List<String> shuffledCrewNames = Randoms.shuffle(crewNames);
        final List<Crew> crews = CrewRepository.findByName(shuffledCrewNames);

        return Pairs.create(crews);
    }

    private boolean hasDuplicatePair(final List<Pairs> levelPairs, final Pairs pairs) {
        return levelPairs.stream()
                .anyMatch(pairs::hasDuplicatePair);
    }

    private void readPair() {
        outputView.printInfo();
        final MatchInfo matchInfo = getMatchInfo();

        final Pairs pairs = MatchRepository.findPairsByMatchInfo(matchInfo);
        final PairsDto pairsDto = PairsDto.from(pairs);
        outputView.printMatchResult(pairsDto);
    }

    private void initPair() {
        MatchRepository.init();
        outputView.printInit();
    }
}
