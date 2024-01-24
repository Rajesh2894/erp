<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/rti/rtiPioResponse.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script type="text/javascript" src="js/mainet/script-library.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<!-- <script>
/* function printdiv(el){
	debugger
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
} */
$("html, body").animate({ scrollTop: 0 }, "slow");
</script> -->
<style>
.widget {
	padding: 40px;
}

.widget-content {
	border: 1px solid #000;
}

/*  div img {
	width: 35%;
}  */
/* div img {
	width: 100px !important;
	height: 100px !important;
} */
.invoice .widget-content.padding {
	padding: 20px;
}

.border-black {
	border: 1px solid #000;
	padding: 10px;
	min-height: 180px;
	width: 75%;
}

.padding-left-50 {
	padding-left: 50px !important;
}

.padding-right-50 {
	padding-right: 50px !important;
}


@media print {
	@page {
		size: portrait;
	}
	.padding-right-50 {
		padding-right: 40px !important;
	}
}

body {
	font-size: 20px !important;
}

.form-horizontal div ol li {
	margin-bottom: 10px;
}

/* .form-horizontal row div img {
	width: 80px !important;
	height: 80px !important;
} */
.margin-left-9 {
	margin-top: 9px !important;
}

.margin-left-50 {
	margin-top: 50px !important;
}

.margin-top-100 {
	margin-top: 100px !important;
}

.border-bottom-solid {
	border-bottom: 1px solid #505458;
}

#receipt .form-horizontal ol li:first-child {
	margin-left:0px !important;
}

.logo-left {
	width: 30%;
}

.logo-right {
	width: 45%;
	
}
</style>





