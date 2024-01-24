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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<!-- <script type="text/javascript" src="js/water/water.js"></script> -->
<script type="text/javascript" src="js/water/changeOfOwnershipScrutiny.js"></script>

<!-- <script>var paramOne =<c:out value="${paramOne}"/></script> -->
<!-- <script type="text/javascript">var myVar = "${model.paramOne}";</script> -->
<%-- <script src="<c:url value="/resources/js/extjs.js" />" type="text/javascript"></script> --%> 
<!--  ChangeOfOwnerShipValidn/ChangeOfOwnerShipView -->
<%-- ${pageContext.request.servletPath} --%>

<script type="text/javascript">

	
$(document).ready(function(){
	
 		
	//disable all fields on page load
	
  var errors=${command.hasValidationErrors()};
 
 	if(!errors){
	$('#searchConnection').attr('disabled',true);
	$("#submitBtn").attr('disabled',true);
	$('input[type=text]').attr('disabled',true);
	$('input[type=textarea]').attr('disabled',true);
	$('#remark').attr('disabled',true);
	$('select').attr("disabled", true);
	
	$('#conNum').attr('disabled',true);
	$('#conName').attr('disabled',true);
	$('#oldAdharNo').attr('disabled',true);
	$('#conSize').attr('disabled',true);		
	$('.trfClass').attr('disabled',true);
	
	if ($('#bplNo').val() == '') {
		$('#bpldiv').hide();
	} else {
		$('#bpldiv').show();
		$('#povertyLineId').val('Y');
	}

	if (('${command.scrutinyApplicable}') == 'true') {
			$("#scrutinyDiv").show();
		} else {
			$("#scrutinyDiv").hide();
		}
 	} else{
 		
          var valCheck= document.getElementById('testId');
            
          valCheck.value=1;          
 		
		
		 $("#submitBtn").attr('disabled',false);
		 $('input[type=text]').attr('disabled',false);
		 $('input[type=textarea]').attr('disabled',false);
		 $('#editBtn').attr('disabled',true);
		 
		 $('#conNum').attr('disabled',true);
	     $('#conName').attr('disabled',true);
		 $('#oldAdharNo').attr('disabled',true);
		 $('#conSize').attr('disabled',true);		
		 $('.trfClass').attr('disabled',true);
		
		 
		 if ($('#bplNo').val() == '') {
				$('#bpldiv').hide();
			} else {
				$('#bpldiv').show();
				$('#povertyLineId').val('Y');
			}
	 }    
	
	});
	
</script>



<apptags:breadcrumb></apptags:breadcrumb>


<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.head.changeOwner" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith"/><i class="text-red-1">*</i><spring:message code="water.ismandtry"/> 
				</span>
			</div>
			<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
	<form:form action="ChangeOfOwnership.html" method="POST" class="form-horizontal" id="changeOfOwnerForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				
      		<form:hidden path="" id="testId" value=""/>
      		
				<div id="allicantDetails">
					<apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail>
				</div>
