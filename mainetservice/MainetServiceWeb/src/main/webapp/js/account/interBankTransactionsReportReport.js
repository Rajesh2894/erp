$(function() {
		var response = __doAjaxRequest('AccountReceiptEntry.html?SLIDate','GET', {}, false, 'json');
		var disableBeforeDate = new Date(response[0], response[1], response[2]);
		var date = new Date();
		var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
		$("#fromDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			minDate : disableBeforeDate,
			maxDate : today
		});
		$("#toDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			minDate : disableBeforeDate,
			maxDate : today
		});
		$('#fromDateId').change(function() {
			//alert();
			var check = $(this).val();
			if (check == '') {
				$(this).parent().switchClass("has-success", "has-error");
			} else {
				$(this).parent().switchClass("has-error", "has-success");
			}
		});
		$('#toDateId').change(function() {
			//alert();
			var check = $(this).val();
			if (check == '') {
				$(this).parent().switchClass("has-success", "has-error");
			} else {
				$(this).parent().switchClass("has-error", "has-success");
			}
		});
		$("#fromDateId").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});
		$("#toDateId").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});
	});

function viewReport(formUrl, actionParam) {
	  
	  var errorList = [];
	  errorList = validateForm(errorList);
	  var fromDateId = $('#fromDateId').val();
	  var toDateId = $('#toDateId').val();
	  if (errorList.length > 0) {
			displayErrorsPage(errorList);
		} else if (errorList.length == 0) {
	      var data = {
				"fromDateId" : fromDateId,
				"toDateId" : toDateId,
			};
		    var divName = '.content-page';
			var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,data,'html');
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
}
}
function validateForm(errorList) {
	    
		var errorList=[];
		var dateFrom='01/04/';
		var dateTo='31/03/';
		var finciStartDate;    
		var finciToDate;     
		var finciToDate1;
	    if($("#fromDateId").val()=="" && $("#toDateId").val()=="")
		 {
		 
		 errorList.push(getLocalMessage('please.Select.From.Date.and.To.Date'));
		 }
	     else if($("#fromDateId").val()!="" && $("#toDateId").val()=="")
		 {
		 errorList.push("Please Select To Date"); 
		 }
	     else if($("#fromDateId").val()=="" && $("#toDateId").val()!="")
		 {
		  errorList.push("Please Select From Date"); 
		 }
	  var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	  var eDate = new Date($("#fromDateId").val().replace(pattern,'$3-$2-$1'));
	  var currentFinancialYearFromDate=eDate.getFullYear()+1;
	  var sDate = new Date($("#toDateId").val().replace(pattern,'$3-$2-$1'));
	      finciToDate=sDate.getFullYear()+1;
	      finciToDate1=sDate.getFullYear();
	      finciStartDate=finciToDate1-1;
	      if (eDate > sDate) {
		  errorList.push("To Date can not be less than From Date");
	      }
	     dateFrom=new Date((dateFrom+finciStartDate).replace(pattern,'$3-$2-$1'));
	     dateTo=new Date((dateTo+finciToDate).replace(pattern,'$3-$2-$1'));
	     var  currentdateTo=new Date(("31/03/"+currentFinancialYearFromDate).replace(pattern,'$3-$2-$1'));
	  if((eDate<dateFrom) || ((sDate>currentdateTo)||(sDate>dateTo))){
		  errorList.push("From Date and To Date should be within the financial year");
	  }
	return errorList;
}
function displayErrorsPage(errorList) {
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'+ errorList[index] + '</li>';
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
function printdiv(printpage) {
	
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}
function back() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'interBankTransactionsReport.html');
	$("#postMethodForm").submit();
}


function interBankReset() {
	$("#postMethodForm").prop('action', '');
	$("#fromDateId, #toDateId").val("").trigger("chosen:updated"); //Defect #164655
	$('.error-div').hide();
	prepareTags();
}
