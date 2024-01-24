
var dsgid = '';
$(function() {
	
	$("#grid").jqGrid(
			{
				url : "AccountVoucherEntry.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : ['',getLocalMessage('account.voucher.number'),getLocalMessage('account.voucher.date'),getLocalMessage('accounts.posting.date'),getLocalMessage('voucher.template.entry.master.vouchertype'),getLocalMessage('accounts.trans.refno'),getLocalMessage('accounts.receipt.entryType'),getLocalMessage('accounts.receipt.narration'),"", "", getLocalMessage('bill.action')],//"Edit","View"
				colModel : [
				             {name : "vouId",width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}, hidden:true}, 
				             {name : "vouNo",width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }}, 
				             {name : "vouDate",width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "vouPostingDate",width : 20,sortable : true, searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }}, 
				             {name : "voucherDesc",width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "vouReferenceNo",width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "entryFrom",width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "narration",width : 80,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] }},
				             {name : "authoFlg",width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}, hidden:true},
				             {name : "urlIdentifyFlag",width : 20,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}, hidden:true},
				             {name: 'vouId', index: 'vouId', width:40 , align: 'center !important',formatter:addLink,search :false}
				            ],
				pager : "#pagered",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "vouId",
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
				caption : getLocalMessage('voucher.List'),
				
				formatter: function (v) {
				    // uses "c" for currency formatter and "n" for numbers
				    return Globalize.format(Number(v), "c");
				},
				unformat: function (v) {
				    return Globalize.parseFloat(v);
				}
				
			});
	 jQuery("#grid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	// $("#pagered_left").css("width", "");
});



function addLink(cellvalue, options, rowdata) 
{
   return "<a class='btn btn-blue-3 btn-sm viewVoucherEntryMasterClass' title='View'value='"+rowdata.vouId+"' vouId='"+rowdata.vouId+"' ><i class='fa fa-building-o'></i></a> " +
   		 "<a class='btn btn-warning btn-sm editVoucherEntryMasterClass' title='Edit'value='"+rowdata.vouId+"' vouId='"+rowdata.vouId+"' ><i class='fa fa-pencil'></i></a> " +
   		"<a class='btn btn-blue-3 btn-sm printVoucherEntryMasterClass' title='Print'value='"+rowdata.vouId+"' vouId='"+rowdata.vouId+"' ><i class='fa fa-print'></i></a> ";
}

