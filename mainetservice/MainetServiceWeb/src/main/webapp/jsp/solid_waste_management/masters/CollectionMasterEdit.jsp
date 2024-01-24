<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/RouteAndCollectionMaster.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="Collection.master.header"
					text="Collection Point Master" />
			</h2>
			<apptags:helpDoc url="BeatMaster.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand"
						text="Field with" /><i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="BeatMaster.html"
				id="RouteAndCollectionPointMaster"
				name="RouteAndCollectionPointMaster" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="RouteName"><spring:message
							code="Collection.master.route.name" text="Route Name" /></label>
					<div class="col-sm-4">
						<form:input name="" path="RouteMasterDTO.roName" type="text"
							class="form-control" id="RouteName" disabled="true"></form:input>
					</div>
					<label class="control-label col-sm-2" for="RouteNo"><spring:message
							code="Collection.master.route.no" text="Route No." /></label>
					<div class="col-sm-4">
						<form:input name="" path="RouteMasterDTO.roNo" type="text"
							class="form-control" id="RouteNo" disabled="true"></form:input>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="StartingPoint"><spring:message
							code="route.master.start.point" text="Starting Point" /></label>
					<div class="col-sm-4">
						<form:select path="RouteMasterDTO.roStartPoint"
							cssClass="form-control  mandColorClass" id="startPoint"
							data-rule-required="true" disabled="true">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${command.locList}" var="lookUp">
								<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="control-label col-sm-2 required-control"
						for="EndPoint"><spring:message
							code="route.master.end.point" text="End Point" /></label>
					<div class="col-sm-4">
						<form:select path="RouteMasterDTO.roEndPoint"
							cssClass="form-control  mandColorClass" id="endPoint"
							data-rule-required="true" disabled="true">
							<form:option value="${data.roStartPointName}">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${command.locList}" var="lookUp">
								<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<form:hidden path="routeMasterDTO.startLattitude" id="startLat" />
				<form:hidden path="routeMasterDTO.endLattitude" id="endLat" />
				<form:hidden path="routeMasterDTO.startLongitude" id="startLong" />
				<form:hidden path="routeMasterDTO.endLongitude" id="endLong" />
				<div class="table-responsive">
					<c:set var="d" value="0" scope="page" />
					<table id="unitDetailTable"
						class="table table-striped table-bordered appendableClass unitDetails">
						<c:set var="d" value="0" scope="page"></c:set>
						<thead>
							<tr>
								<th width="60" class="required-control"><spring:message
										code="Collection.master.seqno" text="Seq. No." /></th>

								<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
									showOnlyLabel="false"
									pathPrefix="routeMasterDTO.tbSwRootDets[0].codWard"
									isMandatory="true" hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control required-control" showAll="false"
									hasTableForm="true" showData="false" columnWidth="10%" />
								<th class="required-control"><spring:message
										code="ollection.master.collection.point.name"
										text="Collection Point Name" /></th>
								<th width="250"><spring:message
										code="Collection.master.detail.address" text="Detail Address" /></th>
								<th><spring:message code="Collection.master.latitude"
										text="Latitude" /></th>
								<th><spring:message code="Collection.master.longitude"
										text="Longitude" /></th>
								<th width="120" class="required-control"><spring:message
										code="Collection.master.collection.type"
										text="Collection Type" /></th>
								<th width="80"><spring:message
										code="Collection.master.rfid" text="RFID/No" /></th>

								<th width="80"><spring:message
										code="Collection.master.assumed.quantity"
										text="Assumed Quantity" /></th>

								<th colspan="2"><a href="javascript:void(0);" title="Add"
									class="addCF btn btn-success btn-sm unit" id="addUnitRow"><i
										class="fa fa-plus-circle"></i></a></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.routeMasterDTO.tbSwRootDets}"
								var="data" varStatus="index">

								<tr class="firstUnitRow">
									<td><form:input
											path="routeMasterDTO.tbSwRootDets[${index.index}].roSeqNo"
											cssClass="form-control required-control mandColorClass"
											id="seqno${index.index}"></form:input></td>
									<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
										showOnlyLabel="false"
										pathPrefix="routeMasterDTO.tbSwRootDets[${index.index}].codWard"
										isMandatory="true" hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control required-control " showAll="false"
										hasTableForm="true" showData="true" columnWidth="10%" />
									<td><form:input
											path="routeMasterDTO.tbSwRootDets[${index.index}].roCollPointname"
											cssClass="form-control required-control mandColorClass"
											value="" id="collpoint"></form:input></td>
									<td><div class="input-group">
											<form:input
												path="routeMasterDTO.tbSwRootDets[${index.index}].roCollPointadd"
												class="form-control" value="" id="colladd"
												onclick="getLatLong(0)"></form:input>
											<span class="input-group-addon"> <strong
												class="fa fa-globe"><span class="hide"><spring:message
															code="Collection.master.latitude" text="Latitude" /></span></strong>
											</span>
										</div></td>
									<td><form:input
											path="routeMasterDTO.tbSwRootDets[${index.index}].roCollLatitude"
											class="form-control" value="" id="collLatitude"></form:input></td>
									<td><form:input
											path="routeMasterDTO.tbSwRootDets[${index.index}].roCollLongitude"
											class="form-control" value="" id="collLongitude"></form:input></td>
									<td><c:set var="baseLookupCode" value="COT" /> <apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="routeMasterDTO.tbSwRootDets[${index.index}].roCollType"
											cssClass="form-control required-control" isMandatory="true"
											selectOptionLabelCode="selectdropdown" hasId="true"
											hasTableForm="true" /></td>
									<td><form:input
											path="routeMasterDTO.tbSwRootDets[${index.index}].referenceNo"
											class="form-control" value="" id="referenceid"></form:input></td>
									<td><form:input
											path="routeMasterDTO.tbSwRootDets[${index.index}].roAssumQuantity"
											class="form-control" value="" id="assumedQty"></form:input></td>
									<td class="text-center"><form:checkbox
											id="roCollActive${index.index}"
											path="routeMasterDTO.tbSwRootDets[${index.index}].roCollActive"
											value="Y" /></td>
								</tr>
							</c:forEach>
							<c:if test="${command.routeMasterDTO.tbSwRootDets.size() == 0 }">
								<tr class="firstUnitRow">
									<td><form:input
											path="routeMasterDTO.tbSwRootDets[0].roSeqNo"
											cssClass="form-control mandColorClass" id="seqno"></form:input></td>
									<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
										showOnlyLabel="false"
										pathPrefix="routeMasterDTO.tbSwRootDets[0].codWard"
										isMandatory="true" hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control required-control " showAll="false"
										hasTableForm="true" showData="true" columnWidth="10%" />
									<td><form:input
											path="routeMasterDTO.tbSwRootDets[0].roCollPointname"
											class="form-control" value="" id="collpoint"></form:input></td>

									<td><div class="input-group">
											<form:input
												path="routeMasterDTO.tbSwRootDets[0].roCollPointadd"
												class="form-control" value="" id="colladd"
												onclick="getLatLong(0)"></form:input>
											<span class="input-group-addon"> <strong
												class="fa fa-globe"><span class="hide"><spring:message
															code="Collection.master.latitude" text="Latitude" /></span></strong>
											</span>
										</div></td>
									<td><form:input
											path="routeMasterDTO.tbSwRootDets[0].roCollLatitude"
											class="form-control" value="" id="collLatitude"></form:input></td>
									<td><form:input
											path="routeMasterDTO.tbSwRootDets[0].roCollLongitude"
											class="form-control" value="" id="collLongitude"></form:input></td>
									<td><c:set var="baseLookupCode" value="COT" /> <apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="routeMasterDTO.tbSwRootDets[0].roCollType"
											cssClass="form-control required-control" isMandatory="true"
											selectOptionLabelCode="selectdropdown" hasId="true"
											hasTableForm="true" /></td>
									<td><form:input
											path="routeMasterDTO.tbSwRootDets[0].referenceNo"
											class="form-control" value="" id="referenceid"></form:input></td>
									<td><form:input
											path="routeMasterDTO.tbSwRootDets[0].roAssumQuantity"
											class="form-control" value="" id="assumedQty"></form:input></td>

									<td class="text-center"><form:checkbox
											path="routeMasterDTO.tbSwRootDets[0].roCollActive"
											id="roCollActive" value="Y" checked="true" /></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
				<div class="text-left padding-top-10">
					<div class="form-group">
						<label class="col-sm-2 control-label" for="UploadDocument"><spring:message
								code="population.master.excel.upload" text="Excel Upload" /></label>
						<div class="col-sm-2">
							<apptags:formField fieldPath="excelFileName"
								showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
								maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="EXCEL_IMPORT_VALIDATION_EXTENSION"
								currentCount="0" fieldType="7">
							</apptags:formField>
							<small class="text-blue-2" style="padding-left: 10px;"><spring:message
									code="population.master.excel.upload.max"
									text="(Upload Excel upto 5MB )" /></small>
						</div>
						<div class="col-sm-2">
							<form:hidden path="excelFileName" id="filePath" />
							<button type="button" class="btn btn-success save"
								name="button-save" value="saveExcel" style=""
								onclick="uploadExcelFile();" id="button-save">
								<spring:message code="Upload" text="Save Excel" />
							</button>
						</div>
						<label class="col-sm-2 control-label" for="ExportDocument"><spring:message
								code="Collection.master.excel.template" text="Excel Template" /></label>
						<div class="col-sm-4">
							<button type="button" class="btn btn-success save"
								name="button-Cancel" value="import" style=""
								onclick="exportExcelData();" id="import">
								<spring:message code="Collection.master.excel.template"
									text="Excel Template" />
							</button>
						</div>
					</div>
					<div class="form-group">
						<div class="text-center">
							<a data-toggle="collapse" href="#collapseExample"
								class="btn btn-blue-2" id="viewloc" onclick="initMap2()"><i
								class="fa fa-map-marker"></i> <spring:message
									code="disposal.site.master.view.location" text="View  Location" /></a>
						</div>
						<div class="collapse margin-top-10" id="collapseExample">
							<div class="border-1 padding-5"
								style="height: 400px; width: 100%;" id="map-canvas"></div>
						</div>
					</div>
					<div class="text-center padding-top-10">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveCollectionMasterForm(this);">
							<spring:message code="solid.waste.submit" text="Submit"></spring:message>
						</button>
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="backRouteMasterForm();" id="button-Cancel">
							<spring:message code="solid.waste.back" text="Back" />
						</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
