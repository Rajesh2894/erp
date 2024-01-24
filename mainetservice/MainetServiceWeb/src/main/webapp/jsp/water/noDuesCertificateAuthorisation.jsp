
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/water/noDuesCertificateAuthorisation.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<div id="NoDuesCertiForm">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
		<!-- Start info box -->
		<div class="widget">
			<div class="widget-header">
				<h2>
					<label><spring:message
							code="water.nodues.noduesserviceAuth" /></label>
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
				<form:form action="NoDuesCertAuthoController.html"
					class="form-horizontal form" name="noDuesCertificateAuth"
					id="noDuesCertificateAuth">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv" style="display: none;">
						<i class="fa fa-plus-circle"></i>
					</div>

					<%-- <apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail> --%>
					<div class="accordion-toggle">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#Applicant"><spring:message
									code="applicantinfo.label.header" /></a>
						</h4>
						<div class="panel-collapse collapse in" id="Applicant">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="applicantTitle"><spring:message
										code="applicantinfo.label.title" /></label>
								<c:set var="baseLookupCode" value="TTL" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="applicantDetailDto.applicantTitle"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false" disabled="true"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" />

								<label class="col-sm-2 control-label required-control"
									for="firstName"><spring:message
										code="applicantinfo.label.firstname" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control"
										readonly="true" path="applicantDetailDto.applicantFirstName"
										id="firstName" data-rule-required="true"></form:input>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label" for="middleName"><spring:message
										code="applicantinfo.label.middlename" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control"
										readonly="true" path="applicantDetailDto.applicantMiddleName"
										id="middleName"></form:input>
								</div>
								<label class="col-sm-2 control-label required-control"
									for="lastName"><spring:message
										code="applicantinfo.label.lastname" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control"
										readonly="true" path="applicantDetailDto.applicantLastName"
										id="lastName" data-rule-required="true"></form:input>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="applicantinfo.label.mobile" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control hasMobileNo" readonly="true"
										maxlength="10" path="applicantDetailDto.mobileNo"
										id="mobileNo" data-rule-required="true"></form:input>
								</div>
								<label class="col-sm-2 control-label"><spring:message
										code="applicantinfo.label.email" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control"
										readonly="true" path="applicantDetailDto.emailId" id="emailId"></form:input>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="address.line1" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control"
										readonly="true" path="applicantDetailDto.areaName"
										id="areaName" data-rule-required="true"></form:input>
								</div>
								<label class="col-sm-2 control-label"><spring:message
										code="address.line2" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control"
										readonly="true" path="applicantDetailDto.villageTownSub"
										id="villTownCity"></form:input>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message
										code="address.line3" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control"
										readonly="true" path="applicantDetailDto.roadName"
										id="roadName"></form:input>
								</div>
								<label class="col-sm-2 control-label required-control"><spring:message
										code="applicantinfo.label.pincode" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control hasNumber"
										readonly="true" path="applicantDetailDto.pinCode" id="pinCode"
										maxlength="6" data-rule-required="true"></form:input>
								</div>
							</div>
							<div class="form-group">
								<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="applicantDetailDto.dwzid"
									isMandatory="true" hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" disabled="true"
									cssClass="form-control  required-control" />
							</div>

							<c:if test="${not empty command.applicantDetailDto.bplNo }">
								<div class="form-group" id="bpldiv">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="applicantinfo.label.bplno" /></label>
									<div class="col-sm-4">
										<form:input name="" type="text"
											class="form-control required-control"
											path="applicantDetailDto.bplNo" id="bplNo" maxlength="16"
											readonly="true" />
									</div>
								</div>
							</c:if>

						</div>
					</div>

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
										readonly="true" id="consumerNo" type="text"
										class="form-control"></form:input>
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
										readonly="true" name="noOfCopies" type="text"
										class="form-control"></form:input>
								</div>

								<label class="col-sm-2 control-label required-control"><spring:message
										code="" text="DueAmount" /></label>
								<div class="col-sm-4">
									<form:input path="reqDTO.duesAmount" id="duesAmount"
										name="duesAmount" type="text" class="form-control text-right"
										readonly="true"></form:input>
								</div>
							</div>
						</div>
					</div>

					<div class="accordion-toggle">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#NoDuesInfo"><label><spring:message
										code="" text="Payment Information" /></label></a>
						</h4>
						<div class="panel-collapse collapse in" id="paymentInfo">
							<div class="form-group margin-top-10" id="">
								<label class="col-sm-2 control-label"><spring:message
										code="water.field.name.amountPaid" text="Amount Paid" /></label>
								<div class="col-sm-4">
									<input type="text" class="form-control"
										value="${command.offlineDTO.amountToShow}" maxlength="12"
										readonly="true">
								</div>
							</div>
						</div>
					</div>

					<div class="form-group">
						<apptags:CheckerAction showInitiator="true" hideForward="true"
							hideSendback="true"></apptags:CheckerAction>
					</div>


					<div class="text-center padding-bottom-20">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveForm(this)" id="submit">
							<spring:message code="bt.save" text="Submit" />
						</button>

						<!-- use form:input -->
						<input type="button" class="btn btn-danger"
							onclick="window.location.href='AdminHome.html'" value="Back">

					</div>

				</form:form>
			</div>
		</div>
	</div>
	<!-- End of info box -->

</div>
