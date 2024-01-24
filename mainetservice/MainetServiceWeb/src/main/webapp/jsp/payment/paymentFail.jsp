<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.1.135/jspdf.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.js"></script>

 
 <script type='text/javascript'>
function callPdf(){

	  alert("Please close the current Browser Window");
 	/* html2canvas(document.body,{
 		
 		onrendered : function(canvas){
 			 var imgData = canvas.toDataURL( 'image/png');              
              var doc = new jsPDF('p', 'mm', "a4");
              doc.addImage(imgData, 'PNG',10, 10);  
              var date = new Date(); 
              var fileName =date+'reciept.pdf';         	
              doc.save(fileName);
              alert("Please close the current Browser Window");
 		}		
 	}); */
 	
 }

 </script>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<style type="text/css">


#middle_right h1 {
	width: 100%;
 	color:#274472;
	text-shadow:1px 1px 0px rgba(39, 68, 114, 0.1);
	font-weight:600;
	padding: 5px 0 5px;
	clear: both;
	font-size:15px;
	text-indent:10px;
}

 #content {
	width: 95.5%;
	float: left;
	background:rgba(255, 255, 255, 1);
	box-shadow:0 0 0 1px rgba(51, 51, 51, 0.2) inset;
	border-radius:4px;
	padding: 2%;
}

table.gridtable {
	width: 100%;
	font-size: 11px;
	color: #333333;
	border-collapse: collapse;
	overflow-x: auto;
	text-align: center;
}
table.gridtable th {
	padding: 8px;
	background-color: rgba(81, 81, 81, 0.1);
	border: 1px solid rgba(81, 81, 81, 0.4);
}
table.gridtable th.bnd {
	padding: 4px!important;
	background-color: rgba(81, 81, 81, 0.1);
	border: 1px solid rgba(81, 81, 81, 0.4);
}
table.gridtable td {
	font-size: 11px;
	line-height: 20px;
	padding: 8px;
	background-color: rgba(255, 255, 255, 0.5);
	border: 1px solid rgba(81, 81, 81, 0.4);
	text-align: left;
	vertical-align: top;
}
table.gridtable td.bnd{
	font-size: 11px;
	line-height: 17px;
	padding: 4px !important;
	background-color: rgba(255, 255, 255, 0.5);
	border: 1px solid rgba(81, 81, 81, 0.4);
	text-align: left;
	vertical-align: top;
}

table.gridtable tr:nth-child(2n) {
background: #f1f1f1;
}
table.gridtable td a {
	word-break:break-all;
}

 


@media print
{
	.logo > ul > li:first-child{ margin-left:0px; margin-top:10px;}
	.logo > ul > li:nth-child(2){font-size:20px;}
	#heading_bredcrum{display:none;}	
	.wrapper {margin-top:-20px;}
	.logo > ul > li:last-child{margin-right:0px; margin-top:10px;}
	#left_menu, #cssmenu, #topper, #footer2 {display:none;}
	#main_wrapper{border:none; box-shadow:none;}
	#middle_right{ width:100%;}
	#middle_right h1{display:none;}
	#content	{width:70%;}
	#main_logout_wrapper {display:none;}
	#footer {display:none;}
	.top_hed{display:none;}
	#bs-navbar-collapse-1{display:none;}
	.welcome{display:none;}
	.navbar-default{display:none;}
    #right_logo{position: fixed; left: 700px; }      
     #main_wrapper{margin-left: 100px}  
     #printButn{display:none;}    
     .colors-switcher{display:none;}    
     .left-bar{display:none;}    
     .right-bar{display:none;}
     .backToTop{display:none; !important}
     #toggle{display:none;}
}
</style>	  	
<body id ="target">
<div id="middle_right">	
<div class="container">
<h1><spring:message code="eip.stp.header" text="Transaction Detaifls"/><span>${command.status}</span></h1>

