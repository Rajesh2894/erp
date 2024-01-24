<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/objection/objectionSummary.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<b><spring:message code="obj.objectionsummary"></spring:message></b>
			</h2>
			<apptags:helpDoc url="ObjectionDetails.html"></apptags:helpDoc>
			<div class="additional-btn">
				
			</div>
		</div>

		<div class="widget-content padding">

			<form:form method="POST" action="ObjectionDetails.html"
				class="form-horizontal" id="rtiObjectionSummaryForm"
				name="rtiObjectionSummaryForm">
				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">



							<div class="form-group">
								
							<label class="col-sm-2 control-label required-control" for="objectionDeptId"><spring:message code="obj.deptName" text="Department"/></label>
								<div class="col-sm-4">
								<form:select path=""
											onchange="getObjectionServiceByDept()" class="form-control mandColorClass chosen-select-no-results" id="objectionDeptId" data-rule-required="true">
												<form:option value="">
													<spring:message code="obj.selDepartment" text="Select Department"/>
												</form:option>
												<c:forEach items="${command.departments}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}" 
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
									</form:select>
								
								</div>
								
								<%-- 	<apptags:select labelCode="obj.objDept"
										items="${command.departments}" path=""
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showAll="false" isLookUpItem="true" changeHandler="getObjectionServiceByDept()"></apptags:select> --%>

									<label class="col-sm-2 control-label "
										for="objtype"><spring:message code="obj.serviceName" /></label>
									<%-- <c:set var="baseLookupCode" value="OBJ" /> --%>
									<apptags:lookupField
										items="${command.serviceList}"
										path="objectionDetailsDto.serviceId" cssClass="form-control chosen-select-no-results"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="obj.title" />
								</div>
								
								<div id="objectionReferenceNumber">
								</div>
															
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label " 
							for="refNo"><!-- Property No/RTI No/Trade License No --><spring:message code="obj.ReferenceNo"/></label>
						<div class="col-sm-4">
							<form:input type="dept" class="form-control mandColorClass" id="refNo"
								path="objectionDetailsDto.objectionReferenceNumber"
								data-rule-required="true"></form:input>
						</div>
						
						
						<c:set var="baseLookupCode" value="OBJ" />
						<apptags:select labelCode="obj.appealType"
									items="${command.getLevelData(baseLookupCode)}"
									path="objectionDetailsDto.objectionOn"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="false" showAll="false" isLookUpItem="true"></apptags:select>
									
						
					</div>
					<div class="form-group">
							<label class="col-sm-10 text-red"><spring:message code="obj.note"/></label>
					</div>
					
					<div class="text-center clear padding-10">
						<input type="button" class="button-input btn btn-blue-2"
							id="objDetailSearch" value="<spring:message code="book.bt.search" />"
							onclick="" />

						<button type="Reset" class="btn btn-warning" id="resetform"
							onclick="resetObjectionSummaryForm(this)">
							<spring:message code="obj.reset" />
						</button>

						<input type="button" class="btn btn-success btn-submit" id="add"
							value="<spring:message code="book.bt.add" />"
							onclick="addObjectionEntryForm(this);" />

					</div>
				</div>
				
				
				<div class="table-responsive clear">	
					<table class="table table-striped table-bordered" id="datatables">
					<thead>
					<tr>
					<th width="15%" align="center"><spring:message code="obj.ObjectionAppeal" text="Objection/Appeal No."/></th>
					<th width="15%" align="center"><spring:message code="obj.ObjectionAppealDate" text="Objection/Appeal Date"/></th>
					<th width="20%" align="center"><spring:message code="obj.ObjectionAppellant" text="Appellant Name"/></th>
					<th width="15%" align="center"><spring:message code="obj.Status" text="Status"/></th>
					<th width="15%" align="center"><spring:message code="obj.AppealType" text="Objection Appeal Type"/></th>
					<th width="15%" align="center"><spring:message code="obj.hearingAction" text="Hearing Action"/></th>
					</tr>
					</thead>
					<tbody>

					</tbody>
					</table>
				</div>

			</form:form>
		</div>
	</div>
</div>

