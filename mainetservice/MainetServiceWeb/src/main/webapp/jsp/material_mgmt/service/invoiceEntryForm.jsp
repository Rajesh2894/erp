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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/material_mgmt/service/invoiceEntry.js"></script>
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
<!-- Start info box -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="material.management.store.invoice.entry" text="Store Invoice Entry" />
			</h2>
			<apptags:helpDoc url="StoreInvoiceEntry.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>

			<form:form action="StoreInvoiceEntry.html" name="storeInvoiceEntryForm" 
					id="storeInvoiceEntryFormId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
						<span aria-hidden="true">&times;</span></button>
					<span id="errorId"></span>
				</div>

				<form:hidden path="saveMode" id="saveMode" />				
				<form:hidden path="levelCheck" id="levelCheck" />				
				<form:hidden path="invoiceSaveStatus" id="invoiceSaveStatus" />	
				<form:hidden path="invoiceEntryDTO.removeFileById" id="removeFileById" />


				<div class="form-group">
					<c:if test="${command.saveMode eq 'V' || command.saveMode eq 'E'}">
						<label class="col-sm-2 control-label"><spring:message code="material.management.invoice.status"
								text="Invoice Status" /></label>
						<div class="col-sm-4">
							<c:choose>
								<c:when test="${command.invoiceEntryDTO.invoiceStatus eq 'D'}">
									<input type="text" value="<spring:message code="store.draft"
											text="Draft" />" class="form-control" disabled="true" />
								</c:when>
								<c:when test="${command.invoiceEntryDTO.invoiceStatus eq 'P'}">
									<input type="text" value="<spring:message code="material.management.pending.for.approval"
											text="Pending For Approval" />" class="form-control" disabled="true" />
								</c:when>
								<c:when test="${command.invoiceEntryDTO.invoiceStatus eq 'A'}">
									<input type="text" value="<spring:message code="material.management.approved"
											text="Approved" />" class="form-control" disabled="true" />
								</c:when>  
								<c:when test="${command.invoiceEntryDTO.invoiceStatus eq 'R'}">
									<input type="text" value="<spring:message code="purchase.requisition.rejected"
											text="Rejected" />" class="form-control" disabled="true" />
								</c:when>
							</c:choose>					
						</div>
					</c:if>
					<c:if test="${command.saveMode ne 'A'}">
						<apptags:input labelCode="material.management.invoice.number" path="invoiceEntryDTO.invoiceNo" isDisabled="true" />										
					</c:if>
				</div>

				<div class="form-group">
					<c:choose>
						<c:when test="${command.saveMode eq 'A'}">
							<label class="col-sm-2 control-label required-control" for="store"><spring:message
									code="material.management.store" text="Store Name" /></label>
							<div class="col-sm-4">
								<form:select path="invoiceEntryDTO.storeId" id="storeId" class="form-control chosen-select-no-results"
									data-rule-required="true" onchange="getPONumbers()" disabled="${command.saveMode ne 'A'}">
									<form:option value="">
										<spring:message code="material.item.master.select" text="Select" />
									</form:option>
									<c:forEach items="${command.storeIdNameList}" var="data">
										<form:option value="${data[0]}">${data[1]}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</c:when>
						<c:otherwise>
							<form:hidden path="invoiceEntryDTO.storeId" id="storeId" />
							<apptags:input labelCode="material.management.store" path="invoiceEntryDTO.storeName" isMandatory="true" 
									isReadonly="true" />						
						</c:otherwise>
					</c:choose>	

					<apptags:date fieldclass="lessthancurrdate" labelCode="material.management.invoice.date" 
							datePath="invoiceEntryDTO.invoiceDate" isMandatory="true" isDisabled="${command.saveMode ne 'A'}"/>
				</div>

				<div class="form-group">
					<c:choose>
						<c:when test="${command.saveMode eq 'A'}">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="Purchase.order.number" text="Purchase Order No." /></label>
							<div class="col-sm-4">
								<form:select path="invoiceEntryDTO.poId" id="poId" disabled="${command.saveMode ne 'A'}"
									class="form-control chosen-select-no-results input-group"
									data-rule-required="true" onchange="getGRNListByPoId()" >
									<form:option value="">
										<spring:message code="adh.select" text="Select"></spring:message>
									</form:option>
									<c:forEach items="${command.purchaseOrderDtoList}" var="purchase">
										<form:option value="${purchase.poId}">${purchase.poNo}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</c:when>
						<c:otherwise>
							<form:hidden path="invoiceEntryDTO.poId" id="poId" />
							<apptags:input labelCode="Purchase.order.number" path="invoiceEntryDTO.poNumber" isMandatory="true" 
									isReadonly="true" />						
						</c:otherwise>
					</c:choose>		

					<form:hidden path="invoiceEntryDTO.vendorId" id="vendorId" />
					<apptags:input labelCode="purchase.requisition.vendorName" path="invoiceEntryDTO.vendorName" isMandatory="true" 
							isReadonly="true" />			
				</div>
				
				
				<c:if test="${command.saveMode eq 'A'}">
					<div class="form-group">
						<label class="col-sm-2 control-label required-control" for="grnNo"><spring:message
								code="material.management.grnNo" text="GRN No." /></label>
						<div class="col-sm-4">
							<form:select path="invoiceEntryDTO.grnIdList" multiple="true" 
								cssClass="form-control mandColorClass chosen-select-no-results"
								id="grnId" data-rule-required="true">
								<form:option value="">
									<spring:message code="material.management.select" text="Select" />
								</form:option>
								<c:forEach items="${command.goodsReceivedNotesMultiselectList}" var="grnList">
									<form:option value="${grnList.grnid}">${grnList.grnno}</form:option>
								</c:forEach>
							</form:select>
						</div>
	
						<div class="col-sm-6">
							<button type="button" class="btn btn-blue-2" onclick="searchInspectionDataById(this);">
								<spring:message code="material.management.get.grn.details" text="Get GRN Details" />
							</button>
						</div>
					</div>
				</c:if>
				

	<!--------------------------------- GRN Details --------------------------------------------------->
				<h4>
					<spring:message code="material.management.grn.details" text="GRN Details" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table id="unitDetailTable" class="table table-striped table-bordered appendableClass unitDetail">
								<thead>
									<tr>
										<th><spring:message code="store.master.srno" text="Sr.No." /></th>
										<th><spring:message code="material.management.grnNo" text="GRN No." /></th>
										<th><spring:message code="material.management.grnDate" text="GRN Date" /></th>
										<c:if test="${command.saveMode eq 'A'}">
											<th width="10%"><spring:message code="material.management.action" text="Action" /></th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.invoiceEntryDTO.invoiceEntryGRNDTOList}" var="list" varStatus="status">
									<tr class="firstUnitRow">
										<td align="center" width="10%"><input type="text" class="form-control text-center" disabled="true"
												id="SrNo${d}" value="${d+1}" /></td>
										<td><form:hidden path="invoiceEntryDTO.invoiceEntryGRNDTOList[${d}].grnId" id="grnId${d}" />
											<form:input path="invoiceEntryDTO.invoiceEntryGRNDTOList[${d}].grnNumber" id="grnNumber${d}" type="text"
												class="form-control" readonly="true" /></td>
										<td><form:input path="invoiceEntryDTO.invoiceEntryGRNDTOList[${d}].grnDate" id="grnDate${d}" type="text"
												class="form-control" readonly="true" /></td>
										
										<c:if test="${command.saveMode eq 'A'}">
											<td class="text-center" ><a href="javascript:void(0);" id="removeGrn"
												title="<spring:message code="material.management.delete" text="Delete" />"
												class="removeGrn btn btn-danger btn-sm delete"><i class="fa fa-trash-o"></i></a></td>
										</c:if>		
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>


		<!------------------------------------ Item Details ------------------------------------------------>
				<h4>
					<spring:message code="material.item.master.itemDetails" text="Item Details" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="count" value="0" scope="page"></c:set>
							<table id="invoiceItemDetailsTableID"
								class="table table-striped table-bordered appendableClass unitDetail">
								<thead>
									<tr>
										<th align="center" width="10%"><spring:message code="store.master.srno" text="Sr.No." /></th>
										<th><spring:message code="material.item.master.name" text="Item Name" /><i class="text-red-1">*</i></th>
										<th><spring:message code="U.o.M" text="UoM" /><i class="text-red-1">*</i></th>
										<th><spring:message code="material.management.acceptedQuantity" text="Accepted Quantity" />
												<i class="text-red-1">*</i></th>
										<th><spring:message code="purchase.requisition.unitPrice" text="Unit Price" />
												<i class="text-red-1">*</i></th>
										<th><spring:message code="material.management.taxable.amount" text="Taxable Amount" />
												<i class="text-red-1">*</i></th>
										<th><spring:message code="purchase.requisition.txAmount" text="Tax Amount" />
												<i class="text-red-1">*</i></th>
										<th><spring:message code="purchase.requisition.totValue" text="Total Value" />
												<i class="text-red-1">*</i></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.invoiceEntryDTO.invoiceEntryDetailDTOList}" var="data" varStatus="index">
										<tr class="invoiceItemDetailsRow">
											<td class="text-center"><input type="text" class="form-control text-center" disabled="true"
												id="SrNo${count}" value="${count+1}" /></td>
												
											<td><form:hidden path="invoiceEntryDTO.invoiceEntryDetailDTOList[${count}].grnId" id="grnId${count}" />
												<form:hidden path="invoiceEntryDTO.invoiceEntryDetailDTOList[${count}].grnItemEntryId" 
													id="grnItemEntryId${count}" />
												<form:input path="invoiceEntryDTO.invoiceEntryDetailDTOList[${count}].itemId" id="itemId${count}"
													class="form-control mandColorClass" value="${data.itemName}" data-rule-required="true"
													disabled="true" /></td>
											
											<td><form:input path="invoiceEntryDTO.invoiceEntryDetailDTOList[${count}].uomName"
													id="uomName${count}" type="text" class="form-control mandColorClass" readonly="true" /></td>
											
											<td><form:input path="invoiceEntryDTO.invoiceEntryDetailDTOList[${count}].quantity" 
													id="quantity${count}" class="form-control mandColorClass text-right" data-rule-required="true"
													disabled="true" /></td>
													
											<td><form:input path="invoiceEntryDTO.invoiceEntryDetailDTOList[${count}].unitPrice" 
													id="unitPrice${count}" class="form-control mandColorClass text-right" 
													data-rule-required="true" disabled="true" /></td>
													
											<td><form:input path="invoiceEntryDTO.invoiceEntryDetailDTOList[${count}].taxableValue"
												id="taxableValue${count}" class="form-control mandColorClass text-right" 
												data-rule-required="true" disabled="true" /></td>
													
											<td><form:input path="invoiceEntryDTO.invoiceEntryDetailDTOList[${count}].tax"
												id="tax${count}" class="form-control mandColorClass text-right" data-rule-required="true" 
												disabled="true" /></td>
											
											<td><form:input path="invoiceEntryDTO.invoiceEntryDetailDTOList[${count}].totalAmt" 
												id="totalAmt${count}" class="form-control mandColorClass text-right" data-rule-required="true"
												disabled="true" /></td>		
										</tr>
										<c:set var="count" value="${count + 1}" scope="page" />
									</c:forEach>
								</tbody>
							</table>

							<div class="form-group">
								<div class="col-sm-6"></div>
								<apptags:input labelCode="material.management.total.item.amount" isMandatory="true" 
										path="invoiceEntryDTO.itemAmt" isReadonly="true" cssClass="text-right"/>
							</div>
						</div>
					</div>
				</div>


		<!--------------------------------- Overheads --------------------------------------------------->
				<h4>
					<spring:message code="material.management.deductions" text="Deductions" />
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
										<th><spring:message  code="material.management.deductions" text="Deductions" />
											<span class="mand showMand">*</span></th>
										<th width="20%"><spring:message code="material.management.decision"
											text="Decision" /><span class="mand showMand">*</span></th>
										<th width="20%"><spring:message code="purchase.requisition.Amt" 
											text="Amount" /><span class="mand showMand">*</span></th>
										<c:if test="${command.saveMode eq 'A' || command.saveMode eq 'E'}">
											<th width="10%"><spring:message code="material.management.action" text="Action" /></th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(command.invoiceEntryDTO.invoiceOverheadsDTOList)>0 }">
											<c:forEach items="${command.invoiceEntryDTO.invoiceOverheadsDTOList}" var="overhd" > 
												<tr class="overheadsTableRow">
													<td><input type="text" class="form-control text-center" disabled="true" 
																id="SrNo${h}" value="${h+1}" />
													<td ><form:hidden path="invoiceEntryDTO.invoiceOverheadsDTOList[${h}].invOverheadId"
															id="overHeadId${h}" />
														 <form:select path="invoiceEntryDTO.invoiceOverheadsDTOList[${h}].description"
														cssClass="form-control chosen-select-no-results" id="description${h}" 
														disabled="${1 eq command.levelCheck || command.saveMode eq 'V'}" >
														<form:option value="">
															<spring:message code="material.management.select" text="Select" />
														</form:option>
														<c:forEach items="${command.listTaxDefinationDto}" var="listTaxDesc">
															<form:option value="${listTaxDesc.taxId}">${listTaxDesc.taxDesc}</form:option>
														</c:forEach>
													</form:select></td>
													<td><form:select path="invoiceEntryDTO.invoiceOverheadsDTOList[${h}].overheadType" 
															disabled="${1 eq command.levelCheck || command.saveMode eq 'V'}" id="overHeadType${h}"  
															onchange="totalOverheadCount();" cssClass="form-control" data-rule-required="true">
																<form:option selected="selected" value="D">
																	<spring:message code="" text="Deduction" />
																</form:option>
															</form:select></td>
													<td ><form:input disabled="${1 eq command.levelCheck || command.saveMode eq 'V'}"
															path="invoiceEntryDTO.invoiceOverheadsDTOList[${h}].amount" id="amount${h}" 
															type="text" class="form-control amountOverhead" onchange="totalOverheadCount();"
															onkeypress="return hasAmount(event, this, 10, 2)" /></td>
			
													<c:if test="${command.saveMode eq 'A' || command.saveMode eq 'E'}">
														<td class="text-center" >
															<a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" 
																class="addOF btn btn-success btn-sm" id="addUnitRow"><i class="fa fa-plus-circle"></i></a>
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
												<td><input type="text" class="form-control text-center" disabled="true" 
															id="SrNo${h}" value="${h+1}" />
												<td ><form:hidden path="invoiceEntryDTO.invoiceOverheadsDTOList[${h}].invOverheadId"
														id="overHeadId${h}" />
													<form:select path="invoiceEntryDTO.invoiceOverheadsDTOList[${h}].description"
														cssClass="form-control chosen-select-no-results" id="description${h}" 
														disabled="${1 eq command.levelCheck || command.saveMode eq 'V'}" >
														<form:option value="" selected="selected">
															<spring:message code="material.management.select" text="Select" />
														</form:option>
														<c:forEach items="${command.listTaxDefinationDto}" var="listTaxDesc">
															<form:option value="${listTaxDesc.taxId}">${listTaxDesc.taxDesc}</form:option>
														</c:forEach>
													</form:select>
													<form:input type="hidden" path="" /></td>
												<td><form:select path="invoiceEntryDTO.invoiceOverheadsDTOList[${h}].overheadType" 
														disabled="${1 eq command.levelCheck || command.saveMode eq 'V'}" id="overHeadType${h}"  
														onchange="totalOverheadCount();" cssClass="form-control" data-rule-required="true">
														<form:option selected="selected" value="D">
															<spring:message code="" text="Deduction" />
														</form:option>
													</form:select></td>
												<td ><form:input disabled="${1 eq command.levelCheck || command.saveMode eq 'V'}"
														path="invoiceEntryDTO.invoiceOverheadsDTOList[${h}].amount" id="amount${h}" 
														type="text" class="form-control amountOverhead" onchange="totalOverheadCount();"
														onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																<c:if test="${command.saveMode eq 'A' || command.saveMode eq 'E'}">
													<td class="text-center" >
														<a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" 
															class="addOF btn btn-success btn-sm" id="addUnitRow"><i class="fa fa-plus-circle"></i></a>
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
								<apptags:input labelCode="material.management.deduction.total.amount" isMandatory="true" 
										path="invoiceEntryDTO.overheadAmt" isReadonly="true" cssClass="text-right" />	
								
								<apptags:input labelCode="material.management.total.invoice.amount" isMandatory="true" 
										path="invoiceEntryDTO.invoiceAmt" isReadonly="true" cssClass="text-right" />	
							</div>
							
						</div>
					</div>
				</div>

		<!---------------------- File Upload ----------------------------->
				<c:if test="${command.saveMode eq 'A' || command.saveMode eq 'E'}">
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message code="swm.fileupload" /></label>
						<div class="col-sm-4">
							<small class="text-blue-2"> <spring:message code="fuelling.advice.file.upload"
									text="(Upload File upto 5MB and only pdf,doc,xls,xlsx)" />
							</small>
							<apptags:formField fieldType="7" labelCode="" hasId="true" fieldPath="" isMandatory="false"
								showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
								validnFunction="CHECK_LIST_VALIDATION_EXTENSION" currentCount="0" />
						</div>
					</div>
				</c:if>				
				
				<c:if test="${!command.attachDocsList.isEmpty()}">
					<div class="table-responsive">
						<table class="table table-bordered table-striped" id="deleteDoc">
							<tr>
								<th width="8%"><spring:message code="population.master.srno" text="Sr. No." /></th>
								<th><spring:message code="scheme.view.document" text="View Document" /></th>
								<c:if test="${command.saveMode ne 'V' }">
									<th width="8%"><spring:message code="FireCallRegisterDTO.form.action" text="Action" /></th>
								</c:if>
							</tr>
							<c:set var="e" value="0" scope="page" />
							<c:forEach items="${command.attachDocsList}" var="lookUp">
								<tr>
									<td>${e+1}</td>
									<td><apptags:filedownload filename="${lookUp.attFname}"
											filePath="${lookUp.attPath}" actionUrl="vehicleFuel.html?Download" /></td>
									<c:if test="${command.saveMode ne 'V' }">
										<td class="text-center"><a href='#' id="deleteFile" onclick="return false;" 
											class="btn btn-danger btn-sm"><i class="fa fa-trash"></i></a> 
											<form:hidden path="" value="${lookUp.attId}" /></td>
									</c:if>											
								</tr>
								<c:set var="e" value="${e + 1}" scope="page" />
							</c:forEach>
						</table>
					</div>
				</c:if>


		<!---------------------- USer Action ----------------------------->
				<c:if test="${command.levelCheck eq 1 }">
					<apptags:CheckerAction hideForward="true" hideSendback="true" hideUpload="true"></apptags:CheckerAction>
				</c:if>
	

				<div class="text-center padding-bottom-10">
					<c:if test="${command.saveMode ne 'V' }">
						<button type="button" class="btn btn-success" onclick="submitInvoiceEntryForm(this)">
								<spring:message code="material.management.submit" text="Submit"></spring:message>
						</button>						
						<c:if test="${0 eq command.levelCheck && command.saveMode eq 'A'}">
							<button type="button" class="btn btn-warning"
								onclick="addInvoiceEntryForm('StoreInvoiceEntry.html','addInvoiceEntryForm');">
								<spring:message code="material.management.reset" text="Reset"></spring:message>
							</button>
						</c:if>
					</c:if>
										
					<c:choose>
						<c:when test="${0 ne command.levelCheck}">
							<apptags:backButton url="AdminHome.html"></apptags:backButton>										
						</c:when>
						<c:otherwise>
							<apptags:backButton url="StoreInvoiceEntry.html"></apptags:backButton>				
						</c:otherwise>
					</c:choose>
				</div>

			</form:form>
		</div>
	</div>
</div>
