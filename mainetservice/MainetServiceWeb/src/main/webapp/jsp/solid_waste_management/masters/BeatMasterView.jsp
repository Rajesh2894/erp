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
<script type="text/javascript"
	src="js/solid_waste_management/BeatMaster.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<style>
.input-group-field {
	display: table-cell;
	width: 114px;
}
</style>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="route.master.route.maste" text="Route Master" />
			</h2>

		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="population.master.mand"
						text="Field with" /><i class="text-red-1">*</i> <spring:message
						code="population.master.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="BeatMaster.html"
				name="RouteAndCollectionPointMaster"
				id="RouteAndCollectionPointMasterId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="route.master.heading" text="Beat Master" />
								</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="RouteNo"><spring:message
											code="Collection.beat.no" text="Beat No." /></label>
									<div class="col-sm-4">
										<form:input path="BeatMasterDTO.beatNo" readonly="true"
											class="form-control mandColorClass hasNumber" id="RouteNo"></form:input>
									</div>
									<label class="control-label col-sm-2 required-control"
										for="RouteName"><spring:message
											code="Collection.beat.name" text="Beat Name" /></label>
									<div class="col-sm-4">
										<form:input name="" path="BeatMasterDTO.beatName" type="text"
											readonly="true" class="form-control mandColorClass"
											id="RouteName"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 " for="StartingPoint"><spring:message
											code="route.master.start.point" text="Starting Point" /></label>
									<div class="col-sm-4">
										<form:select path="BeatMasterDTO.beatStartPoint"
											disabled="true"
											cssClass="form-control chosen-select-no-results "
											id="startPoint" data-rule-required="true">
											<form:option value="">
												<spring:message code='master.selectDropDwn' />
											</form:option>
												<c:if test="${userSession.languageId eq 1}">
											<c:forEach items="${command.locList}" var="lookUp">
												<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
											</c:forEach>
											</c:if>
												<c:if test="${userSession.languageId eq 2}">
											<c:forEach items="${command.locList}" var="lookUp">
												<form:option value="${lookUp.locId}" code="">${lookUp.locNameReg}</form:option>
											</c:forEach>
											</c:if>
										</form:select>
									</div>
									<label class="control-label col-sm-2 " for="EndPoint"><spring:message
											code="route.master.end.point" text="End Point" /></label>
									<div class="col-sm-4">
										<form:select path="BeatMasterDTO.beatEndPoint" disabled="true"
											cssClass="form-control chosen-select-no-results "
											id="endPoint" data-rule-required="true">
											<form:option value="">
												<spring:message code='master.selectDropDwn' />
											</form:option>
												<c:if test="${userSession.languageId eq 1}">
											<c:forEach items="${command.locList}" var="lookUp">
												<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
											</c:forEach>
											</c:if>
												<c:if test="${userSession.languageId eq 2}">
											<c:forEach items="${command.locList}" var="lookUp">
												<form:option value="${lookUp.locId}" code="">${lookUp.locNameReg}</form:option>
											</c:forEach>
											</c:if>
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<label for="text-1514452710761" class="col-sm-2 control-label "><spring:message
											code="route.master.total.distance" text="Total Distance " /></label>
									<div class="col-sm-4">
										<div class="input-group col-sm-12 ">
											<form:input type='text' path="BeatMasterDTO.beatDistance"
												disabled="true" name="" id="totalDistanceId"
												cssClass='form-control  text-right decimal'></form:input>
											<span class="input-group-addon"><spring:message
													code="swm.kilometer" text="KiloMeter" /></span>
										</div>
									</div>
									<label class="control-label col-sm-2 " for="VehicleType"><spring:message
											code="route.master.coll.mode" text="Collection Mode" /></label>
									<c:set var="baseLookupCode" value="VCH" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										disabled="true" path="BeatMasterDTO.beatVeType"
										cssClass="form-control " isMandatory="true" hasId="true"
										selectOptionLabelCode="selectdropdown" />
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="NearestDisposalSite"><spring:message
											code="route.master.nearest.Disposal.site"
											text="Nearest Disposal Site" /></label>

									<div class="col-sm-4">
										<form:select path="BeatMasterDTO.mrfId"
											cssClass="form-control chosen-select-no-results mandColorClass"
											disabled="true" id="nrDisName" data-rule-required="true">
											<form:option value="">
												<spring:message code='master.selectDropDwn' />
											</form:option>
											<c:forEach items="${command.mrfMasterList}" var="lookUp">
												<form:option value="${lookUp.mrfId}" code="">${lookUp.mrfPlantName}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<label class="control-label col-sm-2 "
										for="DistanceFromDisposalSite"><spring:message
											code="route.master.distance.diposal.site"
											text="Distance From Disposal Site" /></label>
									<div class="col-sm-4">
										<div class="input-group col-sm-12 ">
											<form:input type="text" path="BeatMasterDTO.beatDistDes"
												disabled="true" cssClass='form-control  text-right decimal'
												id="DistancesiteId"></form:input>
											<span class="input-group-addon"><spring:message
													code="swm.kilometer" text="KiloMeter" /></span>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 " for="beatAnimalCount"><spring:message
											code="swm.beat.animal.count" text="Beat Animal Count" /></label>
									<div class="col-sm-4">
										<form:input path="BeatMasterDTO.animalCount"
											class="form-control mandColorClass hasNumber"
											id="animalCount" readonly="true"></form:input>
									</div>
									<label class="control-label col-sm-2 " for="beatIndCount"><spring:message
											code="swm.beat.compost"
											text="Estimated No/Establishment preparing compost" /></label>
									<div class="col-sm-4">
										<form:input name="" path="BeatMasterDTO.decompHouse"
											type="text" class="form-control mandColorClass hasNumber"
											id="decompHouse" readonly="true"></form:input>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2" for="population"><spring:message
											code="" text="Beat Population" /></label>
									<div class="col-sm-4">
										<form:input path="BeatMasterDTO.beatPopulation"
											class="form-control mandColorClass hasNumber"
											id="beatPopulation" readonly="true"></form:input>
									</div>
									<label class="control-label col-sm-2" for="beatResCount"><spring:message
											code="swm.beat.res.count"
											text="Estimated Beat Residencial Count" /></label>
									<div class="col-sm-4">
										<form:input name="" path="BeatMasterDTO.beatResCount"
											type="text" class="form-control mandColorClass hasNumber"
											id="beatResCount" readonly="true"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2" for="beatComCount"><spring:message
											code="swm.beat.com.count"
											text="Estimated Beat Commercial Count" /></label>
									<div class="col-sm-4">
										<form:input path="BeatMasterDTO.beatComCount"
											class="form-control mandColorClass hasNumber"
											id="beatComCount" readonly="true"></form:input>
									</div>
									<label class="control-label col-sm-2" for="beatIndCount"><spring:message
											code="swm.beat.ind.count"
											text="Estimated Beat Industrial Count" /></label>
									<div class="col-sm-4">
										<form:input name="" path="BeatMasterDTO.beatIndCount"
											type="text" class="form-control mandColorClass hasNumber"
											id="beatIndCount" readonly="true"></form:input>
									</div>
								</div>
								<div class="form-group">

									<label class="col-sm-2 control-label" for=""><spring:message
											code="employee.verification.status" text="Status" /> </label>
									<div class="col-sm-4">
										<label class="radio-inline "> <form:radiobutton
												id="status1" path="BeatMasterDTO.beatActive" value="Y"
												disabled="true" checked="checked" /> <spring:message
												code="swm.Active" text="Active" />
										</label> <label class="radio-inline "> <form:radiobutton
												id="status2" path="BeatMasterDTO.beatActive" disabled="true"
												value="N" /> <spring:message code="swm.Inactive"
												text="Inactive" />
										</label>
									</div>
								</div>


							</div>
						</div>
					</div>
				</div>
				
				
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
				<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"><spring:message
										code="swm.beat.area.details" /></a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="table-responsive">
								<c:set var="d" value="0" scope="page" />
							<table id="areaDetailTable" class="table table-striped table-bordered appendableClass wasteDetails">
								<thead>
									<tr>
										<th width="100"><spring:message code="area.details.id"></spring:message></th>
												<th><spring:message code="swm.beat.areatype"></spring:message><i
													class="text-red-1">*</i></th>
												<th><spring:message code="swm.beat.areaname"></spring:message><i
													class="text-red-1">*</i></th>
												<th><spring:message code="swm.beat.household.count"></spring:message></th>
												<th><spring:message code="swm.beat.shop.count"></spring:message></th>
												<th><spring:message code="swm.beat.total"></spring:message></th>
												<th scope="col" width="8%">
												<spring:message code="solid.waste.action" text="Action" /></th>	
									</tr>
								</thead>
								<tfoot>
									<tr>
									
								<th colspan="3" class="text-center padding-top-10">Total</th>
								<td><form:input name=""  class="form-control text-right" type="text" value="" readonly="true" id="totalHouseHoldCount" path=""/></td>
								<td><form:input name="" class="form-control text-right" type="text" value="" readonly="true" id="totalShopCount" path=""/></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
								</tfoot>
								<tbody>
								<c:forEach items="${command.beatDetailListDto}" var="data"
										varStatus="index">
									<tr class="firstAreaRow">
										<td align="center" width="10%"><form:input path="" cssClass="form-control mandColorClass "
												id="sequence${d}" value="${d+1}" disabled="true" /></td>
										
											
											
											 <td><c:set var="baseLookupCode" value="ARE" /> 
										<form:select path="beatMasterDTO.tbSwBeatDetail[${d}].beatAreaType" 
											class="form-control"   id="beatAreaType${d}" disabled="true" >
											<form:option value="0">Select</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
												<form:option value="${lookUp.lookUpId}" >${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>
										
										<td><form:input type="text" path="beatMasterDTO.tbSwBeatDetail[${d}].beatAreaName"
												class="form-control" id="beatAreaName${d}" disabled="true"></form:input></td>
												<td><form:input type="text" path="beatMasterDTO.tbSwBeatDetail[${d}].beatHouseHold"
												class="form-control text-right" id="beatHouseHold${d}" disabled="true" onchange="houseHoldCount()"></form:input></td>
												<td><form:input type="text" path="beatMasterDTO.tbSwBeatDetail[${d}].beatShop"
												class="form-control text-right" id="beatShop${d}" disabled="true" onchange="houseHoldCount()"></form:input></td>
												<td><form:input type="text" path=""
												class="form-control text-right" id="grandCount${d}" readonly="true"></form:input></td>
												
										<td class="text-center">
										<a href="javascript:void(0);" data-toggle="tooltip" title="Add"
											data-placement="top" onclick="addEntryData('areaDetailTable');" disabled="true"
											class=" btn btn-success btn-sm"><i
												class="fa fa-plus-circle" ></i></a>
										<a href="#" data-toggle="tooltip" data-placement="top" class="btn btn-danger btn-sm"
											onclick="deleteEntry($(this),'removedIds');" disabled="true"> <strong class="fa fa-trash"></strong> <span class="hide"><spring:message
														code="solid.waste.delete" text="Delete" /></span>
										</a></td>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
									</c:forEach>
								</tbody>
							</table>
									</div>
								</div>
							</div>
						</div>
						</div>
				
				<div class="text-center padding-top-10">

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backRouteMasterForm();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
				<div class="form-group">
					<div class=" padding-top-10">
						<label class=" col-sm-8 text-red text-bold text-small" for=""><spring:message
								code="swm.note"
								text="Please Note: Start Location and End Location must have latitude and longitude define in location master" /></label>

					</div>
				</div>

			</form:form>
		</div>
	</div>
</div>