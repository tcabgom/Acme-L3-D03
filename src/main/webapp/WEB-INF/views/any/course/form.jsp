<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.course.form.label.code" path="code" readonly="true"/>	
	<acme:input-textbox code="any.course.form.label.title" path="title" readonly="true"/>	
	<acme:input-textarea code="any.course.form.label.courseAbstract" path="courseAbstract" readonly="true"/>	
	<acme:input-money code="any.course.form.label.retailPrice" path="retailPrice" readonly="true"/>	
	<acme:input-url code="any.course.form.label.furtherInformation" path="furtherInformation" readonly="true"/>	
	<acme:input-checkbox code="any.course.form.label.activityType" path="activityType" readonly="true"/>

	<acme:button code="any.course.form.button.tutorial" action="/authenticated/tutorial/list?tutorialId=${id}"/>
</acme:form>