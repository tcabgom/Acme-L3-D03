
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="lecturer.lecture.form.label.title" 					path="title"/>	
	<acme:input-textbox code="lecturer.lecture.form.label.lecAbstract" 				path="lecAbstract"/>	
	<acme:input-double 	code="lecturer.lecture.form.label.learningTime" 			path="learningTime"/>	
	<acme:input-textbox code="lecturer.lecture.form.label.body" 					path="body"/>	
	<acme:input-select 	code="lecturer.lecture.form.label.knowledge" 				path="knowledge" choices="${knowledge}"/>	
	<acme:input-textbox code="lecturer.lecture.form.label.furtherInformationLink" 	path="furtherInformation"/>
	<acme:input-textbox code="lecturer.lecture.form.label.draftMode" 				path="draftMode" readonly="true"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == false}">	 
			<acme:button code="lecturer.lecture.list.button.add" 				action="/lecturer/lectures-in-course/create?lectureId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="lecturer.lecture.form.button.update" 			action="/lecturer/lecture/update"/>
			<acme:submit code="lecturer.lecture.form.button.delete" 			action="/lecturer/lecture/delete"/>
			<acme:submit code="lecturer.lecture.form.button.publish" 			action="/lecturer/lecture/publish"/>
			<acme:button code="lecturer.lecture.list.button.add" 				action="/lecturer/lectures-in-course/create?lectureId=${id}"/>
			<acme:button code="lecturer.lecture.list.button.deleteFromCourse" 	action="/lecturer/lectures-in-course/delete?lectureId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.lecture.form.button.create" 			action="/lecturer/lecture/create"/>
		</jstl:when>		
	</jstl:choose>
	
</acme:form>