package mx.kinich49.expensetracker.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static final double PRICE_SCALE = 100;

    public static final DecimalFormat LIMIT_FORMAT = new DecimalFormat("#,##0.00");
}