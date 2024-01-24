var startPArray = [];

/**
 * Add Route Form
 * 
 * @param formUrl
 * @param actionParam
 * @returns
 */
function addRouteForm(formUrl, actionParam) {
    var divName = '.content-page';
    var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
	    divName);
    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();
    $("#beatComCount").val(0);
    $("#beatResCount").val(0);
    $("#beatIndCount").val(0);
    $("#decompHouse").val(0);
    $("#animalCount").val(0);

}

/**
 * Get Route master Data
 * 
 * @param formUrl
 * @param actionParam
 * @param roId
 * @returns
 */
function getRoutemasterData(formUrl, actionParam, roId) {
    var data = {
	"roId" : roId
    };
    var divName = '.content-page';
    var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
	    divName);
    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();
    houseHoldCount();
}
/**
 * Back Route Master Form
 * 
 * @returns
 */
function backRouteMasterForm() {
    $("#postMethodForm").prop('action', '');
    $("#postMethodForm").prop('action', 'BeatMaster.html');
    $("#postMethodForm").submit();
}
$(document)
	.ready(
		function() {
		    var table = $('.rcm').DataTable(
			    {
				"oLanguage" : {
				    "sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ],
					[ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 5,
				"bInfo" : true,
				"lengthChange" : true,
				"bPaginate" : true,
				"bFilter" : true,
				"ordering" : false,
				"order" : [ [ 1, "desc" ] ]
			    });
		    $('input#totalDistanceId')
			    .keypress(
				    function(e) {
					var errorList = [];
					var charCode = (e.which) ? e.which
						: e.keyCode;
					if (charCode != 46
						&& charCode > 31
						&& (charCode < 48 || charCode > 57)) {
					    errorList
						    .push(getLocalMessage("population.validation.popYearsize"));
					    return false;
					}
				    });
		    $('input#DistancesiteId')
			    .keypress(
				    function(e) {
					var errorList = [];
					var charCode = (e.which) ? e.which
						: e.keyCode;
					if (charCode != 46
						&& charCode > 31
						&& (charCode < 48 || charCode > 57)) {
					    errorList
						    .push(getLocalMessage("population.validation.popYearsize"));
					    return false;
					}
				    });

		});

/**
 * Search Route Master
 * 
 * @returns
 */
function searchRouteMaster() {
    var data = {
	"routeNo" : $('#RouteNo').val(),
	"routeName" : $('#RouteName').val(),
    };
    var divName = '.content-page';
    var ajaxResponse = doAjaxLoading('BeatMaster.html?searchRouteMaster', data,
	    'html', divName);
    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();
}

/**
 * Reset Route Master Form
 * 
 * @returns
 */
function resetRouteMaster() {
    $("#postMethodForm").prop('action', '');
    $("#postMethodForm").prop('action', 'BeatMaster.html');
    $("#postMethodForm").submit();
    $('.error-div').hide();
}

/**
 * validate Search Form
 * 
 * @param errorList
 * @returns
 */
function validateSearchForm(errorList) {
    
    var RouteNo = $("#RouteNo").val();
    if (RouteNo == "" || RouteNo == null) {
	errorList.push(getLocalMessage("swm.validation.route"));
    }
    var RouteName = $("#RouteName").val();
    if (RouteName == "" || RouteName == null) {
	errorList.push(getLocalMessage("swm.validation.routename"));
    }
    return errorList;
}

/**
 * Delete Routemaster Data
 * 
 * @param formUrl
 * @param actionParam
 * @param roId
 * @returns
 */
function deleteRoutemasterData(formUrl, actionParam, roId) {
    if (actionParam == "deleteRouteMaster") {
	showConfirmBoxForDelete(roId, actionParam);
    }
}

/**
 * Show Confirm BoxFor Delete
 * 
 * @param roId
 * @param actionParam
 * @returns
 */

function showConfirmBoxForDelete(roId, actionParam) {
    var errMsgDiv = '.msg-dialog-box';
    var message = '';
    var cls = 'Proceed';
    message += '<h4 class=\"text-center text-danger padding-5\">'
	    + getLocalMessage('Do you want to delete?') + '</h4>';
    message += '<div class=\'text-center padding-bottom-18\'>'
	    + '<input type=\'button\' value=\'' + cls
	    + '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
	    + ' onclick="proceedForDelete(' + roId + ')"/>' + '</div>';
    $(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
    $(errMsgDiv).html(message);
    $(errMsgDiv).show();
    $('#btnNo').focus();
    showModalBox(errMsgDiv);
    return false;
}

/**
 * Proceed For Delete
 * 
 * @param roId
 * @returns
 */
function proceedForDelete(roId) {
    $.fancybox.close();
    var requestData = 'roId=' + roId;
    var divName = '.content-page';
    var ajaxResponse = doAjaxLoading('BeatMaster.html?' + 'deleteRouteMaster',
	    requestData, 'html');
    $('.content').removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();
}

/**
 * Save Route Master Form
 * 
 * @param element
 * @returns
 */
function saveRouteMasterForm(element) {
    
    var errorList = [];
    errorList = validateForm(errorList);
    errorList = errorList.concat(validateAreaDetails());
    if (errorList.length > 0) {
	displayErrorsOnPage(errorList);
    } else if (errorList.length == 0) {
	return saveOrUpdateForm(element,
		getLocalMessage('swm.save.beatMaster.success'),
		'BeatMaster.html', 'saveform');
    }
}

/**
 * validateForm
 * 
 * @param errorList
 * @returns
 */

function validateForm(errorList) {
    

    var RouteName = $("#RouteName").val();
    if (RouteName == "" || RouteName == null) {
	errorList.push(getLocalMessage("swm.validation.routename"));
    }
    var nrDisName = $("#nrDisName").val();
    if (nrDisName == "" || nrDisName == null) {
	errorList.push(getLocalMessage("swm.validation.disposalsitenearest"));
    }
    /*
     * var areaType = $("#areaType").val(); if (areaType == "0" || areaType ==
     * null) { errorList.push(getLocalMessage("swm.validation.collectionType")); }
     */
    /*
     * var collCount = $("#collCount").val(); if (collCount == "" || collCount ==
     * null) {
     * errorList.push(getLocalMessage("swm.validation.collectionCount")); }
     */
    var totalDistanceId = $("#totalDistanceId").val();
    if (totalDistanceId != null) {
	if (totalDistanceId < 0) {
	    errorList.push(getLocalMessage("swm.validation.totaldistancesize"));
	}
    }
    /*
     * var dryAssQty = $("#dryAssQty").val(); if (dryAssQty == "" || dryAssQty ==
     * null) {
     * errorList.push(getLocalMessage("swm.validation.dryAssumeQuantity")); }
     * var wetAssQty = $("#wetAssQty").val(); if (wetAssQty == "" || wetAssQty ==
     * null) {
     * errorList.push(getLocalMessage("swm.validation.wetAssumeQuantity")); }
     * var hazAssQty = $("#hazAssQty").val(); if (hazAssQty == "" || hazAssQty ==
     * null) {
     * errorList.push(getLocalMessage("swm.validation.hazardousAssumeQuantity")); }
     */
    var DistancesiteId = $("#DistancesiteId").val();
    if (DistancesiteId != null) {
	if (DistancesiteId < 0) {
	    errorList
		    .push(getLocalMessage("swm.validation.DistancesiteIdsize"));
	}
    }
    return errorList;
}

/**
 * Display Errors Page
 * 
 * @param errorList
 * @returns
 */
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

/**
 * Get Lat Long
 * 
 * @param i
 * @returns
 */
function getLatLong(i) {
    var geocoder = new google.maps.Geocoder();
    var con = $("#colladd" + i).val();
    var com = con;
    geocoder
	    .geocode(
		    {
			'address' : com
		    },
		    function(results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
			    var x = results[0].geometry.location.lat();
			    var y = results[0].geometry.location.lng();
			    var latlng = new google.maps.LatLng(x, y);
			    $("#collLatitude" + i).val(x);
			    $("#collLongitude" + i).val(y);
			} else {
			    res.innerHTML = getLocalMessage("disposal.validation.locationerror")
				    + status;
			}
		    });
}

/**
 * Reset Form
 * 
 * @returns
 */
function resetForm() {
    $("#postMethodForm").prop('action', '');
    $("select").val("").trigger("chosen:updated");
    $('.error-div').hide();
    prepareTags();
}

/**
 * init Beat
 * 
 * @param mapList
 * @returns
 */

var map;
var colors = [];
function initBeat(mapList) {
    requestArray = [];
    for ( var route in mapList) {
	var waypts = [];
	var start, finish
	var lastpoint
	data = mapList[route]
	console.log(data);
	limit = data.length
	for (var waypoint = 0; waypoint < limit; waypoint++) {
	    if (data[waypoint] === lastpoint) {
		continue;
	    }
	    lastpoint = data[waypoint]
	    waypts.push({
		location : data[waypoint],
		stopover : true
	    });
	}
	start = (waypts.shift()).location;
	finish = waypts.pop();
	if (finish === undefined) {
	    finish = start;
	} else {
	    finish = finish.location;
	}
	var startArray = start.split(",");
	var finishArray = finish.split(",");
	var request = {
	    origin : {
		lat : parseFloat(startArray[0]),
		lng : parseFloat(startArray[1])
	    },
	    destination : {
		lat : parseFloat(finishArray[0]),
		lng : parseFloat(finishArray[1])
	    },
	    waypoints : waypts,
	    travelMode : google.maps.TravelMode.DRIVING
	};
	requestArray.push({
	    "route" : route,
	    "request" : request
	});
    }
    processRequests(requestArray);
}

/**
 * Process Requests
 * 
 * @param requestArray
 * @returns
 */
function processRequests(requestArray) {
    for (var i = 0; i < requestArray.length; i++) {
	submitRequest(i, directionResults, requestArray[i].request);
    }
}

/**
 * Submit Request
 * 
 * @param i
 * @param directionResults
 * @param request
 * @returns
 */
function submitRequest(i, directionResults, request) {
    var directionsService = new google.maps.DirectionsService();
    directionsService.route(request, function(result, status) {
	directionResults(result, status, i);
    });
}

/**
 * Direction Results
 * 
 * @param result
 * @param status
 * @param i
 * @returns
 */
function directionResults(result, status, i) {
    if (status == google.maps.DirectionsStatus.OK) {
	var renderer = new google.maps.DirectionsRenderer();
	renderer.setMap(map);
	renderer.setOptions({
	    preserveViewport : true,
	    suppressInfoWindows : true,
	    polylineOptions : {
		strokeWeight : 4,
		strokeOpacity : 0.8,
		strokeColor : colors[i]
	    },
	    markerOptions : {
		icon : pinSymbol(colors[i]),
	    }
	});
	renderer.setDirections(result);
    }
}

/**
 * Pin Symbol
 * 
 * @param color
 * @returns
 */
function pinSymbol(color) {
    return {
	path : 'M 0,0 C -2,-20 -10,-22 -10,-30 A 10,10 0 1,1 10,-30 C 10,-22 2,-20 0,0 z M -2,-30 a 2,2 0 1,1 4,0 2,2 0 1,1 -4,0',
	fillColor : color,
	fillOpacity : 1,
	strokeColor : '#000',
	strokeWeight : 2,
	scale : 0.5,
    };
}

/**
 * init
 * 
 * @param mapList
 * @returns
 */
function init(mapList) {
    
    var mapOptions = {
	zoom : parseInt(getLocalMessage("Route.map.zoom")),
	center : new google.maps.LatLng(
		(getLocalMessage("Route.lattitute.initialize")),
		parseFloat(getLocalMessage("Route.Longitutde.initialize"))),
	mapTypeControl : false,
	streetViewControl : false,
	mapTypeId : google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById('map-canvas2'),
	    mapOptions);
}

/**
 * Get Vehicle Position
 * 
 * @param maploc
 * @returns
 */
function getVehiclePosition(maploc) {
    var locations = [ maploc ];
    deleteMarkers();
    for (i = 0; i < locations[0].length; i++) {
	for (i = 0; i < locations[0].length; i++) {
	    addVehicleMarker(locations[0][i][1], locations[0][i][2],
		    locations[0][i][0]);
	}

    }

}

/**
 * Add Vehicle Marker
 * 
 * @param lat
 * @param lngt
 * @param vehId
 * @returns
 */
var markers = [];
function addVehicleMarker(lat, lngt, vehId) {
    
    if (lat != null && lngt != null) {
	var marker = new google.maps.Marker({
	    position : new google.maps.LatLng(lat, lngt),
	    map : map,
	    icon : {
		url : 'images/SWM/car1.gif',
		scaledSize : new google.maps.Size(40, 40)
	    }
	});
	var infowindow = new google.maps.InfoWindow();
	google.maps.event.addListener(marker, 'mouseover',
		(function(marker, i) {
		    return function() {
			infowindow.setContent(vehId);
			infowindow.open(map, marker);
		    }
		})(marker, i));
	google.maps.event.addListener(marker, 'mouseout', (function(marker, i) {
	    return function() {
		infowindow.close();
	    }
	})(marker, i));
	markers.push(marker);
    }

}

// Sets the map on all markers in the array.
function setMapOnAll(map) {
    for (var i = 0; i < markers.length; i++) {
	markers[i].setMap(map);
    }
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
    setMapOnAll(null);
}

// Deletes all markers in the array by removing references to them.
function deleteMarkers() {
    clearMarkers();
    markers = [];
}

function showMap2() {
    
    data = {};
    var URL = 'BeatMaster.html?getMapData';
    var returnData = __doAjaxRequest(URL, 'POST', data, false, 'Json');
    mapList = returnData[0];
    getColor();
    init(mapList);
    initBeat(mapList);
    getvehLocation();

}

function getColor() {
    for (var m = 0; m <= Object.keys(mapList).length; m++) {
	colors.push("#" + ((1 << 24) * Math.random() | 0).toString(16));
    }
}

function getvehLocation() {
    var URL = 'BeatMaster.html?getvehicleData';
    var returnData = __doAjaxRequest(URL, 'POST', data, false, 'Json');
    getVehiclePosition(returnData);
    // globe++
    window.setInterval(getvehLocation, 60000);
}

function validateAreaDetails(errorList) {
    var errorList = [];
    var i = 0;
    if ($.fn.DataTable.isDataTable('#areaDetailTable')) {
	$('#areaDetailTable').DataTable().destroy();
    }

    $("#areaDetailTable tbody tr").each(
	    function(i) {
		
		var beatAreaType = $("#beatAreaType" + i).val();
		var beatAreaName = $("#beatAreaName" + i).val();
		var rowCount = i + 1;

		if (beatAreaType == "0" || beatAreaType == undefined) {
		    errorList.push(getLocalMessage("swm.validate.area.type")
			    + rowCount);
		}

		if (beatAreaName == "" || beatAreaName == undefined) {
		    errorList.push(getLocalMessage("swm.validate.area.name")
			    + rowCount);
		}

	    });
    return errorList;

}

function houseHoldCount() {
    

    var i = 0;
    if ($.fn.DataTable.isDataTable('#areaDetailTable')) {
	$('#areaDetailTable').DataTable().destroy();
    }

    $("#areaDetailTable tbody tr")
	    .each(
		    function(i) {
			
			var beatHouseHold = $("#beatHouseHold" + i).val();
			var beatShop = $("#beatShop" + i).val();
			var totalHouseHoldCount;
			var totalShopCount;
			if (i == 0) {
			    totalHouseHoldCount = 0;
			    totalShopCount = 0;
			} else {
			    totalHouseHoldCount = $("#totalHouseHoldCount")
				    .val();
			    totalShopCount = $("#totalShopCount").val();
			}
			var rowCount = i + 1;

			if (beatHouseHold == "" || beatHouseHold == '0'
				|| beatHouseHold == undefined)
			    beatHouseHold = 0;

			if (beatShop == "" || beatShop == '0'
				|| beatShop == undefined)
			    beatShop = 0;

			var grandCount = parseInt(beatHouseHold)
				+ parseInt(beatShop);
			var totalHouseHoldCount = parseInt(beatHouseHold)
				+ parseInt(totalHouseHoldCount);
			var totalShopCount = parseInt(beatShop)
				+ parseInt(totalShopCount);

			if (!isNaN(grandCount)) {
			    document.getElementById('grandCount' + i).value = grandCount;
			}
			if (!isNaN(totalHouseHoldCount)) {
			    document.getElementById('totalHouseHoldCount').value = totalHouseHoldCount;
			}
			if (!isNaN(totalShopCount)) {
			    document.getElementById('totalShopCount').value = totalShopCount;
			}
		    });
}

function addEntryData(tableId) {
    
    var errorList = [];
    errorList = validateAreaDetails();
    if (errorList.length == 0) {
	$("#errorDiv").hide();
	addTableRow(tableId, false);
	hasNumber();
    } else {
	displayErrorsOnPage(errorList);
    }
}
function deleteEntry(obj, ids) {
	var rowCount = $('#areaDetailTable >tbody >tr').length;
	let errorList = [];
	if (rowCount == 1) {
		errorList.push(getLocalMessage("Cannot delete"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	else {
    var totalWeight = 0;
    deleteTableRow('areaDetailTable', obj, ids);
    $('#areaDetailTable').DataTable().destroy();
    houseHoldCount();
    triggerTable();
	}
}

function checkDuplicateareaName() {
    
    $("#areaDetailTable tbody tr").each(function(i) {
	
	var beatAreaName = $("#beatAreaName" + i).val().trim();
	var duplicate = '';
	var myarray = new Array();
	if (beatAreaName != '') {
	    for (j = 0; j < i; j++) {
		myarray[j] = $("#beatAreaName" + j).val();
	    }
	}
	if (myarray.includes(beatAreaName)) {
	    duplicate = 'Duplicate area names are not allowed';
	}
	if (duplicate != '') {
	    $("#beatAreaName" + i).val('');
	    var errorList = [];
	    errorList.push(duplicate);
	    displayErrorsOnPage(errorList);

	}

    });
}

function checkDuplicateareaType() {
    
    $("#areaDetailTable tbody tr").each(function(i) {
	
	var beatAreaType = $("#beatAreaType" + i).val().trim();
	var duplicate1 = '';
	var myarray = new Array();
	if (beatAreaType != '') {
	    for (j = 0; j < i; j++) {
		myarray[j] = $("#beatAreaType" + j).val();
	    }
	}
	if (myarray.includes(beatAreaType)) {
	    duplicate1 = 'Duplicate area types are not allowed';
	}
	if (duplicate1 != '') {
	    $("#beatAreaType" + i).val('');
	    var errorList = [];
	    errorList.push(duplicate1);
	    displayErrorsOnPage(errorList);

	}

    });
}

function hasNumber() {
	$('.hasNumber').on('input', function() {
		this.value = this.value.replace(/[^0-9]/g, '');
	});
}