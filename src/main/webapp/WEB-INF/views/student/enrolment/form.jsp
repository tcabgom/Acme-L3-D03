<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="${isFinished}">

    <jstl:choose>
        <jstl:when test="${_command == 'finalise'}">
            <acme:input-textbox  code="student.enrolment.form.label.creditCardHolder" path="creditCardHolder"/>
            <acme:input-integer  code="student.enrolment.form.label.creditCardNibble" path="creditCardNibble"/>
            <acme:input-textbox  code="student.enrolment.form.label.expirationDate" path="expirationDate"/>
            <acme:input-integer  code="student.enrolment.form.label.securityCode" path="securityCode"/>
        </jstl:when>
        <jstl:otherwise>
            <acme:input-textbox   code="student.enrolment.form.label.code" path="code"/>
            <acme:input-textbox   code="student.enrolment.form.label.motivation" path="motivation"/>
            <acme:input-textarea  code="student.enrolment.form.label.goals" path="goals"/>
        </jstl:otherwise>
    </jstl:choose>


    <jstl:choose>
        <jstl:when test="${_command == 'create'}">
            <acme:button code="student.enrolment.form.button.create"        action="/student/enrolment/create"/>
        </jstl:when>
        <jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
            <jstl:if test="${!readonly}">
                <acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
                <acme:button code="student.enrolment.form.button.finalise" action="/student/enrolment/finalise?id=${id}"/>
                <acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
            </jstl:if>
        </jstl:when>
        <jstl:otherwise>
            <acme:submit code="student.enrolment.form.button.finalise" action="/student/enrolment/finalise"/>
        </jstl:otherwise>
    </jstl:choose>

</acme:form>