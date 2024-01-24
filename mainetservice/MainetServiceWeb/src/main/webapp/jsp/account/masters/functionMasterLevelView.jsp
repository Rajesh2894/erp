<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link href="css/menu/ui.dynatree.css" rel="stylesheet" type="text/css"><%@ taglib
	prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/functionalMaster.js"></script>
<script src="js/menu/jquery.dynatree.js"></script>
<script src="js/mainet/validation.js"></script>

<script>
$(document).ready(function() {	 
	if('${viewmode}'=='EDIT')
	{
		 $('#parentCode').attr('readOnly',true);
		 $('#parentFinalCode').attr('readOnly',true);
	}
});
</script>

<!-- Start info box -->

<div class="widget" id="content">
	<div class="widget-header">
		<h2>
			<spring:message code="acc.master.funMaster" />
		</h2>
	<apptags:helpDoc url="AccountFunctionMaster.html" helpDocRefURL="AccountFunctionMaster.html"></apptags:helpDoc>		
	</div>
	<div class="widget-content padding">
		<div class="mand-label clearfix">
			<span><spring:message code="account.common.mandmsg" /> <i
				class="text-red-1">*</i> <spring:message
					code="account.common.mandmsg1" /></span>
		</div>

		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="accountFunctionMasterBean" name="frmMaster"
			method="POST" action="AccountFunctionMaster.html">

			<form:hidden path="hasError" id="hasError" />

			<div class="warning-div alert alert-danger alert-dismissible hide"
				id="clientSideErrorDivScrutiny"></div>
			<spring:hasBindErrors name="accountFunctionMasterBean">
				<div class="warning-div alert alert-danger alert-dismissible"
					id="errorDivScrutiny">
					<button type="button" class="close" aria-label="Close"
						onclick="closeErrBox()">
						<span aria-hidden="true">&times;</span>
					</button>
					<ul>
						<li><i class='fa fa-exclamation-circle'></i>&nbsp;<form:errors
								path="*" /></li>
					</ul>
				</div>
			</spring:hasBindErrors>
			<c:if test="${!viewMode}">
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#parent_label"><spring:message
										code="account.master.parentLabel" /> </a>
							</h4>
						</div>
						<div id="parent_label" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group" id="1">
									<label class="label-control col-sm-2 required-control"><spring:message
											code="acc.master.funLvl" /> </label>
									<div class="col-sm-4">
										<form:input id="parentFunLevel0" path="parentLevel"
											value="${accountFunctionMasterBean.parentLevel}"
											class="form-control" disabled="${viewMode}" readonly="true"
											data-rule-required="true" />
									</div>

									<label class="label-control col-sm-2 required-control"><spring:message
											code="acc.master.funCode" /></label>
									<div class="col-sm-4">
										<form:input id="parentCode" path="parentCode"
											onblur="setParentValue()" class="form-control hasNumber"
											disabled="${viewMode}" maxlength="${maxParentLvlLength}"
											data-rule-required="true" />
									</div>
									<form:hidden path="" value="${maxParentLvlLength}"
										id="maxParentLenght" />
								</div>

								<div class="form-group" id="2">
									<label class="label-control col-sm-2 required-control"><spring:message
											code="acc.master.funDesc" /> </label>
									<div class="col-sm-4">
										<form:input path="parentDesc" cssClass="form-control"
											id="parentfunDesc" disabled="${viewMode}"
											data-rule-required="true" />
									</div>
									<label class="label-control col-sm-2 "><spring:message
											code="acc.master.funFinalCode" /></label>
									<div class="col-sm-4">
										<form:input id="parentFinalCode" path="parentFinalCode"
											class="form-control hasNumber" readonly="true"
											maxlength="${maxParentLvlLength}" />
									</div>
								</div>

							</div>
						</div>
					</div>

					<fieldset id="divId" class="clear">



						<form:hidden path="codconfigId" />
						<ul id="ulId">


							<c:if test="${viewmode eq 'EDIT' or viewmode eq 'ADD'}">
								<form:hidden path="newRecord" value="true" />
								<li>

									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">
												<a data-toggle="collapse" class=""
													data-parent="#accordion_single_collapse"
													href="#child_label${count}"><spring:message
														code="account.master.childLabel" /> </a>
											</h4>
										</div>
										<div id="parent_label" class="panel-collapse collapse in">
											<div class="panel-body">

												<div id="child_label${count}" class=" ">
													<div class="panel-body">

														<form:hidden path="functionId" />

														<div class="form-group" id="3">
															<div class="col-sm-4">
																<label class="required-control"><spring:message
																		code="acc.master.funLvl" /></label>
																<form:hidden path="" id="levelData" value="${levelSize}" />
																<form:select path="listDto[${count}].childLevel"
																	class="form-control" id="childFunLevel${count}"
																	onchange="fetchMaxLengthAndResetChildParentLevel()"
																	disabled="${viewMode}" data-rule-required="true">
																	<form:option value="" id="">
																		<spring:message code="accounts.master.selLevel" />
																	</form:option>
																	<c:forEach items="${getLevels}" varStatus="status"
																		var="level">
																		<form:option value="${status.count}"
																			code="${status.count}"> ${level} </form:option>
																	</c:forEach>
																</form:select>

															</div>
															<div class="col-sm-4">
																<form:hidden path="listDto[${count}].childFunId" />
																<label class="required-control"><spring:message
																		code="acc.master.funCode" /></label>
																<form:input id="childFunCode${count}"
																	path="listDto[${count}].childCode"
																	cssClass="hasNumber form-control"
																	disabled="${viewMode}"
																	onchange="clearParentCode(${count})"
																	data-rule-required="true" />
															</div>
															<div class="col-sm-4">
																<label class="required-control"><spring:message
																		code="acc.master.funDesc" /> </label>
																<form:input path="listDto[${count}].childDesc"
																	cssClass="form-control" id="funDesc${count}"
																	disabled="${viewMode}" data-rule-required="true" />
															</div>
														</div>

														<div class="form-group">
															<div class="col-sm-4">
																<label class="required-control"><spring:message
																		code="acc.master.funParLvl" /> </label>

																<form:select path="listDto[${count}].childParentLevel"
																	class="hasNumber form-control"
																	id="childParentFunLevel${count}"
																	onchange="getParentCode(${count})"
																	disabled="${viewMode}" data-rule-required="true">
																	<form:option value="">
																		<spring:message code="accounts.master.selLevel" />
																	</form:option>
																	<c:forEach items="${getLevels}" varStatus="status"
																		var="level">
																		<form:option value="${status.count}"
																			code="${status.count}"> ${level} </form:option>
																	</c:forEach>
																</form:select>
															</div>
															<div class="col-sm-4">
																<label class="required-control"><spring:message
																		code="acc.master.funParCode" /></label>
																<form:select path="listDto[${count}].childParentCode"
																	class="hasNumber form-control"
																	id="childParentCode${count}"
																	onchange="getFinalCode(${count})"
																	disabled="${viewMode}" data-rule-required="true">
																	<form:option value="">
																		<spring:message code="accounts.master.selLevel" />
																	</form:option>
																</form:select>
															</div>
															<div class="col-sm-4">
																<label class="label-control"><spring:message
																		code="accounts.master.compCode" /></label>
																<form:input id="childFinalCode${count}"
																	path="listDto[${count}].childFinalCode"
																	cssClass="hasNumberMaxEight form-control"
																	readonly="true" />
															</div>
														</div>


													</div>
												</div>
											</div>
										</div>
									</div>


								</li>
							</c:if>
						</ul>

					</fieldset>
					<INPUT type="hidden" id="count" value="0" /> <INPUT type="hidden"
						id="countForParentLevel" value="" /> <INPUT type="hidden"
						id="countFinalCode" value="" />
					<c:if test="${!viewMode}">

						<div class="pull-right">
							<button id="addRow" type="button" onclick="addRowFunction()"
								class="btn btn-success" title="Add">
								<i class="fa fa-plus"></i>
							</button>
							&nbsp;
							<button id="removeRow" type="button" title="Remove"
								onclick="removeRowFunction()" class="btn btn-danger">
								<i class="fa fa-minus"></i>
							</button>
						</div>
					</c:if>
					<c:if test="${!viewMode and viewmode ne 'EDIT'}">
						<div class="text-center">
							<input type="button" class="btn btn-success btn-submit"
								onclick="saveLeveledDataForFunctionMaster(this)" value="<spring:message code="account.bankmaster.save" text="Save"/>">
							</input> <input type="button" id="Reset"
								class="btn btn-warning addFunctionMasterClass" value="<spring:message code="account.bankmaster.reset" text="Reset"/>"></input>
							<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountFunctionMaster.html'"
							value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
						</div>
					</c:if>

					<c:if test="${viewmode eq 'EDIT'}">
						<div class="text-center">
							<input type="button" class="btn btn-success btn-submit"
								onclick="saveEditedDataForFunctionMaster(this)" value="<spring:message code="account.bankmaster.save" text="Save"/>">
							</input>
							
							<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountFunctionMaster.html'"
							value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
						</div>

					</c:if>

					<c:if test="${viewMode}">
						<div class="text-center">
							<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountFunctionMaster.html'"
							value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
						</div>
					</c:if>
				</div>
			</c:if>

			<c:if test="${viewMode}">
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="menu.structure.label" /></label>
					<div class="col-sm-10">
						<div id="tree" class="notes createStructure">
							<c:set var="setOfNode" value="${setOfNodeForFunction}"
								scope="request" />
							<jsp:include page="treeViewRepresentationFunctionMaster.jsp"></jsp:include>
						</div>
					</div>
				</div>

				<div class="panel-body" id="editChildNode">

					<form:hidden path="functionId" />


					<div class="form-group" id="3">
						<div class="col-sm-4">
							<label class="label-control"><spring:message
									code="acc.master.funLvl" /> </label>
							<form:hidden path="" id="levelData" value="${levelSize}" />
							<form:select path="editedDataChildLevel" class="form-control"
								id="editedChildFunLevel"
								onchange="fetchMaxLengthAndResetChildParentLevel()"
								disabled="${true}">
								<form:option value="" id="">
									<spring:message code="accounts.master.selLevel" />
								</form:option>
								<c:forEach items="${getLevels}" varStatus="status" var="level">
									<form:option value="${status.count}" code="${status.count}">${level}</form:option>
								</c:forEach>
							</form:select>

						</div>
						<div class="col-sm-4">
							<label class="label-control"><spring:message
									code="acc.master.funCode" /></label>
							<form:input id="editedChildFunCode" path="editedDataChildCode"
								cssClass="hasNumber form-control required-control"
								readonly="${viewMode}" maxlength="${childCodeDigit}" />
						</div>
						<div class="col-sm-4">
							<label class="label-control required-control"><spring:message
									code="acc.master.function.Description" /> </label>
							<form:input path="editedDataChildDesc" cssClass="form-control"
								id="editedDataChildDesc" readonly="false" />
						</div>

						<div class="col-sm-4" id="statusDiv">
							<label class="label-control required-control"><spring:message
									code="accounts.master.status" /></label>
							<form:select path="editedChildStatus" class="form-control"
								id="editedChildStatus">
								<form:option value="">
									<spring:message code="accounts.master.sel.status" />
								</form:option>
								<c:forEach items="${functionStatus}" var="status">
									<form:option value="${status.lookUpId}">${status.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
							<form:hidden path="edittedFunctionId" id="edittedFunctionId" />
						</div>
					</div>
				</div>

				<div class="panel-body" id="editParentNode">
					<div class="form-group" id="4">
						<div class="col-sm-4">
							<label class="label-control"><spring:message
									code="acc.master.funParLvl" /> </label>

							<form:select path="editedDataChildParentLevel"
								class="hasNumber form-control" id="editedDataChildParentLevel"
								onchange="getParentCode(${count})" disabled="${true}">
								<form:option value="">
									<spring:message code="accounts.master.selLevel" />
								</form:option>
								<c:forEach items="${getLevels}" varStatus="status" var="level">
									<form:option value="${status.count}" code="${status.count}"> ${level} </form:option>
								</c:forEach>
							</form:select>
						</div>
						<div class="col-sm-4">
							<label class="label-control"><spring:message
									code="acc.master.funParCode" /></label>
							<form:input id="editedDataChildParentCode"
								path="editedDataChildParentCode"
								cssClass="hasNumberMaxEight form-control" readonly="true" />

						</div>
						<div class="col-sm-4">
							<label class="label-control"><spring:message
									code="accounts.master.compCode" /></label>
							<form:input id="editedDataChildCompositeCode"
								path="editedDataChildCompositeCode"
								cssClass="hasNumberMaxEight form-control" readonly="true" />
						</div>
					</div>
				</div>

				<div class="text-center">
					<c:if test="${viewModeLevel != 'Y'}">
						<input type="button" id="saveButttonOnDescChange"
							class="btn btn-success btn-submit"
							onclick="updateDescForFunctionMaster(this)" value="Save">
						</input>
					</c:if>
					<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountFunctionMaster.html'"
							value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
				</div>
			</c:if>
		</form:form>
	</div>
</div>
