/**
 * @author: Harsha Ramachandran
 */

$(document).ready(function() {
	$("#createData").hide();
	$("#orgSearch").hide();
	$("#search").hide();
	$("#reset").hide();
	var defaultStatus = $("#default").val();
	if(defaultStatus == 'Y'){
		$("#createData").show();
		$("#orgSearch").show();
		$("#search").show();
		$("#reset").show();
	}
});
	
	var selectedIds = new Array();
	var orgid = '';
	$(function() {

		$("#grid").jqGrid(
				{
					url : "Organisation.html?getGridData",
					datatype : "json",
					mtype : "GET",
					colNames : [ getLocalMessage('tbOrganisation.id'), getLocalMessage('tbOrganisation.oNlsOrgname'),
					             getLocalMessage('tbOrganisation.oNlsOrgnameMar'),getLocalMessage('tbOrganisation.parentOrg') ,getLocalMessage('master.grid.column.action') ],
					colModel : [
					{
						name : "ulbOrgID",
						width : 60,
						sortable : true,
						sorttype: 'number',
						search:true
					}, {
						name : "onlsOrgname",
						width : 120,
						sortable : true,
						search:true
					}, {
						name : "onlsOrgnameMar",
						width : 120,
						sortable : true,
						search:true
					},{
						name : "defaultStatus",
						width : 50,
						sortable : false,
						align: 'center !important',
						search:false
					}, { 
						name: 'orgid', 
						index: 'orgid', 
						width: 35, 
						align: 'center !important', 
						sortable: false,
						search : false,
						formatter:actionFormatter
						}
					 ],
					pager : "#pagered",
					rowNum : 10,
					rowList : [ 5, 10, 20, 30 ],
					sortname : "orgid",
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
					editurl : "Organisation.html?update",
					caption : "Organisation List",
					ignoreCase: true
				});
		jQuery("#grid").jqGrid('navGrid', '#pagered', {edit : false,add : false,del : false,refresh : true});
		$("#pagered_left").css("width", "");
	});

    function actionFormatter(cellvalue, options, rowObject){
		return "<a class='btn btn-warning btn-sm' title='Edit' onclick=\"editOrg('"+rowObject.orgid+"')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> "
	}
    
    function editOrg(orgId){
    	var gr = jQuery("#grid").jqGrid('getGridParam',
		'selrow');
    	if (orgId != '') {
    			var url = "Organisation.html?formForUpdate";
    			var requestData = "orgid=" + orgId;
    			var returnData =__doAjaxRequest(url,'POST',requestData, false,'html');
    			var divName = '.widget';
				$("#custBankDiv")
						.html(returnData);
				$("#custBankDiv").show();
	
    	}
    }


	$(function() {

		$("#createData")
				.click(
						function() {
							$('.error-div').hide();
							var url = "Organisation.html?form";
							var returnData = "";

							$
									.ajax({
										url : url,
										success : function(response) {
			
											var divName = '.widget';
											$("#custBankDiv").html(response);
											$("#custBankDiv").show();
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

	function showConfirmBox() {
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = 'Yes';

		message += '<p class="padding-top-10">Are you sure want to delete ?</p>';
		message += '<p style=\'text-align:center;margin: 5px;\'>'
				+ '<br/><input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
				+ ' onclick="deleteData()"/>' + '</p>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	}

	function deleteData() {

		var url = "Organisation.html?delete";

		var returnData = "orgid=" + orgid;

		$
				.ajax({
					url : url,
					data : returnData,
					success : function(response) {
						if (response == 'success') {
							$.fancybox.close();
							$("#grid").jqGrid('setGridParam', {
								datatype : 'json'
							}).trigger('reloadGrid');
						}
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList
								.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});

	}

	function closeOutErrBox() {
		$('.error-div').addClass('hide');
	}
	
	function searchOrganisation(){
		var errorList = [];
		$("#errorDivOrgMas").hide();
				var url = "Organisation.html?searchOrganisation";
				var requestdata = {
						"orgid" : $("#orgId").val()
				}
				$.ajax({
					url : url,
					data:requestdata,
					success : function(response) {
						$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});
	}
	
	function orgReset(){
		window.location.href = 'Organisation.html'; 	
	}
	