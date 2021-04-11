package mx.kinich49.expensetracker.validations;

import mx.kinich49.expensetracker.exceptions.BusinessException;

/**
 * An interface to declare a combination of {@link Condition}
 * <p>
 * If at least one condition is not satisfied, then a {@link BusinessException} should be thrown.
 * The exception's message should explain the error in a user-friendly manner
 */
public interface Validator<T extends ValidatorParameter> {

    /**
     * @param param the param to assert whether it meets all related conditions
     * @throws BusinessException if any {@link Condition}
     *                           is not satisfied
     */
    void validate(T param) throws BusinessException;
}
