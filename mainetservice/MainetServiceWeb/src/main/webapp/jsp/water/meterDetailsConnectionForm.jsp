 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/cfc/scrutiny.js"></script>

<script type="text/javascript">
$(document).ready(function(){
  $('select[name="csmrInfo.codDwzid1"]').attr('disabled',true); 
	  $('select[name="csmrInfo.codDwzid2"]').attr('disabled',true); 


});

function saveMeterDetails(element)
{
	 var appId='${command.lableValueDTO.applicationId}';
		var serviceId= '${command.serviceId}';
		var errorList = [];
		var labelId='${command.lableValueDTO.lableId}';
		var csMeteredccn= $('#connectionSize').val();
		if(csMeteredccn == null){
			errorList.push(getLocalMessage('water.meter.status'));
		}
	
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		} else {
			saveOrUpdateForm(element, "Your application saved successfully!",
					'', 'save');

			if ($("#error").val() != 'Y') {
				loadScrutinyLabel(
						'ScrutinyLabelView.html?setViewDataFromModule', appId,
						labelId, serviceId);
			}

		}

	}
</script>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="water.met.det"/></h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith"/><i class="text-red-1">*</i> <spring:message code="water.ismandtry"/>			</span>
			</div>
			<form:form action="MeterDetailsConnectionForm.html"
				class="form-horizontal form" name="frmMeterDetails"
				id="frmMeterDetails">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<h4 class="margin-top-0"><spring:message code="water.form.appdetails"/></h4>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="water.road.service"/></label>
					<div class="col-sm-4">
						<form:input  type="text" class="form-control" path="serviceName" id="serviceName" disabled="true"></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="water.road.applNo"/></label>
					<div class="col-sm-4">
						<form:input  type="text" class="form-control" disabled="true"
							path="csmrInfo.applicationNo"></form:input>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="water.road.applName"/></label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control" disabled="true"
							path="applicantFullName"></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="water.road.applDate"/></label>
					<div class="col-sm-4">
						<form:input  type="text" class="form-control" disabled="true"
							path="csmrInfo.csApldate"></form:input>
					</div>
				</div>
				<div class="form-group"> 
                 <apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true" showOnlyLabel="false"
									pathPrefix="csmrInfo.codDwzid" isMandatory="false" hasLookupAlphaNumericSort="true" hasSubLookupAlphaNumericSort="true" cssClass="form-control" showAll="true"/>
  
                                </div>
				
				
				<h4><spring:message code="water.met.metOrNonmet"/></h4>
				<div class="form-group">
				<label class="col-sm-2 control-label required-control"><spring:message code="water.met.status"/></label>
							<div class="col-sm-4">
				<c:set var="baseLookupCode" value="WMN" /> <form:select
										path="csmrInfo.csMeteredccn" class="form-control"
										id="connectionSize">
											<form:option value="0"><spring:message code="water.met.selStatus"/></form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
									</form:select>
				
					<%-- <label class="checkbox-inline"> <form:radiobutton
							name="Metered" path="csmrInfo.csMeteredccn" value="Y"/> Metered
					</label> <label class="checkbox-inline"> <form:radiobutton
							name="Metered" path="csmrInfo.csMeteredccn" value="N"/> Non-Metered
					</label> --%>
				</div>
				</div>
				
				<c:set var="appId" value="${command.lableValueDTO.applicationId}"/>
				<c:set var="labelId" value="${command.lableValueDTO.lableId}"/>
				<c:set var="serviceId" value="${command.serviceId}"/>
				<form:hidden path="hasError" id="error"/> 
				
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit" onclick="saveMeterDetails(this)"><spring:message code="water.btn.submit"/></button>
					 <input type="button" onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${appId}','${labelId}','${serviceId}')" 
				 class="btn btn-danger" value="<spring:message code="water.btn.back"/>">
				</div>
			</form:form>
		</div>
	</div>
	<!-- End of info box -->
</div>

 