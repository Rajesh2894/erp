<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ page import="java.io.*,java.util.*"%>
<script src="js/mainet/validation.js"></script>
<script>
$(document).ready(function() {
	
	$('.accountClass').each(function(i) {
		if($("#type"+i).val()==0 ||$("#type"+i).val()=='0')
			{
		$("#tr"+i).find('div.chosen-container').remove();
		$("#tr"+i).find(".applyChoosen").chosen().trigger("chosen:updated");
			}
	});
	if('${command.mode}'!='A')
	{
		var config = {
			      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			    }
			    for (var selector in config) {
			      $(selector).chosen(config[selector]);
			    }
			 
	}
	  $('.required-control').next().children().addClass('mandColorClass');
	var cnt = $('#tbl tr').length - 1;
	$("#voucherEntry").hide();
 $('.accountClass').each(function(i) {
		  if($("#isDeleted"+i).val()=="D")
		  {	
		 $('#tr'+i).hide();
		  }
		 $(".deletedRow").hide();
		 if($('#type'+i).find('option:selected').attr('code')=='DR')
			{
			$("#amount"+i).addClass("debitClass").removeClass("creditClass");
			}
		else if($('#type'+i).find('option:selected').attr('code')=='CR')
			{
			$("#amount"+i).addClass("creditClass").removeClass("debitClass");
			}
		else
			{
			$("#amount"+i).removeClass("debitClass creditClass");
			}
			}); 
 
 $("#voucherType").change(function(){
	 $('[id^="type"]').val("0");
	$('[id^="amount"]').val("");
	 $('[id^="secondary"]').chosen('destroy'); 
	 $('[id^="secondary"]').empty();
	
	 $('[id^="secondary"]').append('<option value="0">Select Account Head</option>');
	 
 });
 
if('${command.mode}'=='AUTH')
{
$("#voucherType").attr("disabled","true");
}
if('${command.isAuthApplication}' =='Y' || '${command.modeView}'=='V')
{
$('input[type=text]').attr('disabled',true);
$('label.control-label').each(function(){
	$(this).removeClass('required-control');
	
});

$('.addDedButton').attr('disabled',true);
$('.delDedButton').attr('disabled',true);
$("#narration").attr('disabled',true);
$('select').attr("disabled", true);	
$("#AuthDiv").hide();
$('select').removeClass('mandColorClass');
$('#narration').removeClass('mandColorClass');
$('.datepicker ').removeClass("datepicker hasDatepicker");
$("select[id^=secondary]").trigger("chosen:updated");
$("#voucherEntry").show();
}
}); 
function printdiv(printpage)
{
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr+newstr+footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}

</script>
<div class="widget-header">
	<h2>
		<strong><spring:message
				code="account.journal.voucher.transaction"
				text="Voucher Transaction" /></strong>
	</h2>
	<apptags:helpDoc url="AccountVoucherEntry.html" helpDocRefURL="AccountVoucherEntry.html"></apptags:helpDoc>	
