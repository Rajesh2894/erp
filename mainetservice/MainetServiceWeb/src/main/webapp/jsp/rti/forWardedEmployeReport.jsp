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
<script type="text/javascript" src="js/rti/rtiPioResponse.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/script-library.js"></script>

<style>
.revenue p {
	font-size: 1em;
}
</style>

<script type="text/javascript">
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



<!-- Start Content here -->
<div class="content animated slideInDown">
	<div class="widget invoice revenue" id="receipt">
		<div class="widget-content padding">
			<table border="2">
				<form:form action="PioResponse.html" class="form-horizontal"
					name="rejectionReport" id="rejectionReport">
					<c:choose>
						<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
							<c:set var="orgName"
								value="${userSession.organisation.ONlsOrgname}" />
						</c:when>
						<c:otherwise>
							<c:set var="orgName"
								value="${userSession.organisation.oNlsOrgnameMar}" />
						</c:otherwise>
					</c:choose>
					<c:forEach items="${command.rtiFrdEmpDet}" var="rtiFrdEmp">
						<div class="col-sm-12" style="padding: 10px; min-height: 1000px;">
							<div class="row">

								<%-- <div class="col-xs-3">
								<img src="${userSession.orgLogoPath}" width="80">
							</div> --%>

								<h3 align="center">
									<b><spring:message code="rti.frd.content1"
											text="Letter of Intimation" /></b>
								</h3>

							</div>

							<div class="row margin-top-10 clear">
								<div class="col-sm-1 col-sm-offset-9 col-xs-2 col-xs-offset-8 ">
									<p>
										<spring:message code="frd.slno" text="Sr No.:" />
									</p>
								</div>
								<div class="col-sm-2 col-xs-2">
									<p>
										<span class="text-bold">${command.reqDTO.rtiNo}</span>
									</p>
								</div>
								<div class="col-sm-1 col-sm-offset-9 col-xs-2 col-xs-offset-8 ">
									<p>
										<spring:message code="rti.deliveryDate" text="Date:" />
									</p>
								</div>
								<div class="col-sm-2 col-xs-2">
									<p>
										<span class="text-bold"><fmt:formatDate
												value="<%=new java.util.Date()%>" pattern="dd/MM/yyyy" /></span>
									</p>
								</div>
							</div>

							<div class="row margin-top-30 clear">
								<div class="col-sm-12">
									<p>To,</p>
									<p class="text-bold margin-left-30">
										<!-- ApplicantName -->
										${rtiFrdEmp.empName}
									</p>

									<p class="text-bold margin-left-30">
										<!-- ApplicantAddress -->
										${rtiFrdEmp.deptName},${rtiFrdEmp.empDesg},
									</p>
									<p
										class="text-bold margin-left-30>
									<b>${orgName}</b>
								</p>
								<div class="col-sm-10 col-sm-offset-2">
									<p class="padding-top-20">
										<span class="text-bold"><spring:message
												code="loi.subject" text="Subject:" /></span>
												
										<spring:message code="frd.subject.det"
											text="Regarding providing the desired information on the request letter under Section-5(4) of the Right to Information Act, 2005." />

									</p>
								</div><br>
								<!-- <p class="margin-top-20 margin-bottom-20">
									<spring:message code=".sirmadam" text="Sir/Madam," />
								</p>
 -->
								<p>
                                    <spring:message code="rti.frd.sub1" text="Request letter received in the office of the undersigned" />
						<b><spring:message code="rti.information.report.Content3"
											text="Shri/Smt./Kum." /></b>&nbsp;<b>${command.cfcEntity.apmFname}
												${command.cfcEntity.apmMname} ${command.cfcEntity.apmLname}</b>&nbsp;<spring:message code="rti.frd.content2" text="Resident-" /><b>${command.cfcAddressEntity.apaAreanm}</b>
												
										 &nbsp;<spring:message code="rti.frd.date" text="dated "  />&nbsp;<b>${command.reqDTO.apmApplicationDateDesc}</b> &nbsp;
										 <spring:message code="rti.frd.content3" text="which is under the Right to Information Act 2005 in this office. View received"  />
									&nbsp:<b><spring:message code="rti.frd.date" text="dated "  />&nbsp;${command.reqDTO.rtiDeciDet }</b>.



									<spring:message code="rti.frd.content4"
										text="(photocopy attached). The information of point number-01, 02, 03, 04 and 05 sought on the above letter is related to your board.Therefore, make sure to provide the information of the requested point number-01, 02, 03, 04 and 05 on the request letter in front of the undersigned in 07 Number of Days.Attached as above."></spring:message>
									<spring:message code="loi.repo" text=""></spring:message>
								</p><br><br>
								
							<p><b><spring:message code="rti.frd.content5" text="Copy:" /><spring:message code="rti.information.report.Content3"
											text="Shri/Smt./Kum." /></b>&nbsp;<b>${command.cfcEntity.apmFname}
												${command.cfcEntity.apmMname} ${command.cfcEntity.apmLname}</b>&nbsp;<spring:message code="rti.frd.content2" text="Resident-" /><b>${command.cfcAddressEntity.apaAreanm}&nbsp;</b></p>
							</div>
						
						
						

						<br>
						<br>
						<br>
						<div class="clear"></div>

						<br>
						<br>
					
						<div class="row margin-top-10 clear">

							<div class="col-sm-3 col-sm-offset-8 col-xs-3 col-xs-offset-8 ">
								<p>
									<b><b>(</b>${command.pioName}<b>)</b></b>
								</p>
							</div>
						</div>
						<div class="row margin-top-10 clear">
							<div class="col-sm-4 col-sm-offset-8 col-xs-4 col-xs-offset-8 ">
								<p>
									<b><spring:message code="frd.rti.report"
											text="Public Information Officer/Chief Town Health Officer" /><br>${orgName}<b>
								</p>
							</div>

						</div>
						
						
</div>
					</c:forEach>
					<div class="text-center padding-20">
						<button onclick="printdiv('receipt')"
							class="btn btn-primary hidden-print">
							<i class="fa fa-print"></i>
							<spring:message code="rti.RePrint.print" text="Print" />
						</button>
						
							<apptags:backButton  buttonLabel="Close" url="AdminHome.html"></apptags:backButton>
						
					</div>
				</form:form>

			</table>
		</div>
	</div>
</div>