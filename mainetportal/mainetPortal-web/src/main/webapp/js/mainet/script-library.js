
/***

Author : Pranit.Mhatre
Created Date : 01 November, 2013


***/

/***
 * Declaring global level java script variables.
 */

// Division name for displaying transparent window 
// while showing child pop-up box or any kind of message box.

var modalDivName	=	'modalwindow';

// Division name for displaying any main form.
var formDivName		=	'.content-page';

var	dialog			=	'.dialog';

var parDivName		=	'.popup-dialog';

var childDivName	=	'.child-popup-dialog';

var regFormDivName  =	'.popup-form-div';

var	errMsgDiv		=	'.msg-dialog-box';


var options = '';

var control = '';

//serverUrl,oMyForm,multiFileObject,htmlObj
var oMyForm1		=	'';

var serverUrl1		=	'';

var multiFileObject1		=	'';

var htmlObj1		=	'';

var htmlObjList	=	new Array();

var multiFileObjectList	=	new Array();

var countList	=	new Array();

var count1		=	'';

var xOffset = 8;
var yOffset = 20;

var en = {
        required: "This field is required.",
        remote: "Please fix this field.",
        email: "Please enter a valid email address.",
        url: "Please enter a valid URL.",
        date: "Please enter a valid date.",
        dateISO: "Please enter a valid date ( ISO ).",
        number: "Please enter a valid number.",
        digits: "Please enter only digits.",
        equalTo: "Please enter the same value again.",
   
    },
    reg = {
        required: "यह फ़ील्ड आवश्यक है।",
        remote: "कृपया यह फ़ील्ड ठीक करें।",
        email: "कृपया एक वैध ई - मेल एड्रेस डालें।",
        url: "कृपया एक मान्य यूआरएल दर्ज कीजिए।",
        date: "कृपया एक मान्य तिथि प्रविष्ट करें।",
        dateISO: "Si us plau, escriu una data (ISO) vàlida.",
        number: "कृपया एक सही संख्या डालिये।",
        digits: "कृपया केवल अंक दर्ज करें।",
        creditcard: "Si us plau, escriu un número de tarjeta vàlid.",
        equalTo: "कृपया फिर से वही संख्या डालिये।",
        extension: "Si us plau, escriu un valor amb una extensió acceptada.",
        maxlength: $.validator.format("कृपया {0} से ज्यादा नहीं दर्ज करें।"),
        minlength: $.validator.format("कृपया कम से कम {0} दर्ज करें।"),
        rangelength: $.validator.format("Si us plau, escriu un valor entre {0} i {1} caracters."),
        range: $.validator.format("Si us plau, escriu un valor entre {0} i {1}."),
        max: $.validator.format("Si us plau, escriu un valor menor o igual a {0}."),
        min: $.validator.format("Si us plau, escriu un valor major o igual a {0}.")
    };

jQuery.fn.log=function (logmsg){
	console.log("%s: %o", logmsg,this);
	return this;
};

jQuery.fn.limitMaxlength = function(options){

    var settings = jQuery.extend({
        attribute: "maxlength",
        onLimit: function(){},
        onEdit: function(){}
    }, options);

    // Event handler to limit the textarea
    var onEdit = function(){
        var textarea = jQuery(this);
        var maxlength = parseInt(textarea.attr(settings.attribute));

        if(textarea.val().length > maxlength){
            textarea.val(textarea.val().substr(0, maxlength));

            // Call the onlimit handler within the scope of the textarea
            jQuery.proxy(settings.onLimit, this)();
        }

        // Call the onEdit handler within the scope of the textarea
        jQuery.proxy(settings.onEdit, this)(maxlength - textarea.val().length);
    }

    this.each(onEdit);

    return this.keyup(onEdit)
                .keydown(onEdit)
                .focus(onEdit)
                .live('input paste', onEdit);
}
$(document).ready(function(){
	
	$("#carosel .owl-carousel").owlCarousel({
        autoPlay: 3000, //Set AutoPlay to 3 seconds
        pagination: false,
        items : 6,
        navigation : true,
        margin:5,
        itemsDesktop : [1199,6],
        itemsDesktopSmall : [980,3],
        itemsTablet: [768,2],
        itemsTabletSmall: false,
        itemsMobile : [479,1]
    });
	
	
	
	if($('div').hasClass('right-login')){
	    $('a#toggle').hide();
	}
	

 	 


  	
	
 var onEditCallback = function(remaining){
   $(this).siblings('.charsRemaining').text("Characters remaining: " + remaining);

        if(remaining > 0){
         //   $(this).css('background-color', 'white');
        }
    }

    var onLimitCallback = function(){
//        $(this).css('background-color', 'red');
    }

   /* $('textarea[maxlength]').limitMaxlength({
        onEdit: onEditCallback,
        onLimit: onLimitCallback
 } );*/ 
});


 


$( document ).ready(function() {
	
	 //$("a").attr("role","link") Role Define in each link 
		   
	$('.charsRemaining').hide(); 
	$('textarea').focus(function(){
	
	      $(this).parent().find('.charsRemaining').show();       
	})
	$('textarea').blur(function(){

      $(this).parent().find('.charsRemaining').hide();       
})

});

$( document ).ready(function() {
	
	  $("#toggle-btn").click(function() {
	       $("#tabmenuhover").slideToggle("slow"); 
 	    });
	  
	  

	   
	
	
	
	/*if ( $.browser.msie) {
		
			
		$('input[type=text]').each(function() {
		  var input = $(this);
		  if (input.val() == input.attr('placeholder')) {
		    input.val('');
		  }
		}).blur(function() {
		  var input = $(this);
		  if (input.val() == 0 || input.val() == input.attr('placeholder')) {
		    input.val(input.attr('placeholder'));
		  }
		}).blur();
	}*/

jQuery('.hasNumber').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
  
});	
jQuery('.hasCharacter').keyup(function () { 
    this.value = this.value.replace(/[^a-z A-Z]/g,'');
   
});

jQuery('.hasCharacterInUperCase').keyup(function () { 
	
	this.value = this.value.replace(/[^a-z A-Z]/g,'').toUpperCase();
	
});

jQuery('.maxLength20').keyup(function () { 
	 $(this).attr('maxlength','20');
  
});

jQuery('.maxLength1950').keyup(function () { 
	
	 $(this).attr('maxlength','1990');
 
});

jQuery('.maxLength100').keyup(function () { 
	
	 $(this).attr('maxlength','95');

});


jQuery('.maxLength500').keyup(function () { 
	
	 $(this).attr('maxlength','490');

});

jQuery('.maxLength150').keyup(function () { 
	
	 $(this).attr('maxlength','150');

});

jQuery('.maxLength30').keyup(function () { 
	 $(this).attr('maxlength','60');
  
});

jQuery('.maxLength3').keyup(function () { 
	 $(this).attr('maxlength','3');
 
});
jQuery('.maxLength4').keyup(function () { 
	 $(this).attr('maxlength','4');

});

jQuery('.maxLength1980').keyup(function () { 
	 $(this).attr('maxlength','2000');
 
});

jQuery('.maxLength1000').keyup(function () { 
	 $(this).attr('maxlength','1000');

});
jQuery('.maxLength300').keyup(function () { 
	 $(this).attr('maxlength','300');
 
});

jQuery('.hasDecimal').keyup(function () { 
    this.value = this.value.replace(/[^0-9\.]/g,'');
});

jQuery('.hasPincode').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','6');
});

jQuery('.hasMobileNo').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','10');
});

jQuery('.hasMobileNo12').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','12');
});

jQuery('.hasemailclass').blur(function () { 		
    this.value = this.value.replace(/[^a-zA-Z0-9 @.]/g,'');
});

jQuery('.hasFaxNo').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','12');
});


//$('ul#nav li:has(ul)').addClass('submenu');




	/* Dance icon Hover Effect */
	$('.danceicon').each(function(){
		
		var new_icon = $(this).find('.fa').clone().addClass('fa-fadeout');
		$(this).prepend($(new_icon));
		
	});

	$('.hasAmount').keyup(function () {
	
		//var regx	=	/^\d+(?:\.\d{0,2})?$/;
		//var regx	= /^[-+]?[0-9]*.[0-9]{2}$/;
		var regx	= /^\d{0,5}(\.\d{1,3})?$/;
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			$(this).val('');	
		}   
	});

	$('.has3Decimal').keyup(function () {
		
		var regx	=   /^\d+(\.\d{0,3})?$/;
	
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		}   
	});

});

/*** Some Common Function Which are Used For To Get Element Info ***/

function isElemVisibleById(elem){	
	return $('#'+elem).css('display')=='none'?false:true;	
}

function isElemVisibleByClass(elem){	
	return $('.'+elem).css('display')=='none'?false:true;	
}

function getElemId(elem) {
	return document.getElementById(elem);
}

function getIdByName(elem){
	return getElemName(elem).id;
}

function getElemName(elem) {
	return document.getElementsByName(elem);
}

function getElemNameVal(elem) {
	var ee=getElemName(elem);
	return ee[0].value;
}

function getElementValue(elem) {
	return getElemId(elem).value;
}

function setElementValue(elem,val) {	
	var ee=getElemName(elem);
	ee[0].value=val;
}

function setFocus(elem) {
	getElemId(elem).focus();
}

function setFocusByName(elem) {
	var ee=getElemName(elem);
	ee[0].focus(); 
}

function isEmpty(elem) {
	
	if($.trim($(getElemId(elem)).val())=='' || $.trim($(getElemId(elem)).val())=='0')
		return true;
	else 
		return false;
}

function isEmptyNumField(elem) {
	if($.trim($(getElemId(elem)).val())=='')
		return true;
	else 
		return false;
}

function makeEmpty(elem) {
	$(getElemId(elem)).val('');
}

function defaultDropDown(elem,html) {
	$(getElemId(elem)).html(html);
}

function roundTo(num,decimals){
	return Math.round(num*Math.pow(10,decimals))/Math.pow(10,decimals);
}


function hasCheckBoxCheck(elem) {alert($('input:checkbox[name='+elem+']').attr('checked'));
	return $('input:checkbox[name='+elem+']').attr('checked');
}

function scroll(element){	
	$('html, body').animate({scrollTop:$('#'+element).position().top}, 'slow');
}

function findClosestElementId(sourceElement,htmlElement){
	return $(sourceElement).closest(htmlElement).attr('id');
}

function checkRedirectResponse(response) {
	
	if (response)
	{
		if ($.isPlainObject(response) && response.rediretToHomePage)
		{
			window.location.assign(response.rediretToHomePage);
			throw new Error();
		}
	}
}

/**
 * To convert given date object into dd/MM/yyyy format.
 * eg. 15/07/2013	
 */

function getDateFormat(dateObj)
{
	if(dateObj==null || typeof(dateObj)=='undefined' || dateObj=='') return null;
	else
	{
		var date	=	new	Date(dateObj);		
		return [date.getDate().padLeft(),(date.getMonth()+1).padLeft(),date.getFullYear()].join('/');
	}
}

function ignoreCase(value) {
	return value.toUpperCase();
}

/*** 
 * 
 *	To pad 0's at the left side of number.
 *
 *	e.g. if number is 5 then 05
 *		 if number is 0 then 00
 */

Number.prototype.padLeft = function(base,chr){
   var  len = (String(base || 10).length - String(this).length)+1;
   return len > 0? new Array(len).join(chr || '0')+this : this;
};


/**
 * To check whether given HTML element exists in page or not.
 */
jQuery.fn.doesExist = function(){
    return jQuery(this).length > 0;
};

/**
 * To make center position of given element.
 */
jQuery.fn.center = function () {
    this.css("position","absolute");
    this.css("top", ( $(window).height() - this.height() ) / 2+$(window).scrollTop() + "px");
    this.css("left", ( $(window).width() - this.width() ) / 2+$(window).scrollLeft() + "px");
    return this;
};

/**
 * To make full screen of given element.
 */
jQuery.fn.fullScreen = function () {
    this.css("position","absolute");
    this.css("height", ( $(window).height())+ "px");
    this.css("width", ( $(window).width())+ "px");
    return this;
};

/**
 * This function set drop-down element index for given value. 
 * @param objId the drop-down element id.
 * @param val the value at which drop-down index to be set.
 */
function setDropDownSelected(objId,val) {	
	$('#'+objId+' option').each(function(i, option)
		{ 
	        if(i==val) $('#configureType option:eq('+i+')').attr('selected', true);        
	        else $('#configureType option:eq('+i+')').attr('selected', false);
	    }
	);	
}


/**
 * This method provides the facility for sending ajax request to the server.
 * @param url the requested url.
 * @param reqType the request type of ajax.
 * @param data the requested data to be send.
 * @param async the synchronous request or not ?
 * @param dataType the data type of the response.
 * @returns ajaxResponse the ajax response data from server.
 */
/*function __doAjaxRequest(url,reqType,data,async,dataType,contentType) {
	
	
	var result	='Empty';
	
	$.ajax({
			url 	: 	url,
			data 	: 	data,
			type	: 	reqType,
			async	:	async,
			dataType: 	dataType,
			contentType: contentType,
			success	: function(response) {
				checkRedirectResponse(response);
				result=response;
				return result;
			},
			error: function(xhr, ajaxOptions, thrownError) {  
	            alert(xhr.responseText);
	            alert(ajaxOptions);
	            alert(thrownError);      
			}
		});
	
	return result; 
}*/

function __doScriptLoading(scriptPath)
{
	$.getScript(scriptPath, function( data, textStatus, jqxhr ) {
	  console.log( data ); // Data returned
	  console.log( textStatus ); // Success
	  console.log( jqxhr.status ); // 200
	  console.log( "Load was performed." );
	});
}
/**
 * This method provides the facility for sending ajax request to the server.
 * @param url the requested url.
 * @param reqType the request type of ajax.
 * @param data the requested data to be send.
 * @param async the synchronous request or not ?
 * @param dataType the data type of the response.
 * @param loaderId the loading element id for showing loading data.
 * @returns ajaxResponse the ajax response data from server.
 */
/*function __doAjaxRequest(url,reqType,data,async,dataType,contentType) {
	
	
	var result=null;
	
	$.ajax({
			url 	: 	url,
			data 	: 	data,
			type	: 	reqType,
			async	:	async,
			dataType: 	dataType,
			contentType: contentType,
			success	: function(response) {
				checkRedirectResponse(response);
				result=response;
			},
			error: function(xhr, ajaxOptions, thrownError) {  
	            alert(xhr.responseText);
	            alert(ajaxOptions);
	            alert(thrownError);      
			}
		});
	
	return result; 	
}*/


function fn_showPreview() {
	
	var tip = $(this).find('.tip');
	
	$(".tip_trigger").hover(function(){		
	    tip = $(this).find('.tip');
	    tip.show(); //Show tooltip
	}, function() {		
	    tip.hide(); //Hide tooltip        
	}).mousemove(function(e) {
		
	    var mousex = e.pageX + 20; //Get X coodrinates
	    var mousey = e.pageY + 20; //Get Y coordinates
	    var tipWidth = tip.width(); //Find width of tooltip
	    var tipHeight = tip.height(); //Find height of tooltip

	    //Distance of element from the right edge of viewport
	    var tipVisX = $(window).width() - (mousex + tipWidth);
	    //Distance of element from the bottom of viewport
	    var tipVisY = $(window).height() - (mousey + tipHeight);

	    if ( tipVisX < 20 ) { //If tooltip exceeds the X coordinate of viewport
	        mousex = e.pageX - tipWidth - 20;
	    } if ( tipVisY < 20 ) { //If tooltip exceeds the Y coordinate of viewport
	        mousey = e.pageY - tipHeight - 20;
	    } 
	    tip.css({  top: mousey, left: mousex });
	});
}

/**
 * To reload or refresh the grid  
 * @param gridId the id of grid which is to be refresh.
 */
function reloadGrid(gridId,datatype) {
	//$('#'+gridId).trigger('reloadGrid');
	if(datatype==null || datatype=='')
		datatype='json';
	
	$("#"+gridId).setGridParam(
							{datatype:datatype
							}
						).trigger('reloadGrid');
}


function findGridRowCount(gridId)
{
	return $("#"+gridId).jqGrid('getGridParam', 'records');
}

/**
 * This metthod is used to export grid json object data to excel format data.
 * @param $gridID the grid id of which data to be export.
 * @param $URL the url containing request send to the server.
 * @param $fileName the file name to be give to exported file. 
 * @param $excludeArrId the array containing ids which to be exclude while generating csv file.  
 */
