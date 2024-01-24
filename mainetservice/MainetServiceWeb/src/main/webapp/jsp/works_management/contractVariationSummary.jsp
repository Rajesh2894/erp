<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>s
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/works_management/workContractVariationForm.js"></script>
<script type="text/javascript"
	src="js/works_management/workContractVariationApproval.js"></script>

<apptags:breadcrumb>
</apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sor.contract.variation.summary"
					text="Contract Variation Summary" />
			</h2>
			<div class="additional-btn">
			<apptags:helpDoc url="ContractVariation.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="ContractVariation.html" class="form-horizontal"
				name="workContractVariationForm" id="workContractVariationForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="work.order.contract.no" text="Contract Agreement Number" /></label>
					<div class="col-sm-4">
						<form:select path="newContractId"
							cssClass="form-control chosen-select-no-results" id="contractId"
							onchange="">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.workOrderContractDetailsDto}"
								var="lookUp">
								<form:option value="${lookUp.contNo}" code="">${lookUp.contNo}</form:option>
							</c:forEach>

						</form:select>
					</div>

					<label class="control-label col-sm-2" for="ContractDate"><spring:message
							code="sor.contract.agreement.date" text="Contract Date"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" type="text" class="form-control datepicker"
								id="contractAgreementDate" />
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="work.assignee.vendorName" text="Vendor Name" /></label>
					<div class="col-sm-4">

						<form:input path="" id="vendorId" class="form-control" />
					</div>
				</div>
				<div class="text-center clear padding-10">
					<button class="btn btn-blue-2  search searchContractVariation"
						type="button">
						<i class="button-input"></i>
						<spring:message code="works.management.search" text="" />
					</button>

					<apptags:resetButton></apptags:resetButton>
					<button class="btn btn-success add"
						onclick="openAddContractVariation('ContractVariation.html','AddContractVariation');"
						type="button">
						<i class="button-input"></i>
						<spring:message code="works.management.add" text="" />
					</button>

				</div>
				<div class="table-responsive clear">
					<table class="table table-striped table-bordered" id="datatables">
						<thead>
							<tr>
								<th width="20%" scope="col" align="center"><spring:message
										code="sor.contract.agreement.no" /></th>
								<th width="20%" scope="col" align="center"><spring:message
										code="sor.contract.agreement.date" /></th>
								<th width="20%" scope="col" align="center"><spring:message
										code="work.assignee.vendorName" /></th>
								<th width="20%" scope="col" align="center"><spring:message
										code="work.estimate.status" /></th>
								<th width="20%" scope="col" align="center"><spring:message
										code="estate.grid.column.action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.workOrderContractDetailsDto}"
								var="summaryDto">
								<tr>
									<td>${summaryDto.contNo}</td>
									<td>${summaryDto.contDate}</td>
									<td align="center">${summaryDto.venderName}</td>
									<td align="center">${summaryDto.workOrderStatus}</td>
									<td align="center"><c:choose>
											<c:when test="${summaryDto.workOrderStatus ne 'Draft'}">
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="View"
													onclick="getActionForDefination(${summaryDto.contId},'V');">
													<i class="fa fa-eye"></i>
												</button>
											</c:when>
											<c:otherwise>
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="View"
													onclick="getActionForDefination(${summaryDto.contId},'V');">
													<i class="fa fa-eye"></i>
												</button>
												<button type="button" class="btn btn-warning btn-sm"
													title="Edit"
													onclick="getActionForDefination(${summaryDto.contId},'E');">
													<i class="fa fa-pencil"></i>
												</button>
												<button type="button" class="btn btn-green-3 btn-sm"
													title="Send for Approval"
													onclick="sendForApproval(${summaryDto.contId},'S');">
													<i class="fa fa-share-square-o "></i>
												</button>
											</c:otherwise>
										</c:choose></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>

