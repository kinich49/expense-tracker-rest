package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
