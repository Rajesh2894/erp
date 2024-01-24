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

<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="swm.sanitationstafftrgt"
						text="Sanitation Staff Target" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand" /><i
					class="text-red-1">* </i> <spring:message
						code="solid.waste.mand.field" /> </span>
			</div>
			<!-- End mand-label -->


			<!-- Start Form -->
			<form:form action="VehicleTarget.html" commandName="command"
				class="form-horizontal form" name="Segregation Form"
				id="id_sanitationForm">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- End Validation include tag -->
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="form-group">

					<c:choose>
						<c:when
							test="${command.saveMode eq 'E' || command.saveMode eq 'V'}">
							<apptags:input labelCode="swm.targetfrmDt"
								cssClass="fromDateClass fromDate"
								path="sanitationStaffTargetDto.sanTgfromdt" isMandatory="true"
								isReadonly="true" isDisabled="true" />
							<apptags:input labelCode="swm.targettoDt"
								cssClass="toDateClass toDate"
								path="sanitationStaffTargetDto.sanTgtodt" isMandatory="true"
								isReadonly="true" isDisabled="true" />
						</c:when>
						<c:otherwise>
							<apptags:input labelCode="swm.targetfrmDt" isReadonly="false"
								cssClass="fromDateClass fromDate"
								path="sanitationStaffTargetDto.sanTgfromdt" isMandatory="true" placeholder="dd/mm/yyyy" maxlegnth="10" />
							<apptags:input labelCode="swm.targettoDt" isReadonly="false"
								cssClass="toDateClass toDate"
								path="sanitationStaffTargetDto.sanTgtodt" isMandatory="true" placeholder="dd/mm/yyyy" maxlegnth="10" />

						</c:otherwise>
					</c:choose>
				</div>
				<div class="table-responsive margin-top-10">
					<form:hidden path="sanitationStaffTargetDto.total" id="id_total" />

					<c:set var="d" value="0" scope="page"></c:set>
					<table class="table table-striped table-condensed table-bordered"
						id="id_segregationformTable3">
						<thead>
							<tr>
								<th scope="col" width="5%"><spring:message
										code="public.toilet.master.srno" text="Sr.No." /></th>
								<th width="15%"><spring:message code="swm.vehiclenumber"
										text="Vehicle Number" /><i class="text-red-1">* </i></th>

								<th><spring:message code="swm.route" text="Route" /><i
									class="text-red-1">* </i></th>

								<th width="20%"><spring:message code="swm.grbgVol"
										text="Garbage Volume" /><i class="text-red-1">* </i></th>
								<c:if test="${command.saveMode eq 'C'}">		
									<th scope="col" width="8%"><spring:message code="solid.waste.action" text="Action" /></th>
								</c:if>
							</tr>
						</thead>
						<tbody>

							<c:choose>

								<c:when
									test="${command.saveMode eq 'E' || command.saveMode eq 'V'}">
									<tr>
										<td align="center" width="5%"><form:input path="srNo"
												cssClass="form-control mandColorClass " id="sequence${d}"
												value="${d+1}" readonly="true" /></td>
										<td align="center"><form:select
												path="sanitationStaffTargetDET.vehicleId"
												cssClass="form-control mandColorClass" id="empid${d}"
												onchange=""
												disabled="${command.saveMode eq 'V' ? true : false }"
												data-rule-required="true">
												<form:option value="">
													<spring:message code="solid.waste.select" />
												</form:option>
												<c:forEach items="${command.vehicleMasterList}" var="lookup">
													<form:option value="${lookup.veId}">${lookup.veNo}</form:option>
												</c:forEach>
											</form:select></td>
										<td align="center"><form:select
												path="sanitationStaffTargetDET.roId"
												cssClass="form-control mandColorClass" id="roId${d}"
												onchange=""
												disabled="${command.saveMode eq 'V' ? true : false }"
												data-rule-required="true">
												<form:option value="">
													<spring:message code="solid.waste.select" />
												</form:option>
												<c:forEach items="${command.routelist}" var="lookup">
													<form:option value="${lookup.beatId}">${lookup.beatNo }    ${ lookup.beatName}</form:option>
												</c:forEach>
											</form:select></td>
										<td align="right">
											<div class="input-group">
												<form:input path="sanitationStaffTargetDET.sandVolume"
													cssClass="form-control  mandColorClass hasDecimal text-right"
													onchange="" id="sandVolume${d }"
													disabled="${command.saveMode eq 'V' ? true : false }" />
												<span class="input-group-addon"><spring:message
														code="swm.kgs" text="Kilograms" /></span>
											</div>
										</td>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:when>
								<c:otherwise>
									<tr>
										<td align="center" width="5%"><form:input path="srNo"
												cssClass="form-control mandColorClass " id="sequence${d}"
												value="${d+1}" readonly="true" /></td>
										<td align="center"><form:select
												path="sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].vehicleId"
												cssClass="form-control mandColorClass" id="empid${d}"
												onchange="" disabled="" data-rule-required="true">
												<form:option value=""><spring:message code="solid.waste.select" /></form:option>
												<c:forEach items="${command.vehicleMasterList}" var="lookup">
													<form:option value="${lookup.veId}">${lookup.veNo}</form:option>
												</c:forEach>
											</form:select></td>
										<td align="center"><form:select
												path="sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].roId"
												cssClass="form-control mandColorClass" id="roId${d}"
												onchange="" disabled="" data-rule-required="true">
												<form:option value=""><spring:message code="solid.waste.select" /></form:option>
												<c:forEach items="${command.routelist}" var="lookup">
													<form:option value="${lookup.beatId}">${lookup.beatNo }   ${ lookup.beatName}</form:option>
												</c:forEach>
											</form:select></td>
										<td align="right">
											<div class="input-group">
												<form:input
													path="sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].sandVolume"
													cssClass="form-control  mandColorClass hasDecimal text-right"
													onchange="" id="sandVolume${d }" disabled="" />
												<span class="input-group-addon"><spring:message
														code="swm.kgs" text="Kilograms" /></span>
											</div>
										</td>
										<td class="text-center" width="8%">
										<c:if test="${command.saveMode eq 'C'}">
											<a href="javascript:void(0);"
												data-toggle="tooltip" data-placement="top" title="Add"
												onclick="addEntryData('id_segregationformTable3');"
												class=" btn btn-success btn-sm"><i
												class="fa fa-plus-circle"></i></a>
										</c:if>
										<a class="btn btn-danger btn-sm delButton" title="Delete"
											onclick="deleteEntry('id_segregationformTable3',$(this),'sequence${d}')">
												<i class="fa fa-minus"></i>
										</a></td>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />

								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="Proceed(this)" id="btnSave">
							<spring:message code="solid.waste.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<button type="button" class="btn btn-warning"
							onclick="ResetForm(this)" id="btnReset">
							<spring:message code="solid.waste.reset" text="Reset" />
						</button>
					</c:if>
					<apptags:backButton url="VehicleTarget.html"></apptags:backButton>
				</div>
				<!-- End button -->
			</form:form>
		</div>
	</div>
</div>





