<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HDFC- CCA Payment Gateway</title>
</head>
<!-- <body onload="test();"> -->
<body onload="document.pay.submit();">
	<div class="clearfix" id="home_content">
		<div class="col-xs-12">

			<div class="row">

				<div class="form-div"
					style="border: 1px solid #ff6600; padding: 20px; width: 60%; margin: 0 auto;"
					align="center">

					<p>
						We are redirecting to HDFC CCA in few secs. <br> Please do
						not click "Refresh" or back/forward button on your browser.
					</p>

					<form:form action="${command.payRequestMsg}" id="pay" name="pay"
						method="post">
					</form:form>

					<%-- <form id="nonseamless" method="post" name="redirect"
						action="https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction&encRequest=8a4835289108db555635db20e6f96af7bbf990f1ac20340189cd535a3bad09225d56f466b9dd64525f69f55d605d1b5fdc4b99248e943f618b46c35c930f6e6424725b30523d2c8c274613e4e2491cb6335db8b368dbf19f86351449e43f62ca2328095ec5d9e3b12c5a85e6e1973cc5ec774d9fe705ccf4fdf32ab4f24a2defc6f6aa23c237d9f4ac7aa9d2a06dcd6ae361313d6b9e78c1140ab767b2982adb8e075c8b8c6e8be82c955ef51c6305b3125731b9f96aa65c185ebeb20b9b65e4111c6e58c5807c7d0f129934aeb2e424e98e12fb2302be0292c5df0a586802063d11db41ff8dc4b11d59513da204eec63dc9d4f6a189a8b63c3c993a59b21d4f4d72f1160edff3c68d6fca604813b3aeb15e94cc4654ce57545d8127124906e25204018827d0675e8321e2c573a5c0fcb01b1c8e2a8a0a02f64e3c63336a21f19a3eae4105b700a5c0b8a0929ccd53396784c6c55de923e755ee8a00e9a0b783c657e0ec81049cbce65ae59b670a165fea6678b57aba91e054d7030c9d56b859&access_code=AVJU02FH12BC56UJCB">
						
					<!-- <input type="hidden" id="command" name="command" value="initiateTransaction"/> -->
					<!-- <input type="hidden" id="encRequest" name="encRequest"
						value="https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction&encRequest=78b956dee531655b074886b288f22ebb05c6dc1c3674edfcf73598aa12fcb720d7c73f70f9c6c528b6724e40c3ac941132620ad4a54fbbf86bea4e110ba48073661dcec5481903c67ae7bd59f274344dddbe2d9eff9db81e64e28489823e36b2af26215c91e0cdf5238e8c07cbb9c797e92063ad67d36544eba6e3a124b9a58a34d9c9450d6cb5f0a5fa03130486b068a35cc50c3dfb170db450160f02d46a42045e5af42bdb8e13433fa744bab24c6c17c139e2d1b7667c1129512df30afbfbf0483fbfe3c9f7222d50e7e8017ee7264b4da5d72aae8370b95439118d9d498fdf03bb3d59762ebac521c02273254dc8a27ffef9ce2440d4bdbee1092733bfce6ee9fb162d9201b879907d99310a2623ed33271c70b39c0f66cf0defe4a5211521a35d6133943fdac3900791d56456e42d9072f8449007ac0b8ea0eb59ade721a7e038413867fdec87722db6410915273a8bdee8f2a5b7ae25558e9f2022a78ebf5693210db36506d0f30dabb14c09a3&access_code=AVQM02FH02BA39MQAB">
					<input type="hidden" name="access_code" id="access_code"
						value="AVQM02FH02BA39MQAB"> -->
					<script language='javascript'>
						/* document.redirect.submit(); */
						
						function test()
						{
							alert("sdsdf");
							document.getElementById("nonseamless").submit();							}
					</script>
					</form> --%>

				</div>
			</div>
		</div>
	</div>
</body>
</html>