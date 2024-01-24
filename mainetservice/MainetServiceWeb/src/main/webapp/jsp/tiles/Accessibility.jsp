
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />

<script type="text/javascript">
$(document).ready(function(){
    var textsize = getCookie("accessibilitytextsize");
    var accessibility = getCookie("accessibility");
    var text="";
    var contrast="";
    switch (textsize) {
    case "LGT":
    	text='Largest';
        break;
    case "LGR":
    	text='Larger';
        break;
    case "MDM":
    	text='Medium';
         break;
    case "SLR":
    	text='Smaller';
         break;
    case "SLT":  
    	text='Smallest';
         break;
    default:
    	text='';
	}
    $("input[name=textsize][value=" + text + "]").prop('checked', true);
    switch (accessibility) {
    case "Y":
    	contrast='HC';
        break;
    case "M":
    	contrast='SC';
        break;
    default:
    	contrast='';
	}
    $("input[name=contrastscheme][value=" + contrast + "]").prop('checked', true);
});
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

		/* var rates = document.getElementsByName('stqc_contrastscheme');
		var ratevalue;
		for(var i = 0; i < rates.length; i++){
		    if(rates[i].checked){
		        ratevalue = rates[i].value; */
		/*   alert(ratevalue); */
		/*   alert(arg+"arg"+window.location.href) */
		if (arg == "O") {
			/*   alert(arg+"O") */
			/* localStorage.setItem('accessibility','Y'); */

			var d = new Date();
			d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
			var expires = "expires=" + d.toGMTString();
			document.cookie = "accessibilityCol" + "=" + 'O' + "; " + expires;
			var user = getCookie("accessibility");
			/*  alert("user"+user); */

		}
		if (arg == "B") {
			/* alert(arg+"B") */
			/*  localStorage.setItem('accessibility','N') */
			var d = new Date();
			d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
			var expires = "expires=" + d.toGMTString();
			document.cookie = "accessibilityCol" + "=" + 'B' + "; " + expires;

		}

		if (arg == "G") {
			/*  alert(arg+"G") */
			/*  localStorage.setItem('accessibility','N') */
			var d = new Date();
			d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
			var expires = "expires=" + d.toGMTString();
			document.cookie = "accessibilityCol" + "=" + 'G' + "; " + expires;

		}
		location.reload(window.location.href);
		/*  alert("Hi");
		 */
		/*   location.reload("AdminHome.html");  */
		/*   window.open("AdminHome.html") */
		/*    }
		} */
	}
	function setcontrast2() {
		var rates = document.getElementsByName('contrastscheme');
		var ratevalue;
		for (var i = 0; i < rates.length; i++) {
			if (rates[i].checked) {
				ratevalue = rates[i].value;
				 //alert(ratevalue); 
				if (ratevalue == "HC") {
					/* localStorage.setItem('accessibility','Y'); */

					var d = new Date();
					d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
					var expires = "expires=" + d.toGMTString();
					document.cookie = "accessibility" + "=" + 'Y' + "; "
							+ expires;
					var user = getCookie("accessibility");
					/*  alert("user"+user); */

				}
				if (ratevalue == "SC") {
					/*  localStorage.setItem('accessibility','N') */
					var d = new Date();
					d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
					var expires = "expires=" + d.toGMTString();
					document.cookie = "accessibility" + "=" + 'M' + "; "
							+ expires;

				}
				/*  alert("Hi");
				 */
				/*   location.reload("AdminHome.html");  */
				/*   window.open("AdminHome.html") */
			}
		}

		var rates2 = document.getElementsByName('textsize');
		/* alert(rates2.length+"rates2"); */
		var ratevalue;
		for (var i = 0; i < rates2.length; i++) {
			if (rates2[i].checked) {
				ratevalue = rates2[i].value;
				//alert(ratevalue);  
				if (ratevalue == "Largest") {
					/* localStorage.setItem('accessibility','Y'); */

					var d = new Date();
					d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
					var expires = "expires=" + d.toGMTString();
					document.cookie = "accessibilitytextsize" + "=" + 'LGT'
							+ "; " + expires;
					var user = getCookie("accessibility");
					/*  alert("user"+user); */

				}
				if (ratevalue == "Larger") {
					/*  localStorage.setItem('accessibility','N') */
					var d = new Date();
					d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
					var expires = "expires=" + d.toGMTString();
					document.cookie = "accessibilitytextsize" + "=" + 'LGR'
							+ "; " + expires;

				}

				if (ratevalue == "Medium") {
					/* localStorage.setItem('accessibility','Y'); */

					var d = new Date();
					d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
					var expires = "expires=" + d.toGMTString();
					document.cookie = "accessibilitytextsize" + "=" + 'MDM'
							+ "; " + expires;
					var user = getCookie("accessibility");
					/*  alert("user"+user); */

				}
				if (ratevalue === "Smaller") {
					/*  localStorage.setItem('accessibility','N') */
					var d = new Date();
					d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
					var expires = "expires=" + d.toGMTString();
					document.cookie = "accessibilitytextsize" + "=" + 'SLR'
							+ "; " + expires;

				}

				if (ratevalue === "Smallest") {
					/*  localStorage.setItem('accessibility','N') */
					var d = new Date();
					d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
					var expires = "expires=" + d.toGMTString();
					document.cookie = "accessibilitytextsize" + "=" + 'SLT'
							+ "; " + expires;

				}
			}
		}
	}
	function setTextContrast2()
	{
		var textsize="Medium";
		var contrast="SC";
		$("input[name=textsize][value="+textsize+"]").prop('checked', true);
		$("input[name=contrastscheme][value="+contrast+"]").prop('checked', true);
		setcontrast2();
	}
