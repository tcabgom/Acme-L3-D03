SSS<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.records.form.label.subject" path="subject"/>
	<acme:hidden-data path="audit"/>
	<acme:input-textbox code="auditor.records.form.label.mark" path="mark"/>
	<acme:input-textbox code="auditor.records.form.label.assesment" path="assesment"/>
	<acme:input-moment code="auditor.records.form.label.auditingPeriodInitial" path="auditingPeriodInitial"/>
	<acme:input-moment code="auditor.records.form.label.auditingPeriodEnd" path="auditingPeriodEnd"/>
	<acme:input-textbox code="auditor.records.form.label.draftMode" path="draftMode" readonly="true"/>
	<jstl:choose>
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="auditor.records.form.button.create" action="/auditor/auditing-records/create?auditId=${auditId }"/>
        </jstl:when>
        <jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
            <acme:submit code="auditor.records.form.button.update" action="/auditor/auditing-records/update?auditId=${auditId}"/>
            <acme:submit code="auditor.records.form.button.delete" action="/auditor/auditing-records/delete?auditId=${auditId}"/>
        </jstl:when>
    </jstl:choose>
</acme:form>