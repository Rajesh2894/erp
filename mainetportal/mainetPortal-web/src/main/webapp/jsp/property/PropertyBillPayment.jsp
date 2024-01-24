<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <script src="js/mainet/validation.js"></script>
 <script src="js/property/propertyBillPayment.js" ></script>   
 
 <apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here --> 
<div class="content"> 
  <div class="widget">
    <div class="widget-header">
      <h2><spring:message code="propertyBill.BillCollection"/></h2>
      <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
    </div>
    <div class="widget-content padding">
      <form:form action="PropertyBillPayment.html" class="form-horizontal" name="PropertyBillPayment" id="PropertyBillPayment">
       		<jsp:include page="/jsp/tiles/validationerror.jsp" />
       		<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>
       		
	
		
            <div class="panel-group accordion-toggle" id="#accordion_single_collapse">  
<%--        <c:if test="${(command.billMas.csIdn ne null) or (not empty command.message && command.message ne null)}"> --%>
        	<div class="panel panel-default" id="paymentInfo">
	                <div class="panel-heading">
	                  <h4 class="panel-title"><a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#ConnectionDetails">Property Details</a></h4>
	  				</div>
  				
		  			<div id="ConnectionDetails" class="panel-collapse collapse in">
				         <div class="panel-body">
			<div class="form-group" >		
				<apptags:input labelCode="propertydetails.PropertyNo." path="propBillPaymentDto.assNo" cssClass="mandColorClass" isDisabled="true"></apptags:input>
				<apptags:input labelCode="propertydetails.oldpropertyno" path="propBillPaymentDto.assOldpropno" cssClass="mandColorClass" isDisabled="true"></apptags:input>		
			</div>
				         <div class="form-group">		         
				         	<apptags:input labelCode="ownerdetail.Ownername" path="billPayDto.primaryOwnerName" isDisabled="true" ></apptags:input>
<%-- 				         	<apptags:input labelCode="propertydetails.PropertyNo." path="billPayDto.propNo" isDisabled="true" ></apptags:input>
 --%>				         	
				         </div>
				         </div>
		         	</div>
		         	</div>
         		<div class="panel panel-default" >
		          	 <div class="panel-heading">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#propertyAddress"><spring:message
									code="property.Propertyaddress" /></a>
						</h4></div>
						<div class="panel-collapse collapse in" id="propertyAddress">
						<div class="panel-body">
							<div class="form-group">
								
								<apptags:textArea isMandatory="true" labelCode="property.propertyaddress" path="billPayDto.address" isDisabled="true"></apptags:textArea>
								<apptags:input labelCode="property.location" path="billPayDto.location" isDisabled="true"></apptags:input>
								<%-- <apptags:select cssClass="chosen-select-no-results" labelCode="property.location" items="" path="" isMandatory="true" isLookUpItem="true" selectOptionLabelCode="select Location">
								</apptags:select> --%>		
							</div>
							
							<div class="form-group">					
								<apptags:input cssClass="form-control hasPincode" labelCode="property.pincode" path="billPayDto.pinCode"  isDisabled="true"></apptags:input>							
							</div>
					     </div> 
		            </div></div>
          
         

        	<div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"><a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#PayableAmountDetails">Receipt Amount Details</a></h4>
  				</div>
  				
  				<div id="PayableAmountDetails" class="panel-collapse collapse in">
         			<div class="panel-body">

									<c:if test="${command.billPayDto.billDisList  ne null && not empty command.billPayDto.billDisList}">
									 <div class="table-responsive">
							            <table class="table table-bordered table-condensed">
							            <thead>
							              <tr>
							                <th><spring:message code="propertyBill.TaxDescription"/></th>
							                <th><spring:message code="propertyBill.BalanceArrears"/></th>
							                <th><spring:message code="propertyBill.CurrentTax"/></th>
							                <th><spring:message code="propertyBill.TotalBalance"/></th>
							              </tr></thead>
							              <tbody>
						               <c:forEach items="${command.billPayDto.billDisList}" var="billDet" varStatus="detStatus">  
						          
						            <tr>
						              <td>${billDet.taxDesc}</td> 
						                <td>${billDet.arrearsTaxAmt}</td> 
						                <td>${billDet.currentYearTaxAmt}</td> 
						               <td>${billDet.totalTaxAmt}</td> 
							             </tr> 
							               </c:forEach> </tbody>
							            </table>
							          </div>
								
								</c:if> 
							        <div class="form-group padding-top-10" >
									<label class="col-sm-2 control-label"><spring:message code="propertyBill.TotalReceivable"/></label>
									<div class="col-sm-4">
									<div class="input-group">
							          <form:input path="billPayDto.totalPayableAmt" cssClass="form-control" id="totalPayable" readonly="true"/>
							          <label class="input-group-addon"><i class="fa fa-inr"></i></label>
							        </div>
									</div>
									<c:choose>
	              					<c:when test="${command.billPayDto.partialAdvancePayStatus eq 'PAI'}">	              					
									<label class="col-sm-2 control-label"><spring:message code="propertyBill.EnterReceiptAmount"/></label>
									<div class="col-sm-4">
 									<div class="input-group"> 
								         <form:input path="billPayDto.totalPaidAmt" cssClass="form-control mandColorClass" maxlength="10" id="payAmount" value="${command.billPayDto.totalPayableAmt}" readonly="true"/>
								         <label class="input-group-addon"><i class="fa fa-inr"></i></label>
								     </div> 
									</div>
									</c:when>
									<c:otherwise>
									<label class="col-sm-2 control-label"><spring:message code="propertyBill.EnterReceiptAmount"/></label>
									<div class="col-sm-4">
 									<div class="input-group"> 
								      	   <form:input path="billPayDto.totalPaidAmt" cssClass="form-control mandColorClass" maxlength="10" id="payAmount" />
								           <label class="input-group-addon"><i class="fa fa-inr"></i></label>
								     </div> 
									</div>
									</c:otherwise>
									</c:choose>
									
									</div>
		<c:if test="${command.billPayDto.billDisList eq null || empty command.billPayDto.billDisList}">
		 <div class="form-group" >
		<label class="col-sm-10 control-label text-red">Since No Dues are pending, Your Payment will be considered as Advance Payment</label>
		</div>
		</c:if> 
									
									
								<%-- <c:if test="${not empty command.message && command.message ne null }">
								 <div class="form-group" >
								<label class="col-sm-10 control-label text-red">${command.message}</label>
								</div>
								</c:if> --%> 
					</div>
				</div>
			</div>
		
			<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
		
         <div class="text-center padding-top-10">
          <button type="button" class="btn btn-success"  onclick="savePropertyFrom(this)"><spring:message code="propertyBill.Submit"/></button>
          <button type="button" class="btn btn-danger" onclick="searchBillPay(this)"><spring:message code="propertyBill.Back"/></button>
          <button type="button" class="btn btn-warning" onclick="resetButton(this)"><spring:message code="property.reset" text="Reset"/></button>
<%--           <button type="button" class="btn btn-danger" onclick="window.location.href='AdminHome.html'"><spring:message code="propertyBill.Back"/></button>
 --%>         </div>
<%--        </c:if> --%>
       </div>
    
      </form:form>
    </div>
  </div>
</div>