<div class="clearfix" id="content" align="center">
			<div class="form-div" align="center">
			
			<c:if test="${not empty command.labelName}">
					<div align="center">
						<div class="form-elements">
						<c:choose>
							<c:when test="${not empty command.applicationId}">
								<span class="green fa-lg">
										 ${command.labelName} :
						    			<font color="green">${command.applicationId}</font>
						    	</span>
						    	
							</c:when>
							<c:otherwise>
								<span class="green fa-lg">
									 ${command.labelName} :
						    		<font>${command.applicationId}</font>
					    		</span>					    							    		
							</c:otherwise>
						</c:choose>
						</div>						
				    </div>
			   </c:if>
				<div class="form-elements padding_10">
    
    			<!-- Defect# 126498 Failure and cancel payment receipt should be generated. -->
               <c:if
				test="${ 'failure' eq command.status}">
					<p class="blue"><spring:message code="eip.canceltopay.cancelmsg" text=""/></p>
               
				<div id="toPrint" class="form-elements">
					<table class="gridtable">
					
					<tr>
						<th colspan="2"><h4><spring:message code="eip.ftp.tranDetails" text="Transaction Details"/></h4></th>
					</tr>
							<tr>
								<th><label for=""><spring:message code="eip.stp.serviceInfo" text="Service Information"/></label></th>
								<td><span>${command.productinfo}</span></td>
							</tr>
							<tr>
								<th><label for=""><spring:message code="eip.payment.payeeName" text="Payee Name"/></label></th>
								<td><span>${command.firstName}</span></td>
							</tr>
							
							<tr>
								<th><label for=""><spring:message code="eip.stp.phoneNo" text="Contact No"/></label></th>
								<td><span>${command.mobileNo}</span></td>
							</tr>
							<tr>
								<th><label for=""><spring:message code="eip.payment.email" text="Email Id"/></label></th>
								<td><span>${command.email}</span></td>
							</tr>
							<tr>
								<th><label for=""><spring:message code="eip.stp.paymentAmount" text="Payment Amount(Rs)"/></label></th>
								<!--D#128914 Property Bill payment  If amount is large then in payment receipt it is showing in form of 5.0E7.-->
								<fmt:formatNumber type="number" value="${command.amount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amount"/>			 					 
								 <td><span>${amount}</span></td>
								
							</tr>
							<tr>
								<th><label for=""><spring:message code="eip.stp.orderNo" text="Order No."/></label></th>
								<td><span>${command.trackId}</span></td>
							</tr>
							
							<tr>
								<th><label for=""><spring:message code="eip.stp.tranRefNo" text="Transaction Reference No."/></label></th>
								<td><span>${command.transactionId}</span></td>
							</tr>
							<tr>			               
			               <th><label for=""><spring:message code="eip.stp.tranDateTime" text="Transaction Date Time"/></label></th>                              
							<td><span>${command.paymentDateTime}</span></td>
							</tr>
							<tr>
								<th><label for=""><spring:message code="eip.stp.tranStatus" text="Transaction Status"/></label></th>
								<td><span>${command.status}</span></td>
							</tr>
						</table>
				</div>
				</c:if>	
				<c:if test="${ 'fail' eq command.status}">			
				<div id="toPrint" class="form-elements">
					<table class="gridtable">
					
					<tr>
						<th colspan="2"><h4>${command.errorMsg}</h4></th>
					</tr>
				</table></div>
				</c:if>
				
				</div>
				
				<div class="btn_fld clear margin_top_10" id="printButn">
<%-- 				   <a  href="javascript:callPdf();"  class="css_btn"><i class="fa fa-print fa-fw"></i><spring:message code="rti.RePrint.save" text="Close Receipt"/></a>												
 --%>	        				 <a href="/mobile/close"><spring:message code="rti.RePrint.save" text="Close Receipt"/></a>	
  	
 </div> 
	       		</div>
		</div>
		</div>
		</div>
		
		</body>		