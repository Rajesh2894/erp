<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

 <script type="text/javascript">
	$(document).ready(function() {
	$('.fancybox').fancybox();
	});
	
	function printContent(el){
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	}

</script> 

 <div class="animated slideInDown"> 
      <!-- Your awesome content goes here -->
      
          
      <div class="widget" id="receipt">
     	 <div class="widget-content padding">
     
        <c:forEach items="${command.billMasPrint}" var="billMasMap" varStatus="statusPayment"> 
		<c:set var="billMas" value="${billMasMap.value}"></c:set>
		
        
          <div class="row">
            <div class="col-xs-3 margin-bottom-10"><img src="assets/img/bihar-logo.png"></div>
            <div class="col-xs-6 text-center">
              <h4 class="margin-bottom-0">${command.orgName}</h4>
              <p><spring:message code="water.bill"/></p>
               <p><strong><spring:message code="water.gst.no"/></strong> ${userSession.organisation.orgGstNo}</p>
            </div>
            <div class="col-xs-3 text-right margin-bottom-10"><img src="assets/img/bihar-logo.png"></div>
          </div>
          <div class="table-responsive">
            <table class="table table-bordered table-condensed">
  <tr>
    <th colspan="2"><spring:message code="water.nodues.connectionNo"/></th>
    <th colspan="3"><spring:message code="water.owner.details.oname"/></th>
    <th colspan="4"><spring:message code="water.BillInformation"/></th>
  </tr>
  <tr>
    <td colspan="2">${billMas.waterMas.csCcn}</td>
    <td colspan="3">${billMas.waterMas.csName}&nbsp;${billMas.waterMas.csMname}&nbsp;${billMas.waterMas.csLname}</td>
    <th colspan="2"><p class="text-right"><spring:message code="water.meterReadingViewDetails.BillNo."/></p></th>
    <th colspan="2"><p class="text-right"><spring:message code="water.waterBillGeneration.BillDate"/></p></th>
  </tr>
  <tr>
    <th colspan="2"><spring:message code="water.ConnectionSize"/></th>
     <th colspan="3"><spring:message code="water.meterBillPrint.OwnerAddress"/></th>
    <td colspan="2"><p class="text-right">${billMas.bmNo}</p></td>
    <td colspan="2"><p class="text-right">
     <fmt:formatDate pattern="dd/MM/yyyy" value="${billMas.bmBilldt}" var="billDate" />
     ${billDate}
    </p></td>
  </tr>
  <tr>
    <td colspan="2" rowspan="3">${billMas.conSize}</td>
    <td colspan="3" rowspan="3">${billMas.waterMas.csFlatno}&nbsp; ${billMas.waterMas.csBldplt} &nbsp;${billMas.waterMas.csAdd} &nbsp;${billMas.waterMas.csLanear} &nbsp;${billMas.waterMas.csRdcross}</td>
    <th colspan="2" rowspan="2"><spring:message code="water.meterBillPrint.MeterCompanyName"/></th>
    <th colspan="2"><spring:message code="water.bill.duration"/></th>
  </tr>
  <tr>
    <th><p class="text-right"><spring:message code="water.disconnect.from"/></p></th>
    <th><p class="text-right"><spring:message code="water.disconnect.to"/></p></th>
  </tr>
  <tr>
    <td colspan="2">${billMas.meterMas.mmMtrmake}</td>
    <td><p class="text-right"><fmt:formatDate pattern="dd/MM/yyyy" value="${billMas.bmFromdt}" var="fromDate" />
                ${fromDate}</p></td>
    <td><p class="text-right"><fmt:formatDate pattern="dd/MM/yyyy" value="${billMas.bmTodt}" var="toDate" />
                ${toDate}</p></td>
  </tr>
  <tr>
    <th colspan="2"><spring:message code="MeterReadingDTO.tariff1"/></th>
    <th colspan="3"><spring:message code="water.meterBillPrint.BillAddress"/></th>
    <th colspan="2"><p class="text-right"><spring:message code="water.meterBillPrint.PreviousReadingDate"/></p></th>
    <th colspan="2"><p class="text-right"><spring:message code="water.meterBillPrint.PreviousReading"/></p></th>
  </tr>
  <tr>
    <td colspan="2">${billMas.tarriffCategory}</td>
    <td colspan="3">${billMas.waterMas.csBadd}&nbsp; ${billMas.waterMas.csBbldplt} &nbsp;${billMas.waterMas.csBlanear} &nbsp;${billMas.waterMas.csBrdcross}</td>
    <td colspan="2"><p class="text-right"></p> </td>
    <td colspan="2"><p class="text-right">${billMas.meterRead.previousReading1}</p></td>
  </tr>
  <tr>
    <th width="103"><spring:message code="water.propertydetails.prtyNo"/></th>
    <th colspan="2"><spring:message code="water.meter.cutOffMeterNo"/></th>
    <th><spring:message code="water.met.status"/></th>
    <th><spring:message code="water.meterReading.GapCode"/></th>
    <th colspan="2"><p class="text-right"><spring:message code="water.CurrentReadingDate"/></p></th>
    <th colspan="2"><p class="text-right"><spring:message code="water.CurrentReading(Unit)"/></p></th>
  </tr>
  <tr>
    <td>${billMas.waterMas.propertyNo}</td>
    <td colspan="2">${billMas.meterMas.mmMtrno}</td>
    <td><!-- MOK -->${billMas.meterRead.meterStatusCode}</td>
    <td><!-- NOG -->${billMas.meterRead.meterGapCode}</td>
    <td colspan="2"><p class="text-right">
    <%-- <fmt:parseDate value=" ${billMas.meterRead.mrdMrdate}" pattern="yyyy-MM-dd HH:mm:ss" var="myDate"/> --%>
    <fmt:formatDate pattern="dd/MM/yyyy" value="${billMas.meterRead.mrdMrdate}" />
    </p></td>
    <td colspan="2"><p class="text-right">${billMas.meterRead.mrdMtrread}</p></td>
  </tr>
  <tr>
    <td colspan="5" rowspan="11" style="padding:0 !important;">
	<c:if test="${billMas.meterRead.fileDownLoadPath ne null && not empty billMas.meterRead.fileDownLoadPath}">
    <img width="460px" class="water-bill-img" src="${billMas.meterRead.fileDownLoadPath}">
    </c:if>
     <c:if test="${billMas.meterRead.fileDownLoadPath eq null || empty billMas.meterRead.fileDownLoadPath}">
     	<table class="table table-bordered table-condensed">
  <tr>
    <th colspan="6"><spring:message code="water.PreviousAverageConsumption"/></th>
  </tr>
 <tr>
   <th colspan="2"><spring:message code="water.meterBillPrint.Cycle"/></th>
  <th colspan="2"><spring:message code="water.meterBillPrint.NoOfDays"/></th>
   	<th colspan="2"><spring:message code="water.meterBillPrint.TotalConsumption"/></th>
   </tr>
   <c:if test="${billMas.meterRead.previousReading1 ne null}">
  <tr>
  
  <td colspan="2">${billMas.meterRead.previousCycle1}</td>
   <td colspan="2">${billMas.meterRead.previousDays1}</td>
    <td colspan="2">${billMas.meterRead.previousReading1}</td>
   
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading2 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle2}</td>
   <td colspan="2">${billMas.meterRead.previousDays2}</td>
  <td colspan="2">${billMas.meterRead.previousReading2}</td>
   
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading3 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle3}</td>
   <td colspan="2">${billMas.meterRead.previousDays3}</td>
     <td colspan="2">${billMas.meterRead.previousReading3}</td>
   
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading4 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle4}</td>
    <td colspan="2">${billMas.meterRead.previousDays4}</td>
   <td colspan="2">${billMas.meterRead.previousReading4}</td>
  
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading5 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle5}</td>
    <td colspan="2">${billMas.meterRead.previousDays5}</td>
    <td colspan="2">${billMas.meterRead.previousReading5}</td>
  
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading6 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle6}</td>
    <td colspan="2">${billMas.meterRead.previousDays6}</td>
    <td colspan="2">${billMas.meterRead.previousReading6}</td>
  
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading7 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle7}</td>
   <td colspan="2">${billMas.meterRead.previousDays7}</td>
    <td colspan="2">${billMas.meterRead.previousReading7}</td>
   
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading8 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle8}</td>
   <td colspan="2">${billMas.meterRead.previousDays8}</td>
    <td colspan="2">${billMas.meterRead.previousReading8}</td>
   
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading9 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle9}</td>
    <td colspan="2">${billMas.meterRead.previousDays9}</td>
  <td colspan="2">${billMas.meterRead.previousReading9}</td>
  
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading10 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle10}</td>
  <td colspan="2">${billMas.meterRead.previousDays10}</td>
    <td colspan="2">${billMas.meterRead.previousReading10}</td>
    
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading11 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle11}</td>
   <td colspan="2">${billMas.meterRead.previousDays11}</td>
   <td colspan="2">${billMas.meterRead.previousReading11}</td>
   
  </tr>
  </c:if>
