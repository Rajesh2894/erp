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
<script type="text/javascript"
	src="js/works_management/workVigilance.js"></script>

<!-- End JSP Necessary Tags -->
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.vigilance.response.memo"
					text="Response Memo" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>

			<form:form action="WorkVigilance.html" cssClass="form-horizontal"
				name="workVigilanceForm" id="workVigilanceForm">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<form:hidden path="removeFileById" id="removeFileById" />
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="referenceDetailsById" id="referenceDetailsById" />

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<!-- End Of Create Memo Section -->

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="work.vigilance.response.memo" text="Response Memo" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control "><spring:message
											code="work.vigilance.reference.type" text="Reference Type" /></label>
									<div class="col-sm-4">
										<form:select path="vigilanceDto.referenceType"
											cssClass="form-control mandColorClass " id="referenceType"
											disabled="true">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<form:option value="M">
												<spring:message code="work.vigilance.m.b" text="MB" />
											</form:option>
											<form:option value="B">
												<spring:message code="work.vigilance.bill" text="BILL" />
											</form:option>
										</form:select>
									</div>

									<label class="control-label col-sm-2"><spring:message
											code="work.vigilance.reference.no" text="Reference No." /></label>
									<div class="col-sm-4">
										<form:input path="vigilanceDto.referenceNumber"
											id="vigilenceReferenceNo" type="text"
											class="form-control preventSpace" disabled="true" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="work.vigilance.memo.date" text="Memo Date" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="vigilanceDto.memoDate"
												cssClass="form-control datepicker mandColorClass"
												id="memoDate" readonly="true" disabled="true" />
											<label class="input-group-addon" for="memoDate"><i
												class="fa fa-calendar"></i><span class="hide"> <spring:message
														code="" text="icon" /></span><input type="hidden" id=memoDate></label>
										</div>
									</div>

									<label class="col-sm-2 control-label required-control"><spring:message
											code="work.vigilance.inspection.date" text="Inspection Date" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="vigilanceDto.inspectionDate"
												cssClass="form-control datepicker mandColorClass"
												id="inspectionDate" readonly="true" disabled="true" />
											<label class="input-group-addon" for="inspectionDate"><i
												class="fa fa-calendar"></i><span class="hide"> <spring:message
														code="" text="icon" /></span><input type="hidden"
												id=inspectionDate></label>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control "><spring:message
											code="work.vigilance.reference.type" text="Reference Type" /></label>
									<div class="col-sm-4">
										<form:select path="vigilanceDto.memoType"
											cssClass="form-control mandColorClass " id="memoType"
											disabled="true">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<form:option value="A">
												<spring:message code="work.vigilance.actionable"
													text="Actionable" />
											</form:option>
											<form:option value="I">
												<spring:message code="work.vigilance.informational"
													text="Informational" />
											</form:option>
										</form:select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="work.vigilance.comments.and.remarks"
											text="Comments/Remark" /></label>
									<div class="col-sm-10">
										<form:textarea path="vigilanceDto.memoDescription"
											cssClass="form-control mandColorClass" id="commentRemarks"
											disabled="true" />
									</div>
								</div>

							</div>
						</div>
					</div>
					<!-- End Of Response Memo Section -->

					<!-- Start Of File Upload Section -->

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="work.order.documents" text="Work Order Documents" />
								</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:if test="${fn:length(command.attachDocsList)>0}">
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="deleteDoc">
											<tr>
												<th width="" align="center"><spring:message
														code="ser.no" text="" /><input type="hidden" id="srNo"></th>
												<th scope="col" width="64%" align="center"><spring:message
														code="work.estimate.document.description"
														text="Document Description" /></th>
												<th scope="col" width="30%" align="center"><spring:message
														code="scheme.view.document" /></th>
											</tr>
											<c:set var="e" value="0" scope="page" />
											<c:forEach items="${command.attachDocsList}" var="lookUp">
												<tr>
													<td>${e+1}</td>
													<td>${lookUp.dmsDocName}</td>
													<td><apptags:filedownload
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="WorkVigilance.html?Download" /></td>
												</tr>
												<c:set var="e" value="${e + 1}" scope="page" />
											</c:forEach>
										</table>
									</div>
									<br>
								</c:if>
							</div>
						</div>
					</div>
					<!-- End Of File Upload Section -->
				</div>

				<div class="text-center clear padding-10">
					<button type="button" id="save" class="btn btn-success btn-submit"
						onclick="saveResponseMemoVigilance();">
						<spring:message code="" text="Send Response" />
					</button>

					<apptags:resetButton></apptags:resetButton>

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" id="button-Cancel"
						onclick="backWorkVigilance();">
						<spring:message code="works.management.back" text="" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>