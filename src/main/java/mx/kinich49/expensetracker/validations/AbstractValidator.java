package mx.kinich49.expensetracker.validations;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.utils.StringUtils;

public abstract class AbstractValidator<T extends ValidatorParameter> implements Validator<T> {

    protected final AbstractConditionProvider<T> conditionProvider;

    public AbstractValidator(AbstractConditionProvider<T> conditionProvider) {
        this.conditionProvider = conditionProvider;
    }

    @SuppressWarnings("all")
    @Override
    public void validate(T param) throws BusinessException {
        conditionProvider.buildConditions(param);

        StringBuilder accumulator = new StringBuilder();

        while (conditionProvider.hasNextCondition()) {
            var pair = conditionProvider.getNextCondition();
            Condition condition = pair.getLeft();
            condition.assertCondition(pair.getRight())
                    .ifPresent(accumulator::append);
        }

        if (StringUtils.isNeitherNullNorEmptyNorBlank(accumulator.toString())) {
            throw new BusinessException(accumulator.toString().trim());
        }
    }

}
