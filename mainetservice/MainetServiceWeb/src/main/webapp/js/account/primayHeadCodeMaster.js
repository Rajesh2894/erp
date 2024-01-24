
$(function() {		
	$(document).on('click', '.createPrimaryCode', function() {
		var $link = $(this);
		var url = "PrimaryAccountCodeMaster.html?showLeveledform";
		var returnData =__doAjaxRequest(url,'post','',false);
		$('.content').html(returnData);
	    prepareDateTag();
	    $("#createSaveDiv").hide();	
	    $("#updateBtn").hide();
	    $("#addModeDiv").hide();
		return false;	
	});
});


$(function() {		
	$(document).on('click', '.editPrimaryCode', function() {
		var $link = $(this);
		var url = "PrimaryAccountCodeMaster.html?showLeveledformForEdit";
		var returnData =__doAjaxRequest(url,'post','',false);
		$('.content').html(returnData);
	    prepareDateTag();
		return false;	
	});
	
});


function closeErrBox() {
	$('.warning-div').hide();
}

$( document ).ready(function() {
	var errorList=[];
	$('.error-div').hide();
	$('#editChildNode').hide();
	$('#editParentNode').hide();
	$('#saveButttonOnDescChange').hide();
	$("#childFunLevel0 option[value='1']").hide();
	$('#addDelete').hide();
	var totalLevel=$("#levelData").val();
	
  var mode = $("#modeFlag").val();
  if(mode=='' || mode=="ADD")	{
$("#tree").dynatree(
			{
	checkbox : true,
	classNames : {
		checkbox : "dynatree-radio"
	}, 
	selectMode : 1,
	onSelect : function(select, node) {
		$('.warning-div').hide();
		$('#superNode').attr('checked', false);
		$("#mainBackBtn").hide();
		$('fieldset.clear').show();
		$("#accordion_single_collapse").show();
		$("#addModeDiv").show();
		$("#superParentDiv").hide();
		$("#addDelete").show();
		$("#saveForSuper").hide();
		$("#saveForChild").show();
		var status = $.map(node.tree.getSelectedNodes(),
			 function(node){
				return node.data.href
			     });
		var selKeys2 = $.map(node.tree.getSelectedNodes(),
				function(node) {
					return node.data.title;
				});
		var string = selKeys2 + "";
		var arrayOfSelectedData = string.split("-&gt;");
		var parentCompCode = arrayOfSelectedData[0] + ""; 
		var compoCodeLength=parentCompCode.trim().length;
		var str = status + "";
		var selectedIndex= str.split("-");
		var superParentId = selectedIndex[0]+"";
	    var	pacHeadId = selectedIndex[1]+"";
		var status  = selectedIndex[2]+"";
		var headType  = selectedIndex[3]+"";
		var selectedLevel;
		var parentCompCodeIntoArray = parentCompCode.substring(0,parentCompCode.lastIndexOf("-")).split("-");
		if(parentCompCodeIntoArray==''){
			selectedLevel=(parentCompCodeIntoArray.length);}
		   else{
			  selectedLevel=(parentCompCodeIntoArray.length)+1;
		   }
		if(parseInt(selectedLevel)===parseInt(totalLevel) ||parentCompCode===''){
			$('fieldset.clear').hide();
			$("#mainBackBtn").show();
		}

		var errorList=[];
		$("#childHeadType").val(headType);
		$("#hiddenParentCode").val(parentCompCode.trim());
		$("#childFunCode0").val("")
		$("#funDesc0").val("")
		$("#childFinalCode0").val("")
		
		if(mode=="ADD"){
		  var url = 'PrimaryAccountCodeMaster.html?getCodeDigits';
		  var lvl =(selectedLevel+1);
		  var requestData = "selectValue=" + lvl+"&compositeCode=" + "";
	      var maxLength=__doAjaxRequest(url,'post',requestData,false);
		 $(".mxlength").attr("maxlength", maxLength[0]);
		 }
		if(superParentId==''){
			var pacHeadId = selectedIndex[1];
			$("#childFunLevel0").val((selectedLevel+1)).prop('disabled',true);
			$("#hiddenCodeLevel").val((selectedLevel+1));
			$("#hiddenParentId").val(pacHeadId)

		}else if (superParentId !=0 ||superParentId !=''){
			$("#childFunLevel0").val((selectedLevel+1)).prop('disabled',true);
			$("#hiddenCodeLevel").val((selectedLevel+1));
			$("#hiddenParentId").val(superParentId)
		}
					
	},
	});
 }

if(mode=="EDIT"){
$('#mainBackBtn').show();
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
					$("#mainBackBtn").hide();
					$('#editModeDiv').show();

					var selKeys2 = $.map(node.tree.getSelectedNodes(),
									function(node) {
										return node.data.title;
									});
				
					var status = $.map(node.tree.getSelectedNodes(),
						 function(node){
					        	return node.data.href
						     });
					var str = status + "";
					var selectedIndex= str.split("-");
					var superParentId = selectedIndex[0];
					
				    var	pacHeadId = selectedIndex[1];
					var editedChildFunCode  = selectedIndex[4]+"";
					var string = selKeys2 + "";
					var arrayOfSelectedData = string.split("-&gt;");
				
					var parentCompCode = arrayOfSelectedData[0] + "";
					
				    var parentCompCodeIntoArray = parentCompCode.substring(0,parentCompCode.lastIndexOf("-")).split("-");
					
					if(parentCompCodeIntoArray==''){
						selectedLevel=(parentCompCodeIntoArray.length);
						}
					else{
					   selectedLevel=(parentCompCodeIntoArray.length)+1;
			 		   }
			                 

					if(parentCompCode.substring(0,parentCompCode.lastIndexOf("-"))==''){
						$("#hiddenParentCode").val(null);
					}
		     	if(parentCompCode.substring(0,parentCompCode.lastIndexOf("-"))!=''){
						$("#hiddenParentCode").val(parentCompCode.substring(0,parentCompCode.lastIndexOf("-")));
					}
					$("#hiddenParentCode").val(parentCompCode);

				$("#editedDataChildCompositeCode").val(parentCompCode.trim());
					
					if(superParentId==''){
						var pacHeadId = selectedIndex[1];
						$("#editedChildFunLevel").val(selectedLevel).prop('disabled',true);
						$("#hiddenCodeLevel").val(selectedLevel);
						$("#hiddenParentId").val(pacHeadId)
		
					}else if (superParentId !=0 ||superParentId !=''){
						$("#editedChildFunLevel").val(selectedLevel).prop('disabled',true);
						$("#hiddenCodeLevel").val(selectedLevel);
						$("#hiddenParentId").val(superParentId)
 					}
					$('#editedChildFunCode').val(editedChildFunCode);
					$('#editedDataChildDesc').val(arrayOfSelectedData[1].trim());
					$("#editedChildStatus").val(selectedIndex[2]);
					$("#budgetCheckFlag").val(selectedIndex[5]);
					 $('#editedChildFunLevel').val(selectedLevel);

					 requestData = {
							"headId":selectedIndex[1]
					 };
					 var ajaxResponse = doAjaxLoading(
							    'PrimaryAccountCodeMaster.html?getHeadCodeDescReg',
							    requestData, 'json');
					 $('#childDescReg').val(ajaxResponse);
					 
				 	$('#editChildNode').show();
						if($("#editedChildFunLevel").val()==$('#editedChildFunLevel option:last-child').val())
							{
							$('#statusDiv').show();	
							$('#budgetFlagDiv').show();
							}
						else
							{
							$("#statusDiv").hide();
							$('#budgetFlagDiv').hide();
							}
						
						$('html, body').animate({scrollTop : $("#editParentNode").offset().top}, 1000);
						
				}
				
			});
}
});


