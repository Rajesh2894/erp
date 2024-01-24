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
<script type="text/javascript"
	src="js/solid_waste_management/report/surveyReport.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="swm.survey.entry.form"
						text="Survey Entry Form" /></strong>
			</h2>
			<apptags:helpDoc url="SurveyReportMaster.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="SurveyReportMaster.html" commandName="command"
				class="form-horizontal form" name="" id="id_surveyReport">
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- End Validation include tag -->
				<div class="form-group">

					<label class="col-sm-2 control-label " for=""><spring:message
							code="swm.survey.type" text="Survey Type" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="LCT" />
						<form:select path="" cssClass="form-control mandColorClass"
							id="sType" onchange="" disabled="" data-rule-required="true">
							<form:option value="0">
								<spring:message code="solid.waste.select" text="select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2 search"
						onclick="searchSurvey('SurveyReportMaster.html', 'searchSurveyReport')">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search" />
					</button>

				</div>
				<!-- End button -->
				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="id_surveyTable">
							<thead>
								<c:if test="${command.locationList.size()!=0}">
									<tr>
										<th><spring:message code="vehicle.master.vehicle.srno"
												text="Sr. No." /></th>
										<th><spring:message code="swm.survey.name" text="Name" /></th>
										<th><spring:message code="swm.survey.location.address"
												text="Loaction Address" /></th>
										<th><spring:message code="population.master.ward"
												text="Ward" /></th>
										<th><spring:message code="swm.action" text="Action" /></th>
									</tr>
								</c:if>
								<c:if test="${command.locationList.size()==0}">

									<h4>
										<strong><spring:message
												code="swm.report.data.NoFound" text="No Record Found...!!!" /></strong>
									</h4>
								</c:if>
							</thead>
							<tbody>

								<c:forEach items="${command.surveyReportDTOList}" var="loc"
									varStatus="loop">
									<tr>
										<td hidden="${loc.locId}"></td>
										<td align="center">${loop.count}</td>
										<td align="center">${command.locationList[loop.index].locNameEng}</td>
										<td align="center">${command.locationList[loop.index].locAddress}</td>
										<td align="center">
										<c:if test="${loc.codWard1 != null && loc.codWard2 == null}">
										<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(loc.codWard1)"
											var="lookup" />${lookup.lookUpDesc }</c:if>
										<c:if test="${loc.codWard1 != null && loc.codWard2 != null}">
										<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(loc.codWard2)"
											var="lookup" />${lookup.lookUpDesc }</c:if></td>
										
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View"
												onclick="openaddSurveyReport(${command.locationList[loop.index].locId},'SurveyReportMaster.html','viewSurveyReport')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit"
												onclick="openaddSurveyReport(${command.locationList[loop.index].locId},'SurveyReportMaster.html','addSurveyReport')">
												<i class="fa fa-pencil"></i>
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





