/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package exceptions;

/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

public class InvalidInputException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidInputException (String message) {
        super(message);
    }
}
