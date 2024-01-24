<!-- Start JSP Necessary Tags -->
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
<script type="text/javascript" src="js/adh/hoardingMaster.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="hoarding.master.table.title"
					text="Hoarding Master"></spring:message>
			</h2>
		</div>
		<div class="widget-content padding">

			<form:form action="HoardingMaster.html" name="hoardingMaster"
				id="hoardingMaster" cssClass="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp"></jsp:include>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label"> <spring:message
							code="hoarding.label.number" text="Hoarding Number"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select path="hoardingMasterDto.hoardingNumber"
							id="hoardingNumber" class="chosen-select-no-results"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.hoardingMasterDtoList }"
								var="masterDto">
								<form:option value="${masterDto.hoardingNumber }">${masterDto.hoardingNumber }</form:option>
							</c:forEach>
						</form:select>
					</div>
					<!-- <This is for Non Hierarchical> -->
					<label class="col-sm-2 control-label"><spring:message
							code="hoarding.label.hoarding.status" text="Hoarding Status" /></label>
					<c:set var="baseLookupCode" value="HRS" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="hoardingMasterDto.hoardingStatus" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="adh.select" isMandatory="false"
						showOnlyLabel="Status" />
				</div>
				<div class="form-group">
					<!-- <This is for Hierarchical> -->
					<apptags:lookupFieldSet baseLookupCode="ADH" hasId="true"
						pathPrefix="hoardingMasterDto.hoardingTypeId"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" isMandatory="false"
						cssClass="form-control margin-bottom-10" showAll="false"
						columnWidth="20%" />

					<label class="col-sm-2 control-label"> <spring:message
							code="hoarding.label.hoarding.location" text="Location"></spring:message>
					</label>
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
				<div class="text-center margin-bottom-10">
					<button type="button" class="btn btn-success" id="searchHoarding"
						title="Search" onclick="">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='HoardingMaster.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-blue-2" title="Add"
						onclick="addHoardingMaster('HoardingMaster.html','addHoardingMaster')">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.add" text="Add"></spring:message>
					</button>
				</div>

				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="hoardingTable">
							<thead>
								<tr>
									<th><spring:message code="hoarding.label.number"
											text="Hoarding Number"></spring:message></th>
									<th><spring:message code="hoarding.label.old.number"
											text="Old Hoarding Number"></spring:message></th>
									<th><spring:message
											code="hoarding.master.registration.date"
											text="Registration Date"></spring:message></th>
									<th><spring:message
											code="hoarding.label.hoarding.description" text="Description"></spring:message></th>
									<th><spring:message code="hoarding.label.hoarding.type"
											text="Hoarding Type"></spring:message></th>
									<th><spring:message code="hoarding.label.hoarding.action"
											text="Action"></spring:message></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.hoardingMasterDtoList}"
									var="masterList">
									<tr>
										<td align="center">${masterList.hoardingNumber}</td>
										<td align="center">${masterList.hoardingOldNumber}</td>
										<td align="center">${masterList.hrdFmtDate}</td>
										<td align="center">${masterList.hoardingDescription}</td>
										<td align="center">${masterList.hoardingTypeIdDesc}</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View Hoarding Master"
												onclick="editAndViewHoardingMaster(${masterList.hoardingId},'V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit Hoarding Master"
												onclick="editAndViewHoardingMaster(${masterList.hoardingId},'E')">
												<i class="fa fa-pencil-square-o"></i>
											</button>
										</td>
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