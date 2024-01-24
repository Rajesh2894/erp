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
<script type="text/javascript" src="js/adh/advertiserMaster.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="advertiser.master.title"
					text="Advertiser Master" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message></span>
			</div>
			<form:form action="AdvertiserMaster.html" name="advertiserMaster"
				id="advertiserMaster" class="form-horizontal" commandName="command">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="advertiser.master.advertiser.number" /></label>

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

					<label class="col-sm-2 control-label"><spring:message
							code="advertiser.master.advertiser.old.number" /></label>

					<div class="col-sm-4">
						<form:select path="advertiserMasterDto.agencyOldLicNo"
							id="agencyOldLicNo" class="chosen-select-no-results"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.advertiserMasterDtoList}"
								var="masterDto">
								<form:option value="${masterDto.agencyOldLicNo}">${masterDto.agencyOldLicNo}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="form-group">


					<label class="col-sm-2 control-label"><spring:message
							code="advertiser.master.advertiser.name" /></label>

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
							code="advertiser.master.advertiser.status" /></label>
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
				</div>


				<div class="text-center margin-bottom-10">
					<button type="button" class="btn btn-success" title="Search"
						onclick="searchAdvertiser()">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='AdvertiserMaster.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-blue-2" title="Add"
						onclick="addAdvertiserMaster('AdvertiserMaster.html','addAdvertiserMaster')">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.add" text="Add"></spring:message>
					</button>
				</div>


				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="advertiserTable">
							<thead>
								<tr>
									<th><spring:message
											code="advertiser.master.advertiser.number"
											text="Advertiser Number" /></th>
									<th><spring:message
											code="advertiser.master.advertiser.old.number"
											text="Old Advertiser Number" /></th>
									<th><spring:message
											code="advertiser.master.registration.date"
											text="Registration Date" /></th>
									<th><spring:message
											code="advertiser.master.advertiser.name"
											text="Advertiser Name" /></th>
									<th><spring:message
											code="advertiser.master.advertiser.status" text="Status" /></th>
									<th><spring:message code="adh.view.edit" text="View/Edit" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.advertiserMasterDtoList}"
									var="masterList">
									<tr>
										<td>${masterList.agencyLicNo }</td>
										<td>${masterList.agencyOldLicNo }</td>
										<td>${masterList.agencyRegisDate }</td>
										<td>${masterList.agencyName }</td>
										<td>${masterList.agencyStatus }</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View Advertiser Master"
												onclick="editadvertiserMaster(${masterList.agencyId},'V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit Advertiser Master"
												onclick="editadvertiserMaster(${masterList.agencyId},'E')">
												<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
											</button>
											
											<button type="button" class="btn btn-primary hidden-print"
												title="Print Agency License Letter"
												onclick="printAgencyLicenseLetter(${masterList.agencyId})">
												<i class="fa fa-print"></i>
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

