<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css">
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="js/material_mgmt/master/indentProcess.js" type="text/javascript"></script>
<div id="searchIndPage">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="department.indent.summaryHeading" text="Department Indent Summary" />
				</h2>
				<apptags:helpDoc url="IndentProcess.html"></apptags:helpDoc>
			</div>
			<div class="pagediv">
				<div class="widget-content padding">
					<form:form id="defSummaryFrm" name="indentProcess"
						class="form-horizontal" action="IndentProcess.html" method="post">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
						
						<div class="form-group">
							<label class="control-label col-sm-2"><spring:message
									code="department.indent.storeName" text="Store Name" /></label>
							<div class="col-sm-4 ">
								<form:select path="indentProcessDTO.storeid" id="storeid" class="form-control" 
										cssClass="form-control chosen-select-no-results" data-rule-required="true">
									<form:option value="" selected ="true">
										<spring:message code="material.management.select" text="select" />
									</form:option>
									<c:forEach items="${command.storeIdNameList}" var="data">
										<form:option value="${data[0]}">${data[1]}</form:option>
									</c:forEach>	
								</form:select>
							</div>
							
							<label class="control-label col-sm-2" for="indentno"><spring:message
									code="department.indent.no" text="Indent No." /></label>
							<div class="col-sm-4">
								<form:select path="indentProcessDTO.indentno" id="indentno" class="form-control" 
										cssClass="form-control chosen-select-no-results" data-rule-required="true">
									<form:option value="" selected ="true">
										<spring:message code="material.management.select" text="select" />
									</form:option>
									<c:forEach items="${command.listIndentProcessDTO}" var="data">
										<form:option value="${data.indentno}">${data.indentno}</form:option>
									</c:forEach>	
								</form:select>
							</div>
						</div>
						
						 <div class="form-group">
							<label class="col-sm-2 control-label" for="department"><spring:message 
									code="department.indent.dept" text="Department Name" /></label>
							<div class="col-sm-4">
								<form:select path="indentProcessDTO.deptId" id="deptId" 
									cssClass="form-control chosen-select-no-results" >
									<form:option value="">
										<spring:message code="material.management.select" text="select" />
									</form:option>
									<c:forEach items="${command.departmentIdNameList}" var="department">
										<form:option value="${department[0]}">${department[1]}</form:option>
									</c:forEach>
								</form:select>
							</div>
									
							<label class="control-label col-sm-2"><spring:message
								code="department.indent.indentor.name" text="Indentor Name" /></label>
							<div class="col-sm-4">
								<form:select path="indentProcessDTO.indenter" id="indenter"	data-rule-required="true"
										cssClass="form-control mandColorClass chosen-select-no-results" >
									<form:option value="">
										<spring:message code="material.management.select" text="select" />
									</form:option>
									<c:forEach items="${command.employees}" var="employees">
										<form:option value="${employees[3]}" label="${employees[0]} ${employees[2]}"></form:option>
									</c:forEach>	
								</form:select>		
							</div>
						</div>
						
						 <div class="form-group">                          
                         	<label class="col-sm-2 control-label "><spring:message
											code="department.indent.status" text="Indent Status" /></label>
							<div class="col-sm-4">
								<form:select path="indentProcessDTO.status"
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
							<button type="button" class="btn btn-blue-2"
								onclick="searchIndentStore('IndentProcess.html','searchIndentStore');"
								title="<spring:message code="material.management.search" text="Search" />" >
								<i class="fa fa-search"></i><spring:message code="material.management.search" 
								text="Search" />
							</button>		
							<button type="button" class="btn btn-warning" onclick="window.location.href='IndentProcess.html'"
									title="<spring:message code="material.management.reset" text="Reset" />" >
								<i class="fa fa-refresh"></i>
								<spring:message code="material.management.reset" text="Reset" />
							</button>		
							<button type="button" class="button-input btn btn-success" onclick="addIndent('IndentProcess.html','addIndent');"
									title="<spring:message code="material.management.add" text="Add" />" >
								<i class="fa fa-plus-circle"> </i>
								<spring:message code="material.management.add" text="Add" />
							</button>
						</div>
						
						<div class="table-responsive clear">
							<table class="table table-striped table-bordered"
								id="searchStore">
								<thead>
									<tr>
										<th align="center"><spring:message code="store.master.srno" text="Sr No." /></th>
										<th align="center"><spring:message code="department.indent.no" text="Indent No." /></th>
										<th align="center"><spring:message code="department.indent.date" text="Indent Date" /></th>
										<th align="center"><spring:message code="department.indent.indentor.name" text="Indentor Name" /></th>
										<th align="center"><spring:message code="department.indent.storeName" text="Store Name" /></th>
										<th align="center"><spring:message code="department.indent.status" text="Indent Status" /></th>
										<th align="center"><spring:message code="store.master.action" text="Action" /></th>
									</tr>
								</thead>
								 <tbody>
									<c:forEach items="${command.listIndentProcessDTO}" var="summaryDTO"
										varStatus="index">
										<tr>
											<td class="text-center">${index.count}</td>
											<td align="center">${summaryDTO.indentno}</td>
											<td align="center"><fmt:formatDate
													value="${summaryDTO.indentdate}" pattern="dd-MM-yyyy" /></td>
											<td align="center">${summaryDTO.indenterName}</td>
											<td align="center">${summaryDTO.storeDesc}</td>
											<td align="center"><spring:eval
                										expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp(summaryDTO.status,'IDS',${UserSession.organisation}).getLookUpDesc()" var="otherField"/>${otherField}</td>
											<td class="text-center">
												<button type="button" class="btn btn-blue-2 btn-sm"
												onClick="getIndentDataById('IndentProcess.html','viewDeptIndent',${summaryDTO.indentid})"
													title="<spring:message code="material.management.view" text="View" />">
													<i class="fa fa-eye"></i>
												</button></td>
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
