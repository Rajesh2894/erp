var getAllLocations = "locations/ajax/fetchData.html?input=";
var getAllLocationsByPinCode="grievance.html?getAllLocationsByPinCode";
var getAllLocationsByOrgId="locations/ajax/getAllLocationsByOrgId.html";
$(document).ready(function(){

	getGeoLocation();
	
		
		if($("#departmentComplaint").find("option:selected").val()!= undefined && $("#departmentComplaint").find("option:selected").val() != 0){
			//hitForWardZone();
			$('#kdmcWardZoneDiv').show();
		}else{
			$('#kdmcWardZoneDiv').hide();
		}
	
		
		
		
		if($("#complaintType").find("option:selected").val()!= undefined && $("#complaintType").find("option:selected").val() != 0){
			//used when reset and validation error from server side
			hitOnChangeSubtypeData();	
		
		}else{
			$('#residentDivId').hide();	
		}
		
		//used when reset and validation error from server side
		if($('#kdmcEnv').val() == "Y" && $('#extReferNumber').val() != undefined && $('#extReferNumber').val() !='' ){
			//fetchPropertyFlatNo();
			//show when flat no if selected
			/*if($("#flatNo").find("option:selected").val())
				$('#flatNoSelectId').show();*/
		}
		
		if($('#kdmcEnv').val() == "Y"){
			labelChange();	
		}
		
		

	$('.Complaint input[type="radio"]').click(function(){
		if($(this).attr("value")=="NewComplaint"){
			$(".box").not(".NewComplaint").hide();
			$(".NewComplaint").show();
		}
		if($(this).attr("value")=="ReopenComplaint" || reopenComplaintMode =="true"){
			$(".box").not(".ExistingComplaint").hide();
			$(".ExistingComplaint").show();
			var response = __doAjaxRequest("grievance.html?getAllGrievanceRaisedByRegisteredCitizenView",'post',"",false,'html');
			$('#reopenComplaintId').html(response);
		}       
	});
	
	


	$('#orgId').change(function() {
		var org =$('#orgId').val();
		$('#departmentComplaint').html('');
		$('#departmentComplaint').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
		$('#location').html('');
		$('#location').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
		$('#complaintType').html('');
		$('#complaintType').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
		if(org !="" && org!="0"){
			var requestData = {"orgId":$('#orgId').val()}
			var response = __doAjaxRequest('grievance.html?grievanceDepartments','post',requestData,false,'json');

			$.each(response, function(index, value) {
				$('#departmentComplaint').append($("<option></option>").attr("value",value.lookUpId).attr("code",value.lookUpCode).text(value.lookUpDesc));
			});
			$('#departmentComplaint').trigger('chosen:updated');	
		}
	});

	$(".rstButton").click(function() {      
		$('select').val('0').trigger('chosen:updated');
		$(".otp").html('');
	});
	
	$('#complaintType').change(function() {
		//function call
		
		$('#residentId').val("");
		hitOnChangeSubtypeData();
	});
	
	$('#extReferNumber').blur(function() {
		var submitBTDisable= false;
		let deptCode  =  $("#departmentComplaint :selected").attr('code');
		let amtDuesCheck = $('#amtDuesCheck').val();
		if($('#kdmcEnv').val()== "Y" && $('#departmentComplaint').val() > 0 && $('#extReferNumber').val() != ""  && amtDuesCheck == "Y") {
			//first  check deptCode is property (some extra API for get property details) or water
			if(deptCode =='AS'){
				//call for Property flats (billingMethod :I/W)
				let flatNoList = fetchPropertyFlatNo();
				//if flat list is present than go for dues check by selecting flat no 
				//else directly hit without flat no because  flat list zero it mean it is whole property case
				if(flatNoList.length>0 && flatNoList[0]!= null){
						
				}else{
					//hit API to check dues or not
					findOpWardZoneByReferenceNo();
					//checkForDues();
				}
				
			}else if(deptCode == 'WT'){
				findOpWardZoneByReferenceNo();
				//checkForDues();
			}
		}else if($('#kdmcEnv').val()== "Y" && $('#departmentComplaint').val() > 0 && $('#extReferNumber').val() != ""){
			//U#113577
			//it will execute when dues check, not need but here also if property details need than 
			//first fetch property flat no(billingMethod :I/W) concept
			//if not doing this than every time it will give invalid property no
			
				if(deptCode =='AS'){
					//call for Property flats
					let flatNoList = fetchPropertyFlatNo();
					if(flatNoList.length>0){
						
					}else{
						findOpWardZoneByReferenceNo();	
					}
					
				}else if(deptCode == 'WT'){
					findOpWardZoneByReferenceNo();
				}
			}
		
		
	});
	
	//it will only execute in case of property department selected
	$('#flatNo').change(function() {
		//hit for checkDues in case of property
		findOpWardZoneByReferenceNo();
		//let amtDuesCheck = $('#amtDuesCheck').val();
		/*if(amtDuesCheck == "Y"){
			checkForDues($("#flatNo").find("option:selected").val());	
		}*/
		
	});
	

});

