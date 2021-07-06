package br.com.alura.ecommerce.exceptions;

public class GsonDeserializerException extends RuntimeException {

    public GsonDeserializerException(String message, Throwable cause) {
        super(message, cause);
    }
}
