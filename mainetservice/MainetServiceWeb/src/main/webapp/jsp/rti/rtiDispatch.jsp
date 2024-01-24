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
<script type="text/javascript" src="js/rti/rtiDispatch.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/script-library.js"></script>
<script>
	$(function() {

		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
		/* maxDate : '0' */
		});
	});
</script>
<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<b> <spring:message code="rti.applicationFormStatus"></spring:message>
				</b>
			</h2>
		</div>

		<apptags:helpDoc url="RtiApplicationDetailForm.html"></apptags:helpDoc>

		<div class="widget-content padding">

			<form:form method="POST" action="RtiDispatch.html"
				commandName="command" class="form-horizontal" id="rtiDispatchForm"
				name="rtiDispatchForm">
				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a1"> <spring:message
									code="rti.applicantDetails" /></a>
						</h4>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<table
									class="table table-bordered  table-condensed margin-bottom-10">
									<tbody>
										<tr>
											<th colspan="2" style="text-align: left;"><spring:message
													code="rti.applicantDetails" /></th>
										</tr>
										<tr>
											<td width="116"><spring:message code="rti.applicationNO" /></td>
											<td width="373">${command.reqDTO.apmApplicationId}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.date" /></td>
											<td>${command.reqDTO.applicationDate}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.typeApp" /></td>
											<td>${command.reqDTO.applicant}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.applicationName" /></td>
											<td>${command.cfcEntity.apmFname}
												${command.cfcEntity.apmMname} ${command.cfcEntity.apmLname}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.title" /></td>
											<td>${command.reqDTO.title}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.firstName" /></td>
											<td>${command.cfcEntity.apmFname}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.middleName" /></td>
											<td>${command.cfcEntity.apmMname}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.lastName" /></td>
											<td>${command.cfcEntity.apmLname}</td>
										</tr>
										<tr>
											<td><spring:message code="applicantinfo.label.gender" /></td>
											<td>${command.reqDTO.gen}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.mobile1" /></td>
											<td>${command.cfcAddressEntity.apaMobilno}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.location" /></td>
											<td>${command.reqDTO.locationName}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.address" /></td>
											<td>${command.cfcAddressEntity.apaAreanm}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.pinCode" /></td>
											<td>${command.cfcAddressEntity.apaPincode}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.uidNo" /></td>
											<td>${command.cfcEntity.apmUID}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.type" /></td>
											<td>${command.reqDTO.isBPL}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.bplNo" /></td>
											<td>${command.cfcEntity.apmBplNo}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.yearofissue" /></td>
											<td>${command.cfcEntity.apmBplYearIssue}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.issueauthority" /></td>
											<td>${command.cfcEntity.apmBplIssueAuthority}</td>
										</tr>
										<c:if test="${not empty command.mediaTypeList}">
										<tr>
											<td><spring:message code="rti.chargemediaType" /></td>
											<td>${command.mediaTypeList}</td>
										</tr>
										</c:if>
										<c:if
											test="${not empty command.cfcAddressEntity.apaAreanm && not empty command.reqDTO.pincodeNo}">
											<tr>
												<th colspan="2" style="text-align: left;"><spring:message
														code="rti.corrAdd" /></th>
											</tr>
											<tr>
												<td><spring:message code="rti.address" /></td>
												<td>${command.reqDTO.corrAddrsAreaName}</td>
											</tr>
											<tr>
												<td><spring:message code="rti.pinCode" /></td>
												<td>${command.reqDTO.corrAddrsPincodeNo}</td>
											</tr>
										</c:if>
										<c:if
											test="${not empty command.reqDTO.inwAuthorityName && not empty command.reqDTO.inwAuthorityDept && not empty command.reqDTO.inwAuthorityAddress && not empty command.reqDTO.inwReferenceNo && not empty command.reqDTO.referenceDate}">
											<tr>
												<th colspan="2" style="text-align: left;"><spring:message
														code="rti.refdetails" />(Form E)</th>
											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.inwAuthorityName}">
											<tr>
												<td><spring:message code="rti.authorityName" /></td>
												<td>${command.reqDTO.inwAuthorityName}</td>
											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.inwAuthorityDept}">
											<tr>
												<td><spring:message code="rti.depName" /></td>
												<td>${command.reqDTO.inwAuthorityDept}</td>
											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.inwAuthorityAddress}">
											<tr>
												<td><spring:message code="rti.address" /></td>
												<td>${command.reqDTO.inwAuthorityAddress}</td>
											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.inwReferenceNo}">
											<tr>
												<td><spring:message code="rti.refno" /></td>
												<td>${command.reqDTO.inwReferenceNo}</td>
											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.referenceDate}">
											<tr>
												<td><spring:message code="rti.refdate" /></td>
												<td>${command.reqDTO.referenceDate}</td>
											</tr>
										</c:if>
										<c:if
											test="${not empty command.reqDTO.stampNo && not empty command.reqDTO.stampAmt && not empty command.reqDTO.inwardTypeName}">
											<tr>
												<th colspan="2" style="text-align: left;"><spring:message
														code="rti.refdetails" />(Stamp)</th>
											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.stampNo}">
											<tr>
												<td><spring:message code="rti.stampno" /></td>
												<td>${command.reqDTO.stampNo}</td>
											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.stampAmt}">
											<tr>
												<td><spring:message code="rti.stampamt" /></td>
												<td>${command.reqDTO.stampAmt}</td>
											</tr>
										</c:if>
										<%-- <tr>
											<td><spring:message code="rti.download" /></td>
											<td><c:forEach items="${command.fetchDocumentList}"
													var="lookUp">
													<apptags:filedownload filename="${lookUp.attFname}"
														filePath="${lookUp.attPath}"
														actionUrl="PioResponse.html?Download"></apptags:filedownload>
													<form:hidden path="" value="${lookUp.attId}" />
												</c:forEach></td>
										</tr> --%>
										<tr>
											<th colspan="2" style="text-align: left;"><spring:message
													code="rti.refmode" /></th>
										</tr>
										<tr>
											<td><spring:message code="rti.inwardtype" /></td>
											<td>${command.reqDTO.inwardTypeName}</td>
										</tr>
										<tr>
											<th colspan="2" style="text-align: left;"><spring:message
													code="rti.rtiInfo" /></th>
										</tr>
										<tr>
											<td><spring:message code="rti.dept" /></td>
											<td>${command.reqDTO.departmentName}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.related.dept"
													text="RTI Related Department" /></td>
											<td>${command.reqDTO.relatedDeptName}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.subject" /></td>
											<td>${command.reqDTO.rtiSubject}</td>
										</tr>
										<%-- <tr>
											<td><spring:message code="rti.appln.uploadedfile" /></td>
											<td><c:forEach items="${command.fetchApplnUpload}"
													var="lookUp">
													<apptags:filedownload filename="${lookUp.attFname}"
														filePath="${lookUp.attPath}"
														actionUrl="RtiDispatch.html?Download"></apptags:filedownload>
													<form:hidden path="" value="${lookUp.attId}" />
												</c:forEach></td>
										</tr> --%>
										<!-- MOM POINTS -->
										<%-- <tr>
											<td><spring:message code="ApplicationForm.rtiDetails" /></td>
											<td>${command.reqDTO.rtiDetails}</td>
										</tr> --%>
										<tr>
											<td>
												<%-- <a href="PioResponse.html?showHistoryDetails"> <spring:message
														code="rti.history" text="Action History" />
											</a> --%> <a
												class="fancybox fancybox.ajax text-small text-info"
												href="RtiDispatch.html?showHistoryDetails"> <spring:message
														code="rti.history"
														text="Click here to show remarks and attachment history" />
													<i class="fa fa-question-circle "></i></a>
											</td>
										</tr>

										<tr>
											<th colspan="2" style="text-align: left;"><spring:message
													code="rti.doc" /></th>
										</tr>

										<tr>
											<td><spring:message code="rti.checklist.file" /></td>
											<td><c:forEach items="${command.fetchDocumentList}"
													var="lookUp">
													<apptags:filedownload dmsDocId="${lookUp.dmsDocId}"
														filename="${lookUp.attFname}" filePath="${lookUp.attPath}"
														actionUrl="RtiDispatch.html?Download"></apptags:filedownload>
													<form:hidden path="" value="${lookUp.attId}" />
												</c:forEach></td>
										</tr>

										<tr>
											<td><spring:message code="rti.appln.uploadedfile" /></td>
											<td><c:forEach items="${command.fetchApplnUpload}"
													var="lookUp">
													<apptags:filedownload dmsDocId="${lookUp.dmsDocId}"
														filename="${lookUp.attFname}" filePath="${lookUp.attPath}"
														actionUrl="RtiDispatch.html?Download"></apptags:filedownload>
													<form:hidden path="" value="${lookUp.attId}" />
												</c:forEach></td>
										</tr>
										<tr>
											<td><spring:message code="rti.appln.uploadedfile.pio"
													text="Document Uploaded By APIO" /></td>
											<td><c:forEach items="${command.fetchPioUploadDoc}"
													var="lookUp">
													<apptags:filedownload dmsDocId="${lookUp.dmsDocId}"
														filename="${lookUp.attFname}" filePath="${lookUp.attPath}"
														actionUrl="RtiDispatch.html?Download"></apptags:filedownload>
													<form:hidden path="" value="${lookUp.attId}" />
												</c:forEach></td>
										</tr>


									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group" id="">

								<label id="deliveryModeType"
									class="col-sm-2 control-label required-control"
									for="deliveryType"><spring:message
										code="rti.deliverymode" /></label>

								<div class="col-sm-4">
									<c:set var="baseLookupCode" value="DMT" />
									<form:select path="reqDTO.finalDispatchMode"
										onchange="getDeliveryMode()"
										class="form-control mandColorClass" id="finalDispatchMode"
										data-rule-required="true">
										<form:option value="0">
											<spring:message code="rti.sel.deliveryMode" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>


							<%-- 	<label class="col-sm-2 control-label required-control"
									for="dispatchdate"><spring:message
										code="rti.deliveryDate" text="Date" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input class="form-control mandColorClass datepicker"
											id="dispatchDate" readOnly="true" onchange=""
											path="reqDTO.dispatchDate"></form:input>
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
									</div>
								</div> --%>


							</div>
							<div id="deliveryMode"></div>
						</div>
					</div>
				</div>
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"
						for="dispatchNo"><spring:message code="report.content32"
							text="DispatchNo" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control hasNumber"
							path="reqDTO.dispatchNo" id="dispatchNo"></form:input>

					</div>

					<%-- 
					<label class="col-sm-2 control-label" for="dispatchDt"><spring:message
							code="Dispatch Date" text="Dispatch Date" /><i
						class="text-red-1">*</i></label>
					<div class="col-sm-4">
						<form:input name="pincodeNo" type="text"
							class="form-control hasNumber" id="dispatchDt"
							path="reqDTO.dispatchDt"></form:input>
					</div> --%>

					<label class="col-sm-2 control-label" for="dispatchDt"><spring:message
							code="report.content33" text="Dispatch Date" /><i
						class="text-red-1">*</i></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input class="form-control mandColorClass datepicker"
								id="dispatchDt" readOnly="true" onchange=""
								path="reqDTO.dispatchDt"></form:input>
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>


				</div>


				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveDispatchForm(this)">
						<spring:message code="rti.submit" />
					</button>
					<button type="button" class="btn btn-danger" id="back"
						onclick="backPage()">
						<spring:message code="rti.back"></spring:message>
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

<script>
	var dispatchDt = $('#dispatchDt').val();
	if (dispatchDt) {
		$('#dispatchDt').val(dispatchDt.split(' ')[0]);
	}
</script>

