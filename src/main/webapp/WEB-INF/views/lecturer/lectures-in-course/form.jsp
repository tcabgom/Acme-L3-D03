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
	<acme:input-textbox code="lecturer.lecturesInCourse.form.label.lecture.title" 			path="title" readonly="true"/>	
	<acme:input-textbox code="lecturer.lecturesInCourse.form.label.lecture.lecAbstract" 	path="lecAbstract" readonly="true"/>	
	<acme:input-select code="lecturer.lecturesInCourse.form.label.course" 			path="course" choices="${courses}"/>	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'delete') && !cursos.isEmpty()}">
			<acme:submit code="lecturer.lecturesInCourse.form.button.delete" action="/lecturer/lectures-in-course/delete?lectureId=${lectureId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.lecturesInCourse.form.button.create" action="/lecturer/lectures-in-course/create?lectureId=${lectureId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>