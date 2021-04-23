package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
