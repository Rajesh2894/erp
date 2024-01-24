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
<script type="text/javascript" src="js/rti/rtiDispatch.js"></script>
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
			<form:form action="PioResponse.html" class="form-horizontal"
				name="rejectionReport" id="rejectionReport">

				<div class="row">

					<div class="col-xs-12 text-center">
						<h3 class="text-bold text-center margin-top-20">
							<spring:message code="rti.rejection.report.content1" text="" />
						</h3><br>
						<h3 align="center"><spring:message code="rti.rejection.rule" text="" /></h3>

					</div>
				</div>


				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="rti.rejection.report.content2" text="From" />

						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left">
						<p>
							<b>${command.pioName}</b>
						</p>
					</div>
				</div>

				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="report.cona19" />
						</p>
					</div>
				</div>

				<div class="row margin-top-10 clear">
					<div class="col-sm-12 col-xs-12">
						<p>
							<spring:message code="rti.rejection.report.content3"
								text="The Public Information Officer" />
						</p>
					</div>


				</div>
				<div class="row clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="rti.rejection.report.content4"
								text="(Department/office)" />
						</p>
					</div>

					<div class="col-sm-2 col-xs-2 text-left">
						<p>
							<b>${command.reqDTO.departmentName}</b>
						</p>
					</div>
				</div>
				<div class="row  clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="rti.rejection.report.content5" text="No." />

						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left">
						<p>
							<b>${command.reqDTO.rtiNo}</b>
						</p>
					</div>


					<div class="col-sm-1 col-xs-1 text-left">
						<p>
							<spring:message code="rti.date" text="Date. " />
						</p>
					</div>
					<div class="margin-left-20">
						<p>
							<b>${command.reqDTO.dateDesc}</b>
						</p>
					</div>


				</div>

				<div class="row clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="rti.rejection.report.content6" text="To" />
						</p>
					</div>
				</div>

				<div class="row clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="rti.rejection.report.content7"
								text="Shri/Smt./Kum." />

						</p>
					</div>
					<div class="col-sm-10 col-xs-10 text-left">
						<p>
							<b>${command.reqDTO.applicantName}</b>
						</p>
					</div>
				</div>
				<div class="row clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="rti.address" text="Address:" />

						</p>
					</div>
					<div class="col-sm-10 col-xs-10 text-left">
						<p>
							<b>${command.cfcAddressEntity.apaAreanm}</b>
						</p>
					</div>
				</div>
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="rti.rejection.report.content8" text="Sir," />
						</p>
					</div>
				</div>


				<div class="row margin-top-30 clear">
					<div class=" col-sm-12">
						<p>

							<span class="margin-left-20"> <spring:message
									code="rti.rejection.report.content9" /></span> <b>${command.reqDTO.dateDesc}</b>
							<spring:message code="rti.rejection.report.content10" />
							<b>${command.reqDTO.rtiNo}</b>
							<spring:message code="rti.rejection.report.content11" />
							<c:forEach items="${command.apprejMasList}" var="approveRejList"
								varStatus="status">
								<tr>
									<%-- <td>${status.count}</td> --%>
									<td><b>${approveRejList.artRemarks}</b></td>

								</tr>
							</c:forEach>

							<br> <br>
							<spring:message code="rti.rejection.report.content12" />
							<br>
						</p>
					</div>
				</div>
				<div class="row margin-top-30 clear">
					<div class=" col-sm-12">
						<p>
							<spring:message code="rti.rejection.report.content13" />
							<b>${command.reqDTO.departmentName}</b>)
							<spring:message code="rti.rejection.report.content14" />
						</p>
					</div>

				</div>

				<div class="row margin-top-30 clear">
					<div class=" col-sm-12">
						<p>
							<spring:message code="rti.rejection.report.content15" />
							<b>(First/Second)</b>
							<spring:message code="rti.rejection.report.content16" />
							<br>
							<spring:message code="rti.rejection.report.content17" />

						</p>
					</div>

				</div>

				<br>
				<br>
				<br>
				<div class="clear"></div>

				<br>
				<br>


				<div class="row margin-top-8 clear">
					<div class="col-sm-2 col-sm-offset-8 col-xs-4 col-xs-offset-8">
						<p>
							<spring:message code="rti.information.report.content25" />
						</p>
					</div>
				</div>


				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-sm-offset-8 col-xs-2 col-xs-offset-8 ">
						<p>
							<spring:message code="rti.rejection.report.content3" />

						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left">
						<p>
							<b>${command.pioName}</b>
						</p>
					</div>
				</div>

				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-sm-offset-8 col-xs-2 col-xs-offset-8">
						<p>
							<spring:message code="rti.rejection.report.content18" />

						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left">
						<p>
							<b>${command.reqDTO.departmentName}</b>
						</p>
					</div>

				</div>

				
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-sm-offset-8 col-xs-2 col-xs-offset-8">
						<p>
							<spring:message code="rti.rejection.report.content19" />

						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left">
						<p>
							<%-- <b>${command.reqDTO.departmentName}</b> --%>
						</p>
					</div>
				</div>
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-sm-offset-8 col-xs-2 col-xs-offset-8">
						<p>
							<spring:message code="rti.rejection.report.content20" />

						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left">
						<p>
							<b>${command.reqDTO.mobileNo}</b>
						</p>
					</div>
				</div>

				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-sm-offset-8 col-xs-2 col-xs-offset-8">
						<p>
							<spring:message code="rti.rejection.report.content21" />

						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left">
						<p>
							<b>${command.reqDTO.email}</b>
						</p>
					</div>
				</div>
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-sm-offset-8 col-xs-2 col-xs-offset-8">
						<p>
							<%-- <spring:message code="rti.rejection.report.content23"  /> --%>

						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left">
						<p>
							<%-- <b>${command.reqDTO.website}</b> --%>
						</p>
					</div>
				</div>


				<div class="text-center padding-20">
					<button onclick="printdiv('receipt')"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="rti.RePrint.print" text="Print" />
					</button>
					<button onClick="backPage()" type="button"
						class="btn btn-blue-2  hidden-print">Close</button>
				</div>


			</form:form>
		</div>
	</div>
</div>