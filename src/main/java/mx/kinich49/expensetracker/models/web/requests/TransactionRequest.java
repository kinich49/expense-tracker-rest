package mx.kinich49.expensetracker.models.web.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionRequest {

    private String title;
    private String memo;
    private int amount;
    private PaymentMethodRequest paymentMethod;
    private LocalDateTime dateCreated;
    private StoreRequest store;
    private CategoryRequest category;
}