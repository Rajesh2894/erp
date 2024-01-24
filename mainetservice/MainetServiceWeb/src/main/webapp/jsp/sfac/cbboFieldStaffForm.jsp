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
<script type="text/javascript" src="js/sfac/cbboFiledStaffForm.js"></script>

<style>
table.crop-details-table tbody tr td>input[type="checkbox"] {
	margin: 0.5rem 0 0 -0.5rem;
}

.stateDistBlock>label[for="sdb3"]+div {
	margin-top: 0.5rem;
}

.charCase {
	text-transform: uppercase;
}

#udyogAadharApplicable, #isWomenCentric {
	margin: 0.6rem 0 0 0;
}
</style>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.cbbo.field.staff.form"
					text="CBBO Field Staff Details Entry Form" />
			</h2>
			<apptags:helpDoc url="CBBOFiledStaffDetailForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="CBBOFiledStaffDetailForm"
				action="CBBOFiledStaffDetailForm.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse"
									href="#circularDetails"> <spring:message
										code="sfac.circular.notify.details"
										text="Circular/Notification Details" />
								</a>
							</h4>
						</div>
						<div id="circularDetails" class="panel-collapse collapse in">
							<div class="panel-body">


								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.cbbo.field.staff.expert.name"
											text="CBBO Expert Name" /></label>
									<div class="col-sm-4">
										<form:input path="dto.cbboExpertName" id="cbboExpertName" disabled="${command.viewMode eq 'V' ? true : false }"
											class="form-control hasCharacter" maxlength="50" />
									</div>

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.emailId"
											text="Email Id" /></label>
									<div class="col-sm-4">
										<form:input path="dto.emailId" id="emailId" disabled="${command.viewMode eq 'V' ? true : false }"
											class="form-control hasEmail" />
									</div>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.cbbo.field.staff.contact.no"
											text="Email Id" /></label>
									<div class="col-sm-4">
										<form:input path="dto.contactNo" id="contactNo" disabled="${command.viewMode eq 'V' ? true : false }"
											class="form-control hasNumber" maxlength="10" />
									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.state" text="State" /></label>
									<div class="col-sm-4">
										<form:select path="dto.sdb1" id = "sdb1"
											cssClass="form-control chosen-select-no-results" disabled="true" >
											<form:option value="">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.stateList}" var="dto2">
												<form:option value="${dto2.lookUpId}">${dto2.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
									

								</div>

								<div class="form-group">
									
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.district" text="District" /></label>
									<div class="col-sm-4">
										<form:select path="dto.sdb2" id = "sdb2"
											cssClass="form-control chosen-select-no-results" disabled="true" >
											<form:option value="">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.districtList}" var="dto2">
												<form:option value="${dto2.lookUpId}">${dto2.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.block" text="Block" /></label>
									<div class="col-sm-4">
										<form:select path="dto.sdb3" id = "sdb3" disabled="${command.viewMode eq 'V' ? true : false }"
											cssClass="form-control chosen-select-no-results" onchange="getSD(this)" >
											<form:option value="">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.blockList}" var="dto2">
												<form:option value="${dto2.lookUpId}">${dto2.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>

								
								

							

							</div>
						</div>
					</div>


				


				</div>

				<div class="text-center padding-top-10">
					<c:if test="${command.viewMode ne 'V'}">
						<button type="button" class="btn btn-success"
							title='<spring:message code="sfac.submit" text="Submit" />'
							onclick="saveCBBOFiledStaffDetailForm(this);">
							<spring:message code="sfac.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.viewMode eq 'A'}">
						<button type="button" class="btn btn-warning"
							title='<spring:message code="sfac.button.reset" text="Reset"/>'
							onclick="ResetForm();">
							<spring:message code="sfac.button.reset" text="Reset" />
						</button>
					</c:if>
					<apptags:backButton url="CBBOFiledStaffDetailForm.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>