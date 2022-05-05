package esi.project.ils.ErrorHandling;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ValidationErrorResponse {

    private List<Violation> violations = new ArrayList<>();
    private int statusCode;
    private Date timestamp;

    public ValidationErrorResponse(int statusCode, Date timestamp, List<Violation> violations) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.violations = violations;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<Violation> getViolations() {
        return violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }
}