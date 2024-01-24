
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<!-- <script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script> -->
<!-- <script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script> -->
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script type="text/javascript" src="js/common/sequenceConfigMaster.js"></script>

<!-- <script>
	$("select").chosen();
</script -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>Sequence Configuration Master</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="SequenceConfigrationMaster.html" method="post"
				class="form-horizontal" name="sequenceConfigMaster"
				id="sequenceConfigMaster">

				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- ----------------- this div below is used to display the error message on the top of the page--------------------------->


				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<h4 class="margin-top-0">
					<spring:message code="common.sequenceconfig.seqinfo"
						text="Sequence Information" />
				</h4>



				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="common.sequenceconfig.Name" text="Sequence Name" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCodeSQN" value="SQN" />
						<form:select path="configMasterDTO.seqName" class="form-control"
							id="seqName"
							disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
							<form:option value="0">Select</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCodeSQN)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}">${lookUp.lookUpCode}</form:option>
							</c:forEach>
						</form:select>

					</div>



					<c:set var="baseLookupCodeSEC" value="SEC" />
					<label class="col-sm-2 control-label required-control"><spring:message
							code="common.sequenceconfig.category" text="Category" /></label>


					<apptags:lookupField
						items="${command.getLevelData(baseLookupCodeSEC)}"
						path="configMasterDTO.catId" changeHandler="categoryChange() "
						cssClass="form-control" hasChildLookup="false" hasId="true"
						showAll="false" selectOptionLabelCode="Select Category"
						disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' ? true : false}" />

				</div>
				<!-- ; document.getElementById('seqFrmNo').disabled = true; -->

				<div class="form-group">

					<label for="text-1" class="col-sm-2 control-label required-control">Department
						Name </label>
					<div class="col-sm-4">

						<form:select path="configMasterDTO.deptId"
							class="form-control mandColorClass chosen-select-no-results"
							label="Select" id="deptId" onchange="searchPrefix()"
							disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
							<form:option value="" selected="true">Select Department Name</form:option>
							<c:forEach items="${command.departmentList}" var="dept">
								<form:option value="${dept.dpDeptid}" code="${dept.dpDeptcode}">${dept.dpDeptdesc}</form:option>
							</c:forEach>

						</form:select>
					</div>




					<label class="col-sm-2 control-label required-control"><spring:message
							code="common.sequenceconfig.sequencetype" text="Sequence Type" /></label>
					<div class="col-sm-4	">


						<c:set var="baseLookupCodeSQT" value="SQT" />
						<form:select path="configMasterDTO.seqType" class="form-control"
							id="seqType"
							disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
							<form:option value="0">Select</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCodeSQT)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>

					</div>
				</div>


				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="common.sequenceconfig.separator" text="Separator" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCodeSEP" value="SEP" />
						<form:select path="configMasterDTO.seqSep" class="form-control"
							id="seqSep"
							disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
							<form:option value="0">Select</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCodeSEP)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>

					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="common.sequenceconfig.seqlen" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control hasNumber"
							path="configMasterDTO.seqLength" id="seqLength" maxlength="2"
							autocomplete="false"
							disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
					</div>

				</div>


				<div class="form-group">
					<label for="text-1" class="control-label col-sm-2"><spring:message
							code="common.sequenceconfig.custseq" text="Custom Sequence" /></label>
					<div class="col-sm-4">
						<label class="radio-inline"> <form:radiobutton
								disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"
								id="custSeqY" path="configMasterDTO.custSeq" value="Y"
								checked="true" onclick="validateCustSeq()" /> <spring:message
								code="" text="Yes" /> <!--  validateCustSeq() -->
						</label> <label class="radio-inline "> <form:radiobutton
								disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"
								id="custSeqN" path="configMasterDTO.custSeq" value="N"
								onclick="document.getElementById('seqFrmNo').disabled = true;" />
							<spring:message code="" text="No" />
						</label>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="common.sequenceconfig.seqstartnum" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control hasNumber"
							path="configMasterDTO.seqFrmNo" id="seqFrmNo" maxlength="12"
							autocomplete="false"
							disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
					</div>

				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="common.sequenceconfig.fromdate" text="Start Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="configMasterDTO.fromDate" id="fromDate"
								class="form-control mandColorClass datepicker" readonly="true"
								disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"
								onchange="validateDate()" />
							<label class="input-group-addon"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"></label>
						</div>
					</div>
					<label class="col-sm-2 control-label "><spring:message
							code="common.sequenceconfig.duedate" text="End Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="configMasterDTO.endDate" id="endDate"
								class="form-control mandColorClass datepickerEndDate"
								readonly="true"
								disabled="${command.saveMode eq 'V' || command.saveMode eq  'E' || command.saveMode eq  'C'? true : false}"
								onchange="validateDate()" />
							<label class="input-group-addon"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"></label>
						</div>
					</div>
				</div>



				<div class=" widget-content padding form-group">
					<h4>
						<spring:message code="common.sequenceconfig.seqfactorder"
							text="Sequence Factor Order" />
					</h4>
				</div>


				<div class="table-responsive margin-left-0">
					<c:set var="d" value="0"></c:set>
					<table class="table table-striped table-condensed table-bordered"
						id="seqFactorOrder">

						<thead>
							<tr>
								<th class="col-sm-2" style="text-align: left"><spring:message
										code="common.sequenceconfig.factorname" text="Factor Name"></spring:message></th>
								<th class="col-sm-4" style="text-align: left"><spring:message
										code="common.sequenceconfig.prefix" text="Prefix"></spring:message></th>
								<th class="col-sm-4" style="text-align: left"><spring:message
										code="common.sequenceconfig.order" text="Order"></spring:message></th>
							</tr>
						</thead>

						<tbody>
							<tr>

								<th class="col-sm-2" style="text-align: left"><spring:message
										code="common.sequenceconfig.statecode" text="State Code"></spring:message></th>

								<td><c:set var="baseLookupCodeSTT" value="STT" /> <form:select
										path="configMasterDTO.configDetDTOs[${d}].prefixId"
										class="form-control" id="prefixId1"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
										<form:option value="">Select</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCodeSTT)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpCode}">${lookUp.lookUpDesc} </form:option>
										</c:forEach>
									</form:select></td>

								<td><form:input type="text" class="form-control hasNumber"
										path="configMasterDTO.configDetDTOs[${d}].seqOrder"
										id="seqOrder1" maxlength="2" autocomplete="off"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
								</td>
							</tr>

							<tr>

								<th class="col-sm-2" style="text-align: left"><spring:message
										code="common.sequenceconfig.divcode" text="Division Code"></spring:message></th>

								<td><c:set var="baseLookupCodeDVN" value="DVN" /> <form:select
										path="configMasterDTO.configDetDTOs[${d+1}].prefixId"
										class="form-control" id="prefixId2"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
										<form:option value="">Select</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCodeDVN)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpCode}">${lookUp.lookUpDesc} </form:option>
										</c:forEach>
									</form:select></td>

								<td><form:input type="text" class="form-control hasNumber"
										path="configMasterDTO.configDetDTOs[${d+1}].seqOrder"
										id="seqOrder2" maxlength="2" autocomplete="off"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' ? true : false}"></form:input>
								</td>

							</tr>

							<tr>
								<th class="col-sm-2" style="text-align: left"><spring:message
										code="common.sequenceconfig.districtcode" text="District Code"></spring:message></th>

								<td><c:set var="baseLookupCodeDIS" value="DIS" /> <form:select
										path="configMasterDTO.configDetDTOs[${d+2}].prefixId"
										class="form-control" id="prefixId3"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
										<form:option value="">Select</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCodeDIS)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>

								<td><form:input type="text" class="form-control hasNumber"
										path="configMasterDTO.configDetDTOs[${d+2}].seqOrder"
										id="seqOrder3" maxlength="2" autocomplete="off"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>

								</td>

							</tr>

							<tr>
								<th class="col-sm-2" style="text-align: left"><spring:message
										code="common.sequenceconfig.ulbcode" text="ULB Code"></spring:message></th>

								<td><form:select
										path="configMasterDTO.configDetDTOs[${d+3}].prefixId"
										class="form-control" id="prefixId4"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'  ? true : false}">
										<form:option value="">Select</form:option>
										<form:option value="Y">Yes</form:option>
										<form:option value="N">No</form:option>
									</form:select></td>

								<td><form:input type="text" class="form-control hasNumber"
										path="configMasterDTO.configDetDTOs[${d+3}].seqOrder"
										id="seqOrder4" maxlength="2" autocomplete="off"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
								</td>

							</tr>

							<tr>
								<th class="col-sm-2" style="text-align: left"><spring:message
										code="common.sequenceconfig.servvicecode" text="State Code"></spring:message></th>

								<td><form:select
										path="configMasterDTO.configDetDTOs[${d+4}].prefixId"
										class="form-control" id="prefixId5"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
										<form:option value="">Select</form:option>
										<form:option value="Y">Yes</form:option>
										<form:option value="N">No</form:option>
									</form:select></td>

								<td><form:input type="text" class="form-control hasNumber"
										path="configMasterDTO.configDetDTOs[${d+4}].seqOrder"
										id="seqOrder5" maxlength="2" autocomplete="off"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
								</td>

							</tr>

							<tr>
								<th class="col-sm-2" style="text-align: left"><spring:message
										code="common.sequenceconfig.businessunitcode"
										text="Business Code"></spring:message></th>

								<td><form:select
										path="configMasterDTO.configDetDTOs[${d+5}].prefixId"
										class="form-control" id="prefixId6"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
										<form:option value="">Select</form:option>
										<form:option value="Y">Yes</form:option>
										<form:option value="N">No</form:option>
									</form:select></td>

								<td><form:input type="text" class="form-control hasNumber"
										path="configMasterDTO.configDetDTOs[${d+5}].seqOrder"
										id="seqOrder6" maxlength="2" autocomplete="off"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
								</td>

							</tr>

							<tr>
								<th class="col-sm-2" style="text-align: left"><spring:message
										code="common.sequenceconfig.deptcode" text="Department Code"></spring:message></th>

								<td><c:set var="baseLookupCodeDIS" value="DIS" /> <form:select
										path="configMasterDTO.configDetDTOs[${d+6}].prefixId"
										class="form-control" id="prefixId7"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
										<form:option value="">Select</form:option>
										<form:option value="Y">Yes</form:option>
										<form:option value="N">No</form:option>
									</form:select></td>

								<td><form:input type="text" class="form-control hasNumber"
										path="configMasterDTO.configDetDTOs[${d+6}].seqOrder"
										id="seqOrder7" maxlength="2" autocomplete="off"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
								</td>

							</tr>

							<tr>
								<th class="col-sm-2" style="text-align: left"><spring:message
										code="common.sequenceconfig.deptprefix" text="Select Prefix"></spring:message></th>

								<td><c:choose>
										<c:when
											test="${command.saveMode eq 'V' || command.saveMode eq 'E'}">
											<form:input type="text" class="form-control  "
												path="configMasterDTO.configDetDTOs[${d+7}].prefixId"
												id="prefixId8" autocomplete="off"
												disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
										</c:when>
										<c:otherwise>
											<form:select
												path="configMasterDTO.configDetDTOs[${d+7}].prefixId"
												class="form-control" label="Select" id="prefixId8"
												onchange="getPrefixData()"
												disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
												<form:option value="" code="" selected="true">Select Department Prefix</form:option>

											</form:select>
										</c:otherwise>

									</c:choose></td>

								<td><form:input type="text" class="form-control hasNumber"
										path="configMasterDTO.configDetDTOs[${d+7}].seqOrder"
										id="seqOrder8" maxlength="2" autocomplete="off"
										disabled="true"></form:input></td>

							</tr>

							<tr>
								<th class="col-sm-2" style="text-align: left"><spring:message
										code="common.sequenceconfig.level" text="Select Level"></spring:message></th>

								<td><c:choose>
										<c:when
											test="${command.saveMode eq 'V' || command.saveMode eq 'E'}">
											<form:input type="text" class="form-control  "
												path="configMasterDTO.configDetDTOs[${d+8}].prefixId"
												id="prefixId9" autocomplete="off"
												disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
										</c:when>
										<c:otherwise>
											<form:select
												path="configMasterDTO.configDetDTOs[${d+8}].prefixId"
												class="form-control" label="Select" id="prefixId9"
												disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
												<form:option value="" code="" selected="true">Select</form:option>

											</form:select>
										</c:otherwise>

									</c:choose></td>



								<td><form:input type="text" class="form-control hasNumber"
										path="configMasterDTO.configDetDTOs[${d+8}].seqOrder"
										id="seqOrder9" maxlength="2" autocomplete="off"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
								</td>
							<tr>
								<th class="col-sm-2" style="text-align: left"><spring:message
										code="common.sequenceconfig.usageType" text="Usage Type"></spring:message></th>

								<td><form:select
										path="configMasterDTO.configDetDTOs[${d+9}].prefixId"
										class="form-control" id="prefixId10"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'  ? true : false}">
										<form:option value="">Select</form:option>
										<form:option value="Y">Yes</form:option>
										<form:option value="N">No</form:option>
									</form:select></td>

								<td><form:input type="text" class="form-control hasNumber"
										path="configMasterDTO.configDetDTOs[${d+9}].seqOrder"
										id="seqOrder10" maxlength="2" autocomplete="off"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
								</td>

							</tr>

							<tr>
								<th class="col-sm-2" style="text-align: left"><spring:message
										code="common.sequenceconfig.financialyearwise"
										text="Financial Year Wise"></spring:message></th>

								<td><form:select
										path="configMasterDTO.configDetDTOs[${d+10}].prefixId"
										class="form-control" id="prefixId11"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'  ? true : false}">
										<form:option value="">Select</form:option>
										<form:option value="Y">Yes</form:option>
										<form:option value="N">No</form:option>
									</form:select></td>

								<td><form:input type="text" class="form-control hasNumber"
										path="configMasterDTO.configDetDTOs[${d+10}].seqOrder"
										id="seqOrder11" maxlength="2" autocomplete="off"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
								</td>

							</tr>
							
							<tr>
								<th class="col-sm-2" style="text-align: left"><spring:message
										code="common.sequenceconfig.designation"
										text="Designation"></spring:message></th>

								<td><form:select
										path="configMasterDTO.configDetDTOs[${d+11}].prefixId"
										class="form-control" id="prefixId12"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'  ? true : false}">
										<form:option value="">Select</form:option>
										<form:option value="Y">Yes</form:option>
										<form:option value="N">No</form:option>
									</form:select></td>

								<td><form:input type="text" class="form-control hasNumber"
										path="configMasterDTO.configDetDTOs[${d+11}].seqOrder"
										id="seqOrder12" maxlength="2" autocomplete="off"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
								</td>

							</tr>
							
							<tr>
								<th class="col-sm-2" style="text-align: left"><spring:message
										code="common.sequenceconfig.tradeCategory"
										text="Trade Category"></spring:message></th>

								<td><form:select
										path="configMasterDTO.configDetDTOs[${d+12}].prefixId"
										class="form-control" id="prefixId13"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'  ? true : false}">
										<form:option value="">Select</form:option>
										<form:option value="Y">Yes</form:option>
										<form:option value="N">No</form:option>
									</form:select></td>

								<td><form:input type="text" class="form-control hasNumber"
										path="configMasterDTO.configDetDTOs[${d+12}].seqOrder"
										id="seqOrder13" maxlength="2" autocomplete="off"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
								</td>

							</tr>

							<tr>
								<th class="col-sm-2" style="text-align: left"><spring:message
										code="" text="Calendar Year Wise"></spring:message></th>

								<td><form:select
										path="configMasterDTO.configDetDTOs[${d+13}].prefixId"
										class="form-control" id="prefixId14"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}">
										<form:option value="">Select</form:option>
										<form:option value="Y">Yes</form:option>
										<form:option value="N">No</form:option>
									</form:select></td>

								<td><form:input type="text" class="form-control hasNumber"
										path="configMasterDTO.configDetDTOs[${d+13}].seqOrder"
										id="seqOrder14" maxlength="2" autocomplete="off"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
								</td>

							</tr>

                             <tr>
								<th class="col-sm-2" style="text-align: left"><spring:message
										code="common.sequenceconfig.Field"
										text="Field"></spring:message></th>

								<td><form:select
										path="configMasterDTO.configDetDTOs[${d+14}].prefixId"
										class="form-control" id="prefixId15"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'  ? true : false}">
										<form:option value="">Select</form:option>
										<form:option value="Y">Yes</form:option>
										<form:option value="N">No</form:option>
									</form:select></td>

								<td><form:input type="text" class="form-control hasNumber"
										path="configMasterDTO.configDetDTOs[${d+14}].seqOrder"
										id="seqOrder15" maxlength="2" autocomplete="off"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'? true : false}"></form:input>
								</td>

							</tr>

						</tbody>

					</table>
				</div>
				<!-- </div> -->

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="common.sequenceconfig.seqstatus" /></label>


					<div class="col-sm-4">


						<label class="radio-inline"> <form:radiobutton
								disabled="${command.saveMode eq 'V'? true : false}" id="Astatus"
								path="configMasterDTO.seqStatus" value="A" checked="true" /> <spring:message
								code="common.sequenceconfig.activestatus" text="Active" />
						</label> <label class="radio-inline "> <form:radiobutton
								disabled="${command.saveMode eq 'V'? true : false}" id="Istatus"
								path="configMasterDTO.seqStatus" value="I" /> <spring:message
								code="common.sequenceconfig.inactivestatus" text="InActive" />
						</label>

					</div>
				</div>

				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
						<button class="btn btn-success  submit"
							onclick="saveSequenceConfiguration(this)" id="Submit"
							type="button" name="button" value="save">
							<i class="button-input"></i>
							<spring:message code="common.sequenceconfig.save" />
						</button>

						<button type="button" class="btn btn-warning"
							onclick="ResetForm()">
							<spring:message code="common.sequenceconfig.reset" />
						</button>
					</c:if>
					<button type="back" class="btn btn-danger" onclick="backForm()">
						<spring:message code="common.sequenceconfig.back" />
					</button>

				</div>


			</form:form>
		</div>
	</div>
</div>