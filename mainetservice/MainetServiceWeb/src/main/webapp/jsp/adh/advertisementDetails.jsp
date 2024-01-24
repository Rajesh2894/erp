<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/adh/advertisementDetails.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="advertisement.tracking.table.title"
					text="Advertisement Tracking"></spring:message>
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="AdvertisementDetails.html"
				cssClass="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp"></jsp:include>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="advertiser.label.name" /></label>

					<div class="col-sm-4">
						<form:select path="advertiserMasterDto.agencyName" id="agencyName"
							class="chosen-select-no-results" data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.advertiserMasterDtoList}"
								var="masterDto">
								<form:option value="${masterDto.agencyName}">${masterDto.agencyName}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="advertiser.label.license.number" /></label>
					<div class="col-sm-4">
						<form:select path="advertiserMasterDto.agencyLicNo"
							id="agencyLicNo" class="chosen-select-no-results"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.advertiserMasterDtoList}"
								var="masterDto">
								<form:option value="${masterDto.agencyLicNo}">${masterDto.agencyLicNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="advertiser.label.mode" /></label>

					<div class="col-sm-4">
						<form:select path="advertiserMasterDto.agencyName" id="agencyName"
							class="chosen-select-no-results" data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.advertiserMasterDtoList}"
								var="masterDto">
								<form:option value="${masterDto.agencyName}">${masterDto.agencyName}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="advertiser.label.location" /></label>
					<div class="col-sm-4">
						<form:select path="hoardingMasterDto.locationId" id="locationId"
							class="chosen-select-no-results" data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.locationList}" var="location">
								<form:option value="${location.locId}">${location.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="advertiser.label.license.status" /></label>
					<div class="col-sm-4">
						<form:select path="advertiserMasterDto.agencyStatus"
							id="agencyStatus" class="chosen-select-no-results"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" />
							</form:option>
							<form:option value="A">
								<spring:message code="adh.active" />
							</form:option>
							<form:option value="I">
								<spring:message code="adh.inactive" />
							</form:option>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="adh.financial.year" /></label>

					<div class="col-sm-4">
						<form:select path="" id="agencyName"
							class="chosen-select-no-results" data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.listOfinalcialyear}"
								var="finalcialYear">
								<form:option value="${finalcialYear.key}"
									code="${finalcialYear.key}">${finalcialYear.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center margin-bottom-10">
					<button type="button" class="btn btn-blue-2" id="searchHoarding"
						title="Search" onclick="">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='AdvertisementDetails.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.reset" text="Reset"></spring:message>
					</button>
				</div>

				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="hoardingTable">
							<thead>
								<tr>
									<th><spring:message code="advertiser.label.license.number" text="License/Permit Number"></spring:message></th>
									<th><spring:message code="advertiser.label.name" text="Name Of The Advertiser"></spring:message></th>
									<th><spring:message code="advertiser.label.mode" text="Advertisement Mode"></spring:message></th>
									<th><spring:message code="advertiser.label.location" text="Location"></spring:message></th>
									<th><spring:message code="advertiser.label.license.status" text="License/Permit Status"></spring:message></th>
									<th><spring:message code="adh.label.renewal.due.date" text="Renewal Due Date"></spring:message></th>
									<th><spring:message code="adh.label.closure.date" text="Closure Date"></spring:message></th>
									<th><spring:message code="adh.label.balance.amount" text="SD Balance Amount"></spring:message></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.advertiserMasterDtoList }"
									var="list">
									<tr>
										<td align="center">${list.agencyLicNo}</td>
										<td align="center">${list.agencyName}</td>
										<td align="center"></td>
										<td align="center">${list.agencyName}</td>
										<td align="center">${list.agencyStatus}</td>
										<td align="center">${list.agencyName}</td>
										<td align="center">${list.agencyLicToDate}</td>
										<td align="center">${list.agencyName}</td>
										
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</form:form>
		</div>

	</div>
</div>