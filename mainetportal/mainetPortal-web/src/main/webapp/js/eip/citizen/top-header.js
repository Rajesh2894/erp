/*csrftoken script*/
function csrftoken(e){
	var token = $("meta[name='_csrf']").attr("content");
	$("input[name='_csrf']").val(token) ;
	e.submit();
}
/*TopBar script start */
$(document).ready(function($) {
    $(".pull-left ul li a,.pull-right ul li a").focus(function() {
        $(this).parent().addClass('active');
    });
    $(".pull-left ul li a,.pull-right ul li a").focusout(function() {
        $(this).parent().removeClass('active');
    });

    $('#incfont').click(function() {
        curSize = parseInt($('#text-resize').css('font-size')) + 1;
        if (curSize <= 19)
            $('#text-resize').css('font-size', curSize);
    });
    $('#decfont').click(function() {
        curSize = parseInt($('#text-resize').css('font-size')) - 1;
        if (curSize >= 10)
            $('#text-resize').css('font-size', curSize);
    });
    $('#norfont').click(function() {
        curSize1 = parseInt($('#text-resize').css('font-size', ''));
        $('#text-resize').css('font-size', curSize1);
    });
});
/*TopBar script end */

//JS to open department employee login on another tab (ASCL Portal)
$(document).ready(function() {
	$('a.dept-login').on('click', function() {
		$(this).attr('target', '_blank');
	});
});

function citizenChildPoppup(response) {
    $('#serviceId').val('');
    var childPoppup = '.popup-dialog';
    $(childPoppup).addClass('login-dialog');
    $(childPoppup).html(response);
    $(childPoppup).show();
    showModalBox(childPoppup);
}

function showConfirm(form) {
    var childDivName = '.child-popup-dialog';
    var message = ' ';
    var msg1 = getLocalMessage("com.link.red1");
    var msg2 = getLocalMessage("com.link.red2");
    var btnMsg = getLocalMessage("bt.ok");
    message += '<div class="col-sm-12 padding-10 text-center">';
    message += '<h3>' + msg1 + ' </br>' + msg2 + '</h3>';
    message += '<div class="col-sm-12 text-center padding-10">';
    message += '<input type="button" class="btn btn-success" value=' + btnMsg + ' onclick=OpenNewTab("' + form + '"); />';
    message += '</div>';
    message += '</div>';
    $(childDivName).html(message);
    showModalBox(childDivName);
}

function OpenNewTab(url) {
    var win = window.open(url, '_blank');
    win.focus();
    closeFancyOnLinkClick('.child-popup-dialog');
}

function openEIPGuideLines(data) {
    var myWindow = window.open("", "MsgWindow1", "scrollbars=1,width=800,height=600");
    var result = __doAjaxRequest("CitizenContactUs.html?showPage", 'GET', data, false);
    myWindow.document.write(result);
}
/*Child Popup Dialog end */

function changeLanguage(url) {
    var array = document.URL.split('/');
    array = array[array.length - 1];
    url = url + '&url=' + array;
    window.location.href = url;
}

/*SetCookie script start*/
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function setcontrast(arg) {
    if (arg == "O") {
        var d = new Date();
        d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
        var expires = "expires=" + d.toGMTString();
        document.cookie = "accessibilityCol" + "=" + 'O' + "; " + expires;
        var user = getCookie("accessibility");
    }
    if (arg == "B") {
        var d = new Date();
        d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
        var expires = "expires=" + d.toGMTString();
        document.cookie = "accessibilityCol" + "=" + 'B' + "; " + expires;
    }
    if (arg == "G") {
        var d = new Date();
        d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
        var expires = "expires=" + d.toGMTString();
        document.cookie = "accessibilityCol" + "=" + 'G' + "; " + expires;
    }
    location.reload(window.location.href);
}
/*SetCookie script end*/

$(document).ready(function() {
if($(".navigation .section-nav li").length < 15){
	if ((navigator.userAgent.indexOf('MSIE') != -1) || (navigator.userAgent.match(/Trident\/7\./)) || (navigator.userAgent.indexOf('Edge') != -1))  { 
		$('.navigation ul').css('display','table');
	}else{
	$('.navigation ul').css('display','grid');}
}
else{$('.navigation ul').css('display','block')}
});


//Code Added by ABM2144 for browser detection below IE10 and redirecting to compatibility page
var IE = "Explorer";
var path = "browser.html"
var BrowserDetect = {
        init: function () {
            this.browser = this.searchString(this.dataBrowser) || "Other";
            this.version = this.searchVersion(navigator.userAgent) || this.searchVersion(navigator.appVersion) || "Unknown";
        },
        searchString: function (data) {
            for (var i = 0; i < data.length; i++) {
                var dataString = data[i].string;
                this.versionSearchString = data[i].subString;

                if (dataString.indexOf(data[i].subString) !== -1) {
                    return data[i].identity;
                }
            }
        },
        searchVersion: function (dataString) {
            var index = dataString.indexOf(this.versionSearchString);
            if (index === -1) {
                return;
            }

            var rv = dataString.indexOf("rv:");
            if (this.versionSearchString === "Trident" && rv !== -1) {
                return parseFloat(dataString.substring(rv + 3));
            } else {
                return parseFloat(dataString.substring(index + this.versionSearchString.length + 1));
            }
        },

        dataBrowser: [
            {string: navigator.userAgent, subString: "Edge", identity: "MS Edge"},
            {string: navigator.userAgent, subString: "MSIE", identity: "Explorer"},
            {string: navigator.userAgent, subString: "Trident", identity: "Explorer"},
            {string: navigator.userAgent, subString: "Firefox", identity: "Firefox"},
            {string: navigator.userAgent, subString: "Opera", identity: "Opera"},  
            {string: navigator.userAgent, subString: "OPR", identity: "Opera"},  
            {string: navigator.userAgent, subString: "Chrome", identity: "Chrome"}, 
            {string: navigator.userAgent, subString: "Safari", identity: "Safari"}       
        ]
    };
    
    BrowserDetect.init();
	if((BrowserDetect.browser==IE)&&(BrowserDetect.version<10)){
	$.ajax({
	  url: path,
	  cache: false,
	success : function(response) {					
		$("body").html(response);
	},});}
//Code Added by ABM2144 for browser detection below IE10 and redirecting to compatibility page    



