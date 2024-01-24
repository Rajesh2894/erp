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
	src="js/solid_waste_management/populationMaster.js"></script>
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
				<spring:message code="population.master.heading"
					text="Population Master"></spring:message>
			</h2>
			<apptags:helpDoc url="PopulationMaster.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="PopulationMaster.html" name="PopulationMaster"
				id="PopulationMasterList" class="form-horizontal">
				<spring:message code="" text="No Record Found" var="noRecord" />
				<form:hidden path="populationMasterDTO.searchMessage" value=""
					id="Existdata" />
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
					<label class="control-label col-sm-2 " for="Year"><spring:message
							code="population.master.year" text="Select Year"></spring:message></label>

					<c:set var="baseLookupCode" value="CYR" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="populationMasterDTO.popYear"
						cssClass="form-control required-control" isMandatory="false"
						selectOptionLabelCode="selectdropdown" hasId="true" />
				</div>			
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search" title='<spring:message code="solid.waste.search" text="Search" />'
						onclick="searchPopulationMaster(this);">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" title='<spring:message code="solid.waste.reset" text="Reset" />'
						onclick="resetPopulation();">
						<spring:message code="solid.waste.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add" title='<spring:message code="population.master.add.population" text="Add" />'
						onclick="addpopulationForm('PopulationMaster.html','AddPopulationMaster');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="population.master.add.population" text="Add" />
					</button>
					<button type="button" class="btn btn-success add" title='<spring:message code="population.master.excel.upload" text="Excel Upload" />'
						onclick="addpopulationForm('PopulationMaster.html','excelUploadMaster');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="population.master.excel.upload" text="Excel Upload" />
					</button>
				</div>
				<div class="table-responsive clear">
					<table summary="Dumping Ground Data"
						class="table table-bordered table-striped pmId">
						<thead>
							<tr>
								<th><spring:message code="population.master.srno"
										text="Sr.No."></spring:message></th>
								<th><spring:message code="population.master.census.year" text="Census Year"></spring:message></th>							
								<th><spring:message code="population.master.total.population" text="Total Population"></spring:message></th>								
								<th><spring:message code="population.master.action"	text="Action"></spring:message></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.populationSet}" var="data"	varStatus="index">
								<tr>
									<td class="text-center" width="10%">${index.count}</td>
									<td width="39%" class="text-center" ><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getCPDDescription(data.popYear,'')" /></td>

										<td  width="39%" class="text-center">${data.totalPopulation}</td>
							
										<td style="width: 12%" class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												onClick="getViewPopulationmasterData(${data.popYear})"
												data-original-title="View" title="View">
												<strong class="fa fa-eye"></strong><span class="hide"><spring:message
														code="solid.waste.view" text="View"></spring:message></span>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												onClick="getPopulationmasterData(${data.popYear})"
												data-original-title="Edit" title="Edit">
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
