<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.io.*,java.util.*" %>
<link href="../assets/libs/jqueryui/jquery-ui-datepicker.css" rel="stylesheet" type="text/css">
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/rnl/master/contractAgreement.js"></script>

<script>
$(document).ready(function(){
onLoadUploadPage();
}); 
</script>	
		<div class="widget margin-bottom-0 width-400">
			<div class="widget-header">
				<h2><spring:message code="rnl.master.cont.upload" text="Contract Photo/Thumb Upload"></spring:message></h2>
			</div>
			<div class="widget-content padding">
         <form:form action="ContractAgreement.html"
					class="form-horizontal form" name="ContractAgreement"
					id="ContractAgreement">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					  <div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
	     <button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span>&times;</span></button>
	 	<span id="errorId"></span>
     </div> 
           <form:hidden path="photoId"  id="photoId"/> 
            <form:hidden path="thumbId"  id="thumbId"/>   
                        <div class="form-group">
                          <div class="col-sm-6">
                            <div class="thumbnail">
                              <h5 class="text-center"><spring:message code="rnl.master.photos" text="Photos"></spring:message></h5>
                              <div id="showPhoto">  
                              </div>
                              <div class="caption text-center">        
                                <div id="pho" class="text-center">
                             <c:if test="${command.modeType ne 'V'}">
                              	<div id="addPhoto">
		                          	<apptags:formField fieldType="7" labelCode=""
											hasId="true"
											fieldPath="contractMastDTO.contractPart1List[1].contp1PhotoFilePathName"
											isMandatory="false" showFileNameHTMLId="true" 
											fileSize="BND_COMMOM_MAX_SIZE"
											maxFileCount="CHECK_LIST_MAX_COUNT"
		             						validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
											currentCount="${command.getPhotoId()}" />
									
                                </div>
                                <div class="col-xs-6"><div id="removePhoto"></div></div>
                             </c:if>
                                	 </div>
									<c:if test="${command.modeType eq 'V'}">
										<c:set var="code" value="${command.getViewuploadId()}" /> 
											<c:if test="${command.getUploadType() eq 'U' || command.getUploadType() eq 'UW'}">
		                                 		<c:if test="${command.contractMastDTO.contractPart1List[code].getContp1PhotoFilePathName() ne null}">			
													<apptags:filedownload filename="${command.contractMastDTO.contractPart1List[code].getContp1PhotoFileName()}" filePath="${command.contractMastDTO.contractPart1List[code].getContp1PhotoFilePathName()}" actionUrl="ContractAgreement.html?Download"/>
	 											</c:if>
	 										</c:if>											
											<c:if test="${command.getUploadType() eq 'V' || command.getUploadType() eq 'VW'}">
												<c:if test="${command.contractMastDTO.contractPart2List[code].getContp2PhotoFilePathName() ne null}">			
													<apptags:filedownload filename="${command.contractMastDTO.contractPart2List[code].getContp2PhotoFileName()}" filePath="${command.contractMastDTO.contractPart2List[code].getContp2PhotoFilePathName()}" actionUrl="ContractAgreement.html?Download"/>
	 											</c:if>
	 										</c:if>
 									</c:if>
                               </div>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <div class="thumbnail text-center">
                              <h5 class="text-center"><spring:message code="rnl.thumb.impression" text="Thumb Impression"></spring:message></h5>
                             <div id="showThumb">                               
                              </div>
                              <div class="caption">
                                <div id="thum" class="text-center"> 
                                 <c:if test="${command.modeType ne 'V'}">
	                                 <div id="addThumb" >
		       							 <apptags:formField fieldType="7" labelCode=""
													hasId="true"
													fieldPath="contractMastDTO.contractPart1List[1].contp1ThumbFilePathName"
													isMandatory="false" showFileNameHTMLId="true"
													fileSize="BND_COMMOM_MAX_SIZE"
													maxFileCount="CHECK_LIST_MAX_COUNT"
				             						validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
													currentCount="${command.getThumbId()}" />   
									</div>
									<div id="removeThumb" class="col-xs-6"><div id="removeButton"></div></div>
								</c:if>
										 	<c:if test="${command.modeType eq 'V'}">
											<c:set var="code" value="${command.getViewuploadId()}" /> 
											<c:if test="${command.getUploadType() eq 'U' || command.getUploadType() eq 'UW'}">
		                                 	<c:if test="${command.contractMastDTO.contractPart1List[code].getContp1ThumbFilePathName() ne null}">			
											<apptags:filedownload filename="${command.contractMastDTO.contractPart1List[code].getContp1ThumbFileName()}" filePath="${command.contractMastDTO.contractPart1List[code].getContp1ThumbFilePathName()}" actionUrl="ContractAgreement.html?Download"/>
											</c:if>
	 										</c:if>											
											<c:if test="${command.getUploadType() eq 'V' || command.getUploadType() eq 'VW'}">
											<c:if test="${command.contractMastDTO.contractPart2List[code].getContp2ThumbFilePathName() ne null}">			
											<apptags:filedownload filename="${command.contractMastDTO.contractPart2List[code].getContp2ThumbFileName()}" filePath="${command.contractMastDTO.contractPart2List[code].getContp2ThumbFilePathName()}" actionUrl="ContractAgreement.html?Download"/>
											</c:if>
											</c:if>
	 										</c:if>
								                        </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="text-center">
                         <c:if test="${command.modeType ne 'V'}">
                          <button type="button" onclick="submitUpload()" class="btn btn-success btn-submit">Submit</button>
                          <button type="button" onclick="deleteUploadedFile()" class="btn btn-danger">Cancel</button>
                          </c:if>
                            <c:if test="${command.modeType eq 'V'}">
                             <button type="button" onclick="submitUpload()" class="btn btn-danger">Back</button>
                            </c:if>
                        </div>
                     
          </form:form>
                        </div>
                      </div>