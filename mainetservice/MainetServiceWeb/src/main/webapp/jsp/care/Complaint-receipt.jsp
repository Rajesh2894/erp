<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<style>
#receipt .table tr td, #receipt .table tr th {
	font-size: 1em !important;
	padding: 10px 10px !important;
	text-align: left !important
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Your awesome content goes here -->
	<div class="widget" id="receipt">
		<div class="widget-content padding">

			<form action="" method="get">
				<div id="receipt">
					<div id="background"
						style="position: absolute; z-index: 1; background: transparent; display: block; min-height: 50%; min-width: 50%; color: yellow; top: 390px; left: 250px;">
						<p id="bg-text"
							style="color: lightgrey; font-size: 50px; transform: rotate(300deg); -webkit-transform: rotate(300deg);">
							<spring:message code="care.cm"
								text="Mukhyamantri Ward Kaaryalaya" />
						</p>
					</div>
					<table class="table table-bordered margin-top-20"
						style="position: relative; z-index: 9999;">
						<tr>
							<td colspan="4"><spring:message code="care.applicantCopy"
									text="Applicant Copy" /></td>
						</tr>
						<tr>
							<td colspan="4">
								<div class="col-xs-3">
									<img width="80" src="${userSession.orgLogoPath}">
								</div>
								<div class="col-xs-6 text-center">
									<h3 class="margin-bottom-0">${complaintAcknowledgementModel.organizationName}</h3>
									<p>
										<spring:message code="care.receipt"
											text="Complaint Acknowledgement Receipt" />
									</p>
								</div>
								<div class="col-xs-3 text-right">
									<img src="${userSession.orgLogoPath}" width="80">
								</div>
							</td>
						</tr>
						<tr>
							<th align="left"><spring:message code="care.receipt.token"
									text="Token Number:" /></th>
							<td>${complaintAcknowledgementModel.tokenNumber}</td>
							<th align="left"><spring:message
									code="care.receipt.dateAndTiem" text="Date and Time:" /></th>
							<td>${complaintAcknowledgementModel.formattedDate}</td>
						</tr>
						<tr>
							<th align="left"><spring:message
									code="care.receipt.applicantname" text="Applicant Name :" />:</th>
							<td colspan="4">${complaintAcknowledgementModel.complainantName}</td>
						</tr>

						<tr>
							<th align="left"><spring:message code="care.address"
									text="Address :" />:</th>
							<td>${complaintAcknowledgementModel.address}</td>
							<th align="left"><spring:message code="care.landmark"
									text="Landmark :" />:</th>
							<td>${complaintAcknowledgementModel.landmark}</td>
						</tr>
						<tr>
							<th align="left"><spring:message
									code="care.receipt.complaintsubtype"
									text="Complaint Sub Type :" />:</th>
							<td colspan="4">
								${complaintAcknowledgementModel.complaintSubType}</td>
						</tr>
						<tr>
							<th align="left"><spring:message
									code="care.receipt.complaintDescription"
									text="Complaint Description :" /></th>
							<td colspan="4">${complaintAcknowledgementModel.description}</td>
						</tr>
						<tr>
							<td colspan="4">
								<c:if test="${command.requestType eq 'C'}">		
								<div>
									<spring:message code="care.note" text=" " />
								</div>		
								</c:if>
								 <br>
							<br>
							<br>
								<div style="padding-right:40px !important;" class="col-xs-12 text-right margin-top-30 text-bold padding-right-70">
									<spring:message code="care.signature" text="Signature" />
								</div>
								<div class="col-xs-12  text-right margin-top-10"><spring:message code="care.prabhari.word" text="Prabhari" /> ${complaintAcknowledgementModel.ward}</div>
							</td>
						</tr>
					</table>



					<table class="table table-bordered margin-top-20"
						style="position: relative; z-index: 9999;">
						<tr>
							<td colspan="4"><spring:message code="care.departmentCopy"
									text="Department Copy" /></td>
						</tr>
						<tr>
							<td colspan="4">
								<div class="col-xs-3">
									<img width="80" src="${userSession.orgLogoPath}">
								</div>
								<div class="col-xs-6 text-center">
									<h3 class="margin-bottom-0">${complaintAcknowledgementModel.organizationName}</h3>
									<p>
										<spring:message code="care.receipt"
											text="Acknowledgement Receipt" />
									</p>
								</div>
								<div class="col-xs-3 text-right">
									<img src="${userSession.orgLogoPath}" width="80">
								</div>
							</td>
						</tr>
						<tr>
							<th align="left"><spring:message code="care.receipt.token"
									text="Application Number:" /></th>
							<td>${complaintAcknowledgementModel.tokenNumber}</td>
							<th align="left"><spring:message
									code="care.receipt.dateAndTiem" text="Date and Time:" /></th>
							<td>${complaintAcknowledgementModel.formattedDate}</td>
						</tr>
						<tr>
							<th align="left"><spring:message
									code="care.receipt.applicantname" text="Applicant Name :" />:</th>
							<td colspan="4">${complaintAcknowledgementModel.complainantName}</td>
						</tr>

						<tr>
							<th align="left"><spring:message code="care.address"
									text="Address :" />:</th>
							<td>${complaintAcknowledgementModel.address}</td>
							<th align="left"><spring:message code="care.landmark"
									text="Landmark :" />:</th>
							<td>${complaintAcknowledgementModel.landmark}</td>
						</tr>
						<tr>
							<th><spring:message code="care.receipt.complaintsubtype"
									text="Application Type :" />:</th>
							<td colspan="4">
								${complaintAcknowledgementModel.complaintSubType}</td>
						</tr>
						<tr>
							<th><spring:message code="care.receipt.complaintDescription"
									text="Application Description :" /></th>
							<td colspan="4">${complaintAcknowledgementModel.description}</td>
						</tr>
						<tr>
							<td colspan="4">
							 	<c:if test="${command.requestType eq 'C'}">			 
								<div>
									<spring:message code="care.note" text=" " />
								</div>  			
								</c:if>  <br>
							<br>
							<br>
								<div style="padding-right:40px !important;" class="col-xs-12 text-right margin-top-30 text-bold padding-right-70">
									<spring:message code="care.signature" text="Signature" />
								</div>
								<div class="col-xs-12  text-right margin-top-10"><spring:message code="care.prabhari.word" text="Prabhari" /> ${complaintAcknowledgementModel.ward}</div>
							</td>
						</tr>
					</table>



				</div>

				<div class="text-center margin-top-10">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="care.receipt.print" text="Print" />
					</button>
					<c:choose>
						<c:when
							test="${userSession.employee.designation.dsgshortname eq 'OPR'}">
							<button type="button" class="btn btn-danger hidden-print"
								onclick="window.location.href='OperatorDashboardView.html'">
								<spring:message code="care.receipt.back" text="Back" />
							</button>
						</c:when>
						<c:otherwise>
							<button type="button" class="btn btn-danger hidden-print"
								onclick="window.location.href='AdminHome.html'">
								<spring:message code="care.receipt.back" text="Back" />
							</button>
						</c:otherwise>
					</c:choose>
				</div>

			</form>
		</div>
	</div>
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