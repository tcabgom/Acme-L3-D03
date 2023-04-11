
package acme.entities.configuration;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.dom4j.tree.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Configuration extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "^[A-Z]{3}$")
	protected String			currency;

	@NotBlank
	protected String			aceptedCurrencies;

	// Derived attributes -----------------------------------------------------


	public boolean isAcceptedCurrency(final String currency) {
		final String currencyUpperCase = currency.toUpperCase();
		for (final String aceptedCurrencies : this.aceptedCurrencies.toUpperCase().split(","))
			if (currencyUpperCase.equals(aceptedCurrencies))
				return true;
		return false;
	}

	// Relationships ----------------------------------------------------------

}
