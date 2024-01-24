<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/works_management/revisedEstimate.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sor.contract.variation.summary"
					text="" />
			</h2>
			<div class="additional-btn">
			<apptags:helpDoc url="WorksRevisedEstimate.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="RevisedEstimate.html" class="form-horizontal"
				name="revisedEstimateForm" id="revisedEstimateForm">
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
								<spring:message code='master.selectDropDwn' />
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
						<!-- This form tag binds the incoming data from the user to the dto using the path attribute -->
						<form:select path=""
							class="form-control chosen-select-no-results"
							label="Select" id="vendorId">
							<form:option value="" selected="true"><spring:message code='master.selectDropDwn' text='Select' /></form:option>
							<c:forEach items="${command.vendorList}" var="vendor">
								<form:option value="${vendor.vmVendorid}">${vendor.vmVendorname}</form:option>
							</c:forEach>

						</form:select>
					</div>
					
					
					
				</div>
				<div class="text-center clear padding-10">
					<button class="btn btn-blue-2  search searchRevisedEstimate" title='<spring:message code="works.management.search" text="Search" />'
						type="button">
						<i class="fa fa-search padding-right-5"></i>
						<spring:message code="works.management.search" text="Search" />
					</button>

					<button type="Reset" class="btn btn-warning" title='<spring:message code="works.management.reset" text="Reset" />'
						onclick="resetRevisedEstimate()">
						<spring:message code="works.management.reset" text="Reset" />
					</button>
					<button class="btn btn-primary add" title='<spring:message code="works.management.add" text="Add" />'
						onclick="openAddRevisedEstimate('WorksRevisedEstimate.html','AddRevisedEstimate');"
						type="button">
						<i class="fa fa-plus-circle padding-right-5"></i>
						<spring:message code="works.management.add" text="Add" />
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
										code="works.management.action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.workOrderContractDetailsDto}"
								var="summaryDto">
								<tr>
									<td class="text-center">${summaryDto.contNo}</td>
									<td class="text-center">${summaryDto.contDate}</td>
									<td align="center">${summaryDto.venderName}</td>
									<td align="center">${summaryDto.workOrderStatus}</td>
									<td align="center"><c:choose>
											<c:when test="${summaryDto.workOrderStatus ne 'Draft'}">
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="<spring:message code="works.management.view"></spring:message>"
													onclick="getActionForDefination(${summaryDto.contId},'V');">
													<i class="fa fa-eye"></i>
												</button>
											</c:when>
											<c:otherwise>
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="<spring:message code="works.management.view"></spring:message>"
													onclick="getActionForDefination(${summaryDto.contId},'V');">
													<i class="fa fa-eye"></i>
												</button>
												<button type="button" class="btn btn-warning btn-sm"
													title="<spring:message code="works.management.edit"></spring:message>"
													onclick="getActionForDefination(${summaryDto.contId},'E');">
													<i class="fa fa-pencil-square-o"></i>
												</button>
												<button type="button" class="btn btn-green-3 btn-sm"
													title="<spring:message code="work.management.send.for.approval"></spring:message>"
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