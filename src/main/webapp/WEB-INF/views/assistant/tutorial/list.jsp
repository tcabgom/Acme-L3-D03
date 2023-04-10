<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="assistant.tutorial.list.label.title"            path="title"            width="45%"/>
	<acme:list-column code="assistant.tutorial.list.label.course"           path="course.title"     width="45%"/>
	<acme:list-column code="assistant.tutorial.list.label.numberOfSessions" path="numberOfSessions" width="7.5%"/>
	<acme:list-column code="assistant.tutorial.list.label.draftMode"        path="draftMode"        width="7.5%"/>
</acme:list>

<acme:button code="assistant.tutorial.list.button.create" action="/assistant/tutorial/create"/>