package mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice;

import lombok.Data;
import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Validator;
import mx.kinich49.expensetracker.validations.ValidatorParameter;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.CommercialEstablishmentRequestNameConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.ConditionParameterImpl;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.NotNullCommercialEstablishmentRequestConditionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommercialEstablishmentRequestValidatorImpl implements
        Validator<CommercialEstablishmentRequestValidatorImpl.Parameter> {

    private final NotNullCommercialEstablishmentRequestConditionImpl notNullCondition;
    private final CommercialEstablishmentRequestNameConditionImpl nameCondition;

    @Autowired
    public CommercialEstablishmentRequestValidatorImpl(NotNullCommercialEstablishmentRequestConditionImpl notNullCondition,
                                                       CommercialEstablishmentRequestNameConditionImpl nameCondition) {
        this.notNullCondition = notNullCondition;
        this.nameCondition = nameCondition;
    }

    @Override
    public void validate(Parameter param) throws BusinessException {
        var conditionParameter = new ConditionParameterImpl(param.request);
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

    @Data
    public static class Parameter implements ValidatorParameter {
        private final CommercialEstablishmentRequest request;
    }
}
