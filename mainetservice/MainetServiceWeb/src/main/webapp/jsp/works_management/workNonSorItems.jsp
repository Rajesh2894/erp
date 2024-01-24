<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/works_management/workEstimateSummary.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script>
	jQuery('#tab3').addClass('active');
</script>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.estimate.title" text="Work Estimation" />
			</h2>
			<div class="additional-btn">
				  <apptags:helpDoc url="WorkEstimate.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>

			<form:form action="WorkEstimate.html" class="form-horizontal"
				name="workEstimateNonSor" id="workEstimateNonSor">
				<jsp:include page="/jsp/works_management/workEstimateTab.jsp" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="removeNonSorById" id="removeNonSorById" />
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.def.workCode" text="Work Code" /></label>
					<div class="col-sm-4">
						<form:input path="newWorkCode" cssClass="form-control"
							id="definationNumber" readonly="true" data-rule-required="true" />
					</div>
					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="work.def.workname" text="" /> </label>
					<div class="col-sm-4">
						<form:select path="newWorkId"
							class="form-control chosen-select-no-results mandColorClass"
							id="workDefination" onchange="getDefinationNumber();"
							disabled="true">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.workDefinitionDto}" var="workDef">
								<form:option code="${workDef.workcode}"
									value="${workDef.workId }">${workDef.workName }</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="">
					<c:set var="d" value="0" scope="page"></c:set>
					<table class="table table-bordered table-striped"
						id="nonSorDetails">
						<thead>
							<tr>
								<th width=""><spring:message code="ser.no" text="" /><input
									type="hidden" id="srNo"></th>
								<th scope="col" width="8%" align="center"><spring:message
										code="work.estimate.overheads.ItemCode" /></th>
								<th scope="col" width="20%" align="center"><spring:message
										code="work.management.description" /></th>
								<th scope="col" width="12%" align="center"><spring:message
										code="work.estimate.quantity" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.management.unit" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.estimate.rate" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.estimate.Total" /></th>
								<th scope="col" width="15%" align="center"><spring:message
										code="wms.Remark" text="Remark" /></th>
								<c:if test="${command.saveMode ne 'V'}">
								<th align="center" width="10%" scope="col"><spring:message
											code="works.management.action" text="Action" /> <!-- <button type="button"
											onclick="return false;"
											class="btn btn-blue-2 btn-sm  addNonSORDetails">
											<i class="fa fa-plus-circle"></i>
										</button> --></th>
								</c:if>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th colspan="6" class="text-right"><spring:message
										code="work.estimate.Total" /></th>
								<th colspan="1"><form:input path="" id="totalnonSor"
										cssClass="form-control text-right" readonly="true" /></th>
								<th colspan="1" class="text-right"></th>
								<th colspan="1" class="text-right"></th>
							</tr>
						</tfoot>

						<c:choose>
							<c:when test="${command.saveMode eq 'V'}">
								<c:forEach var="schemeListData"
									items="${command.workEstimateNonSorList}" varStatus="status">
									<tr class="appendableClass">
										<td><form:input path="" id="sNo${d}" value="1"
												readonly="true" cssClass="form-control" /> <form:hidden
												path="workEstimateNonSorList[${d}].workEstemateId"
												id="workEstemateId${d}" /></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].sorDIteamNo"
												readonly="true"
												onkeyup="inputPreventSpace(event.keyCode,this);"
												cssClass="form-control" id="sorDIteamNo${d}" /></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].sorDDescription"
												readonly="true" cssClass="form-control"
												id="sorDDescription${d}" /></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].workEstimQuantity"
												cssClass="form-control text-right calculation"
												id="workQuantity${d}" readonly="true" placeholder="0000.0000"
												onkeypress="return hasAmount(event, this, 5, 4)"
												onchange="getAmountFormatInDynamic((this),'workQuantity')" /></td>
										<td><form:select
												path="workEstimateNonSorList[${d}].sorIteamUnit"
												cssClass="form-control chosen-select-no-results" id="sorIteamUnit${d}"
												disabled="true">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${command.unitLookUpList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].sorBasicRate"
												cssClass="form-control text-right calculation"
												id="sorRate${d}" readonly="true" placeholder="00.00"
												onkeypress="return hasAmount(event, this, 11, 2)"
												onchange="getAmountFormatInDynamic((this),'sorRate')" /></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].workEstimAmount"
												cssClass="form-control text-right amount" readonly="true"
												id="workAmount${d}" placeholder="0000.00" /></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].meRemark"
												cssClass="form-control" readonly="true" id="meRemark${d}" /></td>

									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
							</c:when>
							<c:when test="${fn:length(command.workEstimateNonSorList) > 0}">
								<c:forEach var="schemeListData"
									items="${command.workEstimateNonSorList}" varStatus="status">
									<tr class="appendableClass">
										<td><form:input path="" id="sNo${d}" value="1"
												readonly="true" cssClass="form-control" /> <form:hidden
												path="workEstimateNonSorList[${d}].workEstemateId"
												id="workEstemateId${d}" /></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].sorDIteamNo"
												onkeyup="inputPreventSpace(event.keyCode,this);"
												cssClass="form-control" id="sorDIteamNo${d}" /></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].sorDDescription"
												cssClass="form-control" id="sorDDescription${d}" /></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].workEstimQuantity"
												cssClass="form-control text-right calculation"
												id="workQuantity${d}" placeholder="000.0000"
												onkeypress="return hasAmount(event, this, 5, 4)"
												onchange="getAmountFormatInDynamic((this),'workQuantity')" /></td>
										<td><form:select
												path="workEstimateNonSorList[${d}].sorIteamUnit"
												cssClass="form-control chosen-select-no-results" id="sorIteamUnit${d}">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${command.unitLookUpList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].sorBasicRate"
												cssClass="form-control text-right calculation"
												id="sorRate${d}"
												onkeypress="return hasAmount(event, this, 11, 2)"
												onchange="getAmountFormatInDynamic((this),'sorRate')"
												placeholder="00.00" /></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].workEstimAmount"
												cssClass="form-control text-right amount" readonly="true"
												id="workAmount${d}" placeholder="0000.00" /></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].meRemark"
												cssClass="form-control" id="meRemark${d}" /></td>
										<!-- <td class="text-center"><a href='#'
											onclick='return false;'
											class='btn btn-danger btn-sm deleteNonSORDetails'><i
												class="fa fa-trash"></i></a></td> -->
										<td class="text-center"><button type="button"
												onclick="return false;" id="addButton${d}"
												class="btn btn-blue-2 btn-sm  addNonSORDetails">
												<i class="fa fa-plus-circle"></i>
											</button>
											<button type="button" onclick="return false;"
												class='btn btn-danger btn-sm deleteNonSORDetails'
												id="delButton${d}">
												<i class="fa fa-trash"></i>
											</button></td>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tbody>
									<tr class="appendableClass">
										<td><form:input path="" id="sNo${d}" value="1"
												readonly="true" cssClass="form-control" /></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].sorDIteamNo"
												onkeyup="inputPreventSpace(event.keyCode,this);"
												cssClass="form-control" id="sorDIteamNo${d}" /></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].sorDDescription"
												cssClass="form-control" id="sorDDescription${d}" /></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].workEstimQuantity"
												cssClass="form-control text-right calculation"
												id="workQuantity${d}" placeholder="000.0000"
												onkeypress="return hasAmount(event, this, 5, 4)"
												onchange="getAmountFormatInDynamic((this),'workQuantity')" /></td>
										<td><form:select
												path="workEstimateNonSorList[${d}].sorIteamUnit"
												cssClass="form-control" id="sorIteamUnit${d}">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${command.unitLookUpList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].sorBasicRate"
												onkeypress="return hasAmount(event, this, 11, 2)"
												id="sorRate${d}" placeholder="00.00"
												onchange="getAmountFormatInDynamic((this),'sorRate')"
												cssClass="form-control text-right calculation" /></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].workEstimAmount"
												cssClass="form-control text-right amount" readonly="true"
												id="workAmount${d}" placeholder="0000.00" /></td>
										<td><form:input
												path="workEstimateNonSorList[${d}].meRemark"
												cssClass="form-control" id="meRemark${d}" /></td>
										<!-- <td class="text-center"><a href='#'
											onclick='return false;'
											class='btn btn-danger btn-sm deleteNonSORDetails'><i
												class="fa fa-trash"></i></a></td> -->
										<td class="text-center"><button type="button"
												onclick="return false;"
												class="btn btn-blue-2 btn-sm  addNonSORDetails">
												<i class="fa fa-plus-circle"></i>
											</button>
											<button type="button" onclick="return false;"
												class='btn btn-danger btn-sm deleteNonSORDetails'
												id="delButton${d}">
												<i class="fa fa-trash"></i>
											</button></td>

									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</tbody>
							</c:otherwise>
						</c:choose>

					</table>
				</div>
				<div class="text-right text-blue-2 ">
					<p>
						<strong><spring:message code="work.total.non.sorValue" />
							<span id="totalSor">0.00</span></strong> <br> <strong><spring:message
								code="work.total.estimat.value" /><span id="totalNon">${command.totalEstimateAmount}</span></strong>
					</p>
				</div>


				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" id="save" class="btn btn-success btn-submit" title='<spring:message code="work.management.SaveContinue" text="Save & Continue" />'
							onclick="saveNonSor(this);">
							<spring:message code="work.management.SaveContinue"
								text="Save & Continue" />
						</button>
					</c:if>
					<c:if test="${command.requestFormFlag eq 'TNDR'}">
						<button type="button" class="button-input btn btn-danger" title='<spring:message code="works.management.back" text="Back" />'
							name="button-Cancel" onclick="backToTender();" id="button-Cancel">
							<spring:message code="works.management.back" text="Back" />
						</button>
					</c:if>

					<c:if test="${command.requestFormFlag eq 'AP'}">
						<button type="button" class="button-input btn btn-danger" title='<spring:message code="works.management.back" text="Back" />'
							name="button-Cancel"
							onclick="backSendForApprovalForm(${command.workProjCode});"
							id="button-Cancel">
							<spring:message code="works.management.back" text="Back" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'M'}">
						<button type="button" class="button-input btn btn-danger" title='<spring:message code="works.management.back.to.mb" text="Back To MB" />'
							name="button-Cancel" value="Cancel" style=""
							onclick="backAddMBMasterForm();" id="button-Cancel">
							<spring:message code="works.management.back.to.mb" text="Back To MB" />
						</button>
					</c:if>
					<c:if
						test="${command.requestFormFlag ne 'AP' && command.requestFormFlag ne 'TNDR' && command.saveMode ne 'M'}">
						<button type="button" class="button-input btn btn-danger" title='<spring:message code="works.management.back" text="Back" />'
							name="button-Cancel" value="Cancel" style=""
							onclick="backForm();" id="button-Cancel">
							<spring:message code="works.management.back" text="Back" />
						</button>
					</c:if>

				</div>
			</form:form>
		</div>
	</div>
</div>