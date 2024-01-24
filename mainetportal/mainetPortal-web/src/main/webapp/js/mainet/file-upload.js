/**
 * @author Vikrant.Thakur
 * @since 4 October 2014.
 *
 */

/** Created conditions for IE9 support
 *  -- Ritesh Patil
 * 
 * */

jQuery.browser = {};
(function () {
    jQuery.browser.msie = false;
    jQuery.browser.version = 0;
    if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
        jQuery.browser.msie = true;
        jQuery.browser.version = RegExp.$1;
    }
})();
$.getScript("js/mainet/jquery.browser.js")
  .done(function( script, textStatus ) {
    console.log( textStatus );
  })
  .fail(function( jqxhr, settings, exception ) {
    $( "div.log" ).text( "Triggered ajaxError handler." );
});


    $( document ).ready(function()  {
	 var message='';
	 var okMsg = getLocalMessage('bt.ok');
	$('.fileUpload  input ' ).click(function(){this.value='';})
    	$(".fileUploadClass").off();

    	 if($(".fileUploadClass").length !=	0)	
								{			
									validnFunction();
								}
							
							if(!(($.browser.msie) && ($.browser.version==9.0))){
									$(".fileUploadClass").change(function(event)
									{
										
									var	file=event.target.files;	
									if(file.length != 0){
										var fileSize = 0;
										
										for (var i = 0; i < file.length; i++) {
											fileSize = fileSize + file[i].size;
											
										}
										
										var fileName=file[0].name;
										var fileExtension = fileName.substr((fileName.lastIndexOf('.') + 1));
										var fileNamePattern = new RegExp(/^[a-z A-Z 0-9#_.-]+$/);
										if (!fileNamePattern.test( fileName ) ) {
											
										   var msg = getLocalMessage("Common.file.validation.file.pattern.text");
											
										   message='<h4 class="text-center padding-5 margin-top-0 text-info" style="line-height: 20px;">'+msg+'</h4>';
										   message +='<div class="text-center padding-top-10"><input type=\'button\' value=\''+okMsg+'\' class=\'btn btn-blue-2\' onclick="$.fancybox.close()" /></div>';
										   $('.error-dialog').html(message);
										   showModalBoxWithoutClose('.error-dialog');
							     		   $(this).val(null);
							     		  
							     		   return false;
										}
										
										//D#123049
										if( ($(this).attr('checklistDocSize')!="" && fileSize <= parseInt($(this).attr('checklistDocSize'))) || ($(this).attr('maxsize')!= "" && fileSize <= parseInt($(this).attr('maxsize')))){
										
											
											for (var i = 0; i < file.length; i++) {
												var fileName=file[i].name;
												var fileExtension = fileName.substr((fileName.lastIndexOf('.') + 1));
												var listArray =$(this).attr('extension').split(',');
												var flag=0;
												for (var j = 0; j < listArray.length; j++) {
													 if(flag == 0){
															if(listArray[j] == lowerCase(fileExtension)){
																flag++;
																break;
															}
														}
												}
												 
												if(flag == 0){
													
													var msg = getLocalMessage("Common.file.validation.error.msg.only") + $(this).attr('extension') +"&nbsp"+ getLocalMessage("Common.file.validation.error.msg.allow");
													   message='<h4 class="text-center padding-10 text-info">'+msg+'</h4>';
													   message +='<div class="text-center padding-bottom-10"><input type=\'button\' value=\''+okMsg+'\' class=\'btn btn-blue-2\' onclick="$.fancybox.close()" /></div>';
													   $('.error-dialog').html(message);
													   showModalBoxWithoutClose('.error-dialog');
													   /*$('.error-dialog').html(getLocalMessage("Common.file.validation.error.msg.only") + $(this).attr('extension') +"&nbsp"+ getLocalMessage("Common.file.validation.error.msg.allow"));
										     		   showModalBox('.error-dialog');*/
										     		   $(this).val(null);
										     		   return false;
												}
												
											}
										}else{
											let docSize=$(this).attr('maxsize') != "" ? parseInt($(this).attr('maxsize')) :parseInt($(this).attr('checklistDocSize')); 
											
											var sizeInMB = (docSize / (1024*1024)).toFixed(2);
										   var msg;
										   if(sizeInMB >= 1){
											   $('.error-dialog').html('<p class="text-center padding-10 text-bold margin-top-20">' +  + '</p>');
											   msg=getLocalMessage("Common.file.validation.error.msg.upload") + sizeInMB + " MB.";
										   }
										   else{
											   msg=getLocalMessage("Common.file.validation.error.msg.upload") + sizeInMB*1000 + " KB."
										   }
										   
										   message='<h4 class="text-center padding-10 text-info">'+msg+'</h4>';
										   message +='<div class="text-center padding-bottom-10"><input type=\'button\' value=\''+okMsg+'\' class=\'btn btn-blue-2\' onclick="$.fancybox.close()" /></div>';
										   $('.error-dialog').html(message);
										   showModalBoxWithoutClose('.error-dialog');
										   /*$('.error-dialog').html(getLocalMessage("Common.file.validation.error.msg.upload") + sizeInMB + " MB.");
							     		   showModalBox('.error-dialog');*/
							     		   $(this).val(null);
							     		   return false;
										}
										
										var url	=	'';
                                        var	formName =	findClosestElementId(this, 'form');
                                        var theForm	=	'#'+formName;
                                        url = $(theForm).attr('action')+'?doFileUpload';
                                        var oMyForm	=	new FormData();
                                        for (var i = 0; i < file.length; i++) {
                                        	oMyForm.append("file" + i,file[i]);
                                        }
                                        oMyForm.append("fileCode",$(this).attr('code'));
                                        oMyForm.append("browserType","Other");
                                        __doAjaxRequestForFileUpload(url, 'post', oMyForm , false,'json',$(this).attr('code'),$(this).attr('data-callback'));
											
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
													 	
													 	var callbackOtherTask = $(this).data("callback");
													 	if(callbackOtherTask == undefined || !callbackOtherTask || callbackOtherTask == '') {
													 		otherTask(jsonResponse.hiddenOtherVal);
													 	} else if(typeof callbackOtherTask === "function"){
													 		callbackOtherTask();
													 	}
														
													}
													catch(e)
													{
														
													}
												
												 $("#"+jsonResponse.hiddenOtherVal).html(msg);
												};
												
												if(iframeId.addEventListener)
												iframeId.addEventListener("load",eventHandler,true);
												if(iframeId.attachEvent)iframeId.attachEvent("onload",eventHandler);
												form.setAttribute("target","upload_iframe");
												form.setAttribute("action",url);
									            form.setAttribute("method","post");
												form.setAttribute("enctype","multipart/form-data");
												form.setAttribute("encoding","multipart/form-data");
											
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
												form.setAttribute("action",preUrl);
												
												if($(this).attr('code'))
										    	{		    		
										    	  progressLoadingWindow($(this).attr('code'));
										    	}
												
												
										}else{
											
											 var msg = getLocalMessage("Common.file.validation.error.msg.only") + $(this).attr('extension') + "&nbsp" + getLocalMessage("Common.file.validation.error.msg.allow");
											 message='<h4 class="text-center padding-10 text-info">'+msg+'</h4>';
											 message +='<div class="text-center padding-bottom-10"><input type=\'button\' value=\''+okMsg+'\' class=\'btn btn-blue-2\' onclick="$.fancybox.close()" /></div>';
											 $('.error-dialog').html(message);
											 showModalBoxWithoutClose('.error-dialog');
											 /*$('.error-dialog').html(getLocalMessage("Common.file.validation.error.msg.only") + $(this).attr('extension') + "&nbsp" + getLocalMessage("Common.file.validation.error.msg.allow"));
								     		   showModalBox('.error-dialog');*/
								     		   $(this).val(null);
								     		   return false;
											
										   }	
										 
 	                             }else{
 	                            	   var sizeInMB = (parseInt($(this).attr('maxsize')) / (1024*1024)).toFixed(2);
 	                            	   var msg = getLocalMessage("Common.file.validation.error.msg.upload") +sizeInMB+ " MB.";
									   message='<h4 class="text-center padding-10 text-info">'+msg+'</h4>';
									   message +='<div class="text-center padding-bottom-10"><input type=\'button\' value=\''+okMsg+'\' class=\'btn btn-blue-2\' onclick="$.fancybox.close()" /></div>';
									   $('.error-dialog').html(message);
									   showModalBoxWithoutClose('.error-dialog');
										   /*$('.error-dialog').html(getLocalMessage("Common.file.validation.error.msg.upload") +sizeInMB+ " MB.");
 	                            	   showModalBox('.error-dialog');*/
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
						
						
					var divlist = $("div[id^='file_list_']");
					var url = document.URL;
					url = url.substr(0, url.indexOf('?'));
					var size = parseInt(divlist.length);
					if (size > 0) {
						for (var i = 0; i < divlist.length; i++)
						{
							var obj = divlist[i].getAttribute('id');
							otherTask(obj, url);
						}
					}
							
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
						if(index != 0)
							msg+="<img src='css/images/close.png' alt='Remove' width='17' title='Remove' id='"+value+"' onclick='doFileDelete(this)' ></li>";
				 });
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
	    var data	=	'fileId='+$(obj).attr('id')+'&'+data1;	
		
	 	var jsonResponse	=	 __doAjaxRequest(url,'post',data,false,'json');
	 	
	 	/*
	 	 * If User Want To do Any task After Deletion of any file from FileMap
	 	 * need To give implementation of otherDeletionTask(obj) Respective js file
	 	 */
	 	
	 	try{
	 		otherDeletionTask(obj);
	 	}catch(e){
	 		console.log(e);
	 	}	
	
if(!(($.browser.msie) && ($.browser.version==9.0))){ 	
	
    $('.fileUploadClass').val(null);
  	
	if(jsonResponse.status){
		$("#"+jsonResponse.hiddenOtherVal).html(jsonResponse.message);
		//D#129631 start
		let delObj = $(obj).attr('id');
		let pos=delObj.lastIndexOf("_");
		let position=delObj.substring(pos + 1)
		$('#tooltip_'+position).show();
		//D#129631 end
	}else{
		showErrormsgboxTitle(jsonResponse.message);
	}		
	
	
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
				if(index != 0)
					msg+="<img src='css/images/close.png' alt='Remove' width='17' title='Remove' id='"+value+"' onclick='doFileDelete(this)' ></li>";
		 });
	  }
	});
	 
	 if(jsonResponse.status){
		 $("#"+jsonResponse.hiddenOtherVal).html(msg);
		//D#129631 start
			let delObj = $(obj).attr('id');
			let pos=delObj.lastIndexOf("_");
			let position=delObj.substring(pos + 1)
			$('#tooltip_'+position).show();
			//D#129631 end
	 }else{
		 showErrormsgboxTitle(msg);
	 }
				
	 

	}
	 
 }
 

