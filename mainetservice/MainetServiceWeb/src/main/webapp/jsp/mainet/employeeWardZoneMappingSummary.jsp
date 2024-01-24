<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/validity_master/employeeWardZoneMapping.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code=""
					text="Employee Ward Zone Master" />
			</h2>
		</div>


		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message></span>
			</div>


			<form:form action="EmployeeWardZoneMapping.html"
				name="EmployeeWardZoneMapping" id="EmployeeWardZoneMapping"
				class="form-horizontal" commandName="command">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
					
					
					<div class="text-center padding-bottom-10">
					
					<button type="button" id="addMappingId" class="btn btn-blue-2"
						onclick="addWardZoneMapping('EmployeeWardZoneMapping.html','addWardZone')">
						<spring:message code="contract.label.add.contract.mapping"
							text="ADD Contract Mapping"></spring:message>
					</button>
				</div>





<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="hoardingDetailTable">
							<thead>
								<tr>
									<td><spring:message code=""
											text="Employee Name"></spring:message></td>
									<td>View/Edit</td>
								</tr>
							</thead>

							<tbody id="propertyListId">
								<c:forEach items="${command.empWardZoneMstList}"
									var="contractMapping" varStatus="count">
									<tr>
										<td>${contractMapping.empName}</td>
										
										
										
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View Advertiser Master"
												onclick="editwWardZoneMapping(${contractMapping.empId},'V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit Advertiser Master"
												onclick="viewWardZoneMapping(${contractMapping.empId},'E')">
												<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
											</button>
										</td>
										
										
										<%-- 
										<td><a href="javascript:void(0);"
											class="btn btn-blue-2 btn-sm text-center margin-left-30"
											data-original-title="View Mapping"
											onClick="viewAdhContractMapping(${contractMapping.empId},'V')"><strong
												class="fa fa-eye"></strong><span class="hide"><spring:message
														code="" text="View"></spring:message></span></a></td> --%>
											<%-- <td><a href="javascript:void(0);"
										class="btn btn-darkblue-3 text-center margin-left-30"
										onClick="printContractEstate(${contractMapping.empId})"><strong><i
												class="fa fa-print"></i></strong></a></td> --%>
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