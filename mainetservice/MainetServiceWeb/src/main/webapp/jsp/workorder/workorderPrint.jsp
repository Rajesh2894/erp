<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
  <script src="js/siteinspection/siteinspection.js"></script> 
 <script type="text/javascript"> 
 HTMLElementObject.contentEditable=false
</script> 

<!--  <ol class="breadcrumb">
      <li><a href="../index.html"><i class="fa fa-home"></i></a></li>
      <li>Work Order Printing</li>
    </ol> -->
    <!-- ============================================================== --> 
    <!-- Start Content here --> 
    <!-- ============================================================== -->
    <div class="animated slideInDown"> 
      <!-- Start info box -->
      <div class="widget" id="letter">
        <div class="widget-content padding">
          <div class="form-group clearfix">
            <div class="col-xs-3 text-left"><img src="assets/img/logo.png"></div>
            <div class="col-xs-6 text-center margin-top-10">
             <h4 class="margin-bottom-0"> ${userSession.ULBName.lookUpDesc}</h4>
            </div>
            <div class="col-xs-3 text-right"><img src="assets/img/logo.png"></div>
          </div>
          <div class="form-group clearfix margin-top-15">
            <div class="col-xs-6 text-left"><p> No. - ${WorkOrderNumber} </p>
            <input type ="hidden" value ="${WorkOrderNumber}" id="WorkOrderNumber"/>  </div>
            <div class="col-xs-6 text-right"> <p>Date - ${NewDate} </p></div>
          </div>
          <div class="form-group clearfix margin-top-20">
            <div class="col-xs-1">From</div>
            <div class="col-xs-11">
              <p>Municipal Engineer</p>
              <p> ${userSession.ULBName.lookUpDesc}</p>
            </div>
          </div>
          <div class="form-group clearfix margin-top-20">
            <div class="col-xs-1">To</div>
            <div class="col-xs-11">
              <p>${ApplicantFullName}</p>
             </div> 
           </div>  
           <div class="form-group clearfix margin-top-20">
            <div class="col-xs-1"></div>
            <div class="col-xs-11"> 
              <p> Address <i class="fa fa-map-marker fa-1x red" aria-hidden="true"></i>: ${applicantAddress}</p>
              <p class="margin-top-15">Subject: ${ServiceName}</p>
              <p class="margin-top-15">Reference: Your Application for New Water Connection Application number: ${ApplicationID}  and Application Date: ${ApplicationDate} is approved and your connection no.is ${ConnectionNo} </p>
            </div>
          </div>
          <div class="form-group clearfix">
            <p class="margin-bottom-20">Sir/Madam,<br><br>
              Your application dated ${ApplicationDate}  has been proccessed and found in order. You are requested to lay your pipe line upto Municipal Corporation Branch Line and submit the completion report. An amount of Rs. ${chargestotal} is received at ULB.</p>
           <div class="form-group clearfix">
          <table class="table table-message">
          <table class="table table-border">
              <tr>
                <th class="text-left-imp">Service</th>
                <th class="text-left-imp">Rupees</th>
              </tr>
             	    
            	<c:forEach items="${Chargedescruption}" var="singleDoc" varStatus="count">
            	<tr>
            	<td><strong> ${singleDoc.key}</strong></td>
            	<td> <strong>${singleDoc.value}</strong></td>
            	</tr>
            	</c:forEach>
            	<tr>
                <th><p class="text-right">Total</p></th>
                <th><p class="text-left">${chargestotal}</p></th>
              </tr>
           
           </table>
		</div>
		
			<%-- <p class="margin-bottom-10 margin-top-10"><spring:message code="trutirejection.rejctionremark"/></p> --%>
			<p class="padding-vertical-15"><strong>Terms &amp; Conditions </strong></p>
     		<table class="table">
             	    
            	<c:forEach items="${TermsConditon}" var="singleDoc" varStatus="count">
            	<tr>
            	<td width="50">${count.index+1}</td>
            	<td>${singleDoc.artRemarks}</td>
            	</tr>
            	</c:forEach>
           
           </table>
    
        <div class="form-group clearfix padding-vertical-15">
            
           <c:if test="${PrintReprintFlg == 'Y'}"> 
           <div class="col-xs-4">
              <p class="padding-top-30">Copy To: Plumber Name</p>
              <div class="barcode padding-top-10">101.1234567896587</div>
            </div>
            <div class="col-xs-4 text-center" id="Duplicat">
            <i class="fa fa-files-o fa-2x red" aria-hidden="true"></i>
           <strong>Duplicate  Copy </strong>
            </div>
            
            </c:if>
            
            <c:if test="${PrintReprintFlg == null}"> 
            <div class="col-xs-8">
              <p class="padding-top-30">Copy To: Plumber Name</p>
              <div class="barcode padding-top-10">101.1234567896587</div>
            </div>
            </c:if>
            <div class="col-xs-4 text-center">
              <p>By Order,</p>
              <p class="margin-top-15">Municipal Engineer</p>
              <p>Muzaffarpur Municipal Corporation</p>
            </div>
          </div>
          </div>
         
          
        
          <div class="text-center clearfix margin-top-10" id ="hideprintbuttton">
            <c:if test="${PrintReprintFlg == null}">  
             <button onclick ="printContent2('letter')" class="btn btn-primary hidden-print printClass"><i class="icon-print-2"></i> <spring:message code="siteinspection.print"/></button>
           </c:if>
           
            <c:if test="${PrintReprintFlg == 'Y'}">
             <button onclick ="printContent2('letter')" class="btn btn-primary hidden-print printClass"><i class="icon-print-2"></i> Reprint</button>
            </c:if>  
           <!--   <input type="button" class="btn btn-default" value="Back" onclick="window.location.href='WorkOrder.html'"> -->
            <!--  <input type="button"
						onclick="window.location.href='WorkOrder.html'"
						class="btn btn-default hidden-print" value="Back"> -->
             <input type="button" onclick="window.location.href='WorkOrder.html'" class="btn btn-danger hidden-print" value="Back">
          </div>
          
        
        </div>
      </div>
     

    </div>