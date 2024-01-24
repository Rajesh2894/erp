function openCitizenGuideLines(data) {
	var myWindow = window.open("", "MsgWindow3", "scrollbars=1,width=800,height=600");
	var result = __doAjaxRequest("CitizenContactUs.html?showPage", 'GET', data, false);
		myWindow.document.write(result);
	}
	function openRelatedForm(url, elem) {
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', url);
		$("#postMethodForm").submit();
	}
	
	function breadcrumb(data) {
		if(data!=null && data!=''){
			var array = data.split("+");
			var breadcrumbdata='<li><a href="CitizenHome.html"><i class="fa fa-home"></i>'+ getLocalMessage("menu.home")+'</a></li>';
				$.each(array, function(i) {
					breadcrumbdata=breadcrumbdata +'<li>'+array[i]+'</li>';
				});
				localStorage['breadCrumbDataPortal'] = breadcrumbdata;
		}
	}

function breadcrumbMultiLang(data,dataReg) {
		
		if(data!=null && data!=''){
			var array = data.split("+");
			var message=getLocalMessage('menu.home.eng');
			var breadcrumbdata='<li><a href="CitizenHome.html"><i class="fa fa-home"></i> '+message+' </a></li>';
				$.each(array, function(i) {
					breadcrumbdata=breadcrumbdata +'<li>'+array[i]+'</li>';
				});
				localStorage['breadCrumbDataPortal'] = breadcrumbdata;
		}
		if(dataReg!=null && dataReg!=''){
			var array = dataReg.split("+");
			var message=getLocalMessage('menu.home.reg');
			var breadcrumbdataReg='<li><a href="CitizenHome.html"><i class="fa fa-home"></i> '+message+' </a></li>';
				$.each(array, function(i) {
					breadcrumbdataReg=breadcrumbdataReg +'<li>'+array[i]+'</li>';
				});
				localStorage['breadCrumbdataRegPortal'] = breadcrumbdataReg;
		}
	}	
/*Mega menu properties script*/
var golablid;
jQuery(document).ready(function($) {
	$("ul.dropdown-menu, ul.dropdown-menu li").each(function() {
		var elem = $(this);
		if (elem.children().length == 0) {
			elem.remove();
		}
		if ($('ul.dropdown-menu li').has('ul')) {
			$(this).parent().addClass('dropdown-submenu');
		}
	});
});

/*TopBar script start */
$(document).ready(function($) {
	$(".navigation a, #mobile a").removeAttr('title');	
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
	