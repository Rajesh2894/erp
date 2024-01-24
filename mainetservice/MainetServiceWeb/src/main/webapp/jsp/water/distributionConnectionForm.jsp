 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/cfc/scrutiny.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#codDwzid1').attr('disabled',true);
	$('#codDwzid2').attr('disabled',true);
});

function saveDistributionDetails(element)
{
	  var appId='${command.lableValueDTO.applicationId}';
	   var serviceId= '${command.serviceId}';
	  var labelId='${command.lableValueDTO.lableId}';
	  saveOrUpdateForm(element,"Your application saved successfully!", '', 'save');
	  if($("#error").val()!='Y')
      {
	
	      loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule',appId,labelId,serviceId); 
	   } 


}
function calculateCCNSize(element)
	{
		var	formName =	findClosestElementId(element, 'form');
		var theForm	=	'#'+formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
	    var URL = 'DistributionConnectionForm.html?CalculateCCNSize';
        var returnData =__doAjaxRequest(URL,'POST',requestData, false);
        var divName = '#validationDiv';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		$(divName).show();
		
	}

</script>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="water.dist.det"/></h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith"/> <i class="text-red-1">*</i><spring:message code="water.ismandtry"/> 
				</span>
			</div>
			<form:form action="DistributionConnectionForm.html"
				class="form-horizontal form" name="frmDistributionDetails"
				id="frmDistributionDetails">
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
									pathPrefix="csmrInfo.codDwzid" isMandatory="false" hasLookupAlphaNumericSort="false" hasSubLookupAlphaNumericSort="true" cssClass="form-control" showAll="true"/>
  
             </div>
				<h4><spring:message code="water.dist.legalStatus"/></h4>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message code="water.status"/></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="WLI" />
						<form:select path="csmrInfo.csListatus" class="form-control" id="">
							<form:option value="0"><spring:message code="water.dist.connStatus"/></form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message code="water.dist.connType"/></label>
							<div class="col-sm-4">
											<c:set var="baseLookupCode" value="WCT" /> <form:select
										path="csmrInfo.csCcntype" class="form-control"
										id="">
											<form:option value="0"><spring:message code="water.dist.sel.connType"/></form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
									</form:select>
											</div>
				</div>
				<h4><spring:message code="water.dist.linedet"/></h4>
				
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message code="water.noOfFamily"/></label>
					<div class="col-sm-4">
						
							<form:input  type="text" class="form-control hasNumber"
								path="csmrInfo.noOfFamilies"></form:input>
							
						
					</div>
					<label class="col-sm-2 control-label " for="newnoOfTaps"><spring:message
												code="water.NoofTaps" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control hasNumber"
												path="csmrInfo.csNooftaps" ></form:input>
										</div>
				
				</div>
				
				<c:if test="${command.noOfUsageLabel ne null  && not empty command.noOfUsageLabel}">
				<div class="form-group">
					<label class="col-sm-2 control-label required-control">${command.noOfUsageLabel}</label>
					<div class="col-sm-4">
					
							<form:input name="" type="text" class="form-control hasNumber"
								path="csmrInfo.csNoofusers"></form:input>
						
					</div>
				</div>
				</c:if>
				
				<div class="form-group">
				<apptags:lookupFieldSet baseLookupCode="SLN" hasId="true" showOnlyLabel="false"
									pathPrefix="codId" isMandatory="false" hasLookupAlphaNumericSort="false" hasSubLookupAlphaNumericSort="false" cssClass="form-control" showAll="false"/>
					</div>
					<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="water.dist.discharge"/></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input type="text" class="form-control hasNumber"
								path="csmrInfo.distribution.rcDistccndif"></form:input>
							<span class="input-group-addon" id="basic-addon1">M<sup>3</sup>/day
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="water.dist.pressure"/></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input name="" type="text" class="form-control hasNumber"
								path="csmrInfo.distribution.rcDistpres"></form:input>
							<span class="input-group-addon" id="basic-addon1">Kg/cm<sup>2</sup></span>
						</div>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="water.dist.length"/></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input name="" type="text" class="form-control hasNumber"
								path="csmrInfo.distribution.rcLength"></form:input>
							<span class="input-group-addon" id="basic-addon1">Mtr.</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 col-xs-12 control-label"><spring:message code="water.dist.period"/></label>
						<div class="col-sm-4">
						<form:input type="text" class="form-control hasNumber" path="csmrInfo.distribution.rcTotdisttime"></form:input>
		    </div>
					
					<!--  <label class="col-sm-2 control-label"><button type="button" class="btn btn-success" onclick="calculateCCNSize(this);">Calculate CCN Size</button></label> -->
					  <label class="col-sm-2 control-label required-control"><spring:message code="water.ConnectionSize"/></label>
							
                            <c:set var="baseLookupCode" value="CSZ" />
                             <apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="csmrInfo.csCcnsize" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="select" isMandatory="true" />
				</div>
			<%-- 	<div class="form-group">
				 <label class="col-sm-2 control-label required-control">Institute Type </label>
							    <div class="col-sm-4">
											 <form:select
										path="csmrInfo.distribution.instId" class="form-control"
										id="">
											<form:option value="0">Select Institute Type</form:option>
											<c:forEach items="${command.instituteList}"
												var="lookUp">
												<form:option value="${lookUp.instId}"
													code="">${lookUp.instType}</form:option>
											</c:forEach>
									</form:select>
											</div>
											</div> --%>
				<c:set var="appId" value="${command.lableValueDTO.applicationId}"/>
				<c:set var="labelId" value="${command.lableValueDTO.lableId}"/>
				<c:set var="serviceId" value="${command.serviceId}"/>
			    <form:hidden path="hasError" id="error"/> 
				
				<div class="text-center padding-top-10">
				<button type="button" class="btn btn-success btn-submit" onclick="saveDistributionDetails(this)"><spring:message code="water.btn.submit"/></button>
					 <input type="button" onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${appId}','${labelId}','${serviceId}')"
						class="btn btn-danger" value="<spring:message code="water.btn.back"/>">
				</div>
			</form:form>
		</div>
	</div>
	<!-- End of info box -->
</div>


 