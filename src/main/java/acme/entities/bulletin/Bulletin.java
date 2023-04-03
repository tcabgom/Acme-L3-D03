package acme.entities.bulletin;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Entity
@Getter
@Setter
public class Bulletin extends AbstractEntity {

    // Serialisation identifier -----------------------------------------------

    protected static final long serialVersionUID = 1L;

    // Attributes -------------------------------------------------------------

    @PastOrPresent
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    protected Date instantiationMoment;

    @NotBlank
    @Length(max = 75)
    protected String title;

    protected boolean critical;

    @NotBlank
    @Length(max = 100)
    protected String message;

    @URL
    protected String furtherInformation;

    // Derived attributes -----------------------------------------------------

    // Relationships ----------------------------------------------------------

}
