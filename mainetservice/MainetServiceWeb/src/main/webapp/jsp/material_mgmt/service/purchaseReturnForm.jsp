<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/material_mgmt/service/purchaseReturn.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<style>
.input-group-field {
	display: table-cell;
	width: 114px;
}
</style>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start info box -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="material.management.purchaseReturn" text="Purchase Return" />
			</h2>
			<apptags:helpDoc url="PurchaseReturn.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>
			
			<form:form action="PurchaseReturn.html"
				name="purchaseReturnForm" id="purchaseReturnFormId"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close"><span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				
				<form:hidden path="saveMode" id="saveMode" />				
				
				
				<div class="form-group">

					<c:choose>
						<c:when test="${command.saveMode eq 'A'}">
							<label class="col-sm-2 control-label required-control" for="grnNo"><spring:message
									code="material.management.grnNo" text="GRN No." /></label>
							<div class="col-sm-4">
								<form:select path="purchaseReturnDto.grnId"
									cssClass="form-control mandColorClass chosen-select-no-results"
									id="grnId" data-rule-required="true">
									<form:option value="">
										<spring:message code="material.management.select" text="Select" />
									</form:option>
									<c:forEach items="${command.goodsReceivedNotesDtoList}" var="grnList">
										<form:option value="${grnList.grnid}" code="${grnList.grnno}">${grnList.grnno}</form:option>
									</c:forEach>
								</form:select>
							</div>
		
							<div class="col-sm-6">
								<button type="button" class="btn btn-blue-2" onclick="searchInspectionDataById(this);">
									<spring:message code="material.management.loadRejectedItems" text="Get Rejected Items" />
								</button>
							</div>								
						</c:when>
						<c:otherwise>
							<apptags:input labelCode="material.management.purchaseReturnNo" path="purchaseReturnDto.returnNo" 
								isDisabled="true"/>
							
							<apptags:input labelCode="material.management.grnNo" isMandatory="true" path="purchaseReturnDto.grnNo" />
						</c:otherwise>
					</c:choose>
				</div>

				<div class="form-group">
					<apptags:date fieldclass="lessthancurrdate" labelCode="material.management.returnDate" 
						datePath="purchaseReturnDto.returnDate" isMandatory="true"  />
					
					<form:hidden path="purchaseReturnDto.poId" id="poId" />
					<apptags:input labelCode="material.management.poNo" path="purchaseReturnDto.poNo" 
							isMandatory="true" isReadonly="true" />			
				</div>

				<div class="form-group">	
					<apptags:date fieldclass="datepicker" labelCode="material.management.grnDate" 
						datePath="purchaseReturnDto.grnDate" isMandatory="true" isDisabled="true" />				
							
					<form:hidden path="purchaseReturnDto.storeId" id="storeId" />
					<apptags:input labelCode="material.management.store" path="purchaseReturnDto.storeName"
							isMandatory="true" isReadonly="true" />			
				</div>

				<div class="form-group">
					<form:hidden path="purchaseReturnDto.vendorId" id="vendorId" />
					<apptags:input labelCode="purchase.requisition.vendorName" path="purchaseReturnDto.vendorName" 
							isMandatory="true" isReadonly="true" />			
					
					<apptags:textArea labelCode="material.management.noting" isMandatory="true" 
							path="purchaseReturnDto.noting" maxlegnth="250" />
				</div>



	<!---------------------- Rejected Item Table ------------------------->
				<h4>
					<spring:message code="material.management.grnRejectedItems" text="GRN Rejected Items" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table id="purchaseReturnItems" summary="GRN Rejected Items"
								class="table table-bordered table-striped rcm">
								<thead>
									<tr>
										<th width="10%"><spring:message code="material.management.SrNo" text="Sr.No" /></th>
										<th><spring:message code="material.management.itemName" text="Item Name" />
												<i class="text-red-1">*</i></th>
										<th><spring:message code="material.management.UoM" text="UoM" />
												<i class="text-red-1">*</i></th>
										<th><spring:message code="material.management.batchOrSerialNo" text="Batch No. / Serial No." /></th>
										<th><spring:message code="material.management.rejectedQuantity" text="Rejected Quantity" />
												<i class="text-red-1">*</i></th>
										<th><spring:message code="material.management.remarks" text="Remarks" />
												<i class="text-red-1">*</i></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.purchaseReturnDto.purchaseReturnDetDtoList}" var="data" varStatus="index">
										<tr class="ExpiredItemDetailsRow">
											<td class="text-center"><input type="text" class="form-control text-center" disabled="true"
												id="SrNo${d}" value="${d+1}" /></td>
												
											<td><form:hidden path="purchaseReturnDto.purchaseReturnDetDtoList[${d}].grnId" 
													id="grnId${d}" />
												<form:hidden path="purchaseReturnDto.purchaseReturnDetDtoList[${d}].grnItemEntryId" 
													id="grnItemEntryId${d}" />
												<form:input
													path="purchaseReturnDto.purchaseReturnDetDtoList[${d}].itemId" id="itemId${d}"
													class="form-control mandColorClass" value="${data.itemName}"
													data-rule-required="true" disabled="true" /></td>
											
											<td class="text-center"><form:input path="purchaseReturnDto.purchaseReturnDetDtoList[${d}].uomName"
													value="${data.uomName}" id="uomName${d}" type="text" class="form-control" readonly="true" /></td>
												
											<td class="text-center"><form:input
													path="purchaseReturnDto.purchaseReturnDetDtoList[${d}].itemNo" id="itemNo${d}"
													class="form-control mandColorClass" value="${data.itemNo}"
													data-rule-required="true" disabled="true" /></td>
											
											<td class="text-center"><form:input
													path="purchaseReturnDto.purchaseReturnDetDtoList[${d}].quantity" id="quantity${d}"
													class="form-control mandColorClass" value="${data.quantity}"
													data-rule-required="true" disabled="true" /></td>
											
											<td class="text-center"><form:input
													path="purchaseReturnDto.purchaseReturnDetDtoList[${d}].rejectionRemark" id="rejectionRemark${d}"
													class="form-control mandColorClass" value="${data.rejectionRemark}"
													data-rule-required="true" disabled="true" /></td>
												
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				
				<div class="text-center padding-bottom-10">
					<c:if test="${command.saveMode eq 'A'}">
						<button type="button" class="btn btn-success btn-submit" onclick="savePurchaseReturnForm(this);">
							<spring:message code="material.management.submit" text="Submit"></spring:message>
						</button>	
						<button type="button" class="btn btn-warning" 
							onclick="addPurchaseReturnForm('PurchaseReturn.html','addPurchaseReturnForm');">
							<spring:message code="material.management.reset" text="Reset"></spring:message>
						</button>
					</c:if>
					<apptags:backButton url="PurchaseReturn.html"></apptags:backButton>					
				</div>

			</form:form>
		</div>
	</div>
</div>
