<%@page import="java.util.Date"%>
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
<script>
	function printContent(el) {
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	}
</script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget" id="receipt">
		<div class="widget-content padding">
			<div class="row">
				<div class="col-xs-3 text-left">
					<img width="80" src="${userSession.getCurrent().orgLogoPath}">
				</div>
				<div class="col-xs-6 text-center">
					<h4 class="margin-bottom-0">
						<c:if test="${userSession.languageId eq 1}">
							<spring:message code=""
								text="${userSession.organisation.ONlsOrgname}" />
						</c:if>
						<c:if test="${userSession.languageId ne 1}">
							<spring:message code=""
								text="${userSession.organisation.oNlsOrgnameMar}" />
						</c:if>
					</h4>
					<p>${command.serviceName}</p>
				</div>

				<div class="col-xs-3 text-right">
					<img width="80" src="${userSession.getCurrent().orgLogoPath}">
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-4">
					<img class="img_thumbnail" src="${command.fileDownLoadPath}">
				</div>
				<div class="row">
					<div class="col-xs-6 text-left margin-top-10">
						<p>
							<spring:message code="water.plumber.licno" />
							:${command.plumLicNo}
						</p>
						<p>
							<spring:message code="water.plumberLicense.name" />
							:${command.plumberFullName}
						</p>
						<p>
							<spring:message code="water.plumberLicense.address" />
							:${command.plumAddress}
						</p>
						<p>
							<spring:message code="water.plumberLicense.licensePeriod" />
							:${command.plumLicFrmDate}
							<spring:message code="water.plumberLicense.to" />
							${command.plumLicToDate}
						</p>
					</div>
				</div>
			</div>
			<%-- <div class="form-group clearfix">
				<p class="margin-bottom-10 margin-top-10">
					<spring:message code="water.plumberLicense.termsAndConditions" />
				</p>
				<table class="table">
					<c:forEach items="${command.tbApprejMas}" var="singleDoc"
						varStatus="count">
						<tr>
							<td width="50">${count.index+1}</td>
							<td>${singleDoc.artRemarks}</td>
						</tr>
					</c:forEach>
				</table>
			</div> --%>

			<div class="row">
				<div class="col-xs-6 text-left margin-top-10">
					<p>
						<spring:message code="water.plumberLicense.licenseHolderSign" />
					</p>
				</div>
				<div class="col-xs-3 col-xs-push-9 text-center">
					<p>
						<spring:message code="siteinspection.authorizedsign" />
					</p>
					<img src="../../images/sign.png">
				</div>
			</div>
			<%-- <c:if test="${command.serviceCode eq 'DPL' }">
				<p align="center">
					<spring:message code="" text="Duplicate Plumber License" />
				</p>
			</c:if> --%>
		</div>
		<div class="text-center margin-top-20">
			<button onclick="printContent('receipt')"
				class="btn btn-primary hidden-print">
				<i class="icon-print-2"></i> Print
			</button>
			<input type="button" onclick="window.location.href='AdminHome.html'"
				class="btn btn-default hidden-print" value="Back">
		</div>


	</div>
</div>

