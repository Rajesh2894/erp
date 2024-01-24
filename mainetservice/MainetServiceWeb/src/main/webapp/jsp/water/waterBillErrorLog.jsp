<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <script>
 function fnExcelReport()
 {
          var tab_text="<table border='2px'><tr bgcolor='#87AFC6'>";
          var textRange; var j=0;
       tab = document.getElementById('tbl1'); // id of table


       for(j = 0 ; j < tab.rows.length ; j++) 
       {     
             tab_text=tab_text+tab.rows[j].innerHTML+"</tr>";
             //tab_text=tab_text+"</tr>";
       }

       tab_text=tab_text+"</table>";
       tab_text= tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
       tab_text= tab_text.replace(/<img[^>]*>/gi,""); // remove if u want images in your table
       tab_text= tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params

 		var ua = window.navigator.userAgent;
 		var msie = ua.indexOf("MSIE "); 
 	
 		 if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer
 			{
 			   txtArea1.document.open("txt/html","replace");
 			   txtArea1.document.write(tab_text);
 			   txtArea1.document.close();
 			   txtArea1.focus(); 
 			 	sa=txtArea1.document.execCommand("SaveAs",true,"billerrors.xls");
 			 }  
		  else                 //other browser not tested on IE 11
			  sa = window.open('data:application/vnd.ms-excel, <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />' + encodeURIComponent(tab_text));  
			  return (sa);
 }
 </script>
<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here --> 
<iframe id="txtArea1" style="display:none"></iframe>
<div class="content"> 
  <div class="widget">
    <div class="widget-header">
      <h2><spring:message code="water.bill.generation.error" text="Bill Generation Errors Detail"/></h2>
      <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
    </div>
    <div class="widget-content padding">
      <form:form action="WaterBillGeneration.html" method="post" class="form-horizontal" name="WaterBillGeneration" id="waterBillGenerationForm">
       	
       	<div class="form-group" >
		<label class="col-sm-2 control-label"><spring:message code="water.waterBillErrorLog.TotalRecordsforbillgeneration"/></label>
		<div class="col-sm-4">
		<form:input path="" cssClass="form-control"  id="" value="${command.billSize}" readonly="true"></form:input>
		</div>
		
		<label class="col-sm-2 control-label"><spring:message code="water.waterBillErrorLog.TotalRecordsselectedforbillgeneration"/></label>
		<div class="col-sm-4">
		<form:input path="" cssClass="form-control"  id="" value="${command.billselectSize}" readonly="true"/>
		</div>
		</div>
		
		<div class="form-group" >
		<label class="col-sm-2 control-label"><spring:message code="water.waterBillErrorLog.TotalnoOfbillsgenerated"/></label>
		<div class="col-sm-4">
		<c:set var="errorvalues" value="${command.billselectSize - command.errorSize}"></c:set>
		<form:input path="" cssClass="form-control"  id="" value="${errorvalues}" readonly="true"/>
		</div>
		<label class="col-sm-2 control-label"><spring:message code="water.waterBillErrorLog.TotalnoOfbillsgotErros"/></label>
		<div class="col-sm-4">
		<form:input path="" cssClass="form-control"  id="" value="${command.errorSize}" readonly="true"/>
		</div>
		</div>
       	
       	<c:if test="${command.errorListMap.size()>0}">
			  <div class="table-responsive">
              <table class="table table-bordered table-condensed" id="tbl1">
	              	<tr>
					   <th><spring:message code="water.nodues.srno"/></th>
					   <th><spring:message code="water.ConnectionNo"/></th>
					   <th><spring:message code="water.ErrorMessage"/></th>
	              	</tr>
	            	<c:forEach items="${command.errorListMap}" var="error" varStatus="statusPayment"> 
					<c:set var="errorData" value="${error.value}"></c:set>
	              	<tr>
		              <td><c:out value="${statusPayment.index+1}"></c:out></td>
		              <td><c:out value="${errorData.connumber}"></c:out></td>
		              <td><c:out value="${errorData.errMsg}"></c:out></td>
	              	</tr>
             	 </c:forEach>
         		</table>
         </div>
       	</c:if>
       	
         <div class="text-center padding-top-10">
         	<c:if test="${command.errorListMap.size()>0}">
          <button type="button" class="btn btn-success"  onclick="fnExcelReport()"><spring:message code="water.bill.excel"/></button>
          </c:if>
          <button type="button" class="btn btn-success" onclick="window.location.href='AdminHome.html'"><spring:message code="water.meter.ok"/></button>
        </div>
      </form:form>
    </div>
  </div>
</div>
