<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<script type="text/javascript"
	src="js/social_security/beneficiaryPaymentOrder.js"></script>
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content" id="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="" text="Beneficiary Payment Order" />
				</h2>
				<apptags:helpDoc url="AssetFunctionalLocation.html"></apptags:helpDoc>
			</div>
			<!-- start of section for search functional code-->
			<div class="widget-content padding">
				<form:form action="BeneficiaryPaymentOrder.html"
					class="form-horizontal" name="BeneficiaryPaymentOrder"
					id="BeneficiaryPaymentOrder">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<!---------------- Search Criteria start ---------------------------------->
					<div class="form-group">
						<label for="" class="col-sm-2 control-label required-control mandColorClass"><spring:message
								code="pension.sch.eligibility.selectschemename" /></label>
						<div class="col-sm-4">
							<form:select name="serviceId" path="bpoDto.schemeId"
								id="serviceId" class="form-control" disabled="true">
								<option value="0"><spring:message text="Select" /></option>
								<c:forEach items="${command.serviceList}" var="objArray">
									<form:option value="${objArray[0]}" code="${objArray[2]}"
										label="${objArray[1]}"></form:option>
								</c:forEach>
							</form:select>
						</div>

<%-- 						<label for="" class="col-sm-2 control-label required-control mandColorClass"><spring:message
								code="pension.sch.eligibility.paymentschedule" /></label>
						<div class="col-sm-4">
							<	 path="bpoDto.paymentScheduleId"
								class="form-control chosen-select-no-results" disabled="true"
								id="paymentscheId">
								<form:option value="">
									<spring:message code='master.selectDropDwn' />
								</form:option>	
								<c:forEach items="${command.paymentList}" var="slookUp">
									<form:option value="${slookUp.lookUpId}"
										code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
 --%>					
 					</div>
			<%-- 		<div class="form-group">
						<c:set var="baseLookupCodeCYR" value="CYR" />
						<label class="col-sm-2 control-label required-control mandColorClass" for="Year">
							<spring:message text="Year:" />
						</label>
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCodeCYR)}"
							path="bpoDto.year" disabled="true" cssClass="form-control"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="Select" isMandatory="true" />
						<c:set var="baseLookupCodeMON" value="MON" />
						<label class="col-sm-2 control-label required-control mandColorClass"
							for="assetgroup"> <spring:message text="Months" /></label>
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCodeMON)}"
							path="bpoDto.month" disabled="true" cssClass="form-control"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="Select" isMandatory="true" />
					</div>
		--%>

					<%-- <div class="text-center padding-bottom-20">
						<button type="button" class="btn btn-success" id="searchBPO"
							disabled="disabled">
							<spring:message code="search.data" text="Search" />
						</button>
						<button type="Reset" class="btn btn-warning" onclick="resetBPO()"
							disabled="disabled">
							<spring:message code="reset.msg" text="Reset" />
						</button>
					</div> --%>

					<!---------------- Search Criteria end---------------------------------->
					<div class="table-responsive clear">
						<table class="table table-striped table-bordered" id="bpoHome">
							<thead>
								<tr>

									<td align="left"><spring:message text="Select All" />
										<form:checkbox path="bpoDto.checkBox"
											onchange="checkBoxValidationCall(this)"
											class="checkedcheckBox margin-left-50" value="true" disabled="true" /></td>
									<td align="center"><spring:message text="Beneficiary Name" /></td>
									<td align="center"><spring:message
											text="Beneficiary Number" /></td>
									<td align="center"><spring:message text="Amount" /></td>
									<td align="center"><spring:message text="Bank Name" /></td>
									<td align="center"><spring:message text="IFSC Code" /></td>
									<td align="center"><spring:message text="Account Number" /></td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.bpoDto.dtoList}" var="view"
									varStatus="getindex">
									<form:hidden path="" id="caertificateDate"  value="${view.lastCertificateDate}"/>
									<tr>

										<td align="center"><form:checkbox path="bpoDto.checkBox"
												id="checkBoxIds" value="true" class="checkedcheckBox"
												disabled="true" /></td>
										<td>${view.beneficiaryName}</td>
										<td>${view.beneficiaryNumber}</td>
										<td align="right">${view.amount}</td>
										<td>${view.bankName}</td>
										<td>${view.ifscCode}</td>
										<td>${view.accountNumber}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="form-group">
						<apptags:input labelCode="Remark" path="bpoDto.remark" cssClass=""
							isMandatory="false" isDisabled="true"></apptags:input>
							
					</div>


					<%-- <apptags:CheckerAction hideForward="true" hideSendback="true"></apptags:CheckerAction>
					<div class="text-center">
					
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="saveRtgsPayment(this);">
							<spring:message code="asset.transfer.save" text="Save" />
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div> --%>
					<div class="panel-group accordion-toggle" id="beneficaryPaymentOrderViewId">
						<div class="panel panel-default">
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<div class="widget-content padding">
											<apptags:CheckerAction hideForward="true" hideSendback="true"></apptags:CheckerAction>
										</div>
										<div class="text-center">
											<button type="button" id="save"
												class="btn btn-success btn-submit"
												onclick="saveRtgsPayment(this);">
												<spring:message code="asset.transfer.save" text="Save" />
											</button>
											<apptags:backButton url="AdminHome.html"></apptags:backButton>
										</div>

									</div>
								</div>

							</div>
						</div>
					</div>





				</form:form>
			</div>

		</div>
	</div>
</div>