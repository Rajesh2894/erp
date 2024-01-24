<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/works_management/tenderInitationApproval.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="tender.updatetender"
					text="Tender Award Details" />
			</h2>

			<div class="additional-btn">
				<apptags:helpDoc url="TenderInitiation.html"></apptags:helpDoc>
			</div>
		</div>

		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span> <spring:message code="works.fiels.mandatory.message" /></span>
			</div>
			<!-- End mand-label -->

			<!-- Start Form -->
			<form:form action="TenderInitiation.html" class="form-horizontal"
				name="TenderInitiation" id="TenderInitiation"
				modelAttribute="command">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<!-- End Validation include tag -->

				<!-- Start Each Section -->

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="project.master.dept" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="initiationDto.deptId"
							cssClass="form-control chosen-select-no-results" id="deptId"
							onchange="getProjects(this);" disabled="true">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.departmentList}" var="list">
								<form:option value="${list.dpDeptid}" code="${list.dpDeptid}">${list.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label "><spring:message
							code="project.master.projname" text="Project Name" /></label>
					<div class="col-sm-4">
						<form:input path="initiationDto.projectName" id="projName"
							class="form-control preventSpace" readonly="true" />
						<form:hidden path="initiationDto.projId" id="projId" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="tender.tenderdate" text="Tender/Quotation Ref. Date" /></label>

					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="initiationDto.tenderDate" id="tenderDate"
								class="form-control tenderDatePicker" value=""
								data-rule-required="true" readonly="true"
								disabled="true" />
							<label class="input-group-addon" for="tenderDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=tenderDate></label>
						</div>
					</div>

					<label for="select-start-date" class="col-sm-2 control-label"><spring:message
							code="tender.estimated.cost" text="Estimated Cost" /> </label>
					<div class="col-sm-4">
						<form:input path="initiationDto.tenderTotalEstiAmount"
							cssClass="form-control text-right" id="tenderTotalEstiAmount"
							readonly="true" />
					</div>
				</div>

				<div class="form-group">
					<label for="select-start-date"
						class="col-sm-2 control-label required-control"><spring:message
							code="tender.tenderNo" text="Tender/Quotation No." /> </label>
					<div class="col-sm-4">
						<form:input path="initiationDto.tenderNo"
							cssClass="form-control mandColorClass preventSpace" id="tenderNo"
							data-rule-required="true"
							readonly="true" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="tender.tenderSubmissiondate" text="Tender Submission Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="initiationDto.workDto[0].tndSubmitDate"
								id="tenderSubDate" class="form-control tenderDatePicker"
								value="" readonly="true"
								disabled="true" />
							<label class="input-group-addon" for="tenderSubDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=tenderSubDate></label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="select-start-date" class="col-sm-2 control-label"><spring:message
							code="tender.resolutionNo" text="Resolution No." /> </label>
					<div class="col-sm-4">
						<form:input path="initiationDto.workDto[0].tndAwdResNo"
							cssClass="form-control mandColorClass preventSpace"
							id="resolutionNo" disabled="${command.mode eq 'V'}"
							readonly="true" />
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="tender.resolutionDate" text="Resolution Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="initiationDto.workDto[0].tndAwdResDate"
								id="tenderResDate" class="form-control tenderDatePicker"
								readonly="true"
								disabled="true" />
							<label class="input-group-addon" for="tenderResDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=tenderResDate></label>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="tndPGRate"><spring:message
							code="tender.additional.securityRateDeposit"
							text="Additional Performance Security Deposit Rate" /></label>

					<div class="col-sm-4">
						<form:input path="initiationDto.workDto[0].tndPGRate"
							id="tndPGRate" class="form-control preventSpace hasAmount"
							maxlength="50" readonly="true"
							onkeypress="return hasAmount(event, this, 10, 2)"
							onchange="getAmountFormatInDynamic((this),'tndPGRate')" />
					</div>
					<label class="col-sm-2 control-label" for="tndPGAmount"><spring:message
							code="tender.securityAmount.deposit"
							text="Additional Performance Security Deposit" /></label>
					<div class="col-sm-4">
						<form:input path="initiationDto.workDto[0].tndPGAmount"
							id="tndPGAmount" class="form-control preventSpace hasAmount"
							maxlength="50" readonly="true"
							onkeypress="return hasAmount(event, this, 10, 2)"
							onchange="getAmountFormatInDynamic((this),'tndPGAmount')" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="tender.designation" text="Approved By" /></label>
					<div class="col-sm-4">
						<form:select path="initiationDto.workDto[0].tndAuthDesgid"
							cssClass="form-control chosen-select-no-results"
							data-rule-required="true" id="tndAuthDesgid"
							disabled="true">
							<form:option value="0">
								<spring:message code="tender.selectDesg"
									text="select Designation"></spring:message>
							</form:option>
							<c:forEach items="${command.designationList}" var="lookUp">
								<form:option value="${lookUp.dsgid}" code="${lookUp.desgName}">${lookUp.desgName}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="col-sm-2 control-label "><spring:message
							code="tender.authority" text="Competent Authority" /></label>
					<c:set var="baseLookupCode" value="MPT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="initiationDto.workDto[0].tndCopntAuth"
						cssClass="form-control required-control chosen-select-no-results"
						selectOptionLabelCode="selectdropdown" hasId="true"
						disabled="true" />
				</div>
				
				<div class="form-group">

					<label class="col-sm-2 control-label "><spring:message
						code="tender.bankGuarante"	text="Bank Guarantee" /></label>
					<div class="col-sm-4">
						<form:input path="initiationDto.tenderBankAmt" id="" disabled="true"
							class="form-control hasNumber" maxlength="50" />
					</div>
					
					<label class="col-sm-2 control-label"><spring:message
						code="tender.additionalPersd" text="Additional Performance SD" /></label>
					<div class="col-sm-4">
						<form:input path="initiationDto.tenderProvAmt" id="" disabled="true"
							class="form-control hasNumber" maxlength="50" />
					</div>
					
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2"> <spring:message
							code="tender.Gst" text="GST"></spring:message></label>
					<div class="col-sm-4">
						<label for="including" class="radio-inline mandColorClass"><form:radiobutton
								path="initiationDto.workDto[0].tndGSTApl" value="I"
								id="including" data-rule-required="true"
								disabled="true" /> <spring:message
								code="tender.GstIncluding" text="Including" /> </label> <label
							for="excluding" class="radio-inline mandColorClass"><form:radiobutton
								path="initiationDto.workDto[0].tndGSTApl" value="E"
								id="excluding" data-rule-required="true"
								disabled="true" /> <spring:message
								code="tender.GstExcluding" text="Excluding" /> </label>

					</div>
					<c:if test="${command.mode ne 'V'}">
						<div class="col-sm-4">
							<button class="btn btn-primary" onclick="showBIDFormForCreate(this);"
								type="button">
								<i class="fa fa-plus-circle padding-right-5"></i>
								<spring:message code="wms.BID.addButton" text="Add BID Details" />
							</button>
						</div>
					</c:if>
				</div>
				

				<!-- End Each Section -->

				<!-- Start button -->

				<div>
					<h4>
						<spring:message code="tender.updatework"
							text="Work Award Details" />                           <!-- defect #120246 -->
					</h4>
					<c:set var="c" value="0" scope="page" />
					<!-- <div class="table-responsive"> -->
					<table class="table table-bordered table-striped"
						id="tendWorkUpdate">
						<thead>
							<tr>
								<th width="8%"><spring:message code='work.def.workCode'
										text="Work Code" /></th>
								<th width="10%"><spring:message code='work.def.workname'
										text="Work Name" /></th>
								<th width="10%"><spring:message code='tender.estimate.cost'
										text="Estimate Cost" /></th>
								<th width="10%"><spring:message code='tender.type'
										text="Tender Type" /><span class="mand">*</span></th>
								<th width="10%"><spring:message code=''
										text="Percentage-Is" /></th>
								<th width="10%"><spring:message code='tender.value'
										text="Percentage/Amount" /><span class="mand">*</span></th>
								<th width="10%"><spring:message code='tender.work.cost'
										text="Accepted Work Cost" /><span class="mand">*</span></th>
								<th width="15%"><spring:message code='mb.ContractorName'
										text="Contractor Name" /><span class="mand">*</span></th>
								<th width="9%"><spring:message
										code='wms.NoOfDaysForAggrement'
										text="No. Of Days For Agreement" /><span class="mand">*</span></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.initiationDto.workDto}" var="workDto">

								<tr class="tenderWorkUpdate">
									<form:input type="hidden"
										path="initiationDto.workDto[${c}].workId"
										value="${mstDto.workId}" />

									<td><form:input path="" id="workCode${c}"
											cssClass="form-control" value="${workDto.workCode}"
											readonly="true" /></td>
									<td><form:input path="" id="workName${c}"
											cssClass="form-control" value="${workDto.workName}"
											readonly="true" /></td>
									<td><form:input path="" id="workEstimateAmt${c}"
											cssClass="form-control text-right"
											value="${workDto.workEstimateAmt}" readonly="true" /></td>

									<td class="tenderTypes"><form:select 
											path="initiationDto.workDto[${c}].tenderType"
											cssClass="form-control  tenderType" id="tenderType${c}"
											data-rule-required="true"
											onchange="calculateTotalAmount(${c});"
											disabled="true">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${valueTypeList}" var="payType">
												<form:option value="${payType.lookUpId }"
													code="${payType.lookUpCode}">${payType.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>
									<td><form:select
											path="initiationDto.workDto[${c}].tendTypePercent"
											cssClass="form-control" id="tendTypePercent${c}"
											data-rule-required="true"
											disabled="true">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${valueTypeAmount}" var="payType">
												<form:option value="${payType.lookUpId }"
													code="${payType.lookUpCode}">${payType.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>


									<td><form:input
											path="initiationDto.workDto[${c}].tenderValue"
											cssClass="form-control mandColorClass text-right"
											id="tenderValue${c}" data-rule-required="true"
											onkeypress="return hasAmount(event, this, 10, 2)"
											onchange="getAmountFormatInDynamic((this),'tenderValue')"
											onkeyup="calculateTotalAmount(${c});"
											readonly="true" /></td>

									<td><form:input
											path="initiationDto.workDto[${c}].tenderAmount"
											cssClass="form-control mandColorClass text-right"
											data-rule-required="true" id="totalTenderAmount${c}"
											readonly="true" /></td>


									<td><form:select
											path="initiationDto.workDto[${c}].venderId"
											cssClass="form-control mandColorClass chosen-select-no-results"
											id="vendorId${c}" disabled="true"
											data-rule-required="true">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>

											<c:forEach items="${vendorList}" var="vendor">
												<form:option value="${vendor.vmVendorid }"
													label="${vendor.vmVendorname} => ${vendor.vmPanNumber}"></form:option>
											</c:forEach>
										</form:select></td>

									<td><form:input
											path="initiationDto.workDto[${c}].tenderNoOfDayAggremnt"
											cssClass="form-control mandColorClass hasNumber"
											id="tenderNoOfDayAggremnt${c}" data-rule-required="true"
											readonly="true" /></td>

									<c:set var="c" value="${c + 1}" scope="page" />
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<apptags:CheckerAction hideForward="true" hideSendback="true" hideUpload="true"></apptags:CheckerAction>
				<div class="text-center clear padding-10">
					<button type="button" id="save" class="btn btn-success"
						onclick="saveTenderAwardApprovalData(this);">
						<i class="fa fa-sign-out padding-right-5"></i>
						<spring:message code="mileStone.submit" text="" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel"
						onclick="window.location.href='TenderInitiation.html'"
						id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>
				</div>
				<!-- End button -->
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
</div>