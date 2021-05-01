package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity(name = "Payment_Methods")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String name;

    public static PaymentMethod from(PaymentMethodRequest request) {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(request.getId());
        paymentMethod.setName(request.getName());
        return paymentMethod;
    }
}
