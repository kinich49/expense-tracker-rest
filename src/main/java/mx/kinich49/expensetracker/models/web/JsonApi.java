package mx.kinich49.expensetracker.models.web;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JsonApi<Data> {

    private final Data data;

}
