package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.utils.StringUtils;

import java.util.*;

@Data
public class ErrorBuilder {

    private final Set<Integer> consumedKeys = new HashSet<>();
    private final StringBuilder stringBuilder = new StringBuilder();

    public void acceptIfAbsent(ErrorWrapper errorWrapper) {
        if (!consumedKeys.contains(errorWrapper.getErrorCode())) {
            stringBuilder.append(errorWrapper.getErrorMessage());
            consumedKeys.add(errorWrapper.getErrorCode());
        }
    }

    public Optional<String> buildErrorMessage() {
        if (StringUtils.isNeitherNullNorEmptyNorBlank(stringBuilder.toString())) {
            return Optional.of(stringBuilder.toString());
        }

        return Optional.empty();
    }
}
