<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/works_management/termsAndConditionForApproval.js"></script>
<script type="text/javascript"
	src="js/works_management/measurementBookApproval.js"></script>

<%
response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="mb.Approval" text="Measurement Book Approval" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="MeasurementBookApproval.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="MeasurementBookApproval.html"
				class="form-horizontal" id="measurementBook" name="measurementBook">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<br>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="Measurment.book.form" /> </a>
							</h4>
						</div>
						<div id="" class="panel-collapse ">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="project.master.dept" /></label>
									<div class="col-sm-4">
										<form:select path="workDefinitionDto.deptId"
											cssClass="form-control mandColorClass" disabled="true"
											id="depId">
											<c:forEach items="${command.departmentsList}"
												var="department">
												<form:option value="${department.dpDeptid}"
													code="${department.dpNameMar}">${department.dpDeptdesc}</form:option>
											</c:forEach>

										</form:select>
									</div>
									<apptags:input labelCode="project.master.projname"
										path="workOrderDto.projName" cssClass="" isReadonly="true"></apptags:input>
								</div>

								<div class="form-group">
									<apptags:textArea labelCode="work.def.workname"
										path="workOrderDto.workName" isReadonly="true"></apptags:textArea>
									<apptags:input labelCode="MB.amount"
										path="workOrderDto.contractValue" isReadonly="true"
										cssClass="text-right"></apptags:input>
								</div>

								<div class="form-group">

									<label for="" class="col-sm-2 control-label required-control"><spring:message
											code="work.order.workOrder.no" text="Work Order No." /></label>
									<div class="col-sm-4">
										<form:select path="workOrderDto.workId"
											cssClass="form-control chosen-select-no-results mandColorClass"
											id="workId" data-rule-required="true"
											onchange="OpenCreateMb(this);" disabled="true" >
											<form:option value="3">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.workOrderDtoList}" var="lookUp">
												<form:option value="${lookUp.workId}" code="">${lookUp.workOrderNo}</form:option>

											</c:forEach>
										</form:select>
									</div>
									<label for="" class="col-sm-2 control-label"><spring:message
											code="work.estimate.approval.sanction.date"
											text="Sanction Date " /> </label>
									<div class="col-sm-4">
										<form:input path="workOrderDto.startDate"
											cssClass="form-control dates" readonly="true" id="startDate" />
									</div>
								</div>

								<div class="form-group">
									<apptags:input labelCode="agreement.number"
										path="workOrderDto.contractMastDTO.contNo" isReadonly="true"></apptags:input>
								</div>

							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="link.to.view.MB" /></label>
						<div class="col-sm-4">
							<a class="text-center" href="#"
								onclick="getEditWork(${command.measurementBookMasterDto.workMbId},'V')"
								style="text-decoration: underline; text-align: center;"><span><c:out
										value="${command.measurementBookMasterDto.workMbNo}"></c:out></span></a>
						</div>
					</div>
                    
                   

					<div class="panel panel-default">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#remarks"> <spring:message
									code="" text="Note Sheet Remarks" /></a>
						</h4>

						<div id="remarks" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered  table-condensed margin-bottom-10"
									id="remarks">
									<thead>
										<th width="15%"><spring:message code="mb.Date" text="Date" /><span
											class="mand"></span></th>
										<th width="12%"><spring:message code="mb.task.name" text="Task Name" /><span
											class="mand"></span></th>
										<th width="12%"><spring:message code="mb.decision" text="Decision" /><span
											class="mand"</span></th>
										<th width="35%"><spring:message code="mb.remark" text="Remark" /><span
											class="mand"</span></th>

									</thead>
									<c:forEach var="taxData" items="${command.actionWithDocs}"
										varStatus="remarks">
										<c:if test="${taxData.comments != null}">
											<tr style="text-align: center;">
												<td><fmt:formatDate pattern="dd/MM/yyyy hh:mm a"
														value="${taxData.createdDate}" /></td>
												<td><c:out value="${taxData.taskName}"></c:out></td>
												<td><c:out value="${taxData.decision}"></c:out></td>
												<td><c:out value="${taxData.comments}"></c:out></td>

											</tr>
										</c:if>
									</c:forEach>
								</table>
							</div>
						</div>

					</div>


					<c:if test="${command.completedFlag ne 'Y' }">
					<apptags:CheckerAction showInitiator="true"  />
					</c:if>
	
				</div>
				<div class="text-center clear padding-10">
				<c:if test="${command.completedFlag ne 'Y' }">
					<button type="button" id="save" class="btn btn-success"
						onclick="showConfirmBoxForApproval(this);">
						<i class="fa fa-sign-out padding-right-5"></i>
						<spring:message code="mileStone.submit" text="" />
					</button>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel"
						onclick="window.location.href='AdminHome.html'" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>