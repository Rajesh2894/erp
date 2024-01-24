/**
 * @author Vikrant.Thakur
 * @since 4 October 2014.
 *
 */

/** Created conditions for IE9 support
 *  -- Ritesh Patil
 * 
 * */
$.getScript("js/mainet/jquery.browser.js")
  .done(function( script, textStatus ) {
    console.log( textStatus );
  })
  .fail(function( jqxhr, settings, exception ) {
    $( "div.log" ).text( "Triggered ajaxError handler." );
});


    $( document ).ready(function()  {	
	
	

    	 if($(".fileUploadClass").length !=	0)	
								{			
									validnFunction();
								}
							
							if(!(($.browser.msie) && ($.browser.version==9.0))){
									$(".fileUploadClass").change(function(event)
									{
									var	file=event.target.files;	
									if(file.length != 0)
										{
										   
										var fileName=file[0].name;
										var fileExtension = fileName.substr((fileName.lastIndexOf('.') + 1));
										
										if(file[0].size <= parseInt($(this).attr('maxsize'))){
											
											 var listArray =$(this).attr('extension').split(',');
											 var flag=0;
											 for (var i = 0; i < listArray.length; i++) {
										          if(flag == 0){
															if(listArray[i] == lowerCase(fileExtension)){
																flag++;
																break;
															 }
														}
												 }
											 
											 if(flag != 0){
												    var url	=	'';
												    var	formName =	findClosestElementId(this, 'form');
													var theForm	=	'#'+formName;
												    url = $(theForm).attr('action')+'?doFileUpload';
									                var oMyForm	=	new FormData();
													oMyForm.append("file",file[0]);
													oMyForm.append("fileCode",$(this).attr('code'));
													oMyForm.append("browserType","Other");
													__doAjaxRequestForFileUpload(url, 'post', oMyForm , false,'json',$(this).attr('code'));
												 
											 }else{
												   $('.error-dialog').html(getLocalMessage("Common.file.validation.error.msg.only") + $(this).attr('extension') +"&nbsp"+ getLocalMessage("Common.file.validation.error.msg.allow"));
									     		   showModalBox('.error-dialog');
									     		   $(this).val(null);
									     		   return false;
												 }
											}else{
												
											   var sizeInMB = (parseInt($(this).attr('maxsize')) / (1024*1024)).toFixed(2);
											   $('.error-dialog').html(getLocalMessage("Common.file.validation.error.msg.upload") + sizeInMB + " MB.");
								     		   showModalBox('.error-dialog');
								     		   $(this).val(null);
								     		   return false;
										  }
											  
										}
									
									});
							  }else{
								  
								 $(".fileUploadClass").change(function(event)
    	                  
									{
									 var fileExtension = $(this).val().substr(($(this).val().lastIndexOf('.') + 1));
									  if(20971520  >= parseInt($(this).attr('maxsize'))){  
									 
										 var listArray =$(this).attr('extension').split(',');
										 var flag=0;
										 
										 for (var i = 0; i < listArray.length; i++) {
									          
											  if(flag == 0){
														if(listArray[i] == lowerCase(fileExtension)){
															flag++;
															break;
														 }
													}
											 }
										 
										 if(flag != 0){
    	                	   
									    	//var token = '';
									    	/*$.ajax({
									    		url : "Autherization.html?getRandomKey",
									    		type : "POST",
									    		async : false,
									    		success : function(response) {
									    			token = response;
									    		},
									    		error : function(xhr, ajaxOptions, thrownError) {
									    			var errorList = [];
									    			errorList.push(getLocalMessage("admin.login.internal.server.error"));
									    			showError(errorList);
									    		}
									    	});*/
    	                	                  form=this.form;
								    	      var formName =	findClosestElementId(this, 'form');
										      var theForm	=	'#'+formName;
										      var preUrl=$(theForm).attr('action');
										      url = $(theForm).attr('action')+'?doFileUpload';
									          $('#fileCode').remove();
									          $('#browserType').remove(); 
									          $('#upload_iframe').remove();
												var iframe=document.createElement("iframe");
												iframe.setAttribute("id","upload_iframe");
												iframe.setAttribute("name","upload_iframe");
												iframe.setAttribute("width","0");
												iframe.setAttribute("height","0");
												iframe.setAttribute("border","0");
												iframe.setAttribute("style","width: 0; height: 0; border: none;");
												form.parentNode.appendChild(iframe);
												window.frames['upload_iframe'].name="upload_iframe";
												iframeId=document.getElementById("upload_iframe");
												var eventHandler=function()
												{
												if(iframeId.detachEvent)
												iframeId.detachEvent("onload",eventHandler);
												else
												iframeId.removeEventListener("load",eventHandler,false);
												
												
												if(iframeId.contentDocument)
												{
												content=iframeId.contentDocument.body.innerHTML;
												}
												else if(iframeId.contentWindow)
												{
												content=iframeId.contentWindow.document.body.innerHTML;
												}
												else if(iframeId.document)
												{
												content=iframeId.document.body.innerHTML;
												}
												var result=content;
												var jsonResponse = JSON.parse(result);
												if(!jsonResponse.status)	
														showErrormsgboxTitle(jsonResponse.url);	
												 var jsonMessage=jsonResponse.message;
												 
												 var msg="";
												 var firstLoop = jsonMessage.split('?');
												 
												 $.each(firstLoop, function( index, value ) {
															
												     if(index == 0){
														 msg="<ul><li><b>"+value+"</b></li>";
													 }
													 if(index != 0){
														 var secondLoop = value.split('*');	 
															 $.each(secondLoop, function( index, value ) {
																 if(index == 0) 
																	    msg+="<li>"+value;
																	if(index != 0)
																		msg+="<img src='css/images/close.png' alt='Remove' width='17' title='Remove' id='"+value+"' onclick='doFileDelete(this)' ></li>";
															  });
													 }
														
												 });
												
												 try
													{
														otherTask();
													}
													catch(e)
													{
														
													}
												
												// $("#"+jsonResponse.hiddenOtherVal).html(jsonMessage.replace(/\"/g, ""));
												 $("#"+jsonResponse.hiddenOtherVal).html(msg);
												//setTimeout('iframeId.parentNode.removeChild(iframeId)',70);
												//return event.preventdefault();
												};
												
												if(iframeId.addEventListener)
												iframeId.addEventListener("load",eventHandler,true);
												if(iframeId.attachEvent)iframeId.attachEvent("onload",eventHandler);
												form.setAttribute("target","upload_iframe");
												form.setAttribute("action",url);
									            form.setAttribute("method","post");
												form.setAttribute("enctype","multipart/form-data");
												form.setAttribute("encoding","multipart/form-data");
												//form.setAttribute("SecurityToken",token);
												//form.setAttribute("file",this.files[0]);
												form.setAttribute("file",$(this).val());
												var	my_tb=document.createElement('INPUT');
												my_tb.type='HIDDEN';
												my_tb.id='fileCode';
												my_tb.name='fileCode';
												my_tb.value=$(this).attr('code');
												form.appendChild(my_tb); 
												var	my_tb1=document.createElement('INPUT');
												my_tb1.type='HIDDEN';
												my_tb1.id='browserType';
												my_tb1.name='browserType';
												my_tb1.value='IE';
												form.appendChild(my_tb1);
												form.submit();
												//document.getElementById('upload').innerHTML=content;
												form.setAttribute("action",preUrl);
												
												if($(this).attr('code'))
										    	{		    		
										    	  progressLoadingWindow($(this).attr('code'));
										    	}
												
												
										}else{
											
											 $('.error-dialog').html(getLocalMessage("Common.file.validation.error.msg.only") + $(this).attr('extension') + "&nbsp" + getLocalMessage("Common.file.validation.error.msg.allow"));
								     		   showModalBox('.error-dialog');
								     		   $(this).val(null);
								     		   return false;
											
										   }	
										 
 	                             }else{
 	                            	   var sizeInMB = (parseInt($(this).attr('maxsize')) / (1024*1024)).toFixed(2);
										   $('.error-dialog').html(getLocalMessage("Common.file.validation.error.msg.upload") +sizeInMB+ " MB.");
 	                            	   showModalBox('.error-dialog');
							     		   $(this).val(null);
							     		   return false;
 	                            	 
 	                            	 
 	                             }
								     });
								  
							 }
							
							checkExcelUploadState($(".excelUploadTypeClass").val());
							$(".excelUploadTypeClass").change(function(event)
							{
								$("#logDiv").hide();
								checkExcelUploadState($(this).val());
									
							});

				});

  
