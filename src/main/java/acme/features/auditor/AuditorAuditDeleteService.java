
package acme.features.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditDeleteService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;

}
