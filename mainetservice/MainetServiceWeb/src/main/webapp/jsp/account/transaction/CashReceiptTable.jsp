<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/mainet/script-library.js"></script>
<script>
$(document).ready(function() {
	function closeErrBox() {
		$('.error-div').hide();
	}
    $("#selectallLD").click(function () {
  var totalAmount=0;
  $('.ledgerTr').each(function(i) {
	  var amount=parseFloat($("#ledgerAmount"+i).val())
	  totalAmount=totalAmount+amount;
	  $("#ledgerTotalAmount").val(totalAmount);
	  
  });
   
  var status = this.checked; // "select all" checked status
  $('.case1').each(function(){ //iterate all listed checkbox items
      this.checked = status; //change ".checkbox" checked status
  });
  if($("#selectallLD:checked").length==0)
	  {
	  $("#ledgerTotalAmount").val(0);
	  }
    });
    
$(".case1").click(function(){
        
    	
    	
    	 var id=$(this).attr('id');
 		var amount=parseFloat($("#ledgerAmount"+id).val());
 		if($("#ledgerTotalAmount").val()!=undefined && $("#ledgerTotalAmount").val()!="")
 			{
 		  var totalAmount=parseFloat($("#ledgerTotalAmount").val());
 			}
 		else
 			{
 			var totalAmount=0;
 			}
 		if($('input[id="'+id+'"]:checked').length >0)
 		{
 		 
 		     totalAmount=totalAmount+amount;
 		}
 	else
 		{
 		
 		     totalAmount=totalAmount-amount;
 		}
   
   
     // $("#ledgerTotalAmount").val(totalAmount);
      
      var result = parseFloat(totalAmount.toFixed(2));
      $("#ledgerTotalAmount").val(result);
      getAmountFormatInStatic('ledgerTotalAmount');
      
     	var alertms=getLocalMessage('tp.app.alMessg');
     	
     	if($(".case1").length == 0)	
     	{	
     		alert(alertms);
     	}
    
     	 $("#deptId").removeClass("mandColorClass");
    	});
});



</script>
<form:form id="frmMaster2" class="form-horizontal"
	modelAttribute="accountChequeOrCashDepositeBean" name="frmMaster"
	method="POST" action="AccountDepositSlip.html">
	<form:hidden path="isEmpty" id="isEmpty" />
	<div id="ledgerDetailId" class="panel-collapse in">
		<div class="panel-body">
			<div class="table-responsive" style="max-height: 250px; overflow:auto;">
				<table id="ledgerDetailsId"
					class="table table-bordered table-condensed">
					<tbody id="ledgerTbody">
						<tr>
							<th scope="col" width="20%"><spring:message
									code="accounts.receipt.receipt_date" text="Receipt Date" /></th>
							<th scope="col" width="20%"><spring:message
									code="cheque.dd.receipt.number" text="Receipt No." /></th>
							<th scope="col" width="20%"><spring:message
									code="accounts.receipt.manual_receipt_no" text="Manual Receipt No." /></th>
							<th scope="col" width="20%"><spring:message
									code="accounts.receipt.receipt_amount" text="Receipt Amount" /></th>
									
							<th scope="col" width="20%" align="center"><label
								class="checkbox-inline"> <input type="checkbox"
									name="All" id="selectallLD" onchange="getAmountFormatInStatic('ledgerTotalAmount')" /> <spring:message
										code="account.budgetopenmaster.selectall" text="Select All"  /></label></th>

						</tr>
						<c:forEach items="${listOfLedgerDetails}" var="denLookupVar"
							varStatus="status">
							<c:set value="${status.index}" var="count"></c:set>
							<tr class="ledgerTr">
								<td><form:input type="text" id="coins"
										cssClass="form-control text-left"
										path="listOfLedgerDetails[${count}].rmDate" readonly="true" ></form:input></td>
								<td><form:input type="text" id="rmRcptno"
										cssClass="form-control text-left"
										path="listOfLedgerDetails[${count}].rmRcptno" readonly="true" ></form:input></td>
										<td><form:input type="text" id="manualReceiptNo"
										cssClass="form-control text-left"
										path="listOfLedgerDetails[${count}].manualReceiptNo" readonly="true" ></form:input></td>
										<td><form:input type="text" id="ledgerAmount${count}"
										cssClass="form-control text-right"
										path="listOfLedgerDetails[${count}].feeAmount" readonly="true" ></form:input></td>
										
								<td align="center" scope="col"><label class="radio-inline"><form:checkbox
											path="listOfLedgerDetails[${count}].selectDs" value=""
											name="case1" disabled=" " class="case1" id="${count}" /></label></td>
								<form:hidden path="listOfLedgerDetails[${count}].receiptIds" />
							</tr>
						</c:forEach>
						<tr>
							<td colspan="3" class="text-left"><b><spring:message
										code="account.cheque.dishonour.total" text="Total :" /></b></td>
							<td align="right"><form:input
									path="listOfReceiptDetails[0].totalAmount"  readonly="true" 
									id="ledgerTotalAmount" class="form-control text-right"  /></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form:form>
