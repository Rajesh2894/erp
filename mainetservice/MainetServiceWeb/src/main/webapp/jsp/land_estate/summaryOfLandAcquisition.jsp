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
<script type="" src="js/land_estate/landEstate.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="land.acq.summary.title" text="Land Acquisition Summary" />
			</h2>
			<apptags:helpDoc url="LandAcquisition.html" />
		</div>
		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="" cssClass="form-horizontal"
				id="" name="">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->

				<div class="form-group">
					
					<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.acq.summary.proposal" /></label>
											
						<div class="col-sm-4 ">
						<form:select path="acquisitionDto.lnServno" id="proposalNo"
											cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true">
						<form:option value="">Select</form:option>
						<c:forEach items="${command.proposalNoList}" var="ProposalNolist">
						<form:option value="${ProposalNolist}">${ProposalNolist}</form:option>
						 </c:forEach>
						 </form:select>					
						</div>
						
						<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.acq.location" />
									</label>
									<div class="col-sm-4 ">
										<form:select path="acquisitionDto.locId" id="locId"
											cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true"
											disabled="${command.saveMode eq 'VIEW'}">
											<form:option value="0">Select</form:option>
											<c:forEach items="${command.locationList}" var="location">
												<form:option value="${location.locId}">${location.locNameEng}</form:option>
											</c:forEach>
										</form:select>
									</div>
											
					
				</div>
				<div class="form-group">
					<%-- <apptags:input labelCode="land.acq.summary.status" path="acquisitionDto.acqStatus"></apptags:input> --%>
					<apptags:input labelCode="land.acq.summary.payTo" path="acquisitionDto.payTo"></apptags:input>
					
									<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.acq.summary.proposalstatus" text=" Proposal Status" />
									</label>
									<div class="col-sm-4 ">
										<form:select path="acquisitionDto.acqStatus" id="acqStatusId"
											cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true">
											<form:option value="">Select</form:option>
											<form:option value="T">T - transit ( In process )</form:option>
											<form:option value="A">A - Acquired</form:option>
										</form:select>
									</div>
					
					<%-- <label class="col-sm-2 control-label "><spring:message
					code="land.acq.mode" text="Acquisition Mode" /></label>
					<c:set var="baseLookupCode" value="AQM" />
					<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="acquisitionDto.acqModeId" cssClass="form-control"
										hasChildLookup="false" hasId="true"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="Acquisition Mode"
										disabled="${command.saveMode eq 'VIEW'}" /> --%>
										
				</div>

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2" title="Search"
						id="searchlandAcquisition">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.acq.summary.search" text="Search" />
					</button>

					<button type="button"
						onclick="window.location.href='LandAcquisition.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.acq.summary.reset" text="Reset" />
					</button>

					<button type="button" class="btn btn-primary"
						onclick="addLandAcq('LandAcquisition.html','addLandAcq');"
						title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.acq.summary.add" text="Add" />
					</button>

				</div>
				<!-- End button -->

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="acqSummaryDatatables">
						<thead>
							<tr>
								<th width="3%" align="center"><spring:message
										code="land.acq.summary.sr" text="Sr. No" /></th>
								<th class="text-center"><spring:message
										code="land.acq.summary.proposal" text="Proposal Number" /></th>
								<th class="text-center"><spring:message
										code="land.acq.summary.prposalDate" text="Proposal Date" /></th>
								<th class="text-center"><spring:message
										code="land.acq.summary.landDescription" text="Land Description" /></th>
								<th class="text-center"><spring:message
										code="land.acq.summary.surveyno" text="Survey Number" /></th>
								<th class="text-center"><spring:message
										code="land.acq.summary.proposalstatus" text="Proposal Status" /></th>
								<th class="text-center"><spring:message
										code="land.acq.summary.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.landAcquisitionList}"
								var="land" varStatus="status">
								<tr>
									<td class="text-center">${status.count}</td>
									<td class="text-center">${land.apmApplicationId}</td>
									<td class="text-center">${land.acqDateDesc}</td>
									<td class="text-center">${land.lnDesc}</td>
									<td class="text-center">${land.lnServno}</td>
									<td class="text-center">${land.acqStatus}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2"
											name="button-plus" id="button-plus"
											onclick="getActionForDefination(${land.lnaqId},${land.apmApplicationId},'${land.assetId}','VIEW')"
											title="<spring:message code="land.acq.summary.view" text="View"></spring:message>">
											<i class="fa fa-eye" aria-hidden="true"></i>
										</button>
										
										
											<button type="button" class="btn btn-blue-2"
												onclick="getActionForDefination(${land.lnaqId},${land.apmApplicationId},'${land.assetId}','ASSET')"
												title="<spring:message code="land.acq.summary.transfer" text="Transfer In Asset"></spring:message>">
												<i class="fa fa-arrow-right" aria-hidden="true"></i>
											</button>
											
									</td>
									</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->

