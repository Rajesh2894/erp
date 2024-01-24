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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/trade_license/changeInCategorySubcategoryForm.js"></script>

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="trade.change.in.category.subcategory.form"></spring:message></b>
				</h2>

				<apptags:helpDoc url="ChangeInCategorySubcategoryForm.html"></apptags:helpDoc>
			</div>

			<div class="widget-content padding">

				<form:form action="ChangeInCategorySubcategoryForm.html"
					class="form-horizontal" id="changeInCategorySubcategory"
					name="changeInCategorySubcategory">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
		

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">

					<c:if test="${command.scrutunyEditMode ne 'SM'}">
						<div class="panel panel-default">
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">

										<label class="col-sm-2 control-label required-control"><spring:message
												code="license.no" text="License No." /></label>
										<div class="col-sm-4">

											<form:input path="tradeMasterDetailDTO.trdLicno" type="text"
												class="form-control" id="licenseNo" />

										</div>
									</div>
								</div>
							</div>
						</div>
						</c:if>

						<c:if test="${command.licenseDetails eq 'Y'}">
							<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a3"> <spring:message
											code="trade.licenseDetails" /></a>
								</h4>
								<div id="a3" class="panel-collapse collapse in">
									<div class="panel-body">


										<div class="form-group">

											<label class="col-sm-2 control-label required-control"><spring:message
													code="owner.details.name" text="owner name" /></label>
											<div class="col-sm-4">
												<form:input path="tradeMasterDetailDTO.trdOwnerNm"
													id="trdOwnerNm" class="form-control mandColorClass"
													value="" readonly="true" />
											</div>

											<label class="col-sm-2 control-label required-control"><spring:message
													code="license.details.businessAddress"
													text="Property Business Address" /></label>
											<div class="col-sm-4">
												<form:input path="tradeMasterDetailDTO.trdBusadd"
													id="trdBusadd" class="form-control mandColorClass" value=""
													readonly="true" />
											</div>

										</div>

										<div class="form-group">

											<c:set var="baseLookupCode" value="MWZ" />
											<apptags:lookupFieldSet
												cssClass="form-control required-control"
												baseLookupCode="MWZ" hasId="true"
												pathPrefix="tradeMasterDetailDTO.trdWard" disabled="true"
												hasLookupAlphaNumericSort="true"
												hasSubLookupAlphaNumericSort="true" showAll="false" />

										</div>

										<div class="form-group">

											<label class="col-sm-2 control-label required-control"
												for="licenseFromPeriod"><spring:message
													code="license.details.licenseFromPeriod"
													text="License From Period" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input class="form-control mandColorClass datepicker"
														id="licFromDate" readonly="true" path="licFromDateDesc"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>

											<label class="col-sm-2 control-label required-control"
												for="licenseToPeriod"><spring:message
													code="license.details.licenseToPeriod"
													text="License To Period" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input class="form-control mandColorClass datepicker"
														id="lLictoDate" readonly="true" path="licToDateDesc"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<c:if test="${command.tradeDTO ne null && not empty command.tradeDTO || command.scrutunyEditMode ne 'SM'}">


							<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a1"> <spring:message
											code="trade.details.history" /></a>
								</h4>
								<div id="a1" class="panel-collapse collapse in">
									<div class="panel-body">
										<c:set var="d" value="0" scope="page"></c:set>
										<table
											class="table table-bordered  table-condensed margin-bottom-10"
											id="itemDetails">
											<thead>

												<tr>
													<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
														showOnlyLabel="false"
														pathPrefix="tradeDTO.tradeLicenseItemDetailDTO[${d}].triCod"
														isMandatory="true" hasLookupAlphaNumericSort="true"
														hasSubLookupAlphaNumericSort="true"
														cssClass="form-control required-control" showAll="false"
														disabled="true" hasTableForm="true" showData="false"
														columnWidth="20%" />

                                                   <th width="20%"><spring:message code="" text="Item Value"/></th> 
													<%-- <th width="20%"><spring:message code="trade.rate"></spring:message><span
														class="mand">*</span></th> --%>
												</tr>
											</thead>

											<%-- <tfoot>
												<tr>
													<th colspan="2" class="text-right"><spring:message
															code="trade.total" /></th>
													<th colspan="1"><form:input path="tbLoiMas[0].loiAmount"
															id="totalitemDetail" cssClass="form-control text-right"
															readonly="true" placeholder="00.00" /></th>
													<th colspan="1" class="text-right"></th>

												</tr>
											</tfoot> --%>

											<tbody>
												<c:choose>
													<c:when
														test="${fn:length(command.tradeDTO.tradeLicenseItemDetailDTO) > 0}">
														<c:forEach var="taxData"
															items="${command.tradeDTO.tradeLicenseItemDetailDTO}"
															varStatus="status">
															<tr class="itemDetailClass">
																<form:hidden
																	path="tradeDTO.tradeLicenseItemDetailDTO[${d}].triId"
																	id="triId${d}" />
																<apptags:lookupFieldSet baseLookupCode="ITC"
																	hasId="true" showOnlyLabel="false"
																	pathPrefix="tradeDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																	hasLookupAlphaNumericSort="true"
																	hasSubLookupAlphaNumericSort="true"
																	cssClass="form-control required-control"
																	disabled="true" showAll="false" hasTableForm="true"
																	showData="true" />
                                                                <td><form:input
																		path="tradeDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																		type="text"
																		disabled="true"
																		class="form-control hasNumber"
																		placeholder="Enter Item Value"
																		id="trdUnit${d}" /></td>
                                                                
																<%-- <td><form:input
																	path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triRate"
																	type="text"
																	class="form-control text-right unit required-control hasNumber"
																	placeholder="00.00" readonly="true" id="triRate${d}" /></td>  --%>

															</tr>
															<c:set var="d" value="${d + 1}" scope="page" />
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr class="itemDetailClass">
															<form:hidden
																path="tradeDTO.tradeLicenseItemDetailDTO[${d}].triId"
																id="triId${d}" />
															<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
																showOnlyLabel="false"
																pathPrefix="tradeDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																hasLookupAlphaNumericSort="true"
																hasSubLookupAlphaNumericSort="true" disabled="true"
																cssClass="form-control required-control "
																showAll="false" hasTableForm="true" showData="true" />
																
																<td><form:input
																		path="tradeDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																		type="text"
																		disabled="true"
																		class="form-control hasNumber"
																		placeholder="Enter Item Value"
																		id="trdUnit${d}" /></td>
																

															<%-- <td><form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triRate"
															type="text"
															class="form-control text-right unit required-control hasNumber"
															placeholder="00.00" readonly="true" id="triRate${d}" /></td>  --%>

														</tr>

														<c:set var="d" value="${d + 1}" scope="page" />
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							</c:if>
							</c:if>

							<!-- for history change in category and sub category -->
							
							<c:if test="${command.editMode eq 'E'}">
							<div class="panel panel-default">
						
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a1"> <spring:message
											code="trade.change.in.category.subcategory" /></a>
								</h4>
								<div id="a1" class="panel-collapse collapse in">
									<div class="panel-body">
										<c:set var="d" value="0" scope="page"></c:set>
										<table
											class="table table-bordered  table-condensed margin-bottom-10"
											id="itemDetails">
											<thead>

												<tr>
													<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
														showOnlyLabel="false"
														pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
														isMandatory="true" hasLookupAlphaNumericSort="true"
														hasSubLookupAlphaNumericSort="true"
														cssClass="form-control required-control" showAll="false"
														disabled="true" hasTableForm="true" showData="false"
														columnWidth="20%" />
													
														<th width="20%"><spring:message code="" text="Item Value"/></th> 
														
														<c:if test="${command.scrutunyEditMode eq 'SM'}">
														<th width="5%">Select</th>
													    </c:if>


													<%-- <th width="20%"><spring:message code="trade.rate"></spring:message><span
														class="mand">*</span></th> --%>
												</tr>
											</thead>

											<%-- <tfoot>
												<tr>
													<th colspan="2" class="text-right"><spring:message
															code="trade.total" /></th>
													<th colspan="1"><form:input path="tbLoiMas[0].loiAmount"
															id="totalitemDetail" cssClass="form-control text-right"
															readonly="true" placeholder="00.00" /></th>
													<th colspan="1" class="text-right"></th>

												</tr>
											</tfoot> --%>

											<tbody>
												<c:choose>
													<c:when
														test="${fn:length(command.tradeMasterDetailDTO.tradeLicenseItemDetailDTO) > 0}">
														<c:forEach var="taxData"
															items="${command.tradeMasterDetailDTO.tradeLicenseItemDetailDTO}"
															varStatus="status">
															<tr class="itemDetailClass">
																<form:hidden
																	path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
																	id="triId${d}" />
																<apptags:lookupFieldSet baseLookupCode="ITC"
																	hasId="true" showOnlyLabel="false"
																	pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																	hasLookupAlphaNumericSort="true"
																	hasSubLookupAlphaNumericSort="true"
																	cssClass="form-control required-control"
																    showAll="false" hasTableForm="true" 
																	showData="true" />
																		<td><form:input
																		path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																		type="text"
																		
																		class="form-control text-right hasNumber"
																		placeholder="Enter Item Value"
																		id="trdUnit${d}" /></td>
																	<c:if test="${command.scrutunyEditMode eq 'SM'}">
																	<td class="text-center"><form:checkbox
																			path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].selectedItems"
																			id="selectedItems${d}" /></td>
																</c:if>

																<%-- <td><form:input
																	path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triRate"
																	type="text"
																	class="form-control text-right unit required-control hasNumber"
																	placeholder="00.00" readonly="true" id="triRate${d}" /></td>  --%>

															</tr>
															<c:set var="d" value="${d + 1}" scope="page" />
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr class="itemDetailClass">
															<form:hidden
																path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
																id="triId${d}" />
															<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
																showOnlyLabel="false"
																pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																hasLookupAlphaNumericSort="true"
																hasSubLookupAlphaNumericSort="true"
																cssClass="form-control required-control " 
																showAll="false" hasTableForm="true" showData="true" />
																	<td><form:input
																		path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																		type="text"
																		
																		class="form-control text-right hasNumber"
																		placeholder="Enter Item Value"
																		id="trdUnit${d}" /></td>
																
																<c:if test="${command.scrutunyEditMode eq 'SM'}">
																<td class="text-center"><form:checkbox
																		path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].selectedItems"
																		id="selectedItems${d}" /></td>
															</c:if>

															<%-- <td><form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triRate"
															type="text"
															class="form-control text-right unit required-control hasNumber"
															placeholder="00.00" readonly="true" id="triRate${d}" /></td>  --%>

														</tr>

														<c:set var="d" value="${d + 1}" scope="page" />
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							</c:if>
							<%-- </c:if> --%>
						<%-- </c:if> --%>

						<c:if
							test="${command.paymentCheck ne null && command.paymentCheck eq 'Y' || command.scrutunyEditMode ne 'SM'}">

							<!---------------------------------------------------------------document upload start------------------------ -->
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title table" id="">
										<a data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse1" href="#a5"><spring:message
												text="Document Upload Details" /></a>
									</h4>
								</div>
								<div class="form-group">
									<c:if test="${not empty command.documentList}">
										<fieldset class="fieldRound">
											<div class="overflow">
												<div class="table-responsive">
													<table
														class="table table-hover table-bordered table-striped">
														<tbody>
															<tr>
																<th><label class="tbold"><spring:message
																			code="tp.serialNo" text="Sr No" /></label></th>
																<th><label class="tbold"><spring:message
																			code="tp.docName" text="Document Name" /></label></th>
																<th><label class="tbold"><spring:message
																			code="water.download" /></label></th>
															</tr>

															<c:forEach items="${command.documentList}" var="lookUp"
																varStatus="lk">
																<tr>
																	<td><label>${lk.count}</label></td>
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<td><label>${lookUp.clmDescEngl}</label></td>
																		</c:when>
																		<c:otherwise>
																			<td><label>${lookUp.clmDesc}</label></td>
																		</c:otherwise>
																	</c:choose>
																	<td>

																		<div>
																			<apptags:filedownload filename="${lookUp.attFname}"
																				filePath="${lookUp.attPath}"
																				actionUrl="AdminHome.html?Download"></apptags:filedownload>
																		</div>

																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</fieldset>
									</c:if>
								</div>
							</div>

									<!---------------------------------------------------------------document upload end------------------------ -->
									<%-- <div class="form-group">
										<label class="col-sm-2 control-label"><spring:message
												code="water.field.name.amounttopay" /></label>
										<div class="col-sm-4">
											<form:input class="form-control" path=""
												value="${command.offlineDTO.amountToShow}" maxlength="12"
												readonly="true"></form:input>
											<a class="fancybox fancybox.ajax text-small text-info"
												href="ChangeInCategorySubcategoryForm.html?showChargeDetailsMarket"><spring:message
													code="water.lable.name.chargedetail" /> <i
												class="fa fa-question-circle "></i></a>
										</div>
									</div> --%>
								


							<%-- <jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" /> --%>

							<%-- <c:if test="${command.scrutunyEditMode eq 'SM'}">
							<div class="padding-top-10 text-center">

								<button type="button" class="btn btn-success" id="continueForm"
									onclick="generateChallanAndPayment(this);">
									<spring:message code="trade.submit" />
								</button>

								<button type="button" class="btn btn-danger" id="back"
									onclick="backPage()">
									<spring:message code="trade.back"></spring:message>
								</button>

							</div>
							</c:if> --%>

						</c:if>

						<%-- <c:if test="${command.licenseDetails eq null}"> --%>
						<%-- <c:if test="${command.scrutunyEditMode ne 'SM'}">
							<div class="padding-top-10 text-center">

								<button type="button" class="btn btn-success" id="continueForm"
									onclick="getLicenseDetails(this);">
									<spring:message code="trade.search" />
								</button>

								<button type="button" class="btn btn-danger" id="back"
									onclick="backPage()">
									<spring:message code="trade.back"></spring:message>
								</button>
								
								<button type="button" class="btn btn-danger" id="back"
							onclick="window.location.href = 'AdminHome.html'">
							<spring:message code="trade.back"></spring:message>
						</button>

								<button type="Reset" class="btn btn-warning" id="resetform"
									onclick="resetDuplicateForm(this)">
									<spring:message code="trade.reset" />
								</button>

							</div>
						</c:if> --%>

					<%-- 	<c:if test="${command.checklistCheck eq 'Y'}">
							<div class="padding-top-10 text-center">

								<button type="button" class="btn btn-success" id="continueForm"
									onclick="getChecklistAndCharges(this);">
									<spring:message code="trade.proceed" />
								</button>

								<button type="button" class="btn btn-danger" id="back"
									onclick="window.location.href = 'AdminHome.html'">
									<spring:message code="trade.back"></spring:message>
								</button>
								
								<button type="button" class="btn btn-danger" id="backEdit"
										onclick="editApplication(this);">
										<spring:message code="trade.editappln"></spring:message>
									</button>

							</div>
						</c:if> --%>
						
						<c:if test="${command.scrutunyEditMode eq 'SM'}">

								<div class="text-center padding-bottom-10">
									<button type="button" class="btn btn-success"
										onclick="editCategoryForm(this)" id="editButtonId">
										<spring:message text="save" code="trade.proceed" />
									</button>
								</div>
							</c:if>
						
					</div>
				</form:form>
				
				<div class="text-center padding-bottom-10" id="scrutinyDiv">
					<jsp:include page="/jsp/cfc/sGrid/scrutinyButtonTemplet.jsp"></jsp:include>
				</div>
				
			</div>
		</div>
	</div>
</div>

