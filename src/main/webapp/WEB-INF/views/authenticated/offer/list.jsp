
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.offer.listAll.label.header" path="header" width="20%"/>
	<acme:list-column code="authenticated.offer.listAll.label.summary" path="summary" width="70%"/>
	<acme:list-column code="authenticated.offer.listAll.label.price" path="price" width="10%"/>
</acme:list>