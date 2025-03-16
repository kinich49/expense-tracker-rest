package mx.kinich49.expensetracker.models.web;

import lombok.Data;

@Data
public class ErrorWrapper {

    private final int errorCode;
    private final String errorMessage;

}
