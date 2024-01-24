
$("#accountFunctionMasterBean").validate({
	
	onkeyup: function(element) {
	       this.element(element);
	       console.log('onkeyup fired');
	 },
	onfocusout: function(element) {
	       this.element(element);
	       console.log('onfocusout fired');
	}
});

$(function() {
	$("#functionMasterGrid").jqGrid(
			{
				url : "AccountFunctionMaster.html?getGridData",datatype : "json",mtype : "POST",
				colNames : [ '',getLocalMessage('acc.master.funCode'),getLocalMessage('acc.master.funDesc'),"", getLocalMessage('bill.action')],
				colModel : [ {name : "functionId",width : 20,sortable :  false,searchoptions: { "sopt": [ "eq"] },hidden:true  },
				             {name : "functionCode",width : 20,sortable :  true,searchoptions: { "sopt": [ "eq"] }  },
				             {name : "functionDesc",width : 75,sortable : true,searchoptions: { "sopt": ["bw", "eq"] }}, 
				             {name : "defaultOrgFlag",width : 20,sortable : true,searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				             { name: 'functionId', index: 'functionId', width:20 , align: 'center !important',formatter:addLink,search :false},
				            ],
				pager : "#pagered",
				emptyrecords: getLocalMessage("jggrid.empty.records.text"),
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
				caption : getLocalMessage('account.configuration.function.masters.list')
			});
	 jQuery("#functionMasterGrid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	 $("#pagered_left").css("width", "");
});


function returnEditUrl(cellValue, options, rowdata, action) {
    return "<a href='#'  return false; class='editClass' value='"+rowdata.spId+"' ><img src='css/images/edit.png' width='20px' alt='Edit Charge Master' title='Edit Scrutiny Data' /></a>";
}

function returnViewUrl(cellValue, options, rowdata, action) {
	return "<a href='#'  return false; class='viewFunctionMasterGridClass' value='"+rowdata.spId+"'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}

function addLink(cellvalue, options, rowdata) {
    return "<a class='btn btn-blue-3 btn-sm viewFunctionMasterGridClass' title='View'value='"+rowdata.functionId+"' id='"+rowdata.functionId+"' ><i class='fa fa-building-o'></i></a> " +
    	   "<a class='btn btn-warning btn-sm viewFunctionMasterClass' title='Edit'value='"+rowdata.functionId+"' id='"+rowdata.functionId+"' ><i class='fa fa-pencil'></i></a> " +		
    	   "<a class='btn btn-success btn-sm editClass' title='Add Child Function'value='"+rowdata.functionId+"' id='"+rowdata.functionId+"' ><i class='fa fa-plus text-white' aria-hidden='true'></i></a> ";
 }


$(function() {		
	$(document).on('click', '.addFunctionMasterClass', function() {
		var $link = $(this);
		var functionId = 1;
		var url = "AccountFunctionMaster.html?showLeveledform";
		var requestData = "functionId=" + functionId  + "&MODE=" + "VIEW";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		$('.content').html(returnData);
		prepareDateTag();
		return false;	
	});
	
});

$( document ).ready(function() {
	$('.error-div').hide();
	$('#editChildNode').hide();
	$('#editParentNode').hide();
	$('#saveButttonOnDescChange').hide();
	$("#childFunLevel0 option[value='1']").hide();
	$("#tree").dynatree(
			{
				checkbox : true,
				classNames : {
					checkbox : "dynatree-radio"
				},
				selectMode : 1,
				autoCollapse : false,
				
				onSelect : function(select, node) {
					$('#saveButttonOnDescChange').show();
					$('#rootNode').attr('checked', false);
					$("#etId").val('');
					$("#flag").val('');
					var selKeys2 = $
							.map(node.tree.getSelectedNodes(),
									function(node) {
										return node.data.title;
									});
					var status= $.map(node.tree.getSelectedNodes(),
							function(node) {
								return node.data.href;
							});
			        var statusFId = status+ ""; 
			       var arrayOfstatusFunctionId = statusFId.split("-");
			       
			       var statusCode = arrayOfstatusFunctionId[0];
			       var functionId = arrayOfstatusFunctionId[1];
			       
			       
			       
					var string = selKeys2 + "";
					var arrayOfSelectedData = string.split("-&gt;");
					var compCode = arrayOfSelectedData[0] + "";
					$('#editedChildFunCode').val(compCode.substring(compCode.lastIndexOf("-") + 1));
					var parentCompCodeIntoArray = compCode.substring(0,compCode.lastIndexOf("-")).split("-");
					
					$('#editedDataChildDesc').val(arrayOfSelectedData[1].trim());
					
					 $("#editedChildStatus").val(statusCode.trim());
					 $("#edittedFunctionId").val(functionId);
					 
					 
					 if(compCode.substring(0,compCode.lastIndexOf("-"))==''){
						 $('#editedChildFunLevel').val(parentCompCodeIntoArray.length); 
					 }
					 else{
						 $('#editedChildFunLevel').val(parentCompCodeIntoArray.length+1); 
					 }
					if(compCode.substring(0,compCode.lastIndexOf("-"))==''){
						$('#editParentNode').hide();
						$('#editedDataChildParentCode').val('');
					}
						$('#editChildNode').show();
						if($("#editedChildFunLevel").val()==$('#editedChildFunLevel option:last-child').val())
						{
						$('#statusDiv').show();	
						}
					else
						{
						$("#statusDiv").hide();
						}
						
						$('#editedDataChildCompositeCode').val('');
						if(compCode.substring(0,compCode.lastIndexOf("-"))!=''){
							$('#editedDataChildParentCode').val(compCode.substring(0,compCode.lastIndexOf("-")));
							$('#editedDataChildCompositeCode').val(arrayOfSelectedData[0]);
							$('#editedDataChildParentLevel').val(parentCompCodeIntoArray.length);
							$('#editParentNode').show();	
						}
						$('html, body').animate({scrollTop : $("#editParentNode").offset().top}, 1000);
						

				}
				
			});
	
});
$(function() {		
	$(document).on('click', '.editClass', function() {
		var errorList = [];
		var $link = $(this);
		var funId = $link.closest('tr').find('td:eq(0)').text();
		var defaultOrgFlag = $link.closest('tr').find('td:eq(3)').text();
		var url = "AccountFunctionMaster.html?editGridData";
		var requestData = "functionId=" + funId + "&MODE=" + "EDIT";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		if(defaultOrgFlag=="Y"){
			$('.content').html(returnData);
		}
		else{
			errorList.push(getLocalMessage('account.child.selection.protected'));
			if(errorList.length>0){
		    	var errorMsg = '<ul>';
		    	$.each(errorList, function(index){
		    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		    	});
		    	errorMsg +='</ul>';
		    	$('#errorId').html(errorMsg);
		    	$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');	
				return false;
		    }
		}	
	});
		
});

$(function() {		
	$(document).on('click', '.viewFunctionMasterClass', function() {
		var errorList = [];
		var $link = $(this);
		var funId = $link.closest('tr').find('td:eq(0)').text();
		var defaultOrgFlag = $link.closest('tr').find('td:eq(3)').text();
		var url = "AccountFunctionMaster.html?editGridData";
		var requestData = "functionId=" + funId + "&MODE=" + "VIEW";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		if(defaultOrgFlag=="Y"){
			$('.content').html(returnData);
		}
		else{
			errorList.push(getLocalMessage('account.protected.orgonization'));
			if(errorList.length>0){
		    	var errorMsg = '<ul>';
		    	$.each(errorList, function(index){
		    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		    	});
		    	errorMsg +='</ul>';
		    	$('#errorId').html(errorMsg);
		    	$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');	
				return false;
		    }
		}	
	});
		
});

$(function() {		
	$(document).on('click', '.viewFunctionMasterGridClass', function() {
		var $link = $(this);
		var funId = $link.closest('tr').find('td:eq(0)').text();
		var url = "AccountFunctionMaster.html?viewGridData";
		var requestData = "functionId=" + funId + "&MODE=" + "VIEW";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		$('.content').html(returnData);
		
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr("disabled", true);
		$('select').prop('disabled', true).trigger("chosen:updated");
		
		prepareDateTag();
			
	});
		
});

function closeErrBox() {
	$('.error-div').hide();
	$('.warning-div').hide();
}

function removeRowFunction (){
	var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	  i = stringId.match(/[0-9]+/g);
	  var errorList= [];	
	  var  childFieldLevel= $("#childFunLevel" + i).val();
	    var  parentFieldLevel= $("#childParentFunLevel" + i).val();
	    var  childCode=$("#childFunCode" + i).val();
	    var parentCode=$("#childParentCode" + i).val();
	    var desc=$("#funDesc" + i).val();
	    var childFinalCode = $("#childFinalCode" + i).val();

	    var requestData = "childFinalCode=" + childFinalCode + "&addOrRemoveRow=" + "removeRow";
	    
	    var isDuplicte =__doAjaxRequest('AccountFunctionMaster.html?validateDuplicateCompositeCode','post',requestData,false);
	
	var rowCount = $('#divId li').length;
	if(rowCount<=1){
		return false;
	}
	
	$('#ulId li').last().remove();
}




function getFincalCode(){
	
	
	var count = $('#countFinalCode').val();
	var childFunCode = $('#childFunCode'+count).val();
	var childParentCode = $('#childParentCode'+count).val();
	if(childFunCode!=''){
		if(childParentCode!=''){
			var childFinalCode = childFunCode+childParentCode;
			$('#childFinalCode'+count).val(childFinalCode)
			count++;
			$('#countFinalCode').val(count);
		}
	}
}



	
var incrementvalue;

function getParentFieldCode(){
	
	$("#parentFieldCode0").keyup(function(event) {
	       var stt=$(this).val();
	           $("#parentFinalCode0").val(stt);
	});
		
}



function saveEditedDataForFunctionMaster(obj){
			var	formName =	findClosestElementId(obj, 'form');
			var theForm	=	'#'+formName;
			var requestData = {};
			requestData = __serializeForm(theForm);
			var url	=	$(theForm).attr('action')+'?' + 'saveAddChildData';
			
			var response= __doAjaxRequestValidationAccor(obj,url, 'post', requestData, false, 'html');
		    if(response != false){
		    	 var obj=$(response).find('#hasError'); 
				    if(obj.val()=='Y')	    	{
				    	$('.content').html(returnData);
				    	prepareDateTag();
				    	}else{
				    		showConfirmBox();
				    	}
		    }
		    
}


function validateEachFieldWithFunctionLevels(){
	   var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
		  i = stringId.match(/[0-9]+/g);
		  var errorList= [];	
		  var  childFieldLevel= $("#childFunLevel" + i).val();
		    var  parentFieldLevel= $("#childParentFunLevel" + i).val();
		    var  childCode=$("#childFunCode" + i).val();
		    var childFunctionStatus=$("#childFunctionStatus" + i).val();
		    
		    var parentCode=$("#childParentCode" + i).val();
		    var desc=$("#funDesc" + i).val();
		    var  childFunLevel= $("#childFunLevel" + i).val();
		    var  parentFunLevel= $("#childParentFunLevel" + i).val();
		    
		    var childFinalCode = $("#childFinalCode" + i).val();

		    var requestData = "childFinalCode=" + childFinalCode + "&addOrRemoveRow=" + "addRow"; 
		    
		    var isDuplicte =__doAjaxRequest('AccountFunctionMaster.html?validateDuplicateCompositeCode','post',requestData,false);
		    
		    if(isDuplicte){
		    	$("#childFunLevel" + i).val('');
		    	$("#childParentFunLevel" + i).val('');
		    	$("#childFunCode" + i).val('');
		    	$("#childParentCode" + i).val('');
		    	$("#childFinalCode" + i).val('');
		    	
		    	errorList.push("Final Code Already Exist.");
		    }
		    
		    
		    if(parseInt(parentFunLevel)>parseInt(childFunLevel)||parseInt(parentFunLevel)==parseInt(childFunLevel)){
		    	errorList.push("Select Correct Level");
		    	
		    }
		    else if(parseInt(parentFunLevel)<parseInt(childFunLevel)||parseInt(parentFunLevel)==parseInt(childFunLevel)){
		    	if(parseInt(childFunLevel)-parseInt(parentFunLevel)>1||parseInt(childFunLevel)-parseInt(parentFunLevel)==0){
		    		errorList.push("Select Correct Level");
		    	}
		    }
		    
		    if(childFieldLevel == '0') {
		    	errorList.push("Child function level must not be empty");
		 	}
		     
		     if(parentFieldLevel == '0') {
		    	 errorList.push("Child parent level must not be empty");
		 	}
		     if(childCode == '') {
		    	 errorList.push("Child function code must not be empty");
		 	}
		     if(parentCode == '0') {
		    	 errorList.push("Child parent code must not be empty");
		 	}
		     if(desc == '') {
		    	 errorList.push("Child function description must not be empty");
		 	}
		     return bindError(errorList);
}


function addRowFunction (){
	
	var noError = true; //validateEachFieldWithFunctionLevels();
	if(!noError){
		return false;
	}else{
			  var content = $('#divId li:last').clone();
			  content.append("<script>$(document).ready(function(){$('.hasNumber').keyup(function () {" 
					  +"this.value = this.value.replace(/[^0-9]/g,'');"+
			             "}); });</script>");
			  $("#divId ul > li").last().after(content);
			  content.find("input:text").val("")
			  var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
			  count = stringId.match(/[0-9]+/g);
			  incrementvalue=++count;
			   var id="#ulId";
			   content.find('select:eq(0)').attr({'path': 'listDto['+incrementvalue+'].childLevel'}).attr({'name': 'listDto['+incrementvalue+'].childLevel' }).attr({'id': 'childFunLevel'+incrementvalue });
			   content.find('select:eq(1)').attr({'path': 'listDto['+incrementvalue+'].childParentLevel'}).attr({'name': 'listDto['+incrementvalue+'].childParentLevel' }).attr({'id': 'childParentFunLevel'+incrementvalue });
			   content.find('input:text:eq(0)').attr({'path': 'listDto['+incrementvalue+'].childCode' }).attr({'name': 'listDto['+incrementvalue+'].childCode' }).attr({'id': 'childFunCode'+incrementvalue}).addClass("hasNumber");
			   content.find('input:text:eq(1)').attr({'path': 'listDto['+incrementvalue+'].childDesc'}).attr({'name': 'listDto['+incrementvalue+'].childDesc' }).attr({'id': 'funDesc'+incrementvalue });
			   content.find('select:eq(2)').attr({'path': 'listDto['+incrementvalue+'].childParentCode'}).attr({'name': 'listDto['+incrementvalue+'].childParentCode' }).attr({'id': 'childParentCode'+incrementvalue}).attr({'value': ''}).addClass("hasNumber");;
			   content.find('input:text:eq(2)').attr({'path': 'listDto['+incrementvalue+'].childFinalCode'}).attr({'name': 'listDto['+incrementvalue+'].childFinalCode' }).attr({'id': 'childFinalCode'+incrementvalue});   
			   content.find('select:eq(3)').attr({'path': 'listDto['+incrementvalue+'].childFunctionStatus'}).attr({'name': 'listDto['+incrementvalue+'].childFunctionStatus' }).attr({'id': 'childFunctionStatus'+incrementvalue+'' });
			   content.find('#childFunCode'+incrementvalue).attr("onchange", "clearParentCode(" + (incrementvalue) + ")");
			   content.find('label').closest('.error').remove(); //for removal duplicate
			   $('#count').val(count);
			   
	}
}

function validateLevel(){
	var errorList = [];
	 var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	  i = stringId.match(/[0-9]+/g);
	 	var  childFunLevel= $("#childFunLevel" + i).val();
	    var  parentFunLevel= $("#childParentFunLevel" + i).val();
	    if(parseInt(parentFunLevel)>parseInt(childFunLevel)||parseInt(parentFunLevel)==parseInt(childFunLevel)){
	    	errorList.push("Select Correct Level");
	    }
	    else if(parseInt(parentFunLevel)<parseInt(childFunLevel)||parseInt(parentFunLevel)==parseInt(childFunLevel)){
	    	if(parseInt(childFunLevel)-parseInt(parentFunLevel)>1||parseInt(childFunLevel)-parseInt(parentFunLevel)==0){
	    		errorList.push("Select Correct Level");
	    	}
	    }
	   return bindError(errorList);
}

function bindError(errorList){
	if(errorList.length > 0){
   		
  		 var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

  		$.each(errorList, function(index) {
  			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
  		});

  		errMsg += '</ul>';
  		$('.warning-div').show();
  		$("#clientSideErrorDivScrutiny").removeClass('hide');
  		$("#clientSideErrorDivScrutiny").html(errMsg);					
  		$('html,body').animate({ scrollTop: 0 }, 'slow');
  		return false;
  	}else{
  		return true;
  	}
}

function saveLeveledDataForFunctionMaster(obj){
		var	formName =	findClosestElementId(obj, 'form');
		var theForm	=	'#'+formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var url	=	$(theForm).attr('action')+'?' + 'create';
		
		var returnData = __doAjaxRequestValidationAccor(obj,url, 'post', requestData, false, 'html');
	    if(returnData != false){
	    	var obj=$(returnData).find('#hasError'); 
		    if(obj.val()=='Y')
		    	{
		    	$('.content').html(returnData);
		    	prepareDateTag();
		    	}else{
		    		showConfirmBox();
		    	}
	    }
}


function showConfirmBox(){
	   var redirectUrl = 'AccountFunctionMaster.html';
	   var message="";
	   var succmsg = getLocalMessage('account.form.submit.success');
	   var okmsg = getLocalMessage('account.ok');
		 message	+='<h5 class="text-blue-2 text-center padding-15">'+ ''+succmsg+''+'</h5>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+okmsg+'\'  id=\'btnNo\' class=\'btn btn-success\'    '+ 
		' onclick="closebox(\''+errMsgDiv+'\',\''+redirectUrl+'\')"/>'+	
		'</div>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		if(redirectUrl=="" || redirectUrl==null){
		    showModalBox(errMsgDiv);
		}else{
			  showModalBoxWithoutClose(errMsgDiv);
		}
	    	
	}

function updateDescForFunctionMaster(obj){
	
	var errorList= [];
	
	var editedChildStatus=$("#editedChildStatus").val();
	
	if($('#editedDataChildDesc').val().trim().length<1){
		errorList.push("Please Enter Description");
	}
	if(editedChildStatus == '0' || editedChildStatus == '') {
		  errorList.push("Please select child function status");
	}
	
	if(errorList.length>0){
		 return bindError(errorList);
	}
	else{
		var	formName =	findClosestElementId(obj, 'form');
		var theForm	=	'#'+formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var url	=	$(theForm).attr('action')+'?' + 'saveEditedData';
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
	    var obj=$(returnData).find('#hasError'); 
	    if(obj.val()=='Y')	    	{
	    	$('.content').html(returnData);
	    	prepareDateTag();
	    	}else{
	    		showConfirmBox();
	    	}
	return false;
	}
}


function fetchMaxLengthAndResetChildParentLevel(){
	    var levelCodeForChildField;
	    var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	    var incrementvalue = stringId.match(/[0-9]+/g);
		 $("#childParentFunLevel"+incrementvalue).find('option').remove();
		 var optionAsString = '';
		 var selectedLevel = $('#childFunLevel'+incrementvalue).find(":selected").val()-1;
		 var text = $('#childFunLevel'+incrementvalue).find('option[value='+selectedLevel+']').text();
		 var optionAsString2 = '';
		 optionAsString2 += "<option value=''> Select </option>";
		 optionAsString2 += "<option value='"+ selectedLevel +"'>" + text +"</option>";
		 $('#childParentFunLevel'+incrementvalue).append(optionAsString2);
		$('#childParentFunLevel'+incrementvalue).val(0);
		levelCodeForChildField  = $('#childFunLevel'+incrementvalue).find(":selected").val();
		var url = 'AccountFunctionMaster.html?getCodeDigits';
		var requestData = "selectValue=" + levelCodeForChildField;
		var digitForChildFieldCode=__doAjaxRequest(url,'post',requestData,false);
		
	 	$('#childFunCode'+incrementvalue).val('');
	 	$('#childFunCode'+incrementvalue).attr({'maxlength':digitForChildFieldCode[0]})
				
}


//changed
var countForParentLevelSelection='';
function getParentCode(count){
	countForParentLevelSelection = $('#countForParentLevel').val();
	
	 var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	 countForParentLevelSelection = stringId.match(/[0-9]+/g);
	   
	var childLevel = $('#childParentFunLevel'+countForParentLevelSelection).val();
	var childCode = $('#childCode'+countForParentLevelSelection).val();
	
	
	var requestData = "selectLevel=" + childLevel;
	
	var codeWithDesc =__doAjaxRequest('AccountFunctionMaster.html?populateParentCode','post',requestData,false);
	
	var optionAsString = '';
	$("#childParentCode"+countForParentLevelSelection).find('option').remove();
	var select =getLocalMessage('account.common.select');
	optionAsString += "<option value=''> "+select+" </option>";
	for(var i=0;  i<codeWithDesc.length;  i++){
		var compositeCode = codeWithDesc[i].substring(0,codeWithDesc[i].indexOf('('));
		if(compositeCode==''){
			optionAsString += "<option value='"+ codeWithDesc +"'>" + codeWithDesc[i] +"</option>";	
		}else{
			optionAsString += "<option value='"+ compositeCode +"'>" + codeWithDesc[i] +"</option>";
		}
	}
	$("#childParentCode"+countForParentLevelSelection).append(optionAsString);
}


//changed
function getFinalCode(count){
	 var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	   countForchildLevel = stringId.match(/[0-9]+/g);
	   
    var childLevel = $('#childFunLevel'+countForchildLevel).val();
	var childParentLevel = $('#childParentFunLevel'+countForchildLevel).val();
	
	var childCode = $('#childFunCode'+countForchildLevel).val();
	var childParentCode = $('#childParentCode'+countForchildLevel).val();
	
	var childDesc = $('#funDesc'+countForchildLevel).val();
	
	var requestData = "childLevel=" + childLevel + "&childParentLevel=" + childParentLevel+"&childCode="+childCode+"&childParentCode="+childParentCode+"&childDesc="+childDesc;
	
	var returnData =__doAjaxRequest('AccountHeadPrimaryAccountCodeMaster.html?storeChildDetails','post',requestData,false);
	
	$('#childFinalCode'+countForchildLevel).val(returnData);
	
	$('#parentCode').prop("readonly",true);
}



function setParentValue(){
	var errorList = [];
	var childLevel = 1;
	var childParentLevel = 0;
	var childCode = $('#parentCode').val();
	var childParentCode = 0;
	var childDesc = $('#parentfunDesc').val();
	
	var maxParentCodeLenght= $("#maxParentLenght").val();
	
	if(childCode != null && childCode != ""){
	 if(childCode.length != maxParentCodeLenght) {
    	 errorList.push("Parent function code must be "+maxParentCodeLenght+" digit");
 	}else{
 		$('#parentFinalCode').val(childCode);
 	 }
	}
	
	if(errorList.length==0){
	var requestData = "childLevel=" + childLevel + "&childParentLevel=" + childParentLevel+"&childCode="+childCode+"&childParentCode="+childParentCode+"&childDesc="+childDesc;
	
	$("#childParentCode0").append('');
	
	var returnData =  __doAjaxRequest('AccountFunctionMaster.html?storeChildDetails','post',requestData); 
	$('#parentFinalCode').val(childCode);
	}
	 
	 if(errorList.length>0){
			$('#parentCode').val('');
			$('#parentFinalCode').val('');
			
			 return bindError(errorList);
		}else{
			$('.warning-div').hide();
		}
}
	 
	 
	function validateCompositeCode(){
		
		  var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
		  var  i = stringId.match(/[0-9]+/g);
		  var errorList= [];	
		  var prntCode= $("#parentCode").val().trim();
	      var childCode= $("#childFunCode"+i).val();
	      
	      if(childCode==''){
	    	  var compositeCode=prntCode.trim();
	      }
	      var compositeCode= (prntCode.trim()+"-"+childCode.trim());
	      var url = 'AccountFunctionMaster.html?validateDuplicateCode';
	      var requestData = "compositeCode=" + compositeCode;
		  var isDuplicate=__doAjaxRequest(url,'post',requestData,false);
		 if(isDuplicate){
			 errorList.push("Function Code "+childCode+" already present");
		  }
		 if(errorList.length>0){
			 $('#childFunCode'+i).val('');
			 $('#childFinalCode'+i).val('');		
			 return bindError(errorList);
		 }else{
			
		 }
	 }
	
	function clearParentCode(cont){
		
		var errorList = [];
		
		$('#childParentCode'+cont).val('');
		$('#childFinalCode'+cont).val('');
		
		var parentCode = $('#parentCode').val();
		if(parentCode == null || parentCode == ""){
			 errorList.push("Please enter parent function code");
			 $('#childFunCode'+cont).val('');
		}
		
		var childFunLevel = $('#childFunLevel'+cont).val();
		if(childFunLevel == null || childFunLevel == ""){
			 errorList.push("Please select child function Level");
			 $('#childFunCode'+cont).val('');
		}
		
		levelCodeForChildField  = $('#childFunLevel'+cont).find(":selected").val();
		var url = 'AccountFunctionMaster.html?getCodeDigits';
		var requestData = "selectValue=" + levelCodeForChildField;
		
		var digitForChildFieldCode=__doAjaxRequest(url,'post',requestData,false);
	 	
	 	var childFunCode = $('#childFunCode'+cont).val();
	 	var maxChildFieldCode = digitForChildFieldCode[0];
	 	if(childFunCode != null && childFunCode != ""){
	 	if(childFunCode.length != maxChildFieldCode) {
	    	 errorList.push("Child function code must be "+maxChildFieldCode+" digit");
	    	 $('#childFunCode'+cont).val('');
	 		}
	 	}
	 
	    if(errorList.length>0){
			 return bindError(errorList);
		}else{
			$('.warning-div').hide();
		}
		
	}
	