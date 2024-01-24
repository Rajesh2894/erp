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
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.min.js"></script>
<script
	src="js/trade_license/changeInCategorySubcategoryForm.js"></script>



<script>
	$(function() {

		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
		/* maxDate : '0' */
		});
	});
</script>

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

				<form:form method="POST" action="ChangeInCategorySubcategoryForm.html"
					class="form-horizontal" id="changeInCategorySubcategoryEdit"
					name="changeInCategorySubcategoryEdit">
					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv" style="display:none;"></div>
							
					</div>

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						
						<div class="panel panel-default">

							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="trade.licenseDetails" /></a>
							</h4>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">
								
								
								<div class="form-group">
										<label id="licenseType"
											class="col-sm-2 control-label required-control"
											for="licenseType"><spring:message
												code="license.details.licenseType" /></label>
										<c:set var="baseLookupCode" value="LIT" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tradeMasterDetailDTO.trdLictype"
											cssClass="form-control" hasChildLookup="false" hasId="true"
											showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" showOnlyLabel="applicantinfo.label.title" />

									</div>
								
								<div id="licensePeriod" style="display:none;">
									<div class="form-group">


										<label class="col-sm-2 control-label required-control"
											for="licenseFromPeriod"><spring:message
												code="license.details.licenseFromPeriod" text="License From Period" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control mandColorClass datepicker"
													id="trdLicfromDate" 
													path="tradeMasterDetailDTO.trdLicfromDate"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

										<label class="col-sm-2 control-label required-control"
											for="licenseToPeriod"><spring:message
												code="license.details.licenseToPeriod" text="License To Period" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control mandColorClass datepicker"
													id="trdLictoDate"
													path="tradeMasterDetailDTO.trdLictoDate"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

									</div>
									</div>
									</div>
									</div>
									</div>
									
							
							<c:choose>

						<c:when test="${command.propertyActiveStatus eq 'Y'}">
									<div class="form-group">

									<label class="col-sm-2 control-label"><spring:message
										code="trade.license.propertyId" text="Property No." /></label>		
										<div class="col-sm-4">
										
										<div class="input-group">
												<form:input path="tradeMasterDetailDTO.pmPropNo" type="text"
													class="form-control" id="propertyNo"/>
												
												<a href="#" class="input-group-addon"
													onclick="getPropertyDetails(this);"><i class="fa fa-search"></i></a>
											</div>
										</div>	
										
										
									<label class="col-sm-2 control-label "><spring:message
											code="license.details.propertyOwnerName" text="Property Owner Name" /></label>
									<div class="col-sm-4">
										<form:input path="" id="primaryOwnerName" 
											class="form-control mandColorClass hasCharacter" value=""
											readonly="true" data-rule-required="" />
									</div>
															
									</div>
										
									<div class="form-group">
									
									<label class="col-sm-2 control-label"><spring:message
											code="license.details.usagetype" text="Property Usage Type" /></label>
									<div class="col-sm-4">
										<form:input path="" id="usage" 
											class="form-control mandColorClass hasCharacter" value=""
											readonly="true" data-rule-required="" />
									</div>
									
									
									
									<label class="col-sm-2 control-label"><spring:message
											code="license.details.outstandingtype" text="Property Outstanding Tax" /></label>
									<div class="col-sm-4">
										<form:input path="" id="totalOutsatandingAmt"
											class="form-control mandColorClass hasNumber text-right" value="" placeholder="00.00"
											readonly="true" data-rule-required="" />
									</div>
									
									</div>
									
									<div class="form-group">

										<apptags:input labelCode="license.details.businessName"
											cssClass="hasCharacter" isMandatory="true" isReadonly="true"
											path="tradeMasterDetailDTO.trdBusnm"></apptags:input>
											
									<label class="col-sm-2 control-label required-control"><spring:message
											code="license.details.businessAddress" text="Property Business Address" /></label>
									<div class="col-sm-4">
										<form:input path="" id="trdBusadd" 
											class="form-control mandColorClass" value=""
											readonly="true" data-rule-required="" />
									</div>

									</div>
									</c:when>
									<c:otherwise>
									
									<div class="form-group">

									<label class="col-sm-2 control-label"><spring:message
										code="trade.license.propertyId" text="Property No." /></label>		
										<div class="col-sm-4">
										<form:input path="tradeMasterDetailDTO.pmPropNo" id="propertyNo" 
											class="form-control" value=""
											readonly="" data-rule-required="" />
										
										</div>	
										
										
									<label class="col-sm-2 control-label "><spring:message
											code="license.details.propertyOwnerName" text="Property Owner Name" /></label>
									<div class="col-sm-4">
										<form:input path="tradeMasterDetailDTO.trdOwnerNm" id="primaryOwnerName" 
											class="form-control mandColorClass hasCharacter" value=""
											readonly="" data-rule-required="" />
									</div>
															
									</div>
										
									
									<div class="form-group">

										<apptags:input labelCode="license.details.businessName"
											cssClass="hasCharacter" isMandatory="true"  isReadonly="true"
											path="tradeMasterDetailDTO.trdBusnm"></apptags:input>
											
									<label class="col-sm-2 control-label required-control"><spring:message
											code="license.details.businessAddress" text="Property Business Address" /></label>
									<div class="col-sm-4">
										<form:input path="tradeMasterDetailDTO.trdBusadd" id="trdBusadd" 
											class="form-control mandColorClass" value=""
											readonly="true" data-rule-required="" />
									</div>

									</div>
									</c:otherwise>
									</c:choose>
										<div class="form-group">
									 
									 <c:set var="baseLookupCode" value="MWZ" />
											<apptags:lookupFieldSet cssClass="form-control required-control" 
											baseLookupCode="MWZ" hasId="true" pathPrefix="tradeMasterDetailDTO.trdWard" 
											hasLookupAlphaNumericSort="true" hasSubLookupAlphaNumericSort="true" showAll="false" isMandatory="true"/>
									
									
									</div>			
						
						
						<div class="panel panel-default">
						
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a2"> <spring:message
											code="trade.details.history" /></a>
								</h4>
								<div id="a2" class="panel-collapse collapse in">
									<div class="panel-body">
										<c:set var="d" value="0" scope="page"></c:set>
										<table
											class="table table-bordered  table-condensed margin-bottom-10"
											id="itemDetailss">
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
														
														<th width="20%"><spring:message code="trade.item.value"
															text="Value" /></th>


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
															<tr class="itemDetailClasses">
																<form:hidden
																	path="tradeDTO.tradeLicenseItemDetailDTO[${d}].triId"
																	id="triId${d}" />
																<apptags:lookupFieldSet baseLookupCode="ITC"
																	hasId="true" showOnlyLabel="false"
																	pathPrefix="tradeDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																	hasLookupAlphaNumericSort="true"
																	hasSubLookupAlphaNumericSort="true"
																	cssClass="form-control required-control cateSubcat"
																	 disabled="true" showAll="false" hasTableForm="true"
																	showData="true" />
																	
														<td><form:input
																path="tradeDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																type="text" disabled="true"
																class="form-control text-right unit required-control hasNumber"
																placeholder="Enter Item value" id="trdUnit${d}" /></td>


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
														<tr class="itemDetailClasses">
															<form:hidden
																path="tradeDTO.tradeLicenseItemDetailDTO[${d}].triId"
																id="triId${d}" />
															<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
																showOnlyLabel="false"
																pathPrefix="tradeDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																hasLookupAlphaNumericSort="true"
																hasSubLookupAlphaNumericSort="true" disabled="true"
																cssClass="form-control required-control cateSubcat"
																showAll="false" hasTableForm="true" showData="true" />
													<td><form:input
															path="tradeDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
															type="text" disabled="true"
															class="form-control text-right unit required-control hasNumber"
															placeholder="Enter Item value" id="trdUnit${d}" /></td>

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
						
						
						<!-- new entry -->
						
						<c:if test="${not empty command.tradeDTO}">
						<div class="panel panel-default">
						
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a4"> <spring:message
										code="trade.change.in.category.subcategory" /></a>
							</h4>
							<div id="a4" class="panel-collapse collapse in">
								<div class="panel-body">
									<c:set var="d" value="0" scope="page"></c:set>
									<table
										class="table table-bordered  table-condensed margin-bottom-10"
										id="itemDetails">
										<thead>
                                   
											<tr>
												<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
													showOnlyLabel="false"
													pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCategory"
													isMandatory="true" hasLookupAlphaNumericSort="true"
													hasSubLookupAlphaNumericSort="true" 
													cssClass="form-control required-control tradeCat" showAll="false"
													hasTableForm="true" showData="false" columnWidth="20%" />
                                                 <th width="20%"><spring:message code="trade.item.value" text="Value" /></th>
												
												<th width="5%"><a title="Add"
														class="btn btn-blue-2 btn-sm addItemCF" onclick=""><i
															class="fa fa-plus"></i></a></th>
															
												
											</tr>
										</thead>
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
																id="triId${d+1}" />
															<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
																showOnlyLabel="false" 
																pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCategory"
																isMandatory="true" hasLookupAlphaNumericSort="true"
																hasSubLookupAlphaNumericSort="true"
																cssClass="form-control required-control tradeCat"
																showAll="false" hasTableForm="true" showData="true" />

															<td><form:input
																	path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																	type="text"
																	class="form-control text-right unit required-control hasNumber"
																	placeholder="Enter Item value" id="trdUnit${d}" /></td>
															<td class="text-center"><a
																	href="javascript:void(0);"
																	class="btn btn-danger btn-sm delButton" id="deleteRow_"
																	onclick=""><i
																		class="fa fa-minus"></i></a></td>
															<%-- </c:if> --%>



														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="itemDetailClass">
														<form:hidden
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
															id="triId${d+1}" />
														<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
															showOnlyLabel="false" 
															pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCategory"
															isMandatory="true" hasLookupAlphaNumericSort="true"
															hasSubLookupAlphaNumericSort="true"
															cssClass="form-control required-control tradeCat" showAll="false"
															hasTableForm="true" showData="true" />


														<td><form:input
																path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																type="text"
																class="form-control text-right unit required-control hasNumber"
																placeholder="Enter Item value" id="trdUnit${d}" /></td>

														<%-- <td><form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triRate"
															type="text"
															class="form-control text-right unit required-control hasNumber"
															placeholder="00.00" readonly="true" id="triRate${d}" /></td> --%>
														<td class="text-center"><a href="javascript:void(0);"
															class="btn btn-danger btn-sm delButton"
															onclick=""><i
																class="fa fa-minus"></i></a></td>


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
						
 				<%-- <c:if test="${command.paymentCheck eq null}">
						<div class="padding-top-10 text-center">

							<button type="button" class="btn btn-success" id="continueForm"
								onclick="saveTradeLicenseForm(this);">
								<spring:message code="trade.proceed" />
							</button>
							
							<button type="button" class="btn btn-success" id="continueForm"
								onclick="getChecklistAndCharge(this);">
								<spring:message code="trade.proceed" />
							</button>

							<button type="button" class="btn btn-danger" id="back"
								onclick="backPage()">
								<spring:message code="trade.back"></spring:message>
							</button>
					</div>
					</c:if>		 --%>
					
			<!-- </div> -->
			
			
			<div class="padding-top-10 text-center">

							<button type="button" class="btn btn-success" id="continueForm"
								onclick="getChecklistAndCharges(this);">
								<spring:message code="trade.proceed" />
							</button>
							
							<%-- <button type="button" class="btn btn-success" id="continueForm"
								onclick="getChecklistAndCharge(this);">
								<spring:message code="trade.proceed" />
							</button> --%>

							<button type="button" class="btn btn-danger" id="back"
								onclick="window.location.href = 'CitizenHome.html'">
								<spring:message code="trade.back"></spring:message>
							</button>
					</div>
					</div>
					</form:form>
					</div>
					</div>
					</div>
					
					