<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/water/executePlumberLicense.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script> 

<script type="text/javascript">
 $(document).ready(function(){
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate: '-0d',
		yearRange: "-100:-0",
		});
	$('input[type=text]').attr('disabled',true);
	$("#executionDate").attr('disabled',false);
 });
</script>
<div id="plumExecuteDiv">
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="water.plumberLicense.execution"/></h2>
		</div>
		<div class="widget-content padding">
			 <div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith"/><i class="text-red-1">*</i><spring:message code="water.ismandtry"/>
				</span>
			</div> 
			<form:form action="ExecutePlumberLicense.html" class="form-horizontal form" name="frmExecutePlumberLicense"
				id="executePlumberLicense">
				
				 <jsp:include page="/jsp/tiles/validationerror.jsp" /> 
				      <h4><spring:message code="water.meterDet.appDet"/></h4>
						<div class="form-group">
							<label class="col-sm-2 control-label "><spring:message code="water.meterDet.servName"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control disablefield" path="serviceName" id=""></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message code="water.meterDet.appliNo"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control disablefield" path="applicationId" id=""></form:input>
							</div>
						</div>
				
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message code="water.meterDet.appliName"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control disablefield" path="applicantFullName" id=""></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message code="water.meterDet.appDate"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control disablefield" path="applicationDate" id=""></form:input>
							</div>
						</div>
						
					<h4><spring:message code="water.reconnection.executionDetail"/></h4>
				
			 		<div class="form-group">
            			<label class="col-sm-6 checkbox-inline"><form:checkbox path="executePlmLic"  value="Y" id="executePlmLicId"/> <spring:message code="water.plumberLicense.executePlmLic"/></label>
              
            			<label class="col-sm-2 control-label"><spring:message code="water.plumberLicense.issueDate"/></label>
              			<div class="col-sm-4">
                			<div class="input-group">
                  			<form:input path="plmLicIssueDate" class="form-control lessthancurrdate" id="executionDate" onchange="checkedExecutionBox();"/>
                  			<label class="input-group-addon" for="executionDate"><i class="fa fa-calendar"></i></label>
                			</div>
              			</div>
              		</div>
            
            		<div class="form-group">
              			<label class="col-sm-2 control-label"><spring:message code="water.apprvdBy"/></label>
              				<div class="col-sm-4">
                				<form:input path="approvedBy" class="form-control"/>
              				</div>
              			<label class="col-sm-2 control-label"><spring:message code="water.apprvdDate"/></label>
              				<div class="col-sm-4">
                				<div class="input-group">
                  						<form:input path="approvalDate" class="form-control" id="datepicker3"/>
                  					<label class="input-group-addon" for="datepicker3"><i class="fa fa-calendar"></i></label>
                				</div>
              				</div>
           			</div> 
                    <div class="text-center">
            			<button type="submit" class="btn btn-success btn-submit" onclick="updatedPlumberLicenseExecutionDetailByDept(this)"><spring:message code="water.btn.submit"/></button>
            			<button type="reset" class="btn btn-warning"><spring:message code="water.btn.reset"/></button>
            		</div>

			</form:form>
		</div>
	</div>
</div>
</div>
