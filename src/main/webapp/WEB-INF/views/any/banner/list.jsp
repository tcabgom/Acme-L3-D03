<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.banner.list.label.slogan" path="slogan" width="20%"/>
	<acme:list-column code="any.banner.list.label.linkToPicture" path="linkToPicture" width="5%"/>
	<acme:list-column code="any.banner.list.label.linWebDocument" path="linWebDocument" width="5%"/>
	<acme:list-column code="any.banner.list.label.displayPeriodEnding" path="displayPeriodEnding" width="5%"/>
</acme:list>