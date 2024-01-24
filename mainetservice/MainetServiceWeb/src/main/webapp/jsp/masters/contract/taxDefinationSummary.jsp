<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/masters/contract/taxDefination.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="wms.TaxDeductionSummary"
					text="Tax Deduction Summary" />
			</h2>
<apptags:helpDoc url="TaxDefination.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="TaxDefination.html" class="form-horizontal"
				id="taxDefination" name="taxDefination">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="wms.TaxCategory" text="Tax Category" /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control mandColorClass chosen-select-no-results"
							id="taxId">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${command.taxList}" var="lookUp">
								<c:if test="${!fn:contains(lookUp.descLangFirst, 'Withheld')}">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.descLangFirst} - ${lookUp.lookUpCode}</form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="wms.PanCardApplicable" text="PanCard Applicable" /></label>
					<div class="col-sm-4">
						<form:select path="" class="form-control chosen-select-no-results"
							id="panCard">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<form:option value="Y">
								<spring:message code="work.vigilance.inspection.required.yes"
									text="Yes" />
							</form:option>
							<form:option value="N">
								<spring:message code="work.vigilance.inspection.required.no"
									text="No" />
							</form:option>
						</form:select>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button class="btn btn-success  search"
						onclick="searchTaxDefination();" type="button">
						<i class="button-input"></i>
						<spring:message code="works.management.search" text="Search" />
					</button>
					<button class="btn btn-warning  reset" type="reset"
						onclick="window.location.href='TaxDefination.html'">
						<i class="button-input"></i>
						<spring:message code="reset.msg" />
					</button>
					<button class="btn btn-blue-2"
						onclick="openTaxDefinationForm('C');" type="button">
						<i class="button-input"></i>
						<spring:message code="wms.CreateTaxDeduction"
							text="Create Tax Deduction" />
					</button>
				</div>
				<div class="table-responsive">
					<table class="table table-bordered table-striped" id="taxTable">
						<thead>
							<tr>
								<th width="10%"><spring:message code="wms.TaxNames"
										text="Tax Names" /></th>
								<th width="15%"><spring:message code="wms.VendorSubType"
										text="Vendor SubType" /></th>
								<th width="10%"><spring:message code="wms.Threshold"
										text="Threshold" /></th>
								<th width="10%"><spring:message code="work.management.unit" text="Unit" /></th>
								<th width="15"><spring:message code="wms.PanCard"
										text="PanCard" /></th>
								<th width="8%"><spring:message
										code="wms.AmountOrPercentage" text="Amount/Percent" /></th>
								<th width="5%"><spring:message code="wms.PercentageValue"
										text="Percentage Value" /></th>
								<th width="10%"><spring:message code="work.estimate.Total"
										text="Amount" /></th>
								<th scope="col" width="15%" class="text-center"><spring:message
										code="estate.grid.column.action" /></th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${command.taxDefinationList}" var="mstDto">
								<tr>
									<td>${mstDto.taxDesc}</td>
									<td>${mstDto.vendorSubTypeDesc}</td>
									<td class="text-right">${mstDto.taxThreshold}</td>
									<td>${mstDto.taxUnitDesc}</td>
									<td>${mstDto.taxPanApp}</td>
									<td>${mstDto.lookUpDesc}</td>
									<td class="text-right">${mstDto.raTaxPercent}</td>
									<td class="text-right">${mstDto.raTaxValue}</td>

									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="View Tax Deduction"
											onclick="getActionForDefination(${mstDto.taxId},'V');">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											title="Edit Tax Deduction"
											onclick="getActionForDefination(${mstDto.taxId},'E');">
											<i class="fa fa-pencil"></i>
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>