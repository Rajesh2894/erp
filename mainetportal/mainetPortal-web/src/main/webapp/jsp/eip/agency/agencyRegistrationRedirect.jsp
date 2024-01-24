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
<script src="js/agency/agencyRegistrationRedirect.js"></script>

<script>

$('.form-control').bind("cut copy paste",function(e) {
    e.preventDefault();
});
	 $(document).ready(function() {
		<c:if test="${command.viewdata eq 'R' || command.viewdata eq 'H' }"> 
			$('#testID').find(':input').attr('disabled', 'disabled').removeClass('mandClassColor').not(":button").addClass("disablefield");
		
		</c:if> 
		
		if($("#chargesId").val() == 'Y'){
			$(".hideConfirmBtn").hide();
		}
	}); 
	 
	 function saveData(element)	{
		 
	 	 
	 	 return saveOrUpdateForm(element,"Your application for TP Technical person saved successfully!", 'AgencyRegistrationRedirect.html?TPPrintReport', 'saveform'); 

	 }
	 
	 function saveReUploadData(element)	{
		 	/*  return saveOrUpdateForm(element,"Your application for change Of ownership saved successfully!", 'TPTechnicalPerson.html?redirectToPay', 'saveform'); */
		 	 
		 	 return saveOrUpdateForm(element,"Your application for TP Technical person saved successfully!", 'CitizenHome.html', 'saveform'); 

		 }
	 
	 
	 
