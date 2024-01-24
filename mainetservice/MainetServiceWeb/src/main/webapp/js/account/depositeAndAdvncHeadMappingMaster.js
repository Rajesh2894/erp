
$(function () {
    $("#grid").jqGrid({
        url: "AccountDepositeAndAdvnHeadsMappingEntryMaster.html?getGridData",
        datatype: "json",
        mtype: "POST",
        colNames: [getLocalMessage('acc.master.fundMasterCode'),getLocalMessage('acc.master.fieldCode'),getLocalMessage('acc.master.fieldCode'),getLocalMessage('account.budgetopenmaster.primaryaccountcode'),getLocalMessage('account.budgetopenmaster.secondaryaccountcode')],
        colModel: [
            { name: "fundCode", sortable: true ,width : 30,},
            { name: "fieldCode", sortable: true ,width : 30,},
            { name: "functionCode",  sortable: true,width : 30, },
            { name: "primaryCode", sortable: false ,width : 30,},
            { name: "secondaryCode", sortable: false ,width : 30,},
        ],
        pager: "#pagered",
        rowNum: 30,
        rowList: [5, 10, 20, 30],
        sortname: "cpmId",
        sortorder: "desc",
        height:'auto',
        viewrecords: true,
        gridview: true,
        loadonce: true,
        jsonReader : {
            root: "rows",
            page: "page",
            total: "total",
            records: "records", 
            repeatitems: false,
        }, 
        autoencode: true,
        caption: "List"
    }); 
}); 

function openEntryForm() {
	var arr = validateData();
	if(arr[4]){
	var url = "AccountDepositeAndAdvnHeadsMappingEntryMaster.html?showLeveledform";
	var requestData = "mappingId=" + arr[0]  + "&depositeId=" + arr[1] + "&advncId="+arr[2] + "&deptId="+ arr[3];
	var returnData =__doAjaxRequest(url,'post',requestData,false);
	
	$('.content').html(returnData);
	
	prepareDateTag();
	return false;
	}
}


function validateData(){
	var mappingId = $("#mappintType").val();
	var depositeId = $("#depositeType").val();
	var advnId = $("#advancedType").val();
	var deptId = $("#deptId").val();
	var array =  new Array();
	array[0]=mappingId;
	array[1]=depositeId;
	array[2]=advnId;
	array[3]=deptId;
	var errorList = [];
	var levelCode  = $('#mappintType option:selected').attr('code');
	if(mappingId == 0) {
		errorList.push("Please Select Mapping Type");
	}
	
	if(depositeId == 0 && advnId==0 ) {
		errorList.push("Please Select Deposite Or Advance Type");
	}
	
	if(levelCode!='AHM' && deptId == 0) {
		errorList.push("Please Select Department Type");
	
	}
	 if(errorList.length > 0){
			
		 var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	
		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		});
	
		errMsg += '</ul>';			 
		$("#errorDiv").html(errMsg);					
		$("#errorDiv").removeClass('hide')
		$('html,body').animate({ scrollTop: 0 }, 'slow');
		
		errorList = [];		
		array[4]=false;
		return array;
	}
	 array[4]=true;
	return array;
}
	
	



$(function() {		
	$(document).on('click', '.depositeHead', function() {
		
	});
});

$( document ).ready(function() {
	/*$('.error-div').hide();*/
	$('#depositeHead').hide();
	$('#advancedHead').hide();
	
	
	
	var content = $('#tableId').clone();
	content.find("select").val("");
	content.find("input:text").val("");
	content.find("input:hidden").val('');
});

function populateFileds(){
	var levelCode  = $('#mappintType option:selected').attr('code');
	if(levelCode=='DHM'){
		$('#depositeHead').show();
		$('#advancedHead').hide();
	}else{
		$('#depositeHead').hide();
		$('#advancedHead').show();
	}	
}



