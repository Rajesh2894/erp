<!-- Start JSP Necessary Tags -->
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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/legal/counterAffidavit.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script> 
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
				<spring:message code="" text="Counter Affidavit Form"/>
			</h2>
			<!-- SET HELP DOC URL WHICH YOU SET IN YOU CONTROLLER INDEX METHOD -->
			<apptags:helpDoc url="CounterAffidavit.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="CounterAffidavit.html" name="CounterAffidavitForm" 	id="CounterAffidavitForm" class="form-horizontal">
				
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>
				<jsp:include page="/jsp/legal/caseEntryViewForm.jsp" /> 
					
				<div class="panel-group accordion-toggle" id="counterAffidavitDetail">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="" text="Counter Affidavit Details" />
								</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">

									<apptags:date labelCode="Affidavit Date"
										datePath="counterAffidavitDTO.afDate" isMandatory="true"
										cssClass="form-control" fieldclass="lessthancurrdate2 date"
										isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:date>

									<apptags:date labelCode="Counter Affidavit Date"
										datePath="counterAffidavitDTO.cafDate" isMandatory="true"
										cssClass="form-control" fieldclass="lessthancurrdate2 date"
										isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:date>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="Census"><spring:message code=""
											text="Affidavit Type" /></label>
									<c:set var="baseLookupCode" value="AFT" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="counterAffidavitDTO.cafType"
										cssClass="mandColorClass form-control" isMandatory="true"
										hasId="true" selectOptionLabelCode="selectdropdown"
										disabled="${command.saveMode eq 'V' ? true : false }" />


								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 ">
										<spring:message code="" text="Plaintiff//Petitioner Name" />
									</label>
									<c:set var="p"
										value="${command.caseEntryDTO.tbLglCasePddetails}"
										scope="page"></c:set>

									<div class="col-sm-4">
										<form:select
											class=" mandColorClass form-control chosen-select-no-results"
											path="counterAffidavitDTO.cafPlaintiff" id="cafPlaintiff"
											disabled="${command.saveMode eq 'V' ? true : false }">
											<form:option value="">
												<spring:message code="lgl.select" text="Select" />
											</form:option>
											<c:forEach items="${p}" var="plaintiff">
												<c:if test="${plaintiff.csedFlag eq 'P'}">
													<form:option value="${plaintiff.csedName}">${plaintiff.csedName}</form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</div>


									<label class="control-label col-sm-2">
										<spring:message code="" text="Defender/Respondent Name" />
									</label>
									<c:set var="p"
										value="${command.caseEntryDTO.tbLglCasePddetails}"
										scope="page"></c:set>

									<div class="col-sm-4">
										<form:select
											class=" mandColorClass form-control chosen-select-no-results"
											path="counterAffidavitDTO.cafDefender" id="cafPlaintiff"
											disabled="${command.saveMode eq 'V' ? true : false }">
											<form:option value="">
												<spring:message code="lgl.select" text="Select" />
											</form:option>
											<c:forEach items="${p}" var="defender">
												<c:if test="${defender.csedFlag eq 'D'}">
													<form:option value="${defender.csedName}">${defender.csedName}</form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</div>

								</div>

							</div>
						</div>

					</div>
				 
				 </div>
				 
				 <!-- FILE UPLOAD------------------------------------------------------------------------------------------------>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="#a4" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a4"> <spring:message
										code="" text="Counter Affidavit Documents Uploading" /><small class="text-blue-2 margin-left-5"> <spring:message
															code="legal.doc.msg" text="(Only .pdf, .png and .jpg is allowed upto 1 MB)" /></small></a>
							</h4>
						</div>
						<div id="a4" class="panel-collapse collapse in">
							<form:hidden path="removeCommonFileById"
								id="removeCommonFileById" />

							<div class="panel-body">

								<c:if test="${fn:length(command.attachDocsList)>0}">
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="deleteCommonDoc">
											<tr>
												<th width="" align="center"><spring:message
														code="ser.no" text="" /><input type="hidden" id="srNo"></th>
												<th scope="col" width="64%" align="center"><spring:message
														code="work.estimate.document.description"
														text="Document Description" /></th>
												<th scope="col" width="30%" align="center"><spring:message
														code="scheme.view.document" /></th>
												<c:if test="${command.saveMode ne 'V'}">
													<th scope="col" width="8%"><spring:message
															code="lgl.action" text="Action"></spring:message></th>
												</c:if>
											</tr>
											<c:set var="e" value="0" scope="page" />
											<c:forEach items="${command.attachDocsList}" var="lookUp">
												<tr>
													<td>${e+1}</td>
													<td>${lookUp.dmsDocName}</td>
													<td><apptags:filedownload
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="CounterAffidavit.html?Download" /></td>
													<c:if test="${command.saveMode ne 'V'}">
														<td class="text-center"><a href='#' id="deleteCommonFile" onclick="return false;"
															class="btn btn-danger btn-sm"><i class="fa fa-trash"></i></a>
															<form:hidden path="" value="${lookUp.attId}" /></td>
													</c:if>
												</tr>
												<c:set var="e" value="${e + 1}" scope="page" />
											</c:forEach>
										</table>
									</div>
									<br>
								</c:if>

								<div id="doCommonFileAttachment">
									<div class="table-responsive">
										<c:set var="cd" value="0" scope="page" />
										<c:if test="${command.saveMode ne 'V'}">
											<table class="table table-bordered table-striped"
												id="attachCommonDoc">
												<tr>
													<th><spring:message
															code="work.estimate.document.description"
															text="Document Description" /></th>
													<th><spring:message code="lgl.comments.upload"
															text="Upload Document" /></th>
													<th scope="col" width="8%"><spring:message code="lgl.action" text="Action" /></th>		
												</tr>

												<tr class="appendableUploadClass">


													<td><form:input
															path="commonFileAttachment[${cd}].doc_DESC_ENGL"
															maxlength="100" class=" form-control" /></td>
													<td class="text-center"><apptags:formField
															fieldType="7"
															fieldPath="commonFileAttachment[${cd}].doc_DESC_ENGL"
															currentCount="${cd}" showFileNameHTMLId="true"
															fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
															maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
															callbackOtherTask="callbackOtherTask()">
														</apptags:formField></td>
													<td class="text-center" width="8%">
													<a onclick='doCommonFileAttachment(this);'
														class="btn btn-blue-2 btn-sm addButton"><i
															class="fa fa-plus-circle"></i></a>
													<a href='#' id="0_file_${cd}"
														onclick="doFileDelete(this)"
														class='btn btn-danger btn-sm delButton'><i
															class="fa fa-trash"></i></a></td>
												</tr>
												<c:set var="cd" value="${cd+1}" scope="page" />
											</table>
										</c:if>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
					<div class="text-center margin-top-10">
					<c:if test="${command.saveMode ne 'V'}">
						<input type="button" value="<spring:message code="bt.save" text="Save" />" onclick="return saveForm(this);" class="css_btn btn btn-success">
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<input type="button" class="btn btn-warning " value="<spring:message code="bt.clear" text="Reset"/>" onclick="openForm('CounterAffidavit.html','create')">
					</c:if>
					<apptags:backButton url="CounterAffidavit.html"/>
			  </div>
			</form:form>
		</div>
	</div>
</div> 
