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
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/masters/customerMaster/customerMaster.js"
	type="text/javascript"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Customer Master" />
			</h2>
			<apptags:helpDoc url="CustomerMaster.html"
				helpDocRefURL="CustomerMaster.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">

			<form:form action="CustomerMaster.html" commandName="command"
				class="form-horizontal form" name="CustomerMaster"
				id="id_CustomerMaster">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">

					<label class="label-control col-sm-2 "> <spring:message
							code="customerMaster.custName" text="Customer Name"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input id="custName" path="custMasterDTO.custName"
							class="form-control" data-rule-required="true"
							data-rule-maxLength="200" onchange="" />
					</div>



					<label class="label-control col-sm-2 "> <spring:message
							code="customerMaster.custAddress" text="Customer Address"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input id="custAddress" path="custMasterDTO.custAddress"
							class="form-control" data-rule-required="true"
							data-rule-maxLength="200" />
					</div>

				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success searchData"
						onclick="searchCustomer('CustomerMaster.html','searchCustomer')">
						<i class="fa fa-search"></i>
						<spring:message code="master.search" text="Search" />
					</button>

					<input type="button" class="btn btn-warning"
						onclick="javascript:openRelatedForm('CustomerMaster.html');"
						value="<spring:message code="reset.msg" text="Reset"/>"
						id="cancelEdit" />

					<button type="button" class="btn btn-blue-2 createData"
						onclick="openaddcustomerFrom('CustomerMaster.html','openCustomerForm')">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="add.msg" text="Add" />
					</button>
					<button type="button" class="btn btn-primary"
						onclick="openaddcustomerFrom('CustomerMaster.html','uploadCustomerForm');">
						<spring:message code="master.expImp" text="Export/Import" />
					</button>
				</div>
				<div class="text-right padding-bottom-10"></div>

				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="id_customerTable">
							<thead>
								<tr>
									<th><spring:message code="customerMaster.custId" text="Customer ID" /></th>
									<th><spring:message code="customerMaster.custName" text="Customer Name" /></th>
									<th><spring:message code="customerMaster.custAddress" text="Customer Address" /></th>
									<th><spring:message code="accounts.master.status" text="Status" /></th>
									<th><spring:message code="bill.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.custMasterDtos}" var="custList"
									varStatus="loop">
									<tr>
										<td align="center">${custList.custId}</td>
										<td align="center">${custList.custName}</td>
										<td align="center">${custList.custAddress}</td>
										<td class="text-center">
										
										<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getDefaultValue('ACN').getLookUpId()" var="cstatus"/>
										<c:choose>
												<c:when test="${custList.custStatus eq cstatus}">
													<a href="#" class="fa fa-check-circle fa-2x green "
														title="Active"></a>
												</c:when>
												<c:otherwise>
													<a href="#" class="fa fa-times-circle fa-2x red "
														title="InActive"></a>
												</c:otherwise>
											</c:choose></td>
										<%-- <td align="center">${custList.custStatus}</td> --%>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View"
												onclick="modifyCustomer(${custList.custId},'CustomerMaster.html','ViewCustomerMaster')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit"
												onclick="modifyCustomer(${custList.custId},'CustomerMaster.html','EditCustomerMaster')">
												<i class="fa fa-pencil"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

