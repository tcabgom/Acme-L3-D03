<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<acme:input-textbox  code="assistant.tutorial.form.label.title"              path="title"/>
	<acme:input-select   code="assistant.tutorial.form.label.course"             path="course"             choices="${courses}"/>
	<acme:input-textbox  code="assistant.tutorial.form.label.code"               path="code"/>
	<acme:input-textarea code="assistant.tutorial.form.label.tutorialAbstract"   path="tutorialAbstract"/>
	<acme:input-textarea code="assistant.tutorial.form.label.goals"              path="goals"/>
	<acme:input-double   code="assistant.tutorial.form.label.estimatedTotalTime" path="estimatedTotalTime" readonly="true"/>
	<acme:input-integer  code="assistant.tutorial.form.label.numberOfSessions"    path="numberOfSessions" readonly="true"/>
	<acme:input-checkbox code="assistant.tutorial.form.label.draftMode"          path="draftMode" readonly="true"/>

	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.tutorial.form.button.create" action="/assistant/tutorial/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<acme:submit code="assistant.tutorial.form.button.update"   action="/assistant/tutorial/update"/>
			<acme:submit code="assistant.tutorial.form.button.delete"   action="/assistant/tutorial/delete"/>
			<acme:button code="assistant.tutorial.form.button.sessions" action="/assistant/tutorial-session/list?masterId=${id}"/>
			<jstl:if test="${draftMode}">
				<acme:submit code="assistant.tutorial.form.button.publish"  action="/assistant/tutorial/publish"/>
			</jstl:if>
		</jstl:when>
	</jstl:choose>

</acme:form>