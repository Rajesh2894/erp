<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<script type="text/javascript">
var totalDocNumber =0;
$(document).ready(function() {
	
	   $('#app1').prop("disabled",true);
	   $('#app2').prop("disabled",true);
	   $('#app3').prop("disabled",true);
	  // $('#locationDiv').hide();
	   
	  $(".radioBtnId").each(function(i){
		totalDocNumber = i;
      });

	  $('#depID').change(function(){
          if($(this).val() == 0){
        	   $("#entity\\.agencyLocation").val("");
           }else{
        	   $("#entity\\.agencyLocation").val($("#depID :selected").text());
            }
            $('#locationDiv').hide();
    	 });
 });


  

 function myFun(){
	
	var flag  = 0;
	var count = 0;
	$(".radioBtnId").each(function(i){
		
		if($("#reject"+ i).is(":checked")){
			flag = 1;
			count = i;
		}
		
		if($("#approve"+ i).is(":checked")){			
			count = i;
		}
	});
	
	$("#app3").prop("disabled",false);
   if(flag == 1 && (count == totalDocNumber)){
	   $("#app1").prop("checked",false);
	   $("#app1").prop("disabled",true);
	   $("#app2").prop("disabled",false);
	   
   }else if(count==totalDocNumber){
	   
	   $("#app1").prop("checked",true);
	   $("#app1").prop("disabled",false);
	   $("#app2").prop("disabled",true);
	   $("#app3").prop("disabled",true);
	   
   }
	
}
	
	
function openViewModeForm(formName,actionParam)
{
	
	var theForm	=	'#'+formName;
	
	var divName	=	formDivName;
	
	var url	=	$(theForm).attr('action');
	
	if (!actionParam) 
	{
		//Do nothing if no action param is set.
	}
	else
	{
		url+='?'+actionParam;
	}
	
	var requestData = __serializeForm(theForm);
	
	var ajaxResponse	=	doAjaxLoading(url, requestData, 'html', divName);
	
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	
	prepareTags();
	
	$(divName).show();
	
}

function saveAuthorizationDetail(obj){
	var errorList = [];

	var savemsg=getLocalMessage('eip.agencyauth.savemsg');

	errorList = validateActionButton(errorList);

	if (errorList.length == 0) {
		$("div.error-div").hide();
	
	 saveOrUpdateForm(obj,savemsg, 'AgencyAuthorization.html','');
	}else {
		showError(errorList);
		$("div.error-div").show();	
	 }
}


function validateActionButton(errorList) {

	if(!$("#app1").is(":checked") && !$("#app2").is(":checked") && !$("#app3").is(":checked")){
		//alert('Please Seelct Action for authorization');
		errorList.push(getLocalMessage('eip.agenyAuth.selectAnyRadioBtn.msg'));
	}
	
	/* if($("#app1").val()=="A"){
	  alert('Group Id Mandatory');	
	} */
	 return errorList;
  }
function showError(errorList) {
	
	
	var errMsg = '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
    $('.error-div').html(errMsg);
}

