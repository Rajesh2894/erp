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



<!-- Start Content here -->

<div class="content animated top">
	<div class="widget">


		<div class="widget-content padding">
			<form:form id="fpoPMMLInfo" action="FPOProfileManagementForm.html"
				method="post" class="form-horizontal">
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
									data-parent="#accordion_single_collapse" href="#mlInfoDiv">
									<spring:message code="sfac.fpo.pm.marketLinkage"
										text="Market Linkage" />
								</a>
							</h4>
						</div>
						<div id="mlInfoDiv" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="mlInfoTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>


											<th><spring:message code="sfac.cob.commodity.name"
													text="Commodity Name" /></th>

											<th><spring:message code="sfac.fpo.pm.marketPlace"
													text="Market Place (Mandi/Supermart)" /></th>
											<th><spring:message code="sfac.fpo.pm.CommodityRate"
													text="Commodity Rate" /></th>



											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.marketLinkageInfoDTOs)>0 }">
												<c:forEach var="dto"
													items="${command.dto.marketLinkageInfoDTOs}"
													varStatus="status">
													<tr class="appendableMLDetails">
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" />
														<td><form:input
																path="dto.marketLinkageInfoDTOs[${d}].commodityName"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="commodityNameML${d}"
																class="form-control hasCharacter" maxlength="100" /></td>
														<td><form:input
																path="dto.marketLinkageInfoDTOs[${d}].marketPlace"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="marketPlace${d}" class="form-control alphaNumeric"
																maxlength="200" /></td>
														<td><form:input
																path="dto.marketLinkageInfoDTOs[${d}].commodityRate"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="commodityRateML${d}" class="form-control "
																onkeypress="return hasAmount(event, this, 10, 2)" /></td>



														<td class="text-center"><c:if
																test="${command.viewMode ne 'V'}">
																<a class="btn btn-blue-2 btn-sm addMLButton"
																	title='<spring:message code="sfac.fpo.add" text="Add" />'
																	onclick="addMLButton(this);"> <i
																	class="fa fa-plus-circle"></i></a>
																<a class='btn btn-danger btn-sm deleteMLDetails '
																	title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																	onclick="deleteMLDetails(this);"> <i
																	class="fa fa-trash"></i>
																</a>
															</c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableMLDetails">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNo${d}"
															value="${d+1}" disabled="true" />
													<td><form:input
															path="dto.marketLinkageInfoDTOs[${d}].commodityName"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="commodityNameML${d}"
															class="form-control hasCharacter" maxlength="100" /></td>
													<td><form:input
															path="dto.marketLinkageInfoDTOs[${d}].marketPlace"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="marketPlace${d}" class="form-control alphaNumeric"
															maxlength="200" /></td>
													<td><form:input
															path="dto.marketLinkageInfoDTOs[${d}].commodityRate"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="commodityRateML${d}" class="form-control "
															onkeypress="return hasAmount(event, this, 10, 2)" /></td>



													<td class="text-center"><c:if
															test="${command.viewMode ne 'V'}">
															<a class="btn btn-blue-2 btn-sm addMLButton"
																title='<spring:message code="sfac.fpo.add" text="Add" />'
																onclick="addMLButton(this);"> <i
																class="fa fa-plus-circle"></i></a>
															<a class='btn btn-danger btn-sm deleteMLDetails '
																title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																onclick="deleteMLDetails(this);"> <i
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

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#enaamInfoDiv">
									<spring:message code="sfac.fpo.pm.enaam.details"
										text="E-Naam Details" />
								</a>
							</h4>
						</div>
						<div id="enaamInfoDiv" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.fpo.registeredOnEnam" text="Registered On E-NAM" /></label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="fpoMasterDto.registeredOnEnam" class="form-control chosen-select-no-results"
											id="registeredOnEnam"
											disabled="${command.viewMode eq 'V' ? true : false }">
											<form:option value="0">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<apptags:input labelCode="sfac.fpo.userIdEnam"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										cssClass="mandColorClass " path="fpoMasterDto.userIdEnam"
										maxlegnth="50"></apptags:input>
								</div>
							</div>
						</div>
					</div>


				</div>

				<div class="text-center padding-top-10">
					<c:if test="${command.viewMode ne 'V'}">
						<button type="button" class="btn btn-success"
							title='<spring:message code="sfac.savencontinue" text="Save & Continue" />'
							onclick="saveMarketLinkageForm(this);">
							<spring:message code="sfac.savencontinue" text="Save & Continue" />
						</button>
					</c:if>
					<button type="button" class="btn btn-danger"
						onclick="navigateTab('transport-tab','transportInfo','')"
						title='<spring:message code="sfac.button.back" text="Back" />'>
						<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>