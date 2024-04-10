package br.com.marlonbarbearia.exceptions;

public class NotAllowedException extends RuntimeException {

    public NotAllowedException() { super(); }

    public NotAllowedException(String msg) {
        super(msg);
    }
}
