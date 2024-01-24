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
<script type="text/javascript" src="js/material_mgmt/service/materialDispatchNote.js"></script>
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
				<spring:message code="material.management.mdn.issue.items" text="Issue Items" />
			</h2>
			<apptags:helpDoc url="MaterialDispatchNote.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>

			<form:form action="MaterialDispatchNote.html" name="MaterialDispatchNoteIssueDetails" 
					id="MaterialDispatchNoteIssueDetailsId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
					<span aria-hidden="true">&times;</span></button><span id="errorId"></span>
				</div>
				
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="levelCheck" id="levelCheck" />				
				<form:hidden path="indexCount" id="indexCount" />
				<form:hidden path="management" id="management" />
				
				
				<div class="form-group">
					<form:hidden path="dispatchNoteDTO.matDispatchItemList[${command.indexCount}].itemId" id="itemId" />
					<apptags:input labelCode="material.management.itemName" isMandatory="true" isReadonly="true" 
							path="dispatchNoteDTO.matDispatchItemList[${command.indexCount}].itemName" />
							
					<apptags:input labelCode="material.management.mdn.remaining.tobe.issued" isMandatory="true" isReadonly="true" 
							path="remainQtyToBeIssued" />
				</div>

				<c:choose>
					<c:when test="${command.management eq 'B'}">
						<div class="widget-header">
							<h4>
								<spring:message code="material.management.mdn.management.in.batch"
									text="Item Management Method - In Batch" />
							</h4>
						</div>
						<c:set var="e" value="0" scope="page" />
						<table
							class="table table-striped table-bordered appendableClass unitDetail"
							id="inBatchTableId">
							<thead>
								<tr>
									<th width="10%" class="text-center"><spring:message
											code="material.management.SrNo" text="Sr No" /></th>
									<th width="20%" class="text-center"><spring:message code="binLocMasDto.binLocation" 
											text="Bin Location" /><i class="text-red-1">*</i></th>
									<th width="20%" class="text-center"><spring:message
											code="material.management.batchNo" text="Batch No." /><i
											class="text-red-1">*</i></th>
									<c:if test="${command.saveMode ne 'V' }">
										<th width="20%" class="text-center"><spring:message code="departemnt.indent.available.quantity"
											text="Available Quantity" /><i class="text-red-1">*</i></th>
									</c:if>
									<th width="20%" class="text-center"><spring:message code="department.indent.message.issued.quantity" 
											text="Issued Quantity" /><i class="text-red-1">*</i></th>
									<c:if test="${command.saveMode ne 'V' }">
										<th width="10%" class="text-center" width="10%"><spring:message code="material.management.action" 
											text="Action"></spring:message></th>
									</c:if>								
								</tr>
							</thead>
							<tbody>						
								<c:choose>
									<c:when test="${empty command.noteItemsEntryDTOList}">
										<tr class="inBatchTableRow">
											<td><input type="text" class="form-control text-center" disabled="true"
												id="inBatchSrNo${e}" value="${e+1}" /></td>
		
											<td ><form:select path="noteItemsEntryDTOList[${e}].issueBinLocation" id="inBatchBinLocId${e}" 
													cssClass="chosen-select-no-results form-control required-control" data-rule-required="false" 
													onchange="getItemNumberListByBinLocation('${e}')" >
													<form:option value="0">
														<spring:message code="material.management.select" text="Select" />
													</form:option>
													<c:forEach items="${command.binLocList}" var="binLoc">
														<form:option value="${binLoc.binLocId}" >${binLoc.binLocation}</form:option>
													</c:forEach>
												</form:select></td>
												
											<td><form:select path="noteItemsEntryDTOList[${e}].itemNo" id="batchNo${e}" 
													cssClass="form-control chosen-select-no-results" data-rule-required="false" 
													onchange="getInBatchAvailbleQtyByBin('${e}')" >
													<form:option value="0">
														<spring:message code="material.management.select" text="Select" />
													</form:option>
													<c:forEach items="${command.itemNumberList}" var="batchNo">
														<form:option value="${batchNo}" >${batchNo}</form:option>
													</c:forEach>
												</form:select></td>
											
											<c:if test="${command.saveMode ne 'V' }">	
												<td><form:input type="text" id="availableQtyInBatch${e}" class="form-control" 
													path="noteItemsEntryDTOList[${e}].availableQuantity" readonly="true" /></td>
											</c:if>
													
											<td><form:input type="text" id="issuedQtyInBatch${e}" class="form-control text-right" 
													onchange="calculateInBatchIssuedQty()" path="noteItemsEntryDTOList[${e}].quantity"
													onkeypress="return hasAmount(event, this, 10, 2)"  /></td>
											<c:if test="${command.saveMode ne 'V' }">
												<td  class="text-center"><a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" onclick="addUnitRowInBatch(this);"
														class="btn btn-success btn-sm " id="addUnitRowInBatch${e}"><i class="fa fa-plus-circle"></i></a>  
													<a href="javascript:void(0);" title="<spring:message code="material.management.delete" text="Delete" />" class="btn btn-danger btn-sm delete deleteInBatchItemRow" 
														id="deleteInBatchItemRow${e}"><i class="fa fa-trash-o"></i></a></td>
											</c:if>
										</tr>
										<c:set var="e" value="${e+1}" scope="page" />
									</c:when>
									
									<c:otherwise>
										<c:forEach items="${command.noteItemsEntryDTOList}" var="details"
											varStatus="status">
											<tr class="inBatchTableRow">
												<td><input type="text" class="form-control text-center" disabled="true"
													id="inBatchSrNo${e}" value="${e+1}" /></td>
			
												<td><form:select path="noteItemsEntryDTOList[${e}].issueBinLocation" id="inBatchBinLocId${e}" 
														cssClass="form-control chosen-select-no-results" data-rule-required="false" 
														onchange="getItemNumberListByBinLocation('${e}')"  >
														<form:option value="0">
															<spring:message code="material.management.select" text="Select" />
														</form:option>
														<c:forEach items="${command.binLocList}" var="binLoc">
															<form:option value="${binLoc.binLocId}" >${binLoc.binLocation}</form:option>
														</c:forEach>
													</form:select></td>
	
												<td>
													<c:choose>
														<c:when test="${command.saveMode eq 'V'}">
															<form:input type="text" id="batchNo${e}" class="form-control" 
																path="noteItemsEntryDTOList[${e}].itemNo" readonly="true" />
														</c:when>
														<c:otherwise>
															<form:select path="noteItemsEntryDTOList[${e}].itemNo" id="batchNo${e}" 
																cssClass="form-control chosen-select-no-results" data-rule-required="false" 
																onchange="getInBatchAvailbleQtyByBin('${e}')" >
																<form:option value="0">
																	<spring:message code="material.management.select" text="Select" />
																</form:option>
																<c:forEach items="${command.noteItemsEntryDTOList[e].itemNumberList}" var="batchNo">
																	<form:option value="${batchNo}" >${batchNo}</form:option>
																</c:forEach>
															</form:select>															
														</c:otherwise>
													</c:choose>
												</td>
			
												<c:if test="${command.saveMode ne 'V' }">
													<td><form:input type="text" id="availableQtyInBatch${e}" class="form-control" 
														path="noteItemsEntryDTOList[${e}].availableQuantity" readonly="true" /></td>
												</c:if>
														
												<td><form:input type="text" id="issuedQtyInBatch${e}" class="form-control text-right" 
														onchange="calculateInBatchIssuedQty()" path="noteItemsEntryDTOList[${e}].quantity"
														onkeypress="return hasAmount(event, this, 10, 2)"  /></td>
												<c:if test="${command.saveMode ne 'V' }">
													<td  class="text-center"><a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" onclick="addUnitRowInBatch(this);"
															class="btn btn-success btn-sm " id="addUnitRowInBatch${e}"><i class="fa fa-plus-circle"></i></a>  
														<a href="javascript:void(0);" title="<spring:message code="material.management.delete" text="Delete" />"  class="btn btn-danger btn-sm delete deleteInBatchItemRow" 
															id="deleteInBatchItemRow${e}"><i class="fa fa-trash-o"></i></a></td>
												</c:if>	
												
											</tr>
											<c:set var="e" value="${e+1}" scope="page" />
										</c:forEach>								
									</c:otherwise>							
								</c:choose>
							</tbody>
						</table>
					</c:when>
	
	
					<c:when test="${command.management eq 'S'}">
						<div class="widget-header">
							<h4>
								<spring:message code="material.management.mdn.management.serial"
									text="Item Management Method - Serial" />
							</h4>
						</div>
						<c:set var="f" value="0" scope="page" />
						<table
							class="table table-striped table-bordered appendableClass unitDetail"
							id="serialTableId">
							<thead>
								<tr>
									<th width="10%" class="text-center"><spring:message
											code="material.management.SrNo" text="Sr No" /></th>
									<th width="20%" class="text-center"><spring:message code="binLocMasDto.binLocation" text="Bin Location" />
										<i class="text-red-1">*</i></th>
									<th width="20%" class="text-center"><spring:message code="material.item.serialNumber"
											text="Serial Number" /><i class="text-red-1">*</i></th>
									<c:if test="${command.saveMode ne 'V'}">
										<th width="20%" class="text-center"><spring:message code="departemnt.indent.available.quantity"
											text="Available Quantity" /><i class="text-red-1">*</i></th>
									</c:if>
									<th width="20%" class="text-center"><spring:message code="department.indent.message.issued.quantity" text="Issued Quantity" /><i
										class="text-red-1">*</i></th>
									<c:if test="${command.saveMode ne 'V'}">
										<th width="10%" class="text-center"><spring:message code="material.management.action" 
											text="Action"></spring:message></th>
									</c:if>							
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${empty command.noteItemsEntryDTOList}">
											<tr class="serialTableRow">
												<td><input type="text" class="form-control text-center" disabled="true"
													id="serSrNo${f}" value="${f+1}" /></td>
													
												<td><form:select path="noteItemsEntryDTOList[${f}].issueBinLocation" id="serialBinLocId${f}" 
														cssClass="form-control chosen-select-no-results" data-rule-required="false" 
														onchange="getSerialListByBinLocation('${f}')" >
														<form:option value="0">
															<spring:message code="material.management.select" text="Select" />
														</form:option>
														<c:forEach items="${command.binLocList}" var="binLoc">
															<form:option value="${binLoc.binLocId}" >${binLoc.binLocation}</form:option>
														</c:forEach>
													</form:select></td> 
			
												<td><form:select path="noteItemsEntryDTOList[${f}].itemNo" id="serialNumber${f}" 
														cssClass="form-control chosen-select-no-results" data-rule-required="false" 
														onchange="getSerialAvailbleQty('${f}')" >
														<form:option value="0">
															<spring:message code="material.management.select" text="Select" />
														</form:option>
														<c:forEach items="${command.itemNumberList}" var="serialNo">
															<form:option value="${serialNo}" >${serialNo}</form:option>
														</c:forEach>
													</form:select></td>
																										
												<td><form:input type="text" id="availableQtySerial${f}" class="form-control" 
														path="noteItemsEntryDTOList[${f}].availableQuantity" readonly="true" /></td>
														
												<td><form:input type="text" id="issuedQtySerial${f}" class="form-control text-right" 
														onchange="calculateSerialIssuedQty()" path="noteItemsEntryDTOList[${f}].quantity"
														onkeypress="return hasAmount(event, this, 10, 2)"  /></td>
			
												<td  class="text-center"><a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" onclick="addSerialUnitRow(this);"
															class="btn btn-success btn-sm " id="addSerialUnitRow${f}"><i class="fa fa-plus-circle"></i></a>  
														<a href="javascript:void(0);" title="<spring:message code="material.management.delete" text="Delete" />" class="btn btn-danger btn-sm delete deleteSerialUnitRow" 
															id="deleteSerialUnitRow${f}"><i class="fa fa-trash-o"></i></a></td>		
			
											</tr>
											<c:set var="f" value="${f+1}" scope="page" />
									</c:when>
									
									<c:otherwise>
										<c:forEach items="${command.noteItemsEntryDTOList}" var="details"
											varStatus="status">
											<tr class="serialTableRow">
												<td><input type="text" class="form-control text-center" disabled="true"
													id="serSrNo${f}" value="${f+1}" /></td>
		
												<td><form:select path="noteItemsEntryDTOList[${f}].issueBinLocation" id="serialBinLocId${f}" 
														cssClass="form-control chosen-select-no-results" data-rule-required="false" 
														onchange="getSerialListByBinLocation('${f}')" >
														<form:option value="0">
															<spring:message code="material.management.select" text="Select" />
														</form:option>
														<c:forEach items="${command.binLocList}" var="binLoc">
															<form:option value="${binLoc.binLocId}" >${binLoc.binLocation}</form:option>
														</c:forEach>
													</form:select></td> 
															
												<td>
													<c:choose>
														<c:when test="${command.saveMode eq 'V'}">
															<form:input type="text" id="serialNumber${f}" class="form-control" 
																path="noteItemsEntryDTOList[${f}].itemNo" readonly="true" />
														</c:when>
														<c:otherwise>
															<form:select path="noteItemsEntryDTOList[${f}].itemNo" id="serialNumber${f}" 
																cssClass="form-control chosen-select-no-results" data-rule-required="false" 
																onchange="getSerialAvailbleQty('${f}')" >
																<form:option value="0">
																	<spring:message code="material.management.select" text="Select" />
																</form:option>
																<c:forEach items="${command.noteItemsEntryDTOList[f].itemNumberList}" var="serialNo">
																	<form:option value="${serialNo}" >${serialNo}</form:option>
																</c:forEach>
															</form:select>															
														</c:otherwise>
													</c:choose>
												</td>
														
												<c:if test="${command.saveMode ne 'V'}">
													<td><form:input type="text" id="availableQtySerial${f}" class="form-control" 
														path="noteItemsEntryDTOList[${f}].availableQuantity" readonly="true" /></td>
												</c:if>
															
												<td><form:input type="text" id="issuedQtySerial${f}" class="form-control text-right" 
														onchange="calculateSerialIssuedQty()" path="noteItemsEntryDTOList[${f}].quantity"
														onkeypress="return hasAmount(event, this, 10, 2)" /></td>
														
												<c:if test="${command.saveMode ne 'V'}">													
													<td  class="text-center"><a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" onclick="addSerialUnitRow(this);"
														class="btn btn-success btn-sm " id="addSerialUnitRow${f}"><i class="fa fa-plus-circle"></i></a>  
													<a href="javascript:void(0);" title="<spring:message code="material.management.delete" text="Delete" />" class="btn btn-danger btn-sm delete deleteSerialUnitRow" 
															id="deleteSerialUnitRow${f}"><i class="fa fa-trash-o"></i></a></td>	
												</c:if>	
		
											</tr>
											<c:set var="f" value="${f+1}" scope="page" />	
										</c:forEach>		
									</c:otherwise>							
								</c:choose>
							</tbody>
						</table>
					</c:when>
	
	
					<c:when test="${command.management eq 'N'}">
						<div class="widget-header">
							<h4>
								<spring:message code="department.indent.item.management.method.not.in.batch"
									text="Item Management Method - Not In Batch" />
							</h4>
						</div>
						<c:set var="d" value="0" scope="page" />
						<table id="notInBatchTableID"
							class="table table-striped table-bordered appendableClass unitDetail">
							<thead>
								<tr>
									<th width="10%" class="text-center"><spring:message
											code="material.management.SrNo" text="Sr No" /></th>
									<th class="text-center"><spring:message code="binLocMasDto.binLocation" text="Bin Location" />
										<i class="text-red-1">*</i></th>
									<c:if test="${command.saveMode ne 'V' }">
										<th width="25%" class="text-center"><spring:message code="departemnt.indent.available.quantity"
											text="Available Quantity" /><i class="text-red-1">*</i></th>
									</c:if>
									<th width="25%" class="text-center"><spring:message code="department.indent.message.issued.quantity"
											text="Issued Quantity" /><i class="text-red-1">*</i></th>
									<c:if test="${command.saveMode ne 'V' }">
										<th width="10%" class="text-center" width="10%"><spring:message code="material.management.action" 
											text="Action"></spring:message></th>
									</c:if>								
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${empty command.noteItemsEntryDTOList}">
										<tr class="notInBatchTableRow">
											<td><input class="form-control text-center"
												disabled="true" id="notInBatchSequence${d}" value="${d+1}" /></td>
												
											<td><form:select path="noteItemsEntryDTOList[${d}].issueBinLocation" id="notInBatchBinLocId${d}" 
													cssClass="form-control chosen-select-no-results" hasId="true" data-rule-required="false" 
													onchange="getNotInBatchAvailbleQtyByBin('${d}')" >
													<form:option value="0">
														<spring:message code="material.management.select" text="Select" />
													</form:option>
													<c:forEach items="${command.binLocList}" var="binLoc">
														<form:option value="${binLoc.binLocId}" >${binLoc.binLocation}</form:option>
													</c:forEach>
												</form:select></td>
		
											<td><form:input type="text" id="availableQtyNotInBatch${d}" class="form-control" 
													path="noteItemsEntryDTOList[${d}].availableQuantity" readonly="true" /></td>
													
											<td><form:input type="text" id="issuedQtyNotInBatch${d}" class="form-control text-right" 
													onchange="calculateNotInBatchIssuedQty()" path="noteItemsEntryDTOList[${d}].quantity"
													onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																				
											<td  class="text-center" width="10%"><a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" onclick="addNewNotInBatchItemRow(this);"
														class="btn btn-success btn-sm " id="addNewNotInBatchItemRow${d}"><i class="fa fa-plus-circle"></i></a>  
													<a href="javascript:void(0);" title="<spring:message code="material.management.delete" text="Delete" />" onclick=""
														class="btn btn-danger btn-sm delete deleteNotInBatchUnitRow" id="deleteNotInBatchUnitRow${d}"><i class="fa fa-trash-o"></i></a></td>		
										</tr>
										<c:set var="d" value="${d+1}" scope="page" />
									</c:when>
									
									<c:otherwise>
										<c:forEach items="${command.noteItemsEntryDTOList}">
											<tr class="notInBatchTableRow">
												<td><input class="form-control text-center"
													disabled="true" id="notInBatchSequence${d}" value="${d+1}" /></td>
													
												<td><form:select path="noteItemsEntryDTOList[${d}].issueBinLocation" id="notInBatchBinLocId${d}" 
														cssClass="form-control chosen-select-no-results" hasId="true" data-rule-required="false" 
														onchange="getNotInBatchAvailbleQtyByBin('${d}')" >
														<form:option value="0">
															<spring:message code="material.management.select" text="Select" />
														</form:option>
														<c:forEach items="${command.binLocList}" var="binLoc">
															<form:option value="${binLoc.binLocId}" >${binLoc.binLocation}</form:option>
														</c:forEach>
													</form:select></td>												
													
												<c:if test="${command.saveMode ne 'V' }">
													<td><form:input type="text" id="availableQtyNotInBatch${d}" class="form-control" 
														path="noteItemsEntryDTOList[${d}].availableQuantity" readonly="true" /></td>
												</c:if>
														
												<td><form:input type="text" id="issuedQtyNotInBatch${d}" class="form-control text-right" 
														onchange="calculateNotInBatchIssuedQty()" path="noteItemsEntryDTOList[${d}].quantity"
														onkeypress="return hasAmount(event, this, 10, 2)"  /></td>
																					
												<c:if test="${command.saveMode ne 'V' }">
													<td class="text-center" width="10%"><a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" 
															onclick="addNewNotInBatchItemRow(this);" class="btn btn-success btn-sm " 
															id="addNewNotInBatchItemRow${d}"><i class="fa fa-plus-circle"></i></a>  
														<a href="javascript:void(0);" title="<spring:message code="material.management.delete" text="Delete" />" onclick=""
															class="btn btn-danger btn-sm delete deleteNotInBatchUnitRow" 
															id="deleteNotInBatchUnitRow${d}"><i class="fa fa-trash-o"></i></a></td>
												</c:if>
			
											</tr>
											<c:set var="d" value="${d+1}" scope="page" />
										</c:forEach>								
									</c:otherwise>							
								</c:choose>
							</tbody>
						</table>
					</c:when>
	
				</c:choose>
	
	
				<div class="form-group">
					<apptags:textArea labelCode="remark.goods" isMandatory="true"
							path="dispatchNoteDTO.matDispatchItemList[${command.indexCount}].inspectorRemarks" />

					<apptags:input labelCode="department.indent.total.issued.quantity"
							isMandatory="true"  isReadonly="true"
							path="dispatchNoteDTO.matDispatchItemList[${command.indexCount}].issuedQty" />
				</div>
				
				<!-------------------------------------- Buttons --------------------------------------------->
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2" onclick="saveMDNItemEntryDetails(this)">
						<spring:message code="master.save.bin" text="Save" />
					</button>
					<button type="button" class="btn btn-danger" onclick="backToMDNForm()">
						<spring:message code="material.management.back" text="Back" />
					</button>
				</div>
								
			</form:form>
		</div>
	</div>
</div>
