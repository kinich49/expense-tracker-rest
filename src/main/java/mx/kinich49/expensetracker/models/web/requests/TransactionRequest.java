package mx.kinich49.expensetracker.models.web.requests;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TransactionRequest {

    private long categoryId;
    private String title;
    private String memo;
    private int amount;
    private LocalDate dateCreated;
}