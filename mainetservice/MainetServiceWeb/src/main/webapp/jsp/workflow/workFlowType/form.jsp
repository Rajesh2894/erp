<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/workflow/workFlowType/form.js"></script>
<link rel="stylesheet"
	href="assets/libs/bootstrap-multiselect/css/bootstrap-multiselect.css"
	type="text/css">
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<script type="text/javascript">
	
</script>

<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="workflow.grid.workflowmaster"
				text="Work Flow Master" />
		</h2>
		<apptags:helpDoc url="WorkFlowType.html"></apptags:helpDoc>
	</div>
	<div class="widget-content padding">
		<div class="mand-label clearfix">
			<span><spring:message code="contract.breadcrumb.fieldwith"
					text="Field with" /> <i class="text-red-1">*</i> <spring:message
					code="common.master.mandatory" text="is mandatory" /> </span>
		</div>
		<div class="error-div alert alert-danger alert-dismissible"
			id="errorDivId" style="display: none;">
			<ul>
				<li><label id="errorId"></label></li>
			</ul>
		</div>
		<form:form action="WorkFlowType.html" method="POST"
			name="workFlowTypeForm" class="form-horizontal" id="workFlowTypeForm">
			<form:hidden path="workFlowMasDTO.hiddeValue" id="hiddeValue" />
			<form:hidden path="workFlowMasDTO.currentOrgId" id="onOrgSelect" />
			<form:hidden path="workFlowMasDTO.currentOrgId" id="onOrg_0" />
			<form:hidden path="" id="kdmcEnv" value="${command.kdmcEnv}" />
			<form:hidden path="" id="billType" value="${command.billType}" />
			<form:hidden path="" id="category" value="${command.category}" />
			<form:hidden path="" id="auditDeptWiseFlag" value="${command.auditDeptWiseFlag}" />
			
			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse"
								data-parent="#accordion_single_collapse" href="#a1"><spring:message
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
										class="form-control chosen-select-no-results mandColorClass">
										<form:option value="0">
											<spring:message code="selectdropdown" text="select" />
										</form:option>
										
										<c:forEach items="${command.deptList}" var="objArray">
											<c:if test="${userSession.languageId eq 1}">
												<form:option value="${objArray[0]}" code="${objArray[3]}" label="${objArray[1]}"></form:option>
											</c:if>
											<c:if test="${userSession.languageId eq 2}">
												<form:option value="${objArray[0]}" code="${objArray[3]}" label="${objArray[2]}"></form:option>
											</c:if>
										</c:forEach>
									
									</form:select>
								</div>
								<label class="col-sm-2 control-label required-control"><spring:message
										code="workflow.form.field.service" text="Service" /></label>
								<div class="col-sm-4">
									<form:select path="workFlowMasDTO.serviceId" id="serviceId"
										class="form-control chosen-select-no-results mandColorClass"
										onchange="getServiceCode();">
										<form:option value="0">
											<spring:message code="selectdropdown" text="select" />
										</form:option>
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
									selectOptionLabelCode="selectdropdown" isMandatory="true" />

								<div id="complaintTypeDiv">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="workflow.comptType" text="Complaint Type" /></label>
									<div class="col-sm-4">
										<form:select name="complaintId"
											path="workFlowMasDTO.complaintId" id="complaintId"
											class="form-control chosen-select-no-results mandColorClass">
											<form:option value="0">
												<spring:message code="selectdropdown" text="select" />
											</form:option>
										</form:select>
									</div>
								</div>
								<c:if test="${command.billType eq 'Y'}">
								<div id="billTypeDiv">
										<label class="col-sm-2 control-label required-control"><spring:message
										code="" text="Bill Type" /></label>
										<div class="col-sm-4">
											<form:select path="workFlowMasDTO.wmSchCodeId2"  id="billTyp"
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
										<form:select path="workFlowMasDTO.wmSchCodeId2" id="tradeCat"
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
										<form:select path="workFlowMasDTO.wmSchCodeId1"
											class="form-control chosen-select-no-results" id="baseDeptId">
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

								<!------------  On Selection of serviceCode == 'WOA'  ------------------------------------------------------------------>
								<div id="sourceOfFundDiv">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="wms.SourceofFund" text="Source of Fund" /></label>
									<div class="col-sm-4">
										<form:select path="workFlowMasDTO.wmSchCodeId1"
											class="form-control chosen-select-no-results" id="sourceCode"
											onchange="getSchemeNames(this);">
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
								
								<!------------  On Selection of serviceCode == 'CAE'  ------------------------------------------------------------------>
								<div id="sfacMasterDiv">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="" text="Name" /></label>
									<div class="col-sm-4">
										<form:select path="workFlowMasDTO.extIdentifier"
											class="form-control chosen-select-no-results mandColorClass" id="masId">
											<form:option value="0">
												<spring:message code='master.selectDropDwn' />
											</form:option>
											<c:forEach items="${command.commanMasDtoList}" var="dto">
												<c:choose>
													<c:when	test="${dto.shortCode eq 'CBBO'}">
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
								
								
								
								<div id="workdeprtIdByDept">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="workflow.form.field.work.dept" text="Department" /></label>
								<div class="col-sm-4">
									<form:select path="workFlowMasDTO.wmSchCodeId2" id="workdepartmentId" onchange="changeWardZone();"
										class="form-control chosen-select-no-results mandColorClass">
										<form:option value="0">
											<spring:message code="selectdropdown" text="select"  />
										</form:option>
										<c:forEach items="${command.deptList}" var="objArray">
											<c:if test="${userSession.languageId eq 1}">
												<form:option value="${objArray[0]}" code="${objArray[3]}" label="${objArray[1]}"></form:option>
											</c:if>
											<c:if test="${userSession.languageId eq 2}">
												<form:option value="${objArray[0]}" code="${objArray[3]}" label="${objArray[2]}"></form:option>
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
											class="form-control" id="assetClassBy">
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
								
								

								<!---------------------------------------------------------------------------------------------------------------------->
							
								<!--------------------On Selection of serviceCode == 'MOV'-------------------------------------------------------------------------------------------------->
								<div id="vehMainteinedBy">
									<label class="col-sm-2 control-label required-control">
										<spring:message code='' text="Vehicle Maintained By" />
									</label>
									<div class="col-sm-4">
									    
										<form:select path="workFlowMasDTO.extIdentifier"
											class="form-control chosen-select-no-results" id="vehMaintainBy">
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
										class="form-control chosen-select-no-results">
										<form:option value="0">
											<spring:message code='master.selectDropDwn' />
										</form:option>
									</form:select>
								</div>
							</div>
								



							<div class="form-group">
								<apptags:input labelCode="workflow.form.field.label.fromAmount"
									path="workFlowMasDTO.fromAmount" cssClass="hasAmount"></apptags:input>
								<apptags:input labelCode="workflow.form.field.label.toAmount"
									path="workFlowMasDTO.toAmount" cssClass="hasAmount"></apptags:input>
							</div>
							
							<div id="wardZOneSelectionDiv">
								<div class="form-group clear">
									<label class="col-sm-2 control-label"><spring:message
											code="workflow.locationType" text="Location Type" /><span
										class="mand">*</span></label>
									<div class="col-sm-4">
										<span> <label class="radio-inline"> <form:radiobutton
													value="A" path="workFlowMasDTO.type" /> <spring:message
													code="workflow.form.field.label.all" /></label> <label
											class="radio-inline"> <form:radiobutton value="N"
													path="workFlowMasDTO.type" /> <spring:message
													code="workflow.form.field.label.wardzone" /></label>
										</span>
									</div>
								</div>
							</div>
							<div id="zone-ward"></div>
						</div>
					</div>
				</div>
				<div class="panel panel-default" id="mappingDiv">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a2"><spring:message
									code="workflow.step" text="Add Work Flow Steps" /> </a>
						</h4>
					</div>
					<div id="a2" class="panel-collapse collapse in">
						<div class="panel-body">
							<div id="table-responsive" class="table-overflow-sm"></div>
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
						<button type="button" class="btn btn-warning" id="resetFlow" title="Reset">
							<spring:message code="bt.clear"  />
						</button>
					</c:if>
					<input type="button" id="backBtn" class="btn btn-danger" title="Back"
						onclick="back()" value="<spring:message code="bt.backBtn"/>" />
				</div>
				<form:hidden path="removeChildIds" />
				<input type="hidden" id="langId" value="${userSession.languageId}" />
			</div>
		</form:form>
	</div>
</div>