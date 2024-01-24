<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="js/account/receivableDemandEntry.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script src="js/mainet/validation.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="receivable.demand.entry.summary" text="Receivable Demand Entry Summary" /></strong>
			</h2>
			<apptags:helpDoc url=""></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="ReceivableDemandEntry.html" commandName="command" class="form-horizontal form" name="ReceivableDemandEntry" id="id_ReceivableDemandEntry">
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- End Validation include tag -->
				
				<form:hidden path="" value="${formError}" id="formError"/>
				<input type="hidden" id="langId" value="${userSession.languageId}" />
				<input type="hidden" id="listSize" value="${command.receivableDemandEntryDtosList.size()}">
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message code="receivable.demand.entry.ccn.idn" text="IDN/CCN Number" /></label>
					<div class="col-sm-4 ">
						<form:input path="" cssClass="form-control" id="refNumber" readonly="" disabled="" maxlength="12"/>
					</div>
					<label class="col-sm-2 control-label" for=""><spring:message code="misc.bill.no" text="Supplementry Bill Number" /></label>
					<div class="col-sm-4">
						<form:input path="" cssClass="form-control" id="billNo" readonly="" disabled=""  maxlength="15"/>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success searchData" onclick="searchReceivableDemandEntry('ReceivableDemandEntry.html','searchReceivableDemandEntry')">
						<i class="fa fa-search"></i>
						<spring:message code="master.search" text="Search" />
					</button>
					<input type="button" class="btn btn-warning" onclick="javascript:openRelatedForm('ReceivableDemandEntry.html');" value="<spring:message code="reset.msg" text="Reset"/>" id="cancelEdit" />
					<button type="button" class="btn btn-blue-2 createData" onclick="openaddForm('ReceivableDemandEntry.html','openForm')">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="add.msg" text="Add" />
					</button>

				</div>
				<c:if test="${command.receivableDemandEntryDtosList.size()!=0 }">
				<div class="text-right padding-bottom-10"></div>

				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered" id="id_customerTable">
							<thead>
								<tr>
									<th><spring:message code="misc.bill.no" text="Supplementry Bill No" /></th>
									<th><spring:message code="misc.bill.gen.date" text="Supplementary Bill Generation Date" /></th>
									<th><spring:message code="receivable.demand.entry.rcpt.date" text="Receipt Date " /></th>
									<th><spring:message code="receivable.demand.entry.rcpt.no" text="Receipt Number" /></th>
									<th><spring:message code="receivable.demand.entry.bill.amnt" text="Bill Amount" /></th>
									<th><spring:message code="bill.action" text="Action" /></th>
								</tr>
							</thead>
								
							<tbody>

								<c:forEach items="${command.receivableDemandEntryDtosList }" var="billList" varStatus="loop">
									<tr>

										<td align="center">${billList.billNo}</td>
										<td align="center"><fmt:formatDate type="date" value="${billList.createdDate}" pattern="dd-MM-yyyy" /></td>										
										<td align="center"><fmt:formatDate type="date" value="${billList.receiptDate}" pattern="dd-MM-yyyy" /></td>
										<td align="center">${billList.receiptNo}</td>
										<fmt:formatNumber var="bill" value="${billList.billAmount}" pattern="#"></fmt:formatNumber>
										<td align="right">${bill}</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm" onClick="ViewReceivableDemandEntry(${billList.billId},'ReceivableDemandEntry.html','viewReceivableDemandEntry')" title="View">
												<strong class="fa fa-eye"></strong> <span class="hide"> <spring:message code="solid.waste.view" text="View"></spring:message>
												</span>
											</button>
											<button type="button" class="btn btn-warning btn-sm" title="Edit" onclick="ViewReceivableDemandEntry(${billList.billId},'ReceivableDemandEntry.html','editReceivableDemandEntry');">
												<i class="fa fa-pencil"></i>
											</button>
											<button onclick="printChallan(${billList.billId},'ReceivableDemandEntry.html','printChallan');" class="btn btn-darkblue-3" type="button" title="Print">
												<strong class="fa fa-print"></strong><span class="hide"><spring:message code="solid.waste.print" text="Print"></spring:message></span>
											</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				</c:if>

			</form:form>
		</div>
	</div>
</div>