</table>
     </c:if>
    </td>
    <th colspan="2"><p class="text-right"><spring:message code="water.nonMeterBillprint.TotalPeriodconsumptionUse"/></p></th>
    <th><p class="text-right"><spring:message code="water.nonMeterBillprint.UsageAdjustment"/></p></th>
    <th>
    <p class="text-right"><spring:message code="water.meterBillPrint.TotalConsumption"/></p></th>
  </tr>
  <tr>
    <td colspan="2"><p class="text-right">${billMas.meterRead.mrdMtrread-billMas.meterRead.previousReading1}</p></td>
    <td><p class="text-right"><!-- 0 --></p></td>
    <td><p class="text-right">${billMas.meterRead.csmp}</p></td>
  </tr>
  <tr>
    <th><spring:message code="water.tax.name"/></th>
    <th><p class="text-right"><spring:message code="water.nonMeterBillprint.ArrearsAmount"/></p></th>
    <th><p class="text-right"><spring:message code="water.nonMeterBillprint.TaxAmount"/></p></th>
    <th><p class="text-right"><spring:message code="water.nonMeterBillprint.Total"/></p></th>
  </tr>
  <c:forEach items="${billMas.tbWtBillDet}" var="billDet" varStatus="detStatus"> 
   <tr>
     <td>${billDet.taxDesc}</td>
     <td><p class="text-right">${billDet.bdPrvBalArramt}</p></td>
     <td><p class="text-right">${billDet.bdCurBalTaxamt}</p></td>
     <td><p class="text-right">${billDet.total}</p></td>
   </tr>
   </c:forEach>
    
  <tr>
    <th colspan="3"><p class="text-right"><spring:message code="water.nonMeterBillprint.TotalBillAmount"/></p></th>
    <td><p class="text-right">${billMas.grandTotal}</p></td>
  </tr>
  <tr>
    <th colspan="3"><p class="text-right"><spring:message code="water.waterBillGeneration.AdjustmentEntry"/></p></th>
    <td><p class="text-right">0.00</p></td>
  </tr>
  <tr>
    <th colspan="3"><p class="text-right"><spring:message code="water.waterBillGeneration.AdjustedAmount"/></p></th>
    <td><p class="text-right">${billMas.excessAmount}</p></td>
  </tr>
  <tr>
    <th colspan="3"><p class="text-right"><spring:message code="water.waterBillGeneration.BalanceExcessAmount"/></p></th>
    <td><p class="text-right">${billMas.balanceExcessAmount}</p></td>
  </tr>
  <tr>
    <th colspan="3"><p class="text-right"><spring:message code="water.waterBillGeneration.RebateAmount"/></p></th>
    <td><p class="text-right">${billMas.rebateAmount}</p></td>
  </tr>
  <tr>
    <th colspan="3"><p class="text-right"><spring:message code="water.MeterBillprint.TotalPayableAmount"/></p></th>
    <td><p class="text-right">${billMas.bmTotalOutstanding}</p></td>
  </tr>
  </table>
  
  <c:if test="${billMas.meterRead.fileDownLoadPath ne null && not empty billMas.meterRead.fileDownLoadPath}">
  <table class="table table-bordered table-condensed" style="float:left; width:50%;">
  <tr>
    <th colspan="6"><spring:message code="water.PreviousAverageConsumption"/></th>
  </tr>
  <tr>
   <th colspan="2"><spring:message code="water.meterBillPrint.Cycle"/></th>
  <th colspan="2"><spring:message code="water.meterBillPrint.NoOfDays"/></th>
   	<th colspan="2"><spring:message code="water.meterBillPrint.TotalConsumption"/></th>
   </tr>
   <c:if test="${billMas.meterRead.previousReading1 ne null}">
  <tr>
  
  <td colspan="2">${billMas.meterRead.previousCycle1}</td>
   <td colspan="2">${billMas.meterRead.previousDays1}</td>
    <td colspan="2">${billMas.meterRead.previousReading1}</td>
   
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading2 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle2}</td>
   <td colspan="2">${billMas.meterRead.previousDays2}</td>
  <td colspan="2">${billMas.meterRead.previousReading2}</td>
   
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading3 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle3}</td>
   <td colspan="2">${billMas.meterRead.previousDays3}</td>
     <td colspan="2">${billMas.meterRead.previousReading3}</td>
   
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading4 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle4}</td>
    <td colspan="2">${billMas.meterRead.previousDays4}</td>
   <td colspan="2">${billMas.meterRead.previousReading4}</td>
  
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading5 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle5}</td>
    <td colspan="2">${billMas.meterRead.previousDays5}</td>
    <td colspan="2">${billMas.meterRead.previousReading5}</td>
  
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading6 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle6}</td>
    <td colspan="2">${billMas.meterRead.previousDays6}</td>
    <td colspan="2">${billMas.meterRead.previousReading6}</td>
  
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading7 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle7}</td>
   <td colspan="2">${billMas.meterRead.previousDays7}</td>
    <td colspan="2">${billMas.meterRead.previousReading7}</td>
   
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading8 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle8}</td>
   <td colspan="2">${billMas.meterRead.previousDays8}</td>
    <td colspan="2">${billMas.meterRead.previousReading8}</td>
   
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading9 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle9}</td>
    <td colspan="2">${billMas.meterRead.previousDays9}</td>
  <td colspan="2">${billMas.meterRead.previousReading9}</td>
  
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading10 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle10}</td>
  <td colspan="2">${billMas.meterRead.previousDays10}</td>
    <td colspan="2">${billMas.meterRead.previousReading10}</td>
    
  </tr>
  </c:if>
  <c:if test="${billMas.meterRead.previousReading11 ne null}">
  <tr>
  <td colspan="2">${billMas.meterRead.previousCycle11}</td>
   <td colspan="2">${billMas.meterRead.previousDays11}</td>
   <td colspan="2">${billMas.meterRead.previousReading11}</td>
   
  </tr>
  </c:if>
