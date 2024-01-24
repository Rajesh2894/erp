<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/roadcutting/roadCutting.js"></script>
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message text="Application For Cable Laying"
						code="roadcutting.home.application" />
				</h2>
				<apptags:helpDoc url="RoadCutting.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<form:form id="RoadCuttingId" action="RoadCutting.html"
					method="POST" class="form-horizontal" name="RoadCuttingId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<!-------------------------------------------------------- Applicant Information----------------------------------- -->
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse1">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="" id=""
										data-parent="#accordion_single_collapse1" href="#Applicant"><spring:message
											text="Applicant Information" /></a>
								</h4>
							</div>
							<div id="Applicant" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="RoadCuttingDto.applicantCompName1"
											path="roadCuttingDto.applicantCompName1" isMandatory="true"
											maxlegnth="200" cssClass="form-control">
										</apptags:input>
										<apptags:textArea path="roadCuttingDto.companyAddress1"
											labelCode="RoadCuttingDto.companyAddress1" isMandatory="true"
											maxlegnth="500" cssClass="form-control" />
									</div>
							
								<div class="form-group">
									<apptags:input labelCode="RoadCuttingDto.personName1"
										path="roadCuttingDto.personName1" isMandatory="true"
										maxlegnth="100" cssClass="form-control">
									</apptags:input>
									<apptags:textArea path="roadCuttingDto.personAddress1"
										labelCode="RoadCuttingDto.personAddress1" isMandatory="true"
										maxlegnth="500" cssClass="form-control" />
								</div>
								<div class="form-group">
									<apptags:input path="roadCuttingDto.faxNumber1"
										labelCode="RoadCuttingDto.faxNumber1" isMandatory="true"
										maxlegnth="100" cssClass="form-control hasNumber" />
									<apptags:input labelCode="RoadCuttingDto.telephoneNo1"
										path="roadCuttingDto.telephoneNo1" isMandatory="true"
										cssClass="form-control hasNumber" maxlegnth="15">
									</apptags:input>
								</div>
								<div class="form-group">
									<apptags:input labelCode="RoadCuttingDto.personMobileNo1"
										path="roadCuttingDto.personMobileNo1" isMandatory="true"
										cssClass="form-control hasNumber" maxlegnth="10">
									</apptags:input>
									<apptags:input labelCode="RoadCuttingDto.personEmailId1"
										path="roadCuttingDto.personEmailId1" isMandatory="false"
										cssClass="hasemailclass" dataRuleEmail="true" maxlegnth="50">
									</apptags:input>
								</div>
									</div>
							</div>
						</div>

						<!-----------------------------------------------------------------------Local office Details -------------------------------- -->
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse1" href="#a1"><spring:message
											text="Local Office Details" /></a>
								</h4>
							</div>
							<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label" for=""> <spring:message
											text="Same As Applicant Details" />
									</label>
									<div class="col-sm-4">
										<form:checkbox path="" id="localDetail"
											onclick="localDetails()" class="margin-top-10 margin-left-10"
											disabled="false" value="ture"></form:checkbox>

									</div>
								</div>
								
									<div class="form-group">
										<apptags:input labelCode="RoadCuttingDto.companyName2"
											path="roadCuttingDto.companyName2" isMandatory="true"
											maxlegnth="100" cssClass="form-control">
										</apptags:input>
										<apptags:textArea path="roadCuttingDto.companyAddress2"
											labelCode="RoadCuttingDto.companyAddress2" isMandatory="true"
											maxlegnth="500" cssClass="form-control" />
									</div>
								
								<div class="form-group">
									<apptags:input labelCode="RoadCuttingDto.personName2"
										path="roadCuttingDto.personName2" isMandatory="true"
										maxlegnth="100" cssClass="form-control">
									</apptags:input>
									<apptags:textArea path="roadCuttingDto.personAddress2"
										labelCode="RoadCuttingDto.personAddress2" isMandatory="true"
										maxlegnth="500" cssClass="form-control" />

								</div>
								<div class="form-group">
									<apptags:input path="roadCuttingDto.faxNumber2"
										labelCode="RoadCuttingDto.faxNumber2" isMandatory="true"
										cssClass="form-control hasNumber" maxlegnth="100" />
										<apptags:input labelCode="RoadCuttingDto.telephoneNo2"
										path="roadCuttingDto.telephoneNo2" isMandatory="true"
										cssClass="form-control hasNumber" maxlegnth="12">
									</apptags:input>
								</div>
								<div class="form-group">
								<apptags:input path="roadCuttingDto.personMobileNo2"
										labelCode="RoadCuttingDto.personMobileNo2" isMandatory="true"
										cssClass="form-control hasNumber" maxlegnth="10" />
									<apptags:input labelCode="RoadCuttingDto.personEmailId2"
										path="roadCuttingDto.personEmailId2" isMandatory="false"
										cssClass="hasemailclass" dataRuleEmail="true" maxlegnth="50">
									</apptags:input>
								</div>
							</div>
							</div>
						</div>
						<!------------------------------------------------------------------- contractor details---------------------------------- -->
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse1" href="#a2"><spring:message
											text="Contractor Details" /></a>
								</h4>
							</div>
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="RoadCuttingDto.contractorName"
											path="roadCuttingDto.contractorName" isMandatory="true"
											maxlegnth="100" cssClass="form-control">
										</apptags:input>
										<apptags:textArea path="roadCuttingDto.contractorAddress"
											labelCode="RoadCuttingDto.contractorAddress"
											isMandatory="true" maxlegnth="500"
											cssClass="form-control" />
									</div>
								
								<div class="form-group">
									<apptags:input path="roadCuttingDto.contractorContactPerName"
										labelCode="RoadCuttingDto.contractorContactPerName"
										isMandatory="true" maxlegnth="100"
										cssClass="form-control" />
									<apptags:input
										labelCode="RoadCuttingDto.contracterContactPerMobileNo"
										path="roadCuttingDto.contracterContactPerMobileNo"
										isMandatory="true" cssClass="form-control hasNumber"
										maxlegnth="10">
									</apptags:input>
								</div>
								<div class="form-group">
									<apptags:input path="roadCuttingDto.contractorEmailId"
										labelCode="RoadCuttingDto.contractorEmailId"
										isMandatory="false" cssClass="hasemailclass"
										dataRuleEmail="true" maxlegnth="50"/>
								</div>
							</div>
							</div>
						</div>
						<!---------------------------------------------------------------Road/Route Details  -->
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse1" href="#a3"><spring:message code="road.info"
											text="Technical Information" /></a>
								</h4>
							</div>
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<apptags:lookupFieldSet baseLookupCode="ZWB" hasId="true"
											showOnlyLabel="false" pathPrefix="roadCuttingDto.codWard"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control required-control margin-bottom-10"
											showAll="false" columnWidth="20%" />
									</div>
								
								
									<c:set var="d" value="0" scope="page" />
									<div class="table-responsive">
										<table class="table text-left table-striped table-bordered"
											id="roadRoute">
											<tbody>
												<tr>
													<th><spring:message
															code="RoadCuttingDto.typeOfTechnology" />*</th>
													<th><spring:message
															code="RoadCuttingDto.roadRouteDesc" />*</th>
													<th><spring:message	code="RoadCuttingDto.roadEnd" />*</th>	
													<th><spring:message code="RoadCuttingDto.roadType" />*</th>
													<th><spring:message code="RoadCuttingDto.numbers" />*</th>
													<th><spring:message code="RoadCuttingDto.length" />*</th>
													<th><spring:message code="RoadCuttingDto.breadth" /></th>
													<th><spring:message code="RoadCuttingDto.height" /></th>
													<th><spring:message code="RoadCuttingDto.diameter" /></th>
													<th><spring:message code="RoadCuttingDto.quantity" /></th>
													<th><spring:message code="RoadCuttingDto.uploadImage" text="Upload"/></th>
													<th><a href="javascript:void(0);" data-toggle=""
														data-placement="top"
														class="addRoad btn btn-success btn-sm"
														data-original-title="Add" id="addRoad" title="add"><i
															class="fa fa-plus-circle"></i></a></th>
												</tr>
												<%--  --%>
													<c:choose>
														<c:when test="${ empty command.roadCuttingDto.roadList }">
														
															<c:forEach varStatus="roadstatus" begin="0" end="${fn:length(roadCuttingDto.roadList)}">
													<tr class="appendableClass">
														<td><form:select
																path="roadCuttingDto.roadList[${roadstatus.index}].typeOfTechnology"
																cssClass="form-control" data-rule-required="true">
																<form:option value="">Select</form:option>
																<c:forEach items="${command.listTEC}" var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].roadRouteDesc"
																class="form-control" maxlength="500" /></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].rcdEndpoint"
																class="form-control" maxlength="500"  /></td>
														<td><form:select
																path="roadCuttingDto.roadList[${roadstatus.index}].roadType"
																cssClass="form-control" data-rule-required="true">
																<form:option value="">Select</form:option>
																<c:forEach items="${command.listROT}" var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].numbers"
																cssClass="text-right form-control hasNumber"
																maxlength="3" onchange="calculateQuantity(this)"/></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].length"
																cssClass="decimal33 text-right form-control"
																maxlength="7" onchange="calculateQuantity(this)"/></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].breadth"
																cssClass="decimal13 text-right form-control"
																maxlength="5" onchange="calculateQuantity(this)"/></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].height"
																cssClass="decimal13 text-right form-control"
																maxlength="5" onchange="calculateQuantity(this)"/></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].diameter"
																cssClass="decimal0_3 text-right form-control"
																maxlength="5" onchange="calculateQuantity(this)"/></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].quantity"
																cssClass="decimal text-right form-control"
																maxlength="15" readonly="true"/></td>
														<td> <a class="btn btn-blue-2 uploadbtn" data-toggle="+" onclick="openUploadForm(${roadstatus.index},'A')"><i class="fa fa-camera"></i> Upload &amp; View</a></td>

														<td><a href="javascript:void(0);" data-toggle=""
															data-placement="top"
															class="remRoad btn btn-danger btn-sm"
															data-original-title="Delete" title="Delete"><i
																class="fa fa-trash"></i></a></td>
													</tr>
												</c:forEach>
														</c:when>
														<c:otherwise>
															<c:forEach varStatus="roadstatus" items="${command.roadCuttingDto.roadList }">
													<tr class="appendableClass">
														<td><form:select
																path="roadCuttingDto.roadList[${roadstatus.index}].typeOfTechnology"
																cssClass="form-control" data-rule-required="true">
																<form:option value="">Select</form:option>
																<c:forEach items="${command.listTEC}" var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].roadRouteDesc"
																class="form-control" maxlength="500" /></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].rcdEndpoint"
																class="form-control" maxlength="500"  /></td>
														<td><form:select
																path="roadCuttingDto.roadList[${roadstatus.index}].roadType"
																cssClass="form-control" data-rule-required="true">
																<form:option value="">Select</form:option>
																<c:forEach items="${command.listROT}" var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].numbers"
																cssClass="text-right form-control hasNumber"
																maxlength="3" onchange="calculateQuantity(this)"/></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].length"
																cssClass="decimal33 text-right form-control"
																maxlength="7" onchange="calculateQuantity(this)"/></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].breadth"
																cssClass="decimal13 text-right form-control"
																maxlength="5" onchange="calculateQuantity(this)"/></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].height"
																cssClass="decimal13 text-right form-control"
																maxlength="5" onchange="calculateQuantity(this)"/></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].diameter"
																cssClass="decimal0_3 text-right form-control"
																maxlength="5" onchange="calculateQuantity(this)"/></td>
														<td><form:input
																path="roadCuttingDto.roadList[${roadstatus.index}].quantity"
																cssClass="decimal text-right form-control"
																maxlength="15" readonly="true"/></td>
														<td> <a class="btn btn-blue-2 uploadbtn" data-toggle="+" onclick="openUploadForm(${roadstatus.index},'A')"><i class="fa fa-camera"></i> Upload &amp; View</a></td>

														<td><a href="javascript:void(0);" data-toggle=""
															data-placement="top"
															class="remRoad btn btn-danger btn-sm"
															data-original-title="Delete" title="Delete"><i
																class="fa fa-trash"></i></a></td>
													</tr>
												</c:forEach>
														
														</c:otherwise>
													</c:choose>
													
											</tbody>
										</table>
									</div>

								</div>

							</div>
						</div>
						<!---------------------------------------------------------------total budget and estimate amount---------- -->
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse1" href="#a4"><spring:message
											text="Total budget and estimate amount" /></a>
								</h4>
							</div>
							<div id="a4" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="RoadCuttingDto.totalCostOfproject"
											path="roadCuttingDto.totalCostOfproject" isMandatory="true"
											maxlegnth="30" cssClass="text-right has2Decimal">
										</apptags:input>
										<apptags:input path="roadCuttingDto.estimteForRoadDamgCharge"
											labelCode="RoadCuttingDto.estimteForRoadDamgCharge"
											isMandatory="true" maxlegnth="30" cssClass="text-right has2Decimal"/>
									</div>
								</div>
							</div>
						</div>
						<!---------------------------------------------------------------document upload start------------------------ -->
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse1" href="#a5"><spring:message
											text="Document Upload Details" /></a>
								</h4>
							</div>
							<div id="a5" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><spring:message text="Sr.No" /></th>
													<th><spring:message text="Document Group" /></th>
													<th><spring:message text="Document Status" /></th>
													<th><spring:message text="Upload document" /></th>
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
																<apptags:formField fieldType="7" labelCode=""
																	hasId="true" fieldPath="checkList[${lk.index}]"
																	isMandatory="false" showFileNameHTMLId="true"
																	fileSize="BND_COMMOM_MAX_SIZE"
																	checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
																	maxFileCount="CHECK_LIST_MAX_COUNT"
																	validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
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
						</div>
						<!---------------------------------------------------------------document upload end------------------------ -->

						<%-- <c:if test="${command.payable eq true}"> --%>
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="water.field.name.amounttopay" /></label>
							<div class="col-sm-4">
								<form:input class="form-control text-right" path=""
									value="${command.amountToPaid}" maxlength="12" readonly="true" ></form:input>
								<a class="fancybox fancybox.ajax text-small text-info"
									href="RoadCutting.html?showChargeDetails"><spring:message
										code="water.lable.name.chargedetail" /> <i
									class="fa fa-question-circle "></i></a>
							</div>
						</div>
						<apptags:payment></apptags:payment>
						<%-- </c:if> --%>


						<div class="text-center">

							<apptags:resetButton></apptags:resetButton>
							<button type="button" class="button-input btn btn-success"
								name="button" value="procced"
								onclick="saveRoadCuttingInfo(this)" id="procced">
								<spring:message text="Procced" />
							</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>