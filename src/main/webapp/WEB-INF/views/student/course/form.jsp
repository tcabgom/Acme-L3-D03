<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	
	<acme:input-textbox   code="student.course.form.label.title" path="title" readonly="true"/>
	<acme:input-textarea  code="student.course.form.label.courseAbstract" path="courseAbstract"/>
	<acme:input-textbox   code="student.course.form.label.code" path="code"/>
	<acme:input-money     code="student.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-textbox   code="student.course.form.label.lecturer" path="lecturer"/>
	<acme:input-url  	  code="student.course.form.label.furtherInformation" path="furtherInformation"/>
	<acme:hidden-data path="draftMode"/>

	<acme:button code="student.course.form.label.showLectures" action="/student/lecture/list?courseId=${courseId}"/>
</acme:form>