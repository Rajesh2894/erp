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
<script type="text/javascript" src="js/sfac/fundReleaseRequestForm.js"></script>
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
				<spring:message code="sfac.fund.release.req.form"
					text="Fund Release Request To Ministry Form" />
			</h2>
			<apptags:helpDoc url="FundReleaseRequestFormApproval.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="FundReleaseRequestApprovalForm"
				action="FundReleaseRequestFormApproval.html" method="post"
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
									data-parent="#accordion_single_collapse" href="#fundReqDetails">
									<spring:message code="sfac.fund.release.req.ministry"
										text="Fund Release Request To Ministry" />
								</a>
							</h4>
						</div>
						<div id="fundReqDetails" class="panel-collapse collapse in">
							<div class="panel-body">


								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.IA.name" text="IA Name" /></label>
									<div class="col-sm-4">
										<form:input path="dto.iaName" id="iaName"
											class="form-control " disabled="true" />
										<form:hidden path="dto.iaId" id="iaId" />

									</div>

									<label class="col-sm-2 control-label"><spring:message
											code="sfac.fund.release.req.fileRef"
											text="File Reference No." /></label>
									<div class="col-sm-4">
										<form:input path="dto.fileReferenceNumber" disabled="${command.viewMode eq 'V' ? true : false }"
											id="fileReferenceNumber" class="form-control alphaNumeric" />
									</div>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.cob.financial.year" text="Financial Year" /></label>
									<div class="col-sm-4">
										<form:select path="dto.financialYear" id="financialYear"
											disabled="${command.viewMode eq 'V' ? true : false }"
											cssClass="form-control chosen-select-no-results">
											<form:option value="0">
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
									data-parent="#accordion_single_collapse" href="#newDemandetails">
									<spring:message code="sfac.fund.release.req.newdemand.details"
										text="New Demand Details" />
								</a>
							</h4>
						</div>
						<div id="newDemandetails" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="newDemandetailsTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>


											<th width="20%"><spring:message
													code="sfac.fund.release.req.purpose" text="Purpose/Demand For" /></th>
											<th><spring:message code="sfac.fund.release.req.noOfFPO"
													text="Allocated No Of FPOs" /></th>
											<th><spring:message code="sfac.milestone.comp.allocatedBudget"
													text="Allocated Budget" /></th>
													
											<th><spring:message code="sfac.fund.release.req.fund.release.till.date"
													text="Total Fund Release Till Date" /></th>				
											<th width="15%"><spring:message code="sfac.fund.release.req.utilized.amount"
													text="Utilized Amount" /></th>
											<th><spring:message code="sfac.fund.release.req.newDemand" text="New Demand" /></th>
											<th><spring:message code="sfac.fund.release.req.utlization.report" text="Upload Utilization Report" /></th>
											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.fundReleaseRequestDetailDtos)>0 }">
												<c:forEach var="dto"
													items="${command.dto.fundReleaseRequestDetailDtos}"
													varStatus="status">
													<tr class="appendableNewDemandetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" />
														<td><div><c:set var="baseLookupCode" value="PDP" /> <form:select
																path="dto.fundReleaseRequestDetailDtos[${d}].purposeFor"
																class="form-control chosen-select-no-results" 
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="purposeFor${d}">
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
															</div></td>

														<td><form:input
																path="dto.fundReleaseRequestDetailDtos[${d}].allocatedNoOfFPO"
																readonly="true" id="allocatedNoOfFPO${d}" class="form-control"
																 /></td>
														<td><form:input
																path="dto.fundReleaseRequestDetailDtos[${d}].allocatedBudget"
																 id="allocatedBudget${d}" class="form-control" 
																 disabled="${command.viewMode eq 'V' ? true : false }"/></td>
														<td><form:input
																path="dto.fundReleaseRequestDetailDtos[${d}].totalFundRelTillDate"
																 id="totalFundRelTillDate${d}" class="form-control"
																 disabled="${command.viewMode eq 'V' ? true : false }" /></td>	
														<td><form:input
																path="dto.fundReleaseRequestDetailDtos[${d}].utilizedAmount"
																 id="utilizedAmount${d}" class="form-control" 
																 disabled="${command.viewMode eq 'V' ? true : false }"/></td>
														<td><form:input
																path="dto.fundReleaseRequestDetailDtos[${d}].newDemand"
																 id="newDemand${d}" class="form-control" 
																 disabled="${command.viewMode eq 'V' ? true : false }"/></td>	
															<td>
															<c:if test="${command.viewMode ne 'V'}">
															<apptags:formField
														fieldType="7"
														fieldPath="dto.fundReleaseRequestDetailDtos[${d}].attachments[${d}].uploadedDocumentPath"
														currentCount="${d}" showFileNameHTMLId="true"
														folderName="${d}" fileSize="CARE_COMMON_MAX_SIZE"
														isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
													validnFunction="ALL_UPLOAD_VALID_EXTENSION">
													</apptags:formField>
													</c:if>
															<c:if
															test="${command.dto.fundReleaseRequestDetailDtos[d].attachDocsList[0] ne null  && not empty command.dto.fundReleaseRequestDetailDtos[d].attachDocsList[0] }">
															<input type="hidden" name="deleteFileId"
																value="${command.dto.fundReleaseRequestDetailDtos[d].attachDocsList[0].attId}">
															<input type="hidden" name="downloadLink"
																value="${command.dto.fundReleaseRequestDetailDtos[d].attachDocsList[0]}">
															<apptags:filedownload
																filename="${command.dto.fundReleaseRequestDetailDtos[d].attachDocsList[0].attFname}"
																filePath="${command.dto.fundReleaseRequestDetailDtos[d].attachDocsList[0].attPath}"
																actionUrl="FundReleaseRequestForm.html?Download"></apptags:filedownload>
														</c:if>
														
														</td>		 			 			


														<td class="text-center"><c:if
																test="${command.viewMode ne 'V'}">
																<a class="btn btn-blue-2 btn-sm addNewDemandButton"
																	title='<spring:message code="sfac.fpo.add" text="Add" />'
																	onclick="addNewDemandButton(this);"> <i
																	class="fa fa-plus-circle"></i></a>
																<a class='btn btn-danger btn-sm deleteNewDemandDetails '
																	title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																	onclick="deleteNewDemandDetails($(this));"> <i
																	class="fa fa-trash"></i>
																</a>
															</c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableNewDemandetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" />
														<td><div><c:set var="baseLookupCode" value="PDP" /> <form:select
																path="dto.fundReleaseRequestDetailDtos[${d}].purposeFor"
																class="form-control chosen-select-no-results" 
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="purposeFor${d}">
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
															</div></td>

														<td><form:input
																path="dto.fundReleaseRequestDetailDtos[${d}].allocatedNoOfFPO"
																readonly="true" id="allocatedNoOfFPO${d}" class="form-control" 
																/></td>
														<td><form:input
																path="dto.fundReleaseRequestDetailDtos[${d}].allocatedBudget"
																 id="allocatedBudget${d}" class="form-control"
																 disabled="${command.viewMode eq 'V' ? true : false }" /></td>
														<td><form:input
																path="dto.fundReleaseRequestDetailDtos[${d}].totalFundRelTillDate"
																 id="totalFundRelTillDate${d}" class="form-control" 
																 disabled="${command.viewMode eq 'V' ? true : false }"/></td>	
														<td><form:input
																path="dto.fundReleaseRequestDetailDtos[${d}].utilizedAmount"
																 id="utilizedAmount${d}" class="form-control" 
																 disabled="${command.viewMode eq 'V' ? true : false }"/></td>
														<td><form:input
																path="dto.fundReleaseRequestDetailDtos[${d}].newDemand"
																 id="newDemand${d}" class="form-control" 
																 disabled="${command.viewMode eq 'V' ? true : false }"/></td>	
															<td>
															<c:if test="${command.viewMode ne 'V'}">
															<apptags:formField
														fieldType="7"
														fieldPath="dto.fundReleaseRequestDetailDtos[${d}].attachments[${d}].uploadedDocumentPath"
														currentCount="${d}" showFileNameHTMLId="true"
														folderName="${d}" fileSize="CARE_COMMON_MAX_SIZE"
														isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
													validnFunction="ALL_UPLOAD_VALID_EXTENSION">
													</apptags:formField>
													</c:if>
															<c:if
															test="${command.dto.fundReleaseRequestDetailDtos[d].attachDocsList[0] ne null  && not empty command.dto.fundReleaseRequestDetailDtos[d].attachDocsList[0] }">
															<input type="hidden" name="deleteFileId"
																value="${command.dto.fundReleaseRequestDetailDtos[d].attachDocsList[0].attId}">
															<input type="hidden" name="downloadLink"
																value="${command.dto.fundReleaseRequestDetailDtos[d].attachDocsList[0]}">
															<apptags:filedownload
																filename="${command.dto.fundReleaseRequestDetailDtos[d].attachDocsList[0].attFname}"
																filePath="${command.dto.fundReleaseRequestDetailDtos[d].attachDocsList[0].attPath}"
																actionUrl="FundReleaseRequestForm.html?Download"></apptags:filedownload>
														</c:if>
													
														</td>		 			 			


														<td class="text-center"><c:if
																test="${command.viewMode ne 'V'}">
																<a class="btn btn-blue-2 btn-sm addNewDemandButton"
																	title='<spring:message code="sfac.fpo.add" text="Add" />'
																	onclick="addNewDemandButton(this);"> <i
																	class="fa fa-plus-circle"></i></a>
																<a class='btn btn-danger btn-sm deleteNewDemandDetails '
																	title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																	onclick="deleteNewDemandDetails($(this));"> <i
																	class="fa fa-trash"></i>
																</a>
															</c:if></td>

													</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.fund.release.req.newDemand.total" text="New Demand Total" /></label>
									<div class="col-sm-4">
										<form:input path="dto.newDemandTotal" id="newDemandTotal"  readonly="true"
											class="form-control " />
										

									</div>

									

								</div>
							</div>
						</div>


					</div>




				</div>
				
					<div class="">

						<apptags:CheckerAction  hideForward="true" hideSendback="true"
							hideUpload="true"></apptags:CheckerAction>
					</div>

				<div class="text-center padding-top-10">
						<button type="button" class="btn btn-success"
							title='<spring:message code="sfac.submit" text="Submit" />'
							onclick="saveFundReleaseReqApprovalData(this);">
							<spring:message code="sfac.submit" text="Submit" />
						</button>
									
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
					
				</div>

			</form:form>
		</div>
	</div>
</div>