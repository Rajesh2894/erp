<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/legal/judgementImplementation.js"></script>
<script src="js/mainet/validation.js"></script>
<!-- End JSP Necessary Tags -->
<script>
    function closeCurrentTab() {
	close();
    }
</script>
<div class="content">
	<div class="widget">

		<div class="widget-header">
			<h2>
				<spring:message code="judgement.imple.form.title" text="Judgement Implementation Form" />
			</h2>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">

				<span><spring:message code="legal.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="legal.mand.field"
						text="is mandatory"></spring:message></span>
			</div>
			<form:form action="JudgementImplementation.html"
				name="JudgementImplementation" id="JudgementImplementation"
				class="form-horizontal" commandName="command">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="caseEntryDTO.cseId" id="cseId" />
				<form:hidden path="removeAttendeeId" id="removeAttendeeId" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>









				<div class="panel-group accordion-toggle" id="caseHearingTable">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="lgl.hearingdet" text="Hearing Details" /><small class="text-blue-2 margin-left-5"> <spring:message
															code="legal.doc.msg" text=" (Multiple documents/images up to 1 MB can be upload. Only pdf,jpg,png is allowed.)" /></small>
								</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="table-responsive">
									<table id="hearingdetailsTable"
										class="table table-bordered table-striped">
										<thead>

											<tr>
												<th><spring:message code="label.checklist.srno"
														text="Sr.No." /></th>
												<th><spring:message code="CaseHearingDTO.hrDate"
														text="Hearing Date" /></th>
												<th><spring:message code="CaseHearingDTO.hrStatus"
														text="Status" /></th>
												<th><spring:message code="CaseHearingDTO.hrPreparation"
														text="Preparation" /></th>
												<th><spring:message code="lgl.document.uploadDocuments"
														text="Upload Documents" /></th>

											</tr>
										</thead>
										<tfoot>

										</tfoot>
										<tbody>
											<c:set value="0" var="e" scope="page"></c:set>
											<c:forEach items="${command.caseHearingListHistory}"
												var="data" varStatus="index">
												<tr>
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sequence${e}"
															value="${e+1}" disabled="true" /></td>
													<td><form:input
															path="caseHearingListHistory[${e}].hrDate"
															cssClass="form-control"
															id="hrDate${e}" disabled="true" /></td>
													<td class="text-center"><c:set var="baseLookupCode"
															value="HSC" /> <form:select
															path="caseHearingListHistory[${e}].hrStatus"
															cssClass="form-control mandColorClass" id="hrStatus${e}"
															data-rule-required="true"
															disabled="true" 
															>
															<form:option value="0"
															disabled="true" 
															>
																<spring:message code="selectdropdown" text="select" />
															</form:option>
															<c:forEach
																items="${command.getSortedLevelData(baseLookupCode)}"
																var="lookUp"
																
																>
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
															</c:forEach>
														</form:select></td>
													<td class="text-center"><form:input
															path="caseHearingListHistory[${e}].hrPreparation"
															class="form-control" id="hrPreparation${e}" 
															disabled="true" 
															/></td>
													<td class="text-center"><c:set var="count"
															value="${index.index}" /> <c:set var="lookUp"
															value="${command.attachDocsList[count]}" /> <c:if
															test="${lookUp ne null }">
															<apptags:filedownload filename="${lookUp.attFname}"
																filePath="${lookUp.attPath}"
																actionUrl="JudgementImplementation.html?Download" />
														</c:if></td>
												</tr>

												<c:set var="e" value="${e + 1}" scope="page" />
											</c:forEach>

										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center padding-bottom-10">

					<%-- <apptags:backButton url="JudgementImplementation.html?EDIT"></apptags:backButton> --%>
					<%-- <button type="button" class="btn btn-danger" data-toggle="tooltip"
						data-original-title="back" onclick="editJudgeImplementation('${caseEntryDTO.cseId}','JudgementImplementation.html','EDIT','V')">
						<i class="fa fa-chevron-circle-left padding-right-5"
							aria-hidden="true"></i>Back
					</button> --%>
				</div>
			</form:form>
		</div>
	</div>
</div>
