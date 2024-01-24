<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/material_mgmt/service/grnInspection.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="GRN.inspection.heading" text="GRN Inspection Summary" />
			</h2>
			<apptags:helpDoc url="GoodsReceivedNoteInspection.html" />
		</div>
	
		<div class="widget-content padding">
			<form:form action="GoodsReceivedNoteInspection.html"
				name="GRN Inspection" id="PurchaseOrderId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label " for="grnNo"><spring:message
							code="material.management.grnNo" /></label>
					<div class="col-sm-4">
						<form:select path="goodsReceivedNotesDto.grnid"  
							cssClass="form-control mandColorClass chosen-select-no-results"
							id="grnid" data-rule-required="true">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.goodsReceivedNotesDtoList}" var="grnList">
								<form:option value="${grnList.grnid}" code="${grnList.grnno}">${grnList.grnno}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label "><spring:message
							code="material.management.poNo" text="PO No." /></label>
					<div class="col-sm-4">
						<form:select path="goodsReceivedNotesDto.poid" id="poid"
							class="form-control chosen-select-no-results input-group"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.purchaseOrderObjects}" var="poObject">
								<form:option value="${poObject[0]}">${poObject[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<apptags:date fieldclass="datepicker" labelCode="material.item.fromDate"
						datePath="goodsReceivedNotesDto.fromDate" cssClass="fromDateClass fromDate"></apptags:date>
				
					<apptags:date fieldclass="datepicker" labelCode="material.item.toDate"
						datePath="goodsReceivedNotesDto.toDate" cssClass="toDateClass fromDateClass"></apptags:date>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message code="Store.Name" text="Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="goodsReceivedNotesDto.storeid" id="storeId"
							class="form-control chosen-select-no-results" data-rule-required="true">
							<form:option value="">
								<spring:message code="Item.Select" />
							</form:option>
							<c:forEach items="${command.storeIdAndNameList}" var="storeMas">
								<form:option value="${storeMas[0]}">${storeMas[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search" onclick="searchGRNInspection(this);">
						<i class="fa fa-search"></i>
						<spring:message code="material.management.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" onclick="window.location.href='GoodsReceivedNoteInspection.html'" >
						<i class="fa fa-refresh"></i>
						<spring:message code="material.management.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add" onclick="openForm('GoodsReceivedNoteInspection.html','addGrnInspection')">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="material.management.add" text="Add" />
					</button>
				</div>
				
				<div class="table-responsive clear">
					<table class="table table-striped table-bordered" id="id_GrnTable">
						<thead>
							<tr>
								<th width="5%" align="center"><spring:message code="store.master.srno" text="Sr.No." /></th>
								<th width="10%" align="center"><spring:message code="material.management.grnNo" text="GRN No." /></th>
								<th width="20%" align="center"><spring:message code="material.management.inspectionDate"
										text="Inspection Date" /></th>
								<th width="20%" align="center"><spring:message code="material.management.poNo" text="PO No." /></th>
								<th width="15%" align="center"><spring:message code="store.master.name" text="Store Name" /></th>
								<th width="10%" align="center"><spring:message code="TbDeathregDTO.form.action" text="Action" /></th>
							</tr>
						</thead>						
						<tbody>
							<c:forEach items="${command.goodsReceivedNotesDtoList}" var="notes" varStatus="item">
								<tr>
									<td class="text-center">${item.count}</td>
									<td align="center">${notes.grnno}</td>
									<td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${notes.inspectiondate}"/></td>
									<td align="center">${notes.poNumber}</td>
									<td align="center">${notes.storeName}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm" title="View Details"
											onclick="getGRNInspectionData('GoodsReceivedNoteInspection.html','viewGRNInspection',${notes.grnid})">
											<i class="fa fa-eye"></i>
										</button>
										<c:if test="${notes.status eq 'D'}">
											<button type="button" class="btn btn-warning btn-sm"
												onclick="getGRNInspectionData('GoodsReceivedNoteInspection.html','editGRNInspection',${notes.grnid})">
												<strong class="fa fa-pencil"></strong><span class="hide"><spring:message
														code="material.management.edit" text="Edit"></spring:message></span>
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
