
package acme.features.assistant.tutorial;

import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialCreateService extends AbstractService<Assistant, Tutorial> {

}
