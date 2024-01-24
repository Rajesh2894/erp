<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css">
<script src="js/material_mgmt/service/IndentIssue.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	if('V' == $('#saveMode').val()){
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr("disabled", true);
		$('select').prop('disabled', true).trigger("chosen:updated");
	}	
});
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="department.indent.indent.issue.details" text="Indent Issue Details" />
		</h2>
		<apptags:helpDoc url="IndentProcessApproval.html" />
	</div>
	<div class="widget-content padding">

		<div class="mand-label clearfix">
			<span><spring:message code="water.fieldwith" /><i class="text-red-1">*</i> <spring:message code="water.ismandtry" /></span>
		</div>

		<form:form class="form-horizontal" commandName="command" action="IndentProcessApproval.html" 
				method="POST" name="" id="">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />

			<div class="warning-div error-div alert alert-danger alert-dismissible"
				id="errorDiv" style="display: none;"></div>

			<div class="form-group">
				<form:hidden path="indentProcessDTO.item[${command.indexCount}].itemid" id="itemid" />
				<apptags:input labelCode="material.management.itemName" isMandatory="treu" isReadonly="true" 
						path="indentProcessDTO.item[${command.indexCount}].itemName" />
				
				<label class="col-sm-2 control-label" for="receivedQuantity"><spring:message
						code="department.indent.requested.quantity" text="Requested Quantity" /></label>
				<div class="col-sm-4">
					<form:input type="text" class="form-control text-right"
						maxlength="200" path="indentProcessDTO.item[${command.indexCount}].quantity"
						readonly="true" id="receivedQuantity" data-rule-required="true"></form:input>
				</div>
			</div>
			
			<form:hidden path="levelcheck" id="levelcheck"  />
			<form:hidden path="saveMode" id="saveMode" />
			<form:hidden path="lastChecker" id="lastChecker" />
			<form:hidden path="indexCount" id="indexCount" />
			<form:hidden path="managementCode" id="managementCode" />			
			<form:hidden path="indentProcessItemDTO.indentid" id="indentid" />
			<form:hidden path="indentProcessItemDTO.inditemid" id="inditemid" />

			<c:choose>
				<c:when test="${command.managementCode eq 'B'}">
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
								<th width="20%" class="text-center"><spring:message code="department.indent.message.issued.quantity" text="Issued Quantity" /><i
									class="text-red-1">*</i></th>
								<c:if test="${command.saveMode ne 'V' }">
									<th width="10%" class="text-center" width="10%"><spring:message code="material.management.action" 
										text="Action"></spring:message></th>
								</c:if>								
							</tr>
						</thead>
						<tbody>						
							<c:choose>
								<c:when test="${empty command.listIndentIssueDtos}">
									<tr class="inBatchTableRow">
										<td><input type="text" class="form-control text-center" disabled="true"
											id="inBatchSrNo${e}" value="${e+1}" /></td>
	
										<td ><form:select path="listIndentIssueDtos[${e}].binlocation" id="inBatchBinLocId${e}" 
												cssClass="form-control chosen-select-no-results" data-rule-required="false" 
												onchange="getItemNumberListByBinLocation('${e}')" >
												<form:option value="0">
													<spring:message code="material.management.select" text="Select" />
												</form:option>
												<c:forEach items="${command.binLocList}" var="binLoc">
													<form:option value="${binLoc.binLocId}" >${binLoc.binLocation}</form:option>
											</c:forEach>
											</form:select></td>
											
										<td><form:select path="listIndentIssueDtos[${e}].itemno" id="batchNo${e}" 
												cssClass="form-control chosen-select-no-results" data-rule-required="false" 
												onchange="getInBatchAvailbleQtyByBin('${e}')" >
												<form:option value="0">
													<spring:message code="material.management.select" text="Select" />
												</form:option>
												<c:forEach items="${command.itemNumberList}" var="batchNo">
													<form:option value="${batchNo}" >${batchNo}</form:option>
												</c:forEach>
											</form:select></td>
												
										<td><form:input type="text" id="availableQtyInBatch${e}" class="form-control" 
												path="listIndentIssueDtos[${e}].availableQty" readonly="true" /></td>
												
										<td><form:input type="text" id="issuedQtyInBatch${e}" class="form-control text-right" 
												onchange="calculateInBatchIssuedQty()" path="listIndentIssueDtos[${e}].issueqty"
												onkeypress="return hasAmount(event, this, 10, 2)"  /></td>
										
										<td  class="text-center"><a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" onclick="addUnitRowInBatch(this);"
													class="btn btn-success btn-sm " id="addUnitRowInBatch${e}"><i class="fa fa-plus-circle"></i></a>  
												<a href="javascript:void(0);" title="<spring:message code="material.management.delete" text="Delete" />" class="btn btn-danger btn-sm delete deleteInBatchItemRow" 
													id="deleteInBatchItemRow${e}"><i class="fa fa-trash-o"></i></a></td>			
									</tr>
									<c:set var="e" value="${e+1}" scope="page" />
								</c:when>
								
								<c:otherwise>
									<c:forEach items="${command.listIndentIssueDtos}" var="details"
										varStatus="status">
										<tr class="inBatchTableRow">
											<td><input type="text" class="form-control text-center" disabled="true"
												id="inBatchSrNo${e}" value="${e+1}" /></td>
		
											<td><form:select path="listIndentIssueDtos[${e}].binlocation" id="inBatchBinLocId${e}" 
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
															path="listIndentIssueDtos[${e}].itemno" readonly="true" />
													</c:when>
													<c:otherwise>
														<form:select path="listIndentIssueDtos[${e}].itemno" id="batchNo${e}" 
															cssClass="form-control chosen-select-no-results" data-rule-required="false" 
															onchange="getInBatchAvailbleQtyByBin('${e}')" >
															<form:option value="0">
																<spring:message code="material.management.select" text="Select" />
															</form:option>
															<c:forEach items="${command.listIndentIssueDtos[e].itemNumberList}" var="batchNo">
																<form:option value="${batchNo}" >${batchNo}</form:option>
															</c:forEach>
														</form:select>															
													</c:otherwise>
												</c:choose>
											</td>
		
											<c:if test="${command.saveMode ne 'V' }">
												<td><form:input type="text" id="availableQtyInBatch${e}" class="form-control" 
													path="listIndentIssueDtos[${e}].availableQty" readonly="true" /></td>
											</c:if>
													
											<td><form:input type="text" id="issuedQtyInBatch${e}" class="form-control text-right" 
													onchange="calculateInBatchIssuedQty()" path="listIndentIssueDtos[${e}].issueqty"
													onkeypress="return hasAmount(event, this, 10, 2)"  /></td>
											<c:if test="${command.saveMode ne 'V' }">
												<td  class="text-center"><a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" onclick="addUnitRowInBatch(this);"
														class="btn btn-success btn-sm " id="addUnitRowInBatch${e}"><i class="fa fa-plus-circle"></i></a>  
													<a href="javascript:void(0);" title="<spring:message code="material.management.delete" text="Delete" />" class="btn btn-danger btn-sm delete deleteInBatchItemRow" 
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


				<c:when test="${command.managementCode eq 'S'}">
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
										text="Serial No." /><i class="text-red-1">*</i></th>
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
								<c:when test="${empty command.listIndentIssueDtos}">
										<tr class="serialTableRow">
											<td><input type="text" class="form-control text-center" disabled="true"
												id="serSrNo${f}" value="${f+1}" /></td>
												
											<td><form:select path="listIndentIssueDtos[${f}].binlocation" id="serialBinLocId${f}" 
													cssClass="form-control chosen-select-no-results" data-rule-required="false" 
													onchange="getSerialListByBinLocation('${f}')" >
													<form:option value="0">
														<spring:message code="material.management.select" text="Select" />
													</form:option>
													<c:forEach items="${command.binLocList}" var="binLoc">
														<form:option value="${binLoc.binLocId}" >${binLoc.binLocation}</form:option>
													</c:forEach>
												</form:select></td> 
		
											<td><form:select path="listIndentIssueDtos[${f}].itemno" id="serialNumber${f}" 
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
													path="listIndentIssueDtos[${f}].availableQty" readonly="true" /></td>
													
											<td><form:input type="text" id="issuedQtySerial${f}" class="form-control text-right" 
													onchange="calculateSerialIssuedQty()" path="listIndentIssueDtos[${f}].issueqty"
													onkeypress="return hasAmount(event, this, 10, 2)"  /></td>
		
											<td  class="text-center"><a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" onclick="addSerialUnitRow(this);"
														class="btn btn-success btn-sm " id="addSerialUnitRow${f}"><i class="fa fa-plus-circle"></i></a>  
													<a href="javascript:void(0);" title="<spring:message code="material.management.delete" text="Delete" />" class="btn btn-danger btn-sm delete deleteSerialUnitRow" 
														id="deleteSerialUnitRow${f}"><i class="fa fa-trash-o"></i></a></td>		
		
										</tr>
										<c:set var="f" value="${f+1}" scope="page" />
								</c:when>
								
								<c:otherwise>
									<c:forEach items="${command.listIndentIssueDtos}" var="details"
										varStatus="status">
										<tr class="serialTableRow">
											<td><input type="text" class="form-control text-center" disabled="true"
												id="serSrNo${f}" value="${f+1}" /></td>
	
											<td><form:select path="listIndentIssueDtos[${f}].binlocation" id="serialBinLocId${f}" 
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
															path="listIndentIssueDtos[${f}].itemno" readonly="true" />
													</c:when>
													<c:otherwise>
														<form:select path="listIndentIssueDtos[${f}].itemno" id="serialNumber${f}" 
															cssClass="form-control chosen-select-no-results" data-rule-required="false" 
															onchange="getSerialAvailbleQty('${f}')" >
															<form:option value="0">
																<spring:message code="material.management.select" text="Select" />
															</form:option>
															<c:forEach items="${command.listIndentIssueDtos[f].itemNumberList}" var="serialNo">
																<form:option value="${serialNo}" >${serialNo}</form:option>
															</c:forEach>
														</form:select>															
													</c:otherwise>
												</c:choose>
											</td>
													
											<c:if test="${command.saveMode ne 'V'}">
												<td><form:input type="text" id="availableQtySerial${f}" class="form-control" 
													path="listIndentIssueDtos[${f}].availableQty" readonly="true" /></td>
											</c:if>
														
											<td><form:input type="text" id="issuedQtySerial${f}" class="form-control text-right" 
													onchange="calculateSerialIssuedQty()" path="listIndentIssueDtos[${f}].issueqty"
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


				<c:otherwise>
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
								<c:when test="${empty command.listIndentIssueDtos}">
									<tr class="notInBatchTableRow">
										<td><input class="form-control text-center"
											disabled="true" id="notInBatchSequence${d}" value="${d+1}" /></td>
											
										<td><form:select path="listIndentIssueDtos[${d}].binlocation" id="notInBatchBinLocId${d}" 
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
												path="listIndentIssueDtos[${d}].availableQty" readonly="true" /></td>
												
										<td><form:input type="text" id="issuedQtyNotInBatch${d}" class="form-control text-right" 
												onchange="calculateNotInBatchIssuedQty()" path="listIndentIssueDtos[${d}].issueqty"
												onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																			
										<td  class="text-center" width="10%"><a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" onclick="addNewNotInBatchItemRow(this);"
													class="btn btn-success btn-sm " id="addNewNotInBatchItemRow${d}"><i class="fa fa-plus-circle"></i></a>  
												<a href="javascript:void(0);" title="<spring:message code="material.management.delete" text="Delete" />" 
													class="btn btn-danger btn-sm delete deleteNotInBatchUnitRow" id="deleteNotInBatchUnitRow${d}"><i class="fa fa-trash-o"></i></a></td>		
									</tr>
									<c:set var="d" value="${d+1}" scope="page" />
								</c:when>
								
								<c:otherwise>
									<c:forEach items="${command.listIndentIssueDtos}">
										<tr class="notInBatchTableRow">
											<td><input class="form-control text-center"
												disabled="true" id="notInBatchSequence${d}" value="${d+1}" /></td>
												
											<td><form:select path="listIndentIssueDtos[${d}].binlocation" id="notInBatchBinLocId${d}" 
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
													path="listIndentIssueDtos[${d}].availableQty" readonly="true" /></td>
											</c:if>
													
											<td><form:input type="text" id="issuedQtyNotInBatch${d}" class="form-control text-right" 
													onchange="calculateNotInBatchIssuedQty()" path="listIndentIssueDtos[${d}].issueqty"
													onkeypress="return hasAmount(event, this, 10, 2)"  /></td>
																				
											<c:if test="${command.saveMode ne 'V' }">
												<td class="text-center" width="10%"><a href="javascript:void(0);" title="<spring:message code="material.management.add" text="Add" />" 
														onclick="addNewNotInBatchItemRow(this);" class="btn btn-success btn-sm " 
														id="addNewNotInBatchItemRow${d}"><i class="fa fa-plus-circle"></i></a>  
													<a href="javascript:void(0);" title="<spring:message code="material.management.delete" text="Delete" />" 
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
				</c:otherwise>

			</c:choose>


			<div class="form-group">
				<apptags:input labelCode="department.indent.total.issued.quantity" isMandatory="true" 
					isReadonly="true" path="indentProcessDTO.item[${command.indexCount}].issuedqty" />

				<apptags:textArea labelCode="remark.goods" isMandatory="true"
							path="indentProcessDTO.item[${command.indexCount}].Remarks" />
			</div>

			</br>
			<div class="text-center padding-bottom-10">
				<c:if test="${command.saveMode ne 'V' }">
					<button type="button" class="btn btn-blue-2"
						onclick="saveIndentIssue(this)">
						<spring:message code="master.save" text="Save"></spring:message>
					</button>
				</c:if>
				<button type="button" class="button-input btn btn-danger"
					name="button-Cancel" value="Cancel" style=""
					onclick="backButton();" id="button-Cancel">
					<spring:message code="solid.waste.back" text="Back" />
				</button>
			</div>

		</form:form>
	</div>
</div>