</script>

</head>
<body>
		<ol class="breadcrumb">
		
			<c:if test="${empty userSession.employee.emploginname}" var="user">
				<li><a href="Home.html"><i class="fa fa-home"></i></a></li>
			</c:if>
			<c:if test="${!empty userSession.employee.emploginname and userSession.employee.emploginname eq'NOUSER' }" var="user">
				<li><a href="AdminHome.html"><i class="fa fa-home"></i></a></li>
			</c:if>
			<c:if test="${!empty userSession.employee.emploginname and userSession.employee.emploginname ne'NOUSER' }" var="user">
				<li><a href="AdminHome.html"><i class="fa fa-home"></i></a></li>
			</c:if>
			<li>Accessibility</li>
		</ol>
		<div class="content">
			<div class="widget">
				<div class="widget-header">
					<h2>Accessibility</h2>
				</div>
				<div class="widget-content padding">
						<div class="accesform">
							<div>
								<p class="bg-info padding-10">
									Accessibility options enables you to increase or decrease the
									font size and/or change color scheme of this website according
									to your preferences. All of us must have come across
									situations where we need the services of talented people but
									can't manage to get one, because of certain constraints.
								</p>

								<form:form method="post" action="Accessibility.html" id="frmchangetextsize1" cssClass="form-horizontal" modelAttribute="command">
								<fieldset id="textsizeoptions">
									<h4>Change Text Size</h4>
									<div class="form-group">
										<div class="col-xs-8 col-xs-offset-2">
											<label for="Largest" class="radio">
												<form:radiobutton id="Largest" path="textsize" value="Largest"/> Largest
											</label>
											<label for="Larger" class="radio">
												<form:radiobutton id="Larger" path="textsize" value="Larger"/> Larger
											</label>
											<label for="Medium" class="radio">
												<form:radiobutton id="Medium" path="textsize" value="Medium"/> Medium
											</label>
											<label for="Smaller" class="radio">
												<form:radiobutton id="Smaller" path="textsize" value="Smaller"/> Smaller
											</label>
											<label for="Smallest" class="radio">
												<form:radiobutton id="Smallest" path="textsize" value="Smallest"/> Smallest
											</label>
										</div>
									</div>
								</fieldset>
								<fieldset id="contrastoptions">
								<h4>Contrast Schemes</h4>
									<div class="form-group">
										<div class="col-xs-8 col-xs-offset-2">
										<label for="High" class="radio">
											<form:radiobutton id="High" path="contrastscheme" value="HC"/> High Contrast
										</label>
										<label for="Standard" class="radio">
											<form:radiobutton id="Standard" path="contrastscheme" value="SC"/> Standard Contrast
										</label>												
										</div>											
									</div>
								</fieldset>
								<div class="text-center padding-bottom-10">
									<c:if test="${ !empty userSession.employee.emploginname and userSession.employee.emploginname ne'NOUSER' }"	var="user">
										<input type="submit" name="applyoptions" value="Apply" class="btn btn-success btn-submit" onclick="setcontrast2()">
										<input type="submit" class="btn btn-warning" value="<spring:message code="" text="Reset"/>"  onclick="setTextContrast2()">
										<a href="AdminHome.html" class="btn btn-danger">Back</a>										
									</c:if>
									<c:if test="${empty userSession.employee.emploginname or userSession.employee.emploginname eq'NOUSER' }" var="user">
										<input type="submit" name="applyoptions" value="Apply" class="btn btn-success " onclick="setcontrast2()">
										<input type="submit" class="btn btn-warning" value="<spring:message code="" text="Reset"/>"  onclick="setTextContrast2()">
										<a href="AdminHome.html" class="btn btn-danger">Back</a>
									</c:if>
								</div>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>