function openPopupForLocation(url){
	$('#locationDiv').show();
} 
</script>


	<div class="widget">
	<div class="widget-header">
		<h2><spring:message code="eip.agencyauthorization"/></h2>
	</div>
	
	<div  class="widget-content padding" id="content">
	<div class="mand-label clearfix"><span>Field with <i class="text-red-1">*</i> is mandatory</span></div>
		<form:form action="AgencyAuthorizationForm.html" name="frmAgencyAuthorizationForm" id="frmAgencyAuthorizationForm" class="form-horizontal">	
				
		<jsp:include page="/jsp/tiles/validationerror.jsp"/>
	 	<div class="error-div" style="display:none;"></div>			
		<c:set value="${command.getAgencyCode()}" var="agnCode"/>	
		<%-- <c:out value="${command.agencyCode}"/>	 testing --%>	
	 
	      <c:if test="${(agnCode eq 'CFC') || (agnCode eq 'LGL')  || (agnCode eq 'RL') || (empty agnCode)}">
				<div class="form-group">							
					<label class="col-sm-2 control-label"><spring:message code="eip.agency.name" /> :</label>
					<div class="col-sm-4">
						<form:input path="" value="${command.entity.agencyName}" cssClass="form-control" disabled="true"/>
					</div>	
					<label class="col-sm-2 control-label"><spring:message code="eip.agency.type" /> :</label>
					<div class="col-sm-4">
						<form:input path="" value="${command.agencyType}" cssClass="form-control" disabled="true" />
					</div>
				</div>
				
				
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="eip.agency.owner.name" /> :</label>
					<div class="col-sm-4">
					 	<form:input path="" value="${command.entity.empname}" cssClass="form-control" disabled="true"/>
				    </div>
				    
					<label class="col-sm-2 control-label "><spring:message code="eip.agency.OwnerMname" /> :</label>
					<div class="col-sm-4">
						<form:input path="" value="${command.entity.empMName}" cssClass="form-control" disabled="true"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message code="eip.agency.OwnerLname" /> :</label>
					<div class="col-sm-4">
						<form:input path="" value="${command.entity.empLName}" cssClass="form-control" disabled="true"/>
					</div>
					
					<label class="col-sm-2 control-label "><spring:message code="eip.agency.reg.dob" /> :</label>
					<div class="col-sm-4">
						<input class="form-control" type="text" readonly="readonly"  value="<fmt:formatDate value="${command.entity.empdob}" pattern="dd-MM-YYYY" />" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="eip.agency.reg.mobNo" /> :</label>
					<div class="col-sm-4">
						<form:input path="" value="${command.entity.empmobno}" cssClass="form-control" disabled="true"/>
					</div>
					<label  class="col-sm-2 control-label"><spring:message code="eip.agency.reg.mail" /> :</label>
					<div class="col-sm-4">
						<form:input path="" value="${command.entity.empemail}" cssClass="form-control" disabled="true" />
					</div>
				</div>
				
				<div class="form-group">
					<label  class="col-sm-2 control-label"><spring:message code="eip.agency.panNo" /> :</label>
					<div class="col-sm-4">
						<form:input path="" value="${command.entity.panCardNo}" cssClass="form-control" disabled="true"/>
					</div>
					<label  class="col-sm-2 control-label"><spring:message code="eip.agency.reg.address1" /> :</label>
					<div class="col-sm-4">
						<form:input path="" value="${command.entity.empAddress}" cssClass="form-control" disabled="true"/>
					</div>
				</div>
					
				<c:if test="${agnCode eq 'HD' }">                        
					<div class="form-group">							
						<label class="col-sm-2 control-label required-control" > <spring:message code="eip.agency.hospital.type" text="Hospital Type " /> :</label>
						<div class="col-sm-4">	
							<form:input path="" value="${command.entity.hospitalTypeName}" maxlength="12" disabled="true" readonly="true" cssClass="disablefield" tabindex="16"/>
						</div>
						<label class="col-sm-2 control-label required-control" ><spring:message code="eip.agency.hospital.code" text="Hospital Code" /> :</label>
						<div class="col-sm-4">	
							<form:input path="" value="${command.entity.hospitalCode}" maxlength="3" readonly="true" disabled="true" cssClass="disablefield" tabindex="16"/>
						</div>
					</div>
				</c:if>
					<div class="form-group">
						<label id="locationDiv"  class="col-sm-2 control-label required-control" ><spring:message code="eip.agency.location" /> :</label>
						<div class="col-sm-4">	    
						    <form:select path="entity.dpDeptid" cssClass="form-control">
							    <form:option value="0" label="Select Location" />
							    <form:options itemLabel="dpDeptdesc" items="${locationList}" itemValue="dpDeptid"/> 
						    </form:select>
						</div>
					 </div>
				
				
					<c:if test="${agnCode eq 'LGL' }">
				
						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message code="eip.agency.interviewdate" text="" /> :</label>
							<div class="col-sm-4">
								<apptags:dateField fieldclass="datepicker" showDefault="" datePath="" cssClass="form-control"  />
						  </div>
					
						<div>
							<label class="col-sm-2 control-label required-control" ><spring:message code="eip.agency.appointdate" text="" /> :</label>
						</div>	
						<div class="col-sm-4">
							<apptags:dateField fieldclass="datepicker" datePath="" cssClass="mandClassColor" />
							<!-- <span class="mand">*</span> -->
						</div>
					</div>
				</c:if>
	 </c:if>	
	         <div class="form-group">
				<label class="col-sm-2 control-label required-control"> <spring:message code="eip.agency.grpid"/> :</label>
				<div class="col-sm-4">
				    <form:select path="entity.gmid" cssClass="form-control">
				    <form:option value="0"><label><spring:message code="eip.agency.sel.grp" /></label></form:option>
				    <form:options items="${agencyList}"/>
				    </form:select>
				</div>
			</div>		
