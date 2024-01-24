<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script>
$(function() {
	$("#fromDate").datepicker({
		maxDate : '-0d',
		changeMonth : true,
		changeYear : true,
		yearRange : "-100:-0",
	});
});

function resubmitForm(element) {
	return saveOrUpdateForm(element,"",'CitizenHome.html', 'saveform');
}
</script>

	<!-- PAGE BREADCRUM SECTION  -->
	<ol class="breadcrumb">
				<li><a href="CitizenHome.html"><spring:message code="menu.home" /></a></li>
				<li class="active"><spring:message text="Document Resubmit"	code="cfc.resubmit.doc.upload.search" /></li>
	</ol>
 <!-- Start Content here --> 
    <div class="content"> 
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message text="Document Resubmit" code="cfc.resubmit.doc.upload.search" /></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
        <div class="widget-content padding">	
		<form:form action="DocumentResubmission.html" name="frmMasterForm" id="frmMasterForm" class="form-horizontal">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<c:if test="${not empty command.attachmentList}">
		<h4><spring:message text="Applicant Details" code="cfc.applicant.detail"/></h4>
			
			<div class="form-group">
                <label class="control-label col-sm-2"><spring:message code="checklistVerification.serviceName"  text="Service Name" /> :</label>
				<div class="col-sm-4">
					<span class="form-control height-auto">${command.applicationService}</span>
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
				<table class="table table-bordered table-condensed" id="docTable">
					<tr>
						<th width="10%"><spring:message code="checklistVerification.srNo" /></th>
						<th width="20%"><spring:message code="checklistVerification.document" /></th>
						<th width="15%"><spring:message code="checklistVerification.documentStatus" /></th>
						<th width="30%"><spring:message code="cfc.rh.remark" text="Rejected/Hold Remarks"/></th>
						<th width="15%"><spring:message code="cfc.rejected.doc" /></th>
						<th width="20%"><spring:message code="cfc.upload.doc" /></th>
					</tr>
					<c:forEach var="singleDoc" items="${command.documentsList}" varStatus="count">
						<c:if test="${count.index eq 0}">
							<input type="hidden" value=" ${fn:length(command.documentsList)}"  id="attSize"> 
						</c:if>
						<tr>
					       <td class="hide">${singleDoc.lookUpExtraLongOne}</td>
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
								<td>${singleDoc.extraStringField1}</td>
							<td class="row1">
									<c:set var="links"	value="${fn:split(singleDoc.descLangSecond,',')}" />
									<c:forEach items="${links}" var="download" varStatus="status">
										<apptags:filedownload filename="${singleDoc.lookUpCode}" filePath="${download}" actionUrl="CitizenHome.html?Download"></apptags:filedownload>
									</c:forEach><%-- ${singleDoc.lookUpCode} --%>
								
							</td>
							<td><apptags:formField fieldType="7" fieldPath="attachment.attPath"
									labelCode="" currentCount="${count.index}"
									showFileNameHTMLId="true" fileSize="CHECK_COMMOM_MAX_SIZE"
									maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="ALL_UPLOAD_VALID_EXTENSION"
									folderName="${count.index}"
									checkListSrNo="${singleDoc.lookUpExtraLongOne}"
									checkListSStatus="${singleDoc.lookUpExtraLongTwo}"
									checkListMStatus="${singleDoc.lookUpType}"
									checkListMandatoryDoc="${singleDoc.otherField}"
									checkListDesc="${singleDoc.lookUpDesc}"
									checkListId="${singleDoc.lookUpId}"
									checkListDocDesc="${singleDoc.docDescription}" /></td>
						</tr>
					</c:forEach>
				</table> 
				</div>
				<div class="text-center padding-top-10">
					<input type="button" value="<spring:message code="checklistVerification.submit" />" onclick="resubmitForm(this);" class="btn btn-success" />
					<apptags:backButton url="CitizenHome.html"/>				
				</div>
		</c:if>
		</form:form>
	</div>
</div>
</div>

