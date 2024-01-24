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
 <!-- <script type="text/javascript" src="js/mainet/file-upload.js"></script> -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/adh/adhOwner.js"></script>


   <c:choose>
       <c:when test="${command.ownershipPrefix eq 'A' }">

		<c:set var="d" value="0" scope="page"></c:set>
		<table class="table table-striped table-bordered" id="ownerDetail">
			<thead>

				<tr class="jointOwnerDetails">
					<th width="20%" class="required-control"><spring:message code="agency.owner" /></th>
					<th width="10%" class="required-control"><spring:message code="agency.mobile.no" /></th>
					<th width="10%" class="required-control"><spring:message code="agency.emailid" /></th>
					<th width="12%"><spring:message code="agency.uid.no" /></th>
					<th width="10%"><spring:message code="agency.pan" /></th>
					<c:if test="${command.openMode ne 'D' }">
					<th width="8%"><spring:message code="property.add/delete" /></th> </c:if>  
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${ fn:length(command.agencyRequestDto.masterDtolist) > 0}">
						
						<c:forEach var="taxData"
							items="${command.agencyRequestDto.masterDtolist}" varStatus="status">
							<tr class="appendableClass">
								<td><form:input
										path="command.agencyRequestDto.masterDtolist[${d}].agencyOwner"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasSpecialChara preventSpace"
										maxLength="100" id="agencyOwner${d}" /></td>
								</td>

								<td><form:input
										path="command.agencyRequestDto.masterDtolist[${d}].agencyContactNo"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasMobileNo preventSpace"
										id="agencyContactNo${d}" data-rule-required="true" /></td>
								<td><form:input
										path="command.agencyRequestDto.masterDtolist[${d}].agencyEmail"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasemailclass preventSpace"
										id="agencyEmail${d}" data-rule-email="true" maxLength="50" /></td>
								<td><form:input
										path="command.agencyRequestDto.masterDtolist[${d}].uidNo" type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasAadharNo preventSpace"
										data-rule-maxlength="12" data-rule-minlength="12"
										id="uidNo${d}" maxlegnth="12" /></td>

								<td><form:input
										path="command.agencyRequestDto.masterDtolist[${d}].panNumber"
										type="text" 
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control text-uppercase hasNoSpace"
										maxLength="10" id="panNumber${d}" autocomplete="off" /></td>
										<c:if test="${command.openMode ne 'D' }">
										<td>
											<a href="javascript:void(0);" title="Add" class="addCF btn btn-success btn-sm"><i class="fa fa-plus-circle"></i></a>
											<a href="javascript:void(0);" title="Delete" class="remCF btn btn-danger btn-sm" id="deleteOwnerRow_${status.count-1}"><i class="fa fa-trash-o"></i></a>
										</td></c:if> 
							</tr>
							<c:set var="d" value="${d + 1}" scope="page" />
						</c:forEach>

					</c:when>
					<c:otherwise>
						<tr class="appendableClass">

							<td><form:input
									path="command.agencyRequestDto.masterDtolist[${d}].agencyOwner"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasSpecialChara preventSpace"
									maxLength="100" id="agencyOwner${d}" /></td>
							</td>

							<td><form:input
									path="command.agencyRequestDto.masterDtolist[${d}].agencyContactNo"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasMobileNo preventSpace"
									id="agencyContactNo${d}" data-rule-required="true" /></td>
							<td><form:input
									path="command.agencyRequestDto.masterDtolist[${d}].agencyEmail"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasemailclass preventSpace"
									id="agencyEmail${d}" data-rule-email="true" maxLength="50" /></td>
							<td><form:input
									path="command.agencyRequestDto.masterDtolist[${d}].uidNo" type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasAadharNo preventSpace"
									data-rule-maxlength="12" data-rule-minlength="12"
									id="uidNo${d}" maxlegnth="12" /></td>

							<td><form:input
									path="command.agencyRequestDto.masterDtolist[${d}].panNumber" type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control text-uppercase hasNoSpace" maxLength="10"
									id="panNumber${d}"  autocomplete="off" /></td>
									<c:if test="${command.openMode ne 'D' }">
									     <td>
											<a href="javascript:void(0);" title="Add" class="addCF btn btn-success btn-sm"><i class="fa fa-plus-circle"></i></a>
											<a href="javascript:void(0);" title="Delete" class="remCF btn btn-danger btn-sm" id="deleteOwnerRow_${status.count-1}"><i class="fa fa-trash-o"></i></a>
										</td></c:if> 
									   
						</tr>
						 <c:set var="d" value="${d + 1}" scope="page" /> 
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</c:when>
	<c:otherwise> 
	<c:if test="${command.ownershipPrefix eq 'P'}">
		<c:set var="d" value="0" scope="page"></c:set>
		<table class="table table-striped table-bordered" id="ownerDetail">
			
					<thead>
					<tr class="singleOwnerDetail">
					<th width="20%" class="required-control"><spring:message code="agency.owner" /></th>
					<th width="10%" class="required-control"><spring:message code="agency.mobile.no" /></th>
					<th width="10%" class="required-control"><spring:message code="agency.emailid" /></th>
					<th width="12%"><spring:message code="agency.uid.no" /></th>
					<th width="10%"><spring:message code="agency.pan" /></th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${ fn:length(command.agencyRequestDto.masterDtolist) > 0}">
						
						<c:forEach var="taxData"
							items="${command.agencyRequestDto.masterDtolist}" varStatus="status">
							<tr class="appendableClass">

								<td><form:input
										path="command.agencyRequestDto.masterDtolist[${d}].agencyOwner"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasSpecialChara preventSpace"
										maxLength="100" id="agencyOwner${d}" /></td>
								</td>

								<td><form:input
										path="command.agencyRequestDto.masterDtolist[${d}].agencyContactNo"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasMobileNo preventSpace"
										id="agencyContactNo${d}" data-rule-required="true" /></td>
								<td><form:input
										path="command.agencyRequestDto.masterDtolist[${d}].agencyEmail"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasemailclass preventSpace"
										id="agencyEmail${d}" data-rule-email="true" maxLength="50" /></td>
								<td><form:input
										path="command.agencyRequestDto.masterDtolist[${d}].uidNo" type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasAadharNo preventSpace"
										data-rule-maxlength="12" data-rule-minlength="12"
										id="uidNo${d}" maxlegnth="12" /></td>

								<td><form:input
										path="command.agencyRequestDto.masterDtolist[${d}].panNumber"
										type="text" 
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control text-uppercase hasNoSpace"
										maxLength="10" id="panNumber${d}" autocomplete="off" /></td>


							</tr>
							<c:set var="d" value="${d + 1}" scope="page" />
						</c:forEach>
					
					</c:when>
					<c:otherwise>

						
						<tr class="appendableClass">

							<td><form:input
									path="command.agencyRequestDto.masterDtolist[${d}].agencyOwner"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasSpecialChara preventSpace"
									maxLength="100" id="agencyOwner${d}" /></td>
							</td>

							<td><form:input
									path="command.agencyRequestDto.masterDtolist[${d}].agencyContactNo"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasMobileNo preventSpace"
									id="agencyContactNo${d}" data-rule-required="true" /></td>
							<td><form:input
									path="command.agencyRequestDto.masterDtolist[${d}].agencyEmail"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasemailclass preventSpace"
									id="agencyEmail${d}" data-rule-email="true" maxLength="50" /></td>
							<td><form:input
									path="command.agencyRequestDto.masterDtolist[${d}].uidNo" type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasAadharNo preventSpace"
									data-rule-maxlength="12" data-rule-minlength="12"
									id="uidNo${d}" maxlegnth="12" /></td>

							<td><form:input
									path="command.agencyRequestDto.masterDtolist[${d}].panNumber" type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control text-uppercase hasNoSpace" maxLength="10"
									id="panNumber${d}" autocomplete="off" /></td>

						</tr>
						<c:set var="d" value="${d + 1}" scope="page" />
					</c:otherwise>
				</c:choose>

			</tbody>
		</table> 
		</c:if>
	</c:otherwise> 
</c:choose>


 