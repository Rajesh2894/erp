<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script type="text/javascript"
	src="js/asset/chartOfDepreciationMaster.js"></script>
<!-- End JSP Necessary Tags -->
<script>
$(function(){
    $(".chzn-select").chosen();
});
</script>
<!-- Start Breadcrumb -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End Breadcrumb -->

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="asset.depreciationMaster.header" />
			</h2>
			<apptags:helpDoc url="ChartOfDepreciationMaster.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">

			<form:form action="ChartOfDepreciationMaster.html"
				class="form-horizontal" name="depreciationMaster"
				id="depreciationMaster">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<input type="hidden" id="moduleDeptUrl"  value="${userSession.moduleDeptCode == 'AST' ? 'ChartOfDepreciationMaster.html':'ITChartOfDepreciationMaster.html'}">
				<form:hidden path="assetChartOfDepreciationMasterDTO.groupId"
					id="groupId" />
				<c:if test="${command.modeType eq 'E'}">
				<form:hidden path="assetChartOfDepreciationMasterDTO.name"
					id="name" />
				<form:hidden path="assetChartOfDepreciationMasterDTO.assetClass"/></c:if>
				<form:input type="hidden" path="modeType"
					value="${command.modeType}" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="asset.depreciationMaster.header" />
								</a>
							</h4>
						</div>

						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<apptags:input labelCode="asset.depreciationMaster.groupId"
										path="assetChartOfDepreciationMasterDTO.groupId"
										isMandatory="false"
										isDisabled="${command.modeType eq 'C'|| command.modeType eq 'V' || command.modeType eq 'E' || command.modeType eq 'D'}"></apptags:input>
									<%-- <apptags:input labelCode="asset.depreciationMaster.groupName"
										path="assetChartOfDepreciationMasterDTO.name" onblur="validateDepreciationName(this)"
										cssClass="hasCharacter" isMandatory="true" maxlegnth="50"
										isDisabled="${command.modeType eq 'V'}"></apptags:input> --%>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="asset.depreciationMaster.groupName" /></label>
									<div class="col-sm-4">
									<form:input	path="assetChartOfDepreciationMasterDTO.name" cssClass="form-control" id="name" maxlength="100"
												data-rule-required="true" onblur="validateDuplicateName(this)" isMandatory="true" autofocus="true"
												disabled="${command.modeType eq 'V'|| command.modeType eq 'E'}"/>
									</div>
								</div>
								
								<div class="form-group">
									<apptags:input labelCode="asset.depreciationMaster.remarks"
										path="assetChartOfDepreciationMasterDTO.remark"
										cssClass="alphaNumeric" isMandatory="false" maxlegnth="500"
										isDisabled="${command.modeType eq 'V'}"></apptags:input>
							    <c:if test="${accountFlag eq true}">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="asset.depreciationMaster.accountCode" /></label>
									<div class="col-sm-4">
										<form:select
											path="assetChartOfDepreciationMasterDTO.accountCode"
											id="accountCode" data-rule-required="true" 
											disabled="${command.modeType eq 'V'}"
											cssClass="form-control mandColorClass chzn-select">
											<form:option value="0" selected="selected">
												<spring:message code="asset.info.select" />
											</form:option>
											<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
											<c:forEach items="${accountCodeAst}" var="lookUp">
												<form:option value="${lookUp.descLangFirst}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
											</c:if>
											<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
											<c:forEach items="${accountCodeAst}" var="lookUp">
												<form:option value="${lookUp.descLangFirst}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
											</c:if>
										</form:select>
									</div>
									</c:if>
									<c:if test="${accountFlag ne true}">
									<apptags:input labelCode="asset.depreciationMaster.accountCode"
								path="assetChartOfDepreciationMasterDTO.accountCode"
								isDisabled="false" isMandatory="true"
								cssClass="alphaNumeric" maxlegnth="100"></apptags:input>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="table-responsive clear">
					<table class="table table-striped table-bordered" id="dtCdmForm">
						<thead>
							<tr>
								<th width="1%" scope="col" align="center"><spring:message
										code="asset.depreciationMaster.srNo" /></th>
								<th width="10%" scope="col" align="center" class="col-sm-2 required-control"><spring:message
										code="asset.depreciationMaster.assetClass" /></th>
								<th width="10%" scope="col" align="center" class="col-sm-2 required-control"><spring:message
										code="asset.depreciationMaster.frequency" /></th>
								<th width="5%" scope="col" align="center"><spring:message
										code="asset.depreciationMaster.rate" /></th>
								<th width="10%" scope="col" align="center" class="col-sm-2 required-control"><spring:message
										code="asset.depreciationMaster.depreciationKey" /></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><form:input type="text" id="OISrNo" value="1"
										disabled="disabled" path="" cssClass="form-control" /></td>
								<td>
									<div class="col-sm-16">
										<%-- <c:set var="baseLookupCode" value="IMO" />
										<form:select
											path="assetChartOfDepreciationMasterDTO.assetClass"
											cssClass="form-control mandColorClass" id="cdmAstCls"
											disabled="${command.modeType eq 'V'}">
											<form:option value="">
												<spring:message code='' text="Select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select> --%>
										
										<form:select
											path="assetChartOfDepreciationMasterDTO.assetClass"
											id="cdmAstCls" data-rule-required="true" isMandatory="true"
											disabled="${command.modeType eq 'V' || command.modeType eq 'E'}" onchange="validateClass(this)"
											cssClass="form-control required-control">
											<form:option value="">
												<spring:message code="asset.info.select" />
											</form:option>
											<c:forEach items="${assetClassAST}" var="lookUp">
												<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>

									</div>
								</td>

								<td>
									<div class="col-sm-16">
										<c:set var="baseLookupCode" value="${userSession.moduleDeptCode == 'AST' ? 'PRF':'IRF'}" />
										<form:select
											path="assetChartOfDepreciationMasterDTO.frequency"
											cssClass="form-control mandColorClass" id="cdmFreq"
											disabled="${command.modeType eq 'V'}">
											<form:option value="">
												<spring:message code='asset.info.select' text="Select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>

									</div>
								</td>

								<td><form:input
										path="assetChartOfDepreciationMasterDTO.rate" id="cdmRate"
										maxlength="5" cssClass="decimal text-right form-control"
										disabled="${command.modeType eq 'V'}" /></td>

								<td>
									<div class="col-sm-16">
										<c:set var="deprLookupCode" value="DPK" />
										<form:select
											path="assetChartOfDepreciationMasterDTO.depreciationKey"
											cssClass="form-control mandColorClass" id="cdmDepreKey"
											disabled="${command.modeType eq 'V'}">
											<form:option value="">
												<spring:message code='asset.info.select' text="Select" />
											</form:option>
											<c:forEach items="${command.getLevelData(deprLookupCode)}"
												var="deprlookUp">
												<form:option value="${deprlookUp.lookUpId}"
													code="${deprlookUp.lookUpCode}">${deprlookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>

									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="text-center clear padding-10">
					<c:if test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D'}">
						<button class="btn btn-success btn"
							onclick="saveAssetChartOfDepreciationMaster(this);" id="save"
							type="button" title='<spring:message code="asset.depreciationMaster.save" text="Save" />'>
							<i class="button-input"></i>
							<spring:message code="asset.depreciationMaster.save" text="Save" />
						</button>
					</c:if>
					<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
						<button type="Reset" class="btn btn-warning" title='<spring:message code="reset.msg" text="Reset" />'
							onclick="resetCdmForm()">
							<spring:message code="reset.msg" text="Reset" />
						</button>
					</c:if>
					<button type="button" class="btn btn-danger" id="back" title='<spring:message code="back.msg" text="Back" />'
						onclick="BackCDM();">
						<spring:message code="back.msg" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>