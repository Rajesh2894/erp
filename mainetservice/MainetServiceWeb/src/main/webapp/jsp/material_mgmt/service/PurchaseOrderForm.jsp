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
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/material_mgmt/service/purchaseOrder.js"></script>
<link rel="stylesheet" href="assets/libs/bootstrap-multiselect/css/bootstrap-multiselect.css" type="text/css">
<script type="text/javascript" src="assets/libs/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
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
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="purchase.requisition.purOrder" text="Purchase Order" />
			</h2>
			<apptags:helpDoc url="PurchaseOrder.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="PurchaseOrder.html" name="PurchaseOrderForm" 
				id="PurchaseOrderForm" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
					
				<form:input type="hidden" path="removeOverheadIds" id="removeOverheadIds" />
				<form:input type="hidden" path="removeTncIds" id="removeTncIds" />
				<form:input type="hidden" path="removeEncIds" id="removeEncIds" />
				<form:hidden path="saveMode" id="saveMode" />			
					
				<div class="form-group">
					<c:if test="${command.saveMode ne 'A'}">
						<apptags:input labelCode="purchase.requisition.purOrderNo" path="purchaseOrderDto.poNo" 
							isMandatory="true" cssClass="form control" isDisabled="true">
						</apptags:input>
					</c:if>

					<label class="col-sm-2 control-label required-control" ><spring:message
							code="purchase.requisition.purOrderDate" text="Purchase Order Date" /></label>
					<div class="col-sm-4">
						<form:input id="poDate" placeholder="dd/mm/yyyy" path="purchaseOrderDto.poDate" 
							disabled="${command.saveMode ne 'A'}" class="form-control poDate" />
					</div>	
				</div>
				
				<div class="form-group">
					<form:hidden path="purchaseOrderDto.storeId" id="storeNameId" />
					<apptags:input labelCode="store.master.name" path="purchaseOrderDto.storeName"
							isMandatory="true" isDisabled="true" />	
				
					<form:hidden path="purchaseOrderDto.vendorId" id="vendorId" />
					<apptags:input labelCode="purchase.requisition.vendorName" path="purchaseOrderDto.vendorName"
							isMandatory="true" isDisabled="true" />	
				</div> 
				
				<div class="form-group">
					<c:if test="${command.saveMode eq 'A'}">
						<label class="control-label col-sm-2 required-control" for="PR Number">
								<spring:message code="pR.Number" text="PR Number" /></label>
						<div class="col-sm-4">
							<form:select path="purchaseOrderDto.prIdGetData" id="prId" 
								onchange="getPurchaseRequisitionData(this);" data-rule-required="true"
								cssClass="form-control mandColorClass chosen-select-no-results" >
								<form:option value="">
									<spring:message code="material.management.select" text="Select" />
								</form:option>
								<c:forEach items="${command.requisitionObject}" var="requisition">
									<form:option value="${requisition[0]}" code="${requisition[1]}">${requisition[1]}</form:option>
								</c:forEach>
							</form:select>
						</div>	
					</c:if>

					<apptags:input labelCode="purchase.requisition.expectDeliveryDate" cssClass="expectedDate" 
						placeholder="dd/mm/yyyy" path="purchaseOrderDto.expectedDeliveryDate" 
						isDisabled="${command.saveMode ne 'A'}" isMandatory="true" />
				</div>

				
		<!--------------------------------- Purchase Requisition Details --------------------------------------------------->
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table id="unitDetailTable"
								class="table table-striped table-bordered appendableClass unitDetail">
								<thead>
									<tr>
										<th><spring:message code="store.master.srno" text="Sr.No." /></th>
										<th><spring:message code="purchase.requisition.prno" text="PR.No" /></th>
										<th><spring:message code="purchase.requisition.prDate" text="PR Date" /></th>
									</tr>
								</thead>
								<tbody>
									<tr class="firstUnitRow">
										<td align="center" width="10%"><form:input path="" cssClass="form-control mandColorClass " 
											id="sequence0" value="1" disabled="true" /></td>
										<td><form:input path="purchaseOrderDto.purchaseOrderDetDto[0].prNo" id="prNoId0"
												type="text" class="form-control" readonly="true" /></td>
										<td><form:input path="purchaseOrderDto.purchaseOrderDetDto[0].prDate"
												id="prDate0" type="text" class="form-control" readonly="true" /></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				
	
				<h4>
					<spring:message code="material.item.master.itemDetails" text="Item Details" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="count" value="0" scope="page"></c:set>
							<table id="purRequisitionDetailTable"
								class="table table-striped table-bordered appendableClass unitDetail">
								<thead>
									<tr>
										<th align="center" width="10%"><spring:message code="store.master.srno" text="Sr.No." /></th>
										<th><spring:message code="material.item.master.name" text="Item Name" /><i class="text-red-1">*</i></th>
										<th><spring:message code="U.o.M" text="UoM" /><i class="text-red-1">*</i></th>
										<th><spring:message code="store.master.quantity" text="quantity" /><i class="text-red-1">*</i></th>
										<th><spring:message code="purchase.requisition.unitPrice" text="Unit Price" /><i class="text-red-1">*</i></th>
										<th><spring:message code="material.item.master.taxper" text="Tax %" /><i class="text-red-1">*</i></th>
										<th><spring:message code="purchase.requisition.totValue" text="Total Value" /><i class="text-red-1">*</i></th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(command.purchaseOrderDto.purchaseOrderDetDto)>0 }">
											<c:forEach items="${command.purchaseOrderDto.purchaseOrderDetDto}" var="list" varStatus="status"> 
												<c:set value="${status.index}" var="count"></c:set>
												<tr class="purRequisitionDetRow">
													<td><form:input path="" class="form-control" disabled="true"
														id="sequence${count}" value="${count+1}"></form:input></td>
														
													<td><form:hidden path="purchaseOrderDto.purchaseOrderDetDto[${count}].podet" id="podet${count}" />
														<form:hidden path="purchaseOrderDto.purchaseOrderDetDto[${count}].prId" id="prId${count}" />
														<form:input path="purchaseOrderDto.purchaseOrderDetDto[${count}].itemId" value="${list.itemName}"
															class="form-control mandColorClass" id="itemId${count}" readonly="true" /></td>
															
													<td><form:input path="purchaseOrderDto.purchaseOrderDetDto[${count}].uonName"
														id="uomId${count}" type="text" class="form-control" readonly="true" />  </td>
													<td><form:input path="purchaseOrderDto.purchaseOrderDetDto[${count}].quantity"
														onkeypress="return hasAmount(event, this, 10, 2)" id="quantity${count}" type="text" 
														class="form-control" disabled="true" />  </td>
													<td><form:input path="purchaseOrderDto.purchaseOrderDetDto[${count}].unitPrice"
														id="unitPrice${count}" type="text" class="form-control" onchange="calculateTotal()"
														onblur="getAmountFormatInDynamic((this),'unitPrice${count}')" disabled="${command.saveMode eq 'V'}"
														onkeypress="return hasAmount(event, this, 10, 2)"/></td>
													<td><form:input path="purchaseOrderDto.purchaseOrderDetDto[${count}].tax"
														id="taxId${count}" type="text" class="form-control" onchange="calculateTotal()"
														onblur="getAmountFormatInDynamic((this),'taxId${count}')" disabled="${command.saveMode eq 'V'}"
														onkeypress="return hasAmount(event, this, 3, 2)"/></td>
													<td><form:input path="purchaseOrderDto.purchaseOrderDetDto[${count}].totalAmt"
														id="totalAmt${count}" type="text" class="form-control totalAmt" readonly="true"
														onkeypress="return hasAmount(event, this, 3, 2)"/></td>
												</tr>
												<c:set var="count" value="${count+1}" scope="page" />
											</c:forEach>											
										</c:when>
										<c:otherwise>
											<tr class="purRequisitionDetRow">
												<td><form:input path="" class="form-control" disabled="true"
													id="sequence${count}" value="${count+1}"></form:input></td>
												<td><form:hidden path="purchaseOrderDto.purchaseOrderDetDto[${count}].podet" id="podet${count}" />
													<form:hidden path="purchaseOrderDto.purchaseOrderDetDto[${count}].prId" id="prId${count}" />
													<form:input path="purchaseOrderDto.purchaseOrderDetDto[${count}].itemId" value="${list.itemName}"
															class="form-control mandColorClass" id="itemId${count}" readonly="true" /></td>
												<td><form:input path="purchaseOrderDto.purchaseOrderDetDto[${count}].uonName"
													id="uomId${count}" type="text" class="form-control" readonly="true" />  </td>
												<td><form:input path="purchaseOrderDto.purchaseOrderDetDto[${count}].quantity"
													onkeypress="return hasAmount(event, this, 10, 2)" id="quantity${count}" 
													type="text" class="form-control" disabled="true" />  </td>
												<td><form:input path="purchaseOrderDto.purchaseOrderDetDto[${count}].unitPrice"
													id="unitPrice${count}" type="text" class="form-control" onchange="calculateTotal()"
													onblur="getAmountFormatInDynamic((this),'unitPrice${count}')" disabled="${command.saveMode eq 'V'}"
													onkeypress="return hasAmount(event, this, 10, 2)"/></td>
												<td><form:input path="purchaseOrderDto.purchaseOrderDetDto[${count}].tax"
													id="taxId${count}" type="text" class="form-control" onchange="calculateTotal()"
													onblur="getAmountFormatInDynamic((this),'taxId${count}')" disabled="${command.saveMode eq 'V'}"
													onkeypress="return hasAmount(event, this, 3, 2)"/></td>
												<td><form:input path="purchaseOrderDto.purchaseOrderDetDto[${count}].totalAmt"
														id="totalAmt${count}" type="text" class="form-control totalAmt" readonly="true"
														onkeypress="return hasAmount(event, this, 3, 2)"/></td>
											</tr>
											<c:set var="count" value="${count+1}" scope="page" />
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
								
							<div class="form-group" >
								<div class="col-sm-6"></div>
								<label class="control-label col-sm-2 required-control" ><spring:message
										code="purchase.requisition.totItemAmount" text="Total Item Amount" /></label>
								<div class="col-sm-4">
									<form:input path="" id="totItemAmount" class="form-control"  readonly="true" />
								</div>
							</div>
						</div>
					</div>
				</div>
				
								
		<!--------------------------------- Overheads --------------------------------------------------->				
				<h4>
					<spring:message code="purchase.requisition.overheads"
						text="Overheads" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="h" value="0" scope="page" />
							<table id="overheadsTableID"
								class="table table-striped table-bordered appendableClass unitDetail">
								<thead>
									<tr>
										<th width="10%"><spring:message code="store.master.srno" text="Sr.No." /></th>
										<th><spring:message code="purchase.requisition.overHeads1" text="Over Heads" /></th>
										<th width="15%"><spring:message code="purchase.requisition.addDeduct" text="Add/Deduct" /></th>
										<th width="15%"><spring:message code="purchase.requisition.Amt" text="Amount" /></th>
										<c:if test="${command.saveMode ne 'V'}">
											<th width="10%"><spring:message code="material.management.action" text="Action" /></th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(command.purchaseOrderDto.purchaseOrderOverheadsDto)>0 }">
											<c:forEach items="${command.purchaseOrderDto.purchaseOrderOverheadsDto}" var="overhd" > 
												<tr class="overheadsTableRow">
													<td align="center"><form:input path="" cssClass="form-control mandColorClass " id="sequence${h}"
															value="${h+1}" disabled="true" /></td>
													<td ><form:hidden path="purchaseOrderDto.purchaseOrderOverheadsDto[${h}].overHeadId" id="overHeadId${h}" />
														<form:input disabled="${command.saveMode eq 'V'}"
															path="purchaseOrderDto.purchaseOrderOverheadsDto[${h}].description"
															id="description${h}" type="text" class="form-control hasCharacterNumbersPercentage" maxlength="100" ></form:input></td>
													<td><form:select onchange="totalOverheadCount();" disabled="${command.saveMode eq 'V'}"
															path="purchaseOrderDto.purchaseOrderOverheadsDto[${h}].overHeadType"
															cssClass="form-control" id="overHeadType${h}" data-rule-required="true">
																<form:option value="0">
																	<spring:message code='' text="Select" />
																</form:option>
																<form:option value="A">
																	<spring:message code="" text="Addition" />
																</form:option>
																<form:option value="D">
																	<spring:message code="" text="Deduction" />
																</form:option>
															</form:select></td>
													<td ><form:input disabled="${command.saveMode eq 'V'}"
															path="purchaseOrderDto.purchaseOrderOverheadsDto[${h}].amount"
															id="amount${h}" type="text" class="form-control amountOverhead" 
															onkeypress="return hasAmount(event, this, 10, 2)"
															onchange="totalOverheadCount();"></form:input></td>
			
													<c:if test="${command.saveMode ne 'V'}">
														<td class="text-center" >
															<a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" 
																class="addOF btn btn-success btn-sm " id="addUnitRow"><i
																class="fa fa-plus-circle"></i></a>
															<a href="javascript:void(0);" class="remOF btn btn-danger btn-sm delete"
																title="<spring:message code="material.management.delete" text="Delete" />"
																id="deleteRow_"><i class="fa fa-trash-o"></i></a>  
														</td>
													</c:if>
												</tr>
												<c:set var="h" value="${h+1}" scope="page" />
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr class="overheadsTableRow">
													<td align="center"><form:input path="" cssClass="form-control mandColorClass " id="sequence${h}"
															value="${h+1}" disabled="true" /></td>
													<td ><form:input disabled="${command.saveMode eq 'V'}"
															path="purchaseOrderDto.purchaseOrderOverheadsDto[${h}].description"
															id="description${h}" type="text" class="form-control hasCharacterNumbersPercentage" maxlength="100" ></form:input></td>
													<td><form:select onchange="totalOverheadCount();" disabled="${command.saveMode eq 'V'}"
															path="purchaseOrderDto.purchaseOrderOverheadsDto[${h}].overHeadType"
															cssClass="form-control" id="overHeadType${h}" data-rule-required="true">
																<form:option value="0">
																	<spring:message code='' text="Select" />
																</form:option>
																<form:option value="A">
																	<spring:message code="" text="Addition" />
																</form:option>
																<form:option value="D">
																	<spring:message code="" text="Deduction" />
																</form:option>
															</form:select></td>
													<td ><form:input disabled="${command.saveMode eq 'V'}"
															path="purchaseOrderDto.purchaseOrderOverheadsDto[${h}].amount"
															id="amount${h}" type="text" class="form-control amountOverhead" 
															onkeypress="return hasAmount(event, this, 10, 2)"
															onchange="totalOverheadCount();"></form:input></td>
			
													<c:if test="${command.saveMode ne 'V'}">
														<td class="text-center" >
															<a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" 
																class="addOF btn btn-success btn-sm " id="addUnitRow"><i
																class="fa fa-plus-circle"></i></a>
															<a href="javascript:void(0);" class="remOF btn btn-danger btn-sm delete"
																title="<spring:message code="material.management.delete" text="Delete" />"
																id="deleteRow_"><i class="fa fa-trash-o"></i></a>  
														</td>
													</c:if>
												</tr>
												<c:set var="h" value="${h+1}" scope="page" />
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
							<div class="form-group">
								<label class="control-label col-sm-2 required-control"><spring:message
										code="total.Overhead.Amount" text="Total Overhead Amount" /></label>
								<div class="col-sm-4">
									<form:input path=""  id="totOverheadAmount" class="form-control" readonly="true" />
								</div>
								<label class="control-label col-sm-2 required-control"><spring:message
										code="total.PO.Amount" text="Total PO Amount" /></label>
								<div class="col-sm-4">
									<form:input path="" id="totPoAmount" class="form-control" readonly="true" />
								</div>
							</div>
						</div>
					</div>
				</div>


		<!--------------------------------- Term & Condition --------------------------------------------------->				
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="k" value="0" scope="page" />
							<table id="tncDetailTableID"
								class="table table-striped table-bordered appendableClass unitDetail">
								<thead>
									<tr>
										<th><spring:message code="store.master.srno" text="Sr.No." /></th>
										<th><spring:message code="purchase.requisition.termNCondition" text="Term & Condition" />
												<i class="text-red-1">*</i></th>
										<c:if test="${command.saveMode ne 'V'}">
											<th width="10%"><spring:message code="material.management.action" text="Action" /></th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(command.purchaseOrderDto.purchaseorderTncDto)> 0 }">
											<c:forEach items="${command.purchaseOrderDto.purchaseorderTncDto}" var="overhd" > 
												<tr class="tncDetailTableRow">
													<td align="center" width="10%"><form:input path="" disabled="true"
															cssClass="form-control mandColorClass " id="sequence${k}" value="${k+1}" /></td>
													<td><form:hidden path="purchaseOrderDto.purchaseorderTncDto[${k}].tncId" id="tncId${k}" />
														<form:input maxlength="250" disabled="${command.saveMode eq 'V'}"
															path="purchaseOrderDto.purchaseorderTncDto[${k}].description"
															id="tncdescription${k}" type="text" class="form-control" /></td>
													<c:if test="${command.saveMode ne 'V'}">
														<td class="text-center" width="10%">
															<a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" 
																class="addTnc btn btn-success btn-sm " id="addTncRow"><i
																class="fa fa-plus-circle"></i></a>
															<a href="javascript:void(0);" class="remTnc btn btn-danger btn-sm delete"
																title="<spring:message code="material.management.delete" text="Delete" />"
																id="deleteTncRow"><i class="fa fa-trash-o"></i></a>  
														</td>
													</c:if>
												</tr>
												<c:set var="k" value="${k+1}" scope="page" />
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr class="tncDetailTableRow">
													<td align="center" width="10%"><form:input path="" disabled="true"
															cssClass="form-control mandColorClass " id="sequence${k}" value="${k+1}" />
													<td><form:input maxlength="250" disabled="${command.saveMode eq 'V'}"
															path="purchaseOrderDto.purchaseorderTncDto[${k}].description"
															id="tncdescription${k}" type="text" class="form-control" /></td>
													<c:if test="${command.saveMode ne 'V'}">
														<td class="text-center" width="10%">
															<a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" 
																class="addTnc btn btn-success btn-sm " id="addTncRow"><i
																class="fa fa-plus-circle"></i></a>
															<a href="javascript:void(0);" class="remTnc btn btn-danger btn-sm delete"
																title="<spring:message code="material.management.delete" text="Delete" />"
																id="deleteTncRow"><i class="fa fa-trash-o"></i></a>  
														</td>
													</c:if>
												</tr>
												<c:set var="k" value="${k+1}" scope="page" />										
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				
								
		<!--------------------------------- Enclosure --------------------------------------------------->				
				<h4>
					<spring:message code="purchase.requisition.enclosure"
						text="Enclosure" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table id="encTableDetailTableID"
								class="table table-striped table-bordered appendableClass unitDetail">
								<thead>
									<tr>
										<th width="10%"><spring:message code="store.master.srno"
												text="Sr.No." /></th>
										<th><spring:message
												code="material.item.master.description" text="Description" />
												 <i class="text-red-1">*</i></th>
										<th width="25%"><spring:message code="purchase.requisition.uploadDoc"
												text="Upload Document" /> <i class="text-red-1">*</i>
												</th>
										<c:if test="${command.saveMode ne 'V'}">
											<th width="10%"><spring:message code="material.management.action" text="Action" /></th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(command.purchaseOrderDto.purchaseorderAttachmentDto)>0 }">		
											<c:forEach items="${command.purchaseOrderDto.purchaseorderAttachmentDto}" var="list" varStatus="status"> 
											  <tr class="encTableDetailTableRow">
												<td align="center" width="10%"><form:input path="" disabled="true"
														cssClass="form-control mandColorClass " id="sequence${d}"
														value="${d+1}" /></td>
												<td><form:hidden path="purchaseOrderDto.purchaseorderAttachmentDto[${d}].podocId"
															id="podocId${d}" />
													<form:input path="purchaseOrderDto.purchaseorderAttachmentDto[${d}].description"
														id="encdescription${d}" type="text" class="form-control hasCharacterNumbers" disabled="${command.saveMode eq 'V'}"></form:input></td>
														
												<td width="20%" class="text-center">
													<c:choose>
														<c:when test="${command.saveMode eq 'V'}">
															<c:if test="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0] ne null  && not empty command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0] }">
																<input type="hidden" name="deleteFileId"
																	value="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0].attId}">
																<input type="hidden" name="downloadLink"
																	value="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0]}">
																<apptags:filedownload
																	filename="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0].attFname}"
																	filePath="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0].attPath}"
																	actionUrl="PurchaseOrder.html?Download"></apptags:filedownload>	
															</c:if>
														</c:when>
														<c:otherwise>
															<apptags:formField fieldType="7"
																fieldPath="purchaseOrderDto.purchaseorderAttachmentDto[${d}].attachments[${d}].uploadedDocumentPath" 
																currentCount="${d}" showFileNameHTMLId="true"
																folderName="${d}" fileSize="BND_COMMOM_MAX_SIZE"
																isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
																validnFunction="ALL_UPLOAD_VALID_EXTENSION">
															</apptags:formField>
															<small class="text-blue-2"> <spring:message code="store.upload"
																text="(Upload File upto 5MB)" /></small>
															<span class="text-blue-2"> <spring:message code="store.upload.desc"
																text="(Only pdf,doc,docx,jpeg,jpg,png,gif,bmp extension(s) file(s) are allowed.)" />
															</span>
															<c:if test="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0] ne null  && not empty command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0] }">
																<input type="hidden" name="deleteFileId"
																	value="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0].attId}">
																<input type="hidden" name="downloadLink"
																	value="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0]}">
																<apptags:filedownload
																	filename="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0].attFname}"
																	filePath="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0].attPath}"
																	actionUrl="PurchaseOrder.html?Download"></apptags:filedownload>
															</c:if>
														</c:otherwise>
													</c:choose>
												</td>
												<c:if test="${command.saveMode ne 'V'}">
													<td class="text-center" width="10%">
														<a  href="javascript:void(0);" onclick='fileCountUpload(this);' class="btn btn-success btn-sm"
												   			title="<spring:message code="material.management.add" text="Add" />" 
												   			id="addEncRow"><i class="fa fa-plus-circle"></i></a>												
														<a href="javascript:void(0);" class="btn btn-danger btn-sm delete" 
															onclick="deleteDocDetails($(this),${d}, ${list.podocId});"
															title="<spring:message code="material.management.delete" text="Delete" />"
															id="deleteEncRow" ><i class="fa fa-trash-o deleteEncRow"></i></a>
													</td>
												</c:if>
											  </tr>
											  <c:set var="d" value="${d + 1}" scope="page" />
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr class="encTableDetailTableRow">
												<td align="center" width="10%"><form:input path="" disabled="true"
														cssClass="form-control mandColorClass " id="sequence${d}"
														value="${d+1}" /></td>
												<td><form:input disabled="${command.saveMode eq 'V'}"
														path="purchaseOrderDto.purchaseorderAttachmentDto[${d}].description"
														id="encdescription${d}" type="text" class="form-control hasCharacterNumbers"></form:input></td>
												<td width="20%" class="text-center">
													<c:choose>
														<c:when test="${command.saveMode eq 'V'}">
															<c:if test="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0] ne null  && not empty command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0] }">
																<input type="hidden" name="deleteFileId"
																	value="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0].attId}">
																<input type="hidden" name="downloadLink"
																	value="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0]}">
																<apptags:filedownload
																	filename="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0].attFname}"
																	filePath="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0].attPath}"
																	actionUrl="PurchaseOrder.html?Download"></apptags:filedownload>	
															</c:if>
														</c:when>
														<c:otherwise>
															<apptags:formField fieldType="7"
																fieldPath="purchaseOrderDto.purchaseorderAttachmentDto[${d}].attachments[${d}].uploadedDocumentPath" 
																currentCount="${d}" showFileNameHTMLId="true"
																folderName="${d}" fileSize="BND_COMMOM_MAX_SIZE"
																isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
																validnFunction="ALL_UPLOAD_VALID_EXTENSION">
															</apptags:formField>
															<span class="text-blue-2"> <spring:message code="store.upload"
																text="(Upload File upto 5MB)" /></span>
															<span class="text-blue-2"> <spring:message code="store.upload.desc"
																text="(Only pdf,doc,docx,jpeg,jpg,png,gif,bmp extension(s) file(s) are allowed.)" />
															</span>
															<c:if test="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0] ne null  && not empty command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0] }">
																<input type="hidden" name="deleteFileId"
																	value="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0].attId}">
																<input type="hidden" name="downloadLink"
																	value="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0]}">
																<apptags:filedownload
																	filename="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0].attFname}"
																	filePath="${command.purchaseOrderDto.purchaseorderAttachmentDto[d].attachDocsList[0].attPath}"
																	actionUrl="PurchaseOrder.html?Download"></apptags:filedownload>
															</c:if>
														</c:otherwise>
													</c:choose>													
												</td>
												<c:if test="${command.saveMode ne 'V'}">
													<td class="text-center" width="10%">
														<a  href="javascript:void(0);" onclick='fileCountUpload(this);' class="btn btn-success btn-sm"
												   			title="<spring:message code="material.management.add" text="Add" />" 
												   			id="addEncRow"><i class="fa fa-plus-circle"></i></a>
														<a href="javascript:void(0);" class="btn btn-danger btn-sm delete" 
															onclick="deleteDocDetails($(this),${d});"
															title="<spring:message code="material.management.delete" text="Delete" />"
												 		   	id="deleteEncRow" ><i class="fa fa-trash-o"></i></a> 
													</td>
												</c:if>
											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:otherwise>
									</c:choose>

								</tbody>
							</table>
						</div>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<c:if test="${command.saveMode ne 'V' }">
						<button type="button" class="btn btn-blue-2 " onclick="saveDraftPurchaseOrderForm(this);">
							<spring:message code="master.save" text="Save" />
						</button>
						<button type="button" class="btn btn-success btn-submit" onclick="submitPurchaseOrderForm(this);">
							<spring:message code="material.management.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'A' }">
						<button type="button" class="btn btn-warning" onclick="resetPurOrderForm();">
							<spring:message code="material.management.reset" text="Reset" />
						</button>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						onclick="backPurOrderForm();" id="button-Cancel">
						<spring:message code="material.management.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

