<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/security_management/contractualStaffMaster.js"></script>


<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="ContractualStaffMaster.form.name"
						text="Contractual Staff Master" /></strong>
			</h2>
		</div>

		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message code="leadlift.master.ismand" />
				</span>
			</div>
			<!-- End mand-label -->

			<form:form action="ContractualStaffMaster.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmContractualStaffMasterForm" id="frmContractualStaffMasterForm">
				<form:hidden path="saveMode" id="saveMode" />

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">

							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="ContractualStaffMaster.form.name" text="Contractual Staff Master" />
								</a>
							</h4>
						</div>

						 <div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:choose>
									<c:when test="${command.saveMode eq 'E' || command.saveMode eq 'V'}">
										<%-- <div class="form-group">
											<apptags:input labelCode="lgl.courtnm"
												path="courtMasterDto.crtName" isMandatory="true"
												cssClass="hasNameClass"
												isDisabled="${command.saveMode eq 'V' ? true : false }" maxlegnth="100"/>


											<label class="col-sm-2 control-label required-control"
												for="courtType"><spring:message code="lgl.courttype" /></label>
											<apptags:lookupField items="${command.getLevelData('CTP')}"
												path="courtMasterDto.crtType" cssClass="form-control"
												selectOptionLabelCode="Select" hasId="true"
												isMandatory="true"
												disabled="${command.saveMode eq 'V' ? true : false }"/>
										</div>

										<div class="form-group">
											<apptags:input labelCode="lgl.courtstarttime"
												cssClass="form-control datetimepicker3" path="courtMasterDto.crtStartTime"
												isMandatory="true"
												isDisabled="${command.saveMode eq 'V' ? true : false }" />
											<apptags:input labelCode="lgl.courtendtime"
												cssClass="form-control datetimepicker3" path="courtMasterDto.crtEndTime"
												isMandatory="true"
												isDisabled="${command.saveMode eq 'V' ? true : false }" />
										</div>

										<div class="form-group">
											<apptags:input labelCode="lgl.courtphoneno"
												path="courtMasterDto.crtPhoneNo" cssClass="hasMobileNo"
												isMandatory="true"
												isDisabled="${command.saveMode eq 'V' ? true : false }" />
											<apptags:input labelCode="lgl.courtemail"
												path="courtMasterDto.crtEmailId" dataRuleEmail="true"
												isMandatory="false"
												isDisabled="${command.saveMode eq 'V' ? true : false }" maxlegnth="50"/>
										</div>

										<div class="form-group">
											<apptags:textArea labelCode="lgl.courtadd"
												path="courtMasterDto.crtAddress" cssClass="maxLength200"
												isMandatory="true"
												isDisabled="${command.saveMode eq 'V' ? true : false }" maxlegnth="200"/>
											<apptags:input labelCode="lgl.courtnmreg"
												path="courtMasterDto.crtNameReg" cssClass="hasNameClass"
												isDisabled="${command.saveMode eq 'V' ? true : false }" maxlegnth="50" />
										</div>

										<label class="col-sm-2 control-label"><spring:message
												code="lgl.crtStatus" text="Court Status" /><span
											class="mand">*</span> </label>
										<div class="col-sm-4">

											<label class="radio-inline" for="crtStatusYes"> <form:radiobutton
													name="crtStatus" path="courtMasterDto.crtStatus"
													checked="checked" value="Y" id="crtStatusYes"
													disabled="${command.saveMode eq 'V' ? true : false }" ></form:radiobutton>
												<spring:message code="lgl.yes" text="Yes" />
											</label> <label class="radio-inline" for="crtStatusNo"> <form:radiobutton
													name="crtStatus" path="courtMasterDto.crtStatus"
													value="N" id="crtStatusNo" 
													disabled="${command.saveMode eq 'V' ? true : false }" ></form:radiobutton>
												<spring:message code="lgl.no" text="No" />
											</label>
										</div> --%>
									</c:when>
									<c:otherwise>
									
										<div class="form-group">
											<apptags:input labelCode="ContractualStaffMasterDTO.name"
												path="dto.name" isMandatory="true"
												cssClass="hasNameClass" maxlegnth="20"/> 
											<apptags:textArea labelCode="ContractualStaffMasterDTO.address"
												path="dto.address" isMandatory="true" maxlegnth="200" cssClass="alphaNumeric"/> 

										</div>

										<div class="form-group">
											<apptags:input labelCode="ContractualStaffMasterDTO.mobNo"
												path="dto.mobNo" cssClass="hasMobileNo"
												isMandatory="true" maxlegnth="10"/>
											<apptags:input labelCode="ContractualStaffMasterDTO.appoinmentDate"
												cssClass="form-control lessthancurrdate" path="dto.appoinmentDate"
												isMandatory="true"/>
										</div>

										<div class="form-group">
											<apptags:input labelCode="ContractualStaffMasterDTO.idNumber"
												path="dto.idNumber" cssClass="alphaNumeric" maxlegnth="8"/>
											<apptags:input labelCode="ContractualStaffMasterDTO.desgId"
												path="dto.desgId" cssClass="hasNameClass" maxlegnth="50"/> 
										</div>
										
										<div class="form-group">
											<apptags:input labelCode="ContractualStaffMasterDTO.scheduleFrom"
												cssClass="form-control fromDateClass" path="dto.scheduleFrom"
												isMandatory="true"/>
											<apptags:input labelCode="ContractualStaffMasterDTO.scheduleTo"
												cssClass="form-control toDateClass" path="dto.scheduleTo"
												isMandatory="true"/>
										</div>
									</c:otherwise>
								</c:choose>
							</div>

						</div> 


						<!-- Start button -->

						<div class="text-center clear padding-10">
							<c:if
								test="${command.saveMode eq 'E' || command.saveMode eq 'A'}">
								<button type="submit" class="button-input btn btn-success"
									onclick="confirmToProceed(this)" name="button-submit" style=""
									id="button-submit">
									<spring:message code="bt.save" text="Submit" />
								</button>
							</c:if>
							<c:if test="${command.saveMode eq 'A'}">
								<button type="Reset" class="btn btn-warning" onclick="resetForm();">
									<spring:message code="bt.clear" text="Reset"></spring:message>
								</button>
							</c:if>
							<apptags:backButton url="ContractualStaffMaster.html"></apptags:backButton>

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
