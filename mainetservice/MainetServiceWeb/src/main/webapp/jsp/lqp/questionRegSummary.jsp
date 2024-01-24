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
<script type="text/javascript" src="js/lqp/questionRegistration.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="lqp.query.summary.title"
					text="Legislative Question Summary" />
			</h2>
			<apptags:helpDoc url="LegislativeQuestion.html" />
		</div>
		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="LegislativeQuestion.html"
				cssClass="form-horizontal" id="LegislativeQuestion"
				name="LegislativeQuestion">
				<!-- Start Validation include tag -->
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->


				<div class="form-group">
					<label class="control-label col-sm-2" for=""> <spring:message
							code="lqp.dept.name" />
					</label>
					<div class="col-sm-4">
						<form:select path="queryRegMasrDto.deptId" id="deptId"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="asset.info.select" />
							</form:option>
							<c:forEach items="${command.departmentList}" var="obj">
								<form:option value="${obj.dpDeptid}" code="${obj.dpDeptcode}">${obj.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label" for="assetgroup"> <spring:message
							code="lqp.question.type" /></label>
					<apptags:lookupField items="${command.getLevelData('QTP')}"
						path="queryRegMasrDto.questionTypeId"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="Select" isMandatory="false" />

				</div>

				<div class="form-group">
					<apptags:input labelCode="lqp.question.no"
						path="queryRegMasrDto.questionId" isMandatory="false"
						isDisabled="false"></apptags:input>
				</div>

				<!-- date picker input set -->
				<div class="form-group">
					<apptags:date fieldclass="datepicker" labelCode="lqp.fromDate"
						datePath="queryRegMasrDto.fromDate"></apptags:date>
					<apptags:date fieldclass="datepicker" labelCode="lqp.toDate"
						datePath="queryRegMasrDto.toDate"></apptags:date>
				</div>


				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2" title="Search"
						id="searchLegislativeQuery">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="lqp.button.search" text="Search" />
					</button>

					<button type="button"
						onclick="window.location.href='LegislativeQuestion.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="lqp.button.reset" text="Reset" />
					</button>

					<button type="button" class="btn btn-primary"
						onclick="addQueryReg('LegislativeQuestion.html','addQueryReg');"
						title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="lqp.button.add" text="Add" />
					</button>

				</div>
				<!-- End button -->

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="queryRegDataTable">
						<thead>
							<tr>
								<th width="2%" align="center"><spring:message
										code="lqp.srno" text="Sr.No" /></th>
								<th width="10%" align="center"><spring:message code="lqp.question.no"
										text="lqp.question.no" /></th>
								<th width="8%" align="center"><spring:message code="lqp.question.type"
										text="lqp.question.type" /></th>
								<th width="30%" align="center"><spring:message code="lqp.subject"
										text="lqp.subject" /></th>
								<th width="10%" align="center"><spring:message code="lqp.mlaName"
										text="lqp.mlaName" /></th>
								<th width="10%" align="center"><spring:message code="lqp.meetingDate"
										text="lqp.meetingDate" /></th>
								<th width="10%" align="center"><spring:message code="lqp.questionRaisedDate"
										text="lqp.questionRaisedDate" /></th>
								<th width="10%" align="center"><spring:message code="lqp.lastDate"
										text="lqp.lastDate" /></th>
								<th width="10%" align="center"><spring:message code="lqp.action"
										text="Action" /></th>
							</tr>
						<tbody>
							<c:forEach items="${command.queryRegMasrDtoList}"
								var="query" varStatus="index">
								<tr>
									<td class="text-center">${index.count}</td>
									<td align="center">${query.questionId}</td>
									<td align="center">${query.questionType}</td>
									<td align="center">${query.questionSubject}</td>
									<td align="center">${query.mlaName}</td>
									<td align="center"><fmt:formatDate value="${query.meetingDate}" pattern="dd-MM-yyyy" /></td>
									<td align="center"><fmt:formatDate value="${query.questionRaisedDate}" pattern="dd-MM-yyyy" /></td>
									<td align="center"><fmt:formatDate value="${query.deadlineDate}" pattern="dd-MM-yyyy" /></td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm margin-right-5 "
											name="button-plus" id="button-plus"
											onclick="showGridOption(${query.questionRegId},'V')"
											title="<spring:message code="lqp.button.view" text="view"></spring:message>">
											<i class="fa fa-eye" aria-hidden="true"></i>
										</button>

										<button type="button" class="btn btn-danger btn-sm btn-sm margin-right-5" name="button-123"
											id="" onclick="showGridOption(${query.questionRegId},'VA')"
											title="<spring:message code="lqp.button.view.answer" text="view answer"></spring:message>"
											<c:if test="${query.status ne 'CONCLUDED'}">disabled='disabled'</c:if>>
											<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
										</button>
										<!-- Reopen Button -->
										<button type="button" class="btn btn-primary btn-sm margin-right-5" name="button-123"
											id="" onclick="showGridOption(${query.questionRegId},'R')"
											title="<spring:message code="lqp.button.reopen" text="Reopen"></spring:message>"
											<c:if test="${query.status ne 'CONCLUDED'}">disabled='disabled'</c:if>>
											<i class="fa fa-exchange" aria-hidden="true"></i>
										</button>
										
										<!--show if there is Attached file  -->
										<c:if test="${query.isAttachDoc eq 'Y'}">
											<button type="button" class="btn btn-primary btn-sm margin-right-5" name="button-123"
												id="" title="<spring:message code="lqp.button.attachment" text="Attachment"></spring:message>">
												<i class="fa fa-paperclip" aria-hidden="true"></i>
											</button>
										</c:if>
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