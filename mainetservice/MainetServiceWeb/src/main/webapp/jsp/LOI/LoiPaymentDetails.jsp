<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script>

$( document ).ready(function(){
	
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate: '-0d',
		yearRange: "-100:-0"
	});
});

function saveData(element)
{
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y') {
	 return saveOrUpdateForm(element,"Your loi payment Saved successfully!", 'LoiPayment.html?redirectToPay', 'saveform');
	}
	else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N')
		{
		 return saveOrUpdateForm(element,"Your loi payment Saved successfully!", 'LoiPayment.html?PrintReport', 'saveform');
		}
	else
	{
	 return saveOrUpdateForm(element,"Your loi payment Saved successfully!", 'LoiPayment.html', 'saveform');
	}
}

</script>
    <ol class="breadcrumb">
      <li><a href="admin-home.html"><i class="fa fa-home"></i></a></li>
      <li>Home</li>
      <li>LOI</li>
      <li class="active">LOI Payment</li>
    </ol>
    <!-- ============================================================== --> 
    <!-- Start Content here --> 
    <!-- ============================================================== -->
    <div class="content">
      <div class="widget">
        <div class="widget-header">
          <h2>LOI Payment</h2>
        </div>
        <div class="widget-content padding"> 
         <div class="mand-label clearfix"><span>Field with <i class="text-red-1">*</i> is mandatory</span></div>
          <form:form action="LoiPayment.html" method="POST" class="form-horizontal" id="loipaymentSearch">
		  <jsp:include page="/jsp/tiles/validationerror.jsp"/>		
            <c:if test="${command.loiMaster.loiRecordFound eq 'Y' }">
 	          <h4>Application Details</h4>		  
                <div class="form-group">
              <label class="col-sm-2 control-label">Service Name</label>
              <div class="col-sm-4">
                <form:input path="searchDto.serviceName" type="text" class="form-control" readonly="true"/>
              </div>
              <label class="col-sm-2 control-label">Application Number</label>
              <div class="col-sm-4">
                <form:input path="loiMaster.loiApplicationId" type="text" class="form-control" readonly="true"/>
              </div>
            </div>
			<h4>Letter of Indent (LOI) Details</h4>

            <div class="form-group">
              <label class="col-sm-2 control-label">LOI Number</label>
              <div class="col-sm-4"> <form:input path="loiMaster.loiNo" type="text" class="form-control" readonly="true"/></div> 
              <label class="col-sm-2 control-label">LOI Date</label>
              <div class="col-sm-4"><fmt:formatDate pattern="dd/MM/yyyy" value="${command.loiMaster.loiDate}" /></div>
            </div>
               
             <h4>Applicant Details</h4>  
              <div class="form-group">
              <label class="col-sm-2 control-label">Applicant Mobile No</label>
              <div class="col-sm-4">
                <form:input path="searchDto.mobileNo" type="text" class="form-control" readonly="true"/>
              </div>
              <label class="col-sm-2 control-label">Applicant EmailId</label>
              <div class="col-sm-4">
               <form:input path="searchDto.email" type="text" class="form-control" readonly="true"/>
              </div>
            	</div>
            
 			 <div class="form-group">
              <label class="col-sm-2 control-label">Applicant Name</label>
              <div class="col-sm-4">
                <form:input path="searchDto.applicantName" type="text" class="form-control" readonly="true"/>
              </div>
            </div>
			
			<h4>LOI Fees and Charges in Details</h4>
			<div class="table-responsive">
            <table class="table table-bordered table-striped">
	<tr>
	<th scope="col" width="80">Sr. No</th>
	<th scope="col">Charge Name</th>
	<th scope="col">Amount</th>
	</tr>
	<c:forEach var="charges" items="${command.searchDto.getChargeDesc()}" varStatus="status">
	<tr>
	<td>${status.count}</td>
	<td><form:input path="" type="text" class="form-control" value="${charges.key}" readonly="true"/></td>
	<td>
		<fmt:formatNumber value = "${charges.value}" type = "number" var="amount" minFractionDigits="2" maxFractionDigits="2" groupingUsed="false" />
		<form:input path="" type="text" class="form-control text-right" value="${amount}" readonly="true"/>
	</td>
	</tr>
	</c:forEach>
	
	<tr>
	<td colspan="2"><span class="pull-right"><b>Total LOI Amount</b></span></td>
	<td> 
		<fmt:formatNumber value="${command.searchDto.total}" type="number" var="totalAmount" minFractionDigits="2" maxFractionDigits="2" groupingUsed="false" />
		<form:input path="searchDto.total" value="${totalAmount}" cssClass="form-control text-right" readonly="true"/>
	</td>
	</tr>
	</table>
	</div>

 <jsp:include page="/jsp/cfc/Challan/offlinePay.jsp"/>

 <div class="text-center padding-bottom-20">
   <button type="button" class="btn btn-success btn-submit" onclick="return saveData(this);">Submit</button>
   <button type="button" class="btn btn-primary" onclick="window.location.href='AdminHome.html'">Back</button>
 </div>
 </c:if>
 <c:if test="${command.loiMaster.loiRecordFound eq 'N' }">
  <span><b>No Record Found </b></span>
 </c:if>
 
 </form:form>
 </div>
 </div>
 </div>
          