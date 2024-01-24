<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="js/mainet/jquery.jqGrid.min.js" type="text/javascript"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/masters/contract/commonAdvanceEntry.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="advance.requisition" text="Advance Requision"></spring:message>
			</h2>
			<apptags:helpDoc url="AdvanceRequisition.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">

			<form:form action="AdvanceRequisition.html" class="form-horizontal"
				name="advanceRequisition" id="advanceRequisition">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="advance.requisition.advancedate" text="Advance Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="advanceEntryDate"
								class="form-control  datepicker" value="" maxLength="10" />
							<label class="input-group-addon" for="advanceDate"><i
								class="fa fa-calendar"></i><span class="hide"><spring:message
										code="account.additional.supplemental.auth.icon" text="icon" /></span>
						</div>
					</div>

				</div>

				<div class="form-group">


					<label class="col-sm-2 control-label" for="name"><spring:message
							code="advance.requisition.vendoremployeename" text="" /></label>
					<div class="col-sm-4">
						<form:select id="vendorId" path=""
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="advance.requisition.select" text="" />
							</form:option>
							<c:forEach items="${command.vendorList}" var="vendorData">
								<form:option value="${vendorData.vmVendorid}">${vendorData.vmVendorname}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label" for="name"><spring:message
							code="advance.requisition.dept" text="" /></label>
					<div class="col-sm-4">
						<form:select id="deptId" path=""
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="advance.requisition.select" text="" />
							</form:option>
							<c:forEach items="${command.departmentsList}" var="deptList">
								<form:option value="${deptList.dpDeptid}">${deptList.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success searchData"
						onclick="searchAdvanceRequitionList();">
						<i class="fa fa-search"></i>
						<spring:message code="advance.requisition.search" text="Search" />
					</button>
					<button class="btn btn-warning  reset" type="reset"
						onclick="backForm();">
						<i class="button-input"></i>
						<spring:message code="advance.requisition.reset" />
					</button>
					<button type="button" class="btn btn-blue-2 createData">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="advance.requisition.add" text="Add" />
					</button>
				</div>

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered" id="datatables">
						<thead>
							<tr>
								<th width="15%" scope="col" align="center"><spring:message
										code="advance.requisition.arn" text="" /></th>
								<th width="12%" scope="col" align="center"><spring:message
										code="advance.requisition.status" text="" /></th>
								<th width="10%" scope="col" align="center"><spring:message
										code="advance.requisition.entrydate" text="" /></th>
								<th width="15%" scope="col" align="center"><spring:message
										code="advance.requisition.advamount" text="" /></th>
								<th width="20%" scope="col" align="center"><spring:message
										code="estate.grid.column.action" /></th>
							</tr>
						</thead>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>

