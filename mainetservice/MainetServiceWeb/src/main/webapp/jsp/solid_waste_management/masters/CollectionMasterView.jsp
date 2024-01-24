<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
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
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="BeatMaster.html"
				id="RouteAndCollectionPointMasterViewId"
				name="RouteAndCollectionPointMasterView" class="form-horizontal">
				<!--Rout Master Data -->
				<div id="receipt">
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="RouteNo"><spring:message
								code="Collection.master.route.no" text="Route No." /></label>
						<div class="col-sm-4">
							<form:input path="RouteMasterDTO.roNo"
								class="form-control mandColorClass" id="RouteNo" disabled="true"></form:input>
						</div>

						<label class="control-label col-sm-2 required-control"
							for="RouteName"><spring:message
								code="Collection.master.route.name" text="Route Name" /></label>

						<div class="col-sm-4">
							<form:input name="" path="RouteMasterDTO.roName" type="text"
								class="form-control mandColorClass" id="RouteName"
								disabled="true"></form:input>
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
								<form:option value="">
									<spring:message code='master.selectDropDwn' />
								</form:option>
								<c:forEach items="${command.locList}" var="lookUp">
									<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<label for="text-1514452710761"
							class="col-sm-2 control-label required-control"><spring:message
								code="route.master.total.distance" text="Total Distance " /></label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12 ">
								<form:input type='text' path="RouteMasterDTO.roDistance" name=""
									id="totalDistanceId" class='form-control mandColorClass'
									disabled="true"></form:input>
								<div class='input-group-field'>
									<form:select path="RouteMasterDTO.roDistanceUnit"
										class=" mandColorClass form-control mandColorClass "
										label="Select" id="distanceUnit" disabled="true">
										<c:forEach items="${command.getLevelData('UOM')}" var="lookup">
											<c:if test="${lookup.otherField eq 'LENGTH'}">
												<form:option value="${lookup.lookUpId}"
													code="${lookup.lookUpCode}">${lookup.lookUpCode}</form:option>
											</c:if>
										</c:forEach>
									</form:select>
								</div>
							</div>
						</div>
						<label class="control-label col-sm-2 required-control"
							for="VehicleType"><spring:message
								code="route.master.vehicle.type" text="Vehicle Type" /></label>
						<c:set var="baseLookupCode" value="VCH" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="RouteMasterDTO.roVeType"
							cssClass="form-control required-control" isMandatory="true"
							hasId="true" selectOptionLabelCode="selectdropdown"
							disabled="true" />
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="NearestDisposalSite"><spring:message
								code="route.master.nearest.Disposal.site"
								text="Nearest Disposal Site" /></label>
						<div class="col-sm-4">
							<form:select path="RouteMasterDTO.deId"
								cssClass="form-control  mandColorClass" id="nrDisName"
								data-rule-required="true" disabled="true">
								<c:forEach items="${command.disName}" var="lookUp">
									<form:option value="${lookUp.deId}" code="">${lookUp.deName}</form:option>
								</c:forEach>
							</form:select>
						</div>
						<label class="control-label col-sm-2 required-control"
							for="DistanceFromDisposalSite"><spring:message
								code="route.master.distance.diposal.site"
								text="Distance From Disposal Site" /></label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12 ">
								<form:input type="text" path="RouteMasterDTO.roDistDes"
									class="form-control mandColorClass" id="DistancesiteId"
									disabled="true"></form:input>
								<div class='input-group-field'>
									<form:select path="RouteMasterDTO.roDistDesUnit"
										class=" mandColorClass form-control mandColorClass "
										label="Select" id="distanceUnit" disabled="true">
										<c:forEach items="${command.getLevelData('UOM')}" var="lookup">
											<c:if test="${lookup.otherField eq 'LENGTH'}">
												<form:option value="${lookup.lookUpId}"
													code="${lookup.lookUpCode}">${lookup.lookUpCode}</form:option>
											</c:if>
										</c:forEach>
									</form:select>
								</div>

							</div>
						</div>
					</div>
					<form:hidden path="routeMasterDTO.startLattitude" id="startLat" />
					<form:hidden path="routeMasterDTO.endLattitude" id="endLat" />
					<form:hidden path="routeMasterDTO.startLongitude" id="startLong" />
					<form:hidden path="routeMasterDTO.endLongitude" id="endLong" />
					<!-- End Route Master Data -->
					<div class="clearfix padding-10"></div>
					<div class="container">
						<c:set var="d" value="0" scope="page"></c:set>
						<table class="table table-bordered  table-fixed"
							id="calculationMb">
							<thead>
								<tr>
									<th width="60"><spring:message
											code="Collection.master.seqno" text="Seq. No." /></th>
									<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
										showOnlyLabel="false"
										pathPrefix="routeMasterDTO.tbSwRootDets[0].codWard"
										isMandatory="true" hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control required-control" showAll="false"
										hasTableForm="true" showData="false" columnWidth="10%" />
									<th><spring:message
											code="ollection.master.collection.point.name"
											text="Collection Point Name" /></th>
									<th width="250"><spring:message
											code="Collection.master.detail.address" text="Detail Address" /></th>
									<th><spring:message code="Collection.master.latitude"
											text="Latitude" /></th>
									<th><spring:message code="Collection.master.longitude"
											text="Longitude" /></th>
									<th width="120"><spring:message
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
												class="form-control" id="seqno" disabled="true"></form:input></td>

										<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
											showOnlyLabel="false"
											pathPrefix="routeMasterDTO.tbSwRootDets[${index.index}].codWard"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control required-control " showAll="false"
											hasTableForm="true" showData="true" columnWidth="10%"
											disabled="true" />
										<td><form:input
												path="routeMasterDTO.tbSwRootDets[${index.index}].roCollPointname"
												class="form-control" value="" id="collpoint" disabled="true"></form:input></td>

										<td><div class="input-group">
												<form:input
													path="routeMasterDTO.tbSwRootDets[${index.index}].roCollPointadd"
													class="form-control" value="" id="colladd" disabled="true"></form:input>
												<span class="input-group-addon"> <strong
													class="fa fa-globe"><span class="hide"><spring:message
																code="Collection.master.latitude" text="Latitude" /></span></strong>
												</span>
											</div></td>
										<td><form:input
												path="routeMasterDTO.tbSwRootDets[${index.index}].roCollLatitude"
												class="form-control" id="collLatitude${index.index}"
												readonly="true"></form:input></td>
										<td><form:input
												path="routeMasterDTO.tbSwRootDets[${index.index}].roCollLongitude"
												class="form-control" id="collLongitude${index.index}"
												readonly="true"></form:input></td>
										<td><c:set var="baseLookupCode" value="COT" /> <apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="routeMasterDTO.tbSwRootDets[${index.index}].roCollType"
												cssClass="form-control required-control" isMandatory="true"
												selectOptionLabelCode="selectdropdown" hasId="true"
												hasTableForm="true" disabled="true" /></td>
										<td><form:input
												path="routeMasterDTO.tbSwRootDets[${index.index}].referenceNo"
												class="form-control" value="" id="referenceid"
												disabled="true"></form:input></td>
										<td><form:input
												path="routeMasterDTO.tbSwRootDets[${index.index}].roAssumQuantity"
												class="form-control" id="assumedQty" disabled="true"></form:input></td>
										<td class="text-center"><form:checkbox
												path="routeMasterDTO.tbSwRootDets[${index.index}].roCollActive"
												id="roCollActive${index.index}" value="Y" disabled="true" />
										</td>

									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="text-center padding-top-10">
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
					<button onClick="printdiv('receipt');"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="solid.waste.print" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backRouteMasterForm();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>