package tech.falabella.qa.exception;

public class DisabledReportException extends RuntimeException {

    public DisabledReportException(String reportName) {
        super("This report".concat(reportName).concat(" is disabled"));
    }
}
