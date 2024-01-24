/**
 * GENERATE JQGRID CONTROL
 * 
 *  <p>Pranit.Mhatre</p>
 * 
 *  <p>This java script function provides facility to create custom jQgrid.</p>
 * 
 *  <p>By the use of this it will automatically generate table tag which provides the facility 
 *  to render list of record data into single table. It provides the facility of 
 *  <code>add/edit/update/delete/search/view/refresh</code>.
 *  </p>  
 *  
 *  <p>By the use of this, we can customize all of these such features provide by grid.</p>
 */

var rowNumWidth	=	35;

var	gridOption	=	{};

var viewRedirect	=	true;

var editURL	=	'';
var deleteURL	=	'';

var allRowIds =[];
var rowCheckBoxFlag = 0;

function prepareGrid(gridOptions) {
	
//var token='';
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
	
	 var t = $("meta[name='_csrf']").attr("content"),
     i = $("meta[name='_csrf_header']").attr("content");
	 
	rowNumWidth	=	(gridOptions.showTotal)	?	55 : rowNumWidth;
	
	gridOption	=	gridOptions;
	
	viewRedirect	=	gridOptions.viewAjaxRequest;
	
	editURL	=	gridOptions.editurl;
	
	deleteURL	=	gridOptions.deleteURL;
	
	$("#" + gridOptions.gridId).jqGrid(
			{
				url : gridOptions.url,
				datatype : "json",
				gridReloadName : gridOptions.gridReloadName,
				mtype : gridOptions.mtype,
				width : gridOptions.width,
				height : gridOptions.height,
				autowidth : true,
				shrinkToFit : true,
				colNames : gridOptions.colHeader,
				colModel : gridOptions.colModel,
				multiselect : gridOptions.multiselect,
				paging : true,
				 loadBeforeSend: function(e) {
			            e.setRequestHeader(i, t)
			        },
				
				pager : $("#" + gridOptions.pagerId),
				editurl : gridOptions.editurl,
				cellEdit : false,
				viewrecords : true,
				loadonce : gridOptions.loadonce,
				sortname : gridOptions.sortCol,
				sortorder : "asc",				
				rowNum : 30,
				rowList : [ 5, 10, 15, 20, 25, 30 ],
				jsonReader : {
					repeatitems : false
				},
				gridComplete: function(){	
					/*if($(this).getGridParam('records')==0)
						$("#" + gridOptions.pagerId).hide();*/
					
					performOnLoadComplete($(this),gridOptions);
					
				},
				beforeSelectRow: function(rowId, e) {
					return $(e.target).is("input:checkbox");
				},
				onSelectRow: function(id,status){
					
					if(status){						
						if(allRowIds.indexOf(id) != -1){
							// found
						}else{							
							if(allRowIds.length == 0){
								allRowIds = jQuery.makeArray( id );
							}else{
								allRowIds.push(id);
							}							
						}
					}else{
						allRowIds = jQuery.grep(allRowIds, function(value) {
						  return value != id;
						});						
					}	
					
				}, 
				onSelectAll: function(aRowids,status) {
					$('.cbox').each(function(i){
						if(i == 0){
							if($(this).is(":checked")){
								allRowIds = aRowids;
								return false;
							}else{
								allRowIds = '';
								return false;
							}
						}
					});
					
				},
				/*loadError : function(jqXHR, textStatus, errorThrown) {
					alert('HTTP status code: ' + jqXHR.status + '\n'
							+ 'textStatus: ' + textStatus + '\n'
							+ 'errorThrown: ' + errorThrown);
					alert('HTTP message body (jqXHR.responseText): ' + '\n'
							+ jqXHR.responseText);
				},*/
				onPaging: function (pgButton) {
					//alert(pgButton);
				},
				caption : gridOptions.caption,
				rownumbers:  gridOptions.showrow,
				rownumWidth : rowNumWidth,
				footerrow: gridOptions.showTotal,
				postData:gridOptions.postData

			}).navGrid('#' + gridOptions.pagerId, {
				add : false,
				edit : false,
				del : false,
				search : gridOptions.search,
				refresh : false,
				view : false
			},{},{},{},{closeAfterSearch: true,
				closeAfterReset: true},{
				odata :['equal', 'not equal', 'less', 'less or equal','greater','greater or equal', 'begins with','does not begin with','is in','is not in','ends with','does not end with','contains','does not contain'],sopt: ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc','nu','nn'] ,
							});
	
	/***	[START]	***/	
	addTemplate = {
			align : "center",	
			editable : false,
			sortable : false,
			search : false,
			/**
			 * @param el - current cell element value
			 * @param obj - current cell object containing attributes like width, align, name, index etc.
			 * @param options - current row optional colModel attributes and values.  
			 */
			formatter : function(el,cellValue, options) {		
				
				var datastring	="<a href='javascript:void(0);'>";
					datastring	+="<img src='css/images/add.png' width='20px' alt='Add' title='Add' />";
					datastring	+="</a>";
				return datastring;
			}
	};
	
	$("#" + gridOptions.gridId).jqGrid("setLabel", "rn", getLocalMessage("grid.serialno"));
	
	/***	[END]	***/
}

