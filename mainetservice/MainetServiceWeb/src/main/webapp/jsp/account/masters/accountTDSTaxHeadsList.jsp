<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/mainet/jquery.jqGrid.min.js"></script>
<script src="js/account/accountTDSTaxHeads.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="tax.heads.master.widget.header" text="" />
			</h2>
		<apptags:helpDoc url="AccountTDSTaxHeadsMaster.html" helpDocRefURL="AccountTDSTaxHeadsMaster.html"></apptags:helpDoc>		
		</div>

		<div class="widget-content padding" id="tdsMappingDiv">
			<form:form action="" modelAttribute="tdsTaxHeadsMasterBean"
				class="form-horizontal">

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
				</div>
				<div class="form-group">
					<label for="pacHeadId" class="col-sm-2 control-label"><spring:message
							code="" text="Budget Head" /></label>
					<div class="col-sm-4">
						<form:select id="budgetCodeId" path="budgetCodeId"
							cssClass="form-control chosen-select-no-results" onchange="">
							<form:option value="">
								<spring:message code="account.common.select" />
							</form:option>
							<c:forEach items="${headCodeMap}" varStatus="status"
								var="pacItem">
								<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="" text="TDS Type" /></label>
					<div class="col-sm-4">
						<form:select id="cpdIdDeductionType" path="cpdIdDeductionType"
							cssClass="form-control">
							<form:option value="">
								<spring:message code="account.common.select" />
							</form:option>
							<c:forEach items="${listOfTbComparamDetItems}" varStatus="status"
								var="taxItem">
								<form:option value="${taxItem.lookUpId}" code="">${taxItem.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="" text="Status" /></label>
					<div class="col-sm-4">
						<form:select path="status" class="form-control" id="status">
							<option value="0">Select</option>
							<c:forEach items="${status}" var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									label="${lookUp.descLangFirst}"></form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>


				<div class="text-center padding-bottom-10">
					<button type="button" id="search"
						value="<spring:message code="search.data"/>"
						class="btn btn-success searchData" onclick="searchTDSData()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<button type="reset" class="btn btn-warning resetSearch">
						<spring:message code="account.btn.reset" text="Reset" />
					</button>
					<button type="button" value="Contra Entry"
						class="btn btn-blue-2 createData">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="account.bankmaster.create" text="Create" />
					</button>
				</div>
			</form:form>

			<table id="grid"></table>
			<div id="pagered"></div>
		</div>
	</div>
</div>
