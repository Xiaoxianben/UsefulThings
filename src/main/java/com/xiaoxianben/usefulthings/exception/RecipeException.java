package com.xiaoxianben.usefulthings.exception;

public class RecipeException extends Exception {
    public RecipeException(String message) {
        super(message);
    }

    public RecipeException(String me, Object... args) {
        super(String.format(me, args));
    }

    public RecipeException(Exception e, String message) {
        super(message, e);
    }
}
