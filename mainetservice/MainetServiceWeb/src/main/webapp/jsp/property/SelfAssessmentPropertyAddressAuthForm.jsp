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
<script>
	$(function() {
		$("#isCorrespondenceAddressSame").on("click", function() {
			$("#hideBillingDetails").toggle(this.checked);
		});
	});
</script>

<style>
#hideBillingDetails {
	display: none;
}
</style>


<div class="accordion-toggle">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#propertyAddress"><spring:message
				code="property.Propertyaddress" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="propertyAddress">
		<div class="form-group">
			<label for="assAddress" class="col-sm-2 required-control"><spring:message
					code="property.propertyaddress" /> </label>
			<div class="col-sm-4">
				<form:textarea type="text" id="assAddress"
					path="entity.proAssAddress" class="form-control" />
			</div>
			<label for="locId" class="col-sm-2 required-control"><spring:message
					code="property.location" /> </label>
			<div class="col-sm-4">
				<%-- <form:select path="entity.locId" class="form-control" id="locId">
					<form:option value="0">select</form:option>
					<c:forEach items="${command.location}" var="locationMap">
						<form:option value="${locationMap.key}">${locationMap.value}</form:option>
					</c:forEach>
				</form:select> --%>
				
				<form:input id="locId" path="entity.locId" value="${entity.locationDesc}"
					class="hasNumber form-control" />
					
			</div>
		</div>
		<div class="form-group">
			<label for="assPincode" class="col-sm-2 required-control"><spring:message
					code="property.pincode" /> </label>
			<div class="col-sm-4">
				<form:input id="assPincode" path="entity.proAssPincode"
					class="hasNumber form-control" />
			</div>

			<label for="assEmail" class="col-sm-2 required-control"> <spring:message
					code="property.email" />
			</label>
			<div class="col-sm-4">
				<form:input id="assEmail" path="entity.proAssCorrEmail"
					class="form-control" />
			</div>

		</div>
		<div class="form-group">
			<div class="col-sm-6">
				<label for="checkbox-1492069540983" class="radio-inline"> <form:checkbox
						path="" value="" id="isCorrespondenceAddressSame" /> <spring:message
						code="property.ifCorrespondingaddress" />
				</label>
			</div>
		</div>
	</div>
</div>
<div id="hideBillingDetails">
	<div class="accordion-toggle">
		<h4 class="margin-top-0 margin-bottom-10 panel-title">
			<a data-toggle="collapse" href="#correspondenceAddress"><spring:message
					code="property.correspondenceaddress" /></a>
		</h4>

		<div class="panel-collapse collapse in" id="correspondenceAddress">

			<div class="form-group">
				<label for="assCorrAddress" class="col-sm-2 control-label">
					<spring:message code="property.correspondenceaddress" />
				</label>
				<div class="col-sm-4">
					<form:input id="assCorrAddress" path="entity.proAssCorrAddress"
						class="form-control" disabled="${viewMode}" />
				</div>
				<label for="assCorrPincode" class="col-sm-2 control-label">
					<spring:message code="property.pincode" />
				</label>

				<div class="col-sm-4">
					<form:input id="assCorrPincode" path="entity.proAssCorrPincode"
						class="form-control" disabled="${viewMode}" />
				</div>
			</div>
			<div class="form-group">
				<label for="assCorrEmail" class="col-sm-2 control-label"> <spring:message
						code="property.email" />
				</label>
				<div class="col-sm-4">
					<form:input id="assCorrEmail" path="entity.proAssCorrEmail"
						class="form-control" disabled="${viewMode}" />
				</div>
			</div>
		</div>
	</div>
</div>
