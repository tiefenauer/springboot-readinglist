package info.tiefenauer.readinglist;

import info.tiefenauer.readinglist.configuration.condition.HasDescriptionCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Conditional(HasDescriptionCondition.class)
@Description(value = "Component with description")
public class ComponentWithDescription {

    Logger LOG = LoggerFactory.getLogger(ComponentWithDescription.class);

    @PostConstruct
    public void bla(){
        LOG.info(ComponentWithDescription.class.getSimpleName() + " has been created");
    }
}