//Defect #110139
function resetFormData(){
	if($('#kdmcEnv').val() !="Y" ){
		$('#GrievanceLocation').val('').trigger('chosen:updated');
		$('#departmentComplaint').val('0').trigger('chosen:updated');
		$('#complaintType').val('0').trigger('chosen:updated');
		$('#location').val('0').trigger('chosen:updated');
		$('#ComplaintDescription').val("");

		$('#Pincode2').val();
		$('#Landmark').val();
		$('.alert').hide();
		$('#residentDivId').hide();
		$("#careDocLbl").removeClass( "required-control");
		//D#128713 start
		$("#refNoLblId").removeClass( "required-control");
		$("#propertyLblId").removeClass( "required-control");
		$("#waterLblId").removeClass( "required-control");
		//D#128713 end
	}else{
		var requestData = {
				"isReset" : "Y"
			}
			var ajaxResponse = __doAjaxRequest('grievance.html', 'POST',requestData, false, 'html');
			$('.content-page').html(ajaxResponse);
		//$('#kdmcWardZoneDiv').hide();	
	}
	
	
	return false;
}

function getComplaintTypeAndLoc() {
	$('#location').html('');
	$('#location').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
	$('#complaintType').html('');
	$('#complaintType').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
	if( $('#departmentComplaint').val() > 0){

		var deptId=$('#departmentComplaint').val();
		var orgId =null;
		if($('#orgId').val()!=undefined){
			orgId =$('#orgId').val();
		}
		var requestData = {"deptId" : deptId,"orgId" : orgId };
		//Fetch Complaint Types
		var complaintRes = __doAjaxRequest('grievance.html?grievanceComplaintTypes','post',requestData,false,'json');

		$.each(complaintRes, function(index, value) {
			$('#complaintType').append($("<option></option>").attr("value",value.lookUpId).attr("code",value.lookUpCode).text(value.lookUpDesc));
		});
		
		

		$('#complaintType').trigger('chosen:updated');	
		
		
		//U#113577
		if($('#kdmcEnv').val() == 'Y'){
			hitForWardZone();
		}
		$('#extReferNumber').val("");
		$('#flatNoSelectId').hide();
		
		$('#residentId').val("");
		$('#residentDivId').hide();
		

		var	LocResponse = __doAjaxRequest('grievance.html?grievanceLocations','post',requestData,false,'json');
		$.each(LocResponse, function(index, value) {
			$('#location').append($("<option></option>").attr("value",value.lookUpId).attr("code",value.lookUpCode).text(value.lookUpDesc));
		});
		$('#location').trigger('chosen:updated');	


	}

}

function getLocation() {
	var pinCode = document.getElementById("Pincode2").value;
	var requestData = 'pinCode='+pinCode;
	var response = __doAjaxRequest(getAllLocationsByPinCode,'post',requestData,false,'html');

	var locations = jQuery.parseJSON(response);
	if(locations.length > 0){
		var loc = locations[0];
		$('#location').val(loc.locId);
	}
}

