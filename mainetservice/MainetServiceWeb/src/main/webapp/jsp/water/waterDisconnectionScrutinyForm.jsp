
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/water/water-disconnection.js"></script>

<script>
	function openViewModeForm(obj) {

		var rowId = obj.split("E")[1];
		var row = jQuery('#gridWaterDisconnection').jqGrid('getRowData', rowId);
		var colData = row['csCcn'];
		$("#csIdn").val(colData);
		$.fancybox.close();

	}

	function saveFormDisconnection(element) {
		saveOrUpdateForm(element, 'Edited Successfully',
				'WaterDisconnectionForm.html?proceed', 'saveform');
		$("#editButtonId").show();

	}
	$(document).ready(
			function() {
				$(".panel-heading").hide();
				$('#submitButtonId').prop('disabled', true);
				$('input[type="text"],textarea,select,input[type="radio"]')
						.attr('disabled', true);

			});
	function editForm(obj) {
		$('#submitButtonId').prop('disabled', false);
		//	$('input[type="text"],textarea,select,input[type="radio"]').attr('disabled',false);
		var disc = $(' #discType option:selected').attr('code');
		if ("T" === disc) {
			document.getElementById("fromdate").disabled = false;
			document.getElementById("todate").disabled = false;
		}
		document.getElementById("discReason").disabled = false;
		document.getElementById("discType").disabled = false;
	}
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.breadcrumb.disconnect"
					text="Temporary permanent closing of water connection" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" /></span>
			</div>
			<form:form action="WaterDisconnectionForm.html"
				class="form-horizontal" name="frmwaterDisconnectionForm"
				id="frmwaterDisconnectionForm" method="post">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<h4 class="margin-top-0">
					<spring:message code="water.DeptCOO.AppliDtls"
						text="Applicant Details" />
				</h4>
				<apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail>


				<h4 class="margin-top-0">
					<spring:message code="water.disconnect.detail"
						text="Disconnection Details" />
				</h4>
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="water.ConnectionNo" text="Connection No." /></label>
					<div class="col-sm-4">
						<form:input path="connectionNo" id="csIdn" name="" readonly="true"
							type="text" class="form-control"></form:input>
					</div>

					<%-- <div class="col-sm-6">
           				 <button class="btn btn-success" onclick="_openPopUpForm('WaterDisconnectionForm.html','searchConnectionDetails');" type="button"><i class="fa fa-search"></i> <spring:message code="bt.search" text="Search"/></button>
         		   </div> --%>

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="water.consumerName" text="Consumer Name" /></label>
					<div class="col-sm-4">
						<form:input path="consumerName" cssClass="form-control"
							id="consumerName" disabled="true" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="water.areaName" text="Area Name" /></label>
					<div class="col-sm-4">
						<form:input path="areaName" cssClass="form-control"
							id="consumerAreaName" disabled="true" />

					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="water.disconnect.type" text="Disconnection Type" /></label>
					<c:set var="baseLookupCode" value="DIC" />
					<apptags:lookupField
						items="${command.getServiceLevelData(baseLookupCode)}"
						path="disconnectionEntity.discType" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="water.select" isMandatory="true" />

					<label class="col-sm-2 control-label required-control"><spring:message
							code="water.disconnect.reason" text="Disconnection Reason" /></label>
					<div class="col-sm-4">
						<form:textarea path="disconnectionEntity.discReason"
							id="discReason" name="textarea" class="form-control"></form:textarea>
					</div>
				</div>
				<fieldset id="tempDisconnection">
					<legend>
						<spring:message code="water.disconnect.temp"
							text="Temporary Disconnection" />
					</legend>
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="water.disconnect.from" text="From Date" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="disconnectionEntity.disconnectFromDate"
									cssClass="form-control Moredatepicker" id="fromdate" />
								<label for="datepicker" class="input-group-addon"><i
									class="fa fa-calendar"></i></label>
							</div>
						</div>
						<label class="col-sm-2 control-label required-control"><spring:message
								code="water.disconnect.to" text="To Date" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="disconnectionEntity.disconnectToDate"
									cssClass="form-control Mostdatepicker" id="todate" />
								<label for="datepicker2" class="input-group-addon"><i
									class="fa fa-calendar"></i></label>
							</div>
						</div>
					</div>
				</fieldset>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"><spring:message
							code="water.plumber.details" text="Plumber Details" /> </label>
					<div class="radio col-sm-2">
						<label><form:radiobutton path="ulbPlumber" value="Y"
								onclick="notUlbPlumber()" id="Yes" />
							<spring:message code="water.plumber.reg" text="ULB Registered" /></label>
					</div>
					<div class="radio col-sm-2">
						<label><form:radiobutton path="ulbPlumber" value="N"
								onclick="notUlbPlumber()" id="No" />
							<spring:message code="water.plumber.notreg"
								text="Not ULB Registered" /></label>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="water.plumber.licno" text="Plumber License No." /></label>
					<div class="col-sm-4">
						<form:input path="plumberLicence"
							cssClass="form-control" id="plumId" disabled="true" />
					</div>
				</div>
				<div class="form-group">
					<c:if test="${not empty command.documentList}">
						<fieldset class="fieldRound">
							<div class="overflow">
								<div class="table-responsive">
									<table class="table table-hover table-bordered table-striped">
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
													<td><label>${lookUp.clmSrNo}</label></td>
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
																actionUrl="WaterDisconnectionForm.html?Download"></apptags:filedownload>
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
				<div class="text-center padding-bottom-10">
					<button type="submit" class="btn btn-success btn-submit"
						onclick=" saveFormDisconnection(this);" id="submitButtonId">
						<spring:message code="water.btn.submit" />
					</button>
					<button type="button" class="btn btn-success"
						onclick="editForm(this)" id="editButtonId">
						<spring:message code="water.btn.edit" />
					</button>
				</div>

			</form:form>
			<div id="scrutinyDiv">
				<jsp:include page="/jsp/cfc/sGrid/scrutinyButtonTemplet.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
