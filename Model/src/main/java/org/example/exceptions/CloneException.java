package org.example.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

public class CloneException extends CloneNotSupportedException {
    private static final Logger logger = LoggerFactory.getLogger(CloneException.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("ExceptionMessages", Locale.getDefault());

    public CloneException(final String message) {
        super(message);
        logger.error(bundle.getString("org.example.exceptions.CloneException"), message);
    }
}
