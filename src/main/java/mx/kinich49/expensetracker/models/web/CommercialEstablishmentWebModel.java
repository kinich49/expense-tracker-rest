package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.CommercialEstablishment;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommercialEstablishmentWebModel {

    private final long id;
    private final String name;

    public static CommercialEstablishmentWebModel from(CommercialEstablishment commercialEstablishment) {
        if (commercialEstablishment == null)
            return null;

        return new CommercialEstablishmentWebModel(commercialEstablishment.getId(),
                commercialEstablishment.getName());
    }

    public static List<CommercialEstablishmentWebModel> from(List<CommercialEstablishment> commercialEstablishments) {
        if (commercialEstablishments == null || commercialEstablishments.isEmpty())
            return null;

        return commercialEstablishments.stream()
                .map(CommercialEstablishmentWebModel::from)
                .collect(Collectors.toList());
    }
}
