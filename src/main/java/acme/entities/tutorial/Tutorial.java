
package acme.entities.tutorial;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.lecture.Course;
import acme.entities.tutorialSession.TutorialSession;
import acme.framework.data.AbstractEntity;
import acme.roles.Assistant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tutorial extends AbstractEntity {

	// Serialisation identifier ------------------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes --------------------------------------------------------------------

	@Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}$")
	@NotBlank
	@Column(unique = true)
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			tutorialAbstract;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	protected boolean			draftMode;

	// Derived attributes ------------------------------------------------------------


	public double getEstimatedTotalTimeInHours(final Collection<TutorialSession> tutorialSessions) {

		final int hoursInMilliseconds = 3600000;
		final int minutesInMilliseconds = 60000;

		double hoursEstimated = .0;

		for (final TutorialSession ts : tutorialSessions) {
			final double thisSessionStartTime = ts.getSessionStart().getTime();
			final double thisSessionEndTime = ts.getSessionEnd().getTime();

			final double thisSessionHours = Math.abs(thisSessionEndTime / hoursInMilliseconds - thisSessionStartTime / hoursInMilliseconds);
			final double thisSessionMinutes = Math.abs(thisSessionEndTime / minutesInMilliseconds - thisSessionStartTime / minutesInMilliseconds) % 60 * 0.01;

			hoursEstimated += thisSessionHours + thisSessionMinutes;
		}
		return hoursEstimated;
	}

	// Relationships -----------------------------------------------------------------


	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Course	course;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Assistant	assistant;

}