function getPincode() {
	var id = document.getElementById("location").value;
	var orgId =null;
	if($('#orgId').val()!=undefined){
		orgId =$('#orgId').val();
	}

	var getPinCodeByLocation = "grievance.html?getPincodeByLocationId";
	var requestData = {"id":id,"orgId" : orgId};
	var response = __doAjaxRequest(getPinCodeByLocation,'post',requestData,false,'html');
	if(response){
		var obj = jQuery.parseJSON(response);
	}
	if(!$.isEmptyObject(response)){
		$('#Pincode2').empty();
		if(response!=0){
			$('#Pincode2').val(response);
		}else
		{
			$('#Pincode2').val('');
		}
	}else{
		$('#Pincode2').val('');
	}
}

function getOrganisation() {

	var distId = $('#district').val();
	$('#orgId').html('');
	$('#orgId').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
	$('#departmentComplaint').html('');
	$('#departmentComplaint').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
	$('#location').html('');
	$('#location').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
	$('#complaintType').html('');
	$('#complaintType').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
	if(distId!="" &&  distId!=0){
		var requestData = {"districtId":$('#district').val()}
		var response = __doAjaxRequest('grievance.html?grievanceOrganisations','post',requestData,false,'json');
		$.each(response, function(index, value) {
			$('#orgId').append($("<option></option>").attr("value",value.lookUpId).attr("code",value.lookUpCode).text(value.lookUpDesc));
		});
		$('#orgId').trigger('chosen:updated');	
	}

}

function generateOTP()
{

	var	formName =	findClosestElementId($("#care"), 'form');
	var theForm	=	'#'+formName;
	var url	=	$(theForm).attr('action')+"?sendOTP";
	var data	=	"mobileNo="+$("#MobileNumber").val();
	var returnData	=	__doAjaxRequest(url, 'post', data , false,'json');
	if(returnData == 'Y'){
		$(".alert").hide();	
		$("#btnOTP").attr('disabled',true);
		$(".otp").html(getLocalMessage("care.otpNote"));	
		setTimeout(function(){
       	 enableSubmit(); //this will send request again and again;
          }, 180000);
	}
	if(returnData == 'N'){

		$(".alert").html(getLocalMessage("care.validation.error.mobileNumber"));
		$(".alert").show();	
	}
	
}
function enableSubmit()
{
	$("#btnOTP").attr('disabled',false);
}

function getDepartment(){
	var org =$('#orgId').val();
	$('#departmentComplaint').html('');
	$('#departmentComplaint').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
	$('#location').html('');
	$('#location').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
	$('#complaintType').html('');
	$('#complaintType').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
	if(org !="" && org!="0"){
		var requestData = {"orgId":$('#orgId').val()}
		var response = __doAjaxRequest('grievance.html?grievanceDepartments','post',requestData,false,'json');

		$.each(response, function(index, value) {
			$('#departmentComplaint').append($("<option></option>").attr("value",value.lookUpId).attr("code",value.lookUpCode).text(value.lookUpDesc));
		});
		$('#departmentComplaint').trigger('chosen:updated');	
	}

}

//  GeoLocation 
function getGeoLocation() {
	if (navigator.geolocation) {
		handleGeoLocationPermission();
	} else { 
		onsole.warn("Geolocation is not supported by this browser.");
	}
}

function handleGeoLocationPermission() {
	
	var geoSettings = {
			enableHighAccuracy: true,
			timeout: 5000,
			maximumAge: 0
	}

	navigator.permissions.query({name:'geolocation'}).then(function(result) {
		if (result.state == 'granted') {
			reportGeoLocationPermission(result.state);
			navigator.geolocation.getCurrentPosition(revealPosition);
		} else if (result.state == 'prompt') {
			reportGeoLocationPermission(result.state);
			navigator.geolocation.getCurrentPosition(revealPosition,positionDenied,geoSettings);

		} else if (result.state == 'denied') {
			reportGeoLocationPermission(result.state);
			//revokePermission();
		}
		result.onchange = function() {
			reportGeoLocationPermission(result.state);
		}
	});
}

