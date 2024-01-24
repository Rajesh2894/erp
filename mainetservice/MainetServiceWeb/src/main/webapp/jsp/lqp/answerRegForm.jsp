<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/lqp/questionRegistration.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="lqp.answer.title"
					text="Assembly Answer Details" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="lqp.fiels.mandatory.message"
						text="Field with * is mandatory" /></span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form method="POST" action="LegislativeAnswer.html"
				cssClass="form-horizontal" id="answerRegFormId" name="answerRegForm">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<form:hidden path="saveMode" id="saveMode" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="lqp.answer.title" text="Assembly Answer Details" /> </a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<apptags:input labelCode="lqp.question.no" isReadonly="true"
										path="queryAnsrMstDto.queryRegistrationMasterDto.questionId" maxlegnth="19"></apptags:input>

									<apptags:date fieldclass="datepicker"
										labelCode="lqp.question.date" isDisabled="true"
										datePath="queryAnsrMstDto.queryRegistrationMasterDto.questionDate"></apptags:date>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label" for=""> <spring:message
											code="lqp.question.type" /></label>
									<apptags:lookupField items="${command.getLevelData('QTP')}"
										path="queryAnsrMstDto.queryRegistrationMasterDto.questionTypeId"
										cssClass="form-control chosen-select-no-results"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="Select" disabled="true" />

									<apptags:input labelCode="lqp.subject" isReadonly="true"
										path="queryAnsrMstDto.queryRegistrationMasterDto.questionSubject"  maxlegnth="99"></apptags:input>
								</div>

								<div class="form-group">
									<apptags:date fieldclass="datepicker"
										labelCode="lqp.question.deadlineDate" isDisabled="true"
										datePath="queryAnsrMstDto.queryRegistrationMasterDto.deadlineDate"></apptags:date>

									<apptags:input labelCode="lqp.mlaName"
										path="queryAnsrMstDto.queryRegistrationMasterDto.mlaName"  maxlegnth="99" isReadonly="true"></apptags:input>
								</div>


								<div class="form-group">
									<apptags:textArea labelCode="lqp.question.details"
										isReadonly="true"
										path="queryAnsrMstDto.queryRegistrationMasterDto.question"
										maxlegnth="249" ></apptags:textArea>
								</div>
								<div class="form-group">
									<apptags:textArea isMandatory="true"
									isDisabled="${command.approvalViewFlag eq 'V'}"
										labelCode="lqp.answer.details" path="queryAnsrMstDto.answer"
										maxlegnth="2999" ></apptags:textArea>
								</div>
								
								<!-- Uploaded Documents -->
								<c:if test="${not empty command.documentDtos}">
									<h4 class="margin-top-0 margin-bottom-10 panel-title">
										<a data-toggle="collapse" href="#DocumentUpload"><spring:message
												code="lqp.upload.doc" /></a>
									</h4>
									<div id="DocumentUpload">
										<fieldset class="fieldRound">
											<div class="overflow">
												<div class="table-responsive">
													<table
														class="table table-hover table-bordered table-striped">
														<tbody>
															<tr>
																<th><label class="tbold"><spring:message
																			code="lqp.srno" text="Sr No" /></label></th>
																<th><label class="tbold"><spring:message
																			code="lqp.attachBy" text="Attach By" /></label></th>
																<th><label class="tbold"><spring:message
																			code="lqp.downlaod" text="Download"/></label></th>
															</tr>

															<c:forEach items="${command.documentDtos}" var="lookUp"
																varStatus="lk">
																<tr>
																	<td><label>${lk.count}</label></td>
																	<td><label>${lookUp.attBy}</label></td>
																	<td><c:set var="links"
																			value="${fn:substringBefore(lookUp.attPath, lookUp.attFname)}" />
																		<apptags:filedownload filename="${lookUp.attFname}"
																			filePath="${lookUp.attPath}"
																			dmsDocId="${lookUp.dmsDocId}"
																			actionUrl="LegislativeAnswer.html?Download"></apptags:filedownload>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</fieldset>
									</div>
								</c:if>

								<!-- Start button -->
								<c:if test="${command.approvalViewFlag eq 'C'}">
									<div
										class="widget-content padding panel-group accordion-toggle"
										id="accordion_single_collapse1">
										<apptags:CheckerAction hideForward="false" hideSendback="true" showInitiator="false"></apptags:CheckerAction>
									</div>
									<div class="text-center widget-content padding">
										<button type="button" id="save"
											class="btn btn-success btn-submit"
											onclick="saveAnswerRegform(this);">
											<spring:message code="master.save" text="Save" />
										</button>
										<button type="button" class="button-input btn btn-danger"
											name="button-Cancel" value="Cancel"
											onclick="window.location.href='AdminHome.html'"
											id="button-Cancel">
											<spring:message code="lqp.button.back" text="Back" />
										</button>
									</div>
								</c:if>
								<c:if test="${command.approvalViewFlag eq 'V'}">
								<div class="text-center">
								<button type="button" class="button-input btn btn-danger"
										name="button-Cancel" value="Cancel" style=""
										onclick="window.location.href='LegislativeQuestion.html'"
										id="button-Cancel">
										<i class="fa fa-chevron-circle-left padding-right-5"></i>
										<spring:message code="lqp.button.back" text="Back" />
									</button>
									</div>
								</c:if>
								<!-- End button -->


							</div>
						</div>
					</div>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->
<script>
	var questionDate = $('#questionDate').val();
	var deadlineDate = $('#deadlineDate').val();
	if (questionDate) {
		$('#questionDate').val(questionDate.split(' ')[0]);
	}
	if (deadlineDate) {
		$('#deadlineDate').val(deadlineDate.split(' ')[0]);
	}
</script>