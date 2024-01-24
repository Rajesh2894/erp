<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/assetRevaluation.js"></script>
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="asset.revaluation.header" text="Asset Revaluation" />
				</h2>
				<apptags:helpDoc url="AssetSearch.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<form:form id="astRevaluate" action="AssetRevaluation.html"
					method="post" class="form-horizontal">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<form:hidden path="revaluationDTO.assetId" />
					<form:hidden path="audit.empId" />
					<form:hidden path="audit.empIpMac" />
					<form:hidden path="revaluationDTO.orgId" />
					<form:hidden path="accountLive" id="accountLive"/>
					<input type="hidden" id="moduleDeptUrl"  value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
					<div class="table-responsive clear">
						<table class="table table-striped table-bordered margin-bottom-20" id="revaluatePage">
							<thead>
								<tr>
									<th width="10%" scope="col" align="center"><spring:message
											code="asset.retire.astcode" /></th>
									<th width="10%" scope="col" align="center"><spring:message
											code="asset.revaluation.assetClass" /></th>
									<th width="10%" scope="col" align="center"><spring:message
											code="asset.revaluation.description" /></th>
									<th width="10%" scope="col" align="center"><spring:message
											code="asset.revaluation.currentValue" /></th>
									<th width="10%" scope="col" align="center"><spring:message
											code="asset.revaluation.costCenter" /></th>
									<th width="10%" scope="col" align="center"><spring:message
											code="asset.revaluation.location" /></th>
									<th width="10%" scope="col" align="center"><spring:message
											code="asset.revaluation.department" /></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><form:input id="OISrNo" path="revaluationDTO.serialNo"
											disabled="true" cssClass="form-control" /></td>
									<td><form:input path="revaluationDTO.assetClassDesc"
											id="revaluateAssetClass" cssClass="form-control" disabled="true" /></td>
									<td><form:input path="revaluationDTO.description"
											id="revaluateDescription" cssClass="form-control"
											disabled="true" /></td>
									<td><form:input path="revaluationDTO.currentValue"
											id="revaluateCurrentValue" cssClass="decimal text-right form-control" disabled="true" /></td>
									<td><form:input path="revaluationDTO.costCenterDesc"
											id="revaluateCostCenter" cssClass="form-control" disabled="true" /></td>
									<td><form:input path="revaluationDTO.locationDesc"
											id="revaluateLocation" cssClass="form-control" disabled="true" />
									</td>
									<td><form:input path="revaluationDTO.departmentDesc"
											id="revaluateDepartment" cssClass="form-control" disabled="true" /></td>
								</tr>
							</tbody>
						</table>
					</div>
					
					<div class="form-group">
						<apptags:input labelCode="asset.revaluation.amount"
								cssClass="decimal text-right form-control required-control" isDisabled="${(mode != null) && (mode eq 'APR')}"
								path="revaluationDTO.newAmount" maxlegnth="18" isMandatory="true" isReadonly="${revalMode != null && revalMode eq 'IMP'}"></apptags:input><i id="updatedAmountIcon"></i>
					</div>
					<c:if test='${revalMode != null && revalMode eq "IMP"}'>
						<input type="hidden" id="revalMode" value="IMP"/>
					</c:if>
					<div class="form-group p_element">
						<label class="col-sm-2 control-label required-control" for=""> <spring:message
									code="asset.revaluation.impType" /></label>
						<c:set var="impTypeCode" value="IMT" />
						<apptags:lookupField
								items="${command.getLevelData(impTypeCode)}"
								path="revaluationDTO.impType" disabled="${(mode != null) && (mode eq 'APR')}" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="true"
							 />
						<apptags:input labelCode="asset.revaluation.impCost"
							cssClass="decimal text-right form-control required-control" isDisabled="${(mode != null) && (mode eq 'APR')}"
							path="revaluationDTO.impCost" maxlegnth="18" isMandatory="true"></apptags:input>
					</div>
					<div class="form-group p_element">
						<apptags:input labelCode="asset.revaluation.updatedUsefulLife"
							cssClass="decimal text-right form-control required-control" maxlegnth="6" isDisabled="${(mode != null) && (mode eq 'APR')}"
							path="revaluationDTO.updUsefulLife" isMandatory="false"></apptags:input>
						<apptags:input labelCode="asset.revaluation.payAdivceNo"
							path="revaluationDTO.payAdviceNo" maxlegnth="100" isDisabled="${(mode != null) && (mode eq 'APR')}"
							cssClass="alphaNumeric" isMandatory="true"></apptags:input>
					</div>
					<input type="hidden" value="${command.revaluationDTO.updUsefulLife}" id="hiddenLife"/>
					<div class="form-group p_element">
						<apptags:textArea labelCode="asset.revaluation.impDesc" maxlegnth="500"
							path="revaluationDTO.impDesc" isDisabled="${(mode != null) && (mode eq 'APR')}"
							cssClass="alphaNumeric" isMandatory="true"></apptags:textArea>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label required-control" for=""><spring:message
								code="asset.revaluation.docDate" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input class="form-control datepicker" id="revaluateDocDate" maxlength="10"
									path="revaluationDTO.docDate" isMandatory="true" disabled="${(mode != null) && (mode eq 'APR')}" ></form:input>
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<apptags:textArea labelCode="asset.revaluation.remarks" maxlegnth="500"
								path="revaluationDTO.remarks" isDisabled="${(mode != null) && (mode eq 'APR')}"
								cssClass="alphaNumeric"></apptags:textArea>
					</div>
					
					<c:if test='${mode == null}'>
						<div class="text-center">
							<button type="button" class="button-input btn btn-success"
								name="button" value="Save" onclick="saveRevaluation(this)" id="save">
								<spring:message code="asset.revaluation.save" />
							</button>
							<%-- <button type="Reset" class="btn btn-warning"
								onclick="resetRevaluation()">
								<spring:message code="reset.msg" text="Reset" />
							</button> --%>
							<apptags:resetButton></apptags:resetButton>
							<button type="button" class="btn btn-danger" id="back"
								onclick="backRevaluation();">
								<spring:message code="back.msg" text="Back" />
							</button>
						</div>
					</c:if>
				</form:form>
			</div>
			<c:if test='${(mode != null) && (mode eq "APR")}'>
							<form:form action="AssetRevaluation.html" id="assetRevaluationApproval"
					method="post" class="form-horizontal">
					<div class="widget-content padding panel-group accordion-toggle"
						id="accordion_single_collapse1">
						<apptags:CheckerAction hideSendback="true" hideForward="true"></apptags:CheckerAction>
					</div>
					<div class="text-center widget-content padding">
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="saveRevaluationAction(this);">
							<spring:message code="asset.transfer.save" text="Save" />
						</button>
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel"
							onclick="window.location.href='AdminHome.html'"
							id="button-Cancel">
							<spring:message code="asset.information.back" text="Back" />
						</button>
					</div>
				</form:form>
				<!-- Defect #3086 Resolution-->	
				<%-- <div class="form-group">
					<form:form action="AssetRevaluation.html" id="assetRevaluationApproval" method="post">
						<!-- It gets enable only when approval status is pending -->
							<apptags:CheckerAction hideForward="true" hideSendback="true"></apptags:CheckerAction>
		
							<button type="button" id="save" class="btn btn-success"
								onclick="saveRevaluationAction(this);">
								<spring:message code="master.save" text="" />
							</button>
							<button type="button" class="button-input btn btn-danger"
								name="button-Cancel" value="Cancel"
								onclick="window.location.href='AdminHome.html'" id="button-Cancel">
								<spring:message code="scheme.master.back" text="" />
							</button>
					</form:form>
				</div> --%>
			</c:if>
		</div>
	</div>
</div>
<script>
$(document).ready(function() {
	<c:choose>
	<c:when test='${(revalMode != null) && (revalMode eq "IMP")}'>
		$(".p_element").show();
	</c:when>
	<c:otherwise>
		$(".p_element").hide();
	</c:otherwise>
	</c:choose>
});
</script>