$("#superNode").change(function(){
	var ischecked= $(this).is(':checked');
	if(ischecked){
		
		$("#tree").dynatree("getRoot").visit(function(node){
			   node.select(false);
			  });	
		$('#superNode').prop('checked',true);
		$('fieldset.clear').hide();
		$("#addModeDiv").show();
		$("#superParentDiv").show();
		$("#saveForSuper").show();
		$("#addDelete").hide();
	    $("#mainBackBtn").hide();
	    $("#saveForChild").hide();
	    $("#codeDesReg").show();
       }else{
    	   $("#addModeDiv").hide();
    	   $("#mainBackBtn").show();
		}

});



var incrementLevel;
function setSecondaryData(){

	 var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	 incrementLevel = stringId.match(/[0-9]+/g);
	 var childDesc= $("#funDesc"+incrementLevel).val().trim();
	 $("#secondaryCodeDesc"+incrementLevel).val("0001-"+childDesc);
}


var incrementvalue;
function fetchMaxLengthAndResetChildParentLevel(){
	$('.warning-div').hide();
	var errorList=[];
	var levelCodeForChildField;
	 var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	 incrementvalue = stringId.match(/[0-9]+/g);
	 var prntCode= $("#hiddenParentCode").val().trim();
	 var childCode= $("#childFunCode"+incrementvalue).val();
	 var selectedLevel = $('#childFunLevel'+incrementvalue).find(":selected").val();
	 var compositeCode= (prntCode.trim()+"-"+childCode.trim());
	 var url = 'PrimaryAccountCodeMaster.html?getCodeDigits';
	 var requestData = "selectValue=" + selectedLevel+"&compositeCode=" + compositeCode;
     var childFieldDetails=__doAjaxRequest(url,'post',requestData,false);

	 if(childFieldDetails[0] != childCode.length){
			errorList.push("Primary Account Code length should be "+childFieldDetails[0]+" digit");
		}
	 
	 if(childFieldDetails[1]==1){
		 errorList.push("Primary Account Code "+childCode+" already present");
	  }
	 
	 if(errorList.length>0){
		 $('#childFunCode'+incrementvalue).val('');
		 $('#childFinalCode'+incrementvalue).val('');		// $("#statusDivMain").hide();
		 return bindError(errorList);
		
	 }else{
		 $('#childFinalCode'+incrementvalue).val(prntCode.trim()+"-"+childCode.trim());
	}

}

