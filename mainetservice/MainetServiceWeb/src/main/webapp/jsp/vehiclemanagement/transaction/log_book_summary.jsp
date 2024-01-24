<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<link rel="stylesheet" type="text/css"
	href="css/mainet/themes/jquery-ui-timepicker-addon.css" />

<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/vehicle_management/logBook.js"></script>

<script type="text/javascript">
		$(".Moredatepicker").timepicker({
			//s dateFormat: 'dd/mm/yy',		
			changeMonth : true,
			changeYear : true,
			minDate : '0',
		});

		$("#time").timepicker({

		});

	
</script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message
						code="VehicleLogBookDTO.book"
						text="Vehicle Log Book" /></strong>
				<apptags:helpDoc url="VehicleLogBook.html"></apptags:helpDoc>
			</h2>
		</div>


		<div class="widget-content padding">
			<form:form action="vehicleLogBookCon.html"
				name="frmVehicleLogBook" id="frmVehicleLogBook" method="POST"
				commandName="command" class="form-horizontal form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>


				<div class="form-group">
          <label class="col-sm-2 control-label"> <spring:message
			code="VehicleLogBookDTO.fromDate" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="vehicleLogBookDTO.fromDate" id="fromDate"
								class="form-control mandColorClass datepicker dateValidation"
								value="" readonly="false"
								maxLength="10" />
							<label class="input-group-addon" for="fromDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=fromDate></label>
						</div>
					</div>
				<label class="col-sm-2 control-label"> <spring:message
							code="VehicleLogBookDTO.toDate"  text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="vehicleLogBookDTO.toDate" id="toDate"
								class="form-control mandColorClass datepicker dateValidation "
								value="" readonly="false" 
								maxLength="10" />
							<label class="input-group-addon" for="toDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message 
								   code="" text="icon" /></span><input type="hidden" id=toDate></label>
						</div>
					</div>
				</div> 
				
				
				<div class="form-group">
		<%-- 		<apptags:input labelCode="VehicleLogBookDTO.veNo"
						path="vehicleLogBookDTO.veNo" 
						cssClass="hasNumber mandColorClass" />
						 --%>
						
						
					<label class="col-sm-2 control-label" for="vehicle"><spring:message
							code="vehicle.sanitary.vehicleNo"  text="Vehicle No" /></label>
					<div class="col-sm-4">
						<form:select id="veNo" path="vehicleLogBookDTO.veNo"
							cssClass="form-control" data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${ListDriver}" var="vehicle">
								<form:option value="${vehicle.veNo}">${vehicle.veNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
						
						
						
						
					<%-- 		<label class="control-label col-sm-2"
						for="fireStation"> <spring:message
							code="VehicleLogBookDTO.fireStation" text="Fire Station" /></label>
					  <c:set var="baseLookupCode" value="FSN" />
				    	<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="vehicleLogBookDTO.fireStation"
						cssClass="mandColorClass form-control" hasId="true"
					  	selectOptionLabelCode="selectdropdown" /> --%>
					  	
					  	
						</div>
                    
               

				 <div class="text-center clear padding-10">
					<button type="button" id="search" class="btn btn-blue-2"
						onclick="SearchLogBookDetails()" title="Search">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="vehicle.search"
							text="Search" />
					</button> 

					<button type="button" id="reset"
						onclick="window.location.href='vehicleLogBookCon.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="vehicle.reset"
							text="Reset" />
					</button>
					
					<input type="button"
						onclick="window.location.href='AdminHome.html'"
						class="btn btn-danger  hidden-print" value="<spring:message code="vehicle.back" text="Back"/>"> 

					<button type="button" id="add" class="btn btn-blue-2"
						onclick="openForm('vehicleLogBookCon.html','logBook')"
						title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="vehicle.add"
							text="Add" />
					</button>


					<!-- Table Grid Start -->

					<div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="logBookDataTable">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message code="audit.mgmt.srno" text="Sr.No" /></th>
									<th width="8%" align="center"><spring:message code="VehicleLogBookDTO.veNo" text="Vehicle Number" /></th>
									<th width="10%" align="center"><spring:message code="VehicleLogBookDTO.driverName" text="Driver Name" /></th>
									<th width="8%" align="center"><spring:message code="VehicleLogBookDTO.outDate" text="Out Date" /></th>
								    <th width="5%" align="center"><spring:message code="VehicleLogBookDTO.vehicleOutTime" text="Out Time" /></th>
									<th width="8%" align="center"><spring:message code="VehicleLogBookDTO.inDate" text="In Date"/></th>
								    <th width="5%" align="center"><spring:message code="VehicleLogBookDTO.vehicleInTime" text="In Time" /></th>
									<th width="5%" align="center"><spring:message code="DailyIncidentRegisterDTO.form.action" text="Action" /></th>

								</tr>
							</thead>
							<tbody>
							<c:forEach items="${VehicleLogBookData}" var="book" varStatus="item" >  
									<tr>
										<td class="text-center">${item.count}</td>
										<td>${book.veNo}</td>
										<td>${book.driverName}</td>
									<%-- 	<td><fmt:formatDate pattern="dd/MM/yyyy"
												value="${book.outDate}" /></td>
										<td>${book.vehicleOutTime}</td>
										<td><fmt:formatDate pattern="dd/MM/yyyy"
												value="${book.inDate}" /></td> --%>
										<td>${book.vehicleInTime}</td>

										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View Vehicle Log Book"
												onclick="modifyLogBook('${book.veID}','vehicleLogBookCon.html','viewVLB','V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit Vehicle Log Book"
												onclick="modifyLogBook('${book.veID}','vehicleLogBookCon.html','editVLB','E')">
												<i class="fa fa-pencil"></i>
											</button>
										</td>
									</tr>
								</c:forEach> 
							</tbody>
						</table>
					</div>

				</div> 

			</form:form>
		</div>
	</div>
</div>
























