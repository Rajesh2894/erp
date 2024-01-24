

var maxDays;
$(document)
		.ready(
				function() {

					$("#dataEntryTableId").dataTable(
							{
								"oLanguage" : {
									"sSearch" : ""
								},
								"aLengthMenu" : [ [ 5, 10, 15, -1 ],
										[ 5, 10, 15, "All" ] ],
								"iDisplayLength" : 5,
								"bInfo" : true,
								"lengthChange" : true
							});
					/*
					 * $('#newAdvertisingTableId').dataTable( { "oLanguage" : {
					 * "sSearch" : "" }, "aLengthMenu" : [ [ 10, 20, 30, -1 ], [
					 * 10, 20, 30, "All" ] ], "iDisplayLength" : 10, "bInfo" :
					 * true, "bStateSave" : true, "lengthChange" : true,
					 * "scrollCollapse" : true, "bSort" : false });
					 */

					
					//ABM2144
			var licMaxTenureDays = $('#licMaxTenureDays').val();
			var yearType = $('#YearType').val();
			$("#licenseFromDate").datepicker({
	            dateFormat : 'dd/mm/yy',
	            changeMonth : true,
	            changeYear : true,
	            maxDate : 0,
	            onSelect : function(selected) {
	            var sdate = $(this).datepicker("getDate");
	            var cdd = 11-sdate.getMonth();
	            var fdd = 2-sdate.getMonth();
	            var mths = Math.floor(licMaxTenureDays/30);
	            var fmths = 0;
	            if(yearType=="C"){
	              if(cdd<mths){
	                sdate.setMonth(11);sdate.setDate(31);
	              }else{
	            	sdate.setDate(sdate.getDate()+parseInt(licMaxTenureDays));
	              }
	            }else{
	            	if(fdd>=0){
	            		fmths = fdd;
	            	}else{
	            		fmths = 12+fdd;
	            	}
	            	if(mths>fmths){
	            		sdate.setDate(sdate.getDate()+parseInt(licMaxTenureDays));sdate.setMonth(2);sdate.setDate(31);
	            	}else{
	            		sdate.setDate(sdate.getDate()+parseInt(licMaxTenureDays));
	            	}		
	            }
	            $("#licenseToDate").datepicker("option", "minDate",selected);
	            $("#licenseToDate").datepicker("setDate",sdate);
	            $("#licenseToDate").datepicker("option", "maxDate",sdate);
	           }
	    
	        });
		
          
					$("#licenseToDate").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						minDate : '0',
						maxDate : +licMaxTenureDays,
					});
					$(".datepicker").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
					// minDate : '-0d',
					});
					$("#fromdate").keyup(function(e) {
						if (e.keyCode != 8) {
							if ($(this).val().length == 2) {
								$(this).val($(this).val() + "/");
							} else if ($(this).val().length == 5) {
								$(this).val($(this).val() + "/");
							}
						}
					});

					$("#todate").keyup(function(e) {
						if (e.keyCode != 8) {
							if ($(this).val().length == 2) {
								$(this).val($(this).val() + "/");
							} else if ($(this).val().length == 5) {
								$(this).val($(this).val() + "/");
							}
						}
					});

					// getAgencyDetails();
					if ($('#hideAgnId').val() != '') {
						$(
								"#agnId option[value='" + $('#hideAgnId').val()
										+ "']").prop('selected', 'selected');
						$('#agnId').trigger("chosen:updated");
					}

					// search advertisement data
					$("#searchDataEntry")
							.click(
									function() {

										var errorList = [];
										var agencyName = $('#agencyId').val();
										var licenseType = $('#licenseType')
												.val();
										if (licenseType == '0') {
											licenseType = '';
										}
										var adhStatus = $('#adhStatus').val();
										if (adhStatus == ''
												|| adhStatus == null
												|| adhStatus == undefined) {
											adhStatus = '';
										}
										var locId = $('#locId').val();
										var licenseFromDate = $('#licenseFromDate').val();
										var licenseToDate = $('#licenseToDate').val();
										if (agencyName != ''
												|| licenseType != ''
												|| adhStatus != ''
												|| locId != '') {
											var requestData = '&agencyId='
													+ agencyName
													+ '&licenseType='
													+ licenseType
													+ '&adhStatus=' + adhStatus
													+ '&locId=' + locId
													+ '&licenseFromDate=' + licenseFromDate
													+ '&licenseToDate=' + licenseToDate;
											var table = $('#dataEntryTableId')
													.DataTable();
											table.rows().remove().draw();
											$(".warning-div").hide();
											var ajaxResponse = __doAjaxRequest(
													'AdvertisementDataEntry.html?searchAdvtDataEntry',
													'POST', requestData, false,
													'json');
											if (ajaxResponse.length == 0) {
												errorList
														.push(getLocalMessage("adh.validate.search"));
												displayErrorsOnPage(errorList);
												return false;
											}
											var result = [];
											$
													.each(
															ajaxResponse,
															function(index) {
																var obj = ajaxResponse[index];
																if (obj.apmApplicationId != null) {
																	result
																			.push([
																					obj.licenseNo,
																					obj.agencyName,
																					obj.locIdDesc,
																					obj.licenseTypeDesc,
																					obj.adhStatusDesc,
																					formatDate(obj.licenseFromDate),
																					formatDate(obj.licenseToDate),
																					'<td >'
																							+ '<button type="button" class="btn btn-blue-2 btn-sm margin-right-10 margin-left-30"  onclick="editOrViewAdvDataEntry(\''
																							+ obj.adhId
																							+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>' ]);
																} else {
																	result
																			.push([
																					obj.licenseNo,
																					obj.agencyName,
																					obj.locIdDesc,
																					obj.licenseTypeDesc,
																					obj.adhStatusDesc,
																					formatDate(obj.licenseFromDate),
																					formatDate(obj.licenseToDate),
																					'<td >'
																							+ '<button type="button" class="btn btn-blue-2 btn-sm margin-right-10 margin-left-30"  onclick="editOrViewAdvDataEntry(\''
																							+ obj.adhId
																							+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
																							+ '<button type="button" class="btn btn-warning btn-sm" onclick="editOrViewAdvDataEntry(\''
																							+ obj.adhId
																							+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>' ]);
																}
															});
											table.rows.add(result);
											table.draw();
										} else {
											errorList
													.push(getLocalMessage('adh.select.any.field'));
											displayErrorsOnPage(errorList);
										}
									});

					if ($("#saveMode").val() == 'V') {
						$("#AdvertisementDataEntry input").prop("disabled",
								true);
						$("#AdvertisementDataEntry select").prop("disabled",
								true);
						$("#AdvertisementDataEntry textArea").prop("disabled",
								true);
					}
					reOrderAdvertisingIdSequence('.advertisingDetailsClass');
				});

$(function() {
	$("#newAdvertisingTableId").on(
			'click',
			'.addAdvertising',
			function(e) {
				var errorList = [];

				errorList = validateAdvertDetails(errorList);
				if (errorList.length == 0) {
					$('.error-div').hide();
					e.preventDefault();
					var clickedRow = $('#newAdvertisingTableId tr').length - 2;
					var facingValue = $('#dispTypeId' + clickedRow).val();
					var content = $("#newAdvertisingTableId").find('tr:eq(1)')
							.clone();
					$("#newAdvertisingTableId").append(content);
					content.find("input:text").val('');
					content.find("select").val('0');
					content.find("input:hidden").val('');
					content.find("input:hidden:eq(0)").val("");
					content.find("textarea").val("");
					content.find("select:eq(5)").chosen().trigger(
							"chosen:updated");

					reOrderAdvertisingIdSequence('.advertisingDetailsClass');
					/*
					 * $("#adhTypeId" + (idcount * 6 + 2) + "
					 * option:not(:first)").remove(); $("#adhTypeId" + (idcount *
					 * 6 + 3) + " option:not(:first)").remove();
					 */
					/*
					 * $("#adhTypeId" + (idcount * 6 + 4) + "
					 * option:not(:first)").remove(); $("#adhTypeId" + (idcount *
					 * 6 + 5) + " option:not(:first)").remove();
					 */
					$('#dispTypeId' + (clickedRow + 1)).val()
							.trigger("chosen:updated");

				} else {
					displayErrorsOnPage(errorList);
				}
			});
});
function addAdvertisementEntry(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	// $('#hrdNumber').hide();
	prepareTags();
}
function validateAdvertDetails(errorList) {
	var rowCount = $('#newAdvertisingTableId tr').length;
	var waste = [];
	var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
	$('.advertisingDetailsClass')
			.each(
					function(i) {

						if (rowCount <= 8) {
							var adhTypeId1 = $("#adhTypeId" + 1).val();
							var adhTypeId2 = $("#adhTypeId" + 2).val();
							var adhTypeId3 = $("#adhTypeId" + 3).val();
							var adhTypeId4 = $("#adhTypeId" + 4).val();
							var adhTypeId5 = $("#adhTypeId" + 5).val();
							var dispTypeId = $("#dispTypeId" + i).val();
							var advDetailsDesc = $("#advDetailsDesc" + i).val();
							var advDetailsHeight = $("#advDetailsHeight" + i)
									.val();
							var advDetailsLength = $("#advDetailsLength" + i)
									.val();
							var advDetailsArea = $("#advDetailsArea" + i).val();
							var unit = $("#unit" + i).val();

							var level = 1;
						} else {
							var utp = i;
							utp = i * 6;
							var adhTypeId1 = $("#adhTypeId" + (utp + 1)).val();
							var adhTypeId2 = $("#adhTypeId" + (utp + 2)).val();
							var adhTypeId3 = $("#adhTypeId" + (utp + 3)).val();
							var adhTypeId4 = $("#adhTypeId" + (utp + 4)).val();
							var adhTypeId5 = $("#adhTypeId" + (utp + 5)).val();
							var dispTypeId = $("#dispTypeId" + i).val();
							var advDetailsDesc = $("#advDetailsDesc" + i).val();
							var advDetailsHeight = $("#advDetailsHeight" + i)
									.val();
							var advDetailsLength = $("#advDetailsLength" + i)
									.val();
							var advDetailsArea = $("#advDetailsArea" + i).val();
							var unit = $("#unit" + i).val();
							if (adhTypeId5 != undefined) {
								errorList.push($("#adhTypeId" + (utp + 5))
										.val())
							} else {
								if (adhTypeId4 != undefined) {
									errorList.push($("#adhTypeId" + (utp + 4))
											.val())
								} else {
									errorList.push($("#adhTypeId" + (utp + 3))
											.val())
								}
							}
						}
						var level = i + 1;
						if (adhTypeId1 == '' || adhTypeId1 == undefined
								|| adhTypeId1 == '0') {
							errorList
									.push(getLocalMessage("adh.select.type.srNo"
											+ " ")
											+ level);
						}
						if (adhTypeId2 == '' || adhTypeId2 == undefined
								|| adhTypeId2 == '0') {
							errorList
									.push(getLocalMessage("adh.select.subtype.srNo"
											+ " ")
											+ level);
						}
						if (advDetailsDesc == '' || advDetailsDesc == undefined
								|| advDetailsDesc == '0') {
							errorList
									.push(getLocalMessage("adh.enter.desc.srNo"
											+ " ")
											+ level);
						}
						/*
						 * if (advDetailsLength == '' || advDetailsLength ==
						 * undefined || advDetailsLength == '0') {
						 * errorList.push(getLocalMessage("Please enter length
						 * for sr.no "+ " ")+ level); }
						 */
						if (advDetailsHeight == ""
								|| advDetailsHeight == undefined
								|| advDetailsHeight == null) {
							errorList
									.push(getLocalMessage("adh.enter.height.srNo"
											+ " ")
											+ level);
						} else {
							if (advDetailsHeight == '0') {
								errorList
										.push(getLocalMessage('adh.height.greater.zero'));
							}
						}
						if (advDetailsLength == ""
								|| advDetailsLength == undefined
								|| advDetailsLength == null) {
							errorList
									.push(getLocalMessage("adh.enter.length.srNo"
											+ " ")
											+ level);
						} else {
							if (advDetailsLength == '0') {
								errorList
										.push(getLocalMessage('adh.length.greater.zero'));
							}
						}
						if (unit == "" || unit == undefined || unit == null) {
							errorList
									.push(getLocalMessage("adh.enter.unit.srNo"
											+ " ")
											+ level);
						} else {
							if (unit == '0') {
								errorList
										.push(getLocalMessage('adh.unit.greater.zero'));
							}
						}
						if (dispTypeId == '' || dispTypeId == undefined
								|| dispTypeId == '0') {
							errorList
									.push(getLocalMessage("adh.select.facing.srNo"
											+ " ")
											+ level);
						}
						if (advDetailsArea == '' || advDetailsArea == undefined || advDetailsArea == '0.00') {
							errorList.push(getLocalMessage("adh.advertisement.validation.enter.area "+ " ")+" " +level);
						}
					});
	/*
	 * var i = 0; var j = 1; var k = 1; var count = 0; for (i = 0; i <=
	 * waste.length; i++) { for (j = k; j <= waste.length; j++) { if (waste[i] ==
	 * waste[j]) { if (count == 0) { errorList
	 * .push(getLocalMessage("swm.validation.wastSubType2change") + (j + 1)); }
	 * count++; } } k++; }
	 */
	return errorList;
}

var idcount;
function reOrderAdvertisingIdSequence(advertisingDetailsClass) {
	$(advertisingDetailsClass)
			.each(
					function(i) {

						var utp = i;
						if (i > 0) {
							utp = i * 6;
						}
						// ID
						$(this).find("input:text:eq(0)").attr("id",
								"sequence" + i).val(i + 1);
						$(this).find("input:hidden:eq(0)").attr("id",
								"adhHrdDetId" + (utp + 1));
						$(this).find("select:eq(0)").attr("id",
								"adhTypeId" + (utp + 1));
						$(this).find("select:eq(1)").attr("id",
								"adhTypeId" + (utp + 2));
						/*
						 * $(this).find("select:eq(2)").attr("id", "adhTypeId" +
						 * (utp + 3)); $(this).find("select:eq(3)").attr("id",
						 * "adhTypeId" + (utp + 4));
						 * $(this).find("select:eq(4)").attr("id", "adhTypeId" +
						 * (utp + 5));
						 */

						$(this).find("textarea:eq(0)").attr("id",
								"advDetailsDesc" + i);
						$(this).find("input:text:eq(1)").attr("id",
								"advDetailsHeight" + i);
						$(this).find("input:text:eq(2)").attr("id",
								"advDetailsLength" + i);
						$(this).find("input:text:eq(3)").attr("id",
								"advDetailsArea" + i);
						$(this).find("input:text:eq(4)").attr("id", "unit" + i);
						$(this).find("select:eq(2)").attr("id",
								"dispTypeId" + i);

						// NAME
						$(this).find("input:text:eq(0)").attr("name", i);
						$(this).find("input:hidden:eq(0)").attr(
								"name",
								"advertisementDto.newAdvertDetDtos[" + i
										+ "].adhHrdDetId");
						$(this).find("select:eq(0)").attr(
								"name",
								"advertisementDto.newAdvertDetDtos[" + i
										+ "].adhTypeId1");
						$(this).find("select:eq(1)").attr(
								"name",
								"advertisementDto.newAdvertDetDtos[" + i
										+ "].adhTypeId2");
						/*
						 * $(this).find("select:eq(2)").attr("name",
						 * "advertisementDto.newAdvertDetDtos[" + i +
						 * "].adhTypeId3");
						 * $(this).find("select:eq(3)").attr("name",
						 * "advertisementDto.newAdvertDetDtos[" + i +
						 * "].adhTypeId4");
						 * $(this).find("select:eq(4)").attr("name",
						 * "advertisementDto.newAdvertDetDtos[" + i +
						 * "].adhTypeId5");
						 */

						$(this).find("textarea:eq(0)").attr(
								"name",
								"advertisementDto.newAdvertDetDtos[" + i
										+ "].advDetailsDesc");
						$(this).find("input:text:eq(1)").attr(
								"name",
								"advertisementDto.newAdvertDetDtos[" + i
										+ "].advDetailsHeight");
						$(this).find("input:text:eq(2)").attr(
								"name",
								"advertisementDto.newAdvertDetDtos[" + i
										+ "].advDetailsLength");
						$(this).find("input:text:eq(3)").attr(
								"name",
								"advertisementDto.newAdvertDetDtos[" + i
										+ "].advDetailsArea");
						$(this).find("input:text:eq(4)").attr(
								"name",
								"advertisementDto.newAdvertDetDtos[" + i
										+ "].unit");
						$(this).find("select:eq(2)").attr(
								"name",
								"advertisementDto.newAdvertDetDtos[" + i
										+ "].dispTypeId");
						idcount = i;
						
					});
}
// Defect #79106
function deleteEntry(obj, ids, id) {

	// get table row number
	var rowCount = $('#newAdvertisingTableId >tbody >tr').length;
	let errorList = [];
	if (rowCount == 1) {
		errorList.push(getLocalMessage("adh.first.row.not.remove"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	doDeletion(id);
	deleteTableRow('newAdvertisingTableId', obj, ids);
	reOrderAdvertisingIdSequence(".advertisingDetailsClass");
	$('#newAdvertisingTableId').DataTable().destroy();
	triggerTable();
}
$(function() {

	/* To add new Row into table */
	$("#newAdvertisingTableId").on('click', '.delButton', function() {

		if ($("#newAdvertisingTableId tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderAdvertisingIdSequence(".advertisingDetailsClass"); // reorder
																		// id
																		// and
																		// Path
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("adh.first.row.not.remove"));
			displayErrorsOnPage(errorList);
		}
	});
});

function calculateArea() {
	$('.advertisingDetailsClass').each(function(i) {

		var area = 0;
		var height = parseFloat($("#advDetailsHeight" + i).val());
		var length = parseFloat($("#advDetailsLength" + i).val());
		if (!isNaN(height) && !isNaN(length)) {
			area = +(height * length);
		}
		$("#advDetailsArea" + i).val(area.toFixed(2));
	});

}

function save(element) {
	var errorList = [];
	
	errorList = validateForm(errorList);
	errorList = validateAdvertDetails(errorList);
	 
	if (errorList.length == 0) {
		var locationId = $("#locId").val();
		if (locationId != null && locationId != '') {
			var requestData = {
				"locationId" : locationId
			}
			var ajaxResponse = __doAjaxRequest(
					'AdvertisementDataEntry.html?getLocationMapping', 'POST',
					requestData, false, 'json');
			if (ajaxResponse == "N") {
				errorList
						.push(getLocalMessage('adh.mapLoc.operationalWardZone'));
				displayErrorsOnPage(errorList);
				return false;
			} else {
				
				return saveOrUpdateForm(element,
						'Advertisement Data Entry saved successfully',
						'AdvertisementDataEntry.html', 'saveform');
			}
		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function editOrViewAdvDataEntry(adhId, mode) {

	var divName = '.content-page';
	var requestData = {
		"saveMode" : mode,
		"adhId" : adhId
	};
	var ajaxResponse = doAjaxLoading('AdvertisementDataEntry.html?EDIT',
			requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
function resetDataEntry(obj) {

	$('input[type=text]').val('');
	$('select').val('').trigger('chosen:updated');
	$(".alert-danger").hide();
	resetForm(obj);
}

function getAgencyDetails() {

	var advertiserCategoryId = $("#advertiseCategory").val();
	if (advertiserCategoryId != '' || advertiserCategoryId != null) {
		var requestData = {
			"advertiserCategoryId" : advertiserCategoryId
		};
		$('#agnId').html('');
		$('#agnId').append(
				$("<option></option>").attr("value", "0").text(
						getLocalMessage('selectdropdown')));
		var ajaxResponse = doAjaxLoading(
				'AdvertisementDataEntry.html?getAgencyName', requestData,
				'html');
		var prePopulate = JSON.parse(ajaxResponse);

		$.each(prePopulate, function(index, value) {
			$('#agnId').append(
					$("<option></option>").attr("value", value.agencyId).text(
							(value.agencyName)));
		});
		$('#agnId').trigger("chosen:updated");
	}
}

function getLicType() {
	 
	var errorList = [];  
    $("#licenseFromDate").datepicker('setDate', null);

	$("#licenseToDate").datepicker('setDate', null);
	    
	 $("#licenseToDate").datepicker("destroy");
	 $("#licenseFromDate").datepicker("destroy");
	 
	var licType = $("#licenseType").val();
	
	requestData = {
		"licType" : licType,
		
	};
	
	if(licType=="")
		{
		
		}
	else
	{   
		var response = doAjaxLoading('AdvertisementDataEntry.html?getLicenceType', requestData,'html');				
	
		//for getting calculate year type
		var yearType = doAjaxLoading('AdvertisementDataEntry.html?getCalculateYearType', requestData,'json');
		
		
		maxDays=response;
		
		if(response !="")
		{
		var licMaxTenureDays = response;
		 $("#licenseFromDate").datepicker({
	            dateFormat : 'dd/mm/yy',
	            changeMonth : true,
	            changeYear : true,
	            maxDate : 0,
	            onSelect : function(selected) {debugger;
	            var licenseFromDate = $("#licenseFromDate").val();
	            if(licenseFromDate ==""){
	            	
	            }
	            else{
	            requestData = {
	            		"licType" : licType,
	            		"licenseFromDate" : licenseFromDate,
	            	};
	            var response = doAjaxLoading('AdvertisementDataEntry.html?getLicenceType', requestData,'html');	
	            licMaxTenureDays = response;
	            }
	            var sdate = $(this).datepicker("getDate");
	            var cdd = 11-sdate.getMonth();
	            var fdd = 2-sdate.getMonth();
	            var mths = Math.floor(licMaxTenureDays/30);
	            var fmths = 0;
	            if(yearType=="C"){
	              if(cdd<mths){
	            	  sdate.setDate(sdate.getDate()+parseInt(licMaxTenureDays));
	                sdate.setMonth(11);sdate.setDate(31);
	              }else{
	            	sdate.setDate(sdate.getDate()+parseInt(licMaxTenureDays));
	              }
	            }else{
	            	/*if(fdd < 0 && (fdd >= -1 || fdd < -4)){
	            		licMaxTenureDays=parseInt(licMaxTenureDays)+parseInt(365);
	            	}*/
	            	if(fdd>=0){
	            		fmths = fdd;
	            	}else{
	            		fmths = 12+fdd;
	            	}
	            	if(mths>fmths){
	            		sdate.setDate(sdate.getDate()+parseInt(licMaxTenureDays));
	            		sdate.setMonth(2);sdate.setDate(31);
	            	}else{
	            		sdate.setDate(sdate.getDate()+parseInt(licMaxTenureDays));
	            	}	
	            	/*if(fdd < 0 && (fdd >= -1 || fdd < -4)){
	            		licMaxTenureDays=parseInt(licMaxTenureDays)-parseInt(365);
	            	}*/
	            }
	            $("#licenseToDate").datepicker("setDate",sdate);
	            $("#licenseToDate").datepicker("option", "minDate",selected);
	            $("#licenseToDate").datepicker("setDate",sdate);
	            $("#licenseToDate").datepicker("option", "maxDate",sdate);
	            $("#licenseToDate").datepicker("setDate",sdate);
	            }
	    
	        });
		
		$("#licenseToDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			minDate : '0',
			maxDate : +licMaxTenureDays,
		});
		}
		
		else
		{
		
		errorList
		.push("Date Range Is not Defined for License Type");

		displayErrorsOnPage(errorList);
	    }
	
    }
   }

function validateForm(errorList) {

	var errorList = [];
	var advertiserCat = $("#advertiseCategory").val();
	var licenseType = $("#licenseType").val();
	var locationType = $("#locCatType").val();
	var advertiserName = $("#agnId").val();
	var location = $("#locId").val();
	var propType = $("#propTypeId").val();
	var adhStatus = $("#adhStatus").val();
	// var dispTypeId = $("#dispTypeId0").val();
	var licFromDate = $("#licenseFromDate").val();
	var licToDate = $("#licenseToDate").val();

	var fromDate = moment(licFromDate, 'DD/MM/YYYY', true).isValid();// true
	var toDate = moment(licToDate, 'DD/MM/YYYY', true).isValid();//true
	/* if(fromDate == false){
	 	errorList.push(getLocalMessage('From date format should be in dd/mm/yyyy'));
	 }*/

	var licenseFromDate = moment(licFromDate, "DD.MM.YYYY HH.mm").toDate();
	var licenseToDate = moment(licToDate, "DD.MM.YYYY HH.mm").toDate();
	if (advertiserCat == 0) {
		errorList.push(getLocalMessage('adh.validate.advertiser.category'));
	}
	if (licenseType == 0) {
		errorList.push(getLocalMessage('adh.validate.licanse.type'));
	}
	if (licFromDate == "" || licFromDate == undefined || licFromDate == null) {
		errorList.push(getLocalMessage('adh.validate.license.from.date'));
	} else {
		if (fromDate == false) {
			errorList
					.push(getLocalMessage('adh.fromDate.format'));
		}
	}

	if (licToDate == "" || licToDate == undefined || licToDate == null) {
		errorList.push(getLocalMessage('adh.validate.license.to.date'));
	} else {
		if (toDate == false) {
			errorList
					.push(getLocalMessage('adh.toDate.format'));
		}
	}
	if ((licenseToDate.getTime()) < (licenseFromDate.getTime())) {
		errorList.push("License to date cannot be less than license from date");
	}
	if (locationType == 0) {
		errorList.push(getLocalMessage('adh.validate.location.type'));
	}
	if (advertiserName == 0) {
		errorList.push(getLocalMessage('adh.validate.advertiser.name'));
	}
	if (location == 0) {
		errorList.push(getLocalMessage('adh.validate.location'));
	}
	if (propType == 0 || propType == undefined) {
		errorList.push(getLocalMessage('adh.validate.property.type'));
	}
	if (adhStatus == 0) {
		errorList.push(getLocalMessage('adh.validate.license.status'));
	}
	/*if (dispTypeId == 0 || dispTypeId == undefined) {
		errorList.push(getLocalMessage('adh.validate.displaytypeid'));
	}*/
	return errorList;
}

function doDeletion(id) {
	requestData = {
		"id" : id
	};
	url = 'AdvertisementDataEntry.html?doDeletion';
	var row = $("#newAdvertisingTableId tbody .advertisingDetailsClass").length;
	if (row != 1) {
		var response = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	}

}

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) 
        month = '0' + month;
    if (day.length < 2) 
        day = '0' + day;

    return [year, month, day].join('-');
}
