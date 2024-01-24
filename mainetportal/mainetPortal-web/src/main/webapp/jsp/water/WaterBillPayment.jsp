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
 <script>
 function serachWaterBillPaymentData(obj)
 {
 	var formName	=	findClosestElementId(obj,'form');
 	
 	var theForm	=	'#'+formName;
 	
 	var url		=	'WaterBillPayment.html?serachWaterBillPayment';
 		
 	$(theForm).attr('action',url);
 	
 	$(theForm).submit();
 }

 function resetbillPayment(element){
		$("#WaterBillPaymentId").submit();
 }
 
 function saveData(element)
 {
var loginUser=$("#empLoginName").val();

	 
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y') {
	 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'WaterBillPayment.html?redirectToPay', 'saveform');
	}
	else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N')
	{
		/* if(loginUser===getLocalMessage("citizen.noUser.loginName")){
			getCitizenLoginForm("N");
		}else{ */
			return saveOrUpdateForm(element,"Bill Payment done successfully!", 'WaterBillPayment.html?PrintReport', 'saveform');
		//}
	}
	else
	{
	 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'WaterBillPayment.html', 'saveform');
	}
 }

 
 jQuery('.maxLength30').keyup(function () { 
	 $(this).attr('maxlength','60');
  
});
 

 
 </script>
<!-- <ol class="breadcrumb">
  <li><a href="CitizenHome.html"><i class="fa fa-home"></i></a></li>
  <li>Services</li>
  <li>Bill Payment</li>
