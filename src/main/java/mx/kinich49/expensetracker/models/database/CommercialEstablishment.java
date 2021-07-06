package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "CommercialEstablishments")
public class CommercialEstablishment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "native")
    @GenericGenerator(name = "native",
            strategy = "native")
    private Long id;

    private String name;

    public static CommercialEstablishment from(CommercialEstablishmentRequest request) {
        CommercialEstablishment commercialEstablishment = new CommercialEstablishment();
        commercialEstablishment.setId(request.getId());
        commercialEstablishment.setName(request.getName());
        return commercialEstablishment;
    }

    public static CommercialEstablishment copy(CommercialEstablishment commercialEstablishment) {
        if (commercialEstablishment == null)
            return null;

        CommercialEstablishment copy = new CommercialEstablishment();
        copy.id = commercialEstablishment.id;
        copy.name = commercialEstablishment.name;

        return copy;
    }
}
