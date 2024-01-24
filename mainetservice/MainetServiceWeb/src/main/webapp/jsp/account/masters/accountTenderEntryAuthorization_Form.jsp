<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/account/accountTenderEntryAuthorization.js"
	type="text/javascript"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script>
$(function() {
	
	$("#cashDepositPanel").hide();
	$("#cashWithDrawPanel").hide();
	$("#entryType").val("1"); 
});
</script>

<script>
	$(document).ready(function(){
		
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}
		
	});
	
	$(document).ready(function() {
		
		var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
		var disableBeforeDate = new Date(response[0], response[1], response[2]);
		var date = new Date();
		var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
		$('#trTenderDate').datepicker({
	         dateFormat: 'dd/mm/yy',
	        changeMonth: true,
	        changeYear: true,
	        minDate: disableBeforeDate,
	        maxDate: today,
			onSelect: function(dateText, inst) {
	         $(this).focus();
	         checkProposalDate();
			} 
		});
		$("#trTenderDate").keyup(function(e){
		    if (e.keyCode != 8){    
		        if ($(this).val().length == 2){
		            $(this).val($(this).val() + "/");
		        }else if ($(this).val().length == 5){
		            $(this).val($(this).val() + "/");
		        }
		     }
		    });
		
		$('#trProposalDate').datepicker({
	        dateFormat: 'dd/mm/yy',
	       changeMonth: true,
	       changeYear: true,
	       minDate: disableBeforeDate,
	       maxDate: today,
			onSelect: function(dateText, inst) {
	        $(this).focus();
			} 
		});
		 $("#trProposalDate").keyup(function(e){
			    if (e.keyCode != 8){    
			        if ($(this).val().length == 2){
			            $(this).val($(this).val() + "/");
			        }else if ($(this).val().length == 5){
			            $(this).val($(this).val() + "/");
			        }
			     }
			    });
		});
	
</script>

