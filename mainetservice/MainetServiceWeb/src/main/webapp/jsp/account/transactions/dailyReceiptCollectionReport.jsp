<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/accountFinancialReport.js"
	type="text/javascript"></script>
<script>

function printdiv(printpage) {
		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}
</script>

<div class="content">
      <div class="widget">
        <div class="widget-header">
          <h2>Summary Of Daily Collection</h2>
        </div>
        <div class="widget-content padding">
          <form action="" method="get" class="form-horizontal">
            <div id="receipt">
              <div class="form-group">
                <div class="col-xs-12 text-center">
                 <h3 class="text-extra-large margin-bottom-0 margin-top-0">Name Of The ULB_________________________</h3><br>
                              <strong>SUMMARY OF DAILY COLLECTION OF_______________________<br>COLLECTION OFFICE/COLLECTION CENTER</strong></p><br>
                    
              </div>
              <div class="col-xs-6 text-left"> 
              <p class="margin-left-30"><strong>Date:______________________</strong></p>
              </div> 
              <div class="col-xs-6 text-left"> 
              <p  class="margin-left-50"> <strong>Sr. No.:______________________</strong></p>
              </div>             
              </div>
              <div class="clearfix padding-10"></div>
              <table class="table table-bordered table-condensed">
  <tbody>
    <tr>
      <th>Sr. No.</th>
      <th>Name Of the Department</th>
      <th>Name of the Revenue Head</th>
      <th>Amount(Rs.)</th>
      <th>Amount(Rs.)</th>
      <th>Deposited With*</th>
    </tr>
    <tr class="text-center">
      <td>1</td>
      <td>2</td>
      <td>3</td>
      <td>4</td>
      <td>5</td>
      <td>6</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
        <td>&nbsp;</td>
       <td>&nbsp;</td>
       <td>&nbsp;</td>
       <td>&nbsp;</td>
       <td>&nbsp;</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
      <td>&nbsp;</td>
       <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
       <td>&nbsp;</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
      <td>&nbsp;</td>
       <td>&nbsp;</td>
       <td>&nbsp;</td>
       <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
   
    <tr>
      <th colspan="2">Grand Total</th>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <th colspan="3">Amount in words:Rupees</th>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <th colspan="3">Receipt No. issued by the Collection Office:</th>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <th colspan="3">(in case collection are deposited with Collection Office)</th>
     <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <th>Cash</th>
     <td>&nbsp;</td>
      <th>Rs.</th>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
     <td>&nbsp;</td>
      <th>Cheque</th>
      <td>&nbsp;</td>
      <th>Rs.</th>
      <td>&nbsp;</td>
     <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <th>Total</th>
      <td>&nbsp;</td>
      <th>Rs.</th>
       <td>&nbsp;</td>
     <td>&nbsp;</td>
    </tr>
  </tbody>
</table>
               <div class="row">
				<div class="col-sm-8">
					<p class="margin-top-10">Prepared By**:____________________________</p>
					<p class="margin-top-15">Checked By**:____________________________</p>
				
					<p class="margin-top-10">Date:</p>
					<p></p>
				</div>
				<div class="col-sm-4">
					<p class="margin-top-10">Examined and Entered</p>
					<p class="margin-top-15">Accountant/Authroized Officer</p>
				
				</div>				
			 </div>
			  
              <div class="text-center hidden-print padding-10">
                <button onClick="printdiv('receipt');" class="btn btn-primary hidden-print"><i class="fa fa-print"></i> Print</button>
                <button type="button" class="btn btn-danger">Cancel</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>