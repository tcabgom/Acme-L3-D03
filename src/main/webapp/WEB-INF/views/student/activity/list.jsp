<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
    <acme:list-column code="student.activity.list.label.title" path="title" width="10%"/>
    <acme:list-column code="student.activity.list.label.type" path="type" width="70%"/>
</acme:list>

<acme:button code="student.activity.list.button.create" action="/student/activity/create?enrolmentId=${enrolmentId}"/>