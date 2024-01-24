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
<script
	src="https://maps.googleapis.com/maps/api/js?libraries=places&key=AIzaSyAvvwgwayDHlTq9Ng83ouZA_HWSxbni25c"></script>

<script type="text/javascript"
	src="js/solid_waste_management/BeatMaster.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start info box -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="route.master.heading"
					text="Route and collection point master" />
			</h2>
			<apptags:helpDoc url="BeatMaster.html" />
		</div>
		<div class="widget-content padding">
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
				<div class="form-group">
					<label class="control-label col-sm-2" for="RouteNo"><spring:message
							code="Collection.beat.no" text="Beat No." /></label>
					<div class="col-sm-4">
						<form:input name="" path="BeatMasterDTO.beatNo" id="RouteNo"
							type="text" class="form-control"></form:input>
					</div>

					<label class="control-label col-sm-2" for="RouteName"><spring:message
							code="Collection.beat.name" text="Beat Name" /></label>
					<div class="col-sm-4">
						<form:input name="" path="BeatMasterDTO.beatName" id="RouteName"
							type="text" class="form-control"></form:input>
					</div>

				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search" title='<spring:message code="solid.waste.search" text="Search" />'
						onclick="searchRouteMaster(this);">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" title='<spring:message code="solid.waste.reset" text="Reset" />'
						onclick="resetRouteMaster();">
						<spring:message code="solid.waste.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add" title='<spring:message code="solid.waste.add" text="Add" />'
						onclick="addRouteForm('BeatMaster.html','addRouteMaster');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="solid.waste.add"
							text="Add"/>
					</button>

				</div>
				<div class="table-responsive">
					<table summary="Dumping Ground Data"
						class="table table-bordered table-striped rcm">
						<thead>
							<tr>
								<th><spring:message code="Collection.master.route.no"
										text="Route No." /></th>
								<th><spring:message code="Collection.master.route.name"
										text="Route Name" /></th>
								<th><spring:message code="route.master.start.point"
										text="Strating Point" /></th>
								<th><spring:message code="route.master.end.point"
										text="End Point" /></th>
								<th><spring:message code="route.master.total.distance"
										text="Total Distance" /></th>
								<th><spring:message code="route.master.disposal.site"
										text="Disposal Site" /></th>
								<th width="100"><spring:message
										code="disposal.site.master.status" text="Status" /></th>
								<th width="150"><spring:message code="solid.waste.action"
										text="Action" /></th>

							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.beatMasterList}" var="data"
								varStatus="index">
								<tr>
									<td class="text-center">${data.beatNo}</td>
									<td>${data.beatName}</td>
									<c:if test="${userSession.languageId eq 1}">
									<td>${data.beatStartPointName}</td>									
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
									<td>${data.beatStartPointNameReg}</td>	
									</c:if>
									<c:if test="${userSession.languageId eq 1}">
									<td>${data.beatEndPointName}</td>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
									<td>${data.beatEndPointNameReg}</td>
									</c:if>
									<td class="text-right">${data.beatDistance}&nbsp;<spring:message
											code="swm.km" text=" KM" /></td>
									<td>${data.deName}</td>
									<td class="text-center"><c:choose>
											<c:when test="${data.beatActive eq 'Y'}">
												<a href="#" class="fa fa-check-circle fa-2x green "
													title="Active"></a>
											</c:when>
											<c:otherwise>
												<a href="#" class="fa fa-times-circle fa-2x red "
													title="Active"></a>
											</c:otherwise>
										</c:choose></td>
									<td class="text-center">
									<button type="button" class="btn btn-blue-2 btn-sm"
												title="View"
												onclick="getRoutemasterData('BeatMaster.html','viewRouteMaster',${data.beatId})">
												<i class="fa fa-eye"></i>
											</button>
									<button type="button"
											class="btn btn-warning btn-sm"
											onClick="getRoutemasterData('BeatMaster.html','editRouteMaster',${data.beatId})"
											title="Edit">
											<strong class="fa fa-pencil"></strong><span class="hide"><spring:message
													code="solid.waste.edit" text="Edit"></spring:message></span>
										</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="form-group">
					<div class="text-center">
						<a data-toggle="collapse" href="#collapse1" class="btn btn-blue-2"
							id="viewloc" onclick="showMap2()"><i class="fa fa-map-marker"></i>
							<spring:message code="route.master.view.route"
								text="View  Routes" /></a>
					</div>
					<div class="collapse margin-top-10" id="collapse1">
						<div class="border-1 padding-5"
							style="height: 400px; width: 100%;" id="map-canvas2"></div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>



