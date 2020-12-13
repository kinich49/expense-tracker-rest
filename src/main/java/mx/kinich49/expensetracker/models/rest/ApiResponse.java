package mx.kinich49.expensetracker.models.rest;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private final T data;

}
