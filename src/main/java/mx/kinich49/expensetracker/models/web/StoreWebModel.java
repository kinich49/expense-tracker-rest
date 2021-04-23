package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.Store;

@Data
public class StoreWebModel {

    private final long id;
    private final String name;

    public static StoreWebModel from(Store store) {
        if (store == null)
            return null;

        return new StoreWebModel(store.getId(),
                store.getName());
    }
}
