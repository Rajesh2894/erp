<!-- Start JSP Necessary Tags -->
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
<!-- End JSP Necessary Tags -->

<script src="js/property/lastPaymentDetails.js"></script>

<div class="accordion-toggle">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#LastBillPayment"><spring:message
				code="property.lastpayment" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="LastBillPayment">
		<div class="form-group">
			
			<label class="col-sm-2 control-label">
			<spring:message code="property.billpayment"/>
			<span class="mand">*</span>
			</label>
			<div class="col-sm-4">
			<label for="notApplicaple" class="radio-inline">
			<form:radiobutton path="provisionalAssesmentMstDto.proAssBillPayment" value="N" name="billPayment" id="notApplicaple" onchange="billPaymentValue()"/>
			<spring:message code="property.na"/></label>
			<label for="manual" class="radio-inline">
			<form:radiobutton name="billPayment" path="provisionalAssesmentMstDto.proAssBillPayment" value="M" id="manual" onchange="billPaymentValue()"/>
			<spring:message code="property.manual"/></label>
			</div>	
		</div>
		
		<div class="form-group">
			
			<apptags:input isMandatory="true" labelCode="property.receiptno" path="provisionalAssesmentMstDto.assLpReceiptNo" maxlegnth="15" cssClass="preventSpace Autocomplete"></apptags:input>

		 	 <label class="col-sm-2 control-label required-control"><spring:message
									code="property.receiptamount" text="" /></label>
			<div class="col-sm-4">						
			<form:input cssClass="form-control mandColorClass text-right Autocomplete"
									onkeypress="return hasAmount(event, this, 15, 2)"
									id="proAssLpReceiptAmt" path="provisionalAssesmentMstDto.assLpReceiptAmt"
									placeholder="999999.99"
									onchange="getAmountFormatInDynamic((this),'proAssLpReceiptAmt')"
									data-rule-required="true"></form:input>
			</div>
			
		</div>
		
		<div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message code="property.receiptdate"/></label>
			<div class="col-sm-4">
		 	<div class="input-group"> 
			<form:input path="provisionalAssesmentMstDto.assLpReceiptDate" class="lessthancurrdate form-control mandColorClass dateClass" id="proAssLpReceiptDate" data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off"/>
			<span class="input-group-addon"><i class="fa fa-calendar"></i></span> </div>
			</div>
									
			<label  class="col-sm-2  required-control ">
			<spring:message code="property.lastpaymentupto"/>
			</label>
			<div class="col-sm-4">
				<form:select id="lastpaymentMadeUpto" path="provisionalAssesmentMstDto.assLpYear" class="form-control mandColorClass" data-rule-prefixValidation="true" onchange="getPaymentMadeDetails()">
					<form:option value="0">
						<spring:message code="property.sel.optn" text="Select" />
					</form:option>
					<c:forEach items="${command.schedule}" var="billschedule">
					<form:option value="${billschedule.lookUpId}">${billschedule.lookUpCode}</form:option>
					</c:forEach>
				</form:select>

			</div>
		</div>
		
		<div class="form-group">
		
		 	 <label class="col-sm-2 control-label required-control"><spring:message
									code="property.billamount" text="" /></label>
			<div class="col-sm-4">						
			<form:input cssClass="form-control mandColorClass text-right Autocomplete"
									onkeypress="return hasAmount(event, this, 15, 2)"
									id="billAmount" path="provisionalAssesmentMstDto.billAmount"
									placeholder="999999.99"
									onchange="getAmountFormatInDynamic((this),'billAmount')"
									data-rule-required="true"></form:input>
			</div>
		 	 
			 	
			<label class="col-sm-2 control-label required-control"><spring:message
									code="property.OutStandingAmount" text="" /></label>
			<div class="col-sm-4">						
			<form:input cssClass="form-control mandColorClass text-right Autocomplete"
									onkeypress="return hasAmount(event, this, 15, 2)"
									id="outstandingAmount" path="provisionalAssesmentMstDto.outstandingAmount"
									placeholder="999999.99"
									onchange="getAmountFormatInDynamic((this),'outstandingAmount')"
									data-rule-required="true"></form:input>
			</div>	 		
		</div>
	
</div>
</div>
