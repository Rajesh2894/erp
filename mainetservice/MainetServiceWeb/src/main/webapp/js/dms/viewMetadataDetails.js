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
	var ward1 = $('#ward1').val();
	var ward2 = $('#ward2').val();
	var ward3 = $('#ward3').val();
	// var docRefNo = $('#docRefNo').val();
	var metadataValue = $('#metadataValue').val();
	if (ward1 == undefined) {
		ward1 = 0;
	}
	if (ward2 == undefined) {
		ward2 = 0;
	}
	if (ward3 == undefined) {
		ward3 = 0;
	}
	var requestData = {
		'level1' : level1,
		'level2' : level2,
		'ward1' : ward1,
		'ward2' : ward2,
		'ward3' : ward3,
		// 'docRefNo' : docRefNo,
		'metadataValue' : metadataValue
	}
	var ajaxResponse = __doAjaxRequest(
			'ViewMetadataDetails.html?searchDetails', 'POST', requestData,
			false, 'html');
	$(divName).html(ajaxResponse);
	$("#frmMetadataDetailTbl").show();

}

$(function() {
	$("#level1").change(
			function() {

				var divName = '.content-page';
				var deptId = $("#level1").val();
				var requestData = {
					'deptId' : deptId
				}
				var ajaxResponse = __doAjaxRequest(
						'ViewMetadataDetails.html?getWardZone', 'POST',
						requestData, false, 'html');
				$(divName).html(ajaxResponse);

			});
});