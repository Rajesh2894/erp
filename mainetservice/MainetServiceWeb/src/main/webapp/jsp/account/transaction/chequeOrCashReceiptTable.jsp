<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
.set-checkbox {
	position: relative !important;
}
</style>


<script>
$(document).ready(function() {
   
	function closeErrBox() {
		$('.error-div').hide();
	}
	 
    // add multiple select / deselect functionality
    $("#selectall").click(function () {
    	
    });
    
$(".case").click(function(){
	   var id=$(this).attr('id');
		var amount=parseFloat($("#receiptAmount"+id).val());
		var totalAmount;
	
		if($("#receiptTotalAmount").val()!=undefined && $("#receiptTotalAmount").val()!="")
			{
		
		  totalAmount=parseFloat($("#receiptTotalAmount").val());
			}
		else
			{
			
			totalAmount=0;
			}
	
	if($('input[id="'+id+'"]:checked').length >0)
		{
		 
		     totalAmount=totalAmount+amount;
		}
	else
		{
		
		     totalAmount=totalAmount-amount;
		}
  
	
     $("#receiptTotalAmount").val(totalAmount);
    	var alertms=getLocalMessage('tp.app.alMessg');
    	
    	if($(".case").length == 0)	
    	{	
    		alert(alertms);
    	}
    	else
    		{
    		
    	
    		}
    
    	});

});

function selectAllReceipt()
{
  var totalAmount=0;
  $('.receiptTr').each(function(i) {
	  var amount=parseFloat($("#receiptAmount"+i).val())
	  totalAmount=totalAmount+amount;
	  $("#receiptTotalAmount").val(totalAmount);
	  
  });
    if($("#selectall:checked").length==0)
    	{
    	$(".case").attr("checked", false);
    	 $("#receiptTotalAmount").val(0);
    	}
    else
    	{
    	$(".case").attr("checked", "checked");
    		}
	}

</script>
<form:form id="frmMaster3" class="form-horizontal"
	modelAttribute="accountChequeOrCashDepositeBean" name="frmMaster"
	method="POST" action="AccountFundMaster.html">
	<div id="receiptDetailId" class="panel-collapse">
		<div class="panel-body">
			<div class="table-responsive">
				<table class="table table-bordered table-condensed">
					<tr>
						<th scope="col" width="10%"><spring:message
								code="account.cheque.dishonour.sr.no" text="Sr No." /></th>
						<th scope="col" width="20%"><spring:message
								code="cheque.dd.receipt.number" text="Receipt No." /></th>
						<th scope="col" width="20%"><spring:message
								code="accounts.receipt.receipt_date" text="Receipt Date" /></th>
						<th scope="col" width="18%"><spring:message
								code="budget.reappropriation.master.departmenttype"
								text="Department" /></th>
						<th scope="col" width="15%"><spring:message
								code="budget.allocation.master.amount" text="Amount" /></th>
						<th scope="col" width="15%" class="text-center"><label
							class="checkbox-inline"><input type="checkbox" name="All"
								id="selectall" class="set-checkbox" onclick="selectAllReceipt()" />
								<spring:message code="account.budgetopenmaster.selectall"
									text="Select All" /></label></th>
					</tr>
					<c:forEach items="${listOfReceiptDetails}" var="denLookupVar"
						varStatus="status">
						<c:set value="${status.index}" var="count"></c:set>
						<tr class="receiptTr">
							<td><form:input cssClass="hasNumber form-control"
									value="${count+1}" path="" readonly="true"></form:input></td>
							<form:hidden path="listOfReceiptDetails[${count}].rmRcptid" />
							<td><form:input type="text" id="coins"
									cssClass="form-control"
									path="listOfReceiptDetails[${count}].rmRcptno" readonly="true"></form:input></td>
							<td><form:input type="text" id="coins"
									cssClass="form-control"
									path="listOfReceiptDetails[${count}].rmDate" readonly="true"></form:input></td>
							<td><form:hidden id="coins" cssClass="form-control"
									path="listOfReceiptDetails[${count}].dpDeptId" readonly="true" />
								<form:input id="coins" cssClass="form-control"
									path="listOfReceiptDetails[${count}].deptName" readonly="true" /></td>
							<td><form:input type="text" id="receiptAmount${count}"
									cssClass="form-control text-right"
									path="listOfReceiptDetails[${count}].rmAmount" readonly="true"></form:input></td>
							<td align="center" scope="col"><form:checkbox id="${count}"
									path="listOfReceiptDetails[${count}].selectDs"
									value="${denLookupVar.rmRcptid}" name="case"
									disabled="${isViewMode}" Class="case" /></td>

						</tr>
					</c:forEach>
					<tr>
						<td colspan="4" align="right"><b><spring:message
									code="account.cheque.dishonour.total" text="Total :" /></b></td>
						<td align="right"><form:input
								path="listOfReceiptDetails[0].totalAmount" readonly="true"
								cssClass="text-right" id="receiptTotalAmount" /></td>
						<td colspan="2" scope="col">&nbsp;</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</form:form>
