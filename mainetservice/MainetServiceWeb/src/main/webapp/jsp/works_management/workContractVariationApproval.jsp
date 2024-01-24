<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/works_management/workContractVariationApproval.js"></script>
<script type="text/javascript"
	src="js/works_management/termsAndConditionForApproval.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.contract.variation.approval.title"
					text="Work Variation Approval" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="works.fiels.mandatory.message" /></span>
			</div>
			<form:form action="WorkContractVariationApproval.html"
				cssClass="form-horizontal" name="contractVariationApproval"
				id="contractVariationApproval">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="contractTaskName" id="contractTaskName" />
				<form:hidden path="flagForSendBack" id="flagForSendBack" />

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#collapse1"><spring:message
										code="work.contract.variation.approval.details"
										text="Contract Variation Details" /> </a>
							</h4>
						</div>
						<div id="" class="panel-collapse ">
							<div class="panel-body">
								<div class="form-group">
									<apptags:input labelCode="project.master.dept"
										isReadonly="true" path="deptName"></apptags:input>

								</div>
								<div class="form-group">
									<apptags:input labelCode="work.order.contract.no"
										isReadonly="true" path="contractAgreementMastDTO.contNo"></apptags:input>

									<apptags:input
										labelCode="work.contract.variation.approval.vendor.name"
										isReadonly="true" path="contractAgreementMastDTO.contp2Name"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:date fieldclass=""
										labelCode="work.contract.variation.approval.contract.from.date"
										datePath="contractAgreementMastDTO.contFromDate"
										readonly="true"></apptags:date>

									<apptags:date fieldclass=""
										labelCode="work.contract.variation.approval.contract.to.date"
										datePath="contractAgreementMastDTO.contToDate" readonly="true"></apptags:date>
								</div>
								<div class="form-group">
									<apptags:input
										labelCode="work.contract.variation.approval.contract.amount"
										isReadonly="true" path="contractAmount" cssClass="text-right"></apptags:input>

									<apptags:input
										labelCode="work.contract.variation.approval.variation.amount"
										isReadonly="true" path="variationAmount" cssClass="text-right"></apptags:input>
								</div>

								<div class="form-group">
									<c:choose>
										<c:when
											test="${(command.contractTaskName eq 'Initiator') && (command.flagForSendBack eq 'SEND_BACK')}">
											<label class="col-sm-2 control-label"><spring:message
													code="work.contract.variation.approval.edit.work.variation"
													text="Link to Edit Work Variation" /></label>
											<div class="col-sm-4">
												<a class="text-center" href="#"
													onclick="viewWorkVariation(${command.contractAgreementMastDTO.contId},'${command.contractMode}');"
													style="text-decoration: underline; text-align: center;"><span><c:out
															value="${command.contractAgreementMastDTO.contNo}"></c:out></span></a>
											</div>
										</c:when>
										<c:otherwise>
											<label class="col-sm-2 control-label"><spring:message
													code="work.contract.variation.approval.view.work.variation" /></label>
											<div class="col-sm-4">
												<a class="text-center" href="#"
													onclick="viewWorkVariation(${command.contractAgreementMastDTO.contId},'${command.contractMode}');"
													style="text-decoration: underline; text-align: center;"><span><c:out
															value="${command.contractAgreementMastDTO.contNo}"></c:out></span></a>
											</div>
										</c:otherwise>
									</c:choose>

									<%--<label class="col-sm-2 control-label"><spring:message
											code="work.estimate.approval.edit.estimate" /></label>
									<div class="col-sm-4">
										<a class="text-center" href="#"
											onclick="getEditWorkEstimate(${command.workDefinitionDto.workId},'${command.estimateMode}')"
											style="text-decoration: underline; text-align: center;"><span><c:out
													value="${command.workDefinitionDto.workcode}"></c:out></span></a>
									</div>--%>
								</div>
							</div>
						</div>
					</div>
					<jsp:include
						page="/jsp/works_management/termsAndConditionForApproval.jsp"></jsp:include>

					<c:if test="${command.completedFlag ne 'Y' }">
					<apptags:CheckerAction></apptags:CheckerAction>
					</c:if>
				</div>
				<div class="text-center clear padding-10">
					<c:if test="${command.completedFlag ne 'Y' }">
					<button type="button" id="save" class="btn btn-success"
						onclick="showConfirmBoxForApproval(this);">
						<spring:message code="mileStone.submit" text="" />
					</button>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel"
						onclick="window.location.href='AdminHome.html'" id="button-Cancel">
						<spring:message code="works.management.back" text="" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

