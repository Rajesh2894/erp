<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/deductionRegister.js"></script>

<script src="js/mainet/validation.js"></script>
<script
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="accounts.deduction.register.head" text="DEDUCTION REGISTER" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>

				<form:form action="DeductionRegister.html" class="form-horizontal"
				id="">
				<div class="form-group">
					<label for="tdsTypeId"
						class="col-sm-2 control-label required-control"><spring:message
							code="accounts.deduction.register.tdstype" text="TDS Type:" /></label>
					<div class="col-sm-4">
						<form:select path="tdsTypeId"
							class="form-control chosen-select-no-results chosenReset"
							id="tdsTypeId" data-rule-required="true">
							<option value=""><spring:message code="account.select"
									text="Select" /></option>
							<c:forEach items="${command.tdsLookUpList}" varStatus="status"
								var="tds">
								<form:option value="${tds.lookUpId}">${tds.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="trTenderDate"><spring:message
							code="accounts.deduction.register.fromdate" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<input class="form-control datepicker" id="frmdate" path=""
								maxlength="10"> <label class="input-group-addon"
								for="trasaction-date-icon30"><i class="fa fa-calendar"></i></label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="trTenderDate"><spring:message
								code="accounts.deduction.register.todate" text="To Date" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<input class="form-control datepicker" id="todate" path=""
									maxlength="10"> <label class="input-group-addon"
									for="trasaction-date-icon30"><i class="fa fa-calendar"></i></label>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center">
					<button type="button" class="btn btn-blue-2"
						onclick="viewDeductionReport(this);">
						<spring:message code="account.financial.view.report"
							text="View Report" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>