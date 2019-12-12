package mx.kinich49.expensetracker.requests;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TransactionItemRequest {

    private long categoryId;
    private String title;
    private String memo;
    private long amount;
    private LocalDate dateCreated;
}