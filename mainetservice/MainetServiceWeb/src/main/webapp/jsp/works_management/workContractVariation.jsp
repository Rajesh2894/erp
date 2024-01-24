<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<%
    response.setContentType("text/html; charset=utf-8");
%>
<script type="text/javascript"
	src="js/works_management/workContractVariationForm.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.estimate.title"
					text="Work Non Sor Estimation" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="ContractVariation.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="ContractVariation.html" class="form-horizontal"
				name="workEstimateNonSorForm" id="workEstimateNonSorForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>


				<form:hidden path="saveMode" id="mode" />
				<form:hidden path="requestFormFlag" id="requestFormFlag" />

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a1"> <spring:message
									code="sor.create.variation.order" /></a>
						</h4>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sor.contract.agreement.no"
											text="Contract Agreement Number" /></label>
									<div class="col-sm-4">
										<c:if test="${command.saveMode eq 'A'}">
											<form:select path="newContractId"
												cssClass="form-control chosen-select-no-results mandColorClass"
												id="contractId" data-rule-required="true"
												onchange="getContractDetils(this)">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${command.workOrderContractDetailsDto}"
													var="lookUp">
													<form:option value="${lookUp.contId}" code="">${lookUp.contNo}</form:option>
												</c:forEach>

											</form:select>
										</c:if>
										<c:if test="${command.saveMode ne 'A'}">
											<form:select path="newContractId"
												cssClass="form-control  mandColorClass" id="contractId"
												data-rule-required="true" disabled="true">
												<form:option value="">
												</form:option>
												<c:forEach items="${command.workOrderContractDetailsDto}"
													var="lookUp">
													<form:option value="${lookUp.contId}" code="">${lookUp.contNo}</form:option>
												</c:forEach>
											</form:select>
										</c:if>
									</div>

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sor.contract.agreement.date"
											text="Contract Agreement Date" /></label>
									<div class="col-sm-4">
										<form:input path="" id="contractAgreementDate"
											class="form-control mandColorClass dates" value=""
											readonly="true" data-rule-required="" />
									</div>
								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sor.startdate" text="Start Date" /></label>
									<div class="col-sm-4">
										<form:input path="" id="startDate"
											class="form-control mandColorClass dates" value=""
											readonly="true" data-rule-required="" />
									</div>


									<label class="col-sm-2 control-label required-control"><spring:message
											code="sor.endate" text="End Date" /></label>
									<div class="col-sm-4">
										<form:input path="" id="endDate"
											class="form-control mandColorClass dates" value=""
											readonly="true" data-rule-required="" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sor.contract.amount" text="Contract Amount" /></label>
									<div class="col-sm-4">
										<form:input path="" cssClass="form-control text-right"
											id="contractAmount" readonly="true" data-rule-required="true" />
									</div>

									<label class="col-sm-2 control-label required-control"><spring:message
											code="" text="Revise Estimate Entry" /></label>
									<div class="col-sm-4">
										<form:select path="workeReviseFlag" cssClass="form-control "
											id="status" onchange="getDetails(this)"
											disabled="${command.saveMode ne 'A'}">
											<form:option value="0">
												<spring:message code='work.management.select' />
											</form:option>
											<form:option value="N">
												<spring:message code="contract.new" />
											</form:option>
											<form:option value="E">
												<spring:message code="contract.existing" />
											</form:option>
										</form:select>
									</div>

								</div>


								<div class="form-group">

									<label class="col-sm-2 control-label required-control"><spring:message
											code="work.defination.number" text="Select Items From:" /></label>
									<div class="radio col-sm-4 margin-top-5">

										<label> <form:radiobutton path="estimateRadioFlag"
												value="S" id="sor" onclick="getTableDetails(this)" /> <spring:message
												code="sor.scheduleofrate" /></label> <label> <form:radiobutton
												path="estimateRadioFlag" value="N" id="nonSor"
												onclick="getTableDetails(this)" /> <spring:message
												code="sor.nonscheduleofrate" />
										</label> <label> <form:radiobutton path="estimateRadioFlag"
												value="B" id="billOfQuantity"
												onclick="getTableDetails(this)" /> <spring:message
												code="sor.billofquantity" /></label>
									</div>
								</div>

							</div>
						</div>
					</div>

					<!-- 	<div id="ViewTable"></div> -->
					<div id="sortablevariation"></div>
					<!-- <div id="NonSorTable"> </div>
				<div id="billOfQuantity1"> </div> -->

					<div class="text-center clear padding-10">
						<c:if test="${command.saveMode ne 'V'}">
							<button type="button" id="save"
								class="btn btn-success btn-submit"
								onclick="saveNonSorDetails(this);">
								<spring:message code="work.management.SaveContinue"
									text="Save & Continue" />
							</button>
						</c:if>

						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="backForm();" id="button-Cancel">
							<spring:message code="works.management.back" text="" />
						</button>

					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
