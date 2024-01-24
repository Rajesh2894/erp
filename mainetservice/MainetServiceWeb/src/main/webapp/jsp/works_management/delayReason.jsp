<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script type="text/javascript" src="js/works_management/delayReason.js"></script>




<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">			
			<h2>
				<spring:message code="wms.delayReason.title" text="Hindrance Entry" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="DelayReason.html" method="post"
				class="form-horizontal" name="delayReason" id="delayReason">

				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- ----------------- this div below is used to display the error message on the top of the page--------------------------->

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="project.master.projname" /></label>
					<div class="col-sm-4">
						
							<form:select path="delayReasonDto.projId" id="projId"
								cssClass="form-control chosen-select-no-results mandColorClass"
								onchange="getCreateWorkName(this);"
								disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
								<form:option value="">
									<spring:message code='work.management.select' />
								</form:option>
								<c:forEach items="${command.projectMasterList}"
									var="activeProjName">
									<form:option value="${activeProjName.projId }"
										code="${activeProjName.workId }">${activeProjName.projNameEng}</form:option>
								</c:forEach>
							</form:select>
						
						
					</div>


					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.def.workname" /></label>
					<div class="col-sm-4">
						<c:if test="${command.saveMode eq 'C'}">
							<form:select path="delayReasonDto.workId" id="workId"
								cssClass="form-control chosen-select-no-results mandColorClass"
								disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
								<form:option value="">
									<spring:message code='work.management.select' />
								</form:option>
							</form:select>
						</c:if>
						<c:if test="${command.saveMode eq 'E' || command.saveMode eq 'V'}">
							<form:select path="delayReasonDto.workId" id="workId"
								cssClass="form-control chosen-select-no-results mandColorClass"
								disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
								<form:option value="${command.delayReasonDto.workId}">${command.delayReasonDto.workName}</form:option>
								
							</form:select>
						</c:if>
					</div>
					
				</div>
				<div class="form-group">

					<label class="col-sm-2 control-label required-control "><spring:message
							code="wms.delayReason.dateOccurance" text="Date of occurance" /></label>
					<div class="col-sm-4">
					<c:if test="${command.saveMode ne 'C'}">
					<div class="input-group">
							<form:input path="delayReasonDto.dateOccuranceDesc" id="dateOccu"
								class="form-control mandColorClass datepickerEndDate " readonly="true"
								onchange="" disabled="${command.saveMode eq 'V'? true : false}" />
							<label class="input-group-addon"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"></label>

						</div></c:if>
						<c:if test="${command.saveMode eq 'C' }">
						<div class="input-group">
							<form:input path="delayReasonDto.dateOccurance" id="dateOccu"
								class="form-control mandColorClass datepickerEndDate " readonly="true"
								onchange="" disabled="${command.saveMode eq 'V'? true : false}" />
							<label class="input-group-addon"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"></label>

						</div>
						</c:if>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="wms.delayReason.dateClearance" text="Date of clearance" /></label>
					<div class="col-sm-4">
					<c:if test="${command.saveMode ne 'C'}">
						<div class="input-group">
							<form:input path="delayReasonDto.dateClearanceDesc"
								id="dateClearance" class="form-control datepickerEndDate"
								readonly="true"
								disabled="${command.saveMode eq 'V'? true : false}" onchange="" />
							<label class="input-group-addon"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"></label>

						</div>
						</c:if>
						<c:if test="${command.saveMode eq 'C'}">
						<div class="input-group">
							<form:input path="delayReasonDto.dateClearance"
								id="dateClearance" class="form-control datepickerEndDate"
								readonly="true"
								disabled="${command.saveMode eq 'V'? true : false}" onchange="" />
							<label class="input-group-addon"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"></label>

						</div>
						</c:if>
					</div>
				</div>

				<div class="form-group">
					<label for="text-1" class="control-label col-sm-2"><spring:message
							code="wms.delayReason.period" text="Period (in days)" /></label>
					<div class="col-sm-4">

						<form:input type="text" class="form-control hasNumber"
							path="delayReasonDto.period" id="period"
							disabled="${command.saveMode eq 'V'? true : false}"></form:input>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="wms.delayReason.overlapPeriod" text="Overlapping Period (in days)" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control hasNumber"
							path="delayReasonDto.overPeriod" id="ovelPeriod"
							disabled="${command.saveMode eq 'V'? true : false}"></form:input>
					</div>

				</div>

				<div class="form-group">
					<label for="text-1" class="control-label col-sm-2 required-control"><spring:message
							code="wms.delayReason.weighOfHind" text="Weightage of hindrance" /></label>
					<div class="col-sm-4">

						<form:input type="text" class="form-control"
							path="delayReasonDto.weigtHind" id="weightage"
							disabled="${command.saveMode eq 'V'? true : false}"></form:input>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="wms.delayReason.netEffDays" text="Net effective days of hindrance" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control"
							path="delayReasonDto.dayHindrnc" id="effDays"
							disabled="${command.saveMode eq 'V'? true : false}"></form:input>
					</div>

				</div>

				<div class="form-group">
					<label for="text-1" class="control-label col-sm-2 required-control"><spring:message
							code="wms.delayReason.remarks" text="Remarks and reference" /></label>
					<div class="col-sm-4">

						<form:input type="text" class="form-control"
							path="delayReasonDto.remark" id="remark"
							disabled="${command.saveMode eq 'V'? true : false}"></form:input>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="wms.delayReason.siteEng" text="Site Engineer" /></label>
					<div class="col-sm-4">

						<form:select path="delayReasonDto.siteEngId" id="siteEngId"
							cssClass="form-control chosen-select-no-results mandColorClass"
							disabled="${command.saveMode eq 'V'? true : false}">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.employees}" var="activeEmp">
								<form:option value="${activeEmp.empId }">${activeEmp.empname}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>
				<div class="form-group">
					<label for="text-1" class="control-label col-sm-2 required-control"><spring:message
							code="wms.delayReason.natureOfHindrnc" text="Nature of Hindrance" /></label>
					<div class="col-sm-4">

						<form:input type="text" class="form-control"
							path="delayReasonDto.natOfHindrnc" id="natHindrnc"
							disabled="${command.saveMode eq 'V'? true : false}"></form:input>
					</div>
					<label for="text-1" class="control-label col-sm-2 required-control"><spring:message
							code="wms.delayReason.contName" text="Contractor Representative Name" /></label>
					<div class="col-sm-4">

						<form:input type="text" class="form-control"
							path="delayReasonDto.contEmpName" id="contName"
							disabled="${command.saveMode eq 'V'? true : false}"></form:input>
					</div>


				</div>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
					<button class="btn btn-success  submit"
						onclick="saveDelayReason(this)" id="Submit" type="button"
						name="button" value="save">
						<i class="button-input"></i>
						<spring:message code="common.sequenceconfig.save" />
					</button>
				<%-- 	Defect #80013 
					<button type="button" class="btn btn-warning"
						onclick="ResetAddForm()">
						<spring:message code="common.sequenceconfig.reset" />
					</button> --%>
					</c:if>
					<button type="back" class="btn btn-danger" onclick="backForm()">
						<spring:message code="common.sequenceconfig.back" />
					</button>

				</div>
			</form:form>
		</div>
	</div>
</div>
