
package acme.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Auditor extends AbstractRole {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Length(max = 75)
	@NotBlank
	protected String			firm;

	@Length(max = 25)
	@NotBlank
	@Column(unique = true)
	protected String			proffesionalID;

	@Length(max = 100)
	@NotBlank
	protected String			certifications;

	@URL
	@Column(nullable = true)
	protected String			link;

}
