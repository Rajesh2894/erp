$(document).ready(function() {
	$("#LicCatSubCategry").hide();
	var deptShortCode = $("#deptShortCode").val();
	if (deptShortCode == 'ML') {
		$("#LicCatSubCategry").show();
	}
})

$(function() {
	$("#grid").jqGrid(
			{
				url : "ScrutinyLabels.html?getGridData",
				datatype : "json",
				mtype : "GET",
				colNames : [ getLocalMessage("master.serviceName"),
						getLocalMessage("event.serviceNameReg"),
						getLocalMessage("workflow.form.field.dep"),
						getLocalMessage("master.tbdeporgmap.DepartmentName"),
						getLocalMessage("master.tbdeporgmap.category"),
						getLocalMessage("master.grid.column.action") ],
				colModel : [ {
					name : "smServiceName",
					sortable : true
				}, {
					name : "smServiceNameMar",
					sortable : true
				}, {
					name : "departmentName",
					sortable : true
				}, {
					name : "departmentNameMar",
					sortable : true
				}, {
					name : "triDesc",
					sortable : true
				}, {
					name : 'smServiceId',
					index : 'smServiceId',
					width : 50,
					align : 'center !important',
					sortable : false,
					formatter : actionFormatter,
					search : false
				}
				// { name: 'smServiceId', index: 'smServiceId', align: 'center',
				// width:30, sortable: false,formatter:editScrutinyMst},
				// { name: 'smServiceId', index: 'smServiceId', align: 'center',
				// width:30, sortable: false,formatter:viewScrutinyMst}
				],
				pager : "#pagered",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "cpmId",
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
				caption : getLocalMessage("common.master.security.seldept")
			});
	jQuery("#grid").jqGrid('navGrid', '#pagered', {
		edit : false,
		add : false,
		del : false,
		refresh : false
	});
	$("#pagered_left").css("width", "");
});

function actionFormatter(cellvalue, options, rowObject) {

	return "<a class='btn btn-blue-3 btn-sm viewClass' value='"
			+ rowObject.smServiceId+","+rowObject.triCod1
			+ "'  title='View' ><i class='fa fa-eye' aria-hidden='true'></i></a> "
			+ "<a class='btn btn-warning btn-sm editClass '  value='"
			+ rowObject.smServiceId+","+rowObject.triCod1
			+ "' title='Edit' ><i class='fa fa-pencil' aria-hidden='true'></i></a> ";
}

$(function() {
	$(document)
			.on(
					'click',
					'.editClass',
					function() {
						
						var url = "ScrutinyLabels.html?formForUpdate";
						// US#113590 start
						var scrutinyId = $(this).attr('value');
						var scrutArray=scrutinyId.split(',');
						var scrutinyId =scrutArray[0];
						var triCod1="0";
						var arrlength=scrutArray.length;
						if(arrlength>1){
							triCod1=scrutArray[1];
							if(triCod1=="null" || triCod1==undefined ||triCod1==""||triCod1==null){
								triCod1="0";
							}
						}
						//end
						var returnData = {
							"smServiceId" : scrutinyId,
							"triCod1" : triCod1
						};

						$
								.ajax({
									url : url,
									datatype : "json",
									mtype : "POST",
									data : returnData,
									success : function(response) {
										var divName = '.form-div';
										$("#content").html(response);
										$("#content").show();
									},
									error : function(xhr, ajaxOptions,
											thrownError) {
										var errorList = [];
										errorList
												.push(getLocalMessage("admin.login.internal.server.error"));
										showError(errorList);
									}
								});
					});
});

$(function() {
	$(document)
			.on(
					'click',
					'.viewClass',
					function() {
						var url = "ScrutinyLabels.html?formForView";
						// US#113590 start
						var scrutinyId = $(this).attr('value');
						var scrutArray=scrutinyId.split(',');
						var scrutinyId =scrutArray[0];
						var triCod1="0";
						var arrlength=scrutArray.length;
						if(arrlength>1){
							triCod1=scrutArray[1];
							if(triCod1=="null" || triCod1==undefined ||triCod1==""||triCod1==null){
								triCod1="0";
							}
						}
						//end
						var returnData = {
							"smServiceId" : scrutinyId,
							"triCod1" : triCod1
						};

						$
								.ajax({
									url : url,
									datatype : "json",
									mtype : "POST",
									data : returnData,
									success : function(response) {
										var divName = '.form-div';
										$("#content").html(response);
										$("#content").show();
									},
									error : function(xhr, ajaxOptions,
											thrownError) {
										var errorList = [];
										errorList
												.push(getLocalMessage("admin.login.internal.server.error"));
										showError(errorList);
									}
								});
					});
});


