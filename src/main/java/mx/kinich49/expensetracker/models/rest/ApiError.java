package mx.kinich49.expensetracker.models.rest;

import lombok.Data;

@SuppressWarnings("unused")
@Data
public class ApiError {

    private final String errorMessage;
}
