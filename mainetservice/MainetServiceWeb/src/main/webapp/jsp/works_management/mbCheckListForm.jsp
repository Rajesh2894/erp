<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/works_management/measurementBook.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script>
	jQuery('#tab5').addClass('active');
	$(document).ready(function() {

		var dateField = $('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			
		});
	});
</script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="wms.MeasurementBookCheckList"
					text="Measurement Book CheckList" />
			</h2>
			<div class="additional-btn">
		   <apptags:helpDoc url="MeasurementBook.html"></apptags:helpDoc>

			</div>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>>
			</div>
			<form:form action="MeasurementBook.html" class="form-horizontal"
				name="checkList" id="checkList">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/works_management/measurementBookTab.jsp" />
				<form:hidden path="" id="saveMode" value="${command.saveMode}" />

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="form-group">

					<label for="" class="col-sm-2 control-label"><spring:message
							code="wms.VerificationDate" text="Verification Date" /> </label>
					<div class="col-sm-4">
					<div class="input-group">
						<form:input path="checkListDto.mbcInspectionDate"
							cssClass="form-control datepicker" id="checkDate" />
						<label class="input-group-addon" for="checkDate"><i
							class="fa fa-calendar"></i><span class="hide"> <spring:message
						 code="" text="icon" /></span><input type="hidden"	id="checkDate"></label>
					</div>

					</div>
					<label for="" class="col-sm-2 control-label"><spring:message
							code="wms.DrawingNo" text="Drawing No." /></label>
					<div class="col-sm-4">
						<form:input path="checkListDto.mbcDrawingNo"
							Class="form-control required-control" id="" />
					</div>
				</div>
				<div class="form-group">
					<label for="" class="col-sm-2 control-label"><spring:message
							code="work.estimate.location" text="Location" /></label>
					<div class="col-sm-4">
						<form:select path="checkListDto.locId"
							cssClass="form-control chosen-select-no-results" id="locIdSt">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.locList}" var="lookUp">
								<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="wms.PartA" text="Part A" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="a" value="0" scope="page" />
								<table class="table table-bordered table-striped" id="">
									<thead>
										<tr>
											<th width="5%"><spring:message code="ser.no"
													text="Sr. No." /><span class="mand">*</span></th>
											<th width="65%"><spring:message code="wms.Question"
													text="Question" /></th>
											<th width="17%"><spring:message code="cfc.Answer"
													text="Answer" /></th>
										</tr>
									</thead>



									<c:forEach var="classACheckList"
										items="${command.checkListDto.classACheckList}"
										varStatus="status">
										<tbody>
											<tr class="appendableClass">
												<td>${a+1}</td>
												<td><c:if test="${userSession.languageId eq '1'}">
														<span>${classACheckList.slLabel}</span>
													</c:if> <c:if test="${userSession.languageId eq '2'}">
														<span>${classACheckList.slLabelMar}</span>
													</c:if></td>

												<td><form:select
														path="checkListDto.classACheckList[${a}].slValidationText"
														class="form-control chosen-select-no-results"
														id="slValidationText${a}">
														<form:option value="">
															<spring:message code='work.management.select' />
														</form:option>
														<form:option value="Yes">
															<spring:message
																code="work.vigilance.inspection.required.yes" text="Yes" />
														</form:option>
														<form:option value="No">
															<spring:message
																code="work.vigilance.inspection.required.no" text="No" />
														</form:option>
														<form:option value="Not Applicable">
															<spring:message
																code="work.vigilance.inspection.required.not.applicable" text="Not Applicable" />
														</form:option>
													</form:select></td>
											</tr>

										</tbody>
										<c:set var="a" value="${a + 1}" scope="page" />
									</c:forEach>


								</table>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="wms.ChecklistColumnsBeamsSlabs"
										text="Checklist for Columns/Beams/Slabs" /></a>
							</h4>
						</div>

						<div id="a2" class="panel-collapse collapse">
							<div class="panel-body">
								<c:set var="b" value="0" scope="page" />

								<table class="table table-bordered table-striped" id="">
									<thead>
										<tr>
											<th width="5%"><spring:message code="ser.no"
													text="Sr. No." /><span class="mand">*</span></th>
											<th width="65%"><spring:message code="wms.Question"
													text="Question" /></th>
											<th width="17%"><spring:message code="cfc.Answer"
													text="Answer" /></th>
										</tr>
									</thead>



									<c:forEach var="checkListColumnBeams"
										items="${command.checkListDto.checkListColumnBeams}"
										varStatus="status">
										<tbody>
											<tr class="appendableClass">
												<td>${b+1}</td>
												<td><c:if test="${userSession.languageId eq '1'}">
														<span>${checkListColumnBeams.slLabel}</span>
													</c:if> <c:if test="${userSession.languageId eq '2'}">
														<span>${checkListColumnBeams.slLabelMar}</span>
													</c:if></td>

												<td><form:select
														path="checkListDto.checkListColumnBeams[${b}].slValidationText"
														class="form-control chosen-select-no-results"
														id="slValidationText${b}">
														<form:option value="">
															<spring:message code='work.management.select' />
														</form:option>
														<form:option value="Yes">
															<spring:message
																code="work.vigilance.inspection.required.yes" text="Yes" />
														</form:option>
														<form:option value="No">
															<spring:message
																code="work.vigilance.inspection.required.no" text="No" />
														</form:option>
														<form:option value="Not Applicable">
															<spring:message
																code="work.vigilance.inspection.required.not.applicable" text="Not Applicable" />
														</form:option>
													</form:select></td>
											</tr>
										</tbody>
										<c:set var="b" value="${b + 1}" scope="page" />
									</c:forEach>

								</table>
							</div>
						</div>
					</div>
					<div class="panel panel-default" id="selectedItems">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a3" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="wms.ChecklistBrickWork" text="Checklist for Brick Work" /></a>
							</h4>
						</div>

						<div id="a3" class="panel-collapse collapse">
							<div class="panel-body">
								<c:set var="c" value="0" scope="page" />

								<table class="table table-bordered table-striped" id="">
									<thead>
										<tr>
											<th width="5%"><spring:message code="ser.no"
													text="Sr. No." /><span class="mand">*</span></th>
											<th width="65%"><spring:message code="wms.Question"
													text="Question" /></th>
											<th width="17%"><spring:message code="cfc.Answer"
													text="Answer" /></th>
										</tr>
									</thead>



									<c:forEach var="checkListBrickWork"
										items="${command.checkListDto.checkListBrickWork}"
										varStatus="status">
										<tbody>
											<tr class="appendableClass">
												<td>${c+1}</td>
												<td><c:if test="${userSession.languageId eq '1'}">
														<span>${checkListBrickWork.slLabel}</span>
													</c:if> <c:if test="${userSession.languageId eq '2'}">
														<span>${checkListBrickWork.slLabelMar}</span>
													</c:if></td>

												<td><form:select
														path="checkListDto.checkListBrickWork[${c}].slValidationText"
														class="form-control chosen-select-no-results"
														id="slValidationText${c}">
														<form:option value="">
															<spring:message code='work.management.select' />
														</form:option>
														<form:option value="Yes">
															<spring:message
																code="work.vigilance.inspection.required.yes" text="Yes" />
														</form:option>
														<form:option value="No">
															<spring:message
																code="work.vigilance.inspection.required.no" text="No" />
														</form:option>
														<form:option value="Not Applicable">
															<spring:message
																code="work.vigilance.inspection.required.not.applicable" text="Not Applicable" />
														</form:option>
													</form:select></td>
											</tr>
										</tbody>
										<c:set var="c" value="${c + 1}" scope="page" />
									</c:forEach>

								</table>


							</div>
						</div>
					</div>



					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a4" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a4"> <spring:message
										code="wms.ChecklistPlastering" text="Checklist for Plastering" /></a>
							</h4>
						</div>

						<div id="a4" class="panel-collapse collapse">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page" />

								<table class="table table-bordered table-striped" id="">
									<thead>
										<tr>
											<th width="5%"><spring:message code="ser.no"
													text="Sr. No." /><span class="mand">*</span></th>
											<th width="65%"><spring:message code="wms.Question"
													text="Question" /></th>
											<th width="17%"><spring:message code="cfc.Answer"
													text="Answer" /></th>
										</tr>
									</thead>



									<c:forEach var="checkListPlastering"
										items="${command.checkListDto.checkListPlastering}"
										varStatus="status">
										<tbody>
											<tr class="appendableClass">
												<td>${d+1}</td>
												<td><c:if test="${userSession.languageId eq '1'}">
														<span>${checkListPlastering.slLabel}</span>
													</c:if> <c:if test="${userSession.languageId eq '2'}">
														<span>${checkListPlastering.slLabelMar}</span>
													</c:if></td>

												<td><form:select
														path="checkListDto.checkListPlastering[${d}].slValidationText"
														class="form-control chosen-select-no-results"
														id="slValidationText${d}">
														<form:option value="">
															<spring:message code='work.management.select' />
														</form:option>
														<form:option value="Yes">
															<spring:message
																code="work.vigilance.inspection.required.yes" text="Yes" />
														</form:option>
														<form:option value="No">
															<spring:message
																code="work.vigilance.inspection.required.no" text="No" />
														</form:option>
														<form:option value="Not Applicable">
															<spring:message
																code="work.vigilance.inspection.required.not.applicable" text="Not Applicable" />
														</form:option>
													</form:select></td>
											</tr>
										</tbody>
										<c:set var="d" value="${d + 1}" scope="page" />
									</c:forEach>

								</table>
							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a5" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a5"> <spring:message
										code="wms.ChecklistWaterSupplyLines"
										text="Checklist for Water Supply Lines" /></a>
							</h4>
						</div>

						<div id="a5" class="panel-collapse collapse">
							<div class="panel-body">
								<c:set var="e" value="0" scope="page" />

								<table class="table table-bordered table-striped" id="">
									<thead>
										<tr>
											<th width="5%"><spring:message code="ser.no"
													text="Sr. No." /><span class="mand">*</span></th>
											<th width="65%"><spring:message code="wms.Question"
													text="Question" /></th>
											<th width="17%"><spring:message code="cfc.Answer"
													text="Answer" /></th>
										</tr>
									</thead>



									<c:forEach var="checkListWaterSupply"
										items="${command.checkListDto.checkListWaterSupply}"
										varStatus="status">
										<tbody>
											<tr class="appendableClass">
												<td>${e+1}</td>
												<td><c:if test="${userSession.languageId eq '1'}">
														<span>${checkListWaterSupply.slLabel}</span>
													</c:if> <c:if test="${userSession.languageId eq '2'}">
														<span>${checkListWaterSupply.slLabelMar}</span>
													</c:if></td>

												<td><form:select
														path="checkListDto.checkListWaterSupply[${e}].slValidationText"
														class="form-control chosen-select-no-results"
														id="slValidationText${e}">
														<form:option value="">
															<spring:message code='work.management.select' />
														</form:option>
														<form:option value="Yes">
															<spring:message
																code="work.vigilance.inspection.required.yes" text="Yes" />
														</form:option>
														<form:option value="No">
															<spring:message
																code="work.vigilance.inspection.required.no" text="No" />
														</form:option>
														<form:option value="Not Applicable">
															<spring:message
																code="work.vigilance.inspection.required.not.applicable" text="Not Applicable" />
														</form:option>
													</form:select></td>
											</tr>
										</tbody>
										<c:set var="e" value="${e + 1}" scope="page" />
									</c:forEach>

								</table>
							</div>
						</div>
					</div>

				</div>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode ne 'V' }">
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="saveCheckListData(this);">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="work.management.SaveContinue" text="Save" />
						</button>

						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" onclick="openMbNonSorForm();"
							id="backButton">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="" />
						</button>

					</c:if>
					<c:if test="${command.saveMode eq 'V' }">
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" onclick="openMbNonSorForm();"
							id="backButton">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="" />
						</button>
					</c:if>
				</div>
			</form:form>
		</div>

	</div>
</div>

