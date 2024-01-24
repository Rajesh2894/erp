<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/validation.js"></script>

<script>

function saveForm(element)
{
	var succMsg = getLocalMessage('eip.dashboard.Loi.SuccessMsg');

	if($("input:radio[name='entity.onlineOfflineCheck']").filter(":checked").val() == 'Y')
	
		return saveOrUpdateForm(element,succMsg,'PendingLOIForm.html?redirectToPay','saveform');
	else
		return saveOrUpdateForm(element,succMsg,'PendingLOIForm.html?PrintReport','saveform');
}
</script>
<div class="form-div">
		<div id="heading_wrapper">
		
			<div id="heading_bredcrum">
				<ul class="breadcrumbs">
					<li><a href="CitizenHome.html"><spring:message
								code="menu.home" /></a></li>
								<li class="active"><spring:message
								code="" text="Payment Of LOI Transactions"/></li>
								</ul>
								</div>
								</div>
	<h1><spring:message code="" text="Payment Of LOI Transaction"/></h1>
		<div class="clearfix" id="home_content">
			<div class="col-xs-12">
				<div class="row">

					<div class="form-div">
			
				<div id="content">
		
		
		
	<form:form method="post" action="PendingLOIForm.html"
		name="frmPendingLOIForm" id="frmPendingLOIForm" class="form"> 

		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div class="regheader"><spring:message code=""  text="Applicant Information"/></div>
		<fieldset>
		<div class="form-elements">
					<label for=""><spring:message code="bnd.serviceName" />:</label> 
					${command.entity.serviceName}
				</div>
				<br>
				<div class="form-elements">
				
					<label for=""><spring:message code="eip.citizen.repayment.applicationid" />:</label> 
					${command.entity.applicationNo}
				</div>
				<br>
			<div class="form-elements">
				
					<label for=""><spring:message code="eip.dashboard.loino" />:</label> 
					${command.entity.loiNo}
				</div>
				<br>
				<div class="form-elements">
					<label for=""><spring:message code="rti.applicantName" />:</label> 
					${command.entity.payeeName}
				</div>
			
			<br>
			<div class="form-elements">
				
					<label for=""><spring:message code="eip.payment.mobileNo" />:</label> 
					${command.entity.phoneNo}
				</div>
				<br>
				<div class="form-elements">
					<label for=""><spring:message code="eip.payment.email" />:</label> 
					${command.entity.email}
				</div>
		
			<br>
			<div class="form-elements">
					<label for=""><spring:message code="birth.search.amount" />:</label> 
					${command.entity.amount}
				</div>
				
			<%-- <div class="form-elements">
				
					<label for=""><spring:message code="eip.stp.orderno.challan.repayment" />:</label>
					${command.entity.orderNo}
				</div> --%>
			
		</fieldset>
		<br>
						<fieldset >
						<div class="regheader">
						<spring:message code="" text="Payment Selection"/>
					</div>
							
						</fieldset>
						
				<div class="btn-fld margin_top_10 clear">
							<input type="submit" class="css_btn" onclick="return saveForm(this);" value="<spring:message code="tp.save" />" />
						
							<apptags:backButton url="CitizenHome.html"></apptags:backButton>
				</div>
			
	</form:form>  
	</div>       
	</div>
	</div>
	</div>
</div>
</div>
