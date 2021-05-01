package mx.kinich49.expensetracker.models.web.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionRequest {

    private final String title;
    private final String memo;
    private final int amount;
    private final PaymentMethodRequest paymentMethod;
    private final LocalDateTime dateCreated;
    private final StoreRequest store;
    private final CategoryRequest category;
}