
$("#accountDepositBean").validate({
	
	onkeyup: function(element) {
	       this.element(element);
	       console.log('onkeyup fired');
	 },
	onfocusout: function(element) {
	       this.element(element);
	       console.log('onfocusout fired');
	}
});

var depId='';
$(function(){
	$("#depositGrid").jqGrid(
			{
				url:"AccountDeposit.html?getGridData",
				datatype:"json",
				mtype:"POST",
				colNames:['',getLocalMessage('account.deposit.depNo'),getLocalMessage('accounts.deposite.date'),getLocalMessage('account.deposite.party.name'),getLocalMessage('account.deposit.Referenceno'),getLocalMessage('bill.amount'),"","","","",getLocalMessage('bill.action')],
				colModel:[
				          	{name : "depId",sortable : true,searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				          	{name : "depNo",sortable : true,searchoptions: { "sopt": ["bw", "eq"] }},
				          	{name : "tempDate" ,sortable : true,searchoptions: { "sopt": [ "eq"] }}, 
				          	{name : "vendorName",sortable : true,searchoptions: { "sopt": [ "eq"] }}, 
				          	//{name : "cpdDepositTypeDup", width : 45,sortable : true,secrchoptions: { "sopt": ["eq"]}},
				          	{name : "depReceiptno",sortable : true,searchoptions: { "sopt": [ "eq"] }}, 
				          	{name : "depositAmount",sortable : true,classes : 'text-right',searchoptions: { "sopt": [ "eq"] }},
				          	{name : "statusCodeFlag",sortable : true,searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				          	{name : "statusCodeValue",sortable : true,searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				          	{name : "balanceAmount",sortable : true,searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				          	{name : "adv_del_flag",sortable : true,searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				          	//{name : 'depId',index : 'depId',width : 20,align : 'center',formatter : returnEditUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}, 
				          	//{name : 'depId',index : 'depId',width : 20,align : 'center',formatter : returnViewUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}
				          	{ name: 'depId', index: 'depId', align: 'center !important',formatter:addLink,search :false},
				          	],

				          	pager : "#pagered",
				          	rowNum : 30,
				          	rowList : [ 5, 10, 20, 30 ],
				          	sortname : "dsgid",
				          	sortorder : "desc",
				          	height : 'auto', 
				          	viewrecords : true,
				          	gridview : true,
				          	loadonce : true,
				          	jsonReader : {
				          		root : "rows",
				          		page : "page",
				          		total : "total",
				          		records : "records",
				          		repeatitems : false,
				          	},
				          	autoencode : true,
				          	caption : getLocalMessage("account.tblHeading.depositList"),
				          	});
								jQuery("#depositGrid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
								$("#pagered_left").css("width", "");
							
				});

function returnEditUrl(cellValue, options, rowdata, action) {
	depId = rowdata.depId;
return "<a href='#'  return false; class='editClass' value='"+depId+"'><img src='css/images/edit.png' width='20px' alt='Edit  Master' title='Edit  Master' /></a>";
}

function returnViewUrl(cellValue, options, rowdata, action) {
	return "<a href='#'  return false; class='viewClass'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}

function addLink(cellvalue, options, rowdata) {
    return "<a class='btn btn-blue-3 btn-sm viewClass' title='View'value='"+rowdata.depId+"' id='"+rowdata.depId+"' ><i class='fa fa-building-o'></i></a> " +
    		"<a class='btn btn-warning btn-sm editClass' title='Edit'value='"+rowdata.depId+"' id='"+rowdata.depId+"' ><i class='fa fa-pencil'></i></a> " +
      		"<a class='btn btn-success btn-sm' title='ReFund' onclick=\"doOpenBillPageForDeposit('"+rowdata.depId+"','"+rowdata.statusCodeFlag+"','"+rowdata.statusCodeValue+"','"+rowdata.balanceAmount+"')\"><i class='fa fa-file-text text-white'></i></a> "+
      		"<a class='btn btn-danger btn-sm' title='Transfer' onclick=\"doOpenTransferPageForDeposit('"+rowdata.depId+"','"+rowdata.statusCodeFlag+"','"+rowdata.statusCodeValue+"','"+rowdata.balanceAmount+"')\"><i class='fa fa-file-text-o'></i></a> ";
 }

$(function() {
	$(document).on('click', '.createData', function() {
		
		var url = "AccountDeposit.html?form";
		var requestData ="MODE_DATA=" + "EDIT";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		$('.content').html(returnData);
		prepareDateTag();
		return false;			
	});			
});

$(function() {
	
	$("#search").click(function(){ 
		//$(".error-div").html('');
		
		var errorList1 = [];
		var dep = $('#depId').val(); 
		 var depId = $.trim($("#depId").val());
		 if(depId==0 || depId=="")
		    errorList1.push(getLocalMessage('account.select.depositNo'));
		 
		 if(errorList1.length > 0){ 
			 
				var errMsg = '<ul>';
				$.each(errorList1, function(index) {
					errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList1[index] + '</li>';
				});

				errMsg += '</ul>';

				$('#errorId').html(errMsg);
				$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');
				return false;
		} 
			if( dep != '' ) {				
			var url = "AccountDeposit.html?update";
			var returnData = "depId="+dep;
			
			$.ajax({
				url : url,
				data : returnData,
				success : function(response) {
					
					var divName = '.widget';
					
					
					$("#depositDiv").html(response);
					$("#depositDiv").show();
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});						
		}
	});
});

$(document).on('click', '.editClass', function() {
	var errorList = [];
	
	var $link = $(this);
	/* var paAdjid = $link.closest('tr').find('td:eq(0)').text(); */
	var depId = $link.closest('tr').find('td:eq(0)').text(); 
	var authStatus = $link.closest('tr').find('td:eq(6)').text();
	var statusCodeValue = $link.closest('tr').find('td:eq(7)').text();
	var receiptEntryIdentifyFlag = $link.closest('tr').find('td:eq(9)').text();
	var url = "AccountDeposit.html?update";
	var requestData = "depId=" + depId + "&MODE_DATA=" + "EDIT";
	var returnData =__doAjaxRequest(url,'post',requestData,false);
	
	if(receiptEntryIdentifyFlag=="Y"){
		errorList.push("You are not allowed to edit this deposit against receipt.");
		if(errorList.length>0){
	    	var errorMsg = '<ul>';
	    	$.each(errorList, function(index){
	    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	    	});
	    	errorMsg +='</ul>';
	    	$('#errorId').html(errorMsg);
	    	$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');	    	
	    }
	}else if(receiptEntryIdentifyFlag=="B"){
		errorList.push("You are not allowed to edit this deposit against bill.");
		if(errorList.length>0){
	    	var errorMsg = '<ul>';
	    	$.each(errorList, function(index){
	    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	    	});
	    	errorMsg +='</ul>';
	    	$('#errorId').html(errorMsg);
	    	$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');	    	
	    }
	}else{
		if(authStatus=="DO"){
			$('.content').html(returnData);
		}
		else{
			errorList.push("It is already "+ statusCodeValue +" so EDIT is not allowed!");
			if(errorList.length>0){
				var errorMsg = '<ul>';
				$.each(errorList, function(index){
					errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
				});
				errorMsg +='</ul>';
				$('#errorId').html(errorMsg);
				$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');	    	
			}
		}
	}
	
});

/*$(function() {
	$(document).on('click', '.editClass', function() {
		var $link = $(this);
		var depId = $link.closest('tr').find('td:eq(0)').text();
		var authStatus = $link.closest('tr').find('td:eq(6)').text();
		if(authStatus!="DO"){
			$('.content').html(returnData);
		}
		else{
		var url = "AccountDeposit.html?update";
		var requestData = "depId=" + depId + "&MODE_DATA=" + "EDIT";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		$('.content').html(returnData);
		
		prepareDateTag();
			
	});
});
*/
$(function() {
	$(document).on('click', '.viewClass', function() {
		var $link = $(this);
		var depId = $link.closest('tr').find('td:eq(0)').text();
		var url = "AccountDeposit.html?update";
		var requestData = "depId=" + depId + "&MODE_DATA=" + "VIEW";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		$('.content').html(returnData);
		
		prepareDateTag();
			
	});
});

function doOpenBillPageForDeposit(depId,statusCodeFlag,statusCodeValue,balanceAmount){
	
	
	
    var errorList = [];
	var gr = jQuery("#depositGrid").jqGrid('getGridParam','selrow');	
	var url = "AccountBillEntry.html?createformFromDeposit";
	var requestData = {"depId" : depId,"MODE"  :"CREATE"};
	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	if(statusCodeFlag=="DO"){
		$('.content').html(returnData);
	}
	else{
		errorList.push("It is already "+ statusCodeValue +" so Refund is not allowed!");
	if(errorList.length>0){
	    var errorMsg = '<ul>';
	    $.each(errorList, function(index){
	    	errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	    });
	    errorMsg +='</ul>';
	    $('#errorId').html(errorMsg);
	    $('#errorDivId').show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');	    	
		}
	}
};

function doOpenTransferPageForDeposit(depId,statusCodeFlag,statusCodeValue,balanceAmount){
	
    var errorList = [];
	var gr = jQuery("#depositGrid").jqGrid('getGridParam','selrow');	
	var url = "AccountVoucherEntry.html?createformFromDeposit";
	var requestData = {"depId" : depId,"MODE"  :"CREATE"};
	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	if(statusCodeFlag=="DO"){
		$('.content').html(returnData);
	}
	else{
		errorList.push("It is already "+ statusCodeValue +" so Transfer is not allowed!");
	if(errorList.length>0){
	    var errorMsg = '<ul>';
	    $.each(errorList, function(index){
	    	errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	    });
	    errorMsg +='</ul>';
	    $('#errorId').html(errorMsg);
	    $('#errorDivId').show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');	    	
		}
	}
};

function closeErrBox() {
	$('.error-div').hide();
}

var elementTemp;
function saveDepositForm(element){
	//debugger;
	elementTemp=element;
	 var errorList = [];
	var depEntryDateId = $('#depEntryDateId').val(); 
	if(depEntryDateId!=null)
		{
		errorList = validatedate(errorList,'depEntryDateId');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#depEntryDateId").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			
			if (date >= sliDate) {
				errorList.push("Deposit date can not be greater than or equal  SLI date");
			  }
			}
		  }
		}
	if(errorList.length>0){
    	
    	var errorMsg = '<ul>';
    	$.each(errorList, function(index){
    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
    		
    	});
    	errorMsg +='</ul>';
    	
    	$('#errorId').html(errorMsg);
    	$('#errorDivId').show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');
    		    	
	}
	else
		{
		showConfirmBoxSave();
	/*var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var url = $(theForm).attr('action');
	//return doActionWithParam(element,"Saved Successfully", requestData, 'AccountDeposit.html');
	
	var response= __doAjaxRequestValidationAccor(element,url+'?create', 'post', requestData, false, 'html');
    if(response != false){
       $('.content').html(response);
    }*/
	//return saveOrUpdateForm(element, 'Saved Successfully', 'AccountDeposit.html', 'create');
		}
}


function showConfirmBoxSave(){
	
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var saveMsg=getLocalMessage("account.btn.save.msg");
	var cls =getLocalMessage("account.btn.save.yes");
	var no=getLocalMessage("account.btn.save.no");
	
	message	+='<h4 class=\"text-center text-blue-2\"> '+saveMsg+' </h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="saveDepositeFormAfterConfimation()"/>  '+ 
	'<input type=\'button\' value=\''+no+'\' tabindex=\'0\' id=\'btnNo\' class=\'btn btn-blue-2 autofocus\'    '+ 
	' onclick="closeConfirmBoxForm()"/>'+ 
	'</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function saveDepositeFormAfterConfimation(){
	//debugger;
	var formName = findClosestElementId(elementTemp, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var url = $(theForm).attr('action');
	//return doActionWithParam(element,"Saved Successfully", requestData, 'AccountDeposit.html');
	
	var response= __doAjaxRequestValidationAccor(elementTemp,url+'?create', 'post', requestData, false, 'html');
    if(response != false){
       $('.content').html(response);
    }else{
    	closeConfirmBoxForm();
    }
}




function displayMessageOnSubmit(successMsg){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = getLocalMessage('account.proceed.btn');
	
	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
	message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="redirectToDepositHomePage()"/></div>';
	 
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function redirectToDepositHomePage () {
	$.fancybox.close();
	window.location.href='AccountDeposit.html';
}

function showPopUpMsg(childDialog){
	$.fancybox({
        type: 'inline',
        href: childDialog,
        openEffect  : 'elastic', // 'elastic', 'fade' or 'none'
        closeBtn : false ,
        helpers: {
			overlay : {
				closeClick : false
			}
		},
		 keys : {
			    close  : null
			  }
    });
	return false;
}


function searchDepositEntryData(){

 	/*alert("In search");*/
	
	$('.error-div').hide();

	var errorList = [];
	
	var depNo = $("#depNo").val();
	var vmVendorid = $("#vmVendorid").val();
	var cpdDepositType = $("#cpdDepositType").val();
	var sacHeadId = $("#sacHeadId").val();
	var date = $("#date").val();
	var depAmount = $("#depAmount").val();
	
	if ((depNo == "" || depNo =="0") && (vmVendorid == "" || vmVendorid =="0") && (cpdDepositType == "" || cpdDepositType =="0") && (sacHeadId == "" || sacHeadId =="0") && (date == "" || date =="0") && (depAmount == "" || depAmount =="0")) {
		errorList.push(getLocalMessage('account.deposit.search.validation'));
	}
	if(date!=null)
		{
		errorList=validatedate(errorList,'date');
		}
	
	if(errorList.length>0){
    	
    	var errorMsg = '<ul>';
    	$.each(errorList, function(index){
    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
    		
    	});
    	errorMsg +='</ul>';
    	
    	$('#errorId').html(errorMsg);
    	$('#errorDivId').show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');
    		    	
    }
	 
	if (errorList.length == 0) 
	{

	var url ="AccountDeposit.html?getjqGridsearch";
	var requestData = {
			"depNo" : depNo,
			"vmVendorid" : vmVendorid,
			"cpdDepositType" : cpdDepositType,
			"sacHeadId" : sacHeadId,
			"date" : date,
			"depAmount" : depAmount
		};
	
	var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	
	if (result != null && result != "") {
		$("#depositGrid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
	} else {
		var errorList = [];
		
		errorList.push(getLocalMessage("account.norecord.criteria"));
		
		if(errorList.length>0){		    	
	    	var errorMsg = '<ul>';
	    	$.each(errorList, function(index){
	    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';		    		
	    	});
	    	errorMsg +='</ul>';		    	
	    	$('#errorId').html(errorMsg);
	    	$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');		    		    	
	    }
		$("#depositGrid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
	}
}
};

function changeValidLiveDepositDate(obj){
	
	$('.error-div').hide();
	var errorList = [];
	
	var maxDate = ($('#liveModeDate').val());
	var minDate = ($('#depEntryDate').val());
	
	var arr = minDate.split('/');
	var day=arr[0];
	var month=arr[1];
	var year=arr[2];
	var curArr = month+"/"+day+"/"+year;
	
	var D5 = new Date(maxDate); 
	var D6 = new Date(curArr); 
	if(D6<D5)
	{
	  
	}
	else{
		errorList.push(getLocalMessage('account.depoDate.notEqual.greater.sliPrefixDate'));
		$('#depEntryDate').val("");
	}
	 if(errorList.length>0){
	    	var errorMsg = '<ul>';
	    	$.each(errorList, function(index){
	    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	    		
	    	});
	    	errorMsg +='</ul>';			    	
	    	$('#errorId').html(errorMsg);
	    	$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');		    	
	 }
};
	
 /*function changeBalanceAmount(obj){*/
	$('#depRefundBal').change(function() {
		 
	var minAmt = ($('#depAmount').val());
	var maxAmt = ($('#depRefundBal').val());
	 
	  if ((minAmt != '' && minAmt != undefined && !isNaN(minAmt)) && (maxAmt != '' && maxAmt != undefined && !isNaN(maxAmt))){
	    try{
	      maxAmt = parseFloat(maxAmt);
	      minAmt = parseFloat(minAmt);

	      if(maxAmt > minAmt) {
	        var msg = "Balance Amount can not be greater than Deposit Amount.!";
	    	  showErrormsgboxTitle(msg);
	        $('#depRefundBal').val("");
	        return false;
	      }
	    }catch(e){
	      return false;
	    }
	  } //end maxAmt minAmt comparison 
	 });
	
	
	// save function added by satish rathore start
	
	  function save(element)
	  {
		  var redirectURL;
		  var errorList = [];	
		  var sumDebit = 0;
		  var sumCredit = 0;
		
		  if($("#formMode").val()=='AUTH') {
			  if($("#authRemark").val()=="")
			  {
			  errorList.push(getLocalMessage("account.enter.approve.disapprove.remark"));
			  } 
			  var transactionAuthDate= $.trim($('#transactionAuthDateId').val());
			  if (transactionAuthDate == '' || transactionAuthDate == undefined) {
				  errorList.push(getLocalMessage("account.authdate"));
			  }
		  } else {
			  var transactionDate= $.trim($('#transactionDateId').val());
			  
			  if (transactionDate == '' || transactionDate == undefined) {
				  errorList.push(getLocalMessage("account.select.transactiondate"));
			  }
			  if($("#voucherType").val()=="0") {
				  errorList.push(getLocalMessage("account.select.voucher.type"));
			   }
			  if($("#voucherSubType").val()=="0") {
				  errorList.push(getLocalMessage("account.select.voucher.subtype"));
			  }
		  
		  }
		  var functionB=false;
		     var secondaryB=false;
		     var typeB=false;
		     var amountB=false;
		     var drCount = 0;
		     var depSaHeadCount = 0;
		     
		     var depSacHeadId=$.trim($("#depSacHeadId").val());
		     
		    $('.accountClass').each(function(i) {
		    	 var functionCode=$.trim($("#function"+i).val());	
			   	 var secondary = $.trim($("#secondary"+i).val());			   	
			     var amount= $.trim($("#amount"+i).val());
			     var type=$.trim($("#type"+i).val());
			     var level=i+1;
			   
			    
			     if($('#type'+i).find('option:selected').attr('code')=='DR'){
			     		 drCount++;
			     }
			     
			     if(($('#type'+i).find('option:selected').attr('code')=='DR') && (depSacHeadId == secondary)){
			    	 depSaHeadCount++;
			     }
			     if(!typeB)
					 if(type==0 ||type=="")
			   		 {
			   		    errorList.push(getLocalMessage("account.select.dr.cr"));
			   		 typeB=true;
			   		 }
			     if(!secondaryB)
			   	 if(secondary==0 || secondary=='0')
		   		 {
		   		    errorList.push(getLocalMessage("account.select.proper.acchead"));
		   		 secondaryB=true;
		   		 }
			     if(!amountB)
			   	 if(amount==0 ||amount=="")
		   		 {
		   		    errorList.push(getLocalMessage("account.enter.amt"));
		   		 amountB=true;
		   		 }
			     
		        });
		    
		    if(depSaHeadCount < 1){
		    	 errorList.push(getLocalMessage("account.mulDebitHead.not.allow"));
		    }
		   
		    if(drCount > 1){
		    	 errorList.push(getLocalMessage("account.debitAccHead.depositAccHead"));
		    }
		   
		    var balAmount=$.trim($("#balAmount").val());	
		    var depBalAmt = parseFloat(balAmount);
		    
		    $('.debitClass').each(function(){
				  sumDebit += parseFloat(this.value);
			  });
			  $('.creditClass').each(function(){
				  sumCredit += parseFloat(this.value);
			  });
			 
			    if(sumDebit!=sumCredit)
				 {
			    	 errorList.push("Sum of Debit and Credit should be same");
				 }
			   
			    
			    if(sumDebit > depBalAmt ){
			    	errorList.push("Deposit debit amount should not be greater than deposit balance amount");
			    }
			    
			    $("#depBalAmount").val(sumDebit);
			    
			    if ($("#formMode").val()!='AUTH') {
					  if($("#narration").val()=="")
					  {
					  errorList.push(getLocalMessage("account.narration"));
					  } 
			    }
			    if(errorList.length > 0){ 
			    	var errMsg = '<ul>';
					$.each(errorList, function(index) {
						errMsg += '<li>' + errorList[index] + '</li>';
					});
		         errMsg += '</ul>';
		          $('#errorId').html(errMsg);
					$('#errorDivId').show();
					$('html,body').animate({ scrollTop: 0 }, 'slow');	
			    return false;
			    }
			 
		  if($("#formMode").val()=='AUTH')
			  {
			  redirectURL="AccountVoucherAuthorisation.html";
			  }
		  else
			  {
			//  alert("else")
			  redirectURL="AccountVoucherEntry.html?VoucherReportForm";
				
			  }
		  
		 /* var	formName =	findClosestElementId(element, 'form');
			var theForm	=	'#'+formName;
		    requestData = __serializeForm(theForm);
		 return doFormActionForSaveForm(element,"saved successfully","saveJournalForm",requestData,redirectURL,".widget-content");*/
		  
		  	var transactionDateId = $("#transactionDateId").val();
		  
		    var formName = findClosestElementId(element, 'form');
			var theForm = '#' + formName;
			var requestData = __serializeForm(theForm);
			var url = $(theForm).attr('action');
			
		//	var response = __doAjaxRequestForSave(url, 'post', requestData, false,'', element);
			var ajaxResponse = __doAjaxRequest(url+'?saveJournalForm', 'POST', requestData, false,'',element);
			
			if ($.isPlainObject(ajaxResponse))
			{
				var message = ajaxResponse.command.message;
				//alert(message);
				displayMessageOnTransferSubmit(ajaxResponse,message, url);
			}
			else{
				var divName = '.content';
				$(divName).html(ajaxResponse);
				$("#transactionDateId").val(transactionDateId);
			}
		//	var response = __doAjaxRequest('ContraVoucherEntry.html?getSacHeadCodes','POST',postData,false,'json');
		//	alert("ajaxResponse"+ajaxResponse)
			 
		
		
			 
	  }
	  // end save 
	  
	  // added one more function authentication date 
	  
	 /* function validateAuthorizationDate( selectedDate) {
			
			
			var requestData = {
					'authorizationDate':selectedDate,
					'transactionDate': $('#transactionDateId').val()
			}
			
			var response = __doAjaxRequest('AccountVoucherAuthorisation.html?onAuthorizationDate', 'GET', requestData, false,'json');
			if (response == 'OK') {
				$('#errorDivId').hide();
			} else {
				$('#transactionAuthDateId').val('');
				var errorList = [];
				errorList.push(response);
				var errMsg = '<ul>';
				$.each(errorList, function(index) {
					errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
						+ errorList[index] + '</li>';
				});
				errMsg += '</ul>';
				
				$('#errorId').html(errMsg);
				$('#errorDivId').show();
				$('html,body').animate({
					scrollTop : 0
				}, 'slow');
				return false;
			}
		}*/
// end authantication date 
	  
	  // setStatusClass added by satish Rathore
	
	  function setStatusClass(obj)
	  {
	  	
	  	 var i=$(obj).attr("id").slice(-1);
	  	 $('#secondary'+i).empty(); 
	  	$('#secondary'+i).chosen('destroy'); 
	  	
	  	 /* $('#secondary'+i).removeClass("chosen-select-no-results");
	  	  $('#secondary'+i).val('').trigger('chosen:updated');*/
	  	
	  	if($("#voucherType").val()!='0') {
	  	var transType=$(obj).find('option:selected').attr('code');
	  	var data={"voucherType" : $("#voucherType option:selected").attr("code")   ,"tranType" : transType};
	  	var result=__doAjaxRequest("AccountVoucherEntry.html?getBudgetCode", "POST", data, false);
//	  	$('#secondary'+i).find('option').remove();
	  	$('#secondary'+i).chosen('destroy'); 
	      var  optionsAsString='<option value="0">Select Account Head</option>';
	      		
	      		$.each( result, function( key, value ) {
	      		      			 optionsAsString += "<option value='" +key+"'>" + value + "</option>"
	         	   			
	      			});
	      $('#secondary'+i).append(optionsAsString);
	     $('#secondary'+i).addClass("chosen-select-no-results");
	     chosen();
	  	if(transType=='DR')
	  		{

	  		
	  		$("#amount"+i).addClass("debitClass").removeClass("creditClass");
	  		}
	  	else if(transType=='CR')
	  		{
	  		
	  		$("#amount"+i).addClass("creditClass").removeClass("debitClass");
	  		}
	  	else
	  		{
	  		$("#amount"+i).removeClass("debitClass creditClass");
	  		}
	  	} else {
	  		 var errMsg = '<ul><li>Please Select Voucher Type</li></ul>';
	   		  $('#errorId').html(errMsg);
	   			$('.error-div').show();
	   			$("#type"+i).val("0");
	  		}
	  	  for(var j=0;j<$('#tbl tr').size();j++)
	  		   {
	  		   var bankId=$("#secondary"+j).val();
	  		   if(bankId!=0)
	  			   {
	  			$("#secondary"+i+" option[value="+bankId+"]").prop('disabled',true);
	  			   }
	  		   }
	  	  $('#secondary'+i).val('').trigger('chosen:updated')
	  	 // $('select[id^=secondary]').val('').trigger('chosen:updated');
//	  	 $('select[id^=secondary]').trigger("liszt:updated");
//	  	 $('select[id^=secondary]').chosen().change( function() {
//	  	  var selectedValue = $(this).find('option:selected').val();
//	  	  $(this).parent().find('option[value="'+ selectedValue +'"]:not(:selected)')
//	  	        .prop("disabled", function( i, val ) {
//	  	              return !val;
//	  	            });
//	  	});

	  	
	  	
	  	/*$(document).ready(function () {
	  		$(function() {
	  			$(".criteria_countries").chosen();
	  			$('select[id^=secondary]').change(function() {
	  			    var selectedVal = $(this).val();
	  			    if (selectedVal != '') {
	  					    $("select[id^=secondary] option[value='"+selectedVal+"']").hide();
	  			            $("select[id^=secondary]").trigger("chosen:updated");
	  			        }
	  			        if (selectedVal == '') {
	  			            $("select[id^=secondary] option[value='"+selectedVal+"']").show();
	  			            $("select[id^=secondary]").trigger("chosen:updated");
	  			        }
	  			    });
	  			});
	  			});*/
	  	 
	         

	            
	       }	
	  // end status class 
	  
	  function removeRow(cnt)
	  {
	 	 var deletedClassCnt=$('.deletedThis').length;
	 	 var allCount=$('#tbl tr').size();
	 	  var checkCount=allCount-deletedClassCnt;
	 	
	 	  if (checkCount > 2) {
	 			
	 		    var id=$("#voutId"+cnt).val();
	 		  
	 		   if($("#formMode").val()!="A")
	 		    	{
	 		    	if(id=='0' || id==undefined ||id=='')
	 		    		{
	 		    		$('#tr'+cnt).remove();
	 		    		//reorderAccount();
	 		    		}
	 		    	   else
	 		    		{
	 		    		$("#isDeleted"+cnt).attr("value","D")
	 		    		var data = 'voutId='+id;
	 	               var url	=	'AccountVoucherEntry.html';
	 	                url+='?removeObject';
	                    var result=__doAjaxRequest(url,'post',data,false,'json');
	                    $('#tr'+cnt).addClass("deletedThis");
	 		    	    $('#tr'+cnt).hide();
	 		    	    $("#amount"+cnt).removeClass("debitClass creditClass");
	 		    		}
	 			    }
	 		    else
	 		    	{
	 		    
	 		    	$('#tr'+cnt).remove();
	 		    	reorderAccount();
	 		    	}
	 			} else {

	 					/*  var msg = getLocalMessage('lgl.cantRemove'); */
	 					var msg = "can not remove";
	 					showErrormsgboxTitle(msg);
	 				}
	   }
	   
	   function addRow(cnt,mode)
	   {
	 	 if(mode=='A')
	 		 {
	 	  if(checkValidationAdd(cnt))
	 	      {  
	 	          var content = $("#tbl tr").last().closest('tr.accountClass').clone();
	 	          content.find('div.chosen-container').remove();
	 	          content.find(".applyChoosen").chosen().trigger("chosen:updated");
	 	          $('#tr'+cnt).after(content); //$('#tbl tr').last().after(content);
	 				//content.find$(".applyChoosen").chosen().trigger("chosen:updated");

	 	                        //$(this).closest('tr.accountClass').after(content);
	 	                         content.find('[id^="isDeleted"]').val("N");
	 	                        
	 							  content.find("select").val("0");
	 							  content.find("input:text").val("");
	 							  content.find("input:hidden").val("");
	 							  content.find("input:hidden").val("");
	 							 // content.find(".chosen-select-no-results").empty();
	 							  content.find('#secondary'+i).addClass("chosen-select-no-results");
	 							   reorderAccount();
	 							    var currCnt=parseInt(cnt)+1;
	 						     
	 						      $("#voutId"+currCnt).val("0");
	 			                  $('#tr'+currCnt).removeClass("deletedThis");
	 					    	  $('#tr'+currCnt).show();
	 					    	  $("#isDeleted"+currCnt).val("N");
	 					    	  $('#secondary'+currCnt).find('option').remove();
	 					    	  $('#secondary'+currCnt).chosen('destroy'); 
	 					    	 $('#secondary'+currCnt).empty(); 
	 					    	 
	 					    	  $('#secondary'+currCnt).append('<option value="0">Select Account Head</option>')
	 					    	  	 
	 			          } 
	 	          else
	 	          {
	 	        	  var errMsg = '<ul><li>Please Enter the All Mandatory fields</li></ul>';
	 	      		  $('#errorId').html(errMsg);
	 	      			$('.error-div').show();
	 	      /*   showErrormsgboxTitle("Please Enter the All Mandatory fields"); */
	 	          } 
	 		 }
	 	 else
	 		 {
	 		 finalcnt=$('#tbl tr').size()-1;
	 		 if(checkValidationAdd(cnt))
	 	      {  
	 	          var content = $("#tbl tr").last().closest('tr.accountClass').clone();
	 	          content.find('div.chosen-container').remove();
	 	          content.find(".applyChoosen").chosen().trigger("chosen:updated");
	 	          $('#tr'+cnt).after(content); //$('#tbl tr').last().after(content);
	 				//content.find$(".applyChoosen").chosen().trigger("chosen:updated");

	 	                        //$(this).closest('tr.accountClass').after(content);
	 	          content.find('[id^="isDeleted"]').val("N");
	 	          content.find('[id^="voutId"]').val("0");
	 	          content.find('[id^="secondary"]').find('option').remove();
	 	          content.find('[id^="secondary"]').chosen('destroy');
	 	          content.find('[id^="secondary"]').empty();
	 	          content.closest("tr").removeClass("deletedThis");    
	 	          content.closest("tr").show();  
	 	          content.find('[id^="secondary"]').append('<option value="0">Select Account Head</option>')
	 							  content.find("select").val("0");
	 							  content.find("input:text").val("");
	 							  content.find("input:hidden").val("");
	 							  $('#isDeleted'+finalcnt).val("N");
	 							 // content.find(".chosen-select-no-results").empty();
	 							  content.find('#secondary'+i).addClass("chosen-select-no-results");
	 							   reorderEdit(content,finalcnt);
	 							    var currCnt=parseInt(cnt)+1;
	 						     
	 						     
	 			               /*   $('#tr'+currCnt).removeClass("deletedThis");
	 					    	  $('#tr'+currCnt).show();*/
	 					    	
	 					    	  /*$('#secondary'+currCnt).find('option').remove();
	 					    	  $('#secondary'+currCnt).chosen('destroy'); 
	 					    	 $('#secondary'+currCnt).empty();*/ 
	 					    	 
	 					    	  $('#secondary'+currCnt).append('<option value="0">Select Account Head</option>')
	 					    	  	 
	 			          } 
	 	          else
	 	          {
	 	        	  var errMsg = '<ul><li>Please Enter the All Mandatory fields</li></ul>';
	 	      		  $('#errorId').html(errMsg);
	 	      			$('.error-div').show();
	 	      /*   showErrormsgboxTitle("Please Enter the All Mandatory fields"); */
	 	          } 
	 		 }
	   }	  
	  
	   function reorderAccount()
		{

			$('.accountClass').each(function(i) {
				 //Ids
				$(this).find("input:text:eq(1)").attr("id", "amount" + (i));
			   /* $(this).find("select:eq(0)").attr("id", "fund" + (i));
				$(this).find("select:eq(1)").attr("id", "function" + (i));*/
			    $(this).find("select:eq(1)").attr("id", "secondary" + (i));
				$(this).find("select:eq(0)").attr("id", "type" + (i));
			    $(this).find(".deletClass").attr("id", "deleteAccount" + (i));
				$(this).find("input:text:eq(2)").attr("id", "isDeleted"+ (i)); 
		    	$(this).find("input:hidden:eq(0)").attr("id", "voutId"+ (i)); 
				$(this).closest("tr").attr("id", "tr" + (i));
				$(this).closest('tr').find('#srNo').text(i+1);
				 $(this).find(".addDedButton").attr("id", "addDetails"+ (i));
				 $(this).find(".delDedButton").attr("id", "deleteDetails"+ (i));
				
			    //names
				
				$(this).find("input:text:eq(1)").attr("name", "entity.details[" + (i) + "].voudetAmt");
			 //   $(this).find("select:eq(0)").attr("name", "entity.details[" + (i) + "].fundId");
			 //   $(this).find("select:eq(1)").attr("name", "entity.details[" + (i) + "].functionId");
			    $(this).find("select:eq(1)").attr("name", "entity.details[" + (i) + "].sacHeadId");
			    $(this).find("select:eq(0)").attr("name", "entity.details[" + (i) + "].drcrCpdId");
			    $(this).find("input:text:eq(1)").attr("onchange", "validateAmount(" + (i) + ")");
			    $(this).find("input:text:eq(2)").attr("name", " entity.details[" + (i) + "].deleted"); 
			    $(this).find("input:hidden:eq(0)").attr("name", " entity.details[" + (i) + "].voudetId"); 
			     $(this).find(".addDedButton").attr("onclick", "addRow(" + (i) + ")");
			    $(this).find(".delDedButton").attr("onclick", "removeRow(" + (i) + ")");
			    $(this).find("select:eq(1)").attr("onchange", "changeList(" + (i) + ")");   
			    	
			    	 
			    	 
				
			    });

		}
	function  reorderEdit(content,i)
	  {
		  $(content).find("input:text:eq(0)").attr("id", "amount" + (i));
		   /* $(this).find("select:eq(0)").attr("id", "fund" + (i));
			$(this).find("select:eq(1)").attr("id", "function" + (i));*/
		    $(content).find("select:eq(1)").attr("id", "secondary" + (i));
			$(content).find("select:eq(0)").attr("id", "type" + (i));
		    $(content).find(".deletClass").attr("id", "deleteAccount" + (i));
			$(content).find("input:text:eq(1)").attr("id", "isDeleted"+ (i)); 
	   	$(content).find("input:hidden:eq(0)").attr("id", "voutId"+ (i)); 
			$(content).closest("tr").attr("id", "tr" + (i));
			$(content).closest('tr').find('#srNo').text(i+1);
			 $(content).find(".addDedButton").attr("id", "addDetails"+ (i));
			 $(content).find(".delDedButton").attr("id", "deleteDetails"+ (i));
			
		    //names
			
			$(content).find("input:text:eq(0)").attr("name", "entity.details[" + (i) + "].voudetAmt");
		 //   $(this).find("select:eq(0)").attr("name", "entity.details[" + (i) + "].fundId");
		 //   $(this).find("select:eq(1)").attr("name", "entity.details[" + (i) + "].functionId");
		    $(content).find("select:eq(1)").attr("name", "entity.details[" + (i) + "].sacHeadId");
		    $(content).find("select:eq(0)").attr("name", "entity.details[" + (i) + "].drcrCpdId");
		    $(content).find("input:text:eq(0)").attr("onchange", "validateAmount(" + (i) + ")");
		    $(content).find("input:text:eq(1)").attr("name", " entity.details[" + (i) + "].deleted"); 
		    $(content).find("input:hidden:eq(0)").attr("name", " entity.details[" + (i) + "].voudetId"); 
		     $(content).find(".addDedButton").attr("onclick", "addRow(" + (i) + ")");
		    $(content).find(".delDedButton").attr("onclick", "removeRow(" + (i) + ")");
		    $(content).find("select:eq(1)").attr("onchange", "changeList(" + (i) + ")");    
	  }
	  
	 function checkValidationAdd(cnt)
	  {
		
		  var check=cnt;
		 
		    if($('#tr'+check).hasClass('deletedThis')==false)
		    	{
		   if($("#secondary"+check).val()=='0' ||  $("#amount"+check).val()=='' || $("#type"+check).val()=='0')
			 {
			return false;
			 }
		    }
		 return true;
	   }
	 
	 
	 function changeList(i)
	 {
	 	var bankId=$("#secondary"+i).val();
	 	var exist=false;
	 	for(var j=0;j<$('#tbl tr').size();j++)
	 	   {
	 	   if(bankId!=0 && i!=j)
	 		   {
	 		   var selectedValue=$("#secondary"+j).val();
	 		  // $("#secondary"+j+" option").prop("disabled", false);
	 		 	$("#secondary"+j+" option[value="+bankId+"]").prop('disabled',true);
	 		 	//$("#secondary"+j+" option[value="+selectedValue+"]").prop('disabled',true);
	 		 	// $('#secondary'+j).val(selectedValue);
	 		   $('#secondary'+j).trigger('chosen:updated');
	 		
	 		   }
	 	     
	 	  
	 	   }
	 	/*for(var k=0;k<$('#tbl tr').size();k++)
	 	   {
	 	   var allDisabeld=$("#secondary"+k+" option[disabled]");
	 		 for(var d=0;d<allDisabeld.length;d++)
	 		    	   {
	 		    	
	 		    	   var disabledVal=$(allDisabeld[d]).val();
	 		    	 
	 		    	   for(var l=0;l<$('#tbl tr').size();l++)
	 					  {
	 		    		   if(k!=l)
	 		    			   {
	 					  var selectedValueL=$("#secondary"+l).val();
	 					  if(disabledVal!=selectedValueL)
	 						  {
	 						  alert("not Matched");
	 						  }
	 		    			   }
	 		    	   }
	 			  
	 			  }
	 		 
	 		 
	 		   }*/


	 }
	 function validateAmount(amountCount)
	  {
		  var regx	= /^\d{0,5}(\.\d{1,3})?$/;
			var amount	=	$("#amount"+amountCount).val();
		
			if(!regx.test(amount))
			{
				$(this).val('');	
			}
			
			var amount = $("#amount"+amountCount).val();

			if (amount != undefined && !isNaN(amount) && amount != null && amount != '') {
				
			var actualAmt = amount.toString().split(".")[0];
			var decimalAmt = amount.toString().split(".")[1];
			
			var decimalPart =".00";
			if(decimalAmt == null || decimalAmt == undefined){
				$('#amount'+amountCount).val(actualAmt+decimalPart);
			}else{
				if(decimalAmt.length <= 0){
					decimalAmt+="00";
					$('#amount'+amountCount).val(actualAmt+(".")+decimalAmt);
				}
				else if(decimalAmt.length <= 1){
					decimalAmt+="0";
					$('#amount'+amountCount).val(actualAmt+(".")+decimalAmt);
				}else{
					if(decimalAmt.length <= 2){
					$('#amount'+amountCount).val(actualAmt+(".")+decimalAmt);
					} 
				  }	
			   }
		    }
			
	  }
	 function displayMessageOnTransferSubmit(successMsg,message,redirectURL) {
			
			var	errMsgDiv		=	'.msg-dialog-box';
			var cls = getLocalMessage('account.proceed.btn');
			
			var d	='<h5 class=\'text-center text-blue-2 padding-5\'>'+message+'</h5>';
			d	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceedTransfer()"/></div>';
			
			$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
			$(errMsgDiv).html(d);
			$(errMsgDiv).show();
			$('#btnNo').focus();
			showPopUpMsg(errMsgDiv);
		}
	 function proceedTransfer() {
			
			//	window.location.href = "AccountReceiptEntry.html";
				//alert("hi")
				
				var theForm = '#frmAccountRecieptReport';
				var requestData = __serializeForm(theForm);
				var url = "AccountVoucherEntry.html?VoucherReportForm"
					var returnData = __doAjaxRequest(url, 'post', requestData, false);
				$(errMsgDiv).show('false');
				   $.fancybox.close();
				   $('#widget').html(returnData);
					//$('.widget').html(returnData);
				
	 } 
	 
	 function findDepositTypeWiseAccountHeads(obj) {

			$('.error-div').hide();
			var errorList = [];

			if (errorList.length == 0) {
				
				var divName = ".content";
				
				var formName = findClosestElementId(obj, 'form');

				var theForm = '#' + formName;

				var requestData = __serializeForm(theForm);

				var url = "AccountDeposit.html?getDepositTypeWiseAccountHeads";

				var response = __doAjaxRequest(url, 'post', requestData, false);

				//$(divName).removeClass('ajaxloader');
				 $('#widget').html(response);
				 //$('.content').html(response);
			}
		};
		function exportTemplate() {

			
			var $link = $(this);
			/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
			// var functionId = 1;
			var url = "AccountDeposit.html?exportTemplateData";
			var requestData = "";
			var returnData = __doAjaxRequest(url, 'post', requestData, false);

			$('.content').html(returnData);

			prepareDateTag();
			return false;
		}
		function closeOutErrorBox() {
			$('#errorDivSec').hide();
		}

		
		