$(function() {	

	/*$(document).on('click', '.createData', function() {
	
		var $link = $(this);
		var spId = $link.closest('tr').find('td:eq(0)').text();
		var prBudgetCodeid = 1;
		var url = "AccountBudgetCode.html?form";
		var requestData = "vouId=" + vouId  + "&MODE_DATA=" + "ADD";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		var divName = ".content";
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		
		return false;		
	});*/			
	
	$(document).on('click', '.editVoucherEntryMasterClass', function() {
	
		var errorList = [];
		var $link = $(this);
		/* var paAdjid = $link.closest('tr').find('td:eq(0)').text(); */
		var vouId = $link.closest('tr').find('td:eq(0)').text(); 
		var authStatus = $link.closest('tr').find('td:eq(8)').text();
		var url = "AccountVoucherEntry.html?update";
		var requestData = "vouId=" + vouId ;
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		if(authStatus=="N") {
			$('.content').html(returnData);
			// finding SLI Date from SLI Prefix
			var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
			
			var disableBeforeDate = new Date(response[0], response[1], response[2]);
			var date = new Date();
			var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
			$("#transactionAuthDateId").datepicker({
				dateFormat: 'dd/mm/yy',		
				changeMonth: true,
				changeYear: true,
				minDate : disableBeforeDate,
				maxDate : today,
				onSelect: function(date) {
					validateAuthorizationDate(date);
				}
			});
			
		} else {
			if(authStatus=="Y")
			 errorList.push(getLocalMessage("accounts.voucher.edit"));
			if(authStatus=="D")
			errorList.push(getLocalMessage("accounts.voucher.rejected.edit"));
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
		
	});
	
	$(document)
	.on(
			'click',
			'.viewVoucherEntryMasterClass',
			function() {
				var $link = $(this);
				var vouId = $link.closest('tr').find('td:eq(0)').text();
				var url = "AccountVoucherEntry.html?formForView";
				var requestData = "vouId=" + vouId ;
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				var divName = '.content';
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				/*$('select').attr("disabled", true);
				$('input[type=text]').attr("disabled", true);
				$('input[type="text"], textarea').attr("disabled", true);
				$('select').prop('disabled', true).trigger("chosen:updated");*/
				return false;
			});
	$(document)
	.on(
			'click',
			'.printVoucherEntryMasterClass',
			function() {
				
				var $link = $(this);
				var vouId = $link.closest('tr').find('td:eq(0)').text();
				var urlMode =$("#mode").val() 
				var url = "AccountVoucherEntry.html?formPrint";
				var requestData = "vouId=" + vouId + "&urlIdentifyFlag=" + urlMode;
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				var divName = '.widget';
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				/*$('select').attr("disabled", true);
				$('input[type=text]').attr("disabled", true);
				$('input[type="text"], textarea').attr("disabled", true);
				$('select').prop('disabled', true).trigger("chosen:updated");*/
				return false;
			});



});


$(document).ready(function() {
	$( document ).ajaxComplete(function() {

	 	 if($("#authStatus").val()!='N')
	 	 {
	 	 $('[id^="frmAccountVoucherEntryD"]').find('a[href]').removeAttr("onclick");
	 	 }

		});	
	$( ".chosen-select-no-results").focus(function() {
		
		var config = {
			      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			    }
			    for (var selector in config) {
			      $(selector).chosen(config[selector]);
			    }
			    $('.required-chosen').next().addClass('mandColorClass');
			    $('.required-control').next().children().addClass('mandColorClass');
		
		}); 
	 
	 
	
	$(".datepicker").datepicker({
		 dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true
	});
	$(".datepicker").datepicker('setDate', new Date()); 
	var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
	var disableBeforeDate = new Date(response[0], response[1], response[2]);
	var date = new Date();
	var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
	$("#fromDate").datepicker({
		dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true,
		minDate : disableBeforeDate,
		maxDate : today
	});
	$("#toDate").datepicker({
		dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true,
		minDate : disableBeforeDate,
		maxDate : today
	});
	
	
	var dateFields = $('.dateClass');
	dateFields.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
	
	
});


  function removeRow(cnt)
 {
	 var deletedClassCnt=$('.deletedThis').length;
	 var allCount=$('#tbl tr').length;
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
	        	  //var errMsg = '<ul><li>Please Enter the All Mandatory fields</li></ul>';
	      		  //$('#errorId').html(errMsg);
	      			//$('.error-div').show();
	      /*   showErrormsgboxTitle("Please Enter the All Mandatory fields"); */
	          } 
		 }
	 else
		 {
		 finalcnt=($('#tbl tr').length)-1;
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
	        	  //var errMsg = '<ul><li>Please Enter the All Mandatory fields</li></ul>';
	      		  //$('#errorId').html(errMsg);
	      			//$('.error-div').show();
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
		    $(this).find("select:eq(0)").attr("onchange", "setStatusClass(this,"+ (i) +")");
		    	 
		    	 
			
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
	    $(content).find("select:eq(0)").attr("onchange", "setStatusClass(this,"+ (i) +")");
	    
  }
  
$(".warning-div ul")
.each(
		function() {
			var lines = $(this).html().split("<br>");
			$(this)
					.html(
							'<li>'
									+ lines
											.join("</li><li><i class='fa fa-exclamation-circle'></i>&nbsp;")
									+ '</li>');
		});
$('html,body').animate({
scrollTop : 0
}, 'slow');


function approve(element) {
	
	var authFlag =  $("#approved").val();
	$("#authFlag").val(authFlag);
	save(element);
}

