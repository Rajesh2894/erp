<script src="js/eip/admin/jquery.session.js"></script> 
<script src="https://www.java.com/js/deployJava.js"></script>
<script>

	   	 window.onload = function(){
    		 if (deployJava.versionCheck('1.6.0_45+') == false) {      

    			showDownLoadPopup();
    		      
    		    } else{
    		    	
    		    	$("#mydiv").hide();
    		   		if($.session.get("myApplet") == undefined){
    		    		$("#i_frame").attr("src", "${userSession.appletURL}");
    		    		$.session.set("myApplet", "0");
        		   		
    		    	}
    		    }
    	 }


	 	function submitForm()
	 	{
	 	
	   		document.forms['jDownload'].submit();
	   		$("#dButton").hide();
	   		$("#loader").show();
	   		$("#guideMsg").show();
	   		$("#pmsg").hide();

			checkForJRE();
		}


	 	function checkForJRE()
	 	{
	 		setInterval(function(){

	   			if (deployJava.versionCheck('1.6.0_45+') == false)
				{

				}
	   			else
		   		{
	   				$("#mydiv").hide();
	   				$("#i_frame").attr("src", "${userSession.appletURL}");
			   		$.session.set("myApplet", "0");
			   		
			   		$('.child-popup-dialog').hide();

			   		location.reload();
			   	}

			  },10000);
		}

    	 function showDownLoadPopup(){

    		var childDivName	=	'.child-popup-dialog';
    		var message=' ';
    		
    		message	+=	'<div align="center">';

    		message	+=  '<img alt="ajax-loader" src="./css/images/ajax-loader.gif" id="loader" />';  
    		
    		message	+=	'<p id="pmsg">You need the latest Java(TM) Runtime Environment. <br\> Click Download to install the latest Java version !!  <br\> <br\> Click below on the Download button to download the Java installer to your computer.</p>';

	    		message	+=  '<div class="buttons btn-fld" align="center">';    		
	    			message	+=	'<input type="button" value="Download" id="dButton" onclick="submitForm();" >';
	    		message	+=	'</div>';
	    		 
				message += '<div id="guideMsg" style="width:100%;color:#000000;margin-bottom:10px;font-family: Trebuchet MS, Helvetica, sans-serif;font-size:15px;" align="left">';

					message	+=  '<div class="regheader" align="left" >  &nbsp; Follow the instructions given below :</div>';
					message += '<table border="0" align="center" style="width:93%;padding:5px;">';
						
							message += '<tr><td>1. </td><td>Once the download is complete.</td></tr>';
							message += '<tr><td>2. </td><td>Open the file you downloaded to start the Java installation.</td></tr>';
							message += '<tr><td>3. </td><td>Click on the run and Install the downloaded file. </td></tr>';
							message += '<tr><td>4. </td><td>You will get success Message once the JAVA insatllation is successfully completed.</td></tr>';
							message += '<tr><td>5. </td><td>You are ready to use website seamelessly now.</td></tr>';
						message += '</table>';
					message += '</div>';
	    		
	    		
		
			message	+=	'</div>';
		
    		
    		$(childDivName).html(message);
    		
    		$("#loader").hide();

    		$("#guideMsg").hide();

    		showModalBox(childDivName);
        }
</script>

<div id="mydiv" style="visibility:hidden;width: 0px;height: 0px;">
	<iframe id="i_frame"></iframe>
</div>

<form action="CitizenHome.html?Download" method="post" name="jDownload" id="jDownload">
	<input type="hidden" name="downloadLink" value="JAVA/jre-7u60-windows-i586.exe">
</form>