package exceptions;

/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */


public class InvalidInputException extends RuntimeException {
    public InvalidInputException (String message) {
        super(message);
    }
}
