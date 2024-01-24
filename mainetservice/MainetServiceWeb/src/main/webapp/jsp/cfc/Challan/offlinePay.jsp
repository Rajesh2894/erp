<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript" src="js/cfc/challan/offlinePay.js"></script>
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
			<a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#Paymentmode"><spring:message code="challan.receipt.label"/></a>
		</h4>
	</div>
	<div id="Paymentmode" class="panel-collapse collapse in">
		<div class="panel-body">
			<div class="form-group">
				<label class="control-label col-sm-2"><spring:message
						code="rti.selectPaymrntMode" /><span class="mand">*</span></label>
				<div class="radio col-sm-8">
					<%-- <label class=" margin-right-20" id="onlinebutton"> <form:radiobutton
							path="offlineDTO.onlineOfflineCheck" value="Y"
							onclick="showDiv(this);" disabled="false" /> <spring:message
							code="rti.onlinePay" />
					</label>  --%>
					<input type="hidden" id="offlineModeFlagId" value="${command.getOfflinePayModeByPrefix()}"/>
					
					<c:if test="${command.getOfflinePayModeByPrefix() ne null && command.getOfflinePayModeByPrefix() eq 'Y'}">
					<label class=" margin-right-20" id="offlineLabel"> <form:radiobutton
							path="offlineDTO.onlineOfflineCheck" value="N"
							onclick="showDiv(this);" id="offlinebutton" /> <spring:message
							code="rti.offlinePay" />
					</label>
                   </c:if>
					<c:if
						test="${command.getLoggedInUserType() eq 'CFC' ||command.getLoggedInUserType() eq ''}">
						<label> <form:radiobutton
								path="offlineDTO.onlineOfflineCheck" value="P" id="payAtCounter"
								onclick="showDiv(this);" checked="checked" /> <spring:message
								code="rti.payAtCounter" />
						</label>
					</c:if>
				</div>

			</div>
		
<%-- 	----------------------------------------------offline payment--------------------------------------------------- --%>
<div class="offlinepayment" id="offlinepayment">
	<div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message
					code="rti.offlinePaymentSelection" /></label>
				<div class="col-sm-4">						
			<c:set var="baseLookupCode" value="OFL" />
			<form:select path="offlineDTO.oflPaymentMode" cssClass="form-control" id="oflPaymentMode">
				<c:forEach items="${command.getLevelData(baseLookupCode)}" var="oflMode">
					<form:option  code="${oflMode.lookUpCode}" value="${oflMode.lookUpId}" >${oflMode.lookUpDesc}</form:option>
					</c:forEach>
</form:select>	
</div>
		<%-- 	
			<apptags:lookupField
					items="${command.getLevelData(baseLookupCode)}"
					path="offlineDTO.oflPaymentMode" cssClass="form-control"
					selectOptionLabelCode="rti.sel.paymentmode" hasId="true"
					changeHandler="showForm(this)" isMandatory="true"/> --%>
	<script type="text/javascript">
	fieldsVisible(${command.offlinePay},${command.getBank()},${command.getUlb()},${command.getDd()},${command.getPostal()});
	</script>
	
	</div>

</div>


<!-- --------------------------------------------payment challan @ bank	------------------------------------------------ -->

<div class="PCB" id="PCB">
	 <div class="form-group">	
			<label class="col-sm-2 control-label required-control"><spring:message code="rti.bankName" /> :</label> 
			<div class="col-sm-4">
			<form:select path="offlineDTO.bankaAccId" cssClass="form-control chosen-select-no-results" id="bankAccId">
					<form:option value="0"><spring:message code="rti.sel.bank" /></form:option>
					<form:options items="${userSession.bankList}" />
				</form:select>
		</div>
</div>
	</div>

<!-- ---------------------------------------------payment challan @ ULB------------------------------------------------	 -->

<div class="PCU" id="PCU">
	<div class="form-elements">
	
					</div>
	</div>


<%-- -----------------------------------------------payment @ ULB Counter---------------------------------------------------- --%>
 <div class="PPO" id="PPO"> 
