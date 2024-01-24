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
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script src="js/account/accountfundMaster.js"></script>
<script src="js/menu/jquery.dynatree.js"></script>
<script src="js/mainet/validation.js"></script>
<link href="css/menu/ui.dynatree.css" rel="stylesheet" type="text/css">
<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="fund.master.widget.header" text="" />
		</h2>
		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"><i
				class="fa fa-question-circle fa-lg"></i></a>
		</div>
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
			modelAttribute="accountFundMasterBean" name="frmMaster" method="POST"
			action="AccountFundMaster.html">
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
										code="fund.master.parent.label" text="" /></a>
							</h4>
						</div>
						<div id="parent_label" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group" id="1">
									<label class="control-label col-sm-2  required-control"><spring:message
											code="fund.master.fund.level" text="Fund Level-1" /> </label>

									<div class="col-sm-4">
										<form:input id="parentFundLevel0" path="parentLevel"
											class="form-control" disabled="${viewMode}"
											data-rule-required="true" />
										<form:hidden path="parentLevelCode" class="form-control" />
										<form:hidden path="codingDigits" class="form-control"
											disabled="" id="${codingDigitMap}" />
									</div>

									<label class="control-label col-sm-2 required-control"><spring:message
											code="fund.master.fund.code" text="" /></label>
									<div class="col-sm-4">
										<form:input id="parentFundCode" path="parentCode"
											onblur="setParentValue()" cssClass="hasNumber form-control"
											maxlength="${maxParentLvlLength}" disabled="${viewMode}"
											data-rule-required="true" />
									</div>
								</div>

								<div class="form-group" id="2">
									<label class="control-label col-sm-2 required-control"><spring:message
											code="fund.master.fund.Desc" text="" /> </label>
									<div class="col-sm-4">
										<form:input path="parentDesc" cssClass="form-control"
											id="parentfunDesc" disabled="${viewMode}"
											data-rule-required="true" />
									</div>

									<label class="control-label col-sm-2"><spring:message
											code="" text="Final Code" /></label>
									<div class="col-sm-4">
										<form:input id="parentFinalCode" path="parentFinalCode"
											cssClass="hasNumber form-control" readonly="true" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<fieldset id="divId" class="clear">
					<form:hidden path="codconfigId" />
					<form:hidden path="codcofdetId" />
					<INPUT type="hidden" id="countFinalCode" value="" />

					<ul id="ulId">
						<c:if test="${viewmode eq 'EDIT'  or viewmode eq 'ADD'}">
							<li>
								<h4>
									<spring:message code="account.master.childLabel" />
								</h4> <form:hidden path="newRecord" value="true" />
								<div class="panel-body">
									<form:hidden path="fundId" />
									<div class="form-group" id="3">
										<div class="col-sm-4">
											<label class="required-control"><spring:message
													code="fund.master.fund.childlevel" text="" /> </label>
											<form:hidden path="" id="levelData" value="${levelSize}" />

											<form:select path="listDto[${count}].childLevelCode"
												class="form-control" id="childFunLevel${count}"
												onchange="fetchMaxLengthAndResetChildParentLevel()"
												disabled="${viewMode}" data-rule-required="true">
												<form:option value="" id="">
													<spring:message code="fund.master.fund.selectlevel" text="" />
												</form:option>
												<c:forEach items="${levelsParentMap}" varStatus="status"
													var="levelChild">
													<form:option value="${levelChild.key}"
														code="${levelChild.key}">${levelChild.value}</form:option>
												</c:forEach>
											</form:select>
										</div>

										<div class="col-sm-4">
											<form:hidden path="listDto[${count}].childFunId" />
											<label class="required-control"><spring:message
													code="" text="Fund Code" /></label>
											<form:input id="childFunCode${count}"
												path="listDto[${count}].childCode"
												cssClass="hasNumber form-control" data-rule-required="true" />
										</div>

										<div class="col-sm-4">
											<label class="required-control"><spring:message
													code="fund.master.fund.funddesc" text="Fund Desc" /> </label>
											<form:input id="childFundDesc${count}"
												path="listDto[${count}].childDesc" cssClass="form-control"
												disabled="${viewMode}" data-rule-required="true" />
										</div>
									</div>

									<div class="form-group" id="4">
										<div class="col-sm-4">
											<label class="required-control"><spring:message
													code="fund.master.fund.parentLevel" text="" /> </label>

											<form:select path="listDto[${count}].childParentLevelCode"
												name="childParentLevel" class="form-control"
												id="parentFunLevel${count}" onchange="getParentCode()"
												disabled="" data-rule-required="true">
												<form:option value="">
													<spring:message code="fund.master.fund.selectlevel" text="" />
												</form:option>

												<c:forEach items="${levelsParentMap}" varStatus="status"
													var="levelParent">
													<form:option value="${levelParent.key}"
														code="${levelParent.key}">${levelParent.value}</form:option>
												</c:forEach>

											</form:select>
										</div>

										<div class="col-sm-4">
											<label class="control-label"><spring:message
													code="accounts.master.primAccParCode" /></label>
											<form:select path="listDto[${count}].childParentCode"
												class="hasNumber form-control" id="childParentCode${count}"
												onchange="getFinalCode(${count})" disabled="${viewMode}"
												data-rule-required="true">
												<form:option value="">
													<spring:message code="accounts.master.selLevel" />
												</form:option>
											</form:select>
										</div>
										<div class="col-sm-4">
											<label class="control-label"><spring:message
													code="fund.master.fund.compositecode" text="" /></label>
											<form:input id="childFinalCode${count}"
												path="listDto[${count}].childFinalCode"
												cssClass="hasMyNewNumber form-control"
												readonly="${viewMode ne 'true'}" />
										</div>
									</div>
									<div class="col-sm-4"></div>
									<div class="col-sm-4"></div>
									<div class="form-group" id="statusDivMain">
										<div class="col-sm-4 hideDivClass" id="statusDivAdd${count}">
											<label class="required-control"><spring:message
													code="accounts.master.status" /></label>
											<form:select path="listDto[${count}].childFundStatus"
												class="form-control" id="childFundStatus${count}"
												disabled="${true}">
												<form:option value="">
													<spring:message code="accounts.master.sel.status" />
												</form:option>
												<c:forEach items="${fundStatus}" var="status">
													<form:option value="${status.lookUpId}">${status.descLangFirst}</form:option>
												</c:forEach>
											</form:select>
										</div>
										<form:hidden path="listDto[${count}].childFundStatus"
											id="hiddenChildFundStatus${count}" />
									</div>
								</div>
							</li>
						</c:if>
					</ul>

				</fieldset>

				<INPUT type="hidden" id="count" value="0" />
				<INPUT type="hidden" id="countFinalCode" value="" />
				<c:if test="${!viewMode}">

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
				<c:if test="${!viewMode and viewmode ne 'EDIT'}">

					<div class="text-center">
						<input type="button" class="btn btn-success btn-submit"
							onclick="saveLeveledDataForFundMaster(this)" value="Save">
						</input> <input type="button" id="Reset"
							class="btn btn-warning addFundMasterClass" value="Reset"></input>
						<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountFundMaster.html'"
							value="<spring:message code="" text="Back"/>" id="cancelEdit" />
						<%-- <apptags:backButton url="AccountFundMaster.html"
							cssClass="btn btn-primary" /> --%>
					</div>

				</c:if>

				<c:if test="${viewmode eq 'EDIT'}">
					<div class="text-center">
						<input type="button" class="btn btn-success btn-submit"
							onclick="saveEditedDataForFundMaster(this)" value="Save">
						</input>

						<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountFundMaster.html'"
							value="<spring:message code="" text="Back"/>" id="cancelEdit" />
					</div>

				</c:if>

				<c:if test="${viewMode}">
					<div class="text-center">
						<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountFundMaster.html'"
							value="<spring:message code="" text="Back"/>" id="cancelEdit" />
					</div>
				</c:if>
			</c:if>

			<c:if test="${viewMode}">
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="menu.structure.label" /> :</label>
					<div class="col-sm-10">
						<div id="tree" class="notes createStructure">
							<c:set var="setOfNode" value="${setOfNode}" scope="request" />
							<jsp:include page="treeViewRepresentationFundMaster.jsp"></jsp:include>
						</div>
					</div>
				</div>



				<div class="panel-body" id="editChildNode">

					<form:hidden path="fundId" />

					<div class="form-group" id="3">
						<div class="col-sm-4">
							<label class="control-label"><spring:message
									code="fund.master.fund.fundlevel" /> </label>
							<form:hidden path="" id="levelData" value="${levelsParentMap}" />
							<form:select path="editedDataChildLevel" class="form-control"
								id="editedChildFunLevel"
								onchange="feteditedChildStatuschMaxLengthAndResetChildParentLevel()"
								disabled="${true}">
								<form:option value="" id="">
									<spring:message code="accounts.master.selLevel" />
								</form:option>
								<c:forEach items="${levelsParentMap}" varStatus="status"
									var="levelParent">
									<form:option value="${levelParent.key}"
										code="${levelParent.key}">${levelParent.value}</form:option>
								</c:forEach>
							</form:select>

						</div>
						<div class="col-sm-4">
							<label class="control-label"><spring:message
									code="fund.master.fund.code" /></label>
							<form:input id="editedChildFunCode" path="editedDataChildCode"
								cssClass="hasNumber form-control" readonly="${viewMode}"
								maxlength="${childCodeDigit}" />
						</div>
						<div class="col-sm-4">
							<label class="control-label"><spring:message
									code="fund.master.fund.description" /> </label>
							<form:input path="editedDataChildDesc" cssClass="form-control"
								id="editedDataChildDesc" readonly="true" />
						</div>

						<div class="col-sm-4" id="statusDiv">
							<label class=""><spring:message
									code="accounts.master.status" /></label>
							<form:select path="editedChildStatus" class="form-control"
								id="editedChildStatus" disabled="true">
								<form:option value="">
									<spring:message code="accounts.master.sel.status" />
								</form:option>
								<c:forEach items="${fundStatus}" var="status">
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
									code="fund.master.fund.parentLevel" /> </label>

							<form:select path="editedDataChildParentLevel"
								class="hasNumber form-control" id="editedDataChildParentLevel"
								onchange="getParentCode(${count})" disabled="${true}">
								<form:option value="">
									<spring:message code="accounts.master.selLevel" />
								</form:option>
								<c:forEach items="${levelsParentMap}" varStatus="status"
									var="levelParent">
									<form:option value="${levelParent.key}"
										code="${levelParent.key}">${levelParent.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
						<div class="col-sm-4">
							<label class="control-label"><spring:message
									code="field.master.parent.code" /></label>
							<form:input id="editedDataChildParentCode"
								path="editedDataChildParentCode"
								cssClass="hasNumberMaxEight form-control" readonly="true" />

						</div>

						<div class="col-sm-4">
							<label class="control-label"><spring:message
									code="field.master.comp.code" /></label>
							<form:input id="editedDataChildCompositeCode"
								path="editedDataChildCompositeCode"
								cssClass="hasNumberMaxEight form-control" readonly="true" />
						</div>

					</div>

				</div>

				<div class="text-center">
					<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountFundMaster.html'"
							value="<spring:message code="account.back" text="Back"/>" id="cancelEdit" />
				</div>
			</c:if>
		</form:form>
	</div>
</div>


