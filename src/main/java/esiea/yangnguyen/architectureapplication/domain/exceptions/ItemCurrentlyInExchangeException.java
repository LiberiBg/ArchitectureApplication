package esiea.yangnguyen.architectureapplication.domain.exceptions;

public class ItemCurrentlyInExchangeException extends RuntimeException {
    public ItemCurrentlyInExchangeException(String message) {
        super(message);
    }
}