<div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message code="payment.freeMode" /></label>
			<apptags:lookupField items="${command.userSession.paymentMode}"
				path="offlineDTO.payModeIn" cssClass="form-control" changeHandler="enableDisableCollectionModes(this)"
				selectOptionLabelCode="rti.sel.paymentmode" hasId="true" 
				isMandatory="true">
			</apptags:lookupField>
	</div>
	</div>
	
	
	<div class="overflow CPAUC">
	<h4><spring:message code="payment.header.name" /></h4>
	<a class="text-center" href="#" onclick="openBankForm();"><spring:message code="add.bank.note" text="Click Here To Add New Bank"/></a>
				<div class="form-group" id="bankDet">
                  <label class="col-sm-2 control-label required-control"><spring:message code="payment.drawnOn" /></label>
                  <div class="col-sm-4">
						<%-- <form:select path="offlineDTO.cbBankId" onchange="getBankCode(this)" id="bankID" cssClass="form-control chosen-select-no-results">
							<form:option value="0"><spring:message code="rti.sel.bank" /></form:option>
							<form:options items="${command.appSession.customerBanks}" />
						</form:select> --%>

						<form:select path="offlineDTO.cbBankId"
							onchange="getBankCode(this)" id="bankID"
							class="form-control chosen-select-no-results">
							<form:option value="0"><spring:message code='rti.sel.bank' />
							</form:option>
						</form:select>
					</div>	
						
						<label class="col-sm-2 control-label"><spring:message code="payment.bankCode" /></label>
						<div class="col-sm-4"><form:input  path="offlineDTO.bmDrawOn" class="form-control" readonly="true" id="drawnOn" />
				</div>
    </div> 
    
    <div class="form-group" >
    <div id="accNo">
	<label class="col-sm-2 control-label required-control"><spring:message code="payment.accountNo" /></label>
	<div  class="col-sm-4"><form:input  path="offlineDTO.bmBankAccountId" class="form-control hasNumber" id="acNo" maxlength="16"/></div>
	</div>


	<label class="col-sm-2 control-label required-control" id="selectType"><spring:message code="payment.checkOrDDNo" /></label>
	<div class="col-sm-4"><form:input  path="offlineDTO.bmChqDDNo" class="form-control hasNumber" id="chqNo" maxlength="10" /></div>
</div>             

<div class="form-group">
	<label class="col-sm-2 control-label required-control" id="selectDate"><spring:message code="payment.checkOrDDDate"/></label>
	<div class="col-sm-4">
		<div class="input-group">
	      <apptags:dateField fieldclass="lessthancurrdate chqDate" datePath="offlineDTO.bmChqDDDate" cssClass="form-control"/> 
	      <label class="input-group-addon"><i class="fa fa-calendar"></i></label>
	    </div>
	</div>

	<label class="col-sm-2 control-label"><spring:message code="payment.reciept.amount"/></label>
	<div class="col-sm-4">
		<fmt:formatNumber value="${command.offlineDTO.amountToShow}" type="number" var="paymentTagAmount" minFractionDigits="2" maxFractionDigits="2" groupingUsed="false" />
		<form:input  path="offlineDTO.amountToShow" value="${paymentTagAmount}" class="form-control amountAlign text-right" readonly="true" id="amountToPay"/>		
	</div>
</div>	
	
	<div class="form-group" id="PCP">
					<label class="col-sm-2 control-label"><spring:message
							code="common.uploadfiles" text="Upload File"/></label>
					<apptags:formField fieldType="7" labelCode="" hasId="true"
						fieldPath="offlineDTO.postalCardDocList[0]" isMandatory="false"
						showFileNameHTMLId="true" fileSize="TRADE_COMMON_MAX_SIZE"
						maxFileCount="CHECK_LIST_MAX_COUNT"
						validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
						currentCount="111" />
						<h6 class="text-blue-2">
												<spring:message code="common.uploadfileupto" text="(Upload File upto 5MB and only pdf,doc,docx,jpeg,jpg,png,gif,bmp)" />
											</h6>
				</div>	
	
</div>
	
	<input type="hidden" value="<spring:message code="pay.option.poNo" />" id="PO" />
	<input type="hidden" value="<spring:message code="pay.option.ddNo" />" id="DD" />
	<input type="hidden" value="<spring:message code="pay.option.account" />" id="CQ" />
	<input type="hidden" value="<spring:message code="pay.option.poNoDate" />" id="POD" />
	<input type="hidden" value="<spring:message code="pay.option.ddNoDate" />" id="DDD" />
	<input type="hidden" value="<spring:message code="pay.option.accountDate" />" id="CQD" />
	<input type="hidden" value="<spring:message code="pay.option.ppNoDate" text="Postal Payment Date"/>" id="PPD" />
	<input type="hidden" value="<spring:message code="pay.option.ppNo" text="Postal Payment No"/>" id="PPN" />
	<input type="hidden" value="<spring:message code="pay.option.njsDate" text="Non-Judicial Stamp Date"/>" id="NJSD" />
	<input type="hidden" value="<spring:message code="pay.option.njsNo" text="Non-Judicial Stamp No"/>" id="NJSN" />
	<input type="hidden" value="<spring:message code="pay.option.challanDate" text="Challan Date"/>" id="CD" />
	<input type="hidden" value="<spring:message code="pay.option.challanNo" text="Challan No"/>" id="CN" />
</div>

				
			
		

	</div>
</div>