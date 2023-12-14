package pairmatching.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {


    public String readMainFunction() {
        System.out.println(Request.MAIN_FUNCTION.value);
        return Console.readLine();
    }

    private enum Request {
        MAIN_FUNCTION("기능을 선택하세요.\n"
                + "1. 페어 매칭\n"
                + "2. 페어 조회\n"
                + "3. 페어 초기화\n"
                + "Q. 종료");
        private final String value;

        Request(final String value) {
            this.value = value;
        }
    }
}
