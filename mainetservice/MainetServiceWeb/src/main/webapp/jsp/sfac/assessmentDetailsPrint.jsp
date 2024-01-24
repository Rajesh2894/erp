<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/sfac/CBBOAssesementEntry.js"></script>
<script>
	$(document).ready(function() {
		$("#cbboAssessEntTable").DataTable().destroy();
	});
    function printContent(el) {
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
    }
</script>
<style>
ul.imp-notes {
	margin: 0 0 0 1rem;
}
ul.imp-notes li {
	font-size: 0.75em;
	list-style-type: disc;
}
table#cbboAssessEntTable > thead > tr > :is(:not(th:nth-child(1), th:nth-child(2))),
table#cbboAssessEntTable > tbody > tr > :is(:not(th:nth-child(1), th:nth-child(2), td:nth-child(1), td:nth-child(2))),
table#cbboAssessEntTable > tfoot > tr > :is(:not(th:nth-child(1), th:nth-child(2))) {
	padding: 0;
}
table#cbboAssessEntTable tbody tr th.text-left {
	text-align: left;
}
table.inner-table,
table.inner-table-2 {
	width: 100%;
}
table.inner-table tbody tr th,
table.inner-table tbody tr td,
table.inner-table-2 thead tr th {
	border: solid rgb(8 131 55 / 94%) !important;
}
table.inner-table thead tr th:not(:nth-child(2)),
table.inner-table tbody tr :is(th:not(:nth-child(2)), td:not(:nth-child(2))),
table.inner-table-2 thead tr > th,
table.inner-table-2 tbody tr > :is(th, td) {
	padding: 0.3rem;
}
table.inner-table > tbody > tr:not(:last-child) > th,
table.inner-table > tbody > tr:not(:last-child) > td {
	border-width: 0 0 1px 0 !important;
}
table.inner-table > tbody > tr:last-child > th,
table.inner-table > tbody > tr:last-child > td {
	border-width: 0 !important;
}
table.inner-table-2 thead tr th,
table.inner-table-2:last-child tbody tr th,
table.inner-table-2:last-child tbody tr td {
	border-width: 0 1px 0 1px !important;
}
table.inner-table-2:not(:last-child) tbody tr th,
table.inner-table-2:not(:last-child) tbody tr td {
	border-width: 0 1px 1px 1px !important;
}
table#cbboAssessEntTable > thead > tr:nth-child(2) > th:nth-child(1),
table#cbboAssessEntTable > thead > tr:nth-child(2) > th:nth-child(2) {
	width: 5.8rem;
}
table.inner-table thead tr th:nth-child(1),
table.inner-table tbody tr :is(th:nth-child(1), td:nth-child(1)) {
	width: 23.16rem;
}
table.inner-table thead tr th:nth-child(3),
table.inner-table tbody tr :is(th:nth-child(3), td:nth-child(3)) {
	width: 9rem;
}
table.inner-table-2 thead tr th:nth-child(1),
table.inner-table-2 tbody tr :is(th:nth-child(1), td:nth-child(1)) {
	width: 13.72rem;
}
table.inner-table-2 thead tr th:nth-child(2),
table.inner-table-2 tbody tr :is(th:nth-child(2), td:nth-child(2)) {
	width: 5.2rem;
}
table.inner-table-2 thead tr th:nth-child(3),
table.inner-table-2 tbody tr :is(th:nth-child(3), td:nth-child(3)) {
	width: 3.94rem;
}
table.inner-table-2 thead tr th:nth-child(4),
table.inner-table-2 tbody tr :is(th:nth-child(4), td:nth-child(4)) {
	width: 5.3rem;
}
table.inner-table-2 thead tr th:nth-child(5),
table.inner-table-2 tbody tr :is(th:nth-child(5), td:nth-child(5)) {
	width: 3.84rem;
}
</style>


