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
	var depSubTypeFlags = $.trim($("#depSubTypeFlag").val());
	
	if(depSubTypeFlags == 'Y'){
	
	$('.accountClass').each(function(i){
	
	$('#type'+i).prop('disabled', true).trigger("chosen:updated");
	$('#secondary'+i).prop('disabled', true).trigger("chosen:updated");	
	$('#amount'+i).prop('disabled', true).trigger("chosen:updated");
	$('#addDetails'+i).prop('disabled', true).trigger("chosen:updated");
	$('#deleteDetails'+i).prop('disabled', true).trigger("chosen:updated");
	});



	
	$('.creditClass').each(function(i){
	$('#amount'+i).prop('disabled', true).trigger("chosen:updated");
	});
	
	$('.debitClass').each(function(i){
	
		$('#amount'+i).prop('disabled', true).trigger("chosen:updated");
		});
	
	$('.addDedButton').each(function(i){
		$('#addDetails'+i).prop('disabled', true).trigger("chosen:updated");
		});
	$('.delDedButton').each(function(i){
		$('#deleteDetails'+i).prop('disabled', true).trigger("chosen:updated");
		});
	
}

	// finding SLI Date from SLI Prefix
	var depositFlag=$.trim($("#depositFlag").val());	
	if(depositFlag == 'Y'){
		
		var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
		var disableBeforeDate = new Date(response[0], response[1], response[2]);
		var sliYear = response[0];
		var date = new Date();
		var data = $('#transactionDateId').val();	
		var curArr;
		var year;
	if(data == null || data == undefined || data == ""){
		curArr = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
	}else{
		var arr = data.split('/');
			var day=arr[0];
			var month=arr[1];
		        year=arr[2];
			curArr = month+"/"+day+"/"+year;
	}
		if(sliYear==year)
			{
		var date = new Date();
		var today = new Date(curArr);
		var todays = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
		$("#transactionDateId").datepicker({
		    dateFormat: 'dd/mm/yy',
			changeMonth: true,
			changeYear: true,
			minDate : today,
			maxDate : todays
		});
		}
		else
			{
		var today = new Date(curArr);
		var date = new Date();
		var todays = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
		$("#transactionDateId").datepicker({
		    dateFormat: 'dd/mm/yy',
			changeMonth: true,
			changeYear: true,
			minDate: today,
			maxDate: todays
		}); 
			}
		$("#transactionDateId").val("");
		}
	
		if(depositFlag != 'Y'){
				var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
				var disableBeforeDate = new Date(response[0], response[1], response[2]);
				var date = new Date();
				var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
				$("#transactionDateId").datepicker({
					dateFormat: 'dd/mm/yy',		
					changeMonth: true,
					changeYear: true,
					minDate : disableBeforeDate,
					maxDate : today,
					onSelect: function(date) {
						validateAuthorizationDate(date);
					} 
				});
			}
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
$('#transactionDateId').removeClass('mandColorClass');
}
}); 
</script>

