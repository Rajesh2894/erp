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
<script src="js/account/primayHeadCodeMaster.js"></script>
<script src="js/mainet/validation.js"></script>

<div class="widget" id="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="accounts.master.primAccCode" />
		</h2>
		<apptags:helpDoc url="PrimaryAccountCodeMaster.html"
			helpDocRefURL="PrimaryAccountCodeMaster.html"></apptags:helpDoc>
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
			name="frmMaster" method="POST" action="PrimaryAccountCodeMaster.html">

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
			<form:hidden path="hasError" id="hasError" />
			<%-- <form:hidden path="child" id="child" /> --%>
			<form:hidden path="" value="${child}"
					id="child" />

			<input type="hidden" value="${viewmode}" id="modeFlag" />
			<c:if test="${viewmode eq 'ADD'}">
				<div class="form-group" id="superParentNode">
					<label class="col-sm-2 control-label"><spring:message
							code="account.create.parent" text="Create Super Parent" /></label>
					<div class="col-sm-10">
						<input class="margin-left-0 margin-top-10" type="checkbox"
							id="superNode" name="superNode">
					</div>
				</div>
			</c:if>
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
			<c:if test="${viewmode eq 'ADD'}">
				<div id="addModeDiv">

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						<div class="panel panel-default" id="superParentDiv">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#parent_label"><spring:message
											code="account.master.parentLabel" /></a>
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
												class="form-control" readonly="true" />
										</div>

										<label class="control-label col-sm-2 required-control"><spring:message
												code="account.head.type" text="Head Type" /></label>
										<div class="col-sm-4">
											<form:select path="cpdOtherAcHeadTypes"
												class="form-control mandColorClass" id="cpdOtherAcHeadTypes"
												onchange="setHeadType()">
												<form:option value="">
													<spring:message code="accounts.master.selLevel" />
												</form:option>
												<c:forEach items="${headType}" var="head">
													<form:option value="${head.otherField}">${head.descLangFirst}</form:option>
												</c:forEach>
											</form:select>
										</div>

									</div>

									<div class="form-group" id="2">
										<label class="control-label col-sm-2 required-control"><spring:message
												code="accounts.master.primAccCode" /></label>
										<div class="col-sm-4">
											<form:input id="parentCode" path="parentCode"
												onblur="setParentValue()"
												class="hasNumber form-control mandColorClass"
												readonly="${editMode}" maxlength="${maxParentLvlLength}" />
										</div>

										<label class="control-label col-sm-2 required-control"><spring:message
												code="accounts.master.primAccCodeDesc" /> </label>
										<div class="col-sm-4">
											<form:input path="parentDesc"
												cssClass="form-control mandColorClass" id="parentfunDesc"
												readonly="false" />
										</div>

									</div>
									<div class="form-group" id="3">

										<form:hidden path="" value="${maxParentLvlLength}"
											id="maxParentLenght" />
									</div>
									<div class="form-group" id="4">

										<label class="control-label col-sm-2 required-control"><spring:message
												code="account.primary.code" text="Primary Account Code Desc(Reg)"/> </label>
										<div class="col-sm-4">
											<form:input path="parentDescReg"
												cssClass="form-control mandColorClass" id="parentfunDesc"
												readonly="false" />
										</div>

									</div>
								</div>
							</div>
						</div>


						<fieldset id="divId" class="clear">
							<ul id="ulId">

								<form:hidden path="newRecord" value="true" />
								<li>
									<h4>
										<spring:message code="account.master.childLabel" />
									</h4>


									<div id="child_label${count}" class=" ">
										<div class="panel-body">

											<form:hidden path="primaryAcHeadId" id="primaryAcHeadId" />

											<div class="form-group">
												<label class="col-sm-2 required-control"><spring:message
														code="accounts.master.primAccCodeLvl" /> </label>
												<div class="col-sm-4">

													<form:hidden path="noOfLevel" id="levelData"
														value="${levelSize}" />
													<form:select path="listDto[${count}].childLevel"
														class="form-control" id="childFunLevel${count}">
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
												<label class="col-sm-2 required-control"><spring:message
														code="accounts.master.primAccCode" /></label>
												<div class="col-sm-4">

													<form:input id="childFunCode${count}"
														path="listDto[${count}].childCode"
														cssClass="hasNumber form-control mandColorClass mxlength"
														maxlength=""
														onchange="fetchMaxLengthAndResetChildParentLevel()" />
												</div>
												
											</div>

											<div class="form-group">
											<label class="col-sm-2"><spring:message
															code="accounts.master.compCode" /></label>
												<div class="col-sm-4">
													
													<form:input id="childFinalCode${count}"
														path="listDto[${count}].childFinalCode"
														cssClass="hasNumberMaxEight form-control" readonly="true" />
												</div>
												<label class="col-sm-2 required-control"><spring:message
															code="accounts.master.primAccCodeDesc" /> </label>
												<div class="col-sm-4">
													
													<form:input path="listDto[${count}].childDesc"
														cssClass="form-control mandColorClass"
														id="funDesc${count}" onchange="setSecondaryData()"
														readonly="${viewMode}" />
												</div>


											</div>
											
											<div class="form-group">
												<label class="col-sm-2 required-control"><spring:message
															code="" text="Primary Account Code Desc(Reg)" /> </label>
												<div class="col-sm-4">
													
													<form:input path="listDto[${count}].childDescReg"
														cssClass="form-control mandColorClass"
														id="funDescReg${count}" onchange="setSecondaryData()"
														readonly="${viewMode}" />
												</div>


											</div>

										</div>
									</div>
								</li>

							</ul>
							<div class="pull-right" id="addDelete">
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

							<div class="text-center" id="saveForChild">
								<input type="button" class="btn btn-success btn-submit"
									onclick="saveChildPrimaryCode(this)" value="Save" /> <input
									type="button" id="Reset"
									class="btn btn-warning createPrimaryCode" value="Reset"></input>
								<input type="button" class="btn btn-danger"
									onclick="window.location.href='PrimaryAccountCodeMaster.html'"
									value="<spring:message code="" text="Back"/>" id="cancelEdit" />
							</div>
						</fieldset>

						<INPUT type="hidden" id="count" value="0" /> <INPUT type="hidden"
							id="countForParentLevel" value="0" /> <INPUT type="hidden"
							id="countFinalCode" value="0" />


						<div class="text-center" id="saveForSuper">
							<input type="button" class="btn btn-success btn-submit"
								onclick="saveParentPrimaryCode(this)" value="<spring:message code="account.configuration.save" text="Save"/>" /> <input
								type="button" class="btn btn-danger"
								onclick="window.location.href='PrimaryAccountCodeMaster.html'"
								value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
						</div>

					</div>
				</div>
			</c:if>
			<form:hidden path="hiddenParentId" id="hiddenParentId" />
			<form:hidden path="hiddenParentCode" id="hiddenParentCode" />
			<form:hidden path="hiddenCodeLevel" id="hiddenCodeLevel" />
			<form:hidden path="codconfigId" />
			<form:hidden path="editedDataChildCompositeCode"
				id="editedDataChildCompositeCode" />
			<form:hidden path="cpdIdAcHeadTypes" id="childHeadType" />

			<c:if test="${viewmode eq 'EDIT'}">
				<div id="editModeDiv">

					<div class="panel-body" id="editChildNode">
						<form:hidden path="primaryAcHeadId" />

						<div class="form-group">
							
								<label class="control-label col-sm-2 required-control"><spring:message
										code="accounts.master.primAccCodeLvl" /> </label>
										<div class="col-sm-4">
								<form:hidden path="noOfLevel" id="levelData"
									value="${levelSize}" />
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
							
							
								<label class="control-label col-sm-2 required-control"><spring:message
										code="accounts.master.primAccCode" /></label>
										<div class="col-sm-4">
								<form:input id="editedChildFunCode" path="editedDataChildCode"
									cssClass="hasNumber form-control" readonly="true"
									maxlength="${childCodeDigit}" />
							</div>
							</div>
							
							<div class="form-group">
							
								<label class="control-label col-sm-2 required-control"><spring:message
										code="accounts.master.primAccCodeDesc" /> </label>
										<div class="col-sm-4">
								<form:input path="editedDataChildDesc"
									cssClass="form-control mandColorClass" id="editedDataChildDesc"
									readonly="false" />
							</div>