function openScrutinyAddForm() {
	var url = "ScrutinyLabels.html?form";
	$
			.ajax({
				url : url,
				success : function(response) {

					var divName = '.form-div';
					$("#content").html(response);
					$("#content").show();
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList
							.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});
}

$("#scrutinyLabelTbl")
		.on(
				"click",
				'.addScrutinyLabel',
				function(e) {

					var errorList = [];
					$('.appendableClass tbody')
							.each(
									function(i) {
										row = i;

										// to validate Rows
										// var slPosition = $("#slPosition" +
										// i).val();
										var levels = $("#levels" + i).val();
										var gmId = $("#gmId" + i).val();
										var slLabel = $("#slLabel" + i).val();
										var slLabelMar = $("#slLabelMar" + i)
												.val();
										var slDatatype = $("#slDatatype" + i)
												.val();
										var slPreValidation = $(
												"#slPreValidation" + i).val();
										var slFormName = $("#slFormName" + i)
												.val();
										var slFormMode = $("#slFormMode" + i)
												.val();
										var slValidationText = $(
												"#slValidationText" + i).val();

										if (levels == '') {
											errorList
													.push(getLocalMessage("common.master.scrutiny.levelnotempty"));
										}
										if (gmId == '') {
											errorList
													.push(getLocalMessage("common.master.scrutiny.selRole"));
										}
										if (slFormMode == '') {
											errorList
													.push(getLocalMessage("common.master.scrutiny.modeNotEmpty"));
										}
										if (slDatatype == '') {
											errorList
													.push(getLocalMessage("common.master.scrutiny.selDataType"));
										}
										if (slLabel == '') {
											errorList
													.push(getLocalMessage("common.master.scrutiny.levelNotEmpty"));
										}
										if (slLabelMar == '') {
											errorList
													.push(getLocalMessage("common.master.scrutiny.regLevelNotEmpty"));
										}

										/*
										 * if(slPreValidation == '') {
										 * errorList.push("Validation must not
										 * empty"); } if(slFormName == '') {
										 * errorList.push("Form must not
										 * empty"); }
										 */

										/*
										 * if(slValidationText == '') {
										 * errorList.push("Text must not
										 * empty"); }
										 */

									});

					if (errorList.length > 0) {

						var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

						$
								.each(
										errorList,
										function(index) {
											errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
													+ errorList[index]
													+ '</li>';
										});

						errMsg += '</ul>';
						$("#errorDivScrutiny").html(errMsg);
						$("#errorDivScrutiny").removeClass('hide')
						$('html,body').animate({
							scrollTop : 0
						}, 'slow');

						errorList = [];
						return false;
					}

					e.preventDefault();

					$(".datepicker").datepicker("destroy");
					var content = $(this).closest('#scrutinyLabelTbl tbody')
							.clone();
					$(this).closest("#scrutinyLabelTbl tbody").after(content);

					// for reset all value
					content.find("select").val("");
					content.find("input:text").val("");
					content.find("input:hidden").val('');

					// for generating dynamic Id
					content.find("select:eq(0)").attr("id", "gmId" + (row + 1));
					content.find("select:eq(1)").attr("id",
							"slDatatype" + (row + 1));
					content.find("select:eq(2)").attr("id",
							"slValidationText" + (row + 1));
					content.find("input:text:eq(0)").attr("id",
							"slPosition" + (row + 1));
					content.find("input:text:eq(1)").attr("id",
							"levels" + (row + 1));
					content.find("input:text:eq(2)").attr("id",
							"slFormName" + (row + 1));
					content.find("input:text:eq(3)").attr("id",
							"slFormMode" + (row + 1));
					content.find("input:text:eq(4)").attr("id",
							"slLabel" + (row + 1));
					content.find("input:text:eq(5)").attr("id",
							"slLabelMar" + (row + 1));
					content.find("input:text:eq(6)").attr("id",
							"slPreValidation" + (row + 1));
					content.find("input:hidden:eq(0)").attr("id",
							"slLabelId" + (row + 1));

					content.find('.delButton').attr("id",
							"delButton" + (row + 1));
					content.find('.addButton').attr("id",
							"addButton" + (row + 1));

					// for generating dynamic path
					content.find("select:eq(0)").attr("name",
							"scrutinyLabelsList[" + (row + 1) + "].gmId");
					content.find("select:eq(1)").attr("name",
							"scrutinyLabelsList[" + (row + 1) + "].slDatatype");
					content.find("select:eq(2)").attr(
							"name",
							"scrutinyLabelsList[" + (row + 1)
									+ "].slValidationText");
					content.find("input:text:eq(0)").attr("name",
							"scrutinyLabelsList[" + (row + 1) + "].slPosition");
					content.find("input:text:eq(1)").attr("name",
							"scrutinyLabelsList[" + (row + 1) + "].levels");
					content.find("input:text:eq(2)").attr("name",
							"scrutinyLabelsList[" + (row + 1) + "].slFormName");
					content.find("input:text:eq(3)").attr("name",
							"scrutinyLabelsList[" + (row + 1) + "].slFormMode");
					content.find("input:text:eq(4)").attr("name",
							"scrutinyLabelsList[" + (row + 1) + "].slLabel");
					content.find("input:text:eq(5)").attr("name",
							"scrutinyLabelsList[" + (row + 1) + "].slLabelMar");
					content.find("input:text:eq(6)").attr(
							"name",
							"scrutinyLabelsList[" + (row + 1)
									+ "].slPreValidation");
					content.find("input:hidden:eq(0)").attr("name",
							"scrutinyLabelsList[" + (row + 1) + "].slLabelId")
							.val("");

					// to add date picker on dynamically created Date fields
					$(".datepicker").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true
					});
					reOrderTableIdSequence();
				});

