<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<ul class="nav nav-tabs margin-bottom-10" id="myTab">

	<li id="tab1"><a data-toggle="tab" href="#menu1"
		onclick="openAddWorkEstimate('WorkEstimate.html','AddWorkEstimate');"><spring:message
				code="work.estimation" text="Estimation" /></a></li>
	<c:if
		test="${command.estimateTypeId eq 'U' || command.estimateTypeId eq '' || command.estimateTypeId eq null}">
		<li id="tab2"><a><spring:message
					code="work.estimate.measurement.sheet" text="Measurement Sheet" /></a></li>
	</c:if>
	<c:if
		test="${command.estimateTypeId eq 'S' || command.estimateTypeId eq 'P'}">
		<li id="tab2"><a data-toggle="tab" href="#menu2"
			onclick='measurementSheet();'><spring:message
					code="work.estimate.measurement.sheet" text="Measurement Sheet" /></a></li>
	</c:if>
	<c:choose>
		<c:when
			test="${command.estimateTypeId eq '' || command.estimateTypeId eq null}">
			<li id="tab3"><a><spring:message
						code="work.estimate.non.sor.items" text="Non-SOR Items" /> </a></li>
			<li id="tab4"><a><spring:message
						code="work.estimate.overheads" text="Overheads" /></a></li>
			<li id="tab5"><a><spring:message
						code="work.estimate.enclosures" text="Enclosures" /></a></li>
		</c:when>
		<c:otherwise>
			<li id="tab3"><a data-toggle="tab" href="#menu3"
				onclick="openAddWorkNonSorForm('WorkEstimate.html','AddWorkNonSorForm');"><spring:message
						code="work.estimate.non.sor.items" text="Non-SOR Items" /></a></li>
			<li id="tab4"><a data-toggle="tab" href="#menu4"
				onclick="openAddWorkOverheadsForm('WorkEstimate.html','AddWorkOverheadsForm');"><spring:message
						code="work.estimate.overheads" text="Overheads" /> </a></li>
			<li id="tab5"><a data-toggle="tab" href="#menu6"
				onclick="openAddWorkOverheadsForm('WorkEstimate.html','AddWorkEnclosuersForm');"><spring:message
						code="work.estimate.enclosures" text="Enclosures" /></a></li>
		</c:otherwise>
	</c:choose>
</ul>