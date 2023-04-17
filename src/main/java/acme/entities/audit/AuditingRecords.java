
package acme.entities.audit;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.enumerates.Mark;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AuditingRecords extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Length(max = 75)
	@NotBlank
	protected String			subject;

	@Length(max = 100)
	@NotBlank
	protected String			assesment;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	protected Date				auditingPeriodInitial;

	// TODO: Restriction: Must be at least 1 hour long
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	protected Date				auditingPeriodEnd;

	@NotNull
	protected Mark				mark;

	@URL
	@Column(nullable = true)
	protected String			furtherInformation;

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	protected Audit				audit;

	protected boolean			draftMode;
}
