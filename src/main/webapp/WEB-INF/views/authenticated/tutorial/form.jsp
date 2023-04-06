<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="user-account.tutorial.form.label.course.title" path="course" readonly="true"/>
	<acme:input-textbox code="user-account.tutorial.form.label.assistant" path="assistant" readonly="true"/>
	<acme:input-textbox code="user-account.tutorial.form.label.code" path="code" readonly="true"/>
	<acme:input-textbox code="user-account.tutorial.form.label.title" path="title" readonly="true"/>
	<acme:input-textbox code="user-account.tutorial.form.label.tutorialAbstract" path="tutorialAbstract" readonly="true"/>
	<acme:input-textbox code="user-account.tutorial.form.label.goals" path="goals" readonly="true"/>
</acme:form>