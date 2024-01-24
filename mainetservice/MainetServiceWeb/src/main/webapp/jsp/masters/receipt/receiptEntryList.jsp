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
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/masters/receipt/receipt.js" type="text/javascript"></script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="totContent">
	<div class="content form-div" id="content">
		<!-- Start info box -->
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="collection.receipt.entry"
						text="Collection Receipts" />
				</h2>
			</div>
			<div class="widget-content padding">

				<form:form action="DepositReceiptEntry.html" id="tbServiceReceiptMasBean"
					modelAttribute="tbServiceReceiptMasBean" class="form-horizontal">
					<div class="error-div alert alert-danger alert-dismissible"
						id="errorDivId" style="display: none;">
						<button type="button" class="close" onclick="closeOutErrBox()"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<span id="errorId"></span>
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
					</div>
					<div class="form-group">
						<label class="control-label  col-sm-2"> <spring:message
								code="collection.receipt.number"></spring:message>
						</label>
						<div class="col-sm-4">
							<input id="rmRcptno" class="form-control"
								maxLength="12" />
						</div>
						<label class="control-label  col-sm-2  "> <spring:message
								code="collection.receipt.date"></spring:message>
						</label>

						<div class="col-sm-4">
							<div class="input-group">
								<input name="" Class="datepicker form-control" id="rmDatetemp"
									maxLength="10" /> <label for="rmDatetemp"
									class="input-group-addon"> <i class="fa fa-calendar"></i><span
									class="hide"><spring:message
											code="collection.receipt.icon" text="icon" /></span><input
									type="hidden" id="rmDatetemp"></label>
							</div>
						</div>

					</div>

					<div class="form-group">
						<label class="control-label  col-sm-2"> <spring:message
								code="collection.receipt.amount" text="Receipt Amount" />
						</label>
						<div class="col-sm-4">
							<input id="rdamount" class="form-control"
								onkeypress="return hasAmount(event, this, 12, 2)" />
						</div>
						<label class="control-label  col-sm-2"> <spring:message
								code="collection.receipt.payeename"></spring:message>
						</label>
						<div class="col-sm-4">
							<form:select id="rm_Receivedfrom" path="rmReceivedfrom"
								class="form-control chosen-select-no-results">
								<form:option value="">
									<spring:message code="collection.receipt.select" />
								</form:option>
								<c:forEach items="${payeeList}" var="payee">
									<form:option value="${payee}">${payee}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="text-center padding-bottom-10">

						<button type="button" class="btn btn-success" id="searchReceipt"
							onclick="searchReceiptData();">
							<i class="fa fa-search"></i>
							<spring:message code="collection.receipt.search" text="Search" />

						</button>
						<button type="button" class="btn btn-warning resetSearch"
							onclick="window.location.href = 'DepositReceiptEntry.html'">
							<spring:message code="collection.receipt.reset" text="Reset" />
						</button>
						<button type="button" class="btn btn-blue-2" id="createReceip"
							onClick="createData();">
							<i class="fa fa-plus-circle"></i>
							<spring:message code="collection.receipt.Add" text="Add" />
						</button>
					</div>

					<div class="table-responsive">
						<table class="table table-bordered table-striped"
							id="receiptDatatables">
							<thead>
								<tr>
									<th scope="col" width="10%" align="center"><spring:message
											code="collection.receipt.number" text="Receipt Number" />
									<th scope="col" width="25%" class="text-center"><spring:message
											code="collection.receipt.date" text="Receipt Date" /></th>
									<th scope="col" width="30%" align="center"><spring:message
											code="collection.receipt.payeename" text="Payee" /></th>
									<th scope="col" width="25%" align="center"><spring:message
											code="collection.receipt.amount" text="Amount" /></th>
									<th scope="col" width="10%" class="text-center"><spring:message
											code="collection.receipt.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${receiptMasBeanList}" var="mstDto">
									<tr>
										<td class="text-center">${mstDto.rmReceiptNo}</td>
										<td class="text-center">${mstDto.rmDatetemp}</td>
										<td class="text-center">${mstDto.rmReceivedfrom}</td>
										<td class="text-right">${mstDto.rmAmount}</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View"
												onclick="getActionForReceipt(${mstDto.rmRcptid},'V');">
												<i class="fa fa-eye"></i>
											</button>
											<c:if test="${command.envFlag eq 'N' }">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="Print"
												onclick="printReceipt(${mstDto.rmRcptid},'P');">
												<i class="fa fa-print"></i>
											</button></c:if>
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