
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/water/noDuesCertificate.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<div id="NoDuesCertiForm">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
		<!-- Start info box -->
		<div class="widget">
			<div class="widget-header">
				<h2>
					<label><spring:message code="water.nodues.noduesservice" /></label>
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"></i></a>
				</div>
			</div>
			<div class="widget-content padding">
				<div class="mand-label clearfix">
					<span><spring:message code="water.fieldwith" /><i
						class="text-red-1">*</i> <spring:message code="water.ismandtry" />
					</span>
				</div>
				<form:form action="NoDuesCertificateController.html"
					class="form-horizontal form" name="noDuesCertificate"
					id="noDuesCertificate">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="msg-error alert alert-danger alert-dismissible"
						id="errorDivId" style="display: none;">
						<ul>
							<li><label id="errorDivId"></label></li>
						</ul>
					</div>

					<apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail>
				

					<div class="accordion-toggle">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#NoDuesInfo"><label><spring:message
										code="water.nodues.noduesinfo" /></label></a>
						</h4>
						<div class="panel-collapse collapse in" id="NoDuesInfo">

							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="water.nodues.connectionNo" /></label>
								<div class="col-sm-4">
									<form:input path="reqDTO.consumerNo" name="consumerNo"
										id="consumerNo" type="text" class="form-control"></form:input>
								</div>
								<div class="col-sm-6">
									<button type="button" class="btn btn-success"
										onclick="searchConnection(this)" id="viewdues">
										<strong class="fa fa-search"></strong>
										<spring:message code="water.view.dues" />
									</button>
									
									<button type="button" class="btn btn-success"
										id="resetConnection" onclick="resetConnDetails()">
										<spring:message code="rstBtn"/>
									</button>
									
								</div>
								
							</div>
              
							<div class="form-group" id="consumerDetails">
								<label class="col-sm-2 control-label"><spring:message
										code="water.nodues.consumername" /></label>
								<div class="col-sm-4">
									<form:input path="reqDTO.consumerName" name="consumerName"
										id="consumerName" type="text" class="form-control"
										readonly="true"></form:input>
								</div>
								<label class="col-sm-2 control-label"><spring:message
										code="water.nodues.consumeradd" /></label>
								<div class="col-sm-4">
									<form:textarea path="reqDTO.consumerAddress"
										id="consumerAddress" name="consumerAddress"
										class="form-control" readonly="true"></form:textarea>
								</div>
								</div>

								<div class="form-group" id="copies">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="water.lable.name.noOfcopies" /></label>
									<div class="col-sm-4">
										<form:input path="reqDTO.noOfCopies" id="noOfCopies"
											name="noOfCopies" type="text" class="form-control"></form:input>
									</div>
								
							
							    
									<label class="col-sm-2 control-label required-control"><spring:message
											code="" text="DueAmount"/></label>
											<div class="col-sm-4">
							<form:input path="reqDTO.duesAmount" id="duesAmount"
											name="duesAmount" type="text" class="form-control text-right"
											readonly="true"></form:input>
											</div>
                                      </div>


							
							<div class="form-group text-center padding-bottom-20" id="back">
								<!-- use form:input -->
								<input type="button" class="btn btn-danger"
									onclick="window.location.href='AdminHome.html'" value="Back">
							</div>

						</div>
					</div>
					
					
					 <%-- <div class="form-group" id="finList">                 
                   <label class="col-sm-2 control-label required-control"><spring:message code="water.lable.name.finYear" /></label>				
					<div class="col-sm-4">
					<form:select path="reqDTO.finYear" name="onYearSelect" class="form-control" id="onYearSelect" >				
					  <form:option value="-1"> <spring:message code="water.lable.name.finYear" /></form:option>								
					  <form:options items="${command.finYear}" itemValue="id" itemLabel="text"/>																					   					   
					</form:select>				
					</div>	
				  <label class="col-sm-2 control-label required-control"><spring:message code="water.lable.name.noOfcopies" /></label>
	              <div class="col-sm-4">
	                <form:input path ="reqDTO.noOfCopies" id ="noOfCopies" name="noOfCopies" type="text" class="form-control"></form:input>
	              </div>
	            </div> 	
	            </div>
                </div>--%>
                
					<%-- <div class="accordion-toggle" id="duelist">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#DueDetails"><label><spring:message
										code="water.nodues.duesDetail" /></label></a>
						</h4>
						<div class="panel-collapse collapse in" id="DueDetails">
							<table class="table table-hover table-bordered table-striped">
								<tr>
									<th><label><spring:message
												code="water.nodues.srno" /></label></th>
									<th><label><spring:message
												code="water.nodues.dueType" /></label></th>
									<th><i class="fa fa-inr"></i> <spring:message
											code="water.nodues.dueAmount" /></th>
								</tr>
								<tr>
									<td>1</td>
									<td><form:input path="reqDTO.waterDues" id="waterDues"
											name="waterDues" type="text" class="form-control"
											readonly="true"></form:input></td>
									<td><form:input path="reqDTO.duesAmount" id="duesAmount"
											name="duesAmount" type="text" class="form-control"
											readonly="true"></form:input>
								</tr>

							</table>
						</div>
					</div> --%>

					<%-- <div class="accordion-toggle" id="generateOtp">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#DueDetails"><label><spring:message
										code="" text="Enter OTP Details" /></label></a>
						</h4>
						<div class="form-group" id="otpDetails">

							<label class="col-sm-2 control-label required-control"><spring:message
									code="" text="Enter OTP" /></label>

							<div class="col-sm-4">
								<form:input path="validateOtp" name="otp" id="validateOtp"
									type="text" class="form-control" data-rule-required="true"></form:input>
							</div>

							<div class="col-sm-4">
								<button type="button" class="btn btn-success"
									onclick="generateOTP(this)">
									<strong class="fa fa-search"></strong>
									<spring:message code="generateOTP" text="GenerateOTP" />
								</button>
							</div>

						</div>

						<div class="form-group text-center padding-bottom-20"id="submitOtp">
							<button type="button" class="btn btn-success"
								onclick="getNODuesChecklistAndCharges(this)">
								<strong class="fa fa-search"></strong>
								<spring:message code="getNODuesChecklistAndCharges"
									text="Submit" />
							</button>
						</div>

					</div>
 --%>


					<div class="padding-top-10 text-center" id="chekListChargeId">
						<button type="button" class="btn btn-success"
							onclick="getNODuesChecklistAndCharges(this)">
							<spring:message code="water.btn.proceed" />
						</button>
					</div>
					<c:if test="${command.checkListApplFlag eq 'A'}">
						<div class="accordion-toggle">

							<h4 id="checkListDetails"
								class="margin-top-0 margin-bottom-10 panel-title">
								<a data-toggle="collapse"
									data-parent="#accordion_single_collapse"
									href="#DocumentAttachment"> Document Attachment <small
									class="text-blue-2">(UploadFile upto 5MB and only .pdf
										or .doc)</small></a>
							</h4>


							<div class="panel-collapse collapse in" id="DocumentAttachment">
								<div id=checkListDetails>
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
																				validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
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
						<c:if test="${command.free ne 'N'}">
							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
							<div class="form-group margin-top-10" id="">
								<label class="col-sm-2 control-label"><spring:message
										code="water.field.name.amounttopay" /></label>
								<div class="col-sm-4">
									<input type="text" class="form-control"
										value="${command.offlineDTO.amountToShow}" maxlength="12"
										readonly="true"> <a
										class="fancybox fancybox.ajax text-small text-info"
										href="NoDuesCertificateController.html?showChargeDetails"><spring:message
											code="water.field.name.amounttopay" /><i
										class="fa fa-question-circle "></i></a>
								</div>
							</div>
						</c:if>
						
					</div>

					<div class="text-center padding-bottom-20" id="NodivSubmit">

						<button type="button" class="btn btn-success btn-submit"
							onclick="saveNoDuesCertificateForm(this)" id="submit">
							<spring:message code="water.nodues.submit" />
						</button>
						<button type="button" class="btn btn-danger"
							onclick="window.location.href='AdminHome.html'">
							<spring:message code="water.nodues.cancel" />
						</button>
						
					</div>

				</form:form>
			</div>
		</div>
	</div>
	<!-- End of info box -->

</div>
