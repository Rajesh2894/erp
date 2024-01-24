<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/vehicle_management/PumpMaster.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="fueling.pump.master.summary"
					text="Fuel Pump Station Summary" />
			</h2>
			<apptags:helpDoc url="RefuellingPumpStationMaster.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="fuelPumpMas.html"
				name="RefuellingPumpStationMaster"
				id="RefuellingPumpStationMasterList" class="form-horizontal">
				<c:set var="d" value="0" scope="page"></c:set>
				<div class="form-group">
					<label class="control-label col-sm-2" for="PumpType"> <spring:message
							code="refueling.pump.master.type" text="Pump Type" />
					</label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<div class='input-group-field'>
								<form:select path="pumpMasterDTO.puPutype"
									class=" mandColorClass form-control "
									cssClass="form-control required-control" label="Select"
									id="puPutype">
									<form:option value="">
										<spring:message code="solid.waste.select" text="Select" />
									</form:option>
									<c:forEach items="${command.getLevelData('PMP')}" var="lookup">
										<form:option value="${lookup.lookUpId}"
											code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>
					<label class="control-label col-sm-2" for="PumpName"> <spring:message
							code="refueling.pump.master.name" text="Pump Name" />
					</label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input type='text' path="pumpMasterDTO.puPumpname"
								class=' form-control ' id="puPumpname" />
						</div>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2"
						onclick="searchPumpMaster('fuelPumpMas.html', 'search');"
						title="<spring:message code="fueling.pump.master.Search" text="Search" />">
						<i class="fa fa-search"></i>
						<spring:message code="fueling.pump.master.Search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning"
						onclick="resetRefuellingPumpStationMaster();"
						title="<spring:message code="solid.waste.reset" text="Reset"></spring:message>">
						<spring:message code="solid.waste.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add"
						onclick="addPumpMaster('fuelPumpMas.html','AddRefuellingPumpMaster');"
						title="<spring:message code="fueling.pump.master.add" text="Add" />">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="fueling.pump.master.add" text="Add" />
					</button>
				</div>
				<div class="table-responsive clear">
					<table summary="Dumping Ground Data"
						class="table table-bordered table-striped sm">
						<thead>
							<tr>
								<th><spring:message code="refueling.pump.master.sr.no"
										text="Sr. No." /></th>
								<th><spring:message code="refueling.pump.master.type"
										text="Pump Type" /></th>
								<th><spring:message code="refueling.pump.master.name"
										text="Pump Name" /></th>
								<th><spring:message
										code="vehicle.master.vehicle.vendor.name" text="Vendor Name" /></th>
								<th><spring:message code="disposal.site.master.adress"
										text="Address" /></th>
								<th width="100"><spring:message
										code="disposal.site.master.status" text="Status" /></th>
								<th width="100"><spring:message
										code="vehicle.maintenance.master.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.pumpMasterList}" var="data"
								varStatus="index">
								<tr>
									<td>${ d+1}</td>
									<td><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.puPutype)"
											var="lookup" />${lookup.lookUpDesc}</td>
									<td>${data.puPumpname}</td>
									<td>${data.vendorName}</td>
									<td>${data.puAddress}</td>
									<td class="text-center"><c:choose>
											<c:when test="${data.puActive eq 'Y'}">
												<a href="#" class="fa fa-check-circle fa-2x green "
													title="Active"></a>
											</c:when>
											<c:otherwise>
												<a href="#" class="fa fa-times-circle fa-2x red "
													title="InActive"></a>
											</c:otherwise>
										</c:choose>
									</td>
									<spring:message	code="vehicle.edit" text="Edit" var="edit"></spring:message>
									<spring:message code="vehicle.view" text="View" var="view"></spring:message>
									<td style="width: 15%" align="center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="getPumpmasterDataView('fuelPumpMas.html','viewRefuellingPumpMaster',${data.puId})"
											title="${view}">
											<strong class="fa fa-eye"></strong> <span class="hide">
												<spring:message code="solid.waste.view" text="View"></spring:message>
											</span>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											onClick="getPumpmasterData('fuelPumpMas.html','editRefuellingPumpMaster',${data.puId})"
											title="${edit}">
											<strong class="fa fa-pencil"></strong><span class="hide"><spring:message
													code="solid.waste.edit" text="Edit"></spring:message></span>
										</button>
									</td>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>