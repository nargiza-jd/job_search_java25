package kg.attractor.job_search_java25.exceptions;

public class ResumeNotFoundException extends NotFoundException {
  public ResumeNotFoundException() {
    super("Resume");
  }

  public ResumeNotFoundException(String message) {
    super(message);
  }
}