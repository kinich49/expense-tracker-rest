package mx.kinich49.expensetracker.requests;

import java.time.LocalDate;
import java.util.Locale.Category;

import lombok.Data;

@Data
public class TransactionItemsRequest {

    private final Category category;
    private final LocalDate localDate;
}