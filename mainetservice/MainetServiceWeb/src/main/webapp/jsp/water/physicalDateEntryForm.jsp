 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/water/physicalDateEntry.js"></script>

<script>


$(document).ready(function(){
	flagCheck();
	
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		 minDate: $("#appldate").val(),
		maxDate: '-0d',
		yearRange: "-100:-0"
	});
	
	 preparePhysicalDateTag();
});

</script>


<apptags:breadcrumb ></apptags:breadcrumb>

	<div class="widget" id="widget">
		<div class="widget-header">
			<h2><spring:message code="water.physical.dateEntry" text="Physical Date Entry"/></h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i	class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith"/><i class="text-red-1">*</i><spring:message code="water.ismandtry"/>
				</span>
			</div>
			<form:form action="PhysicalDateEntry.html"	class="form-horizontal form" name="frmPhysicalDateEntry"	id="frmPhysicalDateEntry">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div alert alert-danger alert-dismissible hide" id="errorDivScrutiny">
				<button type="button" class="close" aria-label="Close" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button>
				
				<ul><li><i class='fa fa-exclamation-circle'></i>&nbsp;<form:errors path="*"/></li></ul>
			</div>
				<script>
				$(".warning-div ul").each(function () {
				    var lines = $(this).html().split("<br>");
				    $(this).html('<li>' + lines.join("</li><li><i class='fa fa-exclamation-circle'></i>&nbsp;") + '</li>');						
				});
					$('html,body').animate({ scrollTop: 0 }, 'slow');
				</script>
				<h4 class="margin-top-0"><spring:message code="water.meterDet.appDet"/></h4>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="water.meterDet.servName"/></label>
					<div class="col-sm-4">
						<form:input  type="text" class="form-control" path="entity.serviceName" id="serviceName" disabled="true"></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="water.meterDet.appliNo"/></label>
					<div class="col-sm-4">
						<form:input  type="text" class="form-control" disabled="true"
							path="entity.applicationNumber"></form:input>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="water.meterDet.appliName"/></label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control" disabled="true"
							path="entity.applicantName"></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="water.meterDet.appDate"/></label>
					<div class="col-sm-4">
					  <fmt:formatDate pattern="dd/MM/yyyy" value="${command.entity.applicationDate}" var="appDate" />
						<form:input  type="text" class="form-control" disabled="true"
							path="" value="${appDate}" id="appldate"></form:input>
					</div>
				</div>
				
				
				
				<h4 class="margin-top-0"><spring:message code="water.physConn.dt"/></h4>
				<div class="form-group">
				<label class="label-control col-sm-2"><spring:message code="weter.physConn"/>:</label>
					<div class="col-sm-4">
					<form:checkbox name="Metered" path="entity.physicalConnFlag" value="Y" id="physicalConnFlag" onclick="flagCheck();" /> 
					</div>
					
					<label class="label-control col-sm-2"><spring:message code="water.physConn.dt"/></label>
					<div class="col-sm-4">
					
					 <form:input path="entity.connectionDate" cssClass="cal form-control lessthancurrdate"  id="connDate" />
					
					 </div>
				</div>
				
				
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit" onclick="savePhysicalConnectionDateEntry(this)"><spring:message code="water.btn.submit"/></button>
					<spring:url var="cancelButtonURL" value="PhysicalDateEntry.html" />
					 	<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message code="water.btn.back"/></a>
				</div>
			</form:form>
		</div>
	</div>
	<!-- End of info box -->


 