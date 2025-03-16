package mx.kinich49.expensetracker.models.web;

import lombok.Getter;

public final class ApiResponse<T> {

    @Getter
    private final T data;
    @Getter
    private final String error;

    public ApiResponse(T data) {
        this.data = data;
        this.error = null;
    }

    public ApiResponse(String error) {
        this.error = error;
        this.data = null;
    }
}
