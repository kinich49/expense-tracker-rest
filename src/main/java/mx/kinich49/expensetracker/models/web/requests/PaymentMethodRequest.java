package mx.kinich49.expensetracker.models.web.requests;

import lombok.Data;

@Data
public class PaymentMethodRequest {

    private final Long id;
    private final String name;
}
