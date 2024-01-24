<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/disaster_management/complainRegister.js"></script>


<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message
						code="ComplainRegisterDTO.form.name" text="Complain Register" /></strong>
			</h2>
		</div>

		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message
						code="leadlift.master.ismand" /> </span>
			</div>
			<!-- End mand-label -->

			<form:form action="ComplainRegisterApproval.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmComplainRegister" id="frmComplainRegister">
				<%-- <form:hidden path="saveMode" id="saveMode" /> --%>

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
									<spring:message code="ComplainRegisterDTO.form.name"
										text="Complain Register" />
								</a>
							</h4>
						</div>

						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">

									<label class="col-sm-2 control-label required-control"
										for="department"><spring:message
											code="ComplainRegisterDTO.department"
											text="ComplainRegisterDTO.department" /></label>
									<div class="col-sm-4">
										<form:select path="entity.department" cssClass="form-control"
											id="department" data-rule-required="true" disabled="${command.saveMode eq 'V'}" >
											<form:option value="">
												<spring:message code="Select" text="Select" />
											</form:option>
											<c:forEach items="${departments}" var="dept">
												<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

									<label class="col-sm-2 control-label required-control"
										for="location"><spring:message
											code="ComplainRegisterDTO.location"
											text="ComplainRegisterDTO.location" /></label>
									<div class="col-sm-4">
										<form:select path="entity.location" cssClass="form-control"
											id="location" data-rule-required="true" disabled="${command.saveMode eq 'V'}">
											<form:option value="">
												<spring:message code="Select" text="Select" />
											</form:option>
											<c:forEach items="${locations}" var="loc">
												<form:option value="${loc.locId}">${loc.locNameEng}-${loc.locArea}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>
								<div class="form-group">
									<apptags:lookupFieldSet baseLookupCode="CMT" hasId="true"
										pathPrefix="entity.complaintType" isMandatory="true"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" cssClass="form-control"
										showAll="false" disabled="${command.saveMode eq 'V'}"/>
								</div>


								<div class="form-group">
									<apptags:input labelCode="ComplainRegisterDTO.complainerName"
										path="entity.complainerName" cssClass="hasNameClass"
										isMandatory="true" maxlegnth="50" isDisabled="${command.saveMode eq 'V'}"/>
									<apptags:input labelCode="ComplainRegisterDTO.complainerMobile"
										cssClass="hasMobileNo" maxlegnth="10" dataRuleMinlength="10"
										path="entity.complainerMobile" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"/>
								</div>

								<div class="form-group">
									<apptags:textArea
										labelCode="ComplainRegisterDTO.complainerAddress"
										path="entity.complainerAddress" cssClass="alphaNumeric"
										maxlegnth="100" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"/>
									<apptags:textArea
										labelCode="ComplainRegisterDTO.complaintDescription"
										path="entity.complaintDescription" cssClass="alphaNumeric"
										maxlegnth="100" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"/>
								</div>

							</div>

						</div>


						<!-- Start button -->
						<apptags:CheckerAction hideForward="true" hideSendback="true"/>
						<div class="text-center clear padding-10">
								<button type="submit" class="button-input btn btn-success"
									onclick="confirmToProceed(this)" name="button-submit" style=""
									id="button-submit">
									<spring:message code="bt.save" text="Submit" />
								</button>
							<apptags:backButton url="AdminHome.html"></apptags:backButton>

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
