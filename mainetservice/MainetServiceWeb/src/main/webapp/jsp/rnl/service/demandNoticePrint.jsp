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
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/mainet/validation.js"></script>
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
.widget{padding:20px !important;margin:0px 120px !important;}

</style>
<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content" id="receipt">
<div class="widget">
	<div class="header">
		<div class="row">
			<div class="col-sm-3 col-xs-3">
				<img src="${userSession.orgLogoPath}" alt="Organisation Logo"
					class="not-logged-avatar">
			</div>
				<c:choose>
					<c:when test="${command.reportLang == 'ENG'}">
						<div class="col-sm-6 col-xs-6 text-center">
							<h4>${userSession.organisation.ONlsOrgname}</h4>
							<spring:message code="rnl.notice.DemandNotice"
								text="Demand Notice" />
						</div>
						<div class="col-sm-3 col-xs-3">
							<span class="text-bold"><spring:message
									code="rnl.notice.date" text="Date:" /></span> <span class=""><fmt:formatDate
									value="${date}" pattern="dd-MM-yyyy" /></span> <br /> <span
								class="text-bold"><spring:message code="rnl.notice.time"
									text="Time:" /></span> <span class=""><fmt:formatDate
									value="${date}" pattern="hh:mm a" /></span>
						</div>
					</c:when>
					<c:otherwise>
					<div class="col-sm-6 col-xs-6 text-center">
						<h4 class="text-bold margin-top-0">
							${userSession.organisation.oNlsOrgnameMar}</h4>
						<h5>${userSession.organisation.orgAddressMar}</h5>
						<h5>${userSession.organisation.ONlsOrgname}</h5>
					</div>
					</c:otherwise>
				</c:choose>
		</div>
		<c:if test="${command.reportLang == 'MAR'}">
			<hr style="background-color: #000000; height: 1px;">
			<div class="row">
				<div class="col-sm-9 col-xs-9 text-left">
					<spring:message code="rnl.demand.report.headerLine" text="Header:" />
				</div>
				<div class="col-sm-3 col-xs-3 text-right">
					<spring:message code="rnl.demand.report.headerDate" text="Date" />
					<fmt:formatDate value="${date}" pattern="dd-MM-yyyy" />
				</div>
			</div>
		</c:if>
	</div>
	<br />

	<div class="">
		<!-- ENG -->
		<c:choose>
			<c:when test="${command.reportLang == 'ENG'}">
				<!-- English Data -->
				<p>
					To,<br /> Mr/Ms. ${command.reportDTO.tenantName}<br /> Reference
					:- Contract No. <span class="text-bold">${command.reportDTO.contractNo}</span>
					Dated <span class="text-bold">${command.reportDTO.contractDate}</span><br />

					Property Name :-<span class="text-bold">${command.reportDTO.propertyName}</span><br />

				</p>
				<br />
				<p class="margin-top-10">Sir/Madam,</p>
				<div class="margin-left-20">
					<p>This is to inform you to make payment of rent as per details
						given below:</p>
				</div>
				<br />

				<!-- arrears and current data  -->
				<table class="table table-bordered margin-top-10 margin-bottom-15">
					<thead>
						<tr>
							<th class="text-center"><spring:message code=""
									text="Tax Name" /></th>
							<th class="text-center"><spring:message code=""
									text="Arrears as on Date ${command.reportDTO.previousFinancialEndDate}" /></th>
							<th class="text-center"><spring:message code=""
									text="Current as on Date ${command.reportDTO.receiptDate}" /></th>
							<th class="text-center"><spring:message code="" text="Total" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${command.demandNoticeDTOList}" var="demand"
							varStatus="status">
							<tr>
								<td class="text-center">${demand.taxName}</td>
								<td class="text-center">${demand.arrearsAmt}</td>
								<td class="text-center">${demand.currentAmt}</td>
								<td class="text-center">${demand.totalAmt}</td>
							</tr>
						</c:forEach>
						<tr>
							<th class="text-center"><spring:message code=""
									text="Final Total" /></th>
							<td class="text-right" colspan="3">${command.reportDTO.finalTotalAmt}</td>
						</tr>
						<tr>
							<th class="text-center"><spring:message code=""
									text="In Words" /></th>
							<td class="text-center" colspan="3">${command.reportDTO.finalTotalAmtInWord}</td>
						</tr>
					</tbody>
				</table>
				<br />
				<br />
				<!-- outstanding data  -->
				<table class="table table-bordered margin-top-10 margin-bottom-15">
					<thead>
						<tr>
							<th class="text-center"><spring:message code=""
									text="Sr No." /></th>
							<th class="text-center"><spring:message
									code="rnl.notice.outDate" text="Outstanding Date" /></th>
							<th class="text-center"><spring:message
									code="rnl.notice.outAmt" text="Outstanding Amount" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${command.reportDTOList}" var="out"
							varStatus="status">
							<tr>
								<td class="text-center">${status.count}</td>
								<td class="text-center">${out.outstandingDate}</td>
								<td class="text-center">${out.outstandingAmt}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div>
					<p>Kindly arrange to make payments against the rent of this
						notice, if not already paid.</p>
				</div>
			</c:when>
			<c:otherwise>
				<!-- MARATHI DATA  -->
				<spring:message code="rnl.demand.report.prati" text="To" />
				<br />
				<p>${command.reportDTO.tenantName}</p>
				<br />
				<br />
				<spring:message code="rnl.demand.report.sub.mar" text="subject" />
				<br />
				<br />
				<spring:message code="rnl.demand.report.org.owner" /> ${command.reportDTO.propertyName} <spring:message
					code="rnl.demand.report.place" text="place" />
				<br />
				<br />
				<br />
				<br />
					<table class="table table-bordered margin-top-10 margin-bottom-15">
						<thead>
							<tr>
								<th style="width: 100px" class="text-center"><spring:message
										code="rnl.demand.report.mudat" text="Data" /></th>
								<th class="text-center"><spring:message
										code="rnl.demand.report.rakkam" text="Amount" /></th>
								<th class="text-center"><spring:message
										code="rnl.demand.report.sevakar" text="Tax" /></th>
							</tr>
						</thead>
						<tbody>
							<!-- Arrears -->
							<tr>
								<td class="text-center">${command.reportDTO.previousFinancialEndDate}
									<spring:message code="rnl.demand.report.date" text="Date" />
								</td>
								<td class="text-center">${command.demandNoticeDTOList[0].arrearsAmt}</td>
								<td class="text-center">${command.demandNoticeDTOList[1].arrearsAmt * 2}</td>
							</tr>
							<!-- Current -->
							<tr>
								<td class="text-center">${command.reportDTO.financialYear}</td>
								<td class="text-center">${command.demandNoticeDTOList[0].currentAmt}</td>
								<td class="text-center">${command.demandNoticeDTOList[1].currentAmt * 2}</td>
							</tr>
							<tr>
								<td class="text-center" ></td>
								<td class="text-center" ></td>
								<td class="text-center" style="height:40px"></td>
							</tr>
							<!-- Total -->
							<tr>
								<th class="text-right"><spring:message
										code="rnl.demand.report.total" text="Total" /></th>
								<td class="text-center" colspan="2">${command.reportDTO.finalTotalAmt}</td>
							</tr>
							<tr>
								<th class="text-right"><spring:message
										code="rnl.demand.report.totalInWord" text="In Words" /></th>
								<td class="text-center" colspan="2"> ${command.reportDTO.finalTotalAmtInWord}</div></td>
							</tr>

						</tbody>
					</table>
					<br />
					<spring:message code="rnl.demand.report.lastStmt" text="notice" />
					<p style="text-align: right">
						<spring:message code="rnl.demand.report.ayukt" text="stamp" />
						<br />
						<spring:message code="rnl.demand.report.malmatta" text="stamp" />
						<br />
						<spring:message code="rnl.demand.report.thane" text="stamp" />
						<br />
					</p>
				</div>
			</c:otherwise>
		</c:choose>
	
	<div class="text-center margin-top-20">
		<button onclick="printContent('receipt')"
			class="btn btn-primary hidden-print">
			<i class="icon-print-2"></i>
			<spring:message code="adh.print" text="Print"></spring:message>
		</button>
		<button type="button" class="btn btn-danger hidden-print"
			onclick="window.close();">
			<spring:message code="adh.close" text="Close"></spring:message>
		</button>
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->
</div>