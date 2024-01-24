function savepostAcentryReceipt(element) {
	var myerrorList = [];

	var tranDate = $.trim($("#tranDateTemp").val());
    if (tranDate == 0 || tranDate == "")
		myerrorList.push(getLocalMessage('account.transaction.entryDate.not.empty'));

    
    var empId = $.trim($("#empId").val());
    if (empId == 0 || empId == "")
		myerrorList.push(getLocalMessage('account.emp.not.empty'));
    
    var modeCpdId = $.trim($("#modeCpdId").val());
    if (modeCpdId == 0 || modeCpdId == "")
		myerrorList.push(getLocalMessage('account.receiptMode.not.empty'));
    
    var accountentrytypeCpdId = $.trim($("#accountentrytypeCpdId").val());               
    if (accountentrytypeCpdId == 0 || accountentrytypeCpdId == "")
		myerrorList.push(getLocalMessage('account.acc.entryType.not.empty'));
  
    
    var postingtypeCpdId = $.trim($("#postingtypeCpdId").val());
    if (postingtypeCpdId == 0 || postingtypeCpdId == "")
		myerrorList.push(getLocalMessage('account.postingType.not.empty'));
     
    var postingDate = $.trim($("#postingDateTemp").val());
    if (postingDate == 0 || postingDate == "")
		myerrorList.push(getLocalMessage('account.postingDate.not.empty'));
    
    var postingRemark = $.trim($("#postingRemark").val());  
    if (postingRemark == 0 || postingRemark == "")
		myerrorList.push(getLocalMessage('account.postingRemark.not.empty'));

    if (myerrorList.length > 0) {

		var errMsg = '<ul>';
		$.each(myerrorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ myerrorList[index] + '</li>';
		});

		errMsg += '</ul>';
		$("#errorId").html(errMsg);
		$('#errorDivId').show();
		$("#errorDivId").removeClass('hide');
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

		errorList = [];
		return false;
	}
    else {
    	
		var formName = findClosestElementId(element, 'form');
		
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var url = $(theForm).attr('action');
alert(url);
		var response = __doAjaxRequestForSave(url, 'post', requestData, false,'', element);
		
		if ($.isPlainObject(response)) {
			
			showConfirmBoxVoucherPosting('Saved successfully  ');
		} else {
			var divName = '.widget';
			$(divName).removeClass('ajaxloader');
			$(divName).html(response);
			savepostAcentryReceipt();
			return false;

		}
	}




}

