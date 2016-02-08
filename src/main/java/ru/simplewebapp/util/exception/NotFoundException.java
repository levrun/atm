package ru.simplewebapp.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = NotFoundException.NO_DATA_FOUND)  // 404
public class NotFoundException extends AtmException {

    public static final String NO_DATA_FOUND = "No data found";

    public NotFoundException() {
        super(NO_DATA_FOUND);
    }
}
