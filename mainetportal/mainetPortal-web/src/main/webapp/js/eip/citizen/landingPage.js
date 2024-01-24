$(document).ready(function() {
    //$(".top-summary").shapeshift();
	 $("#countuser2").attr("data-value", (getCookie("countuser") == null  || getCookie("countuser") == "") ? 0 : getCookie("countuser"));
		$("#countuser2").animateNumbers($("#countuser2").attr("data-value"), true, parseInt($("#countuser2").attr("data-duration")));

});

(function($) {
    $(document).ready(function() {
//        $('a[target=_blank]').click(function(e) {
//            e.preventDefault();
//            //do what you want here
//        });
    });
})(jQuery);

//(function($) {
//    $(window).load(function() {
//        var content = $(".scrolllistbox"),
//            autoScrollTimer = 10000,
//            autoScrollTimerAdjust, autoScroll;
//        content.mCustomScrollbar({
//            scrollButtons: {
//                enable: false
//            },
//            callbacks: {
//                whileScrolling: function() {
//                    autoScrollTimerAdjust = autoScrollTimer * this.mcs.topPct / 12000;
//                },
//            }
//        });
//        content.addClass("auto-scrolling-on auto-scrolling-to-bottom");
//        AutoScrollOn("bottom");
//
//        $(".auto-scrolling-toggle").click(function(e) {
//            e.preventDefault();
//            if (content.hasClass("auto-scrolling-on")) {
//                AutoScrollOff();
//            } else {
//                if (content.hasClass("auto-scrolling-to-top")) {
//                    AutoScrollOn("top", autoScrollTimerAdjust);
//                } else {
//                    AutoScrollOn("bottom", autoScrollTimer - autoScrollTimerAdjust);
//                }
//            }
//        });
//
//        function AutoScrollOn(to, timer) {
//            if (!timer) {
//                timer = autoScrollTimer;
//            }
//            content.addClass("auto-scrolling-on").mCustomScrollbar("scrollTo", to, {
//                scrollInertia: timer,
//                scrollEasing: "easeInOutSmooth"
//            });
//            autoScroll = setTimeout(function() {
//                if (content.hasClass("auto-scrolling-to-top")) {
//                    AutoScrollOn("bottom", autoScrollTimer - autoScrollTimerAdjust);
//                    content.removeClass("auto-scrolling-to-top").addClass("auto-scrolling-to-bottom");
//                } else {
//                    AutoScrollOn("top", autoScrollTimerAdjust);
//                    content.removeClass("auto-scrolling-to-bottom").addClass("auto-scrolling-to-top");
//                }
//            }, timer);
//        }
//
//        function AutoScrollOff() {
//            clearTimeout(autoScroll);
//            content.removeClass("auto-scrolling-on").mCustomScrollbar("stop");
//        }
//
//    });
//})(jQuery);


var childDivName = '.child-popup-dialog';

function closelandingPageImage() {
    $(childDivName).hide();
    return false;
}

function getStatus1(event) {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode == '13') {
        getServiceStatus();
    }
}

function getStatus2(event) {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode == '13') {
        getStatus();
    }
}

function redirectWater() {
    if ($("#BillPayment").val() == 2) {
        window.open('WaterBillPayment.html', '_blank');
    }
}
/* ***********************Get Status Js Start******************* */
function getStatus() {
    var rtiId = $('#rtiId').val();
    var response = getLocalMessage('eip.citizen.noNotEnter');

    if (rtiId) {
        var postdata = 'rtiId= ' + rtiId;

        response = __doAjaxRequest(
            'RtiApplicationStatus.html?getRtiStatus', 'POST',
            postdata, false, 'html');
    }
    citizenChildPoppup(response);

}

function getServiceStatus() {
    var serviceId = $('#serviceId').val();
    var response = '<html><span>' +
        getLocalMessage('service.noNotEnter') + '</span></html>';

    if (serviceId) {
        var postdata = 'serviceId= ' + serviceId;
        response = __doAjaxRequest(
            'ServiceApplicationStatus.html?getServiceStatus',
            'POST', postdata, false, 'html');
    }
    citizenChildPoppup(response);

}

function citizenChildPoppup(response) {
    $('#serviceId').val('');
    var childPoppup = '.popup-dialog';
    $(childPoppup).addClass('login-dialog');
    $(childPoppup).html(response);
    $(childPoppup).show();
    showModalBox(childPoppup);
}


