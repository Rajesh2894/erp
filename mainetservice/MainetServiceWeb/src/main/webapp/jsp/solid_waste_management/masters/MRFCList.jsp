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
	src="js/solid_waste_management/MRFMaster.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?libraries=places&key=AIzaSyAvvwgwayDHlTq9Ng83ouZA_HWSxbni25c"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<style>
.text-uppercase {
	text-align: uppercase;
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="disposal.site.master.heading"
					text="Material Recovary Facility Center" />
			</h2>
			<apptags:helpDoc url="DisposalSiteMaster.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="MRFMaster.html" name="MRFMaster" id="MRFMasterId"
				class="form-horizontal">
				<div class="form-group">
					<apptags:input labelCode="swm.mrf.plantId" cssClass="text-uppercase"
						path="mRFMasterDto.mrfPlantId" isMandatory="false"></apptags:input>
					<apptags:input labelCode="swm.mrf.plantName" cssClass="hasCharacter"
						path="mRFMasterDto.mrfPlantName" isMandatory="false"></apptags:input>
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search" title='<spring:message code="solid.waste.search" text="Search" />'
						onclick="searchMRF('MRFMaster.html','search');">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" onclick="resetMRF();" title='<spring:message code="solid.waste.reset" text="Reset" />'>
						<spring:message code="solid.waste.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add" title='<spring:message code="disposal.site.master.add.new.collection" text="Add New Collection Point" />'
						onclick="addRoutAndCollection('MRFMaster.html','Add');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="disposal.site.master.add.new.collection"
							text="Add New Collection Point" />
					</button>
				</div>
				<div class="table-responsive">
					<table summary="Dumping Ground Data"
						class="table table-bordered table-striped dpsM" id="dpsM">
						<thead>
							<tr>
								<th width="100"><spring:message code="swm.mrf.plantId" text="Plant ID" /></th>
								<th width="230"><spring:message code="swm.mrf.plantName" text="Plant Name" /></th>
								<th width="200"><spring:message code="swm.mrf.plantCat"
										text="Plant Category" /></th>
								<th width="200"><spring:message code="swm.mrf.dateOfOpr" text="Date Of Open" /></th>
								<th><spring:message code="swm.mrf.decentralised" text="DeCentralized" /></th>
								<th width="50"><spring:message code="swm.mrf.ownership" text="Ownership" /></th>
								<th width="300"><spring:message code="route.master.location" text="Location" /></th>
								<th width="200"><spring:message code="swm.mrf.plantCap"
										text="Plant Capacity" /></th>
								<th width="100"><spring:message code="solid.waste.action"
										text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.mRFMasterDtoList}" var="data"
								varStatus="index">
								<tr>
									<td class="text-center">${data.mrfPlantId}</td>
									<td class="text-center">${data.mrfPlantName}</td>
									<td><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getCPDDescription(data.mrfCategory,'')" /></td>
									<td align="center"><fmt:formatDate pattern="dd/MM/yyyy"
											value="${data.mrfDateOpen}" /></td>
									<c:if test="${data.mrfDecentralised eq 'C'}">
										<td width="150">Centralized</td>
									</c:if>
									<c:if test="${data.mrfDecentralised eq 'D'}">
										<td width="150">DeCentralized</td>
									</c:if>
									<c:if test="${data.mrfOwnerShip eq 'U'}">
										<td width="150">ULB</td>
									</c:if>
									<c:if test="${data.mrfOwnerShip eq 'O'}">
										<td width="150">OutSourcing</td>
									</c:if>
									<td class="text-center">${data.locAddress}</td>
									<td>${data.mrfPlantCap}&nbsp; TPD</td>
									<td style="width: 12%" class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="getViewData('MRFMaster.html','viewMrfCenter',${data.mrfId})"
											title="View">
											<strong class="fa fa-eye"></strong><span class="hide"><spring:message
													code="solid.waste.view" text="View"></spring:message></span>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											onClick="getEditData('MRFMaster.html','editMrfCenter',${data.mrfId})"
											title="Edit">
											<strong class="fa fa-pencil"></strong><span class="hide"><spring:message
													code="solid.waste.edit" text="Edit"></spring:message></span>
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="form-group">
					<div class="text-center">
						<a data-toggle="collapse" href="#collapseExample" title='<spring:message code="disposal.site.master.view.location" text="View Location" />'
							class="btn btn-blue-2" id="viewloc" onclick="showMap()"><i
							class="fa fa-map-marker"></i> <spring:message
								code="disposal.site.master.view.location" text="View  Location" /></a>
					</div>
					<div class="collapse margin-top-10" id="collapseExample">
						<div class="border-1 padding-5"
							style="height: 400px; width: 100%;" id="map-canvas"></div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>