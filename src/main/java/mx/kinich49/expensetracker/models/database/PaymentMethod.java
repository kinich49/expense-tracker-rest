package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity(name = "Payment_Methods")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public static PaymentMethod from(PaymentMethodRequest request) {
        if (request == null)
            return null;

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(request.getId());
        paymentMethod.setName(request.getName());

        return paymentMethod;
    }

    public static PaymentMethod copy(PaymentMethod paymentMethod) {
        if (paymentMethod == null)
            return null;

        PaymentMethod copy = new PaymentMethod();
        copy.id = paymentMethod.id;
        copy.name = paymentMethod.name;

        return copy;
    }
}
