<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<ul class="nav nav-tabs margin-bottom-10" id="myTab">
	<c:if test="${command.flag ne null || command.workRaCode ne null }">
		<li id="tab1"><a href="#menu1"
			onclick="callServiceSS('${command.workRaCode}',${command.serviceId},${command.actualTaskId},${command.workFlowId},'${command.mbTaskName}');"><spring:message
					code="works.MBapproval.inspection" text="Inspection Details" /></a></li>

	</c:if>
	<c:choose>
		<c:when test="${command.flag ne null}">
			<li id="tab2"><a href="#menu2" onclick='openUserActionForm()'><spring:message
						code="works.MBapproval.userAction" text="User Action Approval" /></a></li>
		</c:when>
		<c:otherwise>
			<li id="tab2"><a href="#menu2" onclick=''><spring:message
						code="works.MBapproval.userAction" text="User Action Approval" /></a></li>
		</c:otherwise>
	</c:choose>


</ul>