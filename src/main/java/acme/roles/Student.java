package acme.roles;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
public class Student extends AbstractRole {

    // Serialisation identifier -----------------------------------------------

    protected static final long serialVersionUID = 1L;

    // Attributes -------------------------------------------------------------

    @NotBlank
    @Length(max = 75)
    protected String statement;

    @NotBlank
    @Length(max = 100)
    protected String strongFeatures;

    @NotBlank
    @Length(max = 100)
    protected String weakFeatures;

    @URL
    protected String furtherInformation;

    // Derived attributes -----------------------------------------------------

    // Relationships ----------------------------------------------------------

}
