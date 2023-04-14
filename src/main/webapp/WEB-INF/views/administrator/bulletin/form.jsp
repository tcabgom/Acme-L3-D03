<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="${readonly}">
    <acme:hidden-data path="instantiationMoment"/>
    <acme:input-textbox code="administrator.bulletin.form.label.title" path="title"/>
    <acme:input-checkbox code="administrator.bulletin.form.label.critical" path="critical"/>
    <acme:input-textarea code="administrator.bulletin.form.label.message" path="message"/>
    <acme:input-url code="administrator.bulletin.form.label.furtherInformation" path="furtherInformation"/>

    <jstl:if test="${!readonly}">
        <acme:input-checkbox code="administrator.bulletin.form.label.confirmation" path="confirmation"/>
        <acme:submit code="administrator.bulletin.form.button.create" action="/administrator/bulletin/create"/>
    </jstl:if>
</acme:form>