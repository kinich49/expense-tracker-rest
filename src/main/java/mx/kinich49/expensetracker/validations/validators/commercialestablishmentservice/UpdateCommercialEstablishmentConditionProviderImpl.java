package mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.CommercialEstablishmentRequestNameConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.ConditionParameterImpl;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.NotNullCommercialEstablishmentRequestConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.UpdateCommercialEstablishmentConditionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommercialEstablishmentConditionProviderImpl
        extends AbstractConditionProvider<CommercialEstablishmentValidatorParameterImpl> {

    private final NotNullCommercialEstablishmentRequestConditionImpl notNullCondition;
    private final UpdateCommercialEstablishmentConditionImpl updateCondition;
    private final CommercialEstablishmentRequestNameConditionImpl nameCondition;

    @Autowired
    public UpdateCommercialEstablishmentConditionProviderImpl(NotNullCommercialEstablishmentRequestConditionImpl notNullCondition,
                                                              UpdateCommercialEstablishmentConditionImpl updateCondition,
                                                              CommercialEstablishmentRequestNameConditionImpl nameCondition) {
        this.notNullCondition = notNullCondition;
        this.updateCondition = updateCondition;
        this.nameCondition = nameCondition;
    }


    @Override
    public void buildConditions(CommercialEstablishmentValidatorParameterImpl parameter) {
        var conditionParameter = new ConditionParameterImpl(parameter.getRequest());
        addCondition(notNullCondition, conditionParameter);
        addCondition(nameCondition, conditionParameter);
        addCondition(updateCondition, conditionParameter);
    }
}
