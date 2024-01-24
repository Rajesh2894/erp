 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- <%@ page import="com.abm.mainet.common.utility" %> --%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>  

<script type="text/javascript" src="js/disaster_management/disCallDetails.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script> 



<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="DisasterCallDetailsDTO.callDetails" text="Call Details"></spring:message></strong>
				<apptags:helpDoc url="DisCallDetails.html"></apptags:helpDoc>
			</h2>
		</div>
		
		<div class="widget-content padding">
							
		<form:form action="DisCallDetails.html" id="frmDisCallDetails" method="POST" commandName="command" class="form-horizontal form">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />		
		<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
		</div>
				
		<div class="form-group"> 
			<apptags:lookupFieldSet baseLookupCode="CMT" hasId="true"
					pathPrefix="disasterCallDetailsDTO.callType" isMandatory="true"
					hasLookupAlphaNumericSort="true"
					hasSubLookupAlphaNumericSort="true" cssClass="form-control"
					showAll="false" /> 	
		</div>	
		
		<div class="form-group">
		
		<label class="control-label col-sm-2 required-control" for="location"> <spring:message code="DisasterCallDetailsDTO.location" text="" /></label>
		
			<div class="col-sm-4"> 
			<form:select id="location" path="disasterCallDetailsDTO.location" cssClass="form-control chosen-select-no-results " data-rule-required="true" >
				<form:option value="">
					<spring:message code="Select" text="Select" />
				</form:option>
				<c:forEach items="${location}" var="location">
					<form:option value="${location.locId}">${location.locNameEng}-${location.locArea}</form:option>
				</c:forEach>
			</form:select>
			</div>
			
			<apptags:date fieldclass="datepicker"
				labelCode="DisasterCallDetailsDTO.fromDate" isMandatory="true"
				datePath="disasterCallDetailsDTO.fromDate" 
				cssClass="custDate mandColorClass date">
			</apptags:date>	

		</div>
				
		<div class="form-group">	
			<apptags:date fieldclass="datepicker"	
			labelCode="DisasterCallDetailsDTO.toDate" isMandatory="true"
			datePath="disasterCallDetailsDTO.toDate" 
			cssClass="custDate mandColorClass date">
			</apptags:date>
		</div>

		
		<div class="text-center margin-top-10">
			<input type="button" onClick="toCheck(this);" value="<spring:message code="bt.search"/>"
			title='<spring:message code="bt.search"/>'
			class="btn btn-success" id="Search">
			<input type="button" onclick="window.location.href='AdminHome.html'" class="btn btn-danger  hidden-print"
			title='<spring:message code="bt.backBtn" text="Back" />'
			value="<spring:message code="bt.backBtn" text="Back" />">
		</div>
		
		</form:form>
		</div>
	</div>
	
</div>	
	


 