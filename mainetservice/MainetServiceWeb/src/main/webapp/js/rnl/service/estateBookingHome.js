/**
 * ritesh.patil
 */
bookinEstateUrl='EstateBooking.html';

$(document).ready(function() {
	$('.fancybox').fancybox();
	$("#datatables").dataTable({
			"oLanguage": { "sSearch": "" } ,
			"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
		    "iDisplayLength" : 5, 
		    "bInfo" : false,
		    "lengthChange": true
	});	   
	
/*	$("#locationId").change(function(){
		var table = $('#datatables').DataTable();
		table.rows().remove().draw();
		var requestData={'type':$(this).val()};
		var cdmURL = "EstateBooking.html";
		var ajaxResponse = __doAjaxRequest(
				cdmURL
						+ '?filterEstateBookingList',
				'POST', requestData, false,
				'json');
		
		var result = [];
		$.each(ajaxResponse, function(index){
			var lookUp = ajaxResponse[index];
			result.push([index+1,lookUp.propName,lookUp.category,lookUp.location,"<a href='javascript:void(0);' class='btn btn-blue-2 btn-sm' onClick=showCalAndMap(\"" + lookUp.propId +"\")><i class='fa fa-check-circle-o'></i> Check</a>"]);
         });
		table.rows.add(result);
		table.draw()
	 });*/

	$('#openbookingFormId').click(function() {
			var ajaxResponse = __doAjaxRequest(bookinEstateUrl+'?form', 'POST', 'propId='+$('#openbookingFormId').parent().find('input:hidden:first').val(), false,'html');
			$('.content').html(ajaxResponse);
		 });
	  
	 });

function getListOfPropertyForBooking(){
	var errorList = [];
	errorList = validateObjDetailForm(errorList);
	if (errorList.length == 0) {
		
	var category=$("#category").val();
	var eventId=$("#eventId").val();
	var capcityFrom=$("#capcityFrom").val();
	if(capcityFrom==""){
		capcityFrom="0";
	}
	var capcityTo=$("#capcityTo").val();
	if(capcityTo==""){
		capcityTo="99999999";
	}	
	var rentFrom=$("#rentFrom").val();
	if(rentFrom==""){
		rentFrom="0";
	}
	var rentTo=$("#rentTo").val();
	if(rentTo==""){
		rentTo="99999999";
	}
	var table = $('#datatables').DataTable();
	table.rows().remove().draw();
	var requestData={'category':category,
					'eventId':eventId,
					'capcityFrom':capcityFrom,
					'capcityTo':capcityTo,
					'rentFrom':rentFrom,
					'rentTo':rentTo
					};
	var cdmURL = "EstateBooking.html";
	var viewBtn = getLocalMessage('rnl.master.view');
	var checkBtn = getLocalMessage('rnl.book.check');
	var ajaxResponse = __doAjaxRequest(
			cdmURL
					+ '?filterEstateBookingList',
			'POST', requestData, false,
			'json');
		if(ajaxResponse!=null && ajaxResponse!=""){
			var result = [];
			$.each(ajaxResponse, function(index){
				var lookUp = ajaxResponse[index];
				result.push([index+1,lookUp.propName,lookUp.category,lookUp.capacity,lookUp.location,lookUp.totalRent,"<a href='javascript:void(0);' class='btn btn-blue-2 btn-sm' onClick=viewBookingFacility(\"" + lookUp.propId +"\")><i class='fa fa-eye'></i>" + viewBtn + "</a>","<a href='javascript:void(0);' class='btn btn-blue-2 btn-sm' onClick=showCalAndMap(\"" + lookUp.propId +"\")><i class='fa fa-check-circle-o'></i>" + checkBtn + "</a>"]);
		     });
			table.rows.add(result);
			table.draw()
			$('#errorDiv').addClass('hide');
		}else{
			errorList.push(getLocalMessage("rnl.seacrch.valid.nofound"));
			displayErrorsOnPage(errorList);
		}
	}else {
		displayErrorsOnPage(errorList);
	}
}

