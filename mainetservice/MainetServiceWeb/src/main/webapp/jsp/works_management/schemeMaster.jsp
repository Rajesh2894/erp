<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/works_management/schemeMaster.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

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
				<spring:message code="scheme.master.title" text="" />
			</h2>
			<div class="additional-btn">
			  <apptags:helpDoc url="WmsSchemeMaster.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="WmsSchemeMaster.html" class="form-horizontal"
				name="wmsSchemeMaster" id="wmsSchemeMasterForm">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<form:hidden path="removeChildIds" id="removeChildIds" />
				<form:hidden path="removeFileById" id="removeFileById" />
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="cpdMode" id="cpdMode" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="wms.SourceofFund" text="Source of Fund" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label "><spring:message
											code="wms.SourceofFund" text="Source of Fund" /></label>
									<div class="col-sm-4">
										<form:select path="schemeMasterDTO.wmSchCodeId1"
											class="form-control chosen-select-no-results" id="sourceCode"
											onchange="getSchemeDetails(this ,'C');">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.sourceLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>

									</div>

									<label class="col-sm-2 control-label "><spring:message
											code="scheme.master.select.scheme.name" text="Select Scheme Name" /></label>
									<div class="col-sm-4">
										<form:select path="schemeMasterDTO.wmSchCodeId2"
											id="sourceName" class="form-control chosen-select-no-results"
											onchange="getSchemeDetailNames(this);">
											<c:if test="${command.saveMode eq 'C'}">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
											</c:if>
											<c:if test="${command.saveMode ne 'C'}">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${command.schemeLookUps}" var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpCode} -- ${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</c:if>
										</form:select>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 type="h4" class="panel-title">
								<a data-target="#a1" data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a2"><spring:message
										code="scheme.master.title" text="Scheme Master" /></a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<%-- <div class="form-group">
									<c:choose>
										<c:when
											test="${command.saveMode eq 'A' || command.saveMode eq 'P'}">
											<apptags:input labelCode="scheme.master.schemecode"
												path="schemeMasterDTO.wmSchCode" isMandatory="true"
												maxlegnth="10" cssClass="form control mandcolour">
											</apptags:input>
										</c:when>
										<c:otherwise>
											<apptags:input labelCode="scheme.master.schemecode"
												path="schemeMasterDTO.wmSchCode" isMandatory="true"
												maxlegnth="10" cssClass="form control mandcolour"
												isDisabled="true">
											</apptags:input>
										</c:otherwise>
									</c:choose>

								</div> --%>
								<div class="form-group">
									<apptags:textArea labelCode="scheme.master.schemename.eng"
										path="schemeMasterDTO.wmSchNameEng" isMandatory="true"
										maxlegnth="250"></apptags:textArea>

									<apptags:textArea labelCode="scheme.master.schemename.reg"
										path="schemeMasterDTO.wmSchNameReg" isMandatory="true"
										maxlegnth="250"></apptags:textArea>
								</div>
								<div class="form-group">
									<%-- <label class="col-sm-2 control-label required-control"><spring:message
											code="scheme.master.startdate" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="schemeMasterDTO.wmSchStrDate"
												id="wmSchStrDate"
												class="form-control mandColorClass datepicker dateValidation "
												value="" readonly="false" data-rule-required="true"
												maxLength="10" />
											<label class="input-group-addon" for="wmSchStrDate"><i
												class="fa fa-calendar"></i><span class="hide"> <spring:message
														code="" text="icon" /></span><input type="hidden"
												id=wmSchStrDate></label>
										</div>
									</div> --%>
									<%-- <apptags:date fieldclass="datepicker"
										labelCode="scheme.master.enddate"
										datePath="schemeMasterDTO.wmSchEndDate" cssClass="custDate"></apptags:date> --%>
									<label class="col-sm-2 control-label "><spring:message
											code="scheme.master.fund"></spring:message></label>
									<c:choose>
										<c:when test="${command.cpdMode eq 'L'}">
											<div class="col-sm-4">
												<form:select path="schemeMasterDTO.wmSchFund"
													cssClass="form-control" id="wmSchFund">
													<form:option value="">
														<spring:message code='work.management.select' />
													</form:option>
													<c:forEach items="${command.fundList}" var="fundMaster">
														<form:option value="${fundMaster.key}"
															code="${fundMaster.key}">${fundMaster.value}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</c:when>
										<c:otherwise>
											<div class="col-sm-4">
												<form:input path="schemeMasterDTO.schFundName"
													cssClass="form-control" />
											</div>
										</c:otherwise>
									</c:choose>

									<apptags:textArea labelCode="work.management.description"
										path="schemeMasterDTO.wmSchDesc" isMandatory=""
										cssClass="mandColorClass" maxlegnth="1000"></apptags:textArea>
								</div>
								<%--<div class="form-group">
									 <label class="col-sm-2 control-label "><spring:message
											code="scheme.master.project.expenditure" /></label>
									<div class="col-sm-4">
										<form:input path="schemeMasterDTO.wmSchPRev"
											cssClass="form-control  text-right"
											onkeypress="return hasAmount(event, this, 13, 2)"
											onchange="getAmountFormatInDynamic((this),'wmSchPRev')"
											placeholder="00.00" id="wmSchPRev" />
									</div>

							</div> --%>
							</div>
						</div>
					</div>

					<!-- Start Section -->
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv" style="display: none;"></div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 type=h4 class="panel-title">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a3"><spring:message
										code="scheme.master.funding.pattern" text="" /></a>
							</h4>
						</div>
						<div id="a3" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="table-responsive">
									<c:set var="d" value="0" scope="page"></c:set>
									<table class="table table-bordered table-striped"
										id="schemeMstList">
										<tr>
											<th width=""><spring:message code="scheme.master.srno"
													text="" /><input type="hidden" id="srNo"></th>
											<th width="60%"><spring:message
													code="scheme.master.sponsoredby" text="" /></th>
											<th><spring:message code="scheme.master.sharepercent"
													text="" /></th>
											<th class="text-center" width="80"><button type="button"
													onclick="return false;"
													class="btn btn-blue-2 btn-sm  addSchemeMstList">
													<i class="fa fa-plus-circle"></i>
												</button></th>
										</tr>
										<c:choose>
											<c:when
												test="${fn:length(command.schemeMasterDTO.mastDetailsDTO) > 0}">

												<c:forEach var="schemeListData"
													items="${command.schemeMasterDTO.mastDetailsDTO}"
													varStatus="status">
													<tr class="appendableClass">
														<form:hidden
															path="schemeMasterDTO.mastDetailsDTO[${d}].schDetId"
															id="schDetId${d}" />
														<form:hidden
															path="schemeMasterDTO.mastDetailsDTO[${d}].schActiveFlag"
															id="schActiveFlag${d}" value="A" />

														<td></td>
														<td><form:select
																path="schemeMasterDTO.mastDetailsDTO[${d}].schDSpon"
																class=" form-control " id="schDSpon${d}">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.getLevelData('SBY')}"
																	var="lookup">
																	<form:option value="${lookup.lookUpId}"
																		code="${lookup.lookUpCode}">${lookup.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input
																path="schemeMasterDTO.mastDetailsDTO[${d}].schSharPer"
																cssClass="form-control  text-right"
																onkeypress="return hasAmount(event, this, 3, 2)"
																onchange="getAmountFormatInDynamic((this),'schSharPer')"
																placeholder="000.00" id="schSharPer${d}" /></td>
														<td class="text-center"><a href='#'
															onclick='return false;'
															class='btn btn-danger btn-sm deleteSchemeLink'><i
																class="fa fa-trash"></i></a></td>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>

											<c:otherwise>

												<tr class="appendableClass">
													<form:hidden
														path="schemeMasterDTO.mastDetailsDTO[${d}].schDetId"
														id="schDetId${d}" />
													<form:hidden
														path="schemeMasterDTO.mastDetailsDTO[${d}].schActiveFlag"
														id="schActiveFlag${d}" value="A" />
													<td></td>
													<td><form:select
															path="schemeMasterDTO.mastDetailsDTO[${d}].schDSpon"
															class=" form-control " id="schDSpon${d}">
															<form:option value="">
																<spring:message code='work.management.select' />
															</form:option>
															<c:forEach items="${command.getLevelData('SBY')}"
																var="lookup">
																<form:option value="${lookup.lookUpId}"
																	code="${lookup.lookUpCode}">${lookup.lookUpDesc}</form:option>
															</c:forEach>
														</form:select></td>
													<td><form:input
															path="schemeMasterDTO.mastDetailsDTO[${d}].schSharPer"
															cssClass="form-control  text-right"
															onkeypress="return hasAmount(event, this, 3, 2)"
															onchange="getAmountFormatInDynamic((this),'schSharPer')"
															placeholder="000.00" id="schSharPer${d}" /></td>
													<td class="text-center"><a href='#'
														onclick='return false;'
														class='btn btn-danger btn-sm deleteSchemeLink'><i
															class="fa fa-trash"></i></a></td>
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:otherwise>
										</c:choose>
									</table>
								</div>
							</div>
						</div>
					</div>
					<!-- Start Each Section -->

					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv" style="display: none;"></div>
					<div class="panel-default margin-top-5">
						<div class="panel-heading">
							<h4 type=h4 class="panel-title">
								<a data-target="" data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a4"><spring:message
										code="scheme.master.schemedocs" text="" /></a>
							</h4>
						</div>
						<div id="a4" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label" for="UploadDocument"><spring:message
											code="scheme.master.upload" text="" /></label>
									<div class="col-sm-4">
										<small class="text-blue-2" style="padding-left: 10px;"><spring:message
												code='scheme.master.UploadFile' /></small>
										<apptags:formField fieldType="7" fieldPath=""
											showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
											isMandatory="false" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
											currentCount="0">
										</apptags:formField>
									</div>
								</div>
								<c:if test="${command.saveMode eq 'E'}">
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="attachDocs">
											<tr>
												<th><spring:message code="scheme.document.name" text="" /></th>
												<th><spring:message code="scheme.view.document" text="" /></th>
												<th><spring:message code="works.management.action"
														text=""></spring:message></th>
											</tr>
											<c:forEach items="${command.attachDocsList}" var="lookUp">
												<tr>
													<td>${lookUp.attFname}</td>
													<td><apptags:filedownload
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="WmsSchemeMaster.html?Download" /></td>
													<td class="text-center"><a href='#' id="deleteFile"
														onclick="return false;" class="btn btn-danger btn-sm"><i
															class="fa fa-trash"></i></a> <form:hidden path=""
															value="${lookUp.attId}" /></td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</c:if>

							</div>
						</div>
					</div>
				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" id="save" class="btn btn-success btn-submit"
						onclick="saveData(this);">
						<i class="fa fa-sign-out padding-right-5"></i>
						<spring:message code="works.management.save" text="" />
					</button>
					<c:if test="${command.saveMode ne 'E'}">
						<button class="btn btn-warning reset"
							onclick="resetSchemeMaster(this);" type="button">
							<i class="fa fa-undo padding-right-5"></i>
							<spring:message code="works.management.reset" text="" />
						</button>
					</c:if>
					<!-- To differentiate whether coming request is from project Master or not  -->
					<c:choose>
						<c:when test="${command.saveMode eq 'P'}">
							<button type="button" class="button-input btn btn-danger"
								name="button-Cancel" value="Cancel" style=""
								onclick="backAddProjectMasterForm();" id="button-Cancel">
								<i class="fa fa-chevron-circle-left padding-right-5"></i>
								<spring:message code="works.management.back" text="" />
							</button>
						</c:when>
						<c:otherwise>
							<button type="button" class="button-input btn btn-danger"
								name="button-Cancel" value="Cancel" style=""
								onclick="backProjectMasterForm();" id="button-Cancel">
								<i class="fa fa-chevron-circle-left padding-right-5"></i>
								<spring:message code="works.management.back" text="" />
							</button>
						</c:otherwise>
					</c:choose>

				</div>
			</form:form>
		</div>
	</div>
</div>
