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
			<form:form id="fpoPMFinancialInfo"
				action="FPOProfileManagementForm.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


				<form:hidden path="viewMode" id="modeType" />
				<form:hidden path="fpoId" id="fpoId" />
				
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">



					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#finInfoDiv">
									<spring:message code="sfac.fpo.pm.finInfo"
										text="Financial Information of FPO" />
								</a>
							</h4>
						</div>
						<div id="finInfoDiv" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="finInfoTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
											<th><spring:message code="sfac.cob.financial.year"
													text="Financial Year" /></th>

											<th><spring:message code="sfac.fpo.pm.revenue"
													text="Revenue" /></th>
											<th><spring:message code="sfac.fpo.pm.bussActivity"
													text="Businessc Activities" /></th>
											<th><spring:message code="sfac.fpo.pm.noOfBeniFarmer"
													text="No Of Benificiaries Farmers" /></th>
											<th><spring:message code="sfac.fpo.pm.netProfit"
													text="Net Profit" /></th>
													<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
													
										
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.financialInformationDto)>0 }">
												<c:forEach var="dto"
													items="${command.dto.financialInformationDto}"
													varStatus="status">
													<tr class="appendableFinDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}"  disabled="true"/></td>
														<td>	<form:select path="dto.financialInformationDto[${d}].financialYear"
											id="financialYear${d}" disabled="${command.viewMode eq 'V' ? true : false }"
											cssClass="form-control chosen-select-no-results">
											<form:option value="0">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.faYears}" var="lookUp">
												<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
											</c:forEach>
										</form:select>
</td>

													
														<td><form:input
																path="dto.financialInformationDto[${d}].revenue" disabled="${command.viewMode eq 'V' ? true : false }"
																id="revenue${d}" class="form-control" onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																<td><form:input
																path="dto.financialInformationDto[${d}].businessActivities" disabled="${command.viewMode eq 'V' ? true : false }"
																id="businessActivities${d}" class="form-control alphaNumeric" /></td>
																
																<td><form:input
																path="dto.financialInformationDto[${d}].noBeneficiaryFarmers" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noBeneficiaryFarmers${d}" class="form-control hasNumber" maxlength="3" /></td>
																<td><form:input
																path="dto.financialInformationDto[${d}].netProfit" disabled="${command.viewMode eq 'V' ? true : false }"
																id="netProfit${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																
															

														<td class="text-center">
														<c:if test="${command.viewMode ne 'V'}">
														<a
															class="btn btn-blue-2 btn-sm addBankButton" 
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addFinButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteBankDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteFinDetails(this);"> <i
																class="fa fa-trash"></i>
														</a>
														</c:if>
														</td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableFinDetails">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNo${d}"
															value="${d+1}" disabled="true" /></td>
														<td>	<form:select path="dto.financialInformationDto[${d}].financialYear"
											id="financialYear${d}" disabled="${command.viewMode eq 'V' ? true : false }"
											cssClass="form-control chosen-select-no-results">
											<form:option value="0">
												<spring:message text="Select" code="sfac.select" />
											</form:option>
											<c:forEach items="${command.faYears}" var="lookUp">
												<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
											</c:forEach>
										</form:select>
