package mx.kinich49.expensetracker.models.web.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionRequest {

    private long categoryId;
    private String title;
    private String memo;
    private int amount;
    private LocalDate dateCreated;
}