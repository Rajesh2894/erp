$(function() {
	$("#primaryAccountCodeMasterGrid").jqGrid(
			{
				url : "AccountHeadPrimaryAccountCodeMaster.html?getGridData",datatype : "json",mtype : "POST",colNames : [ '',getLocalMessage('accounts.master.primAccCode'),getLocalMessage('accounts.master.primAccDesc'),getLocalMessage('accounts.add.child'),getLocalMessage('accounts.vieworedit')],
				colModel : [ {name : "primaryAcHeadId",width :20,sortable :  false,searchoptions: { "sopt": [ "eq"] },hidden:true  },
				             {name : "primaryAcCodeHeadCode",width : 20,sortable :  false,searchoptions: { "sopt": [ "eq"] }  },
				             {name : "primaryAcCodeHeadDesc",width : 75,sortable : true,searchoptions: { "sopt": ["bw", "eq"] }}, 
				             {name : 'primaryAcHeadId',index : 'primaryAcHeadId',width : 20,align : 'center',formatter : returnEditUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}, 
				             {name : 'primaryAcHeadId',index : 'primaryAcHeadId',width : 20,align : 'center',formatter : returnViewUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}
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
				caption : 'Primary Account Code'
			});
	 jQuery("#primaryAccountCodeMasterGrid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	 $("#pagered_left").css("width", "");
});


function returnEditUrl(cellValue, options, rowdata, action) {
	
    return "<a href='#'  return false; class='editClass' value='"+rowdata.spId+"' ><img src='css/images/edit.png' width='20px' alt='Edit Charge Master' title='Edit Scrutiny Data' /></a>";
}

function returnViewUrl(cellValue, options, rowdata, action) {
	
	return "<a href='#'  return false; class='viewPrimaryMasterGridClass' value='"+rowdata.spId+"'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}


$(function() {		
	$(document).on('click', '.addFunctionMasterClass', function() {
		var $link = $(this);
		
		var url = "AccountHeadPrimaryAccountCodeMaster.html?showLeveledform";
		
		var returnData =__doAjaxRequest(url,'post','',false);
		
		$('.content').html(returnData);
	    prepareDateTag();
	    
	    $("#statusDivMain").hide();
		return false;	
	});
	
});


$(function() {		
	$(document).on('click', '.editClass', function() {
		var $link = $(this);
		var funId = $link.closest('tr').find('td:eq(0)').text();
		var url = "AccountHeadPrimaryAccountCodeMaster.html?editGridData";
		var requestData = "functionId=" + funId + "&MODE=" + "EDIT";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		$('.content').html(returnData);
		$("#statusDivMain").hide();
		prepareDateTag();
			
	});
		
});

$(function() {		
	$(document).on('click', '.viewPrimaryMasterGridClass', function() {
		var $link = $(this);
		var funId = $link.closest('tr').find('td:eq(0)').text();
		var url = "AccountHeadPrimaryAccountCodeMaster.html?editGridData";
		var requestData = "functionId=" + funId + "&MODE=" + "VIEW";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		$('.content').html(returnData);
		
		prepareDateTag();
			
	});
		
});

function closeErrBox() {
	$('.warning-div').hide();
}

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
					
				
					var status = $.map(node.tree.getSelectedNodes(),
						 function(node){
					        	return node.data.href
						     });
										
					var string = selKeys2 + "";
					var arrayOfSelectedData = string.split("-&gt;");

					var compCode = arrayOfSelectedData[0] + "";
					$('#editedChildFunCode').val(compCode.substring(compCode.lastIndexOf("-") + 1));
					var parentCompCodeIntoArray = compCode.substring(0,compCode.lastIndexOf("-")).split("-");
					$('#editedDataChildDesc').val(arrayOfSelectedData[1].trim());
					$("#editedChildStatus").val(status);
					 if(compCode.substring(0,compCode.lastIndexOf("-"))==''){
						 $('#editedChildFunLevel').val(parentCompCodeIntoArray.length); 
					 }
					 else{
						 $('#editedChildFunLevel').val(parentCompCodeIntoArray.length+1); 
					 }
					if(compCode.substring(0,compCode.lastIndexOf("-"))==''){
						$('#editParentNode').hide();
						$('#editedDataChildParentCode').val(null);
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



function attachCodeInArray(count){
	var childLevel = $('#childFunLevel'+count).val();
	var childCode = $('#childCode'+count).val();
	var requestData = "selectLevel=" + childCode;
	requestData = __serializeForm(theForm);
	var objArray =__doAjaxRequest('AccountHeadPrimaryAccountCodeMaster.html','post',requestData,false);
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

var countForParentLevelSelection='';



var incrementvalue;
function fetchMaxLengthAndResetChildParentLevel(){
	$("#statusDivMain").show();

	var levelCodeForChildField;
	 var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	 incrementvalue = stringId.match(/[0-9]+/g);
	// incrementvalue = cnt
	 $("#childParentFunLevel"+incrementvalue).find('option').remove();
	 var optionAsString = '';
	 var selectedLevel = $('#childFunLevel'+incrementvalue).find(":selected").val()-1;
	 var text = $('#childFunLevel'+incrementvalue).find('option[value='+selectedLevel+']').text();
	 var optionAsString2 = '';
	 optionAsString2 += "<option value='"+0+"'> Select </option>";
	 
	 optionAsString2 += "<option value='"+ selectedLevel +"'>" + text +"</option>";
	 $('#childParentFunLevel'+incrementvalue).append(optionAsString2);
	 
	 
		$('#childParentFunLevel'+incrementvalue).val(0);
		levelCodeForChildField  = $('#childFunLevel'+incrementvalue).find(":selected").val();
		var url = 'AccountHeadPrimaryAccountCodeMaster.html?getCodeDigits';
		var requestData = "selectValue=" + levelCodeForChildField;
		var digitForChildFieldCode=__doAjaxRequest(url,'post',requestData,false);
		
		$('#childFunCode'+incrementvalue).val('');
		$('#childFunCode'+incrementvalue).attr({'maxlength':digitForChildFieldCode[0]});
	
		if(digitForChildFieldCode[1]==1){
			$("#statusDivAdd"+incrementvalue).show();
			$("#childPrimaryStatus"+incrementvalue).val(digitForChildFieldCode[2]);
			$("#hiddenChildPrimaryStatus"+incrementvalue).val(digitForChildFieldCode[2]);
		}else{
			$("#statusDivAdd"+incrementvalue).hide();
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
	    if(errorList.length>0){
			 return bindError(errorList);
		}else{
	    
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	var requestData = {};
	
		
	requestData = __serializeForm(theForm);

	var url	=	$(theForm).attr('action')+'?' + 'create';
	var returnData =__doAjaxRequest(url,'post',requestData,false);
	
	$('.content').html(returnData);
	
	prepareDateTag();}
}


function saveEditedData(obj){
	 var noError = validateEachField();
		if(!noError){
			return false;
		}else{
			var	formName =	findClosestElementId(obj, 'form');
			var theForm	=	'#'+formName;
			var requestData = {};
			requestData = __serializeForm(theForm);
			var url	=	$(theForm).attr('action')+'?' + 'saveEditedData';
			var returnData =__doAjaxRequest(url,'post',requestData,false);
			$('.content').html(returnData);
			prepareDateTag();
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
	var childLevel = 1;
	var childParentLevel = 0;
	
	var childCode = $('#parentCode').val();
	var childParentCode = 0;
	var childDesc = $('#parentfunDesc').val();
	var requestData = "childLevel=" + childLevel + "&childParentLevel=" + childParentLevel+"&childCode="+childCode+"&childParentCode="+childParentCode+"&childDesc="+childDesc;
	
	$("#childParentCode0").append('');
	
	var returnData =  __doAjaxRequest('AccountHeadPrimaryAccountCodeMaster.html?storeChildDetails','post',requestData);
	$('#parentFinalCode').val(childCode);
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



function updateDesc(obj){
	
	var errorList=[];
	var editedChildStatus=$("#editedChildStatus").val();
	
	if($('#editedDataChildDesc').val().trim().length<1){
		errorList.push('Please Enter Description');
	}

	if(editedChildStatus == '0') {
		  errorList.push("Please select child status");
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
		
		
		var redirectUrl = 'AccountHeadPrimaryAccountCodeMaster.html';
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
			$('#'+chidlParentLevel).prop( "readonly", true );
		  content.find("input:text").val("")
		  var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
		  count = stringId.match(/[0-9]+/g);
		  
		  
		  incrementvalue=++count;
		 
		   var id="#ulId";
		   content.find('select:eq(0)').attr({'path': 'listDto['+incrementvalue+'].childLevel'}).attr({'name': 'listDto['+incrementvalue+'].childLevel' }).attr({'id': 'childFunLevel'+incrementvalue });
		   content.find('select:eq(1)').attr({'path': 'listDto['+incrementvalue+'].childParentLevel'}).attr({'name': 'listDto['+incrementvalue+'].childParentLevel' }).attr({'id': 'childParentFunLevel'+incrementvalue });
		   content.find('input:text:eq(0)').attr({'path': 'listDto['+incrementvalue+'].childCode' }).attr({'name': 'listDto['+incrementvalue+'].childCode' }).attr({'id': 'childFunCode'+incrementvalue}).addClass("hasNumber");
		   content.find('input:text:eq(1)').attr({'path': 'listDto['+incrementvalue+'].childDesc'}).attr({'name': 'listDto['+incrementvalue+'].childDesc' }).attr({'id': 'funDesc'+incrementvalue});
		   content.find('select:eq(2)').attr({'path': 'listDto['+incrementvalue+'].childParentCode'}).attr({'name': 'listDto['+incrementvalue+'].childParentCode' }).attr({'id': 'childParentCode'+incrementvalue}).attr({'value': ''}).addClass("hasNumber");
		   content.find('input:text:eq(2)').attr({'path': 'listDto['+incrementvalue+'].childFinalCode'}).attr({'name': 'listDto['+incrementvalue+'].childFinalCode' }).attr({'id': 'childFinalCode'+incrementvalue});   
		   content.find('select:eq(3)').attr({'name': 'listDto['+incrementvalue+'].childPrimaryStatus' }).attr({'id': 'childPrimaryStatus'+incrementvalue });
		   content.find(".hideDivClass").attr("id" ,"statusDivAdd" + incrementvalue);
		   content.find('input:hidden:eq(2)').attr({'name': 'listDto['+incrementvalue+'].childPrimaryStatus' }).attr({'id':'hiddenChildPrimaryStatus'+incrementvalue});
			  $("#statusDivAdd"+incrementvalue).hide();
		      $('#count').val(count);
		     
	}
  
}



function validateEachField(){
	   var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
		  i = stringId.match(/[0-9]+/g);
		  var errorList= [];	
		  var  childFieldLevel= $("#childFunLevel" + i).val();
		    var  parentFieldLevel= $("#childParentFunLevel" + i).val();
		    var  childCode=$("#childFunCode" + i).val();
		    var parentCode=$("#childParentCode" + i).val();
		    var desc=$("#funDesc" + i).val();
		    var childFinalCode = $("#childFinalCode" + i).val();

		    var childPrimaryStatus=$("#childPrimaryStatus" + i).val();
		    
		    var requestData = "childFinalCode=" + childFinalCode + "&addOrRemoveRow=" + "addRow"; 
		    
		    var isDuplicte =__doAjaxRequest('AccountHeadPrimaryAccountCodeMaster.html?validateDuplicateCompositeCode','post',requestData,false);
		    
		    if(isDuplicte){
		    	$("#childFunLevel" + i).val('');
		    	$("#childParentFunLevel" + i).val('');
		    	$("#childFunCode" + i).val('');
		    	$("#childParentCode" + i).val('')
		    	$("#childFinalCode" + i).val('');
		    	
		    errorList.push("Final Code Already Exist.");
		    }
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
		    
		    if(childFieldLevel == '0') {
		    	errorList.push("Child field level must not be empty");
		 	}
		     
		     if(parentFieldLevel == '0') {
		    	 errorList.push("Child parent level must not be empty");
		 	}
		     if(childCode == '') {
		    	 errorList.push("Child field code must not be empty");
		 	}
		     if(parentCode == '0'||parentCode == '') {
		    	 errorList.push("Child parent code must not be empty");
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

	    var requestData = "childFinalCode=" + childFinalCode + "&addOrRemoveRow=" + "removeRow";
	    
	    var isDuplicte =__doAjaxRequest('AccountHeadPrimaryAccountCodeMaster.html?validateDuplicateCompositeCode','post',requestData,false);
	
	
	var rowCount = $('#divId li').length;
	if(rowCount<=1){
		return false;
	}
	
	$('#ulId li').last().remove();
}


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
}

function getParentCode(count){
	countForParentLevelSelection = $('#countForParentLevel').val();
	
	 var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	 countForParentLevelSelection = stringId.match(/[0-9]+/g);
	   
	var childLevel = $('#childParentFunLevel'+countForParentLevelSelection).val();
	var childCode = $('#childFunCode'+countForParentLevelSelection).val();
	var childDesc = $('#funDesc'+countForParentLevelSelection).val();
	
	var requestData = "selectLevel=" + childLevel;
	var codeWithDesc =__doAjaxRequest('AccountHeadPrimaryAccountCodeMaster.html?populateParentCode','post',requestData,false);
	var optionAsString = '';
	$("#childParentCode"+countForParentLevelSelection).find('option').remove();
	optionAsString += "<option value='"+0+"'> Select </option>";
	for(var i=0;i<codeWithDesc.length;  i++){
		var compositeCode = codeWithDesc[i].substring(0,codeWithDesc[i].indexOf('('));
		if(compositeCode==''){
			optionAsString += "<option value='"+ codeWithDesc +"'>" + codeWithDesc[i] +"</option>";	
		}else{
			optionAsString += "<option value='"+ compositeCode +"'>" + codeWithDesc[i] +"</option>";
		}
		
	}
	$("#childParentCode"+countForParentLevelSelection).append(optionAsString);
}

function statusDivMode(count){
	
}
