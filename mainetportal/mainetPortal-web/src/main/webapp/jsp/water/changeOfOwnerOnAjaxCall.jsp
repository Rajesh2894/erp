<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<% response.setContentType("text/html; charset=utf-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script  src="js/water/water.js"></script>
<script  src="js/mainet/file-upload.js"></script>

<script >
$(document).ready(function(){
	$('.margin-bottom-0').hide();
	$('#trmGroup1').attr('disabled',true);
	$('#trmGroup2').attr('disabled',true); 
	$('#conSize').attr('disabled',true);
	$('#conName').attr('disabled',true);
	$('#oldAdharNo').attr('disabled',true);
	if ($('#bplNo').val() == '') {
		$('#bpldiv').hide();
	} else {
		$('#bpldiv').show();
	}
	if ($('#canApplyOrNotId').val() != 'Y') {
		$('#confirmToProceedId').attr('disabled',true);
	}
});
</script>

<%-- <ol class="breadcrumb">
	<li><a href="CitizenHome.html"><i class="fa fa-home"></i></a></li>
	<li>Services</li>
	<li><spring:message code="water.head.changeOwner" /></li>
</ol> --%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="water.head.changeOwner" /></h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i class="text-red-1">*</i> <spring:message code ="water.ismandtry"/></span>
			</div>
			<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>

		<form:form action="ChangeOfOwnership.html" method="POST" class="form-horizontal" id="ChangeOfOwnershipId" >
				<jsp:include page="/jsp/tiles/validationerror.jsp" /> 
				<div id="allicantDetails"><jsp:include page="/jsp/mainet/applicantDetails.jsp" /></div>

<h4 class="margin-top-0"><spring:message code="water.changeOwner.oldDetails" /></h4>
			<div id="oldOwner">
				<input type="hidden" value="${command.changeOwnerResponse.canApplyOrNot}" id="canApplyOrNotId">
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message code="water.ConnectionNo"></spring:message></label>
						<div class="col-sm-4"><form:input path="changeOwnerResponse.connectionNumber" type="text" class="form-control disablefield" id="conNum" readonly="false"></form:input></div>
						<div class="col-sm-6"><button type="button" value="Search" class="btn btn-success" id="searchConnection">
								<i class="fa fa-search"></i> <spring:message code="searchBtn"/>
							</button>
							<input type="button" class="btn btn-default" onclick="resetData()" value=<spring:message code="rstBtn"/> />
						</div>
					</div>
					<jsp:include page="/jsp/water/oldOwnerDetails.jsp" />	
			</div>
 
				<h4><spring:message code="water.changeOwner.newDetails" /></h4>
				<div id="newOwner">
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message code="water.title" /></label>
						<c:set var="baseLookupCode" value="TTL" />
						<apptags:lookupField items="${command.getLevelData(baseLookupCode)}" path="changeOwnerMaster.cooNotitle" cssClass="form-control" hasChildLookup="false" hasId="true" showAll="false" selectOptionLabelCode="applicantinfo.label.select" isMandatory="true" />
						<label class="col-sm-2 control-label required-control"><spring:message code="water.owner.details.fname" text=""></spring:message></label>
						<div class="col-sm-4"><form:input path="changeOwnerMaster.cooNoname" type="text"	class="form-control hasSpecialChara" maxlength="200"></form:input>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message code="water.owner.details.mname"></spring:message></label>
						<div class="col-sm-4"><form:input path="changeOwnerMaster.cooNomname" type="text"	class="form-control hasSpecialChara" maxlength="200"></form:input></div>
						<label class="col-sm-2 control-label required-control"><spring:message code="water.owner.details.lname"></spring:message></label>
						<div class="col-sm-4"><form:input path="changeOwnerMaster.cooNolname" type="text" class="form-control hasSpecialChara" maxlength="200"></form:input></div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label required-control">Gender</label>
						<div class="col-sm-4">
							<c:set var="baseLookupCode" value="GEN" />
								<form:select path="changeOwnerMaster.gender" class="form-control" id="newGender" >
									<form:option value="0"><spring:message code="water.select" text="Select"></spring:message></form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
											<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
								</form:select>
						</div>
 						<label class="col-sm-2 control-label"><spring:message code="water.aadhar" /></label>
						<div class="col-sm-4"><form:input type="text" class="form-control hasNumber" path="changeOwnerMaster.ConUidNo" id="newAdharNo" maxlength="12"></form:input>
						</div>
 					</div>
					<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message code="water.remark" /></label>
						<div class="col-sm-10"><form:textarea type="text" class="form-control"	path="changeOwnerMaster.cooRemark" id="remark" maxlength="200"></form:textarea>
						</div> 
					</div>
					
				</div>
			
	<h4><spring:message code="water.additionalOwner" /></h4>
	<c:set var="d" value="0" scope="page" />
	<div class="table-responsive">
	<table class="table table-bordered table-striped" id="customFields">
		<tbody><tr>
					<th width="120"><spring:message code="water.title" /></th>
					<th><spring:message code="water.owner.details.fname" /></th>
					<th><spring:message code="water.owner.details.mname" /></th>
					<th><spring:message code="water.owner.details.lname" /></th>
					<th width="130"><spring:message code="water.owner.details.gender"/></th>
					<th><spring:message code="water.aadhar"/></th>
					<th><spring:message code="water.add/remove"/></th>
			  </tr>
			 <c:choose>
			 	<c:when test="${empty command.additionalOwners}">
			 		 <tr class="appendableClass">
				
				<td> <input type="hidden" id="srNoId_${d}" value="">
				<c:set var="baseLookupCode" value="TTL" />
					<form:select path="additionalOwners[${d}].caoNewTitle" class="form-control" id="caoNewTitle_${d}">
						<form:option value="0"><spring:message code="water.select" text="Select"></spring:message></form:option>
						<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
							<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
				</td>
				<td><form:input  path="additionalOwners[${d}].caoNewFName" class="form-control" id="caoNewFName_${d}"/></td>
                <td><form:input  path="additionalOwners[${d}].caoNewMName" class="form-control" id="caoNewMName_${d}"/></td>
                <td><form:input  path="additionalOwners[${d}].caoNewLName" class="form-control" id="caoNewLName_${d}"/></td>
				<td>
                <c:set var="baseLookupCode" value="GEN" />
					<form:select path="additionalOwners[${d}].caoNewGender" class="form-control" id="caoNewGender_${d}" >
						<form:option value="0"><spring:message code="water.select" text="Select"></spring:message></form:option>
						<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
							<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
                </td>
 				<td><form:input path="additionalOwners[${d}].caoNewUID" class="form-control hasNumber" id="caoNewUID_${d}" maxlength="12"/></td>
									
									<td><a href="javascript:void(0);" data-toggle="tooltip" data-placement="top" class="addCF btn btn-success btn-sm" data-original-title="Add"><i class="fa fa-plus-circle"></i></a>
										<a href="javascript:void(0);" data-toggle="tooltip"	data-placement="top" class="remCF btn btn-danger btn-sm" data-original-title="Delete"><i class="fa fa-trash"></i></a></td>
 								</tr>
			 	</c:when>
			 	
			 	<c:otherwise>
			 			<c:forEach items="${command.additionalOwners}" var="additionalOwner" varStatus="count">
			 				 <tr class="appendableClass">
					<c:set var="d" value="${count.index}" />
				<td> <input type="hidden" id="srNoId_${d}" value="">
				<c:set var="baseLookupCode" value="TTL" />
					<form:select path="additionalOwners[${d}].caoNewTitle" class="form-control" id="caoNewTitle_${d}">
						<form:option value="0"><spring:message code="water.select" text="Select"></spring:message></form:option>
						<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
							<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
				</td>
				<td><form:input  path="additionalOwners[${d}].caoNewFName" class="form-control" id="caoNewFName_${d}"/></td>
                <td><form:input  path="additionalOwners[${d}].caoNewMName" class="form-control" id="caoNewMName_${d}"/></td>
                <td><form:input  path="additionalOwners[${d}].caoNewLName" class="form-control" id="caoNewLName_${d}"/></td>
				<td>
                <c:set var="baseLookupCode" value="GEN" />
					<form:select path="additionalOwners[${d}].caoNewGender" class="form-control" id="caoNewGender_${d}" >
						<form:option value="0"><spring:message code="water.select" text="Select"></spring:message></form:option>
						<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
							<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
                </td>
 				<td><form:input path="additionalOwners[${d}].caoNewUID" class="form-control hasNumber" id="caoNewUID_${d}" maxlength="12"/></td>
									<td><a href="javascript:void(0);" data-toggle="tooltip"	data-placement="top" class="addCF btn btn-success btn-sm" data-original-title="Add"><i class="fa fa-plus-circle"></i></a>
										<a href="javascript:void(0);" data-toggle="tooltip"	data-placement="top" class="remCF btn btn-danger btn-sm"
										data-original-title="Delete"><i class="fa fa-trash"></i></a></td>
									
								</tr>
			 			</c:forEach>
			 	</c:otherwise>
			 </c:choose>

							</tbody>
						</table>
					</div>
          <div class="padding-top-10 text-center">
				<button type="button" class="btn btn-success" id="confirmToProceedId" onclick="getChecklistAndChargesForChangeOfOwner(this)"><spring:message code="water.btn.proceed"/></button>
				<input type="button" class="btn btn-danger" id="backBtnId" onclick="window.location.href='CitizenHome.html'" value=<spring:message code="bckBtn"/> />
			</div>	
	<div id="checkListAndChargeId">

	<c:if test="${not empty command.checkList}" var="docList">
		<h4><spring:message code="water.documentattchmnt" /><small class="text-blue-2"><spring:message code="water.uploadfile.validtn" /></small></h4>
			<div class="table-responsive">
						<table class="table table-hover table-bordered table-striped">
							<tr>
								<th><label class="tbold"><spring:message code="label.checklist.srno" /></label></th>
								<th><label class="tbold"><spring:message code="label.checklist.docname" /></label></th>
								<th><label class="tbold"><spring:message code="label.checklist.status" /></label></th>
								<th><label class="tbold"><spring:message code="label.checklist.upload" /></label></th>
							</tr>
							<tr>
								<c:forEach items="${command.checkList}" var="lookUp" varStatus="lk">
									<tr>
										<td><label>${lookUp.documentSerialNo}</label></td>
										<c:choose>
											<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
												<td><label>${lookUp.doc_DESC_ENGL}</label></td>
											</c:when>
											<c:otherwise>
												<td><label>${lookUp.doc_DESC_Mar}</label></td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${lookUp.checkkMANDATORY eq 'Y'}">
												<td><label><spring:message code="label.checklist.status.mandatory" /></label></td>
											</c:when>
											<c:otherwise>
												<td><label><spring:message code="label.checklist.status.optional" /></label></td>
											</c:otherwise>
										</c:choose>

										<td>
											<div id="docs_${lk}">
												<apptags:formField fieldType="7" labelCode="" hasId="true" fieldPath="changeOwnerMaster.fileList[${lk.index}]" isMandatory="false" showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE" maxFileCount="CHECK_LIST_MAX_COUNT" validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
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
						<label class="col-sm-2 control-label"><spring:message code="water.field.name.amounttopay" /></label>
						<div class="col-sm-4"><input type="text" class="form-control" value="${command.offlineDTO.amountToShow}" maxlength="12" />
							<a class="fancybox fancybox.ajax text-small text-info" href="ChangeOfOwnership.html?showChargeDetails"><spring:message code="water.lable.name.chargedetail" /> <i class="fa fa-question-circle "></i></a>
						</div>
					</div>
					<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
				</c:if>
			<c:if test="${command.enableSubmit eq true}">
				<div class="text-center padding-top-10">
					<input type="button" class="btn btn-success" onclick="saveChangeOfOwnerShip(this);" value=<spring:message code="saveBtn"/> /> 
					<input type="button" class="btn btn-default" onclick="resetData()" value=<spring:message code="rstBtn"/> /> 
					<input type="button" class="btn btn-danger" onclick="window.location.href='CitizenHome.html'" value=<spring:message code="bckBtn"/> />
				</div>
				</c:if>
		</div>
			</form:form>
		</div>
	</div>
</div>