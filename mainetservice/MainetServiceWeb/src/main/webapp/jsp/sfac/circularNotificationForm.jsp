<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/sfac/circularNotificationForm.js"></script>
<script src="js/mainet/file-upload.js"></script>

<style>
table.crop-details-table tbody tr td>input[type="checkbox"] {
	margin: 0.5rem 0 0 -0.5rem;
}

.stateDistBlock>label[for="sdb3"]+div {
	margin-top: 0.5rem;
}

.charCase {
	text-transform: uppercase;
}

#udyogAadharApplicable, #isWomenCentric {
	margin: 0.6rem 0 0 0;
}
</style>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.circular.notify.form"
					text="Circular/Notification Request Form" />
			</h2>
			<apptags:helpDoc url="CircularNotificationForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="CircularNotificationForm"
				action="CircularNotificationForm.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse"
									href="#circularDetails"> <spring:message
										code="sfac.circular.notify.details"
										text="Circular/Notification Details" />
								</a>
							</h4>
						</div>
						<div id="circularDetails" class="panel-collapse collapse in">
							<div class="panel-body">


								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.circular.title" text="Circular Title" /></label>
									<div class="col-sm-4">
										<form:input path="dto.circularTitle" id="circularTitle"
											class="form-control alphaNumeric"
											disabled="${command.viewMode eq 'V' ? true : false }" />
									</div>

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.circular.number" text="Circular No." /></label>
									<div class="col-sm-4">
										<form:input path="dto.circularNo" id="circularNo"
											class="form-control alphaNumeric"
											disabled="${command.viewMode eq 'V' ? true : false }" />
									</div>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.circular.converner" text="Convener Of Circular" /></label>
									<div class="col-sm-4">
										<form:input path="dto.convenerOfCircularName"
											id="convenerOfCircularName" class="form-control"
											disabled="true" />
										<form:hidden path="dto.convenerOfCircular"
											id="convenerOfCircular" />

									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.circular.converner.name" text="Convener Name" /></label>
									<div class="col-sm-4">
										<form:input path="dto.convenerName" id="convenerName"
											class="form-control hasCharacter"
											disabled="${command.viewMode eq 'V' ? true : false }" />
									</div>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.circular.doc" text="Date Of Circular" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.dateOfCircular" type="text"
												class="form-control datepicker mandColorClass"
												 id="dateOfCircular" placeholder="dd/mm/yyyy"
												readonly="true" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>

									</div>


								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.circular.email.body"
											text="Body of Circular/Notification via eMail" /></label>
									<div class="col-sm-10">
										<form:textarea path="dto.emailBody" id="emailBody "
											class="form-control alphaNumeric"
											disabled="${command.viewMode eq 'V' ? true : false }" />

									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.circular.sms.body"
											text="Body of Circular/Notification via SMS " /></label>
									<div class="col-sm-10">
										<form:textarea path="dto.smsBody" id="smsBody"
											class="form-control alphaNumeric"
											disabled="${command.viewMode eq 'V' ? true : false }" />


									</div>
								</div>


								<div class="form-group">

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.circular.upload.doc" text="Upload Upload" /></label>
									<div class="col-sm-4">


										<c:if test="${command.viewMode ne 'V'}">
											<apptags:formField fieldType="7"
												fieldPath="dto.attachments[0].uploadedDocumentPath"
												currentCount="0" showFileNameHTMLId="true"
												folderName="bpdfg0" fileSize="CARE_COMMON_MAX_SIZE"
												isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="ALL_UPLOAD_VALID_EXTENSION">
											</apptags:formField>
										</c:if>
										<c:if
											test="${command.dto.attachDocsList[0] ne null  && not empty command.dto.attachDocsList[0] }">
											<input type="hidden" name="deleteFileId"
												value="${command.dto.attachDocsList[0].attId}">
											<input type="hidden" name="downloadLink"
												value="${command.dto.attachDocsList[0]}">
											<apptags:filedownload
												filename="${command.dto.attachDocsList[0].attFname}"
												filePath="${command.dto.attachDocsList[0].attPath}"
												actionUrl="FPOProfileManagementForm.html?Download"></apptags:filedownload>
											<c:if test="${command.viewMode ne 'V'}">
												<small class="text-blue-2"> <spring:message
														code="sfac.fpo.checklist.validation"
														text="(Upload Image File upto 5 MB)" />
												</small>
											</c:if>
										</c:if>


									</div>



								</div>


							</div>
						</div>
					</div>


					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#recpetails">
									<spring:message code="sfac.circular.recipients.details"
										text="Recipients Details" />
								</a>
							</h4>
						</div>
						<div id="recpetails" class="panel-collapse collapse">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="recpDetailsTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>


											<th width="20%"><spring:message
													code="sfac.circular.orgType" text="Organization Type" /></th>
											<th><spring:message code="sfac.circular.orgName"
													text="Organization Name" /></th>
											<th width="15%"><spring:message code="sfac.state"
													text="State" /></th>
											<th><spring:message code="sfac.district" text="District" /></th>

											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.circularNotiicationDetDtos)>0 }">
												<c:forEach var="dto"
													items="${command.dto.circularNotiicationDetDtos}"
													varStatus="status">
													<tr class="appendablemsRecpDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" />
														<td><form:select
																path="dto.circularNotiicationDetDtos[${d}].orgType" 
																id="orgType${d}" disabled="${command.viewMode eq 'V' ? true : false }"
																cssClass="form-control chosen-select-no-results">
																<form:option value="0">
																	<spring:message text="Select" code="sfac.select" />
																</form:option>
																<c:forEach items="${command.deptList}" var="objArray">
																	<c:if test="${userSession.languageId eq 1}">
																		<form:option value="${objArray[0]}"
																			code="${objArray[3]}" label="${objArray[1]}"></form:option>
																	</c:if>
																	<c:if test="${userSession.languageId eq 2}">
																		<form:option value="${objArray[0]}"
																			code="${objArray[3]}" label="${objArray[2]}"></form:option>
																	</c:if>
																</c:forEach>
															</form:select></td>

														<td><form:input
																path="dto.circularNotiicationDetDtos[${d}].orgName"
																readonly="true" id="orgName${d}" class="form-control" /></td>

														<td><form:select
																path="dto.circularNotiicationDetDtos[${d}].sdb1"
																onchange="getDistrictList(${d});"
																class="form-control chosen-select-no-results"
																id="sdb1${d}"
																disabled="${command.viewMode eq 'V' ? true : false }">
																<form:option value="">
																	<spring:message code="tbOrganisation.select"
																		text="Select" />
																</form:option>
																<c:forEach items="${command.stateList}" var="lookUp">
																	<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>

														<td><form:select
																path="dto.circularNotiicationDetDtos[${d}].sdb2"
																id="distId${d}"
																class="form-control mandColorClass distId"
																disabled="${command.viewMode eq 'V' ? true : false }">
																<form:option value="0">
																	<spring:message code='master.selectDropDwn' />
																</form:option>
																<c:forEach items="${command.districtList}" var="dist">
																	<form:option value="${dist.lookUpId}">${dist.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>



														<td class="text-center"><c:if
																test="${command.viewMode ne 'V'}">
																<a class="btn btn-blue-2 btn-sm addRecpButton"
																	title='<spring:message code="sfac.fpo.add" text="Add" />'
																	onclick="addRecpButton(this);"> <i
																	class="fa fa-plus-circle"></i></a>
																<a class='btn btn-danger btn-sm deleteRecpDetails '
																	title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																	onclick="deleteRecpDetails($(this));"> <i
																	class="fa fa-trash"></i>
																</a>
															</c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendablemsRecpDetails">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNo${d}"
															value="${d+1}" disabled="true" />
													<td><form:select
															path="dto.circularNotiicationDetDtos[${d}].orgType" 
															id="orgType${d}" disabled="${command.viewMode eq 'V' ? true : false }"
															cssClass="form-control chosen-select-no-results">
															<form:option value="0">
																<spring:message text="Select" code="sfac.select" />
															</form:option>
															<c:forEach items="${command.deptList}" var="objArray">
																<c:if test="${userSession.languageId eq 1}">
																	<form:option value="${objArray[0]}"
																		code="${objArray[3]}" label="${objArray[1]}"></form:option>
																</c:if>
																<c:if test="${userSession.languageId eq 2}">
																	<form:option value="${objArray[0]}"
																		code="${objArray[3]}" label="${objArray[2]}"></form:option>
																</c:if>
															</c:forEach>
														</form:select></td>

													<td><form:input
															path="dto.circularNotiicationDetDtos[${d}].orgName"
															readonly="true" id="orgName${d}" class="form-control" /></td>

													<td><form:select
															path="dto.circularNotiicationDetDtos[${d}].sdb1"
															class="form-control chosen-select-no-results"
															id="sdb1${d}" onchange="getDistrictList(${d});"
															disabled="${command.viewMode eq 'V' ? true : false }">
															<form:option value="">
																<spring:message code="tbOrganisation.select"
																	text="Select" />
															</form:option>
															<c:forEach items="${command.stateList}" var="lookUp">
																<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
															</c:forEach>
														</form:select></td>
													<td><form:select
															path="dto.circularNotiicationDetDtos[${d}].sdb2"
															id="distId${d}"
															class="form-control mandColorClass distId"
															disabled="${command.viewMode eq 'V' ? true : false }">
															<form:option value="0">
																<spring:message code='master.selectDropDwn' />
															</form:option>
															<c:forEach items="${command.districtList}" var="dist">
																<form:option value="${dist.lookUpId}">${dist.lookUpDesc}</form:option>
															</c:forEach>
														</form:select></td>



													<td class="text-center"><c:if
															test="${command.viewMode ne 'V'}">
															<a class="btn btn-blue-2 btn-sm addRecpButton"
																title='<spring:message code="sfac.fpo.add" text="Add" />'
																onclick="addRecpButton(this);"> <i
																class="fa fa-plus-circle"></i></a>
															<a class='btn btn-danger btn-sm deleteRecpDetails '
																title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																onclick="deleteRecpDetails($(this));"> <i
																class="fa fa-trash"></i>
															</a>
														</c:if></td>

												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
						</div>


					</div>




				</div>

				<div class="text-center padding-top-10">
					<c:if test="${command.viewMode ne 'V'}">
						<button type="button" class="btn btn-success"
							title='<spring:message code="sfac.submit" text="Submit" />'
							onclick="saveCircularForm(this);">
							<spring:message code="sfac.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.viewMode eq 'A'}">
						<button type="button" class="btn btn-warning"
							title='<spring:message code="sfac.button.reset" text="Reset"/>'
							onclick="ResetForm();">
							<spring:message code="sfac.button.reset" text="Reset" />
						</button>
					</c:if>
					<apptags:backButton url="CircularNotificationForm.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>