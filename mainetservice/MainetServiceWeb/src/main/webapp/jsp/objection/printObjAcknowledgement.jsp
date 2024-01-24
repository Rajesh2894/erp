<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:useBean id="date" class="java.util.Date" />
<script type="text/javascript" src="js/water/noDuesCertificate.js"></script>
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
strong {
	font-weight: bold;
}

.container {
	width: 1050px;
	margin: auto;
	padding: 10px 14px;
	border: 1px solid #ccc;
}

.rec_top {
	width: 100%;
	margin: auto;
	padding: 5px 10px;
}

.rec_top tr td {
	padding: 0px;
	vertical-align: top;
}

.subject_text {
	width: 100%;
	margin: 40px 0px;
}

.subject_left {
	width: 50%;
	float: left;
}

.subject_right {
	width: 50%;
	float: right;
	text-align: right;
}

.rec_details {
	width: 60%;
	margin-top: 30px;
	padding: 0px;
}

.rec_details tr th {
	font-weight: bold;
	margin: 5px 10px;
	padding: 5px;
	text-align: left !important;
	font-size: 14px;
	background: none;
}

.rec_details tr td {
	margin: 5px 10px;
	padding: 5px;
	text-align: left;
	font-size: 14px;
}

.main_text {
	width: 70%;
	margin: 60px auto 0px;
	padding: 0px;
	text-align: center;
}

.sign_text {
	width: 30%;
	float: right;
	margin: 46px auto 0px;
	padding: 0px;
	text-align: center;
}

.sign_text p {
	line-height: 20px;
	font-size: 14px;
}

.bootom_text {
	width: 70%;
	margin: 30px auto 5px;
	padding: 0px;
	text-align: center;
	font-size: 8px;
}

h1 {
	font-size: 32px;
	font-weight: bold;
}

.bl_text {
	font-weight: bold;
	font-size: 18px;
}
</style>
<%--  <apptags:breadcrumb></apptags:breadcrumb> --%>
<div class="content">
	<div id="receipt">
		<div class="container">
			<div style="text-align: right; font-size: 12px">
				<spring:message code="obj.dateAndTime" text="Date & Time" />
				:
				<fmt:formatDate value="${date}" pattern="dd-MM-yyyy hh:mm:ss" />
			</div>
			<table cellpadding="0" cellspacing="0" class="rec_top">
				<tr>
					<td width="20%"><img src="${userSession.orgLogoPath}"
						alt="Organisation Logo" /></td>
					<td align="center" class="heading">
						<h1>
							<c:choose>
								<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
									<label>${userSession.getCurrent().getOrganisation().getONlsOrgname()}</label>
								</c:when>
								<c:otherwise>
									<label>${userSession.getCurrent().getOrganisation().getONlsOrgnameMar()}</label>
								</c:otherwise>
							</c:choose>
						</h1>
						<div class="bl_text">
							<span class="text-bold"><spring:message
									code="obj.acknowledgement" text="Acknowledgement" /></span>
						</div>
					</td>
					<td align="right" width="20%" style="font-size: 12px;"></td>
				</tr>
			</table>
			<div class="subject_text">
				<div class="subject_left">
					<strong> <spring:message code="obj.subject" text="Subject" />
						:
					</strong>
					<spring:message code="obj.subjectContent"
						text="Objetion Against " /> &nbsp;  ${objectionOn}
				</div>
				<div class="subject_right">
					<strong><spring:message code="obj.objNo"
							text="Objection No. :" /></strong>
					${command.objectionDetailsDto.apmApplicationId}
				</div>
				<div style="clear: both"></div>


			</div>
			<table cellpadding="0" cellspacing="0" class="rec_details">
				<tr>
					<th><spring:message code="obj.applicantName"
							text="Applicant Name" /></th>
					<td>: ${command.objectionDetailsDto.fName} &nbsp;
						${command.objectionDetailsDto.lName}</td>
				</tr>
				<tr>
					<th><spring:message code="obj.propertyNo" text="Property No." /></th>
					<td>: ${command.objectionDetailsDto.objectionReferenceNumber}</td>
				</tr>
				<tr>
					<c:choose>
						<c:when test="${not empty command.objectionDetailsDto.flatNo}">
							<th><spring:message code="obj.flatNo" text="Flat No." /></th>
							<td>: ${command.objectionDetailsDto.flatNo}</td>
						</c:when>
					</c:choose>
				</tr>
				<tr>
					<c:choose>
						<c:when test="${not empty command.objectionDetailsDto.noticeNo}">
							<th><spring:message code="obj.noticeNo" text="Notice No." /></th>
							<td>: ${command.objectionDetailsDto.noticeNo}</td>
						</c:when>
					</c:choose>
				</tr>
			</table>
			<div class="main_text">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td style="width: 30%; vertical-align: top; text-align: right;"><strong> <spring:message
									code="obj.objDescription" text="Objection Description :" /> :
							</strong>
						</td>
						<td>${command.objectionDetailsDto.objectionDetails}</td>

					</tr>
				</table>
				
			</div>
			<div class="sign_text">
				<p align="center">
					${userSession.getCurrent().getEmployee().getEmpname()} &nbsp;
					${userSession.getCurrent().getEmployee().getEmplname()}</p>
				<p align="center">
					<c:choose>
						<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
							${userSession.getCurrent().getOrganisation().getONlsOrgname()}
						</c:when>
						<c:otherwise>
							${userSession.getCurrent().getOrganisation().getONlsOrgnameMar()}
						</c:otherwise>
					</c:choose>
				</p>
			</div>
			<div style="clear: right;"></div>
			<div class="bootom_text">
				<spring:message code="obj.objPrintNote" text="Note :" />
			</div>
		</div>
	</div>
</div>
