
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" dir="ltr">
<head>

<head>
<meta charset="UTF-8">
<title>Citizen :: About Us</title>
<link rel="shortcut icon" href="assets/img/favicon.ico">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<!-- Base Css Files for Global -->
<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
<!-- <link href="assets/libs/fontello/css/fontello.css" rel="stylesheet" /> -->
<link href="../../assets/libs/animate-css/animate.min.css" rel="stylesheet" />
<link href="../../assets/libs/pace/pace.css" rel="stylesheet" />
<link href="../../assets/css/style.css" rel="stylesheet" type="text/css" />
<link href="../../assets/css/theme.css" rel="stylesheet" type="text/css" />
<link href="../../assets/css/style-responsive.css" rel="stylesheet" />
<link href="../../assets/libs/lightbox/jquery.fancybox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../../css/QandA/alertify.css" type="text/css" />
<script src="js/mainet/jquery-1.9.1.js"></script>
<script src="js/mainet/jquery.min.js"></script>
<script src="../../assets/libs/bootstrap/js/bootstrap.min.js"></script>
<script src="js/mainet/jquery-ui.js"></script>
<%-- <%@ include file="/jsp/tiles/landingHeaderPage.jsp" %> --%>
<meta charset="UTF-8">
<title>Privacy Policy</title>
<link rel="shortcut icon" href="assets/img/favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<!-- Base Css Files for Global -->
<link href="../../assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="../../assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
<link href="../../assets/libs/pace/pace.css" rel="stylesheet" />
<link href="../../assets/css/style.css" rel="stylesheet" type="text/css" />
<link href="../../assets/css/style-responsive.css" rel="stylesheet" />

<script>
function setCookie(cname,cvalue,exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname+"="+cvalue+"; "+expires;
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

function setcontrast(){

var rates = document.getElementsByName('stqc_contrastscheme');
var ratevalue;
for(var i = 0; i < rates.length; i++){
    if(rates[i].checked){
        ratevalue = rates[i].value;

          if( ratevalue =="HC")
        	{
        		    var d = new Date();
        		    d.setTime(d.getTime() + (60*1000));
        		    var expires = "expires=" + d.toGMTString();
        		    document.cookie = "accessibility"+"="+'Y'+"; "+expires;
        		    var user=getCookie("accessibility");
        	}
          if(ratevalue =="SC")
        	{
        	  var d = new Date();
  		    d.setTime(d.getTime() + (60*1000));
  		    var expires = "expires=" + d.toGMTString();
  		    document.cookie = "accessibility"+"="+'N'+"; "+expires;

        	}  
    
    }
}
}
</script>

</head>
<body>
<div class="content-page">
	<ol class="breadcrumb">
      <li><a href="index.html"><i class="fa fa-home"></i></a></li>
      <li>About Us</li>
    </ol>
 <div id="content" class="content"> 
 <div class="widget">
  <div class="widget-header">
    <h2><strong>Accessibility</strong> </h2>
  </div>
  <div class="widget-content padding-lt">
  
  <div class="content clear-block">
    <div class="accesform"><div>
<p class="space">Accessibility options enables you to increase or decrease the font size and/or change color scheme of this website according to your preferences. <br>All of us must have come across situations where we need the services of talented people but can't manage to get one, because of certain constraints.</p>

<form name="" method="post" action="" id="frmchangetextsize1">
    <h4>Change Text Size</h4>
	<fieldset id="textsizeoptions">                                    
        <legend>Text Size:</legend>
	<div><input type="radio" id="Largest" name="stqc_textsize" value="Largest" class="leftspace Size"><label for="Largest">Largest</label></div>
	 <div><input type="radio" id="Larger" name="stqc_textsize" value="Larger" class="leftspace Size"><label for="Larger">Larger</label> </div>
	 <div><input type="radio" id="Medium" name="stqc_textsize" value="Medium" class="leftspace Size"><label for="Medium">Medium</label> </div>
	 <div><input type="radio" id="Smaller" name="stqc_textsize" value="Smaller"  class="leftspace Size"><label for="Smaller">Smaller</label> </div>
	 <div><input type="radio" id="Smallest" name="stqc_textsize" value="Smallest" class="leftspace Size"><label for="Smallest">Smallest</label></div>
	</fieldset>
	
		<h4>Change Contrast</h4>
    <fieldset id="contrastoptions">                                       
     <legend>Contrast Schemes:</legend>
    <div><input type="radio" id="High" name="stqc_contrastscheme" value="HC" class="leftspace Contrast"><label for="High">High Contrast</label></div> 
	<div><input type="radio" id="Standard" name="stqc_contrastscheme" value="SC"  class="leftspace Contrast"><label for="Standard">Standard Contrast</label></div>
</fieldset>
	
	<h4>Text Spacing</h4>
     <fieldset id="textspacing">
       <legend>Text Spacing:</legend>
	<div><input type="radio" id="Default" name="stqc_textspacing" value="Default"  class="leftspace Spacing"><label for="Default">Default</label></div> 
	<div><input type="radio" id="Wider" name="stqc_textspacing" value="Wider" class="leftspace Spacing"><label for="Wider">Wider</label></div>
	<div><input type="radio" id="Widest" name="stqc_textspacing" value="Widest" class="leftspace Spacing"><label for="Widest">Widest</label></div>
</fieldset>
	
	
    <div class="text-center"><input type="submit" name="applyoptions" value="Apply" class="btn btn-primary " onclick="setcontrast()">
    <a href="javascript:void(0);"
						class="btn btn-primary"
						onclick="setcontrast()">Apply2</a>
    </div>
</form>
</div></div>  </div>
</div>
 </div>

</div>
</div>

<%--  <%@ include file="/jsp/tiles/landingPage-footer.jsp" %> --%>

