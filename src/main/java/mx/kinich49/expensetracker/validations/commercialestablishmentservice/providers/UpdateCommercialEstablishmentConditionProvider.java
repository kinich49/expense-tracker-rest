package mx.kinich49.expensetracker.validations.commercialestablishmentservice.providers;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.conditions.CommercialEstablishmentRequestNameCondition;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.ConditionParameterImpl;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.conditions.NotNullCommercialEstablishmentRequestCondition;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.conditions.UpdateCommercialEstablishmentCondition;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.validators.CommercialEstablishmentValidatorParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommercialEstablishmentConditionProvider
        extends AbstractConditionProvider<CommercialEstablishmentValidatorParameter> {

    private final NotNullCommercialEstablishmentRequestCondition notNullCondition;
    private final UpdateCommercialEstablishmentCondition updateCondition;
    private final CommercialEstablishmentRequestNameCondition nameCondition;

    @Autowired
    public UpdateCommercialEstablishmentConditionProvider(NotNullCommercialEstablishmentRequestCondition notNullCondition,
                                                          UpdateCommercialEstablishmentCondition updateCondition,
                                                          CommercialEstablishmentRequestNameCondition nameCondition) {
        this.notNullCondition = notNullCondition;
        this.updateCondition = updateCondition;
        this.nameCondition = nameCondition;
    }


    @Override
    public void buildConditions(CommercialEstablishmentValidatorParameter parameter) {
        var conditionParameter = new ConditionParameterImpl(parameter.getRequest());
        addCondition(notNullCondition, conditionParameter);
        addCondition(nameCondition, conditionParameter);
        addCondition(updateCondition, conditionParameter);
    }
}
