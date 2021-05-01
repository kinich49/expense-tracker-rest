package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.CommercialEstablishment;

@Data
public class StoreWebModel {

    private final long id;
    private final String name;

    public static StoreWebModel from(CommercialEstablishment commercialEstablishment) {
        if (commercialEstablishment == null)
            return null;

        return new StoreWebModel(commercialEstablishment.getId(),
                commercialEstablishment.getName());
    }
}
