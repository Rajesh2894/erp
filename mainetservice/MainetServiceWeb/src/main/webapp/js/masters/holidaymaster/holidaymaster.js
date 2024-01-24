var leave = [];

$(document).ready(function() {
	$("#wrStartTime").val($("#wrStartTime").val().substr(10));
	$("#wrEndTime").val($("#wrEndTime").val().substr(10));

	var selectedType = $("#wrWeekType").find("option:selected").attr('code');
	if (selectedType == "N") {
		$(".wrWorkWeek").show();
		$(".wrWorkWeekAlternate").hide();
	} else {
		$(".wrWorkWeekAlternate").show();
		$(".wrWorkWeek").hide();
	}

	$("#wrWeekType").prop("disabled", true);

});

$(document).ready(
		function() {

			$('#yearStartDate').MonthPicker({
				ShowIcon : false,
				MonthFormat : 'M, yy',
				AltFormat : 'dd/mm/yy',
				AltField : '#hoYearStartDate'
			});


			$(".add").hide();
			$(".edit").hide();
			$("#workTime").hide();
			
			
			$("input[name$='holidayTime']").click(function() {
		        var test = $(this).val();
		        if(test=="HolidayMaster"){
		        	$("#holidayMas").show();
		        	$("#workTime").hide();
		        }else{
		        	$("#holidayMas").hide();
		        	$("#workTime").show();
		        }
		    });

			// use to show dropdown for week days (Odd/Even)
			var options = [];

			$('.dropdown-menu a').on(
					'click',
					function(event) {

						var $target = $(event.currentTarget), val = $target
								.attr('data-value'), $inp = $target
								.find('input'), idx;

						if ((idx = options.indexOf(val)) > -1) {
							options.splice(idx, 1);
							setTimeout(function() {
								$inp.prop('checked', false)
							}, 0);
						} else {
							options.push(val);
							setTimeout(function() {
								$inp.prop('checked', true)
							}, 0);
						}

						$(event.target).blur();

						return false;
					});

			$(document).ready(function() {
				$(".oddWorkWeek").click(function() {
					var selectedLanguage = new Array();
					$('input[name="selector1"]:checked').each(function() {
						selectedLanguage.push(this.value);
					});
					$("#wrOddWorkWeek").val(selectedLanguage);
				});

				$(".evenWorkWeek").click(function() {
					var selectedLanguage = new Array();
					$('input[name="selector2"]:checked').each(function() {
						selectedLanguage.push(this.value);
					});
					$("#wrEvenWorkWeek").val(selectedLanguage);
				});
			});

		});

var HolidayUrl = 'HolidayMaster.html';
$(document)
		.ready(
				function() {

					$("#HolidayGrid")
							.jqGrid(
									{
										url : 'HolidayMaster.html'
												+ "?HolidayMasterGridData",
										datatype : "json",
										mtype : "POST",
										colNames : [
												'',
												getLocalMessage('holidaymaster.date'),
												getLocalMessage('holidaymaster.holidaydetails'),
												getLocalMessage('estate.grid.column.action') ],
										colModel : [ {
											name : "hoId",
											width : 5,
											hidden : true
										}, {
											name : "hoDate",
											width : 30,
											sortable : true,
											formatter : dateTemplate
										}, {
											name : "hoDescription",
											width : 20,
											search : true
										}, {
											name : 'enbll',
											index : 'enbll',
											width : 30,
											align : 'center !important',
											formatter : actionFormatter,
											search : false
										} ],
										pager : "#HolidayPager",
										rowNum : 30,
										rowList : [ 5, 10, 20, 30 ],
										sortname : "code",
										sortorder : "desc",
										height : 'auto',
										viewrecords : true,
										gridview : true,
										loadonce : true,
										jsonReader : {
											root : "rows",
											page : "page",
											total : "total",
											records : "records",
											repeatitems : false,
										},
										autoencode : true,
										caption : getLocalMessage('holidaymaster.holidaydetails')                
									});
					$("#HolidayGrid").jqGrid('navGrid', '#HolidayPager', {
						edit : false,
						add : false,
						del : false,
						search : true,
						refresh : true,
						closeAfterSearch : true
					});
					$("#HolidayPager_left").css("width", "");
				});
	

function actionFormatter(cellvalue, options, rowObject) {
	return "<a class='btn btn-danger btn-sm' title='Delete' onclick=\"showConfirmBox('"
			+ rowObject.hoId
			+ "','D')\"><i class='fa fa-trash' aria-hidden='true'></i></a> ";
}

function deleteHoliday(hoId) {
	var divName = formDivName;
	var requestData = 'hoId=' + hoId;
	var ajaxResponse = doAjaxLoading(HolidayUrl + '?deleteHoliday',
			requestData, 'html');
	proceed();
	/*$('.content').removeClass('ajaxloader');
	$('.content').html(ajaxResponse);*/
	$("#HolidayGrid").jqGrid('setGridParam', {
		datatype : 'json'
	}).trigger('reloadGrid');

}

function showConfirmBox(hoId, type) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Yes';

	message += '<h4 class=\"text-center text-blue-2 padding-12\">Are you sure want to delete</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\''
			+ cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'  onclick="deleteHoliday('
			+ hoId + ')"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	$("#HolidayGrid").jqGrid('setGridParam', {
		datatype : 'json'
	}).trigger('reloadGrid');

	return false;
}

