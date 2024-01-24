<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/assetRetirement.js"></script>
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="asset.retire.header" text="Asset Retirement" />
				</h2>
				<apptags:helpDoc url="AssetSearch.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<form:form id="astRetire" action="AssetRetirement.html"
					method="post" class="form-horizontal">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<form:hidden path="retireDTO.assetId" />
					<form:hidden path="audit.empId" />
					<form:hidden path="audit.empIpMac" />
					<form:hidden path="retireDTO.orgId" />
					<form:hidden path="accountLive" id="accountLive" />
					<input type="hidden" id="moduleDeptUrl"
						value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
					<input type="hidden" value="${command.taxMasterError}"
						id="taxMasErrId">
					<c:set var="assetFlag"
						value="${userSession.moduleDeptCode == 'AST' ? true : false}" />
					<div class="table-responsive clear">
						<table class="table table-striped table-bordered margin-bottom-20"
							id="retirePage">
							<thead>
								<tr>
									<th width="10%" scope="col" align="center"><spring:message
											code="asset.retire.astcode" /></th>
									<c:choose>

										<c:when test="${assetFlag}">
											<th width="10%" scope="col" align="center"><spring:message
													code="asset.retire.assetClass" /></th>
										</c:when>
										<c:otherwise>
											<th width="10%" scope="col" align="center"><spring:message
													code="asset.information.hardwareName" /></th>
										</c:otherwise>
									</c:choose>

									<th width="10%" scope="col" align="center"><spring:message
											code="asset.retire.description" /></th>
									<th width="10%" scope="col" align="center"><spring:message
											code="asset.retire.currentValue" /></th>
									<th width="10%" scope="col" align="center"><spring:message
											code="asset.retire.costCenter" /></th>

									<c:if test="${assetFlag}">
										<th width="10%" scope="col" align="center"><spring:message
												code="asset.retire.location" /></th>
										<th width="10%" scope="col" align="center"><spring:message
												code="asset.retire.department" /></th>
									</c:if>


								</tr>
							</thead>
							<tbody>
								<tr>
									<td><form:input id="OISrNo" path="retireDTO.serialNo"
											disabled="true" cssClass="form-control" /></td>
									<td><form:input path="retireDTO.assetClassDesc"
											id="retireAssetClass" cssClass="form-control" disabled="true" /></td>
									<td><form:input path="retireDTO.description"
											id="retireDescription" cssClass="form-control"
											disabled="true" /></td>
									<td><form:input path="retireDTO.currentValue"
											id="retireCurrentValue" cssClass="text-right form-control"
											disabled="true" /></td>
									<td><form:input path="retireDTO.costCenterDesc"
											id="retireCostCenter" cssClass="form-control" disabled="true" /></td>
									<c:if test="${assetFlag}">
											<td><form:input path="retireDTO.locationDesc"
													id="retireLocation" cssClass="form-control" disabled="true" />
											</td>
											<td><form:input path="retireDTO.departmentDesc"
													id="retireDepartment" cssClass="form-control"
													disabled="true" /></td>
										</c:if>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label required-control" for="">
							<spring:message code="asset.retire.dispositionMethod" />
						</label>
						<c:set var="baseLookupCode"
							value="${userSession.moduleDeptCode == 'AST' ? 'DCM':'ICM'}" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="retireDTO.dispositionMethod"
							disabled="${(mode != null) && (mode eq 'APR')}"
							cssClass="form-control" hasChildLookup="false" hasId="true"
							showAll="false" selectOptionLabelCode="Select" isMandatory="true"
							changeHandler="getdispositionMethod();" />
						<label class="col-sm-2 control-label required-control" for=""
							id="dispoDate"><spring:message
								code="asset.retire.dispositionDate" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input class="form-control datepicker" id="dispositionDate"
									maxlength="10" data-label="#dispoDate"
									path="retireDTO.dispositionDate" isMandatory="true"
									disabled="${(mode != null) && (mode eq 'APR')}"></form:input>
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
					</div>
					<div id="sellOptionalData" class="p_element">
						<div class="form-group">


							<apptags:input labelCode="asset.retire.disordernum"
								maxlegnth="30" path="retireDTO.disOrderNumber"
								isDisabled="${(mode != null) && (mode eq 'APR')}"
								cssClass="alphaNumeric" isMandatory="true"></apptags:input>


							<label class="col-sm-2 control-label required-control" for=""
								id="disOrdate"><spring:message
									code="asset.retire.disorderdate" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input class="form-control datepicker" id="disOrderDate"
										maxlength="10" data-label="#disOrdate"
										path="retireDTO.DisOrderDate" isMandatory="true"
										disabled="${(mode != null) && (mode eq 'APR')}"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>

						</div>
						<div class="form-group">
							<apptags:input labelCode="asset.retire.soldto.name"
								maxlegnth="100" path="retireDTO.soldToName"
								isDisabled="${(mode != null) && (mode eq 'APR')}"
								cssClass="alphaNumeric" isMandatory="true"></apptags:input>
							<apptags:textArea labelCode="asset.retire.soldto.address"
								maxlegnth="500" path="retireDTO.soldToAddress"
								isDisabled="${(mode != null) && (mode eq 'APR')}"
								cssClass="alphaNumeric form-control"></apptags:textArea>
						</div>
						<div class="form-group">
							<apptags:input labelCode="asset.retire.amount" maxlegnth="18"
								cssClass="decimal text-right form-control required-control"
								isDisabled="${(mode != null) && (mode eq 'APR')}"
								path="retireDTO.amount" isMandatory="true"></apptags:input>

							<label class="control-label col-sm-2 required-control"
								for="retireChartOfAccount"> <spring:message
									code="asset.retire.chartOfAccount" />
							</label>
							<div class="col-sm-4">
								<form:select path="retireDTO.capitalChartOfAccount"
									disabled="${(mode != null) && (mode eq 'APR')}"
									cssClass="form-control required-control"
									id="retireChartOfAccount">
									<form:option value="">
										<spring:message code='asset.info.select' text="Select" />
									</form:option>
									<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
										<c:forEach items="${command.taxMasAccHead}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
										</c:forEach>
									</c:if>
									<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
										<c:forEach items="${command.taxMasAccHead}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.descLangSecond}</form:option>
										</c:forEach>
									</c:if>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2 required-control" for="">
								<spring:message code="asset.paymode" />
							</label>
							<div class="col-sm-4 ">
								<form:select path="retireDTO.payMode"
									disabled="${(mode != null) && (mode eq 'APR')}"
									cssClass="form-control required-control" id="payMode"
									onchange="getpaymodeDetails()">
									<form:option value="">
										<spring:message code='asset.info.select' text="Select" />
									</form:option>
									<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
										<c:forEach items="${command.paymode}" varStatus="status"
											var="levelChild">
											<form:option value="${levelChild.lookUpId}"
												code="${levelChild.lookUpCode}">${levelChild.descLangFirst}</form:option>
										</c:forEach>
									</c:if>
									<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
										<c:forEach items="${command.paymode}" varStatus="status"
											var="levelChild">
											<form:option value="${levelChild.lookUpId}"
												code="${levelChild.lookUpCode}">${levelChild.descLangSecond}</form:option>
										</c:forEach>
									</c:if>
								</form:select>
							</div>
						</div>
						<!-- bank details start -->
						<div id=getpaymentmodeDetails>
							<div class="form-group">
								<label class="control-label col-sm-2 required-control" for="">
									<spring:message code="asset.retire.bankname" text="Bank Name" />
								</label>
								<div class="col-sm-4">
									<form:select path="retireDTO.bankId"
										disabled="${(mode != null) && (mode eq 'APR')}"
										cssClass="form-control required-control" id="bankName">
										<form:option value="">
											<spring:message code='asset.info.select' text="Select" />
										</form:option>
										<c:forEach items="${command.bankList}" varStatus="status"
											var="cbBankid">
											<form:option value="${cbBankid.bankId}"
												code="${cbBankid.bankId}">${cbBankid.bank} - ${cbBankid.branch} - ${cbBankid.ifsc}</form:option>
										</c:forEach>
									</form:select>
								</div>
								<apptags:input labelCode="asset.retire.instrumentno"
									maxlegnth="8" path="retireDTO.chequeNo"
									isDisabled="${(mode != null) && (mode eq 'APR')}"
									cssClass="hasMobileNo" isMandatory="true"></apptags:input>

							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label required-control" for=""
									id="instDate"><spring:message
										code="asset.retire.instrumentdate" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input class="form-control datepicker"
											id="instrumentDate" maxlength="10" data-label="#instDate"
											path="retireDTO.chequeDate" isMandatory="true"
											disabled="${(mode != null) && (mode eq 'APR')}"></form:input>
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
									</div>
								</div>

							</div>


						</div>
						<!--bank details end -->
					</div>
					<div class="form-group">
						<apptags:textArea labelCode="asset.retire.remarks" maxlegnth="500"
							path="retireDTO.remarks"
							isDisabled="${(mode != null) && (mode eq 'APR')}"
							cssClass="alphaNumeric"></apptags:textArea>

						<label class="col-sm-2 control-label required-control" for=""
							id=rnonfucdate><spring:message
								code="asset.retire.nonfunctiondate" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input class="form-control datepicker" id="nonfucDate"
									maxlength="10" data-label="#rnonfucdate"
									path="retireDTO.nonfucDate" isMandatory="true"
									disabled="${(mode != null) && (mode eq 'APR')}"></form:input>
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>

					</div>

					<c:if test='${mode == null}'>
						<div class="text-center">
							<button type="button" class="button-input btn btn-success"
								name="button" value="Save" onclick="saveRetire(this)" id="save">
								<spring:message code="asset.retire.save" />
							</button>
							<%-- <button type="Reset" class="btn btn-warning"
								onclick="resetRetire()">
								<spring:message code="reset.msg" text="Reset" />
							</button> --%>
							<apptags:resetButton></apptags:resetButton>
							<button type="button" class="btn btn-danger" id="back"
								onclick="backRetire();">
								<spring:message code="back.msg" text="Back" />
							</button>
						</div>
					</c:if>
				</form:form>
			</div>
			<c:if test='${(mode != null) && (mode eq "APR")}'>

				<form:form action="AssetRetirement.html"
					id="assetRetirementApproval" method="post" class="form-horizontal">
					<div class="widget-content padding panel-group accordion-toggle"
						id="accordion_single_collapse1">
						<apptags:CheckerAction hideSendback="true" hideForward="true"></apptags:CheckerAction>
					</div>
					<div class="text-center widget-content padding">
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="saveRetirementApprovalAction(this);">
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

			</c:if>
		</div>
	</div>
</div>