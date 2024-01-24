<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/care/complaint-report.js"></script>
<script>
	$(function() {
		
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
	});
	
	
	
	
	
</script>

    <apptags:breadcrumb></apptags:breadcrumb>
    <!-- Start Content here -->
    <div class="content animated slideInDown">
    
	 <div class="widget">
	   <div class="widget-header">
	      <h2><spring:message code="care.reports.heading" text="Grievances Report"/></h2>
	    
			<apptags:helpDoc url="GrievanceReport.html"></apptags:helpDoc>
				
	   </div>
	   <div class="widget-content padding">
	      <form:form method="POST" action="GrievanceReport.html" commandName="command" class="form-horizontal" id="form_grievanceReport">
			<div class="compalint-error-div">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
			</div>
	      
	         <div class="panel-group accordion-toggle" id="accordion_single_collapse">
	            <div class="panel panel-default">
	            
	            <div class="panel-heading">
	            	<h4 class="panel-title">
	            		<a data-toggle="" class="" data-parent="#accordion_single_collapse" href="#a0">
	            			<spring:message code="care.reports.ageing.heading" text="Grievance Ageing Report"/>
	            		</a></h4>
	            </div>
	            
	               <div id="a0" class="panel-collapse collapse in">
	                  <div class="panel-body">
	                     <div class="form-group">
		                    <apptags:select labelCode="care.department" items="${command.departments}"  path="careReportRequest.department" 
		                    selectOptionLabelCode="Select" isMandatory="true" showAll="true" isLookUpItem="true"></apptags:select>
		                    
		                    <div id="id_complaintType">
		                    	<apptags:select labelCode="care.complaintType" items="${command.complaintTypes}"  path="careReportRequest.complaintType" 
		                    		selectOptionLabelCode="Select" isMandatory="true" showAll="true" isLookUpItem="true" ></apptags:select>
		                    
		                    </div>
		                 </div>
		                 <div id="zone-ward"></div>
		                  <div class="form-group">
		                  	<label class="col-sm-2 control-label required-control" for="id_status"><spring:message code="care.reports.status" text="Status"/></label>
							<div class="col-sm-4">
								<form:select class="form-control" id="id_status" path="careReportRequest.status" data-rule-prefixvalidation="true">
									<form:option code="care.select" value="0"><spring:message code="care.select" text="Select"/></form:option>
									<form:option code="care.all" value="1"><spring:message code="care.all" text="All"/></form:option>
									<form:option code="care.closed" value="2"><spring:message code="care.closed" text="Closed"/></form:option>
									<form:option code="care.pending" value="3"><spring:message code="care.pending" text="Pending"/></form:option>
									<c:if test="${command.kdmcEnv eq 'N'}">
												<form:option code="care.rejected" value="4"><spring:message code="care.rejected" text="Rejected"/></form:option>
												<form:option code="care.hold" value="5"><spring:message code="care.hold" text="Hold"/></form:option> 
									</c:if>
									
									<form:option code="care.all" value="-1" cssStyle="display:none;"><spring:message code="care.all" text="All"/></form:option>
									<form:option code="care.closed" value="APPROVED" cssStyle="display:none;"><spring:message code="care.closed" text="Closed"/></form:option>
									<form:option code="care.pending" value="PENDING" cssStyle="display:none;"><spring:message code="care.pending" text="Pending"/></form:option>
									<form:option code="care.rejected" value="REJECTED" cssStyle="display:none;"><spring:message code="care.rejected" text="Rejected"/></form:option>
									<form:option code="care.hold" value="HOLD" cssStyle="display:none;"><spring:message code="care.hold" text="Hold"/></form:option>
									
								</form:select>
							</div>
							
							<label class="col-sm-2 control-label required-control" for="id_reportType"><spring:message code="care.reports.type" text="Report Type"/></label>
							<div class="col-sm-4">
								<form:select class="form-control changeParameterClass" id="id_reportType" path="careReportRequest.reportType" data-rule-prefixvalidation="true">
									<form:option value="0"><spring:message code="care.select" text="Select"/></form:option>
									<form:option value="1"><spring:message code="care.reports.ageingReportSummary" text="Summary Ageing Report"/></form:option>
									<form:option value="2"><spring:message code="care.reports.detailedAgeingReport" text="Detailed Ageing Report"/></form:option>
								</form:select>
							</div>
		                  </div>
		                  <div class="form-group">
							<apptags:input labelCode="care.reports.fromSlab" path="careReportRequest.fromSlab" isMandatory="true" cssClass="hasNumber" maxlegnth="3"></apptags:input>
							<apptags:input labelCode="care.reports.toSlab" path="careReportRequest.toSlab" isMandatory="true" cssClass="hasNumber" maxlegnth="3"></apptags:input>
						  </div>
						  <div class="form-group">
						  <label class="col-sm-2 control-label required-control" for="slabLevels"><spring:message code="care.reports.LevelsPlus" text="Status"/></label>
							<div class="col-sm-4">
						  	<form:select class="form-control" id="slabLevels" path="careReportRequest.slabLevels">
									<form:option code="care.select" value=""><spring:message code="care.select" text="Select"/></form:option>
									<form:option code="" value="1">1</form:option>
									<form:option code="" value="2">2</form:option>
									<form:option code="" value="3">3</form:option>
									<form:option code="" value="4">4</form:option>
									<form:option code="" value="5">5</form:option> 
								</form:select>
							</div>
 							<%-- <apptags:input labelCode="care.reports.LevelsPlus" path="careReportRequest.slabLevels" isMandatory="true" cssClass="hasNumber" maxlegnth="1"></apptags:input> --%>
						   </div>
	                  </div>
	               </div>
	               <div class="text-center clear padding-10">
	               <input type="button" class="btn  btn-success" id="save" name="save" value="<spring:message code="care.submit"/>" onclick="submitComplaintAgeing(this);"  />
	                <input type="button" class="btn btn-warning" id="rstButton" value="<spring:message code="care.reset" text="Reset"/>" onclick="resetFormData()"/>
	                <a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="care.btn.back"></spring:message>
					</a>
		             	<%-- <apptags:submitButton entityLabelCode="Submit" cssClass="button-input btn btn-success" actionParam="grievanceAgeingReport"></apptags:submitButton>	--%>
		                 <%-- <apptags:resetButton cssClass="btnReset"><spring:message code="care.reset" /></apptags:resetButton>	   --%>
	               </div>
	            </div>
	         </div>
	         </form:form>
	   </div>
	</div>
   </div>