function proceed() {
	$("#HolidayGrid").jqGrid('setGridParam', {
		datatype : 'json'
	}).trigger('reloadGrid');
	$.fancybox.close();
	
}

function openFormHoliday(formUrl, actionParam, typeFlag) {
	if (!actionParam) {

		actionParam = "add";
	}
	
	var requestData = 'typeFlag=' + typeFlag;

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

	prepareTags();

}

function saveGridData() {
	
	var errorList = [];
	if ($("#hoYearStartDate").val() == "")
		errorList.push(getLocalMessage("holidaymaster.vldnn.fromdate"));
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
		return false;
	} else {
		$("#errorDiv").hide();
	}

	var requestData = 'yearStartDate=' + $("#hoYearStartDate").val()
			+ '&yearEndDate=' + $("#hoYearEndDate").val();
	var ajaxResponse = doAjaxLoading(HolidayUrl + '?getGridData', requestData,
			'html');

	$("#HolidayGrid").jqGrid('setGridParam', {
		datatype : 'json'
	}).trigger('reloadGrid');

	if (ajaxResponse == '"Y"') {
		$(".add").hide();
		$(".edit").show();
		
	} else {
		$(".add").show();
		$(".edit").hide();
		
	}

}

function showErr(errorList) {
	$(".warning-div").removeClass('hide');
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closePrefixErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);

	$("html, body").animate({
		scrollTop : 0
	}, "slow");
}

function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}

function printHolidayDetails() {
	var requestData = {};
	var errorList = [];

	var returnDataMap = doAjaxLoading('HolidayMaster.html'
			+ '?printHolidayDetails', requestData, 'html');

	if (returnDataMap == '"N"')
		errorList.push(getLocalMessage("holidaymaster.vldnn.gridprint"));

	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
		return false;
	} else {
		$("#errorDiv").hide();
	}

	var returnData = returnDataMap.substring(2, returnDataMap.length - 2);

	var next1 = returnData.split(",");

	var returnDataMaptable = next1;

	var notesGroup = "";

	notesGroup = $("<table id='calTable' class='table table-bordered table-condensed'><tr><th>Holiday Date</th><th>Holiday Description</th></tr></table>");

	var arrayLength = returnDataMaptable.length;
	for (var i = 0; i < arrayLength; i++) {
		var a1;
		var a2;
		var next2 = returnDataMaptable[i].split("=");
		for (var j = 0; j < next2.length; j++) {

			if (j == 0) {
				a1 = next2[j];
			} else {
				a2 = next2[j];
			}
		}

		j = 0;

		var list = "";

		list += "<tr><td>" + a1 + "</td><td>" + a2 + "</td></tr>";

		notesGroup.append(list);

	}

	$('#calTablePrint').hide();
	$("#calTablePrint").html(notesGroup);

	var divContents = $("#calTablePrint").html();

	var printWindow = window.open('', '', 'height=400,width=800');
	printWindow.document
			.write('<html><head><title>Holiday Details</title></head>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">');
	printWindow.document
			.write('<style> @media print{#print {display:none;}}</style>');

	printWindow.document.write('<body>');
	printWindow.document.write(divContents);
	printWindow.document
			.write('<div class="text-center clear" id="print"><button type="button" onclick="window.print();" class="btn btn-default">Print</button> <button type="button" onclick="window.close();" class="btn btn-danger">Close</button></div>');
	printWindow.document.write('</body></html>');
	printWindow.document.close();
	// printWindow.print();

}

function resetHolidayForm() {
	var requestData = {};
	var returnDataMap = doAjaxLoading('HolidayMaster.html' + '?clearGridData',
			requestData, 'html');
	$("#hoYearStartDate").val("");
	$("#hoYearEndDate").val("");
	$("#yearStartDate").val("");
	$("#yearEndDate").val("");
	$(".add").hide();
	$(".edit").hide();
	$("#HolidayGrid").jqGrid('setGridParam', {
		datatype : 'json'
	}).trigger('reloadGrid');
}

$(document).ready(function() {
	
	$('#yearEndDate').click(function(){
		        return false;
		});

	$('#yearStartDate').MonthPicker({
		OnAfterChooseMonth : function(selectedDate) {
			
			var dateformate=$('#yearStartDate').val();
			var month=dateformate.substring(0,3);
			var year=$('#yearStartDate').val().substring(5,9);
			var tomonth;
			var toyear;
			if(month=="Jan")tomonth="Dec";
			if(month=="Feb")tomonth="Jan";
			if(month=="Mar")tomonth="Feb";
			if(month=="Apr")tomonth="Mar";
			if(month=="May")tomonth="Apr";
			if(month=="Jun")tomonth="May";
			if(month=="Jul")tomonth="Jun";
			if(month=="Aug")tomonth="Jul";
			if(month=="Sep")tomonth="Aug";
			if(month=="Oct")tomonth="Sep";
			if(month=="Nov")tomonth="Oct";
			if(month=="Dec")tomonth="Nov";
			
			if(month=="Jan"){
				toyear=year;
			}
			else{
				toyear=+year + +1;
			}
			
			var finaldate=tomonth+","+" "+toyear;
			
			$('#yearEndDate').val(finaldate);
				
		}
	});

});