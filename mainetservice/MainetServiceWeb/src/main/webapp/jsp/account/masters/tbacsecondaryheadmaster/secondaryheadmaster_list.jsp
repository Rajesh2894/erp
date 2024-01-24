<?xml version="1.0" encoding="UTF-8" standalone="no"?>

<div xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:joda="http://www.joda.org/joda/time/tags"
	xmlns:s="http://www.springframework.org/tags"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:util="urn:jsptagdir:/WEB-INF/tags/util"
	xmlns:display="urn:jsptagdir:/WEB-INF/tags/display" version="2.0">
	<%@page import="java.util.Date"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
	<%
		response.setContentType("text/html; charset=utf-8");
	%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<script src="js/account/SecondaryheadMaster.js"></script>

	<jsp:directive.page contentType="text/html;charset=UTF-8" />
	<apptags:breadcrumb></apptags:breadcrumb>

    <div id="content1"> </div>
	<div id="heading_wrapper" class="content">
		<div class="widget">

			<div class="widget-header">
				<h2>
					<spring:message code="accounts.Secondaryhead.SecondaryheadMaster" text="Secondary Account Head Master"></spring:message>
				</h2>
				<apptags:helpDoc url="tbAcSecondaryheadMaster.html" helpDocRefURL="tbAcSecondaryheadMaster.html"></apptags:helpDoc>
			</div>
			<div class="error-div" style="display: none;" id="errorDivDeptMas"></div>
			<div class="widget-content padding">
				<form:form method="post" name=""
					modelAttribute="secondaryheadMaster" class="form-horizontal">

					<div class="error-div alert alert-danger alert-dismissible"
						id="errorDivId" style="display: none;">
						<button type="button" class="close" onclick="closeErrBox()"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<span id="errorId"></span>
					</div>
				
					<div class="form-group">

						<c:if test="${fundStatus == 'Y'}">
							<label class="control-label col-sm-2 required-control"><spring:message
									code="account.budget.code.master.fundcode" /></label>
							<div class="col-sm-4">
								<form:select id="fundId" path="fundId"
									cssClass="fundId form-control mandColorClass chosen-select-no-results"
									data-rule-required="true">
									<form:option value="">
										<spring:message
											code="account.budget.code.master.selectfundcode" />
									</form:option>
									<c:forEach items="${listOfTbAcFundMasterItems}"
										varStatus="status" var="fundMap">
										<form:option value="${fundMap.key}" code="${fundMap.key}">${fundMap.value}</form:option>
									</c:forEach>
									<%-- <c:forEach items="${list}" varStatus="status" var="fundItem">
										<form:option value="${fundItem.fundId}"
											code="${fundItem.fundId}">${fundItem.fundCode}</form:option>
									</c:forEach> --%>
								</form:select>
							</div>
						</c:if>

						<c:if test="${fieldStatus == 'Y'}">
							<label class="control-label col-sm-2 required-control"><spring:message
									code="account.budget.code.master.fieldcode" /></label>
							<div class="col-sm-4">
								<form:select id="fieldId" path="fieldId"
									cssClass="fieldId form-control mandColorClass chosen-select-no-results"
									data-rule-required="true">
									<form:option value="">
										<spring:message
											code="account.budget.code.master.selectfieldcode" />
									</form:option>
									<%-- <c:forEach items="${list}" varStatus="status" var="fieldItem">
										<form:option value="${fieldItem.fieldId}"
											code="${fieldItem.fieldId}">${fieldItem.fieldCode}</form:option>
									</c:forEach> --%>
									<c:forEach items="${listOfTbAcFieldMasterItems}"
										varStatus="status" var="fieldMap">
										<form:option value="${fieldMap.key}" code="${fieldMap.key}">${fieldMap.value}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</c:if>

					</div>

					<div class="form-group">

						<c:if test="${functionStatus == 'Y'}">
							<label class="col-sm-2 control-label" for=""><spring:message
									code="account.budget.code.master.functioncode" text="Function" /></label>
							<div class="col-sm-4">
								<form:select id="functionId" path=""
									cssClass="form-control chosen-select-no-results">
									<form:option value="">
										<spring:message code="account.budget.code.master.selectfunctioncode" text="Select Function" />
									</form:option>
									<c:forEach items="${listOfTbAcFunctionMasterItems}"
										varStatus="status" var="functionMap">
										<form:option value="${functionMap.key}"
											code="${functionMap.key}">${functionMap.value}</form:option>
									</c:forEach>
									<%-- <c:forEach items="${list}" varStatus="status"
										var="functionItem">
										<form:option value="${functionItem.functionId}"
											code="${functionItem.functionId}">${functionItem.functionCode}</form:option>
									</c:forEach> --%>
								</form:select>
							</div>
						</c:if>
						
						<c:if test="${primaryStatus == 'Y'}">
							<label class="col-sm-2 control-label" for=""><spring:message
									code="account.budget.code.master.primaryaccountcode" text="Primary Head" /></label>
							<div class="col-sm-4">
								<form:select id="pacHeadId" path=""
									cssClass="form-control chosen-select-no-results">
									<form:option value="">
										<spring:message code="budget.allocation.master.selectprimaryaccountcode" text="Select Primary Head" />
									</form:option>
									<%-- <c:forEach items="${list}" varStatus="status" var="pacItem">
										<form:option value="${pacItem.pacHeadId}"
											code="${pacItem.pacHeadId}">${pacItem.pacHeadDesc}</form:option>
									</c:forEach> --%>
									<c:forEach items="${listOfPrimaryAcHeadMapMasterItems}"
										varStatus="status" var="pacHeadMap">
										<form:option value="${pacHeadMap.key}"
											code="${pacHeadMap.key}">${pacHeadMap.value}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</c:if>

					</div>

					<div class="form-group">

						<c:if test="${secondaryStatus == 'Y'}">
							<label class="control-label col-sm-2 "><spring:message
									code="account.budget.code.secondaryaccountcode" text="Secondary Head" /> </label>

							<div class="col-sm-4">
								<form:select path=""
									class="form-control chosen-select-no-results" id="sacHeadId"
									disabled="${viewMode}">
									<form:option value="">
										<spring:message code="budget.allocation.master.selectsecondaryaccountcode"
											text="Select Secondary Head" />
									</form:option>
									<c:forEach items="${list}" varStatus="status" var="sacItem">
										<form:option value="${sacItem.sacHeadId}"
											code="${sacItem.sacHeadId}">${sacItem.sacHeadCodeDesc}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</c:if>

						<label class="control-label col-sm-2 "><spring:message
								code="secondary.head.ledger.type" text="Ledger Type" /> </label>

						<div class="col-sm-4">
							<form:select path="sacLeddgerTypeCpdId" class="form-control"
								id="ledgerTypeId" disabled="${viewMode}">
								<form:option value="">
									<spring:message code="secondary.head.sel.ledger.type" text="Select Ledger Type" />
								</form:option>
								<c:forEach items="${legerTypeList}" varStatus="status"
									var="levelChild">
									<form:option code="${levelChild.lookUpCode}"
										value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
						</div>

					</div>

					<div class="text-center padding-bottom-10">
						<button type="button" class="btn btn-success searchData"
							onclick="searchSecondaryHeadData()">
							<i class="fa fa-search"></i>
							<spring:message code="account.bankmaster.search" text="Search" />
						</button>
						<spring:url var="cancelButtonURL"
							value="tbAcSecondaryheadMaster.html" />
						<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message
								code="account.bankmaster.reset" text="Reset" /></a>

						<c:if test="${defaultOrgFlagParentStatus == 'Y'}">
							<button type="submit"
								class="btn btn-blue-2 addSecondaryheadMasterClass"
								href="javascript:void(0);" title="">
								<strong class="fa fa-plus-circle"></strong>
								<spring:message code="account.bankmaster.add" text="Add" />
							</button>
						</c:if>

						<c:if test="${defaultOrgFlagStatus == 'Y'}">
							<button type="submit"
								class="btn btn-blue-2 addSecondaryheadMasterClass"
								href="javascript:void(0);" title="">
								<strong class="fa fa-plus-circle"></strong>
								<spring:message code="account.bankmaster.add" text="Add" />
							</button>
						</c:if>
						<button onclick="PrintSecondaryHeads(this)");"
						class="btn btn-primary hidden-print" type="button">
							<i class="fa fa-print"></i> <spring:message code="account.budget.code.print" text="Print" />
						</button>


						<button type="button" class="btn btn-primary"
							onclick="exportTemplate();">
							<spring:message code="account.bankmaster.export.import" text="Export/Import" />
						</button>
						
						<button type="button" class="btn btn-primary"
							onclick="exportTemplateLinkMap();">
							<spring:message code="account.link.secondary.bank" text="Link Secondary Account Heads with Bank Accounts" />
						</button>
					</div>

					<div class="table">
						<table id="tbAcSecondaryheadMasterGrid"></table>
						<div id="pagered"></div>
					</div>

					<input type="submit" class="hide" Value="submit">
				</form:form>

			</div>
		</div>
	</div>
</div>
