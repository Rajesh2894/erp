<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/cfc/challanAtULB.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	prepareChallanDateTag();
});

function saveForm(element) {
	var paramValue =''
	if($("#pageFlag").val()=='N')
		{
		 paramValue = 'PrintULBCounterReceipt';
		}
	else if($("#pageFlag").val()=='C')
		{
		 paramValue = 'PrintCounterReceipt';
		}

var result=	 saveOrUpdateForm(element, $('#generatereceipt').val(),
			'ChallanAtULBCounter.html?' + paramValue, 'saveform');
prepareChallanDateTag();	
return result;
	
}

function prepareChallanDateTag() {
	var dateFields = $('.lessthancurrdate');
	dateFields.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
}

function regenerateChallan(obj)
{
	 return saveOrUpdateForm(obj,"Challan regenearted successfully!", 'ChallanAtULBCounter.html?PrintRegenerateChallan', 'regenerateChallan');
}
</script>
	<!-- PAGE BREADCRUM SECTION  -->
<%-- 	<ol class="breadcrumb">
		<li><a href="AdminHome.html"><spring:message code="cfc.home" text="Home"/></a></li>
		<li><a href="javascript:void(0);"><spring:message code="cfc.module" text="CFC"/></a></li>
		<li><a href="javascript:void(0);"><spring:message code="cfc.transaction" text="Transactions"/></a></li>
		<li class="active"><spring:message text="Challan At ULB Counter" code="payment.payChallanULB" /></li>
	</ol> --%>
	
	<apptags:breadcrumb></apptags:breadcrumb>
	
	
	<div class="content"> 
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message text="Challan At ULB Counter" code="payment.payChallanULB" /></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
        <div class="widget-content padding">
		
		<form:form action="ChallanAtULBCounter.html" name="frmMasterForm" id="frmMasterForm" class="form-horizontal">
			<jsp:include page="/jsp/tiles/validationerror.jsp"/>
			<form:hidden path="pageUrlFlag" id="pageFlag" value="${command.pageUrlFlag}"/>
			<input type="hidden" value="<spring:message text="Proceed to Generate Receipt." code="challan.generatereceipt" />" id="generatereceipt">
			<!-- START CODING FROM HERE   -->
			<c:if test="${command.pageUrlFlag eq 'N' }">



			<h4 class="margin-top-0"><spring:message code="chn.searchCriteria" text="Search Criteria"/></h4>
			
			<div class="form-group">
                <label class="col-sm-2 control-label"><spring:message text="Challan No" code="chn.challanNumber"/></label>
				 <div class="col-sm-4"><form:input path="challanNo" cssClass="form-control hasNumber"  maxlength="18"/></div>
				<label class="col-sm-2 control-label"><spring:message text="Application No" code="chn.applicationNo"/></label>
				<div class="col-sm-4"><form:input path="applicationNo" cssClass="form-control hasNumber" maxlength="16"/></div>
			</div>

			<div class="text-center margin-bottom-10">
			 <button type="submit" class="btn btn-info" onclick="findAll(this)"><i class="fa fa-search"></i> <spring:message code="master.search" text="Search" /></button>
          <button type="button" class="btn btn-warning" onclick="clearForm('ChallanAtULBCounter.html')"><spring:message code="reset.msg" text="Reset"/></button>				
              </div>
              <%-- Told by Nilima Mam--%>
              
            <%--  <c:if test="${command.expiredFlag eq false }">
              <input type="button" value="<spring:message  code="" text="Regenerate Challan"/>" class="btn btn-success"  onclick="regenerateChallan(this)" />
             </c:if> --%>
              </c:if>
              
           <c:if test="${command.pageUrlFlag eq 'C' and command.expiredFlag eq true}">
           <div class="text-center margin-bottom-10">
              <input type="button" value="<spring:message  code="" text="Regenerate Challan"/>" class="btn btn-success" onclick="regenerateChallan(this)" />
           </div>
           </c:if>   
           
			<c:if test="${not empty command.challanDetails and command.expiredFlag ne true}">
			
			
			<div class="panel-group accordion-toggle" id="accordion_single_collapse">
			
			<div class="panel panel-default">
		  	<div class="panel-heading"><h4 class="panel-title"><a data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" href="#Additional_Owners"><spring:message text="Challan Details" code="challan.payment.details"/></a></h4></div>
		 	<div id="Additional_Owners" class="panel-collapse collapse">
			<div class="panel-body">
			
 			
			<div class="form-group">
                <label class="col-sm-2 control-label"><spring:message text="Challan No" code="chn.challanNumber" /> </label>
				<div class="col-sm-4"><span class="form-control disabled"><c:out value="${command.challanDetails.challanNo }"></c:out></span></div>
				 <label class="col-sm-2 control-label"><spring:message text="Application No" code="chn.applicationNo"/> </label>
				 <div class="col-sm-4"><span class="form-control disabled"><c:out value="${command.challanDetails.applicationId }"></c:out></span></div>
			</div>
			<div class="form-group">
                <label class="col-sm-2 control-label"><spring:message text="Challan Date" code="chn.challanGeneratedDate" /> </label>
				<div class="col-sm-4">
				<div class="input-group">
			 <span class="form-control disabled">
				 	<c:set value="${command.challanDetails.challanDate }" var="challanDate"></c:set>
					<fmt:formatDate type="date" value="${challanDate}" pattern="dd/MM/yyyy" />
					</span>
	      <label class="input-group-addon"><i class="fa fa-calendar"></i></label>
	    </div>
			
				</div>
				<label class="col-sm-2 control-label"><spring:message text="Name Of Applicant" code="chn.name"/> </label>
				<div class="col-sm-4"><span class="form-control disabled"><c:out value="${command.challanDetails.applicantName}"></c:out></span>
				</div>
			</div>
			<div class="form-group">
                <label class="col-sm-2 control-label">
                	<spring:message text="Service Name" code="chn.serviceName"/> </label>
                	<div class="col-sm-4">
				 	<span class="form-control height-auto disabled"><c:out value="${command.challanDetails.serviceName }"></c:out></span>
				</div>
				<label class="col-sm-2 control-label"><spring:message text="Amount" code="chn.amount"/> </label>
				<div class="col-sm-4">
				 		<fmt:formatNumber type="number" value="${command.challanDetails.challanAmount }" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amt"/>
						<span class="form-control disabled" id="amount"><c:out value="${amt }"></c:out></span>
				</div>
			</div>
			</div>
			</div>
			</div>
			
			<%-- 
			<div class="panel panel-default">
		  	<div class="panel-heading"><h4 class="panel-title"><a data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" href="#challan"><spring:message text="Payment" code="challan.payment"/></a></h4></div>
		 	<div id="challan" class="panel-collapse collapse">
			<div class="panel-body">
			
  		
	
			</div>
			</div>
			</div> --%>
			
			
			
			<div class="panel panel-default">
		  	<div class="panel-heading"><h4 class="panel-title"><a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#collection"><spring:message code="payment.header.name" text="Collection Details"/></a></h4></div>
		 	<div id="collection" class="panel-collapse collapse in">
			<div class="panel-body">
		
			<div class="form-group">
                <label class="col-sm-2 control-label required-control">
                	<spring:message code="payment.freeMode" text="Payment Mode"/> 
                </label>
			   	<apptags:lookupField items="${command.userSession.paymentMode}"
					path="offline.payModeIn" cssClass="form-control subsize" changeHandler="enableDisableCollectionModes()"
					selectOptionLabelCode="select payment mode" hasId="true" 
					isMandatory="true" hasChildLookup="false"></apptags:lookupField>
					
			
					</div>
					
			<div id="hideShow">		
 	
		<div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message code="payment.drawnOn" text="Drawn On bank"/></label>
			<div class="col-sm-4">
				<form:select path="offline.cbBankId" onchange="getBankCode(this)" id="bankID" cssClass="form-control chosen-select-no-results mandColorClass">
					<form:option value="0" ><spring:message text="Select Bank Name" code="challan.select.bank"/> </form:option>
					<form:options items="${command.appSession.customerBanks}" />
				</form:select>              
			</div>
			<label class="col-sm-2 control-label"><spring:message code="payment.bankCode" text="Bank Code"/></label>
			<div class="col-sm-4">
				<form:input  path="offline.bmDrawOn" class="form-control disablefield" readonly="true" id="drawnOn" />
			</div>
		</div>
		
		 <div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message code="payment.accountNo" text="Account no."/></label>
			<div class="col-sm-4">
			  <form:input  path="offline.bmBankAccountId" class="hasNumber form-control" id="acNo" maxlength="16"/>             
			</div>
			<label class="col-sm-2 control-label required-control" id="selectType"><spring:message code="payment.checkOrDDNo" text="Cheque Or DD no."/></label>
			<div class="col-sm-4">
				<form:input  path="offline.bmChqDDNo" class="form-control hasNumber" id="chqNo" maxlength="10" />	
			</div>
		</div>
		
		 <div class="form-group">
			<label class="col-sm-2 control-label required-control" id="selectDate"><spring:message code="payment.checkOrDDDate" text="Cheque Or DD Date"/></label>
			<div class="col-sm-4">
			
			<div class="input-group">
			  <apptags:dateField fieldclass="lessthancurrdate chqDate" datePath="offline.bmChqDDDate" cssClass="form-control"/>             
	      <label class="input-group-addon"><i class="fa fa-calendar"></i></label>
	    </div>
			
			</div>
			<label class="col-sm-2 control-label"><spring:message code="payment.reciept.amount" text="Amount"/></label>
			<div class="col-sm-4">
				<form:input  path="offline.amountToPay" class="form-control" readonly="true" id="amountToPay"/>
			</div>
		</div>
		</div>
		
        </div>
        </div>
        </div>
		</div>
		
        
        
        
	<input type="hidden" value="<spring:message code="pay.option.poNo" text="PO no."/>" id="PO">
	<input type="hidden" value="<spring:message code="pay.option.ddNo" text="DD no."/>" id="DD">
	<input type="hidden" value="<spring:message code="pay.option.account" text="Cheque no."/>" id="CQ">
	<input type="hidden" value="<spring:message code="pay.option.poNoDate" text="PO Date "/>" id="POD">
	<input type="hidden" value="<spring:message code="pay.option.ddNoDate" text="DD Date"/>" id="DDD">
	<input type="hidden" value="<spring:message code="pay.option.accountDate" text="Account Date"/>" id="CQD">
			<div class="text-center">
              <input type="button" value="<spring:message code="bt.save" text="Submit"/>" onclick="return saveForm(this);" class="btn btn-success btn-submit">
              <%-- <c:if test="${command.pageUrlFlag eq 'N' }">
              <apptags:backButton url="ChallanAtULBCounter.html"></apptags:backButton>
              </c:if>
              <c:if test="${command.pageUrlFlag eq 'C' }"> --%>
              <apptags:backButton url="AdminHome.html"></apptags:backButton>
             <%--  </c:if> --%>
            </div>
            </c:if>
		</form:form>
	</div>
	</div>
	</div>
	