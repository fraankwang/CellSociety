package exceptions;

@SuppressWarnings("serial")
public class InvalidInputException extends Exception {
	public InvalidInputException (String message) {
		super(message);
	}
}
