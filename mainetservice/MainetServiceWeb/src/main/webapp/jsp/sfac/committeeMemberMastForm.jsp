<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/sfac/committeeMemberMastForm.js"></script>


<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Committee Member Form" />
			</h2>
			<apptags:helpDoc url="CommitteeMemberMaster.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="committeeMemberMastForm"
				action="CommitteeMemberMaster.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.committee.type" text="Committee Type" /></label>
					<c:set var="baseLookupCode" value="MPT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="comMemDto.committeeTypeId"
						disabled="${command.saveMode eq 'V' ? true : false }"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />
						
	                 <label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.committee.mem.org" text="Organization" /></label>
					<div class="col-sm-4">
						<form:input labelCode="Organization" id="organization"  maxlength="200"
							disabled="${command.saveMode eq 'V' ? true : false }"
							path="comMemDto.organization" cssClass="form-control" />
					</div>
				
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.committee.mem.name" text="Name" /></label>
					<div class="col-sm-4">
						<form:input labelCode="Member Name" id="memberName" maxlength="250"
							disabled="${command.saveMode eq 'V' ? true : false }"
							path="comMemDto.memberName" cssClass="form-control" />
					</div>
					
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.designation" text="Designation" /></label>
					<div class="col-sm-4">
						<form:input labelCode="Designation" id="designation" maxlength="200"
							disabled="${command.saveMode eq 'V' ? true : false }"
							path="comMemDto.designation" cssClass="form-control" />
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.contact.no" text="Contact No." /></label>
					<div class="col-sm-4">
						<form:input labelCode="Contact No." id="contactNo" maxlength="10"
							disabled="${command.saveMode eq 'V' ? true : false }"
							path="comMemDto.contactNo" cssClass="form-control hasMobileNo" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.fpo.emailId" text="Email Id" /></label>
					<div class="col-sm-4">
						<form:input labelCode="Email Id" id="emailId" maxlength="50"
							disabled="${command.saveMode eq 'V' ? true : false }"
							path="comMemDto.emailId" cssClass="form-control hasemailclass" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.committee.mem.since" text="Member Since" /></label>
					<div class="col-sm-4 ">
						<div class="input-group ">
							<form:input path="comMemDto.fromDate" type="text"
								class="form-control datepicker mandColorClass memberSince"
								id="fromDate" placeholder="dd/mm/yyyy" readonly="true" />
							<span class="input-group-addon "><i
								class="fa fa-calendar "></i></span>
						</div>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.status" text="Status" /></label>
					<c:set var="baseLookupCode" value="CMS" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="comMemDto.status"
						disabled="${command.saveMode eq 'V' ? true : false }"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />
				</div>


				<div class="text-center padding-top-10">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" class="btn btn-success"
							title='<spring:message code="sfac.submit" text="Submit" />'
							onclick="saveCommiteeDetForm(this)">
							<spring:message code="sfac.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'A'}">
						<button type="button" class="btn btn-warning"
							title='<spring:message code="sfac.button.reset" text="Reset"/>'
							onclick="ResetForm()">
							<spring:message code="sfac.button.reset" text="Reset" />
						</button>
					</c:if>
					<button type="button" class="btn btn-danger"
						title='<spring:message code="sfac.button.back" text="Back"/>'
						onclick="window.location.href ='CommitteeMemberMaster.html'">
						<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>