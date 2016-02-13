package exceptions;

/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

@SuppressWarnings("serial")
public class InvalidInputException extends RuntimeException {
    public InvalidInputException (String message) {
        super(message);
    }
}
