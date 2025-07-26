package kg.attractor.job_search_java25.exceptions;

public class VacancyNotFoundException extends NotFoundException {
    public VacancyNotFoundException() {
        super("Vacancy");
    }

    public VacancyNotFoundException(String message) {
        super(message);
    }
}
