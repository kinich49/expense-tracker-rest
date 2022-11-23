package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity(name = "CommercialEstablishments")
@Getter
@Setter
public class CommercialEstablishment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommercialEstablishment that = (CommercialEstablishment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CommercialEstablishment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
