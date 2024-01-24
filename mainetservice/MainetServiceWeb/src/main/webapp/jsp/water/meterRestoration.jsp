
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {

						$('.lessthancurrdate').datepicker({
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true,
							maxDate : '-0d',
							yearRange : "-100:-0",
						});

						$('input[type=text]').attr('disabled', true);
						$("#resDate").attr('disabled', false);
						$("#connectionNo").attr('disabled', false);
						//$("#meterStatus").prop("disabled", true);
						$("#searchConnection").prop("disabled", true);
						var meter = $("#meteredConnection").val();
						var error = $("#hasErrorId").val();
						if (error == "Y") {
							$("#previousMeterCutOff").prop("disabled", false);
							$("#searchConnection").prop("disabled", false);
							if (meter == "Y") {
								$("#viewMeterDetail").prop('disabled', false);
								$("#resReading").prop("disabled", false);
								$("#newMeteredRes").prop("disabled", false);
							}
						}

						if ($("#newMeterCheckBoxId").val() == "Y") {
							$("#newMeterCost").attr('disabled', false);
							$("#newMeterMake").attr('disabled', false);
							$("#newMeterNo").attr('disabled', false);
							$("#ownership").attr('disabled', false);
							$("#maxMeterReading").attr('disabled', false);
						}

						$("#connectionNo").click(function() {
							$("#searchConnection").prop("disabled", false);
						});

						$("#viewMeterDetail").click(
								function() {
									_openPopUpForm('MeterRestoration.html',
											'getMeterDetails');
								});

						$("#previousMeterCutOff").click(
								function() {
									_openPopUpForm('MeterRestoration.html',
											'getPreviousMeterCutOff');
								});

						$("#searchConnection")
								.click(
										function() {

											$("#errorDivId").hide();

											if ($('#connectionNo').val() == "") {
												$("#errorDivId")
														.html(
																getLocalMessage("water.meterCutOff.validationMSG.connectionNo"))
														.show();
												return false;
											}

											var URL = 'MeterRestoration.html?getConnectionDetails';
											var requestData = {
												'connectionNo' : $(
														'#connectionNo').val(),
											};
											var result = __doAjaxRequest(URL,
													'POST', requestData, false,
													'json');

											if (result == "N") {
												$("#errorDivId")
														.html(
																getLocalMessage("water.meter.noResultFoundMSG"))
														.show();
												$('#connectionNo').val("");
												$('#consumerName').val("");
												$('#conCategory').val("");
												$('#conPremiseType').val("");
												$('#conSize').val("");
												$("#metered").val("");
											} else {
												var splitArray = [];
												splitArray = result.split(",");
												$('#consumerName').val(
														splitArray[0]);
												$('#conCategory').val(
														splitArray[1]);
												$('#conPremiseType').val(
														splitArray[2]);
												$('#conSize')
														.val(splitArray[3]);
												var meteredConn = splitArray[4];
												if (meteredConn == "Y") {
													$("#metered").prop(
															"checked", true);
													$("#newMeteredRes").prop(
															"disabled", false);
													$("#viewMeterDetail").attr(
															'disabled', false);
													//$("#maxMeterReading").attr('disabled',false);
													$("#resReading").attr(
															'disabled', false);

												} else {
													$("#metered").prop(
															"checked", false);
													$("#newMeteredRes").prop(
															"disabled", true);
													$("#viewMeterDetail").attr(
															'disabled', true);
													$("#maxMeterReading").attr(
															'disabled', true);
													$("#newMeterCost").attr(
															'disabled', true);
													$("#newMeterMake").attr(
															'disabled', true);
													$("#newMeterNo").attr(
															'disabled', true);
													$("#ownership").attr(
															'disabled', true);
													$("#resReading").attr(
															'disabled', true);
												}

												$("#previousMeterCutOff").prop(
														"disabled", false);
											}

										});

						$("#resetBtnId").click(function() {
							$("#resReading").val("");
							$("#remarks").val("");
							$("#newMeteredRes").val("");
							$("#resDate").val("");

						});

						$("#newMeteredRes").change(function() {
							if ($("#newMeteredRes").is(":checked")) {

								$("#newMeterCheckBoxId").val("Y");
								$("#newMeterCost").attr('disabled', false);
								$("#newMeterMake").attr('disabled', false);
								$("#newMeterNo").attr('disabled', false);
								$("#ownership").attr('disabled', false);
								$("#maxMeterReading").attr('disabled', false);
							} else {

								$("#newMeterCheckBoxId").val("N");
								$("#newMeterCost").attr('disabled', true);
								$("#newMeterMake").attr('disabled', true);
								$("#newMeterNo").attr('disabled', true);
								$("#ownership").attr('disabled', true);
								$("#maxMeterReading").attr('disabled', true);
								$("#newMeterCost").val("");
								$("#newMeterMake").val("");
								$("#newMeterNo").val("");
								$("#ownership").val("");
								$("#maxMeterReading").val("");

							}
						});

					});

	function saveMeterRestorationDetails(element) {

		return saveOrUpdateForm(element, "", 'AdminHome.html', 'saveform');
	}
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<!-- ============================================================== -->
<!-- Start Content here -->
<!-- ============================================================== -->
<div class="content">
	<!-- Start info box -->
	<div class="row">
		<div class="col-md-12">
			<div class="widget">
				<div class="widget-header">

					<h2>
						<spring:message code="water.meterRes.restoration" />
					</h2>
					<!-- <div class="additional-btn">
						<a href="#" data-toggle="tooltip" data-original-title="Help"><i
							class="fa fa-question-circle fa-lg"></i></a>
					</div> -->
					<apptags:helpDoc url="MeterRestoration.html"></apptags:helpDoc>
				</div>

				<div class="widget-content padding">
					<div class="mand-label clearfix">
						<span><spring:message code="water.fieldwith" /><i
							class="text-red-1">*</i> <spring:message code="water.ismandtry" />
						</span>
					</div>

					<div class="error-div alert alert-danger alert-dismissible"
						id="errorDivId" style="display: none;">
						<button type="button" class="close" onclick="closeOutErrBox()"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>

					<form:form action="MeterRestoration.html"
						class="form-horizontal form" name="frmMeterRestoration"
						id="meterRestoration">

						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<form:hidden path="" id="hasErrorId" value="${command.hasError}" />
						<form:hidden path="" id="meteredConnection"
							value="${command.meteredFlag}" />
						<form:hidden path="newMeterValidation" id="newMeterCheckBoxId" />

						<div class="panel-group accordion-toggle"
							id="accordion_single_collapse">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse"
											href="#restoration_details"><spring:message code="water.dataentry.meter.details" /></a>
									</h4>
								</div>
								<div id="restoration_details" class="panel-collapse collapse in">
									<div class="panel-body">



										<div class="form-group">
											<label class="col-sm-2 control-label required-control"><spring:message
													code="water.ConnectionNo"></spring:message></label>
											<div class="col-sm-4">
												<form:input path="meterCutOffResDTO.connectionNo"
													type="text" class="form-control" id="connectionNo"></form:input>
											</div>
											<div class="col-sm-6">
												<button type="button" value="Search"
													class="btn btn btn-blue-3" id="searchConnection">
													<i class="fa fa-search"> </i> <spring:message code="water.nodues.search" />
												</button>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label "><spring:message
													code="water.consumerName" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control"
													path="consumerName" id="consumerName"></form:input>
											</div>
											<label class="col-sm-2 control-label"><spring:message
													code="water.connectiondetails.tariffcategory" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control"
													path="tarrifCategory" id="conCategory"></form:input>
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-2 control-label"><spring:message
													code="water.connectiondetails.premisetype" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control"
													path="premiseType" id="conPremiseType"></form:input>
											</div>
											<label class="col-sm-2 control-label"><spring:message
													code="water.ConnectionSize" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control"
													path="connectionSize" id="conSize"></form:input>
											</div>
										</div>


										<div class="form-group">
											<div class="col-sm-6">
												<label class="checkbox-inline"> <form:checkbox
														path="meteredFlag" value="Y" id="metered" disabled="true" />
													<spring:message code="water.meter.meteredCheckBox" />
												</label>
											</div>
										</div>




										<div class="text-center">
											<button type="button" value=View class="btn btn-blue-2"
												id="viewMeterDetail" disabled="disabled">
												<spring:message code="water.met.det" />
											</button>
											<button type="button" value=View class="btn btn-blue-2"
												id="previousMeterCutOff" disabled="disabled">
												<spring:message code="water.meter.cututOffPreviousDetails" />
											</button>
										</div>


									</div>
								</div>
							</div>



							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" class="collapsed"
											data-parent="#accordion_single_collapse"
											href="#meter-details"><spring:message
												code="water.meterRes.details" /></a>
									</h4>
								</div>
								<div id="meter-details" class="panel-collapse collapse">
									<div class="panel-body">


										<div class="form-group">
											

											<div class="col-sm-6">
												<label class="checkbox-inline"> <form:checkbox
														path="newMeterResFlag" value="Y" id="newMeteredRes"
														disabled="true" /> <spring:message
														code="water.meterRes.resWithNewMeter" />
												</label>
											</div>
											<label class="col-sm-2 control-label required-control "><spring:message
													code="water.meterRes.ownership" /></label>
											<div class="col-sm-4">
												<c:set var="baseLookupCode" value="WMO" />
												<form:select path="newMeterMasDTO.mmOwnership"
													class="form-control" id="ownership" disabled="true">
													<form:option value="0">
														<spring:message code="water.select" />
													</form:option>
													<c:forEach items="${command.getLevelData(baseLookupCode)}"
														var="lookUp">
														<form:option value="${lookUp.lookUpId}"
															code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-2 control-label required-control "><spring:message
													code="water.meterRes.newMeterNo" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control "
													path="newMeterMasDTO.mmMtrno" id="newMeterNo"></form:input>
											</div>
											<label class="col-sm-2 control-label required-control"><spring:message
													code="water.meterRes.newMeterMake" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control"
													path="newMeterMasDTO.mmMtrmake" id="newMeterMake"></form:input>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"><spring:message
													code="water.meterRes.newMeterCost" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control hasDecimal"
													path="newMeterMasDTO.mmMtrcost" id="newMeterCost"></form:input>
											</div>
											<label class="col-sm-2 control-label required-control"><spring:message
													code="water.meter.cutOffMeterMaxReading" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control hasNumber"
													path="newMeterMasDTO.maxMeterRead" id="maxMeterReading"></form:input>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"><spring:message
													code="water.meterRes.restorationDate" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input path="meterCutOffResDTO.cutResDate"
														class="form-control lessthancurrdate" id="resDate" />
													<label class="input-group-addon" for="cutOffDate"><i
														class="fa fa-calendar"></i></label>
												</div>
											</div>
											<label class="col-sm-2 control-label required-control"><spring:message
													code="water.meterRes.restorationReading" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control hasNumber"
													path="meterCutOffResDTO.cutResRead" id="resReading"></form:input>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"><spring:message
													code="water.remark" /></label>
											<div class="col-sm-4">
												<form:textarea type="textarea"
													class="form-control maxLength100 "
													path="meterCutOffResDTO.cutResRemark" id="remarks"></form:textarea>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>


						<div class="text-center padding-top-10">
							<button type="button" class="btn btn-success btn-submit"
								id="submitBtn" onclick="saveMeterRestorationDetails(this);">
								<spring:message code="water.btn.submit" />
							</button>
							<button type="button" class="btn btn-danger" id="resetBtnId">
								<spring:message code="water.btn.reset" />
							</button>
							<%-- <apptags:backButton url="AdminHome.html"></apptags:backButton> --%>

						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
