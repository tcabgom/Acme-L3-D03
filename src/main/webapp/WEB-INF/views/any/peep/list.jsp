<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
    <acme:list-column code="any.peep.list.label.title" path="title" width="30%"/>
    <acme:list-column code="any.peep.list.label.moment" path="moment" width="70%"/>
</acme:list>