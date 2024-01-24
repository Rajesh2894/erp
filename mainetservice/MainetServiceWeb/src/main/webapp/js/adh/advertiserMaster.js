$(document).ready(function() {
    $('#advertiserMaster').validate({
	onkeyup : function(element) {
	    this.element(element);
	    console.log('onkeyup fired');
	},
	onfocusout : function(element) {
	    this.element(element);
	    console.log('onfocusout fired');
	}
    });
    
    /* Defect #79381 Financial Year Date Range -- Start 3:55 PM 6/23/2020 */
    var currentYear = new Date().getFullYear();
    $('#agencyLicFromDate').datepicker({
    	dateFormat : 'dd/mm/yy',
    	changeMonth : true,
    	changeYear : true,
    	defaultDate: new Date(currentYear, 3, 1),
    	maxDate : new Date((currentYear+1), 2, 31)
        });
    
    $('#agencyLicToDate').datepicker({
    	dateFormat : 'dd/mm/yy',
    	changeMonth : true,
    	changeYear : true,    	
    	minDate : new Date((currentYear), 3, 1),
    	maxDate : new Date((currentYear+1), 2, 31)
        });
    /*  Financial Year Date Range -- End 3:55 PM 6/23/2020 */
    
    $("#advertiserTable").dataTable({
	"oLanguage" : {
	    "sSearch" : ""
	},
	"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
	"iDisplayLength" : 5,
	"bInfo" : true,
	"lengthChange" : true
    });

    $('#advertiserMasterEntry').validate({
	onkeyup : function(element) {
	    this.element(element);
	    console.log('onkeyup fired');
	},
	onfocusout : function(element) {
	    this.element(element);
	    console.log('onfocusout fired');
	}
    });

});


$("#agencyLicIssueDate").keyup(function(e) {
    if (e.keyCode != 8) {
	if ($(this).val().length == 2) {
	    $(this).val($(this).val() + "/");
	} else if ($(this).val().length == 5) {
	    $(this).val($(this).val() + "/");
	}
    }
});

$("#agencyLicToDate").keyup(function(e) {
    if (e.keyCode != 8) {
	if ($(this).val().length == 2) {
	    $(this).val($(this).val() + "/");
	} else if ($(this).val().length == 5) {
	    $(this).val($(this).val() + "/");
	}
    }
});

/*$(function() {
    $( "#agencyLicToDate" ).datepicker({
      changeMonth: true,
      changeYear: true,
      yearRange: '1945:'+(new Date).getFullYear()         
    });
});
*/
$("#agencyLicFromDate").keyup(function(e) {
    if (e.keyCode != 8) {
	if ($(this).val().length == 2) {
	    $(this).val($(this).val() + "/");
	} else if ($(this).val().length == 5) {
	    $(this).val($(this).val() + "/");
	}
    }
});

function addAdvertiserMaster(formUrl, actionParam) {
debugger;
    var divName = '.content-page';
    var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
	    divName);
    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();
}

function save(element, saveMode) {
	debugger;
    var errorList = [];
    errorList = validateForm(errorList, saveMode)
    if (errorList.length == 0) {
	return saveOrUpdateForm(element,
		'Advertiser Master saved successfully',
		'AdvertiserMaster.html', 'saveform');
    } else {
	displayErrorsOnPage(errorList);
    }
}

function editadvertiserMaster(agencyId, mode) {
    var divName = '.content-page';
    var requestData = {
	"saveMode" : mode,
	"agencyId" : agencyId
    };
    var ajaxResponse = doAjaxLoading('AdvertiserMaster.html?EDIT', requestData,
	    'html', divName);
    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();
}

function searchAdvertiser() {
    var errorList = [];

    var agencyLicNo = $("#agencyLicNo").val();
    var agencyOldLicNo = $("#agencyOldLicNo").val();
    var agencyName = $("#agencyName").val();
    var agencyStatus = $("#agencyStatus").val();

    var requestData = {
	"advertiserNumber" : agencyLicNo,
	"advertiserOldNumber" : agencyOldLicNo,
	"advertiserName" : agencyName,
	"advertiserStatus" : agencyStatus
    };

    if (agencyLicNo != '' || agencyOldLicNo != '' || agencyName != ''
	    || agencyStatus != '') {
	var table = $('#advertiserTable').DataTable();
	table.rows().remove().draw();
	$(".warning-div").hide();
	var ajaxResponse = doAjaxLoading(
		'AdvertiserMaster.html?searchAdvertiserMaster', requestData,
		'html');
	var prePopulate = JSON.parse(ajaxResponse);
	var result = [];
	$
		.each(
			prePopulate,
			function(index) {
			    var obj = prePopulate[index];
			    result
				    .push([
					    obj.agencyLicNo,
					    obj.agencyOldLicNo,
					    obj.agencyRegisDate,
					    obj.agencyName,
					    obj.agencyStatus,
					    '<td >'
						    + '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5 margin-left-5"  onclick="editadvertiserMaster(\''
						    + obj.agencyId
						    + '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
						    + '<button type="button" class="btn btn-warning btn-sm margin-right-5 margin-left-5" onclick="editadvertiserMaster(\''
						    + obj.agencyId
						    + '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
						    + '<button type="button"  class="btn btn-primary hidden-print margin-right-5 margin-left-5"  onclick="printAgencyLicenseLetter(\''
						    + obj.agencyId
						    + '\')" title="Print Agency License Letter"><i class="fa fa-print"></i></button>' ]);

			});
	table.rows.add(result);
	table.draw();
	if (prePopulate.length == 0) {
	    errorList.push(getLocalMessage("adh.validate.search"));
	    $("#errorDiv").show();
	    displayErrorsOnPage(errorList);
	} else {
	    $("#errorDiv").hide();
	}
    } else {
	errorList
		.push(getLocalMessage("advertiser.master.validate.serach.button"));
	displayErrorsOnPage(errorList);
    }

}

