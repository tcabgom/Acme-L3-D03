<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="true">
	<acme:input-textbox  code="user-account.tutorial.form.label.course"           path="course.title"/>
	<acme:input-textbox  code="user-account.tutorial.form.label.assistant"        path="assistant.userAccount.nickname"/>
	<acme:input-textbox  code="user-account.tutorial.form.label.code"             path="code"/>
	<acme:input-textbox  code="user-account.tutorial.form.label.title"            path="title"/>
	<acme:input-textarea code="user-account.tutorial.form.label.tutorialAbstract" path="tutorialAbstract"/>
	<acme:input-textarea code="user-account.tutorial.form.label.goals"            path="goals"/>
</acme:form>