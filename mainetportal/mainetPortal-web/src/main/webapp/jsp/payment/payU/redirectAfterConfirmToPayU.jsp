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
<title>PayU Payment Gateway</title>
</head>
<body onload="document.pay.submit();">  
<div class="clearfix" id="home_content">
	<div class="col-xs-12">

		<div class="row">

			<div class="form-div" style="border:1px solid #ff6600;padding: 20px;width: 60%;margin: 0 auto;" align="center" >
			
			<p>We are redirecting to payu in few secs. <br>
								Please do not click "Refresh" or back/forward button on your browser.</p>			
			
			
	 		<form:form 	action="${command.pgUrl}" id="pay" name="pay" method="post">
				<input type="hidden" name="key" value="${command.key}" />
				<input type="hidden" name="hash" value="${command.hash}"/>
				<input type="hidden" name="txnid" value="${command.txnid}" />
			    <input type="hidden" name="surl" value="${command.successUrl}" />
				<input type="hidden" name="furl" value="${command.failUrl}"/>	
				<input type="hidden" name="curl" value="${command.cancelUrl}"/>				    
				<input type="hidden" name="pg" value="${command.pg}"/>
				<input type="hidden" name="udf1" value="${command.udf1}"/>
				<input type="hidden" name="udf2" value="${command.udf2}"/>
				<input type="hidden" name="udf3" value="${command.udf3}"/>
				<input type="hidden" name="udf4" value="${command.udf4}"/>
				<input type="hidden" name="udf5" value="${command.udf5}"/>
				<input type="hidden" name="udf6" value="${command.udf6}"/>
				<input type="hidden" name="udf7" value="${command.udf7}"/>
				<input type="hidden" name="udf8" value="${command.udf8}"/>
				<input type="hidden" name="udf9" value="${command.udf9}"/>
				<input type="hidden" name="udf10" value="${command.udf10}"/> 
				<input type="hidden" name="firstname" value="${command.applicantName}" />
				<input type="hidden" name="email" value="${command.email}" />
				<input type="hidden" name="phone" value="${command.mobNo}" />
				<input type="hidden" name="amount" value="${command.dueAmt}" />
				<input type="hidden" name="productinfo" value="${command.serviceName}" />
				 </form:form>	
			</div>
		</div>
	</div>
</div>

</body>
</html>