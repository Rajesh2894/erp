<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/water/illegalNoticeGeneration.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code=""
					text="Illegal To Legal Connection Notice Generation" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="IllegalConnectionNoticeGeneration.html"></apptags:helpDoc>
			</div>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div>
			<form:form action="IllegalConnectionNoticeGeneration.html"
				class="form-horizontal" name="IllegalConnectionNoticeGeneration"
				id="IllegalConnectionNoticeGeneration">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<form:hidden path="" id="saveMode" value="${command.saveMode}" />

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#Property" data-toggle="collapse"
									class="collapsed" data-parent="#accordion_single_collapse"
									href="#Property"><spring:message code=""
										text="Property Details" /></a>
							</h4>
						</div>
						<div id="Property" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="water.dataentry.property.number" text="Property number" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											onblur="getPropertyDetails(this)" path="csmrInfo.propertyNo"
											id="propertyNo" readonly="" data-rule-required="true"></form:input>
									</div>
									<c:if test="${command.saveMode eq 'C' }">
										<label class="col-sm-2 control-label"><spring:message
												code="" text="Property Usage Type" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												path="csmrInfo.propertyUsageType" id="" readonly="true"></form:input>
										</div>
									</c:if>

								</div>

							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#Owner" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#Owner"><spring:message
										code="" text="Owner Details" /></a>
							</h4>
						</div>
						<div id="Owner" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="csOname"><spring:message
											code="water.dataentry.owner.name" text="Owner Name" /></label>
									<div class="col-sm-4">
										<form:input name="" type="text" class="form-control"
											path="csmrInfo.csOname" id="csOname" disabled="true"
											data-rule-required="true"></form:input>
									</div>
									<label class="col-sm-2 control-label" for="gender"><spring:message
											code="applicantinfo.label.gender" /></label>
									<c:set var="baseLookupCode" value="GEN" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="csmrInfo.csOGender" cssClass="form-control"
										disabled="true" hasChildLookup="false" hasId="true"
										showAll="false"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="false" />
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="csOadd"><spring:message
											code="water.dataentry.Address" text="Address" /></label>
									<div class="col-sm-4">
										<form:textarea name="" type="text" class="form-control "
											disabled="true" path="csmrInfo.csOadd" id="csOadd"
											data-rule-required="true"></form:textarea>
									</div>
									<label class="col-sm-2 control-label required-control"
										for="opincode"><spring:message code="water.pincode" /></label>
									<div class="col-sm-4">
										<form:input name="" type="text" disabled="true"
											class="form-control hasNumber hideElement"
											path="csmrInfo.opincode" id="opincode" maxlength="6"
											data-rule-required="true"></form:input>
									</div>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="mobileNo"><spring:message
											code="applicantinfo.label.mobile" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="csmrInfo.csOcontactno" id="mobileNo" disabled="true"
											data-rule-required="true" data-rule-minlength="10"
											data-rule-maxlength="10"></form:input>
									</div>
									<label class="col-sm-2 control-label" for="emailId"><spring:message
											code="applicantinfo.label.email" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="csmrInfo.csOEmail" id="emailId" disabled="true"
											data-rule-email="true"></form:input>
									</div>
								</div>

								<div class="form-group">
									<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
										showOnlyLabel="false" pathPrefix="csmrInfo.codDwzid"
										isMandatory="true" hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control changeParameterClass" disabled="" />
								</div>
							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									data-target="#waterconnectiondetails"
									href="#waterconnectiondetails"> <spring:message
										code="water.connectiondetails" text="Connection Details" />
								</a>
							</h4>
						</div>
						<div id="waterconnectiondetails" class="panel-collapse collapse">
							<div class="panel-body">

								<div class="form-group">
									<label class="col-sm-2 control-label required-control">
										<spring:message code="water.dataentry.consumer.type"
											text="Consumer type" />
									</label>
									<div class="col-sm-4">
										<form:select path="csmrInfo.typeOfApplication"
											class="form-control changeParameterClass"
											id="typeOfApplication" data-rule-required="true" disabled="">
											<form:option value="">
												<spring:message code="water.dataentry.select" />
											</form:option>
											<form:option value="P">
												<spring:message code="water.perm" />
											</form:option>
											<form:option value="T">
												<spring:message code="water.temp" />
											</form:option>
										</form:select>
									</div>

								</div>

								<div class="form-group" id="fromtoperiod">

									<label class="col-sm-2 control-label required-control"><spring:message
											code="water.fromPeriod" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input type="text"
												class="form-control disDate mandColorClass hideElement"
												id="fromdate" path="csmrInfo.fromDate" />
											<label class="input-group-addon" for="fromd"><i
												class="fa fa-calendar"></i><span class="hide">Date</span></label>
										</div>


									</div>

									<label class="col-sm-2 control-label required-control"><spring:message
											code="water.toPeriod" /></label>

									<div class="col-sm-4">
										<div class="input-group">
											<form:input type="text"
												class="form-control distDate mandColorClass hideElement"
												id="todate" path="csmrInfo.toDate" />
											<label class="input-group-addon" for="tod"><i
												class="fa fa-calendar"></i><span class="hide">Date</span></label>
										</div>


									</div>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="csCcnsize"><spring:message
											code="water.dataentry.connection.size"
											text="Connection Size (in inches)" /></label>

									<div id="notBhagirathi">
										<div class="col-sm-4">
											<form:select path="csmrInfo.csCcnsize" class="form-control"
												id="withoutBhagiRathi" disabled="">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.getLevelData('CSZ')}"
													var="lookup">
													<form:option value="${lookup.lookUpId}"
														code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="" text="Illegal Connection From Period" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input type="text"
												class="form-control  mandColorClass datepicker"
												id="csIllegalDate" path="csmrInfo.csIllegalDate"
												onkeyup="clearInput($(this));" />
											<label class="input-group-addon" for="fromd"><i
												class="fa fa-calendar"></i><span class="hide">Date</span></label>
										</div>
									</div>
								</div>


								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="water.plumber.details" /></label>
									<div class="radio col-sm-4">
										<label> <form:radiobutton path="csmrInfo.csPtype"
												value="U" id="ULBRegister" checked="true" /> <spring:message
												code="water.plumber.reg" />
										</label> <label> <form:radiobutton path="csmrInfo.csPtype"
												value="L" id="NotRegister" /> <spring:message
												code="water.plumber.notreg" />
										</label>
									</div>
									<label class="col-sm-2 control-label required-control"
										for="plumberName"><spring:message
											code="water.plumber.licno" /></label>
									<div id="ulbPlumber">
										<div class="col-sm-4">
											<form:select path="csmrInfo.plumId" class="form-control"
												id="plumber">
												<form:option value="">
													<spring:message code="water.dataentry.select" />
												</form:option>
												<c:forEach items="${command.plumberList}" var="lookUp">
													<form:option value="${lookUp.plumId}">${lookUp.plumFname} ${lookUp.plumMname} ${lookUp.plumLname}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
									<div id="licensePlumber">
										<div class="col-sm-4">
											<form:select path="csmrInfo.plumId" class="form-control"
												id="licPlumber">
												<form:option value="">
													<spring:message code="water.dataentry.select" />
												</form:option>
												<c:forEach items="${command.plumberList}" var="lookUp">
													<form:option value="${lookUp.plumId}">${lookUp.plumFname} ${lookUp.plumMname} ${lookUp.plumLname}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- Start button -->
					<div class="text-center clear padding-10">
						<c:if test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
							<button type="button" id="save"
								class="btn btn-success btn-submit" onclick="saveData(this);">
								<i class="fa fa-sign-out padding-right-5"></i>
								<spring:message code="water.btn.submit" text="Save" />
							</button>
						</c:if>
						<c:if test="${command.saveMode eq 'C'}">
							<button class="btn btn-warning" type="button" id=""
								onclick="resetNoticeForm();">
								<i class="fa fa-undo padding-right-5"></i>
								<spring:message code="water.btn.reset" text="Reset" />
							</button>
						</c:if>

						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="window.location.href='IllegalConnectionNoticeGeneration.html'"
							id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="water.btn.back" text="Back" />
						</button>

					</div>
			</form:form>
		</div>
	</div>
</div>
