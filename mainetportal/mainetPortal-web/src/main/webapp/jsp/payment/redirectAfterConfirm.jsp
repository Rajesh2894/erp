<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body onload="document.payForm.submit();">

<div class="clearfix" id="home_content">
	<div class="col-xs-12">

		<div class="row">

			<div class="form-div" style="border:1px solid #ff6600;padding: 20px;width: 60%;margin: 0 auto;" align="center">
			
			<p>We are redirecting to payu in few secs. <br>
								Please do not click "Refresh" or back/forward button on your browser.</p>
			<form:form 	action="${command.pgBankDetail.pbbankurl}" id="payForm" name="payForm" method="post">
			
				<input type="hidden" name="key" value="${command.bankParamMap.get('key')}" />
				<input type="hidden" name="hash" value="${command.bankParamMap.get('hash')}"/>
				<input type="hidden" name="txnid" value="${command.bankParamMap.get('txnid')}" />
			    <input type="hidden" name="surl" value="${command.bankParamMap.get('surl')}" />
				<input type="hidden" name="furl" value="${command.bankParamMap.get('furl')}"/>					    
				<input type="hidden" name="pg" value="${command.bankParamMap.get('pg')}"/>
				<input type="hidden" name="udf1" value="${command.bankParamMap.get('udf1')}"/>
				<input type="hidden" name="udf2" value="${command.bankParamMap.get('udf2')}"/>
				<input type="hidden" name="udf3" value="${command.bankParamMap.get('udf3')}"/>
				<input type="hidden" name="udf4" value="${command.bankParamMap.get('udf4')}"/>
				<input type="hidden" name="udf5" value="${command.bankParamMap.get('udf5')}"/>
				<input type="hidden" name="firstname" value="${command.bankParamMap.get('firstname')}" />
				<input type="hidden" name="email" value="${command.bankParamMap.get('email')}" />
				<input type="hidden" name="phone" value="${command.bankParamMap.get('phone')}" />
				<input type="hidden" name="amount" value="${command.bankParamMap.get('amount')}" />
				<input type="hidden" name="productinfo" value="${command.bankParamMap.get('productinfo')}" />
				
				<input type="hidden" name="curl" value="PayForm.html?cancel"/>
			
			</form:form>
			</div>
		</div>
	</div>
</div>

</body>
</html>