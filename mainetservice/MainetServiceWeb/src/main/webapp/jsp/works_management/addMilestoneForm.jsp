<!-- Start JSP Necessary Tags -->
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/works_management/milestone.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="work.management.header.label.milestone.entry" text="Milestone Entry" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="FinancialMilestone.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="Milestone.html" class="form-horizontal"
				id="milestone" name="milestone">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<form:hidden path="mileStoneFlag" id="mileStoneFlag" />
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="project.master.projname" /></label>
					<div class="col-sm-4">
						<form:select path="milestoneEntryDto.projId" id="projId"
							class="form-control chosen-select-no-results"
							onchange="getWorkName(this);">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>

							<c:forEach items="${command.projectMasterList}"
								var="activeProjName">
								<form:option value="${activeProjName.projId }"
									code="${activeProjName.projId }">${activeProjName.projNameEng}</form:option>
							</c:forEach>

						</form:select>

					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.def.workname" /></label>
					<div class="col-sm-4">
						<form:select path="milestoneEntryDto.workId" id="workId"
							class="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
						</form:select>
					</div>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.management.milestone.name" text="Milestone Name" /></label>
					<div class="col-sm-4">
						<form:input path="milestoneEntryDto.milestoneName"
							id="milestoneName" class="form-control" />

					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.management.milestone.percentage" text="Milestone Percentage" /></label>
					<div class="col-sm-4">

						<form:input path="milestoneEntryDto.milestonePer"
							cssClass="form-control mandColorClass text-right"
							onkeypress="return hasAmount(event, this, 3, 2)"
							onblur="getValue();" id="milestonePer" />


					</div>
				</div>

				<div class="text-center clear padding-10">

					<button type="button" id="save" class="btn btn-success btn-submit" title='<spring:message code="works.management.save" text="Save" />'
						onclick="saveMilestoneEntry(this);">
						<spring:message code="works.management.save" text="Save" />
					</button>


					<button class="btn btn-warning" type="button" id="" title='<spring:message code="works.management.reset" text="Reset" />'
						onclick="resetMilestoneForm();">
						<spring:message code="works.management.reset" text="Reset" />
					</button>


					<button type="button" class="button-input btn btn-danger" title='<spring:message code="works.management.back" text="Back" />'
						name="button-Cancel" value="Cancel" style=""
						onclick="window.location.href='Milestone.html'" id="button-Cancel">
						<spring:message code="works.management.back" text="Back" />
					</button>

				</div>
			</form:form>
		</div>
	</div>
</div>