package mx.kinich49.expensetracker.validations.commercialestablishmentservice;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommercialEstablishmentServiceErrorCodes {

    public static final int REQUEST_NAME_IS_NULL_OR_EMPTY = 1;
    public static final int COMMERCIAL_ESTABLISHMENT_WITH_NAME_ALREADY_EXISTS = 2;
    public static final int COMMERCIAL_ESTABLISHMENT_WITH_ID_DOES_NOT_EXISTS = 3;
    public static final int INVALID_ID = 4;
}
