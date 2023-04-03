
package acme.entities.offer;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Offer extends AbstractEntity {

	// Serialisation identifier ------------------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes --------------------------------------------------------------------

	@PastOrPresent
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				instantiatiation;

	@NotBlank
	@Length(max = 75)
	protected String			header;

	@NotBlank
	@Length(max = 100)
	protected String			summary;

	// TODO: Restriction: Must be at least 1 day after instantiation
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				availabilityPeriodStart;

	// TODO: Restriction: Must be at least 7 days after starting availability period
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				availabilityPeriodEnd;

	@NotNull
	@Valid
	protected Money				price;

	@URL
	protected String			moreInfo;

	// Derived attributes ------------------------------------------------------------

	// Relationships -----------------------------------------------------------------

}
