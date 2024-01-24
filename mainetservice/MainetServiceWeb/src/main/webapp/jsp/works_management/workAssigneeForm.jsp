<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<link rel="stylesheet"
	href="assets/libs/bootstrap-multiselect/css/bootstrap-multiselect.css"
	type="text/css">
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="js/works_management/workAssignee.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.assignee" text="Work Assignee" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>
			<form:form action="WorkAssignee.html" class="form-horizontal"
				name="WorkAssignee" id="WorkAssignee" modelAttribute="command">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<form:hidden path="mode" id="mode" />
				<%-- <form:hidden path="workOrderId"/> --%>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.order.workOrder.no" text="Work Order No." /></label>
					<div class="col-sm-4">
						<form:select path="workOrderId" data-rule-required="true"
							cssClass="form-control   mandColorClass" onChange="getWorks();"
							id="workOrderId"
							disabled="${command.mode eq 'V' || command.mode eq 'E'}">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.workOderList}" var="WOArray">
								<form:option value="${WOArray[0]}">${WOArray[1]}</form:option>
							</c:forEach>
						</form:select>

					</div>
					<label class="col-sm-2 control-label "><spring:message
							code="" text="Project Name" /></label>
					<div class="col-sm-4">
						<form:input path="workOderDto.tenderMasterDto.projectName"
							id="projectName" class="form-control" readonly="true" />
					</div>

				</div>
				<div class="form-group">


					<label class="col-sm-2 control-label "><spring:message
							code="o" text="Tender No." /></label>
					<div class="col-sm-4">
						<form:input path="workOderDto.tenderMasterDto.tenderNo"
							id="tenderNo" class="form-control" readonly="true" />
					</div>

					<label class="col-sm-2 control-label "><spring:message
							code="o" text="Tender Date" /></label>
					<div class="col-sm-4">
						<form:input path="workOderDto.tenderMasterDto.tenderDate"
							id="tenderDate" class="form-control" readonly="true" />

					</div>

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="Work Assignee" text="Work Assignee" /></label>
					<div class="col-sm-4">
						<form:select path="empId" cssClass="form-control mandColorClass "
							id="assignee" data-rule-required="true">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.empList}" var="emp">
								<form:option value="${emp[3]}"
									label="${emp[0]} ${emp[2]}  => ${emp[4]}"></form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>


				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.def.workCode" text="Work Code" /></label>
					<c:if test="${command.mode eq 'C' }">
						<div class="col-sm-4">
							<form:select path="assignedWorkIds"
								cssClass="form-control mandColorClass " multiple="multiple"
								data-rule-required="true" id="workCode">
								<form:option value="">
									<spring:message code='work.management.select' />
								</form:option>
							</form:select>
						</div>
					</c:if>
					<c:if test="${command.mode eq 'E' || command.mode eq 'V' }">
						<div class="col-sm-4">
							<form:select path="assignedWorkIds"
								cssClass="form-control mandColorClass" multiple="multiple"
								data-rule-required="true" id="workCode">
								<%-- <form:option value="">
								<spring:message code='work.management.select' />
							</form:option> --%>
								<c:forEach
									items="${command.workOderDto.tenderMasterDto.workDto}"
									var="work">
									<form:option value="${work.workId}"
										label="${work.workCode}  >> ${work.workName}"></form:option>
								</c:forEach>
							</form:select>
						</div>

					</c:if>

				</div>

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<c:if test="${command.mode eq 'C' || command.mode eq 'E'}">
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="saveAssignee(this);">
							<spring:message code="works.management.save" text="Save" />
						</button>
					</c:if>
					<c:if test="${command.mode eq 'C' }">
						<button class="btn btn-warning" type="button" id="resetAssignee">
							<spring:message code="works.management.reset" text="Reset" />
						</button>
					</c:if>

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel"
						onclick="window.location.href='WorkAssignee.html'"
						id="button-Cancel">
						<spring:message code="works.management.back" text="Back" />
					</button>

				</div>

			</form:form>
		</div>
	</div>
</div>