// to delete row
$("#scrutinyLabelTbl").on("click", '.deleteScrutinyLabel', function(e) {

	var rowCount = $('#scrutinyLabelTbl tbody').length;
	if (rowCount <= 1) {
		return false;
	}
	$(this).closest('#scrutinyLabelTbl tbody').remove();
	reOrderTableIdSequence();
	e.preventDefault();
});

// to re-order Id's on delete/Add
function reOrderTableIdSequence() {

	$('.appendableClass tbody')
			.each(
					function(i) {

						$(".datepicker").datepicker("destroy");

						$(this).find("select:eq(0)").attr("id", "gmId" + i);
						$(this).find("select:eq(1)").attr("id",
								"slDatatype" + i);
						$(this).find("select:eq(2)").attr("id",
								"slValidationText" + i);
						$(this).find("input:text:eq(0)").attr("id",
								"slPosition" + i);
						$(this).find("input:text:eq(1)").attr("id",
								"levels" + i);
						$(this).find("input:text:eq(2)").attr("id",
								"slFormName" + i);
						$(this).find("input:text:eq(3)").attr("id",
								"slFormMode" + i);
						$(this).find("input:text:eq(4)").attr("id",
								"slLabel" + i).attr('onblur',
								'validateLabel(this,' + i + ')');
						$(this).find("input:text:eq(5)").attr("id",
								"slLabelMar" + i);
						$(this).find("input:text:eq(6)").attr("id",
								"slPreValidation" + i);
						$(this).find("input:hidden:eq(0)").attr("id",
								"slLabelId" + i);

						$(this).parents('tr').find('.delButton').attr("id",
								"delButton" + i);
						$(this).parents('tr').find('.addButton').attr("id",
								"addButton" + i);

						$(this).find("select:eq(0)").attr("name",
								"scrutinyLabelsList[" + i + "].gmId");
						$(this).find("select:eq(1)").attr("name",
								"scrutinyLabelsList[" + i + "].slDatatype");
						$(this).find("select:eq(2)").attr(
								"name",
								"scrutinyLabelsList[" + i
										+ "].slValidationText");
						$(this).find("input:text:eq(0)").attr("name",
								"scrutinyLabelsList[" + i + "].slPosition")
								.val(i + 1);
						$(this).find("input:text:eq(1)").attr("name",
								"scrutinyLabelsList[" + i + "].levels");
						$(this).find("input:text:eq(2)").attr("name",
								"scrutinyLabelsList[" + i + "].slFormName");
						$(this).find("input:text:eq(3)").attr("name",
								"scrutinyLabelsList[" + i + "].slFormMode");
						$(this).find("input:text:eq(4)").attr("name",
								"scrutinyLabelsList[" + i + "].slLabel");
						$(this).find("input:text:eq(5)").attr("name",
								"scrutinyLabelsList[" + i + "].slLabelMar");
						$(this).find("input:text:eq(6)")
								.attr(
										"name",
										"scrutinyLabelsList[" + i
												+ "].slPreValidation");
						$(this).find("input:hidden:eq(0)").attr("name",
								"scrutinyLabelsList[" + i + "].slLabelId");

						$(".datepicker").datepicker({
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true
						});
					});
}
function submitScrutinyLabelForm(obj) {
	
	var formName = findClosestElementId(obj, 'form');
	$('#triCodeList1').prop("disabled",false);
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);

	var url = $(theForm).attr('action');
	var returnData = __doAjaxRequestForSave(url, 'post', requestData, false,
			'', obj);

	if ($.isPlainObject(returnData)) {
		showConfirmBox();
	} else {

		$("#widget").html(returnData);
		$("#widget").show();
		$(".warning-div").removeClass("hide");
		return false;
	}

	/*
	 * $("#widget").html(returnData); $("#widget").show();
	 * $(".warning-div").removeClass("hide");
	 * 
	 * return false;
	 */
}