</table>
</c:if>
  
<table class="table table-bordered table-condensed" style="float:left; width:50%;">
  <tr>
    <th width="358"><spring:message code="water.meterBillPrint.BillDurationInDays"/></th>
    <td><p class="text-right">${billMas.meterRead.ndays}</p></td>
  </tr>
  <tr>
    <th><spring:message code="water.meterBillPrint.DailyAverageConsumption"/></th>
    <td><p class="text-right">
     <fmt:formatNumber type="number" 
            maxFractionDigits="2" value="${billMas.meterRead.csmp/billMas.meterRead.ndays}" />
    </p></td>
  </tr>
  <tr>
    <th><spring:message code="water.meterBillPrint.TotalWaterConsumptionDemand"/></th>
    <td><p class="text-right">${billMas.bmTotalOutstanding}</p></td>
  </tr>
  <tr>
    <th><spring:message code="water.meterBillPrint.BillPayableDate"/></th>
    <td><fmt:formatDate pattern="dd/MM/yyyy" value="${billMas.bmDuedate}" var="dueDate" />
    <p class="text-right">${dueDate}</p></td>
  </tr>
  <tr>
</table>
<p class="text-center margin-top-30">${billMas.bmRemarks}</p>
<table class="table table-bordered table-condensed">
  <tr>
    <th>Rupees(In Words): ${billMas.amountInwords}</th>
  </tr>
</table>
</div>
         
          
          <div class="row margin-top-10">
          	<div class="col-xs-6">
                 <%-- <div>GST NO: ${userSession.organisation.orgGstNo}</div> --%>
            </div>
            <div class="col-xs-3 col-xs-push-3 text-center">
            <p><spring:message code="water.meter.bill.auth.sign"/></p>
            </div>
          </div>
        
        
         <div class="pagebreak"></div>
        </c:forEach>
         </div>
      </div>
      </div>
      
       <div class="text-center margin-top-20">
            <button onclick="printContent('receipt')" class="btn btn-primary hidden-print"><i class="fa fa-print"></i> <spring:message code="water.btn.print"/></button>
       <button type="button" class="btn btn-danger hidden-print" onclick="window.location.href='WaterBillPrinting.html'"><spring:message code="water.btn.back"/></button>
      </div>
          