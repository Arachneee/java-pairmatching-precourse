package pairmatching.view;

import pairmatching.dto.PairsDto;

public class OutputView {

    public void printError(final String message) {
        System.out.println(message);
    }

    public void printInfo() {
        System.out.println(System.lineSeparator() + Response.INFO.value);
    }

    public void printMatchResult(final PairsDto pairsDto) {
        System.out.println(System.lineSeparator() + Response.RESULT.value);
        pairsDto.getPairs().forEach(pair -> System.out.println(String.join(" : ", pair)));
        System.out.println();
    }

    public void printInit() {
        System.out.println(System.lineSeparator() + Response.INIT.value);
    }

    private enum Response {
        INFO("#############################################\n"
                + "과정: 백엔드 | 프론트엔드\n"
                + "미션:\n"
                + "  - 레벨1: 자동차경주 | 로또 | 숫자야구게임\n"
                + "  - 레벨2: 장바구니 | 결제 | 지하철노선도\n"
                + "  - 레벨3: \n"
                + "  - 레벨4: 성능개선 | 배포\n"
                + "  - 레벨5: \n"
                + "############################################"),
        RESULT("페어 매칭 결과입니다."),
        INIT("초기화 되었습니다.");

        private final String value;

        Response(final String value) {
            this.value = value;
        }
    }
}
