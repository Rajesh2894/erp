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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/MRFMaster.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<style>
.input-group-field {
	display: table-cell;
	width: 114px;
}

.grab {
	cursor: pointer;
}
</style>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="disposal.site.master.heading"
					text="MRF Center Master" />
			</h2>
			<apptags:helpDoc url="MRFMaster.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="MRFMaster.html" name="MRFMaster" id="MRFMasterId"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<h4>
					<spring:message code="swm.mrf.Plantdet" text="Plant Details" />
				</h4>
				<div class="form-group">
					<apptags:input labelCode="swm.mrf.plantId"
						path="mRFMasterDto.mrfPlantId" isDisabled="true"
						isMandatory="true" cssClass="form control mandcolour">
					</apptags:input>
					<apptags:input labelCode="swm.mrf.plantName" isDisabled="true"
						path="mRFMasterDto.mrfPlantName" isMandatory="true"
						cssClass="form control mandcolour hasNameClass">
					</apptags:input>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="Year"><spring:message
							code="swm.mrf.plantCat" text="Plant Category"></spring:message></label>
					<c:set var="baseLookupCode" value="DPC" />
					<apptags:lookupField
						items="${command.getSortedLevelData(baseLookupCode)}"
						path="mRFMasterDto.mrfCategory" disabled="true"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />

					<apptags:date fieldclass="datepicker" labelCode="swm.mrf.dateOfOpr"
						isDisabled="true" datePath="mRFMasterDto.mrfDateOpen"
						isMandatory="true" cssClass="custDate mandColorClass">
					</apptags:date>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control "
						for="Decentralized"><spring:message
							code="swm.mrf.decentralised" text="Decentralized ?" /></label>
					<div class="col-sm-4">
						<form:select path="mRFMasterDto.mrfDecentralised"
							cssClass="form-control  required-control mandColorClass"
							disabled="true" id="Decentralized" data-rule-required="true">
							<form:option value="">
								<spring:message code="solid.waste.select" text="select" />
							</form:option>
							<form:option value="C" code="">
								<spring:message code="swm.mrf.centralise" text="Centralized" />
							</form:option>
							<form:option value="D" code="">
								<spring:message code="swm.mrf.decentralise" text="DeCentralized" />
							</form:option>
						</form:select>
					</div>
					<label class="control-label col-sm-2 required-control "
						for="Ownership"><spring:message code="swm.mrf.ownership"
							text="Ownership ?" /></label>
					<div class="col-sm-4">
						<form:select path="mRFMasterDto.mrfOwnerShip" disabled="true"
							cssClass="form-control  required-control mandColorClass"
							id="ownershipId" data-rule-required="true">
							<form:option value="">
								<spring:message code="solid.waste.select" text="select" />
							</form:option>
							<form:option value="U" code="">
								<spring:message code="swm.mrf.ulb" text="ULB" />
							</form:option>
							<form:option value="O" code="">
								<spring:message code="swm.mrf.outsourcing" text="OutSourcing" />
							</form:option>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control "
						for="Location"><spring:message
							code="route.master.location" text="Location" /></label>
					<div class="col-sm-4">
						<form:select path="mRFMasterDto.locId"
							cssClass="form-control   mandColorClass" id="Location"
							disabled="true" data-rule-required="true">
							<form:option value="">
								<spring:message code="solid.waste.select" text="select" />
							</form:option>
							<c:forEach items="${command.locList}" var="lookUp">
								<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="control-label col-sm-2 required-control"
						for="DisposalSiteArea"><spring:message
							code="swm.mrf.reqPlantCap" text="Required Plant Capacity" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input path="mRFMasterDto.mrfPlantCap" disabled="true"
								class="form-control col-xs-8 mandColorClass  hasDecimal"
								id="mrfPlantCap"></form:input>
							<span class="input-group-addon"><spring:message
									code="swm.mrf.MTPD" text="TPD" /></span>
						</div>
					</div>
				</div>
				<h4>
					<spring:message code="swm.mrf.othrDet" text="Other Details" />
				</h4>
				<div class="form-group">
					<label class="control-label col-sm-2  "
						for="Whether Part Of Intrigated Plant(Y/N)"><spring:message
							code="swm.mrf.wintegratedPlant"
							text="Whether Part Of Integrated Plant(Y/N)" /></label>
					<div class="col-sm-4">
						<form:select path="mRFMasterDto.mrfIsIntegratedPlant"
							cssClass="form-control" id="intrigatationId"
							onchange="changeIntegratedPlant()" disabled="true"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="solid.waste.select" text="select" />
							</form:option>
							<form:option value="Y" code="">
								<spring:message code="solid.waste.Yes" text="Yes" />
							</form:option>
							<form:option value="N" code="">
								<spring:message code="solid.waste.No" text="No" />
							</form:option>
						</form:select>
					</div>
					<label class="control-label col-sm-2  "
						for="Integrated  With PlantId"><spring:message
							code="swm.mrf.integratedPlantId" text="Integrated  With Plant ID" /></label>
					<div class="col-sm-4">
						<form:select path="mRFMasterDto.mrfIntegratedPlantId"
							disabled="true" cssClass="form-control"
							id="intrigatationPlantId1" data-rule-required="true">
							<form:option value="">
								<spring:message code="solid.waste.select" text="select" />
							</form:option>
							<c:forEach items="${command.mRFMasterDtoList}" var="lookup">
								<form:option value="${lookup.mrfId}">${lookup.mrfPlantName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2" for=""><spring:message
							code="swm.mrf.rdfWTC" text="Is RDF Also  WTC(Y/N)" /></label>
					<div class="col-sm-4">
						<form:select path="mRFMasterDto.mrfIsrdf"
							cssClass="form-control chosen-select-no-results" id="rdfId"
							onchange="changeRDF()" disabled="true" data-rule-required="true">
							<form:option value="">
								<spring:message code="solid.waste.select" text="select" />
							</form:option>
							<form:option value="Y" code="">
								<spring:message code="solid.waste.Yes" text="Yes" />
							</form:option>
							<form:option value="N" code="">
								<spring:message code="solid.waste.No" text="No" />
							</form:option>
						</form:select>
					</div>
					<label class="control-label col-sm-2 " for="DisposalSiteArea"><spring:message
							code="swm.mrf.qntRDF" text="Quantity Of RDF" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input path="mRFMasterDto.mrfRdfqrt" disabled="true"
								class="form-control col-xs-8 mandColorClass  hasDecimal"
								onkeypress="return isNumberKey(event)" id="qntRDF1"></form:input>
							<span class="input-group-addon"><spring:message
									code="swm.mrf.MTPD" text="TPD" /></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2"
						for="Integrated With C&T(Y/N)"><spring:message
							code="swm.mrf.integtgratedC&T"
							text="Its Integrated with C&T (Y/N) " /></label>
					<div class="col-sm-4">
						<form:select path="mRFMasterDto.mrfIsctc"
							cssClass="form-control chosen-select-no-results"
							id="integratedCT" onchange="changeIntegratedCT()" disabled="true"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="solid.waste.select" text="select" />
							</form:option>
							<form:option value="Y" code="">
								<spring:message code="solid.waste.Yes" text="Yes" />
							</form:option>
							<form:option value="N" code="">
								<spring:message code="solid.waste.No" text="No" />
							</form:option>
						</form:select>
					</div>
					<label class="control-label col-sm-2"
						for="Arrangement If Integrated"><spring:message
							code="swm.mrf.arrangemntIfIntegrated"
							text="Arrangement If Integrated " /></label>
					<div class="col-sm-4">		
					<c:set var="baseLookupCode" value="CTC" />
					<form:select path="mRFMasterDto.mrfIsagreIntegrated"
						cssClass="form-control mandColorClass" id="m"
						 disabled="true" data-rule-required="true" >
						<form:option value="0">
							<spring:message code="solid.waste.select" text="select" />
						</form:option>
						<c:forEach items="${command.getSortedLevelData(baseLookupCode)}"
							var="lookUp">
							<form:option value="${lookUp.lookUpId}"
								code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
					</div>

				</div>
				<div class="form-group">
											
					<label class="col-sm-2 control-label required-control"><spring:message
												code="swm.property.number"
												text="Property number" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control" 
					     path="mRFMasterDto.propertyNo"
						id="propertyNo"
						readonly="true"					
						data-rule-required="true"></form:input>	
					</div>
					
					<div class="col-sm-4">
						
						
					</div>
				
					
				</div>
				<c:if test="${! command.attachDocsList.isEmpty()}">
					<div class="table-responsive">
						<table class="table table-bordered table-striped" id="attachDocs">
							<tr>
								<th width="20%"><spring:message
										code="public.toilet.master.srno" text="Sr. No." /></th>
								<th><spring:message code="swm.viewDocument"
										text="View Document" /></th>
							</tr>
							<c:forEach items="${command.attachDocsList}" var="lookUp"
								varStatus="d">
								<tr>
									<td align="center">${d.count}</td>
									<td align="center"><apptags:filedownload
											filename="${lookUp.attFname}" filePath="${lookUp.attPath}"
											actionUrl="MRFMaster.html?Download" /></td>
									<form:hidden path="" value="${lookUp.attId}" />

								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>
				
				<h4>
					<spring:message code="swm.mrf.projectDet" text="Project Details" />
				</h4>
				<div class="form-group">
					<label class="control-label col-sm-2" for="Project Code"><spring:message
							code="swm.mrf.prjCode" text="Project Code" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input type="text" path="mRFMasterDto.projCode"
								disabled="true" class="form-control" id="ProjectCodeId"></form:input>
							<span class="input-group-addon"> <strong
								class="fa fa-globe"><span class="hide"><spring:message
											code="" text="ProjectCodeId" /></span></strong>
							</span>
						</div>
					</div>
					<apptags:input labelCode="swm.mrf.prjctCost" isDisabled="true"
						path="mRFMasterDto.pojCost" isMandatory="false"
						cssClass="form control mandcolour hasDecimal">
					</apptags:input>
				</div>
				<div class="form-group">
					<apptags:input labelCode="swm.mrf.prjctProgress"
						path="mRFMasterDto.projProgress" isMandatory="false"
						isDisabled="true" cssClass="form control mandcolour ">
					</apptags:input>
					<label class="control-label col-sm-2 required-control" for="Asset Code"><spring:message
							code="vehicle.master.vehicle.asset.code" text="Asset Code" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input type="text" path="mRFMasterDto.assetCode"
								disabled="true" class="form-control" id="AssetId"></form:input>
							<span class="input-group-addon"> <strong
								class="fa fa-globe"><span class="hide"><spring:message
											code="Collection.master.latitude" text="Latitude" /></span></strong>
							</span>
						</div>
					</div>
				</div>
				<h4>
					<spring:message code="swm.mrf.vehDetails" text="Vehicle Details" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table id="unitDetailTable"
								class="table table-striped table-bordered appendableClass unitDetails">
								<thead>
									<tr>
										<th scope="col" width="10%"><spring:message
												code="population.master.srno" text="Sr.No." /></th>
										<th><spring:message code="route.master.vehicle.type"
												text="Vehicle Type" /></th>
										<th><spring:message code="swm.mrf.availCount"
												text="Available Count" /></th>
										<th><spring:message code="swm.mrf.reqCount"
												text="Required Count" /></th>
									</tr>
								</thead>
								<tbody>
								<c:forEach var="vehicleDetails" items="${command.mRFMasterDto.tbSwMrfVechicleDet}">
									<tr class="firstUnitRow">
										<td align="center"><form:input path=""
												cssClass="form-control mandColorClass " id="sequence"
												readonly="true" value="${d+1}" disabled="true" /></td>

										<td><c:set var="baseLookupCode" value="VCH" /> <form:select
												path="mRFMasterDto.tbSwMrfVechicleDet[${d}].veVeType"
												cssClass="form-control mandColorClass" id="veVeType${d}"
												onchange="" disabled="true" data-rule-required="true">
												<form:option value="0">
													<spring:message code="solid.waste.select" text="select" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:input type="text"
												path="mRFMasterDto.tbSwMrfVechicleDet[${d}].mrfvAvalCnt"
												disabled="true" class="form-control text-right hasNumber"
												id="vechAvlId${d}"></form:input></td>

										<td><form:input type="text"
												path="mRFMasterDto.tbSwMrfVechicleDet[${d}].mrfvReqCnt"
												disabled="true" class="form-control text-right hasNumber"
												id="vechReqId${d}"></form:input></td>
									</tr>
									<c:set var="d" value="${d+1}" scope="page" />
										</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>

				<h4>
					<spring:message code="swm.mrf.empDetails" text="Employee Details" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="empDetails">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table id="empDetailTable"
								class="table table-striped table-bordered appendableClass empDetails">
								<thead>
									<tr>
										<th width="100"><spring:message
												code="vehicle.maintenance.master.id" /></th>
										<th><spring:message
												code="employee.verification.designation" text="Designation" /></th>
										<th><spring:message code="swm.mrf.availCount"
												text="Available Count" /></th>
										<th><spring:message code="swm.mrf.reqCount"
												text="Required Count" /></th>
									</tr>
								</thead>
								<tbody>
								<c:forEach var="employeeDet" items="${command.mRFMasterDto.tbSwMrfEmployeeDet}">
									<tr class="firstEmpRow">
										<td align="center"><form:input path="" readonly="true"
												cssClass="form-control mandColorClass " id="sequence"
												value="${d+1}" disabled="true" /></td>

										<td><form:select
												path="mRFMasterDto.tbSwMrfEmployeeDet[${d}].dsgId"
												disabled="true" cssClass="form-control mandColorClass"
												id="dsgnId${d}" data-rule-required="true">
												<form:option value="">
													<spring:message code="solid.waste.select" />
												</form:option>
												<c:forEach items="${command.designationList}" var="lookup">
													<form:option value="${lookup.dsgid}">${lookup.dsgname}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:input type="text"
												path="mRFMasterDto.tbSwMrfEmployeeDet[${d}].mrfeAvalCnt"
												disabled="true" class="form-control text-right hasNumber"
												id="empAvlCountId${d}"></form:input></td>

										<td><form:input type="text"
												path="mRFMasterDto.tbSwMrfEmployeeDet[${d}].mrfeReqCnt"
												disabled="true" class="form-control text-right hasNumber"
												id="empReqCountId${d}"></form:input></td>

									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="text-center padding-top-10">
							<button type="button" class="button-input btn btn-danger"
								name="button-Cancel" value="Cancel" style=""
								onclick="backMrfMasterForm();" id="button-Cancel">
								<spring:message code="solid.waste.back" text="Back" />
							</button>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
