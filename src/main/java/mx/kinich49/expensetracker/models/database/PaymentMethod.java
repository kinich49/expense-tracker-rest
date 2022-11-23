package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity(name = "Payment_Methods")
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentMethod that = (PaymentMethod) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
