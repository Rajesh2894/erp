<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css">
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="js/material_mgmt/service/DepartmentalReturn.js"
	type="text/javascript"></script>
<div id="searchIndPage">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message
						code="material.management.departmental.return.summary"
						text="Departmental Return Summary" />
				</h2>
				<apptags:helpDoc url="DeptReturn.html"></apptags:helpDoc>
			</div>
			<div class="pagediv">
				<div class="widget-content padding">
					<form:form id="deptReturnSummaryFrm" name="DeptReturn"
						class="form-horizontal" action="DeptReturn.html" method="post">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv"></div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="dreturnno"><spring:message
									code="material.management.return.indent.no" text="Indent Return No." /></label>
							<div class="col-sm-4">
								<form:select path="deptReturnDto.dreturnno" id="dreturnno"
									class="form-control"
									cssClass="form-control chosen-select-no-results"
									data-rule-required="true">
									<form:option value="" selected="true">
										<spring:message code="material.management.select"
											text="select" />
									</form:option>
									<c:forEach items="${command.listDeptReturnDTO}" var="data">
										<form:option value="${data.dreturnno}">${data.dreturnno}</form:option>
									</c:forEach>
								</form:select>
							</div>


							<label class="control-label col-sm-2" for="indentno"><spring:message
									code="department.indent.no" text="Indent No." /></label>
							<div class="col-sm-4">
								<form:select path="deptReturnDto.indentid" id="indentno"
									class="form-control"
									cssClass="form-control chosen-select-no-results"
									data-rule-required="true">
									<form:option value="" selected="true">
										<spring:message code="material.management.select"
											text="select" />
									</form:option>
									<c:forEach items="${command.listDeptReturnDTO}" var="data">
										<form:option value="${data.indentid}">${data.indentNo}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<apptags:date fieldclass="datepicker"
								labelCode="material.item.fromDate"
								datePath="deptReturnDto.fromDate" cssClass="fromDateClass" />

							<apptags:date fieldclass="datepicker"
								labelCode="material.item.toDate" datePath="deptReturnDto.toDate"
								cssClass="toDateClass" />
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2"><spring:message
									code="department.indent.storeName" text="Store Name" /></label>
							<div class="col-sm-4 ">
								<form:select path="deptReturnDto.storeid" id="storeid"
									class="form-control"
									cssClass="form-control chosen-select-no-results"
									data-rule-required="true">
									<form:option value="" selected="true">
										<spring:message code="material.management.select"
											text="select" />
									</form:option>
									<c:forEach items="${command.storeIdNameList}" var="data">
										<form:option value="${data[0]}">${data[1]}</form:option>
									</c:forEach>
								</form:select>
							</div>
							<label class="col-sm-2 control-label "><spring:message
									code="department.indent.status" text="Indent Status" /></label>
							<div class="col-sm-4">
								<form:select path="deptReturnDto.status"
									cssClass="form-control chosen-select-no-results" id="status">
									<form:option value="0">
										<spring:message code='council.dropdown.select' />
									</form:option>
									<c:forEach items="${command.getLevelData('IDS')}" var="lookUp">
										<form:option value="${lookUp.lookUpCode}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="text-center clear padding-10">
							<button type="button" class="btn btn-blue-2" onclick="searchIndentReturn(this);"
								title="<spring:message code="material.management.search" text="Search" />" >
								<i class="fa fa-search"></i>
								<spring:message code="material.management.search" text="Search" />
							</button>
							<button type="button" class="btn btn-warning" onclick="window.location.href='DeptReturn.html'"
								title="<spring:message code="material.management.reset" text="Reset" />" >
								<i class="fa fa-refresh"></i>
								<spring:message code="material.management.reset" text="Reset" />
							</button>
							<button type="button" class="button-input btn btn-success"
								title="<spring:message code="material.management.add" text="Add" />"
								onclick="addDeptReturn('DeptReturn.html','addDeptReturn');">
								<i class="fa fa-plus-circle"></i>
								<spring:message code="material.management.add" text="Add" />
							</button>
						</div>

						<div class="table-responsive clear">
							<table class="table table-striped table-bordered"
								id="deptIndentReturnTable">
								<thead>
									<tr>
										<th align="center"><spring:message
												code="store.master.srno" text="Sr No." /></th>
										<th align="center"><spring:message
												code="material.management.return.indent.no"
												text="Indent Return No." /></th>
										<th align="center"><spring:message
												code="department.indent.date" text="Indent Return Date" /></th>
										<th align="center"><spring:message
												code="department.indent.no" text="Indent No." /></th>
										<th align="center"><spring:message
												code="department.indent.storeName" text="Store Name" /></th>
										<th align="center"><spring:message code="material.management.return.indent.status"
												text="Indent Return Status" /></th>
										<th align="center"><spring:message
												code="store.master.action" text="Action" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.listDeptReturnDTO}"
										var="summaryDTO" varStatus="index">

										<tr>
											<td class="text-center">${index.count}</td>
											<td align="center">${summaryDTO.dreturnno}</td>
											<td align="center"><fmt:formatDate
													value="${summaryDTO.dreturndate}" pattern="dd-MM-yyyy" /></td>
											<td align="center">${summaryDTO.indentNo}</td>
											<td align="center">${summaryDTO.storeName}</td>
											<td align="center"><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp(summaryDTO.status,'IDS',${UserSession.organisation}).getLookUpDesc()"
													var="otherField" />${otherField}</td>
											<td class="text-center">
												<button type="button" class="btn btn-blue-2 btn-sm"
													onClick="getIndentReturnDataById('DeptReturn.html','viewDeptIndentReturn',${summaryDTO.dreturnid})"
													title="<spring:message code="material.management.view" text="View" />">
													<i class="fa fa-eye"></i>
												</button>
											</td>
										</tr>

									</c:forEach>
								</tbody>
							</table>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
