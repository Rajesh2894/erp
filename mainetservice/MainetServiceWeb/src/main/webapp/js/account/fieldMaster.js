 
$("#accountFieldMasterBean").validate({
	
	onkeyup: function(element) {
	       this.element(element);
	       console.log('onkeyup fired');
	 },
	onfocusout: function(element) {
	       this.element(element);
	       console.log('onfocusout fired');
	}
});

var fieldId = '';
$(function() {
		$("#grid").jqGrid(
				{
					url : "AccountFieldMaster.html?getGridData",
					datatype : "json",
					mtype : "POST",
					//colNames : [ '', "Field Code", "Field Description", 'Add Child Field', 'View/Edit'],
					colNames : [ '', getLocalMessage('acc.master.fieldCode'), getLocalMessage('acc.master.fieldDesc'), "", getLocalMessage('bill.action')],
					colModel : [ {name : "fieldId",width : 20,sortable : true,searchoptions: { "sopt": ["bw", "eq"] }, hidden:true}, 
					             {name : "fieldCode",width : 20,sortable : true, searchoptions: { "sopt": ["bw", "eq"] }}, 
					             {name : "fieldDesc",width : 75,sortable : true,searchoptions: { "sopt": [ "eq"] }}, 
					             {name : "defaultOrgFlag",width : 20,sortable : true,searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
					             //{name : 'fieldId',index : 'fieldId',width : 20,align : 'center',formatter : returnEditUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}, 
					             //{name : 'fieldId',index : 'fieldId',width : 20,align : 'center',formatter : returnViewUrl,editoptions : {value : "Yes:No"},formatoptions : {disabled : false},search:false}
					             { name: 'fieldId', index: 'fieldId', width:20 , align: 'center !important',formatter:addLink,search :false},
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
					caption : getLocalMessage('account.configuration.field.masters.list')
				});
		 jQuery("#grid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
		 $("#pagered_left").css("width", "");
	});


	function returnEditUrl(cellValue, options, rowdata, action) {
		fieldId = rowdata.fieldId;
		return "<a href='#'  return false; class='editClass' value='"+fieldId+"'><img src='css/images/edit.png' width='20px' alt='Edit  Master' title='Edit  Master' /></a>";
	}
	function returnViewUrl(cellValue, options, rowdata, action) {
		return "<a href='#'  return false; class='viewFieldClass'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
	}
	
	function addLink(cellvalue, options, rowdata) {
	    return "<a class='btn btn-blue-3 btn-sm viewFieldClass' title='View'value='"+rowdata.fieldId+"' id='"+rowdata.fieldId+"' ><i class='fa fa-building-o'></i></a> " +
	    		"<a class='btn btn-warning btn-sm viewFieldMasterClass' title='Edit'value='"+rowdata.fieldId+"' id='"+rowdata.fieldId+"' ><i class='fa fa-pencil'></i></a> " +
	    		"<a class='btn btn-success btn-sm editClass' title='Add Child Field'value='"+rowdata.fieldId+"' id='"+rowdata.fieldId+"' ><i class='fa fa-plus text-white' aria-hidden='true'></i></a> ";
	 }
	
	
	
	
	$( document ).ready(function() {
		$('.error-div').hide();
		$('#statusDivMain').hide();
		$('#editChildNode').hide();
		$('#editParentNode').hide();
		$('#saveButttonOnDescChange').hide();
		$("#childFieldLevel0 option[value='1']").hide();
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
					     	}
						
							$('#editChildNode').show();
							if($('#editedChildFunLevel').val()==$("#editedChildFunLevel option:last-child").val()){
								$('#statusDiv').show();	
							}else{
								$('#statusDiv').hide();	
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
		$(document).on('click', '.createData', function() {

			var $link = $(this);
			/*var spId = $link.closest('tr').find('td:eq(0)').text();*/
			var fieldId = 1;
			var url = "AccountFieldMaster.html?showLeveledform";
			var requestData = "fieldId=" + fieldId  + "&MODE_DATA=" + "EDIT";
			var returnData =__doAjaxRequest(url,'post',requestData,false);
			
			$('.content').html(returnData);
			$("#statusDivMain").hide();
			prepareDateTag();
			return false;			
		});			
	});
	 	 
		$(function() {
			$(document).on('click', '.editClass', function() {
				var errorList = [];
				var $link = $(this);
				var fieldId = $link.closest('tr').find('td:eq(0)').text();
				var defaultOrgFlag = $link.closest('tr').find('td:eq(3)').text();
				var url = "AccountFieldMaster.html?update";
				var requestData = "fieldId=" + fieldId + "&MODE_DATA=" + "EDIT";
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				if(defaultOrgFlag=="Y"){
					$('.content').html(returnData);
					$("#statusDivMain").hide();
				}
				else{
					errorList.push("You are not allowed to add child this selection because it is protected by Default Orgonization.");
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
			
			$(document).on('click', '.viewFieldMasterClass', function() {
				var errorList = [];
				var $link = $(this);
				var fieldId = $link.closest('tr').find('td:eq(0)').text();
				var defaultOrgFlag = $link.closest('tr').find('td:eq(3)').text();
				var url = "AccountFieldMaster.html?update";
				var requestData = "fieldId=" + fieldId + "&MODE_DATA=" + "VIEW";
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				if(defaultOrgFlag=="Y"){
					$('.content').html(returnData);
					$("#statusDivMain").hide();
				}
				else{
					errorList.push("You are not allowed to edit this selection because it is protected by Default Orgonization.");
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
			
			$(document).on('click', '.viewFieldClass', function() {
				var $link = $(this);
				var fieldId = $link.closest('tr').find('td:eq(0)').text();
				var url = "AccountFieldMaster.html?view";
				var requestData = "fieldId=" + fieldId + "&MODE_DATA=" + "VIEW";
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				
				$('.content').html(returnData);
				
				$('select').attr("disabled", true);
				$('input[type=text]').attr("disabled", true);
				$('input[type="text"], textarea').attr("disabled", true);
				$('select').prop('disabled', true).trigger("chosen:updated");
				
				prepareDateTag();
					
			});
							
		});
		
		var incrementvalue;
		
		
function saveFieldMaster(obj){

	/*var noError = validateEachFieldWithFieldLevels();
		if(!noError){
			return false;
			}else{
				return saveOrUpdateForm(element, 'Saved Successfully', 'AccountFieldMaster.html', 'create');
			}*/
		var	formName =	findClosestElementId(obj, 'form');
	    var theForm	=	'#'+formName;
	    var requestData = __serializeForm(theForm);
	    var url	=	$(theForm).attr('action');
	
		//return doAjaxOperation(element, 'Saved Successfully', 'AccountFieldMaster.html', 'create');
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
	//$.fancybox.close();
	window.location.href='AccountFieldMaster.html';
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

		
function removeRowFunction (){
	var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
   i = stringId.match(/[0-9]+/g);
   var errorList= []; 
   var  childFieldLevel= $("#childFieldLevel" + i).val();
   var  parentFieldLevel= $("#childParentLevel" + i).val();
   var  childCode=$("#childFieldCode" + i).val();
   var parentCode=$("#childParentCode" + i).val();
   var desc=$("#childFieldDesc" + i).val();
   var childFinalCode = $("#childFinalCode" + i).val();

   var requestData = "childFinalCode=" + childFinalCode + "&addOrRemoveRow=" + "removeRow";
   
   var isDuplicte =__doAjaxRequest('AccountFieldMaster.html?validateDuplicateCompositeCode','post',requestData,false);
	var rowCount = $('#divId li').length;
   if(rowCount<=1){
   	return false;
   }
   
   $('#ulId li').last().remove();
		    }
		
function addRowFunction (){
	 var noError = true; //validateEachFieldWithFieldLevels();

		if(!noError){
			return false;
		}else{
		   var content = $('#divId li:last').clone();
		   $("#divId ul > li").last().after(content);
		   
		   var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
		   count = stringId.charAt(stringId.length-1);
		   incrementvalue=++count;
		
		    content.find('select:eq(0)').attr({'path': 'listDto['+incrementvalue+'].childLevelCode'}).attr({'name': 'listDto['+incrementvalue+'].childLevelCode' }).attr({'id': 'childFieldLevel'+incrementvalue+'' });
		    content.find('select:eq(1)').attr({'path': 'listDto['+incrementvalue+'].childParentLevelCode'}).attr({'name': 'listDto['+incrementvalue+'].childParentLevelCode' }).attr({'id': 'childParentLevel'+incrementvalue+'' });
		    content.find('input:text:eq(0)').attr({'path': 'listDto['+incrementvalue+'].childCode' }).attr({'name': 'listDto['+incrementvalue+'].childCode' }).attr({'id': 'childFieldCode'+incrementvalue+'' });
		    content.find('input:text:eq(1)').attr({'path': 'listDto['+incrementvalue+'].childDesc'}).attr({'name': 'listDto['+incrementvalue+'].childDesc' }).attr({'id': 'childFieldDesc'+incrementvalue+'' });
		    content.find('select:eq(2)').attr({'path': 'listDto['+incrementvalue+'].parentCode' }).attr({'name': 'listDto['+incrementvalue+'].childParentCode' }).attr({'id': 'childParentCode'+incrementvalue+'' });
		    content.find('input:text:eq(2)').attr({'path': 'listDto['+incrementvalue+'].parentFinalCode'}).attr({'name': 'listDto['+incrementvalue+'].childFinalCode' }).attr({'id': 'childFinalCode'+incrementvalue+'' });
		    content.find('select:eq(3)').attr({'path': 'listDto['+incrementvalue+'].childFieldStatus'}).attr({'name': 'listDto['+incrementvalue+'].childFieldStatus' }).attr({'id': 'childFieldStatus'+incrementvalue+'' });
           
		    content.find('.hideDivClass').attr('id','statusDivAdd'+incrementvalue);
		    content.find('label').closest('.error').remove(); //for removal duplicate
		    
		    content.find('#childFieldCode'+incrementvalue).attr("onchange", "clearParentCode(" + (incrementvalue) + ")");
		    
            content.find('input:hidden:eq(2)').attr({'name':'listDto['+incrementvalue+'].childFieldStatus'}).attr({'id':'hiddenChildFieldStatus'+incrementvalue});
		    content.find("select").val("0");
		    content.find("input:text").val("");
		    
		    $("#statusDivAdd"+incrementvalue).hide();
		    $('#count').val(count);
		}
	}

   function saveEditedData(obj){
	  /* var noError = validateEachFieldWithFieldLevels();
		if(!noError){
			return false;
		}else{*/
	  	var	formName =	findClosestElementId(obj, 'form');
		var theForm	=	'#'+formName;
		var requestData = {};
   		requestData = __serializeForm(theForm);
		var url	=	$(theForm).attr('action')+'?' + 'saveEditedData';
		
		var response= __doAjaxRequestValidationAccor(obj,url, 'post', requestData, false, 'html');
	    if(response != false){
	       $('.content').html(response);
	    }
	    
		/*var returnData =__doAjaxRequest(url,'post',requestData,false);
		$('.content').html(returnData);
		prepareDateTag();
		}*/
 }
   
   function validateEachFieldWithFieldLevels(){
	   var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
		  i = stringId.match(/[0-9]+/g);
		  var errorList= [];	
		  var  childFieldLevel= $("#childFieldLevel" + i).val();
		    var  parentFieldLevel= $("#childParentLevel" + i).val();
		    var  childCode=$("#childFieldCode" + i).val();
		    var parentCode=$("#childParentCode" + i).val();
		    var desc=$("#childFieldDesc" + i).val();
		    var childFieldStatus=$("#childFieldStatus" + i).val();
		    
		    var  childFieldLevel= $("#childFieldLevel" + i).val();
		    var  parentFunLevel= $("#childParentLevel" + i).val();


		    var childFinalCode = $("#childFinalCode" + i).val();

		    var requestData = "childFinalCode=" + childFinalCode + "&addOrRemoveRow=" + "addRow"; 
		    
		    var isDuplicte =__doAjaxRequest('AccountFieldMaster.html?validateDuplicateCompositeCode','post',requestData,false);
		    
		    if(isDuplicte){
		    	$("#childFunLevel" + i).val('');
		    	$("#childParentFunLevel" + i).val('');
		    	$("#childFunCode" + i).val('');
		    	$("#childParentCode" + i).val('')
		    	$("#childFinalCode" + i).val('');
		    	
		    	errorList.push("Final Code Already Exist.");
		    }
		    
		    if(parseInt(parentFunLevel)>parseInt(childFieldLevel)||parseInt(parentFunLevel)==parseInt(childFieldLevel)){
		    	errorList.push("Select Correct Level");
		    	
		    }
		    else if(parseInt(parentFunLevel)<parseInt(childFieldLevel)||parseInt(parentFunLevel)==parseInt(childFieldLevel)){
		    	if(parseInt(childFieldLevel)-parseInt(parentFunLevel)>1||parseInt(childFieldLevel)-parseInt(parentFunLevel)==0){
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
		     if(parentCode == '0') {
		    	 errorList.push("Child parent code must not be empty");
		 	}
		     if(desc == '') {
		    	 errorList.push("Child field description must not be empty");
		 	}
		     
		    /* if(childFieldStatus == '0') {
				  errorList.push("Please select Chilld Field status");
			}*/
		     return bindError(errorList);
   }




function validateLevel(){
	var errorList = [];
	 var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	  i = stringId.match(/[0-9]+/g);
	 	var  childFieldLevel= $("#childFieldLevel" + i).val();
	    var  parentFunLevel= $("#childParentLevel" + i).val();
	    if(parseInt(parentFunLevel)>parseInt(childFieldLevel)||parseInt(parentFunLevel)==parseInt(childFieldLevel)){
	    	errorList.push("Select Correct Level");
	    	
	    }
	    else if(parseInt(parentFunLevel)<parseInt(childFieldLevel)||parseInt(parentFunLevel)==parseInt(childFieldLevel)){
	    	if(parseInt(childFieldLevel)-parseInt(parentFunLevel)>1||parseInt(childFieldLevel)-parseInt(parentFunLevel)==0){
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

function fetchMaxLengthAndResetChildParentLevel(){
	$("#statusDivMain").show();
	var levelCodeForChildField;
	var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	var incrementedValue = stringId.match(/[0-9]+/g);

	 $("#childParentLevel"+incrementedValue).find('option').remove();
	 var optionAsString = '';
	 var selectedLevel = $('#childFieldLevel'+incrementedValue).find(":selected").val()-1;
	 var text = $('#childFieldLevel'+incrementedValue).find('option[value='+selectedLevel+']').text();
	 var optionAsString2 = '';
	 optionAsString2 += "<option value=''> Select </option>";
	 
	 optionAsString2 += "<option value='"+ selectedLevel +"'>" + text +"</option>";
	 $('#childParentLevel'+incrementedValue).append(optionAsString2);
	 
	$('#childFieldCode'+incrementedValue).val('');
	$('#childParentLevel'+incrementedValue).val(0);
 	levelCodeForChildField  = $('#childFieldLevel'+incrementedValue).find(":selected").val();
 	var url = 'AccountFieldMaster.html?getCodeDigits';
 	var requestData = "selectValue=" + levelCodeForChildField;
 	
 	var digitForChildFieldCode=__doAjaxRequest(url,'post',requestData,false);
 	
 	$('#childFieldCode'+incrementedValue).val('');
 	$('#childFieldCode'+incrementedValue).attr({'maxlength':digitForChildFieldCode[0]}) 	
 	 	
 	if(digitForChildFieldCode[1]==1){
		$("#statusDivAdd"+incrementedValue).show();
		$("#childFieldStatus"+incrementedValue).val(digitForChildFieldCode[2]);
		$("#hiddenChildFieldStatus"+incrementedValue).val(digitForChildFieldCode[2]);
	}else{
		$("#statusDivAdd"+incrementedValue).hide();
	  }
	 	 			
}
/*function closeErrBox() {
	$('.error-div').hide();
}*/

function updateDescForFieldMaster(obj){
	 var errorList= [];	

     var editedChildStatus= $("#editedChildStatus").val();
     
	if($('#editedDataChildDesc').val().trim().length<1){
		 errorList.push('Please Enter Description');
	}
	if(editedChildStatus == '0' || editedChildStatus == '') {
		  errorList.push("Please select Chilld Field status");
	}
	
	if(errorList.length > 0){
		return bindError(errorList);
	}else{
		var	formName =	findClosestElementId(obj, 'form');
		var theForm	=	'#'+formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var url	=	$(theForm).attr('action')+'?' + 'saveEditedData';
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		
		
		var redirectUrl = 'AccountFieldMaster.html';
		var message="";
	 var updatemsg=getLocalMessage("account.update.succ");
	 var ok=getLocalMessage("account.ok");
	 message	+='<h5 class="text-blue-2 text-center padding-15">'+ updatemsg+'</h5>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value='+ok+'   id=\'btnNo\' class=\'btn btn-success\'    '+ 
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
	    	 errorList.push("Parent field code must be "+maxParentCodeLenght+" digit");
	 	}else{
	 		$('#parentFinalCode0').val(childCode);
	 	 }
		}
		
	   if(errorList.length == 0){
	   var requestData = "childLevel=" + childLevel + "&childParentLevel=" + childParentLevel+"&childCode="+childCode+"&childParentCode="+childParentCode+"&childDesc="+childDesc;
	   
	   $("#childParentCode0").append('');
	   var returnData =  __doAjaxRequest('AccountFieldMaster.html?storeChildDetails','post',requestData);
	   $('#parentFinalCode0').val(childCode);
	   }
	   
	   /*if(errorList.length == 0){
		 alert("HIII  "+childCode);
		   if(childCode != null && childCode != ""){
		 	//var url = 'AccountFieldMaster.html?checkFieldParentCodeExist';
		 	var requestData = 	"childParentCode="+ childCode ;
			
			var result = __doAjaxRequest('AccountFieldMaster.html?checkFieldParentCodeExist', 'POST', requestData, false);
		 	//var ajaxResponse = __doAjaxRequest(formURL+'?saveForm', 'POST', requestData, false,'json');
		 	
		 	alert("test"+result);
		 	if(result){
		 		 errorList.push("Parent Code is Already Exists.!"); 
 				 $('#parentCode').val('');
		 	 }
		   }
	   }*/
	   
	   if(errorList.length>0){
			$('#parentCode').val('');
			$('#parentFinalCode0').val('');
			
			 return bindError(errorList);
		}else{
			$('.warning-div').hide();
		}
	}


function getFinalCode(count){
	
	var errorList= [];	
	
	var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
	countForchildLevel = stringId.match(/[0-9]+/g);
    var childLevel = $('#childFieldLevel'+countForchildLevel).val();
	var childParentLevel = $('#childParentLevel'+countForchildLevel).val();
						
	var childCode = $('#childFieldCode'+countForchildLevel).val();
	var childParentCode = $('#childParentCode'+countForchildLevel).val();
	var childDesc = $('#childFieldDesc'+countForchildLevel).val();
	var requestData = "childLevel=" + childLevel + "&childParentLevel=" + childParentLevel+"&childCode="+childCode+"&childParentCode="+childParentCode+"&childDesc="+childDesc;
	var returnData =__doAjaxRequest('AccountFieldMaster.html?storeChildDetails','post',requestData,false);
	//alert(returnData);
	$('#childFinalCode'+countForchildLevel).val(returnData);
	/*if(errorList.length == 0){
 		if(returnData != null && returnData != ""){
 	 		var url = 'AccountFieldMaster.html?checkChildFieldCompositeCodeExists';
 	 		var requestData = "compositeCode=" + returnData;
 			var digitForChildFieldCode=__doAjaxRequest(url,'post',requestData,false);
 			if(digitForChildFieldCode){
 				 errorList.push("Child composite code is Already Exists, against this same parent!"); 
 				 $('#childFieldCode'+countForchildLevel).val('');
 				 $('#childFinalCode'+countForchildLevel).val('');
 				 $('#childParentCode'+countForchildLevel).val('');
 		    }else{
 		    	 $('#childFinalCode'+countForchildLevel).val(returnData);
 		    }
 	 	}
 	}*/
	if(errorList.length > 0){
		return bindError(errorList);
	}
}


var countForParentLevelSelection='';
function getParentFieldCode(){
    var stringId =  $('#ulId li').last().find('select:eq(0)').attr('id');
    countForParentLevelSelection = stringId.match(/[0-9]+/g);
      
   var childLevel = $('#childParentLevel'+countForParentLevelSelection).val();
   var childCode = $('#childFieldCode'+countForParentLevelSelection).val();
   
   
   var requestData = "selectLevel=" + childLevel;
   
   var codeWithDesc =__doAjaxRequest('AccountFieldMaster.html?populateParentCode','post',requestData,false);
	var optionAsString = '';
	$("#childParentCode"+countForParentLevelSelection).find('option').remove();
	optionAsString += "<option value=''> Select </option>";
	for(var i=0; i<codeWithDesc.length;  i++){
		var compositeCode = codeWithDesc[i].substring(0,codeWithDesc[i].indexOf('('));
		if(compositeCode==''){
			optionAsString += "<option value='"+ codeWithDesc +"'>" + codeWithDesc[i] +"</option>";	
		}else{
			optionAsString += "<option value='"+ compositeCode +"'>" + codeWithDesc[i] +"</option>";
		}
	}
	$("#childParentCode"+countForParentLevelSelection).append(optionAsString);
   
}

function clearParentCode(cont){
	
	var errorList= [];	
	 
	var parentCode = $('#parentCode').val();
	if(parentCode == null || parentCode == ""){
		 errorList.push("Please enter parent field code");
		 $('#childFieldCode'+cont).val('');
	}
	
	var childFieldLevel = $('#childFieldLevel'+cont).val();
	if(childFieldLevel == null || childFieldLevel == ""){
		 errorList.push("Please select child field Level");
    	 $('#childFieldCode'+cont).val('');
	}
	
	levelCodeForChildField  = $('#childFieldLevel'+cont).find(":selected").val();
 	var url = 'AccountFieldMaster.html?getCodeDigits';
 	var requestData = "selectValue=" + levelCodeForChildField;
 	
 	var digitForChildFieldCode=__doAjaxRequest(url,'post',requestData,false);
 	 	
 	var childFieldCode = $('#childFieldCode'+cont).val();
 	var maxChildFieldCode = digitForChildFieldCode[0];
 	
 	if(childFieldCode != null && childFieldCode != ""){
 	if(childFieldCode.length != maxChildFieldCode) {
    	 errorList.push("Child field code must be "+maxChildFieldCode+" digit");
    	 $('#childFieldCode'+cont).val('');
 		}
 	}
 	
 	if(errorList.length > 0){
		return bindError(errorList);
	}
 	
	$('#childParentCode'+cont).val('');
	$('#childFinalCode'+cont).val('');
}

function closeErrBox() {
	$('.error-div').hide();
	$('.warning-div').hide();
}

function checkParentCodeDuplicateExist(obj){
	
	$('.error-div').hide();
	var errorList = [];

	//alert("HI");
	
	var theForm = '#fieldMaster';
	var requestData = __serializeForm(theForm);
	
	if (errorList.length == 0) {
		
		var url = "AccountFieldMaster.html?checkFieldParentCodeExist";
		
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		
		 if(returnData){
			 //errorList.push("Parent Code is Already Exists.!"); 
			// $('#parentCode').val('');
		 }
	}
	
	if(errorList.length > 0){
		return bindError(errorList);
	}
}

