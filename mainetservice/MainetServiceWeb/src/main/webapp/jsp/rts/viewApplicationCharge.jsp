<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<style>
	.popUp{
		width: 500px;
	}
</style>
<div class="widget margin-bottom-0">
	<div class="widget-header">
		<h2>
			<strong><spring:message
				code="rts.loi.charges"	 text="LOI Charges" /></strong>
		</h2>

	</div>
	<div class="padding-20" style="backgound: #ddd;">

		<form:form action="drainageConnection.html" method="POST"
					name="rtsService" class="form-horizontal" id="rtsService"
					commandName="command">
			<form:hidden path="" value="${errorMsg}" id="errorMsg" />

			<div class="table-responsive" id="budgetDetTable">
				<table id="budgetDetailTable" border="0" cellspacing="0"
					cellpadding="0"
					class="table table-bordered table-striped appendableBudgetDetClass">
					<tbody>
						<tr>
							<th scope="col" width="55%"><spring:message
									code="master.loi.charge.name" text="Charge Name" /></th>
							<th scope="col" width="15%"><spring:message
									code="rts.amount" text="Amount" /></th>
						</tr>
					<c:forEach items="${command.chargesList}" var="chargesList"
							varStatus="status">
							<c:set value="${status.index}" var="d"></c:set>
							<tr>
							<td width="50%"><form:input  path="chargesList[${d}].mediaType"
									disabled="true" readonly="" class="form-control text-center" /></td>
							<fmt:formatNumber type="number" value="${chargesList.chargeAmount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="chargeAmount"/>
							<td width="50%"><form:input  path="chargesList[${d}].chargeAmount" value="${chargeAmount}"
									disabled="true" readonly="" class="form-control text-right" /></td>
                        </tr>
						</c:forEach>
						<tr>
							<th scope="col" width="55%"><spring:message
									code="rts.total.amount" text="Total Amount(In Rs.)" /></th>
							<fmt:formatNumber type="number" value="${command.totalLoiAmount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="totalAmount"/>
							<td width="50%"><form:input  path="totalLoiAmount" value="${totalAmount}"
									disabled="true" readonly="" class="form-control text-right"/></td>
						</tr>
					</tbody>
				</table>
				<div class="text-center padding-10">
					<button type="button" class="btn btn-danger"
						onclick="closeApplicationCharge()">
						<spring:message code="account.close" text="Close" />
					</button>
				</div>



			</div>

		</form:form>
	</div>

</div>