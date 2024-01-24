<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Razorpay Payment Gateway</title>
</head>
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
<script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
<body onload="SubmitPay(this);">

<div class="clearfix" id="home_content">
		<div class="col-xs-12">

			<div class="row">

				<div class="form-div"
					style="border: 1px solid #ff6600; padding: 20px; width: 60%; margin: 0 auto;"
					align="center">

					<p>
						We are redirecting to Razorpay Payment Gateway in few secs. <br> Please do
						not click "Refresh" or back/forward button on your browser.
					</p>
<form action="" id="pay" name="pay" method="post">
  <input type="hidden" name="key" id="key" value="${command.key}"/>
  <input type="hidden" name="amount" id="amount" value="${command.validateAmount}"/>
  <input type="hidden" name="currency" id="currency" value="${command.trnCurrency}"/>
  <input type="hidden" name="name" id="name" value="${command.applicantName}"/>
  <input type="hidden" name="description" id="description" value="Transactional Payment"/>
  <input type="hidden" name="orderId" id="orderId" value="${command.orderId}"/>
  <input type="hidden" name="responseUrl" id="responseUrl" value="${command.responseUrl}"/>
  <input type="hidden" name="email" id="email" value="${command.email}"/>
  <input type="hidden" name="mobNo" id="mobNo" value="${command.mobNo}"/>
  <input type="hidden" name="address" id="address" value="${command.recieptDTO.applicantAddress}"/>
<!-- <button id="rzp-button1">Pay</button> -->
</form>

</div>
</div>
</div>
</div>
<script type="text/javascript">

       function SubmitPay(element) {
        	debugger;
        var key = document.getElementById('key').value.trim();
        var amount = document.getElementById('amount').value.trim();
        var currency = document.getElementById('currency').value.trim();
        var name = document.getElementById('name').value.trim();
        var description = document.getElementById('description').value.trim();
        var orderId = document.getElementById('orderId').value.trim();
        var responseUrl = document.getElementById('responseUrl').value.trim();
        var email = document.getElementById('email').value.trim();
        var mobNo = document.getElementById('mobNo').value.trim();
        var address = document.getElementById('address').value.trim();
	
	var options = {
			"key": key,//"rzp_test_BXNDZx6RL88D83",
			"amount": amount, // Example: 2000 paise = INR 20
			"currency": currency,//currency,
			"name": name,
			"description": description,//description,
			"image": "img/logo.png",// COMPANY LOGO
			"order_id": orderId, 
			"callback_url": responseUrl,
		
			"prefill": {
				"name": name, // pass customer name
				"email": email,// customer email
				"contact": mobNo //customer phone no.
			},
			"notes": {
				"address": address //customer address 
			},
			"theme": {
				"color": "#15b8f3" // screen color
			}
		};
		console.log(options);
		var propay = new Razorpay(options);
			propay.open();
			element.preventDefault();


	}
</script>
</body>
</html>