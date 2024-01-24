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
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script type="text/javascript" src="js/property/ownerDetail.js"></script>

<div class="accordion-toggle">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#OwnshipDetail"><spring:message
				code="property.Ownershipdetail" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="OwnshipDetail">
		<div class="form-group">
			<label class="col-sm-2 control-label required-control"
				for="OwnershipDetail"><spring:message
					code="property.ownershiptype" /></label>
			<div class="col-sm-4">
				<c:set var="baseLookupCode" value="OWT" />
				<form:select path="entity.proAssOwnerType"
					onchange="getOwnerDetails()" class="form-control" id="ownerTypeId">
					<form:option value="0">
						<spring:message code="property.sel.optn" />
					</form:option>
					<c:forEach items="${command.getLevelData(baseLookupCode)}"
						var="lookUp">
						<form:option value="${lookUp.lookUpId}"
							code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>
	</div>

</div>

<div class="accordion-toggle">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#Owner(s)_Detail"><spring:message
				code="property.Owner(s)detail" /></a>
	</h4>

	<div class="panel panel-default ">
		<table id="main-table"
			class="table text-left table-striped table-bordered">
			<tbody>
				<tr class="singleOwnerDetail">
					<th><spring:message code="ownersdetail.ownersname" /></th>
					<th><spring:message code="ownersdetail.husband/fathersname" /></th>
					<th><spring:message code="ownersdetail.mobileno" /></th>
					<th><spring:message code="ownersdetail.gender" /></th>
					<th><spring:message code="ownersdetail.adharno" /></th>
				</tr>
				<tr class="jointOwnerDetails">
					<th><spring:message code="ownersdetail.ownersname" /></th>
					<th><spring:message code="ownersdetail.husband/fathersname" /></th>
					<th><spring:message code="ownersdetail.mobileno" /></th>
					<th><spring:message code="ownersdetail.gender" /></th>
					<th><spring:message code="ownersdetail.adharno" /></th>
					<th><spring:message code="property.add/delete" /></th>
				</tr>
				<tr class="companyDetail">
					<th><spring:message code="ownersdetail.companyname" /></th>
					<th><spring:message code="ownersdetail.contactpersonName" /></th>
					<th><spring:message code="ownersdetail.mobileno" /></th>
					<th><spring:message code="ownersdetail.pancard" /></th>
				</tr>

				<c:forEach
					items="${command.entity.listOfProvisionalAssesmentOwnerDtl}"
					var="currOwner" varStatus="status">
					<c:set value="${status.index}" var="count"></c:set>
					<%-- <tr>
						 	 <td>${currOwner.proAssoOwnerName}</td>
								<td>${currOwner.proAssoGenderDesc}</td>
								<td>${currOwner.proAssoFathusName}</td>
								<td>${currOwner.proAssoMobileno}</td>
								<td>${currOwner.proAssoAddharno}</td>
					</tr>
 --%>

					<form:hidden path="entity.listOfProvisionalAssesmentOwnerDtl[${count}].proAssId" />
					<form:hidden path="entity.listOfProvisionalAssesmentOwnerDtl[${count}].proAssoType" />
					<form:hidden path="entity.listOfProvisionalAssesmentOwnerDtl[${count}].proAssoOtype" />
					<form:hidden path="entity.listOfProvisionalAssesmentOwnerDtl[${count}].proAssoActive" />
					<form:hidden path="entity.listOfProvisionalAssesmentOwnerDtl[${count}].orgid" />
					<form:hidden path="entity.listOfProvisionalAssesmentOwnerDtl[${count}].createdBy" />
					<form:hidden path="entity.listOfProvisionalAssesmentOwnerDtl[${count}].createdDate" />
					<form:hidden path="entity.listOfProvisionalAssesmentOwnerDtl[${count}].updatedBy" />
					<form:hidden path="entity.listOfProvisionalAssesmentOwnerDtl[${count}].updatedDate" />

					<tr class="detail">
						<td><c:set var="d" value="0" scope="page" /> <form:input
								id="assoOwnerName_${count}"
								path="entity.listOfProvisionalAssesmentOwnerDtl[${count}].proAssoOwnerName"
								class="form-control" /></td>

						<td><form:input id="assoFathusName_${count}"
								path="entity.listOfProvisionalAssesmentOwnerDtl[${count}].proAssoFathusName"
								class="form-control" disabled="${viewMode}" /></td>

						<td><form:input id="assoMobileno_${count}"
								path="entity.listOfProvisionalAssesmentOwnerDtl[${count}].proAssoMobileno"
								class="form-control" disabled="${viewMode}" /></td>

						<td class="ownerDetails"><c:set var="baseLookupCode"
								value="GEN" /> <form:select
								path="entity.listOfProvisionalAssesmentOwnerDtl[${count}].proAssoGender" 
								class="form-control" id="ownerGender_${count}">
								<form:option value="0">
									<spring:message code="property.sel.optn" />
								</form:option>

								<c:forEach items="${command.getLevelData(baseLookupCode)}"
									var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>

							</form:select></td>

						<td class="ownerDetails"><form:input
								id="assoAddharno_${count}"
								path="entity.listOfProvisionalAssesmentOwnerDtl[${count}].proAssoAddharno"
								class="form-control" disabled="${viewMode}" /></td>

						<td class="companyDetail"><form:input id="assoPanno_${count}"
								path="entity.listOfProvisionalAssesmentOwnerDtl[${count}].proAssoPanno"
								class="form-control" disabled="${viewMode}" /></td>

						<td class="jointOwnerDetails"><a href="javascript:void(0);"
							title="Add" class="addCF btn btn-success btn-sm"><i
								class="fa fa-plus-circle"></i></a> <a href="javascript:void(0);"
							title="Delete" class="remCF btn btn-danger btn-sm"><i
								class="fa fa-trash-o"></i></a></td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</div>
