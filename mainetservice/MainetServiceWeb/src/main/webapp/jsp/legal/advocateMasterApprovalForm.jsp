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
<script src="js/mainet/validation.js"></script>
<script src="js/legal/advocateMaster.js"></script>
<!-- Start Content here -->
<%
	response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start info box -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="lgl.advocatemaster"
						text="Advocate Master" /></strong>
			</h2>
		</div>
		<apptags:helpDoc url="AdvocateMaster.html" />

		<div class="widget-content padding">
			<form:form action="AdvocateMaster.html" name="AdvocateMaster"
				id="AdvocateMasterForm" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="form-group">

					<div class="col-sm-12">

						<apptags:input labelCode="lgl.advocate.firstName"
							path="advocateMasterDTO.advFirstNm"
							cssClass="hasNameClass hasNoSpace" isDisabled="true"></apptags:input>

						<apptags:input labelCode="lgl.advocate.middleName"
							path="advocateMasterDTO.advMiddleNm"
							cssClass="hasNameClass form-control hasNoSpace" isDisabled="true"></apptags:input>



					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-12">
						<apptags:input labelCode="lgl.advocate.lastName"
							path="advocateMasterDTO.advLastNm"
							cssClass="hasNameClass hasNoSpace" isDisabled="true"></apptags:input>

						<apptags:input labelCode="lgl.barcouncil.no"
							path="advocateMasterDTO.adv_barCouncilNo" isMandatory="false"
							cssClass=" form-control hasNoSpace" maxlegnth="16"
							isDisabled="true"></apptags:input>
					</div>

				</div>

				<div class="form-group">

					<div class="col-sm-12">

						<label class="control-label col-sm-2 required-control"
							for="Census"><spring:message code="lgl.gender"
								text="Gender" /></label>
						<c:set var="baseLookupCode" value="GEN" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="advocateMasterDTO.advGen" 
							cssClass=" form-control mandColorClass hasNameClass" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true" />

						<apptags:date fieldclass="datepicker" labelCode="lgl.dateOfBirth"
							datePath="advocateMasterDTO.advDob" readonly="true"
							cssClass="custDate mandColorClass">
						</apptags:date>

					</div>
				</div>

				<div class="form-group">

					<div class="col-sm-12">
						<apptags:input labelCode="lgl.phoneNumber" isDisabled="true"
							path="advocateMasterDTO.advMobile"
							cssClass="hasMobileNo mandColorClass "></apptags:input>

						<apptags:input labelCode="lgl.emailid" isDisabled="true"
							path="advocateMasterDTO.advEmail" cssClass="hasemailclass">
						</apptags:input>

					</div>
				</div>

				<div class="form-group">

					<div class="col-sm-12">

						<label class="col-sm-2 control-label" for="pannumber"> <spring:message
								code="lgl.panNumber" /></label>
						<div class="col-sm-4">
							<form:input type="text" name="PAN Number" id="advPanno"
								path="advocateMasterDTO.advPanno"
								class="form-control text-uppercase hasNoSpace" maxLength="10"
								onchange="fnValidatePAN(this)" disabled="true" />
						</div>

						<!-- UID : Aadhar number -->
						<apptags:input labelCode="lgl.aadharNumber"
							path="advocateMasterDTO.advUid" isDisabled="true"
							cssClass="hasAadharNo hasNoSpace">
						</apptags:input>

					</div>
				</div>

				<%-- <div class="form-group">

					<div class="col-sm-12">

						<apptags:date fieldclass="datepicker"
							labelCode="lgl.appointment.startDate"
							datePath="advocateMasterDTO.advAppfromdate"
							cssClass="mandColorClass" readonly="true"></apptags:date>

						<apptags:date fieldclass="datepicker"
							labelCode="lgl.appointment.endDate"
							datePath="advocateMasterDTO.advApptodate" readonly="true"
							cssClass="hasNameClass mandColorClass">
						</apptags:date>

					</div>
				</div> --%>

				<div class="form-group">
					<div class="col-sm-12">
						<apptags:textArea labelCode="lgl.advocate.advocateAddress"
							path="advocateMasterDTO.advAddress" isDisabled="true"></apptags:textArea>

						<apptags:textArea labelCode="lgl.advocate.advocateOfficeAddress"
							path="advocateMasterDTO.advOfficeAddress" isDisabled="true"></apptags:textArea>


					</div>

				</div>

				<div class="form-group">
					<div class="col-sm-12">
						<apptags:input labelCode="lgl.advocate.experience"
							path="advocateMasterDTO.advExperience" isDisabled="true"
							cssClass="hasDecimal hasNoSpace"></apptags:input>

						<apptags:input labelCode="lgl.advChamberNo"
							path="advocateMasterDTO.advChamberNo" isDisabled="true"
							cssClass="hasDecimal hasNoSpace"></apptags:input>

					</div>
				</div>
				
		<%-- 		<div class="form-group">

					<div class="col-sm-12">

						<label for="advFeeType" class="col-sm-2 control-label"><spring:message
								code="lgl.advocate.fee.type" text="Fee Type" /></label>
						<apptags:lookupField items="${command.getLevelData('FET')}"
							path="advocateMasterDTO.advFeeType"
							cssClass="form-control mandColorClass hasNameClass"
							selectOptionLabelCode="Select" hasId="true" isMandatory="true"
							disabled="true" />

						<apptags:input labelCode="lgl.advocate.fee.amount"
							path="advocateMasterDTO.advFeeAmt" cssClass="hasAmount"
							isDisabled="true"></apptags:input>
					</div>
				</div> --%>

				<h4 class="panel-title">
					<a data-target="#a1" data-toggle="collapse" class="collapsed"
						data-parent="#accordion_single_collapse" href="#a1"><spring:message
							code="lgl.educationDetails" text="Education Details" /></a>
				</h4>

				<div id="a1" class="panel-collapse collapse in">
					<div class="panel-body">
						<c:set var="j" value="0" scope="page"></c:set>
						<table
							class="table table-bordered  table-condensed margin-bottom-10"
							id="educationDetails" summary="Education Details">
							<thead>
								<tr>
									<th width="5"><spring:message code="lgl.srno"
											text="Sr. No." /></th>
									<th width="15"><spring:message
											code="lgl.adv.qualificationCourse"
											text="Qualification Course" /></th>

									<th width="15"><spring:message
											code="lgl.adv.instituteState" text="Institute State" /></th>

									<th width="15"><spring:message
											code="lgl.adv.boardUniversity" text="Board University" /></th>

									<th width="10"><spring:message code="lgl.adv.result"
											text="Result" /></th>

									<th width="10"><spring:message code="lgl.adv.passingYear"
											text="Passing Year" /></th>

									<th width="10"><spring:message code="lgl.adv.percentage"
											text="Percentage" /></th>


								</tr>
							</thead>

							<tbody>
								<c:forEach
									items="${command.advocateMasterDTO.advEducationDetailDTOList}"
									var="data" varStatus="index">
									<tr class="educationDetailsClass">
										<td class="text-center">${index.count}</td>
										<td align="center">${data.qualificationCourse}</td>
										<td align="center">${data.instituteState}</td>
										<td align="center">${data.boardUniversity}</td>
										<td align="center">${data.result}</td>
										<td align="center">${data.passingYear}</td>
										<td align="center">${data.percentage}</td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
					</div>
				</div>


				<div class="panel-group accordion-toggle" id="ddvocateMasterDetail">
					<div class="panel panel-default">
						<div class="panel-heading">
	
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<div class="widget-content padding">
										<apptags:CheckerAction hideForward="true" hideUpload="true" hideSendback="true"></apptags:CheckerAction>
									</div>
									<div class="text-center margin-top-10">

										<input type="button"
											value="<spring:message code="lgl.save" text="Save" />"
											onclick="return saveAdvocateDecisionForm(this);"
											class="css_btn btn btn-success">
										<apptags:backButton url="AdminHome.html" />
									</div>


								</div>
							</div>

						</div>
					</div>
					</div>



			</form:form>
		</div>

	</div>
</div>

