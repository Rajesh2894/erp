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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/DisposalSiteMaster.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
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
				<spring:message code="disposal.site.master.heading"
					text="Material Recovary Facility Center" />
			</h2>
			<apptags:helpDoc url="DisposalSiteMaster.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="DisposalSiteMaster.html" name="DisposalSiteMaster"
				id="DisposalSiteMasterId" class="form-horizontal">
				<div class="form-group">
					<apptags:input labelCode="Plant ID"
						cssClass="hasNumber" path="DisposalMasterDTO.deId"
						isMandatory="false"></apptags:input>
					
					<apptags:input labelCode="Plant Name"
						path="DisposalMasterDTO.deName" isMandatory="false"></apptags:input>


				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search"
						onclick="searchRoutAndCollection('DisposalSiteMaster.html','search');">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning"
						onclick="resetDisposal();">
						<spring:message code="solid.waste.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add"
						onclick="addRoutAndCollection('DisposalSiteMaster.html','Add');">
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
								<th width="100"><spring:message code="" text="Plant ID" /></th>
								<th width="230"><spring:message code="" text="Plant Name" /></th>
								<th width="200"><spring:message code=""
										text="Plant Category" /></th>
								<th width="200"><spring:message code="" text="Date Of Open" /></th>
								<th><spring:message code="" text="DeCentralized" /></th>
								<th width="300"><spring:message code="" text="Ownership" /></th>
								<th width="100"><spring:message code="" text="Location" /></th>
								<th width="100"><spring:message code=""
										text="Plant Capacity" /></th>
								<th width="100"><spring:message code="solid.waste.action"
										text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-center"></td>
								<td></td>
								<td class="text-right"></td>
								<td></td>
								<td width="150" class="text-right"></td>
								<td></td>
								<td class="text-center"></td>
								<td></td>
								<td style="width: 12%" class="text-center"></td>
							</tr>

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