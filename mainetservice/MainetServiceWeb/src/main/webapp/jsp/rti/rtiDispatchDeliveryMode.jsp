<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>


<c:if test="${command.deliveryMode eq 'POC'}">
	<div class="form-group">

		<apptags:input labelCode="rti.deliveryrefNo"
			path="command.reqDTO.deliveryReferenceNumber"
			cssClass="form-control hasNumber" isMandatory="true" maxlegnth="16"></apptags:input>

		<apptags:input labelCode="rti.docketNo" path="command.reqDTO.DispatchDocketNo"
			cssClass="form-control " isMandatory="true" maxlegnth="40"></apptags:input>
	</div>

	<div class="form-group">
		<apptags:input labelCode="rti.CourierName" path="command.reqDTO.DispatchName"
			cssClass="form-control " isMandatory="true" maxlegnth="199"></apptags:input>
	</div>
</c:if>

<c:if test="${command.deliveryMode eq 'CAO'}">

	<div class="form-group">

		<apptags:input labelCode="rti.authorityName"
			path="command.reqDTO.DispatchName" cssClass="form-control "
			isMandatory="true" maxlegnth="200"></apptags:input>


		<apptags:input labelCode="rti.mobile1" path="command.reqDTO.DispatchMobile"
			cssClass="form-control hasMobileNo" isMandatory="true" maxlegnth="11"></apptags:input>

	</div>

</c:if>
