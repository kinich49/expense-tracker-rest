package mx.kinich49.expensetracker.utils;

import java.util.List;

public class CollectionUtils {

    public static <T> boolean isNeitherNullNorEmpty(List<T> list) {
        return (list != null) && !list.isEmpty();
    }
}
