<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript"
	src="js/solid_waste_management/constructNDemoWasteCollector.js"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text=" " />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="widget invoice" id="receipt">
				<div class="widget-content padding">
					<div id="receipt">
						<form:form id="constructDemolitionWasteCollector"
							commandName="command" name="constructDemolitionWasteCollector"
							class="form-horizontal" action="WasteCollector.html"
							method="post">
									<div class="col-xs-2">
							<h1>
								<img width="80" src="${userSession.orgLogoPath}">
							</h1>
						</div>
						<div class="col-xs-7 text-center">
							<h3 class=" margin-bottom-0 margin-top-0 text-bold">
								<c:if test="${userSession.languageId eq 1}">
									 ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
         							 ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
								</c:if> 							
							</h3>
						</div>
							<div class="col-xs-3">
							<p>
								<spring:message code="solid.waste.date" text="Date" />
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
								<spring:message code="solid.waste.time" text="Time" />
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
							<div class="row">
								<div class="col-xs-12 text-center">
									<h3 class="margin-bottom-0">
										<spring:message code="construct.demolition.approval.heading"
											text="Intimation for collection of debris under construction and demolition waste management rules 2016" />
									</h3>
								</div>
						
							</div>
							
							<div id="export-excel">
								<div class="row margin-top-30 clear">
									<div class="col-sm-12 text-justify">
										<p class="padding-top-20">
											<spring:message code="construct.demolition.approval.letter.part1"
												text="With respect to your Application Number" />
											&nbsp;${command.collectorReqDTO.collectorDTO.applicationId}&nbsp;
											<spring:message code="construct.demolition.approval.letter.part2" text="dated on" />
											&nbsp;
											<fmt:formatDate
												value="${command.collectorReqDTO.collectorDTO.collectionDate}"
												pattern="dd/MM/yyyy" />
											
											<spring:message code="construct.demolition.approval.letter.part3"
												text=", permission is hereby granted in favour of," />
											
											<spring:eval
												expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getCPDDescription(command.collectorReqDTO.applicantDetailDto.applicantTitle,'')" />
											&nbsp;${command.collectorReqDTO.applicantDetailDto.applicantFirstName}&nbsp;${command.collectorReqDTO.applicantDetailDto.applicantMiddleName}&nbsp;${command.collectorReqDTO.applicantDetailDto.applicantLastName}&nbsp;
											<spring:message code="construct.demolition.approval.letter.part4"
												text=" for debris collection under construction and demolition waste management rules 2016 in respect of " />
											&nbsp;${command.collectorReqDTO.applicantDetailDto.areaName}&nbsp;
											<spring:message code="construct.demolition.approval.letter.part5" text="of" />
											&nbsp; ${ userSession.getCurrent().organisation.ONlsOrgname}&nbsp;
											<%-- <spring:message code=""
												text=" Municipal Corporations/ Municipal Council/ Nagar Panchayats. subject to the following conditions/ restrictions." /> --%>
											<spring:message code="construct.demolition.approval.letter.part6"
												text=" subject to the following conditions/ restrictions." />
										</p>
										<p class="margin-top-10">
											<spring:message code="construct.demolition.approval.letter.addressOfsite" text="Address of Site:" />&nbsp;${command.collectorReqDTO.collectorDTO.locAddress}
										</p>
										<p>
											<spring:message code="construct.demolition.approval.letter.typeOfVehicale" text="Type of Vehicle:" />&nbsp;
											<spring:eval
												expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getCPDDescription(command.collectorReqDTO.collectorDTO.vehicleType,'')" />
										</p>
										<p>
											<spring:message code="construct.demolition.approval.letter.vehicleNo" text="Vehicle Number:" />&nbsp;${command.vehicleNumber}
										</p>
										<p>
											<spring:message code="construct.demolition.approval.letter.driverName" text="Driver Name:" />&nbsp;${command.collectorReqDTO.collectorDTO.empName}

										</p>
										<p>
											<spring:message code="construct.demolition.approval.letter.dateofPickup" text="Expected Date of PickUp:" /> &nbsp;	
											<fmt:formatDate value="${command.collectorReqDTO.collectorDTO.pickUpDate}"	pattern="dd/MM/yyyy" />

										</p>
										<p class="margin-top-10"></p>
										<br>

									</div>

								</div>
								<div class="clear"></div>
								<div class="row">
									<div class="col-xs-4 col-xs-push-8  margin-top-50">
										
											<p>______________________</p>
											
										
										<br>
											<spring:message code="construct.demolition.approval.letter.signature" text="Authorised Signatory" />
										<p>
											
											${userSession.getCurrent().organisation.ONlsOrgname}
										</p>
									</div></div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
			<div class="text-center padding-top-10">
				<button onclick="PrintDiv('${cheque.dishonor.register}');"
					class="btn btn-success hidden-print" type="button">
					<i class="fa fa-print"></i>
					<spring:message code="solid.waste.print" />
				</button>
				<apptags:backButton url="AdminHome.html"></apptags:backButton>
			</div>

		</div>
	</div>
</div>
