
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox  code="authenticated.company.form.label.name" path="name"/>
	<acme:input-textbox  code="authenticated.company.form.label.VATNumber" path="VATNumber"/>
	<acme:input-textarea code="authenticated.company.form.label.summary" path="summary"/>
	<acme:input-url      code="authenticated.company.form.label.link" path="link"/>

	<acme:submit test="${_command == 'create'}" code="authenticated.company.form.button.create" action="/authenticated/company/create"/>

</acme:form>