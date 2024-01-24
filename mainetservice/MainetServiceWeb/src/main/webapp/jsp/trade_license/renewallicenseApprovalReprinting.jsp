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
	src="js/trade_license/renewalLicenseApproval.js"></script>
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
					<b><spring:message code="trade.applheader"></spring:message></b>
				</h2>

				<apptags:helpDoc url="LicenseGeneration.html"></apptags:helpDoc>
			</div>

			<div class="widget-content padding">

				<form:form method="POST"
					action="RenewalLicenseApprovalReprinting.html"
					class="form-horizontal" id="RenewalLicenseApprovalReprinting"
					name="RenewalLicenseApprovalReprinting">
					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv"></div>
						<form:hidden path="" id="removedIds" />

					</div>

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">

						<c:if test="${command.skdclEnvFlag ne 'Y'}">
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
													columnWidth="20%" />

												<%-- <th width="20%"><spring:message code="trade.item.value" /></th> --%>

											</tr>
										</thead>


										<tbody>

											<c:if
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

														<%-- <td><form:input
																path="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																type="text"
																class="form-control text-right unit required-control hasNumber"
																placeholder="00.00" readonly="true" id="trdUnit${d}" /></td> --%>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:if>


										</tbody>
									</table>
								</div>
							</div>
						</div>


						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="trade.receiptDetails" /></a>
							</h4>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">

									<c:forEach
										items="${command.tradeDetailDTO.tradeLicenseItemDetailDTO}"
										var="itemList" varStatus="loop">

										<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(${itemList.triCod1},${command.tradeDetailDTO.orgid})"
											var="catagory" />

									</c:forEach>

									<table class="table table-bordered">
										<tbody>
											<tr align="left">
												<td width="150px" rowspan="2">
													<spring:message code="license.type.cat" text="License Type/ License Category" /></td>
												<td colspan="2"><spring:message code="license.fee.details" text="Fees Details" /></td>
											</tr>
											<tr>
												<td><spring:message code="license.type" text="Type" /></td>
												<td><spring:message code="license.amt" text="Amount (Rs.)" /></td>
											</tr>
											<c:forEach items="${command.chargeDescAndAmount}"
												var="chargeMap" varStatus="loop">
												<tr>


													<c:set var="map" value="${command.chargeDescAndAmount}"
														scope="page" />
													<c:if test="${loop.index eq 0}">
														<td rowspan="${map.size()}">${catagory.lookUpDesc }</td>
													</c:if>
													<td>${chargeMap.key}</td>
													<td>${chargeMap.value}</td>

												</tr>
											</c:forEach>
											<tr>
												<td colspan="2"><spring:message code="trade.total.fees" text="Total Fees (Rs.)" /></td>
												<td>${command.rmAmount}</td>
											</tr>
										</tbody>
									</table>






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

									<%-- <div class="form-group">
						            
						            <label class="col-sm-2 control-label"><spring:message code="trade.paymentMode"/></label>
						              <div class="col-sm-4"><form:input path="" type="text" class="form-control" value="cash" readonly="true"/></div> 
						            </div> --%>


								</div>
							</div>
						</div>

						<div class="padding-top-10 text-center">
							<c:if test="${command.viewMode ne 'H'}">
								<button type="button" class="btn btn-success" id="submitForm"
									onclick="renewLicenseNumber(this);">
									<spring:message code="trade.transfer.approval.print" text="Reprint License" />
								</button>
							</c:if>

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

