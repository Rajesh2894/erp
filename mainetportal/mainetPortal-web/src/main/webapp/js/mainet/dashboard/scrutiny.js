/**
 *  RAJENDRA BHUJBAL
 */

function saveScrutinyLabels(obj,successMessage,actionParam,successUrl)
{
	var	formName =	findClosestElementId(obj, 'form');
	
	var theForm	=	'#'+formName;

	var url	= $(theForm).attr('action')+'?' + 'getModuleActionMap';
	
	var returnData =__doAjaxRequest(url, 'post',{}, false);
	
	for (var key in returnData) {
        if (returnData.hasOwnProperty(key)) {
           console.log(key + ': ' + returnData[key]);
        }
    }
	
	return doFormAction(obj,successMessage, actionParam, true , successUrl);
}