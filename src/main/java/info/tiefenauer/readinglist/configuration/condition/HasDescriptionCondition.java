package info.tiefenauer.readinglist.configuration.condition;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Description;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Condition to check if component has a description
 */
public class HasDescriptionCondition implements Condition {
    Logger LOG = LoggerFactory.getLogger(HasDescriptionCondition.class);

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return metadata.isAnnotated(Description.class.getName());
    }
}
