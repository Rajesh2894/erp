function addRoutAndCollection(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getViewData(formUrl, actionParam, deId) {
	
	if (!actionParam) {
		actionParam = "edit";
	}
	var data = {
		"siteNumber" : deId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getEditData(formUrl, actionParam, deId) {
	if (!actionParam) {
		actionParam = "edit";
	}
	var data = {
		"siteNumber" : deId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function deleteData(formUrl, actionParam, deId) {
	
	if (actionParam == "delete") {
		showConfirmBoxForDelete(deId, actionParam);
	}
}
function showConfirmBoxForDelete(deId, actionParam) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger padding-5\">'
			+ getLocalMessage('Do you want to delete?') + '</h4>';
	message += '<div class=\'text-center padding-bottom-18\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + deId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}
function proceedForDelete(deId) {
	$.fancybox.close();
	var requestData = 'siteNumber=' + deId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('DisposalSiteMaster.html?' + 'delete',
			requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchRoutAndCollection(formUrl, actionParam) {
	
	if (!actionParam) {
		actionParam = "search";
	}
	var data = {
		"siteNumber" : $("#deId").val(),
		"siteName" : $("#deName").val()
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
function saveDisposalForm(element) {
	
	var errorList = [];
	errorList = validateForm(errorList);
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	} else if (errorList.length == 0) {
		return saveOrUpdateForm(element,getLocalMessage('scheme.master.creation.success'),'MRFMaster.html', 'saveform');
	}
}
function validateForm(errorList) {
	
	var deName = $("#deName").val();
	var status = $("#status1").val();
	var status1 = $("#status2").val();
	
	if (deName == "" || deName == null) {
		errorList.push(getLocalMessage("disposal.validation.name"));
	}
	var DisposalSiteArea = $("#DisposalSiteArea").val();
	if (DisposalSiteArea== "0" ||DisposalSiteArea=='') {
		errorList.push(getLocalMessage("disposal.validation.area"));
	}
	if (DisposalSiteArea < 0) {
		errorList.push(getLocalMessage("disposal.validation.areasize"));
	}
	var deCategory = $("#deCategory").val();
	if (deCategory == "0" || deCategory == '' || deCategory == null) {
		errorList
				.push(getLocalMessage("disposal.validation.category"));
	}
	var Capacity = $("#Capacity").val();
	if (Capacity == "" || Capacity == null || Capacity == "0" ) {
		errorList.push(getLocalMessage("disposal.validation.capacity"));
	}
	if (Capacity < 0) {
		errorList.push(getLocalMessage("disposal.validation.categorysize"));
	}
	
	var deCapacityUnit = $("#deCapacityUnit").val();
	if (deCapacityUnit == "0" || deCapacityUnit == null || deCapacityUnit == "") {
		errorList.push(getLocalMessage("disposal.validation.capacityunit"));
	}
	var Address = $("#Location").val();
	if (Address == "0" || Address == null || Address == "") {
		errorList.push(getLocalMessage("disposal.validation.location"));
	}
	return errorList;
}
function displayErrorsPage(errorList) {
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}

function backRouteMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'DisposalSiteMaster.html');
	$("#postMethodForm").submit();
}

$(document).ready(function() {
	var table = $('.dpsM').DataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"bPaginate" : true,
		"bFilter" : true,
		"ordering":  false,
	    "order": [[ 1, "desc" ]]
	});
});

function resetDisposal() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'DisposalSiteMaster.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}

function resetForm() {
	
	$('input[type=text]').val('');  
    $('#Location').val(''); 
    $('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
}

function getLatLong() {
	
	var geocoder = new google.maps.Geocoder();
	var con = $("#Location").val();
	var com = con;

	geocoder.geocode({
		'address' : com
	}, function(results, status) {

		if (status == google.maps.GeocoderStatus.OK) {
			
			var x = results[0].geometry.location.lat();
			var y = results[0].geometry.location.lng();
			var latlng = new google.maps.LatLng(x, y);

			$("#Lattitude").val(x);
			$("#Longitude").val(y);
		} else {
			res.innerHTML = getLocalMessage("disposal.validation.locationerror") + status;
		}

	});

}
function showMap(){
	
	data = {};
	var URL = 'DisposalSiteMaster.html?getMapData';
	var returnData = __doAjaxRequest(URL, 'POST', data, false, 'Json');

	mapList=returnData[0];
	mapdeatails=returnData[1];
	initMap2(mapList,mapdeatails);
	
}
function initMap2(mapList,mapdeatails){
	
	var locations = [mapList];
	var locDeatils= [mapdeatails];
	 var feature= [];
	                var map = new google.maps.Map(document.getElementById('map-canvas'), {
	                  zoom: parseInt(getLocalMessage("disposal.map.zoom")),
	                  center:  new google.maps.LatLng((getLocalMessage("disposal.lattitute.initialize")), parseFloat(getLocalMessage("disposal.Longitutde.initialize"))),
	                  mapTypeId: google.maps.MapTypeId.ROADMAP
	                });
	                var infowindow = new google.maps.InfoWindow();
	                var marker, i;	                

	                for (i = 0; i < locations[0].length; i++) {  
		                  marker = new google.maps.Marker({
		                    position: new google.maps.LatLng(locations[0][i][1], locations[0][i][2]),		                   
		                    map: map,
		                  });
		                  google.maps.event.addListener(marker, 'mouseover', (function(marker, i) {
		                    return function() {
		                   var contentString = '<div id="content" style="margin-left:5px;margin-top:3px;overflow:hidden;">'+ '<div id="bodyContent">'+'<img src="'+locations[0][i][7]+'" style="width:200px;height:200px;" alt="NO IMAGE FOUND"/>' +'<br><font style="color:darkblue;font:10px tahoma;margin-left:5px;">Waste Collection Centre </font>'+'<br><div style="font:10px verdana;color:darkgreen;margin-left:5px;">Site Name : ' + locations[0][i][6]+' <br>Category :'+ locations[0][i][0] +'<br>Area :'+locations[0][i][3]+' Acres<br>Capacity : '+locations[0][i][4]+' '+ locations[0][i][5]+ '<br>'+'</div>' + '</div>'+'</div>';
		                   infowindow.setContent(contentString);
		                      infowindow.open(map, marker);		                    
		                    }
		                  })(marker, i));
		                  google.maps.event.addListener(marker, 'mouseout', (function(marker, i) {
			                    return function() {				                    	
			                      this.setIcon('https://maps.gstatic.com/mapfiles/api-3/images/spotlight-poi2.png');
			                      infowindow.open(map, marker);		                    
			                    }
			                  })(marker, i));
		                  
		                  }
    }

function isNumberKey(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 46)
        return false;
    else {
        var len = document.getElementById("DisposalSiteArea").value.length;
        var index = document.getElementById("DisposalSiteArea").value.indexOf('.');
        if (index > 0 && charCode == 46) {
            return false;
        }
        if (index > 0) {
            var CharAfterdot = (len + 1) - index;
            if (CharAfterdot > 5) {
                return false;
            }
        }
    }
    return true;
 }
function isCapacityNumberKey(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 46)
        return false;
    else {
        var len = document.getElementById("Capacity").value.length;
        var index = document.getElementById("Capacity").value.indexOf('.');
        if (index > 0 && charCode == 46) {
            return false;
        }
        if (index > 0) {
            var CharAfterdot = (len + 1) - index;
            if (CharAfterdot > 5) {
                return false;
            }
        }
    }
    return true;
 }

$(function() {
	
	/* To add new Row into table */
	$("#unitDetailTable").on('click', '.addCF', function() {
		
			var content = $("#unitDetailTable").find('tr:eq(1)').clone();
			$("#unitDetailTable").append(content);
			content.find("input:text").val('');
			content.find("select").val('0');
			content.find("input:hidden:eq(0)").val('0');
			$('.error-div').hide();
			reOrderUnitTabIdSequence('.firstUnitRow'); 
	});
});
function reOrderUnitTabIdSequence(firstRow) {
	
	$(firstRow).each(
			function(i) {
				
				// IDs
				$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
				$(this).find("select:eq(0)").attr("id", "veId" + i);
				$(this).find("input:text:eq(1)").attr("id", "vechAvlId" + i);
				$(this).find("input:text:eq(2)").attr("id", "vechReqId" + i);
				// names
				$(this).find("input:text:eq(0)").val(i+1);
				$(this).find("select:eq(0)").attr("name","disposalMasterDTO[" + i+ "].veId");
				$(this).find("input:text:eq(1)").attr("name","disposalMasterDTO[" + i+ "].vechAvlCountId");
				$(this).find("input:text:eq(2)").attr("name","disposalMasterDTO[" + i+ "].vechReqCountId");
			});
}

$(function() {
	
	/* To add new Row into table */
	$("#empDetailTable").on('click', '.empAdd', function() {
		
			var content = $("#empDetailTable").find('tr:eq(1)').clone();
			$("#empDetailTable").append(content);
			content.find("input:text").val('');
			content.find("select").val('0');
			content.find("input:hidden:eq(0)").val('0');
			$('.error-div').hide();
			reOrderUnitTabIdSequence('.firstEmpRow'); 
	});
});
function reOrderUnitTabIdSequence(firstEmpRow) {
	
	$(firstEmpRow).each(
			function(i) {
				
				// IDs
				$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
				$(this).find("select:eq(0)").attr("id", "veId" + i);
				$(this).find("input:text:eq(1)").attr("id", "empAvlId" + i);
				$(this).find("input:text:eq(2)").attr("id", "empReqId" + i);
				// names
				$(this).find("input:text:eq(0)").val(i+1);
				$(this).find("select:eq(0)").attr("name","disposalMasterDTO[" + i+ "].veId");
				$(this).find("input:text:eq(1)").attr("name","disposalMasterDTO[" + i+ "].empAvlCountId");
				$(this).find("input:text:eq(2)").attr("name","disposalMasterDTO[" + i+ "].empReqCountId");
			});
}

function deleteEntry(obj, ids) {
	var totalWeight = 0;
	deleteTableRow('firstUnitRow', obj, ids);
	$('#unitDetailTable').DataTable().destroy();
	triggerTable();
}

function getProjectCode(){
	
	$('#vemId').html('');
	var requestUrl = "DisposalSiteMaster.html?projectCode";
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post',{}, false,'json');
	 $.each(ajaxResponse, function(index, value) {
		    $('#projectId').append($("<option>").attr("value",value.projectCode));
			});
}