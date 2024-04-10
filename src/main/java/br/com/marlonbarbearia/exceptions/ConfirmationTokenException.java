package br.com.marlonbarbearia.exceptions;

public class ConfirmationTokenException extends RuntimeException {

    public ConfirmationTokenException() { super(); }

    public ConfirmationTokenException(String msg) {
        super(msg);
    }
}
