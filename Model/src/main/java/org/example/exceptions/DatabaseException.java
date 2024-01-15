package org.example.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class DatabaseException extends SQLException {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseException.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("ExceptionMessages", Locale.getDefault());

    public DatabaseException(String message) {
        super(message);
        logger.error(bundle.getString("org.example.exceptions.DatabaseException"), message);
    }
}
