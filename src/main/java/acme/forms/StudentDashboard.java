package acme.forms;

import acme.entities.enumerates.ActivityType;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

    // Serialisation identifier -----------------------------------------------
    protected static final long serialVersionUID = 1L;

    // Attributes -------------------------------------------------------------

    protected Map<ActivityType, Integer> totalActivitiesByType;

    protected Statistics activityPeriodStatistics;

    protected Statistics learningTimeStatistics;

    // Derived attributes -----------------------------------------------------


    // Relationships ----------------------------------------------------------

}
