<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<script src="js/mainet/file-upload.js"></script>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script src="js/challan/offlinePay.js"></script>
<script>
jQuery('.hasNumber').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
   
});
</script>
<c:if test="${not empty command.checkList && command.amountToPay ne 0.0}"> 
<div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#Upload_Attachment"> <spring:message code="rnl.payment.upload" text="Upload Attachment"></spring:message><small class="text-blue-2">(Upload File upto 5MB and only .jpg, .jpeg or .gif)</small></a> </h4>
                </div>
                <div id="Upload_Attachment" class="panel-collapse collapse in">
                  <div class="panel-body">
                    
										<div class="overflow margin-top-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped">
													<tbody>
														<tr>
															<th> <spring:message
																		code="water.serialNo" text="Sr No" />
															</th>
															<th> <spring:message
																		code="water.docName" text="Document Name" />
															</th>
															<th> <spring:message
																		code="water.status" text="Status" />
															</th>
															<th> <spring:message
																		code="water.uploadText" text="Upload" />
															</th>
														</tr>
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
																<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																	<td> <spring:message
																				code="water.doc.mand" />
																	</td>
																</c:if>
																<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																	<td> <spring:message
																				code="water.doc.opt" />
																	</td>
																</c:if>
																	<td><div id="docs_${lk}" class="">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath="checkList[${lk.index}]"
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="BND_COMMOM_MAX_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
																			currentCount="${lk.index}" />
																	</div>
																	</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									
                  </div>
                </div>
        </div>
         
             
			 
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#Payment_mode"> <spring:message code="rnl.book.payment" text="Payment"></spring:message></a></h4>
                </div>
                <div id="Payment_mode" class="panel-collapse collapse in">
                         <div class="panel-body">
											<div class="form-group">
		                                        <label class="control-label col-sm-2"><spring:message text="Payment Mode"/><span class="mand">*</span></label>
			                                       <div class="radio">
														<label class="col-sm-1 radio-inline" id="onlineLabel">
														<form:radiobutton path="offlineDTO.onlineOfflineCheck" value="Y"  disabled="false" onclick="showDiv(this);" checked="checked" data-rule-required="true"/>
														<spring:message code="onlineoffline.label.onlinePay"/>
														</label>
														<label class="radio-inline" id="offLineLabel">
														<form:radiobutton path="offlineDTO.onlineOfflineCheck" value="N" onclick="showDiv(this);" id="onlineOfflineCheck1" />
														<spring:message code="onlineoffline.label.offlinePay"/>			
														</label>
											       </div>	
										   </div>
										   <div class="offlinepayment" id="offlinepayment">
	<div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message
					code="onlineoffline.label.offlinePaymentSelection" /></label>


			<c:set var="baseLookupCode" value="OFL" />
			<div class="col-sm-4">
			<form:select path="offlineDTO.oflPaymentMode" cssClass="form-control" id="oflPaymentMode">
				<c:forEach items="${command.getLevelData(baseLookupCode)}" var="oflMode">
					<form:option  code="${oflMode.lookUpCode}" value="${oflMode.lookUpId}" >${oflMode.lookUpDesc}</form:option>
					</c:forEach>
			</form:select>	
			</div>
		
	<script>
	fieldsVisible(${command.offlinePay},${command.getBank()},${command.getUlb()},${command.getDd()},${command.getPostal()});
	</script>



	</div>

</div>
											<div class="form-group margin-top-10">
												<label class="col-sm-2 control-label"> <spring:message
														code="water.field.name.amounttopay" />
												</label>
												<div class="col-sm-4">
													<input type="text" class="form-control"
														value="${command.offlineDTO.amountToShow}"  data-rule-required="true" data-rule-required="12" readonly="readonly"/> <a
														class="fancybox fancybox.ajax text-small text-info"
														href="WasteCollector.html?showSWMChargeDetails">
														<spring:message code="water.field.name.amounttopay" /> <i
														class="fa fa-question-circle "></i>
													</a>
												</div>
											</div>
									
								<div class="form-group">
			                      <div class="col-xs-10 col-xs-push-2">
			                        <label class="checkbox-inline">
			                          <form:checkbox path="accept" data-rule-required="true"></form:checkbox>
			                           <strong> <spring:message code="rnl.payment.accept" text="I Accept"></spring:message>
			                              <c:choose><c:when test="${not empty command.docName}">
									        <a href="javascript:void(0);" onclick="downloadFile('${command.docName}','WasteCollector.html?Download')"><spring:message code="rnl.payment.terms" text="Terms"></spring:message>&amp;<spring:message code="rnl.payment.cond" text="Conditions"></spring:message></a></c:when>
									      <c:otherwise> <a href="#" ><spring:message code="rnl.payment.terms" text="Terms"></spring:message> &amp;<spring:message code="rnl.payment.cond" text="Conditions"></spring:message></a></c:otherwise>
									      </c:choose>
									   </strong> 
									    <span></span>
									   </label>
			                      </div>
			                    </div>
                  </div>
                </div>
              </div>
         </c:if> 