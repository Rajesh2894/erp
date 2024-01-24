$(document).ready(function() {

	$("#frmMetadataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

});

$("#attachDoc").on("click", '.delBtn', function(e) {

	var countRows = -1;
	$('.appendableClass').each(function(i) {
		if ($(this).closest('tr').is(':visible')) {
			countRows = countRows + 1;
		}
	});
	var row = countRows;
	if (row != 0) {
		$(this).parent().parent().remove();
		row--;
	}
	e.preventDefault();
});

$("#deleteDoc").on(
		"click",
		'#deleteFile',
		function(e) {

			var errorList = [];
			if (errorList.length > 0) {
				$("#errorDiv").show();
				showErr(errorList);
				return false;
			} else {
				$(this).parent().parent().remove();
				var fileId = $(this).parent().parent().find(
						'input[type=hidden]:first').attr('value');
				if (fileId != '') {
					removeFileIdArray.push(fileId);
				}
				$('#removeFileById').val(removeFileIdArray);
			}
		});

function documentUpload(element) {

	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('DmsMetadata.html?fileCountUpload', 'POST',
			requestData, false, 'html');
	$("#uploadTagDiv").html(response);
	prepareTags();

}

$(function() {	
	$("#deptId").change(
		
			function() {
		
				var divName = '.content-page';
				var deptId = $("#deptId").val();
				var requestData = {
					'deptId' : deptId
				}
				var ajaxResponse = __doAjaxRequest(
						'DmsMetadata.html?getMetadata', 'POST', requestData,
						false, 'html');
				$(divName).html(ajaxResponse);

			});
});

function saveForm(element) {

	return saveOrUpdateForm(element, "", 'AdminHome.html', 'saveform');
}
function scanDocument(element)
{
    var id = $(element).attr('id') ,array,index=-1,length=1;
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	requestData=requestData+"&"+"id="+id;
	var response = __doAjaxRequest('DmsMetadata.html?setUrl', 'POST',
			requestData, false,'json');
	//var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	 window.open(response, '_blank');
	}