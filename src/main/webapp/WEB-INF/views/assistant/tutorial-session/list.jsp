
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="assistant.tutorialSession.list.label.title"       path="title" width="40%"/>
	<acme:list-column code="assistant.tutorialSession.list.label.tutorial"    path="tutorial.title" width="40%"/>
	<acme:list-column code="assistant.tutorialSession.list.label.sessionType" path="sessionType" width="20%"/>
</acme:list>

<acme:button code="assistant.tutorialSession.list.button.create" action="/assistant/tutorial-session/create"/>