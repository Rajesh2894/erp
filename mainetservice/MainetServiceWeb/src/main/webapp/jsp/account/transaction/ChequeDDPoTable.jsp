<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
.set-checkbox {
	position: relative !important;
}

.case3 {
	position: relative !important;
	margin: 0px !important;
}
</style>


<script>
$(document).ready(function() {
	function closeErrBox() {
		$('.error-div').hide();
	}
	 
    // add multiple select / deselect functionality
    $("#selectallbank").click(function () {
   	 
              $('.case2').attr('checked', this.checked);
              	var cout = 0 ; 	
               if($("#selectallbank").is(":checked") == false){
    		 
    		    $('.case2').attr('checked',false)
    		    searchCheckDetails(this);
    		    cout = 0;
    		   }
              	
              	if($("#selectallbank:checked").length > 0){
    		 
    		  cout++;
    		  searchCheckDetails(this);
    		 
    		 
    		}
    		
         
    });
    
    
    
    
$(".case2").click(function(){
	
    	var alertms=getLocalMessage('tp.app.alMessg');
    	if($(".case2").length == 0)	
    	{	
    		alert(alertms);
    	}
    	else
    		{
    		
    		if($(".case2").length == $(".case2:checked").length) {
                $("#selectallbank").attr("checked", this.checked);
                searchCheckDetails(this);
            } else {
                $("#selectallbank").removeAttr("checked");
                searchCheckDetails(this);
            }
    		}
    	});
    	
    	
// add multiple select / deselect functionality
$("#selectallchequeOrDDD").click(function () {
	   
          var status = this.checked; // "select all" checked status
          $('.case3').each(function(){ //iterate all listed checkbox items
              this.checked = status; //change ".checkbox" checked status
          });
          var totalAmount=0;
          $('.chequeTr').each(function(i) {
        	  var amount=parseFloat($("#chequeAmount"+i).val())
        	  
        	  totalAmount=totalAmount+amount;
        	  $("#chequeTotalAmount").val(totalAmount);
        	  
          });				
          if($("#selectallchequeOrDDD:checked").length==0)
    	  {
    	  $("#chequeTotalAmount").val(0);
    	  }
});




$(".case3").click(function(){
    var id=$(this).attr('id');
 
	var amount=parseFloat($("#chequeAmount"+id).val());
	

	if($("#chequeTotalAmount").val()!=undefined && $("#chequeTotalAmount").val()!="")
		{
	  var totalAmount=parseFloat($("#chequeTotalAmount").val());
		}
	else
		{
		var totalAmount=0;
		}
	if($('input[id="'+id+'"]:checked').length >0)
	{
		if(isNaN(amount)) {
			 totalAmount=totalAmount;
			
		 }else{
			 totalAmount=totalAmount+amount;
		 }
	}
else
	{
	if(isNaN(amount)) {
		totalAmount = 0;
	}else{
		  totalAmount=totalAmount-amount;	
	}
	}


//$("#chequeTotalAmount").val(totalAmount);
var result = parseFloat(totalAmount.toFixed(2));
$("#chequeTotalAmount").val(result);
getAmountFormatInStatic('chequeTotalAmount');


	var alertms=getLocalMessage('tp.app.alMessg');
	
	if($(".case3").length == 0)	
	{	
		alert(alertms);
	}
	$("#deptId").removeClass("mandColorClass");
});

});