/*** Functions defined for parent/child grid custome template ***/

/***	[START]	***/

/**
 * @param el - current cell element value
 * @param obj - current cell object containing attributes like width, align, name, index etc.
 * @param options - current row optional colModel attributes and values.  
 */
function childViewTemplate(el , obj, options)
{
	var gridId		=	$(this).attr('id');				
	var formName	=	gridId.substr(4,gridId.length);
	var formAction	=	formName+'.html?edit&rowId='+el;
	var formId		=	'frm'+formName+'E'+el;
	
	var datastring	="<form action='"+formAction+"' name='"+formId+"' id='"+formId+"' method=\"post\">";
		datastring	+="<a href='javascript:void(0);' onclick=\"_openChildFormUpdate('"+formId+"')\">";
		datastring	+="<img src='css/images/view.png' width='20px' alt='View Details' title='View Details' />";
		datastring	+="</a>";
		datastring	+="</form>";
	
	return datastring;
}


/**
 * @param el - current cell element value
 * @param obj - current cell object containing attributes like width, align, name, index etc.
 * @param options - current row optional colModel attributes and values.  
 */
function childActiveTemplate(el , obj, options)
{
	var datastring	=	"";
	
	if(options.activeStatus=='Y')
	{
		datastring	+="<img src='css/images/inactive.png' width='20px' alt='Inactive' title='Inactive' />";
	}
	else if(options.activeStatus=='N')
	{
		datastring	+="<img src='css/images/active.png' width='20px' alt='Active' title='Active' />";
	}				
	
	return datastring;
}

/**
 * @param el - current cell element value
 * @param obj - current cell object containing attributes like width, align, name, index etc.
 * @param options - current row optional colModel attributes and values.  
 */
function childEditTemplate(el , obj, options)
{
	var gridId		=	$(this).attr('id');				
	var formName	=	gridId.substr(4,gridId.length);
	var formAction	=	formName+'.html?edit&rowId='+el;
	var formId		=	'frm'+formName+'E'+el;
	
	var datastring	="<form action='"+formAction+"' name='"+formId+"' id='"+formId+"' method=\"post\">";
		datastring	+="<a href='javascript:void(0);' onclick=\"_openChildFormUpdate('"+formId+"')\">";
		datastring	+="<img src='css/images/edit.png' width='20px' alt='Edit Details' title='Edit Details' />";
		datastring	+="</a>";
		datastring	+="</form>";
	
	if(options.activeStatus==='Y')
			return "<img src='css/images/edit.png' width='20px' alt='Edit' title='Edit' />";
	else return datastring;
}

