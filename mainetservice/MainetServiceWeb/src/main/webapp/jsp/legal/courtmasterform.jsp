<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script src="js/mainet/validation.js"></script>
<script src="js/legal/courtMaster.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="lgl.courtmasterform"
						text="courtmasterform" /></strong>
			</h2>

		</div>

		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="lgl.fieldwith" /><i
					class="text-red-1">* </i> <spring:message code="lgl.ismandatory" />
				</span>
			</div>
			<!-- End mand-label -->

			<form:form action="CourtMaster.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="courtMasterForm" id="id_courtmasterform">
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="envFlag" id="envFlag"/>

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>
				
				<form:hidden path="courtMasterDto.id"
						cssClass="hasNumber form-control" id="id" />

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">

							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="lgl.courtdet" text="Court Information" />
								</a>
							</h4>
						</div>

						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:choose>
									<c:when
										test="${command.saveMode eq 'E' || command.saveMode eq 'V'}">
										<div class="form-group">
											<apptags:input labelCode="lgl.courtnm"
												path="courtMasterDto.crtName" isMandatory="true"
												cssClass="hasNameClass"
												isDisabled="${command.saveMode eq 'V' ? true : false }"
												maxlegnth="150" />

                                            <!-- #120802 -->
											<apptags:input labelCode="lgl.courtnmreg"
												path="courtMasterDto.crtNameReg" cssClass="alphaChar"
												isDisabled="${command.saveMode eq 'V' ? true : false }"
												maxlegnth="200" />

										</div>

										<div class="form-group">
											<apptags:input labelCode="lgl.courtstarttime"
												cssClass="form-control datetimepicker3"
												path="courtMasterDto.crtStartTime" isMandatory="true"
												isReadonly="false"
												isDisabled="${command.saveMode eq 'V' ? true : false }" />
											<apptags:input labelCode="lgl.courtendtime"
												cssClass="form-control datetimepicker3"
												path="courtMasterDto.crtEndTime" isMandatory="true"
												isReadonly="false"
												isDisabled="${command.saveMode eq 'V' ? true : false }" />
										</div>

										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="courtType"><spring:message code="lgl.courttype" /></label>
											<apptags:lookupField items="${command.getLevelData('CTP')}"
												path="courtMasterDto.crtType" cssClass="form-control"
												selectOptionLabelCode="Select" hasId="true"
												isMandatory="true"
												disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false }" />
											<%-- <apptags:input labelCode="lgl.courtphoneno"
												path="courtMasterDto.crtPhoneNo" cssClass="hasMobileNo"
												isMandatory="false"
												isDisabled="${command.saveMode eq 'V' ? true : false }" />
											<apptags:input labelCode="lgl.courtemail"
												path="courtMasterDto.crtEmailId" dataRuleEmail="true"
												isMandatory="false"
												isDisabled="${command.saveMode eq 'V' ? true : false }" maxlegnth="50"/> --%>
											<apptags:input labelCode="lgl.website"
												path="courtMasterDto.crtWebsite" isMandatory="false"
												isDisabled="${command.saveMode eq 'V' ? true : false }"
												maxlegnth="150"></apptags:input>
										</div>

										<div class="form-group">
											<apptags:textArea labelCode="lgl.courtadd"
												path="courtMasterDto.crtAddress" cssClass="maxLength200"
												isMandatory="true" 
												isDisabled="${command.saveMode eq 'V' ? true : false }"
												maxlegnth="250" />

											<label class="col-sm-2 control-label"><spring:message
													code="lgl.crtStatus" text="Court Status" /><span
												class="mand">*</span> </label>
											<div class="col-sm-4">

												<label class="radio-inline" for="crtStatusYes"> <form:radiobutton
														name="crtStatus" path="courtMasterDto.crtStatus"
														checked="checked" value="Y" id="crtStatusYes"
														disabled="${command.saveMode eq 'V' ? true : false }"></form:radiobutton>
													<spring:message code="lgl.yes" text="Yes" />
												</label> <label class="radio-inline" for="crtStatusNo"> <form:radiobutton
														name="crtStatus" path="courtMasterDto.crtStatus" value="N"
														id="crtStatusNo"
														disabled="${command.saveMode eq 'V' ? true : false }"></form:radiobutton>
													<spring:message code="lgl.no" text="No" />
												</label>
											</div>

										</div>





										<div class="form-group">

											<label class="control-label col-sm-2 required-control" for=""><spring:message
													code="courtMasterDto.crtState" text="State" /></label>
											<div class="col-sm-4">
												<c:set var="baseLookupCodeSTT" value="STT" /> 
												<form:select path="courtMasterDto.crtState"
													class="form-control" id="prefixId1"
													disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
												<form:option value="">Select</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCodeSTT)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpCode}">${lookUp.lookUpDesc} </form:option>
												</c:forEach>
												</form:select>
										</div>

											<!--   120803  maxlength changed for TSCL -->
											<c:if test="${command.envFlag ne 'Y'}">
												<apptags:input labelCode="courtMasterDto.crtCity"
													path="courtMasterDto.crtCity" isMandatory="true"
													cssClass="form-control alphaChar" maxlegnth="250"
													isDisabled="${command.saveMode eq 'V' ? true : false }" />
											</c:if>

											<c:if test="${command.envFlag eq 'Y'}">
												<apptags:input labelCode="courtMasterDto.crtCity"
													path="courtMasterDto.crtCity" isMandatory="true"
													cssClass="form-control alphaChar" maxlegnth="10"
													isDisabled="${command.saveMode eq 'V' ? true : false }" />
											</c:if>

										</div>



									</c:when>
									<c:otherwise>
										<div class="form-group">
											<apptags:input labelCode="lgl.courtnm"
												path="courtMasterDto.crtName" isMandatory="true"
												cssClass="hasNameClass" maxlegnth="150" />
												
											<!-- #120802 -->
											<apptags:input labelCode="lgl.courtnmreg"
												path="courtMasterDto.crtNameReg" cssClass="alphaChar"
												maxlegnth="200" />
										</div>


										<%-- <td align="center"><form:input
																	path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].startime"
																	class="form-control datetimepicker3 mandColorClass"
																	maxlength="10" id="startime${d}"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>


															<td align="center"><form:input
																	path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].endtime"
																	class="form-control datetimepicker3 mandColorClass"
																	maxlength="10" id="endtime${d}"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td> --%>

										<div class="form-group">
											<apptags:input labelCode="lgl.courtstarttime"
												cssClass="form-control datetimepicker3"
												path="courtMasterDto.crtStartTime" isMandatory="true"
												isReadonly="false" />
											<apptags:input labelCode="lgl.courtendtime"
												cssClass="form-control datetimepicker3"
												path="courtMasterDto.crtEndTime" isMandatory="true"
												isReadonly="false" />
										</div>

										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="courtType"><spring:message code="lgl.courttype" /></label>

											<apptags:lookupField items="${command.getLevelData('CTP')}"
												path="courtMasterDto.crtType" cssClass="form-control"
												selectOptionLabelCode="Select" hasId="true"
												isMandatory="true" />
											<%-- <apptags:input labelCode="lgl.courtphoneno"
												path="courtMasterDto.crtPhoneNo" cssClass="hasMobileNo"
												isMandatory="false" />
											<apptags:input labelCode="lgl.courtemail"
												path="courtMasterDto.crtEmailId" dataRuleEmail="true"
												isMandatory="false" maxlegnth="50"/> --%>
											<apptags:input labelCode="lgl.website"
												path="courtMasterDto.crtWebsite" isMandatory="false"
												maxlegnth="150"></apptags:input>

										</div>

										<div class="form-group">
											<apptags:textArea labelCode="lgl.courtadd"
												path="courtMasterDto.crtAddress" isMandatory="true"
												maxlegnth="250" />

										</div>

										<div class="form-group">

											<label class="control-label col-sm-2 required-control" for=""><spring:message
													code="courtMasterDto.crtState" text="State" /></label>
									
										<div class="col-sm-4">
											<c:set var="baseLookupCodeSTT" value="STT" /> 
											<form:select path="courtMasterDto.crtState"
												class="form-control" id="crtState"
												disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
											<form:option value="">Select</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCodeSTT)}"
												var="lookUp">
											<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc} </form:option>
											</c:forEach>
											</form:select>
										</div>

											<!--   120803  maxlength changed for TSCL -->
											<c:if test="${command.envFlag ne 'Y'}">
												<apptags:input labelCode="courtMasterDto.crtCity"
													path="courtMasterDto.crtCity" isMandatory="true"
													cssClass="form-control alphaChar" maxlegnth="250" />
											</c:if>

											<c:if test="${command.envFlag eq 'Y'}">
												<apptags:input labelCode="courtMasterDto.crtCity"
													path="courtMasterDto.crtCity" isMandatory="true"
													cssClass="form-control alphaChar" maxlegnth="10" />
											</c:if>

										</div>


									</c:otherwise>
								</c:choose>
							</div>

						</div>


						<!-- Start button -->

						<div class="text-center clear padding-10">
							<c:if
								test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
								<button type="button" class="button-input btn btn-success"
									onclick="confirmToProceed(this)" name="button-submit" style=""
									id="button-submit">
									<spring:message code="lgl.submit" text="Submit" />
								</button>
							</c:if>
							<c:if test="${command.saveMode eq 'C'}">
								<button type="Reset" class="btn btn-warning"
									onclick="resetForm();">
									<spring:message code="lgl.reset" text="Reset"></spring:message>
								</button>
							</c:if>
							<c:if test="${command.saveMode eq 'E'}">
								<button type="Reset" class="btn btn-warning"
									onclick="resetCourtMaster();" id="resetId">
									<spring:message code="lgl.reset" text="Reset"></spring:message>
								</button>
							</c:if>
							<apptags:backButton url="CourtMaster.html"></apptags:backButton>

						</div>
						<!-- End button -->

					</div>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
	</div>
	<!-- End Widget Content here -->
</div>
<!-- End of Content -->
