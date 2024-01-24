<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/water/listOfClosingConnection.js"></script>    
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<style>
	.zone-ward .form-group > label[for="codDwzid3"] {
		clear: both;
	}
	.zone-ward .form-group > label[for="codDwzid3"],
	.zone-ward .form-group > label[for="codDwzid3"] + div {
		margin-top: 0.7rem;
	}
	.trf-section .form-group > label[for="trmGroup3"] {
		clear: both;
	}
	.trf-section .form-group > label[for="trmGroup3"],
	.trf-section .form-group > label[for="trmGroup3"] + div {
		margin-top: 0.7rem;
	}
</style>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.ListOfClosedConnection"
					text=" List Of Closed Connection" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="water.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="ListOfClosedConnection.html"
				cssClass="form-horizontal" id="ClosedConnectionFormReport">
			       <jsp:include page="/jsp/tiles/validationerror.jsp" /> 
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="zone-ward">
				<div class="form-group">
					<c:set var="baseLookupCode" value="WWZ" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="WWZ" hasId="true"
						pathPrefix="csmrInfoDTO.codDwzid" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" showAll="true"
						isMandatory="true" />
				</div>
				</div>
				
				<div class="trf-section">
					<div class="form-group">
						<c:set var="baseLookupCode" value="TRF" />
						<apptags:lookupFieldSet cssClass="form-control required-control"
							baseLookupCode="TRF" hasId="true"
							pathPrefix="csmrInfoDTO.trmGroup" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true" showAll="true"
							isMandatory="true" />
					</div>
				</div>
				<div class="form-group">
				  <label class="col-sm-2 control-label required-control"
							for="inwardType"> <spring:message
								code="water.list.disconnectionType" text="Disconnection Type" /></label>
						<c:set var="baseLookupCode" value="DIC" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="disconnectionDTO.discType" cssClass="form-control"
							hasChildLookup="false" hasId="true" showAll="true"
							selectOptionLabelCode="applicantinfo.label.select"
							isMandatory="true" showOnlyLabel="Disconnection Type" />
				</div>
         		
          <div class="form-group">
   
				<label class="col-sm-2 control-label required-control"> <spring:message
			code="water.list.fromDate" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="csmrInfoDTO.csFromdt" id="csFromdt"
								class="form-control mandColorClass datepicker dateValidation"
								value="" readonly="false" data-rule-required="true"
								maxLength="10" />
							<label class="input-group-addon" for="csFromdt"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=csFromdt></label>
						</div>
					</div>
				
				
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="water.list.toDate" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="csmrInfoDTO.csTodt" id="csTodt"
								class="form-control mandColorClass datepicker dateValidation "
								value="" readonly="false" data-rule-required="true"
								maxLength="10" />
							<label class="input-group-addon" for="csTodt"><i
								class="fa fa-calendar"></i><span class="hide"> 
					<spring:message code="" text="icon" /></span><input type="hidden" id=csTodt></label>
						</div>
					</div>
				</div> 
				
				
         
         <div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveForm(this)">
						<spring:message code="water.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetform">
						<spring:message code="water.btn.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="water.btn.back"></spring:message>
					</a>
			</div>
       </form:form>
				</div>
			</div>
	  </div>


