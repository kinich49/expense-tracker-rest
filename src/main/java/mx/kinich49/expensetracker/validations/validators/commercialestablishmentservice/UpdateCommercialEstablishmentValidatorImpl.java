package mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Validator;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.Parameter;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.NotNullCommercialEstablishmentRequestConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.UpdateCommercialEstablishmentConditionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommercialEstablishmentValidatorImpl implements Validator<mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice.Parameter> {

    private final NotNullCommercialEstablishmentRequestConditionImpl notNullCondition;
    private final UpdateCommercialEstablishmentConditionImpl updateCondition;

    @Autowired
    public UpdateCommercialEstablishmentValidatorImpl(NotNullCommercialEstablishmentRequestConditionImpl notNullCondition,
                                                      UpdateCommercialEstablishmentConditionImpl updateCondition) {
        this.notNullCondition = notNullCondition;
        this.updateCondition = updateCondition;
    }

    @Override
    public void validate(mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice.Parameter param) throws BusinessException {
        Parameter conditionParameter = new Parameter(param.getRequest());
        StringBuilder accumulator = new StringBuilder();

        notNullCondition.assertCondition(conditionParameter)
                .ifPresent(accumulator::append);

        updateCondition.assertCondition(conditionParameter)
                .ifPresent(accumulator::append);

        String error = accumulator.toString().trim();

        if (StringUtils.isNeitherNullNorEmptyNorBlank(error)) {
            throw new BusinessException(error.trim());
        }

    }
}
