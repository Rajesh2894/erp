<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/cfc/documentResubmission.js"></script>

	<!-- PAGE BREADCRUM SECTION  -->
	<ol class="breadcrumb">
				<li><a href="AdminHome.html"><spring:message code="menu.home" /></a></li>
				<li><a href="javascript:void(0);"><spring:message text="CFC" code="cfc.module.breadcrumb" /></a></li>
				<li class="active"><spring:message text="Search Document To Resubmit" 	code="cfc.resubmit.doc.upload.search" /></li>
	</ol>
	
 <!-- Start Content here --> 
    <div class="content"> 
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message text="Search Document To Resubmit" code="cfc.resubmit.doc.upload.search" /></h2>
           <apptags:helpDoc url="DocumentResubmission.html"></apptags:helpDoc>
        </div>
        <div class="widget-content padding">
          <c:if test="${not command.resubmitedApplication }">
		<div class="mand-label clearfix"><span>Field with <i class="text-red-1">*</i> is mandatory</span></div>
	</c:if>
	
		<form:form action="DocumentResubmission.html" name="frmMasterForm" id="frmMasterForm" class="form-horizontal">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<input type="hidden" id="resubmitDoc" value="<spring:message code="cfc.doc.resubmit"/>">
			<c:if test="${not command.resubmitedApplication }">
					<h4 class="margin-top-0">
						<spring:message text="Search Document To Resubmit"
							code="cfc.resubmit.doc.upload.search" />
					</h4>
					
			<div class="form-group">
              <label class="control-label col-sm-2 required-control"><spring:message code="cfc.application" text="Application Id" /></label>
              <div class="col-sm-4">
               	<form:input path="applicationId" maxlength="16" cssClass="hasNumber form-control"/>
              </div>
              <div class="col-sm-6"><a href="#" onclick="findAll(this)" class="btn btn-success"><i class="fa fa-search"></i> <spring:message code="bt.search" /></a></div>
            </div>
            </c:if>
		<c:if test="${not empty command.attachmentList}">
		<h4><spring:message text="Applicant Details" code="cfc.applicant.detail"/></h4>
			
			<div class="form-group">
                <label class="control-label col-sm-2"><spring:message code="checklistVerification.serviceName"  text="Service Name" /> :</label>
				<div class="col-sm-4">
					<span class="form-control">${command.applicationDetails.applicationService}</span>
				</div>
				<label class="control-label col-sm-2"><spring:message text="Name Of Applicant" code="cfc.applicant.name" /> :</label> 
				<div class="col-sm-4">
					<span class="form-control">${command.applicationDetails.applicantsName }</span>
				</div>
			</div>	
			<div class="form-group">
                <label class="control-label col-sm-2"><spring:message text="Application ID " code="cfc.application" /> :</label> 
				<div class="col-sm-4">
					<span class="form-control">${command.applicationDetails.apmApplicationId }</span>
				</div>	
				<label class="control-label col-sm-2"><spring:message text="Application Date" code="cfc.applIcation.date" /> :</label>
				<c:set value="${command.applicationDetails.apmApplicationDate }" var="appDate"/>
				<div class="col-sm-4">
					<span class="form-control"><fmt:formatDate type="date" value="${appDate}" pattern="dd/MM/yyyy" /></span>
				</div>
			</div>
		
		<input type="hidden" value="${fn:length(command.attachmentList)}" id="docCount"/>
				<h4><spring:message code="cfc.resubmit.doc" /></h4>
				<div class="table-responsive">
				<table class="table table-bordered table-condensed">
					<tr>
						<th><spring:message code="checklistVerification.srNo" /></th>
						<th><spring:message code="checklistVerification.document" /></th>
						<th><spring:message code="checklistVerification.documentStatus" /></th>
						<th><spring:message code="cfc.rejected.doc" /></th>
						<th><spring:message code="cfc.upload.doc" /></th>
					</tr>
						
					<c:forEach var="singleDoc" items="${command.documentsList}" varStatus="count">
						<c:if test="${count.index eq 0}">
							<input type="hidden" value=" ${fn:length(command.documentsList)}"  id="attSize"> 
						</c:if>
						<tr>
							<td>${count.count}</td>
							<td>${singleDoc.lookUpDesc}</td>
							<td><c:choose>
									<c:when test="${singleDoc.otherField eq 'Y'}">
										<spring:message code="checklistVerification.mandatory" />
										<c:set var="docStatus" value="Mandatory" />
									</c:when>
									<c:otherwise>
										<spring:message code="checklistVerification.optional" />
										<c:set var="docStatus" value="Optional" />
									</c:otherwise>
								</c:choose></td>
							<td class="row1">
									<c:set var="links"	value="${fn:split(singleDoc.descLangSecond,',')}" />
									<c:forEach items="${links}" var="download" varStatus="status">
										<apptags:filedownload filename="${singleDoc.lookUpCode}" filePath="${download}" dmsDocId="${singleDoc.extraStringField1}"   actionUrl="DocumentResubmission.html?Download"></apptags:filedownload>
									</c:forEach>
								
							</td>
							
							<td><apptags:formField fieldType="7" fieldPath="attachment.attPath"
									labelCode="" currentCount="${count.index}"
									showFileNameHTMLId="true" fileSize="CHECK_COMMOM_MAX_SIZE"
									maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC"
									folderName="${count.index}"
									checkListSrNo="${singleDoc.lookUpExtraLongOne}"
									checkListSStatus="${singleDoc.lookUpExtraLongTwo}"
									checkListMStatus="${singleDoc.lookUpType}"
									checkListMandatoryDoc="${singleDoc.otherField}"
									checkListDesc="${singleDoc.lookUpDesc}"
									checkListId="${singleDoc.lookUpId}"
									checkListDocDesc="${singleDoc.docDescription}" />
									<small class="text-blue-2"> <spring:message code="bnd.checklist.tooltip"
						               text="(Upload Image File upto 1 MB)" />
									</small></td>
									
							
		
						</tr>
					</c:forEach>
				</table> 
				</div>
				<div class="text-center padding-top-10">
					<c:if test="${ command.resubmitedApplication }">
						<input type="submit" value="<spring:message code="checklistVerification.submit" />" onclick="return resubmitForm(this);" class="btn btn-success btn-submit" />
						<apptags:backButton url="AdminHome.html"/>
					</c:if>
					<c:if test="${not command.resubmitedApplication }">
						<input type="submit" value="<spring:message code="checklistVerification.submit" />" onclick="return saveForm(this);" class="btn btn-success btn-submit" />
						<apptags:backButton url="DocumentResubmission.html"/>
					</c:if>
						
				</div>
		</c:if>
		</form:form>
	</div>
</div>
</div>

