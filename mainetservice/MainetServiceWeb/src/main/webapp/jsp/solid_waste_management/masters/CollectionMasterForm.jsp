<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/RouteAndCollectionMaster.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<!-- Start Content here -->
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
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="BeatMaster.html"
				id="RouteAndCollectionPointMasterId"
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
							code="collection.route.name" text="Route Name" /></label>
					<div class="col-sm-4">
						<form:select id="faYearid" path="routeMasterDTO.roName"
							class="form-control" onchange="setFinancialYrId(this);"
							data-rule-required="true" readonly="">
						</form:select>
					</div>
					<label class="control-label col-sm-2" for="RouteNo"> <spring:message
							code="Collection.master.route.no" text="Route No." />
					</label>
					<div class="col-sm-4">
						<form:input path="routeMasterDTO.roNo" class="form-control"
							id="RouteNo" value="" readonly=""></form:input>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="StartingPoint"><spring:message
							code="route.master.start.point" text="Starting Point" /></label>
					<div class="col-sm-4">
						<form:select path="RouteMasterDTO.roStartPoint"
							cssClass="form-control  mandColorClass" id="startPoint"
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
						for="EndPoint"><spring:message
							code="route.master.end.point" text="End Point" /></label>
					<div class="col-sm-4">
						<form:select path="RouteMasterDTO.roEndPoint"
							cssClass="form-control  mandColorClass" id="endPoint"
							data-rule-required="true">
							<form:option value="${data.roStartPointName}">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${command.locList}" var="lookUp">
								<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="table-responsive">
					<c:set var="d" value="0" scope="page" />
					<table id="unitDetailTable"
						class="table table-striped table-bordered appendableClass unitDetails">
						<c:set var="d" value="0" scope="page"></c:set>
						<thead>
							<tr>
								<%-- 		<th width="2%"><spring:message
										code="Collection.master.seqno" text="Seq. No." /></th> --%>
								<th width="30%"><spring:message
										code="Collection.master.route.name" text="Beat Name" /></th>
								<th width="30%"><spring:message code=""
										text="Beat Start Address" /></th>
								<%-- <th width=""><spring:message code="Collection.master.latitude"
										text="Latitude" /></th>
								<th width=""><spring:message code="Collection.master.longitude"
										text="Longitude" /></th> --%>
								<th width="30%"><spring:message
										code="collection.route.beat.address" text="Beat End Address" /></th>
								<%-- <th width=""><spring:message code="Collection.master.latitude"
										text="Latitude" /></th>
								<th width=""><spring:message code="Collection.master.longitude"
										text="Longitude" /></th> --%>
								<th width="10%"><spring:message
										code="Collection.master.collection.type"
										text="Collection Type" /></th>
								<th width=""><spring:message code="Collection.master.rfid"
										text="RFID/No" /></th>
								<th width=""><spring:message code=""
										text="Assumed Quantity.(Dry)" /></th>
								<th width=""><spring:message code=""
										text="Assumed Quantity.(Wet)" /></th>
								<th width=""><spring:message code=""
										text="Assumed Quantity.(Hazardius)" /></th>
								<th colspan=""><a href="javascript:void(0);" title="Add"
									class="addCF btn btn-success btn-sm unit" id="addUnitRow"><i
										class="fa fa-plus-circle"></i></a></th>
							</tr>
						</thead>
						<tbody>
							<tr class="firstUnitRow">
								<%-- <td width="2%"><form:input
										path="routeMasterDTO.tbSwRootDets[0].roSeqNo"
										class="form-control" id="seqno"></form:input></td> --%>
								<td width=""><form:input
										path="routeMasterDTO.tbSwRootDets[0].roCollPointname"
										class="form-control" value="" id="collpoint"></form:input></td>

								<td width=""><div class="input-group">
										<form:input
											path="routeMasterDTO.tbSwRootDets[0].roCollPointadd"
											class="form-control" value="" id="colladd"
											onblur="getLatLong(0)"></form:input>
										<span class="input-group-addon"> <strong
											class="fa fa-globe"><span class="hide"></span></strong>
										</span>
									</div></td>
								<%-- 	<td width=""><form:input
										path="routeMasterDTO.tbSwRootDets[0].roCollLatitude"
										class="form-control" value="" id="collLatitude"></form:input></td>
								<td width=""><form:input
										path="routeMasterDTO.tbSwRootDets[0].roCollLongitude"
										class="form-control" id="collLongitude"></form:input></td> --%>

								<td width=""><div class="input-group">
										<form:input path="" class="form-control" value="" id="colladd"
											onblur="getLatLong(0)"></form:input>
										<span class="input-group-addon"> <strong
											class="fa fa-globe"><span class="hide"></span></strong>
										</span>
									</div></td>
								<%-- 	<td width=""><form:input path="" class="form-control" value=""
										id="collLatitude"></form:input></td>
								<td width=""><form:input path="" class="form-control"
										id="collLongitude"></form:input></td> --%>

								<td width=""><c:set var="baseLookupCode" value="COT" /> <apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="routeMasterDTO.tbSwRootDets[0].roCollType"
										cssClass="form-control required-control" isMandatory="true"
										selectOptionLabelCode="selectdropdown" hasId="true"
										hasTableForm="true" /></td>
								<td width=""><form:input
										path="routeMasterDTO.tbSwRootDets[0].referenceNo"
										class="form-control" value="" id="referenceid"></form:input></td>
								<td width=""><form:input
										path="routeMasterDTO.tbSwRootDets[0].roAssumQuantity"
										class="form-control" id="assumedQty"></form:input></td>
								<td width=""><form:input
										path="routeMasterDTO.tbSwRootDets[0].roAssumQuantity"
										class="form-control" id="assumedQty"></form:input></td>
								<td width=""><form:input
										path="routeMasterDTO.tbSwRootDets[0].roAssumQuantity"
										class="form-control" id="assumedQty"></form:input></td>
								<td class="text-center" width=""><form:checkbox
										path="routeMasterDTO.tbSwRootDets[0].roCollActive" value="Y"
										checked="true" /></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="text-center padding-top-10">
					<%-- <button type="submit" class="btn btn-green-3 pull-left">
						<i class="fa fa-file-text-o"></i>
						<spring:message code="Collection.master.Import.From.Excel"
							text="Import From Excel" />
					</button> --%>
					<div class="text-center padding-top-10">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveCollectionMasterForm(this);">
							<spring:message code="solid.waste.submit" text="Submit"></spring:message>
						</button>
						<button type="Reset" class="btn btn-warning">
							<spring:message code="solid.waste.reset" text="Reset"></spring:message>
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