<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/validation.js"></script>
<script src="js/account/transaction/chequeBookUtilization.js"
	type="text/javascript"></script>

<apptags:breadcrumb></apptags:breadcrumb>



<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="cheque.book.utilization.title"
					text="Cheque Book Utilization" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="" method="post" class="form-horizontal"
				novalidate="novalidate" modelAttribute="chequeBookUtilizationDto">
				<div class="panel-body">
					<div class="form-group">
						<label for="bankAccountId"
							class="col-sm-2 control-label required-control"><spring:message
								code="bank.account.label" text="Bank Account "></spring:message></label>
						<div class="col-sm-4">
							<form:select type="select" required="required"
								class="form-control mandColorClass chosen-select-no-results"
								name="" id="bankAccountId" data-rule-required="true"
								path="bankAccountId" onchange="getChequeRange()">
								<form:option value="">
									<spring:message code="account.common.select" />
								</form:option>
								<c:forEach items="${bankAccountMap}" varStatus="status"
									var="bankItem">
									<form:option value="${bankItem.key}">${bankItem.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
						<label for="chequeBookId"
							class="col-sm-2 control-label required-control"><spring:message
								code="account.cheque.range" text="Cheque Range"></spring:message></label>
						<div class="col-sm-4">
							<form:select type="select" required="required"
								class="form-control mandColorClass chosen-select-no-results"
								name="" id="chequeBookId" data-rule-required="true"
								path="chequeBookId">
								<form:option value="" selected="selected">
									<spring:message code="account.common.select" text="Select" />
								</form:option>
							</form:select>
						</div>
					</div>
				</div>
				<div class="text-center clear padding-10">
					<button type="button" class="button-input btn btn-success"
						name="button-1496152322980" style="" id="seachBtn"
						onclick="searchUtilizationData(this)">
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning"
						onclick="window.location.reload()">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
					<%-- Defect #153438 --%>
					<apptags:backButton url="AdminHome.html" cssClass="btn btn-danger"></apptags:backButton>
				</div>
			</form:form>
			<table id="chequeBookGrid"></table>
			<div id="pagered"></div>
		</div>
	</div>
</div>