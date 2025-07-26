package kg.attractor.job_search_java25.exceptions;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}