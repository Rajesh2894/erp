<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/mainet/validation.js"></script>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<script>
$(document).ready(function() {
	chosen();
	
	$("#fieldDeptDiv").hide();
	$("#advSearchData").show();
	$("#fromTodateDiv").hide();
	$("#depositedateDiv").hide();
	$("#depositedateDiv").hide();	
	$("#DenominationDetails").hide();	
	$("#processBtn").hide();	
	$("#showSaveBtn").hide();
	
	$("#afterSearchDiv").hide();
	 $(".Moredatepicker").datepicker({
		    dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true,	
			minDate: '0',
		});
	 
	 $(".datepicker").datepicker({
		    dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true,	
			maxDate: '0',
		});
	 
	if('${isViewMode}'=='true')
		{
		var dateFields = $('#depositDate');
		dateFields.each(function () {
			var fieldValue = $(this).val();
			if (fieldValue.length > 10) {
				$(this).val(fieldValue.substr(0, 10));
			}
		});
		$("#afterSearchDiv").show();
		$("#depositDate").removeClass("Moredatepicker hasDatepicker");
		
		$('label.control-label').each(function(){
			$(this).removeClass('required-control');
			
		});

		
		$("#deptId").removeClass("mandColorClass");
		$('#depositDate').attr('disabled', true);
		
		$('#frmMaster input[type="text"]').removeClass("required-control");
		$('#frmMaster select').removeClass("required-control");
		if('${chequeOrCash}'=='C')
		{
		  $("#receiptDetailId2").hide();
		    $("#ledgerDetailId2").show();
		    $("#Denomination").show();
		    $("#getReceiptDetails").hide();
		    $("#chequeOrDDDetails1").hide(); 
		      $("#cashMode").show();
		      $('.denomClass').each(function(i) {
			    	 
		    	  var descCash = $('#denomDesc'+i).val();
				  	var denom = 0 ;
				  	if( $('#denomination'+i).val() != '')
				  		{
				  		 denom = $('#denomination'+i).val();
				  		}
				  	
				  	var amount  = parseFloat(descCash) * parseFloat(denom);
				  	$('#amount'+i).val(amount);
				  	
				  	var total=0;
				  	var coins = $('#coins2').val();
				  	var rowCount = $('.tabledenomination tr').length;

				  	for (var i = 0; i < rowCount -2; i++) {
				  	  
				  		total += parseFloat($("#amount" + i).val());
				  		$("#total").val(total);
				  		$("#ledgerTotalAmount").val(total);
				  	
				  	}
		    	  
		      }); 
		     
	  		
		 
		    
		   }
		else
			{
			 $("#Denomination").hide();
			    $("#chequeOrDDDetails1").show(); 
			    $("#cashMode").hide();
			}
		    
		}
	else
		{
    $("#receiptDetailId2").hide();
    $("#ledgerDetailId2").hide();
    $("#Denomination").hide();
    $("#getReceiptDetails").hide();
    $("#chequeOrDDDetails1").hide();
    
    $("#cashMode").hide();
		}
    $('#account').change(function(){
       var fund=$('#account').find("option:selected").attr('code');
    	$("#fundid").val(fund);
    	$("#hiddenfundId").val(fund);
    });

  
    $('select[name=depositeType]').change(function(){
    	
    
    		  $("#errorDiv").hide();	
    			$("#StoDate").val($("#toDate").val());
    	    	$("#SfromDate").val($("#fromDate").val());
    	    
   
    
    	
     });
    
    $("#fundCode").blur(function(){
        
       	$("#StoDate").val($("#toDate").val());
        });
 $("#fieldCode2").blur(function(){
        
       	$("#StoDate").val($("#toDate").val());
    	$("#SfromDate").val($("#fromDate").val());
        });

    
   $("#toDate").blur(function(){
	   
   	$("#SfromDate").val($("#fromDate").val());
    });
 
    $('input[name=cashdepositeType]').change(function(){
       if( $('input[name=cashdepositeType]:checked').val() =='R')
    	   {
    	    $("#receiptDetailId2").show();
    	    $("#Denomination").show();
    	    $("#getReceiptDetails").show(); 
    	    $("#ledgerDetailId2").hide();
    	    $("#cashMode").show();sd
    	    getReceiptDetails(this,'${isViewMode}');
    	   }
       
       if( $('input[name=cashdepositeType]:checked').val() =='L')
	   {    $("#receiptDetailId2").hide()
    	    $("#ledgerDetailId2").show();
    	    $("#Denomination").show();
    	    $("#getReceiptDetails").show();
    	    getLedgerDetails(this,'${isViewMode}');
	   }
    });
    
    
    $('input[name=depositeAmount]').keyup(function(){
    	if( $('input[name=depositeAmount]').val()!="")
    	{
    		
    		$("#SfromDate").val($("#fromDate").val());
    		$("#StoDate").val($("#toDate").val())
    		$('#SfeeMode').attr('disabled', false).val("");
    		$("#hiddenfundId").val($("#fundid").val())
    		$("#hiddenfieldId").val($("#fieldCode2").val())
    		
    		$("#SfeeMode").val($("#deptId option:selected").val());
    		$('#deptId').attr('disabled', true);
    		$('#fromDate').attr('disabled', true).removeAttr("datepicker hasDatepicker");
    		$('#deptId2').attr('disabled', true);
    		$('#toDate').attr('disabled', true);
    		$('#fieldCode2').attr('disabled', true);
    		$('#fundCode').attr('disabled', true);
    	}
    	
    });
    
    
   
    
    $('select[name=bankId]').change(function(){
    	if( $('input[name=bankId]').val()!="")
    	{
    		$("#SfromDate").val($("#fromDate").val());
    		$("#StoDate").val($("#toDate").val());
    		$('#SfeeMode').attr('disabled', false).val("");
    		$("#SfeeMode").val($("#deptId option:selected").val());
    		$("#hiddenfundId").val($("#fundid").val())
    		$("#hiddenfieldId").val($("#fieldCode2").val())
    		$('#deptId').attr('disabled', true);
    		$('#fromDate').attr('disabled', true).removeAttr("datepicker hasDatepicker");
    		$('#deptId2').attr('disabled', true);
    		$('#toDate').attr('disabled', true);
    		$('#fieldCode2').attr('disabled', true);
    		$('#fundCode').attr('disabled', true);
    	}
    	
    });
    
	function closeErrBox() {
		$('.error-div').hide();
	}
	
    // add multiple select / deselect functionality
    $("#selectallLD").click(function () {
          $('.case1').attr('checked', this.checked);
    });
    
$(".case1").click(function(){
        
    	var alertms=getLocalMessage('tp.app.alMessg');
    	if($(".case1").length == 0)	
    	{	
    		
    	}
    	else
    		{
    		
    		if($(".case1").length == $(".case1:checked").length) {
                $("#selectallLD").attr("checked", "checked");
            } else {
                $("#selectallLD").removeAttr("checked");
            }
    		}
    	});

function closeErrBox() {
	$('.error-div').hide();
}
 
// add multiple select / deselect functionality
$("#selectall").click(function () {
      $('.case').attr('checked', this.checked);
});

$(".case").click(function(){
    
	var alertms=getLocalMessage('tp.app.alMessg');
	if($(".case").length == 0)	
	{	
		alert(alertms);
	}
	else
		{
		
		if($(".case").length == $(".case:checked").length) {
            $("#selectall").attr("checked", "checked");
        } else {
            $("#selectall").removeAttr("checked");
        }
		}
	});
});
</script>
<div class="widget max-width-800 min-width-400 margin-bottom-0 ">
	<div class="widget-header">
		<h2>
			<spring:message code="account.cheque.dishonour.depositslip"
				text="Deposit Slip" />
		</h2>
	</div>

	<div class="widget-content padding">


		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="accountChequeOrCashDepositeBean" name="frmMaster"
			method="POST" action="AccountDepositSlip.html">
			<form:errors path="amount"
				cssClass="alert alert-danger  form-control fa fa-exclamation-circle"
				element="div" onclick="">

			</form:errors>
			<div class="warning-div alert alert-danger alert-dismissible hide "
				id="errorDiv">
				<button type="button" class="close" aria-label="Close"
					onclick="closeErrBox()">
					<span aria-hidden="true">&times;</span>
				</button>

				<ul>
					<li><i class='fa fa-exclamation-circle'></i>&nbsp;</li>
				</ul>
				<script>
					$(".warning-div ul").each(function () {
					    var lines = $(this).html().split("<br>");
					    $(this).html('<li>' + lines.join("</li><li><i class='fa fa-exclamation-circle'></i>&nbsp;") + '</li>');						
					});
		  			$('html,body').animate({ scrollTop: 0 }, 'slow');
		  		</script>
			</div>

			<spring:hasBindErrors name="accountFunctionMasterBean">
				<div class="warning-div alert alert-danger alert-dismissible"
					id="errorDivScrutiny">
					<button type="button" class="close" aria-label="Close"
						onclick="closeErrBox()">
						<span aria-hidden="true">&times;</span>
					</button>
					<ul>
						<li><i class='fa fa-exclamation-circle'></i>&nbsp;<form:errors
								path="*" /></li>
					</ul>
				</div>
			</spring:hasBindErrors>

			<form:hidden path="SfeeMode" id="SfeeMode" />
			<form:hidden path="hiddenfundId" id="hiddenfundId" />
			<form:hidden path="hiddenfieldId" id="hiddenfieldId" />
			<form:hidden path="hasError" id="errorStatus" />
			<form:hidden path="templateExist" id="templateExist" />
			<form:hidden path="depositeSlipNo" id="slipNumber" />

			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">

				<c:if test="${isViewMode}">
					<div class="form-group">

						<label class="control-label col-sm-2"><spring:message
								code="deposite.slip.number" text="Slip Number" /> </label>
						<div class="col-sm-4">

							<form:input path="depositeSlipNo" cssClass="form-control"
								id="slipNumber" readonly="true" />

						</div>

						<label class="control-label col-sm-2 required-control"><spring:message
								code="deposite.slip.date" text="Deposit Slip Date" /> </label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="depositeSlipDate"
									cssClass="form-control Moredatepicker" id="depositDate"
									readonly="${isViewMode}" />
								<label for="depositDate" class="input-group-addon"><i
									class="fa fa-calendar"></i></label>
							</div>
						</div>

					</div>
				</c:if>





				<c:if test="${!isViewMode}">
					<div id="fromTodateDiv">
						<div class="form-group">
							<label class="control-label col-sm-2 "><spring:message
									code="" text="From Date" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="SfromDate" cssClass="datepicker form-control"
										id="fromDate" />
									<label for="fromDate" class="input-group-addon"><i
										class="fa fa-calendar"></i></label>
								</div>
							</div>


							<label class="control-label col-sm-2 "><spring:message
									code="" text="To Date" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="StoDate" id="toDate"
										cssClass="datepicker form-control" />
									<label for="toDate" class="input-group-addon"><i
										class="fa fa-calendar"></i></label>
								</div>
							</div>

						</div>
					</div>
				</c:if>


				<c:if test="${!isViewMode}">
					<div id="fieldDeptDiv">
						<div class="form-group">

							<c:if test="${fieldStatus == 'Y'}">
								<label class="control-label col-sm-2 "><spring:message
										code="" text="Field" /> </label>
								<div class="col-sm-4">
									<form:select path="fieldId"
										class="form-control chosen-select-no-results" id="fieldCode2"
										disabled="${isViewMode}">
										<form:option value="">
											<spring:message code="account.select" text="Select" />
										</form:option>
										<form:option value="-1">
											<spring:message code="account.cheque.cash.all" text="All" />
										</form:option>
										<c:forEach items="${fieldMasterLastLvls}"
											var="fieldMasterData">
											<form:option code="${fieldMasterData.value}"
												value="${fieldMasterData.key}">${fieldMasterData.value}</form:option>
										</c:forEach>
									</form:select>
									<form:hidden path="fieldId" id="saveField" />
								</div>
							</c:if>

							<c:if test="${deptStatus == 'Y'}">
								<label class="col-sm-2 control-label "><spring:message
										code="account.tenderentrydetails.Department" text="Department" /></label>
								<div class="col-sm-4">
									<form:select path="department"
										class="form-control chosen-select-no-results" id="deptId2"
										disabled="${isViewMode}">
										<option value=""><spring:message
												code="account.select" text="Select" /></option>
										<form:option value="-1">
											<spring:message code="account.cheque.cash.all" text="All" />
										</form:option>
										<c:forEach items="${deptList}" var="deptData">
											<form:option value="${deptData.lookUpId}"
												code="${deptData.lookUpCode}">${deptData.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>
								<form:hidden path="department" id="saveDept" />
							</c:if>

						</div>
					</div>
				</c:if>










				<!-- start point of input field for save data -->








				<div class="form-group">
					<label for="radio-group-1477553805187"
						class="col-sm-2 control-label required-control"><spring:message
							code="account.cheque.dishonour.deposit.mode" text="Deposit Mode" />
					</label>

					<div class="col-sm-4">
						<c:if test="${!isViewMode}">
							<form:select class="form-control" id="deptId" path="depositeType">
								<option value=""><spring:message
										code="budget.reappropriation.master.select" text="Select" /></option>
								<c:forEach items="${permanentPayList}" var="deptData">
									<form:option value="${deptData.lookUpCode}"
										code="${deptData.lookUpId}">${deptData.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</c:if>
						<c:if test="${isViewMode}">
							<form:input class="form-control" id="deptId" path="depositeType"
								disabled="true" />
						</c:if>

					</div>
					<label class="col-sm-2 control-label required-chosen"><spring:message
							code="accounts.Secondaryhead.BankAccounts" text="Bank Accounts" />
					</label>
					<form:hidden path="fundId" id="fundid" />
					<div class="col-sm-4">
						<form:select id="account" path="baAccountid"
							cssClass="form-control chosen-select-no-results required-chosen"
							disabled="${isViewMode}">
							<form:option value="">
								<spring:message code="account.cheque.cash.acc.select"
									text="Select Account" />
							</form:option>
							<c:forEach items="${banklist}" var="accountData">
								<form:option value="${accountData.baAccountId}"
									code="${accountData.fundId}">${accountData.baAccountNo}--${accountData.baAccountName}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<form:hidden path="fundId" id="saveFund" />
					<form:hidden path="baAccountid" id="saveAccount" />
				</div>






				<div class="text-center" id="resetDiv">
					<c:if test="${!isViewMode}">
						<a href="javascript:void(0);" id="advSearchData"
							onclick="viewAdvSearchAllData(this)" class="btn btn-blue-2"
							id="searchDeposit1"><i class="fa fa-search"></i>&nbsp;<spring:message
								code="" text="Advanced Search" /></a>
						<a href="javascript:void(0);"
							onclick="searchDepositDetails(this,false)"
							class="btn btn-success" id="searchDeposit"><i
							class="fa fa-search"></i>&nbsp;<spring:message code=""
								text="Search" /></a>
						<button type="reset" class="btn btn-warning Clear" id="Clear"
							onclick="showForm(this)">
							<spring:message code="account.btn.reset" text="Reset" />
						</button>
						<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountDepositSlip.html'"
							value="Back" id="cancelEdit" />
						<div></div>
					</c:if>

				</div>






				<div class="panel panel-default" id="chequeOrDDDetails1">
					<div id="chequeOrDDDetails2" class="panel-collapse in">
						<div class="panel-body">
							<div class="table-responsive">
								<table
									class="table table-bordered table-condensed reduce-padding">
									<tr>
										<th scope="col"><spring:message
												code="accounts.receipt.receipt_no" text="Receipt Number" /></th>
										<th scope="col"><spring:message
												code="accounts.receipt.receipt_date" text="Receipt Date" /></th>
										<th scope="col"><spring:message
												code="account.tenderentrydetails.Department"
												text="Department" /></th>
										<th scope="col"><spring:message
												code="account.cheque.dishonour.instrument.type"
												text="Instrument Type" /></th>
										<th scope="col"><spring:message
												code="accounts.receipt.cheque_dd_no_pay_order"
												text="Instrument No." /></th>
										<th scope="col"><spring:message
												code="account.cheque.dishonour.instrument.date"
												text="InstrumentDate" /></th>
										<th scope="col"><spring:message
												code="account.cheque.dishonour.drawn.bank"
												text="Drawn On Bank" /></th>
										<th scope="col"><spring:message
												code="budget.allocation.master.amount" text="Amount" /></th>

									</tr>
									<c:forEach items="${listOfReceiptDetails}" var="denLookupVar"
										varStatus="status">
										<c:set value="${status.index}" var="count"></c:set>
										<tr>
											<form:hidden
												path="listOfChequeDDPoDetails[${count}].rmRcptid" />
											<form:hidden path="listOfChequeDDPoDetails[${count}].modeId" />
											<td><form:input type="text" id="coins"
													cssClass="form-control text-right"
													path="listOfChequeDDPoDetails[${count}].rmRcptno"
													readonly="true"></form:input></td>
											<td><form:input type="text" id="coins"
													cssClass="form-control text-right"
													path="listOfChequeDDPoDetails[${count}].rmDate"
													readonly="true"></form:input></td>
											<td><form:input type="text" id="coins"
													cssClass="form-control"
													path="listOfChequeDDPoDetails[${count}].deptName"
													readonly="true"></form:input></td>
											<td><form:input type="text" id="coins"
													cssClass="form-control"
													path="listOfChequeDDPoDetails[${count}].instrumentType"
													readonly="true"></form:input></td>
											<td><form:input type="text" id="coins"
													cssClass="form-control"
													path="listOfChequeDDPoDetails[${count}].payOrderNo"
													readonly="true"></form:input></td>
											<td><form:input type="text" id="coins"
													cssClass="form-control"
													path="listOfChequeDDPoDetails[${count}].payOrderDt"
													readonly="true"></form:input></td>
											<td><form:input type="text" id="coins"
													cssClass="form-control"
													path="listOfChequeDDPoDetails[${count}].drawnOnBank"
													readonly="true"></form:input></td>
											<td><form:input type="text" id="coins"
													cssClass="form-control text-right"
													path="listOfChequeDDPoDetails[${count}].rmAmount"
													readonly="true"></form:input></td>

										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
					<c:if test="${!isViewMode}">
						<div class="form-group">
							<label class="control-label col-sm-2 "><spring:message
									code="account.master.remittance.date" text="Remittance Date" /> </label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="depositDateCheque"
										cssClass="form-control Moredatepicker" id="depositDateCheque"
										readonly="${isViewMode}" />
									<label for="depositDateCheque" class="input-group-addon"><i
										class="fa fa-calendar"></i></label>
								</div>
							</div>
						</div>
					</c:if>
				</div>


				<c:if test="${receiptDetailDiv}">
					<div class="panel panel-default" id="receiptDetailId2">
						<c:if test="${!isViewMode}">
							<div class="text-center  padding-bottom-10">
								<INPUT type="button" class="btn btn-success"
									value="Refresh  Receipt Details"
									onclick="getReceiptDetails(this)"
									title=" Fund Code and Field  is  Requierd For  Receipt Details " />
							</div>
						</c:if>
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse"
									href="#receiptDetailId"><spring:message
										code="account.cheque.dishonour.deposite.receipt"
										text="Deposit - Receipt Details" /></a>
							</h4>
						</div>
						<div id="receiptDetailId" class="panel-collapse ">
							<div class="panel-body">
								<div class="table-responsive">

									<table class="table table-bordered table-condensed">
										<tr>
											<th scope="col" width="10%"><spring:message
													code="account.cheque.dishonour.sr.no" text="Sr No." /></th>
											<th scope="col" width="25%"><spring:message
													code="accounts.receipt.receipt_no" text="Receipt Number" /></th>
											<th scope="col" width="20%"><spring:message
													code="accounts.receipt.receipt_date" text="Receipt Date" /></th>
											<th scope="col" width="20%"><spring:message
													code="account.tenderentrydetails.Department"
													text="Department" /></th>
											<th scope="col" width="20%"><spring:message
													code="budget.allocation.master.amount" text="Amount" /></th>
										</tr>
										<c:forEach items="${listOfReceiptDetails}" var="denLookupVar"
											varStatus="status">
											<c:set value="${status.index}" var="count"></c:set>
											<tr class="receiptTr">
												<td><form:input cssClass="hasNumber form-control"
														value="${count+1}" path="" disabled="true"></form:input></td>
												<form:hidden path="listOfReceiptDetails[${count}].rmRcptid" />
												<td><form:input type="text" id="coins"
														cssClass="form-control"
														path="listOfReceiptDetails[${count}].rmRcptno"
														readonly="true"></form:input></td>
												<td><form:input type="text" id="coins"
														cssClass="form-control"
														path="listOfReceiptDetails[${count}].rmDate"
														readonly="true"></form:input></td>
												<td><form:input type="text" id="coins"
														cssClass="form-control"
														path="listOfReceiptDetails[${count}].dpDeptId"
														readonly="true"></form:input></td>
												<td><form:input type="text" id="receiptAmount${count}"
														cssClass="form-control hasAmount"
														path="listOfReceiptDetails[${count}].rmAmount"
														readonly="true"></form:input></td>

											</tr>
										</c:forEach>
										<tr>
											<td colspan="4" align="right"><b><spring:message
														code="account.cheque.dishonour.total" text="Total :" /></b></td>
											<td align="right"><form:input
													path="listOfReceiptDetails[0].totalAmount" readonly="true"
													id="receiptTotalAmount" /></td>

										</tr>
									</table>
								</div>
							</div>
						</div>

					</div>

				</c:if>



				<c:if test="${ledgerDetailDiv}">
					<!-- for Ledger details -->

					<div class="panel panel-default" id="ledgerDetailId2">


						<div class="panel-heading">
							<h4 class="panel-title" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#ledgerDetailId">Deposit
									Details</a>
							</h4>

						</div>
						<div id="ledgerDetailId" class="panel-collapse ">
							<div class="panel-body">
								<div class="table-responsive">
									<table id="ledgerDetailsId"
										class="table table-bordered table-condensed">
										<tbody>
											<tr>
												<th scope="col" width="75%"><spring:message
														code="account.cheque.dishonour.account.head"
														text="Account Head" /></th>
												<th scope="col" width="25%"><spring:message
														code="budget.allocation.master.amount" text="Amount" /></th>



												<c:forEach items="${listOfLedgerDetails}" var="denLookupVar"
													varStatus="status">
													<c:set value="${status.index}" var="count"></c:set>
													<tr class="ledgerTr">
														<td><form:select
																path="listOfLedgerDetails[${count}].secondary"
																id="coins" cssClass="form-control" disabled="true">
																<c:forEach
																	items="${accountChequeOrCashDepositeBean.listOfLedgerDetails[count].secondaryMap}"
																	var="map">
																	<form:option value="${map.key}">${map.value}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input type="text" id="ledgerAmount${count}"
																cssClass="form-control text-right ledgerAmountClass"
																path="listOfLedgerDetails[${count}].feeAmount"
																readonly="true"></form:input></td>
														<form:hidden
															path="listOfLedgerDetails[${count}].receiptIds" />
													</tr>
												</c:forEach>

												<td colspan="1" align="right"><b><spring:message
															code="account.cheque.dishonour.total" text="Total :" /></b></td>
												<td align="right"><form:input
														path="listOfReceiptDetails[0].totalAmount" readonly="true"
														id="ledgerTotalAmount" class="form-control text-right" />
												</td>

											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<!-- Deposite Button-->

						<c:if test="${!isViewMode}">
							<div class="form-group">
								<label class="control-label col-sm-2 "><spring:message
										code="account.master.remittance.date" text="Remittance Date" /> </label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input path="depositDateLedger"
											cssClass="form-control Moredatepicker" id="depositDateLedger"
											readonly="${isViewMode}" />
										<label for="depositDateLedger" class="input-group-addon"><i
											class="fa fa-calendar"></i></label>
									</div>
								</div>
							</div>
						</c:if>
					</div>

				</c:if>

				<c:if test="${!isViewMode}">
					<div class="text-center" id="afterSearchDiv">
						<a href="javascript:void(0);"
							onclick="checkValidationForDepositeDate(this)"
							class="btn btn-success btn-submit" id="processButton">&nbsp;<spring:message
								code="accounts.process" text="Process" /></a> <input type="button"
							class="btn btn-warning Clear" id="Clear"
							onclick="resetDepositForm()" value="<spring:message code="accounts.stop.payment.reset" text="Reset" />"></input> <input
							type="button" class="btn btn-danger"
							onclick="window.location.href='AccountDepositSlip.html'"
							value="<spring:message code="accounts.stop.payment.back" text="Back" />" id="cancelEdit" />
					</div>
				</c:if>

				<c:if test="${!isViewMode}">
					<div class="panel panel-default" id="DenominationDetails">

						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#denomination"><spring:message
										code="account.cheque.dishonour.denomination.detail"
										text="Denomination Details " /> </a>
							</h4>
						</div>
						<div id="denomination" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="table-responsive">
									<table
										class="table table-bordered table-condensed tabledenomination">
										<tr>
											<th scope="col" width="33%"><spring:message
													code="account.cheque.dishonour.cash.detail"
													text="Details of Cash" /></th>
											<th scope="col" width="33%"><spring:message
													code="account.cheque.dishonour.number.count"
													text="Number of Count" /></th>
											<th scope="col" width="34%" class="text-right"><spring:message
													code="budget.allocation.master.amount" text="Amount" /></th>
										</tr>
										<c:if test="${!isViewMode}">
											<c:forEach items="${denLookupList}" var="denLookupVar"
												varStatus="status">
												<c:set value="${status.index}" var="count"></c:set>
												<tr id="denominationTr">
													<td><form:hidden id="thousandId"
															path="cashDep[${count}].tbComparamDet" /> <form:input
															cssClass="hasNumber form-control" id="denomDesc${count}"
															path="cashDep[${count}].denomDesc" disabled="true"></form:input></td>
													<td class="countClass"><form:input
															cssClass="hasNumber form-control"
															id="denomination${count}"
															path="cashDep[${count}].denomination"
															onkeyup="getAmount(${count})"></form:input></td>
													<td class="amountClass"><form:input
															cssClass="hasAmount form-control text-right"
															id="amount${count}" value="0" path="" disabled="true"></form:input></td>
												</tr>
											</c:forEach>
										</c:if>
										<c:if test="${isViewMode}">
											<c:forEach items="${accountChequeOrCashDepositeBean.cashDep}"
												varStatus="status">
												<c:set value="${status.index}" var="count"></c:set>
												<tr class="denomClass">
													<td><form:hidden id="thousandId"
															path="cashDep[${count}].tbComparamDet" /> <form:input
															cssClass="hasNumber form-control" id="denomDesc${count}"
															path="cashDep[${count}].denomDesc" disabled="true"></form:input></td>
													<td><form:input cssClass="hasNumber form-control"
															id="denomination${count}"
															path="cashDep[${count}].denomination"
															onkeyup="getAmount(${count})" disabled="true"></form:input></td>
													<td><form:input
															cssClass="hasAmount form-control text-right"
															id="amount${count}" value="0" path="" disabled="true"></form:input></td>
												</tr>
											</c:forEach>
										</c:if>
										<tr>
											<th><spring:message code="account.voucher.total"
													text="Total" /></th>
											<th><form:input id="total"
													cssClass="hasAmount form-control text-right" path="total"
													readonly="true"></form:input></th>
											<th>&nbsp;</th>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>

				<c:if test="${isViewMode}">
					<div class="panel panel-default" id="Denomination">

						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#denomination"><spring:message
										code="account.cheque.dishonour.denomination.detail"
										text="Denomination Details " /> </a>
							</h4>
						</div>
						<div id="denomination" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="table-responsive">
									<table
										class="table table-bordered table-condensed tabledenomination">
										<tr>
											<th scope="col" width="33%"><spring:message
													code="account.cheque.dishonour.cash.detail"
													text="Details of Cash" /></th>
											<th scope="col" width="33%"><spring:message
													code="account.cheque.dishonour.number.count"
													text="Number of Count" /></th>
											<th scope="col" width="34%" class="text-right"><spring:message
													code="budget.allocation.master.amount" text="Amount" /></th>
										</tr>
										<c:if test="${!isViewMode}">
											<c:forEach items="${denLookupList}" var="denLookupVar"
												varStatus="status">
												<c:set value="${status.index}" var="count"></c:set>
												<tr id="denominationTr">
													<td><form:hidden id="thousandId"
															path="cashDep[${count}].tbComparamDet" /> <form:input
															cssClass="hasNumber form-control" id="denomDesc${count}"
															path="cashDep[${count}].denomDesc" disabled="true"></form:input></td>
													<td class="countClass"><form:input
															cssClass="hasNumber form-control"
															id="denomination${count}"
															path="cashDep[${count}].denomination"
															onkeyup="getAmount(${count})"></form:input></td>
													<td class="amountClass"><form:input
															cssClass="hasAmount form-control text-right"
															id="amount${count}" value="0" path="" disabled="true"></form:input></td>
												</tr>
											</c:forEach>
										</c:if>
										<c:if test="${isViewMode}">
											<c:forEach items="${accountChequeOrCashDepositeBean.cashDep}"
												varStatus="status">
												<c:set value="${status.index}" var="count"></c:set>
												<tr class="denomClass">
													<td><form:hidden id="thousandId"
															path="cashDep[${count}].tbComparamDet" /> <form:input
															cssClass="hasNumber form-control" id="denomDesc${count}"
															path="cashDep[${count}].denomDesc" disabled="true"></form:input></td>
													<td><form:input cssClass="hasNumber form-control"
															id="denomination${count}"
															path="cashDep[${count}].denomination"
															onkeyup="getAmount(${count})" disabled="true"></form:input></td>
													<td><form:input
															cssClass="hasAmount form-control text-right"
															id="amount${count}" value="0" path="" disabled="true"></form:input></td>
												</tr>
											</c:forEach>
										</c:if>
										<tr>

											<td colspan="2" class="text-right"><spring:message
													code="account.cheque.dishonour.total" text="Total :" /></td>
											<td><form:input id="total"
													cssClass="hasAmount form-control text-right" path="total"
													readonly="true"></form:input></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>






			</div>
		</form:form>
	</div>
</div>