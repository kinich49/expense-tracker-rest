package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.StoreRequest;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "Stores")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "native")
    @GenericGenerator(name = "native",
            strategy = "native")
    private Long id;

    private String name;

    public static Store from(StoreRequest request) {
        Store store = new Store();
        store.setId(request.getId());
        store.setName(request.getName());
        return store;
    }
}
