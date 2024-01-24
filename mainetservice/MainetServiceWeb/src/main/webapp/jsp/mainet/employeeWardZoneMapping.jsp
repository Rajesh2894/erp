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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
					
					
					<div class="form-group">
					
					<label class="col-sm-2 control-label"><spring:message
							code="EmployeeDesignation.employeename" text="Employee Name" /></label>

					<div class="col-sm-4">

						<form:select path="employeeWardZoneMapDto.empId" id="empId"
							class="chosen-select-no-results" data-rule-required="true" disabled="${command.saveMode eq 'V' ? true : false }">
							<form:option value="0">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.employeeList}" var="department">
								<form:option value="${department.empId}">${department.empname}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
				</div>
				
				
				
				<div class="overflow margin-top-10">
	<div class="table-responsive">
		<c:set var="d" value="0" scope="page"></c:set>
		<table class="table table-hover table-bordered table-striped"
			id="wardZoneMappingTable">
			<thead>
				<tr>
					<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
						showOnlyLabel="false"
						pathPrefix="employeeWardZoneMapDto.wardZoneDetalList[0].ward"
						isMandatory="true" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control" showAll="false"
						hasTableForm="true" showData="false" columnWidth="10%" />
												
												<c:if
									test="${command.saveMode ne 'V'}">		
														<th scope="col" width="5%"><a href="javascript:void(0);"
							class=" btn btn-blue-2 btn-sm addEntryData bluebtn"> <i
								class="fa fa-plus-circle"></i>
						</a></th>
						</c:if>
					
				</tr>
			</thead>
			<tbody>

					
					
					
					
					
					<c:choose>
					<c:when
						test="${fn:length(command.employeeWardZoneMapDto.wardZoneDetalList) > 0}">
						<c:forEach var="sorDetailsList"
							items="${command.employeeWardZoneMapDto.wardZoneDetalList}"
							varStatus="status">
							<tr class="advertisingDetailsClass">
								
								<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true" 
									pathPrefix="employeeWardZoneMapDto.wardZoneDetalList[${d}].ward"
									isMandatory="true" hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control required-control " showAll="false"
									hasTableForm="true" showData="true" columnWidth="10%" disabled="${command.saveMode eq 'V' ? true : false }"/>
								
								
								
									<c:if
									test="${command.saveMode eq 'E'}">
									<td align="center" width="3%"><a
										href="javascript:void(0);"
										class="btn btn-danger btn-sm delButton" title="Delete"
										onclick=""><i class="fa fa-minus"></i> </a></td>
								</c:if>
							<c:set var="d" value="${d + 1}" scope="page" />
							</tr>
							
						</c:forEach>

					</c:when>
					<c:otherwise>
						<tr class="advertisingDetailsClass">
						

							<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
								pathPrefix="employeeWardZoneMapDto.wardZoneDetalList[0].ward"
								isMandatory="true" hasLookupAlphaNumericSort="true"
								hasSubLookupAlphaNumericSort="true"
								cssClass="form-control required-control " showAll="false"
								hasTableForm="true" showData="true" columnWidth="10%"/>

								<td align="center" width="3%"><a href="javascript:void(0);"
									class="btn btn-danger btn-sm delButton" title="Delete"
									onclick=""><i class="fa fa-minus"></i> </a></td>
							
						</tr>
					</c:otherwise>
				</c:choose>
				
				
				
				
				
				
			</tbody>
		</table>
	</div>
</div>

<div class="text-center padding-top-20">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveEmployeeWardMapping(this)" id="submit">
							<spring:message code="solid.waste.submit" />
						</button>

						<button type="Reset" class="btn btn-warning" onclick="resetForm()">
							<spring:message code="solid.waste.reset" text="Reset" />
						</button>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="window.location.href='EmployeeWardZoneMapping.html'" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>

				</div>


			</form:form>

		</div>
	</div>
</div>