$("#depositeAndAdvnMapEntryTable").on("click", '.addMappingLabel',function(e) {

	var errorList = [];
	 $('.appendableClass tbody').each(function(i) {
		row = i;
		var fundId 				= $("#fundId" + i).val();
		var fieldId			 	= $("#fieldId" + i).val();
		var functionId		 	= $("#functionId" + i).val();
		var primaryCodeId		= $("#primaryCodeId" + i).val();
		var secondaryCodeId 	= $("#secondaryCodeId" + i).val();
		var remarkId  			= $("#remarkId" + i).val();
		
	
		
		if(primaryCodeId == '') {
			errorList.push("Primary Code must not empty");
		}
		
		if(secondaryCodeId == '') {
		errorList.push("Secondary Code must not empty");
	}
		
	}); 
	
	 if(errorList.length > 0){
			
		 var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		});

		errMsg += '</ul>';			 
		$("#errorDiv").html(errMsg);					
		$("#errorDiv").removeClass('hide')
		$('html,body').animate({ scrollTop: 0 }, 'slow');
		
		errorList = [];		
		return false;
	} 

 	e.preventDefault();
 
	var content = $(this).closest('#depositeAndAdvnMapEntryTable tbody').clone();
	$(this).closest("#depositeAndAdvnMapEntryTable tbody").after(content);
	
	// for reset all value 
	content.find("select").val("");
	content.find("input:text").val("");
	content.find("input:hidden").val('');
	
	// for generating dynamic Id
	content.find("select:eq(0)").attr("id","fieldId" + (row + 1));
	content.find("select:eq(1)").attr("id","fundId" + (row + 1));
	content.find("select:eq(2)").attr("id","functionId" + (row + 1));
	content.find("select:eq(3)").attr("id","primaryCodeId" + (row + 1));
	content.find("select:eq(4)").attr("id","secondaryCodeId" + (row + 1));
	content.find("input:text:eq(0)").attr("id","remark" + (row + 1));
	
	
	
 	content.find('.delButton').attr("id", "delButton"+ (row+1));
 	content.find('.addButton').attr("id", "addButton"+ (row+1));
 	
 	// for generating dynamic path
	content.find("select:eq(0)").attr("name","listOfDto[" + (row + 1)+ "].fundId").attr("path","listOfDto[" + (row + 1)+ "].fundId");	
	content.find("select:eq(1)").attr("name","listOfDto[" + (row + 1)+ "].fieldId").attr("path","listOfDto[" + (row + 1)+ "].fieldId");
	content.find("select:eq(2)").attr("name","listOfDto[" + (row + 1)+ "].functionId").attr("path","listOfDto[" + (row + 1)+ "].functionId");
	content.find("select:eq(3)").attr("name","listOfDto[" + (row + 1)+ "].primaryCodeId").attr("path","listOfDto[" + (row + 1)+ "].primaryCodeId");
	content.find("select:eq(4)").attr("name","listOfDto[" + (row + 1)+ "].secondaryCodeId").attr("path","listOfDto[" + (row + 1)+ "].secondaryCodeId");
	content.find("input:text:eq(0)").attr("name","listOfDto[" + (row + 1)+ "].remark");
	
	reOrderTableIdSequence();
});



$("#depositeAndAdvnMapEntryTable").on("click", '.deleteMappingLabel', function(e) {

	var rowCount = $('#depositeAndAdvnMapEntryTable tbody').length;
	if (rowCount <= 1) {
	return false;
	}
	$(this).closest('#depositeAndAdvnMapEntryTable tbody').remove();
	reOrderTableIdSequence();
	e.preventDefault();
});	



