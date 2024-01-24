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
	src="js/solid_waste_management/DisposalSiteMaster.js"></script>
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
					text="Disposal Site Master" />
			</h2>
			<apptags:helpDoc url="DisposalSiteMaster.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="DisposalSiteMaster.html" name="DisposalSiteMaster"
				id="DisposalSiteMasterId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<h4>
					<spring:message code="" text="Plant Details" />
				</h4>
				<div class="form-group">
					<apptags:input labelCode="Plant ID" path="" isMandatory="true"
						cssClass="form control mandcolour hasNameClass">
					</apptags:input>
					<apptags:input labelCode="Plant Name" path="" isMandatory="true"
						cssClass="form control mandcolour hasNameClass">
					</apptags:input>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="Year"><spring:message
							code="" text="Plant Category"></spring:message></label>
					<c:set var="baseLookupCode" value="DPC" />
					<apptags:lookupField
						items="${command.getSortedLevelData(baseLookupCode)}" path=""
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />
					<apptags:date fieldclass="datepicker" labelCode="Date Of Operation"
						datePath="" isMandatory="true" cssClass="custDate mandColorClass">
					</apptags:date>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control "
						for="Ownership"><spring:message code=""
							text="Decentralized ?" /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control chosen-select-no-results required-control mandColorClass"
							id="ownershipId" data-rule-required="true">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<form:option value="Centralized" code="">Centralized</form:option>
							<form:option value="DeCentralized" code="">DeCentralized</form:option>
						</form:select>
					</div>
					<label class="control-label col-sm-2 required-control "
						for="Ownership"><spring:message code="" text="Ownership ?" /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control chosen-select-no-results required-control mandColorClass"
							id="ownershipId" data-rule-required="true">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<form:option value="ULB" code="">ULB</form:option>
							<form:option value="OutSourcing" code="">OutSourcing</form:option>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control "
						for="Location"><spring:message
							code="route.master.location" text="Location" /></label>
					<div class="col-sm-4">
						<form:select path="disposalMasterDTO.deLocId"
							cssClass="form-control   mandColorClass" id="Location"
							data-rule-required="true">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${command.locList}" var="lookUp">
								<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="control-label col-sm-2 required-control"
						for="DisposalSiteArea"><spring:message code=""
							text="Required Plant Capacity" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input path=""
								class="form-control col-xs-8 mandColorClass  hasDecimal"
								onkeypress="return isNumberKey(event)" id="DisposalSiteArea"></form:input>
							<span class="input-group-addon"><spring:message code=""
									text="MTPD" /></span>
						</div>
					</div>
				</div>
				<h4>
					<spring:message code="" text="Other Details" />
				</h4>
				<div class="form-group">
					<label class="control-label col-sm-2  "
						for="Whether Part Of Intrigated Plant(Y/N)"><spring:message
							code="" text="Whether Part Of Integrated Plant(Y/N)" /></label>
					<div class="col-sm-4">
						<form:select path="" cssClass="form-control" id="intrigatationId"
							data-rule-required="true">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<form:option value="Yes" code="">Yes</form:option>
							<form:option value="No" code="">No</form:option>
						</form:select>
					</div>
					<label class="control-label col-sm-2  "
						for="Integrated  With PlantId"><spring:message code=""
							text="Integrated  With Plant ID" /></label>
					<div class="col-sm-4">
						<form:select path="" cssClass="form-control" id="intrigatationId"
							data-rule-required="true">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<form:option value="" code=""></form:option>
							<form:option value="" code=""></form:option>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2"
						for="Whether Part Of Integrated Plant(Y/N)"><spring:message
							code="" text="Is RDF Also  WTC(Y/N)" /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control chosen-select-no-results" id="rdfId"
							data-rule-required="true">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<form:option value="Yes" code="">Yes</form:option>
							<form:option value="No" code="">No</form:option>
						</form:select>
					</div>
					<label class="control-label col-sm-2 " for="DisposalSiteArea"><spring:message
							code="" text="Quantity Of RDF" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input path=""
								class="form-control col-xs-8 mandColorClass  hasDecimal"
								onkeypress="return isNumberKey(event)" id=""></form:input>
							<span class="input-group-addon"><spring:message code=""
									text="MTPD" /></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2"
						for="Integrated With C&T(Y/N)"><spring:message code=""
							text="Its Integrated with C&T (Y/N) " /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control chosen-select-no-results" id="rdfId"
							data-rule-required="true">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<form:option value="Yes" code="">Yes</form:option>
							<form:option value="No" code="">No</form:option>
						</form:select>
					</div>
					<label class="control-label col-sm-2"
						for="Arrangement If Integrated"><spring:message code=""
							text="Arrangement If Integrated " /></label>
					<c:set var="baseLookupCode" value="CTC" />
					<apptags:lookupField
						items="${command.getSortedLevelData(baseLookupCode)}" path=""
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="" text="Plant View" /></label>
					<div class="col-sm-4">
						<small class="text-blue-2"> <spring:message
								code="disposal.site.master.image.upload"
								text="(Upload Image File upto 5 MB)" />
						</small>
						<apptags:formField fieldType="7" labelCode="" hasId="true"
							fieldPath="" isMandatory="false" showFileNameHTMLId="true"
							fileSize="BND_COMMOM_MAX_SIZE"
							maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="CHECK_LIST_VALIDATION_EXTENSION" currentCount="0" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="" text="Machinery View" /></label>
					<div class="col-sm-4">
						<small class="text-blue-2"> <spring:message
								code="disposal.site.master.image.upload"
								text="(Upload Image File upto 5 MB)" />
						</small>
						<apptags:formField fieldType="7" labelCode="" hasId="true"
							fieldPath="" isMandatory="false" showFileNameHTMLId="true"
							fileSize="BND_COMMOM_MAX_SIZE"
							maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="CHECK_LIST_VALIDATION_EXTENSION" currentCount="0" />
					</div>
				</div>
				<h4>
					<spring:message code="" text="Project Details" />
				</h4>
				<div class="form-group">
					<label class="control-label col-sm-2" for="Project Code"><spring:message
							code=" " text="Project Code" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input type="text" path="disposalMasterDTO.deGisId"
								class="form-control" id="ProjectCodeId"></form:input>
							<span class="input-group-addon"> <strong
								class="fa fa-globe"><span class="hide"><spring:message
											code="" text="ProjectCodeId" /></span></strong>
							</span>
						</div>
					</div>
					<apptags:input labelCode="Project Cost( In Rs )"
						path="disposalMasterDTO.deName" isMandatory="false"
						cssClass="form control mandcolour hasNameClass">
					</apptags:input>
				</div>
				<div class="form-group">
					<apptags:input labelCode="Project Progress"
						path="disposalMasterDTO.deName" isMandatory="false"
						cssClass="form control mandcolour hasNameClass">
					</apptags:input>
					<label class="control-label col-sm-2" for="GISId"><spring:message
							code="" text="Asset Code" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input type="text" path="disposalMasterDTO.deGisId"
								class="form-control" id="GISId"></form:input>
							<span class="input-group-addon"> <strong
								class="fa fa-globe"><span class="hide"><spring:message
											code="Collection.master.latitude" text="Latitude" /></span></strong>
							</span>
						</div>
					</div>
				</div>
				<h4>
					<spring:message code="" text="Vehicle Details" />
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
												code="swm.vehicleId" text="Sr.No." /></th>
										<th><spring:message code="" text="Vehicle Type" /></th>
										<th><spring:message code="" text="Available Count" /></th>
										<th><spring:message code="" text="Required Count" /></th>
										<c:if test="${command.saveMode ne 'V'}">
											<th width="100" class="text-center"><a
												href="javascript:void(0);" data-toggle="tooltip"
												data-original-title="Add"
												class="addCF btn btn-success btn-sm unit" id="addUnitRow"><i
													class="fa fa-plus-circle"></i></a></th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<tr class="firstUnitRow">
										<td align="center"><form:input path=""
												cssClass="form-control mandColorClass " id="sequence"
												value="${d+1}" disabled="true" /></td>

										<td><c:set var="baseLookupCode" value="VCH" /> <apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="disposalMasterDTO.veId" cssClass="form-control"
												isMandatory="false" selectOptionLabelCode="selectdropdown"
												hasId="true" hasTableForm="true" /></td>

										<td><form:input type="text"
												path="disposalMasterDTO.vechAvlCountId"
												class="form-control text-right" id="vechAvlId"></form:input></td>


										<td><form:input type="text"
												path="disposalMasterDTO.vechReqCountId"
												class="form-control text-right" id="vechReqId"></form:input></td>


										<td align="center"><a href="#" data-toggle="tooltip"
											data-placement="top" class="btn btn-danger btn-sm"
											onclick="deleteEntry($(this),'removedIds');"> <strong
												class="fa fa-trash"></strong> <span class="hide"><spring:message
														code="solid.waste.delete" text="Delete" /></span>
										</a></td>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<h4>
					<spring:message code="" text="Employee Details" />
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
										<%-- <th><spring:message code="" text="Employee Name" /></th> --%>
										<th><spring:message code="" text="Designation" /></th>
										<th><spring:message code="" text="Available Count" /></th>
										<th><spring:message code="" text="Required Count" /></th>
										<th class="text-center" scope="col" width="6%"><a
											href="javascript:void(0);" data-toggle="tooltip"
											data-original-title="Add"
											class="empAdd btn btn-success btn-sm unit"
											id="empDetailTable"><i class="fa fa-plus-circle"></i></a></th>
									</tr>
								</thead>
								<tbody>
									<tr class="firstEmpRow">
										<td align="center"><form:input path=""
												cssClass="form-control mandColorClass " id="sequence"
												value="${d+1}" disabled="true" /></td>
										<td><form:select path="disposalMasterDTO.desgId"
												cssClass="form-control mandColorClass" id="rdfId"
												data-rule-required="true">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<form:option value="Manager" code="">Manager</form:option>
												<form:option value="clerk" code="">clerk</form:option>
											</form:select></td>
										<td><form:input type="text"
												path="disposalMasterDTO.empAvlCountId"
												class="form-control text-right" id="empAvlCountId"></form:input></td>
										<td><form:input type="text"
												path="disposalMasterDTO.empReqCountId"
												class="form-control text-right" id="empReqCountId"></form:input></td>
										<td align="center"><a href="#" data-toggle="tooltip"
											data-placement="top" class="btn btn-danger btn-sm"
											onclick="deleteEntry($(this),'removedIds');"> <strong
												class="fa fa-trash"></strong> <span class="hide"><spring:message
														code="solid.waste.delete" text="Delete" /></span>
										</a></td>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</tbody>
							</table>
						</div>
						<div class="text-center padding-top-10">
							<button type="button" class="btn btn-success btn-submit"
								onclick="saveDisposalForm(this);">
								<spring:message code="solid.waste.submit" text="Submit"></spring:message>
							</button>
							<button type="Reset" class="btn btn-warning"
								onclick="resetForm()">
								<spring:message code="solid.waste.reset" text="Reset" />
							</button>
							<button type="button" class="button-input btn btn-danger"
								name="button-Cancel" value="Cancel" style=""
								onclick="backRouteMasterForm();" id="button-Cancel">
								<spring:message code="solid.waste.back" text="Back" />
							</button>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
