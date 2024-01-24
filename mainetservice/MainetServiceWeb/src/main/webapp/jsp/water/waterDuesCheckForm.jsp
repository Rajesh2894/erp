<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/water/waterDuesCheck.js"></script>


<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.duesCheck.titile" text="Water Dues Check" />

			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="WaterDuesCheck.html" class="form-horizontal"
				name="WaterDuesCheck" id="WaterDuesCheck">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<c:if test="${command.showForm eq 'N'}">
					<div class="form-group">

						<label class="col-sm-2 control-label"><spring:message
								code="water.duesCheck.connection.number"
								text="Connection number" /></label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control preventSpace"
								path="entrySearchDto.csCcn" id="connectionNo" maxlength="20"
								minlength="9"></form:input>
						</div>

						<label class="col-sm-2 control-label" for="firstName"><spring:message
								code="water.duesCheck.consumer.name" text="Consumer Name" /></label>
						<div class="col-sm-4">
							<form:input name="" type="text" class="form-control"
								path="entrySearchDto.csName" id="csFirstName"></form:input>
						</div>


					</div>
					
					<div class="form-group wardZone">
						<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
							showOnlyLabel="false" pathPrefix="entrySearchDto.codDwzid"
							isMandatory="true" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control " showAll="false"
							showData="true" />
					</div>

					<div class="form-group">
						<div class="padding-top-10 text-center">
							<button type="button" class="btn btn-info"
								onclick="return searchWaterDetail(this);">
								<i class="fa fa-search"></i>
								<spring:message code="water.search" />
							</button>

							<button type="button" class="btn btn-warning"
								onclick="resetWaterDuesForm(this)">
								<spring:message code="water.btn.reset" />
							</button>
						</div>

					</div>

					<div class="table-responsive">
						<div class="table-responsive margin-top-10">
							<table class="table table-striped table-condensed table-bordered"
								id="advertiserTable">
								<thead>
									<tr>

										<th class="width: 350px;" ><spring:message code="water.duesCheck.connection.number" text="Connection Number" /></th>
										<th><spring:message code="water.duesCheck.consumer.name" text="Consumer Name" /></th>
										<th><spring:message code="water.duesCheck.ward" text="Ward" /></th>
										<th><spring:message code="water.duesCheck.zone" text="Zone" /></th>
										<th><spring:message code="water.duesCheck.address" text="Address" /></th>
										<th><spring:message code="water.Action" text="Action" /></th>

									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.entrySearchDtoList}"
										var="searchDto">
										<tr>
											<td>${searchDto.csCcn}</td>
											<td>${searchDto.csName}</td>
											<td>${searchDto.ward}</td>
											<td>${searchDto.zone}</td>
											<td>${searchDto.csAdd}</td>
											<td class="text-center">
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="View Dues" onclick="viewDues(${searchDto.csCcn})">
													<i class="fa fa-eye"></i>
												</button>

											</td>
										</tr>
									</c:forEach>

								</tbody>
							</table>
						</div>
					</div>
				</c:if>

				<c:if test="${command.showForm eq 'Y'}">
					<div class="accordion-toggle">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#a2"><spring:message code="water.duesCheck.connectionDetails"
									text="Connection Details" /></a>
						</h4>
						<div id="a2" class="panel-collapse collapse in">
							<div class="form-group">

								<label class="col-sm-2 control-label"><spring:message
										code="water.duesCheck.connection.number"
										text="Connection number" /></label>
								<div class="col-sm-4">
									<form:input type="text" class="form-control preventSpace"
										path="entrySearchDto.csCcn" id="connectionNo" maxlength="20"										minlength="9" readonly="true"></form:input>
								</div>

								<apptags:input labelCode="water.duesCheck.consumer.name"
									path="entrySearchDto.csName" isReadonly="true"></apptags:input>

							</div>

							<%-- <div class="form-group">
								<apptags:input labelCode="Address"
									path="entrySearchDto.csAdd" isReadonly="true"></apptags:input>
								<apptags:input labelCode="Pincode"
									path="entrySearchDto.csCpinCode" isReadonly="true"></apptags:input>
							</div>
							<div class="form-group">
								<apptags:input labelCode="Mobile No"
									path="entrySearchDto.csContactno" isReadonly="true"></apptags:input>
								<apptags:input labelCode="Email Id"
									path="entrySearchDto.csEmail" isReadonly="true"></apptags:input>
							</div> --%>
						</div>
					</div>


					<div class="accordion-toggle">
						<h4 class="panel-title table margin-top-10" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a5"><spring:message
									code="water.DuesDetails" text="Dues Details" /></a>
						</h4>
						<div id="a5" class="panel-collapse collapse in">

							<table id="BillDetails"
								class="table table-striped table-bordered margin-top-10">

								<tr>
									<th colspan="3"><p class="text-right">
											<spring:message code="water.duesCheck.currentBillAmt" text="Current Bill Amount" />
										</p></th>
									<td colspan="2"><p class="text-right">
											<fmt:formatNumber type="number"
												value="${command.currentBalAmt}" groupingUsed="false"
												maxFractionDigits="2" minFractionDigits="2"
												var="currentBalAmt" />${currentBalAmt}</p></td>
								</tr>
								<tr>
									<th colspan="3"><p class="text-right">
											<spring:message code="water.duesCheck.totalArrears" text="Total Arrears" />
										</p></th>
									<td colspan="2"><p class="text-right">
											<fmt:formatNumber type="number"
												value="${command.totalArrearsAmt}" groupingUsed="false"
												maxFractionDigits="2" minFractionDigits="2"
												var="totalArrearsAmt" />${totalArrearsAmt}</p></td>
								</tr>
								<tr>
									<th colspan="3"><p class="text-right">
											<spring:message code="water.duesCheck.totalPenalty" text="Total Penalty" />
										</p></th>
									<td colspan="2"><p class="text-right">
											<fmt:formatNumber type="number"
												value="${command.totalPenalty}" groupingUsed="false"
												maxFractionDigits="2" minFractionDigits="2"
												var="totalPenalty" />${totalPenalty}</p></td>
								</tr>
								<%-- 	<tr>
										<th colspan="3"><p class="text-right">
												<spring:message code="" text="Advance Payment"/>
											</p></th>
										<td colspan="2"><p class="text-right">
												<fmt:formatNumber type="number"
													value="${command.balanceExcessAmount}" groupingUsed="false"
													maxFractionDigits="2" minFractionDigits="2"
													var="balanceExcessAmount" />
												${balanceExcessAmount}
											</p></td>
									</tr> --%>

								<tr>
									<th colspan="3"><p class="text-right">
											<spring:message code="water.duesCheck.adjustmentAmount" text="Adjustment Amount" />
										</p></th>
									<td colspan="2"><p class="text-right">
											<fmt:formatNumber type="number"
												value="${command.adjustmentEntry}" groupingUsed="false"
												maxFractionDigits="2" minFractionDigits="2"
												var="adjustmentEntry" />
											${adjustmentEntry}
										</p></td>
								</tr>

								<tr>
									<th colspan="3"><p class="text-right">
											<spring:message code="water.duesCheck.totalPayable" text="Total Payable" />
										</p></th>
									<td colspan="2"><p class="text-right">
											<fmt:formatNumber type="number"
												value="${command.totalPaybale}" groupingUsed="false"
												maxFractionDigits="2" minFractionDigits="2"
												var="totalPaybale" />${totalPaybale}</p></td>
								</tr>

							</table>
						</div>
					</div>



					<div class="padding-top-10 text-center">
						<button type="button" class="btn btn-danger" id="back"
							onclick="window.location.href='AdminHome.html'">
							<spring:message code="water.btn.back" text="Back"></spring:message>
						</button>
					</div>
				</c:if>

			</form:form>
		</div>
	</div>
</div>
