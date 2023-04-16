<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
    <acme:list-column code="student.lecture.list.label.title" path="title" width="10%"/>
    <acme:list-column code="student.lecture.list.label.learningTime" path="learningTime" width="70%"/>
    <acme:list-column code="student.lecture.list.label.knowledge" path="knowledge" width="70%"/>
</acme:list>