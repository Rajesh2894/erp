<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/material_mgmt/service/purRequisition.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="purchase.requisition.heading"
					text="Purchase Requisition Summary" />
			</h2>
			<apptags:helpDoc url="PurchaseRequisition.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="PurchaseRequisition.html"
				name="PurchaseRequisition" id="PurchaseRequisitionId"
				class="form-horizontal">
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
					<label class="control-label col-sm-2" for="Store Name"><spring:message
							code="store.master.name" text="Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="purchaseRequistionDto.storeId"
							cssClass="form-control mandColorClass chosen-select-no-results" id="storeNameId"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeIdNameList}" var="data">
								<form:option value="${data[0]}">${data[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2" for="PR.No"><spring:message
							code="purchase.requisition.prno" text="PR.No" /></label>
					<div class="col-sm-4">
						<form:select path="purchaseRequistionDto.prNo"
							cssClass="form-control mandColorClass chosen-select-no-results"
							id="prnoId" data-rule-required="true">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.purchaseRequistionList}" var="purchaseReq">
								<form:option value="${purchaseReq.prNo}">${purchaseReq.prNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				
				<div class="form-group">
					<apptags:date fieldclass="lessthancurrdate" labelCode="material.management.fromDate" 
						datePath="purchaseRequistionDto.fromDate" cssClass="fromDateClass" />

					<apptags:date fieldclass="lessthancurrdate" labelCode="material.management.toDate"
						datePath="purchaseRequistionDto.toDate" cssClass="toDateClass" />
				</div>
				
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search" onclick="searchPurchaseRequisition(this);">
						<i class="fa fa-search"></i>
						<spring:message code="material.management.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" onclick="window.location.href='PurchaseRequisition.html'">
						<i class="fa fa-refresh"></i>
						<spring:message code="material.management.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add"
						onclick="addPurchaseRequisitionForm('PurchaseRequisition.html','addPurRequisition');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="material.management.add" text="Add" />
					</button>
				</div>
				
				<div class="table-responsive">
					<table id="id_purchaseRequisitin" summary="Store Master Data"
						class="table table-bordered table-striped rcm">
						<thead>
							<tr>
								<th><spring:message code="purchase.requisition.prno" text="PR.No" /></th>
								<th><spring:message code="purchase.requisition.prDate" text="PR Date" /></th>
								<th><spring:message code="store.master.name" text="Store Name" /></th>
								<th><spring:message code="store.master.status" text="Status" /></th>
								<th width="250"><spring:message code="material.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.purchaseRequistionList}" var="data"
								varStatus="index">
								<tr>
									<td class="text-center">${data.prNo}</td>
									<td class="text-center"><fmt:formatDate pattern="dd/MM/yyyy" value="${data.prDate}" /></td>
									<td class="text-center">${data.departmentName}</td>
									<c:if test="${data.status eq 'D'}">
										<td class="text-center"><spring:message code="" text="Draft" /></td>
									</c:if>
									<c:if test="${data.status eq 'P'}">
										<td class="text-center"><spring:message code="" text="Pending for Approval" /></td>
									</c:if>
									<c:if test="${data.status eq 'A'}">
										<td class="text-center"><spring:message code="" text="Approved" /></td>
									</c:if>
									<c:if test="${data.status eq 'R'}">
										<td class="text-center"><spring:message code="" text="Rejected" /></td>
									</c:if>
									<c:if test="${data.status eq 'N'}">
										<td class="text-center"><spring:message code="" text="Delete" /></td>
									</c:if>
                                    <c:if test="${data.status eq 'T'}">
										<td class="text-center"><spring:message code="" text="Tender Done" /></td>
									</c:if>
									<c:if test="${data.status eq 'I'}">
										<td class="text-center"><spring:message code="" text="Tender Inprocess" /></td>
									</c:if>

									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="<spring:message code="material.management.view"  text="view" />"
											onclick="getPurchaseRequisitionData('PurchaseRequisition.html','viewPurchaseRequisition',${data.prId})">
											<i class="fa fa-eye"></i>
										</button>
										<c:if test="${data.status eq 'D'}">
											<button type="button" class="btn btn-warning btn-sm"
												onClick="getPurchaseRequisitionData('PurchaseRequisition.html','editPurchaseRequisition',${data.prId})"
												title="<spring:message code="material.management.edit" text="Edit" />">
												<i class="fa fa-pencil"></i>
											</button>
											<button type="button" class="btn btn-danger btn-sm"
												onClick="deletePurchaseRequisitionData('PurchaseRequisition.html','deletePurReqisition',${data.prId})"
												title="<spring:message code="material.management.delete" text="Delete" />">
												<i class="fa fa-trash"></i>
											</button>
										</c:if>
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
