<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/cfc/scrutiny.js"></script>
<script>
	function saveAndGenerateLoi(obj) {
		var errorList=[];
	    var reamrkValidFlag =  $("#reamrkValidFlag").val();
		if (reamrkValidFlag == 'Y' && ($("#loiRemark").val() == "")){
			errorList.push(getLocalMessage('remark.validation'));
		}
		if (errorList == ''){
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var returnData = __doAjaxRequest('LoiGeneration.html?generateLOI',
				'POST', requestData, false);
		showConfirmBoxFoLoiPayment(returnData);
		}else{
			displayErrorsOnPage(errorList);
		}
	}

	function showConfirmBoxFoLoiPayment(sucessMsg) {

		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = getLocalMessage("bt.proceed");

		message += '<h4 class=\"text-center text-blue-2 padding-12\">'
				+ sucessMsg.command.message + '</h4>';
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
				+ ' onclick="showScrutinyLabel()"/>' + '</div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
		return false;
	}

	function showScrutinyLabel() {
		$.fancybox.close();
		loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule', $(
				'#appId').val(), $('#labelId').val(), $('#serviceId').val());
	}

	function calculateTotalLoi() {
		var finalAmount = 0;
		if ($("#advpay").val() == finalAmount) {
			$("#advpay").val("0.00")
		}
		$(".amount").each(function() {
			finalAmount += parseInt($(this).val());
		});
		$("#totAmount").val(finalAmount);
	}

	$("#selectall").click(function() {
		$('.case').attr('checked', this.checked);
	});

	$(".case").click(function() {
		var alertms = getLocalMessage('tp.app.alMessg');
		if ($(".case").length == 0) {
			alert(alertms);
		} else {

			if ($(".case").length == $(".case:checked").length) {
				$("#selectall").attr("checked", "checked");
			} else {
				$("#selectall").removeAttr("checked");
			}
		}
	});
</script>
<ol class="breadcrumb">
	<li><a href="AdminHome.html"><i class="fa fa-home"></i></a></li>
	<li><spring:message code="master.brdCum.Home" text="Home"/></li>
	<li><spring:message code="master.loi.letter" text="LOI"/></li>
	<li class="active"><spring:message code="loi.letter.generate" text="LOI Generation And Payment"/></li>
