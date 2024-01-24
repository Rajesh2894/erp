<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/water/change-usage.js"></script>

<script type="text/javascript">
    function saveData(element) {
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
		":checked").val() == 'Y') {
	    return saveOrUpdateForm(
		    element,
		    "Your application for change Of ownership saved successfully!",
		    'ChangeOfOwnership.html?redirectToPay', 'saveform');
	} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
		.filter(":checked").val() == 'N') {
	    return saveOrUpdateForm(
		    element,
		    "Your application for change Of ownership saved successfully!",
		    'ChangeOfOwnership.html?PrintReport', 'saveform');
	}
    }
    function openViewModeForm(obj) {

	var rowId = obj.split("E")[1];
	var row = jQuery('#gridWaterDisconnection').jqGrid('getRowData', rowId);
	var colData = row['csCcn'];
	$("#csIdn").val(colData);
	$.fancybox.close();

    }
    function saveFormDisconnection(element) {
	return saveOrUpdateForm(element, 'Edited Successfully', 'ChangeOfUsage.html?proceed', 'saveScrutinyForm');
	
    }
    $(document).ready(
	    function() {
		$('#submitButtonId').prop('disabled', true);
		$('input[type="text"],textarea,select,input[type="radio"]')
			.attr('disabled', true);
	    });
    function editForm(obj) {
	$('#submitButtonId').prop('disabled', false);
	document.getElementById("remark").disabled = false;
	document.getElementById("newTrmGroup1").disabled = false;

    }
    
</script>


<apptags:breadcrumb></apptags:breadcrumb>


<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.head.changeUsage" text="Change of Usage" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div>
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
			<form:form action="ChangeOfUsage.html" method="POST"
				class="form-horizontal" id="changeOfUsageId">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div id="allicantDetails">
					<apptags:applicantDetail wardZone="WWZ" disabled="true"></apptags:applicantDetail>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#old_details"><spring:message
									code="water.changeUsage.details.old" text="Old Details" /></a>
						</h4>
					</div>
					<div id="old_details" class="panel-collapse collapse in">
						<div class="panel-body">

							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="water.ConnectionNo"></spring:message></label>
								<div class="col-sm-4">
									<form:input path="requestDTO.connectionNo" type="text"
										class="form-control disablefield" id="conNum" readonly="true"></form:input>
								</div>

							</div>



							<div class="form-group">
								<label class="col-sm-2 control-label"> <spring:message
										code="water.consumerName"></spring:message>
								</label>
								<div class="col-sm-4">
									<%-- <c:set value="${command.consumerName() }" var="name"/>
					              <c:set var="consumerName" value="${fn:replace(name,'null','') }"/> --%>
									<form:input path="applicantDetailDto.applicantFirstName"
										cssClass="form-control disablefield" id="conName"
										disabled="true" readonly="true" />
								</div>

								<label class="col-sm-2 control-label"><spring:message
										code="water.ConnectionSize" /></label>
								<!-- <div class="col-sm-4"> -->
								<!-- <div class="input-group"> -->
								<c:set var="baseLookupCode" value="CSZ" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="requestDTO.connectionSize"
									cssClass="form-control disablefield" hasChildLookup="false"
									showAll="false" showOnlyLabelWithId="true"
									selectOptionLabelCode="applicantinfo.label.select" />
								<!-- </div> -->
								<!-- </div> -->

							</div>

							<div class="form-group">
								<apptags:lookupFieldSet baseLookupCode="TRF" hasId="true"
									showOnlyLabel="false"
									pathPrefix="requestDTO.changeOfUsage.oldTrmGroup"
									isMandatory="false" showOnlyLabelWithId="true"
									cssClass="form-control " showAll="true" />
							</div>

						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#new_details"><spring:message
									code="water.changeUsage.details.new" text="New Details" /></a>
						</h4>
					</div>
					<div id="new_details" class="panel-collapse collapse in">
						<div class="panel-body">

							<div id="newType">
								<div class="form-group">
									<apptags:lookupFieldSet baseLookupCode="TRF" hasId="true"
										pathPrefix="requestDTO.changeOfUsage.newTrmGroup"
										isMandatory="true" hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" cssClass="form-control"
										showAll="true" />
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="water.remark" /></label>
									<div class="col-sm-10">
										<form:textarea type="text" class="form-control"
											path="requestDTO.changeOfUsage.remark" id="remark"
											maxlength="200"></form:textarea>
									</div>
								</div>

							</div>
						</div>
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
																actionUrl="ChangeOfUsage.html?Download"></apptags:filedownload>
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
				<jsp:include page="/jsp/cfc/sGrid/scrutinyButtonTemplet.jsp" />
			</div>
		</div>
	</div>
</div>