<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="js/account/voucherTemplate.js"></script>

<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message
				code="voucher.template.entry.master.vouchertemplateentry"
				text="Voucher Template Entry" />
		</h2>
	<apptags:helpDoc url="VoucherTemplate.html" helpDocRefURL="VoucherTemplate.html"></apptags:helpDoc>		
	</div>
	<div class="widget-content padding">

		<form:form action="VoucherTemplate.html" method="GET"
			class="form-horizontal" modelAttribute="viewData"
			id="VoucherTemplateCreate">
			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#vouchertemplate"><spring:message
									code="voucher.template.entry.master.title"
									text="Voucher Template" /> </a>
						</h4>
					</div>
					<div id="vouchertemplate" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message
										code="voucher.template.entry.templatetype"
										text="Template Type" /></label>
								<div class="col-sm-4">
									<form:input path="" value="${viewData.templateTypeDesc}"
										cssClass="form-control" disabled="true" />
								</div>
								<label class="col-sm-2 control-label"><spring:message
										code="account.budgetopenmaster.financialyear"
										text="Financial Year" /></label>
								<div class="col-sm-4">
									<form:input path="" value="${viewData.financialYearDesc}"
										cssClass="form-control" disabled="true" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label "><spring:message
										code="voucher.template.entry.vouchertype" text="Voucher Type" /></label>
								<div class="col-sm-4">
									<form:input path="" value="${viewData.voucherTypeDesc}"
										cssClass="form-control" disabled="true" />
								</div>
								<label class="col-sm-2 control-label"><spring:message
										code="budget.reappropriation.master.departmenttype"
										text="Department" /></label>
								<div class="col-sm-4">
									<form:input path="" value="${viewData.departmentDesc}"
										cssClass="form-control" disabled="true" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label "><spring:message
										code="voucher.template.entry.master.templatefor"
										text="Template For" /></label>
								<div class="col-sm-4">
									<form:input path="" value="${viewData.templateForDesc}"
										cssClass="form-control" disabled="true" />
								</div>
								<label class="col-sm-2 control-label"><spring:message
										code="accounts.master.status" text="Status" /></label>
								<div class="col-sm-4">
									<form:input path="" value="${viewData.statusDesc}"
										cssClass="form-control" disabled="true" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse1" href="#mappingdetails">
								<spring:message code="voucher.template.map.detail"
									text="Mapping Details" />
							</a>
						</h4>
					</div>
					<div id="mappingdetails" class="panel-collapse collapse in">
						<div class="panel-body">
							<c:set var="d" value="0" scope="page" />
							<div class="table-overflow-sm">
								<table class="table table-bordered table-striped"
									id="mappingDetails">
									<tbody>
										<tr>
											<th width="20%"><spring:message
													code="bank.master.accountType" text="Account Type" /></th>
											<th width="10%"><spring:message
													code="voucher.template.dr.cr" text="Dr. / Cr." /></th>
											<th width="20%"><spring:message
													code="accounts.receipt.payment_mode" text="Mode" /></th>
											<th width="50%"><spring:message
													code="account.bankmaster.acchead" text="Account Head" /></th>
										</tr>
										<c:forEach items="${viewData.mappingDetails}" var="detailInfo"
											varStatus="testIndex">
											<tr class="tableRowClass">
												<c:set var="index" value="${testIndex.index}" scope="page" />

												<td><form:input path=""
														value="${detailInfo.accountTypeDesc}"
														cssClass="form-control" disabled="true" /></td>
												<td><form:input path="" value="${detailInfo.drCrDesc}"
														cssClass="form-control" disabled="true" /></td>
												<td><form:input path="" value="${detailInfo.modeDesc}"
														cssClass="form-control" disabled="true" /></td>
												<td><form:input path=""
														value="${detailInfo.acHeadDesc}" cssClass="form-control"
														disabled="true" /></td>
											</tr>
										</c:forEach>

									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="text-center padding-top-10">
				<button type="button" class="btn btn-danger"
					onclick="window.location.href='VoucherTemplate.html'" id="backBtn">
					<spring:message code="account.bankmaster.back" text="Back" />
				</button>
			</div>
		</form:form>
	</div>
</div>
