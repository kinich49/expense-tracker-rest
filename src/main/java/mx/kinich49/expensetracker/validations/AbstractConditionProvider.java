package mx.kinich49.expensetracker.validations;

import mx.kinich49.expensetracker.utils.Pair;

import java.util.Objects;
import java.util.Queue;

public abstract class AbstractConditionProvider<P extends ValidatorParameter> implements ConditionProvider<P> {

    protected final ThreadLocal<Queue<Pair<Condition<? extends ConditionParameter>, ? extends ConditionParameter>>>
            threadLocal = new ThreadLocal<>();

    @Override
    public Pair<Condition<? extends ConditionParameter>, ? extends ConditionParameter> getNextCondition() {
        return threadLocal.get().poll();
    }

    @Override
    public boolean hasNextCondition() {
        return !Objects.isNull(threadLocal.get()) &&
                !Objects.isNull(threadLocal.get().peek());
    }

}
