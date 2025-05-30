package com.tilldawn.model.language;

public class Result<T> {
    private final T data;
    private final com.tilldawn.model.language.error.Error error;
    private final Message message;

    private Result(T data, com.tilldawn.model.language.error.Error error, Message message) {
        this.data = data;
        this.error = error;
        this.message = message;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, null, null);
    }

    public static <T> Result<T> success(T data, Message message) {
        return new Result<>(data, null, message);
    }

    public static <T> Result<T> success(Message message) {
        return new Result<>(null, null, message);
    }

    public static <T> Result<T> failure(com.tilldawn.model.language.error.Error error) {
        if (error == null) {
            throw new IllegalArgumentException("Error cannot be null for a failed result");
        }
        return new Result<>(null, error, error);
    }

    public T getData() {
        return data;
    }

    public com.tilldawn.model.language.error.Error getError() {
        return error;
    }

    public Message getMessage() {
        if (error != null)
            return error;
        if (message != null) {
            return message;
        }
        return null;
    }

    public boolean isSuccess() {
        return error == null;
    }

    public boolean isError() {
        return error != null;
    }

}
