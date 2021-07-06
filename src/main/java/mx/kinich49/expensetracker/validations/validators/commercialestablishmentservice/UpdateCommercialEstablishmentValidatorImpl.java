package mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Validator;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.CommercialEstablishmentRequestNameConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.ConditionParameterImpl;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.NotNullCommercialEstablishmentRequestConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.UpdateCommercialEstablishmentConditionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommercialEstablishmentValidatorImpl
        implements Validator<ValidatorParameterImpl> {

    private final NotNullCommercialEstablishmentRequestConditionImpl notNullCondition;
    private final UpdateCommercialEstablishmentConditionImpl updateCondition;
    private final CommercialEstablishmentRequestNameConditionImpl nameCondition;

    @Autowired
    public UpdateCommercialEstablishmentValidatorImpl(NotNullCommercialEstablishmentRequestConditionImpl notNullCondition,
                                                      UpdateCommercialEstablishmentConditionImpl updateCondition,
                                                      CommercialEstablishmentRequestNameConditionImpl nameCondition) {
        this.notNullCondition = notNullCondition;
        this.updateCondition = updateCondition;
        this.nameCondition = nameCondition;
    }

    @Override
    public void validate(ValidatorParameterImpl param) throws BusinessException {
        ConditionParameterImpl conditionParameter = new ConditionParameterImpl(param.getRequest());
        StringBuilder accumulator = new StringBuilder();

        notNullCondition.assertCondition(conditionParameter)
                .ifPresent(accumulator::append);

        nameCondition.assertCondition(conditionParameter)
                .ifPresent(accumulator::append);

        updateCondition.assertCondition(conditionParameter)
                .ifPresent(accumulator::append);

        String error = accumulator.toString().trim();

        if (StringUtils.isNeitherNullNorEmptyNorBlank(error)) {
            throw new BusinessException(error.trim());
        }

    }
}