function reportGeoLocationPermission(state) {
	console.log('Permission ' + state);
}

function revealPosition(position){
	
	var lat = position.coords.latitude;
	var long = position.coords.longitude;
	console.log("latitude: "+lat +" longitude: "+ long);
	$("#id_latitude").val(lat);
	$("#id_longitude").val(long);
	//chooseAddr(lat,long);
}

function positionDenied(error){
	console.warn(`ERROR(${error.code}): ${error.message}`);
}


function revokePermission() {
	navigator.permissions.revoke({name:'geolocation'}).then(function(result) {
		report(result.state);
	});
}

function hitForWardZone(){
	if ($('#departmentComplaint').val() > 0) {
		
		var divName = '.content-page';
		var theForm = '#care';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('grievance.html?findWardZone', 'Post', requestData, false,'html');
		
		
		/*var requestData = {
				"orgId" : null,
				"deptId":$('#departmentComplaint').val()
				}*/
    	//var response = __doAjaxRequest('grievance.html?findWardZone','post',requestData,false,'html');
		
        if(response=="") {
        	//ask to samadhan sir what to do when operation prefix not define for department
    		
    		return false;
			} else{
				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				$('#kdmcWardZoneDiv').show();
				
				
			}

	} 
}

function hitOnChangeSubtypeData(){
	if ($('#departmentComplaint').val() > 0) {
		var requestData = {
			"complaintTypeId" : $("#complaintType").find("option:selected").val()
		}
		let response = __doAjaxRequest(
				'grievance.html?checkComplaintTypeData',
				'POST', requestData, false, 'json');
		let residentId=response.residentId;
		let amtDues = response.amtDues;
		let documentReq = response.documentReq;
		let otpValidReq = response.otpValidReq;
		
		if ( residentId == undefined || residentId == null || residentId == 0 || residentId == "N" ) {
			$('#residentDivId').hide();
		} else if(residentId != 0 && residentId != "N"){
			$('#residentDivId').show();
		}
		
		//amtDues set in hidden field
		$('#amtDuesCheck').val(amtDues);
		
		//here document check mandatory or not
		if (documentReq == undefined || documentReq == null || documentReq == 0 || documentReq== "N") {
			$("#careDocLbl").removeClass( "required-control")
		}else if(documentReq != 0  &&  documentReq != "N"){
			$("#careDocLbl").addClass("required-control");
		}
		
		//T#96732 OTP validation by pass
		/*if (otpValidReq == undefined || otpValidReq == null || otpValidReq == 0 || otpValidReq == "N") {
			$("#otpReqDivId").hide();
		}else if(otpValidReq != 0  &&  otpValidReq != "N"){
			$("#otpReqDivId").show();
		}*/
		//D#128713 start
		if (amtDues == undefined || amtDues == null || amtDues == 0 || amtDues== "N") {
			$("#refNoLblId").removeClass( "required-control");
			$("#propertyLblId").removeClass( "required-control");
			$("#waterLblId").removeClass( "required-control");
		}else if(amtDues != 0  &&  amtDues != "N"){
			$("#refNoLblId").addClass("required-control");
			$("#propertyLblId").addClass( "required-control");
			$("#waterLblId").addClass( "required-control");
		}
		//D#128713 end
		
	}
	
}

function labelChange(){
	$('#propertyLblId').hide();
	$('#waterLblId').hide();
	$('#flatNoSelectId').hide();
	$('#refNoLblId').hide();
	$('.refNoClass').hide();
	
	let deptCode  =  $("#departmentComplaint :selected").attr('code');
	if(deptCode =='AS'){
		$('#propertyLblId').show();
		$('.refNoClass').show();
	}else if(deptCode =='WT'){
		$('#waterLblId').show();
		$('.refNoClass').show();
	}else{
		//$('#refNoLblId').show();
		//$('.refNoClass').show();
	}
	
	let flatNO=$("#flatNo").find("option:selected").val();
	if(flatNO!=undefined && flatNO!= "")
		$('#flatNoSelectId').show();
}

