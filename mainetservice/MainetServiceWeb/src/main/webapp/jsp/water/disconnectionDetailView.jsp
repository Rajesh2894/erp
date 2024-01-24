<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- <script type="text/javascript" src="js/mainet/validation.js"></script> -->
<script type="text/javascript">
$(document).ready(function(){
	$('input[type=text]').attr('disabled',true);
});
</script>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
		
			<h2><spring:message code="water.reconnection.disconnectionDetails"/></h2>
		</div>
		<div class="widget-content padding">
			
			<form:form action="WaterReconnectionForm.html" class="form-horizontal form" name="frmWaterReconnectionForm"
				id="frmWaterReconnectionForm">
				
				
				<%-- <jsp:include page="/jsp/tiles/validationerror.jsp" /> --%>
				
				      	<div class="form-group">
               				<label class="col-sm-2 control-label "><spring:message code="water.ConnectionNo"></spring:message></label>
               				<div class="col-sm-4">
                   				<form:input path="connectionNo" type="text" class="form-control disablefield" id="connectionNo" ></form:input>
                  			</div>
               				
               			</div>
						<div class="form-group">
							<label class="col-sm-2 control-label "><spring:message code="water.consumerName"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control disablefield" path="consumerName" id="consumerName"></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message code="water.connectiondetails.tariffcategory"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control disablefield" path="tarrifCate" id="conCategory"></form:input>
							</div>
						</div>
				
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message code="water.connectiondetails.premisetype"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control disablefield" path="primiseType" id="conPremiseType"></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message code="water.ConnectionSize"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control disablefield" path="connectionSize" id="conSize"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message code="water.reconnection.disconnectionMethod"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control disablefield" path="discMethod" id="discMethod"></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message code="water.reconnection.disconnectionDate"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control disablefield" path="discDate" id="discDate"></form:input>
							</div>
						</div>
               
		              	 <div class="form-group">
							<label class="col-sm-2 control-label"><spring:message code="water.reconnection.disconnectionType"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control disablefield" path="discType" id="discType"></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message code="water.remark"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control disablefield" path="discRemarks" id="remarks"></form:input>
							</div>
						</div>
			</form:form>
		</div>
	</div>
</div>
