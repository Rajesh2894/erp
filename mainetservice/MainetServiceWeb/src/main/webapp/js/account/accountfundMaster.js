
$("#accountFundMasterBean").validate({
	
	onkeyup: function(element) {
	       this.element(element);
	       console.log('onkeyup fired');
	 },
	onfocusout: function(element) {
	       this.element(element);
	       console.log('onfocusout fired');
	}
});

var dsgid = '';
var dsgid = '';
$(function() {
	
	$("#grid").jqGrid(
			{
				url : "AccountFundMaster.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : [ '', getLocalMessage('acc.master.fundMasterCode'), getLocalMessage('acc.master.fundMasterDesc'), "", getLocalMessage('bill.action')],
				colModel : [ {name : "fundId",width : 20,sortable :  false,searchoptions: { "sopt": [ "eq"] },hidden:true  },
				             {name : "fundCode",width : 20,sortable : true,searchoptions: { "sopt": [ "eq"] }}, 
				             {name : "fundDesc",width : 75,sortable : true, searchoptions: { "sopt": ["bw", "eq"] }},   
				             {name : "defaultOrgFlag",width : 20,sortable : true,searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				             { name: 'fundId', index: 'fundId', width:20 , align: 'center !important',formatter:addLink,search :false},
				            ],
				pager : "#pagered",
				emptyrecords: getLocalMessage("jggrid.empty.records.text"),
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "fundId",
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
				caption : getLocalMessage('account.configuration.fund.masters.list')
			});
	 jQuery("#grid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	 $("#pagered_left").css("width", "");
});

function returnisdeletedUrl(cellValue, options, rowdata, action) {

	if (rowdata.isdeleted == '0') {
		return "<a href='#'  class='fa fa-check-circle fa-2x green '   value='"
				+ rowdata.isdeleted
				+ "'  alt='Designation is Active' title='Designation is Active'></a>";
	} else {
		return "<a href='#'  class='fa fa-times-circle fa-2x red ' value='"
				+ rowdata.isdeleted
				+ "' alt='Designation is  INActive' title='Designation is InActive'></a>";
	}

}
function returnEditUrl(cellValue, options, rowdata, action) {
	fundId = rowdata.fundId;
	return "<a href='#'  return false; class='editFundMasterClass' value='"+fundId+"'><img src='css/images/edit.png' width='20px' alt='Edit  Master' title='Edit  Master' /></a>";
}

function returnViewUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='viewFundMasterClass' value='"+fundId+"'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}

function returnDeleteUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='deleteDesignationClass fa fa-trash-o fa-2x'  alt='View  Master' title='Delete  Master'></a>";
}


function addLink(cellvalue, options, rowdata) {
    return "<a class='btn btn-blue-3 btn-sm viewFundMaster' title='View'value='"+rowdata.fundId+"' id='"+rowdata.fundId+"' ><i class='fa fa-building-o'></i></a> " +
    		"<a class='btn btn-warning btn-sm viewFundMasterClass' title='Edit'value='"+rowdata.fundId+"' id='"+rowdata.fundId+"' ><i class='fa fa-pencil'></i></a> " +
    		"<a class='btn btn-success btn-sm editFundMasterClass' title='Add Child Fund'value='"+rowdata.fundId+"' id='"+rowdata.fundId+"' ><i class='fa fa-plus text-white' aria-hidden='true'></i></a> ";
 }



function closeOutErrBox() {
	$('.error-div').hide();
}



