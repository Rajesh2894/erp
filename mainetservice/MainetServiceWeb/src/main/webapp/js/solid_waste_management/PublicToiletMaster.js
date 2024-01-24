function addPublicToiletMaster(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchPublicToiletMaster(){
	var data = {
		"type":replaceZero($("#sanType").val()),
		"siteName":replaceZero($("#sanName").val()),
		"ward":replaceZero($("#codWard1").val()),
		"zone":replaceZero($("#codWard2").val())
		};
		var divName = '.content-page';
		var url = "PublicToiletMaster.html?searchPublicToiletMaster";
		var ajaxResponse = doAjaxLoading(url , data, 'html',divName);
		$('.content').removeClass('ajaxloader');		
		$(divName).html(ajaxResponse);
		prepareTags();
}

function searchPublicToiletMaster(formUrl, actionParam) {
	//location, name, number and toilet type
	
	var data = {	
		"type":replaceZero($("#sanType").val()),
		"siteName":replaceZero($("#sanName").val()),
		"ward":replaceZero($("#codWard1").val()),
		"zone":replaceZero($("#codWard2").val())
	};
	var divName = '.content-page';
	var formUrl = "PublicToiletMaster.html?searchPublicToiletMaster";
	var ajaxResponse = doAjaxLoading(formUrl, data, 'html',divName);
	$('.content').removeClass('ajaxloader');		
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getPublicToiletMaster(formUrl, actionParam, sanId) {  
	
	var divName = '.content-page';
	var data= {
		"sanId":sanId	
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getPublicToiletMasterView(formUrl, actionParam, sanId) {
	
	var data = {
		"sanId":sanId	
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function savePublicToiletMaster(element){
	
	var errorList = [];
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element,getLocalMessage('public.toilet.master.add.success'),'PublicToiletMaster.html', 'saveform');
	}
}

function deletePublicToiletMasterData(formUrl, actionParam, sanId) {
	if (actionParam == "deletePublicToiletMaster") {
		showConfirmBoxForDelete(sanId, actionParam);
	}		
}
function showConfirmBoxForDelete(sanId, actionParam) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger\" style=\"font-size:16px;\">'+ getLocalMessage("swm.cnfrmdelete") +'</h4>';
	message += '<div class=\'text-center padding-bottom-20\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + sanId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}
function proceedForDelete(sanId) {
	$.fancybox.close();
	var requestData = 'sanId=' + sanId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('PublicToiletMaster.html?'+ 'deletePublicToiletMaster', requestData, 'html');
	$('.content').removeClass('ajaxloader');		
	$(divName).html(ajaxResponse);
	prepareTags();
}

function backPublicToiletMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'PublicToiletMaster.html');
	$("#postMethodForm").submit();
}

function resetScheme(){
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action','PublicToiletMaster.html');
	$('.error-div').hide();
}

function resetForm() {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('PublicToiletMaster.html' + '?' + 'addPublicToiletMaster', {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$('input[type=text]').val('');  
    $('#Address').val(''); 
    $('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
	prepareTags();
}

function resetPublicToiletMaster() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'PublicToiletMaster.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}
$(document).ready(function(){
	var table = $('.sm').DataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	    "iDisplayLength" : 5, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "bPaginate": true,
	    "bFilter": true,
	    "ordering" : false,
		"order" : [[1, "desc"]]
	    });
	$('input#classLongitude').keypress(function(e){ 
		var errorList = [];
		 if (this.selectionStart == 0 && (e.which >= 65 && e.which <= 90 ) || (e.which >= 97 && e.which <= 122 )){
			 errorList.push(getLocalMessage("swm.validation.longitude"));
		      return false;
		   }
	});
	
	$('input#classLatitude').keypress(function(e){ 
		var errorList = [];
		 if (this.selectionStart == 0 && (e.which >= 65 && e.which <=90  ) || (e.which >= 97 && e.which <= 122 )){
			 errorList.push(getLocalMessage("swm.validation.lattiude"));
		      return false;
		   }
	});

		
});	

function replaceZero(value){
	return value != 0 ? value : undefined;
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

			$("#classLatitude").val(x);
			$("#classLongitude").val(y);
		} else {
			res.innerHTML = getLocalMessage("disposal.validation.locationerror") + status;
		}
	});
}
function showMap(){
	
	data = {};
	var URL = 'PublicToiletMaster.html?getMapData';
	var returnData = __doAjaxRequest(URL, 'POST', data, false, 'Json');
	mapList=returnData[0];
	initMap2(mapList);
}
function initMap2(mapList){
	
	 var locations = [mapList];
	                var map = new google.maps.Map(document.getElementById('map-canvas'), {
	                	 zoom: parseInt(getLocalMessage("PTM.map.zoom")),
		                  center:  new google.maps.LatLng((getLocalMessage("PTM.lattitute.initialize")), parseFloat(getLocalMessage("PTM.Longitutde.initialize"))),
	                  mapTypeId: google.maps.MapTypeId.ROADMAP
	                });
	                var infowindow = new google.maps.InfoWindow();
	                var marker, i;
	                for (i = 0; i < locations[0].length; i++) {  
		                  marker = new google.maps.Marker({
		                    position: new google.maps.LatLng(locations[0][i][1], locations[0][i][2]),		                   
		                    map: map
		                  });
		                  google.maps.event.addListener(marker, 'mouseover', (function(marker, i) {
		                    return function() {
		                      infowindow.setContent(locations[0][i][0]);
		                      infowindow.open(map, marker);
		                    }
		                  })(marker, i));
	                }
    }