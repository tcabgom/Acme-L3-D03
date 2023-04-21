<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="company.practicum.list.label.code" path="code" width="20%"/>
	<acme:list-column code="company.practicum.list.label.title" path="title" width="20%"/>
	<acme:list-column code="company.practicum.list.label.abstractPracticum" path="abstractPracticum" width="20%"/>
	<acme:list-column code="company.practicum.list.label.goals" path="goals" width="20%"/>
	<acme:list-column code="company.practicum.list.label.draftMode" path="draftMode"/>
</acme:list>
<acme:button code="company.practicum.form.button.create" action="/company/practicum/create"/>