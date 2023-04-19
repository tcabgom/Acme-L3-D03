<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.records.form.label.subject" path="subject"/>
	<acme:input-textbox code="auditor.records.form.label.mark" path="mark"/>
	<acme:input-textbox code="auditor.records.form.label.assesment" path="assesment"/>
	<acme:input-textbox code="auditor.records.form.label.auditingPeriodInitial" path="auditingPeriodInitial"/>
	<acme:input-textbox code="auditor.records.form.label.auditingPeriodEnd" path="auditingPeriodEnd"/>
	<acme:input-textbox code="auditor.records.form.label.draftMode" path="draftMode" readonly="true"/>
	<jstl:choose>
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="auditor.records.form.button.create" action="/auditor/auditing-records/create"/>
        </jstl:when>
        <jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
            <acme:submit code="auditor.records.form.form.button.update" action="/auditor/auditing-records/update"/>
            <acme:submit code="auditor.records.form.button.delete" action="/auditor/auditing-records/delete"/>
        </jstl:when>
    </jstl:choose>
</acme:form>