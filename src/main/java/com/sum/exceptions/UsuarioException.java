package com.sum.exceptions;

public class UsuarioException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 8362985090039777818L;

    private String message;

    public UsuarioException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
