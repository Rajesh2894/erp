<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/ui/kendo.all.min.js"></script>
<script type="text/javascript" src="js/water/illegalNoticeGeneration.js"></script>
<script>
	function ExportPdf() {
		kendo.drawing.drawDOM("#downloadapplication", {
			forcePageBreak : ".page-break",
			paperSize : "A4",
			// margin: { top: "0cm", bottom: "0cm" },
			scale : 0.7,
			height : 300,
			template : $("#page-template").html(),
			keepTogether : ".prevent-split"
		}).then(function(group) {
			kendo.drawing.pdf.saveAs(group, "IllegalNoticeForm.pdf")
		});
	}

	function printDiv(divName) {
		var printContents = document.getElementById(divName).innerHTML;
		var originalContents = document.body.innerHTML;
		document.body.innerHTML = printContents;
		window.print();
		document.body.innerHTML = originalContents;
	}
</script>
<!-- Start Content here -->
<div class="content-page">
	<div class="content">
		<div class="widget">

			<div class="widget-content padding">
				<form:form action="IllegalConnectionNoticeGeneration.html"
					class="form-horizontal" id="IllegalConnectionNoticeGeneration"
					name="IllegalConnectionNoticeGeneration">
					<div id="downloadapplication">
						<div class="col-sm-12">
							<p class="text-center text-large">
								${userSession.getCurrent().getOrganisation().getONlsOrgname()} <br>
								Water Supply Department
							</p>
							<div class="clear"></div>
							<hr>
							<p class="text-center">Notice</p>
						</div>



						<div class="col-sm-4 col-sm-offset-8">
							<p class="text-bold">${command.csmrInfo.csIllegalNoticeNo}</p>
							<p class="text-bold">${command.csmrInfo.illegalNoticeDate}</p>
						</div>
						<div class="clear"></div>
						<br> <br>
						<div class="col-sm-12">
							<p class="text-bold">TO,</p>
							<p class="text-bold">${command.csmrInfo.csOname},</p>
							<p class="text-bold">${command.csmrInfo.csOadd},</p>
							<!-- <p class="text-bold">Raipur 422003</p> -->
						</div>
						<div class="clear"></div>
						<br> <br>

						<div class="col-sm-10 col-sm-offset-2">
							<p class="text-bold">Subject: Notice Regarding Illegal water
								connection from Distribution Line</p>
						</div>
						<div class="clear"></div>
						<br>
						<div class="col-sm-4 col-sm-offset-2">
							<p class="text-bold">Ref. Property No :
								${command.csmrInfo.propertyNo}</p>
						</div>

						<%-- <div class="col-sm-6">
							<p class="text-bold">Usage Type :- ${csmrInfo.csCcntype}</p>
						</div> --%>
						<%-- <c:if test="${command.csmrInfo.codDwzid1 ne null}">
							<div class="col-sm-4 col-sm-offset-2">
								<p class="text-bold">Zone/Ward : ${csmrInfo.codDwzid1}</p>
							</div>
						</c:if>
						<c:if test="${command.csmrInfo.codDwzid2 ne null}">
							<div class="col-sm-6">
								<p class="text-bold">Zone/Ward :
									${command.csmrInfo.codDwzid1}</p>
							</div>
						</c:if> --%>
						<div class="clear"></div>
						<br> <br>
						<div class="col-sm-12">
							<p>Dear Sir/ Madam,</p>
						</div>
						<div class="clear"></div>

						<div class="col-sm-12">
							<p>As per above reference subject Notice No.
								${command.csmrInfo.csIllegalNoticeNo} issued by
								${userSession.getCurrent().getOrganisation().getONlsOrgname()}.
								Is inform you that during the inspection done by our officer it
								is found that Illegal connection of size
								${command.csmrInfo.connectionSize} at your property is traced by
								${userSession.getCurrent().getOrganisation().getONlsOrgname()},
								So ULB wants to intimate you about this connection will be taken
								from ULB branch line from the dated
								${command.csmrInfo.illegalDate}. It is informed you that
								regularized your connection to avoid the action.</p>
						</div>
						<div class="clear"></div>
						<br> <br>
						<div class="col-sm-12">
							<p>Note:-</p>
						</div>
						<div class="col-sm-12">
							<ol>
								<li>Citizen can apply from
									${userSession.getCurrent().getOrganisation().getONlsOrgname()}
									authored web portal to regularize the connection</li>
								<li>Citizen can approch to department to regularize the
									connection</li>
							</ol>
						</div>
						<div class="clear"></div>
						<br> <br> <br>

						<div class="col-sm-4 col-sm-offset-8">
							<p class="text-bold text-center">
								Chief Municipal Officer,<br>
								${userSession.getCurrent().getOrganisation().getONlsOrgname()} <br>WATER
								SUPPLY DEPARTMENT
							</p>
						</div>
					</div>
					<div class="text-center" id="divSubmit">

						<input type="button" class="btn btn-blue-2"
							onclick="printDiv('downloadapplication')"
							value="<spring:message code="water.plumberLicense.print"/>" />

						<button type="button" class="btn btn-danger"
							onclick="window.location.href='IllegalConnectionNoticeGeneration.html'">
							<spring:message code="water.btn.back" />
						</button>

					</div>

				</form:form>
			</div>
		</div>
	</div>