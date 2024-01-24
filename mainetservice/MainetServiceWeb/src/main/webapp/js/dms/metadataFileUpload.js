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

	return saveOrUpdateForm(element, "", 'DmsMetadata.html', 'saveform');
}
