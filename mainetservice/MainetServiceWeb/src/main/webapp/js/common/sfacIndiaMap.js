$(document).ready(
		function() {

			$('.chosen-select-no-results').chosen();

		
			
			$("#allocatedTable").dataTable({
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 20, 40, 65, -1 ], [ 20, 40, 60, "All" ] ],
				"iDisplayLength" : 20,
				"bInfo" : true,
				"lengthChange" : true
			});
			
			$("#vacantTable").dataTable({
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 20, 40, 60, -1 ], [ 20, 40, 60, "All" ] ],
				"iDisplayLength" : 20,
				"bInfo" : true,
				"lengthChange" : true
			});


		});

function getDistrictList() {
	var requestData = {
			"stateId" : $("#stateId").val()
	};
	var URL = 'SfacIndiaMap.html?getDistrictList';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#distId').html('');
	$('#distId').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));
	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#distId').append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#distId').trigger("chosen:updated");

	redraw();
}

function getDisData(obj){


	var distId = $('#distId').val();


	var disName = $("#distId option:selected").text();

	var divName = '.content-page';


	var requestData = {
			"distId" : distId,

	};

	var ajaxResponse = doAjaxLoading('SfacIndiaMap.html?districtDetail', requestData, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

	$('#mainDiv').hide();
	$('#blockDiv').show();
	$('#nameDis').html(disName + "District");
	
	prepareTags();
	
	

}

function getBlockData(obj){


	var blockId = $('#blockId').val();


	var disName = $("#blockId option:selected").text();

	var divName = '.content-page';


	var requestData = {
			"blockId" : blockId,

	};

	var ajaxResponse = doAjaxLoading('SfacIndiaMap.html?blockDetail', requestData, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

	$('#mainDiv').hide();
	$('#blockDiv').show();
	$('#nameDis').html(disName + " Block");
	
	prepareTags();
	
	

}



function loadAllocated(dat) {
	

	$('#' + dat + '-tab').find('a').attr('href', '#' + dat);

	$('#fpoPMParentTab a[href="#' + dat + '"]').tab('show');

	$('#viewMode').val(dat.toUpperCase());
	$('#mainDiv').show();
	$('#blockDiv').hide();

	redraw();
}