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
<script type="text/javascript" src="js/sfac/fpoAssessmentForm.js"></script>
<style>
ul.imp-notes {
	margin: 0 0 0 1rem;
}
ul.imp-notes li {
	font-size: 0.75em;
	list-style-type: disc;
}
table#fpoAssessEntTable > thead > tr > :is(:not(th:nth-child(1), th:nth-child(2))),
table#fpoAssessEntTable > tbody > tr > :is(:not(th:nth-child(1), th:nth-child(2), td:nth-child(1), td:nth-child(2))) {
	padding: 0;
}
table#fpoAssessEntTable tbody tr th.text-left {
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
table#fpoAssessEntTable > thead > tr:nth-child(2) > th:nth-child(1),
table#fpoAssessEntTable > thead > tr:nth-child(2) > th:nth-child(2) {
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

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.fpo.assessment.entry.title"
					text="FPO Assessment Entry Form" />
			</h2>
			<apptags:helpDoc url="FPOAssessmentEntry.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="fpoAssessmentEntry" action="FPOAssessmentEntry.html"
				method="post" class="form-horizontal">
				<form:hidden path="fpoName" id="fpoName"/>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.fpo.fpoName" text="FPO Name" /></label>
					<c:if test="${command.saveMode eq 'A'}">
						<div class="col-sm-4">
							<form:select path="assementMasterDto.fpoId" id="fpoId"
								disabled="${command.saveMode eq 'V' ? true : false }"
								cssClass="form-control chosen-select-no-results">
								<form:option value="0">
									<spring:message text="Select" code="sfac.select'" />
								</form:option>
								<c:forEach items="${command.fpoMastDtoList}" var="dto">
									<form:option value="${dto.fpoId}" code="${dto.fpoName}">${dto.fpoName}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>
					<c:if test="${command.saveMode eq 'V'}">
						<div class="col-sm-4">
							<form:input class="form-control" id="fpoName"
								path="assementMasterDto.fpoName" readonly="true" />
						</div>
					</c:if>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.assement.year" text="Assement Year" /></label>
					<div class="col-sm-4">
						<form:select path="assementMasterDto.finYrId" id="finYrId"
							disabled="${command.saveMode eq 'V' ? true : false }"
							cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select'" />
							</form:option>
							<c:forEach items="${command.faYears}" var="lookUp">
								<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
	


		<h3 class="text-center">
			<spring:message code="sfac.fpo.assessment.heading"
				text="FPO Performance Assessment Scoring Sheet" />
		</h3>

	<div class="table-responsive">
					<table class="table table-bordered table-striped" id="fpoAssessEntTable" width="100%">
						<thead>
							<tr>
								<th colspan="3">
									<spring:message code="sfac.analysis" text="Analysis" />
								</th>
							</tr>
							<tr>
								<th width="5%">
									<spring:message code="sfac.key.parameter" text="Key Parameter" />
								</th>
								<th width="5%">
									<spring:message code="sfac.weightage" text="Weightage" />
								</th>
								<th>
									<table class="inner-table">
										<thead>
											<tr>
												<th width="10%"><spring:message code="sfac.sub.parameter" text="Sub Parameter" /></th>
												<th>
													<table class="inner-table-2">
														<thead>
															<tr>
																<th width="5%"><spring:message code="sfac.ass.Condition" text="Condition" /></th>
																<th width="5%"><spring:message code="sfac.sub.weightage" text="Sub Weightage" /></th>
														        <th width="8%" class="required-control"><spring:message code="" text="Marks Awarded" /></th>
																<th width="5%" class="required-control"><spring:message	code="sfac.ass.score" text="Score %" /></th>
																<th width="8%" class="required-control"><spring:message code="sfa.remark" text="Remark" /></th>
													</tr>
														</thead>
													</table>
												</th>
											</tr>
										</thead>
									</table>
								</th>
							</tr>
						</thead>
						<tbody>
						 <c:set var="d" value="0" scope="page"></c:set>
						 <c:set var="w" value="0" scope="page"></c:set>
						 <c:set var="x" value="0" scope="page"></c:set>
							<c:forEach var="assMastDto"
								items="${command.keyParamDtoList}" varStatus="status">
								<tr>
									<td class="text-center vertical-align-middle">${assMastDto.keyParameterDesc}</td>
									<td class="text-center vertical-align-middle">${assMastDto.weightage}</td>
									<c:set var="x" value="${x + assMastDto.weightage}" scope="page" />
									<td>
									 <c:set var="e" value="0" scope="page"></c:set>
										<table class="inner-table">
											<c:forEach var="detDto"
												items="${assMastDto.fpoSubParamDtoList}"
												varStatus="status">
												<tr>
													<td>${detDto.subParameterDesc}</td>
													<td>
													
													<c:set var="f" value="0" scope="page"></c:set>
														<c:forEach var="subDto" items="${detDto.fpoSubParamDetailDtoList}">
															
															<table class="inner-table-2">
																<tr>
																	<td>${subDto.conditionDesc}</td>
																	<td>${subDto.subWeightage}</td>
															    <c:set var="w" value="${w + subDto.subWeightage}" scope="page" />
																	<td>
																		<form:input class="form-control hasNumber" disabled="${command.saveMode eq 'V' ? true : false }" onblur="calculateTotal('${d}${e}${f}');" 
																			 id="marksAwarded${d}${e}${f}" path="keyParamDtoList[${d}].fpoSubParamDtoList[${e}].fpoSubParamDetailDtoList[${f}].marksAwarded" />
																	</td>
																	<td>
																		<form:input class="form-control hasNumber" disabled="${command.saveMode eq 'V' ? true : false }" onblur="calculateScore('${d}${e}${f}');" maxlength="3"
																			onchange="return hasAmount(event, this, 3, 2)" id="score${d}${e}${f}" path="keyParamDtoList[${d}].fpoSubParamDtoList[${e}].fpoSubParamDetailDtoList[${f}].score" />
																	</td>
																	<td>
																		<form:input class="form-control" disabled="true"
																			id="remark${d}${e}${f}" path="keyParamDtoList[${d}].fpoSubParamDtoList[${e}].fpoSubParamDetailDtoList[${f}].remark" />
																	</td>
																</tr>
															</table>
															<c:set var="f" value="${f + 1}" scope="page" />
														</c:forEach>
													</td>
													
												</tr>
												<c:set var="e" value="${e + 1}" scope="page" />
											</c:forEach>
											
										</table>
									</td>
								</tr>
				
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
							</tbody>
							<tfoot>
							<tr>
								<th>
									<spring:message code="sfac.total" text="Total" />
								</th>
								<th>${x}</th>
								<th>
									<table class="inner-table">
										<tr>
									<th></th>
									<th>
										<table class="inner-table-2">
											<tr>
												<th>&nbsp;</th>
												<th>${w}</th>

												<c:if test="${command.saveMode ne 'V'}">
													<th width="10"><form:input class="form-control"
															readonly="true" id="addMarks" path="" value="0.0" /></th>
													<th><form:input class="form-control" readonly="true"
															id="addScore" path="" value="0.0" /></th>
												</c:if>
												<c:if test="${command.saveMode eq 'V'}">
													<th width="10"><form:input class="form-control"
															readonly="true" id="addMarks" path="assementMasterDto.totalMarksAwarded" /></th>
													<th><form:input class="form-control" readonly="true"
															id="addScore" path="assementMasterDto.totalScore" /></th>
												</c:if>
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
				
				
				
		

		<div class="text-center padding-top-10">
			<c:if test="${command.saveMode ne 'V'}">
				<button type="button" class="btn btn-success"
					title='<spring:message code="sfac.button.save" text="Save" />'
					onclick="saveAssessmentDetails(this);">
					<spring:message code="sfac.button.save" text="Save" />
				</button>
				<button type="button" class="btn btn-warning"
					title='<spring:message code="sfac.button.reset" text="Reset"/>'
					onclick="window.location.href ='FPOAssessmentEntry.html'">
					<spring:message code="sfac.button.reset" text="Reset" />
				</button>
			</c:if>
			<apptags:backButton url="AdminHome.html"></apptags:backButton>
		</div>

		</form:form>
	</div>
</div>
</div>
