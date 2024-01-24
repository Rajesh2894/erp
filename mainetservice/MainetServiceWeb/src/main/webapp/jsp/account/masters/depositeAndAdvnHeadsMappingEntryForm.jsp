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
<script src="js/account/depositeAndAdvncHeadMappingMaster.js"></script>


<apptags:breadcrumb></apptags:breadcrumb>

<div class="widget" id="widget">
	<div class="widget-header">
		<h4>
			<spring:message code=""
				text="Advance and Deposite Heads Mapping Entry" />
		</h4>
	<apptags:helpDoc url="AccountDepositeAndAdvnHeadsMappingEntryMaster.html" helpDocRefURL="AccountDepositeAndAdvnHeadsMappingEntryMaster.html"></apptags:helpDoc>		
	</div>


	<div class="widget-content padding">

		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="accountDepositeAndAdvnHeadsMappingEntryMasterBean"
			name="frmMaster" method="POST"
			action="AccountDepositeAndAdvnHeadsMappingEntryMaster.html">
			<div class="warning-div alert alert-danger alert-dismissible hide"
				id="clientSideErrorDivScrutiny"></div>
			<div class="warning-div alert alert-danger alert-dismissible hide"
				id="errorDiv">
				<button type="button" class="close" aria-label="Close"
					onclick="closeErrBox()">
					<span aria-hidden="true">&times;</span>
				</button>

				<ul>
					<li><i class='fa fa-exclamation-circle'></i>&nbsp;<form:errors
							path="*" /></li>
				</ul>
				<script>
					$(".warning-div ul").each(function () {
					    var lines = $(this).html().split("<br>");
					    $(this).html('<li>' + lines.join("</li><li><i class='fa fa-exclamation-circle'></i>&nbsp;") + '</li>');						
					});
		  			$('html,body').animate({ scrollTop: 0 }, 'slow');
		  		</script>
			</div>
			<h4></h4>
			<div class="table-responsive">
				<c:set var="d" value="0" scope="page" />
				<c:set var="count" value="0" scope="page" />
				<form:hidden path="mappingType" />
				<form:hidden path="advancedType" />
				<form:hidden path="depositeType" />
				<form:hidden path="deptId" />
				<table class="table table-bordered table-striped appendableClass"
					id="depositeAndAdvnMapEntryTable">
					<c:choose>
						<c:when
							test="${fn:length(accountDepositeAndAdvnHeadsMappingEntryMasterBean.listOfDto)> 0}">
							<c:forEach
								items="${accountDepositeAndAdvnHeadsMappingEntryMasterBean.listOfDto}"
								var="listOfDto" varStatus="status">
								<c:set value="${status.index}" var="count"></c:set>



								<tbody>
									<tr>

										<th width="150" class=""><spring:message
												code="budget.reappropriation.master.fundcode"
												text="Fund Code" /></th>
										<th width="150" class=""><spring:message
												code="budget.reappropriation.master.fieldcode"
												text="Field Code" /></th>
										<th width="150" class=""><spring:message
												code="budget.reappropriation.master.functioncode"
												text="Function Code" /></th>
										<th width="150" class="required-control"><spring:message
												code="account.budgetreappropriationmaster.primaryaccountcode"
												text="Primary Code" /></th>
										<th width="150" class="required-control"><spring:message
												code="account.budgetreappropriationmaster.secondaryaccountcode"
												text="Secondary Code" /></th>
										<th width="150" class="required-control"><spring:message
												code="deposite.advn.head.final.des" text="Final Description" /></th>
										<th width="20" class="required-control"><spring:message
												code="accounts.master.status" text="Status" /></th>


									</tr>


									<tr>

										<td id="fundCode"><form:hidden
												path="listOfDto[${count}].entityId" /> <form:select
												path="listOfDto[${count}].fundCode" class="form-control"
												id="fundCode${d}">
												<form:option value="">
													<spring:message code="account.select" text="Select" />
												</form:option>
												<c:forEach items="${fundMasterLastLvls}"
													var="fundMasterLastLvls">
													<form:option code="${fundMasterLastLvls.key}"
														value="${fundMasterLastLvls.value}">${fundMasterLastLvls.value}</form:option>
												</c:forEach>
											</form:select></td>
										<td id="fieldCode"><form:select
												path="listOfDto[${count}].fieldCode" class="form-control"
												id="fieldCode${d}">
												<form:option value="">
													<spring:message code="account.select" text="Select" />
												</form:option>
												<c:forEach items="${fieldMasterLastLvls}"
													var="fieldMasterData">
													<form:option code="${fieldMasterData.key}"
														value="${fieldMasterData.value}">${fieldMasterData.value}</form:option>
												</c:forEach>
											</form:select></td>
										<td id="functionCode"><form:select
												path="listOfDto[${count}].functionCode" class="form-control"
												id="functionId${d}">
												<form:option value="">
													<spring:message code="account.select" text="Select" />
												</form:option>
												<c:forEach items="${functionMasterLastLvls}"
													var="functionMasterData">
													<form:option value="${functionMasterData.value}">${functionMasterData.value}</form:option>

												</c:forEach>
											</form:select></td>
										<td id="primaryCode"><form:select
												path="listOfDto[${count}].primaryCode" class="form-control"
												id="primaryCodeId${d}">
												<form:option value="">
													<spring:message code="account.select" text="Select" />
												</form:option>
												<c:forEach items="${primaryCodeMasterLastLvls}"
													var="primaryCodeMasterData">
													<form:option value="${primaryCodeMasterData.value}">${primaryCodeMasterData.value}</form:option>
												</c:forEach>
											</form:select></td>
										<td id="secondaryCode"><form:select
												path="listOfDto[${count}].secondaryCode"
												class="form-control" id="secondaryCodeId${d}">
												<form:option value="">
													<spring:message code="account.select" text="Select" />
												</form:option>
												<c:forEach items="${secondaryCodeMasterLvls}"
													var="secondaryCodeMasterData">
													<form:option value="${secondaryCodeMasterData.value}">${secondaryCodeMasterData.value}</form:option>
												</c:forEach>
											</form:select></td>
										<td id="remark"><form:input
												path="listOfDto[${count}].remark" id="remark${d}" /></td>
										<td><label class="inline-checkbox"><form:checkbox
													path="listOfDto[${count}].status"
													value="${listOfDto.status}" class="margin-left-5" /> </label></td>
									</tr>
								</tbody>
								<c:set var="d" value="${count + 1}" scope="page" />
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tbody>
								<tr>

									<th width="50"><spring:message
											code="budget.reappropriation.master.fundcode"
											text="Fund Code" /></th>
									<th width="250"><spring:message
											code="budget.reappropriation.master.fieldcode"
											text="Field Code" /></th>
									<th width="150"><spring:message
											code="budget.reappropriation.master.functioncode"
											text="Function Code" /></th>
									<th width="150" class="required-control"><spring:message
											code="account.budgetreappropriationmaster.primaryaccountcode"
											text="Primary Code" /></th>
									<th width="150" class="required-control"><spring:message
											code="account.budgetreappropriationmaster.secondaryaccountcode"
											text="Secondary Code" /></th>
									<th width="150" class="required-control"><spring:message
											code="deposite.advn.head.final.des" text="Final Description" /></th>
									<th width="150"><spring:message
											code="deposite.advn.head.text" text="Text" /></th>

									<th rowspan="4" width="105"
										class="vertical-align-middle text-center"><a
										href="javascript:void(0);"
										class="addMappingLabel btn btn-success btn-xs"><i
											class="fa fa-plus-circle"></i></a> <a href="javascript:void(0);"
										class="addMappingLabel btn btn-danger btn-xs"><i
											class="fa fa-minus-circle"></i></a></th>
								<tr>
									<td id="fundCode"><form:select
											path="listOfDto[${count}].fundId" class="form-control"
											id="fundId${d}">
											<form:option value="">
												<spring:message code="account.select" text="Select" />
											</form:option>
											<c:forEach items="${fundMasterLastLvls}" var="fundMasterData">
												<form:option value="${fundMasterData.key}">${fundMasterData.value}</form:option>
											</c:forEach>
										</form:select></td>
									<td id="fieldCode"><form:select
											path="listOfDto[${count}].fieldId" class="form-control"
											id="fieldId${d}">
											<form:option value="">
												<spring:message code="account.select" text="Select" />
											</form:option>
											<c:forEach items="${fieldMasterLastLvls}"
												var="fieldMasterData">
												<form:option value="${fieldMasterData.key}">${fieldMasterData.value}</form:option>
											</c:forEach>
										</form:select></td>
									<td id="functionCode"><form:select
											path="listOfDto[${count}].functionId" class="form-control"
											id="functionId${d}">
											<form:option value="">
												<spring:message code="account.select" text="Select" />
											</form:option>
											<c:forEach items="${functionMasterLastLvls}"
												var="functionMasterData">
												<form:option value="${functionMasterData.key}">${functionMasterData.value}</form:option>
											</c:forEach>
										</form:select></td>
									<td id="primaryCode"><form:select
											path="listOfDto[${count}].primaryCodeId" class="form-control"
											id="primaryCodeId${d}">
											<form:option value="">
												<spring:message code="account.select" text="Select" />
											</form:option>
											<c:forEach items="${primaryCodeMasterLastLvls}"
												var="primaryCodeMasterData">
												<form:option value="${primaryCodeMasterData.key}">${primaryCodeMasterData.value}</form:option>
											</c:forEach>
										</form:select></td>
									<td id="secondaryCode"><form:select
											path="listOfDto[${count}].secondaryCodeId"
											class="form-control" id="secondaryCodeId${d}">
											<form:option value="">
												<spring:message code="account.select" text="Select" />
											</form:option>
											<c:forEach items="${secondaryCodeMasterLvls}"
												var="secondaryCodeMasterData">
												<form:option value="${secondaryCodeMasterData.key}">${secondaryCodeMasterData.value}</form:option>
											</c:forEach>
										</form:select></td>
									<td id="remark"><form:input
											path="listOfDto[${count}].remark" id="remark${d}" /></td>
									<td><label class="inline-checkbox"><form:checkbox
												path="listOfDto[${count}].status" value="Y"
												class="margin-left-5" /></label></td>
								</tr>
							</tbody>
						</c:otherwise>
					</c:choose>
				</table>

			</div>

			<div class="text-center padding-top-10">
				<button class="btn btn-success btn-submit" type="button"
					onclick="saveLeveledData(this)">
					<spring:message code="account.bankmaster.save" text="Save" />
				</button>
				<button class="btn btn-warning" type="reset">
					<spring:message code="account.bankmaster.reset" text="Reset" />
				</button>
				<button class="btn btn-danger" type="button"
					onclick="window.location.href='AccountDepositeAndAdvnHeadsMappingEntryMaster.html'">
					<spring:message code="account.bankmaster.back" text="Back" />
				</button>
			</div>


		</form:form>
	</div>
</div>
