<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.offer.list.label.header" path="header" width="75%"/>
	<acme:list-column code="administrator.offer.list.label.price" path="price" width="25%"/>
</acme:list>

<acme:button code="administrator.offer.list.button.create" action="/administrator/offer/create"/>