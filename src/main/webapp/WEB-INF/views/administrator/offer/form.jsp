<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	
	<acme:input-moment   code="administrator.offer.form.label.instantiatiation"        path="instantiatiation" readonly="true"/>
	<acme:input-textbox  code="administrator.offer.form.label.header"                  path="header"/>
	<acme:input-textarea code="administrator.offer.form.label.summary"                 path="summary"/>
	<acme:input-moment   code="administrator.offer.form.label.availabilityPeriodStart" path="availabilityPeriodStart"/>
	<acme:input-moment   code="administrator.offer.form.label.availabilityPeriodEnd"   path="availabilityPeriodEnd"/>
	<acme:input-money    code="administrator.offer.form.label.price"                   path="price"/>
	<acme:input-url      code="administrator.offer.form.label.moreInfo"                path="moreInfo"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit         code="administrator.offer.form.button.create"        action="/administrator/offer/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="administrator.offer.form.button.update" action="/administrator/offer/update"/>
			<acme:submit code="administrator.offer.form.button.delete" action="/administrator/offer/delete"/>
		</jstl:when>
	</jstl:choose>
</acme:form>