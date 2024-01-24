<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<div id="checkListAndChargeId">
		<c:if test="${command.enableSubmit eq false}">
          <div class="text-center">
						<button type="button" class="btn btn-success" id="confirmToProceedId"
								onclick="getChecklistAndCharges(this)"><spring:message code="water.btn.proceed"/></button>
			</div>	
		
		</c:if>	
		
		 <c:if test="${command.payable eq true  || not empty command.checkList}">
			<div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title">
                  	<a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#Document_Attachment">
						<spring:message code="water.documentattchmnt"/> <small class="text-blue-2">(UploadFile upto 5MB and only .pdf or .doc)</small>
					</a> </h4>
                </div>
                <div id="Document_Attachment" class="panel-collapse in collapse">
                  <div class="panel-body">
                 <c:if test="${not empty command.checkList}">
                  	<div class="table-responsive">
						<table class="table table-hover table-bordered table-striped">
							<tr>
								<th><label class="tbold"><spring:message
											code="label.checklist.srno" /></label></th>
								<th><label class="tbold"><spring:message
											code="label.checklist.docname" /></label></th>
								<th><label class="tbold"><spring:message
											code="label.checklist.status" /></label></th>
								<th><label class="tbold"><spring:message
											code="label.checklist.upload" /></label></th>
							</tr>
							<tr>
								<c:forEach items="${command.checkList}" var="lookUp"	varStatus="lk">
									<tr>
										<td><label>${lookUp.documentSerialNo}</label></td>
										<c:choose>
											<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
												<c:set var="docName" value="${lookUp.doc_DESC_ENGL }"/>
												<td><label>${lookUp.doc_DESC_ENGL}</label></td>
											</c:when>
											<c:otherwise>
												<c:set var="docName" value="${lookUp.doc_DESC_ENGL }"/>
												<td><label>${lookUp.doc_DESC_Mar}</label></td>
											</c:otherwise>
										</c:choose>
										
										<c:choose>
											<c:when test="${lookUp.checkkMANDATORY eq 'Y'}">
												<td><label><spring:message
															code="label.checklist.status.mandatory" /></label></td>
											</c:when>
											<c:otherwise>
												<td><label><spring:message
															code="label.checklist.status.optional" /></label></td>
											</c:otherwise>
										</c:choose>
										<td>
											<div id="docs_${lk}">
												<apptags:formField fieldType="7" labelCode="" hasId="true"
													fieldPath="requestDTO.fileList[${lk.index}]"
													isMandatory="false" showFileNameHTMLId="true"
													fileSize="BND_COMMOM_MAX_SIZE"
													checkListMandatoryDoc="${lookUp.checkkMANDATORY}" 
													maxFileCount="CHECK_LIST_MAX_COUNT"
													checkListDesc="${docName}"
													validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
													currentCount="${lk.index}" />
											</div>
										</td>
									</tr>
								</c:forEach>
							</tr>

						</table>
					</div>
					
					</c:if>
				
				
				
				<c:if test="${command.payable eq true }">
					<div class="form-group margin-top-10">
						<label class="col-sm-2 control-label"><spring:message
								code="water.field.name.amounttopay" /></label>
						<div class="col-sm-4">
							<input type="text" class="form-control"
								value="${command.offlineDTO.amountToShow}" maxlength="12"></input>
							<!-- <a class="fancybox fancybox.ajax text-small text-info" href="javascript:void(0);" onclick="showChargeInfo()">Charge Details <i class="fa fa-question-circle "></i></a> -->
							<a class="fancybox fancybox.ajax text-small text-info"
								href="ChangeOfUsage.html?showChargeDetails"><spring:message
									code="water.lable.name.chargedetail" /> <i
								class="fa fa-question-circle "></i></a>
						</div>
					</div>
					<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp"/>
				</c:if>
					
                  
                  </div>
                </div>
              </div>
		</c:if>		
		</div>