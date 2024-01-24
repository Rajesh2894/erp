
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

<script  src="js/water/changeOfOwner.js"></script>
<script  src="js/mainet/file-upload.js"></script>
<c:if test="${command.hasValidationErrors()}">
	<script >
		if ($('#conNum').val() !='' ) {
			$('#searchConnection').attr('disabled',true);
			$('#confirmToProceedId').attr('disabled',true);
		}
	
	</script>
</c:if>

<div id="fomDivId">

	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">

		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="water.head.changeOwner" />
				</h2>
				<apptags:helpDoc url="ChangeOfOwnership.html"></apptags:helpDoc>

			</div>

			<div class="widget-content padding">
				<div class="mand-label clearfix">
					<span><spring:message code="water.fieldwith" /> <i
						class="text-red-1">*</i> <spring:message code="water.ismandtry" />
					</span>
				</div>
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<ul>
						<li><label id="errorId"><spring:message
									code="water.Error" /></label></li>
					</ul>
				</div>

				<form:form action="ChangeOfOwnership.html" method="POST"
					class="form-horizontal" id="ChangeOfOwnershipId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">

						<%-- <jsp:include page="/jsp/mainet/applicantDetails.jsp" /> --%>

						<apptags:applicantDetail wardZone="WWZ" disabled="true"></apptags:applicantDetail>


						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#Old_Owner_Details"> <spring:message
											code="water.changeOwner.oldDetails" />
									</a>
								</h4>
							</div>
							<div id="Old_Owner_Details" class="panel-collapse collapse">
								<div class="panel-body">
									<div id="oldOwner">
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="conNum"><spring:message
													code="water.ConnectionNo"></spring:message></label>
											<div class="col-sm-4">
												<form:input path="changeOwnerResponse.connectionNumber"
													type="text" class="form-control disablefield" id="conNum"
													readonly="false" data-rule-required="true" disabled="true"></form:input>
											</div>
											
										</div>
										<jsp:include page="/jsp/water/oldOwnerDetails.jsp" />
									</div>
								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#New_Owner_Details"> <spring:message
											code="water.changeOwner.newDetails" />
									</a>
								</h4>
							</div>
							<div id="New_Owner_Details" class="panel-collapse collapse">
								<div class="panel-body">
									<div id="newOwner">
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="ownerTransferMode"><spring:message
													code="water.transferMode" /></label>
											<c:set var="baseLookupCode" value="TFM" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="changeOwnerMaster.ownerTransferMode"
												cssClass="form-control" hasChildLookup="false" hasId="true"
												showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="true"  disabled="true"/>
										</div>

										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="cooNotitle"><spring:message code="water.title" /></label>
											<c:set var="baseLookupCode" value="TTL" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="changeOwnerMaster.cooNotitle" cssClass="form-control"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="true"  disabled="true"/>
											<label class="col-sm-2 control-label required-control"
												for="changeOwnerMaster.cooNoname"><spring:message
													code="water.owner.details.fname"></spring:message></label>
											<div class="col-sm-4">
												<form:input path="changeOwnerMaster.cooNoname" type="text"
													class="form-control hasSpecialChara" maxlength="100"
													data-rule-required="true" disabled="true"></form:input>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"
												for="changeOwnerMaster.cooNomname"><spring:message
													code="water.owner.details.mname"></spring:message></label>
											<div class="col-sm-4">
												<form:input path="changeOwnerMaster.cooNomname" type="text"
													class="form-control hasSpecialChara" maxlength="100" disabled="true"></form:input>
											</div>
											<label class="col-sm-2 control-label required-control"
												for="changeOwnerMaster.cooNolname"><spring:message
													code="water.owner.details.lname"></spring:message></label>
											<div class="col-sm-4">
												<form:input path="changeOwnerMaster.cooNolname" type="text"
													class="form-control hasSpecialChara" maxlength="100"
													data-rule-required="true" disabled="true"></form:input>
											</div>
										</div>

										<div class="form-group">

											<label class="col-sm-2 control-label required-control"
												for="newGender"> <spring:message
													code="water.owner.details.gender" /></label>

											<div class="col-sm-4">
												<c:set var="baseLookupCode" value="GEN" />
												<form:select path="changeOwnerMaster.gender"
													class="form-control" id="newGender"
													data-rule-required="true" disabled="true">
													<form:option value="">
														<spring:message code="water.select" />
													</form:option>
													<c:forEach items="${command.getLevelData(baseLookupCode)}"
														var="lookUp">
														<form:option value="${lookUp.lookUpId}"
															code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
												</form:select>
											</div>

											<label class="col-sm-2 control-label" for="newAdharNo"><spring:message
													code="water.aadhar" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control hasNumber"
													path="changeOwnerMaster.ConUidNo" id="newAdharNo"
													maxlength="12" disabled="true"></form:input>
											</div>

										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label" for="remark"><spring:message
													code="water.remark" /></label>
											<div class="col-sm-10">
												<form:textarea type="text" class="form-control"
													path="changeOwnerMaster.cooRemark" id="remark"
													maxlength="200" disabled="true"></form:textarea>
											</div>
										</div>

									</div>
								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#Additional_Owners"> <spring:message
											code="water.additionalOwner" />
									</a>
								</h4>
							</div>
							<div id="Additional_Owners" class="panel-collapse collapse">
								<div class="panel-body">
									<c:set var="d" value="0" scope="page" />
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="customFields">

											<tbody>

												<tr>
													<th width="120"><spring:message code="water.title" /></th>
													<th><spring:message code="water.owner.details.fname" /></th>
													<th><spring:message code="water.owner.details.mname" /></th>
													<th><spring:message code="water.owner.details.lname" /></th>
													<th width="130"><spring:message
															code="water.owner.details.gender" /></th>
													<th><spring:message code="water.aadhar" /></th>
													<c:if test="${command.enableSubmit ne true}">
														<th><spring:message code="water.add/remove" /></th>
													</c:if>
												</tr>


												<c:if test="${command.enableSubmit ne true}">
													<tr class="appendableClass">
														<td><input type="hidden" id="srNoId_${d}" value="">
															<c:set var="baseLookupCode" value="TTL" /> <form:select
																path="additionalOwners[${d}].caoNewTitle"
																class="form-control" id="caoNewTitle_${d}" disabled="true">
																<form:option value="0">
																	<spring:message code="water.select" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}" disabled="true"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select> <label class="hide" for="caoNewTitle_0"><spring:message
																	code="water.title" /></label> <label class="hide"
															for="caoNewFName_0"><spring:message
																	code="water.owner.details.fname" /></label> <label
															class="hide" for="caoNewMName_0"><spring:message
																	code="water.owner.details.mname" /></label> <label
															class="hide" for="caoNewLName_0"><spring:message
																	code="water.owner.details.lname" /></label> <label
															class="hide" for="caoNewGender_0"><spring:message
																	code="water.owner.details.gender" /></label></td>

														<td><form:input
																path="additionalOwners[${d}].caoNewFName"
																class="form-control  "
																onkeyup="hasCharacterFField(${d})" id="caoNewFName_${d}"
																maxlength="100"  disabled="true"/></td>
														<td><form:input
																path="additionalOwners[${d}].caoNewMName"
																class="form-control " onkeyup="hasCharacterMField(${d})"
																id="caoNewMName_${d}" maxlength="100" disabled="true" /></td>
														<td><form:input
																path="additionalOwners[${d}].caoNewLName"
																class="form-control " onkeyup="hasCharacterLField(${d})"
																id="caoNewLName_${d}" maxlength="100" disabled="true"/></td>
														<td><c:set var="baseLookupCode" value="GEN" /> <form:select
																path="additionalOwners[${d}].caoNewGender"
																class="form-control" id="caoNewGender_${d}" disabled="true">
																<form:option value="0">
																	<spring:message code="water.select"/>
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}" disabled="true"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select> <label class="hide" for="caoNewUID_0"><spring:message
																	code="water.owner.HasNumber" /></label></td>

														<td><form:input
																path="additionalOwners[${d}].caoNewUID"
																onkeyup="hasNumberField(${d})" class="form-control"
																id="caoNewUID_${d}" maxlength="12"  disabled="true"/></td>

														<td><a href="javascript:void(0);"
															data-toggle="tooltip" data-placement="top"
															class="addCF btn btn-success btn-sm"
															data-original-title="Add"><span class="hide" ><spring:message
																		code="water.add" /></span><i class="fa fa-plus-circle"></i></a>
															<a href="javascript:void(0);" data-toggle="tooltip"
															data-placement="top" class="remCF btn btn-danger btn-sm"
															data-original-title="Delete"><span class="hide"><spring:message
																		code="water.delete" /></span> <i class="fa fa-trash"></i></a></td>

													</tr>
												</c:if>
												<c:if test="${command.enableSubmit eq true}">
													<c:forEach items="${command.additionalOwners}"
														var="addOwnerList" varStatus="status">
														<tr class="appendableClass">
															<td><c:set var="baseLookupCode" value="TTL" /> <form:select
																	path="additionalOwners[${status.index}].caoNewTitle"
																	class="form-control" id="caoNewTitle_${status.index}">
																	<form:option value="0" disabled="true">
																		<spring:message code="water.select" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}" disabled="true"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select> <label class="hide" for="caoNewTitle_0"><spring:message
																		code="water.title" /></label> <label class="hide"
																for="caoNewFName_0"><spring:message
																		code="water.owner.details.fname" /></label> <label
																class="hide" for="caoNewMName_0"><spring:message
																		code="water.owner.details.mname" /></label> <label
																class="hide" for="caoNewLName_0"><spring:message
																		code="water.owner.details.lname" /></label> <label
																class="hide" for="caoNewGender_0"><spring:message
																		code="water.owner.details.gender" /></label></td>

															<td><form:input
																	path="additionalOwners[${status.index}].caoNewFName"
																	class="form-control  hasSpecialChara"
																	id="caoNewFName_${status.index}" maxlength="100" /></td>
															<td><form:input
																	path="additionalOwners[${status.index}].caoNewMName"
																	class="form-control hasSpecialChara"
																	id="caoNewMName_${status.index}" maxlength="100" /></td>
															<td><form:input
																	path="additionalOwners[${status.index}].caoNewLName"
																	class="form-control hasSpecialChara"
																	id="caoNewLName_${status.index}" maxlength="100" /></td>
															<td><c:set var="baseLookupCode" value="GEN" /> <form:select
																	path="additionalOwners[${status.index}].caoNewGender"
																	class="form-control" id="caoNewGender_${status.index}">
																	<form:option value="0">
																		<spring:message code="water.select" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select> <label class="hide" for="caoNewUID_0"><spring:message
																		code="water.owner.HasNumber" /></label></td>

															<td><form:input
																	path="additionalOwners[${status.index}].caoNewUID"
																	class="form-control hasNumber"
																	id="caoNewUID_${status.index}" maxlength="12" disabled="true"/></td>

														</tr>
													</c:forEach>
												</c:if>

											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>



						<c:if test="${command.enableSubmit ne true}">
							<div class="padding-top-10 text-center">
								
								<input type="button" class="btn btn-danger" id="backBtnId"
									onclick="window.location.href='CitizenHome.html'"
									value=<spring:message code="bckBtn"/> />
							</div>
						</c:if>
						<!-- File Uploading Area if any document list coming in search list response after calling search web service -->
						
					</div>
				</form:form>
			</div>
		</div>

	</div>
</div>

