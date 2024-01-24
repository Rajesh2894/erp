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
<!-- <script  src="js/water/water.js"></script> -->

<script  src="js/water/changeOfOwner.js"></script> 

<script  src="js/mainet/file-upload.js"></script>

<script >
$(document).ready(function(){
	$('#ChangeOfOwnershipId').validate({
		   onkeyup: function(element) {
	       this.element(element);
	       console.log('onkeyup fired');
	 },
	       onfocusout: function(element) {
	       this.element(element);
	       console.log('onfocusout fired');
	}});
	$("#povertyLineId").change(function(e) {
		
		if ( $("#povertyLineId").val() == 'Y') {
		$("#bpldiv").show();
		 $("#bplNo").data('rule-required',true);
		}
		else
		{
		$("#bpldiv").hide();
		 $("#bplNo").data('rule-required',false);
		}
		});
	if($('#povertyLineId').val()=='N')
	 {
$("#bpldiv").hide();
	 }
if($('#povertyLineId').val()=='Y')
{
	 $("#bpldiv").show();
	 $("#bplNo").data('rule-required',true);
}
else
	 {
	 $("#bpldiv").hide();
	 $("#bplNo").data('rule-required',false);
	 }
	$('.margin-bottom-0').hide();
	$('#trmGroup1').attr('disabled',true);
	$('#trmGroup2').attr('disabled',true); 
	$('#conSize').attr('disabled',true);
	$('#conName').attr('disabled',true);
	$('#oldAdharNo').attr('disabled',true);
	var errorFlag = $('#errorFlag').val();
	
	if ($('#bplNo').val() == '') {
		
		$('#bpldiv').hide();
	} else {
		$('#bpldiv').show();
	}
	

    if(errorFlag=="Y"){
	  $('#searchConnection').attr('disabled',false);
     } else if(errorFlag=="N"){
	  $('#searchConnection').attr('disabled',true);
     }
	
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

function resetData()
{
	$("#conNum").val("");
	$("#conName").val("");
	$("#conSize").val("0");
	$("#oldAdharNo").val("");
	$('#searchConnection').attr('disabled',false);
}



function hasNumberField(index){
	var s=$("#caoNewUID_"+index).val();
	$("#caoNewUID_"+index).val(s.replace(/[^0-9]/g,''));
}
function hasCharacterFField(index){
	var s=$("#caoNewFName_"+index).val();
	$("#caoNewFName_"+index).val(s.replace(/[^a-z A-Z]/g,''));
}
function hasCharacterMField(index){
	var s=$("#caoNewMName_"+index).val();
	$("#caoNewMName_"+index).val(s.replace(/[^a-z A-Z]/g,''));
}
function hasCharacterLField(index){
	var s=$("#caoNewLName_"+index).val();
	$("#caoNewLName_"+index).val(s.replace(/[^a-z A-Z]/g,''));
}

</script>
<c:if test="${command.hasValidationErrors()}">
	<script >
		if ($('#conNum').val() !='' ) {
			$('#searchConnection').attr('disabled',true);
			$('#confirmToProceedId').attr('disabled',true);
		}
	
	</script>
</c:if>

<div  id="fomDivId">

<ol class="breadcrumb">
	<li><a href="CitizenHome.html"><i class="fa fa-home"></i> Home</a></li>
	<li><spring:message code="" text="Water" /></li>
	<li><spring:message code="water.head.changeOwner" /></li>
</ol>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.head.changeOwner" />
			</h2>
			<apptags:helpDoc url="ChangeOfOwnership.html"></apptags:helpDoc>
			
		</div>
		
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div>
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"><spring:message code="water.Error"/></label></li>
				</ul>
			</div>

			<form:form action="ChangeOfOwnership.html" method="POST" class="form-horizontal" id="ChangeOfOwnershipId" >
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="changeOwnerResponse.errorFlag" id="errorFlag"/>
				<form:hidden path="" value="${command.isFree}" id="FreeService" />
				<div class="panel-group accordion-toggle" id="accordion_single_collapse">

				<%-- 	<jsp:include page="/jsp/mainet/applicantDetails.jsp" /> --%>
					
					 	 <apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail>
							
				
					<div class="panel panel-default">
							  	<div class="panel-heading">
							  		<h4 class="panel-title">
							  			<a data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" href="#Old_Owner_Details">
							  				<spring:message code="water.changeOwner.oldDetails" />
							  			</a>
							  		</h4>
							  	</div>
							 	<div id="Old_Owner_Details" class="panel-collapse collapse">
								<div class="panel-body">
									<div id="oldOwner">			
										<div class="form-group">
											<label class="col-sm-2 control-label required-control" for="conNum"><spring:message
													code="water.ConnectionNo"></spring:message></label>
											<div class="col-sm-4">
												<form:input path="changeOwnerResponse.connectionNumber"
													type="text" class="form-control disablefield" id="conNum"
													readonly="false" data-rule-required="true"></form:input>
											</div>
											<div class="col-sm-6">
												<button type="button" value="Search" class="btn btn-blue-2"
													id="searchConnection">
													<i class="fa fa-search"></i> <spring:message code="searchBtn"/>
												</button>
												<input type="button" class="btn btn-warning" onclick="resetData()"
													value=<spring:message code="rstBtn"/> />
											</div>
										</div>
										<jsp:include page="/jsp/water/oldOwnerDetails.jsp" />	
									</div>
								</div>
								</div>
								</div>
			
				<div class="panel panel-default">
				  	<div class="panel-heading">
				  		<h4 class="panel-title">
				  			<a data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" href="#New_Owner_Details">
				  				<spring:message code="water.changeOwner.newDetails" />
				  			</a>
				  		</h4>
				  	</div>
				 		<div id="New_Owner_Details" class="panel-collapse collapse">
							<div class="panel-body">
								<div id="newOwner">
				<div class="form-group">
						<label class="col-sm-2 control-label required-control" for="ownerTransferMode"><spring:message
								code="water.transferMode" /></label>
						<c:set var="baseLookupCode" value="TFM" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="changeOwnerMaster.ownerTransferMode" cssClass="form-control"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="applicantinfo.label.select" isMandatory="true" />						
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label required-control" for="cooNotitle"><spring:message
								code="water.title" /></label>
						<c:set var="baseLookupCode" value="TTL" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="changeOwnerMaster.cooNotitle" cssClass="form-control"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="applicantinfo.label.select" isMandatory="true" />
						<label class="col-sm-2 control-label required-control" for="changeOwnerMaster.cooNoname"><spring:message
								code="water.owner.details.fname"></spring:message></label>
						<div class="col-sm-4">
							<form:input path="changeOwnerMaster.cooNoname" type="text"
								class="form-control hasSpecialChara" maxlength="100" data-rule-required="true"></form:input>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label" for="changeOwnerMaster.cooNomname"><spring:message
								code="water.owner.details.mname"></spring:message></label>
						<div class="col-sm-4">
							<form:input path="changeOwnerMaster.cooNomname" type="text"
								class="form-control hasSpecialChara" maxlength="100"></form:input>
						</div>
						<label class="col-sm-2 control-label required-control" for="changeOwnerMaster.cooNolname"><spring:message
								code="water.owner.details.lname"></spring:message></label>
						<div class="col-sm-4">
							<form:input path="changeOwnerMaster.cooNolname" type="text"
								class="form-control hasSpecialChara" maxlength="100" data-rule-required="true"></form:input>
						</div>
					</div>

					<div class="form-group">

						<label class="col-sm-2 control-label required-control" for="newGender">
						<spring:message code="water.owner.details.gender" /></label>

						<div class="col-sm-4">
							<c:set var="baseLookupCode" value="GEN" />
								<form:select path="changeOwnerMaster.gender" class="form-control" id="newGender" data-rule-required="true">
									<form:option value=""><spring:message code="water.select" /></form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
											<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
								</form:select>
						</div>
						
						<label class="col-sm-2 control-label" for="newAdharNo"><spring:message
								code="water.aadhar" /></label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control hasNumber"
								path="changeOwnerMaster.ConUidNo" id="newAdharNo" maxlength="12"></form:input>
						</div>

					</div>
					<div class="form-group">
							<label class="col-sm-2 control-label" for="remark"><spring:message
								code="water.remark" /></label>
						<div class="col-sm-10">
							<form:textarea type="text" class="form-control"
								path="changeOwnerMaster.cooRemark" id="remark" maxlength="200"></form:textarea>
						</div> 
					</div>
					
				</div>
							</div>
						</div>
				</div>
						
		<div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title">
                  	<a data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" href="#Additional_Owners">
                  		<spring:message code="water.additionalOwner" />
                  	</a>
                  </h4>
                </div>
                <div id="Additional_Owners" class="panel-collapse collapse">
					<div class="panel-body">
					<c:set var="d" value="0" scope="page" />
					<div class="table-responsive">
		<table class="table table-bordered table-striped" id="customFields">
		
			<tbody>
			
				<tr>
					<th width="120"><spring:message code="water.title" /></th>
					<th><spring:message code="water.owner.details.fname" /></th>
					<th><spring:message code="water.owner.details.mname" /></th>
					<th><spring:message code="water.owner.details.lname" /></th>
					<th width="130"><spring:message code="water.owner.details.gender" /></th>
					<th><spring:message code="water.aadhar" /></th>
					<c:if test="${command.enableSubmit ne true}">	 
					<th><spring:message code="water.add/remove" /></th>
					</c:if>
			  </tr>
			  
	
			<c:if test="${command.enableSubmit ne true}">	  	
			 <tr class="appendableClass">			
				<td> <input type="hidden" id="srNoId_${d}" value="">
				<c:set var="baseLookupCode" value="TTL" />
					<form:select path="additionalOwners[${d}].caoNewTitle" class="form-control" id="caoNewTitle_${d}">
						<form:option value="0"><spring:message code="water.select" /></form:option>
						<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
							<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
					<label class="hide" for="caoNewTitle_0"><spring:message code="water.title" /></label>
					<label class="hide" for="caoNewFName_0"><spring:message code="water.owner.details.fname"/></label>
					<label class="hide" for="caoNewMName_0"><spring:message code="water.owner.details.mname"/></label>
					<label class="hide" for="caoNewLName_0"><spring:message code="water.owner.details.lname" /></label>
					<label class="hide" for="caoNewGender_0"><spring:message code="water.owner.details.gender"/></label>
				</td>
				
				<td><form:input path="additionalOwners[${d}].caoNewFName" class="form-control  " onkeyup="hasCharacterFField(${d})" id="caoNewFName_${d}" maxlength="100"/></td>
                <td><form:input path="additionalOwners[${d}].caoNewMName" class="form-control " onkeyup="hasCharacterMField(${d})" id="caoNewMName_${d}" maxlength="100"/></td>
                <td><form:input path="additionalOwners[${d}].caoNewLName" class="form-control " onkeyup="hasCharacterLField(${d})" id="caoNewLName_${d}" maxlength="100"/></td>
				<td>
                <c:set var="baseLookupCode" value="GEN" />
					<form:select path="additionalOwners[${d}].caoNewGender" class="form-control" id="caoNewGender_${d}" >
						<form:option value="0"><spring:message code="water.select" /></form:option>
						<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
							<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
					  <label class="hide" for="caoNewUID_0"><spring:message code="water.owner.HasNumber"/></label>
                </td>
  
 			<td><form:input path="additionalOwners[${d}].caoNewUID" onkeyup="hasNumberField(${d})" class="form-control" id="caoNewUID_${d}" maxlength="12"/></td>
									
									<td><a href="javascript:void(0);" data-toggle="tooltip"
										data-placement="top" class="addCF btn btn-success btn-sm"
										data-original-title="Add"><span class="hide"><spring:message code="water.add"/></span><i class="fa fa-plus-circle"></i></a>
										<a href="javascript:void(0);" data-toggle="tooltip"
										data-placement="top" class="remCF btn btn-danger btn-sm"
										data-original-title="Delete"><span class="hide"><spring:message code="water.delete"/></span> <i class="fa fa-trash"></i></a></td>
									
								</tr>
							</c:if>
				<c:if test="${command.enableSubmit eq true}">	
				<c:forEach items="${command.additionalOwners}" var="addOwnerList" varStatus="status" >
					<tr class="appendableClass">			
				<td> 
				<c:set var="baseLookupCode" value="TTL" />
					<form:select path="additionalOwners[${status.index}].caoNewTitle" class="form-control" id="caoNewTitle_${status.index}">
						<form:option value="0"><spring:message code="water.select" /></form:option>
						<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
							<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
					<label class="hide" for="caoNewTitle_0"><spring:message code="water.title"/></label>
					<label class="hide" for="caoNewFName_0"><spring:message code="water.owner.details.fname"/></label>
					<label class="hide" for="caoNewMName_0"><spring:message code="water.owner.details.mname"/></label>
					<label class="hide" for="caoNewLName_0"><spring:message code="water.owner.details.lname" /></label>
					<label class="hide" for="caoNewGender_0"><spring:message code="water.owner.details.gender" /></label>
				</td>
				
				<td><form:input  path="additionalOwners[${status.index}].caoNewFName" class="form-control  hasSpecialChara" id="caoNewFName_${status.index}" maxlength="100"/></td>
                <td><form:input  path="additionalOwners[${status.index}].caoNewMName" class="form-control hasSpecialChara" id="caoNewMName_${status.index}" maxlength="100"/></td>
                <td><form:input  path="additionalOwners[${status.index}].caoNewLName" class="form-control hasSpecialChara" id="caoNewLName_${status.index}" maxlength="100"/></td>
				<td>
                <c:set var="baseLookupCode" value="GEN" />
					<form:select path="additionalOwners[${status.index}].caoNewGender" class="form-control" id="caoNewGender_${status.index}" >
						<form:option value="0"><spring:message code="water.select" /></form:option>
						<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
							<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
					  <label class="hide" for="caoNewUID_0"><spring:message code="water.owner.HasNumber"/></label>
                </td>
              
 				<td><form:input path="additionalOwners[${status.index}].caoNewUID" class="form-control hasNumber" id="caoNewUID_${status.index}" maxlength="12"/></td>
									
								</tr>
							  	</c:forEach>
							</c:if>		
								
							</tbody>
						</table>
					</div>
					</div>
          		</div>
         </div>  		
			
	
	
	<c:if test="${command.enableSubmit ne true}">
          <div class="padding-top-10 text-center">
				<button type="button" class="btn btn-success" id="confirmToProceedId" onclick="getChecklistAndChargesForChangeOfOwner(this)"><spring:message code="water.btn.proceed"/></button>
				<input type="button" class="btn btn-danger" id="backBtnId" onclick="window.location.href='CitizenHome.html'" value=<spring:message code="bckBtn"/> />
			</div>	
			</c:if>
				<!-- File Uploading Area if any document list coming in search list response after calling search web service -->
	<div id="checkListAndChargeId">
	
	
	<c:if test="${not empty command.checkList}">
		<h4><spring:message code="water.documentattchmnt" /><small class="text-blue-2"><spring:message code="water.uploadfile.validtn" /></small></h4>
			<div class="table-responsive">
						<table class="table table-hover table-bordered table-striped">
							<tr>
								<th><spring:message code="label.checklist.srno" /></th>
								<th><spring:message code="label.checklist.docname" /></th>
								<th><spring:message code="label.checklist.status" /></th>
								<th width="400"><spring:message code="label.checklist.upload" /></th>
							</tr>
							<tr>
								<c:forEach items="${command.checkList}" var="lookUp"
									varStatus="lk">
									<tr>
										<td>${lookUp.documentSerialNo}</td>
										<c:choose>
											<c:when
												test="${userSession.getCurrent().getLanguageId() eq 1}">
												<td>${lookUp.doc_DESC_ENGL}</td>
											</c:when>
											<c:otherwise>
												<td>${lookUp.doc_DESC_Mar}</td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${lookUp.checkkMANDATORY eq 'Y'}">
												<td><spring:message code="label.checklist.status.mandatory" /></td>
											</c:when>
											<c:otherwise>
												<td><spring:message code="label.checklist.status.optional" /></td>
											</c:otherwise>
										</c:choose>

										<td>
											<div id="docs_${lk}">
												<apptags:formField fieldType="7" labelCode="" hasId="true"
													fieldPath="changeOwnerMaster.fileList[${lk.index}]"
													isMandatory="false" showFileNameHTMLId="true"
													fileSize="BND_COMMOM_MAX_SIZE"
													maxFileCount="CHECK_LIST_MAX_COUNT"
													validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
													currentCount="${lk.index}" />
											</div>
										</td>
									</tr>
								</c:forEach>
							</tr>

						</table>
					</div>

				</c:if>
				<c:if test="${command.isFree ne null && command.isFree ne 'F'}">
					<div class="form-group margin-top-10">
						<label class="col-sm-2 control-label"><spring:message
								code="water.field.name.amounttopay" /></label>
						<div class="col-sm-4">
							<input type="text" class="form-control"
								value="${command.offlineDTO.amountToShow}" maxlength="12" readonly></input>
							<!-- <a class="fancybox fancybox.ajax text-small text-info" href="javascript:void(0);" onclick="showChargeInfo()">Charge Details <i class="fa fa-question-circle "></i></a> -->
							<a class="fancybox fancybox.ajax text-small text-info"
								href="ChangeOfOwnership.html?showChargeDetails"><spring:message
									code="water.lable.name.chargedetail" /> <i
								class="fa fa-question-circle "></i></a>
						</div>
					</div>
					<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
				</c:if>
			<c:if test="${command.enableSubmit eq true}">
				<div class="text-center padding-top-10">
					<input type="button" class="btn btn-success"
						onclick="saveChangeOfOwnerShip(this);"
						value=<spring:message code="saveBtn"/> /> <input type="button"
						class="btn btn-warning"
						onclick="resetData()"
						value=<spring:message code="rstBtn"/> /> <input type="button"
						class="btn btn-danger"
						onclick="window.location.href='CitizenHome.html'"
						value=<spring:message code="bckBtn"/> />
				</div>
					</c:if>
		</div>
		</div>
			</form:form>
		</div>
	</div>
	
</div>
</div>