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
<script type="text/javascript"
	src="js/social_security/beneficiaryPaymentOrder.js"></script>
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content" id="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="soc.ben.pay.order" text="Beneficiary Payment Order" />
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
						<label for="" class="col-sm-2 control-label required-control"><spring:message
								code="pension.sch.eligibility.selectschemename" /> </label>
						<div class="col-sm-4">
							<form:select name="serviceId" path="" id="serviceId"
								class="form-control chosen-select-no-results" disabled="false">
								<option value="0"><spring:message text="Select" code="social.select"/></option>
								<c:forEach items="${command.serviceList}" var="objArray">
									<c:choose>
									<c:when	test="${userSession.getCurrent().getLanguageId() eq 1}">
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
						</div>
					</div>

					<div>
					<div class="text-center padding-bottom-20">
						<button type="button" class="btn btn-success" id="searchBPO">
							<spring:message code="search.data" text="Search" />
						</button>
						
						<button type="Reset" class="btn btn-warning" id="resetform"
							onclick="resetBPO(this)">
							<spring:message text="Reset" code="social.btn.reset" />
						</button>
					</div>
				</div>
					<!---------------- Search Criteria end---------------------------------->
					<div class="table-responsive clear">
						<table class="table table-striped table-bordered" id="bpoHome">
							<thead>
								<tr>

									<td align="left"><spring:message text="Select All"  code="soc.select.all"/>
										<form:checkbox path="bpoDto.checkBox"
											id="checkBoxIds"
											value="true" class="margin-left-10" /></td>
									<th align="center"><spring:message text="Beneficiary Name" code="soc.ben.name" /></th>
									<th align="center"><spring:message
											text="Beneficiary Number" code="soc.ben.num"/></th>
									<th align="center"><spring:message text="Amount" code="soc.ben.amt" /></th>
									<th align="center"><spring:message text="Bank Name" code="soc.bank.name" /></th>
									<th align="center"><spring:message text="IFSC Code" code="soc.ifsc.code"/></th>
									<th align="center"><spring:message text="Account Number" code="soc.ac.no"/></th>
								</tr>
							</thead>
						</table>
					</div>
					<div id="nextProcessId">
						<div class="form-group">
							<apptags:input labelCode="soc.Remark" path="bpoDto.remark"
								cssClass="" isMandatory="false"></apptags:input>
						</div>
						<div class="text-center padding-bottom-20">
							<button type="button" class="btn btn-success" id="SubmitId"
								onclick="saveBeneficiaryDetails(this)">
								<spring:message text="submit" code="social.btn.submit" />
							</button>
							<apptags:backButton url="BeneficiaryPaymentOrder.html"></apptags:backButton>
						</div>
					</div>
				</form:form>
			</div>

		</div>
	</div>
</div>