function fn_exportToExcel_old($gridID,$URL,$fileName,$excludeArrId)
{	
	  if($gridID==null || $gridID=='') alert('Please provide valid grid id.');	 
	  
	  else
	  {	  	 
		  if($fileName==null || $.trim($fileName)=='') $fileName="sampleExcel";
		
		  var keys=[], ii=0, fileData="";
		  
		  var ids=$gridID.getDataIDs();  			// Get All IDs		  
		 
		  var row=$gridID.getRowData(ids[0]);     	// Get First row to get the labels
		  
		  for (var k in row) 
		  {
			  var found=false;
			  for(var t=0;t<$excludeArrId.length;t++)
				{
					if($excludeArrId[t]==k)
						{
							found=true;
							break;
						}
					else
						found=false;
					
				}
			  
		    if(!found)
		    	{
		    		keys[ii++]=k;    										// capture col names
		    		fileData=(fileData+k).toUpperCase()+",";     			// output each Column as tab delimited
		    	}
		    
		  }	  
		
		 
		  fileData=fileData+"\n";   								// Output header with end of line
		 
		  for(var i=0;i<$gridID.jqGrid('getGridParam','data').length;i++) 
		  {
		    //row=$gridID.getRowData(ids[i]); 						// get each row
		    
			row=$gridID.jqGrid('getGridParam','data')[i];   		// get each row
			  
		    for(var j=0;j<keys.length;j++) 		    	
		    	{
		    		fileData=fileData+row[keys[j]]+","; 		// output each Row as tab delimited	    		
		    		
		    	}
		   
		    	fileData=fileData+"\n";  			// output each row with end of line
		    	
		  }
		  
		  fileData=fileData+"\n"; 					// end of line at the end
		  
		  var form = "<form name='exportform' action='"+$URL+"' method='post'>";
		  form = form + "<input type='hidden' name='fileData' value='"+fileData+"'>";
		  form = form + "<input type='hidden' name='fileName' value='"+$fileName+"'>";
		  form = form + "</form><script>document.exportform.submit();</sc"+"ript>";
		  
		  OpenWindow=window.open('', '');
		  OpenWindow.document.write(form);
		  OpenWindow.document.close();
		  
	  }
}

/**
 * This metthod is used to export grid json object data to excel format data.
 * @param $gridID the grid id of which data to be export.
 * @param $URL the url containing request send to the server.
 * @param $fileName the file name to be give to exported file. 
 * @param $excludeArrId the array containing ids which to be exclude while generating csv file.  
 */
function fn_exportToExcel($gridID,$URL,$fileName,$excludeArrId)
{	
	if($gridID==null || $gridID=='') alert('Please provide valid grid id.');	 
	  
	  else
	  {	  	 
		  if($fileName==null || $.trim($fileName)=='') $fileName="sampleExcel";
		
		  var keys=[], ii=0, fileData="";
		  
		  var ids=$gridID.getDataIDs();  			// Get All IDs		  
		 
		  var row=$gridID.getRowData(ids[0]);     	// Get First row to get the labels
		  
		  var colModels=$gridID.jqGrid("getGridParam", "colModel");
		  
		  for (var k in row) 
		  {	  
			  var found=false;
			  for(var t=0;t<$excludeArrId.length;t++)
				{
					if($excludeArrId[t]==k)
						{
							found=true;
							break;
						}
					else
						found=false;
					
				}
			  
		    if(!found)
		    	{
		    		keys[ii]=k;    										// capture col names
		    		fileData=(fileData+colModels[ii].index).toUpperCase()+",";     			// output each Column as tab delimited
		    		ii++;
		    	}
		    
		  }	  
		
		 
		  fileData=fileData+"\n";   								// Output header with end of line
		 
		  //$gridID.jqGrid('getGridParam','data')
		  for(var i=0;i< $gridID.getDataIDs().length;i++) 
		  {
		    //row=$gridID.getRowData(ids[i]); 						// get each row
		    
			//row=$gridID.jqGrid('getGridParam','data')[i];   		// get each row
			row=$gridID.getRowData($gridID.getDataIDs()[i]);
		    for(var j=0;j<keys.length;j++) 		    	
		    	{
		    		fileData=fileData+row[keys[j]]+","; 		// output each Row as tab delimited	    		
		    		
		    	}
		   
		    	fileData=fileData+"\n";  			// output each row with end of line
		    	
		  }
		  
		  fileData=fileData+"\n"; 				// end of line at the end
		
		  var form = "<form name='exportform' action='"+$URL+"' method='post'>";
		  form = form + "<input type='hidden' name='fileData' value='"+fileData+"'>";
		  form = form + "<input type='hidden' name='fileName' value='"+$fileName+"'>";
		  form = form + "</form><script>document.exportform.submit();</sc"+"ript>";
		  
		  OpenWindow=window.open('', '');
		  OpenWindow.document.write(form);
		  OpenWindow.document.close();
		  
	  }
	
}
function setGeneralTimePicker(id,type)
{
	if(type=='id')
		{
	id='#'+id;
		}
	else if(type=='class')
		{
		id='.'+id;
		}
	$(id).timepicker({
		 showPeriod: true,
		 showLeadingZero: true,
		 showOn: 'focus',

		  minutes: {
		        starts: 0,                // First displayed minute
		        ends: 59,                 // Last displayed minute
		        interval: 1               // Interval of displayed minutes
		    }, 
		 
	 });
}
function setTimePickerForId(id)
{
	setGeneralTimePicker(id,'id');
}
function setTimePickerForClass(id)
{
	setGeneralTimePicker(id,'class');
}
function setMultipleTimePickerForId(ids)
{
	for(var i=0;i<ids.length;i++)
		{
		var id =ids[i];
		setTimePickerForId(id);
		}
}
function setMultipleTimePickerForClass(ids)
{
	for(var i=0;i<ids.length;i++)
		{
		var id =ids[i];
		setTimePickerForClass(id);
		}
}



function prepareChildrens(lookUpList, objChild)
{
	var firstEl	=	($('#'+objChild+' option:first-child'));
	var lastEl	=	($('#'+objChild+' option:last-child'));
	
	$('#'+objChild).html(firstEl);
	
	$.each(lookUpList, function(index){
		var lookUp=lookUpList[index];
		
		var codId=lookUp.lookUpId;
		var code = lookUp.lookUpCode;
		var codDesc=lookUp.lookUpDesc;		
		
		
		$('#'+objChild).append($('<option>', {
		    value: codId,
		    code : code,
		    text: codDesc
		}));
	});
	
	if(lastEl.text()==getLocalMessage("common.all"))
	{
		$('#'+objChild).append(lastEl);
	}
}
/*** To perform operations related to child pop-up box***/

/***	[START]		***/

/**
 * To open child pop-up box for add details.
 * @param url the string literal containing server url.
 */
/*function _openChildForm(formUrl)
{
	var divName	=	childDivName;
	
	var url	=	formUrl+'?add';
	var ajaxResponse	=	__doAjaxRequest(url,'post',{},false);
	
	if(typeof(ajaxResponse)=="string")
	{	
		_showChildForm(divName, ajaxResponse);
	}
	
	else 
	{
		var message='';
		message	+='<p>'+ ajaxResponse['command']['message']+'</p>';
		message	+='<p style=\'text-align:right;margin: 5px;\'>'+						
				'<input type=\'button\' value=\'Close\''+ 
				' onclick="closebox(\''+errMsgDiv+'\')"/>'+
				'</p>';
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		showModalBox(errMsgDiv);
		return false;
	}	
}*/

/**
 * To open child pop up form in edit mode
 * @param formName the name of the form
 * @param divName the name of the division in which form should display
 */

function _openChildFormUpdate(formName)
{
	var divName	=	childDivName;
	var theForm	=	'#'+formName;
	var requestData = __serializeForm(theForm);
	var ajaxResponse	=	doAjaxLoading($(theForm).attr('action'), requestData, 'html',divName);
	_showChildForm(divName, ajaxResponse);
}

function _showChildForm(divName, ajaxResponse) {
	
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	/*prepareTags();*/
	showModalBox(divName);
}