function saveParentPrimaryCode(obj){
	var errorList = [];
	var cpdOtherAcHeadTypes = $("#cpdOtherAcHeadTypes").val();
	var parentCode =  $("#parentCode").val();
	var parentfunDesc=  $("#parentfunDesc").val();
	var maxParentCodeLenght= $("#maxParentLenght").val();
	
	 if(cpdOtherAcHeadTypes == '') {
    	 errorList.push(getLocalMessage('account.select.head.type'));
 	}
	 if(parentCode == '') {
    	 errorList.push(getLocalMessage('account.code.not.empty'));
 	}
	 if(parentfunDesc == '') {
    	 errorList.push(getLocalMessage('account.description.not.empty'));
 	}
	 
	 if(parentCode.length != maxParentCodeLenght) {
    	 errorList.push(getLocalMessage('account.parent.code') + ' ' +maxParentCodeLenght+ ' ' +getLocalMessage('account.digit'));
 	}
   if(errorList.length>0){
	 return bindError(errorList);
	}
	else
	{
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
    var url	=	$(theForm).attr('action')+'?' + 'createParent';
    var returnData =__doAjaxRequest(url,'post',requestData,false);
    var obj=$(returnData).find('#hasError'); 
    if(obj.val()=='Y')
    	{
    	$('.content').html(returnData);
    	$('fieldset.clear').hide();
    	$("#mainBackBtn").hide();
    	$("#parentCode").val("");
    	$("#cpdOtherAcHeadTypes").val("");
    	prepareDateTag();
    	}
    else{
    	showConfirmSaveBox();
    	}
}
}

function saveChildPrimaryCode(obj){
	 var noError =  validateEachField();
		if(!noError){
			return false;
	}else
	{
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	var requestData = __serializeForm(theForm);
    var url	=	$(theForm).attr('action')+'?' + 'createChild';
    var returnData =__doAjaxRequest(url,'post',requestData,false);
    var obj=$(returnData).find('#hasError'); 
    if(obj.val()=='Y')
    	{
    	$('.content').html(returnData);
    	prepareDateTag();
    	}else{
    		showConfirmSaveBox();
    	}
}
}

function showConfirmBox(){
   var redirectUrl = 'PrimaryAccountCodeMaster.html';
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
    	
}

