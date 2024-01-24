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
<title>MahaOnline Payment Gateway</title>
</head>
<body onload="document.pay.submit();">  
<div class="clearfix" id="home_content">
	<div class="col-xs-12">

		<div class="row">

			<div class="form-div" style="border:1px solid #ff6600;padding: 20px;width: 60%;margin: 0 auto;" align="center" >
			
			<p>We are redirecting to payu in few secs. <br>
								Please do not click "Refresh" or back/forward button on your browser.</p>			
						
			
		      <form:form 	action="${command.payRequestMsg}" id="pay" name="pay" method="post">												
	          </form:form>					
			</div>
		</div>
	</div>
</div>
</body>
</html>