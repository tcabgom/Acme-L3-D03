
package acme.forms;

import java.util.Map;

import acme.entities.enumerates.ActivityType;
import acme.framework.data.AbstractForm;

public class LecturerDashboarb extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long				serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	protected Map<ActivityType, Integer>	totalNumberOfSessionsPerType;

	protected Statistics					lecturerLectures;

	protected Statistics					lecturerCourses;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
