package mx.kinich49.expensetracker.models;

import lombok.Data;

@Data
public class JsonApi<T> {

    private final T data;

}
