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
<script src="js/mainet/validation.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<%-- <script src="js/mainet/ui/jquery-     1.11.1.validate.min.js"></script>
<script src="js/mainet/validation/globalValidation.js"></script>
    <script src="js/mainet/ui/jquery-1.10.2.min.js"></script>  --%>
<script>
$(function() {
	$(".datepicker2").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true,
		maxDate: '0'
		
	}); 	
$('input[name="billPayment"]').on('click', function() {
    if ($(this).val() === 'true') {
    	
    	 $('#assLpReceiptNo, #receiptAmount, #assLpReceiptDate, #lastpaymentMadeUpto').prop("disabled", "disabled");
    	 $('#assLpReceiptNo, #receiptAmount, #assLpReceiptDate, #lastpaymentMadeUpto').val('');
    }
    else {
    	 $('#assLpReceiptNo, #receiptAmount, #assLpReceiptDate, #lastpaymentMadeUpto').removeProp("disabled");
    }
});


});
</script>
<div class="accordion-toggle">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#LastBillPayment"><spring:message
				code="property.lastpayment" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="LastBillPayment">
		<div class="form-group">
			<label for="radio-group-1492067297931" class="col-sm-2 control-label">
			<spring:message code="property.billpayment"/>
			<span class="mand">*</span>
			</label>
			<div class="col-sm-4">
			<label for="notApplicaple" class="radio-inline"><input type="radio" value="na" name="billPayment" id="notApplicaple"><spring:message code="property.na"/></label>
			<label for="manual" class="radio-inline"><input name="billPayment" value="manual" id="manual" type="radio"><spring:message code="property.manual"/></label>
			</div>	
		</div>
		
		<div class="form-group">
			<label for="assLpReceiptNo" class="col-sm-2 label-control required-control">
			<spring:message code="property.receiptno"/>
			</label>
			<div class="col-sm-4">
				<form:input id="assLpReceiptNo" path="entity.proAssLpReceiptNo"
				class="form-control" disabled="${viewMode}" />
			</div>
			<label for="receiptAmount" class="col-sm-2 required-control">
			<spring:message code="property.receiptamount"/>
			</label>
			<div class="col-sm-4">
			<form:input id="receiptAmount" path="entity.proAssLpReceiptAmt"
				class="required form-control" data-rule-required="true"/>
				
			</div>
			
		</div>
	<div class="form-group">
			<label class="label-control col-sm-2 required-control"
				for="assLpReceiptDate"><spring:message
					code="property.receiptdate" text="" /></label>
			<div class="col-sm-4">
				
				<form:input type="text" path="entity.proAssLpReceiptDate" id="assLpReceiptDate"
					class="datepicker2 cal form-control" />
			</div>
			<label for="select-1492067432449" class="col-sm-2 required-control">
			<spring:message code="property.lastpaymentupto"/>
			</label>
			<div class="col-sm-4">
				<form:select id="lastpaymentMadeUpto" path="entity.proAssLpYear" class="form-control">
					<form:option value="0">
						<spring:message code="property.sel.optn" text="Select" />
					</form:option>
					<c:forEach items="${command.schedule}" var="billschedule">
					<form:option value="${billschedule.lookUpId}">
						${billschedule.lookUpCode}
					</form:option>
					</c:forEach>
				</form:select>

			</div>

		</div>
		
</div>
</div>
