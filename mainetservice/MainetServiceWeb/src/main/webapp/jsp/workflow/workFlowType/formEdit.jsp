<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/workflow/workFlowType/formEdit.js"></script>
<link rel="stylesheet"
	href="assets/libs/bootstrap-multiselect/css/bootstrap-multiselect.css"
	type="text/css">
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<div class="widget">
	<div class="widget-header">
		<h2>Workflow Master</h2>
		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"><i
				class="fa fa-question-circle fa-lg"></i></a>
		</div>
	</div>
	<div class="widget-content padding">
		<div class="mand-label clearfix">
			<span>Field with <i class="text-red-1">*</i> is mandatory
			</span>
		</div>
		<div class="error-div alert alert-danger alert-dismissible"
			id="errorDivId" style="display: none;">
			<ul>
				<li><label id="errorId"></label></li>
			</ul>
		</div>
		<form:form action="WorkFlowType.html" method="POST"
			name="workFlowTypeForm" class="form-horizontal" id="workFlowTypeForm">
			<form:hidden path="workFlowMasDTO.wfId" id="wfId" />
			<form:hidden path="workFlowMasDTO.hiddeValue" id="hiddeValue" />
			<form:hidden path="workFlowMasDTO.currentOrgId" id="onOrgSelect" />
			<form:hidden path="" id="auditDeptWiseFlag" value="${command.auditDeptWiseFlag}" />
			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse"
								data-parent="#accordion_single_collapse" href="#a1"> <spring:message
									code="workflow.select" text="Select Workflow" /></a>
						</h4>
					</div>
					<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="workflow.form.field.dep" text="Department" /></label>
								<div class="col-sm-4">
									<form:select path="workFlowMasDTO.deptId" id="departmentId"
										class="form-control" disabled="true">
										<option value="0"><spring:message code="workflow.form.select.department" /></option>
										<c:forEach items="${command.deptList}" var="objArray">
											<form:option value="${objArray[0]}" code="${objArray[3]}" label="${objArray[1]}"></form:option>
										</c:forEach>
										
									</form:select>
								</div>

								<label class="col-sm-2 control-label required-control"><spring:message
										code="workflow.form.field.service" text="Service" /></label>
								<div class="col-sm-4">
									<form:select name="serviceId" path="workFlowMasDTO.serviceId"
										id="serviceId" class="form-control" disabled="true">
										<option value="0"><spring:message code="workflow.form.select.service" /></option>
										<c:forEach items="${command.serviceList}" var="objArray">
											<form:option value="${objArray[0]}" code="${objArray[2]}"
												label="${objArray[1]}"></form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="workflow.mode" text="Work Flow Mode" /> </label>
								<apptags:lookupField items="${command.getLevelData('WFM')}"
									path="workFlowMasDTO.workflowMode"
									cssClass="form-control chosen-select-no-results"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" disabled="${command.modeType eq 'V'}" />
									
									<div id="workdeprtIdByDept">
									<label class="col-sm-2 control-label required-control"><spring:message
										code="workflow.form.field.work.dept" text="Department" /></label>
								<div class="col-sm-4">
									<form:select path="workFlowMasDTO.wmSchCodeId2" id="workdepartmentId" onchange="changeWardZone();"  disabled="true"
										class="form-control chosen-select-no-results mandColorClass">
										<form:option value="0">
											<spring:message code="selectdropdown" text="select" />
										</form:option>
										<c:forEach items="${command.deptList}" var="objArray">
											<c:if test="${userSession.languageId eq 1}">
												<form:option value="${objArray[0]}" label="${objArray[1]}"></form:option>
											</c:if>
											<c:if test="${userSession.languageId eq 2}">
												<form:option value="${objArray[0]}" label="${objArray[2]}"></form:option>
											</c:if>
										</c:forEach>
									</form:select>
								</div>
								</div>
								
							<c:if test="${command.auditDeptWiseFlag eq 'Y'}">
								<div id="assetclassby">
									<label class="col-sm-2 control-label required-control">
										<spring:message code='' text="Asset Class" />
									</label>
									<div class="col-sm-4">
									    
										<form:select path="workFlowMasDTO.wmSchCodeId2"
											class="form-control" id="assetClassBy" disabled="true">
											<form:option value="0">
												<spring:message code="solid.waste.select" text="select" />
											</form:option>
											<c:set var="baseLookupCode" value="ACL" />
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								</c:if>
									
								<div id="complaintTypeDiv">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="workflow.comptType" text="Complaint Type" /></label>
									<div class="col-sm-4">
										<form:select name="complaintId"
											path="workFlowMasDTO.complaintId" id="complaintId"
											class="form-control" disabled="true">
											<option value="0"><spring:message code="selectdropdown" text="select"/></option>
											<c:forEach items="${command.compList}" var="objArray">
												<form:option value="${objArray.compId}"
													label="${objArray.deptName} => ${objArray.complaintDesc}"></form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<c:if test="${command.billType eq 'Y'}">
								<div id="billTypeDiv">
										<label class="col-sm-2 control-label required-control"><spring:message
										code="" text="Bill Type" /></label>
										<div class="col-sm-4">
											<form:select path="workFlowMasDTO.wmSchCodeId2"  id="billTyp" disabled="true"
												class="form-control chosen-select-no-results "  label="Select">
												<c:set var="baseLookupCode" value="ABT" />
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
									</c:if>
									
								<c:if test="${command.category eq 'Y'}">	
									<div id="tradeCateg">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="" text="License Category" /></label>
										<div class="col-sm-4">
											<form:select path="workFlowMasDTO.wmSchCodeId2" id="tradeCat" disabled="true"
												class="form-control mandClorClass chosen-select-no-results"
												data-rule-required="true">
												<form:option value="">
													<spring:message code="" text="Select"></spring:message>
												</form:option>
												<c:forEach items="${command.triCodList1}" var="lookup">
													<form:option value="${lookup.lookUpId}">${lookup.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
								</c:if>
								<!------------  On Selection of serviceCode == 'LQE'  ------------------------------------------------------------------>
								<div id="workflowBaseDeptDiv">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="" text="Base Department" /></label>
									<div class="col-sm-4">
										<form:select path="workFlowMasDTO.wmSchCodeId1" disabled="true"
											class="form-control chosen-select-no-results" id="baseDeptId" >
											<form:option value="0">
												<spring:message code='master.selectDropDwn' />
											</form:option>
											<c:forEach items="${command.deptList}" var="objArray">
											<c:if test="${userSession.languageId eq 1}">
												<form:option value="${objArray[0]}" label="${objArray[1]}"></form:option>
											</c:if>
											<c:if test="${userSession.languageId eq 2}">
												<form:option value="${objArray[0]}" label="${objArray[2]}"></form:option>
											</c:if>
										</c:forEach>
										</form:select>
									</div>

								</div>

								<!-------------    On Selection of serviceCode == 'WOA'   ----------->
								<div id="sourceOfFundDiv">
									<label class="col-sm-2 control-label "><spring:message
											code="wms.SourceofFund" text="Source of Fund" /></label>
									<div class="col-sm-4">
										<form:select path="workFlowMasDTO.wmSchCodeId1"
											class="form-control chosen-select-no-results" id="sourceCode"
											disabled="true">
											<form:option value="0">
												<spring:message code='master.selectDropDwn' />
											</form:option>
											<c:forEach items="${command.sourceLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<!----------------------------------------------------------------------->
								
								<!--------------------On Selection of serviceCode == 'MOV'-------------------------------------------------------------------------------------------------->
								<div id="vehMainteinedBy">
									<label class="col-sm-2 control-label required-control">
										<spring:message code='' text="Vehicle Maintained By" />
									</label>
									<div class="col-sm-4">
										<form:select path="workFlowMasDTO.extIdentifier"
											class="form-control" id="vehMaintainBy" disabled="true">
											<form:option value="0">
												<spring:message code="solid.waste.select" text="select" />
											</form:option>
											<c:set var="baseLookupCode" value="MNC" />
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>

							</div>
							<div class="form-group" id="schemeNames">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="" text="Scheme Name" /></label>
								<div class="col-sm-4">
									<form:select path="workFlowMasDTO.wmSchCodeId2" id="schemeId"
										class="form-control chosen-select-no-results" disabled="true">
										<form:option value="0">
											<spring:message code='master.selectDropDwn' />
										</form:option>
										<c:forEach items="${command.sourceLevelLookUps}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>
								
								
								
							</div>
							
							
								<!------------  On Selection of serviceCode == 'CAE'  ------------------------------------------------------------------>
								<div id="sfacMasterDiv">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="" text="Name" /></label>
									<div class="col-sm-4">
										<form:select path="workFlowMasDTO.extIdentifier" disabled="true"
											class="form-control chosen-select-no-results" id="masId">
											<form:option value="0">
												<spring:message code='master.selectDropDwn' />
											</form:option>
											<c:forEach items="${command.commanMasDtoList}" var="dto">
												<c:choose>
													<c:when test="${dto.shortCode eq 'CBBO'}">
														<form:option value="${dto.id}" code="${dto.id}">${dto.name} - ${dto.iaName}</form:option>
													</c:when>
													<c:otherwise>
														<form:option value="${dto.id}" code="${dto.id}">${dto.name}</form:option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</form:select>
									</div>
								</div>
								</div>
							
							

							<div class="form-group">
								<apptags:input labelCode="workflow.form.field.label.fromAmount"
									path="workFlowMasDTO.fromAmount" isDisabled="true"
									cssClass="hasAmount" isMandatory=""></apptags:input>
								<apptags:input labelCode="workflow.form.field.label.toAmount"
									path="workFlowMasDTO.toAmount" isDisabled="true"
									cssClass="hasAmount" isMandatory=""></apptags:input>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2"><spring:message
										code="" text="Status" /></label>
								<div class="col-sm-4">
									<label class="checkbox-inline"><form:checkbox
											path="workFlowMasDTO.status" value="Y" id="wfStatus" /> <spring:message
											code="" text="Active" /> </label>
								</div>
							</div>
							<div id="wardZOneSelectionDiv">
								<div class="form-group clear">
									<label class="col-sm-2 control-label"><spring:message
											code="workflow.locationType" text="Location Type" /><span
										class="mand">*</span></label>
									<div class="col-sm-4">
										<span> <label class="radio-inline"> <form:radiobutton
													value="A" path="workFlowMasDTO.type" disabled="true" /> <spring:message
													code="workflow.form.field.label.all" /></label> <label
											class="radio-inline"> <form:radiobutton value="N"
													path="workFlowMasDTO.type" disabled="true" /> <spring:message
													code="workflow.form.field.label.wardzone" /></label>
										</span>
									</div>
								</div>

							</div>
							
							<div id="zone-ward">
								<apptags:lookupFieldSet baseLookupCode="${command.prefixName}"
									hasId="true" pathPrefix="workFlowMasDTO.codIdOperLevel"
									isMandatory="true" hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" cssClass="form-control"
									showAll="true" disabled="true" />
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-default" id="mappingDiv">
					<c:set var="count" value="0" scope="page" />
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a2"> <spring:message
									code="workflow.step" text="Add Work Flow Steps" />
							</a>
						</h4>
					</div>
					<div id="a2" class="panel-collapse collapse in">
						<div class="panel-body">
							<div id="table-responsive" class="table-overflow-sm">
								<c:set var="count" value="0" scope="page" />
								<table class="table table-bordered table-striped"
									id="customFields">
									<tbody>
										<tr>
											<th width="80"><spring:message code="workflow.event"
													text="Event" /><span class="mand">*</span></th>
											<th width="80"><spring:message code="workflow.org"
													text="Organization" /><span class="mand">*</span></th>
											<th width="80"><spring:message code="workflow.dept"
													text="Department" /><span class="mand">*</span></th>
											<th width="100"><spring:message code="workflow.roleEmp"
													text="Role/Emp" /><span class="mand">*</span></th>
											<th width="100"><spring:message code="workflow.details"
													text="Details" /><span class="mand">*</span></th>
											<th width="50"><spring:message code="workflow.sla"
													text="SLA" /></th>
											<th width="100"><spring:message code="workflow.units"
													text="Units" /></th>
											<th width="50"><spring:message code="workflow.appr"
													text="Number of Approvers" /></th>
											<c:if test="${command.modeType ne 'V'}">
												<th width="70"><spring:message
														code="workflow.addRemove" text="Add/Remove" /></th>
											</c:if>
										</tr>
										<c:forEach items="${command.workFlowMasDTO.workflowDet}"
											var="list">
											<tr class="appendableClass">
												<td><form:input type="hidden" id="id_${count}" value=""
														path="workFlowMasDTO.workflowDet[${count}].wfdId" /> <form:input
														type="hidden" id="hiddenRoleOrEmpId_${count}" value=""
														path="workFlowMasDTO.workflowDet[${count}].hiddenRoleOrEmpId" />
													<form:select
														path="workFlowMasDTO.workflowDet[${count}].eventId"
														class="form-control"
														onchange="checkForDuplicateEvent(this,${count});"
														id="eventMasterId_${count}">
														<form:option value="0">
															<spring:message code="select" text="Select" />
														</form:option>
														<c:forEach items="${command.eventList}" var="objArray">
															<option value="${objArray[3]}"><c:choose><c:when test="${userSession.languageId eq 2}">${objArray[1]}</c:when><c:otherwise>${objArray[0]}</c:otherwise></c:choose></option>
														</c:forEach>
													</form:select> <script>
									   $(document).ready(function(){
										   $('#eventMasterId_'+'${count}').val('${list.eventId}').attr("selected", "selected");
									   });
								 </script></td>

												<td><form:select
														path="workFlowMasDTO.workflowDet[${count}].mapOrgId"
														class="form-control" id="mapOrgId_${count}"
														onchange="showMappingDept(this);">
														<form:option value="0">
															<spring:message code="selectdropdown" text="Select" />
														</form:option>
														<c:forEach items="${command.orgList}" var="orgArray">
															<form:option value="${orgArray[0]}"
																label="${orgArray[1]}"></form:option>
														</c:forEach>
													</form:select></td>

												<td><form:select
														path="workFlowMasDTO.workflowDet[${count}].mapDeptId"
														class="form-control" id="mapDeptId_${count}">
														<form:option value="0">
															<spring:message code="selectdropdown" text="Select" />
														</form:option>
														<c:forEach
															items="${command.workFlowMasDTO.workflowDet[count].mapDeptList}"
															var="deptArray">
															<form:option value="${deptArray[0]}"
																label="${deptArray[1]}"></form:option>
														</c:forEach>
													</form:select></td>
												<td><form:select
														path="workFlowMasDTO.workflowDet[${count}].roleType"
														class="form-control" id="roleType_${count}"
														onchange="showEmpOrRole(this);">
														<form:option value="0">
															<spring:message code="select" text="Select" />
														</form:option>
														<form:option value="R">ROLE</form:option>
														<form:option value="E">Employee</form:option>
													</form:select></td>
													<c:choose>
														<c:when test="${command.modeType eq 'V'}">
															<td><form:textarea
																	path="workFlowMasDTO.workflowDet[${count}].roleOrEmployeeDesc"
																	class="form-control"
																	id="roleOrEmployeeDesc${count}" /></td>
														</c:when>
														<c:otherwise>
															<td class="mul_${count}"><form:select
																	path="workFlowMasDTO.workflowDet[${count}].roleOrEmpIds"
																	class="form-control multiple-chosen" multiple="multiple"
																	id="roleOrEmpId_${count}">
																	<%-- <form:option value="0"><spring:message code="select" text="select"/></form:option> --%>
																	<c:choose>
																		<c:when test="${list.roleType eq 'R'}">
																			<c:forEach
																				items="${command.workFlowMasDTO.workflowDet[count].mapRoleList}"
																				var="objArray">
																				<option value="${objArray[0]}">${objArray[1]}</option>
																			</c:forEach>
																		</c:when>
																		<c:otherwise>
																			<c:forEach
																				items="${command.workFlowMasDTO.workflowDet[count].mapEmpList}"
																				var="objArray1">
																				<option value="${objArray1[3]}">${objArray1[0]}
																					${objArray1[2]}</option>
																			</c:forEach>
																		</c:otherwise>
																	</c:choose>
																</form:select> <script>
																	        $(document).ready(function() {
																	        	  var roleOrEmpIdElement = $('#roleOrEmpId_${count}');
																	        	  var hiddenRoleOrEmpIdElement = $('#hiddenRoleOrEmpId_${count}');
																	        	  var listRoleOrEmpIds = '${list.roleOrEmpIds}';
												
																	        	  roleOrEmpIdElement.val(listRoleOrEmpIds.split(','));
																	        	  roleOrEmpIdElement.multiselect();
																	        	  hiddenRoleOrEmpIdElement.val(${command.workFlowMasDTO.workflowDet[count].hiddenRoleOrEmpId});
																	        	});
																	    </script></td>
														</c:otherwise>
													</c:choose>

												<td><form:input
														path="workFlowMasDTO.workflowDet[${count}].sla"
														class="form-control" id="sla_${count}" maxlength="6"
														onkeypress="return hasAmount(event, this, 5, 2)"
														onchange="getAmountFormatInDynamic((this),'sla')" /></td>
												<td><c:set var="baseLookupCode" value="UTS" /> <form:select
														path="workFlowMasDTO.workflowDet[${count}].unit"
														class="form-control" id="units_${count}">
														<form:option value="0">
															<spring:message code="select" text="Select" />
														</form:option>
														<c:forEach items="${command.getLevelData(baseLookupCode)}"
															var="lookUp">
															<c:if
																test="${lookUp.lookUpCode eq 'D' || lookUp.lookUpCode eq 'H'}">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
															</c:if>
														</c:forEach>
													</form:select></td>
												<td><form:input
														path="workFlowMasDTO.workflowDet[${count}].apprCount"
														class="form-control hasNumber" maxlength="2"
														id="apprCount_${count}" /></td>

												<c:if test="${command.modeType ne 'V'}">
													<td align="center"><a href="javascript:void(0);"
														class="addCF btn btn-success btn-sm"><i
															class="fa fa-plus-circle"></i></a> <a
														href="javascript:void(0);" data-placement="top"
														class="remCF btn btn-danger btn-sm"><i
															class="fa fa-minus"></i></a></td>
												</c:if>
											</tr>
											<c:set var="count" value="${count+1}" scope="page" />
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<c:if test="${command.modeType ne 'V'}">
						<button type="button" class="btn btn-success btn-submit"
							id="submitFlow" title="Submit">
							<spring:message code="bt.save" />
						</button>
					</c:if>
					<c:if test="${command.modeType eq 'C'}">
						<button type="Reset" class="btn btn-warning" id="resetFlow" title="Reset">
							<spring:message code="bt.clear" />
						</button>
					</c:if>
					<input type="button" id="backBtn" class="btn btn-danger" title="Back"
						onclick="back()" value="<spring:message code="bt.backBtn"/>" />
				</div>
				<form:hidden path="removeChildIds" id="removeChildIds" />
			</div>
		</form:form>
	</div>
</div>
