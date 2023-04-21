
package acme.entities.practicum;

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
import acme.entities.practicumSession.PracticumSession;
import acme.framework.data.AbstractEntity;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Practicum extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}$")
	@Column(unique = true)
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstractPracticum;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	protected boolean			draftMode;

	// Derived attributes -----------------------------------------------------


	public double getEstimatedTotalTimeInHours(final Collection<PracticumSession> practicumSessions) {

		final int hoursInMilliseconds = 3600000;
		final int minutesInMilliseconds = 60000;

		double hoursEstimated = .0;

		for (final PracticumSession ps : practicumSessions) {
			final double thisSessionStartTime = ps.getStartWeek().getTime();
			final double thisSessionEndTime = ps.getFinishWeek().getTime();

			final double thisSessionHours = Math.abs(thisSessionEndTime / hoursInMilliseconds - thisSessionStartTime / hoursInMilliseconds);
			final double thisSessionMinutes = Math.abs(thisSessionEndTime / minutesInMilliseconds - thisSessionStartTime / minutesInMilliseconds) % 60 * 0.01;

			hoursEstimated += thisSessionHours + thisSessionMinutes;
		}
		return hoursEstimated;
	}

	// Relationships ----------------------------------------------------------


	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Company	company;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Course	course;

}