$(function() {
	$(document).on('click', '.addFundMasterClass', function() {
		var $link = $(this);
		var fundId = 1;
		var url = "AccountFundMaster.html?formForUpdate";
		var requestData = "fundId=" + fundId +"&MODE_DATA=" + "ADD";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		 $("#statusDivMain").hide();
		return false;
	});

	$(document).on('click', '.editFundMasterClass', function() {
		var errorList = [];
		var $link = $(this);
		var fundId = $link.closest('tr').find('td:eq(0)').text();
		var defaultOrgFlag = $link.closest('tr').find('td:eq(3)').text();
		var url = "AccountFundMaster.html?update";
		var requestData = "fundId=" + fundId + "&MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		if(defaultOrgFlag=="Y"){
			$('.content').html(returnData);
			$("#statusDivMain").hide();
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
	
	$(document).on('click','.viewFundMasterClass',function() {
				var errorList = [];
				var $link = $(this);
				var fundId = $link.closest('tr').find('td:eq(0)').text();
				var defaultOrgFlag = $link.closest('tr').find('td:eq(3)').text();
				var url = "AccountFundMaster.html?update";
				var requestData = "fundId=" + fundId + "&MODE_DATA=" + "VIEW";
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
	
	$(document).on('click','.viewFundMaster',function() {
		var $link = $(this);
		var fundId = $link.closest('tr').find('td:eq(0)').text();
		var url = "AccountFundMaster.html?view";
		var requestData = "fundId=" + fundId + "&MODE_DATA=" + "VIEW";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		return false;
	});
	
	
	
	

});

$(function() {

	$(document).on('click', '.deleteDesignationClass', function() {
		var $link = $(this);
		var dsgid = $link.closest('tr').find('td:eq(0)').text();
		$("#grid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
		showConfirmBoxEmployee(dsgid);

	});	

});




var incrementvalue;


function deleteDataFromGrid(dsgid) {

	var url = "Designation.html?update";
	var requestData = "dsgid=" + dsgid + "&MODE1=" + "Delete";

	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	$.fancybox.close();
	$("#grid").jqGrid('setGridParam', {
		datatype : 'json'
	}).trigger('reloadGrid');
	return false;
}




function displayAddView(obj) {
	var formName = findClosestElementId(obj, 'form');

	var theForm = '#' + formName;

	var divName = childDivName;

	var requestData = __serializeForm(theForm);

	var formAction = $(theForm).attr('action');

	var url = formAction + '?create';

	var returnData = __doAjaxRequest(url, 'post', requestData, false);

	$(divName).html(returnData);

	prepareDateTag();
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

function saveLeveledDataForFundMaster(obj){
	
	var	formName =	findClosestElementId(obj, 'form');
    var theForm	=	'#'+formName;
    var requestData = __serializeForm(theForm);
    var url	=	$(theForm).attr('action');

	var response= __doAjaxRequestValidationAccor(obj,url+'?create', 'post', requestData, false, 'html');
	if(response != false){
	    $('.content').html(response);
	}
}


function displayMessageOnSubmit(successMsg){
	
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = getLocalMessage('account.proceed.btn');
	
	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
	 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="redirectToDishonorHomePage()"/></div>';
	 
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function redirectToDishonorHomePage () {
	window.location.href='AccountFundMaster.html';
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


function validateEachFieldWithFundLevels(){
	   var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
		  i = stringId.match(/[0-9]+/g);
		  var errorList= [];	
		  var  childFunLevel= $("#childFunLevel" + i).val();
		    var  parentFieldLevel= $("#parentFunLevel" + i).val();
		    var  childCode=$("#childFunCode" + i).val();
		    var childFundStatus=$("#childFundStatus" + i).val();
		    
		    var parentCode=$("#childParentCode" + i).val();
		    var desc=$("#funDesc" + i).val();
		    var  childFunLevel= $("#childFunLevel" + i).val();
		    var  parentFunLevel= $("#parentFunLevel" + i).val();
		   
		    

		    var childFinalCode = $("#childFinalCode" + i).val();

		    var requestData = "childFinalCode=" + childFinalCode + "&addOrRemoveRow=" + "addRow"; 
		    
		    var isDuplicte =__doAjaxRequest('AccountFundMaster.html?validateDuplicateCompositeCode','post',requestData,false);
		    
		    if(isDuplicte){
		    	$("#childFunLevel" + i).val('');
		    	$("#childParentFunLevel" + i).val('');
		    	$("#childFunCode" + i).val('');
		    	$("#childParentCode" + i).val('')
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
		    
		    if(childFunLevel == '0') {
		    	errorList.push("Child fund level must not be empty");
		 	}
		     
		     if(parentFieldLevel == '0') {
		    	 errorList.push("Child parent level must not be empty");
		 	}
		     if(childCode == '') {
		    	 errorList.push("Child fund code must not be empty");
		 	}
		     if(parentCode == '0') {
		    	 errorList.push("Child parent code must not be empty");
		 	}
		     if(desc == '') {
		    	 errorList.push("Child fund description must not be empty");
		 	}
		     return bindError(errorList);
}

function closeErrBox() {
	$('.error-div').hide();
	$('.warning-div').hide();
}

function validateLevel(){
	var errorList = [];
	 var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	  i = stringId.match(/[0-9]+/g);
	 	var  childFunLevel= $("#childFunLevel" + i).val();
	    var  parentFunLevel= $("#parentFunLevel" + i).val();
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



function removeRowFunction(){
	 var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	  i = stringId.match(/[0-9]+/g);
	  var errorList= [];	
	  var  childFunLevel= $("#childFunLevel" + i).val();
	    var  parentFieldLevel= $("#parentFunLevel" + i).val();
	    var  childCode=$("#childFunCode" + i).val();
	    var parentCode=$("#childParentCode" + i).val();
	    var desc=$("#funDesc" + i).val();
	    var  childFunLevel= $("#childFunLevel" + i).val();
	    var  parentFunLevel= $("#parentFunLevel" + i).val();
	   
	    

	    var childFinalCode = $("#childFinalCode" + i).val();

	    var requestData = "childFinalCode=" + childFinalCode + "&addOrRemoveRow=" + "removeRow"; 
	    
	    var isDuplicte =__doAjaxRequest('AccountFundMaster.html?validateDuplicateCompositeCode','post',requestData,false);
	
	var rowCount = $('#divId li').length;
	if(rowCount<=1){
		return false;
	}
	
	$('#ulId li').last().remove();		
}




var countForParentLevelSelection='';




$( document ).ready(function() {
	$('.error-div').hide();
	$('#statusDivMain').hide();
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
								
					var string = selKeys2 + "";
					var arrayOfSelectedData = string.split("-&gt;");
					
					var compCode = arrayOfSelectedData[0] + "";
					$('#editedChildFunCode').val(compCode.substring(compCode.lastIndexOf("-") + 1));
					var parentCompCodeIntoArray = compCode.substring(0,compCode.lastIndexOf("-")).split("-");
					$("#editedChildStatus").val(status);
					$('#editedDataChildDesc').val(arrayOfSelectedData[1].trim());
				    
				    if(compCode.substring(0,compCode.lastIndexOf("-"))==''){
						$('#editedChildFunLevel').val(parentCompCodeIntoArray.length);
					}else{
						$('#editedChildFunLevel').val(parentCompCodeIntoArray.length+1);
					}
					if(compCode.substring(0,compCode.lastIndexOf("-"))==''){
						$('#editParentNode').hide();
						$('#editedDataChildParentCode').val('');
						$('#statusDiv').hide();	
					}
						$('#editChildNode').show();
						$('#editedDataChildCompositeCode').val('');
						if(compCode.substring(0,compCode.lastIndexOf("-"))!=''){
							$('#editedDataChildParentCode').val(compCode.substring(0,compCode.lastIndexOf("-")));
							$('#editedDataChildCompositeCode').val(arrayOfSelectedData[0]);
							$('#editedDataChildParentLevel').val(parentCompCodeIntoArray.length);
							$('#editParentNode').show();	
							$('#statusDiv').show();	
						}
						$('html, body').animate({scrollTop : $("#editParentNode").offset().top}, 1000);
						
				}
				
			});
});






function getParentCode(count){
	
	countForParentLevelSelection = $('#countForParentLevel').val();
	
	 var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	 countForParentLevelSelection = stringId.match(/[0-9]+/g);
	   
	var childLevel = $('#parentFunLevel'+countForParentLevelSelection).val();
	var childCode = $('#childFunCode'+countForParentLevelSelection).val();
	
	
	var requestData = "selectLevel=" + childLevel;
	var codeWithDesc =__doAjaxRequest('AccountFundMaster.html?populateParentCode','post',requestData,false);
	var optionAsString = '';
	$("#childParentCode"+countForParentLevelSelection).find('option').remove();
	var selectmsg=getLocalMessage("budget.reappropriation.master.select");
	optionAsString += "<option value=''> "+selectmsg+"</option>";
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


function getFinalCode(count){
	
	 var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	   countForchildLevel = stringId.match(/[0-9]+/g);
var childLevel = $('#childFunLevel'+countForchildLevel).val();
	var childParentLevel = $('#parentFunLevel'+countForchildLevel).val();
						
	var childCode = $('#childFunCode'+countForchildLevel).val();
	var childParentCode = $('#childParentCode'+countForchildLevel).val();
	var childDesc = $('#funDesc'+countForchildLevel).val();
	var requestData = "childLevel=" + childLevel + "&childParentLevel=" + childParentLevel+"&childCode="+childCode+"&childParentCode="+childParentCode+"&childDesc="+childDesc;;
	var returnData =__doAjaxRequest('AccountFundMaster.html?storeChildDetails','post',requestData,false);
	$('#childFinalCode'+countForchildLevel).val(returnData);
}


function setParentValue(){
	var errorList = [];
	var childLevel = 1;
	var childParentLevel = 0;
	
	var childCode = $('#parentFundCode').val();
	var childParentCode = 0;
	var childDesc = $('#parentfunDesc').val();
	
	var maxParentCodeLenght= $("#maxParentLenght").val();
	
	if(childCode != null && childCode != ""){
	 if(childCode.length != maxParentCodeLenght) {
    	 errorList.push("Parent fund code must be "+maxParentCodeLenght+" digit");
 	}else{
 		$('#parentFinalCode').val(childCode);
 	 }
	}
	
	var requestData = "childLevel=" + childLevel + "&childParentLevel=" + childParentLevel+"&childCode="+childCode+"&childParentCode="+childParentCode+"&childDesc="+childDesc;
	
	$("#childParentCode0").append('');
	
	var returnData =  __doAjaxRequest('AccountFundMaster.html?storeChildDetails','post',requestData);
	$('#parentFinalCode').val(childCode);
	
	 	if(errorList.length>0){
			$('#parentFundCode').val('');
			$('#parentFinalCode').val('');
			
			 return bindError(errorList);
		}else{
			$('.warning-div').hide();
		}
}


function saveLeveledDataForPrimaryCode(obj){
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
	    
	    if(errorList.length > 0){
	    	
	    }
	   
	    
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	var requestData = {};
	
		
	requestData = __serializeForm(theForm);

	var url	=	$(theForm).attr('action')+'?' + 'create';
	var returnData =__doAjaxRequest(url,'GET',requestData,false);
	
	$('.content').html(returnData);
	
	prepareDateTag();
}

function saveEditedDataForFundMaster(obj){
			var	formName =	findClosestElementId(obj, 'form');
			var theForm	=	'#'+formName;
			var requestData = {};
			requestData = __serializeForm(theForm);
			var url	=	$(theForm).attr('action')+'?' + 'saveEditedData';
			
			var response= __doAjaxRequestValidationAccor(obj,url, 'post', requestData, false, 'html');
		    if(response != false){
		       $('.content').html(response);
		    }
}


function addRowFunction (){
	var noError = true; //validateEachFieldWithFundLevels();
	if(!noError){
		return false;
	}else{
  
		  var content = $('#divId li:last').clone();
		  content.append("<script>$(document).ready(function(){$('.hasNumber').keyup(function () {" 
				  +"this.value = this.value.replace(/[^0-9]/g,'');"+
		             "}); });</script>");
		  
		  $("#divId ul > li").last().after(content);
		  
		  var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
		  count = stringId.charAt(stringId.length-1);
		  
		  incrementvalue=++count;
		  
		  content.find("select").val("0");
		  content.find("input:text").val("")
		  
		   var id="#ulId";
		   
		   content.find('input:text:eq(0)').attr({'path': 'listDto['+incrementvalue+'].childCode' }).attr({'name': 'listDto['+incrementvalue+'].childCode' }).attr({'id': 'childFunCode'+incrementvalue+'' }).addClass("hasNumber");
		   content.find('input:text:eq(1)').attr({'path': 'listDto['+incrementvalue+'].childDesc'}).attr({'name': 'listDto['+incrementvalue+'].childDesc' }).attr({'id': 'childFundDesc'+incrementvalue+'' });
		   content.find('input:text:eq(2)').attr({'path': 'listDto['+incrementvalue+'].childFinalCode'}).attr({'name': 'listDto['+incrementvalue+'].childFinalCode' }).attr({'id': 'childFinalCode'+incrementvalue+'' });
		   content.find('select:eq(0)').attr({'path': 'listDto['+incrementvalue+'].childLevelCode'}).attr({'name': 'listDto['+incrementvalue+'].childLevelCode' }).attr({'id': 'childFunLevel'+incrementvalue+'' });
		   content.find('select:eq(1)').attr({'path': 'listDto['+incrementvalue+'].childParentLevelCode'}).attr({'name': 'listDto['+incrementvalue+'].childParentLevelCode' }).attr({'id': 'parentFunLevel'+incrementvalue+'' });
		   content.find('select:eq(2)').attr({'path': 'listDto['+incrementvalue+'].childParentCode' }).attr({'name': 'listDto['+incrementvalue+'].childParentCode' }).attr({'id': 'childParentCode'+incrementvalue+'' }).attr({'value': ''}).addClass("hasNumber");
	       content.find('select:eq(3)').attr({'path': 'listDto['+incrementvalue+'].childFundStatus' }).attr({'name': 'listDto['+incrementvalue+'].childFundStatus' }).attr({'id': 'childFundStatus'+incrementvalue+'' });
		  
	       content.find(".hideDivClass").attr("id" ,"statusDivAdd" + incrementvalue);
	       content.find('label').closest('.error').remove(); //for removal duplicate
	       
	       content.find('#childFunCode'+incrementvalue).attr("onchange", "clearParentCode(" + (incrementvalue) + ")");
	       
	       content.find('input:hidden:eq(4)').attr({'name':'listDto['+incrementvalue+'].childFundStatus'}).attr({'id':'hiddenChildFundStatus'+incrementvalue});;
	       $("#statusDivAdd"+incrementvalue).hide();
	       $('#count').val(count);
 
	}
}


function updateDescForFundMaster(obj){
	
	var errorList=[];
	
	if($('#editedDataChildDesc').val().trim().length<1){
		errorList.push('Please Enter Description');
	}
	var editedChildStatus=$("#editedChildStatus").val();
	
	if(editedChildStatus == '0' || editedChildStatus == '') {
		  errorList.push("Please select Chilld function status");
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
		
		
		var redirectUrl = 'AccountFundMaster.html';
		var message="";
	 
		 message	+='<h5 class="text-blue-2 text-center padding-15">'+ 'Record Updated Successfully'+'</h5>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\'OK\'  id=\'btnNo\' class=\'btn btn-success\'    '+ 
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
	
	return false;
	}
}

function fetchMaxLengthAndResetChildParentLevel(){
	 $("#statusDivMain").show();
	var levelCodeForChildField;
	var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	var incrementedValue = stringId.match(/[0-9]+/g);
	
	 $("#parentFunLevel"+incrementedValue).find('option').remove();
	 var optionAsString = '';
	 var selectedLevel = $('#childFunLevel'+incrementedValue).find(":selected").val()-1;
	 var text = $('#childFunLevel'+incrementedValue).find('option[value='+selectedLevel+']').text();
	 var optionAsString2 = '';
	 optionAsString2 += "<option value=''> Select </option>";
	 
	 optionAsString2 += "<option value='"+ selectedLevel +"'>" + text +"</option>";
	 $('#parentFunLevel'+incrementedValue).append(optionAsString2);
	
	
	$('#childFunCode'+incrementedValue).val('');
	$('#parentFunLevel'+incrementedValue).val(0);
 	levelCodeForChildField  = $('#childFunLevel'+incrementedValue).find(":selected").val();
 	var url = 'AccountFundMaster.html?getCodeDigits';
 	var requestData = "selectValue=" + levelCodeForChildField;
 	var digitForChildFieldCode=__doAjaxRequest(url,'post',requestData,false);
 	
 	$('#childFunCode'+incrementedValue).val('');
 	$('#childFunCode'+incrementedValue).attr({'maxlength':digitForChildFieldCode[0]}) 	 
 	
 		if(digitForChildFieldCode[1]==1){
		$("#statusDivAdd"+incrementedValue).show();
		$("#childFundStatus"+incrementedValue).val(digitForChildFieldCode[2]);
		$("#hiddenChildFundStatus"+incrementedValue).val(digitForChildFieldCode[2]);
	}else{
		$("#statusDivAdd"+incrementedValue).hide();
	  }
	 	 			
}


function clearParentCode(cont){
	
	var errorList= [];	
	
	var parentCode = $('#parentFundCode').val();
	if(parentCode == null || parentCode == ""){
		 errorList.push("Please enter parent fund code");
		 $('#childFunCode'+cont).val('');
	}
	
	var childFunLevel = $('#childFunLevel'+cont).val();
	if(childFunLevel == null || childFunLevel == ""){
		 errorList.push("Please select child fund Level");
		 $('#childFunCode'+cont).val('');
	}
	
	levelCodeForChildField  = $('#childFunLevel'+cont).find(":selected").val();
 	var url = 'AccountFundMaster.html?getCodeDigits';
 	var requestData = "selectValue=" + levelCodeForChildField;
 	
 	var digitForChildFieldCode=__doAjaxRequest(url,'post',requestData,false);
 	
 	var childFunCode = $('#childFunCode'+cont).val();
 	var maxChildFieldCode = digitForChildFieldCode[0];
 	
 	if(childFunCode != null && childFunCode != ""){
 	if(childFunCode.length != maxChildFieldCode) {
    	 errorList.push("Child fund code must be "+maxChildFieldCode+" digit");
    	 $('#childFunCode'+cont).val('');
 		}
 	}
 	
 	if(errorList.length > 0){
		return bindError(errorList);
	}
 	
	$('#childParentCode'+cont).val('');
	$('#childFinalCode'+cont).val('');
	
}