<div class="form-div" id="content">
	<div class="widget" id="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="account.pay.tds.work.order"
					text="Work Order Entry Authorization" />
			</h2>
		<apptags:helpDoc url="AccountTenderEntryAuthorization.html" helpDocRefURL="AccountTenderEntryAuthorization.html"></apptags:helpDoc>		
		</div>

		<div class="widget-content padding" id="tenderDiv">

			<c:url value="${saveAction}" var="url_form_submit" />
			<c:url value="${mode}" var="form_mode" />

			<form:form method="POST"
				action="AccountTenderEntryAuthorization.html" name="tenderEntry"
				id="tenderEntry" class="form-horizontal"
				modelAttribute="accountTenderEntryBean">

				<form:hidden path="" id="indexdata" />
				<form:hidden path="trTenderId" id="trTenderId" />
				<form:hidden path="trTenderNo" id="trTenderNo" />
				<form:hidden path="" value="${keyTest}" id="keyTest" />
				<form:input type="hidden" path="" value="${fn:length(list)}"
					id="Count" />
				<form:hidden path="trTenderAmount" id="trTenderAmount" />
				<form:hidden path="" value="${budgetDefParamStatus}" id="budgetDefParamStatusFlag" />

				<c:if test="${!viewMode}">
					<div class="mand-label clearfix">
						<span><spring:message code="account.common.mandmsg" /> <i
							class="text-red-1">*</i> <spring:message
								code="account.common.mandmsg1" /></span>
					</div>
				</c:if>

				<div class="warning-div alert alert-danger alert-dismissible hide"
					id="clientSideErrorDivScrutiny"></div>

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<h4>
					<spring:message code="" text="Work Order Details"></spring:message>
				</h4>
				<div id="tender_entry1" class="panel-collapse collapse in">
					<div class="panel-body">
						<c:if test="${form_mode =='update'}">
							<input type=hidden value="edit" id="editModeExp" />
							<div class="form-group">
								<label class="control-label col-sm-2 required-control"
									for="trTenderNo"><spring:message
										code="account.tenderentrydetails.tenderno" text="" /></label>
								<div class="col-sm-4">
									<form:input type="text" path="trTenderNo" disabled="true"
										class="form-control" />
								</div>
							</div>
						</c:if>

						<div class="form-group">

							<label class="control-label col-sm-2 required-control"
								for="dpDeptid"><spring:message
									code="account.tenderentrydetails.Department" text=""></spring:message></label>
							<div class="col-sm-4">
								<form:select path="dpDeptid" id="dpDeptid"
									class="form-control mandColorClass chosen-select-no-results"
									disabled="${viewMode}" data-rule-required="true"
									onchange="loadBudgetExpenditureData(this)">
									<form:option value="">
										<spring:message code="" text="Select Department" />
									</form:option>
									<c:forEach items="${deptMap }" varStatus="status" var="deptMap">
										<form:option value="${deptMap.key }" code="${ deptMap.key}">${deptMap.value}</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="control-label  col-sm-2 required-control"
								for="vmVendorid"><spring:message
									code="account.tenderentrydetails.VendorEntry"
									text="Vendor Name" /></label>
							<div class="col-sm-4">
								<form:select path="vmVendorid" id="vmVendorid"
									class="form-control mandColorClass chosen-select-no-results"
									disabled="${viewMode}" data-rule-required="true"
									onchange="loadDepositTypeEmdData(this)">
									<form:option value="">
										<spring:message code="" text="Select Vendor Name" />
									</form:option>
									<c:forEach items="${vendorMap}" varStatus="status"
										var="vendorMap">
										<form:option value="${vendorMap.key }"
											code="${vendorMap.key }">${ vendorMap.value}</form:option>
									</c:forEach>
								</form:select>
							</div>

						</div>
						
						<div class="form-group">

							<label class="control-label col-sm-2 required-control"
								for="trTenderDate"><spring:message
									code="account.tenderentrydetails.tenderdate" text="" /></label>
							<div class="col-sm-4">
								<form:input type="text" path="trTenderDate" id="trTenderDate" maxLength="10"
									cssClass="datepicker cal form-control mandColorClass"
									data-rule-required="true" />
							</div>

							<label class="control-label col-sm-2 required-control" for="trTypeCpdId"><spring:message
									code="account.tenderentrydetails.tendertype" text="" /></label>
							<div class="col-sm-4">
							
								<form:select id="trTypeCpdId" path="trTypeCpdId"
									class="form-control" disabled="${viewMode}" data-rule-required="true">
									<form:option value="">
										<spring:message code="" text="Select Work Order Type" />
									</form:option>
									<c:forEach items="${tenderType}" varStatus="status"
										var="tenderTypeLookUp">
										<form:option value="${tenderTypeLookUp.lookUpId }"
											code="${tenderTypeLookUp.lookUpId }">${tenderTypeLookUp.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>

						</div>


						<div class="form-group">



							<label class="control-label col-sm-2 required-control "
								for="trProposalNo"><spring:message
									code="account.tenderentrydetails.proposalnumber" /></label>
							<div class="col-sm-4">
								<form:input id="trProposalNo" path="trProposalNo"
									class="form-control mandColorClass"
									disabled="${viewMode}" data-rule-required="true" />
							</div>

							<label class="control-label col-sm-2  required-control"
								for="trProposalDate"><spring:message
									code="account.tenderentrydetails.proposaldate" text="" /></label>
							<div class="col-sm-4">
								<form:input id="trProposalDate" path="trProposalDate"
									cssClass="datepicker cal form-control mandColorClass"
									 data-rule-required="true" onchange="checkWorkOrderDate()"  maxlength="10"/>
							</div>

						</div>

						<div class="form-group">
							<label class="control-label col-sm-2 required-control"
								for="trNameofwork"><spring:message
									code="account.tenderentrydetails.worksdetail" text="" /></label>
							<div class="col-sm-4">
								<form:textarea type="text" id="trNameofwork" path="trNameofwork"
									class="form-control mandColorClass" disabled="${viewMode}"
									data-rule-required="true" />
							</div>

							<label class="control-label col-sm-2 "
								for="specialconditions"><spring:message
									code="account.tenderentrydetails.SpecialCondition" /></label>
							<div class="col-sm-4">
								<form:textarea name="text" id="specialconditions"
									path="specialconditions" class="form-control"
									disabled="${viewMode}" />
							</div>
							
						</div>
						
						<div class="form-group">

							<label class="control-label col-sm-2"
								for="trEmdAmt"><spring:message
									code="account.tenderentrydetails.emdamount" /></label>
							<div class="col-sm-4">

								<form:select path="trEmdAmt" id="trEmdAmt"
									class="form-control chosen-select-no-results"
									disabled="${viewMode}" >
									<form:option value="">
										<spring:message code="" text="Select Emd Details" />
									</form:option>
									<c:forEach items="${depositCodeMap}" varStatus="status"
										var="depositMap">
										<form:option value="${depositMap.key }"
											code="${depositMap.key }">${ depositMap.value}</form:option>
									</c:forEach>
								</form:select>
								
							</div>
							</div>
							
					</div>
				</div>

				<h4>
					<spring:message code="" text="Expenditure Details"></spring:message>
				</h4>

				<div id="trTenderId" class="">
					<div class="table-overflow-sm" id="budRevTableDivID">
						<table class="table  table-bordered table-striped "
							id="budRevTable">
							<tbody>
								<tr>
									<th width=450><spring:message
											code="account.tenderentrydetails.budgethead" /><span
										class="mand float-right">*</span></th>
									<th width=250><spring:message
											code="account.tenderentrydetails.budgetprovision" /><span
										class="mand float-right"></span></th>
									<th width=250><spring:message
											code="account.tenderentrydetails.balancebudgetprovision" /><span
										class="mand float-right"></span></th>
									<th width=250><spring:message
											code="account.tenderentrydetails.workorderamount" /><span
										class="mand float-right">*</span></th>
								</tr>

								<c:forEach items="${list}" var="tenderlevel" varStatus="status">
									<c:set value="${status.index}" var="count"></c:set>
									<tr id="budRevId" class="appendableClass">
										<form:hidden path="tenderDetList[${count}].trTenderidDet"
											value="${tenderDetList[count].trTenderidDet}"
											code="${tenderDetList[count].trTenderidDet}"
											id="trTenderidDet${count}" />
										<td><form:select id="sacHeadId${count}"
												path="tenderDetList[${count}].sacHeadId"
												cssClass="form-control chosen-select-no-results"
												disabled="${viewmode}" data-rule-required="true"
												onchange="getOrgBalAmount(${count})">
												<form:option value="">
													<spring:message code="account.common.select" />
												</form:option>
												<c:forEach items="${accountBudgetCodeMap}"
													varStatus="status" var="pacItem">
													<form:option value="${pacItem.key }" code="${pacItem.key }">${pacItem.value} </form:option>
												</c:forEach>
											</form:select></td>

										<td><form:input id="budgetaryProv${count}"
												path="tenderDetList[${count}].budgetaryProv"
												class="form-control text-right" readonly="true" /></td>

										<td><form:input id="balanceProv${count }"
												path="tenderDetList[${count }].balanceProv"
												class="form-control text-right" readonly="true" /></td>

										<td><form:input id="trTenderAmount${count}"
												path="tenderDetList[${count}].trTenderAmount"
												class="form-control text-right"
												onkeypress="return hasAmount(event, this, 13, 2)"
												disabled="${viewMode}" data-rule-required="true"
												onkeyup="copyContent(this)"
												onchange="getAmountFormatInDynamic((this),'trTenderAmount')" /></td>

										
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<div class="form-group margin-top-10">
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="budget.additionalsupplemental.authorization.fieldname"
							text=""></spring:message></label>
					<div class="col-sm-10">

						<label for="approved1" class="radio-inline mandColorClass"><form:radiobutton
								path="authStatus" value="Y" id="approved1"
								data-rule-required="true" /> <spring:message
								code="account.pay.tds.approved" text="Approved" /> </label> <label
							for="disApproved" class="radio-inline mandColorClass"><form:radiobutton
								path="authStatus" value="N" id="disApproved"
								data-rule-required="true" /> <spring:message
								code="account.pay.tds.disapproved" text="Unapproved" /> </label>

					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="budget.additionalsupplemental.master.remark" text=""></spring:message>
					</label>
					<div class="col-sm-10">
						<form:textarea id="authRemark" path="authRemark"
							class="form-control mandColorClass" maxLength="200"
							data-rule-required="true" />
					</div>
				</div>



				<div class="text-center padding-10">
					<c:if test="${!viewMode}">

						<button type="button" class="btn btn-success btn-submit"
							onclick="saveTenderEntryForm(this)" id="btnSave">
							<spring:message code="account.configuration.save" text="Save" />
						</button>
						<c:if test="${form_mode !='update'}">
							<button type="Reset" class="btn btn-warning createData">
								<spring:message code="account.bankmaster.reset" text="Reset" />
							</button>
						</c:if>
					</c:if>
					<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountTenderEntryAuthorization.html'"
							value="<spring:message code="" text="Back"/>" id="cancelEdit" />
					
				</div>
			</form:form>
		</div>
	</div>
</div>
