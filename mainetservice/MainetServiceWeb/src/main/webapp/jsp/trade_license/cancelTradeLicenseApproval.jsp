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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/trade_license/cancellationLicenseReport.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>



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

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="" text="License Cancellation"></spring:message></b>
				</h2>

				<apptags:helpDoc url="CancellationLicense.html"></apptags:helpDoc>
			</div>

			<div class="widget-content padding">

				<form:form method="POST" action="CancellationLicense.html"
					class="form-horizontal" id="cancellationLicense"
					name="cancellationLicense">
					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv"></div>
						<form:hidden path="" id="removedIds" />

					</div>

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">


						<c:if test="${fn:length(command.tbLoiMas) > 0}">
							<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a3"> <spring:message
											code="trade.license.loiDetails" /></a>
								</h4>
								<div id="a3" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="form-group">
											<label class="col-sm-2 control-label"><spring:message
													code="trade.loino" /></label>
											<div class="col-sm-4">
												<form:input path="tbLoiMas[0].loiNo" type="text"
													class="form-control" readonly="true" />
											</div>
											<label class="col-sm-2 control-label"><spring:message
													code="trade.loiDate" /></label>
											<div class="col-sm-4">
												<form:input path="loiDateDesc" type="text"
													class="form-control" readonly="true" />
											</div>
										</div>

									</div>
								</div>
							</div>
						</c:if>
						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="trade.details" /></a>
							</h4>
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">
									<c:set var="d" value="0" scope="page"></c:set>
									<table
										class="table table-bordered  table-condensed margin-bottom-10"
										id="itemDetails">
										<thead>

											<tr>
												<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
													showOnlyLabel="false"
													pathPrefix="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
													isMandatory="true" hasLookupAlphaNumericSort="true"
													hasSubLookupAlphaNumericSort="true"
													cssClass="form-control required-control" showAll="false"
													disabled="true" hasTableForm="true" showData="false"
													columnWidth="23%" />
												<th width="8%"><spring:message code="trade.item.value" /></th>
												<!-- #122717 License fee field is not required on Approval-->
                                       <%-- 
												<c:if test="${fn:length(command.tbLoiMas) > 0}">
													<th width="8%"><spring:message code="trade.rate"></spring:message><span
														class="mand">*</span></th>
												</c:if> --%>
											</tr>
										</thead>
										<!-- #122717 License fee field is not required on Approval-->
                                  <%-- 
										<c:if test="${fn:length(command.tbLoiMas) > 0}">
											<tfoot>
												<tr>
													<th colspan="5" class="text-right"><spring:message
															code="trade.total" /></th>
													<th colspan="1" class="text-right"><form:input
															path="tbLoiMas[0].loiAmount" id="totalitemDetail"
															cssClass="form-control text-right" readonly="true"
															placeholder="00.00" /></th>
													<!-- <th colspan="1" class="text-right"></th> -->

												</tr>
											</tfoot>
										</c:if> --%>

										<tbody>
											<c:choose>
												<c:when
													test="${fn:length(command.tradeDetailDTO.tradeLicenseItemDetailDTO) > 0}">
													<c:forEach var="taxData"
														items="${command.tradeDetailDTO.tradeLicenseItemDetailDTO}"
														varStatus="status">
														<tr class="itemDetailClass">
															<form:hidden
																path="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
																id="triId${d}" />
															<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
																showOnlyLabel="false"
																pathPrefix="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																isMandatory="true" hasLookupAlphaNumericSort="true"
																hasSubLookupAlphaNumericSort="true"
																cssClass="form-control required-control" disabled="true"
																showAll="false" hasTableForm="true" showData="true" />
															<td><form:input
																	path="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																	type="text"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	class="form-control text-right unit required-control hasNumber"
																	placeholder="Enter Item value" readonly=""
																	id="trdUnit${d}" /></td>
																	
															<%-- <c:if test="${fn:length(command.tbLoiMas) > 0}">
																<td><form:input
																		path="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triRate"
																		type="text"
																		class="form-control text-right unit required-control hasNumber"
																		placeholder="00.00" readonly="true" id="triRate${d}" /></td>
															</c:if> --%>

														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="itemDetailClass">
														<form:hidden
															path="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
															id="triId${d}" />
														<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
															showOnlyLabel="false"
															pathPrefix="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
															isMandatory="true" hasLookupAlphaNumericSort="true"
															hasSubLookupAlphaNumericSort="true"
															cssClass="form-control required-control " showAll="false"
															hasTableForm="true" showData="true" />
														<td><form:input
																path="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																type="text"
																disabled="${command.viewMode eq 'V' ? true : false }"
																class="form-control text-right unit required-control hasNumber"
																placeholder="Enter Item value" readonly=""
																id="trdUnit${d}" /></td>



                                                        
														<%-- <c:if test="${fn:length(command.tbLoiMas) > 0}">
															<td><form:input
																	path="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triRate"
																	type="text"
																	class="form-control text-right unit required-control hasNumber"
																	placeholder="00.00" readonly="true" id="triRate${d}" /></td>
														</c:if> --%>

													</tr>

													<c:set var="d" value="${d + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<c:if test="${fn:length(command.tbLoiMas) > 0}">

							<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a1"> <spring:message
											code="trade.receiptDetails" /></a>
								</h4>
								<div id="a1" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="form-group">
											<label class="col-sm-2 control-label"><spring:message
													code="trade.receiptno" /></label>
											<div class="col-sm-4">
												<form:input path="rmRcptno" type="text" class="form-control"
													readonly="true" />
											</div>
											<label class="col-sm-2 control-label"><spring:message
													code="trade.receiptAmt" /></label>
											<div class="col-sm-4">
												<form:input path="rmAmount" type="text"
													class="form-control text-right" readonly="true" />
											</div>
										</div>



									</div>
								</div>
							</div>
						</c:if>

						<div class="padding-top-10 text-center">

							<button type="button" class="btn btn-success" id="submitForm"
								onclick="cancelLicenseNumber(this);">
								<spring:message code="" text="Cancel License" />
							</button>

							<button type="button" class="btn btn-danger" id="back"
								onclick="backPage()">
								<spring:message code="trade.back"></spring:message>
							</button>


						</div>
					</div>

				</form:form>
			</div>
		</div>
	</div>
</div>

