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
<script type="text/javascript" src="js/rti/rtiObjectionDetails.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>

<div class="table-responsive">
<table class="table table-bordered  table-condensed" id="objDetails">
	<thead>

		<tr>
			<th><spring:message code="obj.serialNo"></spring:message></th>
			<th><spring:message code="obj.objectionno"></spring:message></th>
			<th><spring:message code="obj.objectiondate"></spring:message></th>
			<th><spring:message code="obj.ownername"></spring:message></th>
			<th><spring:message code="obj.objectiontype"></spring:message></th>
			<th><spring:message code="obj.objstatus"></spring:message></th>
			<th><spring:message code="obj.hearingAction"></spring:message></th>
		</tr>
	</thead>
	<tbody>

		<c:forEach items="${objectionList}" var="ObjectionDetailsDto">
			<tr>

				<td>${ObjectionDetailsDto.objectionSerialNumber}</td>
				<td>${ObjectionDetailsDto.objectionNumber}</td>
				<td>${ObjectionDetailsDto.objectionDate}</td>
				<%-- <td>${ObjectionDetailsDto.ownerName}</td> --%>
				<td>${ObjectionDetailsDto.objectionType}</td>
				<td>${ObjectionDetailsDto.objectionStatus}</td>

				<td class="text-center">
					<button type="button" class="btn btn-blue-2 btn-sm"
						onClick="viewObjDetails(${ObjectionDetailsDto.groupId})"
						title="View Objection Details">
						<i class="fa fa-eye"></i>
					</button>
					<button type="button" class="btn btn-success btn-sm"
						onClick="editObjDetails(${ObjectionDetailsDto.groupId})"
						title="Edit Objection Details">
						<i class="fa fa-pencil"></i>
					</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
