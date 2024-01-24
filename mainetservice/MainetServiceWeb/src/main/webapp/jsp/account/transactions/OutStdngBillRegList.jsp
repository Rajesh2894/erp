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
<script src="js/account/outStandingBillRegitser.js"
	type="text/javascript"></script>

<script src="js/mainet/validation.js"></script>
<script
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="account.outstanding.bill.register" text="Outstanding Bill Register" />
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
			<form:form action="" modelAttribute="OutStdngBillRegisterBean"
				class="form-horizontal" id="BillReceiptPrintId">
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="trTenderDate"><spring:message code="account.financial.as.on.date"
							text="As On Date" /></label>

					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="frmdate"
								cssClass="mandColorClass form-control datepicker"
								data-rule-required="true" maxlength="10" />
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>
						</div>
					</div>
					<label class="col-sm-2 control-label required-control" for="dpDeptid"><spring:message
							code="budget.reappropriation.master.departmenttype" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="" class="form-control chosen-select-no-results"
							id="dpDeptid">
							<form:option value="">
								<spring:message code="account.budgetprojectedexpendituremaster.selectDepartment" text="Select Department" />
							</form:option>
							<c:forEach items="${deptMap}" varStatus="status" var="deptMap">
								<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				
				<div class="form-group">
				<label class="col-sm-2 control-label required-control" for="accountHead"><spring:message
							code="account.Head" text="Account Head" /></label>
					<div class="col-sm-4">		
					<form:select id="accountHead"
						path=""
						class="form-control chosen-select-no-results">
						<form:option value="">
							<spring:message code="account.common.select" />
						</form:option>
						<form:option value="0" code="ALL">All</form:option>
						<c:forEach items="${pacMap}" varStatus="status" var="pacItem">
							<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value}</form:option>
						</c:forEach>
					</form:select>
					</div>
				</div>
				
				
				<div class="text-center margin-top-10">
					<input type="button" id="PrintButn"
						class="btn btn-blue-2" value="<spring:message code='account.financial.view.report' />"/>
					<button type="button" class="btn btn-warning resetSearch"
						onclick="window.location.href ='OutstandingBillRegister.html'">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>





			</form:form>
		</div>
	</div>
</div>