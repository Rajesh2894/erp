<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="js/buildingplan/newTCPLicenseForm.js"></script>
<div class="pagediv">
	<div class="content animated top">
		<div class="widget">
			<div class="widget-content padding">
				<form:form id="applicantFeesForm"
					action="NewTCPLicenseForm.html" method="post"
					class="form-horizontal">
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
						
					<%-- <div class="form-group">
						<label class="col-sm-2 control-label" for="appNo"><spring:message
								code="" text="Application Number" /></label>
						<div class="col-sm-4 ">
							<form:input name="" type="text"
								class="form-control"
								path="" id="applicationNumber"
								maxlength="20"
								disabled="true" />
						</div>
					</div> --%>
					
					<h4>
						<spring:message
								code="" text="Component wise Fee/Charges" />
					</h4>

					<div class="form-group">
						<div class="overflow margin-top-10">
							<div class="table-responsive">
								<table class="table table-bordered table-wrapper">
									<thead>
										<tr>
											<th class="text-left"><spring:message code=""
													text="Fee" /></th>
											<th class="text-left"><spring:message code=""
													text="Amount(in ₹)" /></th>
											<th class="text-left"><spring:message code=""
													text="Calculations" /></th>
										</tr>
									</thead>
									<tbody>
										<c:if
											test="${not empty command.licenseApplicationMasterDTO.feeMasterDto}">
											<c:forEach var="feeMasterDto"
												items="${command.licenseApplicationMasterDTO.feeMasterDto}"
												varStatus="status">
												<tr>
													<td><form:input
															path="licenseApplicationMasterDTO.feeMasterDto[${status.index}].taxCategory"
															value="" class="form-control" disabled="true"
															id="dinNumberId${status.index}" minlength="20"
															maxlength="10" /></td>
													<td class=""><form:input
															path="licenseApplicationMasterDTO.feeMasterDto[${status.index}].feesStr"
															value="" class="form-control text-right" disabled="true"
															id="dinNumberId${status.index}" minlength="20"
															maxlength="10" /></td>
													<td><form:input
															path="licenseApplicationMasterDTO.feeMasterDto[${status.index}].calculation"
															value="${not empty feeMasterDto.calculation ? feeMasterDto.calculation : ''}"
															class="form-control hasNumber" disabled="true"
															id="dinNumberId${status.index}" minlength="20"
															maxlength="10" /></td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>

					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label sub-title"><spring:message
								code="" text="Scrutiny Fee (100%)" /></label>
						
						<div class="col-sm-2 ">
						<div class="input-group">
						<form:input name="" type="text"
								class="form-control alphaNumeric preventSpace required-control text-right"
								path="licenseApplicationMasterDTO.scrutinyFeeStr" id="scrutinyFee" value=""
								maxlength="20"
								disabled="true" />
						<label class="input-group-addon"><i class="fa fa-inr"></i><span
									class="hide"><spring:message
											code="" text="Rupees" /></span></label>
						</div>
						</div>
						
						<label class="col-sm-2 control-label sub-title" ><spring:message
								code="" text="Licence Fees (25%)" /></label>
						
						<div class="col-sm-2 ">
						<div class="input-group">
							<form:input name="" type="text"
								class="form-control alphaNumeric preventSpace required-control text-right"
								path="licenseApplicationMasterDTO.licenseFeeStr" id="licFee"
								maxlength="20"
								disabled="true" />
							<label class="input-group-addon"><i class="fa fa-inr"></i><span
								class="hide"><spring:message
										code="" text="Rupees" /></span></label>
						</div>
						</div>

						<label style="" class="col-sm-2 control-label sub-title "><spring:message
								code="" text="Total Payable" /></label>
							<div class="col-sm-2 ">
							<div class="input-group">
							<form:input name="" type="text"
								class="form-control alphaNumeric preventSpace required-control text-right"
								path="licenseApplicationMasterDTO.totalFeesStr" id="totalFee"
								maxlength="20"
								disabled="true" />
							<label class="input-group-addon"><i class="fa fa-inr"></i><span
									class="hide"><spring:message
											code="" text="Rupees" /></span></label>
							</div>
							</div>

					</div>
					
					<h4>
						<spring:message
								code="" text="Undertakings" />
					</h4>

					<div class="form-group">
						<div class="col-sm-12 ">
						<input type="checkbox" required="" id="undertakingCheck">
						<label class=" check-header" for="ownerTypeId"><spring:message
								code=""
								text="I hereby declare
								that the details furnished above are true and correct to the best
								of my knowledge." /></label>
						</div>
					</div>




					<div class="text-center">
						<c:if test="${command.saveMode ne 'V'}">
							<button type="button" class="button-input btn btn-success"
								name="button" value="Save" onclick="saveFinalApp(this);" id="">
								<spring:message code="" text="Submit" />
							</button>
						</c:if>

						<button type="button" class="btn btn-danger"
							onclick="showTab('#detailsOfLand')" name="button"
							value="Back">
							<spring:message code="" text="Back" />
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>