function searchPostingReceiptData(){

			$('.error-div').hide();
			var errorList = [];
			if (errorList.length == 0) 
			{
				
		    var tranDateTemp=$("#tranDateTemp").val();
	    	var empId = $('#empId').val();
	    	var modeCpdId = $('#modeCpdId').val();
	    	var fieldId = $('#fieldId').val();
	    	alert(fieldId);
	        var url ="AccountVoucherPostingform.html?getreceiptjqGridsearch";
	    	var requestData = "tranDateTemp=" + tranDateTemp + "&empId=" + empId+ "&modeCpdId=" + modeCpdId+ "&fieldId=" + fieldId;
			 $.ajax({				 
					url : url,data : requestData,datatype: "json",	type : 'POST',
					success : function(response) {$("#gridReceipt").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
					},
					error : function(xhr, ajaxOptions, thrownError) 
					{
						var errorList = [];
						errorList.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});
			} else {
				var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
				$.each(errorList, function(index) {
					errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
				});
				errMsg += '</ul>';
			$('.error-div').html(errMsg);	
			$('.error-div').show();
			 return false;
			}
		};	
	

		var ptbId = '';
		$(function() {
			$("#gridReceipt").jqGrid(
					{
						url : "AccountVoucherPostingform.html?getGridData",
						datatype : "json",
						mtype : "POST",
						colNames : [ getLocalMessage('account.receipt.id'), getLocalMessage('cheque.dd.receipt.number'),getLocalMessage('receipt.register.receiptdate'),getLocalMessage('accounts.receipt.name'),getLocalMessage('budget.reappropriation.master.departmenttype'),getLocalMessage('bill.amount'), getLocalMessage('master.view')],
						colModel :
							
					 [ 
						    {name : "accountVoucherPostDetailBean.rmRcptid",index: "accountVoucherPostDetailBean.rmRcptid" ,width :0,hidden : true,sortable : true,searchoptions : {"sopt" : [ "bw", "eq" ]}}, 
							{name : "accountVoucherPostDetailBean.rmRcptno",index: "accountVoucherPostDetailBean.rmRcptno" ,width : 20,sortable : true,searchoptions : {"sopt" : [ "bw", "eq" ]}}, 
			                {name : "accountVoucherPostDetailBean.rmDateTemp",index: "accountVoucherPostDetailBean.rmDateTemp" ,width : 25,sortable : false,searchoptions: { "sopt": [ "eq"] }}, 
				            {name : "accountVoucherPostDetailBean.payeeName",index: "accountVoucherPostDetailBean.payeeName" ,width : 60,sortable : false,searchoptions: { "sopt": [ "eq"] }},
				            {name : "accountVoucherPostDetailBean.department",index: "accountVoucherPostDetailBean.department" ,width : 35,sortable : false,searchoptions: { "sopt": [ "eq"] }},
				            {name : "accountVoucherPostDetailBean.formattedCurrency",index:"accountVoucherPostDetailBean.formattedCurrency", class :"text-right padding-bottom-10",width:35 },
				            {name : "accountVoucherPostDetailBean.rmRcptid",index : "accountVoucherPostDetailBean.rmRcptid",width : 25,align : 'center',formatter : returnViewUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search : false} 
				            ],
						pager : "#pagered",
						rowNum : 30,
						rowList : [ 5, 10, 20, 30 ],
						sortname : "rmRcptno",
						sortorder : "desc",
						height : 'auto',
						viewrecords : true,
						gridview : true,
						loadonce : true,
						jsonReader : {root : "rows",page : "page",total : "total",records : "records",
						repeatitems : false,},
						autoencode : true,
						caption : "Receipt"
					});
			jQuery("#gridReceipt").jqGrid('navGrid', '#pagered', {edit : false,add : false,del : false,search : true,	refresh : false});
			$("#pagered_left").css("width", "");
		});
		
		function returnViewUrl(cellValue, options, rowdata, action) {

			return "<a href='#'  return false; class='viewReceiptClass'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
		}
		
		$(function() {
	
			$(document).on('click', '.viewReceiptClass', function() {
				var $link = $(this);
				var ptbId = $link.closest('tr').find('td:eq(0)').text();
				var url = "AccountReceiptEntry.html?viewMode";
				var requestData = "rmRcptid=" + ptbId + "&MODE1=" + "View" + "&postFlag="  + "P";
				var returnData = __doAjaxRequest(url, 'post', requestData, false);
				var divName = '.content';
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				return false;
			});

		});
		
		function showConfirmBoxVoucherPosting(msg) {
			var errMsgDiv = '.msg-dialog-box';
			var message = '';
			var cls = 'Proceed';
			message += "<br><div class=\"form-group\"><p>  "+msg+"  </p>";
			message += '<p style=\'text-align:center;margin: 5px;\'>'
					+ '<br/><input type=\'button\' value=\'' + cls
					+ '\'  id=\'btnNo\' class=\'btn btn-success \'    '
					+ ' onclick="proceed()"/>' + '</p></div>';

			$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
			$(errMsgDiv).html(message);
			$(errMsgDiv).show();
			$('#btnNo').focus();
			showModalBoxWithoutClose(errMsgDiv);
		}

		function proceed() {

			window.location.href = "javascript:openRelatedForm('AccountVoucherPostingform.html');";
		}		