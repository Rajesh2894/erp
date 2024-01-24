/**
 * ritesh.patil
 * 
 */
var fileArray=[];
locationUrl='LocationMas.html';
$(document).ready(function(){
	
	$("#locationGrid").jqGrid(
			{   
				url : locationUrl+"?locationMasGridData",
				datatype : "json",
				mtype : "POST",
				colNames : [ '',getLocalMessage('locationMas.locationName'),getLocalMessage('locationMas.locationArea'),getLocalMessage('locationMas.landmark'),getLocalMessage('locationMas.pincode'),getLocalMessage('locationMas.status'),getLocalMessage('estate.grid.column.action')],
				colModel : [ {name : "locId",width : 5,  hidden :  true},
				             {name : "locNameEng",width : 30,  sortable :  true},
				             {name : "locArea",width : 20,search :true,sortable :  true},
				             {name : "landmark",width : 20,search :true,sortable :  true}, 
				             {name : "pincode",width : 20,search :true,sortable :  true},
				             {name : "locActive",width : 20,search :false,align: 'center !important',formatter : returnStatus,sortable :  true},
				             { name: 'enbll', index: 'enbll', width: 30, align: 'center !important',formatter:actionFormatter,search :false,sortable :false}
				            ],
				pager : "#locationPager",
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
				caption : getLocalMessage('locationMas.locList')
			});
		   $("#locationGrid").jqGrid('navGrid','#locationPager',{edit:false,add:false,del:false,search:true,refresh:true,closeAfterSearch:true}); 
	       $("#locationPager_left").css("width", "");

	      
		 $('#addLocMasLink').click(function() {
			var ajaxResponse = __doAjaxRequest(locationUrl+'?form', 'POST', {}, false,'html');
			$('.content').html(ajaxResponse);
		 });
		 
		 
	 
});	 

function returnStatus(cellValue, options, rowdata, action) {
	if (rowdata.locActive == 'Y') {
		return "<a href='#'  class='fa fa-check-circle fa-2x green '   value='"+rowdata.isdeleted+"'  alt='Location is Active' title='Location is Active'></a>";
	} else {
		return "<a href='#'  class='fa fa-times-circle fa-2x red ' value='"+rowdata.isdeleted+"' alt='Location is  InActive' title='Location is InActive'></a>";
	}
}


function actionFormatter(cellvalue, options, rowObject) 
{
   return  "<a class='btn btn-blue-3 btn-sm' title='View Location' onclick=\"showLocation('"+rowObject.locId+"','V')\"><i class='fa fa-eye' aria-hidden='true'></i></a> " +
   		   "<a class='btn btn-warning btn-sm' title='Edit Location' onclick=\"showLocation('"+rowObject.locId+"','E')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> ";
}

function showLocation(locId,type){
	
	var divName	=	formDivName;
   var requestData = 'locId='+locId+'&type='+type;
	var ajaxResponse	=	doAjaxLoading(locationUrl+'?form', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$('.content').html(ajaxResponse);
	/*if($('#hiddeValue').val() == 'E'){
		 $('input[type="text"],textarea').prop("disabled", true);
		 $("#isDeptLoc").prop("disabled",true);
		 $("#backBtn").removeProp("disabled");
		 $('#dpDeptId').removeProp("disabled");
		 
			$("#latitudeId").prop("disabled", false);
			$("#longitudeId").prop("disabled", false);

	 }
	if($('#hiddeValue').val() == 'V'){
		$("#locmasForm :input").prop("disabled", true);
		 $("#backBtn").removeProp("disabled");
	}*/
}


function showRLValidation(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
   errMsg += '<ul>';
   $.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
   $('html,body').animate({ scrollTop: 0 }, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	return false;
}

function closeOutErrBox(){
	$('.error-div').hide();
	$('#errorDivSec').hide();
}

function back() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', locationUrl);
	$("#postMethodForm").submit();
}


/***** Excel import export related code start *****/

function exportTemplate() {
	var url = "LocationMas.html?exportTemplateData";
	var requestData = "";
	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	$('.content').html(returnData);

	prepareDateTag();
	return false;
}

$("#tbLocationMaster").validate({

	onkeyup : function(element) {
		this.element(element);
		console.log('onkeyup fired');
	},
	onfocusout : function(element) {
		this.element(element);
		console.log('onfocusout fired');
	}
});

/***** Excel import export related code end *****/