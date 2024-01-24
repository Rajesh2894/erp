<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script>
	$(function() {
		$("#fromDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});

		$("#fromDateId").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});

	});

	function submitCitizenForm(obj) {
		debugger;
		var errorList = [];
		var fromDateId = $("#fromDateId").val();
		var scrutinyId=$("#scrutinyId").val();
		if (fromDateId == '' || fromDateId == undefined) {
			errorList.push(getLocalMessage("Please select date "));
		}
		if (scrutinyId == '' || scrutinyId == undefined||scrutinyId==0) {
			errorList.push(getLocalMessage("Please select service name "));
		}
		/* var URL = 'CitizenOnlinePendingPayment.html?initiatePendPayment';
		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		$(theForm).attr('action', URL);

		$(theForm).submit(); */
		if (errorList.length == 0) {
			return saveOrUpdateForm(obj,
					"Citizen Pending Payment Update Succsessfully",
					'AdminHome.html', 'initiatePendPayment');
		} else {
			displayErrorsOnPage(errorList);
		}

	}

	function cashBookReset(element) {
		$("#fromDateId").val("");
		$("#scrutinyId").val("");
		$("#referenceNo").val("");
		$("#flatNo").val("");
		$("#errorDiv").hide();	
	}
	
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>Citizen Pending Payment</h2>
		</div>
		<apptags:helpDoc url="CitizenOnlinePendingPayment.html"
			helpDocRefURL="CitizenOnlinePendingPayment.html"></apptags:helpDoc>
		<div class="widget-content padding">
			<form:form action="" class="form-horizontal" id="citizenForm"
				name="citizenForm">
				<%-- <div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>--%>
 				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
				</div>
				<div class="form-group">
					<label for="date-1493383113506" class="col-sm-2 control-label "><spring:message
							code="" text="From Date" /><span class="required-control"></span>
					</label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="challanDTO.validDate" id="fromDateId"
								cssClass="mandColorClass form-control" data-rule-required="true"
								maxlength="10" />
							<label class="input-group-addon mandColorClass" for="fromDateId"><i
								class="fa fa-calendar"></i> </label>
						</div>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="common.master.service.name" text="Service Name" /></label>
					<div class="col-sm-4">
						<form:select path="serviceId" class="form-control" id="scrutinyId">
							<form:option value="">
								<spring:message code="scrutiny.select" text="Select" />
							</form:option>
							<c:choose>
								<c:when test="${userSession.languageId eq 1}">
									<c:forEach items="${command.serviceList}" var="serviceData">
										<form:option value="${serviceData.smServiceId }">${serviceData.smServiceName }</form:option>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${command.serviceList}" var="serviceData">
										<form:option value="${serviceData.smServiceId }">${serviceData.smServiceNameMar }</form:option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</form:select>


					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="referenceNo"><spring:message
							code="master.lbl.conNo" text="master.lbl.conNo" /></label>
					<div class="col-sm-4">
						<form:input name="referenceNo" type="text" class="form-control "
							id="referenceNo" path="offlineDTO.referenceNo"></form:input>
					</div>
					<label class="col-sm-2 control-label" for="Flat No"><spring:message
							code="Flat No" text="Flat No" /></label>
					<div class="col-sm-4">
						<form:input name="flatNo" type="text"
							class="form-control" id="flatNo"
							path="offlineDTO.flatNo"></form:input>
					</div>




				</div>

				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-blue-2"
						onClick="submitCitizenForm(this)" 
						title="<spring:message code="submit.msg" text="Submit"/>">
						<spring:message code="submit.msg" text="Submit"></spring:message>
					</button>
					<button type="button" class="btn btn-warning"
						onClick="cashBookReset(this)" title="<spring:message code="reset.msg" text="Reset"/>">
						<spring:message code="reset.msg" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>
