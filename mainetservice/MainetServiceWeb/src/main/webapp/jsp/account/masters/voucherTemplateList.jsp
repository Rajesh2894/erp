<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script src="js/account/voucherTemplate.js"></script>

<c:if test="${empty noDataFound }">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated slideInDown">
</c:if>
<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="voucher.template.entry.master.title"
				text="Voucher Template" />
		</h2>
	<apptags:helpDoc url="VoucherTemplate.html" helpDocRefURL="VoucherTemplate.html"></apptags:helpDoc>		
	</div>
	<div class="widget-content padding">
		<div class="error-div alert alert-danger" id="errorDivId"
			style="display: none;">
			<ul>
				<li><label id="errorId"></label></li>
			</ul>
		</div>
		<form:form action="VoucherTemplate.html" method="POST"
			class="form-horizontal" modelAttribute="formDTO"
			id="VoucherTemplateList">
			<form:hidden path="" value="${noDataFound}" id="noDataFound" />
			<form:hidden path="" value="${dto.currentFYearId}"
				id="currentFYearId" />
			<form:hidden path="" value="${curFinYearIdExistFlag}" id="curFinYearIdExistFlag" />
				
			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
						code="voucher.template.entry.master.templatetype"
						text="Template Type" /></label>
				<div class="col-sm-4">
					<form:select path="templateType" class="form-control">
						<form:option value="0">
							<spring:message code="voucher.template.select.type"
								text="Select Template Type" />
						</form:option>
						<c:forEach items="${dto.templateLookUps}" var="lookUp">
							<form:option value="${lookUp.lookUpId}"
								code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
						</c:forEach>
					</form:select>
				</div>
				<label class="col-sm-2 control-label" for="financialYear"><spring:message
						code="account.budgetopenmaster.financialyear"
						text="Financial Year" /></label>
				<div class="col-sm-4">
					<form:select path="financialYear"
						class="form-control chosen-select-no-results">
						<form:option value="0">
							<spring:message
								code="account.budgetopenmaster.selectfinancialyear"
								text="Select Financial Year" />
						</form:option>
						<c:forEach items="${dto.financialYearMap}" var="entry">
							<form:option value="${entry.key}" code="${entry.key}">${entry.value}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label "><spring:message
						code="voucher.template.entry.vouchertype" text="Voucher Type" /></label>
				<div class="col-sm-4">
					<form:select path="voucherType" class="form-control ">
						<form:option value="0">
							<spring:message code="voucher.template.select.voucher.type"
								text="Select Voucher Type" />
						</form:option>
						<c:forEach items="${dto.vouchertTypeLookUps}" var="lookUp">
							<form:option value="${lookUp.lookUpId}"
								code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
						</c:forEach>
					</form:select>
				</div>
				<label class="col-sm-2 control-label" for="department"><spring:message
						code="budget.reappropriation.master.departmenttype"
						text="Department" /></label>
				<div class="col-sm-4">
					<form:select path="department"
						class="form-control chosen-select-no-results">
						<form:option value="0">
							<spring:message
								code="budget.reappropriation.master.selectdepartmenttype"
								text="Select Department" />
						</form:option>
						<c:forEach items="${dto.departmentLookUps}" var="lookUp">
							<form:option value="${lookUp.lookUpId}"
								code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="templateFor"><spring:message
						code="voucher.template.entry.master.templatefor"
						text="Template For" /></label>
				<div class="col-sm-4">
					<form:select path="templateFor"
						class="form-control chosen-select-no-results">
						<form:option value="0">
							<spring:message code="voucher.template.select.template"
								text="Select Template For" />
						</form:option>
						<c:forEach items="${dto.templateForLookUps}" var="lookUp">
							<form:option value="${lookUp.lookUpId}"
								code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
						</c:forEach>
					</form:select>
				</div>
				<label class="col-sm-2 control-label "><spring:message
						code="accounts.master.status" text="Status" /></label>
				<div class="col-sm-4">
					<form:select path="status" class="form-control">
						<form:option value="0">
							<spring:message code="accounts.master.sel.status"
								text="Select Status" />
						</form:option>
						<c:forEach items="${dto.statusLookUps}" var="lookUp">
							<form:option value="${lookUp.lookUpId}"
								code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="text-center padding-bottom-10">
				<button type="button" class="btn btn-success" id="searchTemplate">
					<i class="fa fa-search"></i>&nbsp;
					<spring:message code="account.bankmaster.search" text="Search" />
				</button>
				<button type="button" class="btn btn-warning"
					onclick="window.location.href='VoucherTemplate.html'" id="backBtn">
					<spring:message code="reset.msg" text="Reset" />
				</button>
				<button type="button" class="btn btn-blue-2" id="createTemplate">
					<i class="fa fa-plus-circle"></i>
					<spring:message code="account.bankmaster.add" text="Add" />
				</button>
			</div>

			<table id="voucherTemplatesGrid"></table>
			<div id="voucherTemplatesPager"></div>

		</form:form>
	</div>
</div>

<c:if test="${empty prefixNotFound }">
	</div>
</c:if>