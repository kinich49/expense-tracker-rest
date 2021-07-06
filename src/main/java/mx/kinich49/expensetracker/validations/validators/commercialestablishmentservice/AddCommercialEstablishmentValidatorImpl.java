package mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Validator;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.CommercialEstablishmentRequestNameConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.Parameter;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.NotNullCommercialEstablishmentRequestConditionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddCommercialEstablishmentValidatorImpl implements
        Validator<mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice.Parameter> {

    private final NotNullCommercialEstablishmentRequestConditionImpl notNullCondition;
    private final CommercialEstablishmentRequestNameConditionImpl nameCondition;

    @Autowired
    public AddCommercialEstablishmentValidatorImpl(NotNullCommercialEstablishmentRequestConditionImpl notNullCondition,
                                                   CommercialEstablishmentRequestNameConditionImpl nameCondition) {
        this.notNullCondition = notNullCondition;
        this.nameCondition = nameCondition;
    }

    @Override
    public void validate(mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice.Parameter param) throws BusinessException {
        var conditionParameter = new Parameter(param.getRequest());
        StringBuilder accumulator = new StringBuilder();

        notNullCondition.assertCondition(conditionParameter)
                .ifPresent(accumulator::append);
        nameCondition.assertCondition(conditionParameter)
                .ifPresent(accumulator::append);

        String error = accumulator.toString().trim();

        if (StringUtils.isNeitherNullNorEmptyNorBlank(error)) {
            throw new BusinessException(error.trim());
        }
    }

}