function __doAjaxRequestForFileUpload(url,reqType,data,async,dataType,fieldName,callback) 
{		

	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
			dataType	: 	dataType,
			url 		: 	url,
			data 		: 	data,
			type		: 	reqType,
			enctype		:	'multipart/form-data',
			processData	:	async,
			contentType	:	async,
			beforeSend : function(xhr) {
				if(header && header != null){xhr.setRequestHeader(header, token);}
		    	if(fieldName)
		    	{		    		
		    		progressLoadingWindow(fieldName);
		    	}
		    },
			
			success	: function(jsonResponse) 
			{		
				
				
				if(! jsonResponse.status){
					showErrormsgboxTitle(jsonResponse.url);
				}else{
					//D#129631 start
					let codeArray=fieldName.split("_");
					let position=codeArray[1];
					$('#tooltip_'+position).hide();
					//D#129631 end	
				}	
					
				
				
				$("#"+jsonResponse.hiddenOtherVal).html(jsonResponse.message);
				
				try
				{
				
					if(callback != undefined && callback != "" ){
						eval(callback);
					} else {
						var action= url.substr(0, url.indexOf('?')); 
					        otherTask(jsonResponse.hiddenOtherVal,action);
					}
				}
				catch(e)
				{
					console.log(e);
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
	var message	='<i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i>';	
	
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
