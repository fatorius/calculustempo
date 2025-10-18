package com.hugosouza.calculustempo.interfaces;

public record ErrorResponse<T>(boolean success, String message) implements ResponseData<T> {
    public ErrorResponse(String message) {
        this(true, message);
    }
}
