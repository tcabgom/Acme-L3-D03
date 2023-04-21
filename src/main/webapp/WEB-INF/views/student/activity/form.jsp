<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	
	<acme:input-textbox   code="student.activity.form.label.title" path="title" />
	<acme:input-textarea  code="student.activity.form.label.activityAbstract" path="activityAbstract"/>
	<acme:input-select    code="student.activity.form.label.type" path="type" choices="${types}"/>
	<acme:input-moment    code="student.activity.form.label.periodStart" path="periodStart"/>
	<acme:input-moment    code="student.activity.form.label.periodEnd" path="periodEnd"/>
	<acme:input-url  	  code="student.activity.form.label.furtherInformation" path="furtherInformation"/>

	<jstl:if test="${_command == 'create'}">
		<acme:submit code="student.activity.form.button.create" action="/student/activity/create?enrolmentId=${enrolmentId}"/>
	</jstl:if>
</acme:form>