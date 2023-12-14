package pairmatching.domain;

import java.util.List;

@FunctionalInterface
public interface Shuffler {

    List<String> shuffle(List<String> inputs);

}
