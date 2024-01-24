
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>


<script>
	function printContent(el) {
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	}
</script>
<style>
.border-bottom-dashed {
	border-bottom: 1px dashed #000000;
}

.pdfAcknowledgementLineHeight {
	line-height: 20px;
	margin-left: 20px;
}
</style>


<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Your awesome content goes here -->
	<div class="widget" id="receipt">
		<style>
@media print {
	.border-bottom-dashed {
		border-bottom: 1px dashed #000000;
	}
	.pdfAcknowledgementLineHeight {
		line-height: 20px;
		margin-left: 20px;
	}
}
</style>

		<div class="widget-content padding">
			<form action="" method="get" class="bnd-acknowledgement">
				<div>
					<div class="margin-0">
						<div class="overflow-hidden padding-vertical-10">
							<div class="col-xs-2"></div>
							<div class="col-xs-8 text-center">

								<h2 class="margin-bottom-10 padding-bottom-10">${userSession.organisation.ONlsOrgnameMar}</h2>

							</div>

						</div>

						<div class="col-xs-7 col-sm-7 margin-bottom-10">
							<p class="margin-top-10">
								<span class="text-bold"><spring:message
										code="council.budget.status.councilNo" text=""></spring:message></span>
								<span class="text-bold margin-left-40">
									${command.couProposalMasterDto.proposalNo} </span>
							</p>
						</div>
						<div class="col-xs-5 col-sm-5 margin-bottom-10">
							<p class="margin-top-10">
								<span class="text-bold"><spring:message
										code="council.budget.status.date" text="Date:"></spring:message></span>
								</span> <span class="text-bold margin-left-40 margin-left-80"><fmt:formatDate
										pattern="dd/MM/yyyy"
										value="${command.couProposalMasterDto.proposalDate}" /> </span>
							</p>
						</div>

						<div class="col-xs-7 col-sm-7 margin-bottom-10">
							<p class="margin-top-10">
								<span class="text-bold"><spring:message
										code="council.budget.status.proposalName" text="Kamache Name"></spring:message></span>
								</span> <span class="text-bold margin-left-40">
									${command.couProposalMasterDto.purposeremark} </span>
							</p>
						</div>
						<div class="col-xs-5 col-sm-5 margin-bottom-10">
							<p class="margin-top-10">
								<span class="text-bold"><spring:message
										code="council.budget.status.field" text="Prabhan Samiti"></spring:message></span>
								</span> <span class="text-bold margin-left-40">
									${command.couProposalMasterDto.budgetHeadDesc} </span>
							</p>
						</div>
						<div class="col-xs-12 col-sm-12 margin-bottom-10">
							<p class="margin-top-10">
								<span class="text-bold"><spring:message
										code="council.budget.status.department" text="Vibhag KA Name"></spring:message></span>
								</span> <span class="text-bold margin-left-40 margin-left-70">
									${command.couProposalMasterDto.proposalDeptName} </span>
							</p>
						</div>
                          <c:if test="${command.couProposalMasterDto.proposalType ne 'N'}">
						<div class="table-overflow-sm" id="opnBalTableDivID">
							<c:forEach items="${command.couProposalMasterDto.yearDtos}"
								var="yearDtos" varStatus="lk">
								<table class="table table-bordered table-condensed"
									id="opnBalTable">

									<tr>
										<td scope="col" width="10%" style="text-align: right"><spring:message
												code="" text="1" /></td>
										<td scope="col" width="70%"><spring:message
												code="council.budget.status.budgetName" text="" /></td>
										<td scope="col" width="20%">${yearDtos.budgetCodeDesc}</td>
									</tr>

									<tr>
										<td scope="col" width="10%" style="text-align: right"><spring:message
												code="" text="2" /></td>
										<td scope="col" width="70%"><spring:message
												code="council.budget.status.amount1" text="" /></td>
										<td scope="col" width="20%">${yearDtos.orginalAmount}</td>
									</tr>

									<tr>
										<td scope="col" width="10%" style="text-align: right"><spring:message
												code="" text="3" /></td>
										<td scope="col" width="70%"><spring:message
												code="council.budget.status.amount2" text="Likhashivesh" />
										</td>
										<td scope="col" width="20%">${yearDtos.crntNxtYrAmount}</td>
									</tr>

									<tr>
										<td scope="col" width="10%" style="text-align: right"><spring:message
												code="" text="4" /></td>
										<td scope="col" width="70%"><spring:message
												code="council.budget.status.amount3" text="Likhashivesh" />
										</td>
										<td scope="col" width="20%">${yearDtos.curntYrAmount}</td>
									</tr>

									<tr>
										<td scope="col" width="10%" style="text-align: right"><spring:message
												code="" text="5" /></td>
										<td scope="col" width="70%"><spring:message
												code="council.budget.status.amount4" text="Likhashivesh" />
										</td>
										<td scope="col" width="20%">${yearDtos.crntAsOnAmount}</td>
									</tr>

									<tr>
										<td scope="col" width="10%" style="text-align: right"><spring:message
												code="" text="6" /></td>
										<td scope="col" width="70%"><spring:message
												code="council.budget.status.amount5" text="Likhashivesh" />
										</td>
										<td scope="col" width="20%">
											${yearDtos.amountForNewProposal}</td>
									</tr>

									<tr>
										<td scope="col" width="10%" style="text-align: right"><spring:message
												code="" text="7" /></td>
										<td scope="col" width="70%"><spring:message
												code="council.budget.status.amount6" text="Likhashivesh" />
										</td>
										<td scope="col" width="20%">${yearDtos.yeBugAmount}</td>
									</tr>

									<tr>
										<td scope="col" width="10%" style="text-align: right"><spring:message
												code="" text="8" /></td>
										<td scope="col" width="70%"><spring:message
												code="council.budget.status.amount7" text="Likhashivesh" />
										</td>
										<td scope="col" width="20%">
											${yearDtos.currentYearAmount}</td>
									</tr>

									<tr>
										<td scope="col" width="10%" style="text-align: right"><spring:message
												code="" text="9" /></td>
										<td scope="col" width="70%"><spring:message
												code="council.budget.status.amount8" text="Likhashivesh" />
										</td>
										<td scope="col" width="20%">
											${yearDtos.currentNextYearAmount}</td>
									</tr>
									<tr>
										<td scope="col" width="10%" style="text-align: right"><spring:message
												code="" text="10" /></td>
										<td scope="col" width="70%"><spring:message
												code="council.budget.status.amount9" text="Likhashivesh" />
										</td>
										<td scope="col" width="20%">${yearDtos.remainingAmount}</td>
									</tr>

								</table>
								<br>
							</c:forEach>
						</div>
						</c:if>
					</div>
					<br>
				</div>

				<div class="text-center margin-top-20">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i>
						<spring:message code="rtgs.payment.print" text="Print"></spring:message>
					</button>

					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.close();">
						<spring:message code="social.close" text="Close"></spring:message>
					</button>

				</div>

			</form>
		</div>

		<!-- End of info box -->