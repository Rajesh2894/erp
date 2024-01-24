<%@ page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<jsp:useBean id="stringUtility"
	class="com.abm.mainet.common.util.StringUtility" />
<jsp:useBean id="marathiConvert"
	class="com.abm.mainet.common.util.Utility"></jsp:useBean>
<%@ page import="java.util.Date"%>
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss"
	var="myDate" />
<style>
#propertyNo {
	margin-top: 1rem;
}
</style>
<script>
function getPropDetails(){
	debugger;
	var billtype=$("#billtype").val();
	if(billtype == "" || billtype== null || billtype==undefined) {
		$('#propertyNo').addClass('hide');
	}
	if(billtype == "PropertyBillPayment.html"){
		var propertyLabel= "Property No.";
		$('#propertyNo').removeClass('hide');
		$('#billdetailtext').html(propertyLabel);
	}
	if(billtype == "WaterBillPayment.html"){
		var propertyLabel= "Consumer No.";
		$('#propertyNo').removeClass('hide');
		$('#billdetailtext').html(propertyLabel);
	}
	if(billtype == "LoiPayment.html"){
		var propertyLabel= "LOI Number";
		$('#propertyNo').removeClass('hide');
		$('#billdetailtext').html(propertyLabel);
	}
	if(billtype == "LeasePayment.html"){
		var propertyLabel= "Property No.";
		$('#propertyNo').removeClass('hide');
		$('#billdetailtext').html(propertyLabel);
	}
} 
function searchBillPaymentData(obj) {
	//$.fancybox.close();
	var formName = findClosestElementId(obj, 'form');

	var theForm = '#WaterBillPaymentSearch';
	var billtype=$("#billtype").val();
	var billNumber =  $("#billNumber").val();
	$.fancybox.close();
	if(billtype == "PropertyBillPayment.html"){
		var url = 'PropertyBillPayment.html?getBillPaymentDetail';
	}
	if(billtype == "WaterBillPayment.html"){
		var url = 'WaterBillPayment.html?serachWaterBillPayment';
	}
	if(billtype == "EstateContractBillPayment.html"){
		var url = 'EstateContractBillPayment.html?redirectToLeasePayment';
	}

	var postdata = {
			"billNumber" : billNumber,
			};
	showloader(true);
   // setTimeout(function() {
		var response = __doAjaxRequest(url, 'POST', postdata, false, 'html');
		$('.content-page').html(response);
	
	showloader(false);
	//}, 200);

}



	function newSerachWaterBillPaymentData(obj) {		
		var formName = findClosestElementId(obj, 'form');

		var theForm = '#WaterBillPaymentSearch';
		var billtype = $("#billtype").val();

		$.fancybox.close();
		
		if (billtype == "PropertyBillPayment.html") {
			var url = 'PropertyBillPayment.html?redirectToPropertyPayment';
		}
		if (billtype == "WaterBillPayment.html") {
			var url = 'WaterBillPayment.html?redirectToWaterPayment';
		}

		var postdata = {}
		
		showloader(true);
		// setTimeout(function() {
		var response = __doAjaxRequest(url, 'POST', postdata, false, 'html');
		$('.content-page').html(response);

		showloader(false);
		//}, 200);

	}
</script>

<div class="section-container citizen-services panel">
	<div class="mkpay">
		<div class="panel-heading">
			<h3>
				<div class="image">
					<!-- <i class="fa fa-credit-card" aria-hidden="true"></i> -->
					<spring:message code="QuickPayment" text="Quick Payment" />
					<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
			</h3>
		</div>
	</div>
	<div class="panel-body">
		<div id="mkpay">
			<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="hidden-lg hidden-md hidden-sm" style="margin-top: 10px;"></div>
				<div class="content-tab">
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="error-msg" style="display: none;"></div>
					<form:form action="WaterBillPayment.html" method="post"
						class="form-horizontal" name="WaterBillPayment"
						id="WaterBillPaymentSearch">
						<div class="form-group">
							<div class="col-sm-12">
								<label for="billtype"><spring:message code="BillType"
										text="Bill Type" /></label> <select class="form-control"
									name="bill-type" id="billtype" onchange="">
									<option value="">
										<spring:message code="selectdropdown" text="Select" /></option>
									<option value="PropertyBillPayment.html"><spring:message
											code="PropertyBill" text="Property Bill" /></option>
									<option value="WaterBillPayment.html"><spring:message
											code="WaterBill" text="Water Bill" /></option>
									<option value="LoiPayment.html"><spring:message
											code="loiPayment" text="LOI Payment" /></option>
									<option value="EstateContractBillPayment.html"><spring:message
											code="leasePayment" text="Lease Bill Payment" /></option>
								</select>
							</div>
							<%-- <div class="col-sm-12 propertyNo hide" id="propertyNo"><spring:message code="eg" var="placeholder4" />
									<label for="billNumber" id="billdetailtext"> </label> 
									<input type="text" class="form-control" name="ccnNumber"
										id="billNumber" placeholder="${placeholder4}">										
									</div>  --%>
						</div>
						<div class=" col-sm-12 text-right">
							<br />
							<button type="button"
								onclick="return searchBillPaymentData(this);"
								class="btn btn-danger margin-top-10 margin-bottom-10">
								<spring:message code="eip.page.process" text="Proceed" />
							</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>