function showConfirmBox() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';

	message += '<p>Form Submitted Successfully</p>';
	message += '<p style=\'text-align:center;margin: 5px;\'>'
			+ '<br/><input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'css_btn \'    '
			+ ' onclick="proceed()"/>' + '</p>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}

function proceed() {
	window.location.href = 'ScrutinyLabels.html';
}

function closeErrBox() {
	$('.warning-div').addClass('hide');
}

function validateLabel(obj, curIndex) {
	// T#110976
	if ($('#kdmcEnv').val() != "Y" || $('#psclEnv').val() != "Y") {
		$('.appendableClass tbody')
				.each(
						function(i) {
							if (curIndex != i
									&& $("#slLabel" + i).val() == $(
											"#slLabel" + curIndex).val()) {
								var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

								errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;Duplicate Label is not allowed</li></ul>';
								$("#errorDivScrutiny").html(errMsg);
								$("#errorDivScrutiny").removeClass('hide')
								$("#slLabel" + curIndex).val('');
								$('html,body').animate({
									scrollTop : 0
								}, 'slow');

								return false;
							}
						});
	}
}

function searchScrutinyData(Obj) {
	
	var scrutinyId = $("#serviceId").val();
	var deptId = $("#depId").val();
	// US#113590
	var triCod1 = $("#triCodeList1").val();
	if (triCod1 == undefined || triCod1 == null || triCod1 == "") {
		triCod1 = "0";
	}
	// US#113590 end
	var url = "ScrutinyLabels.html?searchData";
	$
			.ajax({
				url : url,
				datatype : "json",
				mtype : "POST",
				data : {
					"scrutinyId" : scrutinyId,
					"deptId" : deptId,
					"triCod1" : triCod1
				},
				success : function(response) {
					$("#grid").jqGrid('setGridParam', {
						datatype : 'json'
					}).trigger('reloadGrid');
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList
							.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});
}

function refreshService(obj) {
	$("#LicCatSubCategry").hide();
	var deptId = $("#depId").val();
	var url = "ScrutinyLabels.html?refreshService";
	$
			.ajax({
				url : url,
				datatype : "json",
				mtype : "POST",
				data : {
					"deptId" : deptId
				},
				success : function(response) {
					$('#service-div').show();
					$("#serviceId").html('');
					$("#serviceId").append(
							$("<option></option>").attr("value", "").text(
									getLocalMessage('Select')));
					var errorList = [];

					if ($.isEmptyObject(response)) {
						$('#service-div').hide();

						errorList.push(getLocalMessage("master.no.service.found"));
						showScrutinyError(errorList);
					} else {
						$("#errorDivScrutiny").hide();
						$("#errorDivScrutiny").html("");

						$.each(response, function(index, value) {
							$("#serviceId").append(
									$("<option></option>").attr("value",
											value.smServiceId).text(
											value.smServiceName));
						});
					}
					$(".chosen-select-no-results").trigger("chosen:updated");

				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList
							.push(getLocalMessage("admin.login.internal.server.error"));
					showScrutinyError(errorList);
				}
			});

}

function showScrutinyError(errorList) {
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDivScrutiny").html(errMsg);
	$("#errorDivScrutiny").show();
	$("html, body").animate({
		scrollTop : 0
	}, "slow");
}
function showCategoryAndSubCategory() {
	
	var serviceId = $("#scrutinyId").val();
	if (serviceId == "" || serviceId == undefined || serviceId == null) {
		serviceId = $("#serviceId").val();
	}

	var requestData = {
		"scrutinyId" : serviceId
	};
	if (serviceId != null && serviceId != undefined && serviceId != "") {
		var ajaxResponse = doAjaxLoading(
				'ScrutinyLabels.html?getServiceShortCode', requestData, 'html');
		var prePopulate = JSON.parse(ajaxResponse);
		if (prePopulate == 'ML') {

			$("#LicCatSubCategry").show();
		} else {
			$("#LicCatSubCategry").hide();
		}
	}
}