<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"	src="js/vehicle_management/VehicleEMployee.js"></script>
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
				<spring:message code="vehicle.employee.details"
					text="Vehicle Employee Details Form"></spring:message>
			</h2>
			<apptags:helpDoc url="vehicleEmpDetails.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="vehicleEmpDetails.html" name=""
				id="PopulationMasterList" class="form-horizontal">
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
							code="" text="Employee Name"></spring:message></label>
						<div class="col-sm-4">
						<form:select path="" class="form-control  "
							label="Select" id="">
							<form:option value="0"><spring:message code="solid.waste.select" text="select"/></form:option>
							<%-- <c:forEach items="${command.vehicleMasterList}" var="lookup">
								<form:option value="${lookup.veId}" code="${lookup.veId}">${lookup.veNo}</form:option>
							</c:forEach> --%>
						</form:select>
					</div>
			
				</div>			
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search"
						onclick="">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning"
						onclick="">
						<spring:message code="solid.waste.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add"
						onclick="addpopulationForm('PopulationMaster.html','AddPopulationMaster');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="population.master.add.population" text="Add" />
					</button>		
				</div>

			</form:form>
		</div>
	</div>
</div>
