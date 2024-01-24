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

<script type="text/javascript"
	src="js/trade_license/hawkerOwnerFamilyTable.js"></script>

	<c:set var="d" value="0" scope="page"></c:set>
	<table class="table table-striped table-bordered" id="ownerFamilyDetail">
		<thead>
			<tr>
				<th width="20%"><spring:message code="hawkerLicense.ownerFamilyDetail.Name" text="Name"></spring:message></th>
				<th width="20%"><spring:message code="hawkerLicense.ownerFamilyDetail.Age" text="Age"></spring:message><span
					class="mand">*</span></th>
				<th width="15%"><spring:message code="hawkerLicense.ownerFamilyDetail.AadharNo" text="Aadhar No"></spring:message></th>
				<th width="20%"><spring:message code="hawkerLicense.ownerFamilyDetail.Relation" text="Relation"></spring:message><span
					class="mand">*</span></th>

			<c:if test="${command.openMode ne 'D' }">
				<th colspan="2"  width="5%"><a href="javascript:void(0);" title="Add"
					class=" addCF btn btn-success btn-sm" id="addUnitRow"
					onclick=''> <i class="fa fa-plus-circle"></i></a></th>
			</c:if>

		</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when
					test="${fn:length(command.tradeMasterDetailDTO.ownerFamilydetailDTO)>0 }">
					<c:forEach var="taxData"
						items="${command.tradeMasterDetailDTO.ownerFamilydetailDTO}"
						varStatus="status">
						<tr class="appendableFamilyClass">
							<td><form:input
									path="tradeMasterDetailDTO.ownerFamilydetailDTO[${d}].famMemName"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasSpecialChara preventSpace"
									maxLength="100" id="famMemName${d}" /></td>



							<td><form:input
									path="tradeMasterDetailDTO.ownerFamilydetailDTO[${d}].famMemAge"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasNumber preventSpace"
									maxLength="3" id="famMemAge${d}" /></td>



							<td><form:input
									path="tradeMasterDetailDTO.ownerFamilydetailDTO[${d}].famMemUidNo"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasNumber hasAadharNo preventSpace"
									id="famMemUidNo${d}" maxlegnth="12" /></td>


							<td><form:input
									path="tradeMasterDetailDTO.ownerFamilydetailDTO[${d}].famMemRelation"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control preventSpace hasSpecialChara"
									data-rule-maxlength="12" data-rule-minlength="12"
									id="famMemRelation${d}" maxlegnth="12" /></td>


                            <c:if test="${command.openMode ne 'D' }">
								<td class="text-center"><a href="javascript:void(0);"
									class="btn btn-danger btn-sm delButton" onclick="DeleteRow(this);"><i
										class="fa fa-minus" id="deleteRow"></i></a></td></c:if>
                            



						</tr>
						<c:set var="d" value="${d + 1}" scope="page" />
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="appendableFamilyClass">

						<td><form:input
								path="tradeMasterDetailDTO.ownerFamilydetailDTO[${d}].famMemName"
								type="text"
								disabled="${command.viewMode eq 'V' ? true : false }"
								class="form-control unit required-control hasSpecialChara preventSpace"
								maxLength="100" id="famMemName${d}" /></td>



						<td><form:input
								path="tradeMasterDetailDTO.ownerFamilydetailDTO[${d}].famMemAge"
								type="text"
								disabled="${command.viewMode eq 'V' ? true : false }"
								class="form-control unit required-control hasNumber preventSpace"
								maxLength="3" id="famMemAge${d}" /></td>



						<td><form:input
								path="tradeMasterDetailDTO.ownerFamilydetailDTO[${d}].famMemUidNo"
								type="text"
								disabled="${command.viewMode eq 'V' ? true : false }"
								class="form-control unit required-control hasNumber hasAadharNo preventSpace"
								id="famMemUidNo${d}" maxlegnth="12" /></td>


						<td><form:input
								path="tradeMasterDetailDTO.ownerFamilydetailDTO[${d}].famMemRelation"
								type="text"
								disabled="${command.viewMode eq 'V' ? true : false }"
								class="form-control unit required-control preventSpace hasSpecialChara"
								data-rule-maxlength="12" data-rule-minlength="12"
								id="famMemRelation${d}" maxlegnth="12" /></td>
								
					
                            <c:if test="${command.openMode ne 'D' }">
								<td class="text-center"><a href="javascript:void(0);"
									class="btn btn-danger btn-sm delButton" onclick="DeleteRow(this);"><i
										class="fa fa-minus" id="deleteRow"></i></a></td></c:if>
                            
								

					</tr>
					<c:set var="d" value="${d + 1}" scope="page" />
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>