</ol>
<!-- ============================================================== -->
<!-- Start Content here -->
<!-- ============================================================== -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="master.loi.letter" text="LOI"/> <spring:message code="master.loi.letter.generation" text="Generation"/>
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="common.sequence.ismandtry" text="Field with * is mandatory"/>
				</span>
			</div>
			<form:form action="LoiGeneration.html" method="POST"
				class="form-horizontal" id="loiGenerationForm"
				name="loiGenerationForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
				<form:hidden path="chargeDesc" id="chargesMap" />
				<form:hidden path="reamrkValidFlag" id="reamrkValidFlag" />
				
				<h4><spring:message code="master.loi.app.details" text="Application Details"/> </h4>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="master.serviceName" text="Service Name"/></label>
					<div class="col-sm-4">
						<form:input path="serviceName" type="text" class="form-control"
							readonly="true" />
					</div>
					<label class="col-sm-2 control-label"><spring:message code="master.loi.app.num" text="Application Number"/></label>
					<c:choose>
					<c:when test="${not empty command.refNo}">
						<div class="col-sm-4">
						<form:input path="refNo" type="text"
							class="form-control" readonly="true" />
					</div>
					</c:when>
					<c:otherwise>
					<div class="col-sm-4">
						<form:input path="entity.loiApplicationId" type="text"
							class="form-control" readonly="true" />
					</div>
					</c:otherwise>
					</c:choose>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="master.loi.applicant.name" text="Applicant Name"/> </label>
					<div class="col-sm-4">
						<form:input path="applicantName" type="text" class="form-control"
							readonly="true" />
					</div>
					<label class="col-sm-2 control-label"> <spring:message code="master.loi.appDateTime" text=" Application Date Time"/></label>${applicationDate}
					<div class="col-sm-4">
						<form:input path="applicationDate" type="text"
							class="form-control" readonly="true" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							text="Applicant Mobile No " code="master.loi.applicant.mob" /> </label>
					<div class="col-sm-4">

						<form:input path="mobNo" type="text" class="form-control"
							readonly="true" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							text="Applicant Email Id" code="master.loi.applicant.email" /> </label>
					<div class="col-sm-4">

						<form:input path="email" type="text" class="form-control"
							readonly="true" />
					</div>
				</div>

				<c:if
					test="${command.entity.loiNo != null || not empty command.entity.loiNo}">
					<h4><spring:message
							text="Letter of Intent (LOI) Details" code="master.loi.ind.det" /></h4>

					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
							text="LOI Number" code="master.loi.loiNo" /></label>
						<div class="col-sm-4">
							<form:input path="entity.loiNo" type="text" class="form-control"
								readonly="true" />
						</div>
						<label class="col-sm-2 control-label"><spring:message
							text="LOI Date" code="master.loi.loidate" /></label>
						<div class="col-sm-4">
							<form:input path="entity.loiDateStr" type="text"
								class="form-control" readonly="true" />
						</div>
					</div>
				</c:if>


				<h4><spring:message
							text="LOI Fees and Charges in Details" code="master.loi.feesandcharges" /> </h4>

				<div class="table-responsive">
					<table class="table table-bordered table-striped">
						<tr>
							<th scope="col" width="80"><spring:message
							text="Sr. No" code="master.lbl.srNo" /></th>
							<th scope="col"><spring:message
							text="Charge Name" code="master.loi.charge.name" /></th>
							<th scope="col"><spring:message
							text="Amount" code="master.loi.amt" /></th>
						</tr>
						<c:forEach var="chargesDesc" items="${command.chargeDesc}"
							varStatus="status">
							<tr>
								<td>${status.count}</td>



								<c:set var="str1" value="${chargesDesc.key}" />
								<c:set var="str2" value="${fn:split(str1, '-')}" />



								<td><form:input path="" type="text" class="form-control"
										value="${str2[0]}" readonly="true" /></td>
								<td><fmt:formatNumber value="${chargesDesc.value}"
										type="number" var="amount" minFractionDigits="2"
										maxFractionDigits="2" groupingUsed="false" /> <c:if
										test="${str2[1] eq 'Y'}">
										<form:input path="chargeDesc[${chargesDesc.key}]" type="text"
											name="advPayAmt"
											class="form-control text-right amount hasNumber"
											value="${amount}" id="advpay" readonly="false" maxlength="4"
											onblur="calculateTotalLoi()" />
									</c:if> <c:if test="${str2[1] ne 'Y'}">
										<form:input path="" type="text"
											class="form-control text-right amount" value="${amount}"
											readonly="true" />
									</c:if></td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="2"><span class="pull-right"><b><spring:message
							text="Total LOI Amount" code="master.loi.total.amt" /></b></span></td>
							<td><fmt:formatNumber value="${command.total}" type="number"
									var="totalAmount" minFractionDigits="2" maxFractionDigits="2"
									groupingUsed="false" /> <form:input path=""
									value="${totalAmount}" cssClass="form-control text-right"
									id="totAmount" readonly="true" /></td>
						</tr>
					</table>
				</div>
				<!-- START US#106604 & D#126605 -->
				<c:if test="${command.getDepartmentCode() eq 'ADH' && not empty command.getRemarkList()}">
					<div class="overflow margin-top-10">
						<table class="table table-hover table-bordered table-striped"
							id="">
							<thead>
								<tr>
									<th scope="col" width="5%"><label
										class="padding-left-20 margin-bottom-10"> <input
											type="checkbox" name="All" id="selectall" class="pull-left">All
									</label></th>
									<th scope="col" width="5%"><spring:message
											code="adh.sr.no" text="Sr.No." /></th>
									<th scope="col" width="" align="center"><spring:message
											code="dashboard.actions.remarks" text="Remarks" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.getRemarkList()}" var="remark"
									varStatus="status">
									<tr>
										<td align="center"><label class="checkbox margin-left-20"><form:checkbox
													path="remarkList[${status.index}]" value="Y"
													id="${status.count}" class="case" name="case" /></label></td>
										<td>${status.count}</td>
										<td>${remark.artRemarks}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				<!-- END US#106604 & D#126605-->
				<c:if test="${command.getDepartmentCode() ne 'ADH'}">
					<div class="form-group padding-top-10">
					<c:if test="${command.reamrkValidFlag ne 'Y'}">
						<label class="col-sm-2 control-label"><spring:message
											code="dashboard.actions.remarks" text="Remarks" /></label>
						</c:if>
						<c:if test="${command.reamrkValidFlag eq 'Y'}">
						<label class="col-sm-2 control-label required-control"><spring:message code="dashboard.actions.remarks" text="Remarks" /></label></c:if>
						<div class="col-sm-4">
							<c:choose>
								<c:when test="${not empty command.entity.loiRemark }">
									<form:textarea path="entity.loiRemark" class="form-control"
										readonly="true" id="loiRemark"/>
								</c:when>
								<c:otherwise>
									<form:textarea path="entity.loiRemark" class="form-control" id="loiRemark" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</c:if>
				<%-- <h4>Authorize</h4>
	<div class="form-group">
		<label class="control-label col-sm-2 required-control"><spring:message code="" text="Authorize"/></label>
			<div class="radio margin-left-20 col-sm-8">
				<label class="col-sm-1">
				<form:radiobutton path="entity.authorize" value="Y"/>
				<spring:message code="" text="Yes"/>
				</label>
				<label class="col-sm-1 margin-left-20">
				<form:radiobutton path="entity.authorize" value="N" />
				<spring:message code="" text="No"/>			
				</label>
			</div>
</div> --%>
				<form:hidden path="appId" id="appId" />
				<form:hidden path="labelId" id="labelId" />
				<form:hidden path="serviceId" id="serviceId" />

				<c:if test="${command.chargesDefined eq 'N'}">
					<span><b>Charges not defined ,LOI can not be generated
							.Please define LOI charges.</b></span>
				</c:if>

				<div class="text-center padding-top-10">
					<c:if test="${command.chargesDefined eq 'Y'}">
						<c:if test="${command.entity.loiRecordFound eq 'N'}">
							<button type="button" class="btn btn-success btn-submit"
								onclick="return saveAndGenerateLoi(this);"><spring:message
											code="submit.msg" text="Submit" /></button>
						</c:if>
					</c:if>
					<button type="button"
						onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${command.appId}','${command.labelId}','${command.serviceId}')"
						class="btn btn-danger "><spring:message
											code="back.msg" text="Back" /></</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
