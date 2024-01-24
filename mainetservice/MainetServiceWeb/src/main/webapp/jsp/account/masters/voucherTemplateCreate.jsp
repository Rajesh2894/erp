<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="js/account/voucherTemplate.js"></script>


<script>
	$(document).ready(function(){
		
		<c:if test="${modeFlag eq 'E'}">
	    var status = $("#status option:selected").attr("code");
		if(status === 'I') {
			$("#status").prop("disabled",true).trigger("chosen:updated");	
			$('.tableRowClass').each(function(i) {
				$("select[id^='budgetId_"+i+"']").prop("disabled",true).trigger("chosen:updated");
				$("select[id^='accountType_"+i+"']").prop("disabled",true).trigger("chosen:updated");
				$("select[id^='mode_"+i+"']").prop("disabled",true).trigger("chosen:updated");
				$("select[id^='status_"+i+"']").prop("disabled",true).trigger("chosen:updated");
				$("select[id^='debitCredit_"+i+"']").prop("disabled",true).trigger("chosen:updated");
				$('input[type=button]').attr('disabled', true);
				
			});
		}else{
			$("#status").prop("disabled",false).trigger("chosen:updated");
		}
		</c:if>	
		
		});
</script>

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

		<form:form action="VoucherTemplate.html" method="POST"
			class="form-horizontal" modelAttribute="formDTO"
			id="VoucherTemplateCreate">
			<div class="error-div alert-danger padding-10 margin-bottom-10"
				id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
			<form:hidden path="" value="${dto.currentFYearId }"
				id="currentFYearId" />
			<form:hidden path="" id="indexdata" />
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
								<form:hidden path="templateId" />
								<label class="col-sm-2 control-label required-control"><spring:message
										code="voucher.template.entry.templatetype"
										text="Template Type" /></label>
								<c:if test="${modeFlag eq 'A' }">
									<div class="col-sm-4">
										<form:select path="templateType"
											class="form-control mandColorClass"
											onchange="clearTemplateForData(this)"
											data-rule-required="true">
											<form:option value="">
												<spring:message code="voucher.template.select.type"
													text="Select Template Type" />
											</form:option>
											<c:forEach items="${dto.templateLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>
								<c:if test="${modeFlag eq 'E' }">
									<div class="col-sm-4">
										<form:hidden path="templateType" />
										<form:select path="templateType" id="templateTypeEdit"
											class="form-control mandColorClass"
											onchange="clearTemplateForData(this)">
											<form:option value="">
												<spring:message code="voucher.template.select.type"
													text="Select Template Type" />
											</form:option>
											<c:forEach items="${dto.templateLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>

								<c:if test="${modeFlag eq 'A' }">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="account.budgetopenmaster.financialyear"
											text="Financial Year" /></label>
									<div class="col-sm-4">
										<form:select path="financialYear"
											class="form-control chosen-select-no-results"
											onchange="selectTemplateTypeData(this)">
											<form:option value="">
												<spring:message
													code="account.budgetopenmaster.selectfinancialyear"
													text="Select Financial Year" />
											</form:option>
											<c:forEach items="${dto.financialYearMap}" var="entry">
												<form:option value="${entry.key}" code="${entry.key}">${entry.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>

								<c:if test="${modeFlag eq 'E' }">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="account.budgetopenmaster.financialyear"
											text="Financial Year" /></label>

									<c:if test="${formDTO.templateTypeCode eq 'FYW'}">
										<div class="col-sm-4">
											<form:hidden path="financialYear" />
											<form:select path="financialYear"
												class="form-control chosen-select-no-results"
												disabled="${modeFlag ne 'true'}">
												<form:option value="">
													<spring:message
														code="account.budgetopenmaster.selectfinancialyear"
														text="Select Financial Year" />
												</form:option>
												<c:forEach items="${dto.financialYearMap}" var="entry">
													<form:option value="${entry.key}" code="${entry.key}">${entry.value}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</c:if>
									<c:if test="${formDTO.templateTypeCode eq 'PN'}">
										<div class="col-sm-4">
											<form:select path="financialYear"
												class="form-control chosen-select-no-results"
												disabled="${modeFlag ne 'true'}">
												<form:option value="">
													<spring:message
														code="account.budgetopenmaster.selectfinancialyear"
														text="Select Financial Year" />
												</form:option>
												<c:forEach items="${dto.financialYearMap}" var="entry">
													<form:option value="${entry.key}" code="${entry.key}">${entry.value}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</c:if>
								</c:if>

								<c:if test="${modeFlag eq 'V' }">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="account.budgetopenmaster.financialyear"
											text="Financial Year" /></label>
									<div class="col-sm-4">
										<form:select path="financialYear"
											class="form-control chosen-select-no-results">
											<form:option value="">
												<spring:message
													code="account.budgetopenmaster.selectfinancialyear"
													text="Select Financial Year" />
											</form:option>
											<c:forEach items="${dto.financialYearMap}" var="entry">
												<form:option value="${entry.key}" code="${entry.key}">${entry.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>

							</div>
							<div class="form-group">

								<c:if test="${modeFlag eq 'A' }">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="voucher.template.entry.vouchertype" text="Voucher Type" /></label>
									<div class="col-sm-4">
										<form:select path="voucherType"
											class="form-control mandColorClass"
											onchange="clearTemplateForData(this)"
											data-rule-required="true">
											<form:option value="">
												<spring:message code="voucher.template.select.voucher.type"
													text="Select Voucher Type" />
											</form:option>
											<c:forEach items="${dto.vouchertTypeLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>
								<c:if test="${modeFlag eq 'E' }">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="voucher.template.entry.vouchertype" text="Voucher Type" /></label>
									<div class="col-sm-4">
										<form:hidden path="voucherType" />
										<form:select path="voucherType" id="voucherTypeEdit"
											class="form-control mandColorClass"
											onchange="clearTemplateForData(this)"
											disabled="${modeFlag ne 'true'}">
											<form:option value="">
												<spring:message code="voucher.template.select.voucher.type"
													text="Select Voucher Type" />
											</form:option>
											<c:forEach items="${dto.vouchertTypeLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>
								<c:if test="${modeFlag eq 'A' }">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="budget.reappropriation.master.departmenttype"
											text="Department" /></label>
									<div class="col-sm-4">
										<form:select path="department"
											class="form-control mandColorClass chosen-select-no-results"
											onchange="clearTemplateForData(this)"
											data-rule-required="true">
											<form:option value="">
												<spring:message
													code="budget.reappropriation.master.selectdepartmenttype"
													text="Select Department" />
											</form:option>
											<c:forEach items="${dto.departmentLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>
								<c:if test="${modeFlag eq 'E' }">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="budget.reappropriation.master.departmenttype"
											text="Department" /></label>
									<div class="col-sm-4">
										<form:hidden path="department" />
										<form:select path="department" id="departmentEdit"
											class="form-control mandColorClass chosen-select-no-results"
											onchange="clearTemplateForData(this)"
											disabled="${modeFlag ne 'true'}">
											<form:option value="">
												<spring:message
													code="budget.reappropriation.master.selectdepartmenttype"
													text="Select Department" />
											</form:option>
											<c:forEach items="${dto.departmentLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="voucher.template.entry.master.templatefor"
										text="Template For" /></label>
								<c:if test="${modeFlag eq 'A' }">
									<div class="col-sm-4">
										<form:select path="templateFor"
											class="form-control mandColorClass chosen-select-no-results"
											onchange="findduplicatecombinationexit(this)"
											data-rule-required="true">
											<form:option value="">
												<spring:message code="voucher.template.select.template"
													text="Select Template For" />
											</form:option>
											<c:forEach items="${dto.templateForLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>
								<c:if test="${modeFlag eq 'E' }">
									<div class="col-sm-4">
										<form:hidden path="templateFor" />
										<form:select path="templateFor" id="templateForEdit"
											class="form-control mandColorClass chosen-select-no-results"
											onchange="findduplicatecombinationexit(this)">
											<form:option value="">
												<spring:message code="voucher.template.select.template"
													text="Select Template For" />
											</form:option>
											<c:forEach items="${dto.templateForLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>
								<label class="col-sm-2 control-label"><spring:message
										code="accounts.master.status" text="Status" /></label>
								<div class="col-sm-4">
									<c:choose>
										<c:when test="${modeFlag eq 'E'}">
											<form:select path="status" id="status" class="form-control"
												disabled="false" onchange="changeAllFlagStatus(this);">
												<form:option value="">
													<spring:message code="accounts.master.sel.status"
														text="Select Status" />
												</form:option>
												<c:forEach items="${dto.statusLookUps}" var="lookUp">
													<c:choose>
														<c:when test="${lookUp.lookUpId eq dto.status}">
															<form:option value="${lookUp.lookUpId}"
																code="${lookUp.lookUpCode}" selected="selected">${lookUp.descLangFirst}</form:option>
														</c:when>
														<c:otherwise>
															<form:option value="${lookUp.lookUpId}"
																code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</form:select>
										</c:when>
										<c:otherwise>
											<form:select path="status" class="form-control"
												disabled="true">
												<c:forEach items="${dto.statusLookUps}" var="lookUp">
													<c:choose>
														<c:when test="${lookUp.lookUpCode eq 'A'}">
															<form:option value="${lookUp.lookUpId}"
																code="${lookUp.lookUpCode}" selected="selected">${lookUp.descLangFirst}</form:option>
														</c:when>
														<c:otherwise>
															<form:option value="${lookUp.lookUpId}"
																code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</form:select>
										</c:otherwise>
									</c:choose>

								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse1" href="#mappingdetails"><spring:message
									code="voucher.template.map.detail" text="Mapping Details" /></a>
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
											<c:if test="${modeFlag eq 'A'}">
												<th width="15%"><spring:message
														code="bank.master.accountType" text="Account Type" /><span
													class="mand">*</span></th>
												<th width="15%" class="text-center"><spring:message
														code="voucher.template.dr.cr" text="Dr. / Cr." /><span
													class="mand">*</span></th>
												<th width="20%"><spring:message
														code="accounts.receipt.payment_mode" text="Mode" /><span
													class="mand">*</span></th>
												<th width="40%"><spring:message
														code="account.bankmaster.acchead" text="Account Head" /><span
													class="mand">*</span></th>
												<th width="10"><spring:message
														code="voucher.template.add.remove" text="Add / Remove" /></th>
											</c:if>

											<c:if test="${modeFlag eq 'E'}">
												<th width="20%"><spring:message
														code="bank.master.accountType" text="Account Type" /><span
													class="mand">*</span></th>
												<th width="10%" class="text-center"><spring:message
														code="voucher.template.dr.cr" text="Dr. / Cr." /><span
													class="mand">*</span></th>
												<th width="20%"><spring:message
														code="accounts.receipt.payment_mode" text="Mode" /><span
													class="mand">*</span></th>
												<th width="50%"><spring:message
														code="account.bankmaster.acchead" text="Account Head" /><span
													class="mand">*</span></th>
											</c:if>
										</tr>

										<c:choose>
											<c:when test="${modeFlag eq 'E'}">
												<c:forEach items="${formDTO.mappingDetails}"
													var="detailInfo" varStatus="testIndex">
													<tr class="tableRowClass">
														<c:set var="index" value="${testIndex.index}" scope="page" />
														<form:hidden path="mappingDetails[${index}].templateIdDet" />
														<td><input type="hidden" id="srNoId_${index}"
															value=""> <form:select
																path="mappingDetails[${index}].accountType"
																class="chosen-select-no-results required-chosen form-control"
																id="accountType_${index}"
																onchange="onAccountTypeModeBudgetHeadChange(${index})">
																<form:option value="">
																	<spring:message code="account.select" text="Select" />
																</form:option>
																<c:forEach items="${dto.accountTypeLookUps}"
																	var="lookUp">
																	<c:choose>
																		<c:when
																			test="${lookUp.lookUpId eq detailInfo.accountType}">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}" selected="selected">${lookUp.descLangFirst}</form:option>
																		</c:when>
																		<c:otherwise>
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
																		</c:otherwise>
																	</c:choose>
																</c:forEach>
															</form:select></td>
														<td><form:select
																path="mappingDetails[${index}].debitCredit"
																class="form-control required-control mandColorClass"
																id="debitCredit_${index}" onchange="onDrCrChange(this)">
																<form:option value="">
																	<spring:message code="account.select" text="Select" />
																</form:option>
																<c:forEach items="${dto.debitCreditLookUps}"
																	var="lookUp">
																	<c:choose>
																		<c:when
																			test="${lookUp.lookUpId eq detailInfo.debitCredit}">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}" selected="selected">${lookUp.descLangFirst}</form:option>
																		</c:when>
																		<c:otherwise>
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
																		</c:otherwise>
																	</c:choose>
																</c:forEach>
															</form:select></td>
														<td><form:select path="mappingDetails[${index}].mode"
																class="chosen-select-no-results required-chosen form-control mode"
																id="mode_${index}"
																onchange="findduplicatecombinationexitEditDynamic(${index})">
																<form:option value="">
																	<spring:message code="account.select" text="Select" />
																</form:option>
																<c:forEach items="${dto.modeLookUps}" var="lookUp">
																	<c:choose>
																		<c:when test="${lookUp.lookUpId eq detailInfo.mode}">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}" selected="selected">${lookUp.descLangFirst}</form:option>
																		</c:when>
																		<c:otherwise>
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
																		</c:otherwise>
																	</c:choose>
																</c:forEach>
															</form:select></td>
														<td><form:select
																path="mappingDetails[${index}].sacHeadId"
																class="chosen-select-no-results required-chosen form-control budgetIdCss"
																id="budgetId_${index}"
																onchange="selectAccountType(${index})">

																<form:option value="">
																	<spring:message code="account.select" text="Select" />
																</form:option>
																<c:if test="${detailInfo.lookupdesc eq 'I'}">
																	<c:forEach items="${pacSacHeadMapI}" var="lookUpI">
																		<form:option value="${lookUpI.key}"
																			code="${lookUpI.key}">${lookUpI.value}</form:option>
																	</c:forEach>
																</c:if>
																<c:if test="${detailInfo.lookupdesc eq 'E'}">
																	<c:forEach items="${pacSacHeadMapE}" var="lookUpE">
																		<form:option value="${lookUpE.key}"
																			code="${lookUpE.key}">${lookUpE.value}</form:option>
																	</c:forEach>
																</c:if>
																<c:if test="${detailInfo.lookupdesc eq 'L'}">
																	<c:forEach items="${pacSacHeadMapL}" var="lookUpL">
																		<form:option value="${lookUpL.key}"
																			code="${lookUpL.key}">${lookUpL.value}</form:option>
																	</c:forEach>
																</c:if>
																<c:if test="${detailInfo.lookupdesc eq 'A'}">
																	<c:forEach items="${pacSacHeadMapA}" var="lookUpA">
																		<form:option value="${lookUpA.key}"
																			code="${lookUpA.key}">${lookUpA.value}</form:option>
																	</c:forEach>
																</c:if>

															</form:select></td>



													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="tableRowClass">
													<c:set var="index" value="${lk.index}" scope="page" />
													<td><input type="hidden" id="srNoId_${index}" value="">
														<form:select path="mappingDetails[${d}].accountType"
															class="chosen-select-no-results required-chosen form-control applyChoosen"
															id="accountType_${d}"
															onchange="onAccountTypeModeBudgetHeadChange(${d})"
															data-rule-required="true">
															<form:option value="">
																<spring:message code="account.select" text="Select" />
															</form:option>
															<c:forEach items="${dto.accountTypeLookUps}" var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
															</c:forEach>
														</form:select></td>
													<td><form:select
															path="mappingDetails[${d}].debitCredit"
															class="form-control required-control mandColorClass"
															id="debitCredit_${d}" onchange="onDrCrChange(this)"
															data-rule-required="true">
															<form:option value="">
																<spring:message code="account.select" text="Select" />
															</form:option>
															<c:forEach items="${dto.debitCreditLookUps}" var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
															</c:forEach>
														</form:select></td>
													<td><form:select path="mappingDetails[${d}].mode"
															class="chosen-select-no-results required-chosen form-control mode applyChoosen"
															id="mode_${d}"
															onchange="findduplicatecombinationexitDynamic(${d})"
															data-rule-required="true">
															<form:option value="">
																<spring:message code="account.select" text="Select" />
															</form:option>
															<c:forEach items="${dto.modeLookUps}" var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
															</c:forEach>
														</form:select></td>

													<td><form:select path="mappingDetails[${d}].sacHeadId"
															class="chosen-select-no-results required-chosen form-control applyChoosen budgetIdCss"
															id="budgetId_${d}" onchange="selectAccountType(${d})"
															data-rule-required="true">
															<form:option value="">
																<spring:message code="account.select" text="Select" />
															</form:option>
														</form:select></td>

													<td class="text-center"><a href="javascript:void(0);"
														data-placement="top" class="addRow btn btn-success btn-sm"
														data-original-title="Add"><i class="fa fa-plus-circle"></i></a>
														<a href="javascript:void(0);" data-placement="top"
														class="removeRow btn btn-danger btn-sm"
														data-original-title="Delete"><i class="fa fa-trash"></i></a></td>
												</tr>
											</c:otherwise>
										</c:choose>

									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="text-center padding-top-10">
				<c:if test="${modeFlag eq 'A' }">
					<input type="button" id="" class="btn btn-success btn-submit" value='<spring:message code="account.configuration.save" text="Save" />'
						onclick="createTemplateSave(this)"></input>
				</c:if>
				<c:if test="${modeFlag eq 'E' }">
					<input type="button" id="" class="btn btn-success btn-submit" value='<spring:message code="account.configuration.save" text="Save" />'
						onclick="createTemplateSave(this)"></input>
				</c:if>
				<c:if test="${modeFlag eq 'A' }">
					<button type="button" class="btn btn-warning" id="createTemplate">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
				</c:if>

				<button type="button" class="btn btn-danger"
					onclick="window.location.href='VoucherTemplate.html'" id="backBtn">
					<spring:message code="account.bankmaster.back" text="Back" />
				</button>
			</div>
		</form:form>
	</div>
</div>
