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
<script type="text/javascript"
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>

<script type="text/javascript"
	src="js/asset/assetInsuranceDetailsDataTable.js"></script>
<style>
<!--
.control {
	width: 1000% !important;
	height: 1000% !important;
}
-->
</style>

<%-- 		<div class="widget-header">
			<h2>
				<spring:message code="asset.insurance.insurancedetail" text="Asset Insurance Detail" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message
							code="" text="Help" /></span></a>
			</div>
		</div> --%>
<div class="widget-content padding">
	<div class="mand-label clearfix">
		<c:if test="${command.modeType ne 'V'}">
			<span><spring:message code="" text="Field with " /><i
				class="text-red-1">*</i> <spring:message code="care.ismendatory"
					text="is mandatory" /></span>
		</c:if>
	</div>
	<form:form id="functionallocation" name="functionallocation"
		class="form-horizontal" commandName="command"
		action="AssetFunctionalLocation.html" method="POST"
		enctype="multipart/form-data">
		<input type="hidden" id="moduleDeptUrl"
				value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
		<div class="compalint-error-div">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
		</div>
		<!-- <div class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<ul>
						<li><label id="errorId"></label></li>
					</ul>
				</div> -->
		<div
			class="warning-div error-div alert alert-danger alert-dismissible"
			id="errorDiv"></div>
		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#Applicant"><spring:message
								code="asset.insurance.insurancedetail" text="Asset Insurance Detail" /></a>
					</h4>
				</div>

				<!-- In case of Edit an input hidden field is added -->
				<c:if test="${command.modeType eq 'E' || command.modeType eq 'C' || command.modeType eq 'D'}">
					<form:input path="modeType" value="${command.modeType}"
						style="display:none" />
				</c:if>

				<!-- In case of Edit an input hidden field is added -->
				<c:if test="${command.modeType eq 'E' || command.modeType eq 'C' || command.modeType eq 'D'}">
					<form:input path="subModeType" value="${command.subModeType}"
						style="display:none" />
				</c:if>



				<div class="panel-body">
					<!-- Additional owner Detail section -->
					<c:set var="d" value="0" scope="page" />
					<div class="table-responsive">
						<table class="table text-left table-striped table-bordered"
							id="funcLocCodeTable">
							<tbody>
								<tr>
									<th width="15%"><spring:message code=""
											text="Asset Insurance No" /></th>
									<th width="15%"><spring:message
											code="asset.insurance.insuranceprov"
											text="Inusrance Provider" /></th>
									<th width="15%"><spring:message
											code="asset.insurance.startdate" text="Start Date" /></th>
									<th width="15%"><spring:message
											code="asset.insurance.enddate" text="End Date" /></th>
									<%-- <th width="15%"><spring:message
											code="asset.insurance.status" text="Start Point" /></th> --%>
									<%-- <th width="15%"><spring:message code="" text="End Point" /></th>
											<th><spring:message code="" text="Unit" /></th> --%>
									<th><%-- <c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
											<a href="javascript:void(0);" data-toggle=""
												data-placement="top" class="addCF btn btn-success btn-sm"
												data-original-title="Add" id="addCF" title="add"><i
												class="fa fa-plus-circle"></i></a>
										</c:if> --%>
										<spring:message
											code="asset.insurance.action" text="Action" />
										</th>
								</tr>



	

								<c:choose>
									<c:when	test="${ not empty(command.astDetailsDTO.astInsuDTOList) }">

										<c:forEach items="${command.astDetailsDTO.astInsuDTOList}"
											var="astInsDTO">
											<tr>
												<td>${astInsDTO.insuranceNo}</td>
												<td>${astInsDTO.insuranceProvider}</td>
												<td><fmt:formatDate value="${astInsDTO.insuranceStartDate}" pattern="dd/MM/yyyy" /></td>
												<td><fmt:formatDate value="${astInsDTO.insuranceEndDate}" pattern="dd/MM/yyyy" /></td>
												<%-- <td>${astInsDTO.status}</td> --%>
												<td class="center">
													<button type="button" class="btn btn-blue-2 btn-sm"
												onClick="viewASTInsuPagePopUp('showAstInsuPage','View','${astInsDTO.insuranceNo}')" title="View Asset">
														<i class="fa fa-eye"></i>
											</button>
											<c:if test="${command.astDetailsDTO.assetInformationDTO.appovalStatus ne 'P' and command.modeType eq 'E' }">
														<button type="button" class="btn btn-success btn-sm"
													onClick="openASTInsu('showAstInsuPage','Edit','${astInsDTO.insuranceNo}')" title="Edit Asset">
															<i class="fa fa-pencil"></i>
														</button>
													</c:if>

												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
											<tr>
												<td colspan="5">No Results Found</td>
											</tr>
									</c:otherwise>
									</c:choose>
							</tbody>
						</table>
					</div>

				</div>
			</div>
		</div>

		<div class="text-center clear padding-10">
			<c:if test="${command.modeType ne 'V'}">
				<%-- <button class="btn btn-success btn"
							onclick="saveFuncLocCode(this);" id="save"
							type="button">
							<i class="button-input"></i>
							<spring:message code="asset.depreciationMaster.save" text="Submit" />
						</button> --%>
				<c:if test="${command.addInsuStatusFlag eq 'N'}">
					<button class="btn btn-success add" id="AddAssetRegisration"
						type="button" onclick="openASTInsu('showAstInsuPage','Add',null)">
						<i class="button-input"></i>
						<spring:message code="" text="Add" />
					</button>
				</c:if>
				<c:if test="${command.modeType eq 'E'}">
					<button class="btn btn-danger" onclick="backToHomePage()">
						<spring:message code="back.msg" text="Back" />
					</button>
				</c:if>

				<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
					<button type="Reset" class="btn btn-warning" onclick="resetCDM()">
						<spring:message code="reset.msg" text="Reset" />
					</button>
				</c:if>
			</c:if>
			<c:if test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D'}">
				<button type="button" class="button-input btn btn-success"
					name="button" value="Save" onclick="saveAstInsurDetDataTable(this);"
					id="save">
					<spring:message code="asset.insurance.save&continue" />
				</button>
			</c:if>
			<!-- D#80189 -->
			<c:if test="${command.approvalProcess ne 'Y' }">
				<c:if test="${command.modeType eq 'V'}">
					<button type="button" class="btn btn-danger" id="back"
						onclick="showTab('#astSer')">
						<spring:message code="back.msg" text="Back" />
					</button>
				</c:if>
			</c:if>
			
		</div>
	</form:form>
</div>


<!-- End of info box -->