function showConfirmSaveBox(){
	   var redirectUrl = 'PrimaryAccountCodeMaster.html';
	   var succ = getLocalMessage('account.journal.voucher.record.success');
	   var ok = getLocalMessage('account.ok');
	   var message="";
		 message	+='<h5 class="text-blue-2 text-center padding-15">'+ succ +'</h5>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\' '+ ok + '\'  id=\'btnNo\' class=\'btn btn-success\'    '+ 
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

function resetParentLevel(count){
	$('#childParentFunLevel'+$('#count').val()).val(0);
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

function setParentValue(){
	var errorList = [];
	var childLevel = 1;
	var childParentLevel = 0;
	var parentCode = $('#parentCode').val();
	var firstDigit = String(parentCode).charAt(0);
	var cpdOtherAcHeadTypes = $('#cpdOtherAcHeadTypes').val();
	var maxParentCodeLenght= $("#maxParentLenght").val();
	
	if(firstDigit != cpdOtherAcHeadTypes){
		errorList.push("Invalid Head Type and Primarycode Combination");
	}  
	 if(parentCode.length != maxParentCodeLenght) {
    	 errorList.push("Parent code must be "+maxParentCodeLenght+" digit");
 	}
	 
	if(errorList.length==0){
	  var requestData = "parentCode=" + parentCode ;
      var isDuplicateCode =__doAjaxRequest('PrimaryAccountCodeMaster.html?validateParentCode','POST',requestData,false);
	   if(isDuplicateCode) {
		errorList.push(getLocalMessage('Primary Account code '+parentCode+' already present'));
	}
	}
	 if(errorList.length>0){
		$('#parentCode').val(cpdOtherAcHeadTypes);
		 return bindError(errorList);
	}else{
		$('.warning-div').hide();
	}
}


function setHeadType(){
	var cpdOtherAcHeadTypes = $('#cpdOtherAcHeadTypes').val();
	$('#parentCode').val(cpdOtherAcHeadTypes);	
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



function updateDesc(obj){
	var errorList=[];
	var editedChildStatus=$("#editedChildStatus").val();
	var budgetCheckFlag=$("#budgetCheckFlag").val();
	
	if($('#editedDataChildDesc').val().trim().length<1){
		errorList.push('Please Enter Description');
	}
	if(editedChildStatus == '0' || editedChildStatus == null || editedChildStatus == '') {
		  errorList.push("Please select child status");
	}
	if(budgetCheckFlag == '0' || budgetCheckFlag == null || budgetCheckFlag == '') {
		  errorList.push("Please select child budget check Flag");
	}
	if(errorList.length>0){
		 return bindError(errorList);
	}
	
	else{
		var	formName =	findClosestElementId(obj, 'form');
		var theForm	=	'#'+formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var url	=	$(theForm).attr('action')+'?' + 'createChild';
		var returnData =__doAjaxRequest(url,'post',requestData,false);
	   var obj=$(returnData).find('#hasError'); 
	   if(obj.val()=='Y')
	   {
		 $('.content').html(returnData);
		    prepareDateTag();
		}else{
	    	 showConfirmBox();
	    	}
		  
	return false;
	}
}

function addRowFunction (){

	var noError = validateEachField();
	if(!noError){
		return false;
	}else{
		  var content = $('#divId li:last').clone();
		  content.append("<script>$(document).ready(function(){$('.hasNumber').keyup(function () {" 
				  +"this.value = this.value.replace(/[^0-9]/g,'');"+
		             "}); });</script>");
		  $("#divId ul > li").last().after(content);
		    var previousContent = $('#divId li:last');
			var ChildCodeId = previousContent.find('input:text:eq(0)').attr('id');
			var chidlLevel = previousContent.find('select:eq(0)').attr('id');
			var chidlParentLevel = previousContent.find('select:eq(1)').attr('id');
			$('#'+ChildCodeId).prop( "readonly", false );
			$('#'+chidlLevel).prop( "readonly", true );
		  content.find("input:text").val("")
		  var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
		  count = stringId.match(/[0-9]+/g);
		  incrementvalue=++count;
		   var id="#ulId";
		   content.find('select:eq(0)').attr({'path': 'listDto['+incrementvalue+'].childLevel'}).attr({'name': 'listDto['+incrementvalue+'].childLevel' }).attr({'id': 'childFunLevel'+incrementvalue });
		   content.find('input:text:eq(0)').attr({'path': 'listDto['+incrementvalue+'].childCode' }).attr({'name': 'listDto['+incrementvalue+'].childCode' }).attr({'id': 'childFunCode'+incrementvalue}).addClass("hasNumber");
		   content.find('input:text:eq(1)').attr({'path': 'listDto['+incrementvalue+'].childFinalCode'}).attr({'name': 'listDto['+incrementvalue+'].childFinalCode' }).attr({'id': 'childFinalCode'+incrementvalue});   
		   content.find('input:text:eq(2)').attr({'path': 'listDto['+incrementvalue+'].childDesc'}).attr({'name': 'listDto['+incrementvalue+'].childDesc' }).attr({'id': 'funDesc'+incrementvalue});
		   content.find('select:eq(1)').attr({'name': 'listDto['+incrementvalue+'].childPrimaryStatus' }).attr({'id': 'childPrimaryStatus'+incrementvalue });
		      $('#count').val(count);
		      $("#childFunLevel"+incrementvalue).val($("#hiddenCodeLevel").val());
	}
  
}

function validateEachField(){
	   var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
		  i = stringId.match(/[0-9]+/g);
		  var errorList= [];	
		  
		    var  childFieldLevel= $("#childFunLevel" + i).val();
		    var  childCode=$("#childFunCode" + i).val();
		    var desc=$("#funDesc" + i).val();
		    var childFinalCode = $("#childFinalCode" + i).val();
		    var childPrimaryStatus=$("#childPrimaryStatus" + i).val();
		    var  childFunLevel= $("#childFunLevel" + i).val();
		    if(childFieldLevel == '0') {
		    	errorList.push("Child field level must not be empty");
		 	}
		     if(childCode == '') {
		    	 errorList.push("Child field code must not be empty");
		 	}
		     if(desc == '') {
		    	 errorList.push("Child field description must not be empty");
		 	}
		     return bindError(errorList);
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
	    var rowCount = $('#divId li').length;
	   if(rowCount<=1){
		return false;
	   }
	
	$('#ulId li').last().remove();
}

