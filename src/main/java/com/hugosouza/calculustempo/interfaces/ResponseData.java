package com.hugosouza.calculustempo.interfaces;

public sealed interface ResponseData<T> permits SuccessResponse, ErrorResponse {
}
