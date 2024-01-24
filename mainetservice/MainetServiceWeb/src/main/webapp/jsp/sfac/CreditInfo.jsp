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
<script type="text/javascript" src="js/sfac/fpoProfileManagement.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />


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

<div class="content animated top">
	<div class="widget">
		

		<div class="widget-content padding">
			<form:form id="fpoPMCreditInfo"
				action="FPOProfileManagementForm.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="fpmId" id="fpmId" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>




				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">



					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#creditInfoDiv">
									<spring:message code="sfac.fpo.pm.creditInfo"
										text="Credit Information" />
								</a>
							</h4>
						</div>
						<div id="creditInfoDiv" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="creditInfoTable">
									<thead>
										<tr>
											<th width="6%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
											<th  width="8%"><spring:message code="sfac.cob.financial.year"
													text="Financial Year" /></th>

											<th width="8%"><spring:message code="sfac.fpo.pm.loanApp"
													text="Loan Applied (If Any)" /></th>
											<th><spring:message code="sfac.fpo.pm.loanAppAmt"
													text="Amount of Loan Applied (INR)" /></th>
											<th><spring:message code="sfac.fpo.pm.loanPurpose"
													text="Purpose of Loan Applied" /></th>
											<th><spring:message code="sfac.fpo.pm.instAvailed"
													text="Institution Availed" /></th>
													
											<th width="8%"><spring:message code="sfac.fpo.pm.loanSaction"
													text="Loan Sanctioned" /></th>
											<th width="8%"><spring:message code="sfac.fpo.pm.loanDisburs"
													text="Loan Disbursed" /></th>
											<th width="8%"><spring:message code="sfac.fpo.pm.loanDisbursAmt"
													text="Loan Amount Disbursed (INR)" /></th>
											<th width="8%"><spring:message code="sfac.fpo.pm.loanUtil"
													text="Utilisation of Loan" /></th>		
											<th width="5%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.creditInformationDetEntities)>0 }">
												<c:forEach var="dto"
													items="${command.dto.creditInformationDetEntities}"
													varStatus="status">
													<tr class="appendableCreditInfoDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" /></td>
														<td>	<form:select path="dto.creditInformationDetEntities[${d}].financialYear"
														disabled="${command.viewMode eq 'V' ? true : false }"
											id="financialYearCI${d}"
											cssClass="form-control chosen-select-no-results">
											<form:option value="0">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.faYears}" var="lookUp">
												<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
											</c:forEach>
										</form:select>
</td>

														<td>
															
																<c:set var="baseLookupCode" value="YNC" />
									<form:select path="dto.creditInformationDetEntities[${d}].loanApplied" class="form-control chosen-select-no-results"
									disabled="${command.viewMode eq 'V' ? true : false }"
										id="loanApplied${d}">
										<form:option value="0">
											<spring:message code="sfac.select" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
														

														</td>
														<td><form:input
																path="dto.creditInformationDetEntities[${d}].AmtOfLoanApplied" disabled="${command.viewMode eq 'V' ? true : false }"
																id="AmtOfLoanApplied${d}" class="form-control" onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																<td><form:input
																path="dto.creditInformationDetEntities[${d}].PurposeOfLoanApplied" disabled="${command.viewMode eq 'V' ? true : false }"
																id="PurposeOfLoanApplied${d}" class="form-control alphaNumeric" maxlength="200" /></td>
																
																<td><form:input
																path="dto.creditInformationDetEntities[${d}].institutionAvailed" disabled="${command.viewMode eq 'V' ? true : false }"
																id="institutionAvailed${d}" class="form-control alphaNumeric" maxlength="200"/></td>
																<td><form:input
																path="dto.creditInformationDetEntities[${d}].LoanSanctioned" disabled="${command.viewMode eq 'V' ? true : false }"
																id="LoanSanctioned${d}" class="form-control" onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																
																<td>
															
																<c:set var="baseLookupCode" value="YNC" />
									<form:select path="dto.creditInformationDetEntities[${d}].LOANDISBURSED" class="form-control chosen-select-no-results" disabled="${command.viewMode eq 'V' ? true : false }"
										id="LOANDISBURSED${d}">
										<form:option value="0">
											<spring:message code="sfac.select" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
														

														</td>
																
																<td><form:input
																path="dto.creditInformationDetEntities[${d}].loanAmountDisbursed" disabled="${command.viewMode eq 'V' ? true : false }"
																id="loanAmountDisbursed${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																<td><form:input
																path="dto.creditInformationDetEntities[${d}].utilizationOfLoan" disabled="${command.viewMode eq 'V' ? true : false }"
																id="utilizationOfLoan${d}" class="form-control alphaNumeric" maxlength="200"/></td>

													

														<td class="text-center">
														<c:if test="${command.viewMode ne 'V'}">
														<a
															class="btn btn-blue-2 btn-sm addCreditInfoButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addCreditInfoButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteCreditInfoDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteCreditInfoDetails(this);"> <i
																class="fa fa-trash"></i>
														</a></c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableCreditInfoDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" /></td>
														<td>	<form:select path="dto.creditInformationDetEntities[${d}].financialYear"
														disabled="${command.viewMode eq 'V' ? true : false }"
											id="financialYearCI${d}"
											cssClass="form-control chosen-select-no-results">
											<form:option value="0">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.faYears}" var="lookUp">
												<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
											</c:forEach>
										</form:select>