function showSaveResultBox(returnData, successMessage, redirectUrl) {

	var success = returnData['command']['status'];
	var commandMessageText = returnData['command']['message'];
	
	var messageText = success ? successMessage : commandMessageText;
	
	var message='';
	var cls = getLocalMessage('eip.page.process');
	
	if(redirectUrl=="" || redirectUrl==null){
	
		message	+='<p class="text-blue-2 text-center padding-15">'+ messageText+'</p>';
		message	+='<div class=\'class="text-center padding-bottom-10"\'>'+	
		
		'</p>';
		
	}
	else{
    
		 message	+='<p class="text-blue-2 text-center padding-10">'+ messageText+'</p>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-success \'    '+ 
		' onclick="closebox(\''+errMsgDiv+'\',\''+redirectUrl+'\')"/>'+	
		'</div>';
	}
	
	
	
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


/**
 * use this function to show decision box
 * 
 * @param obj 
 * @param warnMsg :pass the warning meg string to show on decision box
 * @param successMsg : pass the success msg to show on final save
 * @param redirectUrl : pass the success url
 * @returns {Boolean}
 */

function showDecisionBox(obj,warnMsg,successMsg, redirectUrl) {

	var no = getLocalMessage('eip.commons.no');
	
	var yes = getLocalMessage('eip.commons.yes');
	
	var message='';
	message	+='<p>'+ warnMsg+'</p>';
	message	+='<p >'+						
			'<input class="btn_custom" type=\'button\' value=\''+yes+'\'  id=\'yes\' '+ 
			' onclick="onDecisionBoxAction(\''+obj+'\',\''+successMsg+'\',\''+redirectUrl+'\')"/>'+
			'<input class="btn_custom" type=\'button\' value=\''+no+'\'  id=\'no\' '+ 
			' onclick="closeDecisionBox(\''+errMsgDiv+'\')"/>'+
			'</p>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	
	return false;
}


/**
 *  this will perform final save action if you click on yes
 * @param id
 * @param successMsg  : will show success msg
 * @param redirectURL : will redirect to success URL after final save
 */

function onDecisionBoxAction(id,successMsg,redirectURL){
	
	
	var element = document.getElementById(id);
	var	errMsgDiv		=	'.msg-dialog-box';
	
	$(errMsgDiv).hide();
	disposeModalBox();
	
	saveOrUpdateForm(element,successMsg,redirectURL,'');
	
	
}






/**
 * To save child form data to server.
 * @param formName the string literal containing name of the form whose data to be saved.
 * @param divName the string literal containing name of the division who want to close 
 * 					after saved successfully.
 * @returns {Boolean} false.
 */
function _saveChildForm(obj, successMessage)
{
	var formName	=	findClosestElementId(obj, 'form');
	
	var theForm	=	'#'+formName;
	
	var divName	=	childDivName;
	
	var requestData = __serializeForm(theForm);
	
	var formAction	=	$(theForm).attr('action');	
	
	var url 	=	formAction+'?saveOrUpdate';	
	
	
	var returnData =__doAjaxRequest(url,'post',requestData,false);

	if ($.isPlainObject(returnData))
		{
			_closeChildForm(divName);
			
			var gridId	=	'grid';
			gridId	+=	formName.substr(3,formName.length-1);
			
			if($('#'+gridId).doesExist())			
			{
				reloadGrid(gridId);
			}
			
			//_openChildForm(formAction);
			
			showSaveResultBox(returnData,successMessage,'');
			try
			{
				afterGridSave();				
			}
			catch(e)
			{
				
			}
			
		}
	else if (typeof(returnData) === "string")
		{
			$(divName).html(returnData);
			
			prepareDateTag();
		}
	else {
		alert("Invalid datatype received : " + returnData);
	}
		
	return false;
}

/**
 * To cancel current form request.
 * @param formName the string literal containing current form name.
 * @param divName the string literal containing name of the division.
 * @returns {Boolean} value.
 * 
 */
function _cancelChildForm(obj)
{	
	var formName	=	findClosestElementId(obj, 'form');
	
	var theForm	=	'#'+formName;
	
	var url = $(theForm).attr('action')+'?cancel';
	
	var ajaxResponse	=	doAjaxLoading(url, {}, 'json', childDivName);
	
	if ($.isPlainObject(ajaxResponse))
	{
		_closeChildForm(childDivName);
	}
	
	return false;
}

/**
 * To delete row from the grid.
 * @param formName the name of the form whose row data to be delete.
 * @param msgDiv the name of the division in which to be message to be shown.
 */
function _deleteChildRow(formName)
{	
	var theForm	=	'#'+formName;
	
	var requestData = __serializeForm(theForm);
	
	var url 	=	$(theForm).attr('action');
	
	var gridId	=	'grid';	
	
	gridId	+=$(theForm).attr('action').substr(0,$(theForm).attr('action').lastIndexOf('.html'));	
	
	messageDialog(url,requestData,gridId);
	
	return false;
}


/**
 * To close pop-up box.
 * @param divName the string literal containing name of the division which is to be close. 
 */
function _closeChildForm(divName)
{
	var divName	=	childDivName;
	
	$(divName).hide();
	$(divName).empty();
	disposeModalBox();
}

/***	[END]		***/




/*** To perform operations on single form	***/

/*** 	[START]		***/

function doAjaxLoading(url,requestData,responseType,divName)
{
	var loading	 ='<strong class="fa fa-spinner fa-pulse fa-5x fa-fw margin-top-30"></strong>';
	
	$(divName).addClass('ajaxloader').fadeIn('slow');	
	$(divName).html(loading);
		
	return	__doAjaxRequest(url,'post',requestData,false,responseType);

}

function openGridDialog(formName)
{
	var divName	=	parDivName;
	
	var theForm	=	'#'+formName;
	
	var url	=	$(theForm).attr('action');
	var ajaxResponse	=	doAjaxLoading(url, {}, 'html', divName);
	
	
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
/*	prepareTags();*/
	showModalBox(divName);
	
	//_showChildForm(divName, ajaxResponse);
}

/**
 * To open new form for add new information.
 * @param formUrl the string literal containing url of the server. 
 * @param divName the string literal containing name of the division in thiwch form to displayed.
 */
function openForm(formUrl,actionParam)
{
	if (!actionParam) {
		
		actionParam = "add";
	}
	
	var divName	=	formDivName;
	var ajaxResponse	=	doAjaxLoading(formUrl+'?'+actionParam,{},'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
}

/**
 * To save or update form data.
 * @param formName the string literal containing name of the form 
 * 						whose data to be send to server. 
 * @param gridId the string literal having id of the grid.
 * @param errMsgDiv the string literal containing name of the division 
 * 								which message to be displyed.  
 * @returns {Boolean} false.
 */
function saveOrUpdateForm(obj, successMessage, successUrl, actionParam)
{
	
	if (!actionParam) {
		
		actionParam = "save";
	}
	return doFormActionForSave(obj,successMessage, actionParam, true , successUrl);
}

function doFormActionForSave(obj,successMessage, actionParam, sendFormData, successUrl)
{
	
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	var requestData = {};
	
	if (sendFormData) {
		
		requestData = __serializeForm(theForm);
	}

	var url	=	$(theForm).attr('action')+'?' + actionParam;
	
	
	var returnData=__doAjaxRequestForSave(url, 'post',requestData, false,'',obj);
	if ($.isPlainObject(returnData))
	{
		var message = returnData.command.message;
		
		var hasError = returnData.command.hasValidationError;
		
		if (!message) {
			message = successMessage;
		}
		
		if(message && !hasError)
			{
			   	if(returnData.command.hiddenOtherVal == 'SERVERERROR')
			   		
			   		showSaveResultBox(returnData, message, 'ServerError.html');
			   	
			   	else
			   		
			   		showSaveResultBox(returnData, message, successUrl);
			}
	/*	else if(hasError)
		{
			$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');	
		}*/
		else
			return returnData;
		
	}
	else if (typeof(returnData) === "string")
	{
		$(formDivName).html(returnData);	
		/*prepareTags()*/
	}
	else 
	{
		alert("Invalid datatype received : " + returnData);
	}
	
	return false;
}


function __doAjaxRequestForSave(url, reqType, data, async, dataType,obj) {
	console.log(this.event);
	
	var inputType=$(obj).attr('type');
	 
	$.ajax({
		url : url,
		data : data,
		type : reqType,
		async : async,
		dataType : dataType,
	    beforeSend: function() { 
            $(obj).val(getLocalMessage('lbl.please.wait'));
            $(obj).prop('disabled', true);
      },

		//headers   : {"SecurityToken": token},
		success : function(response) {
			result = response;
			if(response=="success"){
				 $(obj).val(getLocalMessage('submit.msg'));
				 $(obj).prop('disabled', false);
              
            }

		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});
	
	return result;
}



function doFormAction(obj,successMessage, actionParam, sendFormData, successUrl)
{
	
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	var requestData = {};
	
	if (sendFormData) {
		
		requestData = __serializeForm(theForm);
	}

	var url	=	$(theForm).attr('action')+'?' + actionParam;
	
	
	var returnData=__doAjaxRequest(url, 'post',requestData, false);
	if ($.isPlainObject(returnData))
	{
		var message = returnData.command.message;
		
		var hasError = returnData.command.hasValidationError;
		
		if (!message) {
			message = successMessage;
		}
		
		if(message && !hasError)
			{
			   	if(returnData.command.hiddenOtherVal == 'SERVERERROR')
			   		
			   		showSaveResultBox(returnData, message, 'ServerError.html');
			   	
			   	else
			   		
			   		showSaveResultBox(returnData, message, successUrl);
			}
		else if(hasError)
		{
			$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');	
		}
		else
			return returnData;
		
	}
	else if (typeof(returnData) === "string")
	{
		$(formDivName).html(returnData);	
	/*	prepareTags();*/
	}
	else 
	{
		alert("Invalid datatype received : " + returnData);
	}
	
	return false;
}

function jQGridformRedirect(formName,actionParam)
{
	var theForm	=	'#'+formName;
	
	var url	=	$(theForm).attr('action');
	
	if (!actionParam) 
	{
		
	}
	else
	{
		url+='?'+actionParam;
	}
	
	$(theForm).submit();
}

function draftRedirect(formName,actionParam)
{
	var theForm	=	'#'+formName;
	
	var url	=	$(theForm).attr('action');
	
	if (!actionParam) 
	{
		
	}
	else
	{
		url+='?'+actionParam;
	}
	
	$(theForm).submit();
}


/**
 * To open form in update mode.
 * @param formName the string literal containing name of the form to be open for update.
 * @param divName the string literal containing  
 */
function openUpdateForm(formName,actionParam)
{
	
	var theForm	=	'#'+formName;
	
	var divName	=	formDivName;
	
	var url	=	$(theForm).attr('action');
	
	if (!actionParam || actionParam == '' || actionParam == undefined) 
	{
		
	}
	else
	{
		url+='?'+actionParam;
	}
	
	var requestData = __serializeForm(theForm);
	
	var ajaxResponse	=	doAjaxLoading(url, requestData, 'html', divName);
	
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	

	
	$(divName).show();
	
}

/**
 * To delete selected record.
 * @param formName the string literal containing name of the form
 * 					whose data to be deleted.
 */
function deleteRecord(formName,gridId,actionParam)
{
	var theForm	=	'#'+formName;
	var requestData = __serializeForm(theForm);
	
	var formAction	=	$(theForm).attr('action');
	
	if(typeof actionParam!=='undefined' && actionParam!='')
	{
		formAction+='?'+actionParam;
	}
	
	//var gridId	=	'grid'+formAction.substr(0,formAction.lastIndexOf('.html'));		
	reloadGrid(gridId);	
	reloadGrid(gridId);
	
	messageDialog(formAction,requestData,gridId);	
}

/***	[END]		***/

/**
 * To show message dialog box.
 * <p>It provides <code>YES / NO option(s)</code> for confirmation.</p>
 * @param requestUrl the requested server url for delete operation.
 * @param requestData the requested data to be send to server.
 * @param gridObject the grid id to be refresh after deletion if exists. 
 */
function messageDialog(requestUrl,requestData,gridObject)
{
	var message='';
	
	var msg = getLocalMessage('eip.commons.del');
	var cls1 = getLocalMessage('eip.commons.yes');
	var cls2 = getLocalMessage('eip.commons.no');
	
	message='<h4 class="text-center padding-10 text-info">'+msg+'</h4>';
	message	+='<div class="text-center padding-bottom-10"><input type=\'button\' value=\''+cls1+'\' class=\'btn btn-blue-2 margin-right-5\'';
	message	+=' onclick="yesOption(\''+requestUrl+'\',\''+requestData+'\',\''+gridObject+'\')" />';
	message	+='<input type=\'button\' value=\''+cls2+'\' class=\'btn btn-danger\'';
	message	+=' onclick="$.fancybox.close()" /></div>';
	
	$(errMsgDiv).removeClass('ok-msg').addClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);	
}

function msgBox(messageText)
{
	var message='';
	message	+=	'<p>'+messageText+'</p>';
	message	+=	'<p style=\'text-align:right;margin: 5px;\'>';					
	message	+=	'<input type=\'button\' value=\'Close\'';
	message	+=	' onclick="return closebox(\''+errMsgDiv+'\')"/>';
	message	+=	'</p>';
	
	$(errMsgDiv).html(message);	
	
	$(errMsgDiv).show();
	
	showModalBox(errMsgDiv);	
}

/**
 * 
 * @param requestUrl
 * @param requestData
 * @param gridId
 */
function yesOption(requestUrl,requestData,gridId)
{
	
	var ajaxResponse	=	__doAjaxRequest(requestUrl, 'post',requestData,false, 'json');		
	var cls4 = getLocalMessage('eip.commons.closeMe');
	var okMsg = getLocalMessage('bt.ok');
	message='';
	message	+=	'<h4 class="text-center text-info padding-10">'+ajaxResponse.message+'</h4>';
	/*message	+=	'<p style=\'text-align:right;margin: 5px;\'>';					
	message	+=	'<input type=\'button\' value=\''+cls4+'\'';
	message	+=	' onclick="return closebox(\''+errMsgDiv+'\')"/>';
	message	+=	'</p>';*/
	message	+='<div class="text-center padding-bottom-10"><input type=\'button\' value=\''+okMsg+'\' class=\'btn btn-blue-2\'';
	message	+='onclick="$.fancybox.close()" /></div>';
	
	$(errMsgDiv).html(message);	
	
	$(errMsgDiv).show();
	
	showModalBoxWithoutClose(errMsgDiv);	
	
	if(ajaxResponse.status)
	{
		$(errMsgDiv).removeClass('warn-msg').addClass('ok-msg');
		
		try
		{
			afterGridSave();				
		}
		catch(e)
		{
			
		}
		
	}	
	else
	{
		$(errMsgDiv).removeClass('ok-msg').addClass('error-msg');
	}
	
	if(typeof(gridId)!=='undefined')
	{
		if($('#'+gridId).doesExist())			
		{
			reloadGrid(gridId);
		}
	}
}

/**
 * 
 * @param divName
 */
function noOption(divName)
{
	return closebox(divName);
}
/**
 * 
 * @param divName
 * @param redirectUrl
 * @param ajaxResponse
 * @returns {Boolean}
 * 
 * 
 * 
 * 
 */


function showPage1(divName,redirectUrl){
	
	alert("redirectUrl");
}

function closebox(divName,redirectUrl){
	if(typeof redirectUrl!== undefined && redirectUrl!=='undefined' && redirectUrl!=null && (redirectUrl!=''))
	{	
		try
		{
		 var lastIndex = redirectUrl.lastIndexOf("?");
		 
	     var lastchar = redirectUrl.substring(lastIndex	+	1);	
	     	   	   
		if(lastchar == 'PrintReport' || lastchar == 'PrintChallanOnline' || lastchar == 'PrintCounterReceipt')
			{
				 window.open(redirectUrl,'_blank');			
				 window.location.href='CitizenHome.html';
			}
		else if(lastchar == 'PrintULBCounterReceipt' )
		     {
				 window.open(redirectUrl,'_blank');			
				 window.location.href='ChallanAtULBCounter.html';
			}
		
		else if(lastchar == 'EditUserProfile')
		{
		 window.location.href='CitizenHome.html?EditUserProfile';
		}
		
		else
			{
			$("#postMethodFormSuccess").prop('action', '');
			$("#postMethodFormSuccess").prop('action', redirectUrl);
			$("#postMethodFormSuccess").submit();			
			// window.location.href=redirectUrl; 
			}
		}
		catch(e)
		{
			//window.location.href=redirectUrl;
			$("#postMethodFormSuccess").prop('action', '');
			$("#postMethodFormSuccess").prop('action', redirectUrl);
			$("#postMethodFormSuccess").submit();
		}
	}
	
	disposeModalBox();
	
	return false;
}
/**
 * To show background transparent window while displaying pop-up box. 
 */

function closeFancyOnLinkClick(childDialog){
	
	
	$.fancybox.close();
	
}


function showModalBox(childDialog)
{
	
	$.fancybox({
        type: 'inline',
        href: childDialog,
        openEffect  : 'elastic', // 'elastic', 'fade' or 'none'
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

function showModalBoxWithoutClose(childDialog)
{
	
	$.fancybox({
        type: 'inline',
        href: childDialog,
        closeBtn   : false,
        openEffect  : 'elastic', // 'elastic', 'fade' or 'none'
        'width': 400,
        'height': 90,
        'autoSize': false,
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

/* ----- Fancybox for loading starts ----- */
function showLoadingModalBoxWithoutClose(childDialog) {
	$.fancybox({
        type: 'inline',
        href: childDialog,
        closeBtn   : false,
        openEffect  : 'elastic', // 'elastic', 'fade' or 'none'
        'width': 120,
        'height': 90,
        'autoSize': false,
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
/* ----- Fancybox for loading ends ----- */

/**
 * To dispose background transparent window while displaying pop-up box.
 */
function disposeModalBox()
{	
	$('#'+modalDivName).hide();
	return false;
}


function uploadAttachment(serverUrl,multiFileObject,diaplayObj)
{
	var htmlCss	=	"<ul style='float:left;margin-left:90px;padding-top:2px;font-size:10px'>";
	
	 var oMyForm = new FormData();
	
	 $.each(multiFileObject.files,function(index){
		  oMyForm.append("files", multiFileObject.files[index]);
	 });
  
	$.ajax({
	    url: serverUrl+"?FileUpload",
	    data: oMyForm,
	    processData: false,
	    contentType: false,
	    type: 'post', 
	    beforeSend : function() {
	    	$('#'+diaplayObj).html(htmlCss+'<li>Uploading...</li></ul>');
	    },
	    success : function(jsonResponse) {
	    	
	    	checkRedirectResponse(response);
	    	
	    	if(diaplayObj)
	    	{
	    		var html	=	htmlCss;
	    		
	    		var fileList	=	jsonResponse.split(',');
	    		
	    		for(var i=0;i<fileList.length;i++)
	    		{
	    			html+="<li>"+fileList[i]+"</li>";
	    		}
	    		html+="</ul>";
	    		
	    		$('#'+diaplayObj).html(html);
	    	}
	    	
	    }	
	 });
  
  return false;
}

function doUploading(object,multiFileObject,diaplayObj)
{
	var	formName =	findClosestElementId(object, 'form');
	
	var serverUrl	=	$('#'+formName).attr('action');
	return uploadAttachment(serverUrl,multiFileObject,diaplayObj);
}

/**
 * 
 * @param serverUrl the string containing server url where file upload functionality to be performed.
 * @param oMyForm the object containing entire form data.
 * @param multiFileObject the object of file.
 * @param diaplayObj 
 * @returns {Boolean}
 */
function uploadFileAttachment(serverUrl,oMyForm,multiFileObject,htmlObj,count,flag)
{ 
	
	 var msg = getLocalMessage('eip.commons.upload');
	 var message = getLocalMessage('eip.commons.uploaded');
	 var htmlCss='';
	 if(flag)
		 htmlCss	=	"<ul style='margin-top: 5px; margin-left: -35px;'>"; 
	 else
		 htmlCss	=	"<ul>";
	
	 oMyForm1 			= 	oMyForm;
	 serverUrl1			=	serverUrl;
	 multiFileObject1	=	multiFileObject;
	 htmlObj1			=	htmlObj;
	 count1				=	count;
	 
	 var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	 $.ajax({
	    url: serverUrl+"?FileUploads",
	    data: oMyForm,
	    processData: false,
	    contentType: false,
	    type: 'post', 
	    beforeSend : function(xhr) {
	    	if(header && header != null){xhr.setRequestHeader(header, token);}
	    	if(htmlObj)
	    	{
	    		//$('#'+htmlObj).html(htmlCss+'<li>File(s) Uploading...Please wait</li></ul>');
	    		progressDialogBox(htmlObj);
	    	}
	    },
	    success : function(jsonResponse) {	
	    	
	    	//checkRedirectResponse(response);
	    	var html	=	htmlCss;		
			
			if(jsonResponse.status)
			{	 
				
			if(jsonResponse.message)
				{
				var fileList	=	jsonResponse.message.split(',');
			
				
				if(jsonResponse.url == 'FILEEXISTS')
					showErrormsgboxTitle('File Name Already Exists, Please Choose Different File');
	
				if(jsonResponse.hiddenOtherVal)
					{
						var lastchar = jsonResponse.hiddenOtherVal;								
					   	if(lastchar == 'MAXFILECOUNT')
					   		showErrormsgboxTitle(jsonResponse.url);

					}
				if(fileList[0])
					{
						if(flag)
						{
						
							html+=fileList;	
						
						}
				else
					{
					
					
						if(parseInt(fileList.length)==1)
						{
					     html+="<li>"+fileList.length+msg+"<li>";
						}
						else
						{
						 html+="<li>"+fileList.length + message +"<li>";	
						}
						for(var i=0;i<fileList.length;i++)
						{
							
							
							if(count)
								{
									html+="<li>"+fileList[i]+ "&nbsp;&nbsp;&nbsp;&nbsp;<img alt='Remove File' width='17' title='Remove File' src='css/images/close.png' style='cursor:pointer' id='removefil'" +
											'onclick="removeMultipleFileUpload(\''+fileList[i]+'\','+count+')" /> </li>';
								}
							else
								{
								html+="<li>"+fileList[i]+ "&nbsp;&nbsp;&nbsp;&nbsp;<img alt='Remove File' width='17' title='Remove File' src='css/images/close.png' style='cursor:pointer' id='removefil'" +
								'onclick="removeSingleFileUpload(\''+fileList[i]+'\')" /> </li>';
	
								
							}
						}
					}
				}
			}
		}
			/*else
			{	
					html+="<li>"+jsonResponse['command']['message']+"</li>";
			}*/
			
			html+="</ul>";
			
			if(htmlObj)
			{				
				$('#'+htmlObj).html(html);
			}
	    },
		error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
	    
	 });
  
  return false;
}

function removeSingleFileUpload(fileNameToDelete)
{
	
	oMyForm1.append('removeFile',fileNameToDelete);
	
	oMyForm1.append('refreshMode','N');
	
	uploadFileAttachment(serverUrl1,oMyForm1,multiFileObject1,htmlObj1);

}

function removeMultipleFileUpload(fileNameToDelete,currentCount)
{
	
	oMyForm1.append('removeFile',fileNameToDelete);
	
	oMyForm1.append('currentCount',currentCount);	
	
	oMyForm1.append('refreshMode','N');
	
	oMyForm1.append('elemPath',$("#hiddenElemPath"+currentCount).val());
	
	htmlObj1	=	$("#hiddenHtmlPath"+currentCount).val();

	uploadFileAttachment(serverUrl1,oMyForm1,multiFileObject1,htmlObj1);

}

function fileUploadMultipleValidationList()
{
	for(var i=0;i<$("#hiddenMAxListSize").val();i++)
	{
		try
		{
			oMyForm1.append('refreshMode','Y');		
			oMyForm1.append('elemPath',multiFileObjectList[i]);		
			oMyForm1.append('currentCount',countList[i]);		
			uploadFileAttachment(serverUrl1,oMyForm1,multiFileObjectList[i],htmlObjList[i],countList[i]);
		}
		catch(e)
		{
			
		}
	}
}

function excelUploadValidationList()
{
	for(var i=0;i<$("#hiddenMAxListSize").val();i++)
	{
		try
		{
			oMyForm1.append('refreshMode','Y');		
			oMyForm1.append('elemPath',multiFileObjectList[i]);		
			oMyForm1.append('currentCount',countList[i]);		
			uploadFileAttachment(serverUrl1,oMyForm1,multiFileObjectList[i],htmlObjList[i],countList[i],'Y');
		}
		catch(e)
		{
			
		}
	}
}

function fileUploadValidationList()
{
	try
	{
	oMyForm1.append('refreshMode','Y');
	
	uploadFileAttachment(serverUrl1,oMyForm1,multiFileObject1,htmlObj1);
	}
	catch(e)
	{
		
	}

}

function doFileUploading(object,multiFileObject,htmlObj,hasImageUpload,validnFunction,fileSize,errorMsg,count,maxFileCount,flag)
{
	
	try
	{	
		
		
		if(multiFileObject.length)
		{				
			if(multiFileObject[0].value =='')				
				return false;
		}
	  else
		{
			if(multiFileObject.value =='')				
				return false;
		}
		var fileNamePattern = new RegExp(/^[a-z A-Z 0-9_.-]+$/);
		if (!fileNamePattern.test(object.files[0].name)) {			
 		  
 		   return false;
		}
		
		if(fileSize)
		{
			if(object.files[0].size > fileSize )
				{
					showErrormsgboxTitle(errorMsg);
					return false;
				}
		}
	}
	catch(e)
	{
		
	}  
	try
	{
		if(!this[validnFunction]($(object)))
		{ 			
			return false;
		}	
	}
	catch(e)
	{
	}
	var	formName =	findClosestElementId(object, 'form');
	var pathVar	=	$('#elemPath_'+$(object).attr('id')).val();

	/**
	 * To get form data of input element.
	 */
	var formData	=	$('#'+formName).serializeArray();
	
	var oMyForm = new FormData();
	
	oMyForm.append('elemPath',pathVar);
	
	/***
	 * To appeand key/value pair to FormData object.
	 */
	$.each(formData,function(key,input){
		oMyForm.append(input.name,input.value);
    });
	
	/***
	 * To append uploaded file(s) to FormData object.
	 */
	
	
	if(multiFileObject.length)
		{	
			$.each(multiFileObject[0].files,function(index){
				  oMyForm.append("files", multiFileObject[0].files[index]);
			});
		}
	else
		{
			$.each(multiFileObject.files,function(index){
				  oMyForm.append("files", multiFileObject.files[index]);
			});
		}
	
	oMyForm.append('removeFile','');
	
	oMyForm.append('refreshMode','N');
	
	if($("#hiddenrandnum").val())
		oMyForm.append('randnum',$("#hiddenrandnum").val());	
	else
		oMyForm.append('randnum',$("#hiddenrandnum"+count).val());	
	
	oMyForm.append('currentCount',count);	
	
	oMyForm.append('maxFileCount',maxFileCount);	
	
	htmlObjList.push(htmlObj);
	
	multiFileObjectList.push(pathVar);
	
	countList.push(count);
	
	var serverUrl	=	$('#'+formName).attr('action');
	if(serverUrl.lastIndexOf('?')!=-1)
	{
		serverUrl	=	serverUrl.substring(0,serverUrl.lastIndexOf('?'));
	}
	
	return uploadFileAttachment(serverUrl,oMyForm,multiFileObject,htmlObj,count,flag);
}


/**
 * To get sub details information of given code and id for it&acute;s 
 * 	sub HTML drop-down element.
 * <p>This function is only applicable get and set sub-information of <code>HTML drop-down</code> elements.</p>
 *   
 * @param formName the string literal containing name of the current form.
 * @param objParent the parent drop-down object whose code and id is required to fetch sub-information. 
 * @param objChild the child drop-down object to whom information data to be appended.
 * @param defaultString the string literal contains default text for drop-down.
 * @param showAll the boolean literal indicate whether to show <code>All</code> option in child drop-down element.
 */
function getSubDetails(objParent,objChild)
{
	if(typeof(objChild)!='undefined' &	$("#"+objChild).doesExist())
	{
		if(!$("#"+objChild).is("select"))
		{
			$("#"+objChild).log('Given HTML child element must be SELECT type.');
			return false;
		}		
	}
	else
	{
		$("#"+objChild).log('Either given element does not exists or not a valid HTML element ');
		
		return false;
	}
	
	
	var theForm	=	'';
	
	theForm	=	'#'+findClosestElementId(objParent,'form');
	
	var data	=	'typeCode='+$.trim($('option:selected', $(objParent)).attr('code'))+'&';
	data	+='typeId='+$.trim($(objParent).val());	
	
	var url	=	$(theForm).attr('action');
	

	if (url.indexOf("?") >= 0)
	{
		url	=	url.substring(0,url.indexOf("?"));
	}
	
	
	url+='?getSubInfo';
	
	var lookUpList=__doAjaxRequest(url,'post',data,false,'json');
	
	prepareChildrens(lookUpList, objChild);
}


function getSubDetailsForLevel(objParent,objChild,level)
{
	if(typeof(objChild)!='undefined' &	$("#"+objChild).doesExist())
	{
		if(!$("#"+objChild).is("select"))
		{
			$("#"+objChild).log('Given HTML child element must be SELECT type.');
			return false;
		}		
	}
	else
	{
		$("#"+objChild).log('Either given element does not exists or not a valid HTML element ');
		
		return false;
	}
	
	var theForm	=	'';
	
	theForm	=	'#'+findClosestElementId(objParent,'form');
	
	var data	=	'typeCode='+$.trim($('option:selected', $(objParent)).attr('code'))+'&';
	data	+='typeId='+$.trim($(objParent).val());	
	
	data	+='&level='+level;	
	
	var url	=	$(theForm).attr('action');
	

	if (url.indexOf("?") >= 0)
	{
		url	=	url.substring(0,url.indexOf("?"));
	}
	
	url+='?getSubInfoByLevel';
	
	var lookUpList=__doAjaxRequest(url,'post',data,false,'json');
	
	prepareChildrens(lookUpList, objChild);
}

function getSublookUps(objParent,count)
{
	var element = null;
	
	var parentId = objParent.getAttribute('id');
	
	var parentValue = parseInt(getLastElementFromString(parentId));
	
	var childValue = parentValue + 1;
	
	var cleareStartValue = childValue + 1;
	
	var objChild = parentId.replace(parentValue,childValue);
	
	if(typeof(objChild)!='undefined' &	$("#"+objChild).doesExist())
	{
		if(!$("#"+objChild).is("select"))
		{
			$("#"+objChild).log('Given HTML child element must be SELECT type.');
			return false;
		}		
	}
	else
	{
		$("#"+objChild).log('Either given element does not exists or not a valid HTML element ');
		
		return false;
	}
	
	var theForm	=	'';
	
	theForm	=	'#'+findClosestElementId(objParent,'form');
	
	var data	=	'typeCode='+$.trim($('option:selected', $(objParent)).attr('code'))+'&';
	data	+='typeId='+$.trim($(objParent).val());	
	
	var url	=	$(theForm).attr('action');

	if (url.indexOf("?") >= 0)
	{
		url	=	url.substring(0,url.indexOf("?"));
	}
	
	url+='?getSubInfo';
	
	var lookUpList=__doAjaxRequest(url,'post',data,false,'json');
	
	prepareChildrens(lookUpList, objChild);
	
	for (var i = cleareStartValue; i <= count; i++) { 
		
		element = parentId.replace(parentValue,i);

		var firstEl	=	($('#'+element+' option:first-child'));
		
		var lastEl	=	($('#'+element+' option:last-child'));
		
		$('#'+element).html(firstEl);
		
		if(lastEl.text()==getLocalMessage("common.all"))
		{
			$('#'+element).append(lastEl);
		}
	}
}

function getLastElementFromString(str)
{
	return str.charAt(str.length - 1); 
}

/**
 * To search details for the selected criteria details.
 * @param formName the string literal containing name of the current form. 
 */
function findAll(obj,flag)
{
	var formName	=	findClosestElementId(obj,'form');
	
	var theForm	=	'#'+formName;
	
	var url		=	'';
	
	if(!flag)		
	{	
		url	=	$(theForm).attr('action')+'?search';
	}
	else
	{	
		url	=	$(theForm).attr('action');
	}
	
	var result	=	hasSearchContenet(url,'?search');
	if(result)
	{
		var index	=findIndex(url,'?search');
		if(index!=-1)
		{
			url	=	url.substring(0, index);
			url	+=	'?search';
		}
	}
		
	$(theForm).attr('action',url);
	$(theForm).submit();
}

function hasSearchContenet(searchContent,keyword)
{
   return searchContent.indexOf(keyword) != -1 ? true : false;
}

function findIndex(searchContent,keyword)
{
	return searchContent.indexOf(keyword);
}

/**
 * To empty current form information .
 * @param formName the name of the form to be empty. 
 */
function emptyForm(obj, url) {
	var formName = findClosestElementId(obj, 'form');

	var theForm = '#' + formName;

	var prevFormAction = "";
	/**
	 * Added againt D-1098 dated 220514
	 */
	if (!url) {
		prevFormAction = $(theForm).attr('action');

	} else {
		prevFormAction = url ;
	}

	/**
	 * End------------------------------ 
	 */
	//var prevFormAction = $(theForm).attr('action');

	var returnData = __doAjaxRequest(prevFormAction + '?reset', 'post', false,
			'');

	if (returnData == null) {
		console.log("Return json object getting :" + returnData);
		window.location.href = prevFormAction;
	}

	else if (returnData.status) {
		window.location.href = prevFormAction;
	} else {
		var message = '';
		message += '<p>' + returnData.message + '</p>';
		message += '<p style=\'text-align:right;margin: 5px;\'>'
				+ '<input type=\'button\' value=\'Close\''
				+ ' onclick="closebox(\'' + errMsgDiv + '\',\''
				+ prevFormAction + '\')"/>' + '</p>';
		$(errMsgDiv).removeClass('ok-msg');
		$(errMsgDiv).html(message);

		$(errMsgDiv).show();
		showModalBox(errMsgDiv);

	}
}

function forEach(object) {
	$.each(object,function(k,v){
		alert(k	+	'	=	'+	v);
	});
}

/*function prepareTags() {
	prepareDateTag();
	
}*/

function resetForm(resetBtn) {
	
	
	cleareFile(resetBtn);
	
	if (resetBtn && resetBtn.form) {
		
		$('[id*=file_list]').html('');
		$('.error-div').remove();
		resetBtn.form.reset();
		/*prepareTags();*/
		
	};
}

function clearEditForm(resetBtn) {
	
	cleareFile(resetBtn);
	if (resetBtn && resetBtn.form) {
		$('[id*=file_list]').html('');
		$(resetBtn.form)[0].reset();
		$('.alert').remove();
		
	};
}

function resetFormOnAdd(obj)
{
	$('.error-div').remove();
	$('.alert').remove();
	$('[id*=file_list]').html('');
	$(".chosen-select-no-results").val('0').trigger('chosen:updated');	
	$('input[type=radio]').prop('checked',false);
	$('input[type=checkbox]').prop('checked',false);
    $(':input',obj)
    .not(':button, :submit, :reset, :hidden')
    .val('')
    .removeAttr('checked')
    .removeAttr('selected');
	return false; 
}

/*
function resetForm2(resetBtn) {
	
	var url	=	'ChargeDetails.html?reset';

	__doAjaxRequest(url, 'post', '' , false,'json');	    		

}*/

/*
function prepareDateTag() {
	
	var dateFields = $('.date,.datepicker');
	dateFields.each(function () {
		
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});

	$('.datepicker').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true
	});
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate: '-0d',
		yearRange: "-100:-0"
	});
	$('.datetimepicker').datetimepicker({
		dateFormat: 'dd/mm/yy',
		timeFormat: "hh:mm tt",
		changeMonth: true,
		changeYear: true,
		//yearRange: "0:+100",
		//maxDate:'now'
	});
	
	
	$(".fromDateClass").datepicker({		     	
		        dateFormat: 'dd/mm/yy',	
				changeMonth: true,
				changeYear: true,
		        onSelect: function(selected) {
		          $(".toDateClass").datepicker("option","minDate", selected)	
		        }	
		    });
	
	$(".toDateClass").datepicker({			      	
		        dateFormat: 'dd/mm/yy',	
				changeMonth: true,
				changeYear: true,
		        onSelect: function(selected) {	
		           $(".fromDateClass").datepicker("option","maxDate", selected)	
		        }	
		    }); 

}*/



/*


function prepareDateTag1() {
	
	var dateFields = $('.date,.datepicker,.lessthancurrdate');
	dateFields.each(function () {
		
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});

	$('.datepicker').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true
	});
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate: '-0d',
		yearRange: "-100:-0"
	});
	$('.datetimepicker').datetimepicker({
		dateFormat: 'dd/mm/yy',
		timeFormat: "hh:mm tt",
		changeMonth: true,
		changeYear: true,
		yearRange: "-100:-0",
		maxDate:'now'
	});
}

function stringToArray(stringVal,separator)
{
	return stringVal.split(separator);
}

function getElementType(elem)
{
	if(!$('#'+elem).doesExist())
	{
		$('#'+elem).log('Element does not exists');
	}
	if($('#'+elem).is('input'))
	{
		return true;
	}
	else
	{
		return false;
	}
}*/

function showErrormsgboxTitle(title)
{
	
	var message=' ';
	message	+=	'<h4 class="text-center text-info padding-top-0 padding-10">'+ title+'</h4>';
	$(errMsgDiv).removeClass('ok-msg').addClass('warn-msg');
	$(errMsgDiv).html("<br/>"+message);		
	$(errMsgDiv).show();	
	$('#btnNo').focus();
	showModalBox(errMsgDiv);	

}

function deleteFileFromRemote(obj,fileName)
{
		var formName	=	findClosestElementId(obj,'form');
		
		var theForm	=	'#'+formName;
		
		var url	=	$(theForm).attr('action');
		
		url=url.split("?");
		
		var mainUrl=url[0]+'?DeleteFile&filename='+fileName;
			
		__doAjaxRequest(mainUrl, 'post', '' , false,'json');
		
		fileName=fileName.replace(/\./g, "_");
		
		$("#div_"+fileName).hide();

}

function progressDialogBox(htmlElem,messageText)
{
	var message	='<i class=\'fa fa-circle-o-notch fa-spin fa-2x fa-fw\' title=\'Loading...\' alt=\'Loading...\'></i>';
	message=(messageText) ? messageText : message;
	
	$('#'+htmlElem).html(message);
	$('#'+htmlElem).show();
	
	//showModalBox(errMsgDiv);
}

function filterImage(imgObj)
{
	var fileExtension = ['jpeg', 'jpg', 'png', 'gif','bmp'];
	
	if ($.inArray($(imgObj).val().split('.').pop().toLowerCase(), fileExtension) == -1)
	    {
			showErrormsgboxTitle("Only '.jpeg','.jpg', '.png', '.gif','.bmp' formats are allowed.");
			
			return false;
	    }

	return true;
}
function jpegImageValidation(imgObj)
{
	
	var fileExtension = ['jpeg'];
	
	if ($.inArray($(imgObj).val().split('.').pop().toLowerCase(), fileExtension) == -1)
	    {
			showErrormsgboxTitle("Only '.jpeg' formats are allowed.");
			
			return false;
	    }

	return true;
}

function filterVideo(imgObj)
{
	var fileExtension = ['mp4','avi','3gp','mpg','swf','flv'];
	
	if ($.inArray($(imgObj).val().split('.').pop().toLowerCase(), fileExtension) == -1)
	    {
			showErrormsgboxTitle("Only '.mp4','.avi','.3gp','.mpg','.swf','.flv' formats are allowed.");
			
			return false;
	    }

	return true;
}


function pdfvalidn(imgObj)
{
	var fileExtension = ['pdf'];
	
	if ($.inArray($(imgObj).val().split('.').pop().toLowerCase(), fileExtension) == -1)
	    {
			showErrormsgboxTitle("Only .pdf file allowed");
			
			return false;
	    }

	return true;
}


// This method for pdf,word and excel validation

function pdfWordExcelvalidn(imgObj)
{
	var fileExtension = ['pdf','doc','docx','xls','xlsx'];
	
	if ($.inArray($(imgObj).val().split('.').pop().toLowerCase(), fileExtension) == -1)
	    {
			showErrormsgboxTitle("Only '.pdf .doc .docx .xls .xlsx ' file allowed");
			
			return false;
	    }

	return true;
}

function designXlsFilevalidn(imgObj)
{
	var fileExtension = ['xls'];
	
	if ($.inArray($(imgObj).val().split('.').pop().toLowerCase(), fileExtension) == -1)
	    {
			showErrormsgboxTitle("Only '.xls' file allowed");
			
			return false;
	    }

	return true;
}

function designFilevalidn(imgObj)
{
	var fileExtension = ['dxf','dwg','pdf','zip','rar'];
	
	if ($.inArray($(imgObj).val().split('.').pop().toLowerCase(), fileExtension) == -1)
	    {
			showErrormsgboxTitle("Only '.dxf .dwg .pdf .zip .rar' file allowed");
			
			return false;
	    }

	return true;
}

function _customAjaxRequest(obj, successMessage,divclassName,actionName,addParam)
{
	var formName	=	findClosestElementId(obj, 'form');
	
	var theForm	=	'#'+formName;
	
	var divName	=	divclassName;
	
	var requestData = __serializeForm(theForm);
	
	var formAction	=	$(theForm).attr('action');	
	
	var url 	=	formAction+'?'+actionName;	
	
	var returnData =__doAjaxRequest(url,'post',requestData,false);

	if ($.isPlainObject(returnData))
	{
		
	}
	else if (typeof(returnData) === "string")
	{
		$(divName).html(returnData);
		
		prepareDateTag();
	}
	else {
		alert("Invalid datatype received : " + returnData);
	}
	
	return false;
}
function _customAjaxRequest1(obj, successMessage,divclassName,actionName,addParam)
{
	var formName	=	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	var divName	=	divclassName;
	var requestData = __serializeForm(theForm);
	var formAction	=	$(theForm).attr('action');		
	var url 	=	formAction+'?'+actionName;
	var returnData =__doAjaxRequest(url,'post',requestData,false);
	return returnData;
	
     
	/*if ($.isPlainObject(returnData))
	{
		
	}
	else if (typeof(returnData) === "string")
	{
		$(divName).html(returnData);
		prepareDateTag1();
	}
	else 
	{
		alert("Invalid datatype received : " + returnData);
	}
	return false;*/
}

function getLocalMessage(key)
{
	var url = 'CitizenHome.html?getMessage&key='+key;
	var returnData =__doAjaxRequest(url,'post',key,false);
	return returnData;
}

/**
 * To show background transparent window while displaying pop-up box. 
 */



function showRegisterBox(childDialog)
{
	
	$.fancybox({
        type: 'inline',
        href: childDialog,
        openEffect  : 'elastic', // 'elastic', 'fade' or 'none'
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

function cleareFile(obj)
{	var action = obj.form.action;
	var url	=	action+'?cleareFile';
   var count= __doAjaxRequest(url,'post',{},false);
   
   for(var i = 0; i < count; i++)
	   {
	  $("#file_list_"+i).find("*").remove(); 
	   }

	fileUploadMultipleValidationList();
}

function signPDF(obj)
{	var action = obj.form.action;
	var url	=	action+'?signPDF';
	__doAjaxRequest(url,'post',{},false);
}

function viewScrutiny(appId)
{
	var divName		=	'.form-div';
	
	var data	=	'appId='+appId;
	
	var url	= "ScrutinyLabel.html";
	
	url+='?setViewData';
	
	var ajaxResponse = doAjaxLoading(url,data,'html',divName);

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	
	/*prepareTags();*/
}

////////////////////////
/**
 * 
 */


function viewApplAndScrutiny(applId,securityToken)
{
	var data	=	'applId='+applId;
	
	var url	= "ScrutinyGridForm.html";
	
	url+='?getMenuParam';

	var menuparams =__doAjaxRequest(url,'post',data,false);
	
	if(menuparams.length == 3)
	{
		//openForm(menuparams[0]+'?viewApplication');
		/*data = 'applId='+applId+'&menuparams='+menuparams;
		var divName	=	formDivName;
		var ajaxResponse	=	doAjaxLoading(menuparams[0]+'?viewApplication',data,'html',divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		*/
		/*prepareTags();*/
		
		
		var my_form=document.createElement('FORM');
		my_form.name='myForm';
		my_form.method='POST';
		my_form.action=menuparams[0]+'?viewApplication';


		var	my_tb=document.createElement('INPUT');
		my_tb.type='HIDDEN';
		my_tb.name='menuparams';
		my_tb.value=menuparams;
		my_form.appendChild(my_tb); 

		my_tb=document.createElement('INPUT');
		my_tb.type='HIDDEN';
		my_tb.name='applId';
		my_tb.value=applId;
		my_form.appendChild(my_tb); 
		
		my_tb=document.createElement('INPUT');
		my_tb.type='HIDDEN';
		my_tb.name='SecurityToken';
		my_tb.value=securityToken;
		my_form.appendChild(my_tb);
		
		document.body.appendChild(my_form);
		my_form.submit();
	}
}

function openLoiForm(obj,applId,formUrl,mode,labelId,flag,token,securityToken)
{
	//var textBox =  $.trim($('#lValue'+labelId).val())
	
	var data = 'labelId='+labelId+'&labelValue='+$('#lValue'+labelId).val();
	
	var surl 	= 'ScrutinyLabel.html'+'?'+'validateDecisionString';

	var returnD =__doAjaxRequest(surl,'post',data,false);
	
	getLabelIDList(labelId);

	//getTableClauseQuery(labelId, $('#lValue'+labelId).val());
	
	var formName	=	findClosestElementId(obj, 'form');
	
	var theForm	=	'#'+formName;
	
	var requestData = 'labelId='+labelId;
	
	var formAction	=	$(theForm).attr('action');	
	
	var url 	=	formAction+'?'+'getLabelIdWithQueryString';
	
	var returnData =__doAjaxRequest(url,'post',requestData,false);

	if(returnData != '')
	{
		var labelValue = $('#lValue'+labelId).val();

		var data = 'labelValue='+labelValue;
		
		var surl 	= 'ScrutinyLabel.html'+'?'+'validateByQuery';

		var returnD =__doAjaxRequest(surl,'post',data,false);

		if(returnD == '')
		{
			var msg = returnD;
			
			var message=getLocalMessage('cfc.validateMsg');
			$(errMsgDiv).html(message);
			$(errMsgDiv).show();
		
			showModalBox(errMsgDiv);
		}
		else
		{
			var url	= "ScrutinyLabel.html";
			
			url+='?saveTempScrutinyValue';

			var formName	=	findClosestElementId(obj, 'form');
			
			var theForm	=	'#'+formName;
			
			var requestData = __serializeForm(theForm);
			
			__doAjaxRequest(url,'post',requestData,false);
			
			var my_form=document.createElement('FORM');
			my_form.name='myForm';
			my_form.method='POST';
			my_form.action=formUrl;

			var	my_tb=document.createElement('INPUT');
			my_tb.type='HIDDEN';
			my_tb.name='applId';
			my_tb.value=applId;
			my_form.appendChild(my_tb); 

			my_tb=document.createElement('INPUT');
			my_tb.type='HIDDEN';
			my_tb.name='formUrl';
			my_tb.value=formUrl;
			my_form.appendChild(my_tb); 
			
			my_tb=document.createElement('INPUT');
			my_tb.type='HIDDEN';
			my_tb.name='mode';
			my_tb.value=mode;
			my_form.appendChild(my_tb); 
			
			my_tb=document.createElement('INPUT');
			my_tb.type='HIDDEN';
			my_tb.name='labelId';
			my_tb.value=labelId;
			my_form.appendChild(my_tb); 
			
			my_tb=document.createElement('INPUT');
			my_tb.type='HIDDEN';
			my_tb.name='flag';
			my_tb.value=flag;
			my_form.appendChild(my_tb);
			
			my_tb=document.createElement('INPUT');
			my_tb.type='HIDDEN';
			my_tb.name='token';
			my_tb.value=token;
			my_form.appendChild(my_tb); 
			
			my_tb=document.createElement('INPUT');
			my_tb.type='HIDDEN';
			my_tb.name='SecurityToken';
			my_tb.value=securityToken;
			my_form.appendChild(my_tb);
			
			document.body.appendChild(my_form);
			my_form.submit();
		}
	}

	/*if(returnD != '')
	{
		var msg = returnD;
	
		var message='';
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
	
		showModalBox(errMsgDiv);
	}
	else
	{
		var url	= "ScrutinyLabel.html";
		
		url+='?saveTempScrutinyValue';

		var formName	=	findClosestElementId(obj, 'form');
		
		var theForm	=	'#'+formName;
		
		var requestData = __serializeForm(theForm);
		
		__doAjaxRequest(url,'post',requestData,false);
		
		var my_form=document.createElement('FORM');
		my_form.name='myForm';
		my_form.method='POST';
		my_form.action=formUrl;

		var	my_tb=document.createElement('INPUT');
		my_tb.type='HIDDEN';
		my_tb.name='applId';
		my_tb.value=applId;
		my_form.appendChild(my_tb); 

		my_tb=document.createElement('INPUT');
		my_tb.type='HIDDEN';
		my_tb.name='formUrl';
		my_tb.value=formUrl;
		my_form.appendChild(my_tb); 
		
		my_tb=document.createElement('INPUT');
		my_tb.type='HIDDEN';
		my_tb.name='mode';
		my_tb.value=mode;
		my_form.appendChild(my_tb); 
		
		my_tb=document.createElement('INPUT');
		my_tb.type='HIDDEN';
		my_tb.name='labelId';
		my_tb.value=labelId;
		my_form.appendChild(my_tb); 
		
		my_tb=document.createElement('INPUT');
		my_tb.type='HIDDEN';
		my_tb.name='flag';
		my_tb.value=flag;
		my_form.appendChild(my_tb);
		
		my_tb=document.createElement('INPUT');
		my_tb.type='HIDDEN';
		my_tb.name='token';
		my_tb.value=token;
		my_form.appendChild(my_tb); 
		
		my_tb=document.createElement('INPUT');
		my_tb.type='HIDDEN';
		my_tb.name='SecurityToken';
		my_tb.value=securityToken;
		my_form.appendChild(my_tb);
		
		document.body.appendChild(my_form);
		my_form.submit();
	}*/
}

function showEmpBox(childDialog)
{
	$.fancybox({
        type: 'inline',
        href: childDialog,
        openEffect  : 'elastic', // 'elastic', 'fade' or 'none'
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

/**
 * 
 */
function getSendToEmployee(applId)
{
	var divName	=	'.child-popup-dialog';
	
	var url	=	'ScrutinyGridForm.html'+'?'+'getSendToEmployee';

	var requestData = "applId="+applId;
	
	var ajaxResponse	=	__doAjaxRequest(url,'post',requestData,false);
	
	if(typeof(ajaxResponse)=="string")
	{	
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		
		/*prepareTags();*/
		showEmpBox(divName);
		//_showChildForm(divName, ajaxResponse);
	}
}

function sendBackEmployee(applId,redirectUrl)
{
	var	errMsgDiv		=	'.msg-dialog-box';

	//var redirectUrl = 'ScrutinyGridForm.html';
	
	var url	=	redirectUrl+'?'+'processForSendBack';

	var requestData = "applId="+applId;
	
	var ajaxResponse	=	__doAjaxRequest(url,'post',requestData,false);
	
	if ($.isPlainObject(ajaxResponse))
	{	
		var message='';
		message	+='<p>'+ ajaxResponse['command']['message']+'</p>';
		message	+='<p style=\'text-align:right;margin: 5px;\'>'+						
				'<input type=\'button\' value=\'Close\''+ 
				' onclick="closebox(\''+errMsgDiv+'\',\''+redirectUrl+'\')"/>'+
				'</p>';
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();

		//reloadGrid(gridid);
		
		showModalBox(errMsgDiv);
		
		return false;
	}
}

function processForPrinter(obj,param)
{
	var formName	=	findClosestElementId(obj, 'form');
	
	var theForm	=	'#'+formName;
	
	var requestData = __serializeForm(theForm);
	
	var formAction	=	$(theForm).attr('action');	
	
	var url 	=	formAction+'?'+param;

	var returnData =__doAjaxRequest(url,'post',requestData,false);

	if ($.isPlainObject(returnData))
	{	
		reloadGrid('gridScrutinyGridForm');

		$('.child-popup-dialog').hide();
		
		disposeModalBox();
		
		$.fancybox.close();
		
		window.location.href=formAction;
	}
	else 
	{
		alert('FALSE : ->'+ajaxResponse);
		return false;
	}
}


function processForSendTo(obj,param)
{
	var formName	=	findClosestElementId(obj, 'form');
	
	var theForm	=	'#'+formName;
	
	var requestData = __serializeForm(theForm);
	
	var formAction	=	$(theForm).attr('action');	
	
	var url 	=	formAction+'?'+param;

	var returnData =__doAjaxRequest(url,'post',requestData,false);

	if ($.isPlainObject(returnData))
	{	
		$('.child-popup-dialog').hide();
		
		disposeModalBox();
		
		var message='';
		message	+='<p>'+ returnData['command']['message']+'</p>';
		message	+='<p style=\'text-align:right;margin: 5px;\'>'+						
				'<input type=\'button\' class=\'css_btn\'  value=\'Close\''+ 
				' onclick="closebox(\''+errMsgDiv+'\',\''+formAction+'\')"/>'+
				'</p>';
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();

		showModalBoxWithoutClose(errMsgDiv);
	}
	else 
	{
		
		/*var message='';
		message	+='<p>Some problem occured</p>';
		message	+='<p style=\'text-align:right;margin: 5px;\'>'+						
				'<input type=\'button\' value=\'Close\''+ 
				' onclick="closebox(\''+errMsgDiv+'\',\''+formAction+'\')"/>'+
				'</p>';
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();

		showModalBox(errMsgDiv);*/
		var message='Please select the Employee Name';
		
		$('.error-div').html(message);
		$('.error-div').show();
		return false;
		
	}
}
function validateOnBlur(obj,labelId)
{
	getTableClauseQuery(labelId, $('#lValue'+labelId).val());

	validateDecisionString(labelId, $('#lValue'+labelId).val());
}


function validateOnFocus(obj,labelId)
{
	getLabelIDList(labelId);

	//getTableClauseQuery(labelId, $('#lValue'+labelId).val());
	
	var formName	=	findClosestElementId(obj, 'form');
	
	var theForm	=	'#'+formName;
	
	var requestData = 'labelId='+labelId;
	
	var formAction	=	$(theForm).attr('action');	
	
	var url 	=	formAction+'?'+'getLabelIdWithQueryString';
	
	var returnData =__doAjaxRequest(url,'post',requestData,false);

	if(returnData != '')
	{
		var labelValue = $('#lValue'+returnData[0]).val();

		var data = 'labelValue='+labelValue;
		
		var surl 	= 'ScrutinyLabel.html'+'?'+'validateByQuery';

		var returnD =__doAjaxRequest(surl,'post',data,false);

		if(returnD == '')
		{
			$('#lValue'+labelId).val('');
			$('#lValue'+labelId).attr("editable", "false");
			$('#lValue'+labelId).css({"background-color": "#F5BCA9"});
			$('#lValue'+labelId).attr("disabled", true);
			var tabindex = $('#lValue'+labelId).attr('tabindex');
			tabindex = parseInt(tabindex)+1;
			$('input[tabindex='+tabindex+']').focus();
			//$('*').attr('tabindex', tabindex).focus();
			
		}
		
		return false;
	}

	return false;
}

function getLabelIDList(labelId)
{
	var data = 'labelId='+labelId;
	
	var surl 	= 'ScrutinyLabel.html'+'?'+'getLabelList';

	var returnD =__doAjaxRequest(surl,'post',data,false);

	for (var i = 0; i < returnD.length; i++) { 

		//$('#lValue'+returnD[i]).val('');
		$('#lValue'+returnD[i]).attr("disabled", false);
		$('#lValue'+returnD[i]).css({"background-color": "#FFFFFF"});
	}
}

function validateDecisionString(labelId,labelValue)
{
	var data = 'labelId='+labelId+'&labelValue='+labelValue;
	
	var surl 	= 'ScrutinyLabel.html'+'?'+'validateDecisionString';

	var returnD =__doAjaxRequest(surl,'post',data,false);

	if(returnD != '')
	{
		var msg = returnD;
	
		var message='';
			message	+='<p>'+ msg+'</p>';
			/*message	+='<p style=\'text-align:right;margin: 5px;\'>'+						
					  '<input type=\'button\' value=\'Close\''+ 
					  ' onclick="closebox(\''+errMsgDiv+'\')"/>'+
				      '</p>';*/
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
	
		showModalBox(errMsgDiv);
	}
}

function getTableClauseQuery(labelId,labelValue)
{
	var data = 'labelId='+labelId+'&labelValue='+labelValue;
	
	var surl 	= 'ScrutinyLabel.html'+'?'+'validateTableClause';

	var returnD =__doAjaxRequest(surl,'post',data,false);

	if(returnD == false)
	{
		var msg = getLocalMessage('cfc.validateMsg');
	
		var message='';
		message	+='<p>'+ msg+'</p>';
		/*message	+='<p style=\'text-align:right;margin: 5px;\'>'+						
				'<input type=\'button\' value=\'Close\''+ 
				' onclick="closebox(\''+errMsgDiv+'\')"/>'+
				'</p>';*/
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
	
		showModalBox(errMsgDiv);
		/*
		var tabindex = $('#lValue'+labelId).attr('tabindex');
		
		$('input[tabindex='+tabindex+']').focus();*/
	}
}

function closeForm1()
{
	var childDivName	=	'.child-popup-dialog';
	
	$(childDivName).hide();
	$(childDivName).empty();
	disposeModalBox();
	
	$.fancybox.close();
}
//****************//
/**
 * To open child pop-up box for add details.
 * @param url the string literal containing server url.
 * @param param to select specifific function from the controller
 */
function _openPopUpForm(formUrl,param)
{
	var divName	=	'.child-popup-dialog';
	
	var url	=	formUrl+'?'+param;
	var ajaxResponse	=	__doAjaxRequest(url,'post',{},false);
	
	if(typeof(ajaxResponse)=="string")
	{	
		_showChildForm(divName, ajaxResponse);
	}
	
	else 
	{
		var message='';
		message	+='<p>'+ ajaxResponse['command']['message']+'</p>';
		message	+='<p style=\'text-align:right;margin: 5px;\'>'+						
				'<input type=\'button\' value=\'Close\''+ 
				' onclick="closebox(\''+errMsgDiv+'\')"/>'+
				'</p>';
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		
		showModalBox(errMsgDiv);
		
		return false;
	}	
}

function showConfirmMsgboxTitle(title,functionName,type)
{
	var cls = getLocalMessage('eip.page.process');
		
	var message=' ';
	message	+=	'<p>'+ title+'</p>';
	message	+=	'<p style=\'text-align:right;margin: 5px;\'>';
	message	+=	'<input type=\'button\' value=\''+cls+'\' id=\'btnYes\' class=\'button css_btn\' ';
	message	+=	' onclick="yesConfirmBox(\''+errMsgDiv+'\',\''+functionName+'\',\''+type+'\')"/> &nbsp;';
	message	+=	'</p>';
	
	$(errMsgDiv).removeClass('ok-msg').addClass('warn-msg');
	
	$(errMsgDiv).html(message);		
	$(errMsgDiv).show();	
	$('#btnNo').focus();
	showModalBox(errMsgDiv);	
}

function resubmissionOfApplication(applId,filterType,serviceId,token)
{
	var data	=	{'applId':applId ,'serviceId':serviceId};
	
	var url	= "ResubmissionOfApplication.html";
	
	url+='?ResubmissionOfApplication';

	var menuparams =__doAjaxRequest(url,'post',data,false);
	
	if(menuparams.length > 0)
	{
		var my_form=document.createElement('FORM');
		my_form.name='myForm';
		my_form.method='POST';
		my_form.action=menuparams[0]+'?ResubmissionApplication';


		var	my_tb=document.createElement('INPUT');
		my_tb.type='HIDDEN';
		my_tb.name='menuparams';
		my_tb.value=menuparams;
		my_form.appendChild(my_tb); 

		my_tb=document.createElement('INPUT');
		my_tb.type='HIDDEN';
		my_tb.name='applId';
		my_tb.value=applId;
		my_form.appendChild(my_tb); 
		
		my_tb=document.createElement('INPUT');
		my_tb.type='HIDDEN';
		my_tb.name='filterType';
		my_tb.value=filterType;
		my_form.appendChild(my_tb); 
		
		my_tb=document.createElement('INPUT');
		my_tb.type='HIDDEN';
		my_tb.name='SecurityToken';
		my_tb.value=token;
		my_form.appendChild(my_tb);
		
		document.body.appendChild(my_form);
		my_form.submit();
	}
}


function yesConfirmBox(divName,functionname,type)
{	
	closebox(divName);	
	this[functionname](type);
	return true;
}

function checkUnCheckAll(masterObj,childClass)
{
     $("."+childClass).prop('checked', masterObj.checked);
}

function helpDocMsg() {

	var help=getLocalMessage("helpdoc.not.found.msg");
	showErrormsgboxTitle(help);
}

function validateDob(value){

	var errorList=[];
	
    var empDOB= value;

    if(!empDOB.match(/^(0[1-9]|[12][0-9]|3[01])[\- \/.](?:(0[1-9]|1[012])[\- \/.](19|20)[0-9]{2})$/))
    {
	  errorList.push(getLocalMessage("citizen.login.reg.dob.error2")); 
    }
	else 
		
		{
		var datePat = /^(\d{1,2})(\/|-)(\d{1,2})\2(\d{4})$/;
		 var matchArray = empDOB.match(datePat);
		  month = matchArray[3]; // parse date into variables
		    day = matchArray[1];
		    year = matchArray[4];
		 
		    
		    if (month < 1 || month > 12) { // check month range
		    	 errorList.push(getLocalMessage("citizen.login.reg.dob.error1")); 
		    }
		 
		    if (day < 1 || day > 31) {
		    	 errorList.push(getLocalMessage("citizen.login.reg.dob.error1")); 
		    }
		 
		    if ((month==4 || month==6 || month==9 || month==11) && day==31) {
		    	 errorList.push(getLocalMessage("citizen.login.reg.dob.error1")); 
		    }
		 
		    if (month == 2) { // check for february 29th
		    var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
		    if (day>29 || (day==29 && !isleap)) {
		    	 errorList.push(getLocalMessage("citizen.login.reg.dob.error1")); 
		    }
		    }
	
		    	   var today = new Date();
		    	   var curr_date = today.getDate();
		    	   var curr_month = today.getMonth() + 1;
		    	   var curr_year = today.getFullYear();

		    	   var pieces = empDOB.split('/');
		    	   var birth_date = pieces[0];
		    	   var birth_month = pieces[1];
		    	   var birth_year = pieces[2];

		    	   if(curr_date < birth_date)
		    		   curr_month=curr_month-1;
		    		 if(curr_month < birth_month)
		    			 curr_year=curr_year-1;
		    		 curr_year=curr_year-birth_year;
		    			 if( curr_year <= 0)
		    				 errorList.push(getLocalMessage("citizen.login.reg.dob.error1")); 
		    			 else if(curr_year<18)
		    				 errorList.push(getLocalMessage("citizen.login.reg.dob.error3")); 
		    				 
		    	}

    return errorList;
	
}

function validateMobNo(value){
	var errorList=[];
	var mobileNo=value;
	if(mobileNo.length!=""){
		var count=0;
		
		for(var i=0; i<mobileNo.length; i++){
			
			var val=mobileNo.charAt(i);
			if(val==0){
				count++;
			}
			
		}
		
		if(count==10){
			errorList.push(getLocalMessage("citizen.login.valid.mob.error"));
		}else if(mobileNo.length<=9){
			errorList.push(getLocalMessage("citizen.login.valid.10digit.mb.error"));
		}
		
	}else{
		errorList.push(getLocalMessage("citizen.login.mob.error"));
	}
	
	return errorList;
	
}
/*
function enableDisableFormElement(formId,flag)
{
	if(flag)
	{
		$('#'+formId+' :input').prop('readonly', false);
	}
	else
	{
		$('#'+formId+' :input').prop('readonly', true);
	}
}
*/
/**
 *  Use this function to allow user to enter only valid amount
 *  @param e 			: pass event during calling
 *  @param obj			: pass form object(this)
 *  @param maxLength	: pass no of digits which you want to allow as rupees(like you need to allow only thousand then pass 4 as maxLength)
 *  @param decNo		: pass no of digits which you want to allow as paise(like you can pass 2 )
 */
function hasAmount(e, obj, maxLength, decNo) {
	var keycode;

	if (window.event)
		keycode = window.event.keyCode;
	else if (e) {
		keycode = e.which;
	} else {
		return true;
	}

	var fieldval = (obj.value), dots = fieldval.split(".").length;

	if (keycode == 46) {
		return dots <= 1;
	}
	if (keycode == 8 || keycode == 9 || keycode == 46 || keycode == 13) {
		// back space, tab, delete, enter 
		return true;
	}
	if ((keycode >= 32 && keycode <= 45) || keycode == 47
			|| (keycode >= 58 && keycode <= 127)) {
		return false;
	}
	if (fieldval == "0" && keycode == 48) {
		return false;
	}
	if (fieldval.indexOf(".") != -1) {
		if (keycode == 46) {
			return false;
		}
		var splitfield = fieldval.split(".");
		if (splitfield[1].length >= decNo && keycode != 8 && keycode != 0)
			return false;
	} else if (fieldval.length >= maxLength && keycode != 46) {
		return false;
	} else {
		return true;
	}
}


function getChargeByUTP(obj,id)
{
	var theForm	=	'';
	
	theForm	=	'#'+findClosestElementId(obj,'.form');
	
	if(theForm)
	{
		var var1 = $(obj).attr('value');
		
		var data	='utp1='+$('#'+id).val()+'&utp2='+var1+'&area='+$('#tppCoverArea').val()+'&bHeight='+$('#tppBuildHeight').val();
		
		var url	=	$(theForm).attr('action');
		
		if (url.indexOf("?") >= 0)
		{
			url	=	url.substring(0,url.indexOf("?"));
		}

		url+='?getCharges';

		var amt=__doAjaxRequest(url,'post',data,false,'json');

		if(amt)
			$('#hiddenAmt').val(amt);
		else
			$('#hiddenAmt').val(0);
	}
	else
	{
		$('#hiddenAmt').val(0);
	}
	
}

function disableButton(obj) {
    obj.disabled = true;
    obj.form.submit();
	/*$(obj).prop('disabled',true);
	var formId=$(obj).closest("form").attr('id');
	$('#'+formId).submit();*/
}
function inProcessApplication(applId,actionParam)
{
var theForm	=	'#'+formName;
	
	var url	=	$(theForm).attr('action');
	
	if (!actionParam) 
	{
		
	}
	else
	{
		url+='?'+actionParam;
	}
	
	$(theForm).submit();
}

/*window.onbeforeunload = function (event) {
    var message = 'Important: Please click on \'Save\' button to leave this page.';
    if (typeof event == 'undefined') {
        event = window.event;
    }
    if (event) {
        event.returnValue = message;
    }
    alert(message);
    return message;
};
*/
function getServiceStatus(serviceId,token)
{
 var response='<html><span>'+getLocalMessage('service.noNotEnter')+'</span></html>';
	
	if(serviceId)
	  {
		var postdata='serviceId= '+ serviceId;
	    response = __doAjaxRequest('ResubmissionOfApplication.html?getInprocessApplicationView',
			'POST', postdata, false, 'html');
	   }  
	 citizenChildPoppup(response);


}
function citizenChildPoppup(response) {
    
	var childPoppup = '.popup-dialog';
	$(childPoppup).addClass('login-dialog');
	$(childPoppup).html(response);
	$(childPoppup).show();
    showModalBox(childPoppup);

} 

function downloadFile(filePath,action)
{
	
	var csrf_token = $('meta[name=_csrf]').attr('content');
    var csrf_param = $('meta[name=_csrf_header]').attr('content');
    
   // alert("csrf_token"+csrf_token);
   // alert("csrf_param"+csrf_param);
    
    
	var my_form=document.createElement('FORM');
	my_form.name='myForm';
	my_form.method='POST';
	my_form.action=action;
	my_form.target='_blank';
	
	var	my_tb=document.createElement('INPUT');
	my_tb.type='HIDDEN';
	my_tb.name='downloadLink';
	my_tb.value=filePath;
	my_form.appendChild(my_tb); 
	
	
	var	my_tb_csrf=document.createElement('INPUT');
	my_tb_csrf.type='HIDDEN';
	my_tb_csrf.name= '_csrf';
	my_tb_csrf.value = csrf_token;
	my_form.appendChild(my_tb_csrf); 
	
	document.body.appendChild(my_form);
	my_form.submit();

	    
}

function downloadDmsFile(docId,docName,action)
{
	
	var csrf_token = $('meta[name=_csrf]').attr('content');
    var csrf_param = $('meta[name=_csrf_header]').attr('content');
    
   // alert("csrf_token"+csrf_token);
   // alert("csrf_param"+csrf_param);
    
	var my_form=document.createElement('FORM');
	my_form.name='myForm';
	my_form.method='POST';
	my_form.action=action;
	my_form.target='_blank';
	
	var	my_tb=document.createElement('INPUT');
	my_tb.type='HIDDEN';
	my_tb.name='docId';
	my_tb.value=docId;
	my_form.appendChild(my_tb); 
	
	var	my_tb2=document.createElement('INPUT');
	my_tb2.type='HIDDEN';
	my_tb2.name='docName';
	my_tb2.value=docName;
	my_form.appendChild(my_tb2); 
	
	
	var	my_tb_csrf=document.createElement('INPUT');
	my_tb_csrf.type='HIDDEN';
	my_tb_csrf.name= '_csrf';
	my_tb_csrf.value = csrf_token;
	my_form.appendChild(my_tb_csrf); 
	
	document.body.appendChild(my_form);
	my_form.submit();
}

function getSubAlphanumericLookUps(objParent,count,isAlphaNumericSort)
{
	var element = null;
	
	var parentId = objParent.getAttribute('id');
	
	var parentValue = parseInt(getLastElementFromString(parentId));
	
	var childValue = parentValue + 1;
	
	var cleareStartValue = childValue + 1;
	
	var objChild = parentId.replace(parentValue,childValue);
	
	var isAlphaNumeric = "N";
	if(isAlphaNumericSort){
		isAlphaNumeric = "Y";
	}
	
	if(typeof(objChild)!='undefined' &	$("#"+objChild).doesExist())
	{
		if(!$("#"+objChild).is("select"))
		{
			$("#"+objChild).log('Given HTML child element must be SELECT type.');
			return false;
		}		
	}
	else
	{
		$("#"+objChild).log('Either given element does not exists or not a valid HTML element ');
		
		return false;
	}
	
	var theForm	=	'';
	
	theForm	=	'#'+findClosestElementId(objParent,'form');
	
	var data	=	'typeCode='+$.trim($('option:selected', $(objParent)).attr('code'))+'&';
	data	+='typeId='+$.trim($(objParent).val())+'&';	//
	data	+='isAlphaNumeric='+isAlphaNumeric;
	var url	=	$(theForm).attr('action');

	if (url.indexOf("?") >= 0)
	{
		url	=	url.substring(0,url.indexOf("?"));
	}
	
	url+='?getSubAlphanumericSortInfo';
	var lookUpList=__doAjaxRequest(url,'post',data,false,'json');
	
	prepareChildrens(lookUpList, objChild);
	
	for (var i = cleareStartValue; i <= count; i++) { 
		
		element = parentId.replace(parentValue,i);

		var firstEl	=	($('#'+element+' option:first-child'));
		
		var lastEl	=	($('#'+element+' option:last-child'));
		
		$('#'+element).html(firstEl);
		
		if(lastEl.text()==getLocalMessage("common.all"))
		{
			$('#'+element).append(lastEl);
		}
	}
}

function showApplicationIdGenResultBox(messageText, redirectUrl, trackid){
	
	var message='';
	var cls = getLocalMessage('eip.page.process');
	if(redirectUrl=="" || redirectUrl==null){
	
		message	+='<p class="arial">'+ messageText+'</p>';
		message	+='<div class=\'class="btn_fld clear padding_10"\'>'+	
		
		'</p>';
		
	}
	else{
    
	 message	+='<p class="arial">'+ messageText+'</p>';
	 message	+='<div class=\'text-center padding-top-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-success \'    '+ 
	' onclick="closeboxAfterCheckForAppleSarkar(\''+errMsgDiv+'\',\''+redirectUrl+'\',\''+trackid+'\')"/>'+	
	'</div>';
	}
	
	
	
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

function closeboxAfterCheckForAppleSarkar(divName,redirectUrl,trackid){
	
	 if(trackid==null || trackid=='')
		{
			
		 if(typeof redirectUrl!== undefined && redirectUrl!=='undefined' && redirectUrl!=null && (redirectUrl!=''))
			{	
				try
				{
				 var lastIndex = redirectUrl.lastIndexOf("?");
				 
			     var lastchar = redirectUrl.substring(lastIndex	+	1);	
			     
			   
			   
				if(lastchar == 'PrintReport' || lastchar == 'PrintChallanOnline' || lastchar == 'PrintCounterReceipt')
					{
						 window.open(redirectUrl,'_blank');			
						 window.location.href='CitizenHome.html';
					}else if(lastchar == 'PrintULBCounterReceipt' ){
						 window.open(redirectUrl,'_blank');			
						 window.location.href='ChallanAtULBCounter.html';
						
					}
				else
					window.location.href=redirectUrl;
					$("#postMethodFormSuccess").prop('action', '');
					$("#postMethodFormSuccess").prop('action', redirectUrl);
					$("#postMethodFormSuccess").submit();
				}
				catch(e)
				{
					//window.location.href=redirectUrl;
					$("#postMethodFormSuccess").prop('action', '');
					$("#postMethodFormSuccess").prop('action', redirectUrl);
					$("#postMethodFormSuccess").submit();
				}
			}
		 
		 disposeModalBox();
			
		 return false;
		 
		}else{
			
			var result = __doAjaxRequest('rtsServicesAccess.html?sessionClosed', 'POST', '', false);		 
			window.close();
			
		}
	
}

function limitNumberOfLetters(e, obj, fieldsize){
	
	var keycode;

	if (window.event)
		keycode = window.event.keyCode;
	else if (e) {
		keycode = e.which;
	} else {
		return true;
	}
	
	if (keycode == 8 || keycode == 9 || keycode == 46 || keycode == 13) {
		// back space, tab, delete, enter 
		return true;
	}
	
	if (obj.value.length >= fieldsize) {
		return false;
	}
	
}
/*function showConfirm(form){

	var childDivName	=	'.child-popup-dialog';
	var message=' ';
	var msg1 = getLocalMessage("com.link.red1");
	var msg2 = getLocalMessage("com.link.red2");
	var btnMsg=getLocalMessage("bt.ok");
	
	    message	+=	'<div class="sucess">';
        message	+=	'<h4>'+msg1+'</h4>';
        message	+=  '<div class="buttons btn-fld text-center padding-bottom-10">';    		
		message	+=	'<input type="button" class="btn btn-success" value=' +btnMsg+ ' onclick=OpenNewTab("'+form+'"); />' ;
		message	+=	'</div>';
	    message	+=	'</div>';

	
	$(childDivName).html(message);
    showModalBox(childDivName);
}
*/

function openForm2(formUrl,actionParam)
{
	if (!actionParam) {
		
		actionParam = "add";
	}
	
	var divName	=	'.content-page';
	var ajaxResponse	=	doAjaxLoading(formUrl+'?'+actionParam,{},'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	
	
	
}

function openUpdateForm(formName,actionParam,rowId)
{
	
	var theForm	=	'#'+formName;
	
	var divName	=	formDivName;
	
	var url	=	$(theForm).attr('action');
	
	if (!actionParam || actionParam == '' || actionParam == undefined) 
	{
		//Do nothing if no action param is set.
	}
	else
	{
		url+='?'+actionParam;
	}
	
	//var rowId	=	$(theForm).attr('form-data');
	
	var requestData = 'rowId='+rowId;
	if(!rowId || rowId == '' || rowId == undefined){
		requestData = __serializeForm(theForm);
	}
	var ajaxResponse	=	doAjaxLoading(url, requestData, 'html', divName);
	
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	//convertToHindi();
	
	
	$(divName).show();
} 

function deleteElementForLink(formName,actionParam,rowId)
{
	var theForm	=	'#'+formName;
	var requestData = __serializeForm(theForm);
	
	
	
	var formAction	=	$(theForm).attr('action');
	
	if(typeof actionParam!=='undefined' && actionParam!='')
	{
		formAction+='?'+actionParam+"&rowId="+rowId;
	}
	
	var message='';
	
	var msg = getLocalMessage('eip.commons.del');
	var cls1 = getLocalMessage('eip.commons.yes');
	var cls2 = getLocalMessage('eip.commons.no');
	
	message='<p class="text-center padding-10 text-info text-bold">'+msg+'</p>';
	message	+='<p style=\'text-align:center;margin: 5px;\'>';				
	message	+='<span><input type=\'button\' value=\''+cls1+'\' class=\'button btn btn-blue-2 margin-right-5\'';
	message	+=' onclick="yesSectionOption(\''+formAction+'\',\''+requestData+'\')" /></span>';
	message	+='<span><input type=\'button\' value=\''+cls2+'\' class=\'btn btn-danger\'';
	message	+=' onclick="$.fancybox.close()" /></span>';
	message	+='</p>';
	$(errMsgDiv).removeClass('ok-msg').addClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);	
} 

function yesSectionOption(requestUrl,requestData)
{
	
	var ajaxResponse	=	__doAjaxRequest(requestUrl, 'post',requestData,false, 'json');		
	var okMsg = getLocalMessage('bt.ok');
	message='';
	message	+=	'<h4 class="text-center text-info padding-10">'+ajaxResponse.message+'</h4>';
	message	+='<div class="text-center padding-bottom-10"><input type=\'button\' value=\''+okMsg+'\' class=\'btn btn-blue-2\'';
	message	+='onclick="$.fancybox.close()" /></div>';
	$(errMsgDiv).html(message);	
	$(errMsgDiv).show();
	$.fancybox({
        type: 'inline',
        href: errMsgDiv,
        openEffect  : 'elastic', // 'elastic', 'fade' or 'none'
        closeBtn   : false,
        helpers: {
			overlay : {
				closeClick : false,
			}
		},
		 keys : {
			    close  : goBack()
			  }
    });
	return false;
}


 function _agencyAjaxRequest(obj, successMessage,divclassName,actionName,addParam,objChild){
	
	
	var errorList = [];
	//alert( $('#empGender').val());
	//alert( $('#title').val());
	if(($('#empGender').val() != '0' || $('#empGender').val()!= null ) && ($('#title').val() != '0' || $('#title').val()!= null ))  {
				
				if( $('#title :selected').attr('code') == 'MR.')
				
				{
					
					if($('#empGender :selected').attr('code').toLowerCase() == 'M' || $('#empGender :selected').attr('code').toLowerCase() == 'F')
					{
					 
					}
					else
					{
						errorList.push(getLocalMessage("citizen.login.title.right.error") +"<br/>" +getLocalMessage("citizen.login.gender.right.error"));
					}
				}
				else if($('#title :selected').attr('code')  == 'MRS.' || $('#title :selected').attr('code')  == 'MISS.')
				{
		             if($('#empGender :selected').attr('code')  == 'F' || $('#empGender :selected').attr('code')  == 'T')
		             {
					 }
		             else
		             {
						 errorList.push(getLocalMessage("citizen.login.title.right.error") +"<br/>" +getLocalMessage("citizen.login.gender.right.error"));
					 }
				}
				
			}
	var errMsg = '<div>';
    $.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
    errMsg += '</div>';
    $('.error-div').remove();
    $('.error1-div').html(errMsg);
    $('.error1-div').show();
	
		
		if (errorList.length == 0){
	         var response = _customAjaxRequest1(obj, successMessage,divclassName,actionName,addParam);
	         $(".content-page").html(response);
	       /*  $('.content-page').html(response);
	         return response;*/
		}else{

			var errMsg = '<div>';
            $.each(errorList, function(index) {
				errMsg += '<li>' + errorList[index] + '</li>';
			});
            errMsg += '</div>';
            $('.error-div').remove();
            $('.error1-div').html(errMsg);
            $('.error1-div').show();
		}   
	         
	   

}
 function OpenNewTab(url)
 {
   var win=window.open(url,'_blank');
   win.focus();
   closeFancyOnLinkClick('.child-popup-dialog');
  
 }
 
 function changeLanguage(url)
 {
	 if(false)
		{
			var array = document.URL.split('=');
			 if(array[0].indexOf("rowId")>-2)
				{
				 var array2 = document.URL.split('&rowId=');
				url2 =array2[0]+array2[1];

				url2   =url2.split('/');
				url2= url+'&url='+array2;
				window.location.href=url2;
				}
			 else
			{	 
			url2 =array[0]+'&rowId='+array[1];
			url2   =url2.split('/');
			url = url+'&url='+encodeURIComponent(array);
			window.location.href=url;

			}
		}
	 
	  else
		{
		  var array = document.URL.split('?');
		if(array.length == 2){
			var param = array[1];
			array = array[0].split("/");
			array= array[array.length -1];
			array = array +"?"+param;
		} else{
		array = array[0].split("/");
		array= array[array.length -1];
		
		}
		url = url+'&url='+encodeURIComponent(array);
		window.location.href=url;
		}
 }
 

 /**
  * Used method when you want to perform save  
  * 
  * @param obj
  * @param successMessage
  * @param successUrl
  * @param actionParam
  * @returns  server response can be validate form or success message etc 
  */
 function doAjaxOperation(obj, successMessage, successUrl, actionParam)
 {
  
     if (!actionParam) {
 		
 		actionParam = "save";
 	}
 	
 	return doAction(obj,successMessage, actionParam, true , successUrl);
 }

 function doAction(obj,successMessage, actionParam, sendFormData, successUrl)
 {
 	
 	var	formName =	findClosestElementId(obj, 'form');
 	var theForm	=	'#'+formName;
 	if($(theForm).valid() == true){
 	             var requestData = {};
 	
 				if (sendFormData) {
 					
 					requestData = __serializeForm(theForm);
 				}
 			
 				var url	=	$(theForm).attr('action')+'?' + actionParam;
 				
 				
 				var returnData=__doAjaxRequestForSave(url, 'post',requestData, false,'',obj);
 				if ($.isPlainObject(returnData))
 				{
 					var message = returnData.command.message;
 					
 					var hasError = returnData.command.hasValidationError;
 					
 					if (!message) {
 						message = successMessage;
 					}
 					
 					if(message && !hasError)
 						{
 						   	if(returnData.command.hiddenOtherVal == 'SERVERERROR')
 						   		
 						   		showSaveResultBox(returnData, message, 'ServerError.html');
 						   	
 						   	else
 						   		
 						   		showSaveResultBox(returnData, message, successUrl);
 						}
 					else if(hasError)
 					{
 						$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');	
 					}
 					else
 						return returnData;
 					
 				}
 				else if (typeof(returnData) === "string")
 				{
 					$(formDivName).html(returnData);	
 					prepareTags();
 				}
 				else 
 				{
 					alert("Invalid datatype received : " + returnData);
 				}
 				
 				return false;
 				
 	 }else{
 		 $('#accordion_single_collapse .panel-collapse').not(".in").collapse('toggle');
 		 return false;
 	 }	
 }


 /**
  * Used when you do not have accordian and perform validation
  * 
  * @param obj
  * @param url
  * @param reqType
  * @param data
  * @param async
  * @param dataType
  * @returns return server response
  */
 function __doAjaxRequestValidation(obj,url, reqType, data, async, dataType) {
 	 
 	var	formName =	findClosestElementId(obj, 'form');
 	var theForm	=	'#'+formName;
 	if($(theForm).valid() == true){
 		
 		    $.ajax({
 				url : url,
 				data : data,
 				type : reqType,
 				async : async,
 				dataType : dataType,
 				//headers   : {"SecurityToken": token},
 				success : function(response) {
 					result = response;
 				},
 				error : function(xhr, ajaxOptions, thrownError) {
 					var errorList = [];
 					errorList.push(getLocalMessage("admin.login.internal.server.error"));
 					showError(errorList);
 				}
 			});
 		
 			return result;
 	 }else{
 		 return false;
 	 }
 }

 /**
  * Used when you do have accordian and want to perform validation
  * 
  * example checklist , charges etc
  * 
  * @param obj
  * @param url
  * @param reqType
  * @param data
  * @param async
  * @param dataType
  * @returns server response
  */
 function __doAjaxRequestValidationAccor(obj,url, reqType, data, async, dataType) {
 	 
 	var	formName =	findClosestElementId(obj, 'form');
 	var theForm	=	'#'+formName;
 	if($(theForm).valid() == true){
 		
 		    $.ajax({
 				url : url,
 				data : data,
 				type : reqType,
 				async : async,
 				dataType : dataType,
 				//headers   : {"SecurityToken": token},
 				success : function(response) {
 					result = response;
 				},
 				error : function(xhr, ajaxOptions, thrownError) {
 					var errorList = [];
 					errorList.push(getLocalMessage("admin.login.internal.server.error"));
 					showError(errorList);
 				}
 			});
 		
 			return result;
 	 }else{
 		 $('#accordion_single_collapse .panel-collapse').not(".in").collapse('toggle');
 		 return false;
 	 }
 }

/**
 * Used method when you want to perform save  
 * 
 * @param obj
 * @param successMessage
 * @param successUrl
 * @param actionParam
 * @returns  server response can be validate form or success message etc 
 * 
 * This method belongs when we are following exist FrameWork
 * call this method from your js page it will be like
 * 
 * doAjaxOperation(element,"Your application for estate booking saved successfully!",'EstateBooking.html?PrintReport', 'saveform');
 */
function doAjaxOperation(obj, successMessage, successUrl, actionParam)
{
    if (!actionParam) {		
		actionParam = "save";
	}
	
	return doAction(obj,successMessage, actionParam, true , successUrl);
}

function doAction(obj,successMessage, actionParam, sendFormData, successUrl)
{
	
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	if($(theForm).valid() == true){
	             var requestData = {};
	
				if (sendFormData) {
					
					requestData = __serializeForm(theForm);
				}
			
				var url	=	$(theForm).attr('action')+'?' + actionParam;
				
				
				var returnData=__doAjaxRequestForSave(url, 'post',requestData, false,'',obj);
				if ($.isPlainObject(returnData))
				{
					var message = returnData.command.message;
					
					var hasError = returnData.command.hasValidationError;
					
					if (!message) {
						message = successMessage;
					}
					
					if(message && !hasError)
						{
						   	if(returnData.command.hiddenOtherVal == 'SERVERERROR')
						   		
						   		showSaveResultBox(returnData, message, 'ServerError.html');
						   	
						   	else
						   		
						   		showSaveResultBox(returnData, message, successUrl);
						}
					else if(hasError)
					{
						$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');	
					}
					else
						return returnData;
					
				}
				else if (typeof(returnData) === "string")
				{
					$(formDivName).html(returnData);	
					prepareTags();
				}
				else 
				{
					alert("Invalid datatype received : " + returnData);
				}
				
				return false;
				
	 }else{
		 $('#accordion_single_collapse .panel-collapse').not(".in").collapse('toggle');
		 return false;
	 }	
}


/**
 * Used when you do not have accordian and perform validation
 * 
 * @param obj
 * @param url
 * @param reqType
 * @param data
 * @param async
 * @param dataType
 * @returns return server response
 */
function __doAjaxRequestValidation(obj,url, reqType, data, async, dataType) {
	 
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	if($(theForm).valid() == true){
		
		    $.ajax({
				url : url,
				data : data,
				type : reqType,
				async : async,
				dataType : dataType,
				//headers   : {"SecurityToken": token},
				success : function(response) {
					result = response;
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});
		
			return result;
	 }else{
		 return false;
	 }
}

/**
 * Used when you do have accordian and want to perform validation
 * 
 * example checklist , charges etc
 * 
 * @param obj
 * @param url
 * @param reqType
 * @param data
 * @param async
 * @param dataType
 * @returns server response
 * 
 * example 
 * 
 * __doAjaxRequestValidationAccor(element,URL, 'POST', requestData, false,'html');
 * 
 * It will return either false(validate fail) either data(success validation) based on your logic you can perform 
 * 
 */
function __doAjaxRequestValidationAccor(obj,url, reqType, data, async, dataType) {
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	if($(theForm).valid() == true){
		
		    $.ajax({
				url : url,
				data : data,
				type : reqType,
				async : async,
				dataType : dataType,
				//headers   : {"SecurityToken": token},
				success : function(response) {
					result = response;
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});
		
			return result;
	 }else{
		 $('#accordion_single_collapse .panel-collapse').not(".in").collapse('toggle');
		 return false;
	 }
}

/**
 * Used when you do have param with url and want to perform validation
 * 
 * @param element/obj
 * @param Success msg
 * @param requestData
 * @param direct url
 * @returns return server response true/false
 * 
 * example : For you have given url in your action attribute in form  Vendormaster.html?create 
 */
function doActionWithParam(obj,successMessage, sendFormData, successUrl)
{
	
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	if($(theForm).valid() == true){
	             var requestData = {};
	
				if (sendFormData) {
					
					requestData = __serializeForm(theForm);
				}
			
				var url	=	$(theForm).attr('action');
						
				var returnData=__doAjaxRequestForSave(url, 'post',requestData, false,'',obj);
				if ($.isPlainObject(returnData))
				{
					
					var hasError = returnData.command.hasValidationError;
					
					if( !hasError)
						{
						   	if(returnData.command.hiddenOtherVal == 'SERVERERROR')
						   		{
						   		showPopUp(returnData, successMessage, 'ServerError.html');
						   		}
						   	else
						   		{
						   		showPopUp(returnData, successMessage, successUrl);
						   		}
						}
					else if(hasError)
					{
						$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');	
					}
					else
						return returnData;
					
				}
				else if (typeof(returnData) === "string")
				{
					$(formDivName).html(returnData);	
					prepareTags();
				}
				else 
				{
					alert("Invalid datatype received : " + returnData);
				}
				
				return false;
				
	 }else{
		 $('#accordion_single_collapse .panel-collapse').not(".in").collapse('toggle');
		 return false;
	 }	
	
	/**
	 * Used when you do have doActionWithParam and want to perform showPopUp Button
	 * 
	 * @param returnData
	 * @param Success msg
	 * @param redirectUrl
	 * @returns return response true/false
	 */
	function showPopUp(returnData, successMessage, redirectUrl) {

		var messageText = successMessage;
		
		var message='';
		var cls = getLocalMessage('eip.page.process');
		
		 message	+='<p class="text-blue-2 text-center padding-15">'+ messageText+'</p>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-success\'    '+ 
		' onclick="closebox(\''+errMsgDiv+'\',\''+redirectUrl+'\')"/>'+	
		'</div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message); 
		$(errMsgDiv).show();
		$('#btnNo').focus();
	    showModalBoxWithoutClose(errMsgDiv);
		return false;
	}
}


function __doAjaxRequest(url, reqType, data, async, dataType) {
	 
		
    //var result = '';
	//var token='';
	 
	/* $.ajax({
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
	 var token=null;
	    var header=null;
	 if (reqType && reqType.toLowerCase() === "post") {
		 token = $("meta[name='_csrf']").attr("content");
		 header = $("meta[name='_csrf_header']").attr("content");
   /* $("body").bind("ajaxSend", function(elm, xhr, s){
    	   if (s.type == "POST") {
    		   xhr.setRequestHeader(header, token);
    	   }
    });*/
    /*$(document).ajaxSend(function(e, xhr, options) {
    	//if(options.method == 'POST'){
    		xhr.setRequestHeader(header, token);
    	//}
    });*/
    	//headerForXHR = {header: token};
	 }
	 $.ajax({
		url : url,
		data : data,
		type : reqType,
		async : async,
		dataType : dataType,
		beforeSend : function(xhr){if(header && header != null){xhr.setRequestHeader(header, token);}},
		 //headers   : headerForXHR,
		//headers   : {"SecurityToken": token},
		success : function(response) {
			result = response;
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});

	return result;
}				



/**
 * Used when you do have getAmountFormatInDynamic(obj,elementId) Case and want to perform getAmountFormat any Amount Field.
 * 
 * @param obj
 * @param elementId
 * @returns return response getAmountFormat Amount Data.
 */
function getAmountFormatInDynamic(obj,elementId){

	var id=  $(obj).attr('id');
	var arr = id.split(elementId);
	var indx = arr[1];

	var value = $("#"+elementId+indx).val();

	if (value != undefined && !isNaN(value) && value != null && value != '') {
		
	var actualAmt = value.toString().split(".")[0];
	var decimalAmt = value.toString().split(".")[1];
	
	var decimalPart =".00";
	if(decimalAmt == null || decimalAmt == undefined){
		$("#"+elementId+indx).val(actualAmt+decimalPart);
	}else{
		if(decimalAmt.length <= 0){
			decimalAmt+="00";
			$("#"+elementId+indx).val(actualAmt+(".")+decimalAmt);
		}
		else if(decimalAmt.length <= 1){
			decimalAmt+="0";
			$("#"+elementId+indx).val(actualAmt+(".")+decimalAmt);
		}else{
			if(decimalAmt.length <= 2){
			$("#"+elementId+indx).val(actualAmt+(".")+decimalAmt);
			} 
		  }	
	   }
    }

}


function closeOutErrBox(){
	$('.error-div').hide();
}
 
$(document).ready(function(){
	$(".dataTableNormal").dataTable({
		//your normal options
		"language": { "search": "" }, 
		"pagingType": "full_numbers",
		"scrollX": true,
		"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
		"aaSorting": []
		});

	
	$('.dataTableClass').DataTable( {       
		initComplete: function () {
            this.api().columns().every( function () {
                var column = this;
                var select = $('<select class="form-control"><option value="" selected>'+getLocalMessage('all')+'</option></select>')
                    .appendTo( $(column.header()))
                    .on( 'change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );
 
                        column
                            .search( val ? val : '', true, false )
                            .draw();
                    } );
 
                column.data().unique().sort().each( function ( d, j ) {                	
                	if(d.indexOf("<")==0){d=$(d).last().text().trim()}
                    select.append( '<option value="'+d+'">'+d+'</option>' )
                } );
            } );
        },
	
	"language": { "search": "" }, 
	"pagingType": "full_numbers",
	"scrollX": true,
	 "autoWidth": false,
	"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
	 "aaSorting": []

	
    } );
	
	 
	
	
});

$(document).ready(function(){
	
	$(".content-page .dataTableClass font").contents().unwrap();

	/*$("html, body").animate({ scrollTop: 0 }, 500);*/
	
	
	$('a').each(function(){
	    if($(this).attr('target')){$(this).addClass('external').attr("target","_blank");} 
	         else {$(this).addClass('internal').removeAttr("target");}         
	});
	$('a[href$="pdf"]').removeClass('external').addClass('internal');
	$('a[data-target="internal"],a[href*="/MainetService"],a[href*="SectionInformation.html?editForm&rowId"],a[href*="Content.html?links&page"],a[href*="?ShowHelpDoc"]').removeClass('external').addClass('internal').removeAttr("target");
	$('.navigation .columns a').removeClass('external').addClass('internal').removeAttr("target");
	var elems = document.getElementsByClassName('external');
    var confirmIt = function (e) {
    	 if (!confirm('You will be redirect to external website. We cannot be held responsible for the content of external website. Please click OK to continue.')) e.preventDefault();
    };
    for (var i = 0, l = elems.length; i < l; i++) {
    	elems[i].addEventListener('click', confirmIt, false);
    }
	
    $('#mobile li a[href$="Infrastructure.html"]').css('display','none');
	
	});

$(function(){
	var videos =$('video');
	for(var i=0; i<videos.length; i++){
	   videos[i].addEventListener('play', function()
			   {pauseAll(this)}, true);
	}

	function pauseAll(elem){
		
		for(var i=0; i<videos.length; i++){
			//Is this the one we want to play?
			if(videos[i] == elem) continue;
			//Have we already played it && is it already paused?
			if(videos[i].played.length > 0 && !videos[i].paused){
			// Then pause it now
			  videos[i].pause();
			}
		}
	  }
	});

function otherTask(obj,url)
{
	var fileId = obj.substring(obj.lastIndexOf('_') + 1);
	var URL = url+'?getUploadedImage';
	var data = {"fileIndex" : fileId};
	var 	returnData=	__doAjaxRequest(URL, 'post', data , false,'json');
	$('#uploadPreview ul').empty();
	$.each(returnData, function(index, path) {  
	var imgPath= path.substr(0, path.lastIndexOf('_')); 
	var fileName=imgPath.substring(imgPath.lastIndexOf('/') + 1);
	imgPath=imgPath.replace(" ", "%20");
	/*var fileSize=path.substring(path.lastIndexOf('_') + 1);
	var extn=fileName.substring(fileName.lastIndexOf('.') + 1);
	$('#'+obj+' li').remove();
	
	var imgExtnList = ["jpeg", "jpg", "png", "gif", "bmp"];
	var videoExtnList = ["mp4","avi","3gp","mpg","swf","flv"];
	if(imgExtnList.indexOf(extn) > -1 )
		{
		$('#'+obj+' ul').append('<li> <div class="card border-light"> <img src='+imgPath+' class="img-thumb"> <div class="card-header1"><i class="fa fa-picture-o red-thumb" aria-hidden="true"></i><div class="file-name"> <span>'+fileName+'</span><p>'+fileSize+'</p></div><a href="#" class="close1" onclick="doFileDeletephoto(this);" id='+index+'_file_'+fileId+' title="Close"><i class="fa fa-times" aria-hidden="true"></a></i></div></div></li>');
		}
	else if(videoExtnList.indexOf(extn) > -1 )
		{
		$('#'+obj+' ul').append('<li> <div class="card border-light"><div class="card-header1"><i class="fa fa-file-video-o red-thumb" aria-hidden="true"></i><div class="file-name">  <span>'+fileName+'</span><p>'+fileSize+'</p></div><a href="#" class="close1" onclick="doFileDelete(this);" id="'+index+'_file_'+fileId+'" title="Close"><i class="fa fa-times" aria-hidden="true"></a></i></div></div></li>');
		}
	else
		{
		$('#'+obj+' ul').append('<li> <div class="card border-light"><div class="card-header1"><div class="thumbnail-'+extn+' padding-right-20"></div><div class="file-name"> <span>'+fileName+'</span><p>'+fileSize+'</p></div><a href="#" class="close1" onclick="doFileDelete(this);" id="'+index+'_file_'+fileId+'" title="Close"><i class="fa fa-times" aria-hidden="true"></a></i></div>'+'<a href="./'+imgPath+'" title="Download" download><i class="fa fa-download" aria-hidden="true"></i></a>' + '</div></li>');
		$('#'+obj+' ul').append('<li> <div class="card border-light"><div class="card-header1"><div class="thumbnail-'+extn+' padding-right-20"></div><div class="file-name"> <span>'+fileName+'</span><p>'+fileSize+'</p></div><a href="#" class="close1" onclick="doFileDelete(this);" id="'+index+'_file_'+fileId+'" title="Close"><i class="fa fa-times" aria-hidden="true"></a></i></div></div></li>');
		$('#'+obj+' ul').append('<li id="'+index+'_file_'+fileId+'_t"><img src="'+path+'" width="95" height="110"><a  href="#" onclick="doFileDeletephoto(this);" id="'+index+'_file_'+fileId+'" title="Close"><i class="fa fa-trash"></i></a></li>');	
		}*/
});
}


function doFileDeletephoto(obj)
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
	    var t_id = $(obj).attr('id');
	    var data	=	'fileId='+t_id+'&'+data1;	
		
	 	var jsonResponse	=	 __doAjaxRequest(url,'post',data,false,'json');
	 	
	 	$('#'+t_id+'_t').remove();
	
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
				if(index != 0)
					msg+="<img src='css/images/close.png' alt='Remove' width='17' title='Remove' id='"+value+"' onclick='doFileDeletephoto(this)' ></li>";
		 });
	  }
		 
	});
	 
	 if(jsonResponse.status)		
			$("#"+jsonResponse.hiddenOtherVal).html(msg);
		else
			showErrormsgboxTitle(msg);	
	 
	 window.location.reload(false);
	}
	

}



function getOperationalWardZone(orgid,deptId,locId){
	
	var URL=getLocalMessage('operationalWard');
	if(URL===null || URL==='' || URL===undefined){
		URL = 'MainetService/services/rest/common/locationservice/operationalWard/';
	}
	URL+=+orgid+'/'+deptId+'/'+locId;
	var returnData = __doAjaxRequest(URL.toString(), 'GET', {}, false,'json');
	 var formDiv="";
	$(returnData).each(function(i){
		var form="";
		if(i===0||i===2 || i===4 ){
			form =form+ "<div id='operwardZoneDiv' class='form-group'>";	
		}
		 form = form + "<label class='col-sm-2 control-label'>"+returnData[i].wardZoneLabel+"</label>";
		 form = form + "<div class='col-sm-4'>";
		  form = form + "<input type='text' value='"+returnData[i].wardZoneDesc+"'' readonly='readonly' class='form-control' > </div>";
		  if(i===1||i===3 || i===5 ){
				form =form+ "</div>";	
			}
		  	formDiv=formDiv+form;
			});
	return formDiv;
}
function getRevenueWardZone(orgid,deptId,locId){
	var URL=getLocalMessage('revenueWard');
	if(URL===null || URL==='' || URL===undefined){
	URL = 'MainetService/services/rest/common/locationservice/revenueWard/';
	}
	URL+=+orgid+'/'+deptId+'/'+locId;
	var returnData = __doAjaxRequest(URL, 'GET', {}, false,'json');
	 var formDiv="";
	$(returnData).each(function(i){
		var form="";
		if(i===0||i===2 || i===4 ){
			form =form+ "<div id='revewardZoneDiv' class='form-group'>";	
		}
		 form = form + "<label class='col-sm-2 control-label'>"+returnData[i].wardZoneLabel+"</label>";
		 form = form + "<div class='col-sm-4'>";
		  form = form + "<input type='text' value='"+returnData[i].wardZoneDesc+"'' readonly='readonly' class='form-control' > </div>";
		  if(i===1||i===3 || i===5 ){
				form =form+ "</div>";	
			}
		  	formDiv=formDiv+form;
		});
	return formDiv;
}

function getElectoralWardZone(orgid,deptId,locId){
	var URL=getLocalMessage('electoralWard');
	if(URL===null || URL==='' || URL===undefined){
	var URL = 'MainetService/services/rest/common/locationservice/electoralWard/';
	}
	URL+=+orgid+'/'+deptId+'/'+locId;
	var returnData = __doAjaxRequest(URL, 'GET', {}, false,'json');
	 var formDiv="";
	$(returnData).each(function(i){
		var form="";
		if(i===0||i===2 || i===4 ){
			form =form+ "<div id='electwardZoneDiv' class='form-group'>";	
		}
		 form = form + "<label class='col-sm-2 control-label'>"+returnData[i].wardZoneLabel+"</label>";
		 form = form + "<div class='col-sm-4'>";
		  form = form + "<input type='text' value='"+returnData[i].wardZoneDesc+"'' readonly='readonly' class='form-control' > </div>";
		  if(i===1||i===3 || i===5 ){
				form =form+ "</div>";	
			}
		  	formDiv=formDiv+form;
		});
	return formDiv;
}

//Add specific table-Row
function addTableRow(tableId, isDataTable) {
	
	var id = "#" + tableId;
	// remove datatable specific properties
	if ((isDataTable == undefined || isDataTable) && $.fn.DataTable.isDataTable('' + id + '')) {
		$('' + id + '').DataTable().destroy();
	}
	$(".datepicker").datepicker("destroy");
	var content = $('' + id + ' tr').last().clone();
	$('' + id + ' tr').last().after(content);
	
	content.find("input:text").val('');
	content.find("input:hidden").val('');
	content.find("textarea").val('');
	content.find("select").val('');
	content.find("input:checkbox").removeAttr('checked');
	reOrderTableIdSequence(id);
	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
	if(isDataTable == undefined || isDataTable) {
		// adding datatable specific properties
		dataTableProperty(id);
	}
}

// remove specific table-Row
function deleteTableRow(tableId, rowObj, deletedId, isDataTable) {
	
	var id = "#" + tableId;
	var error = [];
	// remove datatable specific properties
	if ((isDataTable == undefined || isDataTable) && $.fn.DataTable.isDataTable(id)) {
		$(id).DataTable().destroy();
		$(".datepicker").datepicker("destroy");
	}
	var rowCount = $("" + id + " tbody tr").length;

	// if rowCount is 1, it means only one row present
	if (rowCount != 1) {
		var deletedSorId = $(rowObj).closest('tr').find(
				'input[type=hidden]:first').attr('value');
		$(rowObj).closest('tr').remove();

		if (deletedSorId != '') {
			var prevValue = $('#' + deletedId).val();
			if (prevValue != '')
				$('#' + deletedId).val(prevValue + "," + deletedSorId);
			else
				$('#' + deletedId).val(deletedSorId);
		}
		reOrderTableIdSequence(id);
		$('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true
		});
		if(isDataTable == undefined || isDataTable) {
			// adding datatable specific properties
			dataTableProperty(id);
		}
	}
}

//reordering all the table Elements
function reOrderTableIdSequence(tableId) {
	
	var noOfTds;
	var type = [];
	var name = [];
	var id = [];
	var text;
	var hidden;
	var select;
	var checkbox;
	var textarea;

	// Extracting all the inputs present in first Row
	var allInputs = $("" + tableId + " tbody tr:first").find(":input");
	noOfTds = allInputs.length;
	for (var td = 0, l = noOfTds; td < l; td++) {
		type[td] = allInputs[td].type;
		name[td] = allInputs[td].name;
		id[td] = allInputs[td].id;
	}

	// reorder the Table-Elements
	$("" + tableId + " tbody tr")
			.each(
					function(i) {
						text = 0;
						hidden = 0;
						select = 0;
						checkbox = 0;
						textarea = 0;

						for (var flowTd = 0; flowTd <= noOfTds; flowTd++) {
							if (type[flowTd] == 'text') {

								$(this)
										.find("input:text:eq(" + text + ")")
										.attr(
												"id",
												id[flowTd].replace(
														/[^a-zA-Z]/g, i))
										.attr(
												"name",
												name[flowTd]
														.replace(
																/[^a-zA-Z\[\]\.\$]/g,
																i));

								if (text == 0) {
									var num = "#"
											+ id[flowTd].replace(/[^a-zA-Z]/g,
													'');
								
								}
								text++;
							}
							if (type[flowTd] == 'hidden') {
								$(this).find("input:hidden:eq(" + hidden + ")")
										.attr(
												"id",
												id[flowTd].replace(
														/[^a-zA-Z]/g, i)).attr(
												"name",
												name[flowTd].replace(
														/[^a-zA-Z\[\]\.\_\$]/g,
														i));
								hidden++;

							}

							if (type[flowTd] == 'select-one') {
								$(this).find("select:eq(" + select + ")").attr(
										"id",
										id[flowTd].replace(/[^a-zA-Z]/g, i))
										.attr(
												"name",
												name[flowTd].replace(
														/[^a-zA-Z\[\]\.\_\$]/g,
														i));
								select++;

							}

							if (type[flowTd] == 'textarea') {
								$(this).find("textarea:eq(" + textarea + ")")
										.attr(
												"id",
												id[flowTd].replace(
														/[^a-zA-Z]/g, i)).attr(
												"name",
												name[flowTd].replace(
														/[^a-zA-Z\[\]\.\_\$]/g,
														i));
								textarea++;

							}

							if (type[flowTd] == 'checkbox') {
								$(this).find(
										"input:checkbox:eq(" + checkbox + ")")
										.attr(
												"id",
												id[flowTd].replace(
														/[^a-zA-Z]/g, i)).attr(
												"name",
												name[flowTd].replace(
														/[^a-zA-Z\[\]\.\_\$]/g,
														i));
								checkbox++;

							}
						}
					});
}


//Redirect the Pagination to Currently Row added Page
function dataTableProperty(id){
	
	$('' + id +' ').dataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [10,20,30,-1], [10,20,30,"All"] ],
	    "iDisplayLength" : 10, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "scrollCollapse": true,
	    "bSort" : false
	   }).fnPageChange( 'last' );	 	 
}

function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
	errMsg += '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	return false;
}
$(function(){
$('.dataTables_filter input[type="search"]').attr('placeholder', getLocalMessage("eip.search"));
});



$(function(){
	document.onkeyup = function(e) {
		/*var hv = $('#gotohome').val();*/
		if ((e.altKey && e.which == 72) || (e.altKey && e.which == 104)) {				//alt+h/H (home page)
		    window.location.href = "CitizenHome.html";
		  }
		  // else if (e.which == 13) {														//Enter (Submit Button)
			    // $('input[type = submit]').click();
			  // }
		  else if ((e.altKey && e.which == 76) || (e.altKey && e.which == 108)) {		//alt+L/l (Logout)
			  window.location.href = "LogOut.html";
			  }
		};
	});

var menu_height=$('.nav-scroll li').length;
if(menu_height >= 30){
	$('.nav-scroll').slimScroll({
	    color: '#313131',
	    size: '10px',
	   // minHeight:'auto',
	   height:'400px',
	    alwaysVisible: true,
	    touchScrollStep: 10
	});
	
}

var menu1_height=$('#nav-scrool-1 li').length;
if(menu1_height >= 30){
$('#nav-scrool-1').slimScroll({
    color: '#313131',
    size: '10px',
    height: '400px',
    alwaysVisible: true,
    touchScrollStep: 10
});
}

var menu2_height=$('.nav-scroll1 li').length;
if(menu2_height >= 30){
	$('.nav-scroll1').slimScroll({
	    color: '#313131',
	    size: '10px',
	    height: '400px',
	    alwaysVisible: true
	});
}

function hasWhiteSpace(s) {
	  return /\s/g.test(s);
}
	   function commonlanguageTranslate(no_spl_char,elementId,event,callback)
	   {
	   	var lang = getLocalMessage('admin.translator.convert.language.id'); 
	   	re = /[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi;
	   	var finalvalue=no_spl_char.split(re);
	   	if(event.keyCode != 9)
	   	for (var i = 0; i < finalvalue.length; i++) {
	   		if(finalvalue[i]!='')
	   		{
	   	var urlStr = "https://inputtools.google.com/request?text=%%"+finalvalue[i]+"%%&itc="+lang+"&num=1&cp=0&cs=1&ie=utf-8&oe=utf-8";

	   	var request = $.ajax({
	   	  url: encodeURI(urlStr),
	   	  method: "POST",
	   	  cache: false,
	   	  dataType: "json",
	   	  
	   	});
	   	 request.done(function( msg ) {

	   	var val = msg[1];
	   	$("#brChildnameMar").val(val.toString());
	   	var arrval = val.toString();
	   	var enval = arrval.split("%%")[1];

	   	var tarval = arrval.split("%%,%%")[1];
	   	var finalval = tarval.lastIndexOf("%%");

	   	var textstr = tarval.substr(0,finalval);

	   	if (no_spl_char.indexOf(enval) >= 0)
	   		 {
	   	no_spl_char=no_spl_char.replace(enval,textstr);
	   		 }

	   	$("#"+elementId).val(no_spl_char);
	   	if(callback!='')
	   	{
	   		callback();
	   	}
	   	});
	   	 
	   	request.fail(function( jqXHR, textStatus ) {
	   	});
	   	}
	   	}
	   	
	   }


function countCharacter(obj,maxLimit,countId) {
	let id=$(obj).attr('id')
	$('#'+id).keyup(function() {  
	 	var textLength = $('#'+id).val().length;  
	    var textRemaining = maxLimit - textLength;  
	    $('#'+countId).html(textRemaining);  
    }); 
}


function closeConfirmBoxForm() {
	var childDivName = '.child-popup-dialog';
	$(childDivName).hide();
	$(childDivName).empty();
	disposeModalBox();
	$.fancybox.close();
}

function otherTaskWithImageThumbNail(url) {
	
	var URL = url+'?getdataOfUploadedImageWithThumbNail';
	var data = {};
	
	var returnData = __doAjaxRequest(URL, 'post', data, false, 'json');
	$('#uploadPreview ul').empty();
	$('#file_list_0').hide();
	
	$('#isEmpPhotoDeleted').val("N");
	if (returnData != '' && returnData != null && returnData != 'null' && returnData !='Internal Server Error.') {
		$('#uploadPreview ul')
				.append(
						'<li id="0_file_0_t"><img src="'+returnData+'" width="150" height="145" class="img-thumbnail"><a  href="#" onclick="doFileDeletephoto(this);" id="0_file_0" title="Delete"><i class="fa fa-trash fa-lg text-danger btn-lg"></i></a></li>');
		$('#uploadPreview').show();
	}
	else{
		var modeVal=$('#mode').val();
		var imagePath=$('#hiddenEmpImagePath').val();
		if(modeVal=='Y' && imagePath != "" && imagePath != null){
			$('#uploadPreview ul')
			.append(
					'<li id="0_file_0_t"><img src="'+imagePath+'" width="150" height="145" class="img-thumbnail"><a  href="#" onclick="doFileDeletephoto(this);" id="0_file_0" title="Delete"><i class="fa fa-trash fa-lg text-danger btn-lg"></i></a></li>');
			$('#uploadPreview').show();
		}
	}
}



function showloader(showloader){
	
	if(showloader){$(".sloading").show();$("#sdiv").show();		
	}else{
	$(".sloading").hide();$("#sdiv").hide();}
}	