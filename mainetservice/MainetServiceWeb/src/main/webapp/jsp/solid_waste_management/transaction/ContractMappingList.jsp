<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/ContractMapping.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="contract.mapping.heading"
					text="Contract Mapping Summary" />
			</h2>
			<apptags:helpDoc url="PopulationMaster.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="ContractMapping.html" name="ContractMapping"
				id="ContractMappingList" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
					<apptags:input labelCode="contract.mapping.contract.no"
						path="contractMappingDTO.contractNo" isMandatory="false"
						cssClass=""></apptags:input>
					<apptags:date fieldclass="datepicker"
						labelCode="contract.mapping.contract.date"
						datePath="contractMappingDTO.contDate" isMandatory="false"
						cssClass="custDate mandColorClass">
					</apptags:date>

				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search"
						onclick="search(this);">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning"
						onclick="resetContract();">
						<spring:message code="solid.waste.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add"
						onclick="addContractMappingForm('ContractMapping.html','AddContractMapping');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="solid.waste.add"
							text="Add" />
					</button>
				</div>
				<div class="table-responsive">
					<table class="table table-bordered table-striped1">
						<thead>
							<tr>
								<th><spring:message code="contract.mapping.contract.no"
										text="Contract No." /></th>
								<th><spring:message code="contract.mapping.department"
										text="Department" /></th>

								<th><spring:message code="contract.mapping.contract.date"
										text="Contract Date" /></th>

								<th><spring:message code="contract.mapping.represented.by"
										text="Represented By" /></th>
								<th><spring:message code="contract.mapping.vendor.name"
										text="Vendor Name" /></th>
								<th><spring:message
										code="contract.mapping.contract.from.date"
										text="Contract From Date" /></th>
								<th><spring:message
										code="contract.mapping.contract.to.date"
										text="Contract To Date" /></th>
								<th width="200"><spring:message code="contract.mapping.view.mapping"
										text="View Mapping" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.contractMappingDTOList}" var="data"
								varStatus="index">
								<tr>
									<td>${data.contractNo}</td>
									<td>${data.deptName}</td>
									<td>${data.contDate}</td>
									<td>${data.representedBy}</td>
									<td>${data.vendorName}</td>
									<td>${data.fromDate}</td>
									<td>${data.toDate}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onclick="getViewNotMapped('ContractMapping.html','ViewContractMapping',${data.contId});"
											 data-original-title="View" title="View">
											<strong class="fa fa-eye"></strong>
										</button>
										<button
											onclick="printContractMappint('ContractMapping.html','print',${data.contId});"
											data-original-title="Print" title="Print"
											class="btn btn-darkblue-3" type="button">
											<i class="fa fa-print"></i>
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



























