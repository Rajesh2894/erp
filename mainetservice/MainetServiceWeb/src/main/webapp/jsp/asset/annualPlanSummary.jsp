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
<script type="text/javascript" src="js/asset/assetAnnualPlan.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message
					code="${userSession.moduleDeptCode == 'AST' ? 'asset.annualPlan.summary':'asset.ITannualPlan.summary'}"
					text="Annual Plan Summary" />
			</h2>
			<apptags:helpDoc url="AssetAnnualPlan.html" />
		</div>
		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="" cssClass="form-horizontal" id="" name="">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<input type="hidden" id="moduleDeptUrl"
					value="${userSession.moduleDeptCode == 'AST' ? 'AssetAnnualPlan.html':'ITAssetAnnualPlan.html'}">

				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="asset.annualPlan.finYear" text="Financial Year" /></label>
					<div class="col-sm-4">
						<form:select path="astAnnualPlanDTO.financialYear"
							id="financialYear" class="form-control chosen-select-no-results"
							onchange="selectTemplateTypeData(this)">
							<form:option value="">
								<spring:message code="asset.info.select" text="Select" />
							</form:option>
							<c:forEach items="${command.faYears}" var="lookUp">
								<form:option value="${lookUp.faYear}" code="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label "
						for="assetgroup"> <spring:message
							code="asset.annualPlan.department" /></label>
					<div class="col-sm-4">
						<form:select path="astAnnualPlanDTO.department.dpDeptid"
							disabled="${command.approvalViewFlag eq 'V'}" id="astDept"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="asset.info.select" />
							</form:option>
							<c:forEach items="${command.departmentsList}" var="obj">
								<form:option value="${obj.dpDeptid}" code="${obj.dpDeptcode}">${obj.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label "
						for="assetgroup"> <spring:message
							code="asset.annualPlan.location" /></label>
					<div class="col-sm-4">
						<form:select path="astAnnualPlanDTO.locationMas.locId"
							disabled="${command.approvalViewFlag eq 'V'}" id="astLoc"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="asset.info.select" />
							</form:option>
							<c:forEach items="${command.locList}" var="location">
								<form:option value="${location.locId}">${location.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2" title='<spring:message code="asset.search" text="Search" />'
						id="searchAnnualPlan">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="asset.search" text="Search" />
					</button>

					<button type="button"
						onclick="window.location.href='${userSession.moduleDeptCode == 'AST' ? 'AssetAnnualPlan.html':'ITAssetAnnualPlan.html'}'"
						class="btn btn-warning" title='<spring:message code="asset.requisition.reset" text="Reset" />'>
						<spring:message code="asset.requisition.reset" text="Reset" />
					</button>

					<button type="button" class="btn btn-primary"
						onclick="openAnnualPlanForm('${userSession.moduleDeptCode == 'AST' ? 'AssetAnnualPlan.html':'ITAssetAnnualPlan.html'}','addAnnualPlan');"
						title='<spring:message code="asset.add" text="Add" />'>
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="asset.add" text="Add" />
					</button>

				</div>
				<!-- End button -->

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="annualPlanSummaryDT">
						<thead>
							<tr>
								<th width="3%" align="center"><spring:message code="asset.requistion.sr.no"
										text="Sr.No" /></th>
								<th class="text-center"><spring:message code="asset.requistion.date"
										text="Date" /></th>
								<th class="text-center"><spring:message code="asset.requistion.location"
										text="Location" /></th>
								<th class="text-center"><spring:message code="asset.requistion.department"
										text="Department" /></th>
								<th class="text-center"><spring:message code="asset.requistion.status"
										text="Status" /></th>
								<th class="text-center"><spring:message
										code="land.acq.summary.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.astAnnualPlanDTOs}" var="plan"
								varStatus="status">
								<tr>
									<td class="text-center">${status.count}</td>
									<td class="text-center">${plan.dateDesc}</td>
									<td class="text-center">${plan.locationDesc}</td>
									<td class="text-center">${plan.departDesc}</td>
									<td class="text-center">${plan.status}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2"
											name="button-plus" id="button-plus"
											onclick="getActionForDefination('${plan.astAnnualPlanId}','VIEW')"
											title="<spring:message code="land.acq.summary.view" text="View"></spring:message>">
											<i class="fa fa-eye" aria-hidden="true"></i>
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

