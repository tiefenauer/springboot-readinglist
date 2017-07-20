package info.tiefenauer.readinglist;

import info.tiefenauer.readinglist.configuration.condition.HasDescriptionCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Conditional(HasDescriptionCondition.class)
public class ComponentWithoutDescription {

    Logger LOG = LoggerFactory.getLogger(ComponentWithoutDescription.class);

    @PostConstruct
    public void onPostConstruc(){
        LOG.error("This message should not be visible because " + ComponentWithoutDescription.class.getSimpleName() + " has no description!");
    }
}