<!-- old owner details -->
		<h4 class="margin-top-0">
					<spring:message code="water.changeOwner.oldDetails" />
				</h4>
				<div id="oldOwner">
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="water.ConnectionNo"></spring:message></label>
						<div class="col-sm-4">
							<form:input path="" value = "${command.ownerDTO.csmrInfoDTO.csCcn}"
								type="text" class="form-control" id="conNum"></form:input>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="water.consumerName"></spring:message></label>
						<div class="col-sm-4">
							<form:input path="" type="text" value="${command.ownerDTO.ownerFullName}"
								class="form-control" id="conName" disabled="disabled"/>
						</div>

						<label class="col-sm-2 control-label"><spring:message
								code="water.aadhar" /></label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control disablefield"
								path="" id="oldAdharNo" value="${command.ownerDTO.csmrInfoDTO.csUid}"
								disabled="disabled"></form:input>
						</div>
					</div>
					<div class="form-group" id="trfDivId">
						<apptags:lookupFieldSet baseLookupCode="TRF" hasId="true"
							showOnlyLabel="false" pathPrefix="ownerDTO.csmrInfoDTO.trmGroup"
							isMandatory="false" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control  trfClass"  showAll="true" />
					</div>
					<div class="form-group">

						<label class="col-sm-2 control-label"><spring:message
								code="water.ConnectionSize" /></label>
						<div class="col-sm-4">
						 <div class="input-group">
							<c:set var="baseLookupCode" value="CSZ" />
								<form:select path="ownerDTO.csmrInfoDTO.csCcnsize" class="form-control" id="conSize" value="${command.ownerDTO.csmrInfoDTO.csCcnsize}">
									<form:option value="0"><spring:message code="water.dataentry.select"/></form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
											<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
								</form:select>
								<span class="input-group-addon"><spring:message code="water.inch"/></span>
								</div>
						</div>
					</div>

				</div>
			<!-- New Owner detail start -->
				<h4>
					<spring:message code="water.changeOwner.newDetails" />
				</h4>
				<div id="newOwner">
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="water.title" /></label>
							<c:set var="baseLookupCode" value="TTL" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="ownerDTO.chNewTitle" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="pt.select" isMandatory="true" />
						<label class="col-sm-2 control-label required-control"><spring:message
								code="water.owner.details.fname"></spring:message></label>
						<div class="col-sm-4">
							<form:input path="ownerDTO.chNewName" type="text"
								class="form-control hasCharacter" maxlength="200"></form:input>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="water.owner.details.mname"></spring:message></label>
						<div class="col-sm-4">
							<form:input path="ownerDTO.chNewMName" type="text"
								class="form-control hasCharacter" maxlength="200"></form:input>
						</div>
						<label class="col-sm-2 control-label required-control"><spring:message
								code="water.owner.details.lname"></spring:message></label>
						<div class="col-sm-4">
							<form:input path="ownerDTO.chNewLname" type="text"
								class="form-control hasCharacter" maxlength="200"></form:input>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message code="water.owner.details.gender"/></label>
						<div class="col-sm-4">
							<c:set var="baseLookupCode" value="GEN" />
								<form:select path="ownerDTO.chNewGender" class="form-control" id="newGender" >
									<form:option value="0">Select</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
											<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
								</form:select>
						</div>
						
						<label class="col-sm-2 control-label"><spring:message
								code="water.aadhar" /></label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control hasNumber"
								path="ownerDTO.chNewUIDNo" id="newAdharNo" maxlength="12"></form:input>
						</div>
					</div>
					<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
								code="water.remark" /></label>
						
						
						<div class="col-sm-10">
							<form:textarea type="text" class="form-control"
								path="ownerDTO.chRemark" id="remark" maxlength="200"></form:textarea>
						</div>
						
					</div>
				</div>
				
		<!-- Additional owner Detail section -->			
	<h4><spring:message code="water.additionalOwner" /></h4>
	 <div class="table-responsive">
	<table class="table table-bordered table-striped" id="customFields">
		<tbody><tr>
					<th width="120"><spring:message code="water.title" /></th>
					<th><spring:message code="water.owner.details.fname" /></th>
					<th><spring:message code="water.owner.details.mname"/></th>
					<th><spring:message code="water.owner.details.lname"/></th>
					<th width="130"><spring:message code="water.owner.details.gender"/></th>
					<th><spring:message code="water.aadhar"/></th>
			  </tr>
			  <c:forEach items="${command.ownerDTO.additionalOwners}" var="additionalOwner" varStatus="lk">
			 <tr class="appendableClass">
				<c:set var="index" value="${lk.index}" scope="page" />
				<td> <input type="hidden" id="srNoId_${index}" value="">
				<c:set var="baseLookupCode" value="TTL" />
					<form:select path="additionalOwners[${index}].caoNewTitle" class="form-control" id="caoNewTitle_${index}" value="${additionalOwner.caoNewTitle}">
						<form:option value="0">Select</form:option>
						<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
							<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
				</td>
				<td><form:input  path="additionalOwners[${index}].caoNewFName" value="${additionalOwner.caoNewFName}" class="form-control"  id="caoNewFName_${index}"/></td>
                <td><form:input  path="additionalOwners[${index}].caoNewMName" value="${additionalOwner.caoNewMName}" class="form-control"  id="caoNewMName_${index}"/></td>
                <td><form:input  path="additionalOwners[${index}].caoNewLName" value="${additionalOwner.caoNewLName}" class="form-control"  id="caoNewLName_${index}"/></td>
				<td>
                <c:set var="baseLookupCode" value="GEN" />
					<form:select path="additionalOwners[${index}].caoNewGender" value="${additionalOwner.caoNewGender}" class="form-control" id="caoNewGender_${index}" >
						<form:option value="0"><spring:message code="water.dataentry.select"/></form:option>
						<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
							<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
                </td>
 				<td><form:input path="additionalOwners[${index}].caoNewUID" value="${additionalOwner.caoNewUID}" class="form-control hasNumber" id="caoNewUID_${index}" maxlength="12"/></td>
									
									<!-- <td><a href="javascript:void(0);" data-toggle="tooltip"
										data-placement="top" class="addCF btn btn-success btn-sm"
										data-original-title="Add"><i class="fa fa-plus-circle"></i></a>
										<a href="javascript:void(0);" data-toggle="tooltip"
										data-placement="top" class="remCF btn btn-danger btn-sm"
										data-original-title="Delete"><i class="fa fa-trash"></i></a></td> -->
									
				</tr></c:forEach>
							</tbody>
						</table>
					</div>
				
<!--show uploaded document by customer  -->
	<c:if test="${not empty command.documentList}">
		<fieldset class="fieldRound">
			<div class="overflow">
			<h4><spring:message code="water.documentattchmnt"/> <small class="text-blue-2">(UploadFile upto 5MB and only .pdf or .doc)</small></h4>
			<div class="table-responsive">
				<table class="table table-hover table-bordered table-striped">
				<tbody>
					<tr>
						<th><label class="tbold"><spring:message code="tp.serialNo" text="Sr No" /></label></th>
						<th><label class="tbold"><spring:message code="tp.docName" text="Document Name" /></label></th>
						<th><label class="tbold"><spring:message code="water.download"/></label></th>
					</tr>
					<c:forEach items="${command.documentList}" var="lookUp" varStatus="lk">
						<tr><td><label>${lookUp.clmSrNo}</label></td>
							<c:choose>
								<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
									<td><label>${lookUp.clmDescEngl}</label></td>
								</c:when>
								<c:otherwise>
									<td><label>${lookUp.clmDesc}</label></td>
								</c:otherwise>
							</c:choose>
								<td><apptags:filedownload filename="${lookUp.attFname}" filePath="${lookUp.attPath}" actionUrl="ChangeOfOwnership.html?Download"/></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			</div>
		</fieldset>
	</c:if>
		<div class="text-center padding-top-10">
			<button type="button" class="btn btn-success" id="editBtn"><spring:message code="water.btn.edit"/></button>
			<button type="button" class="btn btn-success btn-submit" id="submitBtn" onclick="editChangeOfOwnershipByDept(this)"><spring:message code="water.btn.submit"/></button>
		</div>
			</form:form> 
			<!-- For Scrutiny related -->
			<div id="scrutinyDiv">
					<jsp:include page="/jsp/cfc/sGrid/scrutinyButtonTemplet.jsp"></jsp:include>
            </div>
		</div>
	</div>
	<!-- End of info box -->
</div>