</td>

														<td>
															
																<c:set var="baseLookupCode" value="YNC" />
									<form:select path="dto.creditInformationDetEntities[${d}].loanApplied" class="form-control chosen-select-no-results"
									disabled="${command.viewMode eq 'V' ? true : false }"
										id="loanApplied${d}">
										<form:option value="0">
											<spring:message code="sfac.select" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
														

														</td>
														<td><form:input
																path="dto.creditInformationDetEntities[${d}].AmtOfLoanApplied" disabled="${command.viewMode eq 'V' ? true : false }"
																id="AmtOfLoanApplied${d}" class="form-control" onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																<td><form:input
																path="dto.creditInformationDetEntities[${d}].PurposeOfLoanApplied" disabled="${command.viewMode eq 'V' ? true : false }"
																id="PurposeOfLoanApplied${d}" class="form-control alphaNumeric" maxlength="200" /></td>
																
																<td><form:input
																path="dto.creditInformationDetEntities[${d}].institutionAvailed" disabled="${command.viewMode eq 'V' ? true : false }"
																id="institutionAvailed${d}" class="form-control alphaNumeric" maxlength="200" /></td>
																<td><form:input
																path="dto.creditInformationDetEntities[${d}].LoanSanctioned" disabled="${command.viewMode eq 'V' ? true : false }"
																id="LoanSanctioned${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)"/></td>
																
																<td>
															
																<c:set var="baseLookupCode" value="YNC" />
									<form:select path="dto.creditInformationDetEntities[${d}].LOANDISBURSED" class="form-control chosen-select-no-results"
									disabled="${command.viewMode eq 'V' ? true : false }"
										id="LOANDISBURSED${d}">
										<form:option value="0">
											<spring:message code="sfac.select" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
														

														</td>
																
																<td><form:input
																path="dto.creditInformationDetEntities[${d}].loanAmountDisbursed" disabled="${command.viewMode eq 'V' ? true : false }"
																id="loanAmountDisbursed${d}" class="form-control  " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																<td><form:input
																path="dto.creditInformationDetEntities[${d}].utilizationOfLoan" disabled="${command.viewMode eq 'V' ? true : false }"
																id="utilizationOfLoan${d}" class="form-control alphaNumeric"  maxlength="200"/></td>

													

														<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addCreditInfoButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addCreditInfoButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteCreditInfoDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteCreditInfoDetails(this);"> <i
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
						title='<spring:message code="sfac.savencontinue" text="Save & Continue" />'
						onclick="saveCreditInfoForm(this);">
						<spring:message code="sfac.savencontinue" text="Save & Continue" />
					</button>
					</c:if>
					<button type="button" class="btn btn-danger" onclick="navigateTab('license-tab','licenseInfo','')"
						title='<spring:message code="sfac.button.back" text="Back" />'>
					<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>