function disApprove(element) {
	
	var authFlag =  $("#disapproved").val();
	$("#authFlag").val(authFlag);
	save(element);
}

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
		  if(transactionAuthDate!=null && transactionAuthDate!='')
		  {
		  errorList = validatedate(errorList,'transactionAuthDateId');
		  if (errorList.length == 0) {
				var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
				if(response == "Y"){
						errorList.push("SLI Prefix is not configured");
				}else{
				var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
				var date = new Date($("#transactionAuthDateId").val().replace(pattern,'$3-$2-$1'));
				var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
				if (date < sliDate) {
					errorList.push("Transaction authorization date can not be less than SLI date");
				  }
				}
			  }
		  }
	  } else {
		  var transactionDate= $.trim($('#transactionDateId').val());
		  if(transactionDate!=null)
			  {
			  errorList = validatedate(errorList,'transactionDateId');
			  if (errorList.length == 0) {
					var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
					if(response == "Y"){
 						errorList.push("SLI Prefix is not configured");
 					}else{
					var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
					var date = new Date($("#transactionDateId").val().replace(pattern,'$3-$2-$1'));
					var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
					if (date < sliDate) {
						errorList.push("Transaction date can not be less than SLI date");
					  }
					}
				  }
			  }
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
	    $('.accountClass').each(function(i) {
	    	 var functionCode=$.trim($("#function"+i).val());	
		   	 var secondary = $.trim($("#secondary"+i).val());
		     var amount= $.trim($("#amount"+i).val());
		     var type=$.trim($("#type"+i).val());
		     var level=i+1;
		   
		     if(!typeB)
				 if(type==0 ||type=="")
		   		 {
		   		    errorList.push(getLocalMessage("account.select.dr.cr"));
		   		 typeB=true;
		   		 }
		     if(!secondaryB)
		   	 if(secondary==0 || secondary=='0')
	   		 {
	   		    errorList.push(getLocalMessage("account.select.acchead"));
	   		 secondaryB=true;
	   		 }
		     if(!amountB)
		   	 if(amount==0 ||amount=="")
	   		 {
	   		    errorList.push(getLocalMessage("account.enter.amt"));
	   		 amountB=true;
	   		 }
		     
	        });
	    
	    $('.debitClass').each(function(){
			  sumDebit += parseFloat(this.value);
		  });
		  $('.creditClass').each(function(){
			  sumCredit += parseFloat(this.value);
		  });
		  sumDebit= sumDebit.toFixed(2);
		  sumCredit=sumCredit.toFixed(2);
		    if(sumDebit!=sumCredit)
			 {
		    	 errorList.push("Sum of debit and credit should be same");
		   	
			 }
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
	  
	  showConfirmBoxSave();
  }
  
  function showConfirmBoxSave(){
		//debugger;
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var saveMsg=getLocalMessage("account.btn.save.msg");
		var cls =getLocalMessage("account.btn.save.yes");
		var no=getLocalMessage("account.btn.save.no");
		
		 message	+='<h4 class=\"text-center text-blue-2\"> '+saveMsg+' </h4>';
		 message	+='<div class=\'text-center padding-bottom-15\'>'+	
		'<input type=\'button\' value=\''+cls+'\' tabindex=\'0\' id=\'btnNo\' class=\'btn btn-blue-2 autofocus\'    '+ 
		' onclick="saveDataAndShowSuccessMsg()"/>  '+ 
		'<input type=\'button\' value=\''+no+'\' tabindex=\'0\' id=\'btnNo\' class=\'btn btn-blue-2 autofocus\'    '+ 
		' onclick="closeConfirmBoxForm()"/>'+ 
		'</div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutCloseaccount(errMsgDiv);
		
	}

  function saveDataAndShowSuccessMsg(){
	  
	  /* var	formName =	findClosestElementId(element, 'form');
		var theForm	=	'#'+formName;
	    requestData = __serializeForm(theForm);
	 return doFormActionForSaveForm(element,"saved successfully","saveJournalForm",requestData,redirectURL,".widget-content");*/
	  
	  	var transactionDateId = $("#transactionDateId").val();
	  
	    //var formName = findClosestElementId(element, 'form');
		//var theForm = '#' + formName;
		//var requestData = __serializeForm(theForm);
		//var url = $(theForm).attr('action');
		
		var url = "AccountVoucherEntry.html";
		var requestData = $('#frmAccountJournalVoucherEntry').serialize();
		
	//	var response = __doAjaxRequestForSave(url, 'post', requestData, false,'', element);
		var ajaxResponse = __doAjaxRequest(url+'?saveJournalForm', 'POST', requestData, false,'','');
		
		if ($.isPlainObject(ajaxResponse))
		{
			var message = ajaxResponse.command.message;
			//alert("message",message);
			displayMessageOnSubmit(ajaxResponse,message, url);
		}
		else{
			var divName = '.content';
			$(divName).html(ajaxResponse);
			$("#transactionDateId").val(transactionDateId);
		}
	//	var response = __doAjaxRequest('ContraVoucherEntry.html?getSacHeadCodes','POST',postData,false,'json');
	//	alert("ajaxResponse"+ajaxResponse)
		
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
		
		var drCrTypeCode=$("#type"+amountCount+" option:selected").attr("code");
		if (drCrTypeCode != null && drCrTypeCode != "") {
			
			if(drCrTypeCode == "DR"){
				var sumOfDebitAmt = 0;
				for (var i = 0; i < amountCount+1; i++) {
					var drCrTypeCode=$("#type"+i+" option:selected").attr("code");
					if(drCrTypeCode == "DR"){
						var amount = $("#amount"+i).val();
						sumOfDebitAmt += parseFloat(amount);
					}
				}
				$('#drTotalAmount').val(parseFloat(sumOfDebitAmt.toFixed(2)));
				getAmountFormatInStatic('drTotalAmount');
			}
			if(drCrTypeCode == "CR"){
				var sumOfCreditAmt = 0;
				for (var i = 0; i < amountCount+1; i++) {
					var drCrTypeCode=$("#type"+i+" option:selected").attr("code");
					if(drCrTypeCode == "CR"){
						var amount = $("#amount"+i).val();
						sumOfCreditAmt += parseFloat(amount);
					}
				}
				$('#crTotalAmount').val(parseFloat(sumOfCreditAmt.toFixed(2)));
				getAmountFormatInStatic('crTotalAmount');
			}
		}
		//var budgetType=$("#cpdBugtypeId option:selected").attr("code");
		
	    }
		
  }
  function searchData(element)
  {
	  
  	if(checkValidation())
  		{
  		
  		var voucherType = $("#voucherType").val().trim();
  		var mode = $("#mode").val();
		var fromDate =  $("#fromDate").val();
		var toDate =  $("#toDate").val();
		var dateType = $("input[name='dateType']:checked").val();
		var authStatus = $("#authStatus").val();
		var amount = $("#amount").val();
		var refNo = $("#refNo").val();
		
		 var url = "AccountVoucherEntry.html?searchVoucher";
  		
		var requestData = {
				"voucherType" : voucherType,
				"fromDate" : fromDate,
				"toDate" : toDate,
				"dateType" : dateType,
				"authStatus" : authStatus,
				"amount" : amount,
				"refNo" : refNo,
				"urlIdentifyFlag" : mode,
			};
		
      //$('[id^="frmAccountJournalVoucherEntryD"]').find('a[href]').removeAttr("onclick");

		var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	
		if (result != null && result != "") {
			
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
			
			
		} else {
			var errorList = [];
			
			errorList.push(getLocalMessage("account.norecord.criteria"));
			
			if(errorList.length>0)
			{
		   var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li>' + errorList[index] + '</li>';
			});
	      errMsg += '</ul>';
	      $('#errorId').html(errMsg);
			$('#errorDivId').show();
			return false;
			}
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		}
		
 /* $.ajax({
  	url : url,
  	data : requestData,
  	type : 'POST',
  	success : function(response) {
  	 $("#gridAccountVoucherEntry").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
  	$('[id^="frmAccountVoucherEntryD"]').find('a[href]').removeAttr("onclick");
  	alert(response);
  	if(response==0)
  		 {
  		 var errMsg = '<ul><li>Data Not Found For Selected Criteria</li></ul>';
  		  $('#errorId').html(errMsg);
  			$('.error-div').show();
  		 }
  	 else
  		 {
  		 $('.error-div').hide();
  		 //$('[id^="frmAccountJournalVoucherEntryD"]').find('a[href]').removeAttr("onclick");
  		}
  	},
  	error : function(xhr, ajaxOptions, thrownError) {
  		var errorList = [];
  		errorList.push(getLocalMessage("admin.login.internal.server.error"));
  		showError(errorList);
  	}
  });*/	
  	 }
  
  	};
  	
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
function setStatusClass(obj, index)
{
	
	 //var i=$(obj).attr("id").slice(-1);
	var i=index;
	 $('#secondary'+i).empty(); 
	$('#secondary'+i).chosen('destroy'); 
	
	 /* $('#secondary'+i).removeClass("chosen-select-no-results");
	  $('#secondary'+i).val('').trigger('chosen:updated');*/
	
	if($("#voucherType").val()!='0') {
	var transType=$(obj).find('option:selected').attr('code');
	var data={"voucherType" : $("#voucherType option:selected").attr("code")   ,"tranType" : transType};
	var result=__doAjaxRequest("AccountVoucherEntry.html?getBudgetCode", "POST", data, false);
//	$('#secondary'+i).find('option').remove();
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
	  for(var j=0;j<$('#tbl tr').length;j++)
		   {
		   var bankId=$("#secondary"+j).val();
		   if(bankId!=0)
			   {
			$("#secondary"+i+" option[value="+bankId+"]").prop('disabled',true);
			   }
		   }
	  $('#secondary'+i).val('').trigger('chosen:updated')
	 // $('select[id^=secondary]').val('').trigger('chosen:updated');
//	 $('select[id^=secondary]').trigger("liszt:updated");
//	 $('select[id^=secondary]').chosen().change( function() {
//	  var selectedValue = $(this).find('option:selected').val();
//	  $(this).parent().find('option[value="'+ selectedValue +'"]:not(:selected)')
//	        .prop("disabled", function( i, val ) {
//	              return !val;
//	            });
//	});

	
	
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
	
	
	
	
 	
	
	
    


function addForm()
{
	var result=__doAjaxRequest("AccountVoucherEntry.html?addForm", "POST", '', false)
	$(".widget-content").html(result);
	$(".widget-content").show();
	// finding SLI Date from SLI Prefix
	var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
	var disableBeforeDate = new Date(response[0], response[1], response[2]);
	var date = new Date();
	var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
	$("#transactionDateId").datepicker({
		dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true,
		minDate : disableBeforeDate,
		maxDate : today
	});
}
function checkValidation()
{
	var errorList=[];
	
	/*if($("input[name='dateType']:checked").val()!=undefined)
	{*/
		 if($("#fromDate").val()=="" && $("#toDate").val()=="")
			 {
			 errorList.push("Please Select From Date and To Date");
			 }
		 else if($("#fromDate").val()!="" && $("#toDate").val()=="")
			 {
			 errorList.push("Please Select To Date"); 
			 }
		 else if($("#fromDate").val()=="" && $("#toDate").val()!="")
			 {
			 errorList.push("Please Select From Date"); 
			 }
		  if($("#authStatus").val()==0)
			  {
				 errorList.push(getLocalMessage("account.select.authorisation.status")); 
			  }
         if($("#fromDate").val()!=null)
        	 {
        	 errorList = validatedate(errorList,'fromDate');
        	 }
         if($("#toDate").val()!=null)
    	 {
    	 errorList = validatedate(errorList,'toDate');
    	 }
		  var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		  var eDate = new Date($("#fromDate").val().replace(pattern,'$3-$2-$1'));
		  var sDate = new Date($("#toDate").val().replace(pattern,'$3-$2-$1'));
		  if (eDate > sDate) {
			  errorList.push("From date Should be less than to Date");
		        
		    }
	/*}*/
/*	else
		{
		 errorList.push("Please Select Date Type");
		}*/
	if(errorList.length>0)
		{
	 var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li>' + errorList[index] + '</li>';
		});
     errMsg += '</ul>';
      $('#errorId').html(errMsg);
		$('#errorDivId').show();
		return false;
		}
	return true;
}
function resetGridForm(mode)
{
	if(mode!='AUTH')
		{
	window.location.href = 'AccountVoucherEntry.html'; 
		}
	else
		{
		window.location.href = 'AccountVoucherAuthorisation.html'; 
		}
}
function resetVoucherEntry()
{
	$('.error-div').hide();
	$('select[id^=secondary]').chosen('destroy'); 
	$('select[id^=secondary]').empty(); 
	 //$('select[id^=secondary]').val('').trigger('chosen:updated');
	 //$('select[id^=secondary]').removeClass('chosen-select-no-results');
	 
	 
}
function changeList(i)
{
	var bankId=$("#secondary"+i).val();
	var exist=false;
	for(var j=0;j<$('#tbl tr').length;j++)
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

function displayMessageOnSubmit(successMsg,message,redirectURL) {
	
	var	errMsgDiv		=	'.msg-dialog-box';
	var cls = getLocalMessage("account.billEntry.savedBtnProceed");
	
	var d	='<h5 class=\'text-center text-blue-2 padding-5\'>'+message+'</h5>';
	d	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function redirectToHomePage (redirectURL) {
	$.fancybox.close();
	window.location.href=redirectURL;
}

function showPopUpMsg(childDialog) {
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

function proceed() {
	
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

function validateAuthorizationDate( selectedDate) {
	
	
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
}


function exportTemplate() {

	
	var $link = $(this);
	/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
	// var functionId = 1;
	var url = "AccountVoucherEntry.html?exportTemplateData";
	var requestData = "";
	var returnData = __doAjaxRequest(url, 'post', requestData, false);

	$('.content').html(returnData);

	prepareDateTag();
	return false;
}
function closeOutErrorBox() {
	$('#errorDivSec').hide();
}

function getVoucherSubType(obj) {
	let vType =$("#voucherType option:selected").attr("code")
	var requestData = {
		"voucherType" : vType
	};
	$('#voucherSubType' ).empty().append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));

	var ajaxResponse = __doAjaxRequest('AccountVoucherEntry.html?getVoucherSubType', 'POST',
			requestData, false, 'html');
	var prePopulate = JSON.parse(ajaxResponse);

	$.each(prePopulate, function(index, value) {
		$('#voucherSubType').append(
				$("<option></option>").attr("value", value[0]).attr("code", value[1]).text(
						(value[2])));
	});
}
