package kg.attractor.job_search_java25.exceptions;

public class ResumeNotFoundException extends NotFoundException {
  public ResumeNotFoundException(int id) {
    super("Резюме с ID " + id + " не найдено");
  }
}