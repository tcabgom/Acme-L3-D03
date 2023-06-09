
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorialSession.TutorialSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AuthenticatedTutorialRepository extends AbstractRepository {

	@Query("select t from Tutorial t")
	Collection<Tutorial> findAllAvailableTutorials();

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findOneTutorialById(int id);

	@Query("select a from Assistant a")
	Collection<Assistant> findManyAssistantsByTutorial(Tutorial t);

	@Query("select t from Tutorial t where t.course.id = :id")
	Collection<Tutorial> findManyTutorialsByCourseId(int id);

	@Query("select ts from TutorialSession ts where ts.tutorial = :tutorial")
	Collection<TutorialSession> findManyTutorialSessionsByTutorialId(Tutorial tutorial);
}
