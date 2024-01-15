package org.example.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

public class ValueException extends NullPointerException {
    private static final Logger logger = LoggerFactory.getLogger(ValueException.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("ExceptionMessages", Locale.getDefault());

    public ValueException(String message) {
        super(message);
        logger.error(bundle.getString("org.example.exceptions.ValueException"), message);
    }
}
