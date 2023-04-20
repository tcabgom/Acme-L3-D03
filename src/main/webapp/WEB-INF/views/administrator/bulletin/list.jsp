<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
    <acme:list-column code="administrator.bulletin.list.label.instantiationMoment" path="instantiationMoment" width="10%"/>
    <acme:list-column code="administrator.bulletin.list.label.title" path="title" width="70%"/>
    <acme:list-column code="administrator.bulletin.list.label.critical" path="critical" width="10%"/>
</acme:list>