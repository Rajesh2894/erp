<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript"
	src="js/works_management/workEstimateSummary.js"></script>
<script type="text/javascript"
	src="js/works_management/workEstimateApproval.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- End JSP Necessary Tags -->
<script>
	jQuery('#tab1').addClass('active');
</script>
<!-- Start Breadcrumb -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End Breadcrumb -->

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="wms.work.estimation" text="Work Estimation" />
			</h2>
			<div class="additional-btn">
			  <apptags:helpDoc url="WorkEstimate.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>

			<form:form action="WorkEstimate.html" class="form-horizontal"
				id="workEstimate" name="workEstimate">
				<jsp:include page="/jsp/works_management/workEstimateTab.jsp" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<form:hidden path="" value="${command.saveMode}" id="saveMode" />
				<form:hidden path="modeCpd" id="modeCpd" />
				<div class="form-group">

					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="project.master.projname" text="Project Name" /> </label>
					<div class="col-sm-4">
						<form:select path="projectId"
							Class="form-control chosen-select-no-results" id="projId"
							onchange="getProjCode();"
							disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' || command.projectId ne null}"
							data-rule-required="true">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.projectMasterList}" var="list">
								<form:option value="${list.projId}" code="${list.projCode}">${list.projNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label required-control"
						style="display: none;"><spring:message
							code="project.master.projcode" text="Project Code" /></label>
					<div class="col-sm-4" style="display: none;">
						<form:input path="workProjCode" cssClass="form-control"
							id="projCode" readonly="true" />
					</div>


                    <!--  Defect #80050 -->
					<!-- <div class="form-group"> -->
						<label for="" class="col-sm-2 control-label required-control"><spring:message
								code="work.def.workname" text="" /> </label>
						<div class="col-sm-4">
							<form:select path="newWorkId"
								class="form-control chosen-select-no-results mandColorClass"
								id="workDefination" onchange="getDefinationNumber();"
								data-rule-required="true"
								disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' || command.newWorkId ne null}">
								<form:option value="">
									<spring:message code='work.management.select' />
								</form:option>
								<c:forEach items="${command.workDefinitionDto}" var="workDef">
									<form:option code="${workDef.workcode}"
										value="${workDef.workId }">${workDef.workName }</form:option>
								</c:forEach>
							</form:select>
						</div>

					<!-- </div> -->



					<label class="col-sm-2 control-label required-control"
						style="display: none;"><spring:message
							code="work.defination.number" text="work defination number" /></label>
					<div class="col-sm-4" style="display: none;">
						<form:input path="newWorkCode" cssClass="form-control"
							id="definationNumber" readonly="true" data-rule-required="true" />
					</div>
				</div>

				<div class="form-group" style="display: none;">
					<apptags:select labelCode="work.def.workname" items="${test}"
						path="" selectOptionLabelCode="Select" cssClass="form-control "
						isMandatory="true" />
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control " for=""><spring:message
							code='work.estimate.type' /></label>
					<div class="col-sm-4">
						<form:select id="sorList" class="form-control"
							onchange="getSorByValue();" path="estimateTypeId"
							disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' || command.estimateTypeId ne null}">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<form:option value="S">
								<spring:message code="work.estimate.fromsor"
									text="Estimate from SOR" />
							</form:option>
							<form:option value="P">
								<spring:message code="work.estimate.frompreviouswork"
									text="From Previous work Estimate" />
							</form:option>
							<form:option value="U">
								<spring:message code="work.estimate.fromdirectAbstract"
									text="Direct Abstract" />
							</form:option>
						</form:select>
					</div>
				</div>

				<div id="estimationTagDiv">
					<div class="text-center clear padding-10">
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="backForm();" id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="" />
						</button>
					</div>
				</div>

			</form:form>
		</div>
	</div>
</div>