<!-- ------------------------Advertising Agency Code Start-------------------------------------------------- -->			
  <%-- <c:if test="${agnCode eq 'AHD' }">
  
			    <div class="regheader"><spring:message code="advertise.agencyreg.agencyDetails" /></div>
		
						<div class="form-elements">
									<div class="element">
										<label for=""><spring:message
												code="advertise.dataEnttry.agencyName" /> : </label>
									   <form:input path="" value="${command.agnMaster.agnAgencyName}" readonly="true" cssClass="disablefield"/>
									</div>
									 
						</div>
						<div class="form-elements">
									<div class="element">
										<label for=""><spring:message
												code="advertise.agencyreg.propertyNo" /> : </label>
										 <form:input path="" value="${command.propertyNo}" readonly="true" cssClass="disablefield"/>
									</div>
									<div class="element">
										<label for=""><spring:message
												code="advertise.agencyreg.tradeLicenseNo" /> : </label>
										  <form:input path="" value="${command.licenceNo}" readonly="true" cssClass="disablefield"/>
									</div>
						</div>
								<div class="form-elements">
									<div class="element">
										<label for=""><spring:message
												code="advertise.agencyreg.tinNo" /> : </label>
										 <form:input path="" value="${command.agnMaster.agnAgencyTinNo}" readonly="true" cssClass="disablefield"/>
									</div>
									<div class="element">
										<label for=""><spring:message
												code="advertise.agencyreg.vatNo" /> : </label>
										 <form:input path="" value="${command.agnMaster.agnAgencyVatNo}" readonly="true" cssClass="disablefield"/>
									</div>
								</div>
								<div class="form-elements">
									   <div class="element">
										<label for=""><spring:message code="advertise.agencyreg.address" /> : </label>
										<form:input path="" value="${command.agnMaster.agnAgencyAddress}" readonly="true" cssClass="disablefield"/>
										
									  </div>
							    </div>
								<div class="form-elements">
									<div class="element">
										<label for=""><spring:message
												code="advertise.agencyreg.contactNo" /> : </label>
										<form:input path="" value="${command.agnMaster.agnAgencyContactNo}" readonly="true" cssClass="disablefield"/>
									</div>
									<div class="element">
										<label for=""><spring:message
												code="advertise.agencyreg.mobileNo" /> : </label>
										<form:input path="" value="${command.agnMaster.agnAgencyMobileNo}" readonly="true" cssClass="disablefield" />
									</div>
								</div>
								<div class="element">
										 <label
											for=""><spring:message code="advertise.agencyreg.emailId" /> : </label>
									<form:input path="" value="${command.agnMaster.agnAgencyEmail}" readonly="true" cssClass="disablefield"/>	
								</div> 
								<div class="form-elements">
									<div class="element">
										<label for=""><spring:message
												code="advertise.agencyreg.contactPerson" /> : </label>
										<form:input path="" value="${command.agnMaster.agnContactName}" readonly="true" cssClass="disablefield" />
									</div>
									<div class="element">
										<label for=""><spring:message
												code="advertise.agencyreg.contactNo" /> : </label>
										<form:input path="" value="${command.agnMaster.agnContactNo}" readonly="true" cssClass="disablefield"/>
									</div>
								</div>
							<!-- <div class="form-elements"> -->
                               
                                 <div class="element">
                                      <label for=""><spring:message code="advertise.agencyreg.remark" /> : </label>
                                  </div>
                                  <form:input path="" value="${command.agnMaster.agnAgencyRemarks}" readonly="true" cssClass="disablefield"/>
                                  
                                <!-- </div> -->
						
							<div class="regheader">
								<spring:message code="advertise.agencyreg.OwnerDetails" />
							</div>
							
								<div class="form-elements ">
									<div class="element"><label for=""><spring:message code="advertise.agencyreg.ownerName" /> : </label>
									<form:input path="" value="${command.agnMaster.agnOwnerName}" readonly="true" cssClass="disablefield"/>
									</div>
								 </div>
								 <div class="form-elements">
										<div class="element">
											<label for=""><spring:message code="advertise.agencyreg.address" /> : </label>
										
										<form:input path="" value="${command.agnMaster.agnOwnerAddress}" readonly="true" cssClass="disablefield"/>
							         </div>
							       </div>
								   <div class="form-elements">
										<div class="element">
											<label for=""><spring:message
													code="advertise.agencyreg.OwnercontactNo" /> : </label>
											<form:input path="" value="${command.agnMaster.agnContactNo}" readonly="true" cssClass="disablefield"/>
										</div>
									    <div class="element">
											<label for=""><spring:message
													code="advertise.agencyreg.OwnerMobileNo" /> : </label>
											<form:input path="" value="${command.agnMaster.agnOwnerMobileNo}" readonly="true" cssClass="disablefield"/>
									    </div>
								  </div>
								<div class="form-elements">
									<div class="element">
										<label for=""><spring:message
												code="advertise.agencyreg.OwnerEmailId" /> : </label>
										<form:input path="" value="${command.agnMaster.agnOwnerEmailId}" readonly="true" cssClass="disablefield"/>
									</div>
								</div>
								<div class="form-elements">
								    <div class="element">
										<label for=""><spring:message
												code="advertise.agencyreg.OwnerPanNo" /> : </label>
										<form:input path="" value="${command.agnMaster.agnOwnerPanNo}" readonly="true" cssClass="disablefield"/>
									</div>
								    <div class="element">
										<label for=""><spring:message
												code="advertise.agencyreg.OwnerAadharNo" /> : </label>
										<form:input path="" value="${command.agnMaster.agnOwnerAadharNo}" readonly="true" cssClass="disablefield"/>
									</div>
								</div>
								
								    
         <div class="regheader">
			<spring:message code="adv.angreg.additionalOwnerDetails" />
		</div>
     <table class="gridtable" >
				<tr align="center">
					<th><spring:message code="adv.agnreg.addOwnName" text="" /></th>
					<th><spring:message code="adv.agnreg.addOwnAddress" text="" /></th>
					<th><spring:message code="adv.agnreg.addOwnPhoneNo" text="" /></th>
					<th><spring:message code="adv.agnreg.addOwnMobileNo" text="" /></th>
					<th><spring:message code="adv.agnreg.addOwnPanNo" text="" /></th>
					<th><spring:message code="adv.agnreg.addOwnEmailId" text="" /></th>
					<th><spring:message code="adv.agnreg.addOwnAadharNo" text="" /></th>
				</tr>
		<c:forEach var="additionalOwner" items="${command.entity.listOfAdditionalAgencyOwners}" 
		                   varStatus="status">
			<tr>
				<td>${additionalOwner.aaoOwnerName}</td>
				<td>${additionalOwner.aaoOwnerAddress}</td>
				<td>${additionalOwner.aaoOwnerPhoneNo}</td>
				<td>${additionalOwner.aaoOwnerMobileNo}</td>
				<td>${additionalOwner.aaoOwnerPanNo}</td>
				<td>${additionalOwner.aaoOwnerEmailId}</td>
				<td>${additionalOwner.aaoOwnerAadharNo}</td>
			</tr>
		</c:forEach>
  </table>
