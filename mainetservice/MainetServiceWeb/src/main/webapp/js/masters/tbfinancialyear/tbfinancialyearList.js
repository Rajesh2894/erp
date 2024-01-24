/**
 * Harsha
 */
$(document).ready(function() {

$('.error-div').hide();
modelAttributeValue = '${errorvalue}';
if(modelAttributeValue=='Y')
	{
	$('.error-div').show();
	}

	if($('#desgName').val() == getLocalMessage('organisation.designation')){
		$('#editPermission').val('Y');
	}

});


var defaultOrganization='${defaultOrg}';
$(function () {
		console.log('Inside loadgrid');
	    $("#finYeargrid").jqGrid({
	        url: "Financialyear.html?loadFinYearData",
	        datatype: "json",
	        mtype: "GET",   
	        colNames: ['',getLocalMessage('finyear.table.column.startdate'), getLocalMessage('finyear.table.column.enddate'),
	        	getLocalMessage('finyear.table.column.yrstatus'),getLocalMessage('finyear.table.column.monstatus'),getLocalMessage('finyear.table.column.softcloper') ,getLocalMessage('master.grid.column.action')],
	        colModel: [
	        	{name : "yearStatusCode",width : 5,  hidden :  true},
	            { name: "faFromDate", width: 55, sortable: true,formatter:dateformatter,search:true},
	            { name: "faToDate", width: 55, sortable: true,formatter:dateformatter,search:true},
	            { name: "yearStatusDesc", width: 40, sortable: true,search:true},
	            { name: "faMonthStatus", width: 40, sortable: false,search:true},
	            { name: "faYearFromTo", width: 35,align: 'center !important', sortable: false,search:true},
      			{ name: 'faYear', index: 'faYear', width: 35, align: 'center !important', 
      				sortable: false,search : false,formatter:actionFormatter,search:false},
	        ],
	        pager: "#pagered",
	        rowNum: $("#finYeargrid").getGridParam("reccount"),
	        rowList: [5, 10, 20, 30,100],
	        sortname: "faFromDate",
	        sortorder: "desc",
	        height:'auto',
	        viewrecords: true,
	        gridview: true,
	        loadonce: true,
	        jsonReader : {
                root: "rows",
                page: "page",
                total: "total",
                records: "records", 
                repeatitems: false,
               }, 
	        autoencode: true,
	        caption: getLocalMessage('finyear.table.caption')       	
	    }); 
	    jQuery("#finYeargrid").jqGrid('navGrid', '#pagered', {edit : false,add : false,del : false,refresh : false});
		$("#pagered_left").css("width", "");	
	}); 
		

function dateformatter(cellValue, options, rowdata, action) 
{	
	if(cellValue!==null)
	{	
		var date = new Date(cellValue);
		return getDateFormat(date);
	}
	else
	{	
		return '';
	}
}
	
function actionFormatter(cellvalue, options, rowObject){
	var edit = $('#editPermission').val();
	var yearStatusHC = getLocalMessage('tbcomparammas.prefix.hardclose.code');
	if(edit == 'Y'){
		if(yearStatusHC == rowObject.yearStatusCode){
			return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewFinYear('"+rowObject.faYear+"')\"><i class='fa fa-eye' aria-hidden='true'></i></a>";
		}else{
			return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewFinYear('"+rowObject.faYear+"')\"><i class='fa fa-eye' aria-hidden='true'></i></a> "+
			"<a class='btn btn-warning btn-sm' title='Edit' onclick=\"editFinYear('"+rowObject.faYear+"')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> ";
		}
	}else{
		return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewFinYear('"+rowObject.faYear+"')\"><i class='fa fa-eye' aria-hidden='true'></i></a>";
	}
	
}


	$(document).on('click', '.createData', function() {
	
		var url = "Financialyear.html?form";
		$.ajax({
			url : url,
			data : '',
			type : 'POST',
			success : function(response) {
				
				//var divName = '.form-div';				
				//$(divName).removeClass('ajaxloader');
				$('.content').html(response);
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});	
	    
	    return true;
		
	  });



	function editFinYear(finyearId) {
		
		/*var requestDataForCheck = "faYearId="+finyearId;
		var urlForCheck = "Financialyear.html?checkForFinancialYear";
		var returnData =__doAjaxRequest(urlForCheck,'POST',requestDataForCheck, false,'');*/
		
			var url = "Financialyear.html?editFinancialYear";
			var requestData = {
					"finyearId" : finyearId,
					"flag" : 'Y'
			};
		
			$.ajax({
				url : url,
				data : requestData,
				success : function(response) {
					
					var divName = '.form-div';
					
					$(divName).removeClass('ajaxloader');
					$('.content').html(response);
				
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});	
			return false;
	  };

	

	function viewFinYear(finyearId) {		
		var url = "Financialyear.html?viewFinancialYear";		
		var requestData = {
				"finyearId" : finyearId,
				"flag" : 'N'
		};

		$.ajax({
			url : url,
			data : requestData,
			success : function(response) {
				
				var divName = '.form-div';
				
				$(divName).removeClass('ajaxloader');
				$('.content').html(response);
				//prepareTags();
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});	
	    
	    return false;
	  };
	


function closeErrBox() {
	$('.error-div').hide();
}

function showConfirmBox(msg){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	message	+='<h4 class=\"text-info padding-10 padding-bottom-0 text-center\">'+msg+'</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+
	'</div>';
	
	 $(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	 $(errMsgDiv).html(message);
	 $(errMsgDiv).show();
	 $('#btnNo').focus();
	 showModalBox(errMsgDiv);
	 return false;
	}
