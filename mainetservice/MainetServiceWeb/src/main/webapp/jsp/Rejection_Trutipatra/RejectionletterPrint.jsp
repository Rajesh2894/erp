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
<script src="js/cfc/scrutiny.js"></script>

<script>

function printdiv(printpage)
{
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr+newstr+footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}

</script>


<style>
.cert-outer-div {
	border: 3px solid #000000;
}

.border-bottom-black { 
     border-bottom: 3px solid #000000;
     margin:-1px;
}
.overflow-hidden {
	overflow: hidden;
}
.pgmargin {
	margin : 2rem;
}
.fontsz{
	font-size: 16px !important;
}
</style>
   
 <!--       <ol class="breadcrumb">
      <li><a href="../index.html"><i class="fa fa-home"></i></a></li>
      <li>Rejection Letter</li>
    </ol> -->
    
  <!--   <div class="content">  -->

<div class="widget" id="receipt">
    <div class="widget-content padding">
        
		<div id="print-div" class="container">
		   
		<div id="SiteInspectionletter">
			<div class="cert-outer-div pgmargin">
				<div class="row clearfix border-bottom-black">
					<div class="col-xs-3 text-left">
						<img src="css/images/logo2.png">
					</div>
					<div class="col-xs-6 text-center">
						<h4 class="margin-bottom-0"><strong> ${userSession.ULBName.lookUpDesc}</strong></h4>
						<p class="text-bold"><span class="text-bold">(</span>${waterTax}<span class="text-bold">)</span></p>
						<p class="text-bold"><span class="text-bold">(</span>${ServiceName}<span class="text-bold">)</span></p>			 
						<c:if test="${Turtipatra == 'Turtipatra'}">
						<p class="margin-horizontal-10"><spring:message code="trutirejection.turtiremark"/></p>
						</c:if>
						 <c:if test="${Rejction == 'Rejction'}">
						 <p class="margin-horizontal-10">(<spring:message code="trutirejection.Rejection"/>)</p>
						 </c:if>
					</div>
					
					<div class="col-xs-3 text-right">
						<img src="css/images/logo2.png">
					</div>
			
				</div>
			    <div class="margin-top-10 padding-left-30">
										<table class="table">
											
											<tr>
												<td class="text-right"><p class="fontsz"><spring:message code="siteinspection.date" text="Date"/><span class="text-bold">${rejdate}</span></p></td>
											</tr>
											<tr>	
												<td class="text-right">
												<p class="fontsz"><span class="text-bold">
													<c:if test="${Turtipatra == 'Turtipatra'}">
														<p><spring:message code="siteinspection.LetterNo"/> :${TurtipatranNO}</p>
													</c:if>
													<c:if test="${Rejction == 'Rejction'}">
														<p><spring:message code="siteinspection.LetterNo"/> :${RejctionNO}</p>
													</c:if></span></p></td>
											</tr>
											<tr>
												<td><p class="text-bold fontsz"><spring:message code="siteinspection.to"/></p></td>
											</tr>
											<tr>	
												<td class="text-left"><p class="fontsz"><spring:message code="siteinspection.Mr" text="Mr / Mrs "/><span class="text-bold">${Name}</span></p></td>
												<td class="text-left"><p class="fontsz"><span class="text-bold">${dto.applicationAdd}</span></p></td>
												
											</tr>
										
										</table>
										
									</div>
									
				<div class="row margin-top-10 clearfix">
					<%-- <p>${rejdate}</p> --%>
				   
					
					<div class="col-xs-6 text-right margin-top-10">
					<%-- <c:if test="${Turtipatra == 'Turtipatra'}">
					<div class="col-xs-6 text-left margin-top-10">
					
						<p><spring:message code="siteinspection.LetterNo"/> :${TurtipatranNO}</p>
					</div>
					</c:if>
					 <c:if test="${Rejction == 'Rejction'}">
					 <div class="col-xs-6 text-right margin-top-10">
					
						<p><spring:message code="siteinspection.LetterNo"/> :${RejctionNO}</p>
					</div>
					 </c:if> --%>
					</div>
				</div>
				<%-- <p class="margin-top-10"><spring:message code="siteinspection.to"/></p> --%>
				<div class="row text-center">
					<div class="col-xs-9 col-xs-push-1">
						<%-- <p>${Name}</p> --%>
						<p class="margin-top-10">
							<spring:message code="siteinspection.subject" />:
							<c:if test="${Turtipatra == 'Turtipatra'}">
						  Turtipatra  for   ${ServiceName}
						</c:if>
						 <c:if test="${Rejction == 'Rejction'}">
						  <spring:message code="siteinspection.rejectionfor" /> ${ServiceName}
						 </c:if>
						</p>
						<p class="margin-top-10">
							<spring:message code="siteinspection.Reference" />:
							<spring:message code="siteinspection.ApplicationNo" /> ${rejapplicationid} 
							<spring:message code="siteinspection.DateApplication" /> ${ApmApplicationDate}<%-- <fmt:formatDate value="${ApmApplicationDate}" pattern="dd/MM/yyyy" /> --%>
							<br/><c:if test="${WT == 'WT'}"><spring:message code="siteinspection.ConnectionNo" /> ${conncetionNo}</c:if>  
						</p>
						
					</div>
				</div>
				<br/>
				<p class="padding-left-30"><spring:message code="siteinspection.sir" text="Sir/Madam,"/></p><br/>
			   <c:if test="${Turtipatra == 'Turtipatra'}">
				<p class="margin-top-10">
					<spring:message code="trutirejection.turtiletterbody1"/>: ${rejapplicationid}
					 <spring:message code="trutirejection.applicationdate"/> : <fmt:formatDate value="${ApmApplicationDate}" pattern="dd/MM/yyyy" />
					 <spring:message code="trutirejection.for"/> :${rejapplicationid} :${ServiceName}
					 <spring:message code="trutirejection.turtiletterbody2"/>
				</p>
			
				
					<div class="form-group clearfix">
						<p class="margin-bottom-10 margin-top-10"><b><spring:message code="trutirejection.turtiremark"/></b></p>
			     <table class="table">
			             	    
			            	<c:forEach items="${RemarkList}" var="singleDoc" varStatus="count">
			            	<tr>
			            	<td width="50">${count.index+1}</td>
			            	<td>${singleDoc.artRemarks}</td>
			            	</tr>
			            	</c:forEach>
			           
			           </table>
					</div>
			 
			   <p class="margin-top-10">
					  <spring:message code="trutirejection.turtiletterbody3"/>
				</p>
				</c:if>
				
				 <c:if test="${Rejction == 'Rejction'}">
				<p class="margin-top-10 padding-left-30">
					<spring:message code="trutirejection.turtiletterbody1"/><%-- : ${rejapplicationid} --%>
					 <%-- <spring:message code="trutirejection.applicationdate"/> : <fmt:formatDate value="${ApmApplicationDate}" pattern="dd/MM/yyyy" /> --%>
					 <spring:message code="trutirejection.rejctionletterbody1"/> <%-- :${rejapplicationid}  --%><strong>${ServiceName}</strong><br/>
					 <spring:message code="trutirejection.rejctionletterbody2"/>
				</p>
			
				
					<div class="form-group clearfix padding-left-30">
						<p class="margin-bottom-10 margin-top-10"><spring:message code="trutirejection.rejctionremark"/></p>
			     		<table class="table">
			             	    
			            	<c:forEach items="${RemarkList}" var="singleDoc" varStatus="count">
			            	<tr>
			            	<td width="50">${count.index+1}</td>
			            	<td>${singleDoc.artRemarks}</td>
			            	</tr>
			            	</c:forEach>
			           
			           </table>
					</div>
			 
			   <p class="margin-top-10 padding-left-30">
					  <spring:message code="trutirejection.rejctionletterbody3"/>
				</p>
				</c:if> 
				<div class="row margin-top-60">
					<div class="col-xs-3 col-xs-push-9 text-center">
						<p><spring:message code="siteinspection.authorizedsign"/></p>
						<img src="../../images/sign.png" style="width: 50%; margin: 1rem 0rem;">
						<br/>${userSession.ULBName.lookUpDesc}
					</div>
				</div>
			
			
			
			</div>
		
			
				
		   </div>
		</div>
		<div class="text-center">
					<button onclick="printdiv('print-div');" class="btn btn-success"> <i class="icon-print-2"></i> <spring:message code="siteinspection.print"/></button>
					 <input type="button"
										onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${rejapplicationid}','${lableID}','${ServiceId}')"
										class="btn btn-danger" value="Back">
				</div>

</div>
</div>
<!-- </div> -->
