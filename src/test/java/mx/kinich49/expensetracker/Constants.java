package mx.kinich49.expensetracker;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final  class Constants {
    //As of June 6th, 2021
    public static final int TOTAL_CATEGORIES = 8;

    public static final int TAKE_OUT_FOOD_CATEGORY_ID = 1;
    public static final int GAS_CATEGORY_ID = 2;
    public static final int GROCERIES_CATEGORY_ID = 3;
    public static final int HOME_EXPENSES_CATEGORY_ID = 4;
    public static final int MISCELANIUS_CATEGORY_ID = 5;
    public static final int RENT_CATEGORY_ID = 6;
    public static final int SAVINGS_CATEGORY_ID = 7;
    public static final int SERVICES_CATEGORY_ID = 8;
}
