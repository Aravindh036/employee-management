package employee.exception;
public class DataAccessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataAccessException(String messageString) {
        System.out.println(messageString);
    }
}