<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script type="text/javascript" src="js/social_security/familyDetails.js"></script>

<c:set var="d" value="0" scope="page"></c:set>
<table class="table table-striped table-bordered" id="ownerFamilyDetail">
	<thead>
		<tr>
			<th width="15%"><spring:message
					code="social.ownerFamilyDetail.Name" text="Name">
				</spring:message> <span class="mand">*</span></th>
			<th width="15%"><spring:message
					code="social.ownerFamilyDetail.rel" text="Relation"></spring:message><span
				class="mand">*</span></th>
			<th width="10%"><spring:message
					code="social.ownerFamilyDetail.gen" text="Gender">
					<span class="mand">*</span>
				</spring:message></th>
			<th width="10%"><spring:message
					code="social.ownerFamilyDetail.dob" text="Date Of Birth">
				</spring:message> <span class="mand">*</span></th>
			<th width="5%"><spring:message
					code="social.ownerFamilyDetail.Age" text="Age"></spring:message><span
				class="mand">*</span></th>
			<th width="15%"><spring:message
					code="social.ownerFamilyDetail.edu" text="Education"></spring:message><span
				class="mand">*</span></th>
			<th width="15%"><spring:message
					code="social.ownerFamilyDetail.ocu" text="Occupation"></spring:message><span
				class="mand">*</span></th>
			<th width="15%"><spring:message
					code="social.ownerFamilyDetail.contact" text="Contact No"></spring:message><span
				class="mand">*</span></th>


			<th colspan="2" width="5%"><a href="javascript:void(0);"
				title="Add" class=" addCF btn btn-success btn-sm" id="addUnitRow"
				onclick=''> <i class="fa fa-plus-circle"></i></a></th>

		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when
				test="${fn:length(command.applicationformdto.ownerFamilydetailDTO)>0 }">
				<c:forEach var="taxData"
					items="${command.applicationformdto.ownerFamilydetailDTO}"
					varStatus="status">
					<tr class="appendableFamilyClass">
						<td><form:input
								path="command.applicationformdto.ownerFamilydetailDTO[${d}].famMemName"
								type="text"
								class="form-control unit required-control hasSpecialChara preventSpace"
								maxLength="100" id="famMemName${d}" /></td>
						<td><form:input
								path="command.applicationformdto.ownerFamilydetailDTO[${d}].relation"
								type="text"
								class="form-control unit required-control hasSpecialChara preventSpace"
								maxLength="100" id="relation${d}" /></td>

						<td>
							<div>

								<c:set var="baseLookupCode" value="GEN" />
								<form:select
									path="command.applicationformdto.ownerFamilydetailDTO[${d}].gender"
									onchange="" class="form-control mandColorClass" id="gender${d}"
									data-rule-required="true">
									<form:option value="">
										<spring:message code="trade.sel.optn.gender" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<form:option value="${lookUp.lookUpCode}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</td>
						<td> <form:input
								class="form-control mandColorClass datepicker2 addColor"
								autocomplete="off" id="dob${d}"   maxlength="10"
								path="command.applicationformdto.ownerFamilydetailDTO[${d}].dob"></form:input>
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</td>
						<td><form:input
								path="command.applicationformdto.ownerFamilydetailDTO[${d}].age"
								type="text" 
								class="form-control unit required-control hasNumber preventSpace"
								maxLength="3" id="age${d}" /></td>
						<td><form:input
								path="command.applicationformdto.ownerFamilydetailDTO[${d}].education"
								type="text"
								class="form-control unit required-control hasSpecialChara preventSpace"
								id="education${d}" /></td>
						<td><form:input
								path="command.applicationformdto.ownerFamilydetailDTO[${d}].occupation"
								type="text"
								class="form-control unit required-control hasSpecialChara preventSpace"
								id="occupation${d}" /></td>
						<td><form:input
								path="command.applicationformdto.ownerFamilydetailDTO[${d}].contactNo"
								type="text" maxlength="10"
								class="form-control unit required-control hasMobileNo preventSpace"
								id="contactNo${d}" /></td>



						<td class="text-center"><a href="javascript:void(0);"
							class="btn btn-danger btn-sm delButton"
							onclick="DeleteRow(this);"><i class="fa fa-minus"
								id="deleteRow"></i></a></td>




					</tr>
					<c:set var="d" value="${d + 1}" scope="page" />
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="appendableFamilyClass">
					<td><form:input
							path="command.applicationformdto.ownerFamilydetailDTO[${d}].famMemName"
							type="text"
							class="form-control unit required-control hasSpecialChara preventSpace"
							maxLength="100" id="famMemName${d}" /></td>
					<td><form:input
							path="command.applicationformdto.ownerFamilydetailDTO[${d}].relation"
							type="text"
							class="form-control unit required-control hasSpecialChara preventSpace"
							maxLength="100" id="relation${d}" /></td>



					<td>
						<div>

							<c:set var="baseLookupCode" value="GEN" />
							<form:select
								path="command.applicationformdto.ownerFamilydetailDTO[${d}].gender"
								onchange="" class="form-control mandColorClass" id="gender${d}"
								data-rule-required="true">
								<form:option value="">
									<spring:message code="trade.sel.optn.gender" />
								</form:option>
								<c:forEach items="${command.getLevelData(baseLookupCode)}"
									var="lookUp">
									<form:option value="${lookUp.lookUpCode}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</td>
					<td><div class="input-group">
							<form:input
								class="form-control mandColorClass datepicker2 addColor"
								autocomplete="off" id="dob${d}"  maxlength="10"
								path="command.applicationformdto.ownerFamilydetailDTO[${d}].dob"></form:input>
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div></td>
					<td><form:input
							path="command.applicationformdto.ownerFamilydetailDTO[${d}].age"
							type="text" 
							class="form-control unit required-control hasNumber preventSpace"
							maxLength="3" id="age${d}" /></td>
					<td><form:input
							path="command.applicationformdto.ownerFamilydetailDTO[${d}].education"
							type="text"
							class="form-control unit required-control hasSpecialChara preventSpace"
							id="education${d}" /></td>
					<td><form:input
							path="command.applicationformdto.ownerFamilydetailDTO[${d}].occupation"
							type="text"
							class="form-control unit required-control hasSpecialChara preventSpace"
							id="occupation${d}" /></td>
					<td><form:input
							path="command.applicationformdto.ownerFamilydetailDTO[${d}].contactNo"
							type="text" maxlength="10"
							class="form-control unit required-control hasMobileNo preventSpace"
							id="contactNo${d}" /></td>
					<td class="text-center"><a href="javascript:void(0);"
						class="btn btn-danger btn-sm delButton" onclick="DeleteRow(this);"><i
							class="fa fa-minus" id="deleteRow"></i></a></td>



				</tr>
				<c:set var="d" value="${d + 1}" scope="page" />
			</c:otherwise>
		</c:choose>
	</tbody>
</table>