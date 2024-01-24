<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<c:if test="${command.deptCode eq 'AS'}">
	<div class="form-group">
		<div id="dispproperty" style="display: none;">
			<label class="col-sm-2 control-label required-control" id="property"
				for="dept"><spring:message code="common.payment.propNo"
					text="" /></label>
			<div class="col-sm-4">
				<form:input name="" type="dept" class="form-control mandColorClass"
					path="command.objectionDetailsDto.objectionReferenceNumber"
					data-rule-required="true"></form:input>
			</div>
			<label class="col-sm-2 control-label required-control" id="property"
				for="dept"><spring:message
					code="obj.additionalReferenceNumber" text="" /></label>
			<div class="col-sm-4">
				<form:input name="" type="dept" class="form-control"
					path="command.objectionDetailsDto.objectionAddReferenceNumber"></form:input>
			</div>
		</div>
	</div>
</c:if>	
<c:if test="${command.deptCode eq 'RTI'}">
	<div class="form-group">
		<div id="disprti">

			<label class="col-sm-2 control-label required-control" id="rti"
				for="dept"><spring:message code="obj.rtino" text="" /></label>
			<div class="col-sm-4">
				<form:input name="" type="dept" class="form-control mandColorClass"
					path="command.objectionDetailsDto.objectionReferenceNumber"
					data-rule-required="true"></form:input>
			</div>
		</div>
	</div>
</c:if>		

