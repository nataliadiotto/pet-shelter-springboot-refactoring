package com.diotto.petshelter.errors;

public class BadRequest extends RuntimeException{

    public BadRequest(String message) {
        super(message);
    }
}
