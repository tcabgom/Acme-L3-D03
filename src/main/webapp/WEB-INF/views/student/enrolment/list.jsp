<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
    <acme:list-column code="student.course.list.label.code" path="code" width="10%"/>
    <acme:list-column code="student.course.list.label.motivation" path="motivation" width="70%"/>
</acme:list>