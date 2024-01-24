/**
 * ritesh.patil
 */
waterTankerBookingUrl = 'WaterTankerBooking.html';

$(document).ready(
		function() {
			
			$("#errorDiv").hide();
			$('.fancybox').fancybox();
			$("#datatables").dataTable({
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 5,
				"bInfo" : false,
				"lengthChange" : true
			});
			$('#openbookingFormId').click(
                     function() {
						console.log($(this).val());
						var ajaxResponse = __doAjaxRequest(waterTankerBookingUrl+'?form', 'POST', 'propId='+ $('#openbookingFormId').parent().find('input:hidden:first').val(), false,'html');
						$('.content').html(ajaxResponse);
					});

		});

function getListOfPropertyForBooking() {
	var errorList = [];
	var checkBtn = getLocalMessage('rnl.estate.check');
	errorList = validateObjDetailForm(errorList);
	if (errorList.length == 0) {
		var category = $("#category").val();
		var eventId = $("#eventId").val();
		var table = $('#datatables').DataTable();
		table.rows().remove().draw();
		var requestData = {
			'category' : category,
			'eventId' : eventId
		};
		var cdmURL = "WaterTankerBooking.html";
		var ajaxResponse = __doAjaxRequest(cdmURL + '?filterWaterTankerBookingList',
				'POST', requestData, false, 'json');
		if (ajaxResponse != null && ajaxResponse != "") {
			var result = [];
			$
					.each(
							ajaxResponse,
							function(index) {
								var lookUp = ajaxResponse[index];
								result
										.push([
												index + 1,
												lookUp.propName,
												lookUp.capacity,
												lookUp.location,
												lookUp.totalRent,
												"<a href='javascript:void(0);' class='btn btn-blue-2 btn-sm' onClick=showCalAndMap(\""
														+ lookUp.propId
														+ "\")><i class='fa fa-check-circle-o'></i>" + checkBtn + "</a>" ]);
							});
			table.rows.add(result);
			table.draw()
		} else {
			errorList.push(getLocalMessage("rnl.seacrch.valid.nofound"));
			displayErrorsOnPage(errorList);
		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateObjDetailForm(errorList) {
	var category=$("#category").val();
	var eventId=$("#eventId").val();
	
	if (category == "" || category == undefined || category=="0") {
		errorList.push(getLocalMessage("rnl.search.valid.Category"));
	}else if (eventId == "" || eventId == undefined || eventId=="0") {
		errorList.push(getLocalMessage("rnl.search.valid.event"));
	}
	return errorList;
}

function showCalAndMap(propId) {
	$('#openbookingFormId').parent().find('input:hidden:first').val('');
	var xDiv = document.getElementById('Availabilitydiv');
	$('#openbookingFormId').parent().find('input:hidden:first').val(propId);
	if (xDiv.style.height == '') {
		xDiv.style.height = 'auto'
		response = __doAjaxRequest('WaterTankerBooking.html?getCalAndMapData',
				'POST', 'propId=' + propId, false, 'json');
		calendarList = response[0];
		mapList = response[1];
		doc = response[2];
		images = response[3];
		runCalendar(calendarList);
		runGoogleMap(mapList);
		termCondition(doc);
		displayImage(images);
	} else {

		$('#calendar').fullCalendar('destroy');
		event = [];
		xDiv.style.height = ''
	}
	$('html, body').animate({
		scrollTop : $("#Availabilitydiv").offset().top
	}, 2000);
}

function runCalendar(calendarList) {
	propId = $('#openbookingFormId').parent().find('input:hidden:first').val();
	var calendar = $('#calendar').fullCalendar(
			{

				header : {
					right : 'prev,next',
					left : 'title'
				},
				eventSources : [ {
					events : function(start, end, callback) {

						var events = [];
						$(calendarList).each(function() {
							events.push({

								title : $(this).attr('title'),
								start : new Date($(this).attr('start')),
								end : new Date($(this).attr('end')),
								className : $(this).attr('className'),
								id : $(this).attr('id')
							});
						});

						callback(events);

					},
				}, ],
				// an option!

				eventClick : function(calEvent, jsEvent, view) {
					var ajaxResponse = __doAjaxRequest(
							'WaterTankerBooking.html?propertyInfo', 'POST', {
								"bookId" : calEvent.id
							}, false, 'html');
					$('.content').html(ajaxResponse);
				}

			});
}

function runGoogleMap(mapList) {
	var locations = [

	mapList ];
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom : parseFloat(getLocalMessage("rnl.zoom.size")), 
		center : new google.maps.LatLng(parseFloat(getLocalMessage("rnl.latitude.initialize")), 
      		  parseFloat(getLocalMessage("rnl.longitude.initialize"))),
		mapTypeId : google.maps.MapTypeId.ROADMAP
	});

	var infowindow = new google.maps.InfoWindow();

	var marker, i;

	for (i = 0; i < locations.length; i++) {
		marker = new google.maps.Marker(
				{
					position : new google.maps.LatLng(parseFloat(locations[i][1]), parseFloat(locations[i][2])),
					map : map
				});

		google.maps.event.addListener(marker, 'mouseover',
				(function(marker, i) {
					return function() {
						infowindow.setContent(locations[i][0]);
						infowindow.open(map, marker);
					}
				})(marker, i));
	}
}

function termCondition(doc) {
	if (doc != null)
		hyperLink = "<a class='btn btn-link' onclick=\"downloadFile('" + doc
				+ "','WaterTankerBooking.html?Download')\">Terms & Condition</a> ";
	else
		hyperLink = '<a class="btn btn-link" href="#">Terms & Condition</a>';
	$('#termCondi').html(hyperLink);
}

function back() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', waterTankerBookingUrl);
	$("#postMethodForm").submit();
}

