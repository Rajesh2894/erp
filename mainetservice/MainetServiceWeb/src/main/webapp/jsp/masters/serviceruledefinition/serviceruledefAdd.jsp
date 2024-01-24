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

<script type="text/javascript"
	src="js/masters/servicemaster/serviceruledefAdd.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
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
			<c:if test="${mode == 'create' }">
				<spring:message code='add.msg' />
			</c:if>
			<c:if test="${mode == 'update' }">
				<spring:message code='update.msg' />
			</c:if>
		</h2>
	</div>

	<div class="widget-content padding">
		<c:url value="${saveAction}" var="url_form_submit" />

		<form:form method="post" action="${url_form_submit}"
			class="form-horizontal" name="serviceMasterForm"
			id="serviceMasterForm" commandName="tbServicesMst">
			<input type="hidden" id="modeId" value="${mode}">
			<input type="hidden" id="orgId"
				value="${userSession.organisation.orgid}">
			<input type="hidden" name="activeInactive" id="active"
				value="${tbServicesMst.getSmServActive()}">

			<div
				class="warning-div error-div alert alert-danger alert-dismissible"
				id="errorDivScrutiny"></div>
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<c:if test="${mode eq 'update'}">
				<form:hidden path="smServiceId" />
			</c:if>

			<div class="form-group">
				<label class="col-sm-2 label-control required-control "><spring:message
						code='master.department' /></label>
				<div class="col-sm-4">
					<c:choose>
						<c:when test="${languageId eq 1}">

							<form:select path="cdmDeptId"
								class="form-control chosen-select-no-results" id="deptId"
								onchange="getchilddept()">
								<form:option value="">
									<spring:message code='master.selectDropDwn' />
								</form:option>
								<c:forEach items="${deptList}" var="deptData">
									<form:option value="${deptData.dpDeptid }"
										code="${deptData.dpDeptcode }">${deptData.dpDeptdesc }</form:option>
								</c:forEach>
							</form:select>
							<form:hidden path="cdmDeptId" id="deptIdHidden" />
						</c:when>
						<c:otherwise>
							<form:select path="cdmDeptId"
								class="form-control chosen-select-no-results" id="deptId"
								onchange="getchilddept()">
								<form:option value="">
									<spring:message code='master.selectDropDwn' />
								</form:option>
								<c:forEach items="${deptList}" var="deptData">
									<form:option value="${deptData.dpDeptid }"
										code="${deptData.dpDeptcode }">${deptData.dpNameMar }</form:option>
								</c:forEach>
							</form:select>
							<form:hidden path="cdmDeptId" id="deptIdHidden" />
						</c:otherwise>
					</c:choose>
				</div>

				<label class="col-sm-2 label-control"><spring:message
						text='Child Department' /></label>
				<div class="col-sm-4">
					<c:if test="${mode == 'update' }">

						<form:select path="cdmChildDeptId"
							class="form-control chosen-select-no-results" id="childdeptId">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${childDeptList}" var="childdeptData">
								<form:option value="${childdeptData.dpDeptid}">${childdeptData.dpDeptdesc}  </form:option>

							</c:forEach>
						</form:select>
					</c:if>

					<c:if test="${mode == 'create' }">
						<form:select path="cdmChildDeptId" id="cdmChildDeptId"
							class="form-control mandColorClass chosen-select-no-results">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${childdepartment}" var="childdeptData">
								<form:option value="${childdeptData.dpDeptid}">${childdeptData.dpDeptdesc}  </form:option>

							</c:forEach>
						</form:select>
					</c:if>
				</div>

			</div>


			<div class="form-group">
				<label class="col-sm-2 control-label required-control"><spring:message
						code='master.serviceNameEng' text="Service Name(English)" /></label>
				<div class="col-sm-10">
					<form:input path="smServiceName" class="form-control"
						id="serviceNameEng" maxlength="200" />
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label required-control"><spring:message
						code='master.serviceNameHindi' text="Service Name(Regional)" /></label>
				<div class="col-sm-10">
					<form:input path="smServiceNameMar" cssClass="form-control"
						id="serviceNameReg" maxlength="400"/>
				</div>

			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label required-control"><spring:message
						code='master.serviceShortDesc' /></label>
				<div class="col-sm-4">
					<form:input path="smShortdesc" cssClass="form-control"
						maxlength="5" id="smShortdesc" style="text-transform: uppercase;"
						onchange="checkShortCode();" />

				</div>

				<label class="col-sm-2 control-label required-control"><spring:message
						code='master.serviceActive' text="Service Active" /></label>
				<div class="col-sm-4">
					<%-- <input type="hidden" value="${transactionCounter}"
						id="transactionCount"> --%>
					<input type="hidden" value="${activenessLookupId}" id="activeness">

					<c:choose>
						<c:when test="${languageId eq 1}">
							<!-- onchange="checkForTransaction()"  remove transaction check -->
							<form:select path="smServActive" cssClass="form-control"
								id="smServActive">
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
								id="smServActive" onchange="checkForTransaction()">
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
				<label class="col-sm-2 control-label required-control"><spring:message
						code='master.chkListVerfAppl' /></label>
				<div class="col-sm-4">
					<c:choose>
						<c:when test="${languageId eq 1}">
							<form:select path="smChklstVerify"
								cssClass="form-control chosen-select-no-results"
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
							<form:select path="smChklstVerify"
								cssClass="form-control chosen-select-no-results"
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
					<form:input path="comN1" cssClass="form-control hasNumber"
						id="challanvalidty" maxlength="3"/>
				</div>
			</div>
			
			<!--User Story #92830 Need to have checklist approval flag (Applicable / Not Applicable) in service master UI  -->
			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message text="Checklist Approval Applicable"
						code='' /></label>
				<div class="col-sm-4">
						<form:select path="smCheckListReq" id=""
							class="chosen-select-no-results"> 
							<form:option value="Y">
								<spring:message code="" text="Applicable"></spring:message>
							</form:option>
							<form:option value="N">
								<spring:message code="" text="Not Applicable"></spring:message>
							</form:option>
						</form:select>
				</div>
			</div>
			<!--END #92830  -->
			
			
			
			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
						code='master.apprv' /></label>
				<div class="col-sm-4">
					<form:input path="smApprovalForm" cssClass="form-control"
						id="apprv" />
				</div>

				<label class="col-sm-2 control-label"><spring:message
						code='master.rejctn' /></label>
				<div class="col-sm-4">
					<a> </a>
					<form:input path="smRejectionForm" cssClass="form-control"
						id="rejctn" maxlength="200"/>
				</div>
			</div>

			<div class="form-group">

				<label class="col-sm-2 control-label required-control"><spring:message
						code='service.print.responsibility' /></label>
				<div class="col-sm-4">
					<c:choose>
						<c:when test="${languageId eq 1}">
							<form:select cssClass="form-control chosen-select-no-results"
								path="smPrintRespons" id="smPrintRespons"
								onchange="setPrintResponseCode(this)"
								disabled="${empty transactionCount || transactionCount eq 0 ? false:true}">
								<form:option value="">
									<spring:message code='master.selectDropDwn' />
								</form:option>
								<c:forEach items="${prnPrefixData}" var="printResponsibility">
									<form:option value="${printResponsibility.lookUpId }"
										code="${printResponsibility.lookUpCode }">${printResponsibility.descLangFirst }</form:option>
								</c:forEach>
							</form:select>
						</c:when>
						<c:otherwise>
							<form:select cssClass="form-control chosen-select-no-results"
								path="smPrintRespons" id="smPrintRespons"
								onchange="setPrintResponseCode(this)"
								disabled="${empty transactionCount || transactionCount eq 0 ? false:true}">
								<form:option value="">
									<spring:message code='master.selectDropDwn' />
								</form:option>
								<c:forEach items="${prnPrefixData}" var="printResponsibility">
									<form:option value="${printResponsibility.lookUpId }"
										code="${printResponsibility.lookUpCode }">${printResponsibility.descLangSecond }</form:option>
								</c:forEach>
							</form:select>
						</c:otherwise>
					</c:choose>
					<form:hidden path="smPrintResponsCode" id="smPrintResponsCode" />
					<form:input type="hidden" path="ipMac" />
				</div>

				<label class="col-sm-2 control-label"><spring:message
						code='master.remark' /></label>
				<div class="col-sm-4">
					<form:textarea path="smServiceNote" cssClass="form-control"
						id="remark" />
				</div>

			</div>


			<div class="form-group">
				<label class="col-sm-2 control-label required-control"><spring:message
						code='master.feeSchd' /></label>

				<div class="col-sm-4 radio radio-inline feeScheduleAlign">
					<label> <form:radiobutton path="smFeesSchedule" value="0"
							onclick="toggleChargeFlag(this)" id="FreeService"
							class="radio-check" onchange="closeOutErrBox()" /> <spring:message
							code='master.freeServ' />
					</label> <label> <form:radiobutton path="smFeesSchedule" value="1"
							onclick="toggleChargeFlag(this)" id="ChargeableService"
							class="radio-check" onchange="closeOutErrBox()" /> <spring:message
							code='master.chargable' />
					</label>
				</div>

				<div id="iffree">
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

				<label class="col-sm-2 control-label required-control"><spring:message
						code="master.bpmprocess" text="BPM Process" /> </label>
				<div class="col-sm-4">
					<form:select path="smProcessId"
						cssClass="form-control chosen-select-no-results" id="smProcessId">
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
				<button type="button" id="save" class="btn btn-success btn-submit"
					onclick="submitForm(this);return false;">
					<spring:message code='master.save' />
				</button>
				<c:if test="${mode == 'create' }">
					<button type="reset" id="reset" class="btn btn-warning"
						onclick="resetForm()">
						<spring:message code="reset.msg" />
					</button>
				</c:if>
				<button type="button" class="btn btn-danger"
					onclick="window.location.href='ServiceMaster.html'" id="backBtn">
					<spring:message code='back.msg' />
				</button>
			</div>

		</form:form>
	</div>
</div>

