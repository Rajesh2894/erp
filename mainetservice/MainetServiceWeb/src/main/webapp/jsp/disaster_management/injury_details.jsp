<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/disaster_management/injusrydetails.js"></script>

<style>
.error-div ul > li {
	position: relative;
	padding: 0 0 0 1.4rem;
}
.error-div ul > li::before {
	content: "\f06a";
	font-family: 'FontAwesome';
	position: absolute;
	left: 0;
}
</style>


<div class="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="widget">
		<div class="widget-header">
			<h2>
				<%-- Defect #157993 --%>
				<strong><spring:message
						code="disaster.call.no"
						text="Call No :" /><span class="margin-left-5">${command.complainRegisterDTO.complainNo}</span></strong>
			</h2>
		</div>
		<br>
		<div class="widget-content padding">

			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<div id="errorId"></div>
			</div>


			<form:form action="InjuryDetails.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmInjuryDetails" id="frmInjuryDetails">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- Table Grid Start -->

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="callScrutinyDataTable">
						<thead>
							<tr>
								<th width="5%" align="center"><spring:message
										code="audit.mgmt.srno" text="Sr.No" /></th>
								<th width="10%" align="center"><spring:message
										code="complainRegisterDTO.tbDepartment" text="Department Name" /></th>
								<th width="10%" align="center"><spring:message
										code="complainRegisterDTO.empName" text="Employee Name" /></th>
								<th width="10%" align="center"><spring:message
										code="complainRegisterDTO.complainStatus"
										text="Complain Status" /></th>
								<th width="10%" align="center"><spring:message
										code="complainRegisterDTO.complaintRemark"
										text="Complain Remark" /></th>

								<th width="10%" align="center"><spring:message
										code="complainRegisterDTO.callAttendDate"
										text="Call Attend Date" /></th>
								<th width="10%" align="center"><spring:message
										code="complainRegisterDTO.callAttendTime"
										text="Call Attend Time" /></th>
								<th width="10%" align="center"><spring:message
										code="complainRegisterDTO.callAttendEmployee"
										text="Call Attend Employee" /></th>
								<th width="10%" align="center"><spring:message
										code="complainRegisterDTO.reasonForDelay"
										text="Reason for Delay" /></th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${command.call}" var="compDto" varStatus="item">

								<tr>
									<td class="text-center">${item.count}</td>
									<td class="text-center">${compDto.tbDepartment}</td>
									<td class="text-center">${compDto.empName}</td>
									<td class="text-center">${compDto.complainStatus}</td>
									<td class="text-center">${compDto.complaintRemark}</td>
									<td class="text-center"><fmt:formatDate value="${compDto.callAttendDate}" pattern="dd/MM/yyyy" /></td>
									<td class="text-center">${compDto.callAttendTime}</td>
									<td class="text-center">${compDto.callAttendEmployee}</td>
									<td class="text-center">${compDto.reasonForDelay}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<%-- <div class="widget-content padding">

			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>


			<form:form action="InjuryDetails.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmInjuryDetails" id="frmInjuryDetails">
				<jsp:include page="/jsp/tiles/validationerror.jsp" /> --%>

				<!-- 				<div class="warning-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
						<li> <i class="fa fa-exclamation-circle"></i> </li>
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div> -->

				<div
					class="warning-div error-div aaaaaalert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<apptags:input labelCode="ComplainRegisterDTO.noOfInjuryMale"
						onChange="totalInjurCount(this)"
						path="complainRegisterDTO.noOfInjuryMale"
						cssClass="hasNumber form-control" maxlegnth="20"></apptags:input>

					<apptags:input labelCode="ComplainRegisterDTO.noOfInjuryFemale"
						onChange="totalInjurCount(this)"
						path="complainRegisterDTO.noOfInjuryFemale"
						cssClass="hasNumber form-control" maxlegnth="50">
					</apptags:input>
				</div>

				<div class="form-group">
					<apptags:input labelCode="ComplainRegisterDTO.noOfInjuryChild"
						onChange="totalInjurCount(this)"
						path="complainRegisterDTO.noOfInjuryChild"
						cssClass="hasNumber form-control" maxlegnth="20"></apptags:input>

					<apptags:input labelCode="ComplainRegisterDTO.totalInjured"
						isDisabled="true" path="complainRegisterDTO.totalInjured"
						cssClass="hasNumber form-control" maxlegnth="50"></apptags:input>
				</div>


				<div class="form-group">
					<apptags:input labelCode="ComplainRegisterDTO.noOfDeathMale"
						onChange="totalDeathCount(this)"
						path="complainRegisterDTO.noOfDeathMale"
						cssClass="hasNumber form-control" maxlegnth="20"></apptags:input>

					<apptags:input labelCode="ComplainRegisterDTO.noOfDeathFemale"
						onChange="totalDeathCount(this)"
						path="complainRegisterDTO.noOfDeathFemale"
						cssClass="hasNumber form-control" maxlegnth="50"></apptags:input>
				</div>

				<div class="form-group">
					<apptags:input labelCode="ComplainRegisterDTO.noOfDeathChild"
						onChange="totalDeathCount(this)"
						path="complainRegisterDTO.noOfDeathChild"
						cssClass="hasNumber form-control" maxlegnth="20"></apptags:input>

					<apptags:input labelCode="ComplainRegisterDTO.totalDeath"
						isDisabled="true" path="complainRegisterDTO.totalDeath"
						cssClass="hasNumber form-control" maxlegnth="50"></apptags:input>

				</div>

				<div class="form-group">
					<apptags:textArea labelCode="ComplainRegisterDTO.noOfVehDamaged"
						path="complainRegisterDTO.noOfVehDamaged" isMandatory=""
						cssClass="hasNumClass form-control" maxlegnth="500">
					</apptags:textArea>

					<apptags:textArea labelCode="ComplainRegisterDTO.remark"
						path="complainRegisterDTO.remark" isMandatory="true"
						cssClass="hasNumClass form-control" maxlegnth="800">
					</apptags:textArea>
					</div>
					
					 <div class="form-group">

					<label class="control-label col-sm-2"><spring:message
							code="complainRegisterDTO.upload.docs" text="Upload Documents" /></label>
					<div class="col-sm-4">
						<apptags:formField fieldType="7" fieldPath="" labelCode=""
							currentCount="0" showFileNameHTMLId="true" folderName="0"
							fileSize="BND_COMMOM_MAX_SIZE" maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="CHECK_LIST_VALIDATION_EXTENSION" />
						<%-- Defect #152091 --%>
						<small class="text-blue-2">
							<spring:message code="complainRegisterDTO.file.upload.tooltip"
								text="(Upload File upto 5MB and Only pdf,doc,docx,jpeg,jpg,png,gif,bmp extension(s) file(s) are allowed.)"/>
						</small>
					</div>


				</div>

				<form:hidden path="complainRegisterDTO.statusVariable"
					id="statusVariable" />

				<div class="text-center clear padding-10">
					<input type="button" value="<spring:message code="bt.save"/>"
						title='<spring:message code="bt.save"/>'
						onclick="confirmToProceed(this)" class="btn btn-success" id="Save">

					<button type="button" onclick="resetInjury(this);"
						class="btn btn-warning" data-toggle="tooltip"
						data-original-title="">
						<spring:message code="bt.clear" text="Reset"></spring:message>
					</button>

					<input type="button"
						onclick="window.location.href='InjuryDetails.html'"
						title='<spring:message code="bt.backBtn" text="Back" />'
						class="btn btn-danger  hidden-print" value="<spring:message code="bt.backBtn" text="Back" />">

				</div>

			</form:form>

		</div>
	</div>
</div>


