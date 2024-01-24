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

<script  src="js/mainet/validation.js"></script>
<script  src="js/water/change-usage.js"></script>

<script >
$(document).ready(function(){
	$('#changeOfUsageId').validate({
		   onkeyup: function(element) {
	       this.element(element);
	       console.log('onkeyup fired');
	 },
	       onfocusout: function(element) {
	       this.element(element);
	       console.log('onfocusout fired');
	}});	
}); 

function saveData(element)
{
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y') {
	 return saveOrUpdateForm(element,"Your application for change Of ownership saved successfully!", 'ChangeOfOwnership.html?redirectToPay', 'saveform');
	}
	else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N')
		{
		 return saveOrUpdateForm(element,"Your application for change Of ownership saved successfully!", 'ChangeOfOwnership.html?PrintReport', 'saveform');
		}
}

</script>


<div  id="fomDivId">

<%-- <ol class="breadcrumb">
	<li><a href="CitizenHome.html"><i class="fa fa-home"></i></a></li>
	<li>Services</li>
	<li><spring:message code="water.head.changeUsage" text="Change of Usage"/></li>
</ol> --%>

 <apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.head.changeUsage" text="Change of Usage" />
			</h2>
			<apptags:helpDoc url="ChangeOfUsage.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith"/> <i class="text-red-1">*</i> <spring:message code="water.ismandtry"/>
				</span>
			</div>
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			 	
				
			</div>
			
			<form:form action="ChangeOfUsage.html" method="POST" class="form-horizontal" id="changeOfUsageId" >
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


<div class="panel-group accordion-toggle" id="accordion_single_collapse">
				
					<%-- <jsp:include page="/jsp/mainet/applicantDetails.jsp" /> --%>
					<apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail>
				
<div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title">
                  	<a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#old_details"><spring:message code="water.changeUsage.details.old" text="Old Details"/></a> </h4>
                </div>
                <div id="old_details" class="panel-collapse in collapse">
                	 <div class="panel-body">
                	 	<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="water.ConnectionNo"></spring:message></label>
						<div class="col-sm-4">
							<form:input path="requestDTO.connectionNo"
								type="text" class="form-control disablefield" id="conNum"
								readonly="false" data-rule-required="true"></form:input>
						</div>
						<div class="col-sm-6">
							<button type="button" value="Search" class="btn btn-success" id="searchConnection">
								<i class="fa fa-search"></i> <spring:message code="searchBtn"/>
							</button>
							<button type="button" class="btn btn-warning"
										id="reset" onclick="resetConnection(this)">
										<spring:message code="water.nodues.reset"/>
									</button>
						</div>
					</div>
					<c:if test="${command.resultFound eq true }">	
					
					 <div class="form-group" id="consumerDetails">
						<label class="col-sm-2 control-label">
							<spring:message	code="water.consumerName"></spring:message>
						</label>
						<div class="col-sm-4">
							<form:input path="" cssClass="form-control disablefield" id="conName" disabled="true" value="${command.customerInfoDTO.consumerName}"/>
						</div>
						
						<label class="col-sm-2 control-label"><spring:message code="water.ConnectionSize" /></label>
							<c:set var="baseLookupCode" value="CSZ" />
					<apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
						path="requestDTO.connectionSize" cssClass="form-control disablefield"
						hasChildLookup="false" showAll="false" showOnlyLabelWithId="true"
						selectOptionLabelCode="applicantinfo.label.select" />
					</div>
					
					<div class="form-group" id="oldTarrif">
						<apptags:lookupFieldSet baseLookupCode="TRF" hasId="true"
							showOnlyLabel="false" pathPrefix="requestDTO.changeOfUsage.oldTrmGroup" 
							isMandatory="false" showOnlyLabelWithId="true" cssClass="form-control disablefield"  showAll="true"/>
					</div>
					
				<div id="newType">	
				<h4>
					<spring:message code="water.changeUsage.details.new" text="New Details" />
				</h4>
				
					<div class="form-group" id="newTarrif">
						<apptags:lookupFieldSet baseLookupCode="TRF" hasId="true"
							pathPrefix="requestDTO.changeOfUsage.newTrmGroup"
							isMandatory="true" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control mandColorClass"/>
					</div>
					<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
								code="water.remark" /></label>
						<div class="col-sm-10">
							<form:textarea type="text" class="form-control"
								path="requestDTO.changeOfUsage.remark" id="remark" maxlength="200" data-rule-required="true"></form:textarea>
						</div> 
					</div>
					
				</div>
		</c:if>					
                	 </div>
				</div>
              </div>
			
		<c:if test="${command.resultFound eq true }">
			<jsp:include page="/jsp/water/ChargesAndChecklist.jsp"/>
		</c:if>
		
			<c:if test="${command.enableSubmit eq true}">
					<div class="text-center padding-top-10">
						<input type="button" class="btn btn-success"
							onclick="saveChangeOfUsage(this);"
							value=<spring:message code="saveBtn"/> /> 
							<input type="button" class="btn btn-warning" onclick="window.location.href='ChangeOfUsage.html'"
							value=<spring:message code="rstBtn"/> /> <input type="button"
							class="btn btn-danger"
							onclick="window.location.href='CitizenHome.html'"
							value=<spring:message code="bckBtn"/> />
					</div>
			</c:if>
			
		</div>
			</form:form>
		</div>
	</div>
	
</div>
</div>