<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<script src="js/mainet/jquery.jqGrid.min.js"></script>
<script src="js/account/standardAccountHeadMapping.js"></script>
<!-- <script src="js/mainet/script-library.js"></script> -->
<script src="js/mainet/validation.js"></script>
<script>
 $(document).ready(function() {
	
	 displaySelectedGrid();

	   
		if('${mode}'=='update')
		{
			$('#accountTypeEditId').attr('disabled',true);
			$('#primaryHeadId0').attr('disabled',true);
			$('#payModeEditId').attr('disabled',true);
			$('#vendorTypeEditId').attr('disabled',true);
			$('#bankTypeEditId').attr('disabled',true);
			$('#depositTypeEditId').attr('disabled',true);
			$('#investmentTypeEditId').attr('disabled',true);
			$('#advanceTypeEditId').attr('disabled',true);
			$('#assetEditId').attr('disabled',true);
			$('#statutoryDeductionEditId').attr('disabled',true);
			$('#loansEditId').attr('disabled',true);
			$('#bankAccountTypeEditId').attr('disabled',true);
		}
	   
	});
 </script>
<!--Add Section Start Here-->
<div id="heading_wrapper">

	<div class="form-div">
		<c:url value="${saveAction}" var="url_form_submit" />
		<c:url value="${mode}" var="form_mode" />

		<div class="error-div alert alert-danger alert-dismissible"
			id="errorDivId" style="display: none;">
			<button type="button" class="close" onclick="closeOutErrBox()"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<span id="errorId"></span>
		</div>
		<form:form class="form-horizontal"
			modelAttribute="standardAccountHeadDto" action="${url_form_submit}"
			cssClass="form-horizontal" method="POST" id="standardHeadMappingForm">
			<form:hidden path="mode" value="${mode}" id="formMode_Id" />
			<form:hidden path="cpdAccountSubTypeCode" id="cpdAccountSubTypeCode" />
			
			<c:if test="${mode eq 'create'}">
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="bank.master.accountType" text="Account type" /></label>
					<div class="col-sm-4">
						<form:select path="accountType"
							class="form-control chosen-select-no-results" id="accountTypeId"
							onchange="viewOtherFields();">
							<form:option value="0" code="">
								<spring:message code="account.bankmaster.add" text="Add" />
							</form:option>
							<c:forEach items="${accountType}" var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<form:hidden path="successFlag" id="successFlag" />
					<div id="payModeDiv">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="" text="Pay Mode" /></label>
						<div class="col-sm-4">
							<form:select path="payMode"
								class="form-control mandClassColor chosen-select-no-results"
								id="payModeId" onchange="checkingStatusPaymode(this);">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${payMode}" var="mode">
									<form:option value="${mode.lookUpId}" code="${mode.lookUpCode}">${mode.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div id="vendorTypeDiv">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="accounts.vendormaster.vendortype" text="Vendor Type" /></label>
						<div class="col-sm-4">
							<form:select id="vendorTypeId" path="vendorType"
								class="form-control mandClassColor chosen-select-no-results"
								onchange="getPrimaryHeadDescOnPayMode(this);">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${vendorType}" var="type">
									<form:option value="${type.lookUpId}" code="${type.lookUpCode}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div id="bankTypeDiv">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="account.bankmaster.banktype" text="Bank Type" /></label>
						<div class="col-sm-4">
							<form:select id="bankTypeId" path="bankType"
								class="form-control mandClassColor chosen-select-no-results"
								onchange="getPrimaryHeadDescOnPayMode(this);">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${bankType}" var="type">
									<form:option value="${type.lookUpId}" code="${type.lookUpCode}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>


					<div id="depositTypeDiv">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="account.deposit.deposittype" text="Deposit Type" /></label>
						<div class="col-sm-4">
							<form:select id="depositTypeId" path="depositType"
								class="form-control mandClassColor"
								onchange="getPrimaryHeadDescOnPayMode(this);">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${depositType}" var="type">
									<form:option value="${type.lookUpId}" code="${type.lookUpCode}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div id="investmentTypeDiv">
						<label class="col-sm-2 control-label"><spring:message
								code="account.head.map.investment.type" text="Investment Type" /></label>
						<div class="col-sm-4">
							<form:select id="investmentTypeId" path="investmentType"
								class="form-control mandClassColor"
								onchange="getPrimaryHeadDescOnPayMode(this);">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${investmentType}" var="type">
									<form:option value="${type.lookUpId}" code="${type.lookUpCode}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div id="advanceTypeDiv">
						<label class="col-sm-2 control-label"><spring:message
								code="advance.management.master.advancetype" text="Advance Type" /></label>
						<div class="col-sm-4">
							<form:select id="advanceTypeId" path="advanceType"
								class="form-control mandClassColor"
								onchange="getPrimaryHeadDescOnPayMode(this);">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${advanceType}" var="type">
									<form:option value="${type.lookUpId}" code="${type.lookUpCode}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div id="assetTypeDiv">
						<label class="col-sm-2 control-label"><spring:message
								code="account.head.map.asset.type" text="Asset Type" /></label>
						<div class="col-sm-4">
							<form:select id="assetId" path="asset"
								class="form-control mandClassColor"
								onchange="getPrimaryHeadDescOnPayMode(this);">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${asset}" var="type">
									<form:option value="${type.lookUpId}" code="${type.lookUpCode}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div id="statutoryDeductionDiv">
						<label class="col-sm-2 control-label"><spring:message
								code="account.head.map.statutory.deduct.type"
								text="Statutory Deduction Type" /></label>
						<div class="col-sm-4">
							<form:select id="statutoryDeductionId" path="statutoryDeduction"
								class="form-control mandClassColor"
								onchange="getPrimaryHeadDescOnPayMode(this);">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${statutoryDeductions}" var="type">
									<form:option value="${type.lookUpId}" code="${type.lookUpCode}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div id="loansDiv">
						<label class="col-sm-2 control-label"><spring:message
								code="account.head.map.loan.type" text="Loan Type" /></label>
						<div class="col-sm-4">
							<form:select id="loansId" path="loans"
								class="form-control mandClassColor"
								onchange="getPrimaryHeadDescOnPayMode(this);">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${loans}" var="type">
									<form:option value="${type.lookUpId}" code="${type.lookUpCode}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					
					<div id="bankAccountIntTypeDiv">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="account.bankmaster.bankAccounttype" text="Interest Type" /></label>
						<div class="col-sm-4">
							<form:select id="bankAccountIntTypeId" path="bankAccountIntType"
								class="form-control mandClassColor chosen-select-no-results"
								onchange="getPrimaryHeadDescOnPayMode(this);">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${bankAccountIntType}" var="type">
									<form:option value="${type.lookUpId}" code="${type.lookUpCode}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

				</div>
			</c:if>

			<c:if test="${mode ne 'create'}">
				<form:hidden path="accountType" id="accountType" />
				<form:hidden path="accountSubType" id="accountSubType" />
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="bank.master.accountType" text="Account type" /></label>
					<div class="col-sm-4">
						<form:select path="accountType"
							class="form-control chosen-select-no-results"
							id="accountTypeEditId">
							<form:option value="0">
								<spring:message code="account.bankmaster.add" text="Add" />
							</form:option>
							<c:forEach items="${accountType}" var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<form:hidden path="successFlag" id="successFlag" />
					<div id="payModeEditDiv">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="" text="Pay Mode" /></label>
						<div class="col-sm-4">
							<form:select path="payMode"
								class="form-control mandClassColor chosen-select-no-results"
								id="payModeEditId" onchange="checkingStatusPaymode(this);">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${payMode}" var="mode">
									<form:option value="${mode.lookUpId}">${mode.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div id="vendorTypeEditDiv">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="accounts.vendormaster.vendortype" text="Vendor Type" /></label>
						<div class="col-sm-4">
							<form:select id="vendorTypeEditId" path="vendorType"
								class="form-control mandClassColor chosen-select-no-results">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${vendorType}" var="type">
									<form:option value="${type.lookUpId}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div id="bankTypeEditDiv">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="account.bankmaster.banktype" text="Bank Type" /></label>
						<div class="col-sm-4">
							<form:select id="bankTypeEditId" path="bankType"
								class="form-control mandClassColor chosen-select-no-results">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${bankType}" var="type">
									<form:option value="${type.lookUpId}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>


					<div id="depositTypeEditDiv">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="account.deposit.deposittype" text="Deposit Type" /></label>
						<div class="col-sm-4">
							<form:select id="depositTypeEditId" path="depositType"
								class="form-control mandClassColor">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${depositType}" var="type">
									<form:option value="${type.lookUpId}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div id="investmentTypeEditDiv">
						<label class="col-sm-2 control-label"><spring:message
								code="account.head.map.investment.type" text="Investment Type" /></label>
						<div class="col-sm-4">
							<form:select id="investmentTypeEditId" path="investmentType"
								class="form-control mandClassColor">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${investmentType}" var="type">
									<form:option value="${type.lookUpId}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div id="advanceTypeEditDiv">
						<label class="col-sm-2 control-label"><spring:message
								code="advance.management.master.advancetype" text="Advance Type" /></label>
						<div class="col-sm-4">
							<form:select id="advanceTypeEditId" path="advanceType"
								class="form-control mandClassColor">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${advanceType}" var="type">
									<form:option value="${type.lookUpId}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div id="assetTypeEditDiv">
						<label class="col-sm-2 control-label"><spring:message
								code="account.head.map.asset.type" text="Asset Type" /></label>
						<div class="col-sm-4">
							<form:select id="assetEditId" path="asset"
								class="form-control mandClassColor">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${asset}" var="type">
									<form:option value="${type.lookUpId}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div id="statutoryDeductionEditDiv">
						<label class="col-sm-2 control-label"><spring:message
								code="account.head.map.statutory.deduct.type"
								text="Statutory Deduction Type" /></label>
						<div class="col-sm-4">
							<form:select id="statutoryDeductionEditId"
								path="statutoryDeduction" class="form-control mandClassColor">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${statutoryDeductions}" var="type">
									<form:option value="${type.lookUpId}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div id="loansEditDiv">
						<label class="col-sm-2 control-label"><spring:message
								code="account.head.map.loan.type" text="Loan Type" /></label>
						<div class="col-sm-4">
							<form:select id="loansEditId" path="loans"
								class="form-control mandClassColor">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${loans}" var="type">
									<form:option value="${type.lookUpId}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					
					<div id="bankAccountTypeEditDiv">
						<label class="col-sm-2 control-label "><spring:message
								code="account.bankmaster.bankAccounttype" text="Interest Type" /></label>
						<div class="col-sm-4">
							<form:select id="bankAccountTypeEditId" path="bankAccountIntType"
								class="form-control mandClassColor chosen-select-no-results">
								<form:option value="0">
									<spring:message code="account.bankmaster.add" text="Add" />
								</form:option>
								<c:forEach items="${bankAccountIntType}" var="type">
									<form:option value="${type.lookUpId}">${type.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

				</div>
			</c:if>

			<div class="form-group"></div>



			<div class="table-overflow-sm" id="headMapingTable">
				<c:set var="d" value="0" scope="page" />
				<table class="table table-bordered table-striped" id="mappingTable">
					<thead>
						<tr>
							<th scope="col" width="50%"><spring:message code="accounts.Secondaryhead.PrimaryObjectcode"
									text="Primary Account Head" /><span class="mand">*</span></th>
							<c:if test="${mode ne 'create'}">
								<th scope="col" width="20%"><spring:message code="accounts.master.status"
										text="Status" /> <span class="mand">*</span></th>
							</c:if>
							<c:if test="${mode eq 'create'}">
								<th scope="col" width="20%"><spring:message code="account.bankmaster.adddelete"
										text="Add/Delete" /></th>
							</c:if>
						</tr>
					</thead>
					<tbody>

						<c:choose>
							<c:when test="${mode eq 'create'}">
								<tr id="tr0" class="mappingTrClass">
									<td><form:select
											path="primaryHeadMappingList[0].primaryHeadId"
											class="form-control mandColorClass chosen-select-no-results"
											id="primaryHeadId0" onchange="validateAccountCodeDesc(0);">
											<form:option value="0">
												<spring:message code="account.bankmaster.select" />
											</form:option>
											<c:forEach items="${primaryHead}" var="primaryHead">
												<form:option value="${primaryHead.key}">${primaryHead.value}</form:option>
											</c:forEach>
										</form:select></td>



									<td class="text-center">
										<button data-placement="top" title="Add"
											class="btn btn-success btn-sm addbtn" id="addButton0">
											<i class="fa fa-plus-circle"></i>
										</button>
										<button data-placement="top" title="Delete"
											class="btn btn-danger btn-sm delButton" id="delButton0">
											<i class="fa fa-trash-o"></i>
										</button>
									</td>
								</tr>
							</c:when>
							<c:otherwise>


								<tr id="tr0" class="accountClass">
									<td><form:select path="primaryHeadId"
											class="form-control mandClassColor chosen-select-no-results"
											id="primaryHeadId0" disabled="true">
											<form:option value="0">
												<spring:message code="account.bankmaster.select" />
											</form:option>
											<c:forEach items="${primaryHead}" var="primaryHead">
												<form:option value="${primaryHead.key}">${primaryHead.value}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:select path="status"
											class="form-control mandColorClass" id="status">
											<form:option value="0">
												<spring:message code="account.bankmaster.select" />
											</form:option>
											<c:forEach items="${status}" var="status">
												<form:option value="${status.lookUpId}">${status.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<form:hidden path="primaryHeadId" id="" />


								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>

			<div class="text-center padding-top-10">

				<input type="button" class="btn btn-success btn-submit"
					value="<spring:message code="account.bankmaster.save" text="Save"/>"
					onclick="return saveForm(this);" id="submitBtnId">
				<c:if test="${mode eq 'create'}">
					<button type="Reset" class="btn btn-warning createData">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
				</c:if>
				<input type="button" class="btn btn-danger" value="<spring:message code="account.bankmaster.back" text="Back" />"
					onclick="window.location.href='StandardAccountHeadMappping.html'" />
			</div>

		</form:form>
	</div>
</div>