function reOrderTableIdSequence() {

$('.appendableClass tbody').each(function(i) {

$(".datepicker").datepicker("destroy");

$(this).find("select:eq(0)").attr("id","fundId" + i);
$(this).find("select:eq(1)").attr("id","fieldId" + i);
$(this).find("select:eq:eq(2)").attr("id","functionId" + i);
$(this).find("select:eq:eq(3)").attr("id","primaryCodeId" + i);
$(this).find("select:eq(4)").attr("id","secondaryCodeId" + i);
$(this).find("input:text:eq(5)").attr("id","remarkId" + i);


		
$(this).parents('tr').find('.delButton').attr("id", "delButton"+ i);
$(this).parents('tr').find('.addButton').attr("id", "addButton"+ i);


 $(".datepicker").datepicker({
        dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true
});
});
}


function searchForData(Obj){
	var arr = validateData();
	if(arr[4]){
	var url = "AccountDepositeAndAdvnHeadsMappingEntryMaster.html?searchData";
	var requestData = "mappingId=" + arr[0]  + "&depositeId=" + arr[1] + "&advncId="+arr[2] + "&deptId="+ arr[3];
	var response =__doAjaxRequest(url,'POST',requestData,false,'');
	reloadGrid('grid','json');
	}
}

function editData(Obj){
	var arr = validateData();
	if(arr[4]){
	var url = "AccountDepositeAndAdvnHeadsMappingEntryMaster.html?editData";
	var requestData = "mappingId=" + arr[0]  + "&depositeId=" + arr[1] + "&advncId="+arr[2] + "&deptId="+ arr[3];
	var response =__doAjaxRequest(url,'POST',requestData,false,'');
	$('.content').html(response);
	
	prepareDateTag();
	}
}





function saveLeveledData(obj){
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	requestData = __serializeForm(theForm);

	var url	=	$(theForm).attr('action')+'?' + 'saveForm';
	var returnData =__doAjaxRequest(url,'post',requestData,false);
	
	$('.content').html(returnData);
	
	prepareDateTag();
}


function addRow() {

	
	row = i;
	var fundId 				= $("#fundId" + i).val();
	var fieldId			 	= $("#fieldId" + i).val();
	var functionId		 	= $("#functionId" + i).val();
	var primaryCodeId		= $("#primaryCodeId" + i).val();
	var secondaryCodeId 	= $("#secondaryCodeId" + i).val();
	var remarkId  			= $("#remarkId" + i).val();
	



	

var content = $('#tableId :last').clone()
$("#tableId").last().after(content);

// for reset all value 
content.find("select").val("");
content.find("input:text").val("");
content.find("input:hidden").val('');

// for generating dynamic Id
content.find("select:eq(0)").attr("id","fieldId" + (row + 1));
content.find("select:eq(1)").attr("id","fundId" + (row + 1));
content.find("select:eq(2)").attr("id","functionId" + (row + 1));
content.find("select:eq(3)").attr("id","primaryCodeId" + (row + 1));
content.find("select:eq(4)").attr("id","secondaryCodeId" + (row + 1));
content.find("input:text:eq(0)").attr("id","remark" + (row + 1));

	// for generating dynamic path
content.find("select:eq(0)").attr("name","listOfDto[" + (row + 1)+ "].fundId").attr("path","listOfDto[" + (row + 1)+ "].fundId");	
content.find("select:eq(1)").attr("name","listOfDto[" + (row + 1)+ "].fieldId").attr("path","listOfDto[" + (row + 1)+ "].fieldId");
content.find("select:eq(2)").attr("name","listOfDto[" + (row + 1)+ "].functionId").attr("path","listOfDto[" + (row + 1)+ "].functionId");
content.find("select:eq(3)").attr("name","listOfDto[" + (row + 1)+ "].primaryCodeId").attr("path","listOfDto[" + (row + 1)+ "].primaryCodeId");
content.find("select:eq(4)").attr("name","listOfDto[" + (row + 1)+ "].secondaryCodeId").attr("path","listOfDto[" + (row + 1)+ "].secondaryCodeId");
content.find("input:text:eq(0)").attr("name","listOfDto[" + (row + 1)+ "].remark");

reOrderTableIdSequence();
}