<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.cbbo.assessment.entry.title"
					text="CBBO Assessment Entry Form" />
			</h2>
			<apptags:helpDoc url="CBBOAssessmentEntry.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding" id="assessmentDetailsPrint">
			<style type="text/css" media="print">
				@media print {
					@page {
						margin: 15px;
						size: A3 landscape;
					}
					.table-responsive {
						overflow: hidden;
						border: none;
					}
				}
			</style>
			<form:form id="AssesementApproval"
				action="CBBOAssessmentEntry.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.CBBO.name" text="CBBO Name" /></label>
					<div class="col-sm-4">
						<form:input class="form-control" id="cbboName"
							path="assementMasterDto.cbboName" readonly="true" />
					</div>

					<label class="col-sm-2 control-label "><spring:message
							code="sfac.CBBO.UniqueId" text="CBBO Unique Id" /></label>
					<div class="col-sm-4">
						<form:input class="form-control" id="cbboUniqueId"
							path="assementMasterDto.cbboUniqueId" readonly="true" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.nofpoAllocated" text="FPOs Allocated" /></label>
					<div class="col-sm-4">
						<form:input class="form-control" id="fpoAllcTarget"
							path="assementMasterDto.fpoAllcTarget" readonly="true" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.assement.date" text="Assessment Date" /></label>
					<div class="col-sm-4">
						<form:input class="form-control" id="assessmentDate"
							path="assementMasterDto.assessmentDate" readonly="true" />
					</div>
				</div>

	

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.ass.fpoRegCount" text="FPO Registered" /></label>
					<div class="col-sm-4">
						<form:input class="form-control" id="fpoRegCount"
							path="assementMasterDto.fpoRegCount" readonly="true" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.ass.fpoRegPendcount"
							text="No of FPOs-Registration Pending" /></label>
					<div class="col-sm-4">
						<form:input class="form-control" id="fpoRegPendCount"
							path="assementMasterDto.fpoRegPendCount" readonly="true" />
					</div>
				</div>
				
					<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.ass.ia.name" text="Name Of IA Empaneled with" /></label>
							<div class="col-sm-4">
						<form:input class="form-control" id="iaName"
							path="assementMasterDto.iaName" readonly="true" />
					</div>
				</div>




				<h3 class="text-center padding-top-10">
					<spring:message code="sfac.cbbo.assessment.heading.1"
						text="CBBO Assessment Entry Form - Year : " /><span>${command.assYear}</span>
				</h3>
				<h5 class="text-center">
					<spring:message code="sfac.cbbo.assessment.heading.2"
						text="Central Sector Scheme Formation and Promotion of 10,000 Farmer Producer Organizations" />
				</h5>

				<div class="table-responsive">
					<table class="table table-bordered table-striped" id="cbboAssessEntTable" width="100%">
						<thead>
							<tr>
								<th colspan="3">
									<spring:message code="sfac.analysis" text="Analysis" />
								</th>
								<%-- <th>
									<spring:message code="sfac.scoring" text="Scoring" />
								</th> --%>
							</tr>
							<tr>
								<th>
									<spring:message code="sfac.key.parameter" text="Key Parameter" />
								</th>
								<th>
									<spring:message code="sfac.weightage" text="Weightage" />
								</th>
								<th>
									<table class="inner-table">
										<thead>
											<tr>
												<th><spring:message code="sfac.sub.parameter" text="Sub Parameter" /></th>
												<th>
													<table class="inner-table-2">
														<thead>
															<tr>
																<th><spring:message code="sfac.ass.Condition" text="Condition" /></th>
																<th><spring:message code="sfac.sub.weightage" text="Sub Weightage" /></th>
																<th class="required-control"><spring:message code="sfac.ass.overallScore" text="Overall Score" /></th>
																<th class="required-control"><spring:message code="sfac.ass.regFpoFullfilling" text="Registered FPOs fulfilling criteria" /></th>
																<th class="required-control"><spring:message code="sfac.ass.score" text="Score %" /></th>
															</tr>
														</thead>
													</table>
												</th>
												<th><spring:message code="sfac.means.of.verification" text="Mode of Verification" /></th>
											</tr>
										</thead>
									</table>
								</th>
							</tr>
						</thead>
						<tbody>
						 <c:set var="d" value="0" scope="page"></c:set>
						 <c:set var="w" value="0" scope="page"></c:set>
							<c:forEach var="assMastDto"
								items="${command.assementKeyParamDtoList}" varStatus="status">
								<tr>
									<td class="text-center vertical-align-middle">${assMastDto.keyParameterDesc}</td>
									<td class="text-center vertical-align-middle">${assMastDto.weightage}</td>
									<td>
									 <c:set var="e" value="0" scope="page"></c:set>
										<table class="inner-table">
											<c:forEach var="detDto"
												items="${assMastDto.assSubParamDtoList}"
												varStatus="status">
												<tr>
													<td>${detDto.subParameterDesc}</td>
													<td>
													
													<c:set var="f" value="0" scope="page"></c:set>
														<c:forEach var="subDto" items="${detDto.assSubParamDetailDtoList}">
															
															<table class="inner-table-2">
																<tr>
																	<td>${subDto.conditionDesc}</td>
																	<td>${subDto.subWeightage}</td>
															    <c:set var="w" value="${w + subDto.subWeightage}" scope="page" />
																	<td><form:input class="form-control"
																			disabled="true"
																			onblur="calculateScore('${d}${e}${f}');"
																			onkeypress="return hasAmount(event, this, 3, 2)"
																			id="overallScore${d}${e}${f}"
																			path="assementKeyParamDtoList[${d}].assSubParamDtoList[${e}].assSubParamDetailDtoList[${f}].overallScore" />
																	</td>
																	<td><form:input class="form-control hasNumber"
																			disabled="true" maxlength="3"
																			onblur="calculateTotal('${d}${e}${f}');"
																			id="regiFpoCriteria${d}${e}${f}"
																			path="assementKeyParamDtoList[${d}].assSubParamDtoList[${e}].assSubParamDetailDtoList[${f}].regiFpoCriteria" />
																	</td>
																	<td><form:input class="form-control"
																			readonly="true"
																			onchange="return hasAmount(event, this, 3, 2)"
																			id="score${d}${e}${f}"
																			path="assementKeyParamDtoList[${d}].assSubParamDtoList[${e}].assSubParamDetailDtoList[${f}].score" />
																	</td>
																</tr>
															</table>
															<c:set var="f" value="${f + 1}" scope="page" />
														</c:forEach>
													</td>
													<td>${detDto.meansOfVerificationDesc}</td>
												</tr>
												<c:set var="e" value="${e + 1}" scope="page" />
											</c:forEach>
											
										</table>
									</td>
								</tr>
					<!-- 			<tr>
									<th></th>
									<th></th>
									<th>
										<table class="inner-table">
											<tr>
												<th class="text-left">Sub-Total (A)</th>
												<th>
													<table class="inner-table-2">
														<tr>
															<th></th>
															<th></th>
															<th>0.0</th>
															<th></th>
															<th></th>
														</tr>
													</table>
												</th>
												<th></th>
											</tr>
										</table>
									</th>
								</tr> -->
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
							</tbody>
							<tfoot>
							<tr>
								<th>
									<spring:message code="sfac.total" text="Total" />
								</th>
								<th>
									100
								</th>
								<th>
									<table class="inner-table">
										<tr>
											<th></th>
											<th>
												<table class="inner-table-2">
													<tr>
														<th></th>
														<th>${w}</th>
														<th width="10"><form:input class="form-control" readonly="true" id="addOverallScore" path="assementMasterDto.totalOverallScore" /></th>
														<th><form:input class="form-control"  readonly="true" id="addRegFulfill" path="assementMasterDto.totalFpoFullFilling" /></th>
													    <th><form:input class="form-control"  readonly="true" id="addTotalScore" path="assementMasterDto.totalScore" /></th>
													</tr>
												</table>
											</th>
											<th></th>
										</tr>
									</table>
								</th>
							</tr>
						</tfoot>
					</table>
				</div>
				

				<label class="text-blue-2 margin-top-30"><spring:message
						code="sfac.points.to.consider" text="Points To Consider" /></label>
				<ul class="imp-notes margin-bottom-30">
					<li><spring:message code="sfac.cbbo.assessment.entry.note.1"
							text="For FY 2020-21" /></li>
					<li><spring:message code="sfac.cbbo.assessment.entry.note.2"
							text="Resultant Performance %age indicates the on ground effectiveness quotient of CBBO." />
					</li>
					<li><spring:message code="sfac.cbbo.assessment.entry.note.3"
							text="Acceptable performance threshold is considered as 70%" />
					</li>
				</ul>

			


			<div class="text-center margin-top-20">
					<button onclick="printContent('assessmentDetailsPrint')"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print" aria-hidden="true"></i>
						<spring:message code="sfac.print" text="Print" />
					</button>

					<button type="button" class="btn btn-danger hidden-print"
						onclick="javascript:openRelatedForm('CBBOAssessmentEntry.html');">
						<spring:message code="sfac.button.back" text="Back"></spring:message>
					</button>
				</div>


			</form:form>
		</div>
	</div>
</div>