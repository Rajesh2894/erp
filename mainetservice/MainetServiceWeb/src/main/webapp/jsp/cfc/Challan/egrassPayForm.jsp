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


<script>

$(document)
.ready(
		function() {
			debugger;
			$("#Challan").hide();
			$("#eGrsPayMode1")
					.change(
							function() {
							var pMode=	$('#eGrsPayMode1 option:selected').attr('code');
							if(pMode=="M"||pMode=='M')
								$("#Challan").show();
							else
								$("#Challan").hide();
							});

		});
function closeErrBox() {
	$('.error-div').hide();
	$("#error-div").html('');
}
	function submitPayuForm(obj) {
		var errorList = [];
			debugger;
			var eGrsPayMode1 = $('#eGrsPayMode1').val();
			var eGrsPayMode2 = $('#eGrsPayMode2').val();
			var trnRemarks = $('#remarks').val();
			var payModeIn=$('#payModeIn').val();
			var chqNo=$('#chqNo').val();
			var bmChqDDDate=$('#offlineDTO.bmChqDDDate').val();
			var drawnOn=$('#drawnOn').val();
			var applicantName=$('#applicantName').val();
			var pMode=	$('#eGrsPayMode1 option:selected').attr('code');
			if (eGrsPayMode1 == '0'||eGrsPayMode1=='undefined' || eGrsPayMode1=="") {
				errorList.push(getLocalMessage("eip.payment.select"));
			}
			if (eGrsPayMode2 == ""||eGrsPayMode2=='undefined'||eGrsPayMode2=='0') {
				errorList
						.push(getLocalMessage("Please select Aggregator"));
			}

			if (trnRemarks == "" || trnRemarks=='undefined') {
				errorList.push(getLocalMessage("Please enter Remark"));
			}
			if(pMode=="M"||pMode=='M'){
				if (payModeIn == "" || payModeIn=='undefined'||payModeIn=='0') {
					errorList.push(getLocalMessage("Please select offline Payment Type"));
				}
				if(payModeIn!=null && payModeIn!='undefined' && payModeIn!='0'){
				if (chqNo == "" || chqNo=='undefined') {
					errorList.push(getLocalMessage("Please select Cheque / Demand Draft No"));
				}
				if (bmChqDDDate == "" || bmChqDDDate=='undefined') {
					errorList.push(getLocalMessage("Please select Cheque/Demand Draft Date"));
				}
				if (drawnOn == "" || drawnOn=='undefined') {
					errorList.push(getLocalMessage("Please enter Payble Bank "));
				}
				if (applicantName == "" || applicantName=='undefined') {
					errorList.push(getLocalMessage("Please select Cheque/ Demand Draft in favour of"));
				}}
				}
		if (errorList.length > 0) {
			$("#error-div").html('');

			var errMsg = '<div class="closeme">	<img alt="Close" title="Close" align="right"  src="css/images/close.png" onclick="closeErrBox()" width="32"/></div>';
			errMsg += '<ul>';

			$.each(errorList, function(index) {
				errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
						+ errorList[index] + '</li>';
			});

			errMsg += '</ul>';
			$("#error-div").html(errMsg);
			$("#error-div").show();
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
			errorList = [];
			return false;
		}else{
		var actUrl = $('#actionUrl').val();
		var theForm = '#frmPayForm';
		var requestData = __serializeForm(theForm);
		var URL = actUrl + '?confirmToPay';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'html');
		var errors = $(returnData).find(".error-msg");
		$(formDivName).html(returnData);
		}
	}
</script>
<style>
.addColor {
	background-color: #fff !important
}
</style>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<b><spring:message code="license.egras" text="Egrass Payment "></spring:message></b>
			</h2>

			<apptags:helpDoc url="TradeApplicationForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="MandatoryMsg" text="MandatoryMsg"/></span>
			</div>
			<div class="alert alert-danger alert-dissmissible error-div"
				id="error-div" style="display: none;"></div>
			<form:form action="" method="POST" name="frmPayForm" id="frmPayForm"
				class="form-horizontal">

				<form:hidden path="actionUrl" id="actionUrl" />
				<div class="form-group">
						<c:set var="baseLookupCode" value="EPM" />
						<apptags:lookupFieldSet cssClass="form-control required-control"
							baseLookupCode="EPM" hasId="true"
							pathPrefix="paymentReqDto.eGrsPayMode"
							hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true" showAll="false"
							isMandatory="true"/>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="eip.payment.amount" text="Due Amount " /></label>
					<div class="col-sm-4">
						<form:input cssClass="mandClassColor  form-control"
							path="paymentReqDto.dueAmt" id="Amount"  disabled="true"/>
					</div>

					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="eip.payment.remarks" text="Remarks" /></label>
					<div class="col-sm-4">
						<form:input cssClass="mandClassColor  form-control"
							path="paymentReqDto.trnRemarks" id="remarks" />
					</div>
				</div>
				<div id="Challan">
				<jsp:include page="/jsp/cfc/Challan/egrassChallanPay.jsp" />
                </div>
				<br>
				<div class="text-center padding_top_10">

					<button type="button" class="btn btn-success" id="continueForm"
						onclick="submitPayuForm(this);">
						<spring:message code="trade.btn.pay" text="Pay" />
					</button>
					&nbsp;<input type="button" class="btn btn-warning"
						onclick="cleardata(this)"
						value="<spring:message code="eip.payment.resetButton"  text="Reset"/>" />&nbsp;
					<button type="button" class="btn btn-primary"
						onclick="window.location.href='AdminHome.html'">Back</button>
				</div>
			</form:form>




		</div>
	</div>
</div>