function displayImage(images) {
	hyperLink = '';
	if (images != null && images.length != 0 && images !=""){
		$.each(images, function(key, value) {
			hyperLink += "<a href='./" + value
					+ "' class='fancybox' target='_blank'><img src='/MainetService/" + value
					+ "' class='img-thumbnail margin-top-10'/></a>";
		});
	}else{
		hyperLink+='<img src="images/no-image-found.jpg" class="img-thumbnail margin-top-10" />';		
	}
	$('#propImages').html(hyperLink);
}

function changedLocation(value) {

	var table = $('#datatables').DataTable();
	table.rows().remove().draw();
	var ajaxResponse = __doAjaxRequest('WaterTankerBooking.html?filterList', 'POST',
			'type=' + value, false, 'json');
	var result = [];
	$
			.each(
					ajaxResponse,
					function(index) {
						var lookUp = ajaxResponse[index];
						result
								.push([
										index + 1,
										lookUp.propName,
										lookUp.category,
										lookUp.location,
										"<a href='javascript:void(0);' class='btn btn-blue-2 btn-sm' onClick=showCalAndMap(\""
												+ lookUp.propId
												+ "\")><i class='fa fa-check-circle-o'></i> Check</a>" ]);
					});
	table.rows.add(result);
	table.draw()
}

function viewBookingFacility(propId) {
	var divName = '.content-page';
	var requestData = {
		propId : propId
	}
	var ajaxResponse = doAjaxLoading(
			'WaterTankerBooking.html?viewEstateBookingFacility', requestData, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}

function backData() {

	if($("#checkBookingFlag").val() == 'Y'){
		checkBookingAvailability();
	}			
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'WaterTankerBooking.html');
	$("#postMethodForm").submit();
}


function backToHome(){
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'CitizenHome.html');
	$("#postMethodForm").submit();
}
function dispalyCategory() {debugger;
	
	var divName = '.content-page';
	var requestData = {
		"category" : $("#category").val()

	};
	$('#eventId').html('');
	$('#eventId').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));
	var ajaxResponse = doAjaxLoading('WaterTankerBooking.html?getCategoryAndEvent',
			requestData, 'html');
	var prePopulate = JSON.parse(ajaxResponse);
	$.each(prePopulate, function(index, value) {
		$('#eventId').append(
				$("<option></option>").attr("value", value.propEvent).text(
						(value.cpdDesc)));
	});
	$('#eventId').trigger("chosen:updated");

	}

/*User Story #91836*/
function checkBookingAvailability() {
var errorList = [];
    var theForm = '#waterTankerBookingForm';
    var requestData = {};
	requestData = __serializeForm(theForm);
    var response = __doAjaxRequest('WaterTankerBooking.html?getBookingDetails', 'POST', requestData, false);
	$("#errorDiv").hide();
    $.fancybox.close();
    $(formDivName).html(response);
}

