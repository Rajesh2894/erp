<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
function printContent(el){
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}

</script>

<c:if test="${fn:length(command) gt 0}">
<c:set var="myMap" value="${command}"/>	

 <ol class="breadcrumb">
      <li><a href="#"><i class="fa fa-home"></i></a></li>
      <li>Cancel Transaction</li>
    </ol>

<div class="content animated slideInDown">
	<div class="widget invoice" id="bill">
		<div class="widget-content padding">
			<div class="row">
				<div class="col-xs-12 text-center">
					<h3 class="margin-bottom-0">
						<spring:message code="eip.canceltopay.header" />
					</h3>
				</div>
			</div>
			<div class="alert alert-danger padding-15 text-center">
				<spring:message code="eip.canceltopay.cancelmsg" />
			</div>

			<table class="table table-bordered table-striped">
				<tr>
					<td colspan="2"><spring:message code="eip.ftp.tranDetails" text="Transaction Details" /></td>
				</tr>
				<tr>
					<th><spring:message code="eip.stp.serviceInfo" text="Service Information" /></th>
					<td>${myMap.get('productinfo')}</td>
				</tr>
				<tr>
					<th><spring:message code="eip.payment.payeeName" text="Payee Name" /></th>
					<td>${myMap.get('ownerName')}</td>
				</tr>
				<tr>
					<th><spring:message code="eip.stp.phoneNo" text="Contact No" /></th>
					<td>${myMap.get('phone')}</td>
				</tr>
				<tr>
					<th><spring:message code="eip.payment.email" text="Email Id" /></th>
					<td>${myMap.get('email')}</td>
				</tr>
				<tr>
					<th><spring:message code="eip.stp.paymentAmount" text="Payment Amount(Rs)" /></th>
					<td>${myMap.get('amount')}</td>
				</tr>
				<tr>
					<th><spring:message code="eip.stp.orderNo" text="Order No." /></th>
					<td>${myMap.get('txnid')}</td>
				</tr>

				<tr>
					<th><spring:message code="eip.stp.tranRefNo" text="Transaction Reference No." /></th>
					<td>${myMap.get('mihpayid')}</td>
				</tr>
				<%-- <tr>
					<th><c:set var="string1" value="${myMap.get('addedon')}" /> <fmt:parseDate
							value="${string1}" var="theDate" pattern="yyyy-MM-dd HH:mm:ss" />
							<spring:message code="eip.stp.tranDateTime" /></th>
					<td><fmt:formatDate pattern="dd/MM/yyyy"
								value="${theDate}" /></td>
				</tr> --%>
				<tr>
    <th><spring:message code="eip.stp.tranDateTime"/></th>
    <td>${myMap.get('addedon')}</td>
  </tr>
				<tr>
					<th><spring:message code="eip.stp.tranStatus" text="Transaction Status" /></th>
					<td>${myMap.get('status')}</td>
				</tr>
				<tr>
					<th><spring:message code="" text="Transaction Cancel Reason" /></th>
					<td>${myMap.get('error_Message')}</td>
				</tr>
			</table>


			<div class="text-center margin-top-10">
				<button onclick="printContent('bill')"
					class="btn btn-primary hidden-print">
					<i class="fa fa-print fa-fw"></i>
					<spring:message code="rti.RePrint.print" text="Print" />
				</button>
				<a href="${command.get('udf3')}"
					class="btn btn-success hidden-print"><spring:message
						code="rti.proceed" text="Finish" /></a>
			</div>
		</div>
	</div>
</div>
</c:if>