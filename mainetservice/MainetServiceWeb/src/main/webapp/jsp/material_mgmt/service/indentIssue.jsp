<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
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
<div id="searchIndPage">
	<div id="searchAssetPage">
		<apptags:breadcrumb></apptags:breadcrumb>
		<div class="content">
			<div class="widget">
				<div class="widget-header">
					<h2>
						<spring:message code="department.indent.details" text=" Indent Detail" />
					</h2>
					<apptags:helpDoc url="IndentProcessApproval.html"></apptags:helpDoc>
				</div>
				<div class="pagediv">
					<div class="widget-content padding">
						<div class="mand-label clearfix">
							<span><spring:message code="solid.waste.mand"
									text="Field with"></spring:message><i class="text-red-1">*</i>
								<spring:message code="solid.waste.mand.field"
									text="is mandatory"></spring:message> </span>
						</div>
						<form:form id="indentIssueFormId" name="Indent Issue Form"
							class="form-horizontal" action="IndentProcessApproval.html"
							method="post">
							<jsp:include page="/jsp/tiles/validationerror.jsp" />
							<div class="warning-div error-div alert alert-danger alert-dismissible"
								id="errorDiv" style="display: none;"><i class="fa fa-plus-circle"></i>
							</div>
							
							<div class="panel-group accordion-toggle"
								id="accordion_single_collapse">
								<div class="panel panel-default">
									<h4 class="panel-title">
										<a data-target="#a1" data-toggle="collapse" class="collapsed"
											data-parent="#accordion_single_collapse" href="#a1"><spring:message
												code="department.indent.indentor.details" text="Indenter Details" /></a>
									</h4>
									
									<form:hidden path="levelcheck" id="levelcheck"  />
									<form:hidden path="saveMode" id="saveMode" />
									<form:hidden path="lastChecker" id="lastChecker" />
									<form:hidden path="indexCount" id="indexCount" />
																		
									<div id="a1" class="panel-collapse collapse in">
										<div class="panel-body">

											<c:if test="${command.saveMode ne 'A'}">
												<div class="form-group">
													<spring:eval
														expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp('${command.indentProcessDTO.status}','IDS',${UserSession.organisation}).getLookUpDesc()"
														var="otherField" />													
												<apptags:input labelCode="department.indent.status" path="" 
													placeholder="${otherField}"	isReadonly="true" />
												</div>
											</c:if>
										
											<div class="form-group">
												<apptags:input labelCode="department.indent.no" path="indentProcessDTO.indentno" 
														isReadonly="true" />
												
												<apptags:date fieldclass="datepicker" labelCode="department.indent.date"
													datePath="indentProcessDTO.indentdate" isMandatory="true"
													readonly="true" cssClass="custDate mandColorClass">
												</apptags:date>
											</div>
											
											<div class="form-group">
												<label class="control-label col-sm-2 required-control"><spring:message
														code="department.indent.indentor.name" text="Indentor Name" /></label>
												<div class="col-sm-4">
													<form:select path="indentProcessDTO.indenter" id="indenter" 
														cssClass="form-control mandColorClass chosen-select-no-results "
														data-rule-required="true" onchange="getEmpDetails(this)" disabled="true">
														<form:option value="">
															<spring:message code="material.management.select" text="select" />
														</form:option>
														<c:forEach items="${command.employees}" var="employees">
															<form:option value="${employees[3]}" label="${employees[0]} ${employees[2]}"></form:option>
														</c:forEach>
													</form:select>
												</div>

												<form:hidden path="indentProcessDTO.deptId" id="deptId" />
												<apptags:input labelCode="department.indent.dept" path="indentProcessDTO.deptName" isReadonly="true" />												
											</div>
											
											<div class="form-group">
												<form:hidden path="indentProcessDTO.desgId" id="desgId" />
												<apptags:input labelCode="department.indent.designation" path="indentProcessDTO.desgName" isReadonly="true" />
	
												<form:hidden path="indentProcessDTO.reportingmgr" id="reportingmgr" />
												<apptags:input labelCode="department.indent.reporting" path="indentProcessDTO.reportingMgrName" isReadonly="true" />
											</div>
											
											<div class="form-group">
												<apptags:input labelCode="department.indent.beneficiary" path="indentProcessDTO.beneficiary"
													isDisabled="true"/>												
											</div>

										</div>
									</div>
								</div>
							</div>
							
							<div class="panel-group accordion-toggle"
								id="accordion_single_collapse">
								<div class="panel panel-default">
									<h4 class="panel-title">
										<a data-target="#a2" data-toggle="collapse" class="collapsed"
											data-parent="#accordion_single_collapse" href="#a2"><spring:message
												code="department.indent.details" text="Indent Details" /></a>
									</h4>
									
									<div id="a2" class="panel-collapse collapse in">
										<div class="panel-body">
										
											<div class="form-group">
												<form:hidden path="indentProcessDTO.storeid" id="storeid" />
												<apptags:input labelCode="department.indent.storeName" path="indentProcessDTO.storeDesc" 
														isMandatory="treu" isReadonly="true" />

												<form:hidden path="indentProcessDTO.location" id="location" />
												<apptags:input labelCode="department.indent.storeLocation" path="indentProcessDTO.locationName" isReadonly="true" />												
											</div>
											
											<div class="form-group">
												<apptags:input labelCode="department.indent.deliveryAdd" path="indentProcessDTO.deliveryat"
													cssClass="hasChar mandColorClass form-control" isMandatory="true" isDisabled="true" />
													
												<apptags:date fieldclass="datepicker" labelCode="department.indent.deliveryDate"
													datePath="indentProcessDTO.expecteddate" isMandatory="true"
													cssClass="custDate mandColorClass date" isDisabled="true">
												</apptags:date>
											</div>
											
											<c:if test="${command.lastChecker eq 'true' || (command.indentProcessDTO.status eq 'I' && command.saveMode eq 'V')}">
												<div class="form-group">
													<label class="col-sm-2 control-label required-control  "><spring:message
															code="department.indent.issue.by" text="Issue By" /></label>
													<div class="col-sm-4">
														<form:select path="indentProcessDTO.issuedby" id="issuedBy"
															class="form-control chosen-select-no-results" data-rule-required="true">
															<form:option value="">
																<spring:message code="material.management.select" text="select" />
															</form:option>
															<c:forEach items="${command.employees}" var="employees">
																<form:option value="${employees[3]}" label="${employees[0]} ${employees[2]}"></form:option>
															</c:forEach>
														</form:select>
													</div>
	
													<apptags:date labelCode="department.indent.issue.date" datePath="indentProcessDTO.issuedDate"
														fieldclass="datepicker" isMandatory="true"></apptags:date>
												</div>											
											</c:if>
																						
											<div class="panel-group accordion-toggle" id="accordion_single_collapse">
												<h4>
													<spring:message code="material.item.master.itemDetails" text="Item Details" />
												</h4>

												<div id="initialDataTable">
													<c:set var="d" value="0" scope="page"></c:set>
													<table class="table table-bordered margin-bottom-10  table-striped table-bordered" id="initialTable">
														<thead>
															<tr>
																<th width=5% class="text-center"><spring:message code="store.master.srno" text="Sr.No." /></th>
																<th width=15% class="text-center"><spring:message code="department.indent.item.name" 
																		text="Item Name" /><i class="text-red-1">*</i></th>
																<th width=15% class="text-center"><spring:message code="material.management.UoM" 
																		text="UoM" /><i class="text-red-1">*</i></th>
																<th width=15% class="text-center"><spring:message code="material.management.itemManagementMethod" 
																		text="Item Management Method" /><i class="text-red-1">*</i></th>
																<th width=15% class="text-center"><spring:message code="department.indent.requested.quantity"
																		text="Requested Quantity" /><i class="text-red-1">*</i></th>
																<c:if test="${command.lastChecker eq 'true' || 
																		(command.indentProcessDTO.status eq 'I' && command.saveMode eq 'V')}">
																	<th width=15% class="text-center"><spring:message code="department.indent.message.issued.quantity"
																			text="Issued Quantity" /><i class="text-red-1">*</i></th>
																	<th class="text-center"><spring:message code="material.management.remarks" text="Remark" />
																			<i class="text-red-1">*</i></th>																
																	<th width=5% class="text-center"><spring:message code="store.master.action" text="Action" />
																		<i class="text-red-1">*</i></th>																
																</c:if>
															</tr>
														</thead>
														
														<tbody>
															<c:forEach items="${command.indentProcessDTO.item}" var="data" varStatus="loop">
																<c:set value="${loop.index}" var="count"></c:set>
																<tr class="withHeld">
																	<td><form:input path="" id="sNo${d}" value="${d + 1}" readonly="true"
																			cssClass="form-control " disabled="true" /></td>
																			
																	<td><form:hidden path="indentProcessDTO.item[${d}].itemid" id="itemid${d}" /> 
																		<form:input path="indentProcessDTO.item[${d}].itemName" class="form-control" 
																			id="itemName${d}" disabled="true" /></td>
																			
																	<td align="center"><form:input path="indentProcessDTO.item[${d}].uom"
																			class="form-control hasNameClass valid" id="uom${d}" disabled="true" /></td>
																	
																	<td><form:hidden path="indentProcessDTO.item[${d}].managementCode" id="managementCode${d}" />
																		<form:input path="indentProcessDTO.item[${d}].managementDesc"
																			class="form-control hasNameClass valid" id="uom${d}" disabled="true" /></td>																	
																	
																	<td align="center"><form:input path="indentProcessDTO.item[${d}].quantity"
																			class="form-control text-right unit hasNumber" maxlength="10" 
																			id="quantity${d}" placeholder="Enter Quantity Value" disabled="true" /></td>

																	<c:if test="${command.lastChecker eq 'true' || 
																		(command.indentProcessDTO.status eq 'I' && command.saveMode eq 'V') }">

																		<td align="center"><form:input path="indentProcessDTO.item[${d}].issuedqty"
																				class="form-control text-right unit hasNumber" id="issuedQty${d}" 
																				readonly="true" /></td>
																				
																		<td align="center"><form:textarea path="indentProcessDTO.item[${d}].Remarks"
																				class="form-control hasNameClass valid" id="Remarks${d}" readonly="true" /></td>
																	
																		<td>
																			<div class="text-center">
																				<button type="button" class="btn btn-blue-3 btn-sm margin-right-5" title="<spring:message 
																					code="department.indent.view.issue.details" />" onclick="viewIndentIssue(this,'${d}');" >
																					<i class="fa fa-building-o"></i></button>																			
																			</div>
																		</td>
																	</c:if>
																</tr>
																<c:set var="d" value="${d + 1}" scope="page" />
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>	
											
											<c:if test="${ 1 eq command.levelcheck}">
												<br/>
												<apptags:CheckerAction hideForward="true" hideSendback="true"></apptags:CheckerAction>					
											</c:if>											
											
											<div class=" form-group ">
												<div class=" col-sm-12 text-center ">
													<c:if test="${! command.attachDocsList.isEmpty()}">
														<div class="table-responsive">
															<table class="table table-bordered margin-bottom-10" id="deleteDoc">
																<tr>
																	<th class="text-center" width="15%"><spring:message
																			code="population.master.srno" text="Sr. No." /></th>
																	<th><spring:message code="scheme.view.document" text="" /></th>
																</tr>
																<c:set var="e" value="0" scope="page" />
																<c:forEach items="${command.attachDocsList}" var="lookUp">
																	<tr>
																		<td class="text-center">${e+1}</td>
																		<td class="text-center"><apptags:filedownload filename="${lookUp.attFname}"
																				filePath="${lookUp.attPath}" actionUrl="IndentProcessApproval.html?Download" /></td>
																	</tr>
																	<c:set var="e" value="${e + 1}" scope="page" />
																</c:forEach>
															</table>
														</div>
													</c:if>
												</div>
											</div>								
											
											</br>
											<div class="text-center padding-bottom-10">
												<c:if test="${command.saveMode ne 'V'}">
													<button type="button" class="btn btn-success" id="submit" onclick="submitIndentIssueForm(this)">
														<spring:message code="material.management.submit" text="Submit"></spring:message>
													</button>
												</c:if>
												<c:choose>
													<c:when test="${ 0 eq command.levelcheck}">
														<apptags:backButton url="IndentProcess.html"></apptags:backButton>
													</c:when>
													<c:otherwise>
														<apptags:backButton url="AdminHome.html"></apptags:backButton>
													</c:otherwise>
												</c:choose>	
											</div>
											
										</div>
									</div>
								</div>
							</div>
										
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>