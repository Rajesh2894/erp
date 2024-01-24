<!-- Start JSP Necessary Tags -->
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/works_management/reports/contractPrint.js"></script>

<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="wms.AgreementPrintSummary"
					text="Agreement Print Summary" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="ContractAgreementPrint.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="ContractAgreementPrint.html"
				class="form-horizontal" id="contractAgreementPrint"
				name="contractAgreementPrint">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="form-group">
					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="work.order.contract.no" text="Contract No." /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control chosen-select-no-results mandColorClass"
							id="contId" data-rule-required="true"
							onchange="getContractDetail(this)">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.contractSummaryDTOList}" var="lookUp">
								<form:option value="${lookUp.contId}" code="">${lookUp.contNo}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2"><spring:message
							code="tender.vendorname" text="Contractor Name" /></label>
					<div class="col-sm-4">
						<form:input path="" id="contp2Name"
							class="form-control mandColorClass " value="" readonly="true" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="mb.contract.StartDate" text="Contract From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="contFromDate" class="form-control "
								value="" readonly="true" />
							<label class="input-group-addon" for=""><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span></label>
						</div>
					</div>
					<%-- <label class="col-sm-2 control-label"><spring:message
							code="mb.contract.EndDate" text="Contract To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="contToDate"
								class="form-control " value="" readonly="true" />
							<label class="input-group-addon" for=""><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span></label>
						</div>
					</div> --%>
				</div>

				<div class="text-center padding-bottom-10">
					<button class="btn btn-primary hidden-print " type="button"
						onclick="printContractAgreement();">
						<i class="fa fa-print padding-right-5"></i>
						<spring:message code="work.estimate.report.print" text="Print" />
					</button>
					<c:if test="${command.sudaEnv eq 'N'}">
					<button type="button" class="btn btn-primary hidden-print"
						onClick="printNoticeInvitingTender();"
						title="Notice Inviting Tender">
						<i class="fa fa-print padding-right-5" aria-hidden="true"></i>
						<spring:message code="wms.NIT" text="NIT" />
					</button></c:if>
					<button class="btn btn-warning  reset" type="button"
						onclick="window.location.href='ContractAgreementPrint.html'">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="works.management.reset" />
					</button>
					<button class="button-input btn btn-danger" type="button"
						onclick="window.location.href='AdminHome.html'">
						<i class="button-input"></i><i
							class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>