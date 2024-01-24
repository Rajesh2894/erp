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
	src="js/solid_waste_management/PublicToiletMaster.js"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?libraries=places&key=AIzaSyAvvwgwayDHlTq9Ng83ouZA_HWSxbni25c"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="public.toilet.master.heading"
					text="Public Toilet Master" />
			</h2>
			<apptags:helpDoc url="PublicToiletMaster.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="PublicToiletMaster.html" name="PublicToiletMaster"
				id="PublicToiletMasterList" class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-sm-2 " for="Census"><spring:message
							code="public.toilet.master.type" text="Toilet Type" /></label>
					<c:set var="baseLookupCode" value="TOT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="sanitationMasterDTO.sanType" cssClass="form-control"
						selectOptionLabelCode="solid.waste.select" hasId="true" />


					<apptags:input labelCode="public.toilet.master.name"
						path="sanitationMasterDTO.sanName" cssClass="hasNameClass "></apptags:input>
				</div>
				<div class="form-group">
					<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
						pathPrefix="sanitationMasterDTO.codWard"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control margin-bottom-10" showAll="false"
						columnWidth="20%" />
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2"
						onclick="searchPublicToiletMaster('PublicToiletMaster.html', 'search');">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning"
						onclick="resetPublicToiletMaster();">
						<spring:message code="solid.waste.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add"
						onclick="addPublicToiletMaster('PublicToiletMaster.html','addPublicToiletMaster');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="solid.waste.add"
							text="Add" />
					</button>
				</div>
				<div class="table-responsive clear">
					<table summary="Public Toilet Data"
						class="table table-bordered table-striped sm">
						<thead>
							<tr>
								<th><spring:message code="public.toilet.master.type"
										text="Toilet Type" /></th>
								<th><spring:message code="public.toilet.master.code"
										text="Toilet Code" /></th>
								
								<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
									showOnlyLabel="false"
									pathPrefix="sanitationMasterList[0].codWard"
									isMandatory="true" hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control required-control" showAll="false"
									hasTableForm="true" showData="false" columnWidth="10%" />
									
								<th><spring:message code="public.toilet.master.seatCount"
										text="Seat Count" /></th>
								<th width="100"><spring:message
										code="disposal.site.master.status" text="Status" /></th>
								<th width="100"><spring:message
										code="vehicle.maintenance.master.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.sanitationMasterList}" var="data"
								varStatus="index">
								<tr>
									<td><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.sanType)"
											var="lookup" />${lookup.lookUpDesc }</td>
									<td>${data.sanName}</td>
									<%-- <td><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(data.codWard1)"
											var="lookup" />${lookup.lookUpDesc }</td>
									<td><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(data.codWard2)"
											var="lookup" />${lookup.lookUpDesc }</td> --%>

									<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
										showOnlyLabel="true"
										pathPrefix="sanitationMasterList[${index.index}].codWard"
										isMandatory="true" hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control required-control" showAll="false"
										hasTableForm="true" showData="true" columnWidth="10%" disabled="false"/>
									<td align="center">${data.sanSeatCnt}</td>
									<td class="text-center"><c:choose>
											<c:when test="${data.sanActive eq 'Y'}">
												<a href="#" class="fa fa-check-circle fa-2x green "
													title="Active"></a>
											</c:when>
											<c:otherwise>
												<a href="#" class="fa fa-times-circle fa-2x red "
													title="InActive"></a>
											</c:otherwise>
										</c:choose></td>
									<td style="width: 15%" align="center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="getPublicToiletMasterView('PublicToiletMaster.html','viewPublicToiletMaster',${data.sanId})"
											title="View">
											<strong class="fa fa-eye"></strong> <span class="hide">
												<spring:message code="solid.waste.view" text="View"></spring:message>
											</span>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											onClick="getPublicToiletMaster('PublicToiletMaster.html','editPublicToiletMaster',${data.sanId})"
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
						<a data-toggle="collapse" href="#collapseExample"
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
