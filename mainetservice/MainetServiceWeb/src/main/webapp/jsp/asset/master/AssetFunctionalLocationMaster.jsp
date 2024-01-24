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
<script type="text/javascript" src="js/asset/funcLocationCode.js"></script>

<style>
<!--
.control {
	width: 1000% !important;
	height: 1000% !important;
}
-->
</style>




<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated slideInDown">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="asset.functional.location.label.header" text="Functional Location Hierarchy Master" />
			</h2>
			<apptags:helpDoc url="AssetFunctionalLocation.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
			<c:if test="${command.modeType ne 'V'}">
				<span><spring:message code="care.ismendatory"
						text="Field with * is mandatory" /></span>
			</c:if>
			</div>
			<form:form id="functionallocation" name="functionallocation"
				class="form-horizontal" commandName="command"
				action="AssetFunctionalLocation.html" method="POST"
				enctype="multipart/form-data">
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
					id="errorDiv">
				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse1">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse1" href="#Applicant"><spring:message
										code="Functional.location.hierarchy.master" text="Functional Location Hierarchy Master" /></a>
							</h4>
						</div>
						
						<!-- In case of Edit an input hidden field is added -->
						<c:if test="${command.modeType eq 'E' || command.modeType eq 'C'}">
							<form:input path="modeType" value="${command.modeType}" style="display:none"/>
						</c:if>
						

						
						<div class="panel-body">
							<!-- Additional owner Detail section -->
							<c:set var="d" value="0" scope="page" />
							<div class="table-responsive">
								<table class="table text-left table-striped table-bordered"
									id="funcLocCodeTable">
									<thead>
										<tr>
											<th width="15%"><spring:message code="ast.function.master.location.code"
													text="Function Location Code" />*</th>
											<th width="15%"><spring:message code="ast.function.master.description"
													text="Description" />*</th>
											<th width="15%"><spring:message code="ast.function.master.parentid"
													text="Function Location Parent ID" />*</th>
											<th width="15%"><spring:message code="ast.function.master.startpoint"
													text="Start Point" />*</th>
											<th width="15%"><spring:message code="ast.function.master.endpoint" text="End Point" />*</th>
											<th><spring:message code="ast.function.master.unit" text="Unit" />*</th>
											<th>
											<c:if test="${command.modeType eq 'C'}">
											<a href="javascript:void(0);" data-toggle=""
												data-placement="top" class="addCF btn btn-success btn-sm"
												data-original-title="Add" id="addCF" title="add"><i
													class="fa fa-plus-circle"></i></a>
											</c:if>
											</th>
										</tr>
										</thead>
										<tbody>
										<c:forEach items="${command.funcLocDTOList}" var="funcLocDTO"
											varStatus="count">
											<tr class="appendableClass">
											<td>
											<c:choose>
												<c:when test = "${command.modeType eq 'C'}">
												<form:input	path="funcLocDTOList[${count.index}].funcLocationCode"	cssClass="form-control" 
												data-rule-required="true" onblur="validateFuncLocCode(this)" 
												disabled="${command.modeType eq 'V'}" readonly="${command.modeType eq 'E'}"/>
												</c:when>
												<c:otherwise>
												<form:input	path="funcLocDTOList[${count.index}].funcLocationCode" cssClass="form-control" 
													data-rule-required="true" disabled="${command.modeType eq 'V'}" 
													readonly="${command.modeType eq 'E'}"/>
												
												</c:otherwise>
											</c:choose>
											</td>
												<td><form:input
														path="funcLocDTOList[${count.index}].description"
														class="form-control" maxlength="100" disabled="${command.modeType eq 'V'}"/>
												</td>
												<td>
													<form:select
														path="funcLocDTOList[${count.index}].parentId"
														cssClass="form-control" disabled="${command.modeType eq 'V'}">
														<form:option value=""><spring:message code='asset.info.select' /></form:option>
														<c:forEach items="${command.parentFuncLocDTOList}" var="obj">
	 														<form:option value="${obj.funcLocationId}" code="${obj.funcLocationId}">${obj.funcLocationCode}</form:option>
 														</c:forEach>

													</form:select>
											
												</td>
												<td>
													<form:input
														path="funcLocDTOList[${count.index}].startPoint"
														cssClass="decimal text-right form-control" onkeyup="verifyDecimalNo(this)"  maxlength="100" disabled="${command.modeType eq 'V'}" />
												</td>
												<td>
													<form:input
														path="funcLocDTOList[${count.index}].endPoint"
														cssClass="decimal text-right form-control" onkeyup="verifyDecimalNo(this)" maxlength="100" disabled="${command.modeType eq 'V'}"/>
												</td>
												<td>
													<form:select path="funcLocDTOList[${count.index}].unit"
														cssClass="form-control" data-rule-required="true" disabled="${command.modeType eq 'V'}">
														<form:option value=""><spring:message code='asset.info.select' /></form:option>
														<c:forEach items="${command.unitLookUpList}" var="lookUp">
															<form:option value="${lookUp.lookUpId}"
																code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
														</c:forEach>
													</form:select>
												</td>
												
												<td><!-- <button type="button" class="btn btn-blue-2 btn-sm"
														name="button-view" id="button-view" title="view">
														<i class="fa fa-eye" aria-hidden="true"></i>
													</button>
													<button type="button" class="btn btn-warning btn-sm"
														name="button-edit" id="button-edit" title="edit">
														<i class="fa fa-pencil" aria-hidden="true"></i>
													</button> <a href="javascript:void(0);" data-toggle=""
													data-placement="top" class="remCF btn btn-danger btn-sm"
													data-original-title="Delete" title="Delete"><i
														class="fa fa-trash"></i></a> -->
												<c:if test="${command.modeType eq 'C'}">
												<a href="javascript:void(0);" data-toggle=""
													data-placement="top" class="remCF btn btn-danger btn-sm"
													data-original-title="Delete" title="Delete"><i
														class="fa fa-trash"></i></a>
												</c:if>		
														</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>

						</div>
					</div>
				</div>

				<div class="text-center clear padding-10">
					<c:if test="${command.modeType ne 'V'}">
						<button class="btn btn-success btn"
							onclick="saveFuncLocCode(this);" id="save"
							type="button">
							<i class="button-input"></i>
							<spring:message code="asset.depreciationMaster.save" text="Submit" />
						</button>
						<c:if test="${command.modeType eq 'C'}">
							<button type="Reset" class="btn btn-warning" onclick="resetCDM()">
								<spring:message code="reset.msg" text="Reset" />
							</button>
						</c:if>
					</c:if>	
					<button type="button" class="btn btn-danger" id="back"
						onclick="BackCDM();">
						<spring:message code="back.msg" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>


<!-- End of info box -->


