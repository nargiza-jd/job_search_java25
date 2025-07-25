package kg.attractor.job_search_java25.exceptions;

public class RespondedApplicantNotFoundException extends NotFoundException {
    public RespondedApplicantNotFoundException() {
        super("Responded applicant");
    }

    public RespondedApplicantNotFoundException(String message) {
        super(message);
    }
}