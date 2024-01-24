

$(document).ready(function() {	

    $("#datatables").dataTable({
    	"oLanguage" : {
    	    "sSearch" : ""
    	},
    	"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
    	"iDisplayLength" : 5,
    	"bInfo" : true,
    	"lengthChange" : true
        });
	
	
 	 $( "#fromDate" ).datepicker({
 	          dateFormat : 'dd/mm/yy',
 	    		changeMonth : true,
 	    		changeYear : true,
 	    		onClose: function( selectedDate ) {
 	        $( "#toDate" ).datepicker( "option", "minDate", selectedDate );
 	      }
 	    });
 	    
 	    $( "#toDate" ).datepicker({
 	    		dateFormat : 'dd/mm/yy',
 	    		changeMonth : true,
 	    		changeYear : true,
 	    		onClose: function( selectedDate ) {
 	          $( "#fromDate" ).datepicker( "option", "maxDate", selectedDate );
 	      }
 	    });
      
      
      
      
});

function resetForm() {
	$("select").val("").trigger("chosen:updated");
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'DuplicateReceiptPrinting.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}

function searchDetails(){
var errorList = [];
var fromDate = $("#fromDate").val();
var toDate = $("#toDate").val();
var propertyName = $("#propertyName").val();

	errorList = validateObjDetailForm(errorList);
	if (errorList.length == 0) {
	var table = $('#datatables').DataTable();
	table.rows().remove().draw();
	var requestData={'fromDate':fromDate,
					'toDate':toDate,
					'propertyName':propertyName	};
	
	var cdmURL = "DuplicateReceiptPrinting.html";
	var ajaxResponse = __doAjaxRequest(cdmURL + '?getBookingDetails','POST', requestData, false,'json');
		if(ajaxResponse!=null && ajaxResponse!=""){
			var result = [];
			$.each(ajaxResponse, function(index){
				var lookUp = ajaxResponse[index];
				result.push([index+1,lookUp.fromDate,lookUp.toDate,lookUp.propName,lookUp.applicantName,
					'<td >'
					+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onClick="propertyInfo(\''
					+ lookUp.bookingId
					+ '\')" title="View"><i class="fa fa-eye"></i></button>' 
					+ '<button type="button"  class="btn btn-warning btn-sm margin-right-10"  onClick="downoladReceipt(\''
					+ lookUp.receiptId
					+ '\')" title="Print"><i class="fa fa-print"></i></button>'  
					+ '</td>']);
		     });
			table.rows.add(result);
			table.draw()
		}else{
			errorList.push(getLocalMessage("rnl.seacrch.valid.nofound"));
			displayErrorsOnPage(errorList);
		}
	}else {
		displayErrorsOnPage(errorList);
	}
}

function back() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action','DuplicateReceiptPrinting.html');
	$("#postMethodForm").submit();
} 
function validateObjDetailForm(errorList) {
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var propertyName = $("#propertyName").val();

	if(fromDate == '' || fromDate == undefined){
		errorList.push(getLocalMessage("Please Select From date"));
	}if(toDate == '' || toDate == undefined){
		errorList.push(getLocalMessage("Please Select To date"));
	}
	if(propertyName == '' || propertyName == '0'){
		errorList.push(getLocalMessage("Please Select To Property name"));
	}
	return errorList;
}

function propertyInfo(bookId) {	
	var ajaxResponse = __doAjaxRequest('EstateBooking.html?propertyInfo', 'POST', 'bookId='+bookId, false,'html');
	$('.content').html(ajaxResponse);
}
function printReceipt() {
	var requestData = {};
	var URL = 'EstateBooking.html?PrintReport';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	window.open(URL, '_blank');
	window.location.href = "AdminHome.html";
}


function downoladReceipt(receiptId){
	
	 	var data='receiptId='+receiptId;	
		var URL='DuplicateReceiptPrinting.html?receiptDownload';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);
		if(returnData!="" || returnData !=null){
		var title='Payment Receipt';
		var printWindow = window.open('', '_blank');
		printWindow.document.write('<html><head><title>' + title + '</title>');		
		printWindow.document.write(returnData);
		printWindow.document.write('</body></html>');
		printWindow.document.close();	
		}		
}
