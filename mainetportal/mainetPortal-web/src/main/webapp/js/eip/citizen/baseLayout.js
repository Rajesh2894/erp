(function($){
  $(document).ready(function(){
	  $('a[target=_blank]').click(function(e){
	    e.preventDefault();	//do what you want here
	  }); 
	    var date = new Date();
		date.setTime(date.getTime() + (10 * 1000));
		var expires = "expires=" +date.toGMTString();
		 document.cookie = "ScriptOnOff3"+"="+'ON'+"; "+expires; 
	  });
});

function dispNonLogService(obj) {
	$(obj).attr("class", "active");
	var formData = "dispNonLogInMenu=true";
	$.ajax({
		type : 'POST',
		//dataType: 'application/octet-stream', 
		asynch : false,
		dataType : 'html',
		url : 'CitizenHome.html?dispNonLogInMenu=true',
		success : function(data) {
			//alert(data);
			location.reload();
		},
		error : function(jqXHR, exception) {
			//alert("ERROR:: "+jqXHR);
			if (jqXHR.status === 0) {
				alert('Not connect.\n Verify Network.');
			} else if (jqXHR.status == 404) {
				alert('Requested page not found [404].');
			} else if (jqXHR.status == 500) {
				alert('Internal Server Error [500].');
			} else if (exception === 'parsererror') {
				alert('Requested JSON parse failed.');
			} else if (exception === 'timeout') {
				alert('Time out error.');
			} else if (exception === 'abort') {
				alert('Ajax request aborted.');
			} else {
				alert('Uncaught Error.\n' + jqXHR.responseText);
			}
		}
	});
}
function openDialogD2K(mntid) {
	var token = '';
	$
	.ajax({
		url : "Autherization.html?getRandomKey",
		type : "POST",
		async : false,
		success : function(response) {
			token = response;
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList
					.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});
	showModalBox();
	var d2kUrl = '';
	$.ajax({
		type : 'POST',
		headers : {
			"SecurityToken" : token
		},
		//dataType: 'application/octet-stream', 
		asynch : false,
		dataType : 'json',
		url : 'CitizenHome.html?cfcFormParams=' + mntid,
		success : function(data) {

			window.location.replace(data[0], "_blank");
		},
		error : function(jqXHR, exception) {
			//alert("ERROR:: "+jqXHR);
			if (jqXHR.status === 0) {
				alert('Not connect.\n Verify Network.');
			} else if (jqXHR.status == 404) {
				alert('Requested page not found. [404]');
			} else if (jqXHR.status == 500) {
				alert('Internal Server Error [500].');
			} else if (exception === 'parsererror') {
				alert('Requested JSON parse failed.');
			} else if (exception === 'timeout') {
				alert('Time out error.');
			} else if (exception === 'abort') {
				alert('Ajax request aborted.');
			} else {
				alert('Uncaught Error.\n' + jqXHR.responseText);
			}
		}
	});
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
var resizefunc = [];