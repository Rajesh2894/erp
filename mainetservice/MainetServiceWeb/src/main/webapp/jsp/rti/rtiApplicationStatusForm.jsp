<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/rti/rtiStatusForm.js"></script>
<style>
	.logo-left {
		width: 30%;
	}
	.logo-right {
		width: 45%;
		float: right;
	}
</style>


<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Your awesome content goes here -->
	<div class="widget" id="receipt">
		<div class="widget-content padding">
			<form action="" method="get">
				<div class="row">
					<div class="col-xs-3 col-sm-3">
						<c:choose>
							<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
								<img src="${userSession.orgLogoPath}" alt="${userSession.organisation.ONlsOrgname}"
								class="logo-left img-responsive"/>
							</c:when>
							<c:otherwise>
								<img src="${userSession.orgLogoPath}" alt="${userSession.organisation.oNlsOrgnameMar}"
								class="logo-left img-responsive"/>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-xs-6 col-sm-6 text-center">
						<c:choose>
							<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
								<h3 class="margin-bottom-0">${userSession.organisation.ONlsOrgname}</h3>
							</c:when>
							<c:otherwise>
								<h3 class="margin-bottom-0">${userSession.organisation.oNlsOrgnameMar}<h3>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-xs-3 col-sm-3">
						<img src="./assets/img/RTI-Logo.jpg" alt="RTI" class="logo-right img-responsive"/>
					</div>
				</div>
				
				<div class="row">
					<div class="col-xs-12 col-sm-12 text-center">
						<h4 class="margin-bottom-0 margin-top-0 padding-top-30">
							<spring:message code="rti.receipt" text="" />
						</h4>
					</div>
				</div>
				<div class="row margin-top-30">
					<div class="col-xs-3 text-right">
						<spring:message code="rti.rtiNumber" text="RTI Number:" />
					</div>
					<div class="col-xs-3">${command.reqDTO.rtiNo}</div>
					<div class="col-xs-3 text-right">
						<spring:message code="care.receipt.dateAndTiem"
							text="Date & Time :" />
					</div>
					<div class="col-xs-3">${command.reqDTO.dateDesc}
						<fmt:formatDate value="<%=new java.util.Date()%>"
							pattern="hh:mm a" />
					</div>
				</div>
				<div class="row margin-top-10">
					<div class="col-xs-3 text-right">
						<spring:message code="rti.applicantName" text="Applicant Name :" />
					</div>
					<div class="col-xs-3">${command.reqDTO.applicantName}</div>
					<div class="col-xs-3 text-right">
						<spring:message code="rti.applicationNumber" text="Application Number:" />
					</div>
					<div class="col-xs-3">${command.reqDTO.apmApplicationId}</div>
				</div>
				<div class="row margin-top-10">
					<div class="col-xs-3 text-right">
						<spring:message code="rti.dept" text="Department :" />
					</div>
					<div class="col-xs-3">${command.reqDTO.departmentName}</div>
					<div class="col-xs-3 text-right">
						<spring:message code="rti.applicantNo"
							text="Applicant Mobile No :" />
					</div>
					<div class="col-xs-3">${command.cfcAddressEntity.apaMobilno}</div>
				</div>
				<div class="row margin-top-10">
					<div class="col-xs-3 text-right">
						<spring:message code="rti.rtiStatus1" text="Status :" />
					</div>
					<div class="col-xs-3">
						<c:if test="${command.reqDTO.status eq 'PENDING'}">
							<span class="text-orange-5"> <spring:message
									code="care.status.pending" text="Pending" />
							</span>
						</c:if>
					</div>
					<div class="col-xs-3 text-right">
						<spring:message code="rti.address" text="Address :" />
					</div>
					<div class="col-xs-3">${command.cfcAddressEntity.apaAreanm}</div>
				</div>
				<%-- <div class="row margin-top-10">
          	<div class="col-xs-3 text-right"><spring:message code="care.receipt.location" text="Location :"/></div>
            <div class="col-xs-6">${rtiAcknowledgementDto.ward}</div>
          </div> --%>
				<div class="row margin-top-10">
					<%-- <div class="col-xs-3 text-right"><spring:message code="care.receipt.description" text="Description :"/></div>
            <div class="col-xs-3">${command.reqDTO.rtiDetails}</div> --%>
					
				</div>

				<div class="text-center margin-top-10">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="care.receipt.print" text="Print" />
					</button>
					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="care.receipt.back" text="Back" />
					</button>
				</div>

			</form>
		</div>
	</div>
	<!-- End of info box -->
</div>


<script>
	function printContent(el) {
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	}
	$("html, body").animate({
		scrollTop : 0
	}, "slow");
</script>


<%-- <div class="col-xs-3 text-right"><spring:message code="rti.rtiStatus1" text="Status :"/></div>
          	 <div class="col-xs-3">
          		<c:if test="${rtiAcknowledgementDto.status eq 'CLOSED'}">
					 <span class="text-green-1"> 
							<spring:message code="care.status.closed" text="Closed"/>
					</span> 
					</c:if>
					<c:if test="${rtiAcknowledgementDto.status eq 'EXPIRED'}">
					 <span class="text-red-1"> 
							<spring:message code="care.status.expired"  text="Expired"/>
					</span> 
					</c:if>
					<c:if test="${rtiAcknowledgementDto.status eq 'PENDING'}">
					 <span class="text-orange-5"> 
							<spring:message code="care.status.pending"  text="Pending"/>
					</span> 
				</c:if>	
          	</div> --%>