
package acme.entities.audit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.lecture.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Audit extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}$")
	@NotBlank
	@Column(unique = true)
	protected String			code;

	@Length(max = 100)
	@NotBlank
	protected String			conclusion;

	@Length(max = 100)
	@NotBlank
	protected String			strongPoints;

	@Length(max = 100)
	@NotBlank
	protected String			weakPoints;

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	protected Auditor			auditor;

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	protected Course			course;

	protected boolean			draftMode;

}
