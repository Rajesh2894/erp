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
<style>
.child-popup-dialog {
	width: 1000px !important;
	height: 300px;
}

.disabled{
display:none;
}

</style>

<script>
 $(document).ready(function() {
       $("#payModeDivGrid").hide();
	   $("#bankTypeDivGrid").hide(); 
	   $("#depositTypeDivGrid").hide(); 
	   $("#vendorTypeDivGrid").hide(); 
	   $("#investmentTypeDivGrid").hide(); 
	   $("#advanceTypeDivGrid").hide(); 
	   $("#assetTypeDivGrid").hide(); 
	   $("#statutoryDeductionDiv").hide(); 
	   $("#loansDiv").hide(); 
	   $("#bankAccountIntTypeDivGrid").hide(); 
	   
		if('${mode}'=='update')
		{
			$('#accountTypeId').attr('disabled',true);
			$('#primaryHeadId0').attr('disabled',true);

		}
	   
	});
 </script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="standard.account.head.map"
					text="Standard Account Head Mapping" />
			</h2>
		<apptags:helpDoc url="StandardAccountHeadMappping.html" helpDocRefURL="StandardAccountHeadMappping.html"></apptags:helpDoc>	
		</div>
		<div class="widget-content padding">
			<form class="form-horizontal">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="accounts.acc.type" text="Account type" /></label>
					<div class="col-sm-4">
						<form:select path="accountType"
							class="form-control mandColorClass chosen-select-no-results"
							id="accountType" onchange="viewOtherFieldsGridPage();">
							<form:option value="0">
								<spring:message code="account.bankmaster.select" text="Select"/>
							</form:option>
							<c:forEach items="${accountType}" var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<div id="payModeDivGrid" class="disabled">
						<label class="col-sm-2 control-label "><spring:message
								code="" text="Pay Mode" /></label>
						<div class="col-sm-4">
							<select name="payMode"
								class="form-control mandClassColor chosen-select-no-results"
								id="payModeId">
								<option value="0"><spring:message
										code="account.bankmaster.select" text="Select"/></option>
								<c:forEach items="${payMode}" var="mode">
									<option value="${mode.lookUpId}">${mode.lookUpDesc}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div id="vendorTypeDivGrid" class="disabled">
						<label class="col-sm-2 control-label "><spring:message
								code="accounts.vendormaster.vendortype" text="Vendor Type" /></label>
						<div class="col-sm-4">
							<select id="vendorTypeId" name="payMode"
								class="form-control mandClassColor chosen-select-no-results">
								<option value="0"><spring:message
										code="account.bankmaster.select" text="Select"/></option>
								<c:forEach items="${vendorType}" var="type">
									<option value="${type.lookUpId}">${type.lookUpDesc}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div id="bankTypeDivGrid" class="disabled">
						<label class="col-sm-2 control-label "><spring:message
								code="account.bankmaster.banktype" text="Bank Type" /></label>
						<div class="col-sm-4">
							<select id="bankTypeId" name="payMode"
								class="form-control mandClassColor chosen-select-no-results">
								<option value="0"><spring:message
										code="account.bankmaster.select" text="Select"/></option>
								<c:forEach items="${bankType}" var="type">
									<option value="${type.lookUpId}">${type.lookUpDesc}</option>
								</c:forEach>
							</select>
						</div>
					</div>


					<div id="depositTypeDivGrid" class="disabled">
						<label class="col-sm-2 control-label "><spring:message
								code="account.deposit.deposit.type" text="Deposit Type" /></label>
						<div class="col-sm-4">
							<select id="depositTypeId" name="payMode"
								class="form-control mandClassColor">
								<option value="0"><spring:message
										code="account.bankmaster.select" text="Select"/></option>
								<c:forEach items="${depositType}" var="type">
									<option value="${type.lookUpId}">${type.lookUpDesc}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div id="investmentTypeDivGrid" class="disabled">
						<label class="col-sm-2 control-label"><spring:message
								code="account.head.map.investment.type" text="Investment Type" /></label>
						<div class="col-sm-4">
							<select id="investmentTypeId" name="payMode"
								class="form-control mandClassColor">
								<option value="0"><spring:message
										code="account.bankmaster.select" text="Select"/></option>
								<c:forEach items="${investmentType}" var="type">
									<option value="${type.lookUpId}">${type.lookUpDesc}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div id="advanceTypeDivGrid" class="disabled">
						<label class="col-sm-2 control-label"><spring:message
								code="advance.management.master.advancetype" text="Advance Type" /></label>
						<div class="col-sm-4">
							<select id="advanceTypeId" name="payMode"
								class="form-control mandClassColor">
								<option value="0"><spring:message
										code="account.bankmaster.select" /></option>
								<c:forEach items="${advanceType}" var="type">
									<option value="${type.lookUpId}">${type.lookUpDesc}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div id="assetTypeDivGrid" class="disabled">
						<label class="col-sm-2 control-label"><spring:message
								code="account.head.map.asset.type" text="Asset Type" /></label>
						<div class="col-sm-4">
							<select id="assetId" name="payMode"
								class="form-control mandClassColor">
								<option value="0"><spring:message
										code="account.bankmaster.select" text="Select"/></option>
								<c:forEach items="${asset}" var="type">
									<option value="${type.lookUpId}">${type.lookUpDesc}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div id="statutoryDeductionDiv" class="disabled">
						<label class="col-sm-2 control-label"><spring:message
								code="account.head.map.statutory.deduct.type"
								text="Statutory Deduction Type" /></label>
						<div class="col-sm-4">
							<select id="statutoryDeductionId" name="payMode"
								class="form-control mandClassColor">
								<option value="0"><spring:message
										code="account.bankmaster.select" text="Select"/></option>
								<c:forEach items="${statutoryDeductions}" var="type">
									<option value="${type.lookUpId}">${type.lookUpDesc}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div id="loansDiv" class="disabled">
						<label class="col-sm-2 control-label"><spring:message
								code="account.head.map.loan.type" text="Loan Type" /></label>
						<div class="col-sm-4">
							<select id="loansId" name="payMode"
								class="form-control mandClassColor">
								<option value="0"><spring:message
										code="account.bankmaster.select" text="Select"/></option>
								<c:forEach items="${loans}" var="type">
									<option value="${type.lookUpId}">${type.lookUpDesc}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div id="bankAccountIntTypeDivGrid" class="disabled">
						<label class="col-sm-2 control-label "><spring:message
								code="account.bankmaster.bankAccounttype" text="Interest Type" /></label>
						<div class="col-sm-4">
							<select id="bankAccountIntTypeId" name="payMode"
								class="form-control mandClassColor chosen-select-no-results">
								<option value="0"><spring:message
										code="account.bankmaster.select" text="Select"/></option>
								<c:forEach items="${bankAccountIntType}" var="type">
									<option value="${type.lookUpId}">${type.lookUpDesc}</option>
								</c:forEach>
							</select>
						</div>
					</div>

				</div>

				<div class="text-center padding-bottom-10">

					<button type="button" class="btn btn-success"
						onclick="searchAccountData()" id="search">
						<i class="fa fa-search"></i>&nbsp;
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<spring:url var="cancelButtonURL"
						value="StandardAccountHeadMappping.html" />
					<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message
							code="reset.msg" text="Reset" /></a>

					<c:if test="${defaultOrgFlagParentStatus == 'Y'}">
						<button type="button" class="btn btn-blue-2 createData"
							id="createData">
							<i class="fa fa-plus-circle"></i>
							<spring:message code="account.bankmaster.add" text="Add" />
						</button>
					</c:if>

					<c:if test="${defaultOrgFlagStatus == 'Y'}">
						<button type="button" class="btn btn-blue-2 createData"
							id="createData">
							<i class="fa fa-plus-circle"></i>
							<spring:message code="account.bankmaster.add" text="Add" />
						</button>
					</c:if>


				</div>
				<div class="text-right padding-bottom-10"></div>
				<table id="grid"></table>
				<div id="pagered"></div>
			</form>
		</div>

	</div>
</div>
