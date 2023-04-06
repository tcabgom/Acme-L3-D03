<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="true">
	<acme:input-moment  code="user-account.offer.form.label.instantiatiation"        path="instantiatiation"/>
	<acme:input-textbox code="user-account.offer.form.label.header"                  path="header"/>
	<acme:input-textbox code="user-account.offer.form.label.summary"                 path="summary"/>
	<acme:input-moment  code="user-account.offer.form.label.availabilityPeriodStart" path="availabilityPeriodStart"/>
	<acme:input-moment  code="user-account.offer.form.label.availabilityPeriodEnd"   path="availabilityPeriodEnd"/>
	<acme:input-money   code="user-account.offer.form.label.price"                   path="price"/>
	<acme:input-url     code="user-account.offer.form.label.moreInfo"                path="moreInfo"/>
</acme:form>