</script>


	<!-- PAGE BREADCRUM SECTION  -->
	<ol class="breadcrumb">
				<li><a href="CitizenHome.html"><spring:message code="menu.home" /></a></li>
				<li class="active"><spring:message code="eip.techPerson" /></li>
	</ol>
	<div class="content"> 
	<div class="widget">
  
	<!--HELP DOC SECTION  -->
	<%-- <apptags:helpDoc url="TPTechnicalPerson.html" /> --%>
	
	<!--PAGE HEADING SECTION  disablefield mandClassColor -->
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
		<form:form action="AgencyRegistrationRedirect.html" name="frmMasterForm" id="frmMasterForm" class="form-horizontal" autocomplete="off">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			
			<c:if test="${not command.hasValidationErrors()}">
       			<c:choose>
	      			<c:when test="${userSession.getEmployee().getAuthStatus() eq 'H'}"> 
	       				<br><br>
	       				<%-- <div class="mand-label">
			 			<spring:message code="MandatoryMsg" text="MandatoryMsg" />
		   				</div> --%>
		  				<div class="onHold">
						<h3> <spring:message code="eip.agency.upload.msg.onHold" /> </h3>
	     				</div>
	     			</c:when>
	 
	    			<c:when test="${userSession.getEmployee().getAuthStatus()eq 'R'}">
	     				<br><br>
	       				<%-- <div class="mand-label">
			 			<spring:message code="MandatoryMsg" text="MandatoryMsg" />
		   				</div> --%>	
		  				<div class="onReject">
						<h3> <spring:message code="eip.agency.upload.msg.onReject" /> </h3>
	     				</div>
	  				</c:when>
    			</c:choose>
			</c:if>
			
			<%-- <jsp:include page="/jsp/eip/agency/townPlanning/technicalPerson/tpTechnicalPerson.jsp"></jsp:include> --%>
			
			<%-- <c:if test="${not command.builder}">
			 <jsp:include page="/jsp/eip/agency/townPlanning/technicalPerson/tpTechnicalPerson.jsp"></jsp:include>
			</c:if>
			<c:if test="${command.builder}">
			<jsp:include page="/jsp/eip/agency/townPlanning/builder/tpBuilderRegistration.jsp"></jsp:include>
			</c:if> --%>
			 
			 
			 <div class="padding-top-10 text-center hideConfirmBtn" >
						<button type="button" class="btn btn-success" id="confirmToProceedId"
								onclick="getChecklistAndChargesPlumberLicense(this);"><spring:message code="water.btn.proceed"/></button>
					</div>
			 <form:hidden id="chargesId" path="" value="${command.checkListNCharges}"/>
			 <c:if test="${command.checkListNCharges eq 'Y'}"> 
						 <c:if test="${(not empty command.checkList) and (fn:length(command.checkList) > 0 )}">	
						 
						 
						 <div class="panel panel-default">
						<div class="panel-heading"><h4 class="panel-title"><a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#child-level3">Document Attachment (UploadFile upto 5MB and only .pdf or .doc)</a></h4></div>
						<div id="child-level3" class="panel-collapse">	
						<div class="panel-body">
						 
 							<fieldset class="fieldRound">
								<div class="overflow">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="label.checklist.srno" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="label.checklist.docname" text="Document Name" /></label></th>
													<th><label class="tbold"><spring:message
																code="label.checklist.status" text="Status" /></label></th>
													<th><label class="tbold"><spring:message
																code="label.checklist.upload" text="Upload" /></label></th>
												</tr>

												<c:forEach items="${command.checkList}" var="lookUp" varStatus="lk">
													<tr>
														<td><label>${lookUp.documentSerialNo}</label></td>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<td><label>${lookUp.doc_DESC_ENGL}</label></td>
															</c:when>
															<c:otherwise>
																<td><label>${lookUp.doc_DESC_Mar}</label></td>
															</c:otherwise>
														</c:choose>
														
														<c:choose>
														<c:when
															test="${lookUp.checkkMANDATORY eq 'Y'}">
																<td><label><spring:message code="label.checklist.status.mandatory" /></label></td>
														</c:when>
														<c:otherwise>
																<td><label><spring:message code="label.checklist.status.optional" /></label></td>
														</c:otherwise>
														</c:choose>
														
                                                        <td>
															<div id="docs_${lk}" class="">
																<apptags:formField fieldType="7" labelCode=""
																	hasId="true" fieldPath="entity.fileList[${lk.index}]"
																	isMandatory="false" showFileNameHTMLId="true"
																	fileSize="BND_COMMOM_MAX_SIZE"
																	maxFileCount="CHECK_LIST_MAX_COUNT"
																	validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																	currentCount="${lk.index+1}" 
																	folderName="${lk.index+1}"/>
															</div> 
															
														</td>
													</tr>
												</c:forEach> 
											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
							</div>
							</div>
							</div>
					</c:if>  
						
						  <c:if test="${command.isFree ne 'Y'}">
						  
						  <div class="form-group margin-top-10">
							<label class="col-sm-2 control-label"><spring:message
								code="water.field.name.amounttopay" /></label>
								<div class="col-sm-4">
									<input type="text" class="form-control"
											value="${command.offlineDTO.amountToShow}" maxlength="12"></input>
										<a class="fancybox fancybox.ajax text-small text-info"
											href="ChangeOfOwnership.html?showChargeDetails"><spring:message
											code="water.lable.name.chargedetail" /> <i
											class="fa fa-question-circle "></i></a>
								</div>
							</div>
							<jsp:include page="/jsp/payment/onlineOfflinePay.jsp"/>
						</c:if> 
						<%-- <div class="text-center padding-bottom-20" id="divSubmit">
							<button type="button" class="btn btn-success" onclick="savePlumberLicenseForm(this)" id="submit"><spring:message code="water.btn.submit"/></button>
							<button type="button" onclick="window.location.href='CitizenHome.html'" class="btn btn-default hidden-print"><spring:message code="bt.back"/></button>
						  	<input type="button" class="btn btn-danger" onclick="window.location.href='CitizenHome.html'" value="Cancel" />
						</div> --%>
					</c:if>
			 
			 
			 
			 
			 
			 
			 
			<!------------------------------- DOCUMENT UPLOAD SECTION ---------------------------------------------------------------------------------------------------->
			<%-- <c:if test="${(empty command.cfcAttachmentsAfterReject)}"> 
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
				
			<!-- </div> -->
		</c:if> --%>
		
		 <%-- <c:if test="${(not empty command.cfcAttachmentsAfterReject)  and (fn:length(command.cfcAttachmentsAfterReject) > 0 )}">					
                       <div class="widget-header">
						<h2><spring:message code="eip.agency.login.reUploadDoc" />
						    <spring:message code="eip.agency.login.uploadDoc" /></h2>
						</div>
				       <table class="table table-hover table-striped table-bordered">
				       		<tbody>
								 <tr>
									 <th><label class="tbold"><spring:message code="eip.agency.requireDoc"/></label></th>
									 <th><label class="tbold"><spring:message code="eip.agency.Status"/></label></th>
									 <th><label class="tbold"><spring:message code="eip.agency.upload"/></label></th>
								 </tr>
								<c:forEach items="${command.cfcAttachmentsAfterReject}" var="lookUp" varStatus="lk">
									<tr>
									<td><label>${lookUp.clmDesc}</label></td>
									<td><label>${lookUp.approvalStatus}</label></td>
									<td><apptags:formField fieldType="7"
										fieldPath="attachDocument[${lk.index}].attPath"
										labelCode="" currentCount="${lk.index}"
										showFileNameHTMLId="true" folderName="${lk.index}"
										fileSize="BND_COMMOM_MAX_SIZE"
										maxFileCount="CHECK_LIST_MAX_COUNT"
										validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
										checkListMandatoryDoc="${lookUp.chkStatus}"
										checkListDesc="${lookUp.clmDesc}"
										checkListId="${lookUp.clmId}"
										checkListMStatus="${lookUp.clmStatus}"
										checkListSStatus="${lookUp.chkStatus}"
										checkListSrNo="${lookUp.clmSrNo}" /></td>
									</tr>
							</c:forEach>
						</tbody>
				 </table>
				 
				 <table class="table table-hover table-striped table-bordered">
							
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
				 
	     </c:if> --%>
			<%-- <div class="form-elements clear padding_top_10">
				<div class="element">
					<label><spring:message code="tp.amount"/> :</label>
					<form:input path=""  value="" readonly="true" cssClass="disablefield"/>
				</div>
			</div> --%>
			<%-- <c:if test="${(empty command.cfcAttachmentsAfterReject)}">
			 		<jsp:include page="/jsp/payment/onlineOfflinePay.jsp"/>
					<div class="text-center padding-10">
					    <input type="button" class="btn btn-success" onclick="return saveData(this);" value="Submit" />
                		<!-- <input type="button" class="btn btn-success" onclick="return saveTPLicRegForm(this);" value=<spring:message code="marriage.submitandpay" /> id="submitAndPayButtonId" /> -->
                	</div>
             </c:if>
              <c:if test="${(not empty command.cfcAttachmentsAfterReject)  and (fn:length(command.cfcAttachmentsAfterReject) > 0 )}">
              		<div class="text-center padding-10">
					    <input type="button" class="btn btn-success" onclick="return saveReUploadData(this);" value="Submit" />
                		<!-- <input type="button" class="btn btn-success" onclick="return saveTPLicRegForm(this);" value=<spring:message code="marriage.submitandpay" /> id="submitAndPayButtonId" /> -->
                	</div>
              </c:if> --%>
			<%-- <div id="certificateDetail" style="display: none;">
			<div id="certificateDetail">
	                  <div class="regheader"><spring:message code="water.application.fee.details" /></div>
	                  <div class="form-elements clear">                    
	                    <div class="element">					
							<label for=""><spring:message code="marriage.application.number"/>:</label>
							<span><form:input path="applicationId" id="applicationId" readonly="true"  cssClass="disablefield"/></span>
						</div>	
						<div class="element">								
							<label for=""><spring:message code="marriage.form.payment.amount" /></label>
							<span><form:input path="chargeAmountToPay" id="chargeAmountToPay" readonly="true" cssClass="disablefield"/></span>
						</div>
	                  </div>
	                  <div class="btn_fld margin_top_10">
	                    	<input type="button" class="css_btn"	onclick="return saveTPLicRegForm(this);" value=<spring:message code="marriage.submitandpay" /> id="submitAndPayButtonId" />
	 						<input id="cancelButtonId" type="button" class="css_btn" onclick="goBack()" value="<spring:message code="marriage.cancelElement" />" />	
	                  </div>
             	 </div>
              </div> --%>
			<%-- </c:if> --%>
			
		
			
			
			
			<%-- <div class="btn_fld clear margin_top_10">
				<input type="button" value="<spring:message code="bt.save" text="bt.save"/>" onclick="return saveForm(this);"  class="css_btn">
				<input type="button" value="<spring:message code="bt.clear" />" onclick="clearForm('TPTechnicalPerson.html')" class="css_btn">
			</div> --%>
		</form:form>
		</div>
	</div>
</div>

