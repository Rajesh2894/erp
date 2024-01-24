var ComplaintStatusUrl = "GrievanceComplaintStatus.html";
$(document)
		.ready(
				function() {
				$("#id_complaintList").dataTable({
					"searching":false,
					"oLanguage" : {
						"sSearch" : ""
					},
					"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
					"iDisplayLength" : 5,
					"bInfo" : true,
					"lengthChange" : true
				});

									
					$("#fromDate").datepicker(
							{
								dateFormat : 'dd/mm/yy',
								changeMonth : true,
								changeYear : true,
								onClose : function(selectedDate) {
									$("#toDate").datepicker("option", "minDate", selectedDate);
								}
							});

					$("#toDate").datepicker(
							{
								dateFormat : 'dd/mm/yy',
								changeMonth : true,
								changeYear : true,
								onClose : function(selectedDate) {
									$("#fromDate").datepicker("option", "maxDate", selectedDate);
								}
							}); 
		
                    $('#departmentComplaint')
							.change(function() {
										if ($('#departmentComplaint').val() > 0) {
											var requestData = {
												"deptId" : $(
														'#departmentComplaint')
														.val(),
												"orgId" : null
											}
											var response = __doAjaxRequest(
													'GrievanceComplaintStatus.html?grievanceComplaintTypes',
													'post', requestData, false,'html');

											if (response == "") {
												$('#id_complaintType_div')
														.html('');
												$('#id_complaintType_div')
														.hide();
											} else {
												$('#id_complaintType_div')
														.html(response);
												$('#id_complaintType_div')
														.show();
											}
										}
									});

                    
                    $(".reset").click(function() {
						$("#postMethodForm").prop('action', '');
						$("#postMethodForm").prop('action', ComplaintStatusUrl);
						$("#postMethodForm").submit();

					});
				});

function searchAndViewComplaint(){
	$("#errorDiv").hide();
	$("#id_complaintList").empty();
	fromDate = $("#fromDate").val();
	toDate= $("#toDate").val();	
	searchString = $(".searchString").val();	
	status=$("#status").val();
	
	var errorList=[];
	if(fromDate == "" && toDate == "" && status == "" && searchString == ""){
		errorList.push(getLocalMessage('check.status.search.criteria'));	
	}
	if(fromDate !== "" && toDate == ""){
		errorList.push(getLocalMessage('care.reports.select.toDate'));	
	}
	
	if(errorList.length>0){
		displayErrorsOnPage(errorList);
	}else{
		var divName = '.content-page';
		var requestData = {
				"fromDate":fromDate,
				"toDate":toDate,
				"searchString":searchString,
				"status":status							
		};
		var ajaxResponse = doAjaxLoading('GrievanceComplaintStatus.html?searchGrievance', requestData, 'html');	
	  $(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}


function viewComplaintDetails(searchString) {
	$("#errorDiv").hide();
	searchString = (searchString== null)?$(".searchString").val():searchString;
	var errorList=[];
	if(searchString == undefined || searchString == null || searchString == ""){
		errorList.push(getLocalMessage('care.validator.searchString'));	
	}
	if(errorList.length>0){
		displayErrorsOnPage(errorList);
	}else{
		var divName = '.content-page';
		var requestData = {
				"searchString":searchString,
		};
		var ajaxResponse = doAjaxLoading('GrievanceComplaintStatus.html?viewComplaintDetails', requestData, 'html',
				divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function printContent(el){
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}

function resetApplication(obj) {	
	$('input[type=text]').val('');
	$('select').val('0').trigger('chosen:updated');
	$(".alert-danger").hide();
	resetForm(obj);
}

function newComplaint(){
	
	$("#errorDiv").hide();
	searchString = $(".searchString").val();
	var errorList=[];
	if(searchString == undefined || searchString == null || searchString == ""){
		errorList.push(getLocalMessage('care.validator.mobile.no'));	
	}
	if(errorList.length>0){
		displayErrorsOnPage(errorList);
	}else{
		var divName = '.content-page';
		var requestData = {
				"searchString":searchString,
		};
		var ajaxResponse = doAjaxLoading('GrievanceDepartmentRegistration.html?getApplicationDetails', requestData, 'html',
				divName);
		if(ajaxResponse != undefined || ajaxResponse != null){
		$(divName).removeClass('ajaxloader');
			doAjaxLoading('GrievanceDepartmentRegistration.html', requestData, 'html',
				divName);
		}
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function searchAndViewDetails(){
	$("#errorDiv").hide();
	$("#id_complaintList").empty();
	fromDate = $("#fromDate").val();
	toDate= $("#toDate").val();	
	searchString = $(".searchString").val();	
	status=$("#status").val();
	
	var errorList=[];
	if(fromDate == "" && toDate == "" && status == "" && searchString == ""){
		errorList.push(getLocalMessage('check.status.search.criteria'));	
	}
	if(fromDate !== "" && toDate == ""){
		errorList.push(getLocalMessage('care.reports.select.toDate'));	
	}
	
	if(errorList.length>0){
		displayErrorsOnPage(errorList);
	}else{
		var divName = '.content-page';
		var requestData = {
				"fromDate":fromDate,
				"toDate":toDate,
				"searchString":searchString,
				"status":status							
		};
		var ajaxResponse = doAjaxLoading('OperatorDashboardView.html?searchDetail', requestData, 'html');	
	  $(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
	
}
