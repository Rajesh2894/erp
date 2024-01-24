<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/trade_license/tradeManualReceiptEntryDetails.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="trade.tradeLicDetails" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="TradeManualReceiptEntry.html"
				class="form-horizontal" name="TradeManualReceiptEntryDetails"
				id="TradeManualReceiptEntryDetails">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


				<div class="panel-group accordion-toggle"
					id="#accordion_single_collapse">
					<div class="panel panel-default" id="paymentInfo">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#LicenseDetails"><spring:message
										code="trade.licenseDetails" text="License Details" /></a>
							</h4>
						</div>
						<div id="licDetails" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<apptags:input labelCode="license.details.newLicenseNo"
										path="tradeMasterDetailDTO.trdLicno" cssClass="mandColorClass"
										isReadonly="true"></apptags:input>
									<apptags:input labelCode="license.details.OldLicenseNo"
										path="tradeMasterDetailDTO.trdOldlicno"
										cssClass="mandColorClass" isReadonly="true"></apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="owner.details.name"
										path="ownerFullName" isDisabled="true"></apptags:input>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="panel-group accordion-toggle"
					id="#accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse"
									href="#PayableAmountDetails"><spring:message
										code="trade.receiptAmountDetails"
										text="Receipt Amount Details" /></a>
							</h4>
						</div>

						<div id="PayableAmountDetails" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group padding-top-10">
									<apptags:input labelCode="trade.manualRecNo"
										path="manualReceiptNo" isMandatory="true"
										cssClass="hasSpecialCharAndNumber form-control" maxlegnth="12"></apptags:input>

									<apptags:date labelCode="trade.manualRecDate"
										isMandatory="true" datePath="manualReceiptDate"
										fieldclass="lessthancurrdate"></apptags:date>
								</div>

								<div class="form-group padding-top-10">
									<apptags:input labelCode="trade.reciptAmount"
										path="receiptAmount" isMandatory="true" cssClass="hasNumber"
										maxlegnth="10"></apptags:input>

									<%-- <label for="reciptAmount"
										class="col-sm-2 control-label required-control"><spring:message
											code="trade.reciptAmount" text="Recipt Amount" /></label>
									<div class="col-sm-4">
										<form:input id="receiptAmount" path="receiptAmount"
											cssClass="form-control text-left"
											onkeypress="return hasAmount(event, this, 10, 2)" />
									</div> --%>
								</div>

								<div class="overflow margin-top-10">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><spring:message code="trade.serialNo" text="Sr No" /></th>
													<th><spring:message code="trade.docName"
															text="Document Name" /></th>
													<th><spring:message code="trade.uploadText"
															text="Upload" /></th>
												</tr>
												<tr>
													<td>1</td>
													<td><spring:message code="trade.manualReceipt" /></td>
													<td><div id="docs_0" class="">
															<apptags:formField fieldType="7" labelCode=""
																hasId="true" fieldPath="" isMandatory="false"
																showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE"
																maxFileCount="CHECK_LIST_MAX_COUNT"
																validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																currentCount="0" />
														</div></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>

								<div class="panel panel-default">
									<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
								</div>
								<div class="text-center padding-top-10">
									<button type="button" class="btn btn-success btn-submit"
										onclick="saveManualEntry(this)">
										<spring:message code="bt.save" />
									</button>

									<button type="button" class="btn btn-danger"
										onclick="backToManualReceipt(this)">
										<spring:message code="bt.backBtn" />
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>

			</form:form>
		</div>
	</div>
</div>
