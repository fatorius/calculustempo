package com.hugosouza.calculustempo.interfaces;

public record SuccessResponse<T>(T data, boolean success) implements ResponseData<T> {
    public SuccessResponse(T data) {
        this(data, true);
    }
}

