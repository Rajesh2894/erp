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
<script type="text/javascript" src="js/sfac/fpoManagementCost.js"></script>
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
				<spring:message code="sfac.fpo.mc.name"
					text="FPO Management Cost  Form" />
			</h2>
			<apptags:helpDoc url="FPOManagementCost.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="fpoMgmtCostForm" action="FPOManagementCost.html"
				method="post" class="form-horizontal">
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
									data-parent="#accordion_single_collapse" href="#FPODetails">
									<spring:message code="sfac.fpo.fpoDetails" text="FPO Details" />
								</a>
							</h4>
						</div>
						<div id="FPODetails" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.fpoName" text="FPO Name" /></label>
									<div class="col-sm-4">
									<form:input
							path="dto.foFpoMasterDto.fpoName"
							
							id="fpoName$" class="form-control " disabled="true" />
								<form:hidden path="dto.foFpoMasterDto.fpoId" id="fpoId" />
						<%-- <form:select path="dto.foFpoMasterDto.fpoId" id="fpoId"
							cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.masterDtoList}" var="dto">
								<form:option value="${dto.fpoId}">${dto.fpoName}</form:option>
							</c:forEach>
						</form:select> --%>
									</div>

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cbbo.name" text="CBBO Name" /></label>
									<div class="col-sm-4">
										<form:input
							path="dto.foFpoMasterDto.cbboName"
							
							id="cbboName" class="form-control " disabled="true" />
								<form:hidden path="dto.foFpoMasterDto.cbboId" id="cbboId" />
						<%-- <form:select path="dto.cbboMasterDto.cbboId" id="cbboId"
							cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.cbboMasterList}" var="cbbo">
								<form:option value="${cbbo.cbboId}">${cbbo.cbboName}</form:option>
							</c:forEach>
						</form:select> --%>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.IA.name" text="IA Name" /></label>
									<div class="col-sm-4">
											<form:input
							path="dto.foFpoMasterDto.iaName"
							
							id="iaName" class="form-control " disabled="true" />
								<form:hidden path="dto.foFpoMasterDto.iaId" id="iaId" />
						<%-- <form:select path="dto.iaMasterDto.IAId" id="iaId"
							cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select'" />
							</form:option>
							<c:forEach items="${command.iaNameList}" var="dto">
								<form:option value="${dto.iaId}" code="${dto.IAName}">${dto.IAName}</form:option>
							</c:forEach>
						</form:select> --%>
									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.cob.financial.year" text="Financial Year" /></label>
									<div class="col-sm-4">
										<form:select path="dto.financialYear" id="financialYear" 	disabled="${command.viewMode eq 'V' ? true : false }"
											class="form-control chosen-select-no-results">
											<form:option value="">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.faYears}" var="lookUp">
												<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>

							</div>
						</div>
					</div>


					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#mcDetails">
									<spring:message code="sfac.fpo.mc.mgmtCost"
										text="Management Cost Details" />
								</a>
							</h4>
						</div>
						<div id="mcDetails" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="mcDetailsTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
											<th width="25%"><spring:message code="sfac.fpo.mc.particulars"
													text="Particulars" /></th>

											<th width="15%"><spring:message code="sfac.fpo.mc.mceml"
													text="Management cost Maximum Limit" /></th>
											<th><spring:message code="sfac.fpo.mc.mca"
													text="Management cost Incurred" /></th>

											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.fpoManagementCostDetailDTOs)>0 }">
												<c:forEach var="dto"
													items="${command.dto.fpoManagementCostDetailDTOs}"
													varStatus="status">
													<tr class="appendableMCDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" /></td>

														<td><div><c:set var="baseLookupCode" value="MCP" /> <form:select
																path="dto.fpoManagementCostDetailDTOs[${d}].particulars"
																class="form-control chosen-select-no-results" onchange = "getLimit(${d});"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="particulars${d}">
																<form:option value="0">
																	<spring:message code="sfac.select" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select>
																
															</div>
															</td>




														<td>
														
														<c:set var="baseLookupCode" value="MCP" /> <form:select
																path="dto.fpoManagementCostDetailDTOs[${d}].particulars"
																class="form-control"
																disabled="true"
																id="managementCostExpected${d}">
																<form:option value="0">
																	<spring:message code="sfac.select" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.otherField}</form:option>
																</c:forEach>
															</form:select>
														
														</td>


														<td><form:input
																path="dto.fpoManagementCostDetailDTOs[${d}].managementCostIncurred" 	disabled="${command.viewMode eq 'V' ? true : false }"
																id="managementCostIncurred${d}"  onkeypress="return hasAmount(event, this, 10, 2)"
																class="form-control"  /></td>



														<td class="text-center">
														<c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addCostButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addCostButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteCostDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteCostDetails(this);"> <i
																class="fa fa-trash"></i>
														</a></c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableMCDetails">
													<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" /></td>

														<td><div><c:set var="baseLookupCode" value="MCP" /> <form:select
																path="dto.fpoManagementCostDetailDTOs[${d}].particulars"
																class="form-control chosen-select-no-results" onchange = "getLimit(${d});"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="particulars${d}">
																<form:option value="0">
																	<spring:message code="sfac.select" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select>
															</div>
																
															</td>




														<td><c:set var="baseLookupCode" value="MCP" /> <form:select
																path="dto.fpoManagementCostDetailDTOs[${d}].particulars"
																class="form-control"
																disabled="true"
																id="managementCostExpected${d}">
																<form:option value="0">
																	<spring:message code="sfac.select" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.otherField}</form:option>
																</c:forEach>
															</form:select></td>


														<td><form:input
																path="dto.fpoManagementCostDetailDTOs[${d}].managementCostIncurred" 	disabled="${command.viewMode eq 'V' ? true : false }"
																id="managementCostIncurred${d}"  onkeypress="return hasAmount(event, this, 10, 2)"
																class="form-control "  /></td>



														<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addCostButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addCostButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteCostDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteCostDetails(this);"> <i
																class="fa fa-trash"></i>
														</a></c:if></td>
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:otherwise>
										</c:choose>

									</tbody>
								</table>


							

							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse"
									href="#docDetails"> <spring:message
										code="sfac.fpo.mc.docDetails"
										text="Document Details" /></a>
							</h4>
						</div>
						<div id="docDetails" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="docDetailsTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
										

											<th><spring:message code="sfac.fpo.pm.docDesc"
													text="Document Description" /></th>
											<th><spring:message code="sfac.fpo.pm.licenseDocUpload"
													text="Document Upload" /></th>
											
											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										 <c:choose>
											<c:when
												test="${fn:length(command.dto.fpoManagementCostDocDetailDTOs)>0 }">
												<c:forEach var="dto"
													items="${command.dto.fpoManagementCostDocDetailDTOs}"
													varStatus="status">
													<tr class="appendableDocDetails">

															<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNoDoc${d}"
																value="${d+1}" disabled="true" />
														<td><form:input
																path="dto.fpoManagementCostDocDetailDTOs[${d}].documentDescription" 	disabled="${command.viewMode eq 'V' ? true : false }"
																id="documentDescription${d}" class="form-control alphaNumeric" maxlength="400"/></td>
														
															<td>
															<c:if test="${command.viewMode ne 'V'}">
															<apptags:formField
														fieldType="7"
														fieldPath="dto.fpoManagementCostDocDetailDTOs[${d}].attachments[${d}].uploadedDocumentPath"
														currentCount="${d}" showFileNameHTMLId="true"
														folderName="${d}" fileSize="CARE_COMMON_MAX_SIZE"
														isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
													validnFunction="ALL_UPLOAD_VALID_EXTENSION">
													</apptags:formField>
													</c:if>
															<c:if
															test="${command.dto.fpoManagementCostDocDetailDTOs[d].attachDocsList[0] ne null  && not empty command.dto.fpoManagementCostDocDetailDTOs[d].attachDocsList[0] }">
															<input type="hidden" name="deleteFileId"
																value="${command.dto.fpoManagementCostDocDetailDTOs[d].attachDocsList[0].attId}">
															<input type="hidden" name="downloadLink"
																value="${command.dto.fpoManagementCostDocDetailDTOs[d].attachDocsList[0]}">
															<apptags:filedownload
																filename="${command.dto.fpoManagementCostDocDetailDTOs[d].attachDocsList[0].attFname}"
																filePath="${command.dto.fpoManagementCostDocDetailDTOs[d].attachDocsList[0].attPath}"
																actionUrl="FPOManagementCost.html?Download"></apptags:filedownload>
														</c:if>
														<small class="text-blue-2"> <spring:message
																	code="sfac.fpo.checklist.validation"
																	text="(Upload Image File upto 5 MB)" />
														</small>
														</td>
																
																


														<td class="text-center">
														<c:if test="${command.viewMode ne 'V'}">
														<a
															class="btn btn-blue-2 btn-sm addDocButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="fileCountUpload(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteDocDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteDocDetails($(this),${d});"> <i
																class="fa fa-trash"></i>
														</a></c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableDocDetails">
													<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNoDoc${d}"
																value="${d+1}" disabled="true" />
														<td><form:input
																path="dto.fpoManagementCostDocDetailDTOs[${d}].documentDescription" 	disabled="${command.viewMode eq 'V' ? true : false }"
																id="documentDescription${d}" class="form-control alphaNumeric" maxlength="400"/></td>
														
															<td><c:if test="${command.viewMode ne 'V'}"><apptags:formField
														fieldType="7"
														fieldPath="dto.fpoManagementCostDocDetailDTOs[${d}].attachments[${d}].uploadedDocumentPath"
														currentCount="${d}" showFileNameHTMLId="true"
														folderName="${d}" fileSize="CARE_COMMON_MAX_SIZE"
														isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
														validnFunction="ALL_UPLOAD_VALID_EXTENSION">
														
													</apptags:formField></c:if>
													<c:if
															test="${command.dto.fpoManagementCostDocDetailDTOs[d].attachDocsList[0] ne null  && not empty command.dto.fpoManagementCostDocDetailDTOs[d].attachDocsList[0] }">
															<input type="hidden" name="deleteFileId"
																value="${command.dto.fpoManagementCostDocDetailDTOs[d].attachDocsList[0].attId}">
															<input type="hidden" name="downloadLink"
																value="${command.dto.fpoManagementCostDocDetailDTOs[d].attachDocsList[0]}">
															<apptags:filedownload
																filename="${command.dto.fpoManagementCostDocDetailDTOs[d].attachDocsList[0].attFname}"
																filePath="${command.dto.fpoManagementCostDocDetailDTOs[d].attachDocsList[0].attPath}"
																actionUrl="FPOManagementCost.html?Download"></apptags:filedownload>
														</c:if>
														<small class="text-blue-2"> <spring:message
																	code="sfac.fpo.checklist.validation"
																	text="(Upload Image File upto 5 MB)" />
														</small>
														</td>
																
																


														<td class="text-center">
														<c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addDocButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="fileCountUpload(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteDocDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteDocDetails($(this),${d});"> <i
																class="fa fa-trash"></i>
														</a></c:if></td>

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
						onclick="saveFPOMgmtCostForm(this);">
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
					<apptags:backButton url="FPOManagementCost.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>