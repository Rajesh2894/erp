<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script src="js/tp/builderRegistration.js"></script>
<script src="js/tp/tpLicenseRegistration.js"></script>

<script>
	 $(document).ready(function() {
		<c:if test="${command.viewdata eq 'R' || command.viewdata eq 'H' }"> 
			$('#testID').find(':input').attr('disabled', 'disabled').removeClass('mandClassColor').not(":button").addClass("disablefield");
		
		</c:if> 
	}); 
	 
	 function saveData(element)	{
		 
	 	 
	 	 return saveOrUpdateForm(element,"Your application for TP Technical person saved successfully!", 'TPTechnicalPerson.html?TPPrintReport', 'saveform'); 

	 }
	 
	 function saveReUploadData(element)	{
		 alert("Re-upload");
		 	 
		 	 return saveOrUpdateForm(element,"Your application for TP Technical person saved successfully!", 'CitizenHome.html', 'saveform'); 

		 }
	 
	 
	 
</script>


	
	<ol class="breadcrumb">
				<li><a href="CitizenHome.html"><spring:message code="menu.home" /></a></li>
				<li class="active"><spring:message code="eip.techPerson" /></li>
	</ol>
	<div class="content"> 
	<div class="widget">
      
	
	<c:if test="${not command.builder}">
	<div class="widget-header">
	<h2><spring:message code="eip.techPerson" />	</h2></div>
	</c:if>
	<c:if test="${command.builder}">
	<div class="widget-header">
	<h2><spring:message code="eip.builderReg" /></h2></div>
	</c:if>
	<div id="content" class="widget-content padding">
		<div class="mand-label">
			<span><spring:message code="MandatoryMsg" /></span>
		</div>
		<form:form action="TPTechnicalPerson.html" name="frmMasterForm" id="frmMasterForm" class="form-horizontal">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			
			<c:if test="${not command.hasValidationErrors()}">
       			<c:choose>
	      			<c:when test="${userSession.getEmployee().getAuthStatus() eq 'H'}"> 
	       				<br><br>
	       				
		  				<div class="onHold">
						<h3> <spring:message code="eip.agency.upload.msg.onHold" /> </h3>
	     				</div>
	     			</c:when>
	 
	    			<c:when test="${userSession.getEmployee().getAuthStatus()eq 'R'}">
	     				<br><br>
	       				
		  				<div class="onReject">
						<h3> <spring:message code="eip.agency.upload.msg.onReject" /> </h3>
	     				</div>
	  				</c:when>
    			</c:choose>
			</c:if>
			
			<jsp:include page="/jsp/eip/agency/townPlanning/technicalPerson/tpTechnicalPerson.jsp"></jsp:include>
			
		
			<c:if test="${(empty command.cfcAttachmentsAfterReject)}"> 
			 		<div class="widget-header">
						<h2><spring:message code="eip.agency.upload" />
						   	<spring:message code="eip.agency.login.uploadDoc" /></h2>
					</div>
				
				<c:if test="${not empty command.checkList}">
         			<table class="table table-hover table-striped table-bordered">
							<tbody>
								<tr>
									<th><label class="tbold"><spring:message
												code="marriage.serialNo"/></label></th>
									<th><label class="tbold"><spring:message
												code="marriage.docName" /></label></th>
									<th><label class="tbold"><spring:message
												code="marriage.status"/></label></th>
									<th><label class="tbold"><spring:message
												code="marriage.uploadText"/></label></th>
								</tr>

								<c:forEach items="${command.checkList}" var="lookUp"
					              	varStatus="lk">
                                     <tr>
										<td><label>${lookUp.documentSerialNo}</label></td>
                                        <c:choose>
											<c:when test="${UserSession.getCurrent().getLanguageId() eq 1}">
												<td><label>${lookUp.doc_DESC_ENGL}</label></td>
											</c:when>
										<c:otherwise>
												<td><label>${lookUp.doc_DESC_Mar}</label></td>
										</c:otherwise>
										</c:choose>
										<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
											<td><label>Mandatory</label></td>
										</c:if>
										<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
											<td><label>Optional</label></td>
										</c:if>

										<td>
						     			<div id="docs_${lk}">
						    				<apptags:formField fieldType="7" labelCode="" hasId="true"
												fieldPath="entity.fileList[${lk.index}]" isMandatory="false"
												showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE"
												maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
												currentCount="${lk.index}"/>
										</div>
			           					</td>
								</tr>
								</c:forEach>
							</tbody>
					</table>
						
				</c:if>
				
		</c:if>
		
		 <c:if test="${(not empty command.cfcAttachmentsAfterReject)  and (fn:length(command.cfcAttachmentsAfterReject) > 0 )}">					
                       <div class="widget-header">
						<h2><spring:message code="eip.agency.login.reUploadDoc" />
						    <spring:message code="eip.agency.login.uploadDoc" /></h2>
						</div>
				      
				 
				 <table class="table table-hover table-striped table-bordered">
							
								<tr>
									
									<th><label class="tbold"><spring:message
												code="marriage.docName" /></label></th>
									 <th><label class="tbold"><spring:message
												code="marriage.status"/></label></th> 
									 <th><label class="tbold"><spring:message
												code="marriage.uploadText"/></label></th> 
								</tr>
								 <c:forEach items="${command.cfcAttachmentsAfterReject}" var="lookUp"
					              	varStatus="lk">
                                     <tr>
										<td><label>${lookUp.clmDescEngl}</label></td>
										<td><label>${lookUp.approvalStatus}</label></td> 
										<td>
						     			  <div id="docs_${lk}"> 
						    				<apptags:formField fieldType="7" labelCode="" hasId="true"
												fieldPath="entity.fileList[${lk.index}]" isMandatory="false"
												showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE"
												maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
												currentCount="${lk.index}"/>
										 </div> 
			           					</td>
									</tr>
								</c:forEach> 
							
					</table>
				 
	     </c:if>
			
			<c:if test="${(empty command.cfcAttachmentsAfterReject)}">
			 		<jsp:include page="/jsp/payment/onlineOfflinePay.jsp"/>
					<div class="text-center padding-10">
					    <input type="button" class="btn btn-success" onclick="return saveData(this);" value="Submit" />
                	</div>
             </c:if>
              <c:if test="${(not empty command.cfcAttachmentsAfterReject)  and (fn:length(command.cfcAttachmentsAfterReject) > 0 )}">
              		<div class="text-center padding-10">
					    <input type="button" class="btn btn-success" onclick="return saveReUploadData(this);" value="Submit" />
                	</div>
              </c:if>
			
		</form:form>
		</div>
	</div>
</div>

