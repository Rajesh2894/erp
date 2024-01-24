<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.min.css"
	rel="stylesheet" type="text/css">
<script
	src="js/swm/constructNDemoWasteCollector.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="construct.demolition.header" />
			</h2>
			<apptags:helpDoc url="WasteCollector.html"></apptags:helpDoc>			
		</div>
		<div class="pagediv">
			<div class="widget-content padding">
				<form:form id="constructDemolitionWasteCollector"
					name="constructDemolitionWasteCollector" class="form-horizontal"
					action="WasteCollector.html" method="post">

					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv"></div>
					</div>
					<!--add new prefix  -->
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse1">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#Applicant">
										<spring:message code="construct.demolition.applicant.details"
											text="Applicant Information"></spring:message>
									</a>
								</h4>
							</div>
							<div id="Applicant" class="panel-collapse collapse">
								<jsp:include
									page="/jsp/swm/applicantDetails.jsp"></jsp:include>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse1" href="#a1"><spring:message
											code="construct.demolition.location.details"
											text="Location Details" /></a>
								</h4>
							</div>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="construct.demolition.waste.capacity"
											path="collectorReqDTO.collectorDTO.capacity"
											cssClass="form-control hasNumber" isMandatory="true"
											isDisabled="${command.enableSubmit eq true}"></apptags:input>
										<apptags:input labelCode="construct.demolition.no.trip"
											cssClass="form-control hasNumber" maxlegnth="3"
											path="collectorReqDTO.collectorDTO.noTrip" isMandatory="true"
											isDisabled="${command.enableSubmit eq true}"></apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="construct.demolition.bldg.permission"
											cssClass="form-control" maxlegnth="50"
											path="collectorReqDTO.collectorDTO.bldgPermission"
											isDisabled="${command.enableSubmit eq true}"></apptags:input>
							
							 			<label class="control-label required-control col-sm-2" for="">
											<spring:message code="construct.demolition.vehicle.type" />
										</label>
										<c:set var="baseLookupCode" value="VCH" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="collectorReqDTO.collectorDTO.vehicleType"
											disabled="${command.enableSubmit eq true}" cssClass="form-control"
											changeHandler="" hasChildLookup="false"
											hasId="true" showAll="false" selectOptionLabelCode="Select"
											isMandatory="true" />
									</div>

							
									<div class="form-group">
										<label class="control-label col-sm-2 required-control" for=""> <spring:message
												code="construct.demolition.location" />
										</label>
										<div class="col-sm-4">
											<form:select path="collectorReqDTO.collectorDTO.locationId"
												disabled="${command.enableSubmit eq true}"
												cssClass="form-control chosen-select-no-results"
												isMandatory="true" id="locationId">
												<form:option value="">
													<spring:message code='' text="Select" />
												</form:option>
												<c:forEach items="${command.locList}" var="locationList">
													<form:option value="${locationList.locId }" code="">${locationList.locNameEng }</form:option>
												</c:forEach>
											</form:select>
										</div>

										<apptags:input labelCode="construct.demolition.address.of.consruction.site"
											cssClass="form-control" maxlegnth="50"
											path="collectorReqDTO.collectorDTO.locAddress"
											isMandatory="true" isDisabled="${command.enableSubmit eq true}"></apptags:input>
									
									</div>							
								</div>
							</div>
						</div>
						<div id="payandCheckIdDiv">
							<jsp:include
								page="/jsp/swm/paymentCheckList.jsp"></jsp:include>
						</div>
					</div>
					<c:if test="${command.enableCheckList eq true}">
						<div class="padding-top-10 text-center" id="chekListChargeId">
							<button type="button" class="btn btn-success btn-submit"
								onclick="getChecklistAndCharges(this)">
								<spring:message code="water.btn.proceed" />
							</button>
							<apptags:resetButton></apptags:resetButton>
							<input type="button" id="backBtn" class="btn btn-danger"
								onclick="back()" value="<spring:message code="bt.backBtn"/>" />
						</div>
					</c:if>

					<c:if test="${command.enableSubmit eq true}">
						<div class="padding-top-10 text-center">
							<button type="button" class="btn btn-success btn-submit"
								onclick="saveWasteCollector(this)" id="submit">
								<spring:message code="bt.save" />
							</button>
							<apptags:resetButton></apptags:resetButton>
							<input type="button" id="backBtn" class="btn btn-danger"
								onclick="back()" value="<spring:message code="bt.backBtn"/>" />
						</div>
					</c:if>
				</form:form>
			</div>
		</div>
	</div>
</div>