 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/water/meterDetailsEntry.js"></script>

<script>
$(function() {
prepareMeterdetailDateTag();
});

$('.lessthancurrdate').datepicker({
	dateFormat: 'dd/mm/yy',	
	changeMonth: true,
	changeYear: true,
	minDate: $("#pcDate").val(),
	maxDate: '-0d',
	yearRange: "-100:-0"
});


jQuery('.hasNumber').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
  
});


function prepareMeterdetailDateTag() {
	var dateFields = $('.lessthancurrdate');
	dateFields.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
}


</script>




<apptags:breadcrumb></apptags:breadcrumb>


	<div class="widget" id="widget">
		<div class="widget-header">
			<h2><spring:message code="water.meterDet.entry" /></h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i	class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith"/><i class="text-red-1">*</i><spring:message code="water.ismandtry"/> 				</span>
			</div>
			<form:form action="MeterDetailsEntry.html"	class="form-horizontal form" name="frmMeterDetailsEntry"	id="frmMeterDetailsEntry">
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
				
				 <fmt:formatDate pattern="dd/MM/yyyy" value="${command.entity.pcDate}" var="pcdates" />
						<form:hidden 
							path="" value="${pcdates}" id="pcDate"></form:hidden>
				
				
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="water.meterDet.appliName"/></label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control" disabled="true"
							path="entity.applicantName"></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="water.meterDet.appDate"/></label>
					<div class="col-sm-4">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${command.entity.applicationDate}" var="date" />
						<form:input  type="text" class="form-control" disabled="true"
							path="entity.applicationDate" value="${date}"></form:input>
					</div>
				</div>
				
				
				
				<h4 class="margin-top-0"><spring:message code="water.meterDet.entry"/></h4>
				
				
				
				<div class="form-group">
				<label class="label-control col-sm-2"><spring:message code="water.meterDet.mtrMake"/></label>
					<div class="col-sm-4">
					
					 <form:input path="entity.meterMake" cssClass="form-control hasCharacter" maxlength="39" />
					 </div>
					<label class="label-control col-sm-2 required-control"><spring:message code="water.meterDet.metNo"/></label>
					<div class="col-sm-4">
						<form:input path="entity.meterNumber" cssClass="form-control mandClassColor hasNumber " maxLength="19" />
						
					</div>
					
				</div>
			
				
				<div class="form-group">
				
					<label class="label-control col-sm-2 required-control"><spring:message code="water.meterDet.initalmtrRead"/></label>
					<div class="col-sm-4">
						<form:input path="entity.initialMeterReading" cssClass="form-control mandClassColor hasNumber" maxLength="7" />
						
					</div>
					
					<label class="label-control col-sm-2 required-control"><spring:message code="water.meterDet.mtrMaxRead"/></label>
					<div class="col-sm-4">
						 <form:input path="entity.meterMaxReading" cssClass="form-control hasNumber"  maxLength="7" />
					</div>
				</div>
				
				<div class="form-group">
				
				<label class="label-control col-sm-2 required-control"><spring:message code="water.meterDet.instlDt"/></label>
					<div class="col-sm-4">
					 <form:input path="entity.meterInstallationDate" cssClass="lessthancurrdate cal form-control"  id="" />
					 
					 </div>
				
				
					<label class="label-control col-sm-2"><spring:message code="water.meterDet.mtrCost"/></label>
					<div class="col-sm-4">
						 <form:input path="entity.meterCost" cssClass="form-control hasNumber" maxLength="10" value="0.0" />
					 </div>
				</div>
				<div class="form-group">
				
					
					
				<label class="label-control col-sm-2 required-control"><spring:message code="water.meterDet.ownerShip"/></label>
				<div class="col-sm-4">
				<form:select id="serviceName" path="entity.meterOwnerShip" cssClass="form-control mandClassColor " >
						<form:option value="" >--Select--</form:option>
						<c:forEach items="${ownerShip}" var="ownerShip">
							<form:option value="${ownerShip.lookUpId}">${ownerShip.descLangFirst}</form:option>					
						</c:forEach>
				</form:select>
				
				</div> 
				</div>
				
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit" onclick="saveMeterDetailsEntry(this)"><spring:message code="water.meterDet.submit"/></button>
					<spring:url var="cancelButtonURL" value="MeterDetailsEntry.html" />
					 	<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message code="water.btn.back" text="Back"/></a>
				</div>
			</form:form>
		</div>
	</div>
	<!-- End of info box -->


 