</c:if>
	
		<c:if test="${command.agencyType eq 'Advertising Agency' }">
				<div class="regheader">
					<spring:message code="bnd.uploadDoc" />
				</div>
	   </c:if> --%>
<!-- ------------------------Advertising Agency Code End-------------------------------------------------- -->
<!-- ------------------------Hospital-------------------------------------------------- -->
<c:if test="${agnCode eq 'HD' }">                        
											
	<jsp:include page="/jsp/eip/agency/hospitalDocUploadView.jsp"></jsp:include>
						
</c:if>
<!-- ------------------------Hospital Code End-------------------------------------------------- -->				
<!-- ------------------------TECHNICAL PERSON Code START-------------------------------------------------- -->
 <jsp:include page="/jsp/eip/agency/tpTechnicalPersonView.jsp"></jsp:include>
<%-- <jsp:include page="/jsp/eip/agency/tpBuilderRegistrationView.jsp"></jsp:include> --%>
 <c:if test="${agnCode eq 'TP' }">
	<c:set value="${command.getCpdValue()}" var="cpdValue"/>
	<c:choose>
		<c:when test="${cpdValue eq 'B'}">
			<jsp:include page="/jsp/eip/agency/tpBuilderRegistrationView.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
			<jsp:include page="/jsp/eip/agency/tpTechnicalPersonView.jsp"></jsp:include>
		</c:otherwise>
	</c:choose>
