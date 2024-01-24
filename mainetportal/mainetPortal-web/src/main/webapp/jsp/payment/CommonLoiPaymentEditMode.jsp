<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/script-library.js"></script>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
$(document).ready(function(){
   var amt=($("#payAtCounterId")).val();
   $("#amountToPay").val(amt);
  
		
		var totPay	=	parseFloat($("#loiAmountToPay").val());
	
		$("#loiAmountToPay").val(totPay.toFixed($("#decimalpoint").val()));
	

	
});
</script>

<div class="form-div">

	<!-- PAGE BREADCRUM SECTION  -->
	<ul class="breadcrumbs">
		<li><a href="CitizenHome.html"><spring:message code="menu.home" /></a></li>

		<li class="active"><spring:message code="onl.loi.payment" /></li>
	</ul>


	<h1>
		<spring:message text="LOI PAYMENT" code="onl.loi.payment" />
	</h1>

	<div id="content">
		<div class="mand-label">
			<spring:message code="MandatoryMsg" text="MandatoryMsg" />
		</div>



		<form:form action="CommonLoiPayment.html" name="frmCommonLoiPayment" 	id="frmCommonLoiPayment"  cssClass="form">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<input type="hidden" value="${command.minDecimalSize}" id="decimalpoint">
			<input type="hidden" id="successMsg" value="<spring:message code="onl.loi.payreceived"/>">

			<div class="form-elements clear">
				
				 <div class="regheader">
					<spring:message code="onl.loi.payDetails"/>
				</div>  
				<div class="element">
					<label for="LOI No"><spring:message code="onl.loi.loiNo" /></label>
					<apptags:inputField fieldPath="entity.loiNo" hasId="true" 	maxlegnth="12" isReadonly="true" cssClass="disablefield"/>
				</div>
				
				<div class="element">
					<label for="LOI DATE"><spring:message code="onl.loi.loiDate" /></label>
					<apptags:inputField fieldPath="entity.loiDate" hasId="true" 	maxlegnth="10" isReadonly="true" cssClass="disablefield"/>
				</div>
				
			</div>
			<div class="table clear padding_5">
				<div class="col-155"><label for="Appl NAME">
				
					
				
				
				
			
					<spring:message code="onl.loi.appliName" />
			
				
				
				</label></div>
					<div class="col-9"><apptags:inputField fieldPath="entity.applicantName" hasId="true" 	maxlegnth="12" isReadonly="true" cssClass="disablefield input2"/></div>
			</div>
			<c:if test="${command.deptSelectedCode eq 'ML'}">
			<div class="table clear padding_5">
				<div class="col-155"><label for="Appl NAME">
					<spring:message code="market.holdername" />
			
				</label></div>
				
				<input type="text" id="" value="${command.holderName}" disabled="disabled" readonly="readonly" class="disablefield ">
				</div>
					</c:if>
			<div class="table clear padding_5">
				<div class="col-155"><label for="Appl NAME"><spring:message code="" text="Service Name" /></label></div>
					<div class="col-9"><apptags:inputField fieldPath="entity.smServiceName" hasId="true" 	maxlegnth="12" isReadonly="true" cssClass="disablefield input2"/></div>
			</div>
			
			<div class="form-elements clear">
				<div class="element">
					<label for="Appl NO"><spring:message code="onl.loi.applNo" /></label>
					<apptags:inputField fieldPath="entity.apmApplicationId" hasId="true" 	maxlegnth="12" isReadonly="true" cssClass="disablefield"/>
				</div>
				
				<div class="element">
					<label for="Tottal Amount"><spring:message code="onl.loi.totalamount" text="Loi Amount"/></label>
			 	<form:input path="entity.amountToPay" id="loiAmountToPay" maxlength="12" readonly="true" cssClass="disablefield"/>
				</div>
				
			</div>

			 <div class="regheader">
				<spring:message code="onl.loi.charge.desc" />
			</div> 
		<div class="overflow_auto clear padding_top_10">
			<table class="gridtable">
				<tr>
					<th><spring:message code="onl.loi.serialNo" /></th>
					<th><spring:message code="onl.loi.charge.desc" /></th>
					<th><spring:message code="onl.loi.amount" /></th>
				</tr>
				<c:forEach items="${command.entity.commonDto}" var="pay" varStatus="count">
					<tr>
						
						<td>${count.index+1}</td>
						<td>${pay.chargeName}</td>
						<td><fmt:formatNumber type="number" minFractionDigits="${command.minDecimalSize}" groupingUsed="false"   value="${pay.amount}"/>	</td>
					</tr>
				</c:forEach> 				
					<tr>						
						<th colspan="2"><spring:message code="market.total" /></th>
					 	<th><fmt:formatNumber type="number" minFractionDigits="${command.minDecimalSize}" groupingUsed="false"   value="${command.entity.amountToPay}"/></th>				 	
					</tr>
			</table>
           </div>
       
           <c:if test="${not empty command.entity.jspFilePath}">           
          		 <jsp:include page="${command.entity.jspFilePath}" flush="true" />           
           </c:if>
            <c:if test="${empty command.entity.jspFilePath}">           
        		<form:hidden path="amountToPay" value="${command.entity.amountToPay}" id="hiddenAmount"/>	  
           </c:if>
           
           
           
           <div class="regheader"><spring:message code="advertise.paymentOption" /></div>
         <input type="hidden" id="payAtCounterId" value="${command.entity.amountToPay}" />  
			 <fieldset>
			
			</fieldset> 

			<div class="btn_fld margin_top_10">
				 
				<input type="submit" value="<spring:message code="bt.save" text="save" />"
					onclick="return saveFormValues(this);"  class="css_btn">
					<apptags:backButton url="CommonLoiPayment.html"/>
			</div>
		</form:form>
	</div>
</div>

