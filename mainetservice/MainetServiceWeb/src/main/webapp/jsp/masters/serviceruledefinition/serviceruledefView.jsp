<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
	$(document).ready(
			function() {

				var smChecked = $('input[name=smFeesSchedule]:checked',
						'#serviceMasterViewForm').val();
				if (smChecked == 0) {
					$('#ifchargeable').hide();
				} else {
					$('#ifchargeable').show();
				}
				$('#serviceMasterViewForm input').prop('disabled', true);
				$('#serviceMasterViewForm select').prop('disabled', true);
				$('#serviceMasterViewForm textarea').prop('disabled', true);
				$('#serviceMasterViewForm checkbox').prop('disabled', true);
				$('#backBtn').prop('disabled', false);

			});
</script>
<style>
#serviceMasterForm .feeScheduleAlign {
    margin-top: 3px !important;
}
#serviceMasterForm .smSwitchAlign {
    margin-top: 6.5px !important;
}

#serviceMasterForm .smAppliChargeAlign {
    margin-top: 6px !important;
}
</style>

<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code='service.master' />
			<spring:message code='view.msg' />
		</h2>
	</div>

	<div class="widget-content padding">
		<form:form method="post" action="#" class="form-horizontal"
			name="serviceMasterViewForm" id="serviceMasterViewForm">


			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
						code='master.department' /></label>
				<div class="col-sm-4">
					<form:select path="cdmDeptId" class="form-control" id="deptId">
						<form:option value="">Select</form:option>
						<c:forEach items="${deptList}" var="deptData">
							<form:option value="${deptData.dpDeptid }"
								code="${deptData.dpDeptcode }">${deptData.dpDeptdesc }</form:option>
						</c:forEach>
					</form:select>
				</div>



				<label class="col-sm-2 label-control"><spring:message
						text='Child Department' /></label>
				<div class="col-sm-4">
					<form:select path="cdmChildDeptId"
						class="form-control chosen-select-no-results" id="childdeptId">
						<form:option value="">
							<spring:message code='master.selectDropDwn' />
						</form:option>
						<c:forEach items="${childDeptList}" var="childdeptData">
							<form:option value="${childdeptData.dpDeptid}">${childdeptData.dpDeptdesc}  </form:option>

						</c:forEach>
					</form:select>

				</div>

			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
						code='master.serviceNameEng' /></label>
				<div class="col-sm-10">
					<form:input path="smServiceName" class="form-control "
						id="serviceNameEng" />
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
						code='master.serviceNameHindi' /></label>
				<div class="col-sm-10">
					<form:input path="smServiceNameMar" cssClass="form-control "
						id="serviceNameReg" />
				</div>

			</div>

			<div class="form-group">


				<label class="col-sm-2 control-label"><spring:message
						code='master.serviceShortDesc' /></label>
				<div class="col-sm-4">
					<form:input path="smShortdesc" class="form-control" />
				</div>

				<label class="col-sm-2 control-label"><spring:message
						code='master.serviceActive' /></label>
				<div class="col-sm-4">

					<c:choose>
						<c:when test="${languageId eq 1}">
							<form:select path="smServActive" cssClass="form-control"
								id="smServActive"
								disabled="${empty transactionCount || transactionCount eq 0 ? false:true}">
								<form:option value="">
									<spring:message code='master.selectDropDwn' />
								</form:option>
								<c:forEach items="${acnPrefixList}" var="acnPrefixData">
									<form:option value="${acnPrefixData.lookUpId }"
										code="${acnPrefixData.lookUpCode }">${acnPrefixData.descLangFirst }</form:option>
								</c:forEach>
							</form:select>
						</c:when>
						<c:otherwise>
							<form:select path="smServActive" cssClass="form-control"
								id="smServActive"
								disabled="${empty transactionCount || transactionCount eq 0 ? false:true}">
								<form:option value="">
									<spring:message code='master.selectDropDwn' />
								</form:option>
								<c:forEach items="${acnPrefixList}" var="acnPrefixData">
									<form:option value="${acnPrefixData.lookUpId }"
										code="${acnPrefixData.lookUpCode }">${acnPrefixData.descLangSecond }</form:option>
								</c:forEach>
							</form:select>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
						code='master.chkListVerfAppl' /></label>
				<div class="col-sm-4">
					<c:choose>
						<c:when test="${languageId eq 1}">
							<form:select path="smChklstVerify" cssClass="form-control"
								id="smChklstVerify" onchange="toggleDays(this)">
								<form:option value="">
									<spring:message code='master.selectDropDwn' />
								</form:option>
								<c:forEach items="${aplPrefixData}" var="chkListVerification">
									<form:option value="${chkListVerification.lookUpId }"
										code="${chkListVerification.lookUpCode }">${chkListVerification.descLangFirst }</form:option>
								</c:forEach>
							</form:select>
						</c:when>
						<c:otherwise>
							<form:select path="smChklstVerify" cssClass="form-control"
								id="smChklstVerify" onchange="toggleDays(this)">
								<form:option value="">
									<spring:message code='master.selectDropDwn' />
								</form:option>
								<c:forEach items="${aplPrefixData}" var="chkListVerification">
									<form:option value="${chkListVerification.lookUpId }"
										code="${chkListVerification.lookUpCode }">${chkListVerification.descLangSecond }</form:option>
								</c:forEach>
							</form:select>
							<span class="mand">*</span>
						</c:otherwise>
					</c:choose>

				</div>
				<label class="col-sm-2 control-label"><spring:message
						code='master.challanvalidty' /></label>
				<div class="col-sm-4">
					<form:input path="comN1" cssClass="form-control"
						id="challanvalidty" />
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
						code='master.apprv' /></label>
				<div class="col-sm-4">
					<form:input path="smApprovalForm" class="form-control" />
				</div>
				<label class="col-sm-2 control-label"><spring:message
						code='master.rejctn' /></label>
				<div class="col-sm-4">
					<form:input path="smRejectionForm" class="form-control" />
				</div>
			</div>

			<div class="form-group ">

				<label class="col-sm-2 control-label"><spring:message
						code='service.print.responsibility' /></label>

				<div class="col-sm-4">
					<form:select path="smPrintRespons" id="smPrintRespons"
						class="form-control">
						<form:option value="">
							<spring:message code="contract.label.select" text="Select" />
						</form:option>
						<c:forEach items="${prnPrefixData}" var="printResponsibility">
							<form:option value="${printResponsibility.lookUpId }"
								code="${printResponsibility.lookUpCode }">${printResponsibility.lookUpDesc }</form:option>
						</c:forEach>
					</form:select>
				</div>


				<label class="col-sm-2 control-label"><spring:message
						code='master.remark' /></label>
				<div class="col-sm-4">
					<form:textarea path="smServiceNote" class="form-control" />
				</div>
			</div>


			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
						code='master.feeSchd' /></label>
				<div class="col-sm-4 radio radio-inline feeScheduleAlign">
					<label> <form:radiobutton path="smFeesSchedule" value="0"
							id="FreeService" name="smFeesSchedule" /> <spring:message
							code='master.freeServ' /></label> <label> <form:radiobutton
							path="smFeesSchedule" value="1" id="ChargeableService"
							name="smFeesSchedule" /> <spring:message code='master.chargable' /></label>
				</div>

				<div id="ifchargeable">
					<label class="col-sm-2 control-label"><spring:message
							code='master.chargeableServ' /></label>
					<div class="col-sm-4 smAppliChargeAlign">
						<label class="checkbox-inline"> <form:checkbox
								path="smAppliChargeFlag" value="Y" id="smAppliChargeFlag" /> <spring:message
								code='master.appTime' />
						</label> <label class="checkbox-inline"> <form:checkbox
								path="smScrutinyChargeFlag" value="Y" id="smScrutinyChargeFlag" />
							<spring:message code='master.atScrutinyTm' />
						</label>
					</div>

				</div>

			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
						code='master.selectApplicable' /></label>
				<div class="col-sm-4 smSwitchAlign">
					<label class="checkbox-inline"><form:checkbox
							path="smSwitch" value="Y" /> <spring:message code='master.print' /></label><br>
					<label class="checkbox-inline"><form:checkbox path="comV1"
							value="Y" id="actualservice" /> <spring:message
							code='master.actualService' /></label> <label class="checkbox-inline"><form:checkbox
							path="comV2" value="Y" /> <spring:message
							code='master.printDigitSign' /></label> <label class="checkbox-inline"><form:checkbox
							path="smScrutinyApplicableFlag" value="Y" /> <spring:message
							code='master.scrutinyApplFlag' /></label><br>
				</div>

				<label class="col-sm-2 control-label"><spring:message
						code="master.bpmprocess" text="BPM Process" /> </label>
				<div class="col-sm-4">
					<form:select path="smProcessId" cssClass="form-control"
						id="smServActive" disabled="true">
						<form:option value="">
							<spring:message code='master.selectDropDwn' />
						</form:option>
						<c:forEach items="${bptPrefixList}" var="bptPrefix">
							<form:option value="${bptPrefix.lookUpId }"
								code="${bptPrefix.lookUpCode }">${bptPrefix.descLangFirst }</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
						code="workflow.sla" text="SLA" /> </label>
				<div class="col-sm-4">
					<form:input path="smServiceDuration"
						class="form-control hasNumber text-right" id="smServiceDuration"
						maxlength="5" />
				</div>

				<label class="col-sm-2 control-label"> <spring:message
						code="workflow.units" text="Units" /></label>
				<div class="col-sm-4">
					<form:select id="smDurationUnit" path="smDurationUnit"
						cssClass="form-control">
						<form:option value="0">
							<spring:message code="master.selectDropDwn" text="Select" />
						</form:option>
						<c:forEach items="${utsPrefixList}" var="uts">
							<form:option value="${uts.lookUpId}">${uts.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="text-center padding-top-10">
				<input type="button" class="btn btn-danger" value="Back"
					onclick="window.location.href='ServiceMaster.html'" id="backBtn" />
			</div>

		</form:form>
	</div>
</div>

