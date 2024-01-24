<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript"
	src="js/property/provisionalDetailDemandRegister.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="property.provisional.DemandRegister"
					text="Demand Register" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="property.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="ProvisionalDemand.html" cssClass="form-horizontal"
				id="DemandFormReport" name="DemandFormReport">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<form:hidden path="maxFinDate" id="maxFinDate" />
				<%-- <form:hidden path="minFinDate" id="minFinDate" /> --%>


				<div class="form-group">
					<c:set var="baseLookupCode" value="WZB" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="WZB" hasId="true"
						pathPrefix="propertyDto.mnassward"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" showAll="true"
						isMandatory="true" />
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="property.provisional.Demand.asOnDate" text="As On Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="propertyDto.mnFromdt" id="mnFromdt"
								class="form-control mandColorClass datepicker dateValidation"
								value="" readonly="false" data-rule-required="true"
								maxLength="10" />
							<label class="input-group-addon" for="mnFromdt"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=mnFromdt></label>
						</div>
					</div>
					
					
					<div class="col-sm-2">
							
							<button type="button" class="btn btn-success save"
								name="button-save" value="" id="demandGenerate"
								data-loading-text="<i class='fa fa-spinner fa-spin'></i>Processing Order">
								Generate Demand							
							</button>

					</div>
					
					
					
					<%-- <label class="col-sm-2 control-label required-control"> <spring:message
							code="property.provisional.Demand.toDate" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="propertyDto.mnTodt" id="mnTodt"
								class="form-control mandColorClass datepicker dateValidation "
								value="" readonly="false" data-rule-required="true"
								maxLength="10" />
							<label class="input-group-addon" for="mnTodt"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=mnTodt></label>
						</div>
					</div> --%>
				</div>


				<%-- <div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="property.detail.property.wise" text="Property Wise" /> </label>
					<div class="col-sm-4">
						<form:input path="propertySearchdto.proertyNo" id="proertyNo"
							class="form-control mandColorClass" maxlength="50"></form:input>
					</div>
				</div> --%>

				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveFormDetails(this)">
						Print Report
					</button>
					<button type="Reset" class="btn btn-warning" id="resetForm" onclick="resetFormDetails()">
						<spring:message code="property.btn.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="property.btn.back"></spring:message>
					</a>
				</div>
			</form:form>
		</div>
	</div>
</div>






