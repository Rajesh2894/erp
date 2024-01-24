

function editForm(obj)
{
	 
	$("#submitdiv").show();
	$("#edit").hide();
	$('input[type=text]').attr('disabled',false);
	$('select').attr("disabled", false);
	$('#trmGroup1').attr('disabled',true);
	$('#trmGroup2').attr('disabled',true);
	$("#addOwner").attr('disabled',false);
	$("#applicationNo").attr('disabled',true);
	$("#addConnection").attr('disabled',false);
	$("#deleteConnection1").attr('disabled',false);
	$("#deleteOwner1").attr('disabled',false);
	
	
}


function showConfirmBoxForSave(obj) {
	var errMsgDiv = '.child-popup-dialog';
	var message = '';
    var no = getLocalMessage('eip.commons.no');
	
	var yes = getLocalMessage('eip.commons.yes');
	
	var sure = getLocalMessage('water.are.sure');

	message += '<h5 class=\'text-center text-blue-2 padding-5\'>'
			+ sure + '</h5>';

	message += '<div class=\'text-center\'><input type=\'button\' class= \'btn btn-success margin-right-10 \' value=\''
			+ yes
			+ '\'  id=\'btnNo\' onclick="showViewFormJsp(\''+obj+'\')"/>'
			+'<input type=\'button\' class= "btn btn-danger" value=\''+no+'\'  id=\'no\' '+ 
			' onclick="closeBox()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
}
function closeBox() {
	$.fancybox.close();
	$('#frmNewWaterForm').trigger("reset");
	
}
