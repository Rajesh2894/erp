<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/siteinspection/siteinspection.js"></script>
<script src="js/cfc/scrutiny.js"></script>

<ol class="breadcrumb">
	<li><a href="index.html"><i class="fa fa-home"></i></a></li>
	<li>Print Receipt</li>
</ol>
<!-- Start Content here -->
<div class="content">

	<!-- Your awesome content goes here -->
	<div class="widget" id="receipt">
		<div class="widget-content padding">



			<div id="SiteInspectionletter" class="padding-20">
				<div class="row">
					<div class="col-xs-3 text-left">
						<img width="80" src="${userSession.getCurrent().orgLogoPath}">
					</div>
					<div class="col-xs-6 text-center">
						<h1 class="margin-bottom-0">
							${userSession.ULBName.lookUpDesc}</h1>
						<p>${inspectionLetterDto.serviceName}</p>
						<p class="margin-top-10">
							<spring:message code="siteinspection.SiteInspectionLetter" />
						</p>
					</div>

					<div class="col-xs-3 text-right">
						<img width="80" src="${userSession.getCurrent().orgLogoPath}">
					</div>

				</div>

				<div class="row">
					<div class="col-xs-6 text-left margin-top-10">
						<p>
							<spring:message code="siteinspection.LetterNo" />
							:${inspectionLetterDto.letterNo}
						</p>
					</div>
					<div class="col-xs-6 text-right margin-top-10">
						<p>${inspectionLetterDto.letterDate}</p>
					</div>
				</div>
				<p class="margin-top-10">
					<spring:message code="siteinspection.to" />
				</p>
				<div class="row">
					<div class="col-xs-10 col-xs-push-1">
						<p>${inspectionLetterDto.applicantName}</p>
						<p class="margin-top-10">
							<spring:message code="siteinspection.subject" />
							:
							<spring:message code="siteinspection.SiteInspectionLetterfor" />
							${inspectionLetterDto.serviceName}
						</p>
						<p class="margin-top-10">
							<spring:message code="siteinspection.Reference" />
							:
							<spring:message code="siteinspection.ApplicationNo" />${inspectionLetterDto.applicationNo}
							<spring:message code="siteinspection.DateApplication" />
							${inspectionLetterDto.applicationDate}
						</p>
					</div>
				</div>

				<p class="margin-top-10">
					<spring:message code="siteinspection.letterbody1" />
					${inspectionLetterDto.officerName}
					<spring:message code="siteinspection.letterbody2" />
					${inspectionLetterDto.inspectionDate}
					${inspectionLetterDto.inspectionTime}
					<spring:message code="siteinspection.letterbody3" />
				</p>
				<br>
				<br>

				<div class="row">
					<div class="col-xs-3 col-xs-push-9 text-center">
						<p>
							<spring:message code="siteinspection.authorizedsign" />
						</p>
						<img src="../../images/sign.png">
					</div>
				</div>




			</div>

			<div class="text-center">
				<button onclick="printContent1('SiteInspectionletter')"
					class="btn btn-success">
					<i class="icon-print-2"></i>
					<spring:message code="siteinspection.print" />
				</button>
				<input type="button"
					onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${applId}','${labelId}','${serviceId}')"
					class="btn btn-danger" value="Back">
			</div>

		</div>
	</div>
</div>
