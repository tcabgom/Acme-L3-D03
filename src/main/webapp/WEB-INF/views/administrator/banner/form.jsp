<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="administrator.banner.form.label.instantiation" path="instantiation" readonly="true"/>
	<acme:input-textbox code="administrator.banner.form.label.slogan" path="slogan"/>
	<acme:input-url code="administrator.banner.form.label.linkToPicture" path="linkToPicture"/>
	<acme:input-url code="administrator.banner.form.label.linWebDocument" path="linWebDocument"/>
	<acme:input-moment code="administrator.banner.form.label.displayPeriodInitial" path="displayPeriodInitial"/>
	<acme:input-moment code="administrator.banner.form.label.displayPeriodEnding" path="displayPeriodEnding"/>
	
	<jstl:choose>
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="administrator.banner.form.button.create" action="/administrator/banner/create"/>
        </jstl:when>
        <jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
            <acme:submit code="administrator.banner.form.button.update" action="/administrator/banner/update"/>
            <acme:submit code="administrator.banner.form.button.delete" action="/administrator/banner/delete"/>
        </jstl:when>
    </jstl:choose>
</acme:form>