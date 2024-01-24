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
<script src="js/menu/jquery.dynatree.js"></script>
<link href="css/menu/ui.dynatree.css" rel="stylesheet" type="text/css"><%@ taglib
	prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/accountPrimayHeadCodeMaster.js"></script>
<script src="js/mainet/validation.js"></script>




<!-- Start info box -->

<div class="widget" id="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="accounts.master.primAccCode" />
		</h2>
	</div>


	<div class="widget-content padding">
		<div class="mand-label clearfix">
			<span><spring:message code="account.common.mandmsg" /> <i
				class="text-red-1">*</i> <spring:message
					code="account.common.mandmsg1" /></span>
		</div>
		<div class="error-div alert alert-danger alert-dismissible"
			id="errorDivId" style="display: none;">
			<button type="button" class="close" onclick="closeOutErrBox()"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<span id="errorId"></span>
		</div>
		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="accountHeadPrimaryAccountCodeMasterBean"
			name="frmMaster" method="POST"
			action="AccountHeadPrimaryAccountCodeMaster.html">

			<div class="warning-div alert alert-danger alert-dismissible hide"
				id="clientSideErrorDivScrutiny"></div>
			<spring:hasBindErrors name="accountHeadPrimaryAccountCodeMasterBean">
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


			<input type="hidden" value="${modeFlag}" id="modeFlag" />
			<div class="form-group" id="superParentNode">
				<label class="col-sm-2 control-label"><spring:message
						code="" text="Create Super Parent" /></label>
				<div class="col-sm-10">
					<input class="margin-left-0 margin-top-10" type="checkbox"
						id="superNode" name="superNode">
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
						code="menu.structure.label" /></label>
				<div class="col-sm-10">
					<div id="tree" class="notes createStructure">
						<c:set var="node" value="${node}" scope="request" />
						<jsp:include page="treeViewRepresentationPrimaryCode.jsp"></jsp:include>
					</div>
				</div>
			</div>

			<div class="form-group" id="addModeDiv">
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
									<label class="control-label col-sm-2 required-control"><spring:message
											code="accounts.master.primAccCodeLvl" /> </label>
									<div class="col-sm-4">
										<form:input id="parentLevel" path="parentLevel"
											value="${accountHeadPrimaryAccountCodeMasterBean.parentLevel}"
											class="form-control" readonly="${editMode}" />
									</div>
									<label class="control-label col-sm-2 required-control"><spring:message
											code="accounts.master.primAccCode" /></label>
									<div class="col-sm-4">
										<form:input id="parentCode" path="parentCode"
											onblur="setParentValue()" class="hasNumber form-control"
											readonly="${editMode}" maxlength="${maxParentLvlLength}" />
									</div>
								</div>

								<div class="form-group" id="2">
									<label class="control-label col-sm-2 required-control"><spring:message
											code="accounts.master.primAccCodeDesc" /> </label>
									<div class="col-sm-4">
										<form:input path="parentDesc" cssClass="form-control"
											id="parentfunDesc" readonly="false" />
									</div>
									<label class="control-label col-sm-2"><spring:message
											code="accounts.master.primAccFinalCode" /></label>
									<div class="col-sm-4">
										<form:input id="parentFinalCode" path="parentFinalCode"
											class="hasNumber form-control" readonly="true"
											maxlength="${maxParentLvlLength}" />
									</div>
								</div>
							</div>
						</div>
					</div>

					<fieldset id="divId" class="clear">
						<form:hidden path="codconfigId" />
						<ul id="ulId">

							<form:hidden path="newRecord" value="true" />
							<li>
								<h4>
									<spring:message code="account.master.childLabel" />
								</h4>

								<div id="child_label${count}" class=" ">
									<div class="panel-body">

										<form:hidden path="primaryAcHeadId" id="primaryAcHeadId" />

										<div class="form-group" id="3">
											<div class="col-sm-4">
												<label class="required-control"><spring:message
														code="accounts.master.primAccCodeLvl" /> </label>
												<form:hidden path="" id="levelData" value="${levelSize}" />
												<form:select path="listDto[${count}].childLevel"
													class="form-control" id="childFunLevel${count}"
													onchange="fetchMaxLengthAndResetChildParentLevel()"
													readonly="${viewMode}">
													<form:option value="0" id="">
														<spring:message code="accounts.master.selLevel" />
													</form:option>
													<c:forEach items="${getLevels}" varStatus="status"
														var="level">
														<form:option value="${status.count}"
															code="${status.count}">${level}</form:option>
													</c:forEach>
												</form:select>

											</div>
											<div class="col-sm-4">
												<label class="required-control"><spring:message
														code="accounts.master.primAccCode" /></label>
												<form:input id="childFunCode${count}"
													path="listDto[${count}].childCode"
													cssClass="hasNumber form-control" />
											</div>
											<div class="col-sm-4">
												<label class="required-control"><spring:message
														code="accounts.master.primAccCodeDesc" /> </label>
												<form:input path="listDto[${count}].childDesc"
													cssClass="form-control" id="funDesc${count}"
													readonly="${viewMode}" />
											</div>
										</div>

										<div class="form-group">
											<div class="col-sm-4">
												<label class="required-control"><spring:message
														code="accounts.master.primAccCodeParLvl" /> </label>

												<form:select path="listDto[${count}].childParentLevel"
													class="hasNumber form-control"
													id="childParentFunLevel${count}"
													onchange="getParentCode(${count})" readonly="${viewMode}">
													<form:option value="0">
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
														code="accounts.master.primAccParCode" /></label>

												<form:select path="listDto[${count}].childParentCode"
													onchange="getFinalCode(${count})"
													class="hasNumber form-control" id="childParentCode${count}"
													readonly="${viewMode}">
													<form:option value="0">
														<spring:message code="accounts.master.selLevel" />
													</form:option>

												</form:select>

											</div>

											<div class="col-sm-4">
												<label class="control-label"><spring:message
														code="accounts.master.compCode" /></label>
												<form:input id="childFinalCode${count}"
													path="listDto[${count}].childFinalCode"
													cssClass="hasNumberMaxEight form-control" readonly="true" />
											</div>

										</div>
										<div class="col-sm-4"></div>
										<div class="col-sm-4"></div>
										<div class="form-group" id="statusDivMain">
											<div class="col-sm-4 hideDivClass" id="statusDivAdd${count}">
												<label class="control-label required-control"><spring:message
														code="accounts.master.status" /></label>
												<form:select path="listDto[${count}].childPrimaryStatus"
													class="form-control" id="childPrimaryStatus${count}"
													disabled="${true}">
													<form:option value="0">
														<spring:message code="accounts.master.sel.status" />
													</form:option>
													<c:forEach items="${primaryCodeStatus}" var="status">
														<form:option value="${status.lookUpId}">${status.descLangFirst}</form:option>
													</c:forEach>
												</form:select>
											</div>
											<form:hidden path="listDto[${count}].childPrimaryStatus"
												id="hiddenChildPrimaryStatus${count}" />
										</div>

									</div>
								</div>
							</li>

						</ul>
					</fieldset>


					<INPUT type="hidden" id="count" value="0" /> <INPUT type="hidden"
						id="countForParentLevel" value="0" /> <INPUT type="hidden"
						id="countFinalCode" value="0" />


					<c:if test="${!viewMode}">
						<c:if test="${viewmode eq 'EDIT' or viewmode eq 'ADD' }">
							<div class="pull-right">
								<button id="addRow" type="button" onclick="addRowFunction()"
									class="btn btn-blue-2" title="Add">
									<i class="fa fa-plus"></i>
								</button>
								&nbsp;
								<button id="removeRow" type="button" title="Remove"
									onclick="removeRowFunction()" class="btn btn-danger">
									<i class="fa fa-minus"></i>
								</button>
							</div>
						</c:if>
					</c:if>



					<div class="text-center" id="saveDivForCreate">
						<input type="button" class="btn btn-success btn-submit"
							onclick="saveLeveledDataForPrimaryCode(this)" value="Save">
						</input>

						<input type="button" class="btn btn-danger"
								onclick="window.location.href='AccountHeadPrimaryAccountCodeMaster.html'"
								value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
					</div>


					<div class="text-center" id="saveDivForAdd">
						<input type="button" class="btn btn-success btn-submit"
							onclick="saveEditedData(this)" value="Save"> </input>

						<input type="button" class="btn btn-danger"
								onclick="window.location.href='AccountHeadPrimaryAccountCodeMaster.html'"
								value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
					</div>
				</div>
			</div>
			<div class="form-group" id="editModeDiv">

				<div class="panel-body" id="editChildNode">
					<form:hidden path="primaryAcHeadId" />

					<div class="form-group" id="3">
						<div class="col-sm-4">
							<label class="control-label"><spring:message
									code="accounts.master.primAccCodeLvl" /> </label>
							<form:hidden path="" id="levelData" value="${levelSize}" />
							<form:select path="editedDataChildLevel" class="form-control"
								id="editedChildFunLevel"
								onchange="fetchMaxLengthAndResetChildParentLevel()"
								disabled="${true}">
								<form:option value="0" id="">
									<spring:message code="accounts.master.selLevel" />
								</form:option>
								<c:forEach items="${getLevels}" varStatus="status" var="level">
									<form:option value="${status.count}" code="${status.count}">${level}</form:option>
								</c:forEach>
							</form:select>

						</div>
						<div class="col-sm-4">
							<label class="control-label"><spring:message
									code="accounts.master.primAccCode" /></label>
							<form:input id="editedChildFunCode" path="editedDataChildCode"
								cssClass="hasNumber form-control" readonly="${viewMode}"
								maxlength="${childCodeDigit}" />
						</div>
						<div class="col-sm-4">
							<label class="required-control"><spring:message
									code="accounts.master.primAccCodeDesc" /> </label>
							<form:input path="editedDataChildDesc" cssClass="form-control"
								id="editedDataChildDesc" readonly="false" />
						</div>

						<div class="col-sm-4" id="statusDiv">
							<label class="required-control"><spring:message
									code="accounts.master.status" /></label>
							<form:select path="editedChildStatus" class="form-control"
								id="editedChildStatus">
								<form:option value="0">
									<spring:message code="accounts.master.sel.status" />
								</form:option>
								<c:forEach items="${primaryCodeStatus}" var="status">
									<form:option value="${status.lookUpId}">${status.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
						</div>

					</div>

				</div>

				<div class="panel-body" id="editParentNode">
					<div class="form-group" id="4">
						<div class="col-sm-4">
							<label class="control-label"><spring:message
									code="accounts.master.primAccCodeParLvl" /> </label>

							<form:select path="editedDataChildParentLevel"
								class="hasNumber form-control" id="editedDataChildParentLevel"
								onchange="getParentCode(${count})" disabled="${true}">
								<form:option value="0">
									<spring:message code="accounts.master.selLevel" />
								</form:option>
								<c:forEach items="${getLevels}" varStatus="status" var="level">
									<form:option value="${status.count}" code="${status.count}"> ${level} </form:option>
								</c:forEach>
							</form:select>
						</div>
						<div class="col-sm-4">
							<label class="control-label"><spring:message
									code="accounts.master.primAccParCode" /></label>
							<form:input id="editedDataChildParentCode"
								path="editedDataChildParentCode"
								cssClass="hasNumberMaxEight form-control" readonly="true" />

						</div>

						<div class="col-sm-4">
							<label class="control-label"><spring:message
									code="accounts.master.compCode" /></label>
							<form:input id="editedDataChildCompositeCode"
								path="editedDataChildCompositeCode"
								cssClass="hasNumberMaxEight form-control" readonly="true" />
						</div>

					</div>

				</div>


				<div class="text-center" id="updateBtn">
					<input type="button" id="saveButttonOnDescChange"
						class="btn btn-success btn-submit" onclick="updateDesc(this)" value="Save">
					</input>
					<input type="button" class="btn btn-danger"
								onclick="window.location.href='AccountHeadPrimaryAccountCodeMaster.html'"
								value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
					<%-- <apptags:backButton url="AccountHeadPrimaryAccountCodeMaster.html"
						cssClass="btn btn-primary" /> --%>
				</div>
			</div>
			<div class="text-center" id="mainBackBtn">
				<input type="button" class="btn btn-danger"
								onclick="window.location.href='AccountHeadPrimaryAccountCodeMaster.html'"
								value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
			</div>
		</form:form>
	</div>
</div>
