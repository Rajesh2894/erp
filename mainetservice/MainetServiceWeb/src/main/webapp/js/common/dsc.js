$(document).ready(function(){
	 $.ajax({
         type: "GET",
         cache: false,
         url: "https://localhost:6543/Values/GetAllCertificateList",
         contentType: "application/json; charset=utf-8",
         async: false,
         processData: false,
         cache: false,
         success: function (r) {
             var ddl1 = $("[id*=ddl1]");

             ddl1.empty().append('<option selected="selected" value="0">Please select</option>');
             if (r.length != "0") {
                 $.each(r, function () {
                     ddl1.append($("<option></option>").val(this['SeriolNumber']).html(this['CertificateName']));
                 });
             }
             else {
                 alert("No Certification Details found.Please check the ETL signer Service and Provide the Correct Log on Details.")
             }

         },
         error: function (err) {
             console.log('ashish');
             console.log(err);
             console.log('ggg');
             alert("Please Install & start the ETL Signer Service");
         }
     });
});

function ClearLog() {
    
    $.ajax({
        type: "GET",
        cache: false,
        url: "https://localhost:6543/Values/ClearLog",
        contentType: "application/json; charset=utf-8",
        async: false,
        processData: false,
        success: function (res) {
            //alert("log : " + res)
        },
        error: function (err) {
            alert("Something went wrong while clear log files.");
            return;
        }
    });
}

function email(proAssNo, certificateNo, applicationNo,formUrl) {

	var errorList = [];
	var requestData = 'proAssNo=' + proAssNo + '&certificateNo='
			+ certificateNo + '&applicationNo=' + applicationNo;
	var URL = formUrl+"?EmailCertificate";
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	if (returnData == "f") {
		showBoxForApproval(getLocalMessage('property.fail.email'));
	} else {
		showBoxForApproval(getLocalMessage('property.sucess.email'));
	}
}
function showBoxForApproval(succesMessage) {

	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed = getLocalMessage('BirthRegDto.proceed');
	var no = 'No';
	message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
			+ '</p>';

	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
			+ Proceed + '\'  id=\'Proceed\' ' + ' onclick="closeApproval();"/>'
			+ '</div>';
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
}

function closeApproval() {
	window.location.href = 'AdminHome.html';
	$.fancybox.close();
}

function signCertificate(proAssNo, certificateNo,applicationNo,formUrl,coordinate) {

	var errorList = [];
	var DomainUrl=getLocalMessage("domain");
	var divName = '.content-page';
	var requestData = 'proAssNo=' + proAssNo + '&certificateNo='+ certificateNo + '&applicationNo=' + applicationNo;
	var URL = formUrl+"?signCertificate";
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	
	if (returnData == "false") {
		showBoxForApproval(getLocalMessage('property.sign.document'));
	} else if (returnData == "true") {
		fetchAndSignCertificate(applicationNo,formUrl,DomainUrl,coordinate);
	} else {
		showBoxForApproval(getLocalMessage('birth.death.fail.document'));
	}
}

function fetchAndSignCertificate(applicationNo,formUrl,DomainUrl,coordinate) {
	
    var srno = $("[id*=ddl1]").val();
    var txtfolder = applicationNo;
    var txtfilename = applicationNo+".pdf";
    if (txtfolder == "")
    {
        alert('Please enter Folder Name');
        return;
    }
    if (txtfilename == "")
    {
        alert('Please enter File Name');
        return;
    }
    if (srno != "0") {
        var apidata = {
            "folderName": txtfolder,
            "fileName": txtfilename,
            "coordinates":coordinate
        };
        console.log(apidata);
     
        $.ajax({
            type: "POST",
            cache: false,
			url: DomainUrl+"/value/GetFile",
            contentType: "application/json; charset=utf-8",
            async: false,
            processData: false,
            headers: {
            	'Access-Control-Allow-Origin': '*',
            	'Access-Control-Allow-Methods':'GET, POST,OPTIONS',
            	'Access-Control-Allow-Headers':'content-type,x-csrf-token'
            	    },
            data: JSON.stringify(apidata),
            cache: false,
            success: function (r) {
                if (r.result == "no")
                {
                    alert("file not found.Please provide correct folder and file name.")
                    return false;
                }
                if (r.result == "error") {
                    alert("Soemthing went wrong while creating a  sign block.")
                    return false;
                }
                var apidata = {
					"data": r.result,
                    "signkey": "S1",
                    "serial": srno,
                    "istoken":"Yes",
                    "sigimage": "/9j/4AAQSkZJRgABAQEAYABgAAD/4QAiRXhpZgAATU0AKgAAAAgAAQESAAMAAAABAAEAAAAAAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAABAAEDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9/KKKKAP/2Q==",
                };

                $.ajax({
                    type: "POST",
                    cache: false,
                    url: "https://localhost:6543/Values/Signfile",
                    contentType: "application/json; charset=utf-8",
                    async: false,
                    processData: false,
                    data: JSON.stringify(apidata),
                    cache: false,
                    success: function (r) {
                        var apidata = {
                            "data": r.result,
                            "folderName": txtfolder,
                            "fileName": txtfilename 
                        };
						ClearLog();
                        $.ajax({
                            type: "POST",
                            cache: false,
                            url: DomainUrl+"/value/SaveFile",
                            contentType: "application/json; charset=utf-8",
                            async: false,
                            processData: false,
                            data: JSON.stringify(apidata),
                            cache: false,
                            success: function (r) {
                                if (r.result == "Success"){
                                	var destPath=r.signfilepath;
                                	var requestData = 'applicationNo=' + applicationNo + '&destPath='+ destPath;
                                	var saveUrl = formUrl+"?saveSignedCertificate";
                                	var returnData = __doAjaxRequest(saveUrl, 'POST', requestData, false);
                                	if (returnData == "true") {
                                		showBoxForApproval(getLocalMessage('property.success.certificate'));
                                	} else if (returnData == "false") {
                                		showBoxForApproval(getLocalMessage('property.fail.document'));
                                	} 
								}
								else{
									alert(r.result);
								}
                                
                                return;

                            },
                            error: function (err) {
                                return;
                            }
                        });

                    },
                    error: function (err) {                                
                        return;
                    }
                });

            },
            error: function (err) {                     
                return;
            }
        });

      
    }
    else {
        alert('Please select Certificate');
        return;
    }
}