</c:if> 
<!-- ------------------------TECHNICAL PERSON Code Code End-------------------------------------------------- -->
<c:if test="${empty command.allAgencyDoc}">
		<table  class="table table-hover table-bordered table-striped">
						<tr>
							<th ><spring:message code="eip.agency.srNo" /></th>
							<th><spring:message code="eip.agency.viewAttachments"/></th>
							<th><spring:message code="eip.agency.auth.view.status" text="Status"></spring:message></th>
							<th><spring:message code="eip.agency.auth.view.DocStatus" text="Document Status"></spring:message> </th>
							<th><spring:message code="eip.agency.auth.view.verify" text="Verify"></spring:message> </th>
							<th><spring:message code="eip.agency.auth.view.remark" text="Remarks"></spring:message></th>
			     		</tr>
		</table>			     		
</c:if>	
<c:if test="${empty command.allAgencyDoc }">
			
				<!-- <input type="hidden" name="entity.authStatus" value="s" />
				 -->
		<fieldset>
				<div class="form-elements clear margin_top_10"class="form-group">
				  <label for=""><form:radiobutton  id="app1" onmouseover="countRejectDoc()" path="entity.authStatus" value="A" /> 
				      <spring:message code="eip.agency.approve" /></label> 
				  <label for=""><form:radiobutton id="app2" path="entity.authStatus" value="R" disabled="false"/> 
				      <spring:message code="eip.agency.reject" /></label>			
				  <label for=""><form:radiobutton id="app3" path="entity.authStatus" value="H" disabled="false" /> 
				      <spring:message code="eip.agency.hold" /><span class="mand2">*</span></label>
				</div>
		</fieldset>
		
			<script>
				$('#app1').prop("disabled",false);
				$('#app2').prop("disabled",false);
				$('#app3').prop("disabled",false);
			</script>
	</c:if>


	
	
