<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.offer.form.label.instantiatiation" path="instantiatiation" readonly="true"/>
	<acme:input-textbox code="authenticated.offer.form.label.header" path="header" readonly="true"/>
	<acme:input-textbox code="authenticated.offer.form.label.summary" path="summary" readonly="true"/>
	<acme:input-textbox code="authenticated.offer.form.label.availabilityPeriodStart" path="availabilityPeriodStart" readonly="true"/>
	<acme:input-textbox code="authenticated.offer.form.label.availabilityPeriodEnd" path="availabilityPeriodEnd" readonly="true"/>
	<acme:input-textbox code="authenticated.offer.form.label.price" path="price" readonly="true"/>
	<acme:input-textbox code="authenticated.offer.form.label.moreInfo" path="moreInfo" readonly="true"/>
</acme:form>