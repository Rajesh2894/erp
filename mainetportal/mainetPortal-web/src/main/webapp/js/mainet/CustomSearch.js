var childDivName	=	'.child-popup-dialog';
function getEmplyeeDetails(idValue)
{
	
	var theForm	=	'#frmEmpMaster';
		
	var url		=	$(theForm).attr('action')+'?getEmployeeDetails';
	
	var data	=	'empId='+idValue;	
	
	var returnData	=	__doAjaxRequest(url, 'post', data , false,'json');	
	
	closeFancyOnLinkClick(childDivName);
	
	$("#employeeID").val(returnData[0]);
	$("#employeeName").val(returnData[1]);
	
}

function closeFormData(obj)
{
	_closeChildForm(childDivName);
}

function serachEmpDetails(obj)
{
	_customAjaxRequest1(obj,'',childDivName,'empSearch','');
}

function openChildPopEmpData(formUrl)
{
	var theForm	=	'#frmMaster';
	
	var requestData = __serializeForm(theForm);	
	
	var ajaxResponse	=	__doAjaxRequest(formUrl,'post',requestData,false);
	
	if(typeof(ajaxResponse)=="string")
			{	
				_showChildForm(childDivName, ajaxResponse);
			};
};
