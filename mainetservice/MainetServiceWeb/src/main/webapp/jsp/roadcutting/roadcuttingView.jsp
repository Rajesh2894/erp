<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/roadcutting/roadCutting.js"></script>
<script>
$(document ).ready(function() {
	debugger;
	$(".panel-heading").hide();
	$('#submitButtonId').prop('disabled', true);
	$('input[type="text"],textarea,select,input[type="radio"]').attr('disabled',true);
	
});

<c:if test="${command.hideScrutinyBtn eq 'N'}">
$('#submitButtonId').prop('disabled', false);
$('#decision').prop('disabled', false);
$('#comments').prop('disabled', false);
$('#CheckAction input[type="radio"]').attr('disabled',false);
$('#empId').prop('disabled', false);
$('#serverEvent').prop('disabled', false);
$('#eventEmp').prop('disabled', false);

</c:if>


</script>
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
													<th><spring:message code="RoadCuttingDto.length" /></th>
													<th><spring:message code="RoadCuttingDto.breadth" /></th>
													<th><spring:message code="RoadCuttingDto.height" /></th>
													<th><spring:message code="RoadCuttingDto.diameter" /></th>
													<th><spring:message code="RoadCuttingDto.quantity" /></th>
													<th><spring:message code="RoadCuttingDto.uploadImage" text="Upload"/></th>
													<th><!-- <a href="javascript:void(0);" data-toggle=""
														data-placement="top"
														class="addRoad btn btn-success btn-sm"
														data-original-title="Add" id="addRoad" title="add"><i
															class="fa fa-plus-circle"></i></a> --></th>
												</tr>
												<c:forEach varStatus="roadstatus" begin="0" items="${command.roadCuttingDto.roadList }">
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
																cssClass="text-right form-control"
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
														<td> <a class="btn btn-blue-2 uploadbtn" data-toggle="+" onclick="openUploadForm(${roadstatus.index},'V')"><i class="fa fa-camera"></i> Upload &amp; View</a></td>
														<td></td>
													</tr>
												</c:forEach>
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
													<th><label class="tbold"><spring:message
																code="tp.serialNo" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="tp.docName" text="Document Name" /></label></th>
												     <th><label class="tbold"><spring:message code="water.download"/></label></th>
												</tr>

												<c:forEach items="${command.documentList}" var="lookUp" varStatus="lk">
													<tr>
														<td><label>${lookUp.clmSrNo}</label></td>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<td><label>${lookUp.clmDescEngl}</label></td>
															</c:when>
															<c:otherwise>
																<td><label>${lookUp.clmDesc}</label></td>
															</c:otherwise>
														</c:choose>
														  <td>
							
                        									<div>
																<apptags:filedownload filename="${lookUp.attFname}"
																	filePath="${lookUp.attPath}"
																	actionUrl="AdminHome.html?Download"></apptags:filedownload>
															</div>

													</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
 </div>

				  <!-- LOI CHARGES -->

					<%-- <div class="table-responsive">
						<table class="table table-bordered table-striped">
							<tr>
								<th scope="col" width="80">Sr. No</th>
								<th scope="col">Charge Name</th>
								<th scope="col">Amount</th>
							</tr>
							<c:forEach var="chargesDesc" items="${command.chargeDesc}"
								varStatus="status">
								<tr>
									<td>${status.count}</td>
									<td><form:input path="" type="text" class="form-control"
											value="${chargesDesc.key}" readonly="true" /></td>
									<td>
									<fmt:formatNumber value = "${chargesDesc.value}" type = "number" var="amount" minFractionDigits="2" groupingUsed="false" />
									<form:input path="" type="text" class="form-control" value="${amount}" readonly="true" /></td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="2"><span class="pull-right"><b>Total
											LOI Amount</b></span></td>
								<td>
								<fmt:formatNumber value = "${command.total}" type = "number" var="totalAmount" minFractionDigits="2" groupingUsed="false" />
								<form:input path="" type="text" value="${totalAmount}" class="form-control" readonly="true" /></td>
							</tr>
						</table>
					</div> --%>
					
					<c:if test="${not empty command.chargeDesc}">
					<div class="table-responsive">
		         <table class="table table-bordered table-striped">
						<tr>
						<th scope="col" width="80">Sr. No</th>
						<th scope="col">Charge Name</th>
						<th scope="col">Amount</th>
						</tr>
						<c:forEach var="chargesDesc" items="${command.chargeDesc}" varStatus="status">
						<tr>
						<td>${status.count}</td>
						<td><form:input path="" type="text" class="form-control" value="${chargesDesc.key}" readonly="true"/></td>
						<td>
         				<fmt:formatNumber value = "${chargesDesc.value}" type = "number" var="amount" maxFractionDigits="2" minFractionDigits="2" groupingUsed="false" />
						<form:input path="" type="text" class="form-control text-right" value="${amount}" readonly="true"/></td>
						</tr>
						</c:forEach>
						<tr>
						<td colspan="2"><span class="pull-right"><b>Total LOI Amount</b></span></td>
						<td> 
						<fmt:formatNumber value = "${command.total}" type = "number" var="totalAmount" maxFractionDigits="2" minFractionDigits="2" groupingUsed="false" />
						<form:input path="" type="text" value="${totalAmount}" class="form-control text-right" readonly="true"/></td>
						</tr>
				</table>
		</div>
		</c:if>
				<c:if test="${command.viewMap ne 'Y'}">
				<div class="col-sm-4">
				<a href="http://173.212.202.91:8086/Row/index"  id="" target="_blank"><b>Create GIS Map</b></a>
				</div>
				</c:if>
				<br><br>
				
				
				<c:if test="${command.viewMap eq 'Y'}">
				<div class="col-sm-4">
				<c:set var="GISID" value="${command.roadCuttingDto.applicationId}"></c:set>	
				
				<a href="http://173.212.202.91:8086/Row/Index/?refId=${GISID}"  id="" target="_blank"><b>View GIS Map</b></a>
				</div>
				</c:if>
				<br><br>
				
		
		
				<c:if test="${command.checkNOCApplicable eq 'Y'}">
					<table class="table table-striped table-bordered" id="deptDetail">
								<thead>
									<tr>
									<th width="4%" align="center"><spring:message code="sr.No"
											text="Sr.No" /></th>
										<th width="10%"><spring:message
												code="road.dept.applicable"></spring:message></th>
										<th width="13%"><spring:message code="road.dept.name"></spring:message><span
											class="mand">*</span></th>
										<th width="13%"><spring:message code="road.service.name"></spring:message><span
											class="mand">*</span></th>
										<th width="8%"><spring:message
												code="road.workflow.status"></spring:message><span
											class="mand">*</span></th>
										<th width="13%"><spring:message
												code="road.workflow.initiation"></spring:message><span
											class="mand">*</span></th>
									</tr>
								</thead>

								<tbody>
								<c:set var="p1" value="1" > </c:set>
								
									<c:forEach items="${command.roadCuttingDtoList}"
											var="roadCuttingDto" varStatus="statusOfList">
											<tr id="appendable">
												<td>${statusOfList.count}</td>
									<td><input type="checkbox" value="" id="checkId${p1}" class=" margin-top-10 margin-left-50"
													 onClick="btnEnabledForWorkflow(${p1})" data-rule-required="true"></td>
												<td>${roadCuttingDto.dpDeptdesc}</td>
												<td>${roadCuttingDto.smShortdesc}</td>
												<td>${roadCuttingDto.status}</td>
												<td class="text-center">
													<button type="button" class="btn btn-blue-2 btn-sm"
														onClick="getInitiateWorkflow(${roadCuttingDto.serviceId},${p1});" disabled="disabled"
														id="initiateBtn${p1}">
													<spring:message text="Initiate" code="road.initiate" />
													</button>
												</td>
											</tr>
											<c:set var="p1" value="${p1+1}" > </c:set> 
										</c:forEach>
								</tbody>
							</table>
							</c:if>
							

					
				 <c:if test="${command.hideScrutinyBtn eq 'Y'}">
				<div id="scrutinyDiv" class="text-center">
				<jsp:include page="/jsp/cfc/sGrid/scrutinyButtonTemplet.jsp"></jsp:include>
				
				
			</div>
			 
			</c:if>
			<br>
			<div id = checker>
			<c:if test="${command.hideScrutinyBtn eq 'N'}">
			<apptags:CheckerAction></apptags:CheckerAction>
			</c:if>
				 </div> 
				 
				 <div class="text-center padding-bottom-10">
				   	 <button type="button" class="btn btn btn-submit"	onclick="saveRoadCuttingForm(this);"  id="submitButtonId" ><spring:message text="Submit" code="bt.save" /></button>
				   	 <button type="button" class="btn btn btn-blue-2" onclick="editForm(this)" id="editButtonId" ><spring:message text="Edit" code="bt.edit" /></button>
				  </div>
				  
				</form:form>
				
	
			 
			
			
			</div>
		</div>
	</div>
</div>