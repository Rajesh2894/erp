<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
%>

<style>
.navigation {
	display: none !important;
}
</style>
<script>
	function cleardata() {
		$('#resetFlag').val('Y');
		$('#payename1').val('');
		$('#payename2').val('');
		$('#payeemail1').val('');
		$('#payeemail2').val('');
		$('#paymobile1').val('');
		$('#paymobile2').val('');
		$("select[name='cbBankid']").val("0");
		$('.error-div').hide();
	}

	function cancelTransaction(element) {

		var actionParam = "cancelTransaction";
		var url = 'PaymentController.html' + '?' + actionParam;
		var requestData = '';
		var response = __doAjaxRequestForSave(url, 'post', requestData, false,
				'', element);
		var transactionResponse = response["errMsg"];
		if (transactionResponse == '') {
			var errorList = [];
			errorList
					.push(getLocalMessage("eip.payment.errorOccurred"));
		} else {
			window.location.href = "CitizenHome.html";
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
		}
	}

	function bankReset() {

		var bank = $('#hideBank').val();

		$('#cbBankid option[value="' + bank + '"]').prop('selected', true);

	}

	function closeErrBox() {
		$('.error-div').hide();
		$("#error-div").html('');
	}

	function submitPayuForm() {
		var errorList = [];
		//else {
			var bankId = $('#cbBankid').val();
			var appName = $('#payename2').val();
			var mobno = $('#paymobile2').val();
			var email = $('#payeemail2').val();

			if (bankId == '0') {
				errorList.push(getLocalMessage("eip.payment.select"));
			}
			if (appName == "") {
				errorList
						.push(getLocalMessage("eip.payment.appName"));
			}

			if (mobno == "") {
				errorList.push(getLocalMessage("eip.payment.mobno"));
			}
			if ( mobno != "" && mobno.length != 10) {
				errorList.push(getLocalMessage("eip.payment.mobval"));
			}

			if (email != "") {
				var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

				if (reg.test(email) == false) {
					errorList
							.push(getLocalMessage("eip.payment.email.validation"));

				}

			} else if (email == "" || email == undefined || email == "0") {
				errorList.push(getLocalMessage("eip.payment.email.valid"));
			}

		//}
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
		}
		else if ($('#cbBankid').val() != '0' && $('#payename2').val() != ''
				&& $('#paymobile2').val() != '' && $('#payeemail2').val() != '') {
			document.frmPayForm.submit();
			$('#payButton').prop('disabled', true);
			popup.focus();
		} 
	}
</script>

<script>
	$(document).ready(function() {
		function disableF5(e) {
			if ((e.which || e.keyCode) == 116)
				e.preventDefault();
		}
		;
		$(document).on("keydown", disableF5);

	});
</script>

<style>
.font-label {
	font-style: italic;
	text-align: left;
	font-size: 12px;
	color: #F00;
	display: block;
}
</style>

<ol class="breadcrumb">
	<li><a href="CitizenHome.html"><spring:message
				code="menu.home" /></a></li>
	<li><spring:message code="eip.payment.lbl" text="Payment" /></li>
</ol>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="eip.payment.paymentDetails"
					text="Payment Details" />
			</h2>
		</div>

		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="MandatoryMsg" /></span>
			</div>
			<div class="alert alert-danger alert-dissmissible error-div"
				id="error-div" style="display: none;"></div>
			<jsp:include page="/jsp/tiles/customValidationErrors.jsp" />



			<div class="alert alert-warning padding-15 text-center">
				<spring:message code="eip.payment.waringmsg" />
				<br>
				<spring:message code="eip.payment.mmsg" />
			</div>


			<form:form action="PaymentController.html?confirm" name="frmPayForm"
				id="frmPayForm" modelAttribute="command" class="form-horizontal">

				<div class="form-group">

					<label class="col-sm-2 control-label" for=""><spring:message
							code="eip.payment.dueAmount" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<input type="text" class="form-control" value="${command.dueAmt}"
								disabled> <label class="input-group-addon"><i
								class="fa fa-inr"></i></label>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="eip.payment.payeeName" text="Payee Name" /></label>
					<div class="col-sm-4">
						<form:input cssClass="mandClassColor hasSpecialChara form-control"
							path="applicantName" id="payename2"
							value="${command.applicantName}" />
					</div>


					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="eip.payment.mobileNo" text="Mobile No" /></label>
					<div class="col-sm-4">
						<form:input cssClass="mandClassColor hasNumber form-control"
							path="mobNo" value="${command.mobNo}" maxlength="10"
							id="paymobile2" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="eip.payment.email" text="Email ID" /></label>
					<div class="col-sm-4">
						<form:input path="email" value="${command.email}" id="payeemail2"
							class="form-control" />
					</div>

					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="eip.payment.selectBankLable" text="Payment Gateway" /></label>
					<div class="col-sm-4">
						<form:select id="cbBankid" path="bankId"
							cssClass="mandClassColor cbBankid form-control"
							autocomplete="off">
							<form:option value="0">
								<spring:message code="eip.payment.selectBank"></spring:message>
							</form:option>
							<form:options items="${userSession.onlineBankList}" />
						</form:select>
					</div>
				</div>


				<c:if test="${'Y' eq command.chargeFlag}">

					<div class=" form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="eip.payment.netBank" /></label>
						<div class="col-sm-4">
							<spring:message code="netBankingPayment" />
						</div>
						<label class="col-sm-2 control-label"><spring:message
								code="eip.payment.debitCard" /></label>
						<div class="col-sm-4">
							<spring:message code="debitCardpayment" />
						</div>
					</div>
					<div class=" form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="eip.payment.creditCard" /></label>
						<div class="col-sm-4">
							<spring:message code="creditCardPayment" />
						</div>
					</div>


				</c:if>

				<br>
				<div class="text-center padding_top_10">

					<input type="button" class="btn btn-success"
						value="<spring:message code="eip.payment.payButton"  text="Pay"/>"
						onclick="submitPayuForm()" id="payButton" />&nbsp;<input
						type="button" class="btn btn-warning" onclick="cleardata(this)"
						value="<spring:message code="eip.payment.resetButton"  text="Reset"/>" />&nbsp;
					<input type="button" class="btn btn-danger"
						onclick="cancelTransaction(this)"
						value="<spring:message code="eip.payment.cancelButton"  text="Cancel"/>" />
				</div>
			</form:form>
		</div>
	</div>

</div>

