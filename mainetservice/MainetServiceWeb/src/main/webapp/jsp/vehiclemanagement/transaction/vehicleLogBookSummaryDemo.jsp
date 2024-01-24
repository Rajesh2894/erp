<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<link rel="stylesheet" type="text/css"
	href="css/mainet/themes/jquery-ui-timepicker-addon.css" />

<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/vehicle_management/vehiclelogBook.js"></script>

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
				<strong><spring:message code="VehicleLogBookDTO.book"
						text="Vehicle Log Book" /></strong>
				<apptags:helpDoc url="vehLogBook.html"></apptags:helpDoc>
			</h2>
		</div>


		<div class="widget-content padding">
			<form:form action="vehLogBook.html" name="frmVehicleLogBook"
				id="frmVehicleLogBook" method="POST" commandName="command"
				class="form-horizontal form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label"> <spring:message
							code="vehicle.fuelling.fromDate" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="vehicleLogBookDTO.fromDate" id="fromDate"
								class="form-control mandColorClass datepicker dateValidation"
								value="" readonly="false" maxLength="10" placeholder="dd/mm/yyyy" />
							<label class="input-group-addon" for="fromDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=fromDate></label>
						</div>
					</div>
					<label class="col-sm-2 control-label"> <spring:message
							code="vehicle.fuelling.toDate" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="vehicleLogBookDTO.toDate" id="toDate"
								class="form-control mandColorClass datepicker dateValidation "
								value="" readonly="false" maxLength="10" placeholder="dd/mm/yyyy" />
							<label class="input-group-addon" for="toDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=toDate></label>
						</div>
					</div>
				</div>




				<div class="form-group">
					<label class="col-sm-2 control-label" for="vehicle"><spring:message
							code="vehicle.maintenance.regno" text="Vehicle No" /></label>
					<div class="col-sm-4">
						<form:select class=" mandColorClass form-control chosen-select-no-results"
						    id="veNo" path="vehicleLogBookDTO.veNo" data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${ListDriver}" var="vehicle">
								<form:option value="${vehicle.veName}">${vehicle.vehicleTypeDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>


				<div class="text-center clear padding-10">
					<%-- <button type="button" id="searchLogBook" class="btn btn-blue-2"
						onclick="searchLogBook()" title="Search">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="VehicleLogBookDTO.form.search" text="Search" />
					</button> --%>
					
					<%-- Defect #154109 --%>
					<button type="button" id="search" class="btn btn-blue-2"
						title='<spring:message code="vehicle.search" text="Search" />'
						onclick="SearchLogBookDetails()" title="Search">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="vehicle.search"
							text="Search" />
					</button> 

					<button type="button" id="reset"
						title='<spring:message code="vehicle.reset" text="Reset" />'
						onclick="window.location.href='vehLogBook.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="vehicle.reset" text="Reset" />
					</button>

					<button type="button" id="add" class="btn btn-blue-2"
						title='<spring:message code="vehicle.add" text="Add" />'
						onclick="openForm('vehLogBook.html','logBook')" title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="vehicle.add" text="Add" />
					</button>
                 </div>

					<!-- Table Grid Start -->

					<div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="frmVehicleLogBookTable">
							<thead>
								<tr>
									<th width="10%" align="center"><spring:message
											code="refueling.pump.master.sr.no" text="Sr.No" /></th>
									<th width="16%" align="center"><spring:message
											code="VehicleLogBookDTO.veNo" text="Vehicle Number" /></th>
									<th width="16%" align="center"><spring:message
											code="vehicle.department.driver.name" text="Driver Name" /></th>
									<th width="12%" align="center"><spring:message
											code="vehicle.logbook.out.date" text="Out Date" /></th>
									<th width="12%" align="center"><spring:message
											code="vehicle.logbook.out.time" text="Out Time" /></th>
									<th width="12%" align="center"><spring:message
											code="vehicle.logbook.in.date" text="In Date" /></th>
									<th width="12%" align="center"><spring:message
											code="vehicle.logbook.in.time" text="In Time" /></th>
									<th width="10%" align="center"><spring:message
											code="DailyIncidentRegisterDTO.form.action" text="Action" /></th>

								</tr>
							</thead>
							<tbody>
								<c:forEach items="${VehicleLogBookData}" var="book"
									varStatus="item">
									<tr>
										<td class="text-center">${item.count}</td>
										<td>${book.veName}</td>
										<td>${book.driverName}</td>
										<td><span class="hide"><fmt:formatDate pattern="yyyyMMdd" value="${book.outDate}" />
											</span> <fmt:formatDate pattern="dd/MM/yyyy" value="${book.outDate}" /></td>
										<td>${book.vehicleOutTime}</td>
										<td><span class="hide"><fmt:formatDate pattern="yyyyMMdd" value="${book.inDate}" />
											</span> <fmt:formatDate pattern="dd/MM/yyyy" value="${book.inDate}" /></td>
										<td>${book.vehicleInTime}</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="<spring:message code="vehicle.view" text="View"/>"
												onclick="modifyLogBook('${book.veID}','vehLogBook.html','viewVLB','V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="<spring:message code="vehicle.edit" text="Edit"/>"
												onclick="modifyLogBook('${book.veID}','vehLogBook.html','editVLB','E')">
												<i class="fa fa-pencil"></i>
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

