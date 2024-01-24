
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/fire_management/callDetails.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="CallDetailsDTO.callDetails" text="Call Details"></spring:message></strong>
				<apptags:helpDoc url="CallDetails.html"></apptags:helpDoc>
			</h2>
		</div>
		
		<div class="widget-content padding">			
		
		<form:form action="CallDetails.html" id="frmCallDetails" method="POST" commandName="command" class="form-horizontal form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>
		<div class="form-group">	
		<label class="control-label col-sm-2 required-control" for="fireStation"> <spring:message code="CallDetailsDTO.fireStation" text="Fire Station" /></label>
		<div > 
		<c:set var="baseLookupCode" value="FSN" />
			<apptags:lookupField items="${command.getLevelData(baseLookupCode)}" path="callDetailsDTO.fireStation"
						cssClass="mandColorClass form-control fireStation" isMandatory="true" 
						hasId="true" selectOptionLabelCode="selectdropdown" />
		</div>
			<apptags:date fieldclass="datepicker"
				labelCode="CallDetailsDTO.fromDate" isMandatory="true" 
				datePath="callDetailsDTO.fromDate" 
				cssClass="custDate mandColorClass date">
			</apptags:date>	
		</div> 
		
		<div class="form-group">	
			<apptags:date fieldclass="datepicker"
			labelCode="CallDetailsDTO.toDate" isMandatory="true" 
			datePath="callDetailsDTO.toDate" 
			cssClass="custDate mandColorClass date">
			</apptags:date>
		</div>
		
		<div class="text-center margin-top-10">
			<%-- Defect #158117 --%>
			<input type="button" onClick="searchForm(this);" value="<spring:message code="bt.search"/>"
				title='<spring:message code="bt.search"/>'
				class="btn btn-success" id="Search">
			<button type="button" id="reset"
						title='<spring:message code="rstBtn" text="Reset" />'
						onclick="window.location.href='CallDetails.html'"
						class="btn btn-warning" title="Reset">
						<!-- <i class="fa fa-undo padding-right-5" aria-hidden="true"></i> -->
						<spring:message code="rstBtn"
							text="Reset" />
			</button>
			<input type="button" onclick="window.location.href='AdminHome.html'"
				title='<spring:message code="bckBtn" text="Back" />'
				class="btn btn-danger  hidden-print" value='<spring:message code="bckBtn" text="Back" />'>
		</div>

		
		</form:form>
		</div>
	</div>
</div>	
	

