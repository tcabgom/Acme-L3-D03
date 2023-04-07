
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="${readonly}">
	<acme:input-textbox  code="assistant.tutorialSession.form.label.title"           path="title"/>
	<acme:input-textbox  code="assistant.tutorialSession.form.label.tutorial"        path="tutorial"/>
	<acme:input-textarea code="assistant.tutorialSession.form.label.sessionAbstract" path="sessionAbstract"/>
	<acme:input-select   code="assistant.tutorialSession.form.label.sessionType"     path="sessionType" choices="${sessionTypes}"/>
	<acme:input-moment   code="assistant.tutorialSession.form.label.sessionStart"    path="sessionStart"/>
	<acme:input-moment   code="assistant.tutorialSession.form.label.sessionEnd"      path="sessionEnd"/>
	<acme:input-url      code="assistant.tutorialSession.form.label.moreInfo"        path="moreInfo"/>
	
	<jstl:choose>
		<jstl:when test="${!readonly}">
			<acme:input-checkbox code="assistant.tutorialSession.form.label.confirmation"   path="confirmation"/>
			<acme:submit         code="assistant.tutorialSession.form.button.create"        action="/assistant/tutorial-session/create"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:button code="assistant.tutorialSession.form.button.update" action="/assistant/tutorial-session/update"/>
			<acme:button code="assistant.tutorialSession.form.button.delete" action="/assistant/tutorial-session/delete"/>
		</jstl:otherwise>
	</jstl:choose>
	
</acme:form>