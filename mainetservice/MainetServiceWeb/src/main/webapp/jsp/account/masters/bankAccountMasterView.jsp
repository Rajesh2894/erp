<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ page import="java.io.*,java.util.*"%>
<script src="js/mainet/validation.js"></script>
<script src="js/tableHeadFixer.js"></script>
<script src="js/account/bankAccountMaster.js"></script>
<form:form class="form-horizontal" modelAttribute="bankAccountMaster"
	cssClass="form-horizontal" method="POST" id="bankMasterForm">
	
	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message
				code="account.bankmaster.bankname" /></label>
		<div class="col-sm-10">
			<form:input path="bankName" value=" " cssClass="form-control"
				disabled="true" />
		</div>
	</div>
		
	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message
				code="account.bankmaster.banktype" /></label>
		<div class="col-sm-4">
			<form:input path="viewBankTypeDesc" value=" " cssClass="form-control"
				disabled="true" />
		</div>
	</div>
	<h4>
		<spring:message code="account.bankmaster.accountdetail" />
	</h4>
	<div class="table-responsive">
		<table
			class="table table-bordered table-striped min-width-1600 bankTable">
			<tr>
				<th width="50" scope="col"><spring:message
						code="account.bankmaster.srno" /></th>
				<th width="200" scope="col"><spring:message
						code="account.bankmaster.accountno" /></th>
				<th width="200" scope="col"><spring:message
						code="account.bankmaster.accountname" /></th>
				<th width="150" scope="col"><spring:message
						code="account.bankmaster.type" /></th>
				<th width="200" scope="col"><spring:message 
						code="account.tenderentrydetails.function"
						text="Function" /></th>
				<th width="250" scope="col"><spring:message 
						code="account.budget.code.master.primaryaccountcode"
						text="Primary Head" /></th>
				<th width="150" scope="col"><spring:message
						code="account.bankmaster.field" /></th>
				<th width="200" scope="col"><spring:message
						code="account.bankmaster.fund" /></th>
				<th width="150" scope="col"><spring:message
						code="account.bankmaster.status" /></th>

			</tr>
			<tr id="tr" class="accountClass">
				<td id="srNo">1</td>
				<td><form:input path="viewAccountNo" value=" "
						cssClass="form-control" disabled="true" /></td>
				<td><form:input path="viewAccountName" value=" "
						cssClass="form-control" disabled="true" /></td>
				<td><form:input path="viewAccountType" value=" "
						cssClass="form-control" disabled="true" /></td>
				<td><c:set value="${bankAccountMaster.functionId}"
						var="functionHeadId" /> <c:forEach
						items="${listOfTbAcPrimaryMasterItems}" varStatus="status"
						var="function">
						<c:if test="${function.key eq functionHeadId}">
							<form:input path="" value="${function.value}"
								class="form-control" disabled="true" />
						</c:if>
					</c:forEach></td>
				<td><c:set value="${bankAccountMaster.pacHeadId}"
						var="primaryHeadId" /> <c:forEach
						items="${listOfTbAcFunctionMasterItems}" varStatus="status"
						var="head">
						<c:if test="${head.key eq primaryHeadId}">
							<form:input path="" value="${head.value}" class="form-control"
								disabled="true" />
						</c:if>
					</c:forEach></td>
				<td><c:set value="${bankAccountMaster.fieldId}" var="fieldId" />
					<c:forEach items="${listOfTbAcFieldMasterItems}" varStatus="status"
						var="field">
						<c:if test="${field.key eq fieldId}">
							<form:input path="" value="${field.value}" class="form-control"
								disabled="true" />
						</c:if>
					</c:forEach></td>
				<td><c:set value="${bankAccountMaster.fundId}" var="fundID" />
					<c:forEach items="${fundList}" varStatus="status" var="lookUp">
						<c:if test="${lookUp.fundId eq fundID}">
							<form:input path=""
								value="${lookUp.fundCompositecode} ${lookUp.fundDesc}"
								class="form-control" disabled="true" />
						</c:if>
					</c:forEach></td>
				<td><form:input path="viewAccountStatus" value=" "
						cssClass="form-control" disabled="true" /></td>

			</tr>
		</table>
	</div>
	<br>
	<div class="text-center padding-bottom-20">
		<input type="button" class="btn btn-danger"
			onclick="window.location.href='BankAccountMaster.html'"
			value="<spring:message code="account.back" text="Back"/>"
			id="cancelEdit" />
	</div>


</form:form>


