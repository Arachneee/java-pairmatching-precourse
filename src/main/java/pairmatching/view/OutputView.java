package pairmatching.view;

public class OutputView {

    public void printError(final String message) {
        System.out.println(message);
    }

    private enum Response {
        ;

        private final String value;

        Response(final String value) {
            this.value = value;
        }

        public String getWithEnter() {
            return value + System.lineSeparator();
        }
    }
}
