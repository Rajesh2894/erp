<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script>
	(function($) {
		$(document).ready(function() {
			var example = $('#tabmenuhover').superfish({});
		});
	})(jQuery);
</script>
<style type="text/css">
textarea.description {
	border-color: Transparent;
}
</style>
</head>
<body>

	<apptags:breadcrumb></apptags:breadcrumb>
	
	 
	<div class="content">
		<div class="content-page">
			<!-- Start Content here -->
			<div class="content animated slideInDown">
				<!-- Your awesome content goes here -->
				<div class="widget invoice" id="receipt">
					<div class="widget-content padding">
						<form action="" method="get" class="form-horizontal form">
							<div class="row">
							
							<div class="col-xs-3">
									<img alt="Organisation Logo" width="80" src="${userSession.orgLogoPath}">
								</div>
								<div class="col-xs-6 text-center">
									<label class="margin-bottom-0"
										style="font-size: 24px; font-weight: 400;">${complaintAcknowledgementModel.organizationName}</label>
									<p>
										<spring:message code="care.complaint.receipt"
											text="Complaint Acknowledgement Receipt" />
									</p>
								</div>
								
								
								<div class="col-xs-3 text-right">
									<img alt="Organisation Logo" src="${userSession.orgLogoPath}" width="80">
								</div>
								

							</div>

							<div class="row margin-top-30">
								<div class="col-xs-3 text-right">
									<spring:message code="care.token" text="Token :" />
								</div>
								<div class="col-xs-3">${complaintAcknowledgementModel.tokenNumber}</div>
								<div class="col-xs-3 text-right">
									<spring:message code="care.appNo" text="Application No :" />
								</div>
								<div class="col-xs-3">${applicationId}</div>
							</div>
							<div class="row margin-top-10">

								<div class="col-xs-3 text-right">
									<spring:message code="care.dateAndTiem" text="Date & Time :" />
								</div>
								<div class="col-xs-3">${complaintAcknowledgementModel.formattedDate}</div>
								<div class="col-xs-3 text-right">
									<spring:message code="care.applicant.name"
										text="Applicant Name :" />
								</div>
								<div class="col-xs-3">${complaintAcknowledgementModel.complainantName}</div>
							</div>
							<div class="row margin-top-10">

								<div class="col-xs-3 text-right">
									<spring:message code="care.mobilenumber"
										text="Applicant Mobile Number :" />
								</div>
								<div class="col-xs-3">
									<c:out
										value="${complaintAcknowledgementModel.complainantMobileNo}"></c:out>
								</div>
								<div class="col-xs-3 text-right">
									<spring:message code="care.label.department"
										text="Department :" />
								</div>
								<div class="col-xs-3">${complaintAcknowledgementModel.department}</div>
							</div>
							<div class="row margin-top-10">

								<div class="col-xs-3 text-right">
									<spring:message code="care.complaint.subtype"
										text="Complaint Type :" />
								</div>
								<div class="col-xs-3">${complaintAcknowledgementModel.complaintSubType}</div>
								<div class="col-xs-3 text-right">
									<spring:message code="care.complaint.location"
										text="Location :" />
								</div>
								<c:choose>
									<c:when test="${kdmcEnv eq 'Y'}">
										<div class="col-xs-3">${complaintAcknowledgementModel.landmark}</div>
									</c:when>
									<c:otherwise>
										<div class="col-xs-3">${complaintAcknowledgementModel.ward}</div>
									</c:otherwise>
								</c:choose>
								


							</div>
							<div class="row margin-top-10">
								<div class="col-xs-3 text-right">
									<spring:message code="care.complaint.description"
										text="Description :" />
								</div>
								<div class="col-xs-3">
									<c:out value="${complaintAcknowledgementModel.description}" />
								</div>
								<div class="col-xs-3 text-right">
									<spring:message code="care.label.status" text="Status :" />
								</div>
								<div>
									<c:if
										test="${complaintAcknowledgementModel.status eq 'CLOSED'}">
										<span class="text-green-1"> <spring:message
												code="care.status.closed" text="Closed" />
										</span>
									</c:if>
									<c:if
										test="${complaintAcknowledgementModel.status eq 'EXPIRED'}">
										<span class="text-red-1"> <spring:message
												code="care.status.expired" text="Expired" />
										</span>
									</c:if>
									<c:if
										test="${complaintAcknowledgementModel.status eq 'PENDING'}">
										<span class="text-orange-1"> <spring:message
												code="care.status.pending" text="Pending" />
										</span>
									</c:if>
								</div>



							</div>

							<h5>
								<spring:message code="care.escalation.detail"
									text="Escalation Details" />
							</h5>
							<div class="table-responsive margin-top-10">
								<table class="table table-bordered table-condensed">
									<tr>
										<th><spring:message code="care.level" text="Level" /></th>
										<th><spring:message code="care.duration"
												text="Duration (D:H:M)" /></th>
										<th><spring:message code="care.employee.name"
												text="Employee Name" /></th>
										<th><spring:message code="care.designation"
												text="Designation" /></th>
										<th><spring:message code="care.department"
												text="Department" /></th>
										<th><spring:message code="care.email" text="Email" /></th>
									</tr>
									<c:forEach
										items="${complaintAcknowledgementModel.escalationDetailsList}"
										var="requestLists" varStatus="status">
										<tr>
											<td><c:out value="${requestLists.level}">
												</c:out></td>
											<td><c:out value="${requestLists.sla}">
												</c:out></td>
											<td><c:out value="${requestLists.empName}">
												</c:out></td>
											<td><c:out value="${requestLists.designation}">
												</c:out></td>
											<td><c:out value="${requestLists.department}">
												</c:out></td>
											<td><c:out value="${requestLists.email}">
												</c:out></td>
										</tr>
									</c:forEach>
								</table>

							</div>

							<div class="text-center margin-top-10">
								<button onclick="printContent('receipt')"
									class="btn btn-primary hidden-print">
									<i class="fa fa-print"></i>
									<spring:message code="care.print" text="Print" />
								</button>
								<button type="button" class="btn btn-danger hidden-print"
									onclick="window.location.href='CitizenHome.html'">
									<spring:message code="care.back" text="Back" />
								</button>
							</div>
						</form>
					</div>
					<!-- End of info box -->
				</div>
			</div>
		</div>
		<!--Scroll To Top-->
		<a class="tothetop" href="javascript:void(0);"><strong
			class="fa fa-angle-up"></strong><span><spring:message
					code="care.top" text="Top" /></span></a>
	</div>
	<!-- the overlay modal element -->
	<div class="md-overlay"></div>
	<!-- End of eoverlay modal -->
	<script>
		function printContent(el) {
			var restorepage = document.body.innerHTML;
			var printcontent = document.getElementById(el).innerHTML;
			document.body.innerHTML = printcontent;
			window.print();
			document.body.innerHTML = restorepage;
		}
	</script>
</body>
</html>