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


<script>
    function printContent(el) {
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
    }
</script>
<style>
.border-bottom-dashed {
	border-bottom: 1px dashed #000000;
}
.pdfAcknowledgementLineHeight {
	line-height: 20px;
	margin-left: 20px;
}
</style>


<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Your awesome content goes here -->
	<div class="widget" id="receipt">
		<style>
		@media print {
			.border-bottom-dashed {
				border-bottom: 1px dashed #000000;
			}
			.pdfAcknowledgementLineHeight {
				line-height: 20px;
				margin-left: 20px;
			}
		}
		</style>
		<div class="widget-content padding">
			<form action="" method="get" class="bnd-acknowledgement">
				<div>
				<div class="margin-0">
					<div class="overflow-hidden padding-vertical-10">
						<div class="col-xs-2">
						<h1>
							<img width="80" src="${userSession.orgLogoPath}">
						</h1>
						</div>
						<div class="col-xs-8 text-center">
						<c:if test="${userSession.languageId eq 1}">
							<h2 class="margin-bottom-10 padding-bottom-10">${userSession.organisation.ONlsOrgname}</h2>
							</c:if>
							<c:if test="${userSession.languageId ne 1}">
							<h2 class="margin-bottom-10 padding-bottom-10">${userSession.organisation.ONlsOrgnameMar}</h2>
							</c:if>
						</div>
						
					</div>
					<div class="col-xs-12 col-sm-12 text-center margin-bottom-10">
						<h4 class="margin-top-10">
							<span class="text-bold"><spring:message
								code="social.applicationAckheading" text="Application Form Submission Acknowledgement" ></spring:message></span>
						</h4>
					</div>
				</div>

				<div class="row margin-bottom-10 margin-left-0 margin-right-0">
					<div class="col-xs-3 col-sm-3 padding-top-5">
						<span class="text-bold"><spring:message
								code="social.applicationNo" text="Application No" /></span>
					</div>
					<div class="col-xs-3 col-sm-3">
						:<span class="margin-left-20">${command.applicationformdto.masterAppId}</span>
					</div>
					
					<div class="col-xs-3 col-sm-3 padding-top-5">
						<span class="text-bold"><spring:message
							code="social.applicantName"	text="Applicant Name" /></span>
					</div>
					<div class="col-xs-3 col-sm-3">
						:<span class="margin-left-20">${command.applicationformdto.nameofApplicant}</span>
					</div>
				</div>
				
				<div class="row margin-bottom-10 margin-left-0 margin-right-0">
					<div class="col-xs-3 col-sm-3 padding-top-5">
						<span class="text-bold"><spring:message
							code="social.sec.schemename" text="Scheme Name" /></span>
					</div>
					<div class="col-xs-3 col-sm-3">
						:<div style="margin-top: -20px;" class="margin-left-20">${command.applicationformdto.servDesc}</div>
					</div>
					
					<div class="col-xs-3 col-sm-3 padding-top-5">
						<span class="text-bold"><spring:message
							code="social.sec.subschemename" text="Sub Scheme Name" /></span>
					</div>
					<div class="col-xs-3 col-sm-3">
						:<span class="pdfAcknowledgementLineHeight">${command.applicationformdto.objOfschme}</span>
					</div>
				</div>
				
				<div class="row margin-bottom-10 margin-left-0 margin-right-0">
					<div class="col-xs-3 col-sm-3 padding-top-5">
						<span class="text-bold"><spring:message
							code="social.location" text="Location" /></span>
					</div>
					<div class="col-xs-3 col-sm-3">
						:<span class="margin-left-20">${command.applicationformdto.swdward1}</span>
					</div>
					<c:set var="date" value="<%=new java.util.Date()%>" />
					<div class="col-xs-3 col-sm-3 padding-top-5">
						<span class="text-bold"><spring:message
							code="social.dateAndTime" text="Date & Time" /></span>
					</div>
						<div class="col-xs-3 col-sm-3">
							:<span class="margin-left-20">
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							
							</span>
						</div>
					</div>
				
				<c:if test="${not empty  command.fetchDocumentList}">
					<div class="padding-horizontal-10">
						<table class="table table-striped table-condensed table-bordered">
							<tbody>

								<c:forEach items="${command.fetchDocumentList}" var="checklist"
									varStatus="lk">

									<tr>
										<td>${checklist.clmSrNo}</td>
										<td>${checklist.clmDescEngl}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>	
				</c:if>
				<br>
				<div class="text-right">
					<spring:message code="" text="Office Copy" ></spring:message>
				</div>
				</div>
				<p class="margin-top-10 border-bottom-dashed"></p>
				
				<div>
				<div class="margin-0">
					<div class="overflow-hidden padding-vertical-10">
						<div class="col-xs-2">
							<h1>
								<img width="80" src="${userSession.orgLogoPath}">
							</h1>
						</div>
						<div class="col-xs-8 text-center">
						<c:if test="${userSession.languageId eq 1}">
							<h2 class="margin-bottom-10 padding-bottom-10">${userSession.organisation.ONlsOrgname}</h2>
							</c:if>
							<c:if test="${userSession.languageId ne 1}">
							<h2 class="margin-bottom-10 padding-bottom-10">${userSession.organisation.ONlsOrgnameMar}</h2>
							</c:if>
						</div>
						
					</div>
					<div class="col-xs-12 col-sm-12 text-center margin-bottom-10">
						<h4 class="margin-top-10">
							<span class="text-bold"><spring:message
								code="social.applicationAckheading" text="Application Form Submission Acknowledgement" ></spring:message></span>
						</h4>
					</div>
				</div>

				<div class="row margin-bottom-10 margin-left-0 margin-right-0">
					<div class="col-xs-3 col-sm-3 padding-top-5">
						<span class="text-bold"><spring:message
								code="social.applicationNo" text="Application No" /></span>
					</div>
					<div class="col-xs-3 col-sm-3">
						:<span class="margin-left-20">${command.applicationformdto.masterAppId}</span>
					</div>
					
					<div class="col-xs-3 col-sm-3 padding-top-5">
						<span class="text-bold"><spring:message
							code="social.applicantName"	text="Applicant Name" /></span>
					</div>
					<div class="col-xs-3 col-sm-3">
						:<span class="margin-left-20">${command.applicationformdto.nameofApplicant}</span>
					</div>
				</div>
				
				<div class="row margin-bottom-10 margin-left-0 margin-right-0">
					<div class="col-xs-3 col-sm-3 padding-top-5">
						<span class="text-bold"><spring:message
							code="social.sec.schemename" text="Scheme Name" /></span>
					</div>
					<div class="col-xs-3 col-sm-3">
						:<div style="margin-top: -20px;" class="margin-left-20">${command.applicationformdto.servDesc}</div>
					</div>
					
					<div class="col-xs-3 col-sm-3 padding-top-5">
						<span class="text-bold"><spring:message
							code="social.sec.subschemename" text="Sub Scheme Name" /></span>
					</div>
					<div class="col-xs-3 col-sm-3">
						:<span class="pdfAcknowledgementLineHeight">${command.applicationformdto.objOfschme}</span>
					</div>
				</div>
				
				<div class="row margin-bottom-10 margin-left-0 margin-right-0">
					<div class="col-xs-3 col-sm-3 padding-top-5">
						<span class="text-bold"><spring:message
							code="social.location" text="Location" /></span>
					</div>
					<div class="col-xs-3 col-sm-3">
						:<span class="margin-left-20">
							${command.applicationformdto.swdward1}
						</span>
					</div>
					
					<div class="col-xs-3 col-sm-3 padding-top-5">
						<span class="text-bold"><spring:message
							code="social.dateAndTime" text="Date & Time" /></span>
					</div>
						<div class="col-xs-3 col-sm-3">
						:<span class="margin-left-20">
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</span>
						</div>
					</div>
				
				<c:if test="${not empty  command.fetchDocumentList}">
					<div class="padding-horizontal-10">
						<table class="table table-striped table-condensed table-bordered">
							<tbody>

								<c:forEach items="${command.fetchDocumentList}" var="checklist"
									varStatus="lk">

									<tr>
										<td>${checklist.clmSrNo}</td>
										<td>${checklist.clmDescEngl}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>	
				</c:if>
				<br>
				<div class="text-right">
					<spring:message code="" text="Applicant Copy" ></spring:message>
				</div>
				</div>
				
				<div class="text-center margin-top-20">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i>
						<spring:message code="rtgs.payment.print" text="Print"></spring:message>
					</button>
					<button type="button" class="btn btn-danger" id="back"
							onclick="window.location.href='AdminHome.html'">
						<spring:message code="social.close" text="Close"></spring:message>
						</button>
				</div>
				
			</form>
		</div>
	</div>
	<!-- End of info box -->
</div> 