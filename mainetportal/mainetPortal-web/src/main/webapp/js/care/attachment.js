docs = [];

$(document).ready(function(){

	$('.hasNumber').blur(function () { 
		this.value = this.value.replace(/[^0-9]/g,'');
	});

	$('.alfaNumric').blur(function () { 
		this.value = this.value.replace(/[^a-zA-Z0-9 ]/g,'');;
	});

	$("#customFields").on('click','.remCF',function(){

		if($("#customFields tr").length != 1){
			$(this).parent().parent().remove();
			reOrderTableIdSequence();
		}
		else{
			var errorList = [];
			errorList.push(getLocalMessage("rl.subChild.deletrw.validtn"));   
			displayErrorsOnPageView(errorList);
		}
		hideAndShowAttachmentGrid();
	});

	hideAndShowAttachmentGrid();
});


function reOrderTableIdSequence() {

	$('.appendableClass tr').each(function(i) {
			var index = i-1;
			$(this).find("label:eq(0)").attr("id", "srno_"+index);
			$(this).find("label:eq(0)").html(i);
			$(this).find("td:nth-child(2)> input").attr("id", "docName_"+index).attr("name", "attachments["+index+"].documentName");
			$(this).find("td:nth-child(3)> select").attr("id", "docType_"+index).attr("name", "attachments["+index+"].documentType");
			$(this).find("td:nth-child(4)> input").attr("id", "docPath_"+index).attr("name", "attachments["+index+"].uploadedDocumentPath");
			$(this).find("td:nth-child(4)> a").attr("id", "doc_"+index);
	});	
}

function displayAttachmentGrid(){	
	
	var url = "grievance.html?getUploaddedFiles";
	var response = __doAjaxRequest(url,'post','',false,'html');
	var obj = JSON.parse(response);
	var docOptions = $(".hiddenDOCprefixSelect").html();

	//alert(obj);
	for (var key in obj) {
		var temp = obj[key].toString();
		var files = temp.split(",");
		//alert(files);
		for(i=0; i<files.length; i++) {
			var val = files[i];
			if(docs.length == 0 || docs.indexOf(val) == -1){
				docs.push(val);		
				var markup = "<tr>"+
				"<td><label id='srno_"+key+"'>"+key+"</label></td>" +
				"<td><input id='docName_"+key+"' name='attachments[0].documentName' type='text' class='form-control animated' value='' maxlength='50'/></td>" +
				"<td><select id='docType_"+key+"' name='attachments[0].documentType' class='form-control mandColorClass animated' data-rule-prefixvalidation='true'>"+docOptions+"</select></td>" +
				"<td><input type='hidden' id='docPath_"+key+"' name='attachments[0].uploadedDocumentPath' value='"+val+"'> " +
						"<a id='doc_"+key+"'href='"+val+"' target='_blank'>"+val.split("\\").pop()+"</a></td>" +
				"<td><a href='javascript:void(0);' data-toggle='tooltip' data-placement='top' class='remCF btn btn-danger btn-sm'><i class='fa fa-minus'></i></a></td>" +
				"</tr>";
				$(".appendableClass").append(markup);
			}
		}
	}
	hideAndShowAttachmentGrid();
	reOrderTableIdSequence();
}


function hideAndShowAttachmentGrid(){
	var rowCount = $('#customFields tr').length;
	if(rowCount >1 ){
		$('#customFields').show();
	}else{
		$('#customFields').hide();
	}
}





