
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="lecturer.course.form.label.code" path="code" readonly="false"/>	
	<acme:input-textbox code="lecturer.course.form.label.title" path="title" readonly="false"/>	
	<acme:input-textbox code="lecturer.course.form.label.courseAbstract" path="courseAbstract" readonly="false"/>	
	<acme:input-money code="lecturer.course.form.label.retailPrice" path="retailPrice" readonly="false"/>	
	<acme:input-url code="lecturer.course.form.label.furtherInformation" path="furtherInformation" readonly="false"/>	
	<acme:input-textbox code="lecturer.course.form.label.activityType" path="activityType" readonly="true"/>
	
	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="lecturer.course.lectures" action="/lecturer/lecture/list?courseId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="lecturer.course.lectures" action="/lecturer/lecture/list?lectureId=${id}"/>
			<acme:submit code="lecturer.course.form.button.update" action="/lecturer/course/update"/>
			<acme:submit code="lecturer.course.form.button.delete" action="/lecturer/course/delete"/>
			<acme:submit code="lecturer.course.form.button.publish" action="/lecturer/course/publish"/> 
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.course.form.button.create" action="/lecturer/course/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
