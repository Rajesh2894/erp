<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/material_mgmt/service/purRequisition.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<style>
.input-group-field {
	display: table-cell;
	width: 114px;
}
</style>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start info box -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<c:choose>
					<c:when test="${command.levelCheck eq 1 }">
						<spring:message code="purchase.requisition.purReqApproval" text="Purchase Requisition Approval" />
					</c:when>
					<c:otherwise>
						<spring:message code="purchase.requisition.fomr.heading" text="Purchase Requisition" />
					</c:otherwise>
				</c:choose>				
			</h2>
			<apptags:helpDoc url="PurchaseRequisition.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="PurchaseRequisition.html" name="PurchaseRequisitionForm" id="PurchaseRequisitionId"
				class="form-horizontal">				
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<h4>
					<spring:message code="store.master.details1" text="Purchase Requisition Details" />
				</h4>
				
				<form:hidden path="formMode" id="formMode" />
				<form:hidden path="cpdMode" id="cpdMode" />
				<form:hidden path="removeYearIds" id="removeYearIds" />
				<form:hidden path="removeDetailIds" id="removeDetailIds" />
				<form:hidden path="levelCheck" id="levelCheck" />

				<div class="form-group">
					<label class="control-label col-sm-2  required-control" for="Store Name"><spring:message
							code="store.master.name" text="Store Name"  /></label>
					<div class="col-sm-4">
						<form:select path="purchaseRequistionDto.storeId" onchange="getStoreData()"
							cssClass="form-control mandColorClass chosen-select-no-results" id="storeNameId"
							data-rule-required="true" disabled="${command.levelCheck gt 0}">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeIdNameList}" var="data">
								<form:option value="${data[0]}" code="${data[2]}">${data[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<apptags:date labelCode="purchase.requisition.reqDate" fieldclass="lessthancurrdate" 
						datePath="purchaseRequistionDto.prDate" isDisabled="${command.levelCheck gt 1}"
						cssClass="fromDateClass" isMandatory="true">
					</apptags:date>
				</div>
				
				<div class="form-group">
					<apptags:input labelCode="store.master.store.incharge" isDisabled="${command.levelCheck gt 0}"
						path="purchaseRequistionDto.departmentName" isMandatory="true"
						cssClass="form control mandcolour" isReadonly="true">
					</apptags:input>
					<apptags:input labelCode="store.master.location" isDisabled="${command.levelCheck gt 0}"
						path="purchaseRequistionDto.requestedName" isMandatory="true"
						cssClass="form control mandcolour hasNameClass" isReadonly="true">
					</apptags:input>
				</div>
				
				<h4>
					<spring:message code="material.item.master.itemDetails" text="Item Details" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table id="unitDetailTable" class="table table-striped table-bordered appendableClass unitDetail">
								<thead>
									<tr>
										<th><spring:message code="store.master.srno" text="Sr.No." /> <span class="mand showMand">*</span></th>
										<th><spring:message code="material.item.master.name" text="Item Name"  /><span class="mand showMand">*</span></th>
										<th><spring:message code="store.master.uom" text="Uom" /><span class="mand showMand">*</span></th>
										<th><spring:message code="store.master.quantity" text="Quantity" /><span class="mand showMand">*</span></th>
										<th><spring:message code="material.item.master.tax" text="Tax" /></th>
										<c:if test="${command.levelCheck eq 0 && command.formMode eq 'C'}">
											<th width="10%"><spring:message code="material.management.action" text="Action" /></th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(command.purchaseRequistionDto.purchaseRequistionDetDtoList)>0}">
											<c:forEach items="${command.purchaseRequistionDto.purchaseRequistionDetDtoList}" var="data" varStatus="index">
												<tr class="firstUnitRow">
													<form:hidden path="purchaseRequistionDto.purchaseRequistionDetDtoList[${d}].prdetId" id="prdetId${d}" />
													<td align="center" width="10%"><form:input path="" cssClass="form-control mandColorClass text-center" id="sequence${d}" value="${d+1}" disabled="true" />
													<td><form:select path="purchaseRequistionDto.purchaseRequistionDetDtoList[${d}].itemId" id="itemId${d}"
															cssClass="form-control mandColorClass chosen-select-no-results" data-rule-required="true" 
															onchange="getUomName('${d}')" disabled="${command.levelCheck gt 0}" >
															<form:option value="0">
																<spring:message code="material.management.select" text="Select"  />
															</form:option>
															<c:forEach items="${command.itemIdNameList}" var="item">
																<form:option value="${item[0]}">${item[1]}</form:option>
															</c:forEach>
														</form:select></td>
													<td><form:input path="purchaseRequistionDto.purchaseRequistionDetDtoList[${d}].uonName"
															id="uomId${d}" type="text" class="form-control" disabled="true" /></td>
													<td><form:input path="purchaseRequistionDto.purchaseRequistionDetDtoList[${d}].quantity"
															id="quantity${d}" type="text" class="form-control" 
															onkeypress="return hasAmount(event, this, 11, 1)"/></td>
													<td><form:input path=""
															id="taxId${d}" type="text" class="form-control" disabled="true"></form:input></td>
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:forEach>										
										</c:when>
										
										<c:otherwise>
											<tr class="firstUnitRow">
												<form:hidden path="purchaseRequistionDto.purchaseRequistionDetDtoList[${d}].prdetId" id="prdetId${d}" />
												<td align="center" width="10%"><form:input path="" cssClass="form-control mandColorClass text-center" id="sequence${d}" value="${d+1}" disabled="true" />
												<td><form:select path="purchaseRequistionDto.purchaseRequistionDetDtoList[${d}].itemId" id="itemId${d}" 
														cssClass="form-control mandColorClass chosen-select-no-results" data-rule-required="true" 
														onchange="getUomName('${d}')" disabled="${command.levelCheck gt 0}" >
														<form:option value="0">
															<spring:message code="material.management.select" text="Select"  />
														</form:option>
														<c:forEach items="${command.itemIdNameList}" var="item">
															<form:option value="${item[0]}">${item[1]}</form:option>
														</c:forEach>
													</form:select></td>
												<td><form:input path="purchaseRequistionDto.purchaseRequistionDetDtoList[${d}].uonName"
														id="uomId${d}" type="text" class="form-control" disabled="true" /></td>
												<td><form:input  path="purchaseRequistionDto.purchaseRequistionDetDtoList[${d}].quantity"
														id="quantity${d}" type="text" class="form-control" 
														onkeypress="return hasAmount(event, this, 11, 1)"/></td>
												<td><form:input path=""
														id="taxId${d}" type="text" class="form-control" disabled="true"></form:input></td>												 
												 <c:if test="${command.levelCheck eq 0}">
													<td align="center"><a href="javascript:void(0);"
															title="<spring:message code="material.management.add" text="Add" />"
															class="addPurReq btn btn-success btn-sm unit" id="addUnitRow"><i
															class="fa fa-plus-circle"></i></a>
														<a href='javascript:void(0);' class="btn btn-danger btn-sm delButton"
															title="<spring:message code="material.management.delete" text="Delete" />">
															<i class="fa fa-trash-o"></i></a>	
													</td>
												</c:if>
											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:otherwise>
									</c:choose>									
								</tbody>
							</table>
						</div>
					</div>
				</div>
				
				<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a3" data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" 
									href="#a3"> <spring:message code="material.management.fin.info" text="Financial Year-wise Information"/></a>
							</h4>
						</div>
					<div id="a3" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="">
								<c:set var="d" value="0" scope="page"></c:set>
								<table class="table table-bordered table-striped" id="financeDataDetails">
									<thead>
										<tr>
											<th width="18%"><spring:message code="work.def.fin.year"
													text="Financial Year" /> <span class="mand showMand">*</span></th>
											<th width="18%"><spring:message code="work.def.fin.code"
													text="Finance Code" /> <span class="mand showMand">*</span></th>
											<th width="18%"><spring:message code="work.def.docs.no" text="Approval No." /></th>
											<th width="18%"><spring:message code="purchase.requisition.estimated.amount"
													text="Estimated Amount" /></th>
											<th width="18%"><spring:message code="work.def.view.budget" /></th>
											<%-- Note::
								As Discussed with Samadhan sir, 
								with Ref. to User Story #161240 >> 
								Removed Add/Delete buttons of Purchase Requisition > Financial Year-wise Information >>
								 Should be Only one Budget Head >> Else, Will raise Conflict in Invoice Entry Service for Payment process
											<c:if test="${command.levelCheck eq 0 && command.formMode eq 'C'}">
												<th width="10"><spring:message code="works.management.action" /></th>
											</c:if> --%>
										</tr>
									</thead>

									<tbody>
										<c:choose>
											<c:when test="${fn:length(command.purchaseRequistionDto.yearDto)>0 }">
												<c:forEach items="${command.purchaseRequistionDto.yearDto}">
													<tr class="finacialInfoClass">
														<form:hidden path="purchaseRequistionDto.yearDto[${d}].yearId" id="yearId${d}" />
														<form:hidden path="purchaseRequistionDto.yearDto[${d}].finActiveFlag"
															id="finActiveFlag${d}" value="A" />
														<td><form:select path="purchaseRequistionDto.yearDto[${d}].faYearId"
																cssClass="form-control  chosen-select-no-results" onchange="resetFinanceCode(this,${d});"
																id="faYearId${d}" disabled="${command.levelCheck gt 0}">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.faYears}" var="lookUp">
																	<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
																</c:forEach>
															</form:select></td>
														<c:choose>
															<c:when test="${command.cpdMode eq 'L'}">
																<td><form:select path="purchaseRequistionDto.yearDto[${d}].sacHeadId"
																		cssClass="form-control chosen-select-no-results"
																		onchange="checkForDuplicateHeadCode(this,${d});"
																		id="sacHeadId${d}"
																		disabled="${command.levelCheck gt 0}">
																		<form:option value="">
																			<spring:message code='work.management.select' />
																		</form:option>
																		<c:forEach items="${command.budgetList}" var="lookUp">
																			<form:option value="${lookUp.sacHeadId}">${lookUp.acHeadCode}</form:option>
																		</c:forEach>
																	</form:select></td>
															</c:when>
															<c:otherwise>
																<td><form:input path="purchaseRequistionDto.yearDto[${d}].financeCodeDesc"
																		cssClass="form-control" disabled="${command.levelCheck gt 0}"
																		onchange="checkForDuplicateFinanceCode(this,${d});" id="financeCodeDesc${d}"
																		onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
															</c:otherwise>
														</c:choose>
														<td><form:input path="purchaseRequistionDto.yearDto[${d}].yeDocRefNo"
																cssClass="form-control" id="yeDocRefNo${d}" maxlength="50"
																onkeyup="inputPreventSpace(event.keyCode,this);"
																disabled="${command.levelCheck gt 0}" /></td>
														<td><form:input path="purchaseRequistionDto.yearDto[${d}].yeBugAmount"
																cssClass="form-control text-right " id="yeBugAmount${d}"
																onkeypress="return hasAmount(event, this, 13, 2)"
																onchange="getAmountFormatInDynamic((this),'yeBugAmount')"
																onkeyup="getTotalAmount()" /></td>
														<td class="text-center">
															<button type="button" class="btn btn-primary btn-sm"
																onclick="return viewExpenditureDetails(${d});"
																id="viewExpDet${d}"
																title="<spring:message code="work.def.view.budget" />">
																<i class="fa fa-eye" aria-hidden="true"></i>
															</button>
														</td>
														<c:set var="d" value="${d + 1}" scope="page" />
													</tr>
												</c:forEach>
											</c:when>

											<c:otherwise>
												<tr class="finacialInfoClass">
													<form:hidden path="purchaseRequistionDto.yearDto[${d}].yearId" id="yearId${d}" />
													<form:hidden path="purchaseRequistionDto.yearDto[${d}].finActiveFlag"
														id="finActiveFlag${d}" value="A" />
													<td><form:select path="purchaseRequistionDto.yearDto[${d}].faYearId"
															cssClass="form-control  chosen-select-no-results"
															onchange="resetFinanceCode(this,${d});" id="faYearId${d}">
															<form:option value="">
																<spring:message code='work.management.select' />
															</form:option>
															<c:forEach items="${command.faYears}" var="lookUp">
																<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
															</c:forEach>
														</form:select></td>
													<c:choose>
														<c:when test="${command.cpdMode eq 'L'}">
															<td><form:select path="purchaseRequistionDto.yearDto[${d}].sacHeadId"
																	cssClass="form-control chosen-select-no-results"
																	onchange="checkForDuplicateHeadCode(this,${d});" id="sacHeadId${d}">
																	<form:option value="">
																		<spring:message code='work.management.select' />
																	</form:option>
																	<c:forEach items="${command.budgetList}" var="lookUp">
																		<form:option value="${lookUp.sacHeadId}">${lookUp.acHeadCode}</form:option>
																	</c:forEach>
																</form:select></td>
														</c:when>
														<c:otherwise>
															<td><form:input	path="purchaseRequistionDto.yearDto[${d}].financeCodeDesc"
																	onchange="checkForDuplicateFinanceCode(this,${d});"
																	cssClass="form-control" id="financeCodeDesc${d}"
																	onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
														</c:otherwise>
													</c:choose>
													<td><form:input path="purchaseRequistionDto.yearDto[${d}].yeDocRefNo"
															cssClass="form-control" id="yeDocRefNo${d}" maxlength="50"
															onkeyup="inputPreventSpace(event.keyCode,this);" /></td>
													<td><form:input path="purchaseRequistionDto.yearDto[${d}].yeBugAmount"
															cssClass="form-control Comp text-right" id="yeBugAmount${d}"
															onkeypress="return hasAmount(event, this, 13, 2)"
															onchange="getAmountFormatInDynamic((this),'yeBugAmount')"
															onkeyup="getTotalAmount()" /></td>
													<td class="text-center">
														<button type="button" class="btn btn-primary btn-sm"
															onclick="return viewExpenditureDetails(${d});" id="viewExpDet${d}"
															title="<spring:message code="work.def.view.budget" />">
															<i class="fa fa-eye" aria-hidden="true"></i>
														</button>
													</td>

													<%--Note::
										As Discussed with Samadhan sir, 
										with Ref. to User Story #161240 >> 
										Removed Add/Delete buttons of Purchase Requisition > Financial Year-wise Information >>
										 Should be Only one Budget Head >> Else, Will raise Conflict in Invoice Entry Service for Payment process
																											
													<td align="center"><a href="javascript:void(0);"
														class="btn btn-success btn-sm unit addFinanceDetails"
														title="<spring:message code="works.management.add"/>">
															<i class="fa fa-plus-circle"></i>
													</a> <a href="javascript:void(0);"
														class="btn btn-danger btn-sm delButton"
														title="<spring:message code="works.management.delete" />"><i
															class="fa fa-minus"></i></a></td> --%>
													<c:set var="d" value="${d + 1}" scope="page" />
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				
				<c:if test="${command.levelCheck eq 1 }">
					<br/>
					<apptags:CheckerAction hideForward="true" hideSendback="true" hideUpload="true"></apptags:CheckerAction>
				</c:if>
				  
				<div class="text-center padding-bottom-10">
					<c:choose>
						<c:when test="${command.levelCheck eq 0}">
							<button type="button" class="btn btn-blue-2" onclick="draftForm(this);">
								<spring:message code="master.save" text="Save" />
							</button>
						</c:when>
						<c:when test="${command.levelCheck eq 1}">
							<button type="button" class="btn btn-success btn-submit" onclick="draftForm(this);">
								<spring:message code="material.management.submit" text="Submit" />
							</button>
						</c:when>
					</c:choose>
					<c:if test="${command.levelCheck eq 0}">
						<button type="button" class="btn btn-success btn-submit" onclick="savePurchaseRequisitionForm(this);">
							<spring:message code="material.management.submit" text="Submit"></spring:message>
						</button>
				 	</c:if>
				 	<c:if test="${command.levelCheck eq 0 && command.formMode eq 'C'}">
						<button type="button" class="btn btn-warning" onclick="resetPurReqForm();">
							<spring:message code="material.management.reset" text="Reset"></spring:message>
						</button>
					</c:if>
					<c:choose>
						<c:when test="${command.levelCheck eq 0}">
							<button type="button" class="button-input btn btn-danger" onclick="backPurReqForm();">
								<spring:message code="material.management.back" text="Back" />
							</button>								
						</c:when>
						<c:otherwise>
							<apptags:backButton url="AdminHome.html"></apptags:backButton>			
						</c:otherwise>
					</c:choose>					
				</div>
			</form:form>
		</div>
	</div>
</div>