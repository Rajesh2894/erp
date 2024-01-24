<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script type="text/javascript" src="js/cfc/challan/offlinePay.js"></script>
<script>
jQuery('.hasNumber').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
   
});
</script>

<c:if test="${not empty command.checkList || command.amountToPay ne 0.0}"> 
<div class="panel panel-default">
                 
                  <c:if test="${not empty command.checkList }">
					  <h4 class="panel-title"> <a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#Upload_Attachment"> <spring:message code="rnl.payment.upload" text="Upload Attachment"></spring:message><small class="text-blue-2">(Upload File up to 200KB and only .excel .png .pdf or .doc)</small></a> </h4>
               
                <div id="Upload_Attachment" class="panel-collapse collapse in">
                  <div class="panel-body">
						<div class="overflow margin-top-10">
							<div class="table-responsive">
								<table class="table table-hover table-bordered table-striped">
									<tbody>
										<tr>
											<th><spring:message code="" text="Sr No" /></th>
											<th><spring:message code="" text="Document Name" /></th>
											<th><spring:message code="" text="Status" /></th>
											<th><spring:message code="" text="Upload" /></th>
										</tr>
										<c:forEach items="${command.checkList}" var="lookUp"
											varStatus="lk">
											<tr>
												<td>${lookUp.documentSerialNo}</td>
												<c:choose>
													<c:when
														test="${userSession.getCurrent().getLanguageId() eq 1}">
														<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
														<td><label>${lookUp.doc_DESC_ENGL}</label></td>
													</c:when>
													<c:otherwise>
														<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
														<td><label>${lookUp.doc_DESC_Mar}</label></td>
													</c:otherwise>
												</c:choose>
												<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
													<td><spring:message code="water.doc.mand" /></td>
												</c:if>
												<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
													<td><spring:message code="water.doc.opt" /></td>
												</c:if>
												<td>
													<div id="docs_${lk}" class="">
														<apptags:formField fieldType="7" labelCode="" hasId="true"
															fieldPath="checkList[${lk.index}]" isMandatory="false"
															showFileNameHTMLId="true" fileSize="MIN_FILE_SIZE"
															checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
															maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="ALL_UPLOAD_VALID_EXTENSION"
															currentCount="${lk.index}" checkListDesc="${docName}" />
													</div>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</c:if>
        </div>
         
             
			 
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#Payment_mode"> <spring:message code="rnl.book.payment" text="Payment"></spring:message></a></h4>
                </div>
                <div id="Payment_mode" class="panel-collapse collapse in">
                         <div class="panel-body">
                   
								
								            <h4><spring:message code="rnl.book.mode" text="Mode"></spring:message></h4>
													<div class="form-group">
														<label class="control-label col-sm-2"><spring:message code="rti.selectPaymrntMode" /><span class="mand">*</span></label>
															<div class="radio col-sm-8">
															 <label class=" margin-right-20" id="offlineLabel"> <form:radiobutton
															path="offlineDTO.onlineOfflineCheck" value="N" disabled="${command.amountToPay eq 0.0}"
															onclick="showDiv(this);" id="offlinebutton" /> <spring:message
															code="rti.offlinePay" />
					                                        </label>
					                                        
														   <c:if test="${command.getLoggedInUserType() eq 'CFC' ||command.getLoggedInUserType() eq ''}">
															<label >
															<form:radiobutton path="offlineDTO.onlineOfflineCheck" value="P" id="payAtCounter" onclick="showDiv(this);" disabled="${command.amountToPay eq 0.0}" />
															<spring:message code="rti.payAtCounter" />
															</label>
														</c:if> 
															</div>
                                                     </div>
														 <div class="offlinepayment" id="offlinepayment">
							          	<div class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
											code="rti.offlinePaymentSelection" /></label>
										<div class="col-sm-4">						
									<c:set var="baseLookupCode" value="OFL" />
									<form:select path="offlineDTO.oflPaymentMode" cssClass="form-control" id="oflPaymentMode">
										<c:forEach items="${command.getLevelData(baseLookupCode)}" var="oflMode">
											<form:option  code="${oflMode.lookUpCode}" value="${oflMode.lookUpId}" >${oflMode.lookUpDesc}</form:option>
											</c:forEach>
						</form:select>	
						</div>
												<script type="text/javascript">
												fieldsVisible(${command.offlinePay},${command.getBank()},${command.getUlb()},${command.getDd()},${command.getPostal()});
												</script>
							
							</div>
						
						</div>


				<div class="PPO" id="PPO">
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="payment.freeMode" /></label>
						<apptags:lookupField items="${command.userSession.paymentMode}"
							path="offlineDTO.payModeIn" cssClass="form-control"
							changeHandler="enableDisableCollectionModes(this)"
							selectOptionLabelCode="rti.sel.paymentmode" hasId="true"
							isMandatory="true">
						</apptags:lookupField>
					</div>
				</div>


				<div class="overflow CPAUC">
					<h4>
						<spring:message code="payment.header.name" />
					</h4>

					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="payment.drawnOn" /></label>
						<div class="col-sm-4">
							<form:select path="offlineDTO.cbBankId"
								onchange="getBankCode(this)" id="bankID" cssClass="form-control">
								<form:option value="">
									<spring:message code="rti.sel.bank" />
								</form:option>
								<form:options items="${command.appSession.customerBanks}" />
							</form:select>
						</div>

						<label class="col-sm-2 control-label"><spring:message
								code="payment.bankCode" /></label>
						<div class="col-sm-4">
							<form:input path="offlineDTO.bmDrawOn" class="form-control"
								readonly="true" id="drawnOn" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="payment.accountNo" /></label>
						<div class="col-sm-4">
							<form:input path="offlineDTO.bmBankAccountId"
								class="form-control hasNumber" id="acNo" maxlength="16" />
						</div>


						<label class="col-sm-2 control-label required-control"
							id="selectType"><spring:message
								code="payment.checkOrDDNo" /></label>
						<div class="col-sm-4">
							<form:input path="offlineDTO.bmChqDDNo"
								class="form-control hasNumber" id="chqNo" maxlength="10" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label required-control"
							id="selectDate"><spring:message
								code="payment.checkOrDDDate" /></label>
						<div class="col-sm-4">
							<apptags:dateField fieldclass="lessthancurrdate chqDate"
								datePath="offlineDTO.bmChqDDDate" cssClass="form-control" />
						</div>

						<label class="col-sm-2 control-label"><spring:message
								code="payment.reciept.amount" /></label>
						<div class="col-sm-4">
							<form:input path="offlineDTO.amountToShow"
								class="form-control amountAlign" readonly="true"
								id="amountToPay" />
						</div>
					</div>

					<input type="hidden"
						value="<spring:message code="pay.option.poNo" />" id="PO" /> <input
						type="hidden" value="<spring:message code="pay.option.ddNo" />"
						id="DD" /> <input type="hidden"
						value="<spring:message code="pay.option.account" />" id="CQ" /> <input
						type="hidden"
						value="<spring:message code="pay.option.poNoDate" />" id="POD" />
					<input type="hidden"
						value="<spring:message code="pay.option.ddNoDate" />" id="DDD" />
					<input type="hidden"
						value="<spring:message code="pay.option.accountDate" />" id="CQD" />
				</div>
				<div class="form-group margin-top-10">
												<label class="col-sm-2 control-label"> <spring:message
														code="water.field.name.amounttopay" />
												</label>
												<div class="col-sm-4">
													<input type="text" class="form-control"
														value="${command.offlineDTO.amountToShow}"  data-rule-required="true" data-rule-required="12" readonly="readonly"/> <a
														class="fancybox fancybox.ajax text-small text-info"
														href="EstateBooking.html?showRnLChargeDetails">
														<spring:message code="water.field.name.amounttopay" /> <i
														class="fa fa-question-circle "></i>
													</a>
												</div>
											</div>
									
								<%-- <div class="form-group">
			                      <div class="col-xs-10 col-xs-push-2">
			                        <label class="checkbox-inline">
			                          <form:checkbox path="accept" data-rule-required="true"></form:checkbox>
			                           <strong> <spring:message code="rnl.payment.accept" text="I Accept"></spring:message>
			                              <c:choose><c:when test="${not empty command.docName}">
									        <a href="javascript:void(0);" onclick="downloadFile('${command.docName}','EstateBooking.html?Download')"><spring:message code="rnl.payment.terms" text="Terms"></spring:message>&amp;<spring:message code="rnl.payment.cond" text="Conditions"></spring:message></a></c:when>
									      <c:otherwise> <a href="#" ><spring:message code="rnl.payment.terms" text="Terms"></spring:message> &amp;<spring:message code="rnl.payment.cond" text="Conditions"></spring:message></a></c:otherwise>
									      </c:choose>
									   </strong> 
									    <span></span>
									   </label>
			                      </div>
			                    </div> --%>
                  </div>
                </div>
              </div>
         </c:if>  
         
           