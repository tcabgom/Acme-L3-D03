<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
    <acme:list-column code="any.peep.list.label.title" path="title" width="40%"/>
    <acme:list-column code="any.peep.list.label.moment" path="moment" width="30%"/>
    <acme:list-column code="any.peep.list.label.nick" path="nick" width="30%"/>
</acme:list>

<acme:button code="any.peep.list.button.create" action="/any/peep/create"/>