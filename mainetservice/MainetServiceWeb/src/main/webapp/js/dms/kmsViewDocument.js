$(document).ready(function() {

	$("#frmMetadataDetailTbl").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});


});

function SearchDetails(element) {

	var divName = '.content-page';
	var level1 = $('#level1').val();
	var level2 = $('#level2').val();
	var docType = $('#docType').val();
	var metadataValue = $('#metadataValue').val();
	var requestData = {
		'level1' : level1,
		'level2' : level2,
		'metadataValue' : metadataValue,
		'docType' : docType
	}
	var ajaxResponse = __doAjaxRequest(
			'KmsViewDocument.html?searchDetails', 'POST', requestData,
			false, 'html');
	$(divName).html(ajaxResponse);
	$("#frmMetadataDetailTbl").show();

}