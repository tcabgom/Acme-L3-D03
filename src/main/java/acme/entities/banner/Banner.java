
package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				instantiation;

	// TODO: Restriction: Must be after the instantiation
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				displayPeriodInitial;

	// TODO: Restriction: Must last at least 1 week 
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	protected Date				displayPeriodEnding;

	@URL
	@NotBlank
	protected String			linkToPicture;

	@Length(max = 75)
	@NotBlank
	protected String			slogan;

	@URL
	@NotBlank
	protected String			linWebDocument;

}
