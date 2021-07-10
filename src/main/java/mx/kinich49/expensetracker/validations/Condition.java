package mx.kinich49.expensetracker.validations;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;

import java.util.Optional;

/**
 * A condition is any business rule, requirement or demand
 * an instance must met to be declared "valid". If the parameter is valid,
 * then implementations of this interface must return an <b>Empty</b> Optional.
 * <p>
 * If the parameter is not valid (as it didn't satisfy the business rule),
 * then the Optional must return a message fully describing
 * the reason the instance is not valid.
 * <p>
 * As an example, when adding a new CommercialEstablishment,
 * the new instance is "valid" if its name is unique in
 * the database (case insensitive)
 * <p>
 * That's one business rule, encapsulated in its own
 * Condition implementation
 * <p>
 * It might occur a condition is considered a "gatekeeper", as in,
 * if that condition is not met, then all other consecutive
 * conditions should not even be executed.
 * <p>
 * For example, a Request to add a CommercialEstablishment has
 * a condition the request must not be null. If this condition
 * is not satisfied, then other conditions should not be tested
 * as they might cause errors, or duplicated error messages.
 */
public interface Condition<T extends ConditionParameter> {

    /**
     * @param param the instance to assert it meets all conditions
     * @return an Optional containing an error message, if any condition is not met.
     * An empty Optional otherwise.
     * @throws ValidationFlowException when a "gatekeeper" condition is not met, as in,
     *                                 consecutive conditions should not be tested.
     */
    Optional<String> assertCondition(T param) throws ValidationFlowException;
}
