
var dsgid = '';
var Error_Status = '';

		$(function() {
			$("#grid").jqGrid(
					{
						url : "Designation.html?getGridData",datatype : "json",mtype : "GET",
						colNames : [ getLocalMessage('master.desg.dsgId'), getLocalMessage('master.desg.name'),
						             getLocalMessage('master.desg.regName'),
						             getLocalMessage('master.desg.descShrt'),
						             getLocalMessage('master.dsg.active/inactive'), 
						             getLocalMessage('master.grid.column.action')],
						colModel : [ {name : "dsgid",width : 20,sortable : true, search:false}, 
						             {name : "dsgname",width : 65,sortable : true, search:true},
						             {name : "dsgnameReg",width : 65,sortable : true, search:true},
						             {name : "dsgshortname",width : 35,sortable : true, search:true}, 
						             {name : "isdeleted",width : 35,align: 'center !important',sortable : true,formatter : returnisdeletedUrl,search:false},
						             { name: 'dsgid', index: 'dsgid', width: 30, align: 'center !important', 
						            	 sortable: false,search : false,formatter:actionFormatter
						             }
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
						caption : getLocalMessage('master.desg.gridHeaderName')
					});
			 jQuery("#grid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:true}); 
			 $("#pagered_left").css("width", "");
		});
		
		function returnisdeletedUrl(cellValue, options, rowdata, action) {

			if (rowdata.isdeleted == '0') {
				return "<a href='#'  class='fa fa-check-circle fa-2x green '   value='"+rowdata.isdeleted+"'  alt='Designation is Active' title='Designation is Active'></a>";
			} else {
				return "<a href='#'  class='fa fa-times-circle fa-2x red ' value='"+rowdata.isdeleted+"' alt='Designation is  INActive' title='Designation is InActive'></a>";
			}

		}
		
		function actionFormatter(cellvalue, options, rowObject){
			
			if($('#orgHidden').val() == "Y"){
				return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewDesg('"+rowObject.dsgid+"')\"><i class='fa fa-eye' aria-hidden='true'></i></a> " +
				 "<a class='btn btn-warning btn-sm' title='Edit' onclick=\"editDesg('"+rowObject.dsgid+"')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> ";
			}else{
				return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewDesg('"+rowObject.dsgid+"')\"><i class='fa fa-eye' aria-hidden='true'></i></a> ";
			}
			
		}
		
		
		function returnEditUrl(cellValue, options, rowdata, action) {
			dsgid = rowdata.dsgid;
			return "<a href='#'  return false; class='addDesignationClass'><img src='css/images/edit.png' width='20px' alt='Edit  Master' title='Edit  Master' /></a>";
		}

		function returnViewUrl(cellValue, options, rowdata, action) {

			return "<a href='#'  return false; class='viewDesignationClass'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
		}
		function returnDeleteUrl(cellValue, options, rowdata, action) {

			return "<a href='#'  return false; class='deleteDesignationClass fa fa-trash-o fa-2x'  alt='View  Master' title='Delete  Master'></a>";
		}

	$(document).ready(function() {
		$('.error-div').hide();
		Error_Status = '${Errore_Value}';  
	
	});
	
	 function closeOutErrBox() {
			$('.error-div').hide();
		}
	 

		 function viewDesg(dsgid){
			 	
				var url = "Designation.html?viewMode";
				var requestData = "dsgid=" + dsgid + "&MODE1=" + "View";
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				var divName = '.content';
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				return false;
		 }
		 
		 function editDesg(dsgid){
			
				var url = "Designation.html?formForUpdate";
				var requestData = "dsgid=" + dsgid + "&MODE1=" + "EDIT";
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				var divName = '.content';
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				return false;
		 }
		

		$(function() {

			$(document)
			.on('click',
					'.addDesignationClass',
					function() {
						var $link = $(this);
						var dsgid = $link.closest('tr').find('td:eq(0)').text();
						var url = "Designation.html?formForUpdate";
						var requestData = "dsgid=" + dsgid + "&MODE1=" + "EDIT";
						var returnData =__doAjaxRequest(url,'post',requestData,false);
						var divName = '.content';
						$(divName).removeClass('ajaxloader');
						$(divName).html(returnData);
						return false;
					});
			
			$(document)
					.on(
							'click',
							'.deleteDesignationClass',
							function() {
								var $link = $(this);
								var dsgid = $link.closest('tr').find('td:eq(0)').text();
								$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
								showConfirmBoxEmployee(dsgid);
							});

		});



		function deleteDataFromGrid(dsgid) {
			
			var url = "Designation.html?update";
			var requestData = "dsgid=" + dsgid + "&MODE1="	+ "Delete";
			
			var returnData =__doAjaxRequest(url,'post',requestData,false);
			$.fancybox.close();
			$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			return false;
		}



		function displayAddView(obj){
			 var formName	=	findClosestElementId(obj, 'form');
				
				var theForm	=	'#'+formName;
				
				var divName	=	childDivName;
				var requestData = __serializeForm(theForm);
				var formAction	=	$(theForm).attr('action');	
				
				var url 	=	formAction+'?create';
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				$(divName).html(returnData);
				prepareDateTag();
		}



/*Add Form*/
function saveDataDesignation(obj) {
	var errorList = [];
	var shortValue = $('#designation_dsgshortname').val();
	
	if($('#mode').val() == 'create'){
		if(!($("#isdeleted").is(':checked'))){
			errorList.push(getLocalMessage("master.designation.active"));
		}
	}
	
	if($("#designation_dsgname").val() == 0 || $("#designation_dsgname").val() == ''){
		errorList.push(getLocalMessage("master.enter.name.english"));
	}
	if($("#designation_dsgnamereg").val() == 0 || $("#designation_dsgnamereg").val() == ''){
		errorList.push(getLocalMessage("master.enter.name.regional"));
	}
	if(shortValue == 0 || shortValue == ''){
		errorList.push(getLocalMessage("master.enter.short.code"));
	}else{
		if (shortValue.length < 2) {
			errorList.push(getLocalMessage("master.enter.morethan.twoChar.shortCode"));
		}
	}
	
	if(errorList.length > 0){
		showError(errorList);
		return false;
	}else{
		
		var	formName =	findClosestElementId(obj, 'form');
		var theForm	=	'#'+formName;
		var requestData = __serializeForm(theForm);
		var url	=	$(theForm).attr('action');
		var returnData=__doAjaxRequestForSave(url, 'post', requestData, false,'',obj);
		if($.isPlainObject(returnData)) {
			showConfirmBox();
			 return false;
		} else {
			$(".content").html(returnData);
			$(".content").show();
		}
		return false;
	}
}

function showError(errorList){
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDivScrutiny").html(errMsg);
	$("#errorDivScrutiny").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
}

function showConfirmBox(){

	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls ='Proceed';
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">Record Saved Successfully</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="proceed()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	 return false;
}

function proceed() {
	window.location.href='Designation.html';
}

function resetDesignation(){
	$(".alert").hide();
	$("#errorDivScrutiny").hide();
	var mode = $('#mode').val();
	if(mode == 'Create'){
		$('#designation_dsgname').val("");
		$('#designation_dsgnamereg').val("");
		$('#designation_dsgshortname').val("");
		$('#designation_dsgdesc').val("");
	}
}

function checkForDesignation() {
	var status=$('#status').val();
	if(status != 1){
		var requestData = "dsgid=" + $('#dsgid').val();
		var checkUrl = "Designation.html?checkIfDesOrgMapExists";
		var returnCheck = __doAjaxRequestForSave(checkUrl, 'post', requestData,
				false, '');
		if (returnCheck == true) {
			var msg = 'Transaction already exists, cannot change the status to inactive';
			$(errMsgDiv).html("<h4 class=\"text-center text-blue-2 padding-10\">" + msg	+ "</h4>");
			$(errMsgDiv).show();
			showMsgModalBoxDep(errMsgDiv);
			$('#isdeleted').prop('checked', true).val('A');
		}
	}	
}

function showMsgModalBoxDep(childDialog) {
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic',
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		keys : {
			close : null
		}
	});
	return false;
}