function checkExcelUploadState(val)
{
	if(val == 'T')
	{			
		$(".tempDataClass").show();	
		$(".mainDataClass").hide();
	}
	if(val == 'O')
	{
		$(".tempDataClass").hide();		
		$(".mainDataClass").show();		
	}
}
 
 function validnFunction()
 {
	 
	 	var url	=	'';
		 
	/*	var formAction	=	$('form').attr('action');
		
		alert(formAction);
		
		if(formAction == 'Home.html?Download')
			
			url = $("#frmMaster").attr('action')+'?doFileUploadValidatn';
	
		else
		
			url	=	$('form').attr('action')+'?doFileUploadValidatn';
		
*/		
		var	formName =	findClosestElementId($(".fileUploadClass"), 'form');
		
		var theForm	=	'#'+formName;
	
		url = $(theForm).attr('action')+'?doFileUploadValidatn';
	 
		var data='';	
		if(!(($.browser.msie) && ($.browser.version==9.0))){ 
			
			 data	='browserType=Other';
		}else{
			data ='browserType=IE';
			
			
		}
		
		var jsonResponse	=	 __doAjaxRequest(url,'post',data,false,'json');
 		
		
		if(!(($.browser.msie) && ($.browser.version==9.0))){ 	
 		$.each(jsonResponse,function(index)
				{
 			        $("#"+jsonResponse[index].hiddenOtherVal).html(jsonResponse[index].message);
		 			
				});
		}else{
			
			$.each(jsonResponse,function(index)
		   {
					
			var jsonMessage=jsonResponse[index].message;
			 
			 var msg="";
			 var firstLoop = jsonMessage.split('?');
			 $.each(firstLoop, function( index, value ) {
				if(index == 0){
					 msg="<ul><li><b>"+value+"</b></li>";
				 }
				 
				 if(index != 0){
					 var secondLoop = value.split('*');	 
					 $.each(secondLoop, function( index, value ) {
						  if(index == 0) 
							    msg+="<li>"+value;
							if(index == 1)
								divImageId=value;
							if(index == 2){
								uniqueId=value;
							}
					 });
					     msg+="<img src='css/images/close.png' alt='Remove' width='17' title='Remove' id='"+divImageId+"' uniqueId='"+uniqueId+"' onclick='doFileDelete(this)' ></li>";
			  }
			});
			 
			 $("#"+jsonResponse[index].hiddenOtherVal).html(msg);
		   });
		}
 }
 
 function doFileDelete(obj)
 {
	 
	    
		var url	=	'';
		var	formName =	findClosestElementId(obj, 'form');
		var theForm	=	'#'+formName;
	    url = $(theForm).attr('action')+'?doFileDeletion';
	    
	    if(!(($.browser.msie) && ($.browser.version==9.0))){ 
			 data1	='browserType=Other';
		}else{
			 data1 ='browserType=IE';
			
		}
	    var uniqueId;
	    var valId=$(obj).closest('li').find('input').val();
	    if(valId == undefined){
	    	uniqueId='';
	    }else{
	    	uniqueId=$(obj).closest('li').find('input').val();
	    }
	    var data	=	'fileId='+$(obj).attr('id')+'&'+data1+'&uniqueId='+uniqueId;	
		
	 	var jsonResponse	=	 __doAjaxRequest(url,'post',data,false,'json');
	 	
	 	
	
if(!(($.browser.msie) && ($.browser.version==9.0))){ 	
	
    $('.fileUploadClass').val(null);
  	
	if(jsonResponse.status)		
		$("#"+jsonResponse.hiddenOtherVal).html(jsonResponse.message);
	else
		showErrormsgboxTitle(jsonResponse.message);	
	
}else{
	
	var jsonMessage=jsonResponse.message;
	 
	 var msg="";
	 var firstLoop = jsonMessage.split('?');
	 $.each(firstLoop, function( index, value ) {
		if(index == 0){
			 msg="<ul><li><b>"+value+"</b></li>";
		 }
		 
		 if(index != 0){
			 var secondLoop = value.split('*');	 
			 $.each(secondLoop, function( index, value ) {
				  if(index == 0) 
					    msg+="<li>"+value;
					if(index == 1)
						divImageId=value;
					if(index == 2){
						uniqueId=value;
					}
			 });
			     msg+="<img src='css/images/close.png' alt='Remove' width='17' title='Remove' id='"+divImageId+"' uniqueId='"+uniqueId+"' onclick='doFileDelete(this)' ></li>";
	    }
	});
	 
	 if(jsonResponse.status)		
			$("#"+jsonResponse.hiddenOtherVal).html(msg);
		else
			showErrormsgboxTitle(msg);	
	 
	 window.location.reload(false);
	}
	 
 }
 

