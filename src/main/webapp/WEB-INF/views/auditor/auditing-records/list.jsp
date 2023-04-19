<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.records.list.label.subject" path="subject" width="20%"/>
	<acme:list-column code="auditor.records.list.label.mark" path="mark" width="20%"/>
	<acme:list-column code="auditor.records.list.label.draftMode" path="draftMode" width="20%"/>
</acme:list>
<acme:button code="auditor.records.form.button.create" action="/auditor/auditing-records/create"/>

