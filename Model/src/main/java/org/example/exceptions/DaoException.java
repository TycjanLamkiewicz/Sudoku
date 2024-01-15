package org.example.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class DaoException extends IOException {
    private static final Logger logger = LoggerFactory.getLogger(DaoException.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("ExceptionMessages", Locale.getDefault());

    public DaoException(Throwable cause) {
        super(cause);
        logger.error(bundle.getString("org.example.exceptions.DaoException"));
    }
}
