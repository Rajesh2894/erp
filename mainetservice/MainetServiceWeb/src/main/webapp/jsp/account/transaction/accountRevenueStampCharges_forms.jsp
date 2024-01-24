<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%	response.setContentType("text/html; charset=utf-8");%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/transaction/accountRevenueStampChrg.js"></script>

<script>
	$(document).ready(function(){
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}
	});
</script>

<script src="js/mainet/validation.js"></script>
<script
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
	<script>

	$(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
		
		
	});
		
</script>
	 <!-- Start right content -->
<apptags:breadcrumb></apptags:breadcrumb>
    <!-- Start Content here -->
    <div class="content">
      <div class="widget">
        <div class="widget-header">
          <h2>Revenue Stamp Charges Entry</h2>
         <apptags:helpDoc url="AccountRevenueStampCharges.html" helpDocRefURL="AccountRevenueStampCharges.html"></apptags:helpDoc>	 
        </div>
        <div class="widget-content padding">
         <form:form class="form-horizontal" modelAttribute="revenueStampChargeDTO" id="revenueStampChargeDTOId"
	cssClass="form-horizontal" method="POST" action="AccountRevenueStampCharges.html">
	
	<form:hidden path="" value="${keyTest}" id="keyTest" />
	
	<div class="error-div alert alert-danger alert-dismissible"
		id="errorDivId" style="display: none;">
		<button type="button" class="close" onclick="closeOutErrBox()"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<span id="errorId"></span>
	</div>
	<div class="error-div alert alert-danger alert-dismissible" style="display:block;" id="errorDivCustBankMas">
			<input type="hidden" value="${isDefault}" id="isDefault"/>	
			</div>
            <div id="receipt">
               <div class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-2  text-center">
                  <h3 class="text-large margin-bottom-0 margin-top-0 text-bold">${userSession.getCurrent().organisation.ONlsOrgname}<br>
              Revenue Stamp Charges Entry</h3>  </div>
                  
               
              <div class="clearfix padding-10"></div>
            <div class="container">
            	
              <table class="table table-bordered  table-fixed" >
                <thead>
                 
                <tr >
                  <th>From Date</th>
                  <th>To Date</th>
                  <th>From Amount</th>
                  <th>To Amount</th>
                  <th>Stamp Value</th>
                 
                
                </tr> 
                </thead>
                <tbody>               
              
                <tr>
                  <td><div class="input-group">
						  <form:input path="fromDate" id="fromDate"
								class="datepicker cal form-control" disabled=""></form:input>
								<label class="input-group-addon mandColorClass" for="toDateId"><i class="fa fa-calendar"></i> </label>
						   </div></td>
                  <td><div class="input-group">
						 <form:input path="toDate" id="toDate"
								class="datepicker cal form-control" disabled=""></form:input>
								<label class="input-group-addon mandColorClass" for="toDateId"><i class="fa fa-calendar"></i> </label>
						   </div></td>
                  <td> <form:input path="fromAmount" id="fromAmount" class="form-control" disabled="" onkeypress="return hasAmount(event, this, 12, 2)" /></td>
                  <td> <form:input path="toAmount" id="toAmount" class="form-control" disabled="" onkeypress="return hasAmount(event, this, 12, 2)" /></td>
                  <td> <form:input path="stampCharge" id="stampCharge" class="form-control" disabled="" onkeypress="return hasAmount(event, this, 12, 2)" /></td>
                </tr>
				
               
                </tbody>
              </table>
             
           </div>
           
              <div class="text-center hidden-print padding-10">
              <button type="button" class="btn btn-danger">Back</button>
				 <button type="button" class="btn btn-warning">Reset</button>
				 <button type="button" class="btn btn-success btn-submit"
					onclick="saveRevenueStamp(this)">Save</button>
              </div>
            </div>
          </form:form>
        </div>
      </div>
    </div>
