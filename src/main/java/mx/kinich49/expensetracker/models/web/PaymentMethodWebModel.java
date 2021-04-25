package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.PaymentMethod;

@Data
public class PaymentMethodWebModel {

    private final Long id;
    private final String name;

    public static PaymentMethodWebModel from(PaymentMethod paymentMethod) {
        if (paymentMethod == null)
            return null;

        return new PaymentMethodWebModel(paymentMethod.getId(),
                paymentMethod.getName());
    }
}
