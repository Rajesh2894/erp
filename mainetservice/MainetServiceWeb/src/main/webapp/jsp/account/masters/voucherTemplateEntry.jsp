<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/validation.js"></script>
<script src="js/account/voucherTemplateEntry.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>


<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="voucher.template.entry.form"
					text="Voucher Template Entry Form" />
			</h2>
		<apptags:helpDoc url="VoucherTemplate.html" helpDocRefURL="VoucherTemplate.html"></apptags:helpDoc>		
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="account.common.mandmsg"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="account.common.mandmsg1" text="is mandatory" /> </span>
			</div>
			<c:url value="${saveAction}" var="url_form_submit" />
			<c:url value="${mode}" var="form_mode" />
			<form:form class="form-horizontal" cssClass="form-horizontal"
				method="POST" action="${url_form_submit}" name="${saved}"
				modelAttribute="voucherTemplateMasDto">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span>&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="voucherMode" id="voucherMode" />
				<form:hidden path="saveSuccess" id="saveSuccess" />
				<form:hidden path="isempty" id="isempty" />

				<div class="form-group" id="mainDiv">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="voucher.template.mapping.type" text="Voucher Mapping Type" /></label>
					<apptags:lookupField items="${mappingType}" path="mappingType"
						cssClass="form-control" hasChildLookup="false" hasId="true"
						showAll="false" selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" />
					<div id="financialYear" class="hide">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="account.budgetopenmaster.financialyear"
								text="Financial Year" /></label>
						<div class="col-sm-4">
							<form:select path="financialYearId" cssClass="form-control"
								id="financialId">
								<form:option value="">
									<spring:message
										code="account.budgetopenmaster.selectfinancialyear"
										text="Select Financial Year" />
								</form:option>
								<c:forEach items="${financeYear}" var="lookup">
									<form:option value="${lookup.key}"> ${lookup.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</div>

				<div class="form-group">

					<div class="hide" id="voucherTemp">
						<label class="col-sm-2 control-label"><spring:message
								code="voucher.template.entry.master.title"
								text="Voucher Template" /></label>
						<div class="col-sm-4">
							<form:select id="voucherId" path="voucherId"
								class="form-control mandClassColor"
								onchange="showPermenantTempData(this);">
								<form:option value="">
									<spring:message code="account.common.select" />
								</form:option>
								<c:forEach items="${permLookUpList}" var="permLookUp"
									varStatus="vr">
									<form:option value="${permLookUp.key}">${permLookUp.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="hide" id="financialTemp">
						<label class="col-sm-2 control-label"><spring:message
								code="voucher.template.entry.master.title"
								text="Voucher Template" /></label>
						<div class="col-sm-4">
							<form:hidden id="hiddenVoucherId" path=""
								value="${voucherTemplateMasDto.voucherIdFin}" />
							<form:select id="voucherIdFin" path="voucherIdFin"
								class="form-control mandClassColor"
								onchange="showFinanceTempData(this);">
								<form:option value="">
									<spring:message code="account.common.select" />
								</form:option>
								<c:forEach items="${financialVoucherList}" var="financial">
									<option value="${financial.cpdId}">${financial.cpdDesc}</option>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</div>
				<div id="permanentDiv" class="table-responsive hide">
					<div class="table-responsive max-height-300 padding-top-10">
						<table class="table table-bordered table-condensed" id="permDiv">
							<tr>
								<th width="35%"><spring:message
										code="account.bankmaster.acchead" text="Account Head" /><span
									class="mand">*</span></th>
								<th width="25%"><spring:message
										code="accounts.master.status" text="Status" /><span
									class="mand">*</span></th>
								<th width="15%"><spring:message
										code="account.bankmaster.adddelete" text="Add/Delete" /></th>
							</tr>
							<c:choose>
								<c:when
									test="${not empty voucherTemplateMasDto.voucherTemplateList}">
									<c:forEach items="${voucherTemplateMasDto.voucherTemplateList}"
										var="voucher" varStatus="sts">
										<tr class="voucherClass" id="voucherValForRow">
											<form:hidden
												path="voucherTemplateList[${sts.count-1}].voutId"
												id="voutid${sts.count-1}" />
											<td><form:select id="accHeadId${sts.count-1}"
													path="voucherTemplateList[${sts.count-1}].tbAcSecondaryheadMaster.sacHeadId"
													class="form-control mandClassColor " disabled="disabled">
													<form:option value="">
														<spring:message code="account.common.select" />
													</form:option>
													<c:forEach items="${primarySecondaryHead}" var="accHead">
														<c:choose>
															<c:when
																test="${voucherTemplateList.tbAcSecondaryheadMaster.sacHeadId eq accHead}">
																<form:option value="${accHead.key}"
																	label="${accHead.value}" selected="selected">${accHead.value}</form:option>
															</c:when>
															<c:otherwise>
																<form:option value="${accHead.key}">${accHead.value}</form:option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</form:select> <form:hidden
													path="voucherTemplateList[${sts.count-1}].hiddenSacHeadId"
													id="hiddenSacHeadId${sts.count-1}" /></td>
											<td><form:select
													path="voucherTemplateList[${sts.count-1}].voutStatusFlg"
													id="voutStatusFlg${sts.count-1}" cssClass="form-control"
													disabled="disabled">
													<form:option value="">
														<spring:message code="account.select" text="Select" />
													</form:option>
													<form:option value="A">
														<spring:message code="voucher.template.entry.active"
															text="Active" />
													</form:option>
													<form:option value="I">
														<spring:message code="voucher.template.entry.inactive"
															text="Inactive" />
													</form:option>
												</form:select> <form:hidden
													path="voucherTemplateList[${sts.count-1}].hiddenVoutStatusFlg"
													id="hiddenVoutStatusFlg${sts.count-1}" /></td>
											<td><a data-toggle="tooltip" data-placement="top"
												title="" class="btn btn-blue-2 btn-sm addVoucher"
												id="addVoucher${sts.count-1}"><i class="fa fa-plus"></i></a>
												<a data-toggle="tooltip" data-placement="top" title=""
												class="btn btn-danger btn-sm deleteVoucher"
												id="deleteVoucher${sts.count-1}"><i class="fa fa-trash"></i></a>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="voucherClass" id="voucherValForRow">
										<form:hidden path="voucherTemplateList[0].voutId"
											id="voutidforRow0" />
										<form:hidden path="voucherTemplateList[0].langId" />
										<form:hidden path="voucherTemplateList[0].userId" />
										<form:hidden path="voucherTemplateList[0].orgid" />
										<form:hidden path="voucherTemplateList[0].lgIpMac" />
										<form:hidden path="voucherTemplateList[0].voutStatusFlg" />
										<form:hidden
											path="voucherTemplateList[0].selectedSecondaryValue"
											id="selectedSecondaryValue0" />
										<form:hidden path="voucherTemplateList[0].cpdIdSubtype" />
										<td><form:select id="accHeadId0"
												path="voucherTemplateList[0].tbAcSecondaryheadMaster.sacHeadId"
												class="form-control mandClassColor">
												<form:option value="">
													<spring:message code="account.common.select" />
												</form:option>
												<c:forEach items="${primarySecondaryHead}" var="accHead">
													<form:option value="${accHead.key}">${accHead.value}</form:option>
												</c:forEach>
												<form:hidden path="voucherTemplateList[0].hiddenSacHeadId"
													id="hiddenSacHeadId0" />
											</form:select></td>
										<td><form:select
												path="voucherTemplateList[0].voutStatusFlg"
												id="voutStatusFlg0" cssClass="form-control"
												disabled="disabled">
												<form:option value="A" selected="selected">
													<spring:message code="voucher.template.entry.active"
														text="Active" />
												</form:option>
											</form:select> <form:hidden
												path="voucherTemplateList[0].hiddenVoutStatusFlg"
												id="hiddenVoutStatusFlg0" /></td>
										<td><a data-toggle="tooltip" data-placement="top"
											title="" class="btn btn-blue-2 btn-sm disabled"><i
												class="fa fa-plus"></i></a> <a data-toggle="tooltip"
											data-placement="top" title=""
											class="btn btn-danger btn-sm disabled"><i
												class="fa fa-trash"></i></a></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</table>
					</div>

				</div>
				<div id="financialDiv" class="hide">
					<div class="table-responsive max-height-300 padding-top-10">
						<table class="table table-bordered table-condensed" id="finDiv">
							<tr>
								<th width="35%"><spring:message
										code="account.bankmaster.acchead" text="Account Head" /><span
									class="mand">*</span></th>
								<th width="25%"><spring:message
										code="accounts.master.status" text="Status" /><span
									class="mand">*</span></th>
								<th width="15%"><spring:message
										code="account.bankmaster.adddelete" text="Add/Delete" /></th>
							</tr>
							<c:choose>
								<c:when
									test="${not empty voucherTemplateMasDto.voucherFinanceList}">
									<c:forEach items="${voucherTemplateMasDto.voucherFinanceList}"
										var="financial" varStatus="sts">
										<tr class="financeClass">
											<form:hidden path="voucherFinanceList[${sts.count-1}].voutId"
												id="voutIdFin${sts.count-1}" />
											<td><form:select id="accHeadIdFin${sts.count-1}"
													path="voucherFinanceList[${sts.count-1}].tbAcSecondaryheadMaster.sacHeadId"
													class="form-control mandClassColor " disabled="disabled">
													<form:option value="">
														<spring:message code="account.common.select" />
													</form:option>
													<c:forEach items="${primarySecondaryHead}" var="accHeadFin">
														<c:choose>
															<c:when
																test="${voucherFinanceList.tbAcSecondaryheadMaster.sacHeadId eq accHeadFin}">
																<form:option value="${accHeadFin.key}"
																	selected="selected">${accHeadFin.value}</form:option>
															</c:when>
															<c:otherwise>
																<form:option value="${accHeadFin.key}">${accHeadFin.value}</form:option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
													<form:hidden
														path="voucherFinanceList[${sts.count-1}].hiddenSacHeadId"
														id="hiddenSacHeadIdFin${sts.count-1}" />
												</form:select></td>

											<td><form:select
													path="voucherFinanceList[${sts.count-1}].voutStatusFlg"
													id="voutStatusFlgFin${sts.count-1}" cssClass="form-control">
													<form:option value="">
														<spring:message code="voucher.template.entry.flag"
															text="Select Flag" />
													</form:option>
													<form:option value="A">
														<spring:message code="voucher.template.entry.active"
															text="Active" />
													</form:option>
													<form:option value="I">
														<spring:message code="voucher.template.entry.inactive"
															text="Inactive" />
													</form:option>
												</form:select> <form:hidden
													path="voucherFinanceList[${sts.count-1}].hiddenVoutStatusFlg"
													id="hiddenVoutStatusFlgFin${sts.count-1}" /></td>
											<td><a data-toggle="tooltip" data-placement="top"
												title="" class="btn btn-blue-2 btn-sm addVoucherFin"
												id="addVoucherFin${sts.count-1}"><i class="fa fa-plus"></i></a>
												<a data-toggle="tooltip" data-placement="top" title=""
												class="btn btn-danger btn-sm deleteVoucherFin"
												id="deleteVoucherFin${sts.count-1}"><i
													class="fa fa-trash"></i></a></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="financeClass">
										<form:hidden path="voucherFinanceList[0].voutId"
											id="voutIdFin0" />
										<td><form:select id="accHeadIdFin0"
												path="voucherFinanceList[0].tbAcSecondaryheadMaster.sacHeadId"
												class="form-control mandClassColor " disabled="disabled">
												<form:option value="">
													<spring:message code="account.common.select" />
												</form:option>
												<c:forEach items="${primarySecondaryHead}" var="accHeadFin">
													<form:option value="${accHeadFin.key}">${accHeadFin.value}</form:option>
												</c:forEach>
												<form:hidden path="voucherFinanceList[0].hiddenSacHeadId"
													id="hiddenSacHeadIdFin0" />
											</form:select></td>
										<td><form:select
												path="voucherFinanceList[0].voutStatusFlg"
												id="voutStatusFlgFin0" cssClass="form-control"
												disabled="disabled">
												<form:option value="A">
													<spring:message code="voucher.template.entry.active"
														text="Active" />
												</form:option>
											</form:select> <form:hidden
												path="voucherFinanceList[0].hiddenVoutStatusFlg"
												id="hiddenVoutStatusFlgFin0" /></td>
										<td><a data-toggle="tooltip" data-placement="top"
											title="" class="btn btn-blue-2 btn-sm disabled"><i
												class="fa fa-plus"></i></a> <a data-toggle="tooltip"
											data-placement="top" title=""
											class="btn btn-danger btn-sm disabled"><i
												class="fa fa-trash"></i></a></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</table>
					</div>
				</div>

				<div class="text-center padding-top-10" id="saveDiv">
					<button type="button" class="btn btn-blue-2" id="editButton"
						value="Edit" onclick="editForm(this)">
						<spring:message code="account.edit" text="Edit" />
					</button>
					<button type="button" class="btn btn-success btn-submit" id="saveButton"
						onclick="saveVoucherMaster(this)">
						<spring:message code="account.bankmaster.save" text="Save" />
					</button>
					<button type="Reset" class="btn btn-danger" id="clearButton"
						onclick="window.location.href='VoucherTemplateMaster.html'">
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>

