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
          <h2>Cheque Register</h2>
        </div>
        <div class="widget-content padding">
          <form action="" method="get" class="form-horizontal">
            <div id="receipt">
              <div class="form-group">
                <div class="col-xs-12 text-center">
                   <h3 class="text-extra-large margin-bottom-0 margin-top-0">Name Of The ULB_________________________</h3><br>
                              <strong>RECEIPT REGISTER</strong></p>
                  
                </div>
              </div>
              <div class="padding-5 clear">&nbsp;</div>
              <div class="table-responsive">
              <table  class="table table-bordered table-condensed">
  <tbody>
    <tr>
      <th>Sr. No.</th>
      <th>Date</th>
      <th>Bank Payment Voucher No. &amp; Date</th>
      <th>Payment Order Number &amp; Date</th>
      <th>Name of the  Payee</th>
      <th>Nature of the Payment</th>
      <th>Cheque/Draft No.</th>
      <th>Date of the Cheque/Draft</th>
      <th>Amount(Rs.)</th>
      <th>Entered By</th>
      <th>Signature of the First Authorized Signatory</th>
      <th>Signature of the Second Authroized Signatory</th>
      <th>Date of Issue of Cheque/Draft</th>
      <th>Signature of the Receipent of Cheque/Draft</th>
      <th>Date of Clearence</th>
      <th>Remarks*</th>
    </tr>
    <tr class="text-center">
      <td>1</td>
      <td>2</td>
      <td>3</td>
      <td>4</td>
      <td>5</td>
      <td>6</td>
      <td>7</td>
      <td>8</td>
      <td>9</td>
      <td>10</td>
      <td>11</td>
      <td>12</td>
      <td>13</td>
      <td>14</td>
      <td>15</td>
      <td>16</td>
    </tr>
    <tr><td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    </tr>
      <tr><td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    </tr>
  </tbody>
</table></div>
              <div class="text-center hidden-print padding-10">
                <button onClick="printdiv('receipt');" class="btn btn-primary hidden-print"><i class="fa fa-print"></i> Print</button>
                <button type="button" class="btn btn-danger">Cancel</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>