function fetchPropertyFlatNo(){
	$('#flatNo').html('');
	$('#flatNo').append($("<option></option>").attr("value","").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
	let reqData = {
			"refNo":$('#extReferNumber').val()
		}
	 flatNoList=  __doAjaxRequest('grievance.html?fetchPropertyFlatNo', 'POST', reqData, false,'json');
	if(flatNoList.length>0 && flatNoList[0]!= null){
		$('#flatNoSelectId').show();
		$.each(flatNoList, function(index, value) {
			 $('#flatNo').append($("<option></option>").attr("value",value).text(value));
		});
		 $('#flatNo').trigger('chosen:updated');
	}else{
		$('#flatNoSelectId').hide();
	}
	return flatNoList;
	
}

function checkForDues(flatNo){
	var requestData = {
			"refNo" : $('#extReferNumber').val(),
			"deptId":$('#departmentComplaint').val(),
			"flatNo":flatNo
		}
		let response = __doAjaxRequest('grievance.html?checkDues','POST', requestData, false, 'json');
		
		if (response == "") {
			//enable the BT
			$('.compSubmit').prop('disabled', false);
			
			
			
		} else {
			//alert MSG for 1st clear the dues  and disable the BT
			$('.compSubmit').prop('disabled', true);
			 
			let message	='<p class="text-blue-2 text-center padding-15">'+ response+'</p>';
    		$(childDivName).addClass('ok-msg').removeClass('warn-msg');
    		$(childDivName).html(message);
    		$(childDivName).show();
    		$('#yes').focus();
    		showModalBox(childDivName);
			return false;
		}	
}

function findOpWardZoneByReferenceNo(){
	//U#113577
	var divName = '.content-page';
	var theForm = '#care';
	var requestData = {};
	//D#137538
	var amtDuesCheck = $('#amtDuesCheck').val();
	requestData = __serializeForm(theForm);
	let response = __doAjaxRequest('grievance.html?findOpWardZoneByReferenceNo', 'Post', requestData, false,'html');
	/*let requestData = {
			"refNo" : $('#extReferNumber').val(),
			"deptId":$('#departmentComplaint').val(),
			"flatNo":$("#flatNo").find("option:selected").val()
			}
	let response = __doAjaxRequest('grievance.html?findOpWardZoneByReferenceNo', 'Post', requestData, false,'html');*/
    if(response=="") {
    	//ask to SAMADHAN sir what to do when ward zone not getting by reference no
		/*var warnMsg = getLocalMessage('ward zone issue') ;
		var message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
		$(childDivName).addClass('ok-msg').removeClass('warn-msg');
		$(childDivName).html(message);
		$(childDivName).show();
		$('#yes').focus();
		showModalBox(childDivName);*/
		return false;
		} else{
			$('.content-page').removeClass('ajaxloader');
			$('.content-page').html(response);
			$('#kdmcWardZoneDiv').show();
			
			//D#129022 start  only for KDMC where Complaint integrate with Property and Water
			//disable ward zone if value already bind  till 5 level
	         let ward1=$('#ward1 :selected').val();
	         if(ward1!= "0" && ward1 != undefined){
	        	 $("#ward1").prop("disabled", true);
	         }else{
	        	 $("#ward1").prop("disabled", false);
	         }
	         
	         let ward2=$('#ward2 :selected').val();
	         if(ward2!= "0" && ward2 != undefined){
	        	 $("#ward2").prop("disabled", true);
	         }else{
	        	 $("#ward2").prop("disabled", false);
	         } 
	         
	         let ward3=$('#ward3 :selected').val();
	         if(ward3!= "0" && ward3 != undefined){
	        	 $("#ward3").prop("disabled", true);
	         }else{
	        	 $("#ward3").prop("disabled", false);
	         }
	        
	         let ward4=$('#ward4 :selected').val();
	         if(ward4!= "0" && ward4 != undefined){
	        	 $("#ward4").prop("disabled", true);
	         }else{
	        	 $("#ward4").prop("disabled", false);
	         }
	         
	         let ward5=$('#ward5 :selected').val();
	         if(ward5!= "0" && ward5 != undefined){
	        	 $("#ward5").prop("disabled", true);
	         }else{
	        	 $("#ward5").prop("disabled", false);
	         }
	       //D#129022 end
	         if($('#extReferNumber').val() != ""  && amtDuesCheck == "Y"){
	        	 checkForDues($("#flatNo").find("option:selected").val());
	         }
	         
		}	
}

function getCurrentLocation() {
	
	  if (navigator.geolocation) {
	    navigator.geolocation.getCurrentPosition(showPosition);
	  } else {
	    x.innerHTML = "Geolocation is not supported by this browser.";
	  }
	}

	function showPosition(position) {
	 /* x.innerHTML = "Latitude: " + position.coords.latitude +
	  "<br>Longitude: " + position.coords.longitude;*/
	  $('#id_latitude').val(position.coords.latitude);
	  $('#id_longitude').val(position.coords.longitude);
	  chooseAddr(position.coords.latitude,position.coords.longitude);
}
	
	function chooseAddr(lat1, lng1)
	{
	 myMarker.closePopup();
	 map.setView([lat1, lng1],18);
	 myMarker.setLatLng([lat1, lng1]);
	 lat = lat1.toFixed(8);
	 lon = lng1.toFixed(8);
	 document.getElementById('id_latitude').value = lat;
	 document.getElementById('id_longitude').value = lon;
	 myMarker.bindPopup("Lat " + lat1 + "<br />Lon " + lng1).openPopup();
	}
	function resetmarker(lat1, lng1)
	{
	 myMarker.closePopup();
	 map.setView([lat1, lng1],13);
	 myMarker.setLatLng([lat1, lng1]);
	 lat = lat1.toFixed(8);
	 lon = lng1.toFixed(8);
	 myMarker.bindPopup("Lat " + lat + "<br />Lon " + lon).openPopup();
	} 
function getAddressforJSon(arr)
	{
		 
		 var logx=[];
		 var laty=[];
		//console.log(arr);
		if(arr['candidates'].length == 0){
			document.getElementById('results').innerHTML = "No Address Found";
			 $('#results').addClass('alert alert-danger');
		}else{
			
			for(i = 0; i < arr['candidates'].length; i++)
				//console.log(arr['candidates'][i]['attributes'].City);
				if(arr['candidates'][i]['attributes'].City =='Kalyan' || arr['candidates'][i]['attributes'].City =='Dombivli')
			  {
				
				logx.push(arr['candidates'][i]['location'].y);
				laty.push(arr['candidates'][i]['location'].x);
				 /* out += "<li class='address' title='Show Location and Coordinates' onclick=''>" + arr['candidates'][i].address + "</li>";  */
				chooseAddr(logx[0],laty[0]);
				 document.getElementById('results').innerHTML = "";
				 $('#results').removeClass('alert alert-danger');
			  }else{
				  document.getElementById('results').innerHTML = "Address Not Found Enter Correct Address";
				  $('#results').addClass('alert alert-danger');
				  resetmarker(startlat,startlon);
				  }
			
				
			}
		
	}

	function addr_search()
	{
	 var inp = $('#Landmark').val();
	 inp=inp.replace(/[^a-zA-Z ]/g, "");
	 var addrstr="&outfields=*&f=json";
	 var xmlhttp = new XMLHttpRequest();
	 var url = "https://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer/findAddressCandidates?&singleLine=" + inp+""+addrstr;
	 xmlhttp.onreadystatechange = function()
	 {
	  
	    var myArr = JSON.parse(this.responseText);
	    getAddressforJSon(myArr);
	 
	 };
	 xmlhttp.open("GET", url, true);
	 xmlhttp.send();
	}
	function hideSearchResult(){
		$('#results').hide();
	}