</script>
<form:form id="frmMaster3" class="form-horizontal"
	modelAttribute="accountChequeOrCashDepositeBean" name="frmMaster"
	method="POST" action="AccountFundMaster.html">
	<form:hidden path="isEmpty" id="isEmpty" />
	<div class="panel-heading">
		<h4 class="panel-title" id="">
			<a data-toggle="collapse" class="panel-collapse in"
				href="#chequeOrDDDetails3"><spring:message
					code="cheque.dd.deposite.detail"
					text="Deposit Cheque/DD/PO Details" /></a>
		</h4>
	</div>
	<div id="chequeOrDDDetails3" class="panel-collapse in">
		<div class="panel-body">
			<div class="table-responsive" style="max-height: 250px;">
				<table
					class="table table-bordered table-condensed reduce-padding checkClass"
					id="">
					<tr>
						<th scope="col" width="5%"><spring:message
								code="account.cheque.dishonour.sr.no" text="Sr No." /></th>
						<th scope="col"><spring:message
								code="cheque.dd.receipt.number" text="Receipt No." /></th>
						<th scope="col"><spring:message
								code="accounts.receipt.receipt_date" text="Receipt Date" /></th>
						<th scope="col"><spring:message
								code="budget.reappropriation.master.departmenttype"
								text="Department" /></th>
						<th scope="col"><spring:message
								code="account.bankmaster.type" text="Type" /></th>
						<th scope="col"><spring:message
								code="accounts.receipt.cheque_dd_no_pay_order"
								text="Instrument No." /></th>
						<th scope="col"><spring:message
								code="accounts.receipt.cheque_dd_date" text="Instrument Date" /></th>
						<th scope="col"><spring:message
								code="account.cheque.dishonour.drawn.bank" text="Drawn On Bank" /></th>
						<th scope="col"><spring:message
								code="budget.allocation.master.amount" text="Amount" /></th>
						<th scope="col" class="text-center"><div class="text-center">
								<spring:message code="account.cheque.cash.all" text="All" />
								<br> <input type="checkbox" name="All"
									id="selectallchequeOrDDD" class="case3" onchange="getAmountFormatInStatic('chequeTotalAmount')">
							</div></th>
					</tr>
					<c:forEach items="${listOfChequeDDPoDetails}" var="denLookupVar"
						varStatus="status">
						<c:set value="${status.index}" var="count"></c:set>
						<tr class="chequeTr">
						<form:hidden path="listOfChequeDDPoDetails[${count}].branch" />
							<td><form:input cssClass="hasNumber form-control"
									value="${count+1}" path="" disabled="true"></form:input> <form:hidden
									path="listOfChequeDDPoDetails[${count}].rmRcptid" /> <form:hidden
									path="listOfChequeDDPoDetails[${count}].modeId" /></td>
							<td><form:input type="text" id="coins"
									cssClass="form-control text-right"
									path="listOfChequeDDPoDetails[${count}].rmRcptno"
									readonly="true"></form:input></td>
							<td><form:input type="text" id="coins"
									cssClass="form-control"
									path="listOfChequeDDPoDetails[${count}].rmDate" readonly="true"></form:input></td>
							<td><form:input type="text" id="coins"
									cssClass="form-control"
									path="listOfChequeDDPoDetails[${count}].deptName"
									readonly="true"></form:input></td>
							<td><form:input type="text" id="coins"
									cssClass="form-control"
									path="listOfChequeDDPoDetails[${count}].instrumentType"
									readonly="true"></form:input></td>
							<td><form:input type="text" id="coins"
									cssClass="form-control text-right"
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
							<td><form:input type="text" id="chequeAmount${count}"
									cssClass="form-control text-right"
									path="listOfChequeDDPoDetails[${count}].rmAmount"
									readonly="true" ></form:input></td>
							<td align="center" scope="col"><form:checkbox
									path="listOfChequeDDPoDetails[${count}].selectDs"
									value="${denLookupVar.rmRcptno}" name="case3"
									disabled="${isViewMode}" class="case3" id="${count}"  onchange="getAmountFormatInStatic('chequeTotalAmount')"/></td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="8" class="text-center"><b><spring:message
									code="account.cheque.dishonour.total" text="Total :" /></b></td>
						<td class="text-center"><form:input path="total"
								readonly="true" id="chequeTotalAmount"
								class="form-control text-right"  /></td>
						<td align="right"></td>
					</tr>

				</table>
			</div>
		</div> 
	</div>
</form:form>
