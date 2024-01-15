package org.example.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

public class FileException extends DaoException {
    private static final Logger logger = LoggerFactory.getLogger(FileException.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("ExceptionMessages", Locale.getDefault());

    public FileException(Throwable cause) {
        super(cause);
        logger.error(bundle.getString("org.example.exceptions.FileException"), cause);
    }
}
