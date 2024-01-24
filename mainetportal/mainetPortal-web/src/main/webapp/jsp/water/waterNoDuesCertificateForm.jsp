 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/water/noDuesCertificate.js"></script>
<script src="js/mainet/validation.js"></script>
  <div id="validationDiv">
   <apptags:breadcrumb></apptags:breadcrumb>
    <div class="content"> 
      
          <div class="widget">
            <div class="widget-header">
              <h2><spring:message code="water.nodues.noduesservice"/></h2>
              <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
            </div>
            <div class="widget-content padding">
              <div class="mand-label clearfix"><span><spring:message code="water.fieldwith"/> <i class="text-red-1">*</i> <spring:message code="water.ismandtry"/></span></div>
              <form:form action="NoDuesCertificateController.html"  class="form-horizontal form"  name="noDuesCertificate" id="noDuesCertificate">
               <jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="" id="checkListFlag"
						value="${command.checkListFlag}" />
				<div class="msg-error alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
					<ul><li><label id="errorId"></label></li></ul>
				</div>
       			
       			<div class="panel-group accordion-toggle" id="accordion_single_collapse">                                 
               
                <apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail>
                <%-- <jsp:include page="/jsp/mainet/applicantDetails.jsp"></jsp:include> --%>
                  
                  
               <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title">
                  	<a data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" href="#water_nodues">
						<spring:message code="water.nodues.noduesinfo"/>
					</a>
				</h4>
                </div>
                <div id="water_nodues" class="panel-collapse collapse" id="NoDuesInfo">
                  <div class="panel-body">
                   	<div class="form-group">
	                  <label class="col-sm-2 control-label required-control"><spring:message code="water.nodues.connectionNo"/></label>
	                  <div class="col-sm-4">
	                  
	                    <form:input  path="reqDTO.consumerNo" name="consumerNo"  id ="consumerNo" type="text" class="form-control"  data-rule-required="true"></form:input>
	                  </div>
	                  <%-- <div class="col-sm-6">
	                    <button type="button" class="btn btn-success" onclick="searchConnection(this)"><strong class="fa fa-search"></strong> <spring:message code="water.nodues.search"/></button>
	                  </div> --%>
	                  
	                  <div class="col-sm-6">
	                    <button type="button" class="btn btn-success" id="viewdues" onclick="searchConnection(this)"><strong class="fa fa-search"></strong> <spring:message code="" text="View Dues"/></button>

									
									<button type="button" class="btn btn-warning"
										id="reset" onclick="resetConnection(this)">
										<spring:message code="water.btn.reset" text="Reset"/>
									</button>

										</div>
	                  
	                </div> 
	                <div id="consumerDetails">
	                <div class="form-group">
	                  <label class="col-sm-2 control-label"><spring:message code="water.nodues.consumername"/></label>
	                  <div class="col-sm-4">
	                    <form:input  path="reqDTO.consumerName" name="consumerName" id="consumerName" type="text" class="form-control" readonly="true"></form:input>
	                  </div>
	                  <label class="col-sm-2 control-label"><spring:message code="water.nodues.consumeradd"/></label>
	                  <div class="col-sm-4">
	                    <form:textarea  path="reqDTO.consumerAddress" id="consumerAddress" name="consumerAddress" class="form-control" readonly="true"></form:textarea> 
	                  </div>
	                </div>
	               <%--  <div class="form-group" id="finList">                 
	                   <label class="col-sm-2 control-label required-control"><spring:message code="water.lable.name.finYear" /></label>				
						<div class="col-sm-4">
						<form:select path="reqDTO.finYear" name="onYearSelect" class="form-control" id="onYearSelect"  data-rule-required="true">				
						  <form:option value="-1"> <spring:message code="water.lable.name.finYear" /></form:option>								
						  <form:options items="${command.finYear}" itemValue="id" itemLabel="text"/>
																						   					   
						</form:select>
					
						</div>	
					  <label class="col-sm-2 control-label required-control"><spring:message code="water.lable.name.noOfcopies" /></label>
		              <div class="col-sm-4">
		                <form:input path ="reqDTO.noOfCopies" id ="noOfCopies" name="noOfCopies" type="text" maxlength="5" class="form-control hasNumber"  data-rule-required="true"></form:input>
		              </div>
		            </div>      --%>       
		            
		             <div class="form-group">
	                 <label class="col-sm-2 control-label required-control"><spring:message code="water.lable.name.noOfcopies" /></label>
		              <div class="col-sm-4">
		                <form:input path ="reqDTO.noOfCopies" id ="noOfCopies" name="noOfCopies" type="text" maxlength="5" class="form-control hasNumber"  data-rule-required="true"></form:input>
		              </div>
	                  <label class="col-sm-2 control-label"><spring:message code="water.nodues.dueAmount" text="DueAmount"/></label>
	                  <div class="col-sm-4">
	                    <form:input path ="reqDTO.duesAmount" id ="duesAmount" name="duesAmount" type="text" class="form-control text-right" readonly="true"></form:input>
	                  </div>
	                </div>  
	                
	              	<div class="form-group" id="otpdetails">
	                  <label class="col-sm-2 control-label required-control"><spring:message code="water.nodues.enter.otp" text="Enter Otp"/></label>
	                  <div class="col-sm-4">
	                    <form:input  path="userOtp" name="userOtp"  id ="userOtp" type="text" class="form-control"  data-rule-required="true"></form:input>
	                  </div>
	                  <div class="col-sm-6">
					<button type="button" class="btn btn-success" onclick="generateOtp(this)"> <spring:message code="water.nodues.generate.otp" text="GenerateOtp" /> </button>
			   </div>
	                </div> 
	                
	                <div class="form-group text-center padding-bottom-20" id="submitOtp">
					<button type="button" class="btn btn-success" onclick="getNODuesChecklistAndCharges(this)"> <spring:message code="water.nodues.submit" /> </button>
			   </div>
	
	                
	                </div>
	                <div class="form-group text-center padding-bottom-20" id="back">

								<input type="button" class="btn btn-danger"
									onclick="window.location.href='CitizenHome.html'" value="Back">
							</div>
							
							
                  </div>
                </div>
              </div>
               
             
               
			   <c:if test="${command.checkListApplFlag eq 'A'}">
			   <div class="accordion-toggle">
				 <h4 id="checkListDetails" class="margin-top-0 margin-bottom-10 panel-title">
									<a data-toggle="collapse" class="collapsed"data-parent="#accordion_single_collapse" href="#DocumentAttachment"> 
								<spring:message code="water.documentattchmnt" /><small class="text-blue-2"><spring:message code="water.uploadfile.validtn" /></small></a>
				</h4>
                <div class="panel-collapse collapse in" id="DocumentAttachment">  			                  
                   <div id="waterformappdetails">
								<div class="panel-body">
									<c:if test="${not empty command.checkList}">
										<div class="overflow margin-top-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped">
													<tbody>
														<tr>
															<th><label class="tbold"><spring:message
																		code="water.serialNo" text="Sr No" /></label></th>
															<th><label class="tbold"><spring:message
																		code="water.docName" text="Document Name" /></label></th>
															<th><label class="tbold"><spring:message
																		code="water.status" text="Status" /></label></th>
															<th><label class="tbold"><spring:message
																		code="water.uploadText" text="Upload" /></label></th>
														</tr>

														<c:forEach items="${command.checkList}" var="lookUp"
															varStatus="lk">

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
																<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																	<td><label><spring:message
																				code="water.doc.mand" /></label></td>
																</c:if>
																<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																	<td><label><spring:message
																				code="water.doc.opt" /></label></td>
																</c:if>
																<td>
																	<div id="docs_${lk}" class="">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath="checkList[${lk.index}]"
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="BND_COMMOM_MAX_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
																			currentCount="${lk.index}" />
																	</div>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</c:if>
                                   </div>
                                   
                                   </div>	
							</div>
                      </div>
                      </c:if>
									<form:hidden path="free" id="free" />
									<div id="payment">
										<c:if test="${command.free eq 'N'}">
											<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
											<div class="form-group margin-top-10">
												<label class="col-sm-2 control-label"><spring:message
														code="water.field.name.amounttopay" /></label>
												<div class="col-sm-4">
													<input type="text" class="form-control"
														value="${command.offlineDTO.amountToShow}" maxlength="12"
														readonly="readonly"></input> <a
														class="fancybox fancybox.ajax text-small text-info"
														href="NoDuesCertificateController.html?showChargeDetails"><spring:message
															code="water.lable.name.chargedetail" /> <i
														class="fa fa-question-circle "></i></a>
												</div>
											</div>
										</c:if>
									</div>
									
								
                    </div>        
				<div class="text-center padding-bottom-20" id="divSubmit">
					<button type="button" class="btn btn-success"  onclick="saveNoDuesCertificateForm(this)" id="submit"><spring:message code="water.nodues.submit"/></button>
				    <button type="button" class="btn btn-danger"   onclick="window.location.href='CitizenHome.html'"><spring:message code="water.nodues.cancel"/></button>
			   </div>
               
              </form:form>
            </div>
          </div>
      </div>
      </div>