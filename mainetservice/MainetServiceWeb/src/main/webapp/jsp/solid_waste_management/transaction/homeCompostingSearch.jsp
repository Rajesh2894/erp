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
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script type="text/javascript"
	src="js/solid_waste_management/homeCompostingForm.js"></script>
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
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="swm.home.composting" />
			</h2>
		</div>
		<div class="widget-content padding ">
			<div class="mand-label clearfix">
				<span><spring:message code="population.master.mand"
						text="Field with" /><i class="text-red-1">*</i> <spring:message
						code="population.master.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="HomeCompostingForm.html"
				name="homeCompostingTransaction" id="homeCompostingTransactionId"
				class="form-horizontal">
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
					<%-- <apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
										showOnlyLabel="false" pathPrefix="masterDto.swCod"
										isMandatory="true" hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control changeParameterClass" disabled="" /> --%>
					<label class="control-label col-sm-2"> <spring:message
							code="swm.home.composting.enter.name"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input path="masterDto.swName" class="form-control mandColorClass has"
							id="name" />
					</div>
					
					<label class="control-label col-sm-2"><spring:message
							code="swm.home.composting.mobile"></spring:message> </label>
					<div class="col-sm-4">
						<form:input path="masterDto.swMobile" class="form-control mandColorClass"
							id="mobileNo" />
					</div>
				</div>
					
				<div class="form-group">
					<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
						pathPrefix="masterDto.swCod"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control margin-bottom-10" showAll="false"
						columnWidth="20%" />
				</div>
				
				
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search"
						onclick="searchCitizen(this);">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-success add"
						onclick="addHomeComposting('HomeCompostingForm.html','addHomeComposting');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="solid.waste.add" text="Add" />
					</button>
				</div>

				<div class="table-responsive">
					<table summary="Home Composting Data" id="datatables"
						class="table table-bordered table-striped rcm">
						<thead>
							<tr>
								<%-- <th><spring:message code="swm.home.composting.sr.no" /></th> --%>
								<th><spring:message code="swm.home.composting.citizen.name" /></th>
								<th><spring:message code="swm.home.composting.address" /></th>
								<th width="8%"><spring:message code="swm.home.composting.mobile" /></th>
								
								<th></th>
							</tr>
						</thead>
						<tbody>
						
						<c:forEach items="${command.masterDtoList}" var="data"
								varStatus="index">
							<tr>
								<!-- <td></td> -->
								<td>${data.swName}</td>
								<td>${data.swAddress}</td>
								<td>${data.swMobile}</td>
								
								<td class="text-center" width="10%">
									<button type="button" class="btn btn-blue-2 btn-sm"
										title="View"
										onclick="getcitizenMasterData('HomeCompostingForm.html','viewCitizenMaster',${data.registrationId})">
										<i class="fa fa-eye"></i>
									</button>
									<button type="button" class="btn btn-warning btn-sm"
										onClick="getcitizenMasterData('HomeCompostingForm.html','editCitizenMaster',${data.registrationId})"
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
			</form:form>
		</div>
	</div>
</div>






