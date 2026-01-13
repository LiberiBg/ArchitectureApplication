package esiea.yangnguyen.architectureapplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ItemCurrentlyInExchangeException extends RuntimeException {
    public ItemCurrentlyInExchangeException(String message) {
        super(message);
    }
}
