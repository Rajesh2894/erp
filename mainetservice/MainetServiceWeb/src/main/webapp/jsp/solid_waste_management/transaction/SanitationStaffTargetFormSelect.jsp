<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- End JSP Necessary Tags -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/script-library.js"></script>
<c:choose>
	<c:when
		test="${empty command.sanitationStaffTargetDto.sanitationStaffTargetDet}">
		<c:set var="loop" value="1"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="loop"
			value="${command.sanitationStaffTargetDto.sanitationStaffTargetDet}"></c:set>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${command.targetType eq '1'}">
		<!-- Segregation Wise-->
		<!-- <div class="segregation hidebox"> -->
		<div class="table-responsive margin-top-10">
			<c:set var="d" value="0" scope="page"></c:set>
			<form:hidden path="command.sanitationStaffTargetDto.total"
				id="id_total" />
			<table class="table table-striped table-condensed table-bordered"
				id="id_segregationformTable1">
				<thead>
					<tr>
						<th width="20%"><spring:message code="swm.empname" text="Employee Name" /><i
							class="text-red-1">* </i></th>
						<apptags:lookupFieldSet baseLookupCode="WTY" hasId="true"
							pathPrefix="command.sanitationStaffTargetDto.sanitationStaffTargetDet[0].codWast"
							isMandatory="true" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control" showAll="false"
							hasTableForm="true" showData="false" columnWidth="10%"
							isNotInForm="true" />
						<th width="10%"><spring:message code="swm.grbgVol" text="Garbage Volume" /><i
							class="text-red-1">* </i></th>
						<th scope="col" width="6%"><a href="javascript:void(0);"
							data-toggle="tooltip" data-placement="top" title="Add"
							onclick="addEntryData2('id_segregationformTable1');"
							class=" btn btn-success btn-sm"><i class="fa fa-plus-circle"></i></a></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${command.saveMode eq 'E'}">
							<c:forEach var="tripInfo"
								items="${command.sanitationStaffTargetDto.sanitationStaffTargetDet}"
								varStatus="status">
								<tr class="firstUnitRow">
									<td align="center" width="20%"><form:select
											path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].empid"
											cssClass="form-control mandColorClass" id="empid${d}"
											onchange="" disabled="" data-rule-required="true">
											<form:option value=""><spring:message code="solid.waste.select" /></form:option>
											<c:forEach items="${command.employeeList}" var="lookup">
												<form:option value="${lookup.empId}">${lookup.fullName}</form:option>
											</c:forEach>
										</form:select></td>
									<apptags:lookupFieldSet baseLookupCode="WTY" hasId="true"
										pathPrefix="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].codWast"
										isMandatory="true" hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" disabled=""
										cssClass="form-control required-control " showAll="false"
										hasTableForm="true" showData="true" columnWidth="10%"
										isNotInForm="true" />
									<td align="center" width="10%">
										<div class="input-group">
											<form:input
												path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].sandVolume"
												cssClass="form-control  mandColorClass hasDecimal text-center"
												onchange="" id="sandVolume${d }" disabled="" />
											<span class="input-group-addon"><spring:message
													code="swm.kgs" text="Kilograms" /></span>
										</div>
									</td>
									<td align="center" width="6%">
									<c:choose>
									<c:when test="${d>(command.sanitationStaffTargetDto.sanitationStaffTargetDet).size()}">
									<a
										class="btn btn-danger btn-sm delButton" title="Delete"
										onclick="deleteEntry2('id_segregationformTable1',$(this),'sequence${d}')">
											<i class="fa fa-minus"></i>
									</a>
									</c:when>
									<c:otherwise>
									<a
										class="btn btn-danger btn-sm delButton" title="Delete"
										onclick="deleteEntry2('id_segregationformTable1',$(this),'sequence${d}')">
											<i class="fa fa-minus"></i>
									</a>
									</c:otherwise>
									</c:choose>
									</td>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach var="tripInfo" items="${loop}" varStatus="status">
								<tr class="firstUnitRow">
									<td align="center" width="20%"><form:select
											path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].empid"
											cssClass="form-control mandColorClass" id="empid${d}"
											onchange="" disabled="" data-rule-required="true">
											<form:option value=""><spring:message code="solid.waste.select" /></form:option>
											<c:forEach items="${command.employeeList}" var="lookup">
												<form:option value="${lookup.empId}">${lookup.fullName}</form:option>
											</c:forEach>
										</form:select></td>
									<apptags:lookupFieldSet baseLookupCode="WTY" hasId="true"
										pathPrefix="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].codWast"
										isMandatory="true" hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" disabled=""
										cssClass="form-control required-control " showAll="false"
										hasTableForm="true" showData="true" columnWidth="10%"
										isNotInForm="true" />
									<td align="center" width="10%">
										<div class="input-group">
											<form:input
												path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].sandVolume"
												cssClass="form-control  mandColorClass hasDecimal text-center"
												onchange="" id="sandVolume${d }" disabled="" />
											<span class="input-group-addon"><spring:message
													code="swm.kgs" text="Kilograms" /></span>
										</div>
									</td>
									<td align="center" width="6%"><a
										class="btn btn-danger btn-sm delButton" title="Delete"
										onclick="deleteEntry2('id_segregationformTable1',$(this),'sequence${d}')">
											<i class="fa fa-minus"></i>
									</a></td>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>

						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<!-- Segregation Wise-->
	</c:when>
	<c:when test="${command.targetType eq '2'}">
		<!-- Collection Wise-->
		<!-- <div class="collection hidebox"> -->
		<div class="table-responsive margin-top-10">
			<form:hidden path="command.sanitationStaffTargetDto.total"
				id="id_total" />
			<c:set var="d" value="0" scope="page"></c:set>
			<table class="table table-striped table-condensed table-bordered"
				id="id_segregationformTable2">
				<thead>
					<tr>
						<th scope="col" width="5%"><spring:message code="public.toilet.master.srno"
								text="Sr.No." /></th>
						<th><spring:message code="swm.empname" text="Employee Name" /><i
							class="text-red-1">* </i></th>

						<th><spring:message code="swm.dsplsite" text="Disposal Site" /><i
							class="text-red-1">* </i></th>

						<th><spring:message code="swm.grbgVol" text="Garbage Volume" /><i
							class="text-red-1">* </i></th>
						<th scope="col" width="6%"><a href="javascript:void(0);"
							data-toggle="tooltip" data-placement="top" title="Add"
							onclick="addEntryData('id_segregationformTable2');"
							class=" btn btn-success btn-sm"><i class="fa fa-plus-circle"></i></a></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${command.saveMode eq 'E'}">
							<c:forEach var="tripInfo"
								items="${command.sanitationStaffTargetDto.sanitationStaffTargetDet}"
								varStatus="status">
								<tr>
									<td align="center"><form:input path="command.srNo"
											cssClass="form-control mandColorClass " id="sequence${d}"
											value="${d+1}" disabled="true" /></td>
									<td align="center"><form:select
											path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].empid"
											cssClass="form-control mandColorClass" id="empid${d}"
											onchange="" disabled="" data-rule-required="true">
											<form:option value=""><spring:message code="solid.waste.select" /></form:option>
											<c:forEach items="${command.employeeList}" var="lookup">
												<form:option value="${lookup.empId}">${lookup.fullName}</form:option>
											</c:forEach>
										</form:select></td>
									<td align="center"><form:select
											path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].deId"
											cssClass="form-control mandColorClass" id="deId${d}"
											onchange="" disabled="" data-rule-required="true">
											<form:option value=""><spring:message code="solid.waste.select" /></form:option>
											<c:forEach items="${command.disposalMasterList}" var="lookup">
												<form:option value="${lookup.deId}">${lookup.deName}</form:option>
											</c:forEach>
										</form:select></td>
									<td align="center">
										<div class="input-group">
											<form:input
												path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].sandVolume"
												cssClass="form-control  mandColorClass hasDecimal text-center"
												onchange="" id="sandVolume${d }" disabled="" />
											<span class="input-group-addon"><spring:message
													code="swm.kgs" text="Kilograms" /></span>
										</div>
									</td>
									<td align="center"><a
										class="btn btn-danger btn-sm delButton" title="Delete"
										onclick="deleteEntry('id_segregationformTable2',$(this),'sequence${d}')">
											<i class="fa fa-minus"></i>
									</a></td>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach var="tripInfo" items="${loop}" varStatus="status">
								<tr>
									<td align="center"><form:input path="command.srNo"
											cssClass="form-control mandColorClass " id="sequence${d}"
											value="${d+1}" disabled="true" /></td>
									<td align="center"><form:select
											path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].empid"
											cssClass="form-control mandColorClass" id="empid${d}"
											onchange="" disabled="" data-rule-required="true">
											<form:option value=""><spring:message code="solid.waste.select" /></form:option>
											<c:forEach items="${command.employeeList}" var="lookup">
												<form:option value="${lookup.empId}">${lookup.fullName}</form:option>
											</c:forEach>
										</form:select></td>
									<td align="center"><form:select
											path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].deId"
											cssClass="form-control mandColorClass" id="deId${d}"
											onchange="" disabled="" data-rule-required="true">
											<form:option value=""><spring:message code="solid.waste.select" /></form:option>
											<c:forEach items="${command.disposalMasterList}" var="lookup">
												<form:option value="${lookup.deId}">${lookup.deName}</form:option>
											</c:forEach>
										</form:select></td>
									<td align="center">
										<div class="input-group">
											<form:input
												path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].sandVolume"
												cssClass="form-control  mandColorClass hasDecimal text-center"
												onchange="" id="sandVolume${d }" disabled="" />
											<span class="input-group-addon"><spring:message
													code="swm.kgs" text="Kilograms" /></span>
										</div>
									</td>
									<td align="center"><a
										class="btn btn-danger btn-sm delButton" title="Delete"
										onclick="deleteEntry('id_segregationformTable2',$(this),'sequence${d}')">
											<i class="fa fa-minus"></i>
									</a></td>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<!-- Collection Wise-->
	</c:when>
	<c:when test="${command.targetType eq '3'}">
		<!-- Disposal Wise-->
		<!-- 	<div class="disposal hidebox"> -->
		<div class="table-responsive margin-top-10">
			<form:hidden path="command.sanitationStaffTargetDto.total"
				id="id_total" />
			<c:set var="d" value="0" scope="page"></c:set>
			<table class="table table-striped table-condensed table-bordered"
				id="id_segregationformTable3">
				<thead>
					<tr>
						<th scope="col" width="5%"><spring:message code="public.toilet.master.srno"
								text="Sr.No." /></th>
						<th><spring:message code="swm.empname" text="Employee Name" /><i
							class="text-red-1">* </i></th>

						<th><spring:message code="swm.route" text="Route" /><i
							class="text-red-1">* </i></th>

						<th><spring:message code="swm.grbgVol" text="Garbage Volume" /><i
							class="text-red-1">* </i></th>
						<th scope="col" width="6%"><a href="javascript:void(0);"
							data-toggle="tooltip" data-placement="top" title="Add"
							onclick="addEntryData('id_segregationformTable3');"
							class=" btn btn-success btn-sm"><i class="fa fa-plus-circle"></i></a></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${command.saveMode eq 'E'}">
							<c:forEach var="tripInfo"
								items="${command.sanitationStaffTargetDto.sanitationStaffTargetDet}"
								varStatus="status">
								<tr>
									<td align="center"><form:input path="command.srNo"
											cssClass="form-control mandColorClass " id="sequence${d}"
											value="${d+1}" readonly="true" /></td>
									<td align="center"><form:select
											path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].empid"
											cssClass="form-control mandColorClass" id="empid${d}"
											onchange="" disabled="" data-rule-required="true">
											<form:option value=""><spring:message code="solid.waste.select" /></form:option>
											<c:forEach items="${command.employeeList}" var="lookup">
												<form:option value="${lookup.empId}">${lookup.fullName}</form:option>
											</c:forEach>
										</form:select></td>
									<td align="center"><form:select
											path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].roId"
											cssClass="form-control mandColorClass" id="roId${d}"
											onchange="" disabled="" data-rule-required="true">
											<form:option value=""><spring:message code="solid.waste.select" /></form:option>
											<c:forEach items="${command.routelist}" var="lookup">
												<form:option value="${lookup.roId}">${lookup.roNo }    ${ lookup.roName}</form:option>
											</c:forEach>
										</form:select></td>
									<td align="center">
										<div class="input-group">
											<form:input
												path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].sandVolume"
												cssClass="form-control  mandColorClass hasDecimal text-center"
												onchange="" id="sandVolume${d }" disabled="" />
											<span class="input-group-addon"><spring:message
													code="swm.kgs" text="Kilograms" /></span>
										</div>
									</td>
									<td align="center"><a
										class="btn btn-danger btn-sm delButton" title="Delete"
										onclick="deleteEntry('id_segregationformTable3',$(this),'sequence${d}')">
											<i class="fa fa-minus"></i>
									</a></td>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach var="tripInfo" items="${loop}" varStatus="status">
								<tr>
									<td align="center"><form:input path="command.srNo"
											cssClass="form-control mandColorClass " id="sequence${d}"
											value="${d+1}" readonly="true" /></td>
									<td align="center"><form:select
											path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].empid"
											cssClass="form-control mandColorClass" id="empid${d}"
											onchange="" disabled="" data-rule-required="true">
											<form:option value=""><spring:message code="solid.waste.select" /></form:option>
											<c:forEach items="${command.employeeList}" var="lookup">
												<form:option value="${lookup.empId}">${lookup.fullName}</form:option>
											</c:forEach>
										</form:select></td>
									<td align="center"><form:select
											path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].roId"
											cssClass="form-control mandColorClass" id="roId${d}"
											onchange="" disabled="" data-rule-required="true">
											<form:option value=""><spring:message code="solid.waste.select" /></form:option>
											<c:forEach items="${command.routelist}" var="lookup">
												<form:option value="${lookup.roId}">${lookup.roNo }   ${ lookup.roName}</form:option>
											</c:forEach>
										</form:select></td>
									<td align="center">
										<div class="input-group">
											<form:input
												path="command.sanitationStaffTargetDto.sanitationStaffTargetDet[${d}].sandVolume"
												cssClass="form-control  mandColorClass hasDecimal text-center"
												onchange="" id="sandVolume${d }" disabled="" />
											<span class="input-group-addon"><spring:message
													code="swm.kgs" text="Kilograms" /></span>
										</div>
									</td>
									<td align="center"><a
										class="btn btn-danger btn-sm delButton" title="Delete"
										onclick="deleteEntry('id_segregationformTable3',$(this),'sequence${d}')">
											<i class="fa fa-minus"></i>
									</a></td>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<!-- Disposal Wise-->
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>


