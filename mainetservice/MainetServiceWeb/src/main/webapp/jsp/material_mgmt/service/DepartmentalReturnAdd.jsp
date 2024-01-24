<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css">
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="js/material_mgmt/service/DepartmentalReturn.js"
	type="text/javascript"></script>
<style>
#initialTable_info {
	text-align: left;
}
</style>

<div id="searchAssetPage">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="material.management.Indent.msg"
						text="Departmental Return" />
				</h2>
				<apptags:helpDoc url="DeptReturn.html"></apptags:helpDoc>
			</div>
			<div class="pagediv">
				<div class="widget-content padding">
					<div class="mand-label clearfix">
						<span><spring:message code="solid.waste.mand"
								text="Field with"></spring:message><i class="text-red-1">*</i> <spring:message
								code="solid.waste.mand.field" text="is mandatory"></spring:message>
						</span>
					</div>

					<form:form id="deptReturndAddFrm" name="addDeptReturnform"
						class="form-horizontal" action="DeptReturn.html" method="post"
						commandName="command">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv" style="display: none;">
							<i class="fa fa-plus-circle"></i>
						</div>

						<div class="panel-group accordion-toggle"
							id="accordion_single_collapse">
							<div class="panel panel-default">
								<h4 class="panel-title">
									<a data-target="#a1" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#a1"><spring:message
											code="department.indent.indentor.details"
											text="Indenter Details" /></a>
								</h4>
								<form:hidden path="levelcheck" id="levelcheck" />
								<form:hidden path="saveMode" id="saveMode" />
								<form:hidden path="lastChecker" id="lastChecker" />
								<form:hidden path="indexCount" id="indexCount" />
								<div id="a1" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="form-group">
											<form:hidden
												path="deptReturnDto.deptItemDetailsDTOList[${command.indexCount}].itemid"
												id="itemid" />
										</div>

										<div class="form-group">

											<label class="control-label col-sm-2 required-control"><spring:message
													code="material.management.employee.name"
													text="Employee Name" /></label>
											<div class="col-sm-4">
												<form:select path="deptReturnDto.indenter"
													cssClass="form-control chosen-select-no-results"
													id="indenter"
													onchange="getEmpDetails();IndetNoEmpDetails()"
													disabled="${command.saveMode ne 'A'}">
													<form:option value="">
														<spring:message code="material.management.select"
															text="select" />
													</form:option>
													<c:forEach items="${command.employees}" var="employees">
														<form:option value="${employees[3]}"
															label="${employees[0]} ${employees[2]}"></form:option>
													</c:forEach>
												</form:select>
											</div>

											<apptags:date fieldclass="datepicker"
												labelCode="material.management.returnDate"
												isMandatory="true" datePath="deptReturnDto.dreturndate"
												cssClass="custDate mandColorClass"
												isDisabled="${command.saveMode ne 'A' || command.completedFlag eq 'Y'}">
											</apptags:date>

										</div>

										<div class="form-group">

											<form:hidden path="deptReturnDto.deptId" id="deptId" />
											<apptags:input labelCode="department.indent.dept"
												path="deptReturnDto.deptName" isReadonly="true" />

											<form:hidden path="deptReturnDto.desgId" id="desgId" />
											<apptags:input labelCode="department.indent.designation"
												path="deptReturnDto.desgName" isReadonly="true" />
										</div>

										<div class="form-group">

											<form:hidden path="deptReturnDto.reportingmgr"
												id="reportingmgr" />
											<apptags:input labelCode="department.indent.reporting"
												path="deptReturnDto.reportingMgrName" isReadonly="true" />

											<apptags:input labelCode="department.indent.beneficiary"
												path="deptReturnDto.beneficiary" isReadonly="true" />
										</div>

										<div class="form-group">
											<label class="col-sm-2 control-label"><spring:message
													code="material.management.Is.Received.through.Indent"
													text="Is Received through Indent?" /><span class="mand"></span>
											</label>
											<div class="col-sm-4 radio-btns">

												<label class="radio-inline" for="ApplicableYes"> <form:radiobutton
														name="Applicable" path="deptReturnDto.previndent"
														value="Y" id="applicableYes" onclick="IndetNoEmpDetails()"
														disabled="${command.saveMode ne 'A'}"></form:radiobutton>
													<spring:message code="material.management.Yes" text="Yes" />
												</label> <label class="radio-inline" for="ApplicableNo"
													disabled="${command.saveMode ne 'A'}> <form:radiobutton
														name="Applicable" path="deptReturnDto.previndent"
														value="N" id="ApplicableNo"></form:radiobutton> <spring:message
														code="material.management.No" text="No" />
												</label>

											</div>

											<script>
						$(document).ready(function(){
							if('${command.deptReturnDto.previndent}' == '' || '${command.deptReturnDto.previndent}' == 'N'){
								$('#indentno').hide();
								$('#indentItems').hide();
								$("#ApplicableNo").prop("checked", true);
							}
                         })
					</script>


											<div class="no hidebox" id="indentno">

												<label for="indentid"
													class="control-label col-sm-2 required-control"><spring:message
														code="department.indent.no" text="Indent No." /></label>
												<div class="col-sm-4">

													<form:select class="chosen-select-no-results form-control"
														path="deptReturnDto.indentid" id="indentid"
														onchange="getIndentDetail();"
														disabled="${command.saveMode ne 'A'}">
														<form:option value="">
															<spring:message code="material.item.master.select"
																text="Select" />
														</form:option>
														<c:forEach items="${command.listIndentProcessDTO}"
															var="indent">
															<form:option value="${indent.indentid}"> ${indent.indentno} </form:option>

														</c:forEach>
													</form:select>
												</div>


											</div>

										</div>

										<div class="form-group">
											<form:hidden path="deptReturnDto.storeid" id="storeid" />
											<apptags:input labelCode="store.master.name"
												path="deptReturnDto.storeName" isReadonly="true" />

											<form:hidden path="deptReturnDto.location" id="locId" />
											<apptags:input labelCode="department.indent.storeLocation"
												path="deptReturnDto.locationName" isReadonly="true" />
										</div>

										<div class="text-center clear padding-10" id="indentItems">
											<c:if test="${command.saveMode eq 'A'}">
												<button type="button" class="btn btn-success btn-submit"
													title="Get Indent Items" onclick="searchIndentData(this);">
													<spring:message code="material.management.Get.Indent.Items" text="Get Indent Items" />
												</button>
											</c:if>

											<div id="initialDataTable">
												<c:set var="d" value="0" scope="page"></c:set>
												<c:if
													test="${ not empty command.deptReturnDto.deptItemDetailsDTOList}">
													<table class="table table-bordered margin-bottom-10"
														id="initialTable">
														<thead>
															<tr>
																<th width=5% class="text-center"><spring:message
																		code="store.master.srno" text="Sr.No." /></th>
																<th width=10% class="text-center"><spring:message
																		code="department.indent.item.name" text="Item Name" /><i
																	class="text-red-1">*</i></th>
																<th width=8% class="text-center"><spring:message
																		code="material.management.UoM" text="UoM" /></th>
																<th width=9% class="text-center"><spring:message
																		code="material.management.batchOrSerialNo" text="Batch No/Serial No" /></th>
																<th width=10% class="text-center"><spring:message
																		code="department.indent.message.issued.quantity" text="Issued Quantity" /><i
																	class="text-red-1">*</i></th>
																<th width=10% class="text-center"><spring:message
																		code="material.management.Return.Quantity" text="Return Quantity" /><i
																	class="text-red-1">*</i></th>
																<th width=15% class="text-center"><spring:message
																		code="material.management.Item.Condition" text="Item Condition" /><i class="text-red-1">*</i></th>
																<th width=15% class="text-center"><spring:message
																		code="material.management.Reason.Return" text="Reason for Return" /><i
																	class="text-red-1">*</i></th>
																<c:if test="${command.lastChecker && command.approvalLastFlag eq 'Y' || (command.saveMode eq 'V' && command.deptReturnDto.status eq 'A')}">
																<th width=5% class="text-center"><spring:message
																		code="material.expired.item.entry.mark.for.desposal" text="Mark for Disposal" /><i
																	class="text-red-1">*</i></th>	
																<th width=20% class="text-center"><spring:message
																		code="binLocMasDto.binLocation" text="Bin Location" /><i
																	class="text-red-1">*</i></th>
																	</c:if>
																	
																<c:if test="${command.saveMode eq 'A'}">
																	<th width="10%" class="text-center"><spring:message
																			code="store.master.action" text="Action" /></th>
																</c:if>
															</tr>
														</thead>
														<tbody>



															<c:forEach
																items="${command.deptReturnDto.deptItemDetailsDTOList}"
																var="data" varStatus="loop">
																<%-- <c:set value="${loop.index}" var="count"></c:set> --%>



																<tr class="firstUnitRow">
																	<td><form:input path="" id="sequence${d}"
																			value="${d + 1}" readonly="true"
																			cssClass="form-control text-center" /> </td>
																	<form:hidden
																		path="deptReturnDto.deptItemDetailsDTOList[${d}].primId"
																		id="primId${d}" />
																	<form:hidden path="deptReturnDto.deptItemDetailsDTOList[${d}].preReceivedQty" 
																	id="prereceived${d}"/>
																	
																	
																	

																	<td><form:input
																			path="deptReturnDto.deptItemDetailsDTOList[${d}].itemDesc"
																			id="itemDesc${d}" type="text"
																			class="form-control" readonly="true" />
								
																			</td>
																	<td><form:input
																			path="deptReturnDto.deptItemDetailsDTOList[${d}].uomDesc"
																			id="uomDesc${d}" type="text" class="form-control"
																			readonly="true" /></td>
																	<td><form:input
																			path="deptReturnDto.deptItemDetailsDTOList[${d}].itemno"
																			id="itemno${d}" type="text" class="form-control"
																			readonly="true" /></td>


																	<td><form:input
																			path="deptReturnDto.deptItemDetailsDTOList[${d}].issueqty"
																			id="issueqty${d}" type="text"
																			class="form-control" readonly="true" /></td>
																	<td><form:input
																			path="deptReturnDto.deptItemDetailsDTOList[${d}].returnqty"
																			id="returnqty${d}" type="text"
																			readonly="${command.saveMode ne 'A'}"
																			class="form-control"
																			onchange="CheckReturnQuantity();" /></td>

																	<td><form:select
																			path="deptReturnDto.deptItemDetailsDTOList[${d}].itemcondition"
																			id="itemcondition${d}" cssClass="form-control"
																			disabled="${command.saveMode ne 'A'}" hasId="true"
																			data-rule-required="false">
																			<c:set var="baseLookupCode" value="ICD" />
																			<form:option value="0">
																				<spring:message code="material.management.select"
																					text="Select" />
																			</form:option>
																			<c:forEach
																				items="${command.getLevelData(baseLookupCode)}"
																				var="lookUp">
																				<form:option value="${lookUp.lookUpId}"
																					code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																			</c:forEach>
																		</form:select></td>

																	<td><form:select
																			path="deptReturnDto.deptItemDetailsDTOList[${d}].reasonforreturn"
																			id="reasonforreturn${d}" cssClass="form-control"
																			disabled="${command.saveMode ne 'A'}" hasId="true"
																			data-rule-required="false">
																			<c:set var="baseLookupCode" value="RFR" />
																			<form:option value="0">
																				<spring:message code="material.management.select"
																					text="Select" />
																			</form:option>
																			<c:forEach
																				items="${command.getLevelData(baseLookupCode)}"
																				var="lookUp">
																				<form:option value="${lookUp.lookUpId}"
																					code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																			</c:forEach>
																		</form:select></td>

																	<c:if test="${command.lastChecker && command.approvalLastFlag eq 'Y' || (command.saveMode eq 'V' && command.deptReturnDto.status eq 'A')}">

																		<td><form:checkbox id="isdisposal${d}"
																				path="deptReturnDto.deptItemDetailsDTOList[${d}].disposalflag" 
																				value="N" checked="" onclick="checkDisposal('isdisposal${d}');"  disabled="${command.completedFlag eq 'Y' || command.saveMode eq 'V'}" />
																		</td>
																		<td><form:select
																				path="deptReturnDto.deptItemDetailsDTOList[${d}].binlocation"
																				id="BinId${d}"
																				cssClass="form-control chosen-select-no-results"
																				hasId="true" data-rule-required="false" disabled="${command.completedFlag eq 'Y' || command.saveMode eq 'V'}">
																				<form:option value="0">
																					<spring:message code="material.management.select"
																						text="Select" />
																				</form:option>
																				<c:forEach items="${command.binLocList}"
																					var="binLoc">
																					<form:option value="${binLoc.binLocId}">${binLoc.binLocation}</form:option>
																				</c:forEach>
																			</form:select></td>

																	</c:if>


																	<c:if test="${command.saveMode eq 'A'}">
																		<td>
																			<div class="text-center">

																				<a href='javascript:void(0);'
																					class="btn btn-danger btn-sm "
																					onclick="delButton($(this),${d})"
																					title="<spring:message code="material.management.delete" text="Delete" />">
																					<i class="fa fa-trash-o"></i>
																				</a>
																				</div>
																		</td>
																	</c:if>
																	</div>

																	</td>

																</tr>
																<c:set var="d" value="${d+1}" scope="page" />
															</c:forEach>
															

														</tbody>
													</table>
													</c:if>
											</div>

										</div>


									</div>
								</div>
							</div>
						</div>

						<c:if
							test="${command.lastChecker eq false && command.saveMode ne 'A' && command.completedFlag ne 'Y' && command.saveMode ne 'V' }">
							<div class="widget-content padding panel-group accordion-toggle"
								id="accordion_single_collapse1">
								<apptags:CheckerAction hideSendback="true" hideForward="true"
									hideUpload="true"></apptags:CheckerAction>
							</div>
						</c:if>
					<c:if test="${command.lastChecker && command.approvalLastFlag eq 'Y' ||  (command.saveMode eq 'V' && command.deptReturnDto.status eq 'A')}">
						<div class="text-center clear padding-1" style="text-align: left;">
						<apptags:input labelCode="material.management.noting" path="deptReturnDto.noting"  isDisabled="${command.completedFlag eq 'Y' || command.saveMode eq 'V' }"/>
						</div>
					</c:if>	


				
						<div class="text-center clear padding-10">
						<c:if test="${command.completedFlag ne 'Y' && command.saveMode ne 'V'}">
							<button type="button" class="btn btn-success btn-submit"
								title="<spring:message code="material.management.submit" text="Submit" />"
								onclick="submitIndentReturnForm(this);">
								<spring:message code="material.management.submit" text="Submit" />
							</button>
							</c:if>
							<c:if test="${command.saveMode eq 'A'}">
								<button type="button" class="btn btn-warning" 
									title="<spring:message code="material.management.reset" text="Reset" />"
									onclick="addDeptReturn('DeptReturn.html','addDeptReturn');">
									<spring:message code="material.management.reset" text="Reset" />
								</button>
							</c:if>
							<c:choose>
								<c:when test="${command.saveMode eq 'A' ||command.saveMode eq 'V' }">
									<apptags:backButton url="DeptReturn.html"></apptags:backButton>
								</c:when>
								<c:otherwise>
									<apptags:backButton url="AdminHome.html"></apptags:backButton>
								</c:otherwise>


							</c:choose>
						</div>

					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
