package tech.falabella.qa.exception;

import java.sql.SQLException;

public class MalformedTupleException extends RuntimeException {

    public MalformedTupleException(SQLException e) {
        super(e.getMessage());
    }
}