function __doAjaxRequestForFileUpload(url,reqType,data,async,dataType,fieldName) 
{		
	//var token = '';
	/*$.ajax({
		url : "Autherization.html?getRandomKey",
		type : "POST",
		async : false,
		success : function(response) {
			token = response;
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});*/
	
	$.ajax({
			dataType	: 	dataType,
			url 		: 	url,
			data 		: 	data,
			type		: 	reqType,
			//headers   : {"SecurityToken": token},
			enctype		:	'multipart/form-data',
			processData	:	async,
			contentType	:	async,
			beforeSend : function() {
		    	if(fieldName)
		    	{		    		
		    		progressLoadingWindow(fieldName);
		    	}
		    },
			
			success	: function(jsonResponse) 
			{		
				
				
				if(! jsonResponse.status)	
					showErrormsgboxTitle(jsonResponse.url);	
				
				$("#"+jsonResponse.hiddenOtherVal).html(jsonResponse.message);
				
				try
				{
					otherTask();
				}
				catch(e)
				{
					
				}
			},
			error: function(xhr, ajaxOptions, thrownError) {  
	            alert(xhr.responseText);
	            alert(ajaxOptions);
	            alert(thrownError);      
			}
		});
}


function progressLoadingWindow(codeName)
{
	var message	='<img src=\'css/images/ajax-loader.gif\' title=\'Loading...\' alt=\'Loading...\' />';	
	
	var currentCount	=	getStringchar(codeName);
			
	$("#file_list_"+currentCount).html(message);
	$("#file_list_"+currentCount).show();
}


function getStringchar(str)
{
	    var n = str.lastIndexOf("_");

	    str  =  str.substring(n+1);
	    
	    return str;
}

function lowerCase(value){
	return value.toLowerCase();
}
