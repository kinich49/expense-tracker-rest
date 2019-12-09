package mx.kinich49.expensetracker.base;

import lombok.Data;

@SuppressWarnings("unused")
@Data
public class RestError {

    private final String errorMessage;
    private final int errorCode;
}
