<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/cfc/scrutiny.js"></script>


	<!-- Start info box -->
<div class="">

	<div class="widget-content form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />

		<form:form action="ScrutinyLabelView.html" name="frmScrutinyLabel"
			id="frmScrutinyLabel" class="form">

			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class="collapsed"
							data-parent="#accordion_single_collapse" href="#a10"> <spring:message code="cfc.applicantdetail" text="Applicant Details" /></a>
					</h4>
					<div id="a10" class="panel-collapse collapse">
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-2 control-label"> <spring:message
										code="checklistVerification.serviceName" text="Service Name" />:
								</label>
								<div class="col-sm-4">
									<span class="form-control height-auto">${command.scrutinyLabelDTO.serviceName}</span>
								</div>
								<label class="col-sm-2 control-label"> <spring:message
										code="TCP.applicant.name" text="Name Of Applicant" />:
								</label>
								<div class="col-sm-4">
									<span class="form-control height-auto">${command.scrutinyLabelDTO.applicantName}</span>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label"> <spring:message
										code="cfc.applId" text="Application Id" />:
								</label>
								<c:choose>
									<c:when test="${not empty command.scrutinyLabelDTO.refNo}">
										<div class="col-sm-4">
											<span class="form-control">${command.scrutinyLabelDTO.refNo}</span>
										</div>
									</c:when>
									<c:otherwise>
										<div class="col-sm-4">
											<span class="form-control">${command.scrutinyLabelDTO.applicationId}</span>
										</div>
									</c:otherwise>
								</c:choose>
								<label class="col-sm-2 control-label"> <spring:message
										code="master.loi.applicant.mob" text="Applicant Mobile No" />:
								</label>
								<div class="col-sm-4">
									<span class="form-control">${command.scrutinyLabelDTO.mobNo}</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"> <spring:message
										code="cfc.applicant.email" text="Applicant Email Id" />:
								</label>
								<div class="col-sm-4">
									<span class="form-control">${command.scrutinyLabelDTO.email}</span>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>

