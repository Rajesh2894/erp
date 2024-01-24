<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/social_security/cancellationofpension.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>


<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message text="Pension Cancellation Entry" code="soc.cancel.headig" />
				</h2>
				<apptags:helpDoc url="AssetFunctionalLocation.html"></apptags:helpDoc>

			</div>
			<div class="widget-content padding">
				<form:form id="cancelPensionId" action="CancellationofPension.html"
					method="POST" class="form-horizontal" name="cancelPensionId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<h4><spring:message text="Cancellation of Pension" code="soc.cancel.pension" /></h4>

					<div class="form-group">

						<label class="col-sm-2 control-label required-control" for=""><spring:message
								text="Beneficiary Name / Number"  code="soc.ben.name.num"/></label>

						<div class="col-sm-4">
							<form:select path="cancelPensionDto.beneficiaryno"
								id="benefnumNname" class="form-control chosen-select-no-results"
								data-rule-required="true">
								<form:option value="0">Select</form:option>

								<c:forEach items="${command.cancelPensionDtoList}"
									var="beneficiaryNname">
									<%-- <form:option value="${beneficiaryNname.nameofApplicant}">${beneficiaryNname.nameofApplicant} => ${beneficiaryNname.beneficiaryno}</form:option> --%>
									<form:option value="${beneficiaryNname.beneficiaryno}">${beneficiaryNname.benefnoNname}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div class="form-group">
						<apptags:input labelCode="soc.last.Cert.date"
							path="cancelPensionDto.lastDateofLifeCerti" isReadonly="true"
							cssClass="hasAddressClass "></apptags:input>

						<apptags:input labelCode="social.sec.schemename"
							path="cancelPensionDto.selectSchemeNamedesc" isReadonly="true"></apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="soc.reason"
							path="cancelPensionDto.pensionCancelReason" isMandatory="true"></apptags:input>

						<label class="col-sm-2 control-label required-control" for=""><spring:message
								text="Pension Cancellation Date" code="soc.cancel.date" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input class="form-control" id="cancelDateId"
									maxlength="10" data-label="#dispoDate"
									path="cancelPensionDto.pensionCancelDate" isMandatory="true"></form:input>
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>

					</div>
					<div class="text-center">

						<button type="button" class="btn btn-success" title="Submit"
							onclick="saveCancellationForm(this)"><spring:message text="submit" code="social.btn.submit" /></button>
						<%-- <apptags:backButton url="AdminHome.html"></apptags:backButton> --%>
						<button type="Reset" class="btn btn-warning" id="resetform"
							onclick="resetRenewalForm(this)">
							<spring:message text="Reset" code="social.btn.reset" />
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
</div>
</div>
</head>
<body>

</body>
</html>
<script>
	var lastDateofLifeCerti = $('#lastDateofLifeCerti').val();
	if (lastDateofLifeCerti) {
		$('#lastDateofLifeCerti').val(lastDateofLifeCerti.split(' ')[0]);
	}
</script>