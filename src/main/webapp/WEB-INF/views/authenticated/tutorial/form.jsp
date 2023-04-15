<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="true">
	<acme:input-textbox  code="user-account.tutorial.form.label.assistant"          path="assistant.supervisor"/>
	<acme:input-textbox  code="user-account.tutorial.form.label.code"               path="code"/>
	<acme:input-textbox  code="user-account.tutorial.form.label.title"              path="title"/>
	<acme:input-textarea code="user-account.tutorial.form.label.tutorialAbstract"   path="tutorialAbstract"/>
	<acme:input-textarea code="user-account.tutorial.form.label.goals"              path="goals"/>
	<acme:input-double   code="user-account.tutorial.form.label.estimatedTotalTime" path="estimatedTotalTime"/>
	<acme:input-integer  code="user-account.tutorial.form.label.numberOfSessions"   path="numberOfSessions"/>
</acme:form>