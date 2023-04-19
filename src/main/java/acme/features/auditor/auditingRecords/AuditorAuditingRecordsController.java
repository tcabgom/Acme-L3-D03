
package acme.features.auditor.auditingRecords;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.audit.AuditingRecords;
import acme.framework.controllers.AbstractController;
import acme.roles.Auditor;

@Controller
public class AuditorAuditingRecordsController extends AbstractController<Auditor, AuditingRecords> {

	@Autowired
	protected AuditorAuditingRecordsListService	listService;

	@Autowired
	protected AuditorAuditingRecordsShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
