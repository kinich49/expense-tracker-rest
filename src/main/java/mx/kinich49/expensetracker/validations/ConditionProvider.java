package mx.kinich49.expensetracker.validations;

import mx.kinich49.expensetracker.utils.Pair;

/**
 * Provider class that will return conditions for
 * a given {@link Validator}
 *
 * @param <P>
 */
public interface ConditionProvider<P extends ValidatorParameter> {

    /**
     * Method to build all conditions based on the ValidatorParameter
     * <p>
     * Every condition needs a conditionParameter built at runtime.
     * And each condition can have its own ConditionParameterImpl
     * (Or all can have the same)
     *
     * @param parameter the parameter passed to the {@link Validator}
     */
    void buildConditions(P parameter);

    /**
     * Returns the next condition to be validated. Implementations of
     * this interface are free to decide the best structure
     * to hold the conditions, hence this method only returns
     * one Condition-ConditionParameter at a time.
     * <p>
     * It was decided that this method won't return a Collection
     * to prevent the client from potentially running the stream in parallel.
     * Each condition must be executed serially, and in the specific order
     * each Validator implementation decides
     *
     * @return a Pair containing the Condition and its conditionParameter.
     * The conditionParameter class can be anything that extends {@link ConditionParameter}
     */
    Pair<Condition<? extends ConditionParameter>, ? extends ConditionParameter> getNextCondition();

    /**
     * Ideally, the client will use this method to manually iterate
     * the conditions returned by {@link #getNextCondition}
     *
     * @return true if there's still more conditions to be validated. False otherwise
     */
    boolean hasNextCondition();
}
