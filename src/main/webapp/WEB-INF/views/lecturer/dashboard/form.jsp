<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2><acme:message code="assistant.dashboard.form.title.tutorial"/></h2>

<table class="table table-sm">

	<tr>
        <th scope="row"><acme:message code="assistant.dashboard.form.label.tutorial.count"/></th>
        <td><acme:print value="${assistantTutorials.getCount()}"/></td>
    </tr>
    
    <tr>
    	<th scope="row"><acme:message code="assistant.dashboard.form.label.tutorial.maximum"/></th>
        <td><acme:print value="${assistantTutorials.getMaximum()}"/></td>
    </tr>
    
   	<tr>
    	<th scope="row"><acme:message code="assistant.dashboard.form.label.tutorial.minimum"/></th>
        <td><acme:print value="${assistantTutorials.getMinimum()}"/></td>
    </tr>
    
    <tr>
    	<th scope="row"><acme:message code="assistant.dashboard.form.label.tutorial.average"/></th>
        <td><acme:print value="${assistantTutorials.getAverage()}"/></td>
    </tr>
    
    <tr>
    	<th scope="row"><acme:message code="assistant.dashboard.form.label.tutorial.deviation"/></th>
        <td><acme:print value="${assistantTutorials.getStdDeviation()}"/></td>
    </tr>

</table>

<h2><acme:message code="assistant.dashboard.form.title.session"/></h2>

<table class="table table-sm">

    <tr>
        <th scope="row"><acme:message code="assistant.dashboard.form.label.session.count"/></th>
        <td><acme:print value="${assistantSession.getCount()}"/></td>
    </tr>
    
    <tr>
    	<th scope="row"><acme:message code="assistant.dashboard.form.label.session.maximum"/></th>
        <td><acme:print value="${assistantSession.getMaximum()}"/></td>
    </tr>
    
   	<tr>
    	<th scope="row"><acme:message code="assistant.dashboard.form.label.session.minimum"/></th>
        <td><acme:print value="${assistantSession.getMinimum()}"/></td>
    </tr>
    
    <tr>
    	<th scope="row"><acme:message code="assistant.dashboard.form.label.session.average"/></th>
        <td><acme:print value="${assistantSession.getAverage()}"/></td>
    </tr>
    
    <tr>
    	<th scope="row"><acme:message code="assistant.dashboard.form.label.session.deviation"/></th>
        <td><acme:print value="${assistantSession.getStdDeviation()}"/></td>
    </tr>
    
</table>

<h2><acme:message code="assistant.dashboard.form.title.session-type"/></h2>

<div>
	<canvas id="canvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"HANDS_ON", "THEORY", "BALANCED"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${totalNumberOfHandsOnSessions}"/>, 
						<jstl:out value="${totalNumberOfTheorySessions}"/>, 
						<jstl:out value="${totalNumberOfBalancedSessions}"/>
					]
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>

<acme:return/>
