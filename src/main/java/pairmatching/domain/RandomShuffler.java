package pairmatching.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;

public class RandomShuffler implements Shuffler {


    @Override
    public List<String> shuffle(List<String> inputs) {
        return Randoms.shuffle(inputs);
    }
}
