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
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="js/adh/advertisementDataEntry.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>


<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="adh.advertisement.data.entry.table.title"
					text="Advertisement Data Entry"></spring:message>
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="AdvertisementDataEntry.html"
				name="AdvertisementDataEntry" id="AdvertisementDataEntry"
				cssClass="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp"></jsp:include>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="adh.label.advertiser.name" /></label>
					<div class="col-sm-4">
						<form:select path="advertiserMasterDto.agencyId" id="agencyId"
							class="chosen-select-no-results" data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" />
							</form:option>
							<c:forEach items="${command.advertiserMasterDtoList}"
								var="agency">
								<form:option value="${agency.agencyId}">${agency.agencyName}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="adh.label.license.type" text="License Type" /></label>
					<c:set var="baseLookupCode" value="LIT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="advertisementDto.licenseType" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="adh.select" isMandatory="true"
						showOnlyLabel="License Type" />
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="adh.label.license.status" text="License Status" /></label>
					<div class="col-sm-4">
						<form:select path="advertisementDto.adhStatus" id="adhStatus"
							class="chosen-select-no-results" data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" />
							</form:option>
							<form:option value="A">
								<spring:message code="adh.location.type.active" text="Active"></spring:message>
							</form:option>
							<form:option value="T">
								<spring:message code="adh.location.type.terminate"
									text="Terminate"></spring:message>
								<form:option value="C">
									<spring:message code="adh.location.type.closed" text="Closed"></spring:message>
								</form:option>
								<%-- <form:option value="S">
									<spring:message code="adh.location.type.suspended"
										text="Suspended"></spring:message>
								</form:option> --%>
							</form:option>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="adh.label.location" text="Location" /></label>
					<div class="col-sm-4">
						<form:select path="advertisementDto.locId" id="locId"
							class="chosen-select-no-results" data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" />
							</form:option>
							<c:forEach items="${command.locationList}" var="location">
								<form:option value="${location.locId}">${location.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				
				<div class="form-group">
					<apptags:date labelCode="adh.label.license.from.date"
						datePath="advertisementDto.licenseFromDate"
						fieldclass="datepicker" ></apptags:date>
						
					  <apptags:date labelCode="adh.label.license.to.date" 
						datePath="advertisementDto.licenseToDate" fieldclass="datepicker" ></apptags:date>
				</div>
				
				<div class="text-center margin-bottom-10">
					<button type="button" class="btn btn-success" id="searchDataEntry"
						title="Search" onclick="">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='AdvertisementDataEntry.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-blue-2" title="Add"
						onclick="addAdvertisementEntry('AdvertisementDataEntry.html','addAdvertisementEntry')">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.add" text="Add"></spring:message>
					</button>
				</div>


				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="dataEntryTableId">
						<thead>
							<tr>
								<th><spring:message code="" text="License No."></spring:message></th>
								<th><spring:message code="adh.label.advertiser.name"
										text="Advertiser Nane"></spring:message></th>
								<th><spring:message code="adh.label.location"
										text="Location"></spring:message></th>
								<th><spring:message code="adh.label.license.type"
										text="License Type"></spring:message></th>
								<th><spring:message code="adh.label.license.status"
										text="License Status"></spring:message></th>
										<th><spring:message code="adh.label.license.from.date"
										text="License From Date"></spring:message></th>
										<th><spring:message code="adh.label.license.to.date"
										text="License To Date"></spring:message></th>
								<th><spring:message code="adh.action" text="Action"></spring:message></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.advertisementDtoList }" var="dtoList">
								<tr>
									<td align="center">${dtoList.licenseNo }</td>
									<td align="center">${dtoList.agencyName }</td>
									<td align="center">${dtoList.locIdDesc }</td>
									<td align="center">${dtoList.licenseTypeDesc }</td>
									<td align="center">${dtoList.adhStatusDesc }</td>
									<td align="center"><fmt:formatDate pattern="yyyy-MM-dd" value="${dtoList.licenseFromDate }"/></td>
									<td align="center"><fmt:formatDate pattern="yyyy-MM-dd" value="${dtoList.licenseToDate }"/></td>
									<td class="text-center"><c:choose>
											<c:when test="${dtoList.updatedDate  ne null}">
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="View"
													onclick="editOrViewAdvDataEntry(${dtoList.adhId},'V')">
													<i class="fa fa-eye"></i>
												</button>
											</c:when>
											<c:otherwise>
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="View"
													onclick="editOrViewAdvDataEntry(${dtoList.adhId},'V')">
													<i class="fa fa-eye"></i>
												</button>
												<button type="button" class="btn btn-warning btn-sm"
													title="Edit"
													onclick="editOrViewAdvDataEntry(${dtoList.adhId},'E')">
													<i class="fa fa-pencil-square-o"></i>
												</button>
											</c:otherwise>
										</c:choose></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>
			</form:form>
		</div>
	</div>
</div>