function validateObjDetailForm(errorList) {
	
	var category=$("#category").val();
	var eventId=$("#eventId").val();
	var capcityFrom=$("#capcityFrom");
	var capcityTo=$("#capcityTo");
	var rentFrom=$("#rentFrom");
	var rentTo=$("#rentTo");
	/* change by Suhel Defect #32968
	 * In Booking of Municipal Property, when putting numbers in ‘Capacity To’ and ‘Capacity From’ fields, getting error.
	 * 
	 * */
	
	if (category == "" || category == undefined || category=="0") {
		errorList.push(getLocalMessage("rnl.search.valid.Category"));
	}else if (eventId == "" || eventId == undefined || eventId=="0") {
		errorList.push(getLocalMessage("rnl.search.valid.event"));
	}else if((capcityFrom.val().length!=0 && capcityTo.val().length!=0)){		
		if(parseInt(capcityFrom.val()) > parseInt(capcityTo.val())){
			errorList.push(getLocalMessage("rnl.search.valid.capacity"));
		}		
	}else if((rentFrom.val().length!=0 && rentTo.val().length!=0)){
		
		if(parseInt(rentFrom.val()) > parseInt(rentTo.val())){
			
			errorList.push(getLocalMessage("rnl.search.valid.Rent"));
		}
	}
	return errorList;
}

 

function showCalAndMap(propId) {
	//D#75597
	$('#openbookingFormId').parent().find('input:hidden:first').val('');
    var xDiv = document.getElementById('Availabilitydiv'); 
    $('#openbookingFormId').parent().find('input:hidden:first').val(propId);
    if (xDiv.style.height == '' || toggleFlag==true){ 
    	  $('#calendar').fullCalendar('destroy');
   	 	  event=[];         
          xDiv.style.height = 'auto'
          response=__doAjaxRequest('EstateBooking.html?getCalAndMapData', 'POST', 'propId='+propId, false,'json');
          calendarList=response[0];
          mapList=response[1];
          doc=response[2];
          images=response[3];
          runCalendar(calendarList);
          runGoogleMap(mapList);
          termCondition(doc);
          displayImage(images);
          toggleFlag=true;
    }
    /*else{ 
    	
    	 /*$('#calendar').fullCalendar('destroy');
    	 event=[];
         xDiv.style.height = ''
    }*/
    $('html, body').animate({scrollTop : $("#Availabilitydiv").offset().top}, 2000);
}



