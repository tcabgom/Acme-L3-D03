
package acme.entities.lecture;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.enumerates.ActivityType;
import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Course extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}$")
	@NotBlank
	@Column(unique = true)
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			courseAbstract;

	@NotNull
	protected Money				retailPrice;

	@URL
	protected String			furtherInformation;

	protected boolean			draftMode;

	// Derived attributes -----------------------------------------------------


	public ActivityType courseActivityType(final List<Lecture> lectures) {
		ActivityType knowledge = ActivityType.BALANCED;
		if (!lectures.isEmpty()) {
			int theory = 0;
			int handsOn = 0;
			for (final Lecture l : lectures)
				if (l.getKnowledge().equals(ActivityType.THEORY))
					theory++;
				else if (l.getKnowledge().equals(ActivityType.HANDS_ON))
					handsOn++;
			if (theory > handsOn)
				knowledge = ActivityType.THEORY;
			else if (handsOn > theory)
				knowledge = ActivityType.HANDS_ON;
		}
		return knowledge;
	}

	// Relationships ----------------------------------------------------------


	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Lecturer lecturer;

}
