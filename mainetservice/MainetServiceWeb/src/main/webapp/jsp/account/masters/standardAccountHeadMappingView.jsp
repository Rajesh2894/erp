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
<script src="js/mainet/validation.js"></script>
<script>
 $(document).ready(function() {
	 $("#payModeDiv").hide();
	   $("#bankTypeDiv").hide(); 
	   $("#depositTypeDiv").hide(); 
	   $("#vendorTypeDiv").hide(); 
	   $("#investmentTypeDiv").hide(); 
	   $("#advanceTypeDiv").hide(); 
	   $("#assetTypeDiv").hide(); 
	   $("#statutoryDeductionDiv").hide(); 
	   $("#loansDiv").hide(); 
	   
		if('${mode}'=='view')
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
			$('#bankAccountTypeEditDiv').attr('disabled',true);
		}

	});
 </script>
<div id="heading_wrapper">
	<div class="form-div">
		<form:form class="form-horizontal"
			modelAttribute="standardAccountHeadDto" cssClass="form-horizontal"
			method="POST" id="standardHeadMappingForm">
			<form:hidden path="mode" value="${mode}" id="formMode_Id" />
			<form:hidden path="cpdAccountSubTypeCode" id="cpdAccountSubTypeCode" />
			<div class="form-group">

				<label class="col-sm-2 control-label"><spring:message
						code="accounts.acc.type" text="Account type" /></label>
				<div class="col-sm-4">
					<form:input path="viewAccountTypeDesc" value=""
						cssClass="form-control" disabled="true" />
				</div>

				<form:hidden path="successFlag" id="successFlag" />
				<div id="payModeEditDiv">
					<label class="col-sm-2 control-label "><spring:message
							code="" text="Pay Mode" /></label>
					<div class="col-sm-4">
						<form:input path="viewPayModeDesc" value=""
							cssClass="form-control" disabled="true" />
					</div>
				</div>
				<div id="vendorTypeEditDiv">
					<label class="col-sm-2 control-label "><spring:message
							code="accounts.vendormaster.vendortype" text="Vendor Type" /></label>
					<div class="col-sm-4">
						<form:input path="viewVendorTypeDesc" value=""
							cssClass="form-control" disabled="true" />
					</div>
				</div>
				<div id="bankTypeEditDiv">
					<label class="col-sm-2 control-label "><spring:message
							code="account.bankmaster.banktype" text="Bank Type" /></label>
					<div class="col-sm-4">
						<form:input path="viewBankTypeDesc" value=""
							cssClass="form-control" disabled="true" />
					</div>
				</div>
				<div id="depositTypeEditDiv">
					<label class="col-sm-2 control-label "><spring:message
							code="account.deposit.deposit.type" text="Deposit Type" /></label>
					<div class="col-sm-4">
						<form:input path="viewDepositTypeDesc" value=""
							cssClass="form-control" disabled="true" />
					</div>
				</div>
				<div id="investmentTypeEditDiv">
					<label class="col-sm-2 control-label"><spring:message
							code="account.head.map.investment.type" text="Investment Type" /></label>
					<div class="col-sm-4">
						<form:input path="viewInvestmentTypeDesc" value=""
							cssClass="form-control" disabled="true" />
					</div>
				</div>
				<div id="advanceTypeEditDiv">
					<label class="col-sm-2 control-label"><spring:message
							code="advance.management.master.advancetype" text="Advance Type" /></label>
					<div class="col-sm-4">
						<form:input path="viewAdvanceTypeDesc" value=""
							cssClass="form-control" disabled="true" />
					</div>
				</div>
				<div id="assetTypeEditDiv">
					<label class="col-sm-2 control-label"><spring:message
							code="account.head.map.asset.type" text="Asset Type" /></label>
					<div class="col-sm-4">
						<form:input path="viewAssetDesc" value="" cssClass="form-control"
							disabled="true" />
					</div>
				</div>
				<div id="statutoryDeductionEditDiv">
					<label class="col-sm-2 control-label"><spring:message
							code="account.head.map.statutory.deduct.type"
							text="Statutory Deduction Type" /></label>
					<div class="col-sm-4">
						<form:input path="viewStatutoryDeductionDesc" value=""
							cssClass="form-control" disabled="true" />
					</div>
				</div>
				<div id="loansEditDiv">
					<label class="col-sm-2 control-label"><spring:message
							code="account.head.map.loan.type" text="Loan Type" /></label>
					<div class="col-sm-4">
						<form:input path="viewLoansDesc" value="" cssClass="form-control"
							disabled="true" />
					</div>
				</div>
				<div id="bankAccountTypeEditDiv">
					<label class="col-sm-2 control-label "><spring:message
							code="account.bankmaster.bankAccounttype" text="Interest Type" /></label>
					<div class="col-sm-4">
						<form:input path="viewBankIntTypeDesc" value=""
							cssClass="form-control" disabled="true" />
					</div>
				</div>



			</div>
			<div class="table-overflow-sm" id="headMapingTable">
				<c:set var="d" value="0" scope="page" />
				<table class="table table-bordered table-striped" id="mappingTable">
					<thead>
						<tr>
							<th scope="col" width="60%"><spring:message code="standard.account.primary.account.code.description"
									text="Primary Account Code-Description" /></th>
							<th scope="col" width="20%"><spring:message code="accounts.master.status"
									text="Status" /></th>
						</tr>
					</thead>
					<tbody>
						<tr id="tr0" class="accountClass">
							<td><c:set value="${standardAccountHeadDto.primaryHeadId}"
									var="primaryHeadId" /> <c:forEach items="${primaryHead}"
									varStatus="status" var="head">
									<c:if test="${head.key eq primaryHeadId}">
										<form:input path="" value="${head.value}" class="form-control"
											disabled="true" />
									</c:if>
								</c:forEach></td>
							<td><form:input path="statusDescription" value=""
									cssClass="form-control" disabled="true" /></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="text-center padding-top-10">
				<input type="button" class="btn btn-danger" value="<spring:message
						code="account.bankmaster.back" text="Back" />"
					onclick="window.location.href='StandardAccountHeadMappping.html'" />
			</div>
		</form:form>
	</div>
</div>