</td>

													
														<td><form:input
																path="dto.financialInformationDto[${d}].revenue" disabled="${command.viewMode eq 'V' ? true : false }"
																id="revenue${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																<td><form:input
																path="dto.financialInformationDto[${d}].businessActivities" disabled="${command.viewMode eq 'V' ? true : false }"
																id="businessActivities${d}" class="form-control alphaNumeric" /></td>
																
																<td><form:input
																path="dto.financialInformationDto[${d}].noBeneficiaryFarmers" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noBeneficiaryFarmers${d}" class="form-control hasNumber" maxlength="3"/></td>
																<td><form:input
																path="dto.financialInformationDto[${d}].netProfit" disabled="${command.viewMode eq 'V' ? true : false }"
																id="netProfit${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																
													<td class="text-center">
													<c:if test="${command.viewMode ne 'V'}">
													<a
														class="btn btn-blue-2 btn-sm addBankButton"
														title='<spring:message code="sfac.fpo.add" text="Add" />'
														onclick="addFinButton(this);"> <i
															class="fa fa-plus-circle"></i></a> <a
														class='btn btn-danger btn-sm deleteBankDetails '
														title='<spring:message code="sfac.fpo.delete" text="Delete" />'
														onclick="deleteFinDetails(this);"> <i
															class="fa fa-trash"></i>
													</a></c:if></td>

												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:otherwise>
										</c:choose>

									</tbody>
								</table>
								<div>&nbsp;</div>
								<div>&nbsp;</div>
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.fpo.pm.otsi" text="Overall Turnover since inception (INR)" /></label>
									<div class="col-sm-4">
										<form:input
																path="dto.overallTurnOver" disabled="${command.viewMode eq 'V' ? true : false }"
																id="overallTurnOver" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" />
																
									</div>

									
								</div>
								
								
								<div class="form-group">
									
									<label class="col-sm-2 control-label "><spring:message
											code="sfac.fpo.pm.addSI" text="Additional_Shares_Issued" /></label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" /> <form:select
																path="dto.additionalSharesIssued"
																class="form-control chosen-select-no-results"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="costTypeId${d}">
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
									
									<label class="col-sm-2 control-label "><spring:message
											code="sfac.fpo.pm.dateIssue" text="Date Issued" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.dateIssued" type="text" disabled="${command.viewMode eq 'V' ? true : false }"
												class="form-control datepicker mandColorClass" 
												id="dateIssued" placeholder="dd/mm/yyyy"
												 readonly="true"/>
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>

								</div>


							</div>
						</div>
					</div>
					
			
					
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#OnOffFarmDiv">
									<spring:message code="sfac.fpo.pm.onoffFram"
										text="On/Off Farm Details" />
								</a>
							</h4>
						</div>
						<div id="OnOffFarmDiv" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.cob.commodity.name" text="Commodity Name" /></label>
									<div class="col-sm-4">
										<form:input
																path="dto.CommodityName" disabled="${command.viewMode eq 'V' ? true : false }"
																id="CommodityNameMst" class="form-control hasCharacter" maxlength="100" />
																
									</div>
									
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.pm.commodityQty" text="Commodity Qunatity" /></label>
									<div class="col-sm-4">
										<form:input
																path="dto.commodityQuanity" disabled="${command.viewMode eq 'V' ? true : false }"
																id="commodityQuanityMst" class="form-control hasNumber" maxlength="5" />
																
									</div>

									
								</div>
								
								
								<div class="form-group">
									
									<label class="col-sm-2 control-label "><spring:message
											code="sfac.fpo.pm.liveSN" text="Live stock Name" /></label>
									<div class="col-sm-4">
										<form:input
																path="dto.liveStockName" disabled="${command.viewMode eq 'V' ? true : false }"
																id="liveStockName" class="form-control hasCharacter"  maxlength="100"/>
									</div>
									
									<label class="col-sm-2 control-label "><spring:message
											code="sfac.fpo.pm.liveSQ" text="Live Stock Quantity" /></label>
									<div class="col-sm-4">
										<form:input
																path="dto.liveStockQuanity" disabled="${command.viewMode eq 'V' ? true : false }"
																id="liveStockQuanity" class="form-control hasNumber "  maxlength="5"/>
									</div>

								</div>


							</div>
						</div>
					</div>
					
					
					



				</div>

				<div class="text-center padding-top-10">
				
					<c:if test="${command.viewMode ne 'V'}">
					<button type="button" class="btn btn-success"
						title='<spring:message code="sfac.savencontinue" text="Save & Continue" />'
						onclick="saveFPOProfileMasterForm(this);">
						<spring:message code="sfac.savencontinue" text="Save & Continue" />
					</button>
					</c:if>
				<%-- 	<button type="button" class="btn btn-warning"
						title='<spring:message code="sfac.button.reset" text="Reset"/>'
						onclick="window.location.href ='FPOProfileManagementForm.html'">
						<spring:message code="sfac.button.reset" text="Reset" />
					</button> --%>
					<apptags:backButton url="FPOProfileManagementForm.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>