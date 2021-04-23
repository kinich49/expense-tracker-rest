package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.PaymentMethod;

@Data
public class PaymentMethodWebModel {

    private Long id;
    private String name;

    public static PaymentMethodWebModel from(PaymentMethod paymentMethod) {
        if (paymentMethod == null)
            return null;

        PaymentMethodWebModel webModel = new PaymentMethodWebModel();
        webModel.setId(paymentMethod.getId());
        webModel.setName(paymentMethod.getName());

        return webModel;
    }
}
