<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('.fancybox').fancybox();
	});

	function printContent(el) {
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	}
</script>
<div class="animated slideInDown">
	<!-- Your awesome content goes here -->

	<div class="widget" id="receipt">
		<div class="widget-content padding">
		
			<c:forEach items="${command.billMasPrint}" var="billMasMap"
				varStatus="statusPayment">
				<c:set var="billMas" value="${billMasMap.value}"></c:set>
				<table class="table table-bordered table-condensed">
					<tr>
						<td colspan="4">

							<div class="row">
								<div class="col-xs-3 margin-bottom-10">
									<img width="80" src="${userSession.getCurrent().orgLogoPath}">
								</div>
								<div class="col-xs-6 text-center">
									<h4 class="margin-bottom-0">
										<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
											<spring:message code=""
												text="${userSession.organisation.ONlsOrgname}" />
										</c:if>
										<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
											<spring:message code=""
												text="${userSession.organisation.oNlsOrgnameMar}" />
										</c:if>
									</h4>
									<p>${command.waterBillRule}</p>

									<p>
										<spring:message code="water.bill" />
									</p>

								</div>
								<div class="col-xs-3 text-right margin-bottom-10">
									<img width="80" src="${userSession.getCurrent().orgLogoPath}">
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td><span class="text-bold"><spring:message
									code="wt.connectionNumber" /> :</span> ${billMas.waterMas.csCcn} <br></br>
							<span class="text-bold"><spring:message
									code="wt.propertyNo" /></span> : ${billMas.waterMas.propertyNo}</td>

						<td colspan="2"><span class="text-bold text-underline"><spring:message
									code="wt.nameAndAddress" text="" /></span> :<br></br> <span
							class="text-bold">${billMas.waterMas.csName}&nbsp;${billMas.waterMas.csMname}&nbsp;${billMas.waterMas.csLname}</span>
							&nbsp; <br></br> ${billMas.waterMas.csFlatno}&nbsp;
							${billMas.waterMas.csBldplt} &nbsp;${billMas.waterMas.csAdd} ,
							&nbsp;<span class="text-bold"><spring:message
									code="wt.pincode" /> </span>${billMas.waterMas.csCpinCode}
							&nbsp;${billMas.waterMas.csLanear}
							&nbsp;${billMas.waterMas.csRdcross}</td>
						<td><span class="text-bold text-underline"><spring:message
									code="wt.billDetails" text="Bill Details" /></span> :<br></br> <span
							class="text-bold"> <spring:message code="wt.billNo" /> :
								${billMas.bmNo}
						</span><br></br> <span class="text-bold"><spring:message
									code="wt.billDate" /> </span>: <fmt:formatDate pattern="dd/MM/yyyy"
								value="${billMas.bmBilldt}" var="billDate" /> ${billDate}</td>


					</tr>

					<tr>
						<td><span class="text-bold"><spring:message
									code="wt.connectionSize" /> :</span> ${billMas.conSize} <br></br> <span
							class="text-bold"> <spring:message
									code="wt.terrifCategory" /></span> : ${billMas.tarriffCategory}</td>


						<td colspan="2"><span class="text-bold text-underline"><spring:message
									code="wt.billingAddress" /></span> : <br></br>
							${billMas.waterMas.csBldplt} &nbsp;${billMas.waterMas.csAdd} ,
							&nbsp;<span class="text-bold"><spring:message
									code="wt.pincode" /> </span>${billMas.waterMas.csCpinCode}&nbsp;${billMas.waterMas.csLanear}
							&nbsp;${billMas.waterMas.csRdcross}</td>



						<td><span class="text-bold text-underline"><spring:message
									code="wt.billPeriod" text="Billing Period" /></span> : <br></br> <span
							class="text-bold"> <spring:message code="wt.billFromDate" />
						</span>: <fmt:formatDate pattern="dd/MM/yyyy" value="${billMas.bmFromdt}"
								var="fromDate" /> ${fromDate} <br></br> <span class="text-bold"><spring:message
									code="wt.billToDate" /></span> : <fmt:formatDate pattern="dd/MM/yyyy"
								value="${billMas.bmTodt}" var="toDate" /> ${toDate}</td>


					</tr>

					<tr>
						<td><span class="text-bold"><spring:message
									code="wt.connectionCategory" /> :</span><br></br>
							${billMas.connectionCategory} / ${billMas.connectionType}</td>


						<td colspan="2"><span class="text-bold"><spring:message
									code="wt.connectionLocation" text="Connection Location" /> :</span><br></br>
							${billMas.waterMas.csBldplt} &nbsp;${billMas.waterMas.csAdd}
							&nbsp;${billMas.waterMas.csLanear}
							&nbsp;${billMas.waterMas.csRdcross}</td>



						<td><span class="text-bold"><spring:message
									code="wt.securityDposit" text="Security Deposit" /> :
								${billMas.securityDepositAmount} </span><br></br> <span
							class="text-bold"><spring:message code="wt.isBpl" /></span> :
							${billMas.bplFlag}</td>
					</tr>
					<tr>
						<th><spring:message code="wt.chargerDescription"
								text="Charges Description" /></th>
						<th><spring:message code="wt.arrearsAmount" /></th>
						<th><p class="text-right">
								<spring:message code="wt.currentAmount" text="Current Amount" />
							</p></th>
						<th><p class="text-right">
								<spring:message code="wt.totalAmount" />
							</p></th>
					</tr>
					<c:forEach items="${billMas.tbWtBillDet}" var="billDet"
						varStatus="detStatus">
						<tr>
							<td>${billDet.taxDesc}</td>
							<td><p class="text-right">${billDet.bdPrvBalArramt}</p></td>
							<td><p class="text-right">${billDet.bdCurBalTaxamt}</p></td>
							<td><p class="text-right">${billDet.total}</p></td>
						</tr>
					</c:forEach>
					<tr>
						<td><p class="text-right">
								<spring:message code="wt.totalBillAount" />
							</p></td>
						<td><p class="text-right">${billMas.arrearsTotal}</p></td>
						<td><p class="text-right">${billMas.taxtotal}</p></td>
						<td><p class="text-right">${billMas.grandTotal}</p></td>
					</tr>
					<tr>
						<th colspan="3"><p class="text-right">
								<spring:message code="wt.adjustedAmount" />
							</p></th>
						<td><p class="text-right">0.00</p></td>
					</tr>
					<tr>
						<th colspan="3"><p class="text-right">
								<spring:message code="wt.adjustMentAmount" />
							</p></th>
						<td><p class="text-right">${billMas.excessAmount}</p></td>
					</tr>
					<tr>
						<th colspan="3"><p class="text-right">
								<spring:message code="wt.balanceExessAmount" />
							</p></th>
						<td><p class="text-right">${billMas.balanceExcessAmount}</p></td>
					</tr>
					<tr>
						<th colspan="3"><p class="text-right">
								<spring:message code="wt.rebateAmount" />
							</p></th>
						<td><p class="text-right">${billMas.rebateAmount}</p></td>
					</tr>
					<tr>
						<th colspan="3"><p class="text-right">
								<spring:message code="wt.totalPayableAmount" />
							</p></th>
						<td><p class="text-right">${billMas.bmTotalOutstanding}</p></td>
					</tr>

					<%-- <tr>
						<th colspan="3"><p class="text-right">
								<spring:message code="wt.yearlyBillAmount" text="" />
							</p></th>
						<td><p class="text-right">${command.totalYearlyBill}</p></td>
					</tr> --%>


					<tr>
						<th colspan="4"><spring:message code="wt.rupeesInWord" /> :
							${billMas.amountInwords}</th>
					</tr>

					<tr>
						<th><spring:message code="wt.monthlyBillAmount" /></th>
						<th><spring:message code="wt.yearlyBillAmount" /></th>
						<th><spring:message code="wt.billDueDate" /></th>
						<td rowspan="4" class="auth-sign"><spring:message
								code="wt.authorisedSign" /></td>
					</tr>

					<tr>

						<td class="text-right">${billMas.totalMonthlyBill}</td>
						<td class="text-right">${billMas.totalYearlyBill}</td>
						<td><fmt:formatDate pattern="dd/MM/yyyy"
								value="${billMas.bmDuedate}" var="dueDate" />${dueDate}</td>
					</tr>

					<tr>
						<td colspan="3">Remark :
							<ol>
								<li>${billMas.bmRemarks}</li>
								<li></li>
								<li></li>
							</ol>
						</td>

					</tr>
				</table>

				<div class="pagebreak"></div>
			</c:forEach>
		</div>
	</div>
</div>
	<div class="text-center margin-top-20">
				<button onclick="printContent('receipt')"
					class="btn btn-primary hidden-print">
					<i class="icon-print-2"></i>
					<spring:message code="water.btn.print" />
				</button>
				<button type="button" class="btn btn-danger hidden-print"
					onclick="window.location.href='WaterBillPrinting.html'">
					<spring:message code="water.btn.back" />
				</button>

			</div>