/**
 * @param el - current cell element value
 * @param obj - current cell object containing attributes like width, align, name, index etc.
 * @param options - current row optional colModel attributes and values.  
 */
function childDeleteTemplate(el , obj, options)
{
	var gridId		=	$(this).attr('id');				
	var formName	=	gridId.substr(4,gridId.length);
	var formAction	=	formName+'.html?delete&rowId='+el;
	var formId		=	'frm'+formName+'D'+el;
	
	var datastring	="<form action='"+formAction+"' name='"+formId+"' id='"+formId+"' method=\"post\">";	
		datastring	+="<a href='javascript:void(0);' onclick=\"_deleteChildRow('"+formId+"')\">";
		datastring	+="<img src='css/images/delete.png' width='20px' alt='Delete' title='Delete' />";
		datastring	+="</a>";
		datastring	+="</form>";
	
	if(options.activeStatus==='Y')
		{
			return "<img src='css/images/delete.png' width='20px' alt='Delete' title='Delete' />";
		}
	else	
		{	
			return datastring;					
		}
}

/**
 * @param el - current cell element value
 * @param obj - current cell object containing attributes like width, align, name, index etc.
 * @param options - current row optional colModel attributes and values.  
 */
function activeTemplate(el , obj, options)
{
	var datastring	=	"";
	
	if(el==='Y')
	{
		datastring	+="<img src='css/images/inactive.png' width='20px' alt='Inactive' title='Inactive' />";
	}
	else if(el==='N')
	{
		datastring	+="<img src='css/images/active.png' width='20px' alt='Active' title='Active' />";
	}
	
	return datastring;
}

/**
 * @param el - current cell element value
 * @param obj - current cell object containing attributes like width, align, name, index etc.
 * @param options - current row optional colModel attributes and values.  
 */
function editTemplate(el , obj, options)
{
	var gridId	=	$(this).attr('id');				
	var formName	=	gridId.substr(4,gridId.length);
	
	var url	=	editURL;
	
	if(url===undefined || url=='')
	{
		url	=	formName+".html";
	}
	
	var formAction	=	url+'?edit&rowId='+el;
	var formId	=	'frm'+formName+'E'+el;
		
	var eventHandler	=	"onclick=\"openUpdateForm('"+formId+"')\"";
	
	if(obj.colModel.showInDialog)
	{
		eventHandler	=	"onclick=\"openGridDialog('"+formId+"')\"";
	}
	
	var datastring	="<form action='"+formAction+"' name='"+formId+"' id='"+formId+"' method=\"post\">";
		datastring	+="<a href='javascript:void(0);'"+ eventHandler +">";
		datastring	+="<img src='css/images/edit.png' width='20px' alt='Edit Details' title='Edit Details' />";
		datastring	+="</a>";
		datastring	+="</form>";
	
		if(options.activeStatus==='Y')
			{
				return "<img src='css/images/edit.png' width='20px' alt='Edit' title='Edit' />";
			}
		else	
			{	
				return datastring;					
			}
}

/**
 * @param el - current cell element value
 * @param obj - current cell object containing attributes like width, align, name, index etc.
 * @param options - current row optional colModel attributes and values.  
 */
function viewTemplate(el , obj, options)
{
	var gridId	=	$(this).attr('id');				
	var formName	=	gridId.substr(4,gridId.length);

	var url	=	editURL;
	
	if(url===undefined || url=='')
	{
		url	=	formName+".html";
	}
	
	var formAction	=	url+'?edit';
	
	if(viewRedirect)
	{
		formAction+="&rowId="+el;
	}
	
	var formId	=	'frm'+formName+'E'+el;
	
	var datastring	="<form action='"+formAction+"' name='"+formId+"' id='"+formId+"' method=\"post\">";
	
	if(!viewRedirect)
	{	
		datastring	+="<a href='javascript:void(0);' onclick=\"jQGridformRedirect('"+formId+"')\">";
		datastring	+="<img src='css/images/view.png' width='20px' alt='View Details' title='View Details' />";
		datastring	+="</a>";
		datastring	+="<input type='hidden' name='rowId' id='rowId' value='"+el+"'/>";
	}
	else
	{
		datastring	+="<a href='javascript:void(0);' onclick=\"openUpdateForm('"+formId+"')\">";
		datastring	+="<img src='css/images/view.png' width='20px' alt='View Details' title='View Details' />";
		datastring	+="</a>";
	}
		
	datastring	+="</form>";
	
	return datastring;
}


