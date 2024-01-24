//waterUrl='WaterDataEntrySuite.html';
$(document).ready(function() {
    
//    reloadGrid('gridWaterDataEntrySuite');
	
});
$(function() {
	$("#grid").jqGrid({
		url:"WaterDataEntrySuite.html?SEARCH_GRID_RESULTS",
		datatype: "json",
        mtype: "POST",
        colNames: [getLocalMessage('water.dataentry.property.number'), getLocalMessage('MeterReadingDTO.csCcn'),getLocalMessage('water.dataentry.consumer.name'),getLocalMessage('water.dataentry.guardian.name'),getLocalMessage('water.reconnection.mobileNo'),getLocalMessage('water.plumberLicense.address'),getLocalMessage('master.grid.column.action')],
        colModel: [            
            { name: "propertyNo", width: 40, sortable: false,search:true, align: 'center !important' },
            { name: "csCcn", width: 40, sortable: false,search:true, align: 'center !important' },
            { name: "csName", width: 40, sortable: false,search:true, align: 'center !important' },
            { name: "gardianOwnerName", width: 40, sortable: false,search:true, align: 'center !important' },
            { name: "csContactno", width: 40, sortable: false,search:true, align: 'center !important' },
            { name: 'csAdd', width: 40, sortable: false,search:true, align: 'center !important' },
            { name: 'rowId', index: 'rowId', width: 35, align: 'center !important', sortable: false,formatter:actionFormatter,search:false}
        ],
        pager: "#pagered",
        rowNum: 1000,
        rowList: [5, 10, 20, 30],
        sortname: "rowId",
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
        caption: getLocalMessage('water.propertydetails')
	});
	 jQuery("#grid").jqGrid('navGrid', '#pagered', {edit : false,add : false,del : false,refresh : false});
		$("#pagered_left").css("width", "");
});

function actionFormatter(cellvalue, options, rowObject){
	debugger;
	return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewProperty('"+rowObject.rowId+"')\"><i class='fa fa-eye' aria-hidden='true'></i></a> " +
		 "<a class='btn btn-warning btn-sm' title='Edit' onclick=\"editProperty('"+rowObject.rowId+"')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> ";

}

function viewProperty(rowId) {
	debugger;
	 var divName = ".widget-content";
	var requestData = "rowId="+rowId+"&saveMode=V";
	var ajaxResponse = __doAjaxRequestForSave('WaterDataEntrySuite.html?edit', 'post', requestData, false,
		    '', '');
	var errMsg = ajaxResponse["errMsg"];
    if (errMsg != '' && errMsg != undefined) {
	var errorList = [];
	errorList.push(errMsg);
	showErr(errorList);
    } else {
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	$(divName).show();
    }
}
function editProperty(rowId) {
	debugger;
	 var divName = ".widget-content";
	var requestData = "rowId="+rowId+"&saveMode=E";
	var ajaxResponse = __doAjaxRequestForSave('WaterDataEntrySuite.html?edit', 'post', requestData, false,
		    '', '');
	var errMsg = ajaxResponse["errMsg"];
    if (errMsg != '' && errMsg != undefined) {
	var errorList = [];
	errorList.push(errMsg);
	showErr(errorList);
    } else {
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	$(divName).show();
    }
}

function openUpdateForm(formName, actionParam) {
	showloader(true);
	setTimeout(function(){codeopenUpdateForm(formName, actionParam)},2);
}

function codeopenUpdateForm(formName, actionParam){
	var theForm = '#' + formName;
    var divName = ".widget-content";
    var url = $(theForm).attr('action');
    if (!actionParam) {
    } else {
	url += '?' + actionParam;
    }
    var requestData = __serializeForm(theForm);
    var ajaxResponse = __doAjaxRequestForSave(url, 'post', requestData, false,
	    '', '');
    var errMsg = ajaxResponse["errMsg"];
    if (errMsg != '' && errMsg != undefined) {
	var errorList = [];
	errorList.push(errMsg);
	showErr(errorList);
    } else {
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	$(divName).show();
    }
}

function showErr(errorList) {

    var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
    $.each(errorList, function(index) {
	errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
		+ errorList[index] + '</li>';
    });
    errMsg += '</ul>';
    $(".warning-div").html(errMsg);
    $(".warning-div").removeClass('hide')
    $('html,body').animate({
	scrollTop : 0
    }, 'slow');
    errorList = [];
}

function closeErrBox() {
    $('.warning-div').addClass('hide');
}

function Searchconnection(element) {
    showloader(true);
    setTimeout(function(){
    var data = $('#WaterDataEntrySearchSuite').serialize();
    var URL = 'WaterDataEntrySuite.html?searchData';
    var returnData = __doAjaxRequest(URL, 'POST', data, false);
    $(formDivName).html(returnData);
    reloadGrid('gridWaterDataEntrySuite');
    },2);
}

function resetConnection() {
    var data = {};
    var URL = 'WaterDataEntrySuite.html?resetSeachGrid';
    var returnData = __doAjaxRequest(URL, 'POST', data, false);
    $(formDivName).html(returnData);
}

function addDataEntryDetails() {
    showloader(true);
    setTimeout(function(){
    var errorList = [];    
    var checkWardZonePrefix = checkWardZonePrefixUp();
    if (checkWardZonePrefix != "N") {
	var data = {
	    "type" : "C"
	};
	var URL = 'WaterDataEntrySuite.html?form';
	var returnData = __doAjaxRequest(URL, 'POST', data, false, 'html');
	$('.content-page').html(returnData);
    } else {

	errorList.push(getLocalMessage("water.dataentry.set.ward.zone"));
	showErr(errorList);
    }
    },2);
}

function checkWardZonePrefixUp() {
    var checkUrl = "WaterDataEntrySuite.html?validateWardZonePrefix";
    var postdata = {};
    var returnData = __doAjaxRequest(checkUrl, 'POST', postdata, '', '');
    return returnData;
}