
package acme.forms;

import java.util.Map;

import acme.entities.enumerates.ActivityType;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	/**
	 * 
	 */
	private static final long				serialVersionUID	= 1L;

	protected Map<ActivityType, Integer>	totalNumberOfAuditsByCourseType;

	protected Statistics					auditingRecordsStatistics;
	protected Statistics					periodLengthStatistics;

}
