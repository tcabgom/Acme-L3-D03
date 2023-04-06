
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="user-account.offer.list.label.header" path="header" width="75%"/>
	<acme:list-column code="user-account.offer.list.label.price" path="price" width="25%"/>
</acme:list>