</div>
<div class="widget-content padding" id="receipt">
	<div class="mand-label clearfix">
		<span><spring:message code="" text="Field with" /> <i
			class="text-red-1">*</i> <spring:message code="" text="is mandatory" /></span>
	</div>
	<!--Add Section Start Here-->
	<form:form class="form-horizontal" cssClass="form-horizontal"
		name="frmAccountJournalVoucherEntry"
		id="frmAccountJournalVoucherEntry" method="POST"
		action="AccountVoucherEntry.html">

		<div class="alert alert-danger alert-dismissible error-div"
			id="errorDivId" style="display: none;">
			<button type="button" class="close" onclick="closeOutErrBox()"
				aria-label="Close">
				<span>&times;</span>
			</button>
			<span id="errorId"></span>
		</div>
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<form:hidden path="mode" id="formMode" />

		<div class="form-group" id="voucherEntry">
			<label class="col-sm-2 control-label"><spring:message code=""
					text="Voucher No." /></label>
			<div class="col-sm-4">
				<form:input path="entity.vouNo" cssClass="form-control"
					id="voucherNo" readonly="true"></form:input>
			</div>
			<label class="col-sm-2 control-label required-control"
				for="voucherDate"><spring:message code=""
					text="Voucher Date" /></label>
			<div class="col-sm-4">
				<div class="input-group">
					<form:input type="text" class="form-control datepicker dateClass"
						id="voucherDate" path="entity.vouDate" readonly="true" />
					<label class="input-group-addon" for="voucherDate"><i
						class="fa fa-calendar"></i><span class="hide"><spring:message
								code="" text="Date" /></span></label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label required-control"
				for="voucherType"><spring:message code=""
					text="Voucher Type" /></label>
			<div class="col-sm-4">
				<c:set var="baseLookupCode" value="VOT" />
				<form:select path="entity.vouTypeCpdId" class="form-control"
					id="voucherType">
					<form:option value="0">
						<spring:message code="" text="Select Voucher Type" />
					</form:option>
					<c:forEach items="${command.getLevelData(baseLookupCode)}"
						var="lookUp">
						<form:option value="${lookUp.lookUpId}"
							code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
					</c:forEach>
				</form:select>
			</div>
			<label class="col-sm-2 control-label "><spring:message
					code="" text="Transaction Ref No." /></label>
			<div class="col-sm-4">
				<form:input type="text" path="entity.vouReferenceNo" id="refNo"
					class="form-control" />
			</div>
		</div>
		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse">
			<div class="panel panel-default">
				<div class="panel-body">
					<c:choose>
						<c:when test="${empty command.entity.details}">
							<div class="table-overflow-sm">
								<table class="table table-bordered table-condensed" id="tbl">

									<tr>
										<th width="20%" scope="col"><spring:message code=""
												text="DR/CR" /><span class="mand">*</span></th>
										<th width="60%" scope="col"><spring:message code=""
												text="Account Heads" /><span class="mand">*</span></th>
										<th width="20%" scope="col"><spring:message code=""
												text="Amount" /><span class="mand">*</span></th>
										<th>Add/Remove</th>
									</tr>

									<tr id="tr0" class="accountClass">

										<form:hidden path="entity.details[0].voudetId" id="voutId0" />

										<td><c:set var="baseLookupCode" value="DCR" /> <form:select
												path="entity.details[0].drcrCpdId" class="form-control"
												id="type0" onchange="setStatusClass(this)">
												<form:option value="0">
													<spring:message code="bank.master.selstatus" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>

												</c:forEach>
											</form:select></td>

										<td><form:select path="entity.details[0].sacHeadId"
												class="form-control applyChoosen" id="secondary0"
												onchange="changeList(0)">
												<form:option value="0">
													<spring:message
														code="account.budget.code.master.selectaccountheads"
														text="Select Account Head" />
												</form:option>
												<c:forEach
													items="${command.entity.details[0].accountHeadCodeList}"
													var="lookUp">
													<form:option value="${lookUp.key}">${lookUp.value}</form:option>
												</c:forEach>

											</form:select></td>
										<td><form:input path="entity.details[0].voudetAmt"
												cssClass="form-control hasAmount text-right" id="amount0"
												onchange="validateAmount(0)"></form:input></td>
										<td class="deletedRow"><form:input
												path="entity.details[0].deleted" id="isDeleted0" /></td>
										<td><a data-placement="top" title="Add"
											class="btn btn-success btn-sm addDedButton" id="addDetails0"
											onclick="addRow(0)" tabindex="7"><i
												class="fa fa-plus-circle"></i></a> <a data-placement="top"
											title="Delete" class="btn btn-danger btn-sm delDedButton"
											id="deleteDetails0" onclick="removeRow(0)"><i
												class="fa fa-trash-o"></i></a></td>
									</tr>
									<tr id="tr1" class="accountClass">

										<form:hidden path="entity.details[1].voudetId" id="voutId1" />
										<td><c:set var="baseLookupCode" value="DCR" /> <form:select
												path="entity.details[1].drcrCpdId" class="form-control"
												id="type1" onchange="setStatusClass(this)">
												<form:option value="0">
													<spring:message code="bank.master.selstatus" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select path="entity.details[1].sacHeadId"
												class="form-control applyChoosen" id="secondary1"
												onchange="changeList(1)">
												<form:option value="0">
													<spring:message
														code="account.budget.code.master.selectaccountheads"
														text="Select Account Head" />
												</form:option>
												<c:forEach
													items="${command.entity.details[1].accountHeadCodeList}"
													var="lookUp">
													<form:option value="${lookUp.key}">${lookUp.value}</form:option>
												</c:forEach>

											</form:select></td>
										<td><form:input path="entity.details[1].voudetAmt"
												cssClass="form-control hasAmount text-right" id="amount1"
												onchange="validateAmount(1)"></form:input></td>

										<td class="deletedRow"><form:input
												path="entity.details[1].deleted" id="isDeleted1" /></td>
										<td><a data-placement="top" title="Add"
											class="btn btn-success btn-sm addDedButton" id="addDetails1"
											onclick="addRow(1)"><i class="fa fa-plus-circle"></i></a> <a
											data-placement="top" title="Delete"
											class="btn btn-danger btn-sm delDedButton"
											id="deleteDetails1" onclick="removeRow(1)"><i
												class="fa fa-trash-o"></i></a></td>
									</tr>
								</table>
							</div>
						</c:when>
						<c:otherwise>


							<div class="table-overflow-sm">
								<table class="table table-bordered table-condensed" id="tbl">
									<tr>
										<th scope="col"><spring:message code=""
												text="Debit/Credit" /><span class="mand">*</span></th>
										<th scope="col"><spring:message code=""
												text="Account Head Code" /><span class="mand">*</span></th>
										<th scope="col"><spring:message code="" text="Amount" /><span
											class="mand">*</span></th>
										<c:if test="${command.isAuthApplication ne 'Y'}">
											<th>Add/Remove</th>
										</c:if>
									</tr>
									<c:forEach items="${command.entity.details}" var="details"
										varStatus="status">
										<tr id="tr${status.count-1}" class="accountClass">
											<form:hidden
												path="entity.details[${status.count-1}].voudetId"
												id="voutId${status.count-1}" />
											<td><c:set var="baseLookupCode" value="DCR" /> <form:select
													path="entity.details[${status.count-1}].drcrCpdId"
													class="form-control" id="type${status.count-1}"
													onchange="setStatusClass(this)">
													<form:option value="0">
														<spring:message code="bank.master.selstatus" />
													</form:option>
													<c:forEach items="${command.getLevelData(baseLookupCode)}"
														var="lookUp">
														<form:option value="${lookUp.lookUpId}"
															code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
												</form:select></td>


											<td><form:select
													path="entity.details[${status.count-1}].sacHeadId"
													class="form-control applyChoosen chosen-select-no-results"
													id="secondary${status.count-1}"
													onchange="changeList(${status.count-1})">
													<form:option value="0">
														<spring:message
															code="account.budget.code.master.selectaccountheads"
															text="Select Account Head" />
													</form:option>
													<c:forEach
														items="${command.entity.details[status.count-1].accountHeadCodeList}"
														var="lookUp">
														<form:option value="${lookUp.key}">${lookUp.value}</form:option>
													</c:forEach>
												</form:select></td>

											<td><form:input
													path="entity.details[${status.count-1}].voudetAmt"
													cssClass="form-control hasAmount text-right"
													id="amount${status.count-1}"
													onchange="validateAmount(${status.count-1})"></form:input></td>

											<td class="deletedRow"><form:input
													path="entity.details[${status.count-1}].deleted"
													id="isDeleted${status.count-1}" /></td>
											<c:if test="${command.isAuthApplication ne 'Y'}">
												<td><a data-placement="top" title="Add"
													class="btn btn-success btn-sm addDedButton"
													id="addDetails${status.count-1}"
													onclick="addRow(${status.count-1},'${command.mode}')"><i
														class="fa fa-plus-circle"></i></a> <a data-placement="top"
													title="Delete" class="btn btn-danger btn-sm delDedButton"
													id="deleteDetails${status.count-1}"
													onclick="removeRow(${status.count-1})"><i
														class="fa fa-trash-o"></i></a></td>
											</c:if>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message
					code="accounts.receipt.narration" text="Narration" /></label>
			<div class="col-sm-10">
				<form:textarea class="form-control" id="narration"
					path="entity.narration" maxlength="500"></form:textarea>
			</div>
		</div>
		<c:if test="${command.mode eq 'AUTH'}">
			<div class="form-group padding-top-10" id="AuthDiv">
				<label class="col-sm-2 control-label"> <spring:message
						code="" text="Is Authorised" /></label>
				<div class="col-sm-4 radio">
					<label> <form:radiobutton path="entity.authoFlg" value="Y"
							checked="checked" /> <spring:message code="" text="Yes" />
					</label> <label> <form:radiobutton path="entity.authoFlg" value="N" />
						<spring:message code="" text="No" />
					</label>
				</div>
			</div>
		</c:if>
		<c:if test="${command.isAuthApplication eq 'Y'}">
			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
						code="" text="Authorised By:" /></label>
				<div class="col-sm-4">
					<form:input path="entity.authorityName" cssClass="form-control"
						readonly="true"></form:input>
				</div>
				<label class="col-sm-2 control-label required-control"
					for="voucherDate"><spring:message code=""
						text="Voucher Date" /></label>
				<div class="col-sm-4">
					<div class="input-group">
						<form:input type="text" class="form-control datepicker dateClass"
							path="entity.authoDate" id="authDate" />
						<label class="input-group-addon" for="authDate"><i
							class="fa fa-calendar"></i><span class="hide"><spring:message
									code="" text="Date" /></span></label>
					</div>
				</div>
			</div>
		</c:if>
		<div class="text-center hidden-print padding-10">
			<button onclick="printdiv('receipt');"
				class="btn btn-primary hidden-print">
				<i class="fa fa-print"></i>
				<spring:message code="account.budget.code.print" text="Print" />
			</button>
			
			<input type="button" class="btn btn-danger"
				onclick="javascript:openRelatedForm('AccountVoucherEntry.html');"
				value="<spring:message code="water.btn.cancel"/>" id="cancelEdit" />
		</div>
	</form:form>
</div>
