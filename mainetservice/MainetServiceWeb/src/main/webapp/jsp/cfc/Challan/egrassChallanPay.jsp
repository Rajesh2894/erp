<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript" src="js/cfc/challan/egrassChallanPayment.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript">

jQuery('.hasNumber').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
   
});
</script>
<%-- -----------------------------------------------online offline selection--------------------------------------------------- --%>
<div class="panel panel-default">
	<div class="panel-heading">
		<h4 class="panel-title">
			<a data-toggle="collapse" class=""
				data-parent="#accordion_single_collapse" href="#Paymentmode"><spring:message
					code="challan.receipt.paymentMode" text="Payment Mode Details"/></a>
		</h4>
	</div>
	<div id="Paymentmode" class="panel-collapse collapse in">
		<div class="panel-body">
			<div class="form-group">
				<label hidden="true"> <form:radiobutton
						path="offlineDTO.onlineOfflineCheck" value="P" id="payAtCounter"
						onclick="showDiv(this);" checked="checked" /> <spring:message
						code="rti.payAtCounter" />
				</label>
			</div>

		</div>

		<%-- 	----------------------------------------------offline payment--------------------------------------------------- --%>
		<div class="offlinepayment" id="offlinepayment">
			<div class="form-group">
				<label class="col-sm-2 control-label required-control"><spring:message
						code="rti.offlinePaymentSelection" /></label>
				<div class="col-sm-4">
					<c:set var="baseLookupCode" value="OFL" />
					<form:select path="offlineDTO.oflPaymentMode"
						cssClass="form-control" id="oflPaymentMode">
						<c:forEach items="${command.getLevelData(baseLookupCode)}"
							var="oflMode">
							<form:option code="${oflMode.lookUpCode}"
								value="${oflMode.lookUpId}">${oflMode.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
				</div>
				<script type="text/javascript">
	fieldsVisible(${command.offlinePay},${command.getBank()},${command.getUlb()},${command.getDd()},${command.getPostal()});
	</script>

			</div>

		</div>



		<%-- -----------------------------------------------payment @ ULB Counter---------------------------------------------------- --%>
		<div class="PPO" id="PPO">
			<div class="form-group">
				<label class="col-sm-2 control-label required-control"><spring:message
						code="payment.freeMode.eg" text="Offline Payment Type &#8377" /></label>
				<apptags:lookupField items="${command.userSession.paymentMode}"
					path="offlineDTO.payModeIn" cssClass="form-control"
					changeHandler="enableDisableCollectionModes(this)"
					selectOptionLabelCode="rti.sel.paymentmode" hasId="true"
					isMandatory="true">
				</apptags:lookupField>
			</div>
		</div>


		<div class="overflow CPAUC" >
			<div class="form-group">
				<label class="col-sm-2 control-label required-control"
					id="selectType"><spring:message code="payment.checkOrDDNo.eg" text="Cheque Number" /></label>
				<div class="col-sm-4">
					<form:input path="offlineDTO.bmChqDDNo"
						class="form-control hasNumber" id="chqNo" maxlength="10"  />
				</div>
				<label class="col-sm-2 control-label required-control"
					id="selectDate"><spring:message
						code="payment.checkOrDDDate.eg" text="Cheque Date"  /></label>
				<div class="col-sm-4">
					<div class="input-group">
						<apptags:dateField fieldclass="lessthancurrdate chqDate"
							datePath="offlineDTO.bmChqDDDate" cssClass="form-control" />
						<label class="input-group-addon"><i class="fa fa-calendar"></i></label>
					</div>
				</div>
			</div>

			<div class="form-group">

				<label class="col-sm-2 control-label required-control"><spring:message
						code="payment.bankCode.eg" text="Payble Bank"/></label>
				<div class="col-sm-4">
					<form:input path="offlineDTO.bmDrawOn" class="form-control"
						readonly="false" id="drawnOn" maxlength="100"/>
				</div>
				<label class="col-sm-2 control-label" id="selectAmount"><spring:message
						code="payment.reciept.amount" text="Cheque Amount" /></label>
				<div class="col-sm-4">
					<fmt:formatNumber value="${command.paymentReqDto.dueAmt}"
						type="number" var="paymentTagAmount" minFractionDigits="2"
						maxFractionDigits="2" groupingUsed="false" />
					<form:input path="offlineDTO.amountToShow"
						value="${paymentTagAmount}"
						class="form-control amountAlign text-right" readonly="true"
						id="amountToPay" />
				</div>

			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label required-control"
					id="selectName"><spring:message code="payment.inFavour"
						text="Cheque In Favour Of" /></label>
				<div class="col-sm-4">
					<form:input path="offlineDTO.applicantName" class="form-control "
						id="applicantName" maxlength="100" />
				</div>

			</div>
		</div>
		<input type="hidden" value="<spring:message code="pay.option.poNo" />"
			id="PO" /> <input type="hidden"
			value="<spring:message code="pay.option.ddNO.eg" text="Demand Draft Number"/>" id="DD" /> <input
			type="hidden" value="<spring:message code="pay.option.account" />"
			id="CQ" /> <input type="hidden"
			value="<spring:message code="pay.option.poNoDate" text="Demand Draft Date" />" id="POD" /> <input
			type="hidden" value="<spring:message code="pay.option.ddNoDate.eg" text="Demand Draft Date"/>"
			id="DDD" /> <input type="hidden"
			value="<spring:message code="pay.option.accountDate" />" id="CQD" />
		<input type="hidden"
			value="<spring:message code="pay.option.ppNoDate" text="Postal Payment Date"/>"
			id="PPD" /> <input type="hidden"
			value="<spring:message code="pay.option.ppNo" text="Postal Payment No"/>"
			id="PPN" /> <input type="hidden"
			value="<spring:message code="pay.option.njsDate" text="Non-Judicial Stamp Date"/>"
			id="NJSD" /> <input type="hidden"
			value="<spring:message code="pay.option.njsNo" text="Non-Judicial Stamp No"/>"
			id="NJSN" /> <input type="hidden"
			value="<spring:message code="pay.option.challanDate" text="Challan Date"/>"
			id="CD" /> <input type="hidden"
			value="<spring:message code="pay.option.challanNo" text="Challan No"/>"
			id="CN" />
			<input type="hidden"
			value="<spring:message code="pay.option.egAmount" text="Demand Draft Amount"/>"
			id="DDA" />
			<input type="hidden"
			value="<spring:message code="pay.option.egDDFavour" text="Demand Draft In Favour Of"/>"
			id="DIF" />
			<input type="hidden"
			value="<spring:message code="pay.option.egAmount" text="Cheque Amount"/>"
			id="CDA" />
			<input type="hidden"
			value="<spring:message code="pay.option.egDDFavour" text="Cheque In Favour Of"/>"
			id="CIF" />
	</div>

</div>
</div>