function validateForm(errorList, saveMode) {
	debugger;
    var advertiserName = $("#agencyName").val();
    var advertiserAddress = $("#agencyAdd").val();
    var advertiserMobileNo = $("#agencyContactNo").val();
    var advertiserEmailId = $("#agencyEmail").val();
    var advertiserStatus = $("#agencyStatus").val();
    var advertiserOwner = $("#agencyOwner").val();
    var panNumber = $("#panNumber").val();
    var agencyLicIssueDate = $("#agencyLicIssueDate").val();
    var licFromDate = $("#agencyLicFromDate").val();
    var licToDate = $("#agencyLicToDate").val();
    var gstNo = $("#gstNo").val();

    var licenseFromDate = moment(licFromDate, "DD.MM.YYYY HH.mm").toDate();
    var licenseToDate = moment(licToDate, "DD.MM.YYYY HH.mm").toDate();
    var licenseIssueDate = moment(agencyLicIssueDate, "DD.MM.YYYY HH.mm")
	    .toDate();

    if (advertiserName == "" || advertiserName == undefined
	    || advertiserName == null) {
	errorList
		.push(getLocalMessage('advertiser.master.validate.advertiser.name'));
    }

    if (advertiserAddress == "" || advertiserAddress == undefined
	    || advertiserAddress == null) {
	errorList
		.push(getLocalMessage('advertiser.master.validate.advertiser.address'));
    }

    if (advertiserMobileNo == "" || advertiserMobileNo == undefined
	    || advertiserMobileNo == null) {
	errorList
		.push(getLocalMessage('advertiser.master.validate.advertiser.mobileNo'));
    } else {
	if (advertiserMobileNo.length < 10) {
	    errorList.push(getLocalMessage('adh.validate.mobile.number'));
	}
    }

    if (licFromDate == "" || licFromDate == undefined || licFromDate == null) {
	errorList
		.push(getLocalMessage('advertiser.master.validate.advertiser.licfromdate'));
    }

    if (licToDate == "" || licToDate == undefined || licToDate == null) {
	errorList
		.push(getLocalMessage('advertiser.master.validate.advertiser.lictodate'));
    }

    if (agencyLicIssueDate == "" || agencyLicIssueDate == undefined
	    || agencyLicIssueDate == null) {
	errorList
		.push(getLocalMessage('advertiser.master.validate.advertiser.issue.date'));
    }

    if ((licenseToDate.getTime()) < (licenseFromDate.getTime())) {
	errorList
		.push(getLocalMessage('advertiser.master.validate.lic.to.date.from.date'));
    }

    /*if ((licenseToDate.getTime()) < (licenseIssueDate.getTime())) {
	errorList
		.push(getLocalMessage('advertiser.master.validate.lic.to.date.issue.date'));
    }*/
    if ((licenseFromDate.getTime()) < (licenseIssueDate.getTime())) {
    	errorList
    		.push(getLocalMessage('advertiser.master.validate.lic.from.date.issue.date'));
        }

    if (advertiserEmailId == "" || advertiserEmailId == undefined
	    || advertiserEmailId == null) {
	errorList
		.push(getLocalMessage('advertiser.master.validate.advertiser.emailId'));
    } else {
	if (advertiserEmailId != "") {
	    var emailRegex = new RegExp(
		    /^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
	    var valid = emailRegex.test(advertiserEmailId);
	    if (!valid) {
		errorList.push(getLocalMessage('adh.validate.emailid'));
	    }
	}
    }

    if (advertiserOwner == "" || advertiserOwner == undefined
	    || advertiserOwner == null) {
	errorList
		.push(getLocalMessage('advertiser.master.validate.advertiser.owner'));
    }
    if (panNumber != "") {
	var panVal = $('#panNumber').val();
	var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;

	if (!regpan.test(panVal)) {
	    errorList.push(getLocalMessage('adh.validate.panNumber'));
	}
    }

    var reggst = /^([0-9]){2}([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}([0-9]){1}([a-zA-Z]){1}([0-9]){1}?$/;
    if (!reggst.test(gstNo) && gstNo != '') {
	errorList
		.push(getLocalMessage('adh.gstNo.notValid.format'));
    }

    return errorList;
}

function addHoardingRow() {
    var errorList = [];
    $("#errorDiv").hide();
    if (errorList.length == 0) {
	addTableRow('hoardingDetails');
	$('#hoardingDetails').DataTable();
    } else {
	$('#hoardingDetails').DataTable();
	displayErrorsOnPage(errorList);
    }
}

function deleteHoardingRow(obj, ids) {
    deleteTableRow('hoardingDetails', obj, ids);
    $('#hoardingDetails').DataTable().destroy();
    triggerTable();
}

function printAgencyLicenseLetter(agencyId) {
    
    var divName = '.content-page';
    var requestData = {
	"agencyId" : agencyId
    };

    var ajaxResponse = doAjaxLoading(
	    'AdvertiserMaster.html?printAgencyLicenseLetter', requestData,
	    'html', divName);

    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();
}