<label class="control-label col-sm-2"><spring:message
										code="" text="Primary code Head Desc(Reg)"/> </label>
										<div class="col-sm-4">
								<form:input path="childDescReg"
									cssClass="form-control mandColorClass" id="childDescReg"
									readonly="false" />
							</div>
							
							
							
															</div>
							<div class="form-group" >
							<label class="control-label col-sm-2 required-control"><spring:message
										code="accounts.master.status" /></label>
										<div class="col-sm-4" >
								<form:select path="editedChildStatus"
									cssClass="form-control mandColorClass" id="editedChildStatus">
									<form:option value="0">
										<spring:message code="accounts.master.sel.status" />
									</form:option>
									<c:forEach items="${primaryCodeStatus}" var="status">
										<form:option value="${status.lookUpId}">${status.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>
							
								<label class="control-label col-sm-2 required-control"><spring:message
										code="account.master.budgetCheckFlag" text="Budget Check Flag" /></label>
										<div class="col-sm-4">
								<form:select path="budgetCheckFlag"
									cssClass="form-control mandColorClass" id="budgetCheckFlag">
									<form:option value="0">
										<spring:message code="account.master.select" text="Select" />
									</form:option>
									<c:forEach items="${budgetCheckFlagList}" var="status1">
										<form:option value="${status1.lookUpCode}">${status1.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
								</div>
							
								
							
							
							</div>

						<div class="text-center" id="updateBtn">
							<input type="button" id="saveButttonOnDescChange"
								class="btn btn-success btn-submit" onclick="updateDesc(this)"
								value="Save" /> <input type="button" class="btn btn-danger"
								onclick="window.location.href='PrimaryAccountCodeMaster.html'"
								value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
						</div>
					</div>
				</div>
			</c:if>

			<div class="text-center" id="mainBackBtn">
				<input type="button" class="btn btn-danger"
					onclick="window.location.href='PrimaryAccountCodeMaster.html'"
					value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
			</div>
		</form:form>
	</div>
</div>
