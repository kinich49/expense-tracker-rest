package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.PaymentMethod;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public static List<PaymentMethodWebModel> from(List<PaymentMethod> paymentMethods) {
        if (paymentMethods == null || paymentMethods.isEmpty())
            return null;

        return paymentMethods.stream()
                .map(PaymentMethodWebModel::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
