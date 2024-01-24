<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/bankAccountMaster.js"></script>
<style>
.child-popup-dialog {
	width: 1000px !important;
	height: 300px;
}
</style>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="bank.master.header" text="Bank" />
				<strong><spring:message code="account.field.acc"
						text="Account" /></strong>
			</h2>
		<apptags:helpDoc url="BankAccountMaster.html" helpDocRefURL="BankAccountMaster.html"></apptags:helpDoc>		
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
					<label class="col-sm-2 control-label"><spring:message
							code="accounts.bankfortds.ptbbankname" text="Bank Name" /></label>
					<div class="col-sm-10">
						<select name="" class="form-control chosen-select-no-results"
							id="bankName" >
							<option value="0"><spring:message code="account.common.select" /></option>
							<c:forEach items="${bankMap}" varStatus="status" var="bankMapList">
							<option value="${bankMapList.key}" code="${bankMapList.key}">${bankMapList.value}</option>
							</c:forEach>		
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="bank.master.acc.name" text="Bank Account Name" /></label>
					<div class="col-sm-4">
						<select id="bankAccountName" name=""
							class="form-control chosen-select-no-results mandClassColor">
							<option value="0"><spring:message
									code="account.common.select" /></option>
							
							<c:forEach items="${bankAccountMap}" varStatus="status" var="bankAccountMapList">
							<option value="${bankAccountMapList.key}" code="${bankAccountMapList.key}">${bankAccountMapList.value}</option>
							</c:forEach>
						</select>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="bank.master.acc.number" text="Bank A/c No." /></label>
					<div class="col-sm-4">
						<input name="" class="form-control hasNumber" id="accountNo"></input>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success"
						onclick="searchBankAccountData()" id="search">
						<i class="fa fa-search"></i>&nbsp;
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<spring:url var="cancelButtonURL" value="BankAccountMaster.html" />
					<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message
							code="account.bankmaster.reset" text="Reset" /></a>
					<button type="button" class="btn btn-blue-2 createData"
						id="createData">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="account.bankmaster.add" text="Add" />
					</button>
					<button type="button" class="btn btn-primary" onclick="exportTemplate();">
						<spring:message code="account.bankmaster.export.import" text="Export/Import" />
					</button>
				</div>
				<div class="text-right padding-bottom-10"></div>

				<table id="grid"></table>
				<div id="pagered"></div>
			</form>
		</div>

	</div>
</div>