<c:if test="${not empty command.allAgencyDoc}">
	 <div class="table-responsive">
              <table class="table table-bordered table-striped">
						<tr>
							<th><spring:message code="eip.agency.srNo" /></th>
							<th><spring:message code="eip.agency.viewAttachments"/></th>
							<th><spring:message code="eip.agency.auth.view.status" text="Status"></spring:message></th>
							<th><spring:message code="eip.agency.auth.view.DocStatus" text="Document Status"></spring:message> </th>
							<th><spring:message code="eip.agency.auth.view.verify" text="Verify"></spring:message> </th>
							<th><spring:message code="eip.agency.auth.view.remark" text="Remarks"></spring:message></th>
			     		</tr>
				
	          <c:forEach items="${command.allAgencyDoc}"  var="lookUp" varStatus="lk">
	                    <tr>
	          			<%-- <form action="AgencyAuthorizationForm.html?Download" method="post" id="frm${idappender}_${status.count}" target="_blank" >
						</form> --%>
						
							<td>${lk.count}</td>
							<%--  <td class="row1">
								${lookUp.lookUpCode}
							</td> --%>
						 <td class="row1">
						 
						 <c:set var="links"
								value="${fn:split(lookUp.lookUpDesc,',')}" />
								<c:forEach items="${links}" var="download" varStatus="status">
									<apptags:filedownload filename="${lookUp.lookUpCode}" filePath="${download}" actionUrl="AgencyAuthorizationForm.html?Download"></apptags:filedownload>
								</c:forEach>
								</td> 
							
							<td>
								<c:choose>
									<c:when test="${lookUp.lookUpId eq '1' }">
									<spring:message code="eip.agency.auth.view.mandatory" text="Mandatory"/>
									</c:when>
						
									<c:when test="${lookUp.lookUpId eq '0' }">
									<spring:message code="eip.agency.auth.view.optional" text="Optional"/>
									</c:when>
								</c:choose>
							 </td>
							 <td> 
								<c:choose>
									<c:when test="${lookUp.lookUpType eq 'Y' }">
									   <spring:message code="eip.agency.auth.view.approved" text="Approved"/>
									</c:when>
									<c:when test="${lookUp.lookUpType eq 'N' }">
									   <spring:message code="eip.agency.auth.view.rejected" text="Rejected"/>
									</c:when>
									<c:when test="${lookUp.lookUpType eq null }">
									   <spring:message code="eip.agency.auth.view.pending" text="Pending"/>
									</c:when>
								</c:choose>
							</td>
							<td class="radioBtnId">
								<c:if test="${lookUp.lookUpType eq 'Y' }">
								<label class="radio-inline">
								  <form:radiobutton disabled="true"  value="Y" path="entity.cfcAttachments[${lk.count-1}].clmAprStatus"  id="approve${lk.count-1}" onclick="myFun()"/>
								  <spring:message code="eip.dept.auth.view.yes" text="Yes"/>
								</label>       
								<label class="radio-inline">
									<form:radiobutton disabled="true" value="N" path="entity.cfcAttachments[${lk.count-1}].clmAprStatus" id="reject${lk.count-1}" onclick="myFun()"/>
									<spring:message code="eip.dept.auth.view.no"/>
								</label>       
								</c:if>
								<c:if test="${lookUp.lookUpType eq 'N' }">								
								    <label class="radio-inline">
									    <form:radiobutton  value="Y" path="entity.cfcAttachments[${lk.count-1}].clmAprStatus"  id="approve${lk.count-1}" onclick="myFun()"/>
									    <spring:message code="eip.dept.auth.view.yes" text="Yes"/>
								    </label>
								    <label class="radio-inline">
									    <form:radiobutton  value="N" path="entity.cfcAttachments[${lk.count-1}].clmAprStatus" id="reject${lk.count-1}" onclick="myFun()"/>
									    <spring:message code="eip.dept.auth.view.no"/> 
								    </label>
								</c:if>
								<c:if test="${lookUp.lookUpType eq null }">
								      <label class="radio-inline">
										<form:radiobutton  value="Y" path="entity.cfcAttachments[${lk.count-1}].clmAprStatus"  id="approve${lk.count-1}" onclick="myFun()"/>
										<spring:message code="eip.dept.auth.view.yes" text="Yes"/>
								      </label>
								      <label class="radio-inline">
								      	<form:radiobutton value="N" path="entity.cfcAttachments[${lk.count-1}].clmAprStatus" id="reject${lk.count-1}" onclick="myFun()"/>
								        <spring:message code="eip.dept.auth.view.no"/> 
								      </label>  
								</c:if>
							</td>
							<td>
							    <c:if test="${lookUp.lookUpType eq 'Y' }">
								    <form:input path="entity.cfcAttachments[${lk.count-1}].clmRemark" disabled="=true" cssClass="form-control" readonly="true" />
								</c:if>
								<c:if test="${lookUp.lookUpType eq 'N' }">
								    <form:input  path="entity.cfcAttachments[${lk.count-1}].clmRemark" cssClass="form-control" />								
								</c:if>								 
								<c:if test="${lookUp.lookUpType eq null }">
								    <form:input path="entity.cfcAttachments[${lk.count-1}].clmRemark" cssClass="form-control" />								
								</c:if>								
							</td>
					</tr>
			 </c:forEach>
	   </table>
   </div>
				<div class="form-group">
				  <label class="radio-inline">
				  	<form:radiobutton  id="app1" onmouseover="countRejectDoc()" path="entity.authStatus" value="A" /> 
				    <spring:message code="eip.agency.approve" />
				  </label> 
				  <label class="radio-inline">
				  	<form:radiobutton id="app2" path="entity.authStatus" value="R" disabled="false"/> 
				    <spring:message code="eip.agency.reject" />
				  </label>			
				  <label class="radio-inline">
				  	<form:radiobutton id="app3" path="entity.authStatus" value="H" disabled="false" /> 
				    <spring:message code="eip.agency.hold" />
				  </label>
				</div>
</c:if>	 

			  <div id="123" class="text-center">
				    <input type="button" onclick=" return saveAuthorizationDetail(this);" value="<spring:message code="eip.commons.submitBT"/>"
					class="btn btn-success btn-submit" />
				     <apptags:backButton url="AgencyAuthorization.html" />
			 </div>
          </form:form>
	    </div>
	 </div>
