package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.CommercialEstablishment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<CommercialEstablishment, Long> {
}
