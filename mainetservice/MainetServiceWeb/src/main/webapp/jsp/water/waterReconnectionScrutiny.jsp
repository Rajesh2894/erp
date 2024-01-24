
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/water/reconnection.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {

				$('#searchConnection').attr('disabled', true);
				$("#submitBtn").hide();
				$('input[type=text]').attr('disabled', true);
				$('textarea').attr('readonly', 'readonly');
				$('select').attr("disabled", true);

				$("#viewDisconnectionDetail").click(
						function() {
							_openPopUpForm('WaterReconnectionForm.html',
									'getDisconnectionDetail');
						});

				if ($("#ULBPlumberId") == "Y") {
					$('#ULBRegister').prop('checked', true);
					$("#NotRegister").prop('checked', false);
					$("#NotRegister").prop('disabled', true);
					$('#ULBRegister').prop('disabled', true);
					$(".toshow").hide();
				} else {
					$("#NotRegister").prop('checked', true);
					$('#ULBRegister').prop('checked', false);
					$("#NotRegister").prop('disabled', true);
					$('#ULBRegister').prop('disabled', true);
					$(".toshow").show();
				}

				/* $("#NotRegister").click(function() {
					$(".toshow").show();

				});

				$("#ULBRegister").click(function() {
					$(".toshow").hide();

				});
				 */
				if (('${command.scrutinyApplicable}') == 'true') {
					$("#scrutinyDiv").show();
				} else {
					$("#scrutinyDiv").hide();
				}
			});
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<!-- ============================================================== -->
<!-- Start Content here -->
<!-- ============================================================== -->
<div class="content">
	<!-- Start info box -->
	<div class="row">
		<div class="col-md-12">
			<div class="widget">
				<div class="widget-header">

					<h2>
						<spring:message code="water.reconnection.department" />
					</h2>
					<!-- <div class="additional-btn">
						<a href="#" data-toggle="tooltip" data-original-title="Help"><i
							class="fa fa-question-circle fa-lg"></i></a>
					</div> -->
				</div>

				<div class="widget-content padding">
					<div class="mand-label clearfix">
						<span><spring:message code="water.fieldwith" /> <i
							class="text-red-1">*</i> <spring:message code="water.ismandtry" />
						</span>
					</div>

					<div class="error-div alert alert-danger alert-dismissible"
						id="errorDivId" style="display: none;">
						<button type="button" class="close" onclick="closeOutErrBox()"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<span id="errorId"></span>
					</div>
					<form:form action="WaterReconnectionForm.html"
						class="form-horizontal form" name="frmWaterReconnectionForm"
						id="frmWaterReconnectionForm">

						<jsp:include page="/jsp/tiles/validationerror.jsp" />

						<%-- <jsp:include page="/jsp/mainet/applicantDetails.jsp"></jsp:include> --%>

						<h4 class="margin-top-0">
							<a data-toggle="collapse" href="#Applicant" class=""
								aria-expanded="true"><spring:message
									code="water.form.appdetails" /></a>
						</h4>
						<div id="applicantDetail">
							<apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail>
							<%-- <div class="form-group">
    							<label class="col-sm-2 control-label required-control"><spring:message code="water.title"/></label>
   		 							<c:set var="baseLookupCode" value="TTL" />
									<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="applicantDetailDto.applicantTitle" cssClass="form-control"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="pt.select" isMandatory="true"/>
    							<label class="col-sm-2 control-label required-control" ><spring:message code="water.owner.details.fname"/></label>
    								<div class="col-sm-4" >
      										<form:input name="" type="text" class="form-control"  path="applicantDetailDto.applicantFirstName" id="firstName"></form:input>
    								</div>
 						</div>
						<div class="form-group">
						    <label class="col-sm-2 control-label"><spring:message code="water.owner.details.mname"/></label>
						    <div class="col-sm-4">
						      <form:input name="" type="text" class="form-control" path="applicantDetailDto.applicantMiddleName" id="middleName"></form:input>
						    </div>
						    <label class="col-sm-2 control-label required-control"><spring:message code="water.owner.details.lname"/></label>
						    <div class="col-sm-4">
						      <form:input name="" type="text" class="form-control" path="applicantDetailDto.applicantLastName" id="lastName"></form:input>
						    </div>
						 </div>
						 
						  <div class="form-group">
						  
						    <label class="col-sm-2 control-label required-control"><spring:message code="water.reconnection.mobileNo"/></label>
						    <div class="col-sm-4">
						      <form:input name="" type="text" class="form-control hasNumber" maxlength="10" path="applicantDetailDto.mobileNo" id="mobileNo"></form:input>
						    </div>
						    <label class="col-sm-2 control-label"><spring:message code="water.reconnection.emailId"/></label>
						    <div class="col-sm-4">
						      <form:input name="" type="text" class="form-control" path="applicantDetailDto.emailId" id="emailId"></form:input>
						    </div>
						  </div>
						  <div class="form-group">
						    <label class="col-sm-2 control-label"><spring:message code="water.flatOrbuildingNo"/></label>
						    <div class="col-sm-4">
						      <form:input name="" type="text" class="form-control" path="applicantDetailDto.floorNo" id="flatNo"></form:input>
						    </div>
						    <label class="col-sm-2 control-label"><spring:message code="water.buildingName"/></label>
						    <div class="col-sm-4">
						      <form:input name="" type="text" class="form-control" path="applicantDetailDto.buildingName" id="buildingName"></form:input>
						    </div>
						  </div>
						  <div class="form-group">
						    <label class="col-sm-2 control-label"><spring:message code="water.roadName"/></label>
						    <div class="col-sm-4">
						      <form:input name="" type="text" class="form-control" path="applicantDetailDto.roadName" id="roadName"></form:input>
						    </div>
						    <label class="col-sm-2 control-label required-control"><spring:message code="water.areaName"/></label>
						    <div class="col-sm-4">
						      <form:input name="" type="text" class="form-control" path="applicantDetailDto.areaName" id="areaName"></form:input>
						    </div>
						  </div>

						  <div class="form-group">
						    <label class="col-sm-2 control-label required-control"><spring:message code="water.pincode"/></label>
						    <div class="col-sm-4">
						      <form:input name="" type="text" class="form-control hasNumber" path="applicantDetailDto.pinCode" id="pinCode" maxlength="6"></form:input>
						    </div>
						    <label class="col-sm-2 control-label required-control"><spring:message code="water.adharNo"/></label>
						    <div class="col-sm-4">
						      <form:input name="" type="text" class="form-control hasNumber " path="applicantDetailDto.aadharNo" id="adharNo" maxlength="12"></form:input>
						    </div>
						  </div>
  							<div class="form-group"> 
   
     							<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true" showOnlyLabel="false"
									pathPrefix="applicantDetailDto.dwzid" isMandatory="false" hasLookupAlphaNumericSort="true" hasSubLookupAlphaNumericSort="true" cssClass="form-control required-control" showAll="true"/>
  
 							</div> --%>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="water.plumber.details"></spring:message></label>
							<div class="radio col-sm-4">
								<label> <form:radiobutton
										path="reconnRequestDTO.plumber" value="Y" id="ULBRegister"
										checked="true" /> <spring:message code="water.plumber.reg"></spring:message>
								</label> <label> <form:radiobutton
										path="reconnRequestDTO.plumber" value="N" id="NotRegister" />
									<spring:message code="water.plumber.notreg"></spring:message>
								</label>
							</div>
							<label class="col-sm-2 control-label required-control"
								for="plumberName"><spring:message
									code="water.plumber.licno" /></label>
							<div id="ulbPlumber">
								<div class="col-sm-4">
									<form:select path="plumberId"
										class="form-control" id="plumber">
										<form:option value="">
											<spring:message code="water.dataentry.select" />
										</form:option>
										<c:forEach items="${command.plumberList}" var="lookUp">
											<form:option value="${lookUp.plumId}">${lookUp.plumFname} ${lookUp.plumMname} ${lookUp.plumLname}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div id="licensePlumber">
								<div class="col-sm-4">
									<form:select path="plumberId"
										class="form-control" id="licPlumber">
										<form:option value="">
											<spring:message code="water.dataentry.select" />
										</form:option>
										<c:forEach items="${command.plumberList}" var="lookUp">
											<form:option value="${lookUp.plumId}">${lookUp.plumFname} ${lookUp.plumMname} ${lookUp.plumLname}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
						</div>

						<h4>
							<spring:message code="water.reconnection.details" />
						</h4>
						<div class="form-group">
							<c:set var="baseLookupCode" value="CAN" />
							<label class="col-sm-2"><spring:message
									code="water.reconnection.method" /></label>
							<div class="col-sm-4">
								<form:select path="reconnectionMethod" class="form-control"
									id="reconnectionMethod">
									<form:option value="0">
										<spring:message code="water.select" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select>
							</div>
							<div class="col-sm-2">
								<button type="button" value=View class="btn btn-success"
									id="viewDisconnectionDetail">
									<spring:message
										code="water.reconnection.viewDisconnectionDetails" />
								</button>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="water.remark" /></label>
							<div class="col-sm-4">
								<form:textarea type="textarea" class="form-control disablefield"
									path="reconnectionRemark" id="remarks"></form:textarea>
							</div>
						</div>

						<br>
						<!--show uploaded document by customer  -->
						<c:if test="${not empty command.documentList}">
							<fieldset class="fieldRound">
								<div class="overflow">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="tp.serialNo" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="tp.docName" text="Document Name" /></label></th>
													<th><label class="tbold"><spring:message
																code="water.download" /></label></th>
												</tr>

												<c:forEach items="${command.documentList}" var="lookUp"
													varStatus="lk">
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
														<td><c:set var="links"
																value="${fn:substringBefore(lookUp.attPath, lookUp.attFname)}" />
															<!-- <i
															class="fa fa-download"></i> --> <apptags:filedownload
																filename="${lookUp.attFname}" filePath="${links}"
																actionUrl="WaterReconnectionForm.html?Download"></apptags:filedownload>

														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
						</c:if>

						<div class="text-center padding-top-10">
							<button type="button" id="editId" class="btn btn-success"
								onclick="editForm(this)">
								<spring:message code="water.btn.edit" />
							</button>
							<button type="button" class="btn btn-success" id="submitBtn"
								onclick="updateWaterReconnectionDetailByDept(this)">
								<spring:message code="water.btn.submit" />
							</button>
						</div>
					</form:form>
					<div id="scrutinyDiv">
						<jsp:include page="/jsp/cfc/sGrid/scrutinyButtonTemplet.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
