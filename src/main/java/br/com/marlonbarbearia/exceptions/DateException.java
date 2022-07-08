package br.com.marlonbarbearia.exceptions;

public class DateException extends RuntimeException {

    public DateException() { super(); }

    public DateException(String msg) {
        super(msg);
    }
}
