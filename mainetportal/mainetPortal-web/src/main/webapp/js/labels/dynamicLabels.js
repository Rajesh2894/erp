
var editProperties = function(btnElem){
var spanValue = $("#id_val_"+btnElem.id).text();

	$("#"+btnElem.id).prop("disabled",true);
	var len = spanValue.length;
    $("#id_val_"+btnElem.id).replaceWith("<input type='text' id='id_text_"+btnElem.id+"' value='"+spanValue+"' width='"+len+"'></input>");
	$("#id_save_"+btnElem.id).prop("disabled",false);
	$("#"+btnElem.id).prop("disabled",true);
};

var postValue = function(index){

	//var token = '';
	/*$.ajax({
		url : "Autherization.html?getRandomKey",
		data : "test=1",
		type : "GET",
		async : false,
		dataType : "text",
		success : function(response) {
			token = response;
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});*/
	
	var key = $("#id_key_"+index).text();
	
	var value = $("#id_text_"+index).val();
	var fileName = $("#selectedPropertiesFile option:selected").text();
	var string=key+'~'+value+'~'+fileName;
	$.ajax({
		url:"DynamicLabelProperties.html?updateProperty",
		//headers   : {"SecurityToken": token},
		type:"POST",
		data: 'string='+string,
		
		
		success:function(data, textStatus, jqXHR){
			var responseString=jqXHR.responseText.replace(/^"(.+(?="$))"$/, '$1');
			$("#id_text_"+index).replaceWith("<span id='id_val_"+index+"'>"+responseString+"</span>");
			$("#id_save_"+index).prop("disabled",true);
			$("#"+index).prop("disabled",false);
		},
		error:function(jqXHR, textStatus, errorThrown){
			alert("error "+errorThrown+"jqXHR "+jqXHR.responseText);
		}
	});
};

function showKyeValueList(obj){
	var fileName=$("#selectedPropertiesFile option:selected").text();

	$.get("DynamicLabelProperties.html?displayEachProperty",{"propertyFileName":fileName},function(responseObj){
		showProperties(responseObj);
	},"json").
	fail(function( jqxhr, textStatus, error){
		var err = textStatus + ', ' + error;

		alert( "Request Failed: " + err);
	});
};

var showProperties = function(responseObj){
	$("#example").find("tr:gt(0)").remove();
	var jsonArray = $.parseJSON(responseObj);
	
	var name = '';
	var value = '';
		
	for(var index in jsonArray){
		
		name = jsonArray[index].name;
		value = jsonArray[index].value;
		
		htm = "<tr role='row'><td id='id_key_"+index+"'>"+name+"</td><span>=</span><td id='id_val_"+index+"'>"+value+"</td><td><a href='#'><i class='fa fa-pencil-square fa-lg' id='"+index+"' title='Edit' onclick='javascript:editProperties(this)'></i></a> " +
        "<a href='#'><i class='fa fa-floppy-o fa-lg' id='id_save_"+index+"' title='Save' onclick='javascript:postValue("+index+")' disabled/></a></td></tr>";
		
		
		$("#example").append(htm);
		
	}
	
	$("#example").DataTable();
};

