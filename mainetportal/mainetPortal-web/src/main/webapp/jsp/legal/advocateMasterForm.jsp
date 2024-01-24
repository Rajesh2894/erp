<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/mainet/validation.js"></script>
<script src="js/legal/advocateMaster.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.min.css" 
  rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.min.js"></script>
<script src="js/mainet/jQueryMaskedInputPlugin.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- lgl.advocateMaster.form.heading -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="lgl.advocatemaster" text="Advocate Master" />
			</h2>
			<apptags:helpDoc url="AdvocateMaster.html" />
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">

				<span><spring:message code="legal.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="legal.mand.field"
						text="is mandatory"></spring:message></span>
			</div>
			<form:form action="AdvocateMaster.html" name="AdvocateMaster"
				id="AdvocateMasterForm" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="envFlag" id="envFlag"/>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="orgId"><spring:message
							code="care.organization" text="Organization" /></label>
					<div class="col-sm-4">
						<form:select path="advocateMasterDTO.orgid"
							cssClass="form-control chosen-select-no-results" id="orgId">
							<form:option value="0">
								<spring:message code='Select' text="Select" />
							</form:option>
							<c:forEach items="${command.org}" var="list">
								<form:option value="${list.orgid}">${list.ONlsOrgname}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<apptags:input labelCode="lgl.advocate.firstName"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						path="advocateMasterDTO.advFirstNm" isMandatory="true"
						cssClass="hasNameClass form-control hasNoSpace" maxlegnth="100"></apptags:input>
					<apptags:input labelCode="lgl.advocate.middleName"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						path="advocateMasterDTO.advMiddleNm" isMandatory="false"
						cssClass="hasNameClass form-control hasNoSpace" maxlegnth="100"></apptags:input>

				</div>

				<div class="form-group">
					<apptags:input labelCode="lgl.advocate.lastName"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						path="advocateMasterDTO.advLastNm" isMandatory="true"
						cssClass="hasNameClass form-control hasNoSpace" maxlegnth="100"></apptags:input>
					<apptags:input labelCode="lgl.barcouncil.no"
						path="advocateMasterDTO.adv_barCouncilNo" isMandatory="true"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						cssClass=" form-control hasNoSpace" maxlegnth="16"></apptags:input>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 " for="Census"><spring:message
							code="lgl.gender" text="Gender" /></label>
					<c:set var="baseLookupCode" value="GEN" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="advocateMasterDTO.advGen" isMandatory="true"
						cssClass="form-control" hasId="true"
						disabled="${command.saveMode eq 'V' ? true : false }"
						selectOptionLabelCode="selectdropdown" />

					<label for="text-1" class="control-label col-sm-2"><spring:message
							code="lgl.dg.lDOB" text="Date Of Birth" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input class="form-control datepicker" id="advDob"
								path="advocateMasterDTO.advDob" maxlength="10"
								disabled="${command.saveMode eq 'V' ? true : false }"
								onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')" />
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>

						</div>
					</div>

				</div>

				<div class="form-group">
					<apptags:input labelCode="lgl.advocate.mobileNumber"
						isMandatory="true" path="advocateMasterDTO.advMobile"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						cssClass="hasMobileNo hasNoSpace" maxlegnth="10"></apptags:input>

					<apptags:input labelCode="lgl.emailid"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						path="advocateMasterDTO.advEmail" isMandatory="true"
						cssClass="hasemailclass hasNoSpace" dataRuleEmail="true">
					</apptags:input>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="pannumber"> <spring:message
							code="lgl.panNumber" /></label>
					<div class="col-sm-4">
						<form:input type="text" name="PAN Number" id="advPanno"
							path="advocateMasterDTO.advPanno"
							disabled="${command.saveMode eq 'V' ? true : false }"
							cssClass="form-control hasNoSpace" maxLength="10"
							onchange="fnValidatePAN(this)" />
					</div>

					<apptags:input labelCode="lgl.aadharNumber"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						path="advocateMasterDTO.advUid" cssClass="hasAadharNo hasNoSpace">
					</apptags:input>

				</div>


				<div class="form-group">

					<apptags:textArea labelCode="lgl.advocate.advocateAddress"
						isMandatory="true" path="advocateMasterDTO.advAddress"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						maxlegnth="200" cssClass="alpaSpecial"></apptags:textArea>

					<apptags:textArea labelCode="lgl.advocate.advocateOfficeAddress"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						isMandatory="false" path="advocateMasterDTO.advOfficeAddress"
						maxlegnth="200" cssClass="alpaSpecial"></apptags:textArea>
				</div>

				<div class="form-group">

					<apptags:input labelCode="lgl.advocate.experience"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						path="advocateMasterDTO.advExperience" maxlegnth="5"
						cssClass="decimal22 hasNoSpace"></apptags:input>

					<apptags:input labelCode="lgl.advChamberNo"
						path="advocateMasterDTO.advChamberNo"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						cssClass="hasMobileNo hasNoSpace"></apptags:input>

				</div>
				<%-- 
				<div class="form-group">

					<label for="advFeeType" class="col-sm-2 control-label"><spring:message
							code="lgl.advocate.fee.type" text="Fee Type" /></label>


					<apptags:lookupField items="${command.getLevelData('FET')}"
						path="advocateMasterDTO.advFeeType"
						cssClass="form-control mandColorClass hasNameClass"
						selectOptionLabelCode="Select" hasId="true" isMandatory="false" />

					<label for="text-1" class="col-sm-2 control-label "> <spring:message
							code="lgl.advocate.fee.amount" text="Fee Amount"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input cssClass="form-control" id="advFeeAmt"
							path="advocateMasterDTO.advFeeAmt"
							onkeypress="return hasAmount(event,this,12,2)" />
					</div>
		</div> --%>

				<%-- <div class="form-group">
                    <!--   120797 prefix changed from ADT to AVT bcoz of ADT is used for AuditType in TSCL Proj -->
					<label for="" class="col-sm-2 control-label"><spring:message
							code="lgl.advocate.type" text="Advocate Type" /></label>
					<apptags:lookupField items="${command.getLevelData('AVT')}"
						path="advocateMasterDTO.adv_advocateTypeId"
						cssClass="form-control  " selectOptionLabelCode="Select"
						hasId="true" isMandatory="false" />

				</div> --%>

				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-target="#a1" data-toggle="collapse" class="collapsed"
							data-parent="#accordion_single_collapse" href="#a1"><spring:message
								code="lgl.educationDetails" text="Education Details" /></a>
					</h4>
				</div>


				<div id="a1" class="panel-collapse collapse in">
					<div class="panel-body">
						<table class="table table-bordered  table-condensed margin-bottom-10"
							id="educationDetails" summary="Education Details">
							<c:set var="j" value="0" scope="page"></c:set>
							<thead>
								<tr>
									<th width="5"><spring:message code="lgl.srno" 	text="Sr. No." /></th>
									<th width="15"><spring:message	code="lgl.adv.qualificationCourse" text="Qualification Course" /></th>

									<th width="15"><spring:message code="lgl.adv.instituteState" text="Institute State" /></th>

									<th width="15"><spring:message code="lgl.adv.boardUniversity" text="Board University" /></th>

									<th width="10"><spring:message code="lgl.adv.result" text="Result" /></th>

									<th width="10"><spring:message code="lgl.adv.passingYear"	text="Passing Year" /></th>

									<th width="10"><spring:message code="lgl.adv.percentage" text="Percentage" /></th>
									<th width="20"><spring:message code="lgl.action" text="Action" /></th>
								</tr>
							</thead>

							<tbody>
								<c:choose>
									<c:when test="${not empty command.advocateMasterDTO.advEducationDetailDTOList}">
										<c:set var="e" value="0" scope="page"></c:set>
										<c:forEach items="${command.advocateMasterDTO.advEducationDetailDTOList}"
											var="data" varStatus="index">
											<td align="center"><form:input path=""
													cssClass="form-control mandColorClass" id="sequence${e}"
													value="${e+1}" disabled="true" /></td>

											<td><form:input
													path="advocateMasterDTO.advEducationDetailDTOList[${e}].qualificationCourse"
													cssClass="form-control  hasNameClass"
													id="qualificationCourse${e}" maxlength="500"
													disabled="${command.saveMode eq 'V' ? true : false }" /></td>

											<td><form:input
													path="advocateMasterDTO.advEducationDetailDTOList[${e}].instituteState"
													cssClass="form-control" id="instituteState${e}"
													maxlength="500"
													disabled="${command.saveMode eq 'V' ? true : false }" /></td>

											<td><form:input
													path="advocateMasterDTO.advEducationDetailDTOList[${e}].boardUniversity"
													cssClass="form-control" id="boardUniversity${e}"
													maxlength="500"
													disabled="${command.saveMode eq 'V' ? true : false }" /></td>

											<td><form:input
													path="advocateMasterDTO.advEducationDetailDTOList[${e}].result"
													cssClass="form-control" id="result${e}" maxlength="50"
													disabled="${command.saveMode eq 'V' ? true : false }" /></td>


											<td><form:input
													path="advocateMasterDTO.advEducationDetailDTOList[${e}].passingYear"
													cssClass="hasNumber hasNoSpace  form-control" id="passingYear${e}"
													maxlength="4" placeholder="YYYY"
													disabled="${command.saveMode eq 'V' ? true : false }" /></td>

											<td><form:input
													path="advocateMasterDTO.advEducationDetailDTOList[${e}].percentage"
													cssClass="hasNumber form-control" id="percentage${e}"
													maxlength="3"
													onkeypress="return hasAmount(event, this, 15, 2)"
													disabled="${command.saveMode eq 'V' ? true : false }" /></td>

											<td style="width: 7%;"><a href="#a2"
												data-toggle="tooltip" data-placement="top"
												class="btn btn-blue-2  btn-sm" data-original-title="Add"
												onclick="addEntryData();"><strong class="fa fa-plus"></strong><span
													class="hide"><spring:message code="lgl.add"
															text="Add" /></span></a> <a href="#a3" data-toggle="tooltip"
												data-placement="top" class="btn btn-danger btn-sm"
												data-original-title="Delete"
												onclick="deleteEntry($(this),'removedIds');"> <strong
													class="fa fa-trash"></strong> <span class="hide"><spring:message
															code="lgl.delete" text="Delete" /></span>
											</a></td>
											</tr>
											<c:set var="e" value="${e + 1}" scope="page" />
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="educationDetailsClass">
											<!-- advEducationDetailDTOList -->

											<td align="center"><form:input path=""
													cssClass="form-control mandColorClass" id="sequence${j}"
													value="${j+1}" disabled="true" /></td>

											<td><form:input
													path="advocateMasterDTO.advEducationDetailDTOList[${j}].qualificationCourse"
													cssClass="form-control  hasNameClass"
													id="qualificationCourse${j}" maxlength="500"
													disabled="${command.saveMode eq 'V' ? true : false }" /></td>

											<td><form:input
													path="advocateMasterDTO.advEducationDetailDTOList[${j}].instituteState"
													cssClass="form-control" id="instituteState${j}"
													maxlength="500"
													disabled="${command.saveMode eq 'V' ? true : false }" /></td>

											<td><form:input
													path="advocateMasterDTO.advEducationDetailDTOList[${j}].boardUniversity"
													cssClass="form-control" id="boardUniversity${j}"
													maxlength="500"
													disabled="${command.saveMode eq 'V' ? true : false }" /></td>

											<td><form:input
													path="advocateMasterDTO.advEducationDetailDTOList[${j}].result"
													cssClass="form-control" id="result${j}" maxlength="50"
													disabled="${command.saveMode eq 'V' ? true : false }" /></td>


											<td><form:input
													path="advocateMasterDTO.advEducationDetailDTOList[${j}].passingYear"
													cssClass="hasNumber hasNoSpace  form-control" id="passingYear${j}"
													maxlength="4" placeholder="YYYY"
													disabled="${command.saveMode eq 'V' ? true : false }" /></td>

											<td><form:input
													path="advocateMasterDTO.advEducationDetailDTOList[${j}].percentage"
													cssClass="hasNumber  form-control" id="percentage${j}"
													maxlength="3"
													onkeypress="return hasAmount(event, this, 15, 2)"
													disabled="${command.saveMode eq 'V' ? true : false }" /></td>


											<td style="width: 8%;">
											<a href="#a2" data-toggle="tooltip" data-placement="top"
												class="btn btn-blue-2  btn-sm" data-original-title="Add"
												onclick="addEntryData();"><strong class="fa fa-plus"></strong><span
													class="hide"><spring:message code="lgl.add" text="Add" /></span></a> 
													
											<a href="#a3" data-toggle="tooltip" data-placement="top" class="btn btn-danger btn-sm"
												data-original-title="Delete" onclick="deleteEntry($(this),'removedIds');"> <strong
													class="fa fa-trash"></strong> <span class="hide"><spring:message
															code="lgl.delete" text="Delete" /></span></a>
											</td>

										</tr>
									</c:otherwise>
								</c:choose>
								<c:set var="j" value="${j + 1}" scope="page" />

							</tbody>
						</table>
					</div>
				</div>



				<!-- lgl.advocate.proceed -->
				<div class="text-center padding-bottom-10">
					<c:if test="${command.saveMode eq 'C'}">
						<button type="submit" class="btn btn-success btn-submit"
							onclick="Proceed(this);return false;">
							<spring:message code="lgl.advocate.proceed" text="Proceed"></spring:message>
						</button>

						<button type="Reset" id="reset" class="btn btn-warning" onclick="reset();">
							<spring:message code="lgl.reset" text="Reset"></spring:message>
						</button>
					</c:if>

					<!-- lgl.cancel -->
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Back" onclick="backPage();">
						<spring:message code="lgl.back" text="Back"></spring:message>
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
</body>
</html>