function viewModeTemplate(el , obj, options)
{
	var gridId	=	$(this).attr('id');				
	var formName	=	gridId.substr(4,gridId.length);

	var url	=	'';
	
	if(url===undefined || url=='')
	{
		url	=	formName+".html";
	}
	
	var formAction	=	url+'?viewMode';
	
	if(viewRedirect)
	{
		formAction+="&rowId="+el;
	}
	
	var formId	=	'combfrm'+formName+'E'+el;
	
	var datastring	="<form action='"+formAction+"' name='"+formId+"' id='"+formId+"' method=\"post\">";
	
	if(!viewRedirect)
	{	
		datastring	+="<a href='javascript:void(0);' onclick=\"jQGridformRedirect('"+formId+"')\">";
		datastring	+="<img src='css/images/view.png' width='20px' alt='View Details' title='View Details' />";
		datastring	+="</a>";
		datastring	+="<input type='hidden' name='rowId' id='rowId' value='"+el+"'/>";
	}
	else
	{
		datastring	+="<a href='javascript:void(0);' onclick=\"openViewModeForm('"+formId+"')\">";
		datastring	+="<img src='css/images/view.png' width='20px' alt='View Details' title='View Details' />";
		datastring	+="</a>";
	}
		
	datastring	+="</form>";
	
	return datastring;
}

/**
 * @param el - current cell element value
 * @param obj - current cell object containing attributes like width, align, name, index etc.
 * @param options - current row optional colModel attributes and values.  
 */
function deleteTemplate(el , obj, options) 
{	
	var gridId	=	$(this).attr('id');				
	var formName	=	gridId.substr(4,gridId.length);
	var formId	=	'frm'+formName+'D'+el;
	
	var url	=	deleteURL;
	
	if(url=='')
	{
		url	=	formName+".html";
	}
	
	var datastring	="<form action='"+url+"?delete&rowId="+el+"' name='"+formId+"' id='"+formId+"' method=\"post\">";	
		datastring	+="<a href='javascript:void(0);' onclick=\"deleteRecord('"+formId+"')\">";
		datastring	+="<img src='css/images/delete.png' width='20px' alt='Delete' title='Delete' />";
		datastring	+="</a>";
		datastring	+="</form>";
		
		
	if(options.activeStatus==='Y')
		{
			return "<img src='css/images/delete.png' width='20px' alt='Delete' title='Delete' />";
		}
	else	
		{	
			return datastring;					
		}

		return datastring;					

}

/**
 * Common function for set particular column attributes like align,editable,sortable,search and width.
 * <p>In this function the new width of the column not updated on UI laevel, so need to add width property
 *  at the grid level.</p>
 * @param objColModel the current colModel object.
 * @param objType the type of function call
 * @returns updated colModel object with new assign attribute values.
 */
function setColModel(objColModel,objType)
{
	if((typeof(objColModel)!=='undefined') || (typeof(objType)!=='undefined'))
	{	
		objColModel.align = "center";	
		objColModel.editable = false;
		objColModel.sortable = false;
		objColModel.search = false;
		
		switch(objType)
		{
			case 'DELETE' :	objColModel.width = '50'; break;
			case 'EDIT' :	objColModel.width = '70'; break;
			case 'ACTIVE' :	objColModel.width = '40'; break;
			
			default : objColModel.width = 100; break;
		}
		
	}
	
	else
	{
		alert('Not valid col model object !');
		
		return null;
	}
}