function runCalendar(calendarList) {
    propId=$('#openbookingFormId').parent().find('input:hidden:first').val();
    var calendar = $('#calendar').fullCalendar({
    	 
        header: {
            right: 'prev,next',
            left: 'title'
        },
        eventSources : [
						{
							events : function(start, end,callback) {
								
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
						
						 eventClick: function(calEvent, jsEvent, view) {
	                            var ajaxResponse = __doAjaxRequest('EstateBooking.html?propertyInfo', 'POST', {"bookId":calEvent.id}, false,'html');
								$('.content').html(ajaxResponse);
                            }
						    
                      });
}


function runGoogleMap(mapList){
	
	 var locations = [
	                  /*['<h5>Sai Marriage Palace</h5>Mahakali Caves Road, Chakala, Andheri East, <br>Mumbai, Maharashtra 400093', 19.124831, 72.861485, 1],
	                  ['<h5>ABM 400093', 19.125312, 72.852991, 2],
	                  ['<h5>Ritesh', 19.125489, 72.853324, 3],*/
	                  mapList
	     					];
		   var map = new google.maps.Map(document.getElementById('map'), {
	                  zoom: parseFloat(getLocalMessage("rnl.zoom.size")), 
	                  center: new google.maps.LatLng(parseFloat(getLocalMessage("rnl.latitude.initialize")), 
	                		  parseFloat(getLocalMessage("rnl.longitude.initialize"))),
	                  mapTypeId: google.maps.MapTypeId.ROADMAP
	                });
	                var infowindow = new google.maps.InfoWindow();
	                var marker, i;
	                for (i = 0; i < locations.length; i++) {  
		                  marker = new google.maps.Marker({
		                    position: new google.maps.LatLng(parseFloat(locations[i][1]), parseFloat(locations[i][2])),
		                    map: map
		                  });	
		                  google.maps.event.addListener(marker, 'mouseover', (function(marker, i) {
		                    return function() {
		                      infowindow.setContent(locations[i][0]);
		                      infowindow.open(map, marker);
		                    }
		                  })(marker, i));
	                }
     }

function termCondition(doc){
	  var termsCondition = getLocalMessage('rnl.terms.condition');
	  if(doc != null)
	     hyperLink="<a class='btn btn-link' onclick=\"downloadFile('"+doc+"','EstateBooking.html?Download')\">" + termsCondition + "</a> " ;
	  else
		  hyperLink='<a class="btn btn-link" href="#">' + termsCondition + '</a>';
	  $('#termCondi').html(hyperLink);
}

function back() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', bookinEstateUrl);
	$("#postMethodForm").submit();
}

function displayImage(images){
	/*hyperLink='';  
	if(images != null){
		  $.each( images, function( key, value ) {
			 // hyperLink+="<a href='./"+value+"' class='fancybox'><img src='./"+value+"' class='img-thumbnail margin-top-10'/></a>";
			});
	  }
	  $('#propImages').html(hyperLink);*/
	
	// Changes done behalf of Nilima Mam
	hyperLink='';  
	if(images != null && images.length != 0 && images !=""){
		  $.each( images, function( key, value ) {
			  hyperLink+="<img src='./"+value+"' class='img-thumbnail margin-top-10'/>";
			});
	  }
	else{
		hyperLink+='<img src="images/no-image-found.jpg" class="img-thumbnail margin-top-10" />';		
	}
	  $('#propImages').html(hyperLink);
}

function changedLocation(value){
	
	var table = $('#datatables').DataTable();
	table.rows().remove().draw();
	//var ajaxResponse = __doAjaxRequest('EstateBooking.html/property/filterList', 'POST', 'type='+$(this).val(), false,'json');
	var ajaxResponse = __doAjaxRequest('EstateBooking.html?filterList', 'POST', 'type='+value, false,'json');
	var result = [];
	$.each(ajaxResponse, function(index){
		var lookUp = ajaxResponse[index];
		result.push([index+1,lookUp.propName,lookUp.category,lookUp.location,"<a href='javascript:void(0);' class='btn btn-blue-2 btn-sm' onClick=showCalAndMap(\"" + lookUp.propId +"\")><i class='fa fa-check-circle-o'></i> Check</a>"]);
     });
	table.rows.add(result);
	table.draw()
 }

function viewBookingFacility(propId){
	
	var divName = '.content-page';
	var requestData = {
			propId : propId
	}
	var ajaxResponse = doAjaxLoading('EstateBooking.html?viewEstateBookingFacility', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

	prepareTags();
	
}

function backData() {
	//#34039 - to get data on back button
	var ajaxResponse = __doAjaxRequest(
			'EstateBooking.html?backToSearchForm', 'POST', {}, false,
			'html');
	 $(formDivName).html(ajaxResponse);
	 var rentFrom = $("#rentFrom").val();
	 var rentTo = $("#rentTo").val();
	 var capcityFrom=$("#capcityFrom").val();
	 var capcityTo=$("#capcityTo").val();
	 if (rentFrom == 0.0){
		 $("#rentFrom").val("");
	 }
	 if (rentTo == 9.9999999E7){
		 $("#rentTo").val("");
	 }
	 if (rentFrom == 0.0){
		 $("#rentFrom").val("");
	 }
	 if (capcityFrom == 0){
		 $("#capcityFrom").val("");
	 }
	 if (capcityTo == 99999999){
		 $("#capcityTo").val("");
	 }
	 
	/*
	 * $("#postMethodForm").prop('action', '');
	 * $("#postMethodForm").prop('action', 'EstateBooking.html');
	 * $("#postMethodForm").submit();
	 */
}


function dispalyCategory() {
	debugger;
	var divName = '.content-page';
	var requestData = {
		"category" : $("#category").val()

	};
	$('#eventId').html('');
	$('#eventId').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));
	var ajaxResponse = doAjaxLoading('EstateBooking.html?getCategoryAndEvent',
			requestData, 'html');
	var prePopulate = JSON.parse(ajaxResponse);
	$.each(prePopulate, function(index, value) {
		$('#eventId').append(
				$("<option></option>").attr("value", value.propEvent).text(
						(value.cpdDesc)));
	});
	$('#eventId').trigger("chosen:updated");

	}

