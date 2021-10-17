package mx.kinich49.expensetracker.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(
        value = "classpath:errormessages.properties",
        name = "errormessages")
public class ErrorMessagesConfiguration {
}
