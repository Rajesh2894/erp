<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/assetDepreciationChart.js"></script>
<div class="widget-content padding">
	<form:form action="AssetRegistration.html" id="assetDepreChart"
		method="post" class="form-horizontal">
		<form:hidden path="modeType" id="modeType" />
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div
			class="warning-div error-div alert alert-danger alert-dismissible"
			id="errorDivD"></div>
			<form:hidden path="accountIsActiveOrNot" id="accountIsActiveOrNot" />
			<input type="hidden" id="moduleDeptUrl"
				value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#Applicant"><spring:message
								code="asset.chartofdep.depchart" /></a>
					</h4>
				</div>
				<div class="panel-body">
					<!--------- this for check box conditions start ---------------------------------- -->
					<c:choose>
						<c:when test="${command.modeType eq 'V'}">
							<c:choose>

								<c:when
									test="${command.astDetailsDTO.astDepreChartDTO.deprApplicable}">
									<div class="form-group">
										<label class="col-sm-2 control-label required-control" for="">
											<spring:message code="asset.chartofdep.depre.applicable" />
										</label>
										<div class="col-sm-4">
											<form:checkbox
												path="astDetailsDTO.astDepreChartDTO.deprApplicable"
												checked="${command.astDetailsDTO.astDepreChartDTO.deprApplicable}"
												id="deprApplicable" onclick="showAndHide()"
												class="margin-top-10 margin-left-10" disabled="true"></form:checkbox>

										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="form-group">
										<label class="col-sm-2 control-label " for="">
											<spring:message code="asset.chartofdep.depre.applicable" />
										</label>
										<div class="col-sm-4">
											<form:checkbox
												path="astDetailsDTO.astDepreChartDTO.deprApplicable"
												id="deprApplicable" onclick="showAndHide()"
												class="margin-top-10 margin-left-10" disabled="true"></form:checkbox>

										</div>
									</div>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label class="col-sm-2 control-label" id="deprApplicableLbl" for="">
									<spring:message code="asset.chartofdep.depre.applicable" />
								</label>
								<!--D#83926  -->
								<c:if test="${command.modeType eq 'E'}">
									<%-- <form:hidden
										path="astDetailsDTO.astDepreChartDTO.deprApplicable"
										value="${command.deprApplicable}" /> --%>
								</c:if>
								<div class="col-sm-4">
										<c:choose>
										  <c:when test="${command.modeType eq 'E' && empty command.astDetailsDTO.astDepreChartDTO.oriUseYear}">
											  <form:checkbox
													path="astDetailsDTO.astDepreChartDTO.deprApplicable" id="deprApplicable"
													onclick="showAndHide()" class="margin-top-10 margin-left-10"></form:checkbox>
										   
										  </c:when>
										  <c:otherwise>
										    <form:checkbox
												path="astDetailsDTO.astDepreChartDTO.deprApplicable"
												checked="${astDetailsDTO.astDepreChartDTO.deprApplicable}" id="deprApplicable"
												onclick="showAndHide()" class="margin-top-10 margin-left-10"
												disabled="${command.deprApplicable}"></form:checkbox>
										  </c:otherwise>
										</c:choose>
									

								</div>
							</div>
						</c:otherwise>
					</c:choose>
					<!--------- this for check box conditions end ---------------------------------- -->
					<div id=hideAndShowDeatil>
						<div class="form-group">
							<apptags:input labelCode="asset.chartofdep.salvagevalue"
								cssClass="decimal text-right form-control"  maxlegnth="18"
								isDisabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
								path="astDetailsDTO.astDepreChartDTO.salvageValue"
								isMandatory="false"></apptags:input>

							<label class="col-sm-2 control-label required-control" for="">
								<spring:message code="asset.chartofdep.chartofdeps" />
							</label>
							<div class="col-sm-4">
								<form:select path="astDetailsDTO.astDepreChartDTO.chartOfDepre"
									disabled="${command.modeType eq 'V'}" id="chartOfDepreName"
									data-rule-required="true" isMandatory="true"
									cssClass="form-control required-control mandColorClass">
									<form:option value="0" selected="selected">
										<spring:message code="asset.info.select" />
									</form:option>
									<c:forEach items="${command.chartList}" var="lookUp">
										<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<apptags:input labelCode="asset.chartofdep.Useful.life"
								isDisabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
								path="astDetailsDTO.astDepreChartDTO.oriUseYear"  maxlegnth="18"
								cssClass="form-control hasNumber" isMandatory="true"></apptags:input>
		                    <c:if test="${command.accountIsActiveOrNot eq true}">
							<label class="col-sm-2 control-label required-control" for="dccumuldeprea/c">
								<spring:message code="asset.chartofdep.dccumuldeprea/c" />
							</label>
							<div class="col-sm-4">
								<form:select path="astDetailsDTO.astDepreChartDTO.accumuDepAc"
									disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
									cssClass="form-control mandColorClass chosen-select-no-results"
									id="accumuDepAc" data-rule-required="true">
									<form:option value="">
										<spring:message code='asset.info.select' text="Select" />
									</form:option>
									<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
									<c:forEach items="${command.acHeadCode}" var="accumuDepAcList">
										<form:option value="${accumuDepAcList.lookUpDesc }"
											code="${accumuDepAcList.lookUpId}">${accumuDepAcList.lookUpDesc}</form:option>
									</c:forEach>
									</c:if>
									<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
									<c:forEach items="${command.acHeadCode}" var="accumuDepAcList">
										<form:option value="${accumuDepAcList.descLangSecond }"
											code="${accumuDepAcList.lookUpId}">${accumuDepAcList.descLangSecond}</form:option>
									</c:forEach>
									</c:if>
								</form:select>
							</div>
							</c:if>
							<c:if test="${command.accountIsActiveOrNot ne true}">
							<apptags:input labelCode="asset.chartofdep.dccumuldeprea/c"
								path="astDetailsDTO.astDepreChartDTO.accumuDepAc"
								isDisabled="${command.modeType eq 'V'}" isMandatory="false"
								cssClass="alphaNumeric" maxlegnth="100"></apptags:input>
							</c:if>
						</div>
						<!-- start -->
						<div class="form-group">
							<label class="col-sm-2 control-label" for=""><spring:message
									code="asset.chartofdep.initial.accumulated.depreciation.date" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input class="form-control datepicker"
										id="accumulDeprDate"
										path="astDetailsDTO.astDepreChartDTO.initialAccumulDeprDate"
										isMandatory="false" maxlength="10"
										disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
							<div>
								<apptags:input
									labelCode="asset.chartofdep.initial.accum.depre.amt" maxlegnth="18"
									isDisabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
									path="astDetailsDTO.astDepreChartDTO.initialAccumDepreAmount"
									isMandatory="false"></apptags:input>
							</div>

						</div>

						<c:if test="${command.modeType eq 'V' || command.modeType eq 'E'}">
							<div class="form-group">
								<label class="col-sm-2 control-label" for=""><spring:message
										code="asset.chartofdep.latest.accumulated.depreciation.date" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input class="form-control datepicker"
											id="accumulDeprDate"
											path="astDetailsDTO.astDepreChartDTO.latestAccumulDeprDate"
											isMandatory="false" maxlength="10"
											disabled="${command.modeType eq 'V' || command.modeType eq 'E'}"></form:input>
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
									</div>
								</div>
								<div>
									<apptags:input
										labelCode="asset.chartofdep.latest.accum.depre.amt" maxlegnth="18"
										isDisabled="${command.modeType eq 'V' || command.modeType eq 'E'}"
										path="astDetailsDTO.astDepreChartDTO.latestAccumDepreAmount"
										isMandatory="false"></apptags:input>
								</div>
							</div>
						</c:if>

						<div class="form-group">
							<apptags:input labelCode="asset.chartofdep.remark"
								isDisabled="${command.modeType eq 'V'}" maxlegnth="500"
								path="astDetailsDTO.astDepreChartDTO.remark" isMandatory="false"></apptags:input>
						</div>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${command.approvalProcess ne 'Y' }">
			<div class="text-center">
				<c:choose>
				  <c:when test="${command.modeType eq 'C' || command.modeType eq 'V' || command.modeType eq 'D'}">
				    <c:set var="backButtonAction"
							value="showPreviousTab('${userSession.moduleDeptCode == 'AST' ? '#astLease':'#astInsu'}','#astInsu')" />
				  </c:when>
				  <c:otherwise>
				    <c:set var="backButtonAction" value="backToHomePage()" />
				  </c:otherwise>
				</c:choose>
				
				<c:if test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D'}">
					<button type="button" class="button-input btn btn-success"
						name="button" value="Save" onclick="saveDeprChart(this);"
						id="saveDprBT">
						<spring:message code="asset.chartofdep.save&continue" />
					</button>
				</c:if>
				<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
					<button type="Reset" class="btn btn-warning" onclick="resetChart()">
						<spring:message code="reset.msg" text="Reset" />
					</button>
				</c:if>
				<button type="button" class="btn btn-danger" name="button" id="Back"
					value="Back" onclick="${backButtonAction}">
					<spring:message code="asset.information.back" />
				</button>
				
			</div>
		</c:if>
	</form:form>
</div>