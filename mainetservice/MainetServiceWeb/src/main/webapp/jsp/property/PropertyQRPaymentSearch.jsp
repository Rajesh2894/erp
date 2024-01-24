<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/property/PropertyQRPayment.js"></script>
<script>
$(document).ready(function() {debugger;
    	var redirectURL = "${redirectURL}";
    	if(redirectURL!="" && redirectURL!=null){
    		window.location.href = redirectURL;
        }      
});
</script>
<div id="dataDiv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<!-- Start Content here -->
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="" text="QR Payment" />
				</h2>
				<apptags:helpDoc url="PropertyQRPayment.html"></apptags:helpDoc>

			</div>
			<div class="widget-content padding">
				<form:form action="PropertyQRPayment.html" class="form-horizontal"
					name="PropertyQRPaymentSearch" id="PropertyQRPaymentSearch">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<div class="form-group">
						<apptags:input labelCode="propertydetails.PropertyNo."
							path="propBillPaymentDto.assNo"></apptags:input>
						<apptags:input labelCode="propertydetails.ptinno"
							path="propBillPaymentDto.assOldpropno"></apptags:input>
						<strong><p
								class="text-center text-small padding-bottom-10 text-red-1 ">
								<spring:message code="property.OR"></spring:message>
							</p></strong>
					</div>
					<div class="form-group">
						<apptags:input labelCode="property.old.house.no"
							path="propBillPaymentDto.flatNo">
						</apptags:input>
					</div>
					<div class="form-group">
						<div class="text-center padding-bottom-10">
							<button type="button" class="btn btn-blue-2" id="serchBtn"
								onclick="SearchButton(this)">
								<i class="fa fa-search"></i>
								<spring:message code="property.changeInAss.Search" />
							</button>

						</div>
				</form:form>
			</div>
		</div>
	</div>
</div>