<apptags:breadcrumb></apptags:breadcrumb>
<div id="letter">
	<div class="content">
		<div class="widget">
			<%-- <div class="widget-header">
				<h2>
					<spring:message code="rti.information.to.applicant"
						text="LOI Printing" />
				</h2>
			</div> --%>

			<div class="widget-content padding">
				<%-- <div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="rti.fiels.mandatory.message" text="" /></span>
			</div> --%>
				<div id="receipt">
				<style>
					.padding-left-2rem{
						padding-left:2rem !important;
					}
				</style>
					<form:form action="PioResponse.html" cssClass="form-horizontal"
						id="rtiLoiLetter">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />



						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv"></div>


						<div class="row">
							<div class="col-md-12 text-right">
								<p>
									<span class="margin-left-20"> <spring:message
											code="rti.information.report.Content32" text="Date:" />
									</span>
									<%-- <b>${command.dateDescription}</b> --%>

									<b><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
											value="${command.cfcEntity.apmApplicationDate}" /> </b>
								</p>
							</div>
							<div class="col-xs-2 col-sm-2">
								<img src="${userSession.orgLogoPath}"
									style="width: 100px; height: 100px;" />
							</div>
							<div class="col-xs-7 col-sm-7 text-center">
								<h3 class="margin-left-50">
									<c:choose>
										<c:when
											test="${userSession.getCurrent().getLanguageId() eq 1}">
											${userSession.organisation.ONlsOrgname}
										</c:when>
										<c:otherwise>
											${userSession.organisation.oNlsOrgnameMar}
										</c:otherwise>
									</c:choose>
								</h3>
							</div>
							<div class=" col-xs-3 col-sm-3 text-right">
								
								<img src="./assets/img/RTI-Logo.jpg" alt="RTI"
									class="logo-right" />
							</div>
						</div>
						<!-- <div class="border-bottom-solid"></div> -->
						<div class="row">
							<div class="col-xs-12 text-center">
								<h3 class="text-bold margin-top-20">
									<spring:message code="rti.information.report.Content1"
										text="Intimation To Applicant to deposit fee 
							                        and charges for required information and/or documents" />
								</h3>
								<h3 align="center">
									<spring:message code="rti.information.rule.loi" text="" />
								</h3>
							</div>
						</div>
						<div class="border-bottom-solid"></div>




						<!--  <div class="row margin-top-20 clear">
					
				</div> -->



						<div class="row clear margin-top-100 margin-bottom-12">
							<div class="col-sm-2 col-xs-2 padding-left-2rem">
								<p>
									<b><spring:message code="rti.information.report.Content2"
											text="To" /></b>
								</p>
							</div>
						</div>

						<div class="row clear margin-bottom-12">
							<div class="col-sm-2 col-lg-2 col-xs-2 padding-left-2rem">
								<p>
									<b><spring:message code="rti.information.report.Content3"
											text="Shri/Smt./Kum." /></b>

								</p>
							</div>
							<div class="col-sm-10 col-xs-10 col-lg-10 text-left">
								<p>
									<b>${command.cfcEntity.apmFname}
										${command.cfcEntity.apmMname} ${command.cfcEntity.apmLname}</b>
							</div>
						</div>

						<div class="row clear margin-bottom-10">
							<div class="col-sm-2  col-lg-2 col-xs-2 padding-left-2rem">
								<p>
									<b><spring:message code="rti.information.report.Content4"
											text="Address:" /></b>

								</p>
							</div>
							<div class="col-sm-10 col-xs-10 col-lg-10 text-left">
								<p>
									<b>${command.cfcAddressEntity.apaAreanm}</b>
								</p>
							</div>
						</div>
						<br></br>
						<div class="row margin-top- clear">
							<div class="col-sm-2 col-xs-2 padding-left-2rem">
								<p>
									<b><spring:message code="rti.information.report.Content5"
											text="Sir," /></b>
								</p>
							</div>
						</div>

						<br>
						<div class="row margin-top-20 clear">
							<div class=" col-sm-12 margin-bottom-30 padding-left-2rem">
								<p>
									<span class="margin-left-20"> <spring:message
											code="rti.information.report.Content6"
											text="With reference to your request/application dt." /></span> <b>${command.dateDesc}</b>
									<spring:message code="rti.information.report.Content7"
										text="(I.D.No" />
									<b>${command.cfcEntity.apmApplicationId}</b>
									<spring:message code="rti.information.report.Content8" text=")" />
									<%-- <b>${command.rtiLoidto}</b>) --%>
									<spring:message code="rti.information.report.Content9"
										text="Iam to state that you are required to deposit Rs." />
									<b>${command.reqDTO.amountToPay}</b>
									<spring:message code="rti.information.report.Content10"
										text="(in words Rupeees)" />
									<b>${command.amountInWords}</b>
									<spring:message code="rti.information.report.Content11"
										text=") for required information and documents sought for. &nbsp;It is requested to obtain the copies of the required information/documents after depositing the amount in this Department/office." />
								</p>
							</div>
						</div>




						<c:set var="lookUpList" value="${command.rtiLoiReportDTO}" />
						<div class="col-sm-offset-1 col-sm-10 margin-top-20 padding-left-2rem">
							
							<ol>
								<li>
									<%-- <c:forEach items="${command.chargeAmountList}"
								var="charge" varStatus="chargeL"> --%>
									<div class="col-sm-10 col-xs-10">
										<p>
											<spring:message code="rti.information.report.content12" />
											<b>${command.page} </b>
											<spring:message code="rti.info.report.content12" />
											<spring:message code="rti.info.rep.content12" />
											<%-- <b>${lookUpList[0].chargeAmount} </b> --%>
											<%-- <b>${lookUpList.chargeAmountPages} </b> --%>
											<b>${lookUpList.editedAmountPages} </b>
											<spring:message code="rti.information.report.content13" />
											<spring:message code="rti.information.report.content14" />
											<spring:message code="rti.info.report.content14.freecopy" />
											<%-- <b>${lookUpList[0].freeCopy} </b> --%>
											<b>${lookUpList.freeCopyPages} </b>
											<spring:message code="rti.info.report.content14.freecopy1" />
										</p>
									</div>
									<div class="col-sm-2 col-xs-2 padding-right-0">
										<fmt:formatNumber type="number"
											value="${command.pageQuantity1}" groupingUsed="false"
											maxFractionDigits="2" minFractionDigits="2"
											var="pageQuantity1" />
										<p>
											<span>Rs:</span> <span class="pull-right margin-left-9"><b>${pageQuantity1}</b></span>
										</p>
									</div>
								</li>
								<li class="clear">
									<div class="col-sm-10 col-xs-10">
										<p>
											<spring:message code="rti.information.report.content15" />
											<b>${command.largeCopy}</b>
											<spring:message code="rti.information.report.content16" />
											<%-- <b>${lookUpList[2].chargeAmount} </b> --%>
											<%-- <b>${lookUpList.chargeAmountLargePage} </b> --%>
											<b>${lookUpList.editedAmountLargePage} </b>
											<spring:message code="rti.info.report.content16" />
											<spring:message code="rti.info.report.content14.freecopy" />
											<%-- <b>${lookUpList[2].freeCopy} </b> --%>
											<b>${lookUpList.freeCopyLargePage} </b>
											<spring:message code="rti.info.report.content14.freecopy1" />


										</p>
									</div>
									<div class="col-sm-2 col-xs-2 padding-right-0">
										<fmt:formatNumber type="number"
											value="${command.pageQuantity2}" groupingUsed="false"
											maxFractionDigits="2" minFractionDigits="2"
											var="pageQuantity2" />
										<p>
											<span>Rs:</span> <span class="pull-right margin-left-9"><b>${pageQuantity2}</b></span>
										</p>
									</div>
								</li>

								<li class="clear">
									<div class="col-sm-10 col-xs-10">
										<p>
											<spring:message code="rti.information.report.content17" />
											<b>${command.photo}</b>
											<spring:message code="rti.info.report.content17" />
											<%-- <b>${lookUpList[4].chargeAmount} </b> --%>
											<%-- <b>${lookUpList.chargeAmountPhoto} </b> --%>
											<b>${lookUpList.editedAmountPhoto} </b>
											<spring:message code="rti.information.report.content18" />
											<spring:message code="rti.info.report.content14.freecopy" />
											<%--  <b>${lookUpList[4].freeCopy} </b> --%>
											<b>${lookUpList.freeCopyPhoto} </b>
											<spring:message code="rti.info.report.content14.freecopy1" />
										</p>
									</div>

									<div class="col-sm-2 col-xs-2 padding-right-0">
										<fmt:formatNumber type="number"
											value="${command.pageQuantityForPhoto}" groupingUsed="false"
											maxFractionDigits="2" minFractionDigits="2"
											var="pageQuantityForPhoto" />
										<p>
											<span>Rs:</span> <span class="pull-right margin-left-9"><b>${pageQuantityForPhoto}</b></span>
										</p>
									</div>

								</li>
								<li class="clear">
									<div class="col-sm-10 col-xs-10">
										<p>
											<spring:message code="rti.information.report.content19" />
											<b>${command.flopCopy}</b>
											<spring:message code="rti.info.report.content19" />
											<%-- <b>${lookUpList[1].chargeAmount} </b> --%>
											<%-- <b>${lookUpList.chargeAmountFloppy} </b> --%>
											<b>${lookUpList.editedAmountFloppy} </b>
											<spring:message code="rti.information.report.content20" />
											<spring:message code="rti.info.report.content14.freecopy" />
											<%--  <b>${lookUpList[1].freeCopy} </b> --%>
											<b>${lookUpList.freeCopyFloppy} </b>
											<spring:message code="rti.info.report.content14.freecopy1" />
										</p>
									</div>
									<div class="col-sm-2 col-xs-2 padding-right-0">
										<fmt:formatNumber type="number"
											value="${command.pageQuantity3}" groupingUsed="false"
											maxFractionDigits="2" minFractionDigits="2"
											var="pageQuantity3" />
										<p>
											<span>Rs:</span> <span class="pull-right margin-left-9"><b>${pageQuantity3}</b></span>
										</p>
									</div>
								</li>

								<li class="clear">
									<div class="col-sm-10 col-xs-10">
										<p>
											<spring:message code="rti.information.report.content21" />
										</p>
									</div>

									<div class="col-sm-2 col-xs-2 padding-right-0">
										<fmt:formatNumber type="number"
											value="${command.pageQuantityForCharges}"
											groupingUsed="false" maxFractionDigits="2"
											minFractionDigits="2" var="pageQuantityForCharges" />
										<p>
											<span>Rs:</span> <span class="pull-right margin-left-9"><b>${pageQuantityForCharges}</b></span>
										</p>
									</div>

								</li>
								<li class="clear">
									<div class="col-sm-10 col-xs-10">
										<p>
											<spring:message code="rti.information.report.content22" />
											<b>${command.inspection}</b>
											<spring:message code="rti.info.report.content22" />
											<%-- <b>${lookUpList[3].chargeAmount} </b> --%>
											<%-- <b>${lookUpList.chargeAmountModel} </b> --%>
											<b>${lookUpList.editedAmountModel} </b>
											<spring:message code="rti.info.report.content14.freecopy" />
											<%-- <b>${lookUpList[3].freeCopy} </b> --%>
											<b>${lookUpList.freecopyModel} </b>
											<spring:message code="rti.info.report.content14.freecopy1" />
										</p>
									</div>
									<div
										class="col-sm-2 col-xs-2 padding-right-0 border-bottom-solid">
										<fmt:formatNumber type="number"
											value="${command.pageQuantity4}" groupingUsed="false"
											maxFractionDigits="2" minFractionDigits="2"
											var="pageQuantity4" />
										<p>
											<span>Rs:</span> <span class="pull-right margin-left-9"><b>${pageQuantity4}</b></span>
										</p>
									</div> <%-- </c:forEach> --%>
								</li>

							</ol>
						</div>
						<%-- </c:forEach> --%>


						<div class="clear"></div>
						<div class="col-sm-offset-1 col-sm-10 margin-top-20 padding-left-2rem">
							
							<ul style="list-style:none;">
								<li>
									<div class="col-sm-10 col-xs-10" margin-bottom-30">
										<p class="pull-right">Total</p>
									</div>
									<div class="col-sm-2 col-xs-2 margin-bottom-30 ">
										<fmt:formatNumber type="number" value="${command.grandTotal}"
											groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"
											var="grandTotal" />
										<p>
											<span class="">Rs:</span><span
												class="pull-right text-bold margin-left-9">${grandTotal}</span>
										</p>
									</div>
								</li>
							</ul>
						</div>
						<br></br>
						<br></br>







						<div class="clear row">
							<div
								class="col-xs-4 col-xs-offset-8 col-sm-4 col-lg-3 col-sm-offset-8 col-lg-offset-9 margin-top-100">
								<spring:message code="rti.information.report.pio.content"
									text="With Regards," />
								<!-- <br></br>  -->
								<%-- <spring:message code="rti.information.report.content26"
					text="(signature)" />	 --%>

								<%-- <div class="margin-bottom-10">
							<spring:message code="rti.information.report.content26"
								text="(signature)" />
							</p>
						</div> --%>
								<div class="margin-bottom-10">
									<p>
										<b>${command.pioName}</b>
									</p>
									<p>
										<spring:message code="rti.information.report.content27"
											text="(Public Information Officer)" />
									</p>
								</div>
								<%-- <div class="margin-bottom-10">
									<p>
										<b>${command.department.dpDeptdesc}</b>
									</p>
									<p>
										<spring:message code="rti.information.report.content28"
											text="(Name of Department/Office)" />
									</p>
								</div> --%>
								<div class="margin-bottom-10">
									<spring:message code="rti.information.report.content29"
										text="Telephone No:" />
									<b>${command.pioNumber}</b>
								</div>
								<div class="margin-bottom-10">
									<spring:message code="rti.information.report.content30"
										text="e-mail:" />
									<b>${command.pioEmail}</b>
								</div>
								<div>
									<spring:message code="rti.information.report.content31"
										text="Website:" />
									<%-- <b>${command}</b> --%>
								</div>
							</div>
						</div>

						<!-- For print and back button -->

						<div class="text-center padding-20">
							<button onclick="printdiv('receipt')"
								class="btn btn-primary hidden-print">
								<i class="fa fa-print"></i>
								<spring:message code="trade.print" text="Print" />
							</button>
							<button type="button"
								class="button-input btn btn-danger hidden-print"
								name="button-Cancel" value="Cancel" onclick="backPage();"
								id="button-Cancel">
								<spring:message code="trade.back" text="Back" />
							</button>
						</div>

					</form:form>
				</div>

			</div>
		</div>
	</div>
</div>