</ol> -->
<apptags:breadcrumb></apptags:breadcrumb>
<iframe id="txtArea1" style="display:none"></iframe>
<div class="content"> 
  <div class="widget">
    <div class="widget-header">
      <h2><spring:message code="water.BillPayment" text="Bill Payment"/></h2>
      <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
    </div>
    <div class="widget-content padding">
      <form:form action="WaterBillPayment.html" method="post" class="form-horizontal" name="WaterBillPayment" id="WaterBillPaymentId">
       		<jsp:include page="/jsp/tiles/validationerror.jsp" />
       		<form:hidden path="" id="empLoginName" value="${command.userSession.employee.emploginname}"/>
       	<div class="form-group" >
		<label class="col-sm-2 control-label"><spring:message code="water.nodues.connectionNo"/></label>
		<div class="col-sm-4">
		<form:input path="ccnNumber" cssClass="form-control"  id="connum" />
		</div>
		<div class="col-sm-6">
          <button type="submit" class="btn btn-info" onclick="return serachWaterBillPaymentData(this);"><i class="fa fa-search"></i> <spring:message code="water.nodues.search"/></button>
          <button type="button" class="btn btn-warning" onclick="resetbillPayment(this)"><spring:message code="water.btn.reset"/></button>
        </div>
		</div>
		
		
		 
       <div class="panel-group accordion-toggle" id="accordion_single_collapse"> 
       <c:if test="${(command.applicationNo ne null) or (not empty command.message && command.message ne null)}">
       
       <form:hidden path="" value="${command.message}" id="advancePay"/>
       
       
        <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"><a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#ConnectionDetails"><spring:message code="water.connectiondetails" text="Connection Details"/></a></h4>
  				</div>
  				
  				<div id="#ConnectionDetails" class="panel-collapse collapse in">
         <div class="panel-body">
       
        <div class="form-group">
					<label class="col-sm-2 control-label" for="CsName">
						<spring:message code="water.consumerName" text="Consumner Name"/>
					</label>
					<div class="col-sm-4">
					<c:if test="${ not empty command.applicantDetailDto.applicantMiddleName}">  
						<form:input type="text" class="form-control"
							path="" value="${command.applicantDetailDto.applicantFirstName} ${command.applicantDetailDto.applicantMiddleName} ${command.applicantDetailDto.applicantLastName}" readonly="true"></form:input>
							</c:if>
							<c:if test="${empty command.applicantDetailDto.applicantMiddleName}">
						<form:input type="text" class="form-control"
							path="" value="${command.applicantDetailDto.applicantFirstName} ${command.applicantDetailDto.applicantLastName}" readonly="true"></form:input>
							</c:if>
					</div>
					<label class="col-sm-2 control-label ">
						<spring:message code="water.areaName" />
					</label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control"
							path="applicantDetailDto.areaName" id="billingAreaName" readonly="true"></form:input>
					</div>
					
					<%-- <label class="col-sm-2 control-label"> <spring:message
							code="app.no" text="Application No."/>
					</label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control"
							path="applicationNo" readonly="true" ></form:input>
					</div> --%>
				</div>
       
       
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label" for="billingFlatNo">
						<spring:message code="water.flatOrbuildingNo" />
					</label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control"
							path="applicantDetailDto.flatBuildingNo" id="billingFlatNo" readonly="true"></form:input>
					</div>
					<label class="col-sm-2 control-label"> <spring:message
							code="water.buildingName" />
					</label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control"
							path="applicantDetailDto.buildingName" id="billingBuildingName" readonly="true"></form:input>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"> <spring:message
							code="water.roadName" />
					</label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control"
							path="applicantDetailDto.roadName" id="billingRoadName" readonly="true"></form:input>
					</div>
					
					
					
					
				</div> --%>
       </div>
       </div>
       </div>
       
        <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"><a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#PayableAmountDetails"><spring:message code="water.PayableAmountDetails" text="Payable Amount Details"/></a></h4>
  				</div>
  				 <div id="PayableAmountDetails" class="panel-collapse  collapse in">
         <div class="panel-body">
		
		<c:if test="${command.taxes ne null && not empty command.taxes}">
		<div class="table-responsive">
            <table class="table table-bordered table-condensed">
            
              <tr>
               <th><spring:message code="water.WaterBillPayment.TaxDescription"/></th>
                <th><spring:message code="water.WaterBillPayment.BalanceArrears"/></th>
                <th><spring:message code="water.WaterBillPayment.CurrentTax"/></th>
                <th><p class="text-right"><spring:message code="water.WaterBillPayment.CurrentBalanceTax"/></p></th>
                <th><p class="text-right"><spring:message code="water.WaterBillPayment.TotalBalance"/></p></th>
              </tr>
               <c:forEach items="${command.taxes}" var="billDet" varStatus="detStatus"> 
              <tr>
                <td>${billDet.taxdescription}</td>
                <td><p class="text-right">${billDet.arrearTaxAmount}</p></td>
                <td><p class="text-right">${billDet.taxAmount}</p></td>
                 <td><p class="text-right">${billDet.balabceTaxAmount}</p></td>
                <td><p class="text-right">${billDet.total}</p></td>
              </tr>
               </c:forEach>
              <tr>
                <th colspan="3"><p class="text-right"><spring:message code="water.WaterBillPayment.AdjustmentEntry"/></p></th>
                <td colspan="2"><p class="text-right">0.00</p></td>
              </tr>
              <tr>
                <th colspan="3"><p class="text-right"><spring:message code="water.WaterBillPayment.AdjustedAmount"/></p></th>
                <td colspan="2"><p class="text-right">${command.excessAmount}</p></td>
              </tr>
              <tr>
                <th colspan="3"><p class="text-right"><spring:message code="water.WaterBillPayment.BalanceExcessAmount"/></p></th>
                <td colspan="2"><p class="text-right">${command.balanceExcessAmount}</p></td>
              </tr>
              <tr>
                <th colspan="3"><p class="text-right"><spring:message code="water.WaterBillPayment.RebateAmount"/></p></th>
                <td colspan="2"><p class="text-right">${command.rebateAmount}</p></td>
              </tr>
             
            </table>
          </div>
		</c:if>
		
		
        <div class="form-group padding-top-10" >
		<label class="col-sm-2 control-label"><spring:message code="water.WaterBillPayment.TotalBillAmountPayable"/></label>
		<div class="col-sm-4">
		<div class="input-group">
          <form:input path="billMas.bmTotalOutstanding" cssClass="form-control" id="totalPayable" readonly="true"/>
          <label class="input-group-addon"><i class="fa fa-inr"></i></label>
        </div>
		</div>
		
		<label class="col-sm-2 control-label required-control"><spring:message code="water.billPayment.amount"/></label>
		<div class="col-sm-4">
			<div class="input-group">
	           <form:input path="payAmount" cssClass="form-control mandColorClass hasNumber" maxlength="10" id="payAmount" />
	           <label class="input-group-addon"><i class="fa fa-inr"></i></label>
	         </div>
		</div>
		
		</div>
			<c:if test="${not empty command.message && command.message ne null }">
		 <div class="form-group" >
		<label class="col-sm-10 control-label text-red">${command.message}</label>
		</div>
		</c:if> 
		</div>
		</div>
		</div>
		
		<div class="panel panel-default">
			<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
		</div>
				
         <div class="text-center padding-top-10">
          <button type="button" class="btn btn-success"  onclick="saveData(this)"><spring:message code="water.btn.submit"/></button>
          <button type="button" class="btn btn-danger" onclick="window.location.href='CitizenHome.html'"><spring:message code="water.btn.Back"/></button>
        </div>
        </c:if>
        </div>
      </form:form>
    </div>
  </div>
</div>
