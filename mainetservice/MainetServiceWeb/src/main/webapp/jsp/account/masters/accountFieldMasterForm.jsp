<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/menu/jquery.dynatree.js"></script>
<link href="css/menu/ui.dynatree.css" rel="stylesheet" type="text/css">
<script src="js/account/fieldMaster.js"></script>
<script src="js/mainet/validation.js"></script>

<script>
	$(document).ready(function(){
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}
	});
</script>

<c:url value="${saveAction}" var="url_form_submit" />
<c:url value="${mode}" var="form_mode" />
<div class="widget" id="content">

	<c:if test="${accountFieldMasterBean.hasError eq 'false'}">
		<apptags:breadcrumb></apptags:breadcrumb>

	</c:if>


	<div class="widget-header">
		<h2>
			<spring:message code="field.master.widget.header" text="" />
		</h2>
		<apptags:helpDoc url="AccountFieldMaster.html" helpDocRefURL="AccountFieldMaster.html"></apptags:helpDoc>
	</div>
	<div class="widget-content padding">
		<div class="mand-label clearfix">
			<span><spring:message code="account.common.mandmsg" /> <i
				class="text-red-1">*</i> <spring:message
					code="account.common.mandmsg1" /></span>
		</div>

		<form:form method="POST" action="AccountFieldMaster.html"
			name="fieldMaster" id="fieldMaster" class="form-horizontal"
			modelAttribute="accountFieldMasterBean">

			<form:hidden path="" value="${keyTest}" id="keyTest" />

			<div class="warning-div alert alert-danger alert-dismissible hide"
				id="clientSideErrorDivScrutiny"></div>

			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>

			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<c:if test="${!viewMode}">

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#parent_label"><spring:message
										code="field.master.parent.label" text="" /></a>
							</h4>
						</div>
						<div id="parent_label" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"><spring:message
											code="field.master.field.level" text="" /> </label>
									<div class="col-sm-4">
										<form:input id="parentFieldLevel0" path="parentLevel"
											class="form-control" disabled="${viewMode}"
											data-rule-required="true" />
										<form:hidden path="parentLevelCode" class="form-control"
											disabled="" />
										<form:hidden path="codingDigits" class="form-control"
											disabled="${viewMode}" id="${codingDigitMap}" />
									</div>

									<label class="control-label col-sm-2 required-control"><spring:message
											code="field.master.field.code" text="" /></label>
									<div class="col-sm-4">
										<form:input id="parentCode" path="parentCode"
											onblur="setParentValue(${count})"
											onchange="checkParentCodeDuplicateExist(this)"
											maxlength="${maxParentLvlLength}"
											cssClass="hasNumber form-control" disabled="${viewMode}"
											data-rule-required="true" />
									</div>
									<form:hidden path="" value="${maxParentLvlLength}"
										id="maxParentLenght" />
								</div>

								<div class="form-group" id="2">
									<label class="control-label col-sm-2 required-control"><spring:message
											code="field.master.field.desc" text="" /> </label>
									<div class="col-sm-4">
										<form:input path="parentDesc" cssClass="form-control"
											id="parentFieldDesc0" disabled="${viewMode}"
											data-rule-required="true" />
									</div>

									<label class="control-label col-sm-2"><spring:message
											code="field.master.final.code" text="" /></label>
									<div class="col-sm-4">
										<form:input id="parentFinalCode0" path="parentFinalCode"
											cssClass="hasNumber form-control" readonly="true"
											maxlength="${maxParentLvlLength}" />
									</div>
								</div>
							</div>
						</div>

					</div>
					<form:hidden path="codconfigId" />
					<form:hidden path="codcofdetId" />

					<fieldset id="divId" class="clear">
						<ul id="ulId">



							<c:if test="${viewmode eq 'EDIT' or viewmode eq 'ADD'}">
								<form:hidden path="newRecord" value="true" />
								<li>
									<h4>
										<spring:message code="account.master.childLabel" />
									</h4> <form:hidden path="fieldId" />
									<div class="panel-body">
										<div class="form-group" id="3">
											<div class="col-sm-4">
												<label class="required-control"><spring:message
														code="field.master.field.level" /> </label>
												<form:select path="listDto[${count}].childLevelCode"
													class="form-control" id="childFieldLevel${count}"
													onchange="fetchMaxLengthAndResetChildParentLevel()"
													disabled="${viewMode}" data-rule-required="true">
													<form:option value="" id="">
														<spring:message code="accounts.master.selLevel" />
													</form:option>
													<c:forEach items="${levelsParentMap}" varStatus="status"
														var="levelChild">
														<form:option value="${levelChild.key}"
															code="${levelChild.key}">${levelChild.value}</form:option>
													</c:forEach>
												</form:select>

											</div>
											<div class="col-sm-4">
												<form:hidden path="listDto[${count}].childFieldId" />
												<label class="required-control"><spring:message
														code="field.master.field.code" /></label>
												<form:input id="childFieldCode${count}"
													path="listDto[${count}].childCode"
													cssClass="hasNumber form-control"
													maxlength="${childCodeDigit}"
													onchange="clearParentCode(${count})"
													data-rule-required="true" />
											</div>
											<div class="col-sm-4">
												<label class="required-control"><spring:message
														code="field.master.field.desc" /> </label>
												<form:input path="listDto[${count}].childDesc"
													cssClass="form-control" id="childFieldDesc${count}"
													disabled="${viewMode}" data-rule-required="true" />
											</div>
										</div>

										<div class="form-group">
											<div class="col-sm-4">
												<label class="required-control"><spring:message
														code="field.master.field.parent.level" /> </label>

												<form:select path="listDto[${count}].childParentLevelCode"
													class="hasNumber form-control"
													id="childParentLevel${count}"
													onchange="getParentFieldCode(${count})"
													data-rule-required="true">
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
												<label class="required-control"><spring:message
														code="field.master.parent.code" /></label>


												<form:select path="listDto[${count}].childParentCode"
													class="hasNumber form-control" id="childParentCode${count}"
													onchange="getFinalCode(${count})" data-rule-required="true">
													<form:option value="">
														<spring:message code="accounts.master.selLevel" />
													</form:option>

												</form:select>

											</div>
											<div class="col-sm-4">
												<label><spring:message
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
												<label><spring:message code="accounts.master.status" /></label>
												<form:select path="listDto[${count}].childFieldStatus"
													class="form-control" id="childFieldStatus${count}"
													disabled="${true}">
													<form:option value="">
														<spring:message code="accounts.master.sel.status" />
													</form:option>
													<c:forEach items="${fieldStatus}" var="status">
														<form:option value="${status.lookUpId}">${status.descLangFirst}</form:option>
													</c:forEach>
												</form:select>
											</div>
											<form:hidden path="listDto[${count}].childFieldStatus"
												id="hiddenChildFieldStatus${count}" />
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
								class="btn btn-blue-2" title="Add">
								<i class="fa fa-plus"></i>
							</button>
							&nbsp;
							<button onclick="removeRowFunction();" id="removeRow"
								type="button" title="Remove" class="btn btn-danger">
								<i class="fa fa-minus"></i>
							</button>
						</div>
					</c:if>

					<c:if test="${!viewMode and viewmode ne 'EDIT'}">
						<div class="text-center">
							<input type="button" id="saveBtn" class="btn btn-success btn-submit"
								onclick="saveFieldMaster(this)" value="<spring:message
									code="account.bankmaster.save" text="Save" />"> <input
								type="button" id="Reset" class="btn btn-warning createData"
								value="<spring:message
									code="account.bankmaster.reset" text="Reset" />"></input>
							<spring:url var="cancelButtonURL" value="AccountFieldMaster.html" />
							<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
									code="account.bankmaster.back" text="Back" /></a>
						</div>
					</c:if>

					<c:if test="${viewmode eq 'EDIT'}">
						<div class="text-center">
							<input type="button" class="btn btn-success btn-submit"
								onclick="saveEditedData(this)" value="<spring:message
									code="account.bankmaster.save" text="Save" />">

							<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountFieldMaster.html'"
							value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
							<%-- <apptags:backButton url="AccountFieldMaster.html"
								cssClass="btn btn-primary" /> --%>
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

							<c:set var="node" value="${node}" scope="request" />
							<jsp:include page="treeViewRepresentationFieldMaster.jsp"></jsp:include>
						</div>
					</div>
				</div>

				<div class="panel-body" id="editChildNode">

					<form:hidden path="fieldId" />

					<div class="form-group" id="3">
						<div class="col-sm-4">
							<label class="label-control"><spring:message
									code="field.master.field.level" /> </label>
							<form:hidden path="" id="levelData" value="${levelsParentMap}" />
							<form:select path="editedDataChildLevel" class="form-control"
								id="editedChildFunLevel"
								onchange="fetchMaxLengthAndResetChildParentLevel()"
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
							<label class="label-control"><spring:message
									code="field.master.field.code" /></label>
							<form:input id="editedChildFunCode" path="editedDataChildCode"
								cssClass="hasNumber form-control" readonly="${viewMode}"
								maxlength="${childCodeDigit}" />
						</div>
						<div class="col-sm-4">
							<label class="required-control"><spring:message
									code="field.master.field.desc" /> </label>
							<form:input path="editedDataChildDesc" cssClass="form-control"
								id="editedDataChildDesc" readonly="false" />
						</div>

						<div class="col-sm-4" id="statusDiv">
							<label class="required-control"><spring:message
									code="accounts.master.status" /></label>
							<form:select path="editedChildStatus" class="form-control"
								id="editedChildStatus">
								<form:option value="">
									<spring:message code="accounts.master.sel.status" />
								</form:option>
								<c:forEach items="${fieldStatus}" var="status">
									<form:option value="${status.lookUpId}">${status.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
						</div>

					</div>

				</div>
				<div class="panel-body" id="editParentNode">
					<div class="form-group" id="4">


						<div class="col-sm-4">
							<label class="label-control"><spring:message
									code="field.master.field.parent.level" /> </label>

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
							<label class="label-control"><spring:message
									code="field.master.parent.code" /></label>
							<form:input id="editedDataChildParentCode"
								path="editedDataChildParentCode"
								cssClass="hasNumberMaxEight form-control" readonly="true" />

						</div>

						<div class="col-sm-4">
							<label class="label-control"><spring:message
									code="field.master.comp.code" /></label>
							<form:input id="editedDataChildCompositeCode"
								path="editedDataChildCompositeCode"
								cssClass="hasNumberMaxEight form-control" readonly="true" />
						</div>

					</div>

				</div>

				<div class="text-center">

					<c:if test="${viewModeLevel != 'Y'}">
						<input type="button" id="saveButttonOnDescChange"
							class="btn btn-success btn-submit" onclick="updateDescForFieldMaster(this)"
							value="<spring:message
									code="account.bankmaster.save" text="Save" />">
					</c:if>
					<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountFieldMaster.html'"
							value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
					
				</div>

			</c:if>
		</form:form>

	</div>
</div>

