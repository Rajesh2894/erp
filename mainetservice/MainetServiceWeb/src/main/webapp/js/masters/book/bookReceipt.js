
function resetReceipBookForm()
{

	$("#ReceiptBookId").prop('action', '');
	$("#ReceiptBookId").prop('action','bookreceipt.html');
	$("#ReceiptBookId").submit();

}

function changeTotalBookReceiptNos(e)
{
	
	 var from = $("#receiptNoFrom").val();
	 var to= $("#receiptNoTo").val();
	
	 if((from != null && from != undefined && from !='' && !isNaN(from))
				&& (to != null && to != undefined && to !='' &&  !isNaN(to)))
	 {
	 var total=(parseInt(to)-parseInt(from));
	 
	 $("#totalbookReceiptNo").val(parseInt(total));
	
	 }

}

function addFormData(form) {

	var url = "bookreceipt.html?addRecieptData";
	var ajaxResponse = __doAjaxRequest(url, 'post', {}, false);

	var divName = formDivName;
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	
}		
	

function closeErrBox() {
	$('.error-div').hide();
}

function showErr(errorList) {
	$(".warning-div").removeClass('hide');
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);

	$("html, body").animate({
		scrollTop : 0
	}, "slow");
}

 function saveBookForm(form)
 {
	    var errorList = [];

	
		if ($("#receiptBookNo").val() == "")
		{
			errorList.push(getLocalMessage("book.receiptBookNo.required"));
		
		}
		if ($("#receiptNoFrom").val() == "")
		{
			errorList.push(getLocalMessage("book.receiptNoFrom.required"));
		
		}

		if ($("#receiptNoTo").val() == "")
		{
			errorList.push(getLocalMessage("book.receiptNoTo.required"));
		
		}
		
		if ($("#fayearId").val()=="")
		{
			errorList.push(getLocalMessage("book.fayear.required"));
		
		}
		if ($("#employeeId").val()=="")
		{
			errorList.push(getLocalMessage("book.employee.required"));
		
		}
		
		
		
		if (errorList.length > 0)
		{
			showErr(errorList);
			$("#errorDiv").show();
			
		}
		else
		{
		$("#errorDiv").hide();	
	
 		var theForm	= '#RecieptForm';
 		var requestData = {};
 		requestData = __serializeForm(theForm);

 		var URL = 'bookreceipt.html?create';
 		
 		
 		
 		 return saveOrUpdateForm(form,"book Receipt done successfully!",'bookreceipt.html', 'create');
 
 		
     }
 }

 Url='bookreceipt.html';
 $(document).ready(function(){
		
 	$("#ReceiptBookGrid").jqGrid(
 			{   
 				url : Url+"?ReceiptBookData",
 				datatype : "json",
 				mtype : "GET",
 				colNames : [ '',getLocalMessage("book.receiptbookNo"),getLocalMessage("book.receipbookNoFrom"), getLocalMessage("book.receiptbookNoTo"),  getLocalMessage("book.action")],
 				colModel : [ {name : "rbId",width : 5,  hidden :  true},
 				             {name : "bookreceiptNo",width : 30,  sortable :  true},
 				             {name : "bookreceiptNoFrom",width : 20,search :true,sortable :  true},
 				             {name : "bookreceiptNoTo",width : 20,search :true,sortable :  true}, 
 				          
 				             { name: 'rbId', index: 'rbId', 
 			            	      align: 'center !important', sortable: false, 
 			            	      width : 40,formatter:actionFormatter,search : false
 			                 }
 				            
 				            ],
 				pager : "#ReceiptBookPager",
 				rowNum : 50,
 				rowList : [ 10, 20, 50, 100 ],
 				sortname : "code",
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
 				caption : getLocalMessage("book.caption")/*'Receipt Book Leaf'*/
 			});
 		   $("#ReceiptBookGrid").jqGrid('navGrid','#ReceiptBookPager',{edit:false,add:false,del:false,search:true,refresh:true,closeAfterSearch:true}); 
 	       $("#ReceiptBookPager_left").css("width", "");

 	 
 });	 

 function actionFormatter(cellvalue, options, rowObject)
 {
	
		return  "<a class='btn btn-blue-3 btn-sm' title='View Receipt' onclick=\" ReceiptBook('"+rowObject.rbId+"','V')\"><i class='fa fa-eye' aria-hidden='true'></i></a> " +
		   "<a class='btn btn-warning btn-sm' title='Edit Receipt' onclick=\" ReceiptBook('"+rowObject.rbId+"','U')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> ";
		
	}
 
 
 function ReceiptBook(rbId,flag){

	    var requestData = "rbId="+rbId+"&flag="+flag;
		var url = "bookreceipt.html?editReceiptBookForm";
		var ajaxResponse = __doAjaxRequest(url, 'post',requestData , false);

		var divName = formDivName;
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();

}

 function back() 
 {

		$("#RecieptForm").prop('action', '');
		$("#RecieptForm").prop('action','bookreceipt.html');
		$("#RecieptForm").submit();
	}

 function searchReceiptBookData()
 {
 	
	
 	var empId=$("#employeeId").val();
 	var fayearId=$("#fayearId").val();
 	var url = "bookreceipt.html?getReceiptBookData"
 	var requestData = {"empId" : empId,
 			"fayearId" : fayearId
 			
 			};

	       var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
			'json');
 	
	       if (ajaxResponse == "success")
 	        {
				$("#ReceiptBookGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
	    	   
 	        }
	       else
	       {
				showErrormsgboxTitle(getLocalMessage("book.searchnotfound" /* No records found for selected search criteria"*/));
			}
	       
 	

 }

