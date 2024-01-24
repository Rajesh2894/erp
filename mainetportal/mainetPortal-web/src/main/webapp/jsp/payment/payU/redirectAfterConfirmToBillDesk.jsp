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
<script type="text/javascript" id="resourceScript" src="https://services.billdesk.com/checkout-widget/src/app.bundle.js" async=""></script> 
<title>BillDesk Payment Gateway</title>
</head>


<body onload="SubmitPay();">

	<div class="clearfix" id="home_content">
		<div class="col-xs-12">

			<div class="row">

				<div class="form-div"
					style="border: 1px solid #ff6600; padding: 20px; width: 60%; margin: 0 auto;"
					align="center">

					<p>
						We are redirecting to BillDesk Payment Gateway in few secs. <br> Please do
						not click "Refresh" or back/forward button on your browser.
					</p>
					
						
           <form:form action="${command.pgUrl}" id="pay" name="pay" method="post">
				
				<input type="hidden" name="payRequestMsg" id="payRequestMsg" value="${command.payRequestMsg}"/>
				<input type="hidden"  id="responseUrl" value="${command.responseUrl}"/>
				<!-- <input type="hidden"  id="options" value=${command.addField8}/> -->
				<input type="hidden"  id="enableChildWindowPosting" value="${command.enableChildWindowPosting}"/>
				<input type="hidden"  id="enablePaymentRetry" value="${command.enablePaymentRetry}"/>
				<input type="hidden"  id="retryAttemptCount" value="${command.retryAttemptCount}"/>
						<!--  <a class="main-btn" href="javascript:void(0)" onclick="SubmitPay()" data-animation="fadeInUp" data-delay="1.5s">Pay with BillDesk &nbsp;<code class="icon noselect">&gt;</code></a> -->
		   </form:form>
					
				</div>
			</div>
		</div>
	</div>
	


<script type="text/javascript">

        function SubmitPay() {
        	
        var payRequestMsg = document.getElementById('payRequestMsg').value.trim();
        var responseUrl = document.getElementById('responseUrl').value.trim();
        var enableChildWindowPosting = document.getElementById('enableChildWindowPosting').value.trim();
        var enablePaymentRetry = document.getElementById('enablePaymentRetry').value.trim();
        var retryAttemptCount = document.getElementById('retryAttemptCount').value.trim();
      
	bdPayment.initialize({
			"msg" :payRequestMsg,
			"options" : {
				"enableChildWindowPosting": true,
				"enablePaymentRetry": true,
				"retry_attempt_count": 3,
				"logo": "https://pgi.billdesk.com/merchantcheckout/assets/images/logo.png"
			},
			"callbackUrl" : responseUrl
		});

	}
</script>
</body>
</html>