function getServiceStatus2() {
    var serviceId = $('#serviceId').val();
    var response = '<html><span>' +
        getLocalMessage('service.noNotEnter') + '</span></html>';

    if (serviceId) {
        var postdata = 'serviceId= ' + serviceId;
        response = __doAjaxRequest(
            'ServiceApplicationStatus.html?getServiceStatus',
            'POST', postdata, false, 'html');
        $("#demo1-home").html(response)
        return false;
    } else {
        /*$("#demo2-home").html(response)*/
        return false;
    };
    //citizenChildPoppup1(response);
    //alert(response);

    return false;
}

function resetServiceId()
{
	$("#serviceId").val('');
	$(".alert").hide();
}

$(document).ready(function() {
						$('.error-div').hide();
						$('#submitMunci').click(
										function(event) {
										
											var selectedMunicipal = $(
													"#selectedOrg").find(
													"option:selected").attr(
													'value');
											var errorList = [];
											if(selectedMunicipal ==0 || selectedMunicipal ==-1){
												var msg   = getLocalMessage('eip.landingpage.urbanlocal.msg');
												errorList.push(msg);
												showError(errorList);
												return false;
											}
											var url = 'ULBHome.html?ulbDomain';
											var data = "orgId="
													+ selectedMunicipal;
											var ulbURL = __doAjaxRequest(url,
													'post', data, false, 'json');
											console.log("urlIndex: "+ulbURL);
											if (ulbURL) {
												var curURL = "" + document.location.href;
												var tempURL = document.location.href.substring(0,curURL.lastIndexOf("/"));
												console.log("tempURL: "+tempURL);
												var urlIndex = ulbURL.indexOf(tempURL);
												console.log("urlIndex: "+urlIndex);
											 if(urlIndex == -1 && ulbURL!='Internal Server Error.') {
													window.open(ulbURL, '_blank');
													return false;
												} 
											 else
											 {
											 window.location =document.location.href;
											 }
											}else{
												ulbURL='ULBHome.html?resetULB'+'&orgId='+ selectedMunicipal;
												//window.open(ulbURL, '_blank');
												window.location.href=ulbURL;
											}
										});
						
						$('#submitMunci1').click(
								function(event) {
									
									var selectedMunicipal = $(
											"#selectedOrg1").find(
											"option:selected").attr(
											'value');
									var errorList = [];
									if(selectedMunicipal ==0 || selectedMunicipal ==-1){
										var msg   = getLocalMessage('eip.landingpage.urbanlocal.msg');
										errorList.push(msg);
										showError(errorList);
										return false;
									}
									var url = 'ULBHome.html?ulbDomain';
									var data = "orgId="
											+ selectedMunicipal;
									var ulbURL = __doAjaxRequest(url,
											'post', data, false, 'json');
									console.log("urlIndex: "+ulbURL);
									if (ulbURL) {
										var curURL = "" + document.location.href;
										var tempURL = document.location.href.substring(0,curURL.lastIndexOf("/"));
										console.log("tempURL: "+tempURL);
										var urlIndex = ulbURL.indexOf(tempURL);
										console.log("urlIndex: "+urlIndex);
									 if(urlIndex == -1 && ulbURL!='Internal Server Error.') {
											window.open(ulbURL, '_blank');
											return false;
										} 
									 else
									 {
									 window.location =document.location.href;
									 }
									}else{
										ulbURL='ULBHome.html?resetULB'+'&orgId='+ selectedMunicipal;
										//window.open(ulbURL, '_blank');
										window.location.href=ulbURL;
									}
								});
					});


ScrollRate = 100;

function scrollStatistics_init() {
    DivElmnt = document.getElementById('imp_statistic_scroll');
    ReachedMaxScroll = false;

    DivElmnt.scrollTop = 0;
    PreviousScrollTop = 0;

    ScrollInterval = setInterval('scrollStatistics()', ScrollRate);
}

function scrollStatistics() {

    if (!ReachedMaxScroll) {
        DivElmnt.scrollTop = PreviousScrollTop;
        PreviousScrollTop++;

        ReachedMaxScroll = DivElmnt.scrollTop >= (DivElmnt.scrollHeight - DivElmnt.offsetHeight);
    } else {
        ReachedMaxScroll = (DivElmnt.scrollTop == 0) ? false : true;

        DivElmnt.scrollTop = PreviousScrollTop;
        PreviousScrollTop--;
    }
}

function pauseStatistics() {
    clearInterval(ScrollInterval);
}

function resumeStatistics() {
    PreviousScrollTop = DivElmnt.scrollTop;
    ScrollInterval = setInterval('scrollStatistics()', ScrollRate);
}
/*script for scrolling statistics END*/

function onDistrictChange(obj) {
    $('#selectedMunicipal').find('option:gt(0)').remove();
    var distid = $(obj).val();
    if (distid > 0) {
        var url = 'Home.html?GetULBs';
        var data = "distId=" + distid;
        var lookUpList = __doAjaxRequest(url, 'post', data, false, 'json');
        var optionsAsString = '';
        $.each(lookUpList, function(index) {
            var lookUp = lookUpList[index];
            optionsAsString += '<option value="' + lookUp.orgid + '" code="' + lookUp.orgShortNm + '">' + lookUp.orgname + '</option>';
        });
        $('#selectedMunicipal').append(optionsAsString);
    }
}


function readMore(data) {
    var errMsgDiv = '.msg-dialog-box';
    $(errMsgDiv).html(data);
    showModalBox(errMsgDiv);
    return false;
}

function showError(errorList) {
    var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button>';

    errMsg += '<ul>';

    $.each(errorList, function(index) {
        errMsg += '<li>' + errorList[index] + '</li>';
    });

    errMsg += '</ul>';

    $('.error-div').html(errMsg);
    $(".error-div").show();
}

function closeOutErrBox() {
    $('.error-div').hide();
}

function OpenNewTab(url) {
    var win = window.open(url, '_blank');
    win.focus();
    closeFancyOnLinkClick('.child-popup-dialog');

}

$(document).ready(function($) {
    $("#service-carousel").owlCarousel({
        items: 3
    });
});


function getStatusForm(){
	var url = "grievance.html?grievanceStatus";
	var response = __doAjaxRequest(url,'post','',false,'html');
	if(response != ''){
		$('#complaint_status').html(response);
	}
}
$('#resetButton').click(function(event) {
	$('#searchString').val('');
	$('#status').html('');
	$('#errorDivId').hide();
});

	$('#submitButton').click(function(event) {
		var errorList = [];
			var appId = $("#searchString").val();
			if(appId==''){
				var msg   = getLocalMessage('invalid.appNo');
				errorList.push(msg);
				showError(errorList);
				return false;
			}
			var url="CitizenHome.html";
			var divName	=	"#status";
		    var requestData = "appId="+appId;
			var ajaxResponse	=	doAjaxLoading(url+'?viewFormHistoryDetails', requestData, 'html', divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			$(divName).show();
			return false;
	});
	$('#submitButtonCare').click(function(event) {
		var errorList = [];
			var appId = $("#searchString").val();
			if(appId==''){
				var msg   = getLocalMessage('invalid.appNo');
				errorList.push(msg);
				showError(errorList);
				return false;
			}
			var url="CitizenHome.html";
			var divName	=	"#status";
		    var requestData = "appId="+appId;
			var ajaxResponse	=	doAjaxLoading(url+'?viewFormHistoryDetailslogin', requestData, 'html', divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			$(divName).show();
			return false;
	});
	
function changeULBbylink(orgId){
	debugger;
		var selectedMunicipal =orgId;
	var errorList = [];
	if(selectedMunicipal ==0 || selectedMunicipal ==-1){
	var msg   = getLocalMessage('eip.landingpage.urbanlocal.msg');
	errorList.push(msg);
	showError(errorList);
	return false;
	}
	var url = 'ULBHome.html?ulbDomain';
	var data = "orgId="
		+ selectedMunicipal;
	var ulbURL = __doAjaxRequest(url,
		'post', data, false, 'json');
	console.log("urlIndex: "+ulbURL);
	if (ulbURL) {
	var curURL = "" + document.location.href;
	var tempURL = document.location.href.substring(0,curURL.lastIndexOf("/"));
	console.log("tempURL: "+tempURL);
	var urlIndex = ulbURL.indexOf(tempURL);
	console.log("urlIndex: "+urlIndex);
	if(urlIndex == -1 && ulbURL!='Internal Server Error.') {
		window.open(ulbURL, '_blank');
		return false;
	} 
	else
	{
	window.location =document.location.href;
	}
	}else{
	ulbURL='ULBHome.html?resetULB'+'&orgId='+ selectedMunicipal;
	//window.open(ulbURL, '_blank');
	window.location.href=ulbURL;
}
}