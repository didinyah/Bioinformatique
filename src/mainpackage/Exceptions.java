package mainpackage;

/**
 * Created by SandrosLaptop on 03/03/2017.
 */
public class Exceptions{
    public static class ExceptionCds extends Exception{

    }

    public static class ExceptionPatternLine extends Exception{
        private String message_error;
        public ExceptionPatternLine(String s) {
            message_error = s;
        }

        public ExceptionPatternLine() {

        }
    }

    public static class ExceptionCodonNotFound extends Exception{
        private String message_error;
        public ExceptionCodonNotFound(String s) {
            message_error = s;
        }
    }
}
