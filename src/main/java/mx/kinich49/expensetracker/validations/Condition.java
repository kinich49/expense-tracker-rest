package mx.kinich49.expensetracker.validations;

import java.util.Optional;

/**
 * An interface to hold one or more thigh coupled conditions
 * <p>
 * A condition is any business rule, requirement or demand
 * an instance must met to be declared valid. If the parameter is valid,
 * then implementations of this interface must return an <b>Empty</b> Optional.
 * <p>
 * If the parameter is not valid (as it didn't meet at least one condition),
 * then the Optional must return a message fully describing
 * the reason the param is not valid
 */
public interface Condition<T extends ConditionParameter> {

    /**
     * @param param the instance to assert it meets all conditions
     * @return an Optional containing an error message, if any condition is not met.
     * An empty Optional otherwise.
     */
    Optional<String> assertCondition(T param);
}