<div class="widget" id="widget">
	<div class="widget-content padding">
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
			<form:hidden path="entity.depId" id="depId" />
			<form:hidden path="entity.balAmount" id="balAmount" />
			<form:hidden path="entity.depBalAmount" id="depBalAmount" />
			<form:hidden path="entity.depositFlag" id="depositFlag" />
			<form:hidden path="entity.authoFlg" id="authFlag" />
			<form:hidden path="entity.depSacHeadId" id="depSacHeadId" />
			<form:hidden path="entity.depSubTypeFlag" id="depSubTypeFlag" />
			<form:hidden path="" id="accountHead" value="${command.accountHead}" />
			<form:hidden path="" id="voucherDates"
				value="${command.voucherDates}" />
			<form:hidden path="" id="fromDate" value="${command.fromDate}" />
			<form:hidden path="" id="toDate" value="${command.toDate}" />
			<form:hidden path="" id="faYearId" value="${command.faYearId}" />
			<form:hidden path="" id="openDr" value="${command.openDr}" />
			<form:hidden path="" id="openCr" value="${command.openCr}" />

			<div class="form-group" id="voucherEntry">
				<label class="col-sm-2 control-label"><spring:message
						code="" text="Voucher No." /></label>
				<div class="col-sm-4">
					<form:input path="entity.vouNo" cssClass="form-control"
						id="voucherNo" readonly="true"></form:input>
				</div>

			</div>
			<div class="form-group">
				<label for="transactionDateId"
					class="col-sm-2 control-label required-control"><spring:message
						code="budget.reappropriation.master.transactiondate"
						text="Transaction Date" /></label>
				<div class="col-sm-4">

					<div class="input-group">
						<c:if test="${command.serviceFlag ne 'Y'}">
							<c:if test="${command.mode eq 'A'}">
								<c:set var="now" value="<%=new java.util.Date()%>" />
								<fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="date" />
								<form:input path="entity.transactionDate" id="transactionDateId"
									cssClass="mandColorClass form-control"
									disabled="${command.mode eq 'AUTH' ? true : false}"
									value="${date}"></form:input>
								<label class="input-group-addon mandColorClass"
									for="transactionDateId"><i class="fa fa-calendar"></i>
								</label>
							</c:if>
						</c:if>

						<c:if test="${command.serviceFlag eq 'Y'}">
							<c:if test="${command.mode eq 'A'}">
								<form:input path="entity.transactionDate" id="transactionDateId"
									cssClass="mandColorClass form-control"
									disabled="${command.mode eq 'AUTH' ? true : false}"></form:input>
								<label class="input-group-addon mandColorClass"
									for="transactionDateId"><i class="fa fa-calendar"></i>
								</label>
							</c:if>
						</c:if>

						<c:if test="${command.mode ne 'A'}">
							<form:input path="entity.transactionDate" id="transactionDateId"
								cssClass="mandColorClass form-control"
								disabled="${command.mode eq 'AUTH' ? true : false}" />
							<label class="input-group-addon mandColorClass"
								for="transactionDateId"><i class="fa fa-calendar"></i> </label>
						</c:if>
					</div>

				</div>
				<label class="col-sm-2 control-label "><spring:message
						code="" text="Transaction Ref No." /></label>
				<div class="col-sm-4">
					<form:input type="text" path="entity.vouReferenceNo" id="refNo"
						disabled="${command.mode eq 'AUTH' ? true : false}"
						class="form-control" />
				</div>
			</div>
			<div class="form-group">

				<c:if test="${command.serviceFlag ne 'Y'}">
					<c:if test="${command.mode eq 'A'}">
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
									<c:if test="${lookUp.otherField eq 'Y'}">
										<c:choose>
											<c:when test="${command.entity.vouTypeCpdId ne null }">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}"
													selected="${command.entity.vouTypeCpdId}">${lookUp.lookUpDesc}</form:option>
											</c:when>
											<c:otherwise>
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:otherwise>
										</c:choose>

									</c:if>
								</c:forEach>
							</form:select>
						</div>
					</c:if>
				</c:if>

				<c:if test="${command.serviceFlag eq 'Y'}">
					<c:if test="${command.mode eq 'A'}">
						<label class="col-sm-2 control-label required-control"
							for="voucherType"><spring:message code=""
								text="Voucher Type" /></label>
						<div class="col-sm-4">
							<c:set var="baseLookupCode" value="VOT" />
							<form:hidden path="entity.vouTypeCpdId" />
							<form:select path="entity.vouTypeCpdId" class="form-control"
								id="voucherType" disabled="true">
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
					</c:if>
				</c:if>

				<c:if test="${command.mode ne 'A'}">
					<label class="col-sm-2 control-label required-control"
						for="voucherType"><spring:message code=""
							text="Voucher Type" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="VOT" />
						<form:hidden path="entity.vouTypeCpdId" />
						<form:select path="entity.vouTypeCpdId" class="form-control"
							id="voucherType" disabled="true">
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
				</c:if>



				<c:if test="${command.mode eq 'A'}">

					<label class="col-sm-2 control-label required-control"
						for="voucherSubType"><spring:message code=""
							text="Voucher Sub Type" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="TDP" />
						<form:select path="entity.voucherSubType" class="form-control"
							id="voucherSubType">
							<form:option value="0">
								<spring:message code="" text="Select Voucher Sub Type" />
							</form:option>

							<c:forEach items="${command.voucherSubTypeList}" var="lookUp">
								<c:if test="${lookUp.otherField eq 'JV'}">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</div>
				</c:if>

				<c:if test="${command.mode ne 'A'}">
					<label class="col-sm-2 control-label required-control"
						for="voucherSubType"><spring:message code=""
							text="Voucher Sub Type" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="TDP" />
						<form:hidden path="entity.voucherSubType" />
						<form:select path="entity.voucherSubType" class="form-control"
							id="voucherSubType" disabled="true">
							<form:option value="0">
								<spring:message code="" text="Select Voucher Sub Type" />
							</form:option>
							<c:forEach items="${command.voucherSubTypeList}" var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</c:if>

			</div>

			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse"
								data-parent="#accordion_single_collapse" href="#details"><spring:message
									code="" text="Details" /></a>
						</h4>
					</div>
					<div id="details" class="panel-collapse collapse in">
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
												<th><spring:message code="account.common.add.remove"
														text="Add/Remove" /></th>
											</tr>

											<tr id="tr0" class="accountClass">

												<form:hidden path="entity.details[0].voudetId" id="voutId0" />

												<td><c:set var="baseLookupCode" value="DCR" /> <form:select
														path="entity.details[0].drcrCpdId" class="form-control"
														id="type0" onchange="setStatusClass(this,0)">
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
													class="btn btn-success btn-sm addDedButton"
													id="addDetails0" onclick="addRow(0)" tabindex="7"><i
														class="fa fa-plus-circle"></i></a> <a data-placement="top"
													title="Delete" class="btn btn-danger btn-sm delDedButton"
													id="deleteDetails0" onclick="removeRow(0)"><i
														class="fa fa-trash-o"></i></a></td>
											</tr>
											<tr id="tr1" class="accountClass">

												<form:hidden path="entity.details[1].voudetId" id="voutId1" />
												<td><c:set var="baseLookupCode" value="DCR" /> <form:select
														path="entity.details[1].drcrCpdId" class="form-control"
														id="type1" onchange="setStatusClass(this,1)">
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
													class="btn btn-success btn-sm addDedButton"
													id="addDetails1" onclick="addRow(1)"><i
														class="fa fa-plus-circle"></i></a> <a data-placement="top"
													title="Delete" class="btn btn-danger btn-sm delDedButton"
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
													<th><spring:message code="account.common.add.remove"
															text="Add/Remove" /></th>
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
															onchange="setStatusClass(this,${status.count-1})">
															<form:option value="0">
																<spring:message code="bank.master.selstatus" />
															</form:option>
															<c:forEach
																items="${command.getLevelData(baseLookupCode)}"
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
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label required-control"><spring:message
						code="account.deposit.narration" text="Narration" /></label>
				<div class="col-sm-10">
					<form:textarea class="form-control" id="narration"
						path="entity.narration" maxlength="500"
						disabled="${command.mode eq 'AUTH' ? true : false}"></form:textarea>
				</div>
			</div>

			<c:if test="${command.mode eq 'AUTH'}">
				<div class="form-group padding-top-10" id="AuthDiv">



					<label class="col-sm-2 control-label required-control"><spring:message
							code="account.journal.voucher.remark"
							text="Approval / Unapproval Remark" /></label>
					<div class="col-sm-10">
						<form:textarea class="form-control" id="authRemark"
							path="entity.authRemark" maxlength="500"></form:textarea>
					</div>

				</div>

				<c:if test="${command.isAuthApplication eq 'Y'}">
					<div class="form-group padding-top-10" id="AuthDiv">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="account.journal.voucher.remark"
								text="Approval / Unapproval Remark" /></label>
						<div class="col-sm-10">
							<form:textarea class="form-control" id="authRemark"
								path="entity.authRemark" maxlength="500" disabled="true"></form:textarea>
						</div>
					</div>
				</c:if>

				<c:choose>
					<c:when test="${command.modeView ne 'V'}">
						<div class="form-group">
							<label for="transactionAuthDateId"
								class="col-sm-2 control-label required-control"><spring:message
									code="account.journal.voucher.auth.date"
									text="Authorization Date " /> </label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="entity.transactionAuthDate"
										id="transactionAuthDateId"
										cssClass="mandColorClass form-control" />
									<label class="input-group-addon mandColorClass"
										for="transactionAuthDateId"><i class="fa fa-calendar"></i>
									</label>
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<c:if test="${command.isAuthApplication eq 'Y'}">
							<div class="form-group">
								<label for="transactionAuthDateId"
									class="col-sm-2 control-label required-control"><spring:message
										code="account.journal.voucher.auth.date"
										text="Authorization Date " /> </label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input path="entity.transactionAuthDate"
											id="transactionAuthDateId"
											cssClass="mandColorClass form-control" />
										<label class="input-group-addon mandColorClass"
											for="transactionAuthDateId"><i class="fa fa-calendar"></i>
										</label>
									</div>
								</div>
							</div>
						</c:if>
					</c:otherwise>
				</c:choose>

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
							text="Posting Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input type="text" class="form-control datepicker dateClass"
								path="entity.authoDateDup" id="authDate" />
							<label class="input-group-addon" for="authDate"><i
								class="fa fa-calendar"></i><span class="hide"><spring:message
										code="" text="Date" /></span></label>
						</div>
					</div>
				</div>
			</c:if>

			<div class="text-center clear padding-10">
				<button type="button" class="btn btn-success btn-submit"
					onclick="window.location.href='TransactionTracking.html'">Home</button>
				<button type="button" class="btn btn-danger"
					onclick="findVoucherWiseBack()">
					<spring:message code="account.bankmaster.back" text="Back" />
				</button>
			</div>



		</form:form>
	</div>
</div>
