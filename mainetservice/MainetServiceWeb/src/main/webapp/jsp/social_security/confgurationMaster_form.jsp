<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- End JSP Necessary Tags -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/social_security/schemeConfigurationMaster.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script> 
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<script>
	$("select").chosen();
</script>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="config.mst"
						text="Configuration Master" /></strong>
			</h2>
		</div>

		<div id="content" class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>
			<!-- Start Form -->
			<!-- configurationMaster.html -->
			<form:form action="schemeConfigurationMaster.html" method="POST"
				name="configurationMaster" class="form-horizontal"
				id="configurationMaster" commandName="command"
				onload="beneficiaryStatus(this)">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>


				<div class="mand-label clearfix">
					<span>Field with <i class="text-red-1">*</i> is mandatory
					</span>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="social.sec.schemename" /></label>

					<div class="col-sm-4">
						<c:choose>
							<c:when
								test="${command.saveMode eq 'V' || command.saveMode eq 'E'}">
								<form:select name="applicationformdtoId"
									path="configurtionMasterDto.schemeMstId" id="schemeMstId"
									class="form-control chosen-select-no-results" disabled="true">
									<option value="0"><spring:message text="Select" /></option>
									<c:forEach items="${command.serviceList}" var="objArray">
										<c:choose>
											<c:when
												test="${userSession.getCurrent().getLanguageId() eq 1}">
												<form:option value="${objArray[0]}" code="${objArray[2]}"
													label="${objArray[1]}"></form:option>
											</c:when>
											<c:otherwise>
												<form:option value="${objArray[0]}" code="${objArray[2]}"
													label="${objArray[3]}"></form:option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</c:when>
							<c:otherwise>
								<form:select name="applicationformdtoId"
									path="configurtionMasterDto.schemeMstId" id="schemeMstId"
									class="form-control chosen-select-no-results">
									<option value="0"><spring:message text="Select" /></option>
									<c:forEach items="${command.serviceList}" var="objArray">
										<form:option value="${objArray[1]}" code="${objArray[0]}"
											label="${objArray[2]}"></form:option>
									</c:forEach>
								</form:select>
							</c:otherwise>
						</c:choose>
					</div>


					<apptags:input labelCode="config.mst.beneficiary.count.number"
						path="configurtionMasterDto.beneficiaryCount"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						cssClass="hasAadharNo" isMandatory="true" maxlegnth="12"></apptags:input>
				</div>
				<!--  -->
				<div class="form-group">
					<label for="text-1"
						class="control-label col-sm-2 required-control fromDate"><spring:message
							code="social.demographicReport.fromdate" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input class="form-control datepicker " id="fromDate"
								path="configurtionMasterDto.fromDate"
								onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"
								placeholder="Enter From Date" maxlength="10"
								disabled="${command.saveMode eq 'V' ? true : false }" />
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>
						</div>
					</div>
					<label for="text-1" class="control-label col-sm-2 required-control"><spring:message
							code="social.demographicReport.todate" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input class="form-control datepicker todate" id="toDate"
								path="configurtionMasterDto.toDate"
								onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"
								placeholder="Enter To Date" maxlength="10"
								onchange = "dateEnable()"
								disabled="${command.saveMode eq 'V' ? true : false }"
								 />
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>
						</div>
					</div>
				</div>


				<div class="text-center margin-top-15">
					<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
						<button type="button" class="btn btn-success"
							data-toggle="tooltip" data-original-title="Save"
							onclick="saveConfigurationMasterForm(this,'E')">
							<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i><spring:message code="soc.save" text="Save" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
						<button type="button" class="btn btn-warning"
							onclick="ResetForm()" data-toggle="tooltip"
							data-original-title="Reset">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i><spring:message code="social.btn.reset" text="Reset" />
						</button>
					</c:if>
					<button type="button" class="btn btn-danger" data-toggle="tooltip"
						data-original-title="Back" onclick="backMasterForm()">
						<i class="fa fa-chevron-circle-left padding-right-5"
							aria-hidden="true"></i><spring:message code="social.btn.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
<script>
	debugger;
	 if($("#toDate").val()!="")
	{ 
		var toDate = $("#toDate").val();
		var mode = "${command.saveMode}"

		var newDate = new Date();

		var dd = newDate.getDate();
		var mm = newDate.getMonth() + 1;
		var yy = newDate.getFullYear();
		var todate = dd + "/" + mm + "/" + yy;

		var dateError = compareDate1(toDate, todate);
		if (dateError.length == 0) 
		{
			$("#beneficiaryCount").prop("disabled", true);
		} 
		else 
		{
				$("#beneficiaryCount").prop("disabled", false);
		}
		if(mode == "V")
			{
			$("#beneficiaryCount").prop("disabled", true);
			}
	}
</script>