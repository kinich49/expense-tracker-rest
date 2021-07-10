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
 */
public interface Condition<T extends ConditionParameter> {

    /**
     * @param param the instance to assert it meets all conditions
     * @return an Optional containing an error message, if any condition is not met.
     * An empty Optional otherwise.
     */
    Optional<String> assertCondition(T param) throws ValidationFlowException;
}
