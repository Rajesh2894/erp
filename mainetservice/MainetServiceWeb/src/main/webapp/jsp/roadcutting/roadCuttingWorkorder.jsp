<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
  <script type="text/javascript" src="js/roadcutting/workorderPrintGrid.js">
 <script type="text/javascript"> 
 HTMLElementObject.contentEditable=false
</script> 
<style>
@media print {
 .widget-content,.form-group, .table, #letter{background:#fff !important;}
}
</style>
    <!-- ============================================================== --> 
    <!-- Start Content here --> 
    <!-- ============================================================== -->
    <div class="animated slideInDown"> 
      <!-- Start info box -->
      <div class="widget" id="letter">
        <div class="widget-content padding">
          <div class="form-group clearfix">
            <div class="col-xs-3 col-sm-3 text-left"><img src="${logo}" width="80px" height="80px"></div>
            <div class="col-xs-6 text-center margin-top-10">
             <h1 class="margin-bottom-0"> ${userSession.ULBName.lookUpDesc}</h1>
            </div>
            <!-- <div class="col-xs-3 col-sm-3 text-right"><img src="assets/img/nrda-logo.png" width="80px" height="80px"></div> -->
          </div>
          <div class="form-group clearfix margin-top-15">
            <div class="col-xs-6 text-left"><p> Work Order No. - ${WorkOrderNumber} </p>
            <p>Date - ${NewDate} </p>
            <input type ="hidden" value ="${WorkOrderNumber}" id="WorkOrderNumber"/>  </div>
           <%--  <div class="col-xs-6 text-left"> <p>Date - ${NewDate} </p></div> --%>
          </div>
          <div class="form-group clearfix margin-top-20 text-left">
            <div class="col-xs-11"><strong>From,</strong></div>
            <div class="col-xs-11">
              <p>Chief Engineer</p>
              <p> ${userSession.ULBName.lookUpDesc}</p>
            </div>
          </div>
          <div class="form-group clearfix margin-top-20">
            <div class="col-xs-11"><strong>To,</strong></div>
            <div class="col-xs-11">
             <%--  <p>${ApplicantFullName}</p> --%>
              <p>${data.personName2}</p>
              <p>${data.companyName2}</p>
              <p>${data.companyAddress2}</p>
             </div> 
           </div>  
           <div class="form-group clearfix margin-top-20">
            <div class="col-xs-11"></div>
            <div class="col-xs-11"> 
              <%-- <p> Address <i class="fa fa-map-marker fa-1x red" aria-hidden="true"></i>: ${applicantAddress}</p> --%>
              
              <p class="margin-top-11"><strong>Subject:</strong> ${ServiceName}</p>
              <p class="margin-top-11"><strong>Reference:</strong> </p>
              <p class="margin-top-11"> 1. Your  Application no. ${ApplicationID}   Date ${ApplicationDate} .</p>
              <p class="margin-top-11"> 2. Letter of Intent no. ${loiNo}</p>
              <p class="margin-top-11"> 3. Charges of reinstatement amount Rs.  ${chargestotal}  Receipt No.  ${receiptNo} Date  ${receiptDate} .</p>
            </div>
          </div>
          <div class="form-group clearfix margin-top-20">
          	<div class="col-xs-11"></div>
           <div class="col-xs-11">
           	<p class="margin-top-11"><strong>Sir/Madam,</strong></p>
			<p class="margin-top-11">With reference to your request, excavation activity for the following trench is permitted as below:</p>
			</div>
 		</div>
 		
<div class="form-group clearfix margin-top-20">
<div class="col-sm-12 col-xs-12">
							<p class="margin-top-10 margin-bottom-10"><strong>Road Details</strong></p>
									<div class="table-responsive">
										<table class="table text-left  table-bordered" id="roadRoute">
											<tbody>
												<tr>
													<th><spring:message code="RoadCuttingDto.typeOfTechnology" /></th>
													<th><spring:message code="RoadCuttingDto.roadRouteDesc" /></th>
													<th><spring:message	code="RoadCuttingDto.roadEnd" /></th>
													<th><spring:message code="RoadCuttingDto.roadType" /></th>
													<th><spring:message code="RoadCuttingDto.numbers" /></th>
													<th><spring:message code="RoadCuttingDto.length" /></th>
													<th><spring:message code="RoadCuttingDto.breadth" /></th>
													<th><spring:message code="RoadCuttingDto.height" /></th>
													<th><spring:message code="RoadCuttingDto.diameter" /></th>
													<th><spring:message code="RoadCuttingDto.quantity" /></th>
												</tr>
												<c:forEach varStatus="roadstatus" items="${data.roadList}" var="roadCutting">
													<tr class="appendableClass">
														<td><spring:eval expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getCPDDescription(roadCutting.typeOfTechnology,'')" /></td>
														<td>${roadCutting.roadRouteDesc}</td>
														<td>${roadCutting.rcdEndpoint}</td>
														<td><spring:eval expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getCPDDescription(roadCutting.roadType,'')" /></td>
														<td>${roadCutting.numbers}</td>
														<td>${roadCutting.length}</td>
														<td>${roadCutting.breadth}</td>
														<td>${roadCutting.height}</td>
														<td>${roadCutting.diameter}</td>
														<td>${roadCutting.quantity}</td>

													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>

           	<p class="margin-top-15"><strong>Name of Site Engineer : </strong>${assignEng} </p>
           	
			<p class="margin-top-15"><strong>Subject to the following conditions </strong></p>
			
     		<table class="table text-left table-striped table-bordered" id="conditions">
             	    
            	<c:forEach items="${TermsConditon}" var="singleDoc" varStatus="count">
            	<tr>
            	<td width="50">${count.index+1}</td>
            	<td>${singleDoc.artRemarks}</td>
            	</tr>
            	</c:forEach>
           
           </table>
           
   		 </div>
   		 </div>
        <div class="form-group clearfix padding-top-15">
            
           
           <div class="col-xs-4 text-left">
              <p><strong>By Order</strong></p>
              <p><strong>Chief Engineer</strong></p>
              <p> ${userSession.ULBName.lookUpDesc}</p>
            </div>
            <c:if test="${PrintReprintFlg == 'Y'}"> 
            	<div class="col-xs-4 text-center" id="Duplicat">
            	<i class="fa fa-files-o fa-2x red" aria-hidden="true"></i>
           		<strong>Duplicate  Copy </strong>
            </div>
            
            </c:if>
          </div>
         
         
          
        
          <div class="text-center clearfix margin-top-10" >
            <c:if test="${PrintReprintFlg == null}">  
             <button onclick ="printContent2('letter')" class="btn btn-primary hidden-print printClass hideprintbuttton"><i class="icon-print-2"></i> <spring:message code="siteinspection.print"/></button>
             
           </c:if>
           
            <c:if test="${PrintReprintFlg == 'Y'}">
             <button onclick ="printContent2('letter')" class="btn btn-primary hidden-print printClass hideprintbuttton"><i class="icon-print-2"></i> Reprint</button>
            </c:if>  
             <input type="button" onclick="window.location.href='RoadCuttingWorkOrderPrinting.html'" class="btn btn-danger hidden-print" value="Back"/>
          </div>
          
        
        </div>
      </div>
     

    </div>