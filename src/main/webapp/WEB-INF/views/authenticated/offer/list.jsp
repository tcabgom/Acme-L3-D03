
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="user-account.offer.list.label.header" path="header" width="20%"/>
	<acme:list-column code="user-account.offer.list.label.summary" path="summary" width="70%"/>
	<acme:list-column code="user-account.offer.list.label.price" path="price" width="10%"/>
</acme:list>