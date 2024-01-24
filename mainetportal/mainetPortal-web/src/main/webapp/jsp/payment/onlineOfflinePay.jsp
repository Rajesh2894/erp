<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/challan/offlinePay.js"></script>

<script>

jQuery('.hasNumber').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
   
});
</script>
 
  <div class="accordion-toggle">
  <div class="panel panel-default">
  <div class="panel-heading">
				 <h4 id="checkListDetails" class="margin-top-0 margin-bottom-10 panel-title">
									<a data-toggle="collapse" class="collapsed"data-parent="#accordion_single_collapse" href="#Paymentmode" tabindex="-1"> 
								Payment mode </a>
				</h4></div>
<div class="panel-collapse collapse in" id ="Paymentmode">  
<div class="panel-body">
	 
	<div class="form-group">
		<label class="control-label col-sm-2"><spring:message code="onlineoffline.label.selectPaymrntMode"/><span class="mand">*</span></label>
			<div class="col-sm-4">
				<label class="radio-inline" id="onlineLabel">
				<form:radiobutton path="offlineDTO.onlineOfflineCheck" value="Y" onclick="showDiv(this);" disabled="false"  />
				<spring:message code="onlineoffline.label.onlinePay"/>
				</label>
				<label class="radio-inline" id="offLineLabel">
				<form:radiobutton path="offlineDTO.onlineOfflineCheck" value="N" onclick="showDiv(this);" id="onlineOfflineCheck1" />
				<spring:message code="onlineoffline.label.offlinePay"/>			
				</label>
						
								
			</div>
					
</div>


<div class="offlinepayment" id="offlinepayment">
	<div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message
					code="onlineoffline.label.offlinePaymentSelection" /></label>


			<c:set var="baseLookupCode" value="OFL" />
			<div class="col-sm-4">
			<form:select path="offlineDTO.oflPaymentMode" cssClass="form-control" id="oflPaymentMode">
				<c:forEach items="${command.getLevelData(baseLookupCode)}" var="oflMode">
					<form:option  code="${oflMode.lookUpCode}" value="${oflMode.lookUpId}" >${oflMode.lookUpDesc}</form:option>
					</c:forEach>
			</form:select>	
			</div>
		
	<script>
	fieldsVisible(${command.offlinePay},${command.getBank()},${command.getUlb()},${command.getDd()},${command.getPostal()});
	</script>



	</div>

</div>



<div class="PCB" id="PCB">
	 <div class="form-group">	
			<label class="col-sm-2 control-label required-control"><spring:message code="onlineoffline.label.bankName" /> :</label> 
			<div class="col-sm-4">
			<form:select path="offlineDTO.bankaAccId" cssClass="form-control chosen-select-no-results " id="bankAccId">
					<form:option value="0"><spring:message code="onlineoffline.label.sel.bank" /></form:option>
					<form:options items="${userSession.bankList}" />
				</form:select>
		</div>
</div>
	</div>


<div class="PCU" id="PCU">
	<div class="form-elements">
	
					</div>
	</div>


 <div class="PPO" id="PPO"> 
<div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message code="onlineoffline.label.payment.freeMode" /></label>
			<div class="col-sm-4">
			<apptags:lookupField items="${command.userSession.paymentMode}"
				path="offlineDTO.payModeIn" cssClass="form-control chosen-select-no-results" changeHandler="enableDisableCollectionModes(this)"
				selectOptionLabelCode="onlineoffline.label.sel.paymentmode" hasId="true" 
				isMandatory="true">
			</apptags:lookupField>
		</div>
	</div>
	</div>
	
	
	<div class="overflow CPAUC">
	<h4><spring:message code="onlineoffline.label.payment.header.name" /></h4>
	
	
	<div class="form-group">
                  <label class="col-sm-2 control-label required-control"><spring:message code="payment.drawnOn" /></label>
                  <div class="col-sm-4">
						<form:select path="offlineDTO.cbBankId" onchange="getBankCode(this)" id="bankID" cssClass="form-control">
							<form:option value=""><spring:message code="rti.sel.bank" /></form:option>
							<form:options items="${command.appSession.customerBanks}" />
						</form:select></div>	
						
						<label class="col-sm-2 control-label required-control"><spring:message code="payment.bankCode" /></label>
						<div class="col-sm-4"><form:input  path="offlineDTO.bmDrawOn" class="form-control disablefield" readonly="true" id="drawnOn" />
				</div>
    </div> 
    
    <div class="form-group">
	<label class="col-sm-2 control-label required-control"><spring:message code="onlineoffline.label.payment.accountNo" /></label>
	<div class="col-sm-4"><form:input  path="offlineDTO.bmBankAccountId" class="form-control hasNumber" id="acNo" maxlength="12"/></div>


	<label class="col-sm-2 control-label required-control" id="selectType"><spring:message code="onlineoffline.label.payment.checkOrDDNo" /></label>
	<div class="col-sm-4"><form:input  path="offlineDTO.bmChqDDNo" class="form-control hasNumber" id="chqNo" maxlength="10" /></div>
</div>             

<div class="form-group">
	<label class="col-sm-2 control-label required-control" id="selectDate"><spring:message code="onlineoffline.label.payment.checkOrDDDate"/></label>
	<div class="col-sm-4"><apptags:dateField fieldclass="lessthancurrdate chqDate"
							datePath="offlineDTO.bmChqDDDate"
							cssClass="form-control"/> 	</div>

	<label class="col-sm-2 control-label required-control"><spring:message code="onlineoffline.label.payment.reciept.amount"/></label>
	<div class="col-sm-4"><form:input  path="offlineDTO.amountToPay" class="form-control amountAlign" readonly="true" id="amountToPay"/>		
			</div>
</div>
	
	<input type="hidden" value="<spring:message code="onlineoffline.label.pay.option.poNo" />" id="PO" />
	<input type="hidden" value="<spring:message code="onlineoffline.label.pay.option.ddNo" />" id="DD" />
	<input type="hidden" value="<spring:message code="onlineoffline.label.pay.option.account" />" id="CQ" />
	<input type="hidden" value="<spring:message code="onlineoffline.label.pay.option.poNoDate" />" id="POD" />
	<input type="hidden" value="<spring:message code="onlineoffline.label.pay.option.ddNoDate" />" id="DDD" />
	<input type="hidden" value="<spring:message code="onlineoffline.label.pay.option.accountDate" />" id="CQD" />
</div>
</div>
</div>
</div>
</div>