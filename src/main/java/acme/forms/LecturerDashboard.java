
package acme.forms;

import java.util.Map;

import acme.entities.enumerates.ActivityType;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long				serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	protected Map<ActivityType, Integer>	totalNumberOfLecturesPerType;

	protected Statistics					lecturerLectures;

	protected Statistics					lecturerCourses;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
