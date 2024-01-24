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
<script type="text/javascript" src="js/material_mgmt/service/expiryItems.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="material.management.expired.items" text="Expired Items" />
			</h2>
			<apptags:helpDoc url="DisposalOfStock.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="DisposalOfStock.html" name="DisposalOfStock"
				id="DisposalOfStockId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<%-- <c:if test="${MODE_DATA == 'VIEW'}">
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="material.expired.item.entry.movement.no" text="Movement No" /></label>
					<div class="col-sm-4">
						<form:input path="expiryItemsDto.movementNo" id="itemcode"
							class="form-control mandColorClass" maxLength="500"
							data-rule-required="true" disabled="true"/>
					</div>
					
				</div>
				</c:if> --%>
								
				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="Store"><spring:message
							code="department.indent.storeName" text="Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="expiryItemsDto.storeId"
							class="form-control mandColorClass chosen-select-no-results" id="storeId"
							data-rule-required="false">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeNameList}" var="storeData">
								<form:option value="${storeData.storeId}" code="">${storeData.storeName}</form:option>
							</c:forEach> 
						</form:select>
					</div>
					
					<apptags:date fieldclass="lessthancurrdate" labelCode="material.expired.item.entry.expiry.on.or.before.date" 
						datePath="expiryItemsDto.expiryCheck" isMandatory="true" />
				</div>
				
				<div class="form-group">
					<apptags:date fieldclass="lessthancurrdate" labelCode="material.expired.item.entry.movement.date" 
						datePath="expiryItemsDto.movementDate" isMandatory="true" />
				</div>
				
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search" onclick="searchExpiredItemsFromStore(this);">
						<i class="fa fa-search"></i> <spring:message code="material.expired.item.entry.get.reget.expired.items" 
						text="Get/Re-Get Expired Items"></spring:message>
					</button>
				</div>
							
				<h4>
					<spring:message code="material.expired.item.entry.expired.item.details" text="Expired Item Details" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table id="id_ExpiredItemDetails" summary="Store Master Data"
								class="table table-bordered table-striped rcm">
								<thead>
									<tr>
										<th width="8%"><spring:message code="store.master.srno" text="Sr.No." /><i class="text-red-1">*</i></th>
										<th><spring:message code="binLocMasDto.binLocation" text="Bin Location" />
											<i class="text-red-1">*</i></th>
										<th><spring:message code="material.item.master.itemName" text="Item Name" /><i class="text-red-1">*</i></th>
										<th><spring:message code="store.master.uom" text="UoM" /><i class="text-red-1">*</i></th>
										<th><spring:message code="material.expired.item.entry.batch.no.or.serial.no" 
											text="Batch No./Serial No." /></th>
										<th><spring:message code="material.expired.item.entry.expiry.qty" text="Expiry Quantity" />
											<i class="text-red-1">*</i></th>
										<th width="70" class="text-center"><form:checkbox id="isassetall" path="" value="Y" />
											<i class="text-red-1">*</i></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.expiryItemsDto.expiryItemDetailsDtoList}" var="data" varStatus="index">
										<tr class="ExpiredItemDetailsRow">
											<td class="text-center"><input type="text" 
												class="form-control text-center" disabled="true"
												id="SrNo${d}" value="${d+1}" /></td>
											<td class="text-center"><form:input
													path="expiryItemsDto.expiryItemDetailsDtoList[${d}].binLocation" id="binLocation${d}"
													class="form-control mandColorClass" maxLength="500" value="${data.binLocName}"
													data-rule-required="true" disabled="true" /></td>
											<td class="text-center"><form:input
													path="expiryItemsDto.expiryItemDetailsDtoList[${d}].itemId" id="itemId${d}"
													class="form-control mandColorClass" maxLength="500" value="${data.itemName}"
													data-rule-required="true" disabled="true" /></td>
											<td class="text-center"><form:input 
													path="expiryItemsDto.expiryItemDetailsDtoList[${d}].uomName" value="${data.uomId}"
													id="uomId${d}" type="text" class="form-control" readonly="true" /></td>
											<td class="text-center"><form:input
													path="expiryItemsDto.expiryItemDetailsDtoList[${d}].itemNo" id="itemNo${d}"
													class="form-control mandColorClass" maxLength="500" value="${data.itemNo}"
													data-rule-required="true" disabled="true" /></td>
											<td class="text-center"><form:input
													path="expiryItemsDto.expiryItemDetailsDtoList[${d}].quantity" id="quantity${d}"
													class="form-control mandColorClass" maxLength="500" value="${data.quantity}"
													data-rule-required="true" disabled="true" /></td>
											<td class="text-center"><form:checkbox id="isasset${d}"
													path="expiryItemsDto.expiryItemDetailsDtoList[${d}].flag" value="Y" onclick="checkDisposalForm('isasset${d}');"/></td>
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success btn-submit" onclick="saveExpiredEntryForm(this);">
						<spring:message code="material.expired.item.entry.mark.for.desposal" text="Mark for Disposal"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" onclick="addDisposalOfStockForm('DisposalOfStock.html','addDisposalOfStock');">
						<spring:message code="material.management.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="button-input btn btn-danger" name="button-Cancel" value="Cancel" style=""
						onclick="backForm();" id="button-Cancel">
						<spring:message code="material.management.back" text="Back" />
					</button>
				</div>
				
			</form:form>
		</div>
	</div>
</div>
