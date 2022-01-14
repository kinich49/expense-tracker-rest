package mx.kinich49.expensetracker.validations.commercialestablishmentservice.conditions;

import mx.kinich49.expensetracker.models.database.CommercialEstablishment;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.repositories.CommercialEstablishmentRepository;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.CommercialEstablishmentServiceErrorCodes;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.ConditionParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Component
public class CommercialEstablishmentRequestNameCondition
        implements Condition<ConditionParameterImpl> {

    private final CommercialEstablishmentRepository repository;
    private final ExampleMatcher modelMatcher = ExampleMatcher.matching()
            .withIgnorePaths("id")
            .withMatcher("model", ignoreCase());

    @Autowired
    public CommercialEstablishmentRequestNameCondition(CommercialEstablishmentRepository repository) {
        this.repository = repository;
    }

    /**
     * This condition first validates the request has a non-null, non-empty, non-blank name.
     * If this is met, then it validates the name is unique (case-insensitive)
     *
     * @param param the instance to assert it meets all conditions
     * @return An optional containing an error message if the condition is not met.
     * Empty Otherwise
     */
    @Override
    public Optional<ErrorWrapper> assertCondition(ConditionParameterImpl param) {
        var request = param.getRequest();
        return validateNotNullNotEmptyName(request)
                .or(() -> validateUniqueName(request));
    }

    private Optional<ErrorWrapper> validateNotNullNotEmptyName(CommercialEstablishmentRequest request) {
        var name = request.getName();

        if (StringUtils.isNullOrEmptyOrBlank(name)) {
            var errorMessage = "Request name is null or empty. ";
            var errorWrapper = new ErrorWrapper(CommercialEstablishmentServiceErrorCodes.REQUEST_NAME_IS_NULL_OR_EMPTY, errorMessage);
            return Optional.of(errorWrapper);
        }

        return Optional.empty();
    }

    private Optional<ErrorWrapper> validateUniqueName(CommercialEstablishmentRequest request) {
        var probe = new CommercialEstablishment();
        probe.setName(request.getName());
        var example = Example.of(probe, modelMatcher);

        if (repository.exists(example)) {
            String errorMessage = String.format("A Commercial Establishment entity already exists with the name %1$s. ",
                    request.getName());
            return Optional.of(new ErrorWrapper(CommercialEstablishmentServiceErrorCodes.COMMERCIAL_ESTABLISHMENT_WITH_NAME_ALREADY_EXISTS, errorMessage));
        }
        return Optional.empty();
    }

}