/**
 * @param el - current cell element value
 * @param obj - current cell object containing attributes like width, align, name, index etc.
 * @param options - current row optional colModel attributes and values.  
 */
function dateTemplate(el , obj, options) 
{	
	if(el!==null)
	{	
		var date = new Date(el);
		return getDateFormat(date);
	}
	else
	{	
		return '';
	}
}
function dateTimeTemplate(el , obj, options) 
{	
	if(el!==null)
	{	
		var date = new Date(el);
		
		return date.toLocaleDateString() +" "+ date.toLocaleTimeString();
	}
	else
	{	
		return '';
	}
}

function timeTemplate(el , obj, options) 
{	
	if(el!==null)
	{	
		var date = new Date(el);
		var h = date.getHours();
		var m = date.getMinutes();
		var s = date.getSeconds();
		return h +':'+ m +':'+ s ;
	}
	else
	{	
		return '';
	}
}

/***	[END]	***/

/**
 * To perform some action(s) after successfully loading of jqgrid.
 */
function performOnLoadComplete(gridId,gridOptions)
{
	showColumnTotal(gridId,gridOptions);
}

/**
 * To show total of the column at the footer of the grid.
 * @param gridId the grid identifier.
 * @param gridOptions the option array provided by jqgrid.
 */
function showColumnTotal(gridId,gridOptions)
{
	if(gridOptions.showTotal)
	{
		var colNames=[];
		
		if((gridOptions.colNames)!==null &&	(gridOptions.colNames).length	>	0)
		{
			colNames	=	stringToArray(gridOptions.colNames,',');
		}
		
		var totalText = getLocalMessage("tp.jQGridTotal");
		gridId.jqGrid('footerData','set', {rn: totalText});
		
		var colnames = gridId.jqGrid('getGridParam','colModel');
		
		 for (var i = 0; i < colnames.length; i++)
		 {
			 for(var j=0;j<colNames.length;j++)
			 {
				 if(colnames[i]['name']==colNames[j])
				 {
					 var total = gridId.jqGrid('getCol',colnames[i]['name'],false,'sum');
					 total=total.toFixed(2);
					 var ob = [];
			         ob[colnames[i]['name']] = total;
			         gridId.jqGrid('footerData','set',ob);
					 break;
				 }
			 }
		 }
		
	}
}

/**
 * 
 * @param colName
 * @returns
 */
function getGridColSum(colName)
{
	var	 gridId	=	'#'+gridOption.gridId;
	
	if(!$(gridId).doesExist())
	{
		console.log('Grid doesnot exists.');
		
		return false;
	}
	
	var colnames = $(gridId).jqGrid('getGridParam','colModel');
	
	for(var j=0;j<colnames.length;j++)
	 {
		if(colnames[j]['name']==colName)
		{
			return $(gridId).jqGrid('getCol',colnames[j]['name'],false,'sum');
		}
	 }
}

/**
 * 
 * @param colName
 * @param targetElement
 * @returns {Boolean}
 */
function getGridColSum(colName,targetElement)
{
	var	 gridId	=	'#'+gridOption.gridId;
	
	if(!$(gridId).doesExist())
	{
		console.log('Grid doesnot exists with id '+gridId);
		
		return false;
	}
	
	var colnames = $(gridId).jqGrid('getGridParam','colModel');
	
	for(var j=0;j<colnames.length;j++)
	 {
		if(colnames[j]['name']==colName)
		{
			var	total	=	$(gridId).jqGrid('getCol',colnames[j]['name'],false,'sum');
			
			 total=total.toFixed(2);
			 
			if(typeof targetElement !== 'undefined')
			{
				if(getElementType(targetElement))
				{
					$('#'+targetElement).val(total);
				}
				else
				{
					$('#'+targetElement).html(total);
				}
			}	
			else
			{
				console.log('No such element exists with given id '+targetElement);
			}
			
			break;
		}
	 }
}