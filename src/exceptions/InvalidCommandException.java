/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package exceptions;

/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

@SuppressWarnings("serial")
public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException (String message) {
        super(message);
    }
}
