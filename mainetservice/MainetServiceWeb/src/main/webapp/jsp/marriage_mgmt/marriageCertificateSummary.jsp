<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/marriage_mgmt/marriageCertificate.js"></script>
<script type="text/javascript" src="js/common/dsc.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="mrm.marriage.summary"
					text="Marriage Certificate Summary" />
			</h2>
			<apptags:helpDoc url="MarriageCertificateGeneration.html"></apptags:helpDoc>
		</div>
		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="MarriageCertificateGeneration.html"
				cssClass="form-horizontal" id="marriageCertificate"
				name="marriageCertificate">
				<!-- Start Validation include tag -->
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<div class="form-group">
					<apptags:date fieldclass="datepicker"
						labelCode="mrm.marriage.marriageDate"
						datePath="marriageDTO.marDate"></apptags:date>

					<apptags:input labelCode="mrm.marriage.regNo"
						path="marriageDTO.serialNo"></apptags:input>

				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="location"> <spring:message
							code="mrm.marriage.husbandName" /></label>

					<div class="col-sm-4 ">
						<form:select path="" id="husbandId"
							cssClass="form-control chosen-select-no-results"
							class="form-control mandColorClass" data-rule-required="true">
							<form:option value="">Select</form:option>
							<c:forEach items="${command.husbandList}" var="husband">
								<form:option value="${husband.husbandId}">${husband.firstNameEng} ${husband.lastNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label" for="location"> <spring:message
							code="mrm.marriage.wifeName" /></label>

					<div class="col-sm-4 ">
						<form:select path="" id="wifeId"
							cssClass="form-control chosen-select-no-results"
							class="form-control mandColorClass" data-rule-required="true">
							<form:option value="">Select</form:option>
							<c:forEach items="${command.wifeList}" var="wife">
								<form:option value="${wife.wifeId}">${wife.firstNameEng} ${wife.lastNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center clear padding-10">
					<button class="btn btn-blue-2  search" id="searchMRMCertificate"
						type="button">
						<i class="button-input"></i>
						<spring:message code="mrm.button.search" />
					</button>
					<button type="button"
						onclick="window.location.href='MarriageCertificateGeneration.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="mrm.button.reset" text="Reset" />
					</button>
				</div>
				<div>
				<select name="ddl1" id="ddl1">
				<option value="0">Select Certificate</option>
				</select>
				</div>
				<div class="table-responsive clear">
				<spring:eval
				expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp('C3','CRD',${UserSession.organisation}).getOtherField()" var="otherField"/>
					<table class="table table-striped table-bordered"
						id="marriageCertificateTB">
						<thead>
							<tr>
								<th width="3%" align="center"><spring:message
										code="mrm.srno" text="Sr.No" /></th>
								<th><spring:message code="mrm.marriage.marriageDate"
										text="Marriage Date" /></th>
								<th><spring:message code="mrm.marriage.regNo"
										text="Registration No" /></th>
								<th><spring:message code="mrm.marriage.husbandName"
										text="Husband Name" /></th>
								<th><spring:message code="mrm.marriage.wifeName"
										text="Wife Name" /></th>
								<th><spring:message code="mrm.action" text="Action" /></th>
							</tr>
						<tbody>
							<c:forEach items="${command.marriageDTOs}" var="summaryDTO"
								varStatus="index">
								<tr>
									<td class="text-center">${index.count}</td>
									<td align="center"><fmt:formatDate
											value="${summaryDTO.marDate}" pattern="dd-MM-yyyy" /></td>
									<td align="center">${summaryDTO.serialNo}</td>
									<td align="center">${summaryDTO.husbandDTO.firstNameEng}
										${summaryDTO.husbandDTO.lastNameEng}</td>
									<td align="center">${summaryDTO.wifeDTO.firstNameEng}
										${summaryDTO.wifeDTO.lastNameEng}</td>
									<td class="text-center"><c:if
											test="${not empty summaryDTO.serialNo && summaryDTO.serialNo ne 'NA' }">
											<button type="button" class="btn btn-danger btn-sm btn-sm"
												onClick="printCertificate(${summaryDTO.marId})"
												title="<spring:message code="" text="Print Certificate"></spring:message>">
												<i class="fa fa-print" aria-hidden="true"></i>
											</button>
											<button type="button" class="btn btn-primary btn-sm"
											 onclick="signCertificate('${summaryDTO.marId}','${summaryDTO.marId}',${summaryDTO.applicationId},'MarriageCertificateGeneration.html','${otherField}')">
											 <spring:message code="property.sign.certificate" text="Sign Certificate" />
											</button>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
		</div>
		</form:form>
		<!-- End Form -->
	</div>
	<!-- End Widget Content here -->
</div>
<!-- End Widget  here -->
</div>
<!-- End of Content -->