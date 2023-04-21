
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox  code="authenticated.auditor.form.label.firm"          		path="firm"/>
	<acme:input-textarea code="authenticated.auditor.form.label.proffesionalID" 	path="proffesionalID"/>
	<acme:input-textarea code="authenticated.auditor.form.label.certifications"     path="certifications"/>
	<acme:input-url      code="authenticated.auditor.form.label.link"               path="link"/>

	<acme:submit test="${_command == 'create'}" code="authenticated.auditor.form.button.create" action="/authenticated/auditor/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.auditor.form.button.update" action="/authenticated/auditor/update"/>
</acme:form>