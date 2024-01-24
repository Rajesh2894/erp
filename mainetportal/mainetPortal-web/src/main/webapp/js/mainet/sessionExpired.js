$(document).ready(function(){
	
	   //Disable f5 and browser refresh using JQuery
	
	    //this code handles the F5/Ctrl+F5/Ctrl+R
	    document.onkeydown = checkKeycode
	    
	    function checkKeycode(e) {
	     
	    	var keycode;
	    	
	        if (window.event)
	            keycode = window.event.keyCode;
	        else if (e)
	            keycode = e.which;

	      
	        // Mozilla firefox
	        if ($.browser.mozilla || $.browser.chrome) {
	        	if (keycode == 116 ||(e.ctrlKey && keycode == 82)) {
	                if (e.preventDefault)
	                {
	                    e.preventDefault();
	                    e.stopPropagation();
	                }
	            }
	        } 
	        // IE
	        else if ($.browser.msie) {
	        	
	            if (keycode == 116 || (window.event.ctrlKey && keycode == 82)) {
	                window.event.returnValue = false;
	                window.event.keyCode = 0;
	                window.status = "Refresh is disabled";
	            }
	        }
	        
          else  {
	        	
        	  if (keycode == 116 ||(e.ctrlKey && keycode == 82)) {
	                if (e.preventDefault)
	                {
	                    e.preventDefault();
	                    e.stopPropagation();
	                }
	            }
	        }
	        
	       
	    }
	
	
});



var diag = createNewDialog();



var msecPageLoad;
var msecTimeOut;
var msecWarning;
var expiredWarn=getLocalMessage("session.expired.warning.message");
var btCont=getLocalMessage("bt.Continue");
var expiredWarnMsgDtl1=getLocalMessage("session.expired.warning.message.detail1");
var expiredWarnMsgDtl2=getLocalMessage("session.expired.warning.message.detail2");
var sessExpired=getLocalMessage("session.expired.message");
var btOk=getLocalMessage("bt.ok");
var expiredMsgDtl1=getLocalMessage("session.expired.meassage.detail1");

	
SessionTimerInit();

function SessionTimerInit() {
    SetPageTimes();
    setTimeout("ShowPendingTimeoutDialog()", msecWarning);
}

function createNewDialog() {
    return $('<div></div>');
}

/*function SetPageTimes() {
    msecPageLoad = new Date().getTime();
    msecTimeOut = (<%=session.getMaxInactiveInterval()%> * 60 * 1000);
    msecWarning = (<%=session.getMaxInactiveInterval()%> * 60 * 1000 * .75);  // .75 = 75% of total timeout variable. i.e. if timeout is set for 20 minutes, the dialog should pop up after 15 minutes
    msecWarning = 0;
}*/ 


function SetPageTimes() {
    msecPageLoad = new Date().getTime();
    msecTimeOut = (sessionTime * 1000);
    msecWarning = (sessionTime * 1000 * .92);    /*.92 .75 = 75% of total timeout variable. i.e. if timeout is set for 20 minutes, the dialog should pop up after 15 minutes --%> */
    //msecWarning = 0;
    //alert("msecWarning "+msecWarning);
}
function ShowPendingTimeoutDialog() {
	
    diag.dialog({
        autoOpen: false,
        title: expiredWarn,
        position: { my: "center center", at: "center center", of: "#pagecontainer" },
        closeText: "hide",
        resizable: false,
        draggable: false,
        modal: true,
       buttons:[{
        	text:btCont ,
            click: function() {
              ResetTimeout();
            },
            }]
    });

    UpdateTimeoutMessage();
    diag.dialog("open");
}

function ResetTimeout() {
    diag.dialog("close");
    __doAjaxRequest('CitizenHome.html?sessionHit','post','',false);
    SessionTimerInit();
}

function UpdateTimeoutMessage() {
    
    var msecElapsed = (new Date().getTime()) - msecPageLoad;
    var timeLeft = msecTimeOut - msecElapsed; //time left in miliseconds

    if(timeLeft <= 0)
    {
        RedirectToWelcomePage();
    }
    else
    {
        var minutesLeft = Math.floor(timeLeft / 60000);
        var secondsLeft = Math.floor((timeLeft % 60000) / 1000);
        var sMinutesLeft = ("00" + (minutesLeft).toString()).slice(-2) + ":";
        var sSecondsLeft = ("00" + (secondsLeft).toString()).slice(-2);

        diag.html("<p>"+expiredWarnMsgDtl1+"<br /><br />"+expiredWarnMsgDtl2+ sMinutesLeft + sSecondsLeft + "</p>");
        setTimeout("UpdateTimeoutMessage()", 50);
    }
}

function RedirectToWelcomePage() {
	//$.fancybox.close();
    var diagRedirect = createNewDialog();

    diag.dialog("close");
    diagRedirect.dialog({
        autoOpen: false,
        title: sessExpired,
        position: { my: "center center", at: "center center", of: "#pagecontainer" },
        closeText: "hide",
        resizable: false,
        draggable: false,
        modal: true,
        buttons:[{
        	text: btOk,
            click: function() {
               window.location = "CitizenHome.html";
               diagRedirect.dialog("close");
            },
         }]
    });
    diagRedirect.html("<p>"+expiredMsgDtl1+"</p>");
    diagRedirect.dialog("open");
}

function disableF5(e) { 
	if ((e.which || e.keyCode) == 116) e.preventDefault();
	};