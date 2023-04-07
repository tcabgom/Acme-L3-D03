
package acme.features.assistant.tutorialSession;

import org.springframework.stereotype.Service;

import acme.entities.tutorialSession.TutorialSession;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionCreateService extends AbstractService<Assistant, TutorialSession> {

}
