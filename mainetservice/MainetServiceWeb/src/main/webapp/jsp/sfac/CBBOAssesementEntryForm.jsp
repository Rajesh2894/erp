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

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.cbbo.assessment.entry.title"
					text="CBBO Assessment Entry Form" />
			</h2>
			<apptags:helpDoc url="CBBOAssessmentEntry.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="CBBOAssessmentEntryForm"
				action="CBBOAssessmentEntry.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.CBBO.name" text="CBBO Name" /></label>
					<c:if test="${command.saveMode eq 'A'}">
						<div class="col-sm-4">
							<form:select path="assementMasterDto.cbboId" id="cbboId"
								disabled="${command.saveMode eq 'V' ? true : false }"
								cssClass="form-control chosen-select-no-results">
								<form:option value="0">
									<spring:message text="Select" code="sfac.select'" />
								</form:option>
								<c:forEach items="${command.cbboMasterDtoList}" var="dto">
									<form:option value="${dto.cbboId}" code="${dto.cbboName}">${dto.cbboName}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>
					<c:if test="${command.saveMode eq 'V'}">
						<div class="col-sm-4">
							<form:input class="form-control" id="cbboName"
								path="assementMasterDto.cbboName" readonly="true" />
						</div>
					</c:if>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.assement.year" text="Assement Year" /></label>
					<div class="col-sm-4">
						<form:select path="assementMasterDto.finYrId" id="finYrId" disabled="${command.saveMode eq 'V' ? true : false }"
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

			<%-- 	<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success" id="continueForm" 
						onclick="generateAssementNo(this);" >
						<spring:message code="sfac.proceed" />
					</button>
					<apptags:backButton url="AdminHome.html" isDisabled="${command.showParamTables eq 'Y' ? true : false }"></apptags:backButton>

				</div>
				 --%>
         <%--     <c:if test="${command.showParamTables eq 'Y' }"> --%>
				<%-- <h3 class="text-center">
					<spring:message code="sfac.cbbo.assessment.heading.1" text="CBBO Assessment Entry Form - Year 1" />
				</h3>
				<h5 class="text-center">
					<spring:message code="sfac.cbbo.assessment.heading.2" text="Central Sector Scheme Formation and Promotion of 10,000 Farmer Producer Organizations" />
				</h5> --%>
				
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
																<th width="8%" class="required-control"><spring:message code="sfac.ass.overallScore" text="Overall Score" /></th>
																<th width="8%" class="required-control"><spring:message code="sfac.ass.regFpoFullfilling" text="Registered FPOs fulfilling criteria" /></th>
																<th width="5%" class="required-control"><spring:message code="sfac.ass.score" text="Score %" /></th>
															</tr>
														</thead>
													</table>
												</th>
												<th width="10%"><spring:message code="sfac.means.of.verification" text="Mode of Verification" /></th>
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
																	<td>
																		<form:input class="form-control" disabled="${command.saveMode eq 'V' ? true : false }" onblur="calculateScore('${d}${e}${f}');" 
																			onkeypress="return hasAmount(event, this, 3, 2)" id="overallScore${d}${e}${f}" path="assementKeyParamDtoList[${d}].assSubParamDtoList[${e}].assSubParamDetailDtoList[${f}].overallScore" />
																	</td>
																	<td>
																		<form:input class="form-control hasNumber" disabled="${command.saveMode eq 'V' ? true : false }" onblur="calculateTotal('${d}${e}${f}');" maxlength="3"
																			id="regiFpoCriteria${d}${e}${f}" path="assementKeyParamDtoList[${d}].assSubParamDtoList[${e}].assSubParamDetailDtoList[${f}].regiFpoCriteria" />
																	</td>
																	<td>
																		<form:input class="form-control" disabled="${command.saveMode eq 'V' ? true : false }" readonly="true" onchange="return hasAmount(event, this, 3, 2)"
																			id="score${d}${e}${f}" path="assementKeyParamDtoList[${d}].assSubParamDtoList[${e}].assSubParamDetailDtoList[${f}].score" />
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
														
														<c:if test="${command.saveMode ne 'V'}">
														<th width="10"><form:input class="form-control" readonly="true" id="addOverallScore" path=""  value="0.0"/></th>
														<th><form:input class="form-control"  readonly="true" id="addRegFulfill" path="" value="0.0"/></th>
													    <th><form:input class="form-control"  readonly="true" id="addTotalScore" path="" value="0.0"/></th>
												         </c:if>
												         <c:if test="${command.saveMode eq 'V'}">
												         <th width="10"><form:input class="form-control" readonly="true" id="addOverallScore" path="assementMasterDto.totalOverallScore"/></th>
														<th><form:input class="form-control"  readonly="true" id="addRegFulfill" path="assementMasterDto.totalFpoFullFilling"/></th>
													    <th><form:input class="form-control"  readonly="true" id="addTotalScore" path="assementMasterDto.totalScore" /></th>
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
				
				<label class="text-blue-2 margin-top-30"><spring:message code="sfac.points.to.consider" text="Points To Consider" /></label>
				<ul class="imp-notes margin-bottom-30">
					<li>
						<spring:message code="sfac.cbbo.assessment.entry.note.1" text="For FY 2020-21" />
					</li>
					<li>
						<spring:message code="sfac.cbbo.assessment.entry.note.2" text="Resultant Performance %age indicates the on ground effectiveness quotient of CBBO." />
					</li>
					<li>
						<spring:message code="sfac.cbbo.assessment.entry.note.3" text="Acceptable performance threshold is considered as 70%" />
					</li>
				</ul>
				
			<%-- 	<c:set var="d" value="0" scope="page"></c:set>
				<div class="table-responsive">
					<table class="table table-bordered table-striped"
						id="cbboAssessEntTable">
						<thead>
							<tr>
								<th width="5%"><spring:message code="sfac.sr.no"
										text="Sr. No." /></th>
								<th><spring:message code="sfac.CBBO.name" text="CBBO Name" />
								</th>
								<th width="15%"><spring:message
										code="sfac.assessment.action" text="Assesment Action" /></th>
								<th width="8%"><spring:message code="sfac.action"
										text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="cbboDetDto"
								items="${command.assementMasterDto.cbboAssDetailDtoList}" varStatus="status">
								<tr class="cbboDetailTable">
									<td align="center"><form:input path=""
											cssClass="form-control mandColorClass" id="sNo${d}"
											value="${d+1}" disabled="true" /></td>
									<td><form:select
											path="assementMasterDto.cbboAssDetailDtoList[${d}].cbboId"
											class="form-control" id="cbboId${d}">
											<form:option value="0">
												<spring:message code="sfac.select" text="Select" />
											</form:option>
											<c:forEach items="${command.cbboMasterDtoList}" var="dto">
												<form:option code="${dto.cbboId}"
													value="${dto.cbboId}">${dto.cbboName}</form:option>
											</c:forEach>
										</form:select></td>


									<td class="text-center">
										<button type="button"
											class="btn btn-blue-1 btn-sm"
											title="<spring:message code="sfac.button.initiate" text="Initiate"></spring:message>"
											onclick="sendForApproval(${assementMasterDto.assId},'${assementMasterDto.assessmentNo}',${cbboDetDto.cbboId});">
											<i class="fa fa-share-square-o"></i>
										</button></td>
										<td class="text-center"><a id="" onclick="addRow(this);"
										class="btn btn-blue-2 btn-sm"> <i class="fa fa-plus"></i></a>
										<a href="javascript:void(0);" class="btn btn-danger btn-sm" onclick="deleteRow(this);"
										id=""> <i class="fa fa-minus"></i></span>
									</a></td>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</tbody>
					</table>
				</div> --%>

				<div class="text-center padding-top-10">
				<c:if test="${command.saveMode ne 'V'}">
					<button type="button" class="btn btn-success"
						title='<spring:message code="sfac.button.save" text="Save" />'
						onclick="saveAssessmentDetails(this);">
						<spring:message code="sfac.button.save" text="Save" />
					</button>
					<button type="button" class="btn btn-warning"
						title='<spring:message code="sfac.button.reset" text="Reset"/>'
						onclick="window.location.href ='CBBOAssesementEntry.html'">
						<spring:message code="sfac.button.reset" text="Reset" />
					</button></c:if>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
				
				<%-- </c:if> --%>
				
			</form:form>
		</div>
	</div>
</div>