package pairmatching.service;

import java.util.List;
import pairmatching.domain.Crew;
import pairmatching.domain.CrewRepository;
import pairmatching.domain.MatchInfo;
import pairmatching.domain.MatchRepository;
import pairmatching.domain.Pairs;
import pairmatching.domain.Shuffler;
import pairmatching.dto.PairsDto;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.PairMatchingException;

public class MatchService {

    private final Shuffler shuffler;

    public MatchService(Shuffler shuffler) {
        this.shuffler = shuffler;
    }

    public PairsDto createPairs(final MatchInfo matchInfo) {
        final List<String> crewNames = CrewRepository.findNameByCourse(matchInfo.getCourse());
        final List<Pairs> levelPairs = MatchRepository.findPairsByLevel(matchInfo.getLevel());

        final Pairs pairs = getPairs(matchInfo, crewNames, levelPairs);
        return PairsDto.from(pairs);
    }

    private Pairs getPairs(final MatchInfo matchInfo, final List<String> crewNames, final List<Pairs> levelPairs) {
        int shuffleCount = 3;

        while (shuffleCount-- > 0) {
            final Pairs pairs = getRandomPairs(crewNames);
            if (hasDuplicatePair(levelPairs, pairs)) {
                continue;
            }
            MatchRepository.save(matchInfo, pairs);
            return pairs;
        }
        throw new PairMatchingException(ErrorMessage.CANT_FIND_PAIR);
    }

    private Pairs getRandomPairs(final List<String> crewNames) {
        final List<String> shuffledCrewNames = shuffler.shuffle(crewNames);
        final List<Crew> crews = CrewRepository.findByName(shuffledCrewNames);

        return Pairs.create(crews);
    }

    private boolean hasDuplicatePair(final List<Pairs> levelPairs, final Pairs pairs) {
        return levelPairs.stream()
                .anyMatch(pairs::hasDuplicatePair);
    }

    public PairsDto find(final MatchInfo matchInfo) {
        final Pairs pairs = MatchRepository.findPairsByMatchInfo(matchInfo);
        return PairsDto.from(pairs);
    }

    public void init() {
        MatchRepository.init();
    }
}
