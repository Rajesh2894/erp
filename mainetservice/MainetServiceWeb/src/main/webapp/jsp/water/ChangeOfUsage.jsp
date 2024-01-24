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
<script type="text/javascript" src="js/water/change-usage.js"></script>

<script type="text/javascript">
	/* $(document).ready(function(){
	 $('#trmGroup1').attr('disabled',true);
	 $('#trmGroup2').attr('disabled',true); 
	 $('#conSize').attr('disabled',true);
	 $('#conName').attr('disabled',true);
	 $('#oldAdharNo').attr('disabled',true);
	
	
	 if ($('#bplNo').val() == '') {
	
	 $('#bpldiv').hide();
	 } else {
	 $('#bpldiv').show();
	 }
	
	 }); */

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
</script>
<%-- <c:if test="${command.hasValidationErrors()}">
	<script type="text/javascript">
		if ($('#conNum').val() !='' ) {
			$('#searchConnection').attr('disabled',true);
			$('#confirmToProceedId').attr('disabled',true);
		}
	
	</script>
</c:if> --%>
<style>
	.zone-ward .form-group > label[for="dwzid3"] {
		clear: both;
	}
	.zone-ward .form-group > label[for="dwzid3"],
	.zone-ward .form-group > label[for="dwzid3"] + div {
		margin-top: 0.7rem;
	}
</style>

<div id="fomDivId">

	<apptags:breadcrumb></apptags:breadcrumb>


	<div class="content">
		<!-- Start info box -->
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="water.head.changeUsage"
						text="Change of Usage" />
				</h2>
				<div class="additional-btn">
					<apptags:helpDoc url="ChangeOfUsage.html"></apptags:helpDoc>
				</div>
			</div>
			<div class="widget-content padding">
				<div class="mand-label clearfix">
					<span><spring:message code="water.fieldwith" /><i
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
					<form:hidden path="" id="enableSubmit"
						value="${command.enableSubmit}" />

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">

						<apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#old_details"><spring:message
											code="water.changeUsage.details.old" text="Old Details" /></a>
								</h4>
							</div>
							<div id="old_details" class="panel-collapse in collapse">
								<div class="panel-body">
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="water.ConnectionNo"></spring:message></label>
										<div class="col-sm-4">
											<form:input path="requestDTO.connectionNo" type="text"
												class="form-control disablefield " id="conNum"></form:input>
										</div>
										<div class="col-sm-6">
											<button type="button" value="Search" class="btn btn-success"
												id="searchConnection">
												<i class="fa fa-search"></i>
												<spring:message code="searchBtn" />
											</button>
											<input type="button" class="btn btn-warning"
												id="resetConnection" value=<spring:message code="rstBtn"/> />
											<input type="button" class="btn btn-danger" id="back"
												onclick="window.location.href='AdminHome.html'"
												value=<spring:message code="bckBtn"/> />
										</div>
									</div>

									<%-- <c:if test="${command.resultFound eq true }"> --%>
									<div id="closeDiv">
										<div class="form-group">
											<label class="col-sm-2 control-label"> <spring:message
													code="water.consumerName"></spring:message>
											</label>
											<div class="col-sm-4">
												<form:input path="" cssClass="form-control disablefield"
													id="conName" disabled="true"
													value="${command.customerInfoDTO.consumerName}" />
											</div>

											<label class="col-sm-2 control-label"><spring:message
													code="water.ConnectionSize" /></label>
											<c:set var="baseLookupCode" value="CSZ" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="requestDTO.connectionSize"
												cssClass="form-control disablefield" hasChildLookup="false"
												showAll="false" showOnlyLabelWithId="true"
												selectOptionLabelCode="applicantinfo.label.select" />
										</div>

										<div class="form-group">
											<apptags:lookupFieldSet baseLookupCode="TRF" hasId="true"
												showOnlyLabel="false"
												pathPrefix="requestDTO.changeOfUsage.oldTrmGroup"
												isMandatory="false" showOnlyLabelWithId="true"
												cssClass="form-control disablefield" showAll="true" />
										</div>


										<h4>
											<spring:message code="water.changeUsage.details.new"
												text="New Details" />
										</h4>
										<div id="newType">
											<div class="form-group margin-bottom-0">
												<apptags:lookupFieldSet baseLookupCode="TRF" hasId="true"
													pathPrefix="requestDTO.changeOfUsage.newTrmGroup"
													isMandatory="true" hasLookupAlphaNumericSort="true"
													hasSubLookupAlphaNumericSort="true"
													cssClass="form-control mandColorClass" />
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label required-control"><spring:message
														code="water.remark" /></label>
												<div class="col-sm-10">
													<form:textarea type="text" class="form-control"
														path="requestDTO.changeOfUsage.remark" id="remark"
														maxlength="200"></form:textarea>
												</div>
											</div>

										</div>
									</div>
									<%-- </c:if> --%>
								</div>
							</div>
						</div>
						<c:if test="${command.resultFound eq true }">
							<jsp:include page="/jsp/water/ChargesAndChecklist.jsp" />

						</c:if>
						<c:if test="${command.enableSubmit eq true}">
							<div class="text-center padding-top-10">
								<input type="button" class="btn btn-success btn-submit"
									onclick="saveChangeOfUsage(this);"
									value=<spring:message code="saveBtn"/> /> <input type="button"
									class="btn btn-warning"
									onclick="window.location.href='ChangeOfUsage.html'"
									value=<spring:message code="rstBtn"/> /> <input type="button"
									class="btn btn-danger"
									onclick="window.location.href='AdminHome.html'"
									value=<spring:message code="bckBtn"/> />
							</div>
						</c:if>
					</div>
				</form:form>
			</div>
		</div>
		<!-- End of info box -->
	</div>
</div>