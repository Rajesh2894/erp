var colors = [];
var map;

$(document).ready(function() {
	initMap();
	showMap2();
	getVehiclePosition();
});

function initMap() {

	var mapOptions = {
		center : new google.maps.LatLng(21.2787, 81.8661),
		zoom : 9,
		mapTypeControl : false,
		streetViewControl : false,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};

	map = new google.maps.Map(document.getElementById('map-canvas2'),
			mapOptions);
}
function showMap2() {
	data = {};
	var URL = 'RouteInfo.html?getMapData';
	var returnData = __doAjaxRequest(URL, 'POST', data, false, 'Json');
	mapList = returnData[0];
	getColor();
	initRoute(mapList);
}

function getColor() {
	for (var m = 0; m <= Object.keys(mapList).length; m++) {
		colors.push("#" + ((1 << 24) * Math.random() | 0).toString(16));
	}
}

function getVehiclePosition() {
	
	data = {};
	var URL = 'RouteInfo.html?getvehicleData';
	var returnData = __doAjaxRequest(URL, 'POST', data, false, 'Json');	
	initVehiclePos(returnData);
	window.setInterval(getVehiclePosition, 60000);
}

function initRoute(mapList) {
	var requestArray = [];
	for ( var route in mapList) {
		var waypts = [];
		var start, finish
		var lastpoint

		var data = mapList[route]
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
		console.log(startArray);
		console.log(finishArray);

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

function submitRequest(i, directionResults, request) {
	
	var directionsService = new google.maps.DirectionsService();
	directionsService.route(request, function(result, status) {
		directionResults(result, status, i);
	});
}
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
				icon : {
					path : google.maps.SymbolPath.CIRCLE,
					scale : 4,
					strokeColor : colors[i]
				}
			}
		});

		renderer.setDirections(result);
	}

}
function processRequests(requestArray) {
	for (var i = 0; i < requestArray.length; i++) {
		submitRequest(i, directionResults, requestArray[i].request);
	}
}

var markers = [];
function initVehiclePos(vehicleLocation) {
	
	var locations = [ vehicleLocation ];

	deleteMarkers();
	for (i = 0; i < locations[0].length; i++) {
		addVehicleMarker(locations[0][i][2], locations[0][i][3],
				locations[0][i][1]);
	}
}

//Sets the map on all markers in the array.
function setMapOnAll(map) {
	for (var i = 0; i < markers.length; i++) {
		markers[i].setMap(map);
	}
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
	setMapOnAll(null);
}

//Deletes all markers in the array by removing references to them.
function deleteMarkers() {
	clearMarkers();
	markers = [];
}

function addVehicleMarker(lat, lngt, vehId) {
	
	if (lat != null && lngt != null){	
	var marker = new google.maps.Marker({
		position : new google.maps.LatLng(lat, lngt),
		map : map,
		icon : {
			url : 'images/SWM/car1.gif',
			scaledSize : new google.maps.Size(40, 40)
		}
	});
	var infowindow = new google.maps.InfoWindow();
	google.maps.event.addListener(marker, 'mouseover', (function(marker, i) {
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
