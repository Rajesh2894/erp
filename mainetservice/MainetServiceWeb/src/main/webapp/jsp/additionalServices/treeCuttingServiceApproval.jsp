<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/additionalServices/treeCuttingApproval.js"></script>

<%-- <apptags:breadcrumb></apptags:breadcrumb> --%>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="CFC.service.approval" text="Tree Cutting / Trimming Approval" />
				<apptags:helpDoc url="TreeCuttingTrimmingApproval.html"></apptags:helpDoc>
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
					class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
				</a>
			</div>
		</div>
		<div class="widget-content padding">

			<form:form action="TreeCuttingTrimmingApproval.html"
				id="frmnocbuildpermission" method="POST" commandName="command"
				class="form-horizontal form">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<h4>
					<spring:message code="CFC.applicant.info" text="Applicant Information " />
				</h4>
				<div class="form-group">
					<apptags:date fieldclass="datepicker"
						labelCode="NOCBuildingPermission.date" readonly="true"
						datePath="cfcApplicationMst.lmoddate" isMandatory="true"
						cssClass="custDate mandColorClass date">
					</apptags:date>

					<label class="col-sm-2 control-label required-control"
						for="applicantTitle"><spring:message
							code="NHP.title" text="Title" /></label>
					<c:set var="baseLookupCode" value="TTL" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="cfcApplicationMst.apmTitle" disabled="true"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						isMandatory="true" />

				</div>

				<div class="form-group">


					<apptags:input labelCode="NHP.first.name"
						isDisabled="true"
						cssClass="hasCharacter mandColorClass hasNoSpace"
						path="cfcApplicationMst.apmFname" isMandatory="true"
						maxlegnth="100">
					</apptags:input>

					<apptags:input labelCode="NHP.middle.name"
						isDisabled="true"
						cssClass="hasCharacter mandColorClass hasNoSpace"
						path="cfcApplicationMst.apmMname" maxlegnth="100"></apptags:input>

				</div>

				<div class="form-group">
					<apptags:input labelCode="NHP.last.name"
						isDisabled="true"
						cssClass="hasCharacter mandColorClass hasNoSpace"
						path="cfcApplicationMst.apmLname" isMandatory="true"
						maxlegnth="100"></apptags:input>


					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.gender" text="sex" /></label>
					<c:set var="baseLookupCode" value="GEN" />
					<div class="col-sm-4">
						<form:select path="cfcApplicationMst.apmSex" class="form-control"
							id="gender" disabled="true">
							<c:set var="baseLookupCode" value="GEN" />
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpCode}"
									code="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<%-- <apptags:lookupField items="${command.getLevelData('GEN')}"
						path="cfcApplicationMst.apmSex" disabled="true"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" /> --%>
				</div>

				<div class="form-group">

					<%-- <label class="col-sm-2 control-label required-control"><spring:message
							code="NOCBuildingPermission.applicationType"
							text="Applicant Type" /></label>
					<c:set var="baseLookupCode" value="APT" />
					<apptags:lookupField items="${command.getLevelData('APT')}"
						path="cfcApplicationMst.ccdApmType"
						cssClass="form-control chosen-select-no-results" disabled="true"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" /> --%>
					<%-- <label class="control-label col-sm-2  required-control"
						for="text-1">Department Name </label>
					<div class="col-sm-4">
						<form:select path="nocBuildingPermissionDto.deptId"
							class="form-control mandColorClass chosen-select-no-results"
							label="Select Department Name" disabled="true" id="deptId"
							onchange="refreshServiceData(this,'NOCForBuildingPermission.html','refreshServiceData')">
							<!-- Here the option is being loaded in the drop down list using forEach loop  -->
							<form:option value="" selected="true">Select Department Name</form:option>
							<c:forEach items="${command.deptList}" var="dept">
								<form:option value="${dept.dpDeptid }"
									code="${dept.dpDeptcode }">${dept.dpDeptdesc }</form:option>
							</c:forEach>

						</form:select>
					</div> --%>
					<!-- onchange ="getServiceList(this,'rtsService.html','getServiceList')" -->
				</div>

				<%-- <div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code='master.serviceName' text="service name" /><span
						class="mand">*</span></label>
					<div class="col-sm-4">
						<form:select path="nocBuildingPermissionDto.serviceId"
							class="form-control mandColorClass chosen-select-no-results"
							disabled="true" id="serviceId">

							<form:option value="" selected="true">Select Service Name</form:option>
							<c:forEach items="${command.serviceMstList}" var="ser">
								<form:option value="${ser.smServiceId }"
									code="${ser.smServiceId }">${ser.smServiceName }</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div> --%>

				<%-- <h4>
					<spring:message text="Service Information " />
				</h4>

				<div class="form-group">
					<apptags:input labelCode="NOCBuildingPermission.buildingPermissionAppNo"
						cssClass="hasCharacter mandColorClass hasNoSpace"
						isDisabled="true"
						path="cuttingInfoDto.apmApplicationId"
						isMandatory="true" maxlegnth="100"></apptags:input>

					
				</div>
 --%>
 				<h4 class="margin-top-0">
					<spring:message code="CFC.applicant.info" text="Applicant Information" />
				</h4>
 				<div class="form-group">


					<apptags:input labelCode="TCP.applicant.name"
						isDisabled="true"
						cssClass="hasCharacter mandColorClass hasNoSpace"
						path="cuttingInfoDto.appName" isMandatory="true"
						maxlegnth="100">
					</apptags:input>

					<apptags:input labelCode="TCP.mobile"
						isDisabled="true"
						cssClass="mandColorClass hasNoSpace"
						path="cuttingInfoDto.mobNumber" maxlegnth="100"></apptags:input>

				</div>
				
				<div class="form-group">


					<apptags:input labelCode="NHP.address.emailId"
						isDisabled="true"
						cssClass="hasCharacter mandColorClass hasNoSpace"
						path="cuttingInfoDto.emailId" isMandatory="true"
						maxlegnth="100">
					</apptags:input>

					<apptags:input labelCode="TCP.address"
						isDisabled="true"
						cssClass="hasCharacter mandColorClass hasNoSpace"
						path="cuttingInfoDto.address" maxlegnth="100"></apptags:input>

				</div>
				<h4 class="margin-top-0">
					<spring:message code="TCP.tree.info" text="Information About Tree" />
				</h4>
				
				<div class="form-group">


					<apptags:input labelCode="TCP.tree.desc.name"
						isDisabled="true"
						cssClass="hasCharacter mandColorClass hasNoSpace"
						path="cuttingInfoDto.treeDesc" isMandatory="true"
						maxlegnth="100">
					</apptags:input>

					<apptags:input labelCode="TCP.tree.count"
						isDisabled="true"
						cssClass=" mandColorClass hasNoSpace"
						path="cuttingInfoDto.count" maxlegnth="100"></apptags:input>

				</div>
				<div class="form-group">


					<apptags:input labelCode="TCP.reason"
						isDisabled="true"
						cssClass="hasCharacter mandColorClass hasNoSpace"
						path="cuttingInfoDto.reason" isMandatory="true"
						maxlegnth="100">
					</apptags:input>

					<apptags:input labelCode="TCP.tree.location"
						isDisabled="true"
						cssClass=" mandColorClass hasNoSpace"
						path="cuttingInfoDto.location" maxlegnth="100"></apptags:input>

				</div>
 				

				<c:if test="${not empty command.fetchDocumentList}">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class="" href="#deathRegCor-1"> <spring:message
										code="TbDeathregDTO.attacheddoc" text="Attached Documents" />
								</a>
							</h4>
						</div>


						<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocs">
									<tr>
										<th><spring:message code="scheme.document.name" text="" /></th>
										<th><spring:message code="scheme.view.document" text="" /></th>
									</tr>
									<c:forEach items="${command.fetchDocumentList}" var="lookUp">
										<tr>
											<td align="center">${lookUp.attFname}</td>
											<td align="center"><apptags:filedownload
													filename="${lookUp.attFname}" filePath="${lookUp.attPath}"
													actionUrl="ChecklistVerification.html?Download">
												</apptags:filedownload></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</c:if>

				<div class="form-group">
					<c:choose>
						<c:when test="${CheckFinalApp eq true}">
							<apptags:radio radioLabel="Approve,Reject"
								radioValue="APPROVED,REJECTED" isMandatory="true"
								labelCode="CFC.status" path="cuttingInfoDto.birthRegstatus"
								defaultCheckedValue="APPROVED">
							</apptags:radio>
							<br />
						</c:when>
						<c:otherwise>
							<apptags:radio radioLabel="Approve" radioValue="APPROVED"
								isMandatory="true" labelCode="CFC.status"
								path="cuttingInfoDto.birthRegstatus"
								defaultCheckedValue="APPROVED">
							</apptags:radio>
							<br />
						</c:otherwise>
					</c:choose>
					<apptags:textArea labelCode="CFC.remark" isMandatory="true"
						path="cuttingInfoDto.birthRegremark"
						cssClass="hasNumClass form-control" maxlegnth="100" />
				</div>

				<c:if test="${CheckFinalApp eq true}">
					<div class="form-group" id="reload">
						<label class="col-sm-2 control-label" for="ExcelFileUpload"><spring:message
								code="NOCBuildingPermission.uploadcertificate"
								text="NOC Certificate" /><i class="text-red-1">*</i></label>
						<c:set var="count" value="0" scope="page"></c:set>
						<div class="col-sm-2 text-left">
							<apptags:formField fieldType="7"
								fieldPath="attachments[${count}].uploadedDocumentPath"
								currentCount="${count}" folderName="${count}"
								fileSize="CHECK_COMMOM_MAX_SIZE" showFileNameHTMLId="true"
								isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="ALL_UPLOAD_VALID_EXTENSION" cssClass="clear">
							</apptags:formField>

						</div>

					</div>
				</c:if>

				<div class="text-center padding-top-10">

					<button type="button" class="btn btn-green-3" title="Submit"
						onclick="saveApprovalData(this)">
						<spring:message
							code="CFC.save" text="Save" />
						<i class="fa padding-left-4" aria-hidden="true"></i>
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>
