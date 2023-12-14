package pairmatching.dto;

import java.util.List;
import pairmatching.domain.Pairs;

public class PairsDto {

    private final List<List<String>> pairs;

    private PairsDto(List<List<String>> pairs) {
        this.pairs = pairs;
    }

    public static PairsDto from(final Pairs pairs) {
        return new PairsDto(pairs.getPairsName());
    }

    public List<List<String>> getPairs() {
        return pairs;
    }
}
