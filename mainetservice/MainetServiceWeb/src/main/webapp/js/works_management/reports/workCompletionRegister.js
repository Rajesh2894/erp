$(document).ready(function() {

});
function getWorkName(obj) {

	var requestData = {
		"projId" : $(obj).val()
	}
	$('#workId').html('');
	$('#workId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	var response = __doAjaxRequest('workCompletionRegister.html?worksName',
			'post', requestData, false, 'html');
	var prePopulate = JSON.parse(response);

	$.each(prePopulate, function(index, value) {
		$('#workId').append(
				$("<option></option>").attr("value", value.workId).text(
						(value.workName)));
	});
	$('#workId').trigger("chosen:updated");
}

function getRegister() {
	
	var divName = formDivName;
	var errorList = [];
	var projId = $("#projId").val();
	var workId = $("#workId").val();

	if (projId == '') {
		errorList.push(getLocalMessage('work.Def.valid.select.projName'));
	}
	if (workId == '') {
		errorList.push(getLocalMessage('work.Def.valid.enter.workname'));
	}
	if (errorList.length == 0) {
		var requestData = {
			"projId" : projId,
			"workId" : workId

		}
		var response = __doAjaxRequest(
				'workCompletionRegister.html?getRegister', 'post', requestData,
				false, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(response);
	} else {
		displayErrorsOnPage(errorList);
	}

}

function printDiv(title) {
	
	/*var divContents = document.getElementById("receipt").innerHTML;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button> <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();*/
	
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(title).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;

}