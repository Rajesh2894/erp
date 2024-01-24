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
<script type="text/javascript"
	src="js/adh/advertisementContractMapping.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="contract.details.advertisement.contract.mapping" text="Advertisement Contract Mapping" />
			</h2>
		</div>

		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message></span>
			</div>
			<form:form action="AdvertisementContractMapping.html"
				name="AdvertisementContractMapping"
				id="AdvertisementContractMapping" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="form-group">
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="contract.details.label.number" text="Contract Number" /></label>
						<div class="col-sm-4">
							<form:select path="contractMappingDto.contractId" id="contractNo"
								class="form-control chosen-select-no-results" data-rule-required="true">
								<form:option value="">
									<spring:message code="adh.select" text="Select"></spring:message>
								</form:option>
								<c:forEach items="${command.contractNoList}"
									var="contractNoList">
									<form:option value="${contractNoList[1]}">${contractNoList[1]}</form:option>
								</c:forEach>
							</form:select>
						</div>
						<label class="control-label col-sm-2" for="ContractDate"><spring:message
								code="contract.details.label.date" text="Contract Date"></spring:message></label>
						<div class="col-sm-4">
							<div class="input-group">
								<input name="conDate" id="conDate" type="text"
									class="form-control datepicker" id="ContractDate"> <span
									class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>

						<%-- <apptags:date labelCode="contract.details.label.date"
									datePath="agencyRequestDto.masterDto.agencyLicToDate"
									fieldclass="datepicker" isMandatory="true"></apptags:date> --%>
					</div>
				</div>


				<div class="text-center padding-bottom-10">
					<button type="button" id="searchButtonId"
						onclick="searchContractMapping()"
						class="btn btn-success btn-submit">
						<strong class="fa fa-search"></strong>
						<spring:message code="adh.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='AdvertisementContractMapping.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.reset" text="Reset"></spring:message>
					</button>
					<button type="button" id="addMappingId" class="btn btn-blue-2"
						onclick="addContractMapping('AdvertisementContractMapping.html','addAdvertisementContractMapping')">
						<spring:message code="contract.label.add.contract.mapping"
							text="ADD Contract Mapping"></spring:message>
					</button>
				</div>



				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="hoardingDetailTable">
							<thead>
								<tr>
									<th><spring:message code="contract.details.label.number"
											text="Contract Number"></spring:message></th>
									<th><spring:message code="contract.details.label.date"
											text="Contract Date"></spring:message></th>
									<th><spring:message code="contract.label.department"
											text="Department"></spring:message></th>
									<th><spring:message code="contract.label.represented.by"
											text="Represented By"></spring:message></th>
									<th><spring:message code="contract.label.vendor.name"
											text="Vendor Name"></spring:message></th>
									<th><spring:message
											code="contract.details.label.from.date"
											text="Contract From Date"></spring:message></th>
									<th><spring:message code="contract.details.label.to.date"
											text="Contract To Date"></spring:message></th>
									<th width="120"><spring:message
											code="contract.label.view.mapping" text="View Mapping"></spring:message></th>

								</tr>
							</thead>

							<tbody id="propertyListId">
								<c:forEach items="${command.contractMappingList}"
									var="contractMapping" varStatus="count">
									<tr>
										<td>${contractMapping.contractNo}</td>
										<td>${contractMapping.contDate}</td>
										<td>${contractMapping.deptName}</td>
										<td>${contractMapping.representedBy}</td>
										<td>${contractMapping.vendorName}</td>
										<td>${contractMapping.fromDate}</td>
										<td>${contractMapping.toDate}</td>
										<td><a href="javascript:void(0);"
											class="btn btn-blue-2 btn-sm text-center margin-left-30"
											data-original-title="View Mapping"
											onClick="viewAdhContractMapping(${contractMapping.contId},'V')"><strong
												class="fa fa-eye"></strong><span class="hide"><spring:message
														code="" text="View"></spring:message></span></a></td>
										<%-- 	<td><a href="javascript:void(0);"
										class="btn btn-darkblue-3 text-center margin-left-30"
										onClick="printContractEstate(${contractMapping.contId})"><strong><i
												class="fa fa-print"></i></strong></a></td> --%>
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
