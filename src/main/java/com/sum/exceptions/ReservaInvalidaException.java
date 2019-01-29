package com.sum.exceptions;

public class ReservaInvalidaException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 4410447003417329326L;

    private String message;

    public ReservaInvalidaException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
