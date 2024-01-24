
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/water/reconnection.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.reconnection.header"></spring:message>
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div>
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<form:form action="WaterReconnectionForm.html"
				class="form-horizontal form" name="frmWaterReconnectionForm"
				id="frmWaterReconnectionForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<%-- <jsp:include page="/jsp/mainet/applicantDetails.jsp"></jsp:include> --%>
					<apptags:applicantDetail wardZone="WWZ" disabled="true"></apptags:applicantDetail>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#child-level1"><spring:message
										code="water.reconnection.header"></spring:message></a>
							</h4>
						</div>
						<div id="child-level1" class="panel-collapse">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="water.ConnectionNo"></spring:message></label>
									<div class="col-sm-4">
										<form:input path="reconnRequestDTO.connectionNo" type="text"
											class="form-control disablefield" id="connectionNo"
											data-rule-required="true" disabled="true"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="water.consumerName"></spring:message></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control disablefield"
											path="reconnRequestDTO.consumerName" id="consumerName"
											readonly="true" disabled="true"></form:input>
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="water.connectiondetails.tariffcategory"></spring:message></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control disablefield"
											path="reconnRequestDTO.tarrifCategory" id="conCategory"
											readonly="true" disabled="true"></form:input>
											
									</div>
								</div>
								<div class="form-group">
											
											<label class="col-sm-2 control-label"><spring:message code="water.ConnectionSize" /></label>
											
										<c:set var="baseLookupCode" value="CSZ" />
					<apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
						path="reconnRequestDTO.connectionSize" cssClass="form-control disablefield"
						hasChildLookup="false" showAll="false" showOnlyLabelWithId="true"
						selectOptionLabelCode="applicantinfo.label.select" />	
								
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="water.reconnection.disconnectionMethod"></spring:message></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control disablefield"
											path="reconnRequestDTO.discMethod" id="discMethod"
											readonly="true" disabled="true"></form:input>
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="water.reconnection.disconnectionDate"></spring:message></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control disablefield"
											path="reconnRequestDTO.discDate" id="discDate"
											readonly="true" disabled="true"></form:input>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="water.reconnection.disconnectionType"></spring:message></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control disablefield"
											path="reconnRequestDTO.disconnectionType" id="discType"
											readonly="true" disabled="true"></form:input>
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="water.remark"></spring:message></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control disablefield"
											path="reconnRequestDTO.discRemarks" id="remarks"
											readonly="true" disabled="true"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="water.plumber.details"></spring:message></label>
									<div class="radio col-sm-4">
										<label> <form:radiobutton
												path="reconnRequestDTO.plumber" value="Y" id="ULBRegister"
												checked="true" disabled="true"/> <spring:message code="water.plumber.reg"></spring:message>
										</label> <label> <form:radiobutton
												path="reconnRequestDTO.plumber" value="N" id="NotRegister" disabled="true"/>
											<spring:message code="water.plumber.notreg"></spring:message>
										</label>
									</div>
									<label class="col-sm-2 control-label" for="plumberName"><spring:message
											code="" text="Plumber Name" /></label>
									<%-- <div class="col-sm-4">

											<form:input name="" type="text" class="form-control"
												path="reqDTO.plumberName" id="plumberName"></form:input>
										</div> --%>
									<div id="ulbPlumber">
										<div class="col-sm-4">
											<form:select path="reconnRequestDTO.plumberId"
												class="form-control" id="plumber" disabled="true">
												<form:option value="">
													<spring:message code="water.dataentry.select" text="Select" />
												</form:option>
												<c:forEach items="${command.plumberList}" var="lookUp">
													<form:option value="${lookUp.plumberId}">${lookUp.plumberFullName}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
									<div id="licensePlumber">
										<div class="col-sm-4">
											<form:select path="reconnRequestDTO.plumberId"
												class="form-control" id="licPlumber">
												<form:option value="">
													<spring:message code="water.dataentry.select" text="Select" />
												</form:option>
												<c:forEach items="${command.plumberList}" var="lookUp">
													<form:option value="${lookUp.plumberId}">${lookUp.plumberFullName}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>

								</div>

							</div>
						</div>
						<div class="padding-top-10 text-center">
								
								<input type="button" class="btn btn-danger" id="backBtnId"
									onclick="window.location.href='CitizenHome.html'"
									value=<spring:message code="bckBtn"/> />
							</div>
					</div>

				</div>
			</form:form>
		</div>
	</div>
</div>

