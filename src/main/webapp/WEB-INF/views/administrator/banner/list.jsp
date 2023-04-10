<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan" width="20%"/>
	<acme:list-column code="administrator.banner.list.label.linkToPicture" path="linkToPicture" width="20%"/>
	<acme:list-column code="administrator.banner.list.label.linWebDocument" path="linWebDocument" width="20%"/>
	<acme:list-column code="administrator.banner.list.label.displayPeriodInitial" path="displayPeriodInitial" width="20%"/>
	<acme:list-column code="administrator.banner.list.label.displayPeriodEnding" path="displayPeriodEnding" width="20%"/>
</acme:list>
<acme:button code="